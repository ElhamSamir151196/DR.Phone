package com.example.metr.doctor_phone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RegsisterActivity extends AppCompatActivity {
    private TextView mTextView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG="RegsisterActivity";
    Button  loginButton,ImageButton;
    RadioGroup checked_type_user,checked_gender_user;
    RadioButton check_user_button,check_user_gender_button;
    ImageView image_profile;
    private  static final int PICK_IMAGE=100;
    Uri imageUri;
    int date_year, date_month, date_day;
    private DatabaseReference RootRef;
   // private DatabaseReference mDatabaseReference;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private ProgressBar mProgressBar;
    private UserTemp upload;


    EditText InputName,InputPhoneNumber,InputPassword,InputConfirmPassword,InputAddress, InputEmail,InputCountry;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsister);

        /*************************** Date **********************************/
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);

        mTextView=(TextView) findViewById(R.id.tvDate);
        //mDatabaseReference= FirebaseDatabase.getInstance().getReference("Uploads");


        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal= Calendar.getInstance();
                int Year =cal.get(Calendar.YEAR);
                int month =cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(
                        RegsisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, Year, month , day

                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet: date "+year+"/"+month+"/"+dayOfMonth);
                date_year=year;
                date_day=dayOfMonth;
                date_month=month;
            }
        };

        /////////////////////////////////////////////////////////////////////////
        InputName=(EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber=(EditText) findViewById(R.id.regesiter_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword=(EditText) findViewById(R.id.register_confirm_password_input);
        InputAddress=(EditText) findViewById(R.id.register_address_input);
        InputEmail=(EditText) findViewById(R.id.register_email_input);
        InputCountry=(EditText) findViewById(R.id.register_country_input);

        loadingBar=new ProgressDialog(this);
        loginButton=(Button)findViewById(R.id.register_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateAccount();

            }
        });

        /************************* Choose image*******************************/

        ImageButton=(Button) findViewById(R.id.Image_browse);

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               BrowseGallery();
            }
        });


    }

    private void BrowseGallery(){
        Intent gallary= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallary,PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK &&requestCode == PICK_IMAGE){

            imageUri=data.getData();
            image_profile=(ImageView)findViewById(R.id.Image_browse_view);
            image_profile.setImageURI(imageUri);
        }

    }


    private void CreateAccount() {
        String Name=InputName.getText().toString().trim();
        String PhoneNumber=InputPhoneNumber.getText().toString().trim();
        String Password=InputPassword.getText().toString().trim();
        String ConfirmPassword=InputConfirmPassword.getText().toString().trim();
        String Country=InputCountry.getText().toString().trim();
        String E_mail=InputEmail.getText().toString().trim();
        String Address=InputAddress.getText().toString().trim();
        String gender, type;
        ImageView image_prof;

        checked_type_user =(RadioGroup)findViewById(R.id.user_type_regsister);
        int selectedID=checked_type_user.getCheckedRadioButtonId();
        check_user_button=(RadioButton)findViewById(selectedID);

        checked_gender_user =(RadioGroup)findViewById(R.id.user_gender);
        int selectedID_gender=checked_gender_user.getCheckedRadioButtonId();
        check_user_gender_button=(RadioButton)findViewById(selectedID_gender);

        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"please write your name...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(PhoneNumber)){
            Toast.makeText(this,"please write your Phone number...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"please write your Password...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(ConfirmPassword)){
            Toast.makeText(this,"please write your confirmed Password...",Toast.LENGTH_SHORT).show();
        }else if(!Password.equals(ConfirmPassword)){
            Toast.makeText(this," your Password and confirmed password not equals...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Address)){
            Toast.makeText(this,"please write your Address...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Country)){
            Toast.makeText(this,"please write your Country...",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(E_mail)){
            Toast.makeText(this,"please write your E-mail...",Toast.LENGTH_SHORT).show();
        }else {

            if (image_profile == null){
                image_profile=(ImageView) findViewById(R.id.Image_browse_view);
                Integer image_id=R.drawable.profile;
                image_prof=(ImageView) findViewById(R.id.Image_browse_view);

                Uri uri=Uri.parse("android.resource://com.example.metr.doctor_phone/drawable/image_name");
                image_prof.setImageURI(uri);

            }else{
                image_prof=image_profile;

            }


            /********** type *************/
            if(selectedID==R.id.type_technical){

                type="Technical";
            } else {
                type="Client";
                //type="Admin";

            }


            /**************** gender ***************/
            if(selectedID_gender==R.id.cst_female){

                gender="Female";
            } else {

                gender="Male";
            }

            loadingBar.setTitle("CreateAccount");
            loadingBar.setMessage("please wait,while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateE_mail(Name,PhoneNumber,Password,E_mail,Address,Country,gender,type,image_prof,date_year,date_month,date_day);
        }



    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void validateE_mail(final String name, final String phoneNumber,final String password,final String E_mail,
                                final String Address,final String Country,
                                final String gender,final String type,final ImageView image_prof,final int date_year,
                                final int date_month,final int date_day) {


        RootRef= FirebaseDatabase.getInstance().getReference("USERS");
        mStorageRef= FirebaseStorage.getInstance().getReference("USERS");

        Toast.makeText(RegsisterActivity.this,"email "+E_mail,Toast.LENGTH_SHORT).show();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("USERS").child(E_mail).exists())){
                    StorageReference fileReference;

                    if(imageUri  !=null) {

                       fileReference = mStorageRef.child(System.currentTimeMillis() +
                                "." + getFileExtension(imageUri));
                    }else{
                        imageUri= Uri.parse("android.resource://com.example.metr.doctor_phone/drawable/profile") ;
                         fileReference = mStorageRef.child(System.currentTimeMillis() +
                                "." + getFileExtension(imageUri));
                    }

                    Toast.makeText(RegsisterActivity.this,"before put file ",Toast.LENGTH_SHORT).show();
                        mUploadTask=fileReference.putFile(imageUri)
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

                                        Task<Uri> task=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String PhotoPath=uri.toString();
                                                Toast.makeText(RegsisterActivity.this,"Uploaded Successfuly",
                                                        Toast.LENGTH_SHORT).show();
                                                upload=new UserTemp(name , PhotoPath, password, Address, E_mail,
                                                        Country, gender, type, date_day, date_month, date_year,phoneNumber);

                                                RootRef.child(E_mail).setValue(upload);
                                                loadingBar.dismiss();

                                            }
                                        });


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegsisterActivity.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegsisterActivity.this,"This "+E_mail+" already exist",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegsisterActivity.this,"please try again with anther E-mail ",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    public void create(MenuItem item){
        Intent intent1= new Intent(RegsisterActivity.this,LoginActivity.class);
        //intent.putExtra("EXTRA_TEXT","1");
        startActivity(intent1);


    }
}
