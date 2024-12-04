package com.example.metr.doctor_phone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private OnItemClickListener mListener;
    private List<Upload> mUploads;
    private CommentAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private List<CommentTemp> mUploads_list;


    public ImageAdapter(Context context, List<Upload> uploads){
        mContext=context;
        mUploads=uploads;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item,viewGroup,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {

        Upload uploadCurrent=mUploads.get(i);
        imageViewHolder.textViewName.setText(uploadCurrent.getName());
        imageViewHolder.textViewDate.setText(uploadCurrent.getCurrentDateTimeString());
        imageViewHolder.text_title.setText(uploadCurrent.getText_title());
        imageViewHolder.text_describtion.setText(uploadCurrent.getText_describtion());
        String key=uploadCurrent.getmKey();

        if(FirebaseDatabase.getInstance().getReference("POSTS1").child(key).child("commnts")!=null){

            mUploads_list = new ArrayList<>();
            mAdapter = new CommentAdapter(mContext,mUploads_list);
            imageViewHolder.mRecyclerView.setAdapter(mAdapter);
            mDatabaseReference= FirebaseDatabase.getInstance().getReference("POSTS1").child(key).child("commnts");
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mUploads_list.clear();
                    for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                       CommentTemp upload=postSnapshot.getValue(CommentTemp.class);
                        upload.setmKey(postSnapshot.getKey());
                         mUploads_list.add(upload);
                   }
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(mContext,"problemmm"+mUploads_list.size(),Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(mContext,"problemmm11111111111111111111111111******",Toast.LENGTH_SHORT).show();

                }
            });

        }else{
Toast.makeText(mContext,"empty",Toast.LENGTH_SHORT).show();
        }

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView_post);

        Picasso.with(mContext)
                .load(uploadCurrent.getmImageUri_profile())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView_profile);


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener{

        public TextView textViewName;
        public TextView textViewDate;
        public ImageView imageView_profile;
        public TextView text_describtion;
        public TextView text_title;
        public ImageView imageView_post;
        public List<CommentTemp> Comments_list;
        public Button mButtonAddComment;
        public RecyclerView mRecyclerView;


        private Button btn_add_coment;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_profile=itemView.findViewById(R.id.image_id);
            textViewName=itemView.findViewById(R.id.text_date_name);
            textViewDate=itemView.findViewById(R.id.text_date_date);
            text_describtion=itemView.findViewById(R.id.text_describtion);
            text_title=itemView.findViewById(R.id.text_title);
            imageView_post=itemView.findViewById(R.id.image_view_upload);
            mButtonAddComment=itemView.findViewById(R.id.btn_add_coment);
            mRecyclerView =itemView.findViewById(R.id.recycler_view1);
            mRecyclerView.setHasFixedSize(true);
            //mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


            itemView.setOnClickListener(ImageViewHolder.this);
            itemView.setOnCreateContextMenuListener(ImageViewHolder.this);
            mButtonAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onWhatEverCliick(position);
                        }
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if (position!= RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatEver=menu.add(Menu.NONE,1,1,"Do Whatever");
            MenuItem Delete=menu.add(Menu.NONE,2,2,"Delete");

            doWhatEver.setOnMenuItemClickListener(ImageViewHolder.this);
            Delete.setOnMenuItemClickListener(ImageViewHolder.this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if(mListener != null){
                int position = getAdapterPosition();
                if (position!= RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhatEverCliick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;

                    }
                }
            }

            return false;
        }


    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhatEverCliick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener=listener;
    }


}
