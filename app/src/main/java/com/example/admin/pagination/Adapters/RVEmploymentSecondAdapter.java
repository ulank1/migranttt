package com.example.admin.pagination.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.pagination.Activities.SecondActivities.StorySecondActivity;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Employment;

import java.util.List;

public class RVEmploymentSecondAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Employment> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public Context context;


    public RVEmploymentSecondAdapter(List<Employment> students, RecyclerView recyclerView,Context context) {
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
                    R.layout.item_rv_employment, parent, false);

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

            Employment singleStudent= (Employment) studentList.get(position);


            ((StudentViewHolder) holder).tvManager.setText(singleStudent.getManager());
            ((StudentViewHolder) holder).tvNumber.setText(singleStudent.getPhone_number());
            ((StudentViewHolder) holder).tvAddress.setText(singleStudent.getAdress());
            ((StudentViewHolder) holder).tvNumber1.setText(singleStudent.getPhone_number1());
            ((StudentViewHolder) holder).tvName.setText(singleStudent.getName());
            ((StudentViewHolder) holder).tvNumber2.setText(singleStudent.getPhone_number2());
            ((StudentViewHolder) holder).context= context;
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
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public  Context context;
        public TextView tvAddress,tvManager,tvNumber,tvNumber1,tvNumber2;

        public Employment student;

        public StudentViewHolder(View v) {
            super(v);

            tvName=(TextView) v.findViewById(R.id.tv_name_employment) ;
            tvAddress=(TextView) v.findViewById(R.id.tv_address_second) ;
            tvManager=(TextView) v.findViewById(R.id.tv_manager_employment) ;
            tvNumber=(TextView) v.findViewById(R.id.tv_phone_number_second) ;
            tvNumber1=(TextView) v.findViewById(R.id.tv_phone_number1_second) ;
            tvNumber2=(TextView) v.findViewById(R.id.tv_phone_number2_second) ;

            tvAddress.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", student.getAdress());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
                }
            });
            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhone_number() , null)));
                }
            });
            tvNumber1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhone_number1() , null)));
                }
            });
            tvNumber2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhone_number2() , null)));
                }
            });



            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {



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