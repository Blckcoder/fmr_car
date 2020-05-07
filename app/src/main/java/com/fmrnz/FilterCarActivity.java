package com.fmrnz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.fmrnz.adapter.FilterExpandableAdapter;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.interfaces.ItemInterface;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.FilterModel;

import org.apache.commons.collections4.map.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilterCarActivity extends BaseActivity {

    ExpandableListView filterListView;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    List<String> headerList = new ArrayList<>();
    List<String> childList = new ArrayList<>();

    List<String> list = new ArrayList<>();

    MultiValueMap finalMap = new MultiValueMap();
    private static final String SEPARATOR = ",";
    private String filterValue,filterKey;
    HashMap<String,String> filterData = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_car);
        filterListView = (ExpandableListView)findViewById(R.id.filterExpandableListView);
        expandableListDetail = FilterModel.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        FilterExpandableAdapter expandableListAdapter = new FilterExpandableAdapter(this, expandableListTitle, expandableListDetail, new ItemInterface() {
            @Override
            public void selectedDataCallback(MultiValueMap map) {
                finalMap = map;
                Toast.makeText(FilterCarActivity.this,"Map",Toast.LENGTH_SHORT).show();

            }
        });
        filterListView.setAdapter(expandableListAdapter);



        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set entrySet = finalMap.entrySet();
                Iterator it = entrySet.iterator();
                System.out.println("  Object key  Object value");
                while (it.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) it.next();
                    list = (List) finalMap.get(mapEntry.getKey());
                    StringBuilder csvBuilder = new StringBuilder();

                    for(String value : list){
                        csvBuilder.append(value);
                        csvBuilder.append(SEPARATOR);
                    }

                    filterValue = csvBuilder.toString();
                    filterValue = filterValue.substring(0, filterValue.length() - SEPARATOR.length());
                    filterKey = mapEntry.getKey().toString();
                    filterData.put(filterKey,filterValue);


                    for (int j = 0; j < list.size(); j++) {
                        System.out.println("\t" + mapEntry.getKey() + "\t  " + list.get(j));
                    }
                }

                callFilterRequest();
            }
        });



        /*filterListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
                headerList.add(expandableListTitle.get(groupPosition));
            }
        });

        filterListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();



            }
        });

        filterListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                map.put(expandableListTitle.get(groupPosition),expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                childList.add(expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                return false;
            }
        });*/
    }

    private void callFilterRequest() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FILTER_CAR);
        esLoginRequest.requestMap = filterData;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        // Utils.hideProgressBar(progressIndicatorView, loginBtn);

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.FILTER_CAR:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    ArrayList<CarRentalListModel> rentalDataModelArrayList = networkResponse.rentallistModelArrayList;
                    if(rentalDataModelArrayList != null && rentalDataModelArrayList.size()  >0){
                        Intent intent=new Intent();
                        Bundle bundle  = new Bundle();
                        bundle.putParcelableArrayList("FilterList",rentalDataModelArrayList);
                        intent.putExtra("Data",bundle);
                        setResult(1000,intent);
                        finish();

                    }

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {

                    String responseMessage = networkResponse.responseMessage;
                    failureSweetDialg("Failure", responseMessage);

                }
                else{

                    String responseMessage = networkResponse.responseMessage;
                    failureSweetDialg("Failure", responseMessage);
                }
                break;
        }
    }


}