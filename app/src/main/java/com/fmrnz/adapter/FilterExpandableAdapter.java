package com.fmrnz.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrnz.CustomGridView;
import com.fmrnz.R;
import com.fmrnz.interfaces.ItemInterface;

import org.apache.commons.collections4.map.MultiValueMap;
import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eurysinfosystems on 19/06/18.
 */

public class FilterExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    String type;
    MultiValueMap map = new MultiValueMap();
    ItemInterface itemInterface;

    public FilterExpandableAdapter(Context context, List<String> expandableListTitle,
                                   HashMap<String, List<String>> expandableListDetail, ItemInterface interfaceItem) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.itemInterface = interfaceItem;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
//                .size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.filterHeader);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_child, null);
        }

        final ViewHolder viewHolder = new ViewHolder();

        viewHolder.toolbarGrid = (CustomGridView) convertView.findViewById(R.id.GridView_toolbar);
        viewHolder.rangeSeekBar = (RangeSeekBar)convertView.findViewById(R.id.rangeSeekbar);
        viewHolder.seekBarLayout = (LinearLayout)convertView.findViewById(R.id.rangeLayout);
        viewHolder.rangeseekBarTV = (TextView)convertView.findViewById(R.id.rangeTextView);

        final String listTitle = (String) getGroup(groupPosition);
        String headerTitle = null;
        if(listTitle.equals("Vehicle Type") || listTitle.equals("Vehicle Make") || listTitle.equals("Transmission") || listTitle.equals("Fuel Type")
                || listTitle.equals("Owner Type") || listTitle.equals("Number Of Seats")){
            if(listTitle.equals("Vehicle Type")){
                headerTitle = "vehicle_type";
                type = "";
            }
            if(listTitle.equals("Vehicle Make")){
                headerTitle = "vehicle_make";
                type = "";
            }
            if(listTitle.equals("Transmission")){
                headerTitle = "transmition";
                type = "";
            }
            if(listTitle.equals("Fuel Type")){
                headerTitle = "fuel_type";
                type = "";
            }
            if(listTitle.equals("Owner Type")){
                headerTitle = "owner_type";
                type = "";
            }
            if(listTitle.equals("Number Of Seats")){
                headerTitle = "seats";
                type = "Seater";
            }
            viewHolder.toolbarGrid.setVisibility(View.VISIBLE);
            viewHolder.seekBarLayout.setVisibility(View.GONE);
            viewHolder.toolbarGrid.setVisibility(View.VISIBLE);
            viewHolder.seekBarLayout.setVisibility(View.GONE);
            viewHolder.toolbarGrid.setGravity(Gravity.CENTER);
            viewHolder.toolbarGrid.setHorizontalSpacing(5);
            final List<String> list = this.expandableListDetail.get(this.expandableListTitle.get(groupPosition));
            viewHolder.toolbarGrid.setAdapter(getMenuAdapter(list));

            final String finalHeaderTitle = headerTitle;
            viewHolder.toolbarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.item_text);
                    textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    Toast.makeText(context, listTitle + " -> " + list.get(position), Toast.LENGTH_SHORT).show();
                    map.put(finalHeaderTitle,list.get(position));
                    itemInterface.selectedDataCallback(map);

                }
            });
        }
        else{
            viewHolder.toolbarGrid.setVisibility(View.GONE);
            viewHolder.seekBarLayout.setVisibility(View.VISIBLE);


            if (listTitle.equals("Make Year")){
                headerTitle = "makeyear";
                viewHolder.rangeSeekBar.setRangeValues(1960,2018);
                type = "Year";
            }
            else if (listTitle.equals("Search By Price")){
                viewHolder.rangeSeekBar.setRangeValues(2000,10000);
                type = "Rs";
            }
            else if (listTitle.equals("Search By Distance")){
                viewHolder.rangeSeekBar.setRangeValues(2,50);
                type = "km";
            }

            final String finalHeaderTitle1 = headerTitle;
            viewHolder.rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                    viewHolder.rangeseekBarTV.setText(minValue + "-" + maxValue + " " + type);
//                    list.add(0,minValue.toString());
//                    list.add(1,maxValue.toString());
                    map.put(finalHeaderTitle1,minValue.toString());
                    map.put(finalHeaderTitle1,maxValue.toString());
                    itemInterface.selectedDataCallback(map);
                }
            });
        }




        return convertView;
    }

    private SimpleAdapter getMenuAdapter(List<String> listString)
    {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < listString.size(); i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            if(type.equals("Seater"))
                map.put("itemText", listString.get(i) + " Seater");
            else
                map.put("itemText", listString.get(i));
            data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context,data,R.layout.item_menu,new String[]{"itemText"},new int[] { R.id.item_text});

        return simpleAdapter;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        TextView rangeseekBarTV;
        LinearLayout seekBarLayout;
        CustomGridView toolbarGrid;
        RangeSeekBar rangeSeekBar;
    }
}