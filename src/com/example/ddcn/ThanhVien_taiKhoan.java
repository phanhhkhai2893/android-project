package com.example.ddcn;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ThanhVien_taiKhoan extends Activity implements OnClickListener {

	LinearLayout viewHome, viewVietBai, viewQlBai, viewTk;
	EditText Username, Password, Email, Hoten, Ngaytao;
	ImageView avarta;
	Button dx, btn_update;
	AlertDialog.Builder adb = null;

	Spinner spinnerAvatar;
	ArrayList<Avatar> arrayAvatar;
	AvatarAdapter adapter;
	String tenFileDeLuuVaoDB = "";
	
	item_nguoidung user;

	db_connection db = new db_connection(this);
	Session_mand S_mand = Session_mand.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanh_vien_tai_khoan);
        
		Hoten = (EditText) findViewById(R.id.tv_vb_tieuDe);
		Email = (EditText) findViewById(R.id.tv_tk_email);
		Username = (EditText) findViewById(R.id.tv_tk_usn);
		Password = (EditText) findViewById(R.id.tv_tk_pass);
		Ngaytao = (EditText) findViewById(R.id.tv_tk_ngaytao);

		btn_update = (Button) findViewById(R.id.tv_tk_btnCapnhat);
		btn_update.setOnClickListener(this);
		
		user = db.getUser(S_mand.getMaND());
		if (user != null)
		{
	        this.setTitle(user.getHoten());
		    Hoten.setText(user.getHoten());
		    Email.setText(user.getEmail());
			Username.setText(user.getUsername());
		    Password.setText(user.getPassword());
		    Ngaytao.setText(user.getNgaytao());
		    Ngaytao.setEnabled(false);
		}
		
        viewHome = (LinearLayout) findViewById(R.id.view_home);
        viewHome.setOnClickListener(this);
        
        viewVietBai = (LinearLayout) findViewById(R.id.view_vietBai);
        viewVietBai.setOnClickListener(this);
        
        viewQlBai = (LinearLayout) findViewById(R.id.view_qlBai);
        viewQlBai.setOnClickListener(this);
        
        viewTk = (LinearLayout) findViewById(R.id.view_taikhoan);
        viewTk.setOnClickListener(this);
        
        dx = (Button) findViewById(R.id.thanhvien_dangxuat);
        dx.setOnClickListener(this);

     // ... Các phần ánh xạ View khác giữ nguyên ...

        avarta = (ImageView) findViewById(R.id.imageView1);

        String fileName = user.getAvarta(); 

        if (fileName != null && !fileName.isEmpty()) {
        	
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }

            int imageResourceID = getResources().getIdentifier(
                fileName,           // Tên file ảnh (String)
                "drawable",         // Thư mục chứa ảnh
                getPackageName()    // Tên gói ứng dụng
            );

            if (imageResourceID != 0) {
                avarta.setImageResource(imageResourceID);
            } else {
                avarta.setImageResource(R.drawable.hinh_user); 
            }
        } else {
            avarta.setImageResource(R.drawable.hinh_user); 
        }
        
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
		if (arg0 == btn_update)
		{
			db.update_nguoi_dung(S_mand.getMaND().toString(), Username.getText().toString(),
					Password.getText().toString(), Email.getText().toString(),
					Hoten.getText().toString(), tenFileDeLuuVaoDB);

		    adb = new AlertDialog.Builder(ThanhVien_taiKhoan.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Cập nhật thông tin thành công!");
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

				}
			});

			updateAnh();
			AlertDialog ad = adb.create();
			ad.show();
		}
		if (arg0 == dx)
		{
			S_mand.setMaND(null);
			Intent intent = new Intent(ThanhVien_taiKhoan.this, MainActivity.class);
			finish();
			startActivity(intent);
		}
		if (arg0 == viewHome)
		{
			finish();
			Intent intent = new Intent(ThanhVien_taiKhoan.this, Main_ThanhVien.class);
			startActivity(intent);
		}
		if (arg0 == viewVietBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_taiKhoan.this, ThanhVien_vietBai.class);
			startActivity(intent);
		}
		if (arg0 == viewQlBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_taiKhoan.this, ThanhVien_qlBai.class);
			startActivity(intent);
		}
		if (arg0 == viewTk)
		{
			finish();
			Intent intent = new Intent(ThanhVien_taiKhoan.this, ThanhVien_taiKhoan.class);
			startActivity(intent);
		}
	}
	
	public void updateAnh()
	{
		String fileName = user.getAvarta(); 

		if (fileName != null && !fileName.isEmpty()) {
    	
        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }

        int imageResourceID = getResources().getIdentifier(
            fileName,           // Tên file ảnh (String)
            "drawable",         // Thư mục chứa ảnh
            getPackageName()    // Tên gói ứng dụng
        );

        if (imageResourceID != 0) {
            avarta.setImageResource(imageResourceID);
        } else {
            avarta.setImageResource(R.drawable.hinh_user); 
        }
    } else {
        avarta.setImageResource(R.drawable.hinh_user); 
    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanh_vien_tai_khoan, menu);
		return true;
	}

}
