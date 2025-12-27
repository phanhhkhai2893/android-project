package com.example.ddcn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Ada_thePL extends ArrayAdapter<item_thepl> {

	ArrayList<item_thepl> pl_List;
	
	public Ada_thePL(Context context, int resource, ArrayList<item_thepl> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		pl_List	= objects;
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
        v = inflater.inflate(R.layout.listview_thepl_items, null);

        TextView MaPL = (TextView) v.findViewById(R.id.lbl_pl);
        TextView TenPL = (TextView) v.findViewById(R.id.lbl_tenpl);
        TextView MoTa = (TextView) v.findViewById(R.id.lbl_mota);

        MaPL.setText(pl_List.get(position).getMaThePL());
        TenPL.setText(pl_List.get(position).getTenpl());
        MoTa.setText(pl_List.get(position).getMota());
        
        return v;
	}

}
