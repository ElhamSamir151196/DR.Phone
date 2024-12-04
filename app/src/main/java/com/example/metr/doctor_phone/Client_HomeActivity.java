
package com.example.metr.doctor_phone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Client_HomeActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener{

private Button Add_post;

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorgae;
     String mail1="null";
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__home);

        final String mail=getIntent().getStringExtra("mail");
        mail1=mail;
        Add_post=(Button)findViewById(R.id.add_post);
        Add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Client_HomeActivity.this,Client_Add_PostActivity.class);
                startActivity(intent);
            }
        });

        /***********************************************************/

        mRecyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle=(ProgressBar) findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(Client_HomeActivity.this,mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(Client_HomeActivity.this);

        mStorgae= FirebaseStorage.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference("POSTS1");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload=postSnapshot.getValue(Upload.class);
                    upload.setmKey(postSnapshot.getKey());

                   /* String name= dataSnapshot.child("name").getValue(String.class);
                    String Data= dataSnapshot.child("Data").getValue(String.class);
                    String Tiltle= dataSnapshot.child("Tiltle").getValue(String.class);
                    String desc= dataSnapshot.child("desc").getValue(String.class);
                    String image_profile= dataSnapshot.child("image_profile").getValue(String.class);
                    String PhotoPath= dataSnapshot.child("PhotoPath").getValue(String.class);
                    Upload upload1=new Upload(name,PhotoPath,Tiltle,desc,Data,image_profile);*/
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Client_HomeActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);

            }
        });



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back_arrow:
                Intent intent=new Intent(Client_HomeActivity.this,MainActivity.class);
                intent.putExtra("mail",mail1);
                startActivity(intent);
                return true;
            case R.id.view_profile:
                Intent intent1=new Intent(Client_HomeActivity.this,Client_View_ProfileActivity.class);
                intent1.putExtra("mail",mail1);
                startActivity(intent1);
                return true;
            case R.id.profile_cst:
                Intent intent2=new Intent(Client_HomeActivity.this,Client_Edit_ProfileActivity.class);
                intent2.putExtra("mail",mail1);
                startActivity(intent2);
                return true;
                default:
                    return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(Client_HomeActivity.this,"normal click at position: "+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverCliick(int position) {

        Toast.makeText(Client_HomeActivity.this,"add comment click at position: "+position,Toast.LENGTH_SHORT).show();
        Upload SelectItem=mUploads.get(position);
        final String SelectedKey=SelectItem.getmKey();
        Intent intent=new Intent(Client_HomeActivity.this,  ClientAddComment.class);
        intent.putExtra("key",SelectedKey);
        intent.putExtra("type","Client");
        startActivity(intent);


    }

    @Override
    public void onDeleteClick(int position) {
        //Toast.makeText(ImagesActivity.this,"normal click at position: "+position,Toast.LENGTH_SHORT).show();
        Upload SelectItem=mUploads.get(position);
        final String SelectedKey=SelectItem.getmKey();
        StorageReference imageRef=mStorgae.getReferenceFromUrl(SelectItem.getImageUri());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                mDatabaseReference.child(SelectedKey).removeValue();
                Toast.makeText(Client_HomeActivity.this,"Item Deleted" ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDBListener);
    }
}
