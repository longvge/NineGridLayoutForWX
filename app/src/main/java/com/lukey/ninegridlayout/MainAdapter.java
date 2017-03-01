package com.lukey.ninegridlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * creator  Lukey on 2016/6/14
 */
public class MainAdapter extends BaseAdapter {
    private Context context;
    private List<List<Image>> datalist;

    public MainAdapter(Context context, List<List<Image>> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        List<Image> itemList = datalist.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemt, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMore = (NineGridlayout) convertView.findViewById(R.id.iv_ngrid_layout);
            viewHolder.ivOne = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            viewHolder.ivMore.edge = 20;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.ivMore.setVisibility(View.GONE);
            viewHolder.ivOne.setVisibility(View.GONE);
        }
//        else if (itemList.size() == 1) {
//            viewHolder.ivMore.setVisibility(View.GONE);
//            viewHolder.ivOne.setVisibility(View.VISIBLE);
//
//            handlerOneImage(viewHolder, itemList.get(0));
//        }
        else {
            viewHolder.ivMore.setVisibility(View.VISIBLE);
            viewHolder.ivOne.setVisibility(View.GONE);

            viewHolder.ivMore.setImagesData(itemList);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        viewHolder.item_time.setText(str);
        return convertView;
    }

    private void handlerOneImage(ViewHolder viewHolder, Image image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.ivOne.setLayoutParams(layoutparams);
        viewHolder.ivOne.setClickable(true);
        viewHolder.ivOne.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        viewHolder.ivOne.setImageUrl(image.getUrl());



    }


    class ViewHolder {
        public NineGridlayout ivMore;
        public CustomImageView ivOne;
        public TextView item_time;
    }
}
