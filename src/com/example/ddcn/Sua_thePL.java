package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Sua_thePL extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText MaPL, TenPL, MoTa;
	Button btn_update;
	AlertDialog.Builder adb = null;
	String ma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sua_the_pl);

        this.setTitle("SỬA THẺ PHÂN LOẠI");
        
		Intent intent = getIntent();
		ma = intent.getStringExtra("MaThePL");
		
		MaPL = (EditText) findViewById(R.id.ud_thepl_mapl);
		if (ma != null) {
			MaPL.setText(ma);
			MaPL.setEnabled(false);
		} 

		TenPL = (EditText) findViewById(R.id.ud_thepl_ten);
		MoTa = (EditText) findViewById(R.id.ud_thepl_mota);
		
		btn_update = (Button) findViewById(R.id.ud_thepl_btn);
		btn_update.setOnClickListener(this);

		item_thepl item_pl = db.getPL(ma);
		
		if (item_pl != null)
		{
			MaPL.setText(item_pl.getMaThePL());
			TenPL.setText(item_pl.getTenpl());
			MoTa.setText(item_pl.getMota());
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
				db.update_the_phan_loai(MaPL.getText().toString(), TenPL.getText().toString(),
						MoTa.getText().toString());
				
				adb = new AlertDialog.Builder(Sua_thePL.this);
				adb.setTitle("Thông báo");
				adb.setMessage("Cập nhật thẻ phân loại thành công! Bạn muốn tiếp tục sửa thẻ phân loại?");
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
						Intent intent = new Intent(Sua_thePL.this, Xem_thePL.class);
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
			Intent intent = new Intent(Sua_thePL.this, QL_thePL.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sua_the_pl, menu);
		return true;
	}

}
