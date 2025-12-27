package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Ada_binhluan extends ArrayAdapter<item_binhluan> {

	ArrayList<item_binhluan> bl_List;
	
	public Ada_binhluan(Context context, int resource, ArrayList<item_binhluan> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		bl_List	= objects;
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
        v = inflater.inflate(R.layout.listview_binhluan_items, null);

        TextView MaBL = (TextView) v.findViewById(R.id.lbl_bl_ma);
        TextView NoiDung = (TextView) v.findViewById(R.id.lbl_bl_noidung);
        TextView NgayTao = (TextView) v.findViewById(R.id.lbl_bl_ngaytao);
        TextView MaND = (TextView) v.findViewById(R.id.lbl_bl_mand);
        TextView MaBV = (TextView) v.findViewById(R.id.lbl_bl_mabv);

        MaBL.setText(bl_List.get(position).getMaBL());
        NoiDung.setText(bl_List.get(position).getNoidung());
        NgayTao.setText(bl_List.get(position).getNgaytao());
        MaND.setText(bl_List.get(position).getMaND());
        MaBV.setText(bl_List.get(position).getMaBV());
        
        return v;
	}

}
