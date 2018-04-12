package com.example.sayandeep.quizquotient.Acitivities;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sayandeep.quizquotient.Helper.CategoryViewHolder;
import com.example.sayandeep.quizquotient.Helper.Message;
import com.example.sayandeep.quizquotient.Interface.ItemClickListener;
import com.example.sayandeep.quizquotient.Objects.Category;
import com.example.sayandeep.quizquotient.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CategoryFragment extends Fragment {
    View myFragment;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;
    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }
    private void placeHolder()
    {
        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, Category model, int position) {
                viewHolder.categoryText.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.categoryImage);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Message.makeToastMessage(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey()),"");
                    }
                });
            }
        };
                adapter.notifyDataSetChanged();
                listCategory.setAdapter(adapter);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_category,container,false);
        listCategory=myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        listCategory.setLayoutManager(layoutManager);
        return myFragment;
    }
}