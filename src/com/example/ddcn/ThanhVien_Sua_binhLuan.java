package com.example.ddcn;

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
import android.widget.Toast;

public class ThanhVien_Sua_binhLuan extends Activity implements OnClickListener {

	db_connection db = new db_connection(this);
	EditText NoiDung;
	String mand, mabv;
	Button btn_update;
	AlertDialog.Builder adb = null;
	String maBL;
	item_binhluan binhLuan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanh_vien__sua_binh_luan);

        this.setTitle("SỬA BÌNH LUẬN");
		
		Intent intent = getIntent();
		maBL = intent.getStringExtra("MaBL");
		
		if (maBL != null) {
			NoiDung = (EditText) findViewById(R.id.tv_udBL_nd);
			NoiDung.requestFocus();
			
			btn_update = (Button) findViewById(R.id.tv_udBL_btnUd);
			btn_update.setOnClickListener(this);

			binhLuan = db.getBL(maBL);
			
			if (binhLuan != null)
			{
				NoiDung.setText(binhLuan.getNoidung());
			} else {
			    Toast.makeText(this, "Không tìm thấy bình luận.", Toast.LENGTH_SHORT).show();
			}
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
						binhLuan.getMaND(), binhLuan.getMaBV());
				
				adb = new AlertDialog.Builder(ThanhVien_Sua_binhLuan.this);
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
						Intent intent = new Intent(ThanhVien_Sua_binhLuan.this, ThanhVien_ChiTietBaiViet.class);
						intent.putExtra("MaBV", binhLuan.getMaBV());
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
		getMenuInflater().inflate(R.menu.thanh_vien__sua_binh_luan, menu);
		return true;
	}

}
