package com.example.ddcn;

import java.text.SimpleDateFormat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Them_thePL extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaThePL, TenPL, MoTa;
	Button btn_insert;
	AlertDialog.Builder adb = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_them_the_pl);

        this.setTitle("THÊM THẺ PHÂN LOẠI");
        
		MaThePL = (EditText) findViewById(R.id.ins_thepl_ma);
		String maNew = taoMaMoi(db.getMaPLLonNhat());
		MaThePL.setText(maNew);
		MaThePL.setEnabled(false);
		
		TenPL = (EditText) findViewById(R.id.ins_thepl_ten);
		TenPL.requestFocus();
		
		MoTa = (EditText) findViewById(R.id.ins_thepl_mota);

		btn_insert = (Button) findViewById(R.id.ins_thepl_btn);
		btn_insert.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btn_insert)
		{
			db.insert_the_phan_loai(MaThePL.getText().toString(), TenPL.getText().toString(),
					MoTa.getText().toString());
			
			adb = new AlertDialog.Builder(Them_thePL.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Thêm thẻ phân loại thành công! Bạn muốn tiếp tục thêm thẻ phân loại?");
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
					Intent intent = new Intent(Them_thePL.this, QL_thePL.class);
					startActivity(intent);
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
	}
	
	public void reset()
	{
		MaThePL.setText("");
		TenPL.setText("");
		MoTa.setText("");
		
		TenPL.requestFocus();
		
		String maNew = taoMaMoi(db.getMaBaiVietLonNhat());
		MaThePL.setText(maNew);
		MaThePL.setEnabled(false);
	}
	
	public String taoMaMoi(String maLonNhatHienTai) {
        String newMa = "";

        if (maLonNhatHienTai != null && !maLonNhatHienTai.isEmpty()) {
            try {
                String soHienTaiStr = maLonNhatHienTai.substring(2);
                
                int soHienTai = Integer.parseInt(soHienTaiStr);
                
                int soMoi = soHienTai + 1;
                String soMoiFormatted = String.format("%d", soMoi);
                
                newMa = "PL" + soMoiFormatted;

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng mã pl: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Lỗi độ dài mã pl: " + e.getMessage());
            }
        }
        
        return newMa;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.them_the_pl, menu);
		return true;
	}

}
