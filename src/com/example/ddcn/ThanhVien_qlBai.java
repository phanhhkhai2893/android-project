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
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ThanhVien_qlBai extends Activity implements OnClickListener {

	ArrayList<item_baiviet> bv_List = new ArrayList<item_baiviet>();
	TV_Ada_baiViet ada = null;
	item_baiviet selectedItem;

	LinearLayout viewHome, viewVietBai, viewQlBai, viewTk;
	ListView simpleList;
	AlertDialog.Builder adb = null;

	db_connection db = new db_connection(this);
	Session_mand S_mand = Session_mand.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanh_vien_ql_bai);

        this.setTitle("Quản Lý Bài Viết");
        
		simpleList = (ListView) findViewById(R.id.listView1);
		
		try
		{
			Cursor c = db.get_bai_viet_theo_nd(S_mand.getMaND().toString());
			
			try
			{
				bv_List.clear();
				c.moveToFirst();
				while (!c.isAfterLast())
				{
					item_baiviet i = new item_baiviet(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7));
					bv_List.add(i);
					c.moveToNext();
				}
				c.close();
				
				ada = new TV_Ada_baiViet(this, R.layout.tv_listview_baiviet_items, bv_List);
				simpleList.setAdapter(ada);
				registerForContextMenu(simpleList);
//					
				simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
				    	selectedItem = (item_baiviet) arg0.getItemAtPosition(arg2);
				    	return false;
					}
				});
			}
			catch (Exception e)
			{
				Toast.makeText(getApplication(), "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception ex)
		{
			adb = new AlertDialog.Builder(ThanhVien_qlBai.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Bạn chưa có bài viết! Tạo bài viết ngay?");
			adb.setCancelable(true);
			adb.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					finish();
					Intent intent = new Intent(ThanhVien_qlBai.this, ThanhVien_vietBai.class);
					startActivity(intent);
				}
			});
			adb.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub

				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
		
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
		if (arg0 == viewHome)
		{
			finish();
			Intent intent = new Intent(ThanhVien_qlBai.this, Main_ThanhVien.class);
			startActivity(intent);
		}
		if (arg0 == viewVietBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_qlBai.this, ThanhVien_vietBai.class);
			startActivity(intent);
		}
		if (arg0 == viewQlBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_qlBai.this, ThanhVien_qlBai.class);
			startActivity(intent);
		}
		if (arg0 == viewTk)
		{
			finish();
			Intent intent = new Intent(ThanhVien_qlBai.this, ThanhVien_taiKhoan.class);
			startActivity(intent);
		}
	}
	
	public void reset()
	{
		Cursor c = db.get_bai_viet_theo_nd(S_mand.getMaND().toString());
		
		try
		{
			bv_List.clear();
			c.moveToFirst();
			while (!c.isAfterLast())
			{
				item_baiviet i = new item_baiviet(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7));
				bv_List.add(i);
				c.moveToNext();
			}
			c.close();
			
			ada = new TV_Ada_baiViet(this, R.layout.tv_listview_baiviet_items, bv_List);
			simpleList.setAdapter(ada);
				
			registerForContextMenu(simpleList);
		}
		catch (Exception e)
		{
			Toast.makeText(getApplication(), "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);

	    if (v.getId() == R.id.listView1) { 
	        
	        AdapterView.AdapterContextMenuInfo info = 
	            (AdapterView.AdapterContextMenuInfo) menuInfo;

	        menu.add(Menu.NONE, 1, 1, "Sửa bài viết");
	        menu.add(Menu.NONE, 2, 2, "Xóa bài viết");
	    }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterView.AdapterContextMenuInfo info = 
	        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

	    int position = info.position; 
	    
	    int itemId = item.getItemId();

	    switch (itemId) {
	        case 1:
		        String ma = selectedItem.getMaBV();
				Intent intent = new Intent(ThanhVien_qlBai.this, ThanhVien_Sua_baiViet.class);
				intent.putExtra("MaBV", ma);
				startActivity(intent);
	            return true;
	            
	        case 2:
	        	adb = new AlertDialog.Builder(ThanhVien_qlBai.this);
				adb.setTitle("Xóa bài viết");
				adb.setMessage("Xác nhận xóa bài viết này?");
				adb.setCancelable(true);
				adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						db.delete_bai_viet(selectedItem.getMaBV().toString());
						Toast.makeText(getApplication(), "Xóa bài viết thành công!", Toast.LENGTH_LONG).show();
						reset();
//						Toast.makeText(getApplication(), selectedItem.getMaBL().toString(), Toast.LENGTH_LONG).show();
					}
				});
				adb.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				
				AlertDialog ad = adb.create();
				ad.show();
	            return true;
	            
	        default:
	            return super.onContextItemSelected(item);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thanh_vien_ql_bai, menu);
		return true;
	}

}
