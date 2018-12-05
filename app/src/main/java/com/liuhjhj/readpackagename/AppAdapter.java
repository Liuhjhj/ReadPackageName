package com.liuhjhj.readpackagename;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter implements Filterable{

    private List<App> app;
    private ArrayList<App> mOriginalValues;
    private ArrayFilter mFilter;
    private final Object mLock = new Object();
    private Context mContext;

    AppAdapter(Context context,List<App> app){
        this.mContext = context;
        this.app = app;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return app.get(position);
    }

    @Override
    public int getCount() {
        return app.size();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (mFilter == null){
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.application_item,null);
            holder = new ViewHolder();
            holder.appIcon = view.findViewById(R.id.app_icon);
            holder.appName = view.findViewById(R.id.app_name);
            holder.appPackageName = view.findViewById(R.id.app_package_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.appIcon.setImageDrawable(app.get(position).getAppIcon());
        holder.appPackageName.setText(app.get(position).getAppPackageName());
        holder.appName.setText(app.get(position).getAppName());
        return view;
    }

    static class ViewHolder{
        TextView appPackageName;
        TextView appName;
        ImageView appIcon;
    }

    class ArrayFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // 过滤的结果
            FilterResults results = new FilterResults();
            // 原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(app);
                }
            }
            // 当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<App> list;
                // 同步复制一个原始备份数据
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                // 此时返回的results就是原始的数据，不进行过滤
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<App> values;
                // 同步复制一个原始备份数据
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<App> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    // 从List<App>中拿到App对象
                    final App value = values.get(i);
                    // App对象的任务名称属性作为过滤的参数
                    String valueText = value.getAppName().toLowerCase();
                    // 关键字是否和item的过滤参数匹配
                    if (valueText.contains(prefixString)) {
                        // 将这个item加入到数组对象中
                        newValues.add(value);
                    }
                }
                // 此时的results就是过滤后的List<TaskModel>数组
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            app = (List<App>) results.values;
            if (results.count > 0) {
                // 这个相当于从mDatas中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
                notifyDataSetChanged();
            } else {
                // 当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
                notifyDataSetInvalidated();
            }
        }
    }
}


