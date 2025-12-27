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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class ThanhVien_vietBai extends Activity implements OnClickListener {

	EditText TieuDe, NoiDung;
	Spinner MaPL;
	String mapl;
	Button btn_insert;
	LinearLayout viewHome, viewVietBai, viewQlBai, viewTk;
	AlertDialog.Builder adb = null;

	db_connection db = new db_connection(this);
	Session_mand S_mand = Session_mand.getInstance();
	String maBvNew, ngayTao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanh_vien_viet_bai);

        this.setTitle("Tạo Bài Viết");
        
		maBvNew = taoMaBVMoi(db.getMaBaiVietLonNhat());
		
		TieuDe = (EditText) findViewById(R.id.tv_vb_tieuDe);
		TieuDe.requestFocus();
		
		NoiDung = (EditText) findViewById(R.id.tv_vb_noiDung);
		
		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        ngayTao = formatter.format(currentDate);

//		SPINNER MÃ THẺ PHÂN LOẠI
		MaPL = (Spinner) findViewById(R.id.tv_vb_danhMuc);
		ArrayList<item_thepl> dspl = db.get_DS_MaPL();
		ArrayAdapter<item_thepl> adaPL = new ArrayAdapter<item_thepl>(
		        this, 
		        android.R.layout.simple_spinner_item,
		        dspl
		    );
		adaPL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		MaPL.setAdapter(adaPL);
		
		MaPL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	        	item_thepl selectedThePL = (item_thepl) parent.getItemAtPosition(position);
	            
	        	mapl = selectedThePL.getMaThePL();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	
	        }
	    });
        
		btn_insert = (Button) findViewById(R.id.tv_vb_btnDang);
		btn_insert.setOnClickListener(this);
        
        viewHome = (LinearLayout) findViewById(R.id.view_home);
        viewHome.setOnClickListener(this);
        
        viewVietBai = (LinearLayout) findViewById(R.id.view_vietBai);
        viewVietBai.setOnClickListener(this);
        
        viewQlBai = (LinearLayout) findViewById(R.id.view_qlBai);
        viewQlBai.setOnClickListener(this);
        
        viewTk = (LinearLayout) findViewById(R.id.view_taikhoan);
        viewTk.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_insert)
		{
			db.insert_bai_viet(maBvNew, TieuDe.getText().toString(), 
					NoiDung.getText().toString(), ngayTao, 
					ngayTao, 0, S_mand.getMaND(), mapl);
			adb = new AlertDialog.Builder(ThanhVien_vietBai.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Tạo bài viết thành công! Bạn muốn tiếp tục tạo bài viết?");
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
					finish();
					Intent intent = new Intent(ThanhVien_vietBai.this, Main_ThanhVien.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
		if (arg0 == viewHome)
		{
			finish();
			Intent intent = new Intent(ThanhVien_vietBai.this, Main_ThanhVien.class);
			startActivity(intent);
		}
		if (arg0 == viewVietBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_vietBai.this, ThanhVien_vietBai.class);
			startActivity(intent);
		}
		if (arg0 == viewQlBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_vietBai.this, ThanhVien_qlBai.class);
			startActivity(intent);
		}
		if (arg0 == viewTk)
		{
			finish();
			Intent intent = new Intent(ThanhVien_vietBai.this, ThanhVien_taiKhoan.class);
			startActivity(intent);
		}
	}
	
	public void reset()
	{
		TieuDe.setText("");
		NoiDung.setText("");
		
		TieuDe.requestFocus();
		
		String maBvNew = taoMaBVMoi(db.getMaBaiVietLonNhat());

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
	}
	
	public String taoMaBVMoi(String maLonNhatHienTai) {
        String newMa = "";

        if (maLonNhatHienTai != null && !maLonNhatHienTai.isEmpty()) {
            try {
                String soHienTaiStr = maLonNhatHienTai.substring(2);
                
                int soHienTai = Integer.parseInt(soHienTaiStr);
                
                int soMoi = soHienTai + 1;
                String soMoiFormatted = String.format("%d", soMoi);
                
                newMa = "BV" + soMoiFormatted;

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng mã bv: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Lỗi độ dài mã bv: " + e.getMessage());
            }
        }
        
        return newMa;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanh_vien_viet_bai, menu);
		return true;
	}

}
