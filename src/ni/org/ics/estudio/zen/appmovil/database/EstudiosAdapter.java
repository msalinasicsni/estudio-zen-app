package ni.org.ics.estudio.zen.appmovil.database;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ni.org.ics.estudio.zen.appmovil.domain.ParticipanteZen;
import ni.org.ics.estudio.zen.appmovil.domain.users.Authority;
import ni.org.ics.estudio.zen.appmovil.domain.users.UserPermissions;
import ni.org.ics.estudio.zen.appmovil.domain.users.UserSistema;
import ni.org.ics.estudio.zen.appmovil.helpers.ParticipanteHelper;
import ni.org.ics.estudio.zen.appmovil.helpers.UserSistemaHelper;
import ni.org.ics.estudio.zen.appmovil.utils.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteQueryBuilder;

public class EstudiosAdapter {

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	private final String mPassword;
	private final boolean mFromServer;
	private final boolean mCleanDb;


	public EstudiosAdapter(Context context, String password, boolean fromServer, boolean cleanDb) {
		mContext = context;
		mPassword = password;
		mFromServer = fromServer;
		mCleanDb = cleanDb;
	}

	private static class DatabaseHelper extends EstudiosSQLiteOpenHelper {

		DatabaseHelper(Context context, String password, boolean fromServer, boolean cleanDb) {
			super(FileUtils.DATABASE_PATH, MainDBConstants.DATABASE_NAME, MainDBConstants.DATABASE_VERSION, context,
					password, fromServer, cleanDb);
			createStorage();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MainDBConstants.CREATE_USER_TABLE);
			db.execSQL(MainDBConstants.CREATE_ROLE_TABLE);
			db.execSQL(MainDBConstants.CREATE_PARTICIPANTE_TALBE);
            db.execSQL(ConstantsDB.CREATE_USER_PERM_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
		}
	}

	public EstudiosAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext,mPassword,mFromServer,mCleanDb);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Crea un cursor desde la base de datos
	 *
	 * @return cursor
	 */
	public Cursor crearCursor(String tabla, String whereString, String projection[], String ordenString) throws SQLException {
		Cursor c = null;
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(tabla);
		c = qb.query(mDb,projection,whereString,null,null,null,ordenString);
		return c;
	}

	public static boolean createStorage() {
		return FileUtils.createFolder(FileUtils.DATABASE_PATH);
	}

	/**
	 * Metodos para usuarios en la base de datos
	 *
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */

	//Crear nuevo usuario en la base de datos
	public void crearUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		mDb.insert(MainDBConstants.USER_TABLE, null, cv);
	}

	//Editar usuario existente en la base de datos
	public boolean editarUsuario(UserSistema user) {
		ContentValues cv = UserSistemaHelper.crearUserSistemaContentValues(user);
		return mDb.update(MainDBConstants.USER_TABLE, cv, MainDBConstants.username + "='"
				+ user.getUsername()+"'", null) > 0;
	}

	//Limpiar la tabla de usuarios de la base de datos
	public boolean borrarUsuarios() {
		return mDb.delete(MainDBConstants.USER_TABLE, null, null) > 0;
	}
	//Obtener un usuario de la base de datos
	public UserSistema getUsuario(String filtro, String orden) throws SQLException {
		UserSistema mUser = null;
		Cursor cursorUser = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserSistema(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}

	//Obtener una lista de usuarios de la base de datos
	public List<UserSistema> getUsuarios(String filtro, String orden) throws SQLException {
		List<UserSistema> mUsuarios = new ArrayList<UserSistema>();
		Cursor cursorUsuarios = crearCursor(MainDBConstants.USER_TABLE, filtro, null, orden);
		if (cursorUsuarios != null && cursorUsuarios.getCount() > 0) {
			cursorUsuarios.moveToFirst();
			mUsuarios.clear();
			do{
				UserSistema mUser = null;
				mUser = UserSistemaHelper.crearUserSistema(cursorUsuarios);
				mUsuarios.add(mUser);
			} while (cursorUsuarios.moveToNext());
		}
		if (!cursorUsuarios.isClosed()) cursorUsuarios.close();
		return mUsuarios;
	}

	/**
	 * Metodos para roles en la base de datos
	 *
	 * @param rol
	 *            Objeto Authority que contiene la informacion
	 *
	 */
	//Crear nuevo rol en la base de datos
	public void crearRol(Authority rol) {
		ContentValues cv = UserSistemaHelper.crearRolValues(rol);
		mDb.insert(MainDBConstants.ROLE_TABLE, null, cv);
	}
	//Limpiar la tabla de roles de la base de datos
	public boolean borrarRoles() {
		return mDb.delete(MainDBConstants.ROLE_TABLE, null, null) > 0;
	}
	//Verificar un rol de usuario
	public Boolean buscarRol(String username, String Rol) throws SQLException {
		Cursor c = mDb.query(true, MainDBConstants.ROLE_TABLE, null,
				MainDBConstants.username + "='" + username + "' and " + MainDBConstants.role + "='" + Rol + "'" , null, null, null, null, null);
		boolean result = c != null && c.getCount()>0;
		c.close();
		return result;
	}

	//Obtener los permisos de un usuario de la base de datos
	public UserPermissions getPermisosUsuario(String filtro, String orden) throws SQLException {
		UserPermissions mUser = null;
		Cursor cursorUser = crearCursor(ConstantsDB.USER_PERM_TABLE, filtro, null, orden);
		if (cursorUser != null && cursorUser.getCount() > 0) {
			cursorUser.moveToFirst();
			mUser=UserSistemaHelper.crearUserPermissions(cursorUser);
		}
		if (!cursorUser.isClosed()) cursorUser.close();
		return mUser;
	}

	/**
	 * Metodos para participantes en la base de datos
	 *
	 * @param participante
	 *            Objeto Participante que contiene la informacion
	 *
	 */

	//Crear nuevo Participante en la base de datos
	public void crearParticipante(ParticipanteZen participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		mDb.insertOrThrow(MainDBConstants.PARTICIPANTE_TABLE, null, cv);
	}

	//Crear nuevo Participante en la base de datos desde otro equipo
	public void insertarParticipante(String participanteSQL) {
		mDb.execSQL(participanteSQL);
	}

	//Editar Participante existente en la base de datos
	public boolean editarParticipante(ParticipanteZen participante) {
		ContentValues cv = ParticipanteHelper.crearParticipanteContentValues(participante);
		return mDb.update(MainDBConstants.PARTICIPANTE_TABLE , cv, MainDBConstants.codigo + "="
				+ participante.getCodigo(), null) > 0;
	}
	//Limpiar la tabla de Participante de la base de datos
	public boolean borrarParticipantes() {
		return mDb.delete(MainDBConstants.PARTICIPANTE_TABLE, null, null) > 0;
	}
	//Obtener una Participante de la base de datos
	public ParticipanteZen getParticipante(String filtro, String orden) throws SQLException {
        ParticipanteZen mParticipante = null;
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE , filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipante=ParticipanteHelper.crearParticipante(cursorParticipante);
			//Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);

			//ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
			//mParticipante.setProcesos(procesos);

			//mParticipante.setCasa(casa);
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipante;
	}
	//Obtener una lista de Participante de la base de datos
	public List<ParticipanteZen> getParticipantes(String filtro, String orden) throws SQLException {
		List<ParticipanteZen> mParticipantes = new ArrayList<ParticipanteZen>();
		Cursor cursorParticipante = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, filtro, null, orden);
		if (cursorParticipante != null && cursorParticipante.getCount() > 0) {
			cursorParticipante.moveToFirst();
			mParticipantes.clear();
			do{
                ParticipanteZen mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(cursorParticipante);
				//Casa casa = this.getCasa(MainDBConstants.codigo + "=" +cursorParticipante.getInt(cursorParticipante.getColumnIndex(MainDBConstants.casa)), null);
				//mParticipante.setCasa(casa);

				//ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
				//mParticipante.setProcesos(procesos);

				mParticipantes.add(mParticipante);
			} while (cursorParticipante.moveToNext());
		}
		if (!cursorParticipante.isClosed()) cursorParticipante.close();
		return mParticipantes;
	}

	/**
	 * Obtiene Lista todas las participantes buscando por nombre
	 *
	 * @return lista con participantes
	 */
	public List<ParticipanteZen> getListaParticipantesName(String name) throws SQLException {
		Cursor participantes = null;
		List<ParticipanteZen> mParticipantes = new ArrayList<ParticipanteZen>();
		participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null);
		//participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
		//MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

		if (participantes != null && participantes.getCount() > 0) {
			participantes.moveToFirst();
			mParticipantes.clear();
			do{
                ParticipanteZen mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(participantes);

				//Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
				//mParticipante.setCasa(casa);

				//ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
				//mParticipante.setProcesos(procesos);

				mParticipantes.add(mParticipante);
			} while (participantes.moveToNext());
		}
		if (!participantes.isClosed()) participantes.close();
		return mParticipantes;
	}

	/**
	 * Obtiene Lista todas las participantes buscando por nombre
	 *
	 * @return lista con participantes
	 */
	public List<ParticipanteZen> getListaParticipantesLastName(String lastname) throws SQLException {
		Cursor participantes = null;
		List<ParticipanteZen> mParticipantes = new ArrayList<ParticipanteZen>();
		participantes = crearCursor(MainDBConstants.PARTICIPANTE_TABLE, MainDBConstants.apellido1 + " LIKE '%" + lastname + "%' OR "+ MainDBConstants.apellido2 + " LIKE '%" + lastname + "%'", null, null);
		//participantes = mDb.query(true, ConstantsDB.PART_TABLE, null,
		//MainDBConstants.nombre1 + " LIKE '%" + name + "%' OR "+ ConstantsDB.nombre2 + " LIKE '%" + name + "%'", null, null, null, null, null);

		if (participantes != null && participantes.getCount() > 0) {
			participantes.moveToFirst();
			mParticipantes.clear();
			do{
                ParticipanteZen mParticipante = null;
				mParticipante = ParticipanteHelper.crearParticipante(participantes);

				//Casa casa = this.getCasa(MainDBConstants.codigo + "=" +participantes.getInt(participantes.getColumnIndex(MainDBConstants.casa)), null);
				//mParticipante.setCasa(casa);

				//ParticipanteProcesos procesos = this.getParticipanteProcesos(ConstantsDB.CODIGO+"="+mParticipante.getCodigo().toString(), null);
				//mParticipante.setProcesos(procesos);

				mParticipantes.add(mParticipante);
			} while (participantes.moveToNext());
		}
		if (!participantes.isClosed()) participantes.close();
		return mParticipantes;
	}

	/**
	 * Borra todos los roles de la base de datos
	 *
	 * @return verdadero o falso
	 */
    public boolean borrarTodosPermisos() {
        return mDb.delete(ConstantsDB.USER_PERM_TABLE, null, null) > 0;
    }

	/**
	 * Inserta un usuario en la base de datos
	 *
	 * @param user
	 *            Objeto Usuario que contiene la informacion
	 *
	 */
	public void crearPermisosUsuario(UserPermissions user) {
		ContentValues cv = UserSistemaHelper.crearPermisosUsuario(user);
		mDb.insert(ConstantsDB.USER_PERM_TABLE, null, cv);
	}


    public Boolean verificarData() throws SQLException{
        return false;
    }

    /**
     * Busca un usuario de la base de datos
     *
     * @return true or false
     */

    public boolean existeUsuario(String user) throws SQLException {
        Cursor c = null;
        c = mDb.query(true, MainDBConstants.USER_TABLE, null,
                ConstantsDB.USERNAME + "='" + user +"'", null, null, null, null, null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            if (!c.isClosed()) c.close();
            return true;
        }
        if (!c.isClosed()) c.close();
        return false;
    }

    /**
     * Actualiza un usuario en la base de datos
     *
     * @param user
     *            Objeto Usuario que contiene la informacion
     *
     */
    public boolean actualizarUsuario(UserSistema user) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.USERNAME, user.getUsername());
        cv.put(ConstantsDB.ENABLED, user.getEnabled());
        return mDb.update(MainDBConstants.USER_TABLE, cv, ConstantsDB.USERNAME + "='"
                + user.getUsername() + "'", null) > 0;
    }
}
