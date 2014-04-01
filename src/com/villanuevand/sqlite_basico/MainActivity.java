package com.villanuevand.sqlite_basico;



import com.villanuevand.dbadapter.DbAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText userName,password;
	private DbAdapter helper;
	private static final String TAG_SQL = "EJEMPLO-SQLITE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userName = (EditText) findViewById(R.id.userNameValue);
		password = (EditText) findViewById(R.id.passwordValue);	
		helper = new DbAdapter(getBaseContext());		
	}
	
	public void addUser(View view){
		String name = userName.getText().toString();
		String pass = password.getText().toString();
		long id = helper.insertData(name, pass);
		if(id < 0)
			Log.e(TAG_SQL,"Error al insertar");
		else{
			Log.i(TAG_SQL, "Registro insertado correctamente");
		} 	
		userName.setText("");
		password.setText("");
		userName.requestFocus();
	}
	
	public void viewDetails(View view){
		String data = helper.getAllData();
		Toast.makeText(getApplication(), data, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
