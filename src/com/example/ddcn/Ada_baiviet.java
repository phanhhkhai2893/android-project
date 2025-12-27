package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Ada_baiviet extends ArrayAdapter<item_baiviet> {

	ArrayList<item_baiviet> bv_List;
	
	public Ada_baiviet(Context context, int resource, ArrayList<item_baiviet> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		bv_List	= objects;
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
        v = inflater.inflate(R.layout.listview_baiviet_items, null);

        TextView MaBV = (TextView) v.findViewById(R.id.bv_mabv);
        TextView TieuDe = (TextView) v.findViewById(R.id.bv_tieude);
        TextView NoiDung = (TextView) v.findViewById(R.id.bv_noidung);
        TextView NgayTao = (TextView) v.findViewById(R.id.bv_ngaytao);
        TextView NgayCN = (TextView) v.findViewById(R.id.bv_ngaycn);
        TextView LuotXem = (TextView) v.findViewById(R.id.bv_nguoixem);
        TextView MaND = (TextView) v.findViewById(R.id.bv_mand);
        TextView MaPL = (TextView) v.findViewById(R.id.bv_mapl);

        MaBV.setText(bv_List.get(position).getMaBV());
        TieuDe.setText(bv_List.get(position).getTieude());
        NoiDung.setText(bv_List.get(position).getNoidung());
        NgayTao.setText(bv_List.get(position).getNgaytao());
        NgayCN.setText(bv_List.get(position).getNgaycapnhat());
        LuotXem.setText(String.valueOf(bv_List.get(position).getLuotxem()));
        MaND.setText(bv_List.get(position).getMaND());
        MaPL.setText(bv_List.get(position).getMaThePL());
        
        return v;
	}

}
