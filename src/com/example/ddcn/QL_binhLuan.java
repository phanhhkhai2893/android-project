package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QL_binhLuan extends Activity implements OnClickListener {

	Button btnThem, btnXem;
	db_connection db = new db_connection(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ql_binh_luan);

        this.setTitle("QUẢN LÝ BÌNH LUẬN");
		
        btnThem = (Button) findViewById(R.id.btn_them);
        btnThem.setOnClickListener(this);
        
        btnXem = (Button) findViewById(R.id.btn_xem);
        btnXem.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if (arg0 == btnThem)
		{
			Intent intent = new Intent(QL_binhLuan.this, Them_binhLuan.class);
			startActivity(intent);
		}
		else if (arg0 == btnXem)
		{
			Intent intent = new Intent(QL_binhLuan.this, Xem_binhLuan.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ql_binh_luan, menu);
		return true;
	}

}
