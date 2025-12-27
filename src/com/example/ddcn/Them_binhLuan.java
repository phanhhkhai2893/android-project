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
import android.widget.ListView;
import android.widget.Spinner;

public class Them_binhLuan extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaBL, NoiDung, NgayTao;
	Spinner MaND, MaBV;
	String mand, mabv;
	Button btn_insert;
	AlertDialog.Builder adb = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_them_binh_luan);

        this.setTitle("THÊM BÌNH LUẬN");
        
		MaBL = (EditText) findViewById(R.id.ins_bl_ma);
		String maBvNew = taoMaMoi(db.getMaBLLonNhat());
		MaBL.setText(maBvNew);
		MaBL.setEnabled(false);
		
		NoiDung = (EditText) findViewById(R.id.ins_bl_nd);
		NoiDung.requestFocus();
		
		NgayTao = (EditText) findViewById(R.id.ins_bl_ngaytao);
		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        NgayTao.setText(ngayTao);
        NgayTao.setEnabled(false);
        
//		SPINNER MÃ NGƯỜI DÙNG
		MaND = (Spinner) findViewById(R.id.ins_bl_mand);
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
		MaBV = (Spinner) findViewById(R.id.ins_bl_mabv);
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
		
		btn_insert = (Button) findViewById(R.id.ins_bl_btn);
		btn_insert.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_insert)
		{
			db.insert_binh_luan(MaBL.getText().toString(), NoiDung.getText().toString(),
					NgayTao.getText().toString(), mand, mabv);
			
			adb = new AlertDialog.Builder(Them_binhLuan.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Thêm bình luận thành công! Bạn muốn tiếp tục thêm bình luận?");
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
					Intent intent = new Intent(Them_binhLuan.this, QL_binhLuan.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
	}
	
	public void reset()
	{
		NoiDung.setText("");
		NoiDung.requestFocus();
		
		String maNew = taoMaMoi(db.getMaBLLonNhat());
		MaBL.setText(maNew);
		MaBL.setEnabled(false);

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        NgayTao.setText(ngayTao);
        NgayTao.setEnabled(false);
	}
	
	public String taoMaMoi(String maLonNhatHienTai) {
        String newMa = "";

        if (maLonNhatHienTai != null && !maLonNhatHienTai.isEmpty()) {
            try {
                String soHienTaiStr = maLonNhatHienTai.substring(2);
                
                int soHienTai = Integer.parseInt(soHienTaiStr);
                
                int soMoi = soHienTai + 1;
                String soMoiFormatted = String.format("%d", soMoi);
                
                newMa = "BL" + soMoiFormatted;

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng mã bl: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Lỗi độ dài mã bl: " + e.getMessage());
            }
        }
        
        return newMa;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.them_binh_luan, menu);
		return true;
	}

}
