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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Them_baiViet extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaBV, TieuDe, NoiDung, NgayTao;
	Spinner MaND, MaPL;
	String mand, mapl;
	Button btn_insert;
	AlertDialog.Builder adb = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_them_bai_viet);

        this.setTitle("THÊM BÀI VIẾT");
        
		MaBV = (EditText) findViewById(R.id.ins_mabv);
		String maBvNew = taoMaBVMoi(db.getMaBaiVietLonNhat());
		MaBV.setText(maBvNew);
		MaBV.setEnabled(false);
		
		TieuDe = (EditText) findViewById(R.id.ins_tieude);
		TieuDe.requestFocus();
		NoiDung = (EditText) findViewById(R.id.ins_noidung);
		
		NgayTao = (EditText) findViewById(R.id.ins_ngaytao);
		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        NgayTao.setText(ngayTao);
        NgayTao.setEnabled(false);
        
//		SPINNER MÃ NGƯỜI DÙNG
		MaND = (Spinner) findViewById(R.id.ins_mand);
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
		MaPL = (Spinner) findViewById(R.id.ins_mapl);
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
		
		btn_insert = (Button) findViewById(R.id.ins_btn);
		btn_insert.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_insert)
		{
//			String sql = MaND.getText() + "\n" + Username.getText() + "\n" + Password.getText()+ "\n" + Email.getText()+ "\n" + Hoten.getText()+ "\n" + Avarta.getText()+ "\n" + Ngaytao.getText();
			db.insert_bai_viet(MaBV.getText().toString(), TieuDe.getText().toString(), 
					NoiDung.getText().toString(), NgayTao.getText().toString(), 
					NgayTao.getText().toString(), 0, mand, mapl);
			
			adb = new AlertDialog.Builder(Them_baiViet.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Thêm bài viết thành công! Bạn muốn tiếp tục thêm bài viết?");
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
					Intent intent = new Intent(Them_baiViet.this, QL_baiViet.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
	}
	
	public void reset()
	{
		MaBV.setText("");
		TieuDe.setText("");
		NoiDung.setText("");
		NgayTao.setText("");
		
		TieuDe.requestFocus();
		
		String maBvNew = taoMaBVMoi(db.getMaBaiVietLonNhat());
		MaBV.setText(maBvNew);
		MaBV.setEnabled(false);

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayTao = formatter.format(currentDate);
        
        NgayTao.setText(ngayTao);
        NgayTao.setEnabled(false);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_insBV_toHome) {
			Intent intent = new Intent(Them_baiViet.this, QL_baiViet.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.them_bai_viet, menu);
		return true;
	}

}
