package com.example.metr.doctor_phone;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;

public class ClientAddComment extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads,text_title,text_describtion;
    private EditText mEditTextFileName;
    private ImageView mImageView,image_profile;
    private ProgressBar mProgressBar;

    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseReference;

    private StorageTask mUploadTask;
    String UploadId;

    RadioGroup checked_type_user;
    RadioButton check_user_button;

    String type,type_id;
    boolean exist=false;
    EditText text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_comment);

        Intent i=getIntent();
        UploadId=i.getStringExtra("key");
        type_id=i.getStringExtra("type");


        mButtonUpload=(Button)findViewById(R.id.btn_add_comment_done);
        text_describtion=(EditText)findViewById(R.id.comment_text);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        text=(EditText)findViewById(R.id.comment_text);
        mStorageRef= FirebaseStorage.getInstance().getReference("POSTS1");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("POSTS1");


        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUploadTask !=null&& mUploadTask.isInProgress()){
                    Toast.makeText(ClientAddComment.this, "Upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    if(type_id.equals("Technical")){
                        checked_type_user =(RadioGroup)findViewById(R.id.user_choose);
                        int selectedID=checked_type_user.getCheckedRadioButtonId();
                        check_user_button=(RadioButton)findViewById(selectedID);



                        if(selectedID==R.id.cst_suggestion){

                            exist=false;
                        } else {
                            exist=true;
                        }
                    }else{
                        exist=false;
                        //client
                        checked_type_user.setVisibility(View.INVISIBLE);
                        RadioButton r1=(RadioButton)findViewById(R.id.cst_suggestion);
                        RadioButton r2=(RadioButton)findViewById(R.id.cst_solution);
                        r1.setVisibility(View.INVISIBLE);
                        r2.setVisibility(View.INVISIBLE);


                    }

                    //UploadFile(UploadId);
                    String textt=text.getText().toString();
                    validatePhoneNumber(UploadId, textt, exist);

                }
            }
        });



    }

    private void validatePhoneNumber(final String key, final String Comment_text, final boolean exist) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("POSTS1").child(key).exists())){

                    String name="Aya";
                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                    String Imgae_profile="image_profile_uri";


                    CommentTemp upload=new CommentTemp(name,currentDateTimeString,Comment_text,Imgae_profile,exist);
                    //String UploadId=mDatabaseReference.push().getKey();
                   // mDatabaseReference.child(UploadId).setValue(upload);
                    String UploadId=mDatabaseReference.push().getKey();
                    RootRef.child("POSTS1").child(key).child("commnts").child(UploadId).setValue(upload)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ClientAddComment.this,"your comment uploaded sucessfully",Toast.LENGTH_SHORT).show();

                                    }else {
                                        Toast.makeText(ClientAddComment.this,"Network Error please try again after some times...",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(ClientAddComment.this,"problem in key. ",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void UploadFile(String UploadId){

       // Toast.makeText(ClientAddComment.this,"Uploaded Successfuly comment ",Toast.LENGTH_SHORT).show();
       // mDatabaseReference.child("POSTS1").child(UploadId).child("Comments").setValue("true");
        //Toast.makeText(ClientAddComment.this,"*** Uploaded Successfuly *** ",Toast.LENGTH_SHORT).show();

        }

}
