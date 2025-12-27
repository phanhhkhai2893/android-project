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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ThanhVien_ChiTietBaiViet extends Activity implements OnClickListener {
	
	TextView tieude, ngaytao, luotxem, noidung, tacgia;
	LinearLayout viewHome, viewVietBai, viewQlBai, viewTk;
	ListView dsbl;
	EditText binhLuan;
	Button BL;
	
	ArrayList<item_binhluan> bl_List = new ArrayList<item_binhluan>();
	TV_Ada_binhluan ada = null;
	AlertDialog.Builder adb = null;

	db_connection db = new db_connection(this);
	Session_mand S_mand = Session_mand.getInstance();
	String maBlNew, ngayTao, maBV;
	item_binhluan selectedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thanh_vien__chi_tiet_bai_viet);

		Intent intent = getIntent();
		maBV = intent.getStringExtra("MaBV");

        viewHome = (LinearLayout) findViewById(R.id.view_home);
        viewHome.setOnClickListener(this);
        
        viewVietBai = (LinearLayout) findViewById(R.id.view_vietBai);
        viewVietBai.setOnClickListener(this);
        
        viewQlBai = (LinearLayout) findViewById(R.id.view_qlBai);
        viewQlBai.setOnClickListener(this);
        
        viewTk = (LinearLayout) findViewById(R.id.view_taikhoan);
        viewTk.setOnClickListener(this);
		
		tieude = (TextView) findViewById(R.id.ctbv_tieude);
		ngaytao = (TextView) findViewById(R.id.ctbv_ngaytao);
		luotxem = (TextView) findViewById(R.id.ctbv_luotxem);
		noidung = (TextView) findViewById(R.id.ctbv_noidung);
		tacgia = (TextView) findViewById(R.id.ctbv_tacgia);
		
		binhLuan = (EditText) findViewById(R.id.ctbv_edtBl);
		
		BL = (Button) findViewById(R.id.ctbv_btnBL);
		BL.setOnClickListener(this);
		
		dsbl = (ListView) findViewById(R.id.listView1);
		
		item_baiviet baiViet = db.getBV(maBV);
		if (baiViet != null)
		{
			item_nguoidung nguoiDung = db.getUser(baiViet.getMaND().toString());
			this.setTitle("Bài viết của " + nguoiDung.getHoten().toString());
			
			tieude.setText(baiViet.getTieude().toString());
			ngaytao.setText(baiViet.getNgaytao().toString() + 
					" (Cập nhật: " + baiViet.getNgaycapnhat().toString() + ")");
			luotxem.setText("Lượt xem: " + String.valueOf(baiViet.getLuotxem()));
			noidung.setText(baiViet.getNoidung().toString());
			tacgia.setText("Tác giả: " + nguoiDung.getHoten().toString());
		}
		
		try
		{
			Cursor c = db.get_binh_luan_theo_MaBV(baiViet.getMaBV().toString());
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
				
				ada = new TV_Ada_binhluan(this, R.layout.tv_listview_binhluan_items, bl_List);
				dsbl.setAdapter(ada);
				setListViewHeightBasedOnChildren(dsbl);
				registerForContextMenu(dsbl);
				dsbl.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
				    	selectedItem = (item_binhluan) arg0.getItemAtPosition(arg2);
				    	if (selectedItem.getMaND().equals(S_mand.getMaND().toString())) 
				    	{
				    		return false;
				        } 
				    	else {
				            return true;
				        }
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
			
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == BL)
		{
			maBlNew = taoMaBLMoi(db.getMaBLLonNhat());

			Date currentDate = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	        ngayTao = formatter.format(currentDate);
	        
			db.insert_binh_luan(maBlNew, binhLuan.getText().toString(), 
					ngayTao, S_mand.getMaND().toString(), maBV);
			
			adb = new AlertDialog.Builder(ThanhVien_ChiTietBaiViet.this);
			adb.setTitle("Thông báo");
			adb.setMessage("Thêm bình luận thành công!");
			adb.setCancelable(true);
			adb.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					reset();
				}
			});
			
			AlertDialog ad = adb.create();
			ad.show();
		}
		if (arg0 == viewHome)
		{
			finish();
			Intent intent = new Intent(ThanhVien_ChiTietBaiViet.this, Main_ThanhVien.class);
			startActivity(intent);
		}
		if (arg0 == viewVietBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_ChiTietBaiViet.this, ThanhVien_vietBai.class);
			startActivity(intent);
		}
		if (arg0 == viewQlBai)
		{
			finish();
			Intent intent = new Intent(ThanhVien_ChiTietBaiViet.this, ThanhVien_qlBai.class);
			startActivity(intent);
		}
		if (arg0 == viewTk)
		{
			finish();
			Intent intent = new Intent(ThanhVien_ChiTietBaiViet.this, ThanhVien_taiKhoan.class);
			startActivity(intent);
		}
	}
	
	public void reset()
	{
		binhLuan.setText("");
		binhLuan.requestFocus();
		
		maBlNew = taoMaBLMoi(db.getMaBLLonNhat());

		Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        ngayTao = formatter.format(currentDate);

		item_baiviet baiViet = db.getBV(maBV);
		Cursor c = db.get_binh_luan_theo_MaBV(baiViet.getMaBV().toString());
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
			
			ada = new TV_Ada_binhluan(this, R.layout.tv_listview_binhluan_items, bl_List);
			dsbl.setAdapter(ada);
			setListViewHeightBasedOnChildren(dsbl);
		}
		catch (Exception e)
		{
			Toast.makeText(getApplication(), "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	public String taoMaBLMoi(String maLonNhatHienTai) {
        String newMa = "";

        if (maLonNhatHienTai != null && !maLonNhatHienTai.isEmpty()) {
            try {
                String soHienTaiStr = maLonNhatHienTai.substring(2);
                
                int soHienTai = Integer.parseInt(soHienTaiStr);
                
                int soMoi = soHienTai + 1;
                String soMoiFormatted = String.format("%d", soMoi);
                
                newMa = "BL" + soMoiFormatted;

            } catch (NumberFormatException e) {
                System.err.println("Lỗi định dạng mã bl: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Lỗi độ dài mã bl: " + e.getMessage());
            }
        }
        
        return newMa;
    }
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        return;
	    }

	    int totalHeight = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(0, 0);
	        totalHeight += listItem.getMeasuredHeight();
	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    int extraPadding = (int) (50 * listView.getContext().getResources().getDisplayMetrics().density); 

	    params.height = totalHeight + 
	                    (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 
	                    extraPadding;
	                    
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);

	    if (v.getId() == R.id.listView1) { 
	        
	        AdapterView.AdapterContextMenuInfo info = 
	            (AdapterView.AdapterContextMenuInfo) menuInfo;

	        menu.add(Menu.NONE, 1, 1, "Chỉnh sửa"); // ID=1
	        menu.add(Menu.NONE, 2, 2, "Xóa");      // ID=2
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
				Intent intent = new Intent(ThanhVien_ChiTietBaiViet.this, ThanhVien_Sua_binhLuan.class);
				intent.putExtra("MaBL", selectedItem.getMaBL().toString());
				startActivity(intent);
	            return true;
	            
	        case 2:
	        	adb = new AlertDialog.Builder(ThanhVien_ChiTietBaiViet.this);
				adb.setTitle("Xóa bình luận");
				adb.setMessage("Xác nhận xóa bình luận này?");
				adb.setCancelable(true);
				adb.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						db.delete_binh_luan(selectedItem.getMaBL().toString());
						Toast.makeText(getApplication(), "Xóa bình luận thành công!", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.thanh_vien__chi_tiet_bai_viet, menu);
		return true;
	}

}
