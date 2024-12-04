package com.example.metr.doctor_phone;

import android.app.DatePickerDialog;
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

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button  loginButton;
    EditText text_email, text_password;
    private DatabaseReference RootRef;
   // private List<Upload> mUploads;
   private DatabaseReference mDatabaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_email = (EditText) findViewById(R.id.login_email_input);
        text_password = (EditText) findViewById(R.id.login_password_input);
        loginButton = (Button) findViewById(R.id.login_btn);
        final String E_mail = text_email.toString().trim();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(LoginActivity.this, "Before DB bbbbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();

                mDatabaseReference= FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
              //  Toast.makeText(LoginActivity.this,"email = "+text_email.getText().toString(),Toast.LENGTH_SHORT).show();

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        boolean find = false;
                        boolean wrong_password=false;
                        String type="null";
                        int i=0;
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                            UserTemp upload = postSnapshot.getValue(UserTemp.class);
                            i++;
                            if(upload.getmE_mail().equals(text_email.getText().toString())){

                                if(upload.getmPassword().equals(text_password.getText().toString())){
                                    type=upload.getmTypeUser();
                                    if(upload.getmTypeUser().equals("Admin")){
                                       // type="Admin";
                                        Intent intent=new Intent(LoginActivity.this,Admin_HomeActivity.class);
                                        intent.putExtra("mail",upload.getmE_mail());
                                        startActivity(intent);
                                    }else if(upload.getmTypeUser().equals("Technical")){
                                        //type="Technical";
                                        Intent intent=new Intent(LoginActivity.this,Technical_HomeActivity.class);
                                        intent.putExtra("mail",upload.getmE_mail());
                                        startActivity(intent);
                                    }else{
                                       // type="Client";
                                        Intent intent=new Intent(LoginActivity.this,Client_HomeActivity.class);
                                        intent.putExtra("mail",upload.getmE_mail());
                                        startActivity(intent);
                                    }

                                }else{
                                    wrong_password=true;
                                }
                                find=true;
                                break;
                            }
                        }
                        if(find== false){

                            Toast.makeText(LoginActivity.this,"user doesn't exist  ",Toast.LENGTH_SHORT).show();

                        }else{
                            if(wrong_password == true){
                                Toast.makeText(LoginActivity.this,"wrong password", Toast.LENGTH_SHORT).show();
                            }else{
                               // Toast.makeText(LoginActivity.this,"type = "+type, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(LoginActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });


            }

        });
    }

}
