package com.example.ddcn;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_ThanhVien extends Activity implements OnClickListener {

	ArrayList<item_baiviet> bv_List = new ArrayList<item_baiviet>();
	TV_Ada_baiViet ada = null;
	db_connection db = new db_connection(this);
	String data;
	AlertDialog.Builder adb = null;
	int pos;
	Session_mand S_mand = Session_mand.getInstance();

	ListView simpleList;
	SearchView search;
	Button tim;
	LinearLayout viewHome, viewVietBai, viewQlBai, viewTk;
	Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main__thanh_vien);

        this.setTitle("TRANG CHỦ");
        
        search = (SearchView) findViewById(R.id.searchView1);
        
        tim = (Button) findViewById(R.id.btn_tim);
        tim.setOnClickListener(this);
        
        viewHome = (LinearLayout) findViewById(R.id.view_home);
        viewHome.setOnClickListener(this);
        
        viewVietBai = (LinearLayout) findViewById(R.id.view_vietBai);
        viewVietBai.setOnClickListener(this);
        
        viewQlBai = (LinearLayout) findViewById(R.id.view_qlBai);
        viewQlBai.setOnClickListener(this);
        
        viewTk = (LinearLayout) findViewById(R.id.view_taikhoan);
        viewTk.setOnClickListener(this);
        
		if (S_mand.getMaND() != null) {
			item_nguoidung user = db.getUser(S_mand.getMaND());
		}

		simpleList = (ListView) findViewById(R.id.listView1);

		c = db.get_bai_viet_orderNgayTao();
		if (c == null)
		{
			Toast.makeText(getApplication(), "C rỗng!", Toast.LENGTH_LONG).show();
		}
		else
		{
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
//				
				simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
				    	item_baiviet selectedItem = (item_baiviet) parent.getItemAtPosition(position);
				        
				        String ma = selectedItem.getMaBV();
				        db.update_bai_viet(ma, selectedItem.getTieude(), selectedItem.getNoidung(), selectedItem.getNgaycapnhat(), 
				        		selectedItem.getLuotxem() + 1, selectedItem.getMaND(), selectedItem.getMaThePL());
						Intent intent = new Intent(Main_ThanhVien.this, ThanhVien_ChiTietBaiViet.class);
						intent.putExtra("MaBV", ma);
						startActivity(intent);
				    }
				});
			}
			catch (Exception e)
			{
				Toast.makeText(getApplication(), "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == tim)
		{
			String tuKhoa = search.getQuery().toString();
			thucHienTimKiem(tuKhoa);
		}
		if (arg0 == viewHome)
		{
			
		}
		if (arg0 == viewVietBai)
		{
			finish();
			Intent intent = new Intent(Main_ThanhVien.this, ThanhVien_vietBai.class);
			startActivity(intent);
		}
		if (arg0 == viewQlBai)
		{
			finish();
			Intent intent = new Intent(Main_ThanhVien.this, ThanhVien_qlBai.class);
			startActivity(intent);
		}
		if (arg0 == viewTk)
		{
			finish();
			Intent intent = new Intent(Main_ThanhVien.this, ThanhVien_taiKhoan.class);
			startActivity(intent);
		}
	}
    
	public void thucHienTimKiem(String tuKhoa) {
	    bv_List.clear(); 
	    
	    Cursor c;
	    if (tuKhoa.isEmpty()) {
	        c = db.get_bai_viet_orderNgayTao();
	    } else {
	        c = db.get_bai_viet_by_KQTK(tuKhoa);
	    }

	    if (c != null) {
	        if (c.moveToFirst()) {
	            do {
	                item_baiviet i = new item_baiviet(
	                    c.getString(0), 
	                    c.getString(1), 
	                    c.getString(2), 
	                    c.getString(3), 
	                    c.getString(4), 
	                    c.getInt(5), 
	                    c.getString(6), 
	                    c.getString(7)
	                );
	                bv_List.add(i);
	                
	            } while (c.moveToNext());
	        }
	        c.close();
	    }
	    
	    if (ada != null) {
	        ada.notifyDataSetChanged();
	    } else {
	        ada = new TV_Ada_baiViet(this, R.layout.tv_listview_baiviet_items, bv_List);
	        simpleList.setAdapter(ada);
	    }
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.dangxuat) {
			S_mand.setMaND(null);
			Intent intent = new Intent(Main_ThanhVien.this, MainActivity.class);
			finish();
			startActivity(intent);
	    }
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main__thanh_vien, menu);
		return true;
	}

}
