package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sua_nguoidung extends Activity implements OnClickListener {

	db_connection kn = new db_connection(this);
	EditText MaND, Username, Password, Email, Hoten, Avarta, Ngaytao;
	Button btn_update;
	AlertDialog.Builder adb = null;
	String maNguoiDung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sua_nguoidung);

        this.setTitle("SỬA NGƯỜI DÙNG");
        
		MaND = (EditText) findViewById(R.id.ud_mand);
		Username = (EditText) findViewById(R.id.ud_usn);
		Password = (EditText) findViewById(R.id.ud_pass);
		Email = (EditText) findViewById(R.id.ud_email);
		Hoten = (EditText) findViewById(R.id.ud_hoten);
		Avarta = (EditText) findViewById(R.id.ud_avarta);
		Ngaytao = (EditText) findViewById(R.id.ud_ngaytao);
		btn_update = (Button) findViewById(R.id.btn_update);
		btn_update.setOnClickListener(this);
		
		Intent intent = getIntent();
		maNguoiDung = intent.getStringExtra("MaND");
	    
		if (maNguoiDung != null) {
			MaND.setText(maNguoiDung);
			MaND.setEnabled(false);
		}

    	Ngaytao.setEnabled(false);

		item_nguoidung user = kn.getUser(maNguoiDung); 

		if (user != null) {
		    String username = user.getUsername();
		    String pass = user.getPassword();
		    String email = user.getEmail();
		    String hoten = user.getHoten();
		    String avarta = user.getAvarta();
		    String ngaytao = user.getNgaytao();
		    
		    Username.setText(username);
		    Password.setText(pass);
		    Email.setText(email);
		    Hoten.setText(hoten);
		    Avarta.setText(avarta);
		    Ngaytao.setText(ngaytao);
		} else {
		    Toast.makeText(this, "Không tìm thấy người dùng.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_update)
		{
			kn.update_nguoi_dung(MaND.getText().toString(), Username.getText().toString(), Password.getText().toString(), Email.getText().toString(), Hoten.getText().toString(), Avarta.getText().toString());

		    adb = new AlertDialog.Builder(Sua_nguoidung.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Cập nhật người dùng thành công! Bạn muốn tiếp tục sửa người dùng?");
			adb.setCancelable(true);
			adb.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub

				}
			});
			adb.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Sua_nguoidung.this, Xem_nguoidung.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sua_nguoidung, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_suand_vehome) {
			Intent intent = new Intent(Sua_nguoidung.this, QL_nguoiDung.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

}
