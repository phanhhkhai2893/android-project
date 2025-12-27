package com.example.ddcn;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Xem_baiViet extends Activity {

	ListView simpleList;
	ArrayList<item_baiviet> bv_List = new ArrayList<item_baiviet>();
	Ada_baiviet ada = null;
	db_connection db = new db_connection(this);
	String data;
	AlertDialog.Builder adb = null;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xem_bai_viet);

        this.setTitle("DANH SÁCH BÀI VIẾT");
        

		simpleList = (ListView) findViewById(R.id.listView1);

		Cursor c = db.get_bai_viet();
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
				
				ada = new Ada_baiviet(this, R.layout.listview_baiviet_items, bv_List);
				simpleList.setAdapter(ada);
				
				simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
				    	item_baiviet selectedItem = (item_baiviet) parent.getItemAtPosition(position);
				        
				        String ma = selectedItem.getMaBV();
						Intent intent = new Intent(Xem_baiViet.this, Sua_baiViet.class);
						intent.putExtra("MaBV", ma);
						startActivity(intent);
				    }
				});
				
				simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						pos = arg2;
						adb = new AlertDialog.Builder(Xem_baiViet.this);
						adb.setTitle("Thông tin bài viết");
						String data = db.load_bai_viet(bv_List.get(pos).getMaBV());
						adb.setMessage(data);
						adb.setCancelable(true);
						adb.setPositiveButton("Xóa bài viết", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								adb = new AlertDialog.Builder(Xem_baiViet.this);
								adb.setTitle("Xóa bài viết");
								adb.setMessage("Xác nhận xóa bài viết này?");
								adb.setCancelable(true);
								adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										// TODO Auto-generated method stub
										db.delete_bai_viet(bv_List.get(pos).getMaBV());
										reload();
										Toast.makeText(getApplication(), "Xóa bài viết thành công!", Toast.LENGTH_LONG).show();
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
						return false;
					}
				});
			}
			catch (Exception e)
			{
				Toast.makeText(getApplication(), "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void reload()
	{
		bv_List.clear(); 

	    Cursor c = db.get_bai_viet();
	    if (c != null && c.getCount() > 0) {
	        c.moveToFirst();
	        while (!c.isAfterLast()) {
				item_baiviet i = new item_baiviet(c.getString(0), c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7));
	            bv_List.add(i);
	            c.moveToNext();
	        }
	        c.close();
	    }
	    
	    if (ada != null) {
	        ada.notifyDataSetChanged();
	    } else {
			ada = new Ada_baiviet(this, R.layout.listview_baiviet_items, bv_List);
			simpleList.setAdapter(ada);
	    }
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_xemBV_toHome) {
			Intent intent = new Intent(Xem_baiViet.this, QL_baiViet.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xem_bai_viet, menu);
		return true;
	}

}
