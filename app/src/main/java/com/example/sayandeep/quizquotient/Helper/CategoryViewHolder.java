package com.example.sayandeep.quizquotient.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sayandeep.quizquotient.Interface.ItemClickListener;
import com.example.sayandeep.quizquotient.R;

/**
 * Created by Sayandeep on 12-04-2018.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements ItemClickListener, View.OnClickListener {
    public ImageView categoryImage;
    public TextView categoryText;
    private ItemClickListener itemClickListener;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        categoryImage=itemView.findViewById(R.id.category_image);
        categoryText=itemView.findViewById(R.id.category_text);
        itemView.setOnClickListener( this);
    }

    public void setItemClickListener(ItemClickListener itemClickListner) {
        this.itemClickListener = itemClickListner;
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
