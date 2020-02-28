// WITH HELP FROM: https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
package com.example.foodtasker.Adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.foodtasker.Fragments.OrderHistoryFragment;
import com.example.foodtasker.R;
import com.example.foodtasker.Objects.Order;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import android.util.Log;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private OrderHistoryFragment _context;
    private List<Order> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Order, List<String>> _listDataChild;
    private LayoutInflater infalInflater;

    public ExpandableListAdapter(OrderHistoryFragment context, List<Order> listDataHeader,
                                 HashMap<Order, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            infalInflater = (LayoutInflater) _context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.order_history_tray, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.history_tray_meal_name);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            infalInflater = (LayoutInflater) _context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_order_history, null);
        }

        TextView restaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
        TextView customerName = (TextView) convertView.findViewById(R.id.customer_name);
        TextView customerAddress = (TextView) convertView.findViewById(R.id.customer_address);
        ImageView customerImage = (ImageView) convertView.findViewById(R.id.customer_image);

        final Order order = _listDataHeader.get(groupPosition);
        Log.d("ORDER PASSED TO ADAPTOR", order.toString());
        restaurantName.setText(order.getRestaurantName());
        customerName.setText(order.getCustomerName());
        customerAddress.setText(order.getCustomerAddress());
        Picasso.with(_context.getContext()).load(order.getCustomerImage()).fit().into(customerImage);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}