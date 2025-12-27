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

public class Xem_thePL extends Activity {

	ListView simpleList;
	ArrayList<item_thepl> pl_List = new ArrayList<item_thepl>();
	Ada_thePL ada = null;
	db_connection db = new db_connection(this);
	String data;
	AlertDialog.Builder adb = null;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xem_the_pl);

        this.setTitle("DANH SÁCH THẺ PHÂN LOẠI");
        

		simpleList = (ListView) findViewById(R.id.listView1);

		Cursor c = db.get_the_phan_loai();
		if (c == null)
		{
			Toast.makeText(getApplication(), "C rỗng!", Toast.LENGTH_LONG).show();
		}
		else
		{
			try
			{
				pl_List.clear();
				c.moveToFirst();
				while (!c.isAfterLast())
				{
					item_thepl i = new item_thepl(c.getString(0), c.getString(1), c.getString(2));
					pl_List.add(i);
					c.moveToNext();
				}
				c.close();
				
				ada = new Ada_thePL(this, R.layout.listview_thepl_items, pl_List);
				simpleList.setAdapter(ada);
				
				simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
				    	item_thepl selectedItem = (item_thepl) parent.getItemAtPosition(position);
				        
				        String ma = selectedItem.getMaThePL();
						Intent intent = new Intent(Xem_thePL.this, Sua_thePL.class);
						intent.putExtra("MaThePL", ma);
						startActivity(intent);
				    }
				});
				
				simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						pos = arg2;
						adb = new AlertDialog.Builder(Xem_thePL.this);
						adb.setTitle("Thông tin thẻ phân loại");
						String data = db.load_the_phan_loai(pl_List.get(pos).getMaThePL());
						adb.setMessage(data);
						adb.setCancelable(true);
						adb.setPositiveButton("Xóa thẻ phân loại", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								adb = new AlertDialog.Builder(Xem_thePL.this);
								adb.setTitle("Xóa thẻ phân loại");
								adb.setMessage("Xác nhận xóa thẻ phân loại này?");
								adb.setCancelable(true);
								adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										// TODO Auto-generated method stub
										db.delete_the_phan_loai(pl_List.get(pos).getMaThePL());
										reload();
										Toast.makeText(getApplication(), "Xóa thẻ phân loại thành công!", Toast.LENGTH_LONG).show();
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
		pl_List.clear(); 

	    Cursor c = db.get_the_phan_loai();
	    if (c != null && c.getCount() > 0) {
	        c.moveToFirst();
	        while (!c.isAfterLast()) {
	        	item_thepl i = new item_thepl(c.getString(0), c.getString(1), c.getString(2));
	            pl_List.add(i);
	            c.moveToNext();
	        }
	        c.close();
	    }
	    
	    if (ada != null) {
	        ada.notifyDataSetChanged();
	    } else {
			ada = new Ada_thePL(this, R.layout.listview_thepl_items, pl_List);
			simpleList.setAdapter(ada);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xem_the_pl, menu);
		return true;
	}

}
