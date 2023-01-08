package com.example.customcalendar.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customcalendar.R;
import com.example.customcalendar.adapter.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainFragment extends Fragment {
    ArrayList<Object> mCalendarList = new ArrayList<>();

    TextView titleTv;
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private CalendarAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        initView(rootView);
        initSet();
        setRecyclerView();

        return rootView;
    }

    public void initView(View view) {
        titleTv = (TextView)view.findViewById(R.id.titleTv);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
    }

    public void initSet() {
        initCalendarList();
    }

    public void initCalendarList() {
        GregorianCalendar cal = new GregorianCalendar();
        setCalendarList(cal);
    }

    public void setCalendarList(GregorianCalendar cal) {
        ArrayList<Object> calendarList = new ArrayList<>();

        for (int i = -300; i < 300; i++) {
            try {
                GregorianCalendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + i, 1, 0, 0, 0);

                calendarList.add(calendar.getTimeInMillis());

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // DAY_OF_WEEK는 calendar가 가리키는 특정 날짜가 무슨 요일인지 알기 위해 쓰임
                int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월의 말일 구하기

                for (int j = 0; j < dayOfWeek; j++) {
                    calendarList.add(com.example.customcalendar.util.Keys.EMPTY);
                }
                for (int j = 1; j <= max; j++) {
                    calendarList.add(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCalendarList = calendarList;
        }
    }

    private void setRecyclerView() {
        layoutManager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL);
        adapter = new CalendarAdapter(mCalendarList);
        adapter.setCalendarList(mCalendarList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}