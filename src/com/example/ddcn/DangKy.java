package com.example.ddcn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DangKy extends Activity implements OnClickListener {

	EditText usn, pass, xnMk, email, hoten;
	Button dn, dk;
	db_connection db = new db_connection(this);
	String mandNew, ngayTao;
	AlertDialog.Builder adb = null;
	
	Spinner spinnerAvatar;
	ArrayList<Avatar> arrayAvatar;
	AvatarAdapter adapter;
	String tenFileDeLuuVaoDB = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dang_ky);
		
        this.setTitle("ĐĂNG KÝ");
		
		usn = (EditText) findViewById(R.id.dk_usn);
		pass = (EditText) findViewById(R.id.dk_pass);
		xnMk = (EditText) findViewById(R.id.dk_xnPass);
		email = (EditText) findViewById(R.id.dk_email);
		hoten = (EditText) findViewById(R.id.dk_hoten);
		
		dk = (Button) findViewById(R.id.dk_btn_dk);
		dk.setOnClickListener(this);
		dn = (Button) findViewById(R.id.dk_btn_dn);
		dn.setOnClickListener(this);
		
		mandNew = taoMaNguoiDungMoi(db.getMaNguoiDungLonNhat());

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        ngayTao = formatter.format(currentDate);

        spinnerAvatar = (Spinner) findViewById(R.id.spinner1);
        arrayAvatar = new ArrayList<Avatar>();

        arrayAvatar.add(new Avatar("Mèo", "avarta_cat", R.drawable.avarta_cat));
        arrayAvatar.add(new Avatar("Cáo", "avarta_fox", R.drawable.avarta_fox));
        arrayAvatar.add(new Avatar("Ngựa", "avarta_horse", R.drawable.avarta_horse));
        arrayAvatar.add(new Avatar("Khỉ", "avarta_monkey", R.drawable.avarta_monkey));
        arrayAvatar.add(new Avatar("Cú", "avarta_owl", R.drawable.avarta_owl));
        arrayAvatar.add(new Avatar("Gấu Trúc", "avarta_panda", R.drawable.avarta_panda));
        arrayAvatar.add(new Avatar("Chim Cánh Cụt", "avarta_penguin", R.drawable.avarta_penguin));
        arrayAvatar.add(new Avatar("Gấu Bắc Cực", "avarta_polarbear", R.drawable.avarta_polarbear));
        adapter = new AvatarAdapter(this, R.layout.spinner_item_avatar, arrayAvatar);
        
        spinnerAvatar.setAdapter(adapter);

        spinnerAvatar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng Avatar tại vị trí được chọn
                Avatar selectedAvatar = arrayAvatar.get(position);
                
                // Lấy tên file ảnh ra
                tenFileDeLuuVaoDB = selectedAvatar.getTenFileAnh(); 
                
                // Kết quả tenFileDeLuuVaoDB sẽ là: "avarta_cat", "avarta_fox"...
                // Toast.makeText(getApplicationContext(), "Lưu DB: " + tenFileDeLuuVaoDB, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == dk)
		{
			if (pass.getText().toString().equals(xnMk.getText().toString()))
			{
				db.insert_nguoi_dung(mandNew, usn.getText().toString(), pass.getText().toString(), 
						email.getText().toString(), hoten.getText().toString(), 
						tenFileDeLuuVaoDB, ngayTao);
				
				AlertDialog.Builder adb = new AlertDialog.Builder(DangKy.this);
				adb.setTitle("Thông báo");
				adb.setMessage("Đăng ký tài khoản thành công! Tới trang đăng nhập?");
				adb.setCancelable(true);
				adb.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(DangKy.this, DangNhap.class);
						startActivity(intent);
					}
				});
				adb.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						reset();
					}
				});
				
				AlertDialog ad = adb.create();
				ad.show();
			}
			else
			{
		        xnMk.setError("");
		        xnMk.setText("");
		        xnMk.requestFocus();
				Toast.makeText(getApplication(), "Xác nhận mật khẩu không đúng!", Toast.LENGTH_LONG).show();
			}
		}
		if (arg0 == dn)
		{
			Intent intent = new Intent(DangKy.this, DangNhap.class);
			startActivity(intent);
		}
	}
	
	public void reset()
	{
		usn.setText("");
		pass.setText("");
		email.setText("");
		hoten.setText("");
		
		usn.requestFocus();
		
		mandNew = taoMaNguoiDungMoi(db.getMaNguoiDungLonNhat());

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
	}
	
	public String taoMaNguoiDungMoi(String maLonNhatHienTai) {
        String newMaND = "";

        if (maLonNhatHienTai != null && !maLonNhatHienTai.isEmpty()) {
            try {
                String soHienTaiStr = maLonNhatHienTai.substring(2);
                
                int soHienTai = Integer.parseInt(soHienTaiStr);
                
                int soMoi = soHienTai + 1;
                String soMoiFormatted = String.format("%d", soMoi);
                
                newMaND = "ND" + soMoiFormatted;

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng mã người dùng: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Lỗi độ dài mã người dùng: " + e.getMessage());
            }
        }
        
        return newMaND;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dang_ky, menu);
		return true;
	}

}
