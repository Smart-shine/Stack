package anqi.stack.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import anqi.stack.R;

/**
 * Created by niuanqi on 2017/7/10.
 */

public class ListViewAdapter implements ExpandableListAdapter {
    Context content;

    public ListViewAdapter(Context content) {
        this.content = content;
    }


    public String[] groupStrings = {"仪表盘", "申请管理", "事件管理"};
    public String[][] childStrings = {
            {},
            {"所有申请", "我的申请", "我的审批", "我的工单", "审批流程"},
            {"事件列表", "事件源配置", "事件阈值"}
    };

    @Override
    public int getGroupCount() {
        return groupStrings.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childStrings[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupStrings[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childStrings[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(content).inflate(R.layout.listview_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.icon = (ImageView) convertView.findViewById(R.id.Group_icon);
            groupViewHolder.title = (TextView) convertView.findViewById(R.id.Group_title);
            groupViewHolder.tail = (ImageView) convertView.findViewById(R.id.Group_tail);

            convertView.setTag(groupViewHolder);//????
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
//        groupViewHolder.icon.setImageDrawable();//set image
        groupViewHolder.title.setText(groupStrings[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(content).inflate(R.layout.listview_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.textView = (TextView) convertView.findViewById(R.id.Child_title);

            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.textView.setText(childStrings[groupPosition][childPosition]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;//指定位置上的child  item  是否能够被选中
    }




    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    static class GroupViewHolder {
        ImageView icon;
        TextView title;
        ImageView tail;
    }

    static class ChildViewHolder {
        TextView textView;
    }
}
