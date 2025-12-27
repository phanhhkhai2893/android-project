package com.example.ddcn;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Them_nguoidung extends Activity implements OnClickListener {

	db_connection kn = new db_connection(this);
	EditText MaND, Username, Password, Email, Hoten, Avarta, Ngaytao;
	Button btn_insert;
	AlertDialog.Builder adb = null;
	
	Spinner spinnerAvatar;
	ArrayList<Avatar> arrayAvatar;
	AvatarAdapter adapter;
	String tenFileDeLuuVaoDB = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_them_nguoidung);

        this.setTitle("THÊM NGƯỜI DÙNG");
        
		MaND = (EditText) findViewById(R.id.txt_mand);
		Username = (EditText) findViewById(R.id.txt_usn);
		Password = (EditText) findViewById(R.id.txt_pass);
		Email = (EditText) findViewById(R.id.txt_email);
		Hoten = (EditText) findViewById(R.id.txt_hoten);
		Ngaytao = (EditText) findViewById(R.id.txt_ngaytao);
		btn_insert = (Button) findViewById(R.id.btn_insert);
        
		btn_insert = (Button) findViewById(R.id.btn_insert);
		btn_insert.setOnClickListener(this);
		
		String mandNew = taoMaNguoiDungMoi(kn.getMaNguoiDungLonNhat());
		MaND.setText(mandNew);
		MaND.setEnabled(false);

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        Ngaytao.setText(ngayTao);
        Ngaytao.setEnabled(false);
//		Toast.makeText(getApplication(), "Mã ND max: " + kn.getMaNguoiDungLonNhat() + "\nMã hiện tại: " + mandNew, Toast.LENGTH_LONG).show();
        
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
		if (arg0 == btn_insert)
		{
//			String sql = MaND.getText() + "\n" + Username.getText() + "\n" + Password.getText()+ "\n" + Email.getText()+ "\n" + Hoten.getText()+ "\n" + Avarta.getText()+ "\n" + Ngaytao.getText();
			kn.insert_nguoi_dung(MaND.getText().toString(), Username.getText().toString(), 
					Password.getText().toString(), Email.getText().toString(), 
					Hoten.getText().toString(), tenFileDeLuuVaoDB, Ngaytao.getText().toString());
			
			adb = new AlertDialog.Builder(Them_nguoidung.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Thêm người dùng thành công! Bạn muốn tiếp tục thêm người dùng?");
			adb.setCancelable(true);
			adb.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					reset();
				}
			});
			adb.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Them_nguoidung.this, QL_nguoiDung.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
	}
	
	public void reset()
	{
		MaND.setText("");
		Username.setText("");
		Password.setText("");
		Email.setText("");
		Hoten.setText("");
		Avarta.setText("");
		Ngaytao.setText("");
		
		Username.requestFocus();
		
		String mandNew = taoMaNguoiDungMoi(kn.getMaNguoiDungLonNhat());
		MaND.setText(mandNew);
		MaND.setEnabled(false);

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        Ngaytao.setText(ngayTao);
        Ngaytao.setEnabled(false);
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
		getMenuInflater().inflate(R.menu.them_nguoidung, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_themnd_vehome) {
			Intent intent = new Intent(Them_nguoidung.this, QL_nguoiDung.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

}
