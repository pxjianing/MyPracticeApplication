package com.jn.mypracticeapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.mypracticeapplication.R;
import com.jn.mypracticeapplication.entity.SimpleGVEntity;

public class SimpleGVAdapter extends BaseAdapter {

	private List<SimpleGVEntity> data;
	private LayoutInflater inflater;

	public SimpleGVAdapter(Context context, List<SimpleGVEntity> data) {
		setData(data);
		this.inflater = LayoutInflater.from(context);
	}

	public void setData(List<SimpleGVEntity> data) {
		if (data == null) {
			data = new ArrayList<SimpleGVEntity>();
		}
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.simple_gv_item, null);
			holder.image = (ImageView) convertView.findViewById(R.id.iv_simple_gv_item_image);
			holder.name = (TextView) convertView.findViewById(R.id.tv_simple_gv_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SimpleGVEntity contact = data.get(position);

		holder.image.setImageResource(contact.getImage());
		holder.name.setText(contact.getsIndex());

		return convertView;
	}

	class ViewHolder {
		ImageView image;
		TextView name;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
