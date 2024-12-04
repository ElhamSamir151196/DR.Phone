package com.example.metr.doctor_phone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_HomeActivity extends AppCompatActivity {

    Button Show_details_btn,Show_all_btn,Delete_btn,Update_btn,Change_data_btn;
    EditText inputID;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        Show_all_btn=(Button)findViewById(R.id.admin_users_show_all);
        Show_details_btn=(Button)findViewById(R.id.admin_users_show_details_all);
        Delete_btn=(Button)findViewById(R.id.admin_users_Delete_all);
        Update_btn=(Button)findViewById(R.id.admin_users_update_all);
        Change_data_btn=(Button)findViewById(R.id.admin_change_profile_all);
        final String mail=getIntent().getStringExtra("mail");


        Show_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Admin_HomeActivity.this,Admin_show_usersActivity.class);
                startActivity(intent);
            }
        });


        Show_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Admin_HomeActivity.this);
                builder.setTitle("Personal Details");
                builder.setIcon(R.drawable.ic_person_black_24dp);
                builder.setMessage("Please fill this to complete");
                inputID = new EditText(Admin_HomeActivity.this);
                builder.setView(inputID);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String txt=inputID.getText().toString();
                        Toast.makeText(Admin_HomeActivity.this,txt,Toast.LENGTH_SHORT).show();

                        /***********************************/


                        mDatabaseReference= FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean find = false;
                                String e = txt;
                                String mail="null";
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                                    if (upload.getmE_mail().equals(e)) {
                                        mail=upload.getmE_mail();
                                        find = true;
                                        break;
                                    }

                                }

                                if (find == false) {

                                    Toast.makeText(Admin_HomeActivity.this, "user doesn't exist  ", Toast.LENGTH_SHORT).show();

                                } else {

                                        Intent intent=new Intent(Admin_HomeActivity.this,Admin_show_users_detailsActivity.class);
                                        intent.putExtra("Email",txt);
                                        Toast.makeText(Admin_HomeActivity.this,"txt ="+txt+"  , mail = "+mail,Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(Admin_HomeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(Admin_HomeActivity.this,"Dismiss",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });


                AlertDialog ad=builder.create();
                ad.show();


            }
        });


        Delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Admin_HomeActivity.this);
                builder.setTitle("Personal Details");
                builder.setIcon(R.drawable.ic_person_black_24dp);
                builder.setMessage("Please fill this to complete");
                inputID = new EditText(Admin_HomeActivity.this);
                builder.setView(inputID);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     final String txt=inputID.getText().toString();
                        Toast.makeText(Admin_HomeActivity.this,txt,Toast.LENGTH_SHORT).show();

                        /***********************************/


                        mDatabaseReference= FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean find = false;
                                String e = txt;
                                String type = "null";
                                String mail="null";
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                                    if (upload.getmE_mail().equals(e)) {
                                        type = upload.getmTypeUser();
                                        mail=upload.getmE_mail();
                                        find = true;
                                        break;
                                    }

                                }

                                if (find == false) {

                                    Toast.makeText(Admin_HomeActivity.this, "user doesn't exist  ", Toast.LENGTH_SHORT).show();

                                } else {

                                    if(type.equals("Admin")){
                                        Toast.makeText(Admin_HomeActivity.this, "can't delete Admin  ", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Intent intent=new Intent(Admin_HomeActivity.this,Admin_Delete_UserActivity.class);
                                        intent.putExtra("Email",txt);
                                        Toast.makeText(Admin_HomeActivity.this,"txt ="+txt+"  , mail = "+mail,Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(Admin_HomeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  Toast.makeText(Admin_HomeActivity.this,"Dismiss",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });


                AlertDialog ad=builder.create();
                ad.show();
            }
        });

        Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Admin_HomeActivity.this);
                builder.setTitle("Personal Details");
                builder.setIcon(R.drawable.ic_person_black_24dp);
                builder.setMessage("Please fill this to complete");
                inputID = new EditText(Admin_HomeActivity.this);
                builder.setView(inputID);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String txt=inputID.getText().toString();
                        Toast.makeText(Admin_HomeActivity.this,txt,Toast.LENGTH_SHORT).show();

                        /***********************************/


                        mDatabaseReference= FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean find = false;
                                String e = txt;
                                String type = "null";
                                String mail="null";
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                                    if (upload.getmE_mail().equals(e)) {
                                        type = upload.getmTypeUser();
                                        mail=upload.getmE_mail();
                                        find = true;
                                        break;
                                    }

                                }

                                if (find == false) {

                                    Toast.makeText(Admin_HomeActivity.this, "user doesn't exist  ", Toast.LENGTH_SHORT).show();

                                } else {

                                    if(type.equals("Admin")){
                                        Toast.makeText(Admin_HomeActivity.this, "can't chnage Admin  from here", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Intent intent=new Intent(Admin_HomeActivity.this,Admin_Update_usersActivity.class);
                                        intent.putExtra("Email",txt);
                                        Toast.makeText(Admin_HomeActivity.this,"txt ="+txt+"  , mail = "+mail,Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(Admin_HomeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });


                AlertDialog ad=builder.create();
                ad.show();


            }
        });

        Change_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Admin_HomeActivity.this,Admin_change_dataActivity.class);
                intent.putExtra("Email",mail);
                startActivity(intent);
            }
        });


    }
}
