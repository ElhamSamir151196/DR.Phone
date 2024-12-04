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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Update_usersActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    EditText name, address, password, phone, email;
    Button Update;
    int position;
    UserTemp u;
    private DatabaseReference RootRef;
    boolean done1=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__update_users);

        final String Email = getIntent().getStringExtra("Email");

        name = (EditText) findViewById(R.id.admin_change_name_edit1);
        password = (EditText) findViewById(R.id.admin_change_admin_name_edit1);
        address = (EditText) findViewById(R.id.admin_change_password_edit1);
        phone = (EditText) findViewById(R.id.admin_change_describtion1_edit1);
        email = (EditText) findViewById(R.id.admin_change_e_mail_edit1);
        Update = (Button) findViewById(R.id.admin_change_update_btn1);


        /*****************************************/

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String e = Email;
                int i=0;
                boolean done=false;

                u=new UserTemp();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                    upload.setmKey(postSnapshot.getKey());

                    if (upload.getmKey().equals(e)) {

                        position=i;
                        u=postSnapshot.getValue(UserTemp.class);
                        done=true;
                        done1=true;
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
                    done1=true;
                    // Toast.makeText(Admin_change_dataActivity.this, "password "+u.getmPassword(),Toast.LENGTH_SHORT).show();


                }else{

                    done1=false;
                    //Toast.makeText(Admin_Update_usersActivity.this,"done = false * problem "+Email,Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Admin_Update_usersActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**********************************************/



                RootRef= FirebaseDatabase.getInstance().getReference("USERS");
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        UserTemp upload=new UserTemp(name.getText().toString() , u.getmImageUri(), password.getText().toString(),
                                address.getText().toString(), email.getText().toString(), u.getmCountry(), u.getmGender(), u.getmTypeUser()
                                , u.getMday(), u.getmMonth(), u.getmYear(),phone.getText().toString());


                        if(!u.getmE_mail().equals(email.getText().toString()) && done1==true){
                            mDatabaseReference.child(u.getmE_mail()).removeValue();
                        }

                        RootRef.child(email.getText().toString()).setValue(upload);
                        Toast.makeText(Admin_Update_usersActivity.this,"Change Done",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Admin_Update_usersActivity.this,Admin_HomeActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

}
