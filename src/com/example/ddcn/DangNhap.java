package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DangNhap extends Activity implements OnClickListener {

	EditText usn, pass;
	Button dn, dk;
	db_connection db = new db_connection(this);
	Session_mand S_mand = Session_mand.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dang_nhap);
		
        this.setTitle("ĐĂNG NHẬP");
		
		usn = (EditText) findViewById(R.id.dn_usn);
		pass = (EditText) findViewById(R.id.dn_pass);
		pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
		dn = (Button) findViewById(R.id.btn_dangnhap);
		dn.setOnClickListener(this);
		
		dk = (Button) findViewById(R.id.btn_dk);
		dk.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == dn)
		{
			String username = usn.getText().toString();
			String password = pass.getText().toString();
			
			try
			{
				item_nguoidung user = db.getUserByUsn(username); 
				
				if ((username != null && username.equals(user.getUsername().toString()))
						&& (password != null && password.equals(user.getPassword().toString())))
				{
					S_mand.setMaND(user.getMaND().toString());
					Toast.makeText(getApplication(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
					
					if (username.equals("admin"))
					{
						Intent intent = new Intent(DangNhap.this, MainActivity.class);
						startActivity(intent);
					}
					else
					{
						Intent intent = new Intent(DangNhap.this, Main_ThanhVien.class);
						startActivity(intent);
					}
				}
				else
				{
					usn.setError("Tên đăng nhập hoặc mật khẩu không đúng!");
					pass.setText("");
					usn.requestFocus();
//					Toast.makeText(getApplication(), "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
				}
			}
			catch (Exception ex)
			{
				usn.setError("Người dùng không tồn tại!");
				pass.setText("");
				usn.requestFocus();
			}
		}
		if (arg0 == dk)
		{
			Intent intent = new Intent(DangNhap.this, DangKy.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dang_nhap, menu);
		return true;
	}

}
