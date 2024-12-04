package com.example.metr.doctor_phone;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Client_Add_PostActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__add__post);


        mButtonChooseImage=(Button)findViewById(R.id.button_choose_file);
        mButtonUpload=(Button)findViewById(R.id.Add_post_page);

      //  mTextViewShowUploads=(TextView)findViewById(R.id.text_view_show_uploads);
        text_title=(EditText)findViewById(R.id.client_post_title);
        text_describtion=(EditText)findViewById(R.id.client_post_description);


        mImageView=(ImageView) findViewById(R.id.image_view);

        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);

        mStorageRef= FirebaseStorage.getInstance().getReference("POSTS1");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("POSTS1");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUploadTask !=null&& mUploadTask.isInProgress()){
                    Toast.makeText(Client_Add_PostActivity.this, "Upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    UploadFile();

                }
            }
        });



    }

    private void openFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null){
            mImageUri= data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
            //mImageView.setImageURI(mImageUri);

        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void UploadFile(){
        if(mImageUri  !=null){

            StorageReference fileReference=mStorageRef.child(System.currentTimeMillis()+
                    "."+getFileExtension(mImageUri));

            mUploadTask=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);

                                }
                            },500);

                            String PhotoPath;
                            Toast.makeText(Client_Add_PostActivity.this,"Uploaded Successfuly image ",Toast.LENGTH_SHORT).show();

                            Task<Uri> task=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String PhotoPath=uri.toString();

                                    String name="Ahmed";
                                    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                                    String Imgae_profile="image_profile_uri";
                                    List<CommentTemp> Comments=null;
                                    Upload upload=new Upload(name, PhotoPath,text_title.getText().toString().trim(),
                                            text_describtion.getText().toString().trim(),currentDateTimeString,Imgae_profile,Comments);
                                    String UploadId=mDatabaseReference.push().getKey();
                                    mDatabaseReference.child(UploadId).setValue(upload);

                                   /* String name="Ahmed";
                                    String Data=java.text.DateFormat.getDateTimeInstance().format(new Date());//"15/11/1996,10:2:10";
                                    String Tiltle=text_title.getText().toString().trim();
                                    String desc= text_describtion.getText().toString().trim();
                                    String image_profile="https://firebasestorage.googleapis.com/v0/b/dr-phone-5d014.appspot.com/o/POSTS1%2F1585089345679.jpg?alt=media&token=3eba49bf-ff1e-48e5-a08a-b1451a059940";

                                    HashMap<String , Object> UserDataMap =new HashMap<>();
                                    UserDataMap.put("name",name);
                                    UserDataMap.put("Data",Data);
                                    UserDataMap.put("Tiltle",Tiltle);
                                    UserDataMap.put("desc",desc);
                                    UserDataMap.put("image_profile",image_profile);
                                    UserDataMap.put("PhotoPath",PhotoPath);
                                    String UploadId=mDatabaseReference.push().getKey();


                                    mDatabaseReference.child(UploadId).updateChildren(UserDataMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(Client_Add_PostActivity.this,"Congratulation, your account created sucessufully",Toast.LENGTH_SHORT).show();

                                                    }else {
                                                        Toast.makeText(Client_Add_PostActivity.this,"Network Error please try again after some times...",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
*/
                                    Toast.makeText(Client_Add_PostActivity.this,"*** Uploaded Successfuly *** ",Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Client_Add_PostActivity.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double Progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) Progress);
                        }
                    });
        }else{
            Toast.makeText(Client_Add_PostActivity.this,"No File selected" ,Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenImagesActivity(){
        Intent intent=new Intent(Client_Add_PostActivity.this,Client_HomeActivity.class);
        startActivity(intent);
    }
}
