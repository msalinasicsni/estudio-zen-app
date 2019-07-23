package ni.org.ics.estudio.zen.appmovil.tasks;

import android.content.Context;
import android.util.Log;
import ni.org.ics.estudio.zen.appmovil.database.EstudiosAdapter;

import ni.org.ics.estudio.zen.appmovil.domain.ParticipanteZen;
import ni.org.ics.estudio.zen.appmovil.domain.users.Authority;
import ni.org.ics.estudio.zen.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudio.zen.appmovil.domain.users.UserSistema;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class DownloadBaseTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadBaseTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadBaseTask.class.getSimpleName();
	private EstudiosAdapter estudioAdapter = null;

    private List<UserSistema> usuarios = null;
    private List<Authority> roles = null;
    private List<UserPermissions> permisos = null;
	private List<ParticipanteZen> mParticipantes = null;

    public static final String CATALOGOS = "1";
    public static final String USUARIOS = "2";
    public static final String ROLES= "3";
    public static final String USU_PERMISOS = "4";
	public static final String PARTICIPANTE = "5";

    public static final String PARTICIPANTE_PROC = "6";
    private static final String TOTAL_TASK_GENERALES = "7";

	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int v =0;
    private String codigo;

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];
		
		try {
            error = descargarDatosGenerales();
            error = descargarUsuarios();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		publishProgress("Abriendo base de datos...","1","1");
		estudioAdapter = new EstudiosAdapter(mContext, password, false,false);
		estudioAdapter.open();
        //Borrar los datos de la base de datos
        estudioAdapter.borrarTodosPermisos();
		estudioAdapter.borrarParticipantes();

        try {
            if (usuarios != null){
                try {
                    addUsuarios(usuarios);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
             }
            if (roles != null){
                try {
                    addRoles(roles);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
            }

            if (permisos != null){
                try {
                    addPermisos(permisos);
                } catch (Exception e) {
                    // Regresa error al insertar
                    e.printStackTrace();
                    return e.getLocalizedMessage();
                }
            }


			if (mParticipantes != null){
				v = mParticipantes.size();
				ListIterator<ParticipanteZen> iter = mParticipantes.listIterator();
				while (iter.hasNext()){
					estudioAdapter.crearParticipante(iter.next());
					publishProgress("Insertando participantes en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
				mParticipantes = null;
			}
        } catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}finally {
            estudioAdapter.close();

        }
		return error;
	}
    // url, username, password
    protected String descargarUsuarios() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;

            urlRequest = url + "/movil/usuarios";

            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            publishProgress("Solicitando usuarios",USUARIOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserSistema[]> responseEntity = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserSistema[].class);

            // convert the array to a list and return it
            usuarios = Arrays.asList(responseEntity.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/roles";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);

            // Create a new RestTemplate instance
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            publishProgress("Solicitando roles",ROLES,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<Authority[]> responseEntityRoles = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    Authority[].class);

            // convert the array to a list and return it
            roles = Arrays.asList(responseEntityRoles.getBody());

            // The URL for making the GET request
            urlRequest = url + "/movil/permisos";

            // Set the Accept header for "application/json"
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            publishProgress("Solicitando permisos de usuarios",USU_PERMISOS,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<UserPermissions[]> responseEntityPerm = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    UserPermissions[].class);

            // convert the array to a list and return it
            permisos = Arrays.asList(responseEntityPerm.getBody());

            return null;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }

    // url, username, password
    protected String descargarDatosGenerales() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar participantes
            urlRequest = url + "/movil/participantesZen/";
            publishProgress("Solicitando participantes",PARTICIPANTE,TOTAL_TASK_GENERALES);
            // Perform the HTTP GET request
            ResponseEntity<ParticipanteZen[]> responseEntityParticipante = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
                    ParticipanteZen[].class);
            // convert the array to a list and return it
            mParticipantes = Arrays.asList(responseEntityParticipante.getBody());
            responseEntityParticipante = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }



    private void addUsuarios(List<UserSistema> usuarios) throws Exception {
        int v = usuarios.size();
        ListIterator<UserSistema> iter = usuarios.listIterator();

        while (iter.hasNext()){
            UserSistema usuario = iter.next();
            if (!estudioAdapter.existeUsuario(usuario.getUsername())){
                estudioAdapter.crearUsuario(usuario);
            }
            else{
                estudioAdapter.actualizarUsuario(usuario);
            }
            publishProgress("Insertando Usuarios", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

    private void addRoles(List<Authority> roles) throws Exception {

        int v = roles.size();

        ListIterator<Authority> iter = roles.listIterator();

        while (iter.hasNext()){
            estudioAdapter.crearRol(iter.next());
            publishProgress("Insertando Roles", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }

    private void addPermisos(List<UserPermissions> permisos) throws Exception {
        int v = permisos.size();
        ListIterator<UserPermissions> iter = permisos.listIterator();

        while (iter.hasNext()){
            estudioAdapter.crearPermisosUsuario(iter.next());
            publishProgress("Insertando Permisos", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }
    }

   /* private void addParticipantes(List<Participante> participantes) throws Exception {

        int v = participantes.size();

        ListIterator<Participante> iter = participantes.listIterator();

        while (iter.hasNext()){
            estudioAdapter.crearParticipante(iter.next());
            publishProgress("Participantes", Integer.valueOf(iter.nextIndex()).toString(), Integer
                    .valueOf(v).toString());
        }

    }*/
}
