package com.example.customcalendar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.customcalendar.R;
import com.example.customcalendar.model.CalendarHeader;
import com.example.customcalendar.model.Day;
import com.example.customcalendar.model.EmptyDay;
import com.example.customcalendar.model.MyViewModel;

import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter {
    private final int HEADER = 0; // 날짜 타입
    private final int EMPTY = 1; // 비어있는 일자 타입
    private final int DAY = 2; // 일자 타입

    private List<Object> mCalendarList;

    public CalendarAdapter(List<Object> mCalendarList) {
        this.mCalendarList = mCalendarList;
    }

    public void setCalendarList(List<Object> calendarList) {
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (viewType == HEADER) {
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(inflater.inflate(R.layout.header_item, viewGroup, false));
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) headerViewHolder.itemView.getLayoutParams();
            params.setFullSpan(true); // Span을 하나로 통합하기
            headerViewHolder.itemView.setLayoutParams(params);
            return headerViewHolder;

        } else if (viewType == EMPTY) {
            return new EmptyViewHolder(inflater.inflate(R.layout.empty_item, viewGroup, false));

        } else {
            return new DayViewHolder(inflater.inflate(R.layout.day_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        if(viewType == HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarHeader calendarHeader = new CalendarHeader();

            if(item instanceof Long) {
                calendarHeader.setHeader((Long) item);
            }
            headerViewHolder.bind(calendarHeader);

        } else if(viewType == EMPTY) {

        } else if(viewType == DAY) {
            DayViewHolder dayViewHolder = (DayViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            Day day = new Day();

            if(item instanceof Calendar) {
                day.setCalendar((Calendar) item);
            }
            dayViewHolder.bind(day);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mCalendarList.get(position);

        if (item instanceof Long) {
            return HEADER;
        } else if (item instanceof String) {
            return EMPTY;
        } else {
            return DAY;
        }
    }

    @Override
    public int getItemCount() {
        if(mCalendarList != null) {
            return mCalendarList.size();
        }
        return 0;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = (TextView)itemView.findViewById(R.id.titleTv);
        }

        public void bind(MyViewModel myViewModel) {
            String header = ((CalendarHeader) myViewModel).getHeader(); // 날짜 값 가져오기
            titleTv.setText(header);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayTv;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTv = (TextView)itemView.findViewById(R.id.dayTv);
        }

        public void bind(MyViewModel myViewModel) {
            String day = ((Day) myViewModel).getDay(); // 일자 값 가져오기
            dayTv.setText(day);
        }
    }
}
