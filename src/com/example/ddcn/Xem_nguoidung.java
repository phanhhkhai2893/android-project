package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Xem_nguoidung extends Activity {

	ListView simpleList;
	ArrayList<item_nguoidung> nd_List = new ArrayList<item_nguoidung>();
	Ada_nguoidung ada = null;
	db_connection db = new db_connection(this);
	String data;
	AlertDialog.Builder adb = null;
	int pos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xem_nguoidung);

        this.setTitle("DANH SÁCH NGƯỜI DÙNG");
        

		simpleList = (ListView) findViewById(R.id.listView1);

		Cursor c = db.get_nguoi_dung();
		if (c != null) {
			nd_List.clear();
		    c.moveToFirst();
			while (!c.isAfterLast())
			{
				item_nguoidung i = new item_nguoidung(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
				nd_List.add(i);
				c.moveToNext();
			}
			c.close();
			
			ada = new Ada_nguoidung(this, R.layout.listview_nguoidung_items, nd_List);
			simpleList.setAdapter(ada);
			
			simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			    @Override
			    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
			        item_nguoidung selectedItem = (item_nguoidung) parent.getItemAtPosition(position);
			        
			        String maNguoiDung = selectedItem.getMaND();
					Intent intent = new Intent(Xem_nguoidung.this, Sua_nguoidung.class);
					intent.putExtra("MaND", maNguoiDung);
					startActivity(intent);
			    }
			});
			
			simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					pos = arg2;
					adb = new AlertDialog.Builder(Xem_nguoidung.this);
					adb.setTitle("Thông tin người dùng");
					String data = db.load_nguoi_dung(nd_List.get(pos).getMaND());
					adb.setMessage(data);
					adb.setCancelable(true);
					adb.setPositiveButton("Xóa người dùng", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							adb = new AlertDialog.Builder(Xem_nguoidung.this);
							adb.setTitle("Xóa người dùng");
							adb.setMessage("Xác nhận xóa người dùng này?");
							adb.setCancelable(true);
							adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									db.delete_nguoi_dung(nd_List.get(pos).getMaND());
									reload();
									Toast.makeText(getApplication(), "Xóa người dùng thành công!", Toast.LENGTH_LONG).show();
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
					});
					adb.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							
						}
					});
					
					AlertDialog ad = adb.create();
					ad.show();
					return true;
				}
			});
		}
	}
	
	public void reload()
	{
		nd_List.clear(); 

	    Cursor c = db.get_nguoi_dung();
	    if (c != null && c.getCount() > 0) {
	        c.moveToFirst();
	        while (!c.isAfterLast()) {
	            item_nguoidung i = new item_nguoidung(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
	            nd_List.add(i);
	            c.moveToNext();
	        }
	        c.close();
	    }
	    
	    if (ada != null) {
	        ada.notifyDataSetChanged();
	    } else {
	        ada = new Ada_nguoidung(this, R.layout.listview_nguoidung_items, nd_List);
	        simpleList.setAdapter(ada);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xem_nguoidung, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_xemnd_vehome) {
			Intent intent = new Intent(Xem_nguoidung.this, QL_nguoiDung.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

}
