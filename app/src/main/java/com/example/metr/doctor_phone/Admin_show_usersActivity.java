package com.example.metr.doctor_phone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_show_usersActivity extends AppCompatActivity {

    ListView listView,listView1,listView2;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> mUsernames,mUsernames1,mUsernames2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_users);


        listView = (ListView) findViewById(R.id.list_view_id);
        listView1 = (ListView) findViewById(R.id.list_view_name);
        listView2 = (ListView) findViewById(R.id.list_view_type);
        mUsernames = new ArrayList<>();
        mUsernames1 = new ArrayList<>();
        mUsernames2 = new ArrayList<>();


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames1);
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames2);

        listView.setAdapter(arrayAdapter);
        listView1.setAdapter(arrayAdapter1);
        listView2.setAdapter(arrayAdapter2);



        mDatabaseReference = FirebaseDatabase.getInstance().getReference("USERS");//POSTS1//USERS
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserTemp upload = postSnapshot.getValue(UserTemp.class);
                    mUsernames.add(upload.getmE_mail());
                    mUsernames1.add(upload.getmName());
                    mUsernames2.add(upload.getmTypeUser());

                    }

                arrayAdapter.notifyDataSetChanged();
                arrayAdapter1.notifyDataSetChanged();
                arrayAdapter2.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Admin_show_usersActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
