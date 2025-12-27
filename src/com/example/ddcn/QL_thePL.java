package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QL_thePL extends Activity implements OnClickListener {

	Button btnThem, btnXem;
	db_connection db = new db_connection(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ql_the_pl);

        this.setTitle("QUẢN LÝ THẺ PHÂN LOẠI");
        
        btnThem = (Button) findViewById(R.id.qlpl_btn_them);
        btnThem.setOnClickListener(this);
        
        btnXem = (Button) findViewById(R.id.qlpl_btn_xem);
        btnXem.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if (arg0 == btnThem)
		{
			Intent intent = new Intent(QL_thePL.this, Them_thePL.class);
			startActivity(intent);
		}
		else if (arg0 == btnXem)
		{
			Intent intent = new Intent(QL_thePL.this, Xem_thePL.class);
			startActivity(intent);
		}
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.toHome) {
			Intent intent = new Intent(QL_thePL.this, MainActivity.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ql_the_pl, menu);
		return true;
	}

}
