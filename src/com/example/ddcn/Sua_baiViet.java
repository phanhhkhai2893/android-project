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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Sua_baiViet extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaBV, TieuDe, NoiDung, NgayTao, NgayCN, LuotXem;
	Spinner MaND, MaPL;
	String mand, mapl;
	Button btn_update;
	AlertDialog.Builder adb = null;
	String maBV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sua_bai_viet);

        this.setTitle("SỬA BÀI VIẾT");
        
		Intent intent = getIntent();
		maBV = intent.getStringExtra("MaBV");
		
		MaBV = (EditText) findViewById(R.id.ud_mabv);
		if (maBV != null) {
			MaBV.setText(maBV);
			MaBV.setEnabled(false);
		} 
		
		TieuDe = (EditText) findViewById(R.id.ud_tieude);
		TieuDe.requestFocus();
		NoiDung = (EditText) findViewById(R.id.ud_noidung);
		
		NgayTao = (EditText) findViewById(R.id.ud_ngaytaobv);
		NgayTao.setEnabled(false);
		
		NgayCN = (EditText) findViewById(R.id.ud_ngayCnBv);
		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayCN = formatter.format(currentDate);
        NgayCN.setText(ngayCN);
        NgayCN.setEnabled(false);

		LuotXem = (EditText) findViewById(R.id.ud_luotXemBv);
        
//		SPINNER MÃ NGƯỜI DÙNG
		MaND = (Spinner) findViewById(R.id.ud_spin_mand);
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

//		SPINNER MÃ THẺ PHÂN LOẠI
		MaPL = (Spinner) findViewById(R.id.ud_spin_mapl);
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
	            
	            String tenPL = selectedThePL.getTenpl();
	            String maThePL = selectedThePL.getMaThePL();
	            mapl = maThePL;
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parent) {
	        	
	        }
	    });
		
		btn_update = (Button) findViewById(R.id.ud_btn_suabv);
		btn_update.setOnClickListener(this);
		
		item_baiviet baiViet = db.getBV(maBV);

		if (baiViet != null)
		{
			TieuDe.setText(baiViet.getTieude());
			NoiDung.setText(baiViet.getNoidung());
			NgayTao.setText(baiViet.getNgaytao());
			LuotXem.setText(String.valueOf(baiViet.getLuotxem()));
			
			if (dsnd.indexOf(baiViet.getMaND()) >= 0)
			{
				MaND.setSelection(dsnd.indexOf(baiViet.getMaND()));
			}
			
			String maTheLoaiCanTim = baiViet.getMaThePL();
		    int viTriCanChon = 0;

		    if (dspl != null && maTheLoaiCanTim != null) {
		        for (int i = 0; i < dspl.size(); i++) {
		            if (dspl.get(i).getMaThePL().equals(maTheLoaiCanTim)) {
		                viTriCanChon = i;
		                break; // Tìm thấy thì dừng
		            }
		        }
		    }
		    
		    MaPL.setSelection(viTriCanChon);
		} else {
		    Toast.makeText(this, "Không tìm thấy bài viết.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_update)
		{
			try
			{
				db.update_bai_viet(MaBV.getText().toString(), TieuDe.getText().toString(), 
						NoiDung.getText().toString(), NgayCN.getText().toString(), 
						Integer.parseInt(LuotXem.getText().toString()), mand, mapl);
				
				adb = new AlertDialog.Builder(Sua_baiViet.this);
				adb.setTitle("Thông báo");
				adb.setMessage("Cập nhật bài viết thành công! Bạn muốn tiếp tục sửa bài viết?");
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
						Intent intent = new Intent(Sua_baiViet.this, Xem_baiViet.class);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.toHome) {
			Intent intent = new Intent(Sua_baiViet.this, QL_baiViet.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sua_bai_viet, menu);
		return true;
	}

}
