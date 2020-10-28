package com.ezzetech.mujib100.datasource;

import android.content.Context;

import com.ezzetech.mujib100.R;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListDataSource {

    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new LinkedHashMap<>();


        //List<String> itemList = Arrays.asList(context.getResources().getStringArray(R.array.all_title));

        String[] itemList = context.getResources().getStringArray(R.array.all_title);

        List<String> mujib = Arrays.asList(context.getResources().getStringArray(R.array.mujib));
        List<String> celebration = Arrays.asList(context.getResources().getStringArray(R.array.celebration));
        List<String> resources = Arrays.asList(context.getResources().getStringArray(R.array.resources));
        List<String> events = Arrays.asList(context.getResources().getStringArray(R.array.events));
        List<String> media_and_news = Arrays.asList(context.getResources().getStringArray(R.array.media_and_news));
        List<String> committee = Arrays.asList(context.getResources().getStringArray(R.array.committee));
        List<String> wishes = Arrays.asList(context.getResources().getStringArray(R.array.wishes));
        List<String> contact_us = Arrays.asList(context.getResources().getStringArray(R.array.contact_us));
        List<String> settings = Arrays.asList(context.getResources().getStringArray(R.array.settings));

        for (int i = 0; i < itemList.length; i++) {
            switch (i) {
                case 0:
                    expandableListData.put(itemList[0], mujib);
                    break;

                case 1:
                    expandableListData.put(itemList[1], celebration);
                    break;

                case 2:
                    expandableListData.put(itemList[2], resources);
                    break;

                case 3:
                    expandableListData.put(itemList[3], events);
                    break;

                case 4:
                    expandableListData.put(itemList[4], media_and_news);
                    break;

                case 5:
                    expandableListData.put(itemList[5],committee);
                    break;

                case 6:
                    expandableListData.put(itemList[6],wishes);
                    break;
                case 7:
                    expandableListData.put(itemList[7],contact_us);
                    break;
                case 8:
                    expandableListData.put(itemList[8],settings);
                    break;

            }
        }
        return expandableListData;
    }
}
