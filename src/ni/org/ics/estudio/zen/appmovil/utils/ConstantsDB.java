/*
 * Copyright (C) 2013 ICS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ni.org.ics.estudio.zen.appmovil.utils;

public class ConstantsDB {

    public static final String VIS_EXITO = "VisitaExitosa";

    //Base de datos y tablas
    public static final String USER_PERM_TABLE = "usuarios_permisos";
    public static final String PART_TABLE = "participantes";

    //Campos roles
    //public static final String AUTH = "rol";

    //Campos usuarios
    public static final String USERNAME = "username";
    public static final String ENABLED = "enabled";
    public static final String PASSWORD = "password";
    public static final String U_MUESTRA = "muestra";
    public static final String U_ECASA = "ecasa";
    public static final String U_EPART = "eparticipante";
    public static final String U_ELACT = "elactancia";
    public static final String U_ESAT = "esatisfaccion";
    public static final String U_OBSEQUIO = "obsequio";
    public static final String U_PYT = "pesotalla";
    public static final String U_VAC = "vacunas";
    public static final String U_VISITA = "visitas";
    public static final String U_RECEPCION = "recepcion";
    public static final String U_CONS = "consentimiento";
    public static final String U_CASAZIKA = "casazika";
    public static final String U_TAMZIKA = "tamizajezika";
    public static final String U_PARTO = "datosparto";

    public static final String nombre2 = "nombre2";

    public static final String CREATE_USER_PERM_TABLE = "create table if not exists "
            + USER_PERM_TABLE + " ("
            + USERNAME + " text not null, "
            + U_MUESTRA + " boolean, "
            + U_ECASA + " boolean, "
            + U_EPART + " boolean, "
            + U_ELACT + " boolean, "
            + U_ESAT + " boolean, "
            + U_OBSEQUIO + " boolean, "
            + U_PYT + " boolean, "
            + U_VAC + " boolean, "
            + U_VISITA + " boolean, "
            + U_RECEPCION + " boolean, "
            + U_CONS + " boolean, "
            + U_CASAZIKA + " boolean, "
            + U_TAMZIKA + " boolean, "
            + U_PARTO + " boolean, "
            + "primary key (" + USERNAME + "));";
}

