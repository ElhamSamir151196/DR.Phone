package com.example.metr.doctor_phone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class CommentAdapter   extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<CommentTemp> mCommentTemp;

    public CommentAdapter(Context context, List<CommentTemp> CommentTemp){
        mContext=context;
        mCommentTemp=CommentTemp;

    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item,viewGroup,false);
        return new CommentAdapter.CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        CommentTemp comment=mCommentTemp.get(i);
        commentViewHolder.textViewName.setText(comment.getName_text());
        commentViewHolder.textViewDate.setText(comment.getDate_text());
        commentViewHolder.text_describtion.setText(comment.getDescription_text());
        Picasso.with(mContext)
                .load(comment.getImage_uri())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(commentViewHolder.imageView_profile);

/*        if(commentViewHolder.btn_accept_coment!=null){
            commentViewHolder.btn_accept_coment.setVisibility(View.INVISIBLE);

        }else{
            commentViewHolder.btn_accept_coment.setVisibility(View.VISIBLE);

        }
*/

    }



    @Override
    public int getItemCount() {
        return mCommentTemp.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewDate;
        public ImageView imageView_profile;
        public TextView text_describtion;
  //      public Button btn_accept_coment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_profile=itemView.findViewById(R.id.image_id_comment);
            textViewName=itemView.findViewById(R.id.text_name_comment);
            textViewDate=itemView.findViewById(R.id.text_date_comment);
            text_describtion=itemView.findViewById(R.id.text_describtion_comment);
    //        btn_accept_coment=itemView.findViewById(R.id.btn_accept_comment);
        }



    }


}
