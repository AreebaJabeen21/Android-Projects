package com.example.complin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class complainAdapter extends RecyclerView.Adapter<complainAdapter.complainViewolder> {

    ArrayList<ModelClass> objectModelClasList;
    public complainAdapter(ArrayList<ModelClass>objectModelClasList){
        this.objectModelClasList = objectModelClasList;
    }
    @NonNull
    @Override
    public complainViewolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new complainViewolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.activity_my_complain,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull complainViewolder holder, int position) {
        ModelClass objectModelClass = objectModelClasList.get(position);
        complainViewolder.cattext.setText(objectModelClass.getCategory());
        complainViewolder.objectImgeView.setImageBitmap(objectModelClass.getImage());
    }



    @Override
    public int getItemCount() {
        return objectModelClasList.size();
    }

    public static class complainViewolder extends RecyclerView.ViewHolder{
        public static TextView cattext;

        public static ImageView objectImgeView;

    public complainViewolder(@NonNull View itemView) {
        super(itemView);
        objectImgeView=itemView.findViewById(R.id.show_img);
    }
}
}
