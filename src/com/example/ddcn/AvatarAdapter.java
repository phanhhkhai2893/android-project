package com.example.ddcn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AvatarAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Avatar> avatarList;

    public AvatarAdapter(Context context, int layout, List<Avatar> avatarList) {
        this.context = context;
        this.layout = layout;
        this.avatarList = avatarList;
    }

    @Override
    public int getCount() {
        return avatarList.size();
    }

    @Override
    public Object getItem(int position) {
        return avatarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về vị trí dòng
    }

    private class ViewHolder {
        ImageView imgHinh;
        TextView txtTen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder = new ViewHolder();
            holder.txtTen = (TextView) convertView.findViewById(R.id.tvTenAvatar);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.imgAvatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Gán dữ liệu
        Avatar avatar = avatarList.get(position);
        holder.txtTen.setText(avatar.getTenHienThi());
        holder.imgHinh.setImageResource(avatar.getResId());

        return convertView;
    }
}
