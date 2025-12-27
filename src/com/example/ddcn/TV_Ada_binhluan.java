package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TV_Ada_binhluan extends ArrayAdapter<item_binhluan>  {

	ArrayList<item_binhluan> bl_List;
	db_connection db;
	
	public TV_Ada_binhluan(Context context, int resource, ArrayList<item_binhluan> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		bl_List	= objects;
		
		db = new db_connection(context);
	}

	@Override
	public int getCount() {
	    return bl_List.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        View v = convertView;
        
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.tv_listview_binhluan_items, null);

        TextView TacGia = (TextView) v.findViewById(R.id.bl_items_tacgia);
        TextView NoiDung = (TextView) v.findViewById(R.id.bl_items_noidung);
        ImageView avarta = (ImageView) v.findViewById(R.id.imageView1);

		item_nguoidung user = db.getUser(bl_List.get(position).getMaND());
		
		if (user != null) {
			String fileName = user.getAvarta();
	        
	        if (fileName != null && !fileName.isEmpty()) {
	            
	            int imageResourceID = parent.getContext().getResources().getIdentifier(
	                fileName, 
	                "drawable", 
	                parent.getContext().getPackageName()
	            );
	            
	            if (imageResourceID != 0) {
	            	avarta.setImageResource(imageResourceID);
	            } else {
	                // Trường hợp không tìm thấy file ảnh (ID = 0)
	            	avarta.setImageResource(R.drawable.hinh_user); 
	            }
	        } else {
	            // Trường hợp tên file trống/null
	        	avarta.setImageResource(R.drawable.hinh_user); 
	        }
			TacGia.setText(user.getHoten().toString() + " | " + bl_List.get(position).getNgaytao().toString());
		} else {
			TacGia.setText(bl_List.get(position).getMaND().toString() + " | " + bl_List.get(position).getNgaytao().toString());
		}
		
        NoiDung.setText(bl_List.get(position).getNoidung().toString());
        
        return v;
	}
}
