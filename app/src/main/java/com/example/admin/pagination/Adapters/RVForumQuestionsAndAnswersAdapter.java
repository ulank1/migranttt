package com.example.admin.pagination.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.pagination.Helpers.DataHelper;
import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;
import com.example.admin.pagination.Serializables.VseAndUzery;

import java.util.List;

public class RVForumQuestionsAndAnswersAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<VseAndUzery> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
DataHelper dataHelper;
    String idUser;
    Cursor cursor;
Context context;
    public RVForumQuestionsAndAnswersAdapter(List<VseAndUzery> students, RecyclerView recyclerView, Context context) {
        studentList = students;
        dataHelper=new DataHelper(context);
         cursor=dataHelper.getDataUser();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            idUser=cursor.getString(cursor.getColumnIndex(DataHelper.USER_ID_COLUMN));
        }else idUser="";
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
                    R.layout.item_rv_forum_questions_and_answers, parent, false);

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

            VseAndUzery singleStudent= (VseAndUzery) studentList.get(position);
            if (!singleStudent.getId().equals(idUser)) {
                ((StudentViewHolder) holder).tvName.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).tvText.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).image.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).tvName1.setVisibility(View.GONE);
                ((StudentViewHolder) holder).tvText1.setVisibility(View.GONE);
                ((StudentViewHolder) holder).image1.setVisibility(View.GONE);

                ((StudentViewHolder) holder).tvName.setText("(" + singleStudent.getUsername() + ")");
                ((StudentViewHolder) holder).tvText.setText(singleStudent.getText());


            }else {
                ((StudentViewHolder) holder).tvName1.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).tvText1.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).image1.setVisibility(View.VISIBLE);
                ((StudentViewHolder) holder).tvName.setVisibility(View.GONE);
                ((StudentViewHolder) holder).tvText.setVisibility(View.GONE);
                ((StudentViewHolder) holder).image.setVisibility(View.GONE);

                ((StudentViewHolder) holder).tvName1.setText("(" + singleStudent.getUsername() + ")");
                ((StudentViewHolder) holder).tvText1.setText(singleStudent.getText());

            }
            ((StudentViewHolder) holder).student = singleStudent;
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

        public TextView tvText;
        public TextView tvName1;
        ImageView image,image1;
        public TextView tvText1;
        public VseAndUzery student;

        public StudentViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image1);
            image1 = (ImageView) v.findViewById(R.id.image2);


            tvName = (TextView) v.findViewById(R.id.tv_nick_name_forum_q_a);
            tvText=(TextView) v.findViewById(R.id.tv_text_forum_q_a) ;
            tvName1 = (TextView) v.findViewById(R.id.tv_nick_name_forum_q_a2);
            tvText1=(TextView) v.findViewById(R.id.tv_text_forum_q_a2) ;

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