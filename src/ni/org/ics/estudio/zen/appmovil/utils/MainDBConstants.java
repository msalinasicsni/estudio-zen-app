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

public class MainDBConstants {

	//Base de datos y tablas
	public static final String DATABASE_NAME = "icsestudioszencryp.sqlite3";
	public static final int DATABASE_VERSION = 1;
	
	//Campos metadata
	public static final String pasive = "pasive";
	public static final String deviceId = "identificador_equipo";

	
	//Tabla usuarios
	public static final String USER_TABLE = "users";
	//Campos usuarios
	public static final String username = "username";
	public static final String created = "created";
	public static final String modified = "modified";
	public static final String lastAccess = "lastaccess";
	public static final String password = "password";
	public static final String completeName = "completename";
	public static final String email = "email";
	public static final String enabled = "enabled";
	public static final String accountNonExpired = "accountnonexpired";
	public static final String credentialsNonExpired = "credentialsnonexpired";
	public static final String lastCredentialChange = "lastcredentialchange";
	public static final String accountNonLocked = "accountnonlocked";
	public static final String createdBy = "createdby";
	public static final String modifiedBy = "modifiedby";

	//Crear tabla usuarios
	public static final String CREATE_USER_TABLE = "create table if not exists "
			+ USER_TABLE + " ("
			+ username + " text not null, "  
			+ created + " date, " 
			+ modified + " date, "
			+ lastAccess + " date, "
			+ password + " text not null, "
			+ completeName + " text, "
			+ email + " text, "
			+ enabled  + " boolean, " 
			+ accountNonExpired  + " boolean, "
			+ credentialsNonExpired  + " boolean, "
			+ lastCredentialChange + " date, "
			+ accountNonLocked  + " boolean, "
			+ createdBy + " text, "
			+ modifiedBy + " text, "
			+ "primary key (" + username + "));";
	
	//Tabla roles
	public static final String ROLE_TABLE = "roles";

	//Campos roles
	public static final String role = "role";

	//Crear tabla roles
	public static final String CREATE_ROLE_TABLE = "create table if not exists "
			+ ROLE_TABLE + " ("
			+ username + " text not null, "  
			+ role + " text not null, "
			+ "primary key (" + username + "," + role + "));";

	//Tabla Participantes
    public static final String PARTICIPANTE_TABLE = "participantes";

    // Campos Participantes
    public static final String nombre1 = "nombre1";
    public static final String nombre2 = "nombre2";
    public static final String apellido1 = "apellido1";
    public static final String apellido2 = "apellido2";
    public static final String sexo = "sexo";
    public static final String fechaNac = "fechaNac";
    public static final String nombre1Padre = "nombre1Padre";
    public static final String nombre2Padre = "nombre2Padre";
    public static final String apellido1Padre = "apellido1Padre";
    public static final String apellido2Padre = "apellido2Padre";
    public static final String nombre1Madre = "nombre1Madre";
    public static final String nombre2Madre = "nombre2Madre";
    public static final String apellido1Madre = "apellido1Madre";
    public static final String apellido2Madre = "apellido2Madre";
    public static final String codigo = "codigo";
    public static final String codigoCasa = "codigoCasa";
    public static final String codigoBarrio = "codigoBarrio";
    public static final String nombreBarrio = "nombreBarrio";
    public static final String direccion = "direccion";
    public static final String manzana = "manzana";
    public static final String estudios = "estudios";
    public static final String estPart = "estPart";
    public static final String tutor = "tutor";
    public static final String relacionFamTutor = "relacionFamTutor";

    //Crear tabla participantes
    public static final String CREATE_PARTICIPANTE_TALBE = "create table if not exists "
            + PARTICIPANTE_TABLE + " ("
            + codigo + " integer not null, "
            + nombre1 + " text not null, "
            + nombre2 + " text, "
            + apellido1 + " text not null, "
            + apellido2 + " text, "
            + sexo + " text, "
            + fechaNac + " date, "
            + nombre1Padre + " text not null, "
            + nombre2Padre + " text, "
            + apellido1Padre + " text not null, "
            + apellido2Padre + " text, "
            + nombre1Madre + " text not null, "
            + nombre2Madre + " text, "
            + apellido1Madre + " text not null, "
            + apellido2Madre + " text, "
            + codigoCasa + " integer ,"
            + codigoBarrio + " integer, "
            + nombreBarrio + " text, "
            + direccion + " text, "
            + manzana + " text, "
            + estudios + " text, "
            + estPart + " integer, "
            + tutor + " tutor, "
            + relacionFamTutor + " relacionFamTutor, "
    + "primary key (" + codigo + "));";
}
