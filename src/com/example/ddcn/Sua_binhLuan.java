package com.example.ddcn;

import java.util.ArrayList;

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
import android.widget.Spinner;
import android.widget.Toast;

public class Sua_binhLuan extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaBL, NoiDung, NgayTao;
	Spinner MaND, MaBV;
	String mand, mabv;
	Button btn_update;
	AlertDialog.Builder adb = null;
	String maBL;
	item_binhluan binhLuan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sua_binh_luan);

        this.setTitle("SỬA BÌNH LUẬN");
		
		Intent intent = getIntent();
		maBL = intent.getStringExtra("MaBL");
		
		MaBL = (EditText) findViewById(R.id.ud_bl_ma);
		if (maBL != null) {
			MaBL.setText(maBL);
			MaBL.setEnabled(false);
		} 
		
		NoiDung = (EditText) findViewById(R.id.ud_bl_nd);
		NoiDung.requestFocus();
		
		NgayTao = (EditText) findViewById(R.id.ud_bl_ngaytao);
		NgayTao.setEnabled(false);
        
//		SPINNER MÃ NGƯỜI DÙNG
		MaND = (Spinner) findViewById(R.id.ud_bl_mand);
		ArrayList<String> dsnd = db.get_DS_MaND();
		ArrayAdapter<String> adaND = new ArrayAdapter<String>(
		        this, 
		        android.R.layout.simple_spinner_item,
		        dsnd
		    );
		adaND.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		MaND.setAdapter(adaND);
		
		MaND.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	            mand = parent.getItemAtPosition(position).toString();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	
	        }
	    });

//		SPINNER MÃ THẺ BÀI VIẾT
		MaBV = (Spinner) findViewById(R.id.ud_bl_mabv);
		ArrayList<String> dsbv = db.get_DS_MaBV();
		ArrayAdapter<String> adaBV = new ArrayAdapter<String>(
		        this, 
		        android.R.layout.simple_spinner_item,
		        dsbv
		    );
		adaBV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		MaBV.setAdapter(adaBV);
		
		MaBV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	            mabv = parent.getItemAtPosition(position).toString();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	
	        }
	    });
		
		btn_update = (Button) findViewById(R.id.ud_bl_btn);
		btn_update.setOnClickListener(this);
		
		binhLuan = db.getBL(maBL);
		
		if (binhLuan != null)
		{
			NoiDung.setText(binhLuan.getNoidung());
			NgayTao.setText(binhLuan.getNgaytao());
			
			if (dsnd.indexOf(binhLuan.getMaND()) >= 0)
			{
				MaND.setSelection(dsnd.indexOf(binhLuan.getMaND()));
			}
			
			if (dsbv.indexOf(binhLuan.getMaBV()) >= 0)
			{
				MaBV.setSelection(dsbv.indexOf(binhLuan.getMaBV()));
			}
		} else {
		    Toast.makeText(this, "Không tìm thấy bình luận.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_update)
		{
			try
			{
				db.update_binh_luan(maBL, NoiDung.getText().toString(), 
						mand, mabv);
				
				adb = new AlertDialog.Builder(Sua_binhLuan.this);
				adb.setTitle("Thông báo");
				adb.setMessage("Cập nhật bình luận thành công! Bạn muốn tiếp tục sửa bình luận?");
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
						Intent intent = new Intent(Sua_binhLuan.this, Xem_binhLuan.class);
						startActivity(intent);
					}
				});
				
				AlertDialog ad = adb.create();
				ad.show();
			}
			catch (Exception ex)
			{
			    Toast.makeText(this, "Lỗi Update: " + ex.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sua_binh_luan, menu);
		return true;
	}

}
