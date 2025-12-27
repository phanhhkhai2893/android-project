package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Ada_nguoidung extends ArrayAdapter<item_nguoidung> {

	ArrayList<item_nguoidung> nd_List;
	
	public Ada_nguoidung(Context context, int resource, ArrayList<item_nguoidung> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		nd_List	= objects;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        View v = convertView;
        
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.listview_nguoidung_items, null);
        
        TextView hoten = (TextView) v.findViewById(R.id.lbl_hoten);
        TextView email = (TextView) v.findViewById(R.id.lbl_email);
        TextView ngaytao = (TextView) v.findViewById(R.id.lbl_ngaytao);
        ImageView hinh = (ImageView) v.findViewById(R.id.imageView1);

        hoten.setText(nd_List.get(position).getHoten());
        email.setText(nd_List.get(position).getUsername() + " | " + nd_List.get(position).getEmail());
        
        if (nd_List.get(position).getUsername().equals("admin"))
        	ngaytao.setText("Admin (Tạo: " + nd_List.get(position).getNgaytao() + ")");
        else
        	ngaytao.setText("Thành viên (Tạo: " + nd_List.get(position).getNgaytao() + ")");
        
        String fileName = nd_List.get(position).getAvarta();
        
        if (fileName != null && !fileName.isEmpty()) {
            
            int imageResourceID = parent.getContext().getResources().getIdentifier(
                fileName, 
                "drawable", 
                parent.getContext().getPackageName()
            );
            
            if (imageResourceID != 0) {
            	hinh.setImageResource(imageResourceID);
            } else {
                // Trường hợp không tìm thấy file ảnh (ID = 0)
            	hinh.setImageResource(R.drawable.hinh_user); 
            }
        } else {
            // Trường hợp tên file trống/null
        	hinh.setImageResource(R.drawable.hinh_user); 
        }
        return v;
	}

}
