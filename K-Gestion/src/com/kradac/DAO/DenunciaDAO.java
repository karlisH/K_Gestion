package com.kradac.DAO;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.util.Log;

import com.kradac.controlador.DBHelper;
import com.kradac.modelo.Denuncia;
import com.kradac.modelo.Persona;

public class DenunciaDAO {
	private SQLiteDatabase database;
	private DBHelper dbhelper;
	
	public DenunciaDAO(Context context){
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
	public long Insertar(Denuncia d){
		long estado=0;
		try{
		ContentValues valores=new ContentValues();
		valores.put("titulo", d.getTitulo());
		valores.put("asunto", d.getAsunto());
		valores.put("imagen", (d.getImagen().toString()));
		valores.put("fecha", d.getFecha().toString());
		estado=database.insert("denuncia", null, valores);
		}catch(Exception e){
			estado=0;
		}
		return estado;
		
	}
	public long EliminarRegistro(int codigo){
		long estado=0;
		try{
			estado=database.delete("denuncia", "id_denuncia=?", new String[]{String.valueOf(codigo)});
		}catch(Exception e){
			estado=0;
		}
		return estado;
	}
	public long ModificarRegistro(Denuncia d){
		long estado=0;
		try{
			ContentValues valores=new ContentValues();
			valores.put("titulo", d.getTitulo());
			valores.put("asunto", d.getAsunto());
			valores.put("imagen", (d.getImagen().toString()));
			Date fecha = new Date();
			DateFormat df = DateFormat.getDateInstance();
			String s = df.format(fecha);
			valores.put("fecha", s);
			estado=database.update("denuncia",valores,"id_denuncia="+d.getId(),null);
			}catch(Exception e){
				estado=0;
			}
			return estado;
			
	}
	public ArrayList<Denuncia>ListaTodasDenuncias(){
		Cursor c;
		ArrayList<Denuncia> listado=new ArrayList<Denuncia>();
		c=database.rawQuery("SELECT *FROM denuncia ", null);
		while (c.moveToNext()) {
			Denuncia d=new Denuncia();
			d.setId(c.getInt(0));
			d.setTitulo(c.getString(1));
			d.setAsunto(c.getString(2));
			d.setImagen(c.getString(3));			
			Date fecha=new Date();
			DateFormat df=DateFormat.getDateInstance();
			String s = df.format(c.getInt(4));
			d.setFecha(fecha);
			listado.add(d);
					
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
