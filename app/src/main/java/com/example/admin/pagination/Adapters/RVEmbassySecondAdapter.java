package com.example.admin.pagination.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.pagination.Activities.SecondActivities.EmbassySecondActivity;
import com.example.admin.pagination.Activities.SecondActivities.NewsSecondActivity;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;

import com.example.admin.pagination.Serializables.Consulate;
import com.example.admin.pagination.Serializables.Embassy;
import com.example.admin.pagination.Serializables.Istories;

import java.util.List;

public class RVEmbassySecondAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Consulate> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public Context context;


    public RVEmbassySecondAdapter(List<Consulate> students, RecyclerView recyclerView,Context context) {
        studentList = students;
        this.context=context;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return studentList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_rv_consulate, parent, false);

            vh = new StudentViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StudentViewHolder) {

            Consulate singleStudent= (Consulate) studentList.get(position);

            ((StudentViewHolder) holder).tvPhoneNumber.setText(singleStudent.getPhoneNumber());
            ((StudentViewHolder) holder).tvAddress.setText(singleStudent.getAddress());
            ((StudentViewHolder) holder).tvRegion.setText(singleStudent.getRegion());

            ((StudentViewHolder) holder).student= singleStudent;

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    //
    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRegion;
        Context mContext;

        public TextView tvAddress;
        public TextView tvPhoneNumber;


        public Consulate student;

        public StudentViewHolder(View v) {
            super(v);
            tvRegion = (TextView) v.findViewById(R.id.tv_region_consulate);

            tvAddress = (TextView) v.findViewById(R.id.tv_address_consulate);
            tvPhoneNumber = (TextView) v.findViewById(R.id.tv_phone_number_consulate);

            tvAddress.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", student.getAddress());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
                }
            });
            tvPhoneNumber.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhoneNumber() , null)));
                }
            });


        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);

        }
    }
}