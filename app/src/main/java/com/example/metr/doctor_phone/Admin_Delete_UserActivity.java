package com.example.metr.doctor_phone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Admin_Delete_UserActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    TextView name,address,password,phone,email;
    Button Delete;
    private FirebaseStorage mStorgae;
    int position;
    UserTemp u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__delete__user);
        final String Email=getIntent().getStringExtra("Email");

         name=(TextView) findViewById(R.id.admin_delete_name_edit);
         password=(TextView)findViewById(R.id.admin_delete_password_edit);
         address=(TextView)findViewById(R.id.admin_delete_address_edit);
         phone=(TextView)findViewById(R.id.admin_market_phone_edit);
         email=(TextView)findViewById(R.id.admin_delete_e_mail_edit);
         Delete=(Button)findViewById(R.id.admin_delete_btn);
        mStorgae= FirebaseStorage.getInstance();


        /*****************************************/

        mDatabaseReference= FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean done=false;
                String e = Email;
                int i=0;
                u=new UserTemp();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                    if (upload.getmE_mail().equals(e)) {

                        position=i;
                        u=postSnapshot.getValue(UserTemp.class);
                        done=true;
                        name.setText(upload.getmName());
                        password.setText(upload.getmPassword());
                        address.setText(upload.getmAddress());
                        phone.setText(upload.getmPhoneNumber());
                        email.setText(upload.getmE_mail());
                        break;

                    }
                    i++;
                }
                if(done == true ){
                    Toast.makeText(Admin_Delete_UserActivity.this, "password "+u.getmPassword(),Toast.LENGTH_SHORT).show();


                }else{
                   Toast.makeText(Admin_Delete_UserActivity.this,"done = false * problem "+Email,Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Admin_Delete_UserActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**********************************************/



                UserTemp SelectItem=u;
                final String SelectedKey=SelectItem.getmE_mail();
                StorageReference imageRef=mStorgae.getReferenceFromUrl(SelectItem.getmImageUri());
              //  Toast.makeText(Admin_Delete_UserActivity.this,"image uri = "+SelectItem.getmImageUri(),Toast.LENGTH_SHORT).show();

                imageRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mDatabaseReference.child(SelectedKey).removeValue();
                        Toast.makeText(Admin_Delete_UserActivity.this,"Item Deleted" ,Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_Delete_UserActivity.this,"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });








                /****************************************/



               // Toast.makeText(Admin_Delete_UserActivity.this,"Delete Done",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Admin_Delete_UserActivity.this,Admin_HomeActivity.class);
                startActivity(intent);
            }
        });
    }


}
