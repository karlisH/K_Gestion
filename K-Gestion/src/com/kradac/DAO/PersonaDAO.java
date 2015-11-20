package com.kradac.DAO;

import java.util.ArrayList;

import com.kradac.controlador.DBHelper;


import com.kradac.modelo.Persona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class PersonaDAO {
	private SQLiteDatabase database;
	private DBHelper dbhelper;
	
	public PersonaDAO(Context context){
		dbhelper=new DBHelper(context);
		 
		
	}
	public void abrirDB(){
		try{
		database=dbhelper.open();
		}catch(Exception e){
			Log.i(dbhelper.getDatabaseName(),"Error al abrir o crear la base de datos" + e);   
		}
	}
	public void close(){
		dbhelper.close();
	}
	public long Insertar(Persona p){
		long estado=0;
		try{
		ContentValues valores=new ContentValues();
		valores.put("cedula", p.getCedula());
		valores.put("apellido", p.getApellido());
		valores.put("nombre", p.getNombre());
		valores.put("email", p.getEmail());
		valores.put("telefono",p.getTelefono());
		valores.put("username", p.getUsuario());
		valores.put("password", p.getPassword());
		estado=database.insert("persona", null, valores);
		}catch(Exception e){
			estado=0;
		}
		return estado;
		
	}
	public long EliminarRegistro(int codigo){
		long estado=0;
		try{
			estado=database.delete("persona", "codigo=?", new String[]{String.valueOf(codigo)});
		}catch(Exception e){
			estado=0;
		}
		return estado;
	}
	public long ModificarRegistro(Persona p){
		long estado=0;
		try{
			ContentValues valores=new ContentValues();
			valores.put("cedula", p.getCedula());
			valores.put("apellido", p.getApellido());
			valores.put("nombre", p.getNombre());
			valores.put("telefono",p.getTelefono());
			valores.put("username", p.getUsuario());
			valores.put("password", p.getPassword());
			estado=database.update("persona",valores,"cedula="+p.getCedula(),null);
			}catch(Exception e){
				estado=0;
			}
			return estado;
			
	}
	public ArrayList<Persona>ListaTodasPersonas(){
		Cursor c;
		ArrayList<Persona> listado=new ArrayList<Persona>();
		c=database.rawQuery("SELECT *FROM persona ", null);
		while (c.moveToNext()) {
			Persona p = new Persona();
			p.setCedula(c.getInt(0));
			p.setNombre(c.getString(1));
			p.setApellido(c.getString(2));
			p.setEmail(c.getString(3));
			p.setTelefono(c.getInt(4));
			p.setUsuario(c.getString(5));
			p.setPassword(c.getString(6));
			listado.add(p);
					
		}
		c.close();
		return listado;
	}
	public Persona buscarPersona(int cedula){
		Persona persona=new Persona();
		Cursor c;
		//c=
		return persona;
	}
}
