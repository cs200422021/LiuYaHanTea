package com.tea.liuyahan.liuyahantea.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tea.liuyahan.liuyahantea.R;
import com.tea.liuyahan.liuyahantea.entity.TeaInfoEntity;
import com.tea.liuyahan.liuyahantea.utils.MyHttpUtils;

import java.util.List;

public class NewsFragmentAdapter extends BaseAdapter {
    private List<TeaInfoEntity.DataEntity> datas;
    private LayoutInflater inflater;

    // 主线程中的Handler对象
    private Handler handler;

    public NewsFragmentAdapter(Context context, List<TeaInfoEntity.DataEntity> datas, Handler handler) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_item_fragment_tab, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position != 0) {
            holder.titleTextView.setText(datas.get(position).getTitle());
            if (datas.get(position).getDescription() != null) {
                holder.descTextView.setText(datas.get(position).getDescription());
            } else {
                holder.descTextView.setText(" ");
            }
            holder.infosTextView.setText(datas.get(position).getSource() + "  " + datas.get(position).getNickname() + "  " + datas.get(position).getCreate_time());
            // 异步加载图片
            String imgUrl = datas.get(position).getWap_thumb();
            downloadImage(imgUrl, holder.iconImageView);
        }
        return convertView;
    }

    // 下载网络图片
    private void downloadImage(final String url, final ImageView iconImageView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = MyHttpUtils.getBitmapFromUrl(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iconImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private class ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
        TextView descTextView;
        TextView infosTextView;

        public ViewHolder(View convertView) {
            iconImageView = (ImageView) convertView.findViewById(R.id.icon_iv);
            titleTextView = (TextView) convertView.findViewById(R.id.title_tv);
            descTextView = (TextView) convertView.findViewById(R.id.desc_tv);
            infosTextView = (TextView) convertView.findViewById(R.id.infos_tv);
        }
    }

}
