package com.kradac.kgestion;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kradac.kgestion.R;
import com.kradac.kgestion.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Form extends Activity {
	Context mycontext;
	Connection conexionMySQL;
	private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
	private File file = new File(ruta_fotos);
	private static final int TAKE_PHOTO_CODE = 1;
	ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		mycontext = this;

		TextView txtLatitud = (TextView) findViewById(id.txtLatitud);
		TextView txtLongitud = (TextView) findViewById(id.txtLongitud);
		final TextView txtSubject = (TextView) findViewById(id.txtSubject);
		
		Button btnTomarFoto=(Button)findViewById(id.btnfoto);
		Button btnSend = (Button) findViewById(id.btnSend);
		Button btnCancel = (Button) findViewById(id.btnCancel);
		img=(ImageView)findViewById(id.imgfoto1);
		txtLatitud.setText("" + MainActivity.latitud);
		txtLongitud.setText("" + MainActivity.longitud);
		btnTomarFoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*String file = ruta_fotos  + ".jpg";
				   File mi_foto = new File( file );
				     try {
				                  mi_foto.createNewFile();
				              } catch (IOException ex) {              
				               Log.e("ERROR ", "Error:" + ex);
				             }       
				              //
				              Uri uri = Uri.fromFile( mi_foto );
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				             //Guarda imagen
				            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				            //Retorna a la actividad
				             startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
			*/
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
				onActivityResult(RESULT_OK, TAKE_PHOTO_CODE, cameraIntent);
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String QSLEjecutar = "select * from person limit 10";

					conectarBDMySQL("kradacLoja", "kradac", "200.0.29.117",
							"3306", "kgestiondb");

					Statement st = conexionMySQL.createStatement();
					ResultSet rs = st.executeQuery(QSLEjecutar);

					String resultadoSQL = "";
					Integer numColumnas = 0;

					// número de columnas (campos) de la consula SQL
					numColumnas = rs.getMetaData().getColumnCount();

					// mostramos el resultado
					while (rs.next()) {
						for (int i = 1; i <= numColumnas; i++) {
							if (rs.getObject(i) != null) {
								if (resultadoSQL != "")
									if (i < numColumnas)
										resultadoSQL = resultadoSQL
												+ rs.getObject(i).toString()
												+ ";";
									else
										resultadoSQL = resultadoSQL
												+ rs.getObject(i).toString();
								else if (i < numColumnas)
									resultadoSQL = rs.getObject(i).toString()
											+ ";";
								else
									resultadoSQL = rs.getObject(i).toString();
							} else {
								if (resultadoSQL != "")
									resultadoSQL = resultadoSQL + "null;";
								else
									resultadoSQL = "null;";
							}
						}
						resultadoSQL = resultadoSQL + "n";
					}
					txtSubject.setText(resultadoSQL);
					st.close();
					rs.close();
				}

				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void conectarBDMySQL(String usuario, String contrasena, String ip,
			String puerto, String catalogo) {
		if (conexionMySQL == null) {
			String urlConexionMySQL = "";
			if (catalogo != "")
				urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/"
						+ catalogo;
			else
				urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
			if (usuario != "" & contrasena != "" & ip != "" & puerto != "") {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conexionMySQL = DriverManager.getConnection(
							urlConexionMySQL, usuario, contrasena);
				} catch (ClassNotFoundException e) {
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(), Toast.LENGTH_SHORT)
							.show();
				} catch (SQLException e) {
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch(requestCode){
			case TAKE_PHOTO_CODE:
			// Con la foto hecha y guardada
			// Solo nos queda mostrarla
			final File file = getTempFile(this);
			img.setImageURI(Uri.fromFile(file));
			break;
			}
			}
	}
	private File getTempFile(Context context){
		// Creamos la ruta donde guardar la foto
		// que hagamos con nuestro programa
		final File path = new File( Environment.getExternalStorageDirectory(),
		context.getPackageName() );
		if(!path.exists()){
		path.mkdir();
		}
		return new File(path, "image.jpg");
		}
}
