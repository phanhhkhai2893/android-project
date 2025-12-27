package com.example.ddcn;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Xem_binhLuan extends Activity {

	ListView simpleList;
	ArrayList<item_binhluan> bl_List = new ArrayList<item_binhluan>();
	Ada_binhluan ada = null;
	db_connection db = new db_connection(this);
	String data;
	AlertDialog.Builder adb = null;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xem_binh_luan);

        this.setTitle("DANH SÁCH BÌNH LUẬN");
        

		simpleList = (ListView) findViewById(R.id.listView1);
		
		Cursor c = db.get_binh_luan();
		if (c == null)
		{
			Toast.makeText(getApplication(), "C rỗng!", Toast.LENGTH_LONG).show();
		}
		else
		{
			try
			{
				bl_List.clear();
				c.moveToFirst();
				while (!c.isAfterLast())
				{
					item_binhluan i = new item_binhluan(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
					bl_List.add(i);
					c.moveToNext();
				}
				c.close();
				
				ada = new Ada_binhluan(this, R.layout.listview_binhluan_items, bl_List);
				simpleList.setAdapter(ada);
				
				simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
				    	item_binhluan selectedItem = (item_binhluan) parent.getItemAtPosition(position);
				        
				        String ma = selectedItem.getMaBL();
						Intent intent = new Intent(Xem_binhLuan.this, Sua_binhLuan.class);
						intent.putExtra("MaBL", ma);
						startActivity(intent);
				    }
				});
				
				simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						pos = arg2;
						adb = new AlertDialog.Builder(Xem_binhLuan.this);
						adb.setTitle("Thông tin bình luận");
						String data = db.load_binh_luan(bl_List.get(pos).getMaBL());
						adb.setMessage(data);
						adb.setCancelable(true);
						adb.setPositiveButton("Xóa bình luận", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								adb = new AlertDialog.Builder(Xem_binhLuan.this);
								adb.setTitle("Xóa bình luận");
								adb.setMessage("Xác nhận xóa bình luận này?");
								adb.setCancelable(true);
								adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										// TODO Auto-generated method stub
										db.delete_binh_luan(bl_List.get(pos).getMaBL());
										reload();
										Toast.makeText(getApplication(), "Xóa bình luận thành công!", Toast.LENGTH_LONG).show();
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
		bl_List.clear(); 

	    Cursor c = db.get_binh_luan();
	    if (c != null && c.getCount() > 0) {
	        c.moveToFirst();
	        while (!c.isAfterLast()) {
				item_binhluan i = new item_binhluan(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
				bl_List.add(i);
				c.moveToNext();
	        }
	        c.close();
	    }
	    
	    if (ada != null) {
	        ada.notifyDataSetChanged();
	    } else {
			ada = new Ada_binhluan(this, R.layout.listview_binhluan_items, bl_List);
			simpleList.setAdapter(ada);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xem_binh_luan, menu);
		return true;
	}

}
