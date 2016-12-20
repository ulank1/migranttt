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

import com.example.admin.pagination.Helpers.OnLoadMoreListener;
import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Mig2;
import com.example.admin.pagination.Serializables.NKO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class RVNKOSecondAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    public  RVMigSecondAdapter adapter;
    private List<NKO> studentList;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public  Context context;
    List<Mig2> list;

    public RVNKOSecondAdapter(List<NKO> students, RecyclerView recyclerView, Context context) {
        studentList = students;
        this.context= context;

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
                    R.layout.item_rv_nko, parent, false);

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

            NKO singleStudent= (NKO) studentList.get(position);
            list=new ArrayList<>();

            ((StudentViewHolder) holder).tvManager.setText(singleStudent.getManager());
            ((StudentViewHolder) holder).tvNumber.setText(singleStudent.getPhone());
            ((StudentViewHolder) holder).tvAddress.setText(singleStudent.getAddress());
            ((StudentViewHolder) holder).tvNumber1.setText(singleStudent.getPhone1());
            ((StudentViewHolder) holder).tvName.setText(singleStudent.getTitle());
            ((StudentViewHolder) holder).tvNumber2.setText(singleStudent.getMail());
            Mig2 mig2;

            for (Element element : Jsoup.parse(singleStudent.getText()).select("*")) {
                if (element.tagName().equals("p")) {
                    mig2=new Mig2();
                    mig2.setType(0);
                    mig2.setText(element.text());
                    list.add(mig2);
                }else if(element.tagName().equals("img")) {
                    mig2=new Mig2();
                    mig2.setType(1);
                    mig2.setText(element.attr("src"));
                    list.add(mig2);
                }else  if (element.tagName().equals("li")) {
                    mig2 = new Mig2();
                    mig2.setType(0);
                    mig2.setText(element.text());

                }
            }
            Log.e("SUKA0001",list.size()+"    "+singleStudent.getText());
            adapter= new RVMigSecondAdapter(list,((StudentViewHolder) holder).recyclerView,context);
            ((StudentViewHolder) holder).recyclerView.setAdapter(adapter);

            ((StudentViewHolder) holder).student= singleStudent;
            ((StudentViewHolder) holder).context1= context;
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
        public Context context1;
        public TextView tvAddress,tvManager,tvNumber,tvNumber1,tvNumber2;
        public  RecyclerView recyclerView;
        public NKO student;
        public LinearLayoutManager mLayoutManager;
        public StudentViewHolder(View v) {
            super(v);

            tvName=(TextView) v.findViewById(R.id.tv_name_employment) ;
            tvAddress=(TextView) v.findViewById(R.id.tv_address_second) ;
            tvManager=(TextView) v.findViewById(R.id.tv_manager_employment) ;
            tvNumber=(TextView) v.findViewById(R.id.tv_phone_number_second) ;
            tvNumber1=(TextView) v.findViewById(R.id.tv_phone_number1_second) ;
            tvNumber2=(TextView) v.findViewById(R.id.tv_phone_number2_second) ;
            recyclerView=(RecyclerView) v.findViewById(R.id.nko_recyclerview);

            recyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(context1);

            // use a linear layout manager
            recyclerView.setLayoutManager(mLayoutManager);

            tvNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context1.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhone() , null)));
                }
            });
            tvNumber1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context1.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",student.getPhone1() , null)));
                }
            });
            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) context1.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", student.getAddress());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context1, R.string.toast_copy_to_buffer,Toast.LENGTH_SHORT).show();
                }
            });
            tvNumber2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                    emailIntent.setType("plain/text");
                    // Кому
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                            new String[] { tvNumber2.getText().toString() });

                    // Поехали!
                    context1.startActivity(Intent.createChooser(emailIntent,
                            "Отправка письма..."));
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