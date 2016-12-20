package com.example.admin.pagination.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.admin.pagination.R;
import com.example.admin.pagination.Serializables.Istories;

import java.util.ArrayList;


public class RVLawsAndRightsAdapter extends RecyclerView.Adapter<RVLawsAndRightsAdapter.PersonViewHolder> {
    Context context;
    Istories istories;

    boolean status;
    public class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView nickname;
        TextView text;

        String TAG="TAG";

        PersonViewHolder( View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_forum_laws_r);
            nickname = (TextView)itemView.findViewById(R.id.tv_nick_name_forum_laws_r);
            text = (TextView)itemView.findViewById(R.id.tv_text_forum_laws_r);






        }


    }

   ArrayList<Istories> listVse;

    public RVLawsAndRightsAdapter(Context context, ArrayList<Istories> listVse){
        this.listVse = listVse;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_laws_and_rights, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);



        return pvh;
    }



    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        istories=listVse.get(i);
        personViewHolder.nickname.setText("("+istories.getNickName()+")");
        personViewHolder.text.setText(istories.getText());


    }

    @Override
    public int getItemCount() {
        return listVse.size();
    }
}
