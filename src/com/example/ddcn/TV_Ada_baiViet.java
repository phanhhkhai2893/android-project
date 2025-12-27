package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TV_Ada_baiViet extends ArrayAdapter<item_baiviet> {

	ArrayList<item_baiviet> bv_List;
	db_connection db;
	
	public TV_Ada_baiViet(Context context, int resource, ArrayList<item_baiviet> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		bv_List	= objects;
		
		db = new db_connection(context);
	}

	@Override
	public int getCount() {
	    return bv_List.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        View v = convertView;
        
        LayoutInflater inflater = LayoutInflater.from(getContext());
        v = inflater.inflate(R.layout.tv_listview_baiviet_items, null);

        TextView TieuDe = (TextView) v.findViewById(R.id.tv_BV_tieude);
        TextView NoiDung = (TextView) v.findViewById(R.id.tv_BV_noidung);
        TextView NgayTao = (TextView) v.findViewById(R.id.tv_BV_ngaytao);
        TextView MaND = (TextView) v.findViewById(R.id.tv_BV_mand);

		item_nguoidung user = db.getUser(bv_List.get(position).getMaND());
		item_thepl pl = db.getPL(bv_List.get(position).getMaThePL());
		
		if (user != null) {
		    MaND.setText(user.getHoten() + "   |   " + pl.getTenpl().toString());
		} else {
		    MaND.setText(bv_List.get(position).getMaND() + "   |   " + pl.getTenpl().toString());
		}
		
        TieuDe.setText(bv_List.get(position).getTieude());
        NoiDung.setText(bv_List.get(position).getNoidung());
        NgayTao.setText(bv_List.get(position).getNgaytao() + "   |   Lượt xem: " + String.valueOf(bv_List.get(position).getLuotxem()));
        
        return v;
	}
	
}
