package com.example.admin.pagination.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Mig2;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RVHTAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Mig2> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public Context context;


    public RVHTAdapter(List<Mig2> students, RecyclerView recyclerView, Context context) {
        studentList = students;
        this.context=context;






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
                    R.layout.item_rv_ht_second, parent, false);

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

            Mig2 singleStudent= (Mig2) studentList.get(position);
            if (singleStudent.getType()==0) {
                ((StudentViewHolder) holder).tvName.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).tvName.setText(singleStudent.getText());
                ((StudentViewHolder) holder).imageView.setVisibility(View.GONE);


                ((StudentViewHolder) holder).student = singleStudent;
            } else
            {
                ((StudentViewHolder) holder).tvName.setVisibility(View.GONE);
                ((StudentViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load("http://176.126.167.249/"+singleStudent.getText()).placeholder(R.drawable.fax).into(((StudentViewHolder) holder).imageView);
            }
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



    //
    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        Context mContext;

        public TextView tvEmailId;
        ImageView imageView;
        public Mig2 student;

        public StudentViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_mig_second);
            imageView= (ImageView) v.findViewById(R.id.image_mig_second);



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