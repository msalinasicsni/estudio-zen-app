package ni.org.ics.estudio.zen.appmovil.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import ni.org.ics.estudio.zen.appmovil.domain.ParticipanteZen;
import ni.org.ics.estudio.zen.appmovil.utils.MainDBConstants;

import java.util.Date;

public class ParticipanteHelper {

    public static ContentValues crearParticipanteContentValues(ParticipanteZen participante){
        ContentValues cv = new ContentValues();
        cv.put(MainDBConstants.codigo, participante.getCodigo());
        cv.put(MainDBConstants.nombre1, participante.getNombre1());
        cv.put(MainDBConstants.nombre2, participante.getNombre2());
        cv.put(MainDBConstants.apellido1, participante.getApellido1());
        cv.put(MainDBConstants.apellido2, participante.getApellido2());
        cv.put(MainDBConstants.sexo, participante.getSexo());
        if (participante.getFechaNac()!=null) cv.put(MainDBConstants.fechaNac, participante.getFechaNac().getTime());
        cv.put(MainDBConstants.nombre1Padre, participante.getNombre1Padre());
        cv.put(MainDBConstants.nombre2Padre, participante.getNombre2Padre());
        cv.put(MainDBConstants.apellido1Padre, participante.getApellido1Padre());
        cv.put(MainDBConstants.apellido2Padre, participante.getApellido2Padre());
        cv.put(MainDBConstants.nombre1Madre, participante.getNombre1Madre());
        cv.put(MainDBConstants.nombre2Madre, participante.getNombre2Madre());
        cv.put(MainDBConstants.apellido1Madre, participante.getApellido1Madre());
        cv.put(MainDBConstants.apellido2Madre, participante.getApellido2Madre());
        cv.put(MainDBConstants.codigoCasa, participante.getCodigoCasa());
        cv.put(MainDBConstants.codigoBarrio, participante.getCodigoBarrio());
        cv.put(MainDBConstants.nombreBarrio, participante.getNombreBarrio());
        cv.put(MainDBConstants.direccion, participante.getDireccion());
        cv.put(MainDBConstants.manzana, participante.getManzana());
        cv.put(MainDBConstants.estudios, participante.getEstudios());
        cv.put(MainDBConstants.estPart, participante.getEstPart());
        cv.put(MainDBConstants.tutor, participante.getTutor());
        cv.put(MainDBConstants.relacionFamTutor, participante.getRelacionFamTutor());

        return cv;
    }

    public static ParticipanteZen crearParticipante(Cursor cursor){

        ParticipanteZen mParticipante = new ParticipanteZen();
        mParticipante.setCodigo(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigo)));
        mParticipante.setNombre1(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1)));
        mParticipante.setNombre2(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2)));
        mParticipante.setApellido1(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1)));
        mParticipante.setApellido2(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2)));
        mParticipante.setSexo(cursor.getString(cursor.getColumnIndex(MainDBConstants.sexo)));
        mParticipante.setFechaNac(new Date(cursor.getLong(cursor.getColumnIndex(MainDBConstants.fechaNac))));
        mParticipante.setNombre1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Padre)));
        mParticipante.setNombre2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Padre)));
        mParticipante.setApellido1Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Padre)));
        mParticipante.setApellido2Padre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Padre)));
        mParticipante.setNombre1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre1Madre)));
        mParticipante.setNombre2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombre2Madre)));
        mParticipante.setApellido1Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido1Madre)));
        mParticipante.setApellido2Madre(cursor.getString(cursor.getColumnIndex(MainDBConstants.apellido2Madre)));
        mParticipante.setCodigoCasa(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigoCasa)));
        mParticipante.setCodigoBarrio(cursor.getInt(cursor.getColumnIndex(MainDBConstants.codigoBarrio)));
        mParticipante.setNombreBarrio(cursor.getString(cursor.getColumnIndex(MainDBConstants.nombreBarrio)));
        mParticipante.setDireccion(cursor.getString(cursor.getColumnIndex(MainDBConstants.direccion)));
        mParticipante.setManzana(cursor.getString(cursor.getColumnIndex(MainDBConstants.manzana)));
        mParticipante.setEstudios(cursor.getString(cursor.getColumnIndex(MainDBConstants.estudios)));
        mParticipante.setEstPart(cursor.getInt(cursor.getColumnIndex(MainDBConstants.estPart)));
        mParticipante.setTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.tutor)));
        mParticipante.setRelacionFamTutor(cursor.getString(cursor.getColumnIndex(MainDBConstants.relacionFamTutor)));

        return mParticipante;
    }
}
