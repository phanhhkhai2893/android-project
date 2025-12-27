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

public class QL_baiViet extends Activity implements OnClickListener {

	TextView hello;
	Button btnThem, btnXem;
	db_connection db = new db_connection(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ql_bai_viet);

        this.setTitle("QUẢN LÝ BÀI VIẾT");
		
        btnThem = (Button) findViewById(R.id.qlbv_insert);
        btnThem.setOnClickListener(this);
        
        btnXem = (Button) findViewById(R.id.qlbv_read);
        btnXem.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if (arg0 == btnThem)
		{
			Intent intent = new Intent(QL_baiViet.this, Them_baiViet.class);
			startActivity(intent);
		}
		else if (arg0 == btnXem)
		{
			Intent intent = new Intent(QL_baiViet.this, Xem_baiViet.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_qlBV_toHome) {
			Intent intent = new Intent(QL_baiViet.this, MainActivity.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ql_bai_viet, menu);
		return true;
	}

}
