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

public class QL_nguoiDung extends Activity implements OnClickListener {

	Button btnThemND, btnXemND;
	db_connection db = new db_connection(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ql_nguoi_dung);

        this.setTitle("QUẢN LÝ NGƯỜI DÙNG");
		
        btnThemND = (Button) findViewById(R.id.qlND_themND);
        btnThemND.setOnClickListener(this);
        
        btnXemND = (Button) findViewById(R.id.qlND_xemND);
        btnXemND.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if (arg0 == btnThemND)
		{
			Intent intent = new Intent(QL_nguoiDung.this, Them_nguoidung.class);
			startActivity(intent);
		}
		else if (arg0 == btnXemND)
		{
			Intent intent = new Intent(QL_nguoiDung.this, Xem_nguoidung.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ql_nguoi_dung, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_qlnd_vehome) {
			Intent intent = new Intent(QL_nguoiDung.this, MainActivity.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

}
