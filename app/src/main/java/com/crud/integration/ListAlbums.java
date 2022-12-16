package com.crud.integration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAlbums extends AppCompatActivity implements AlbumRVAdapter.AlbumClickInterface {

    private RecyclerView albumRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<AlbumRVModal> albumRVModalArrayList;
    private RelativeLayout bottonSheetRL;
    private AlbumRVAdapter albumRVAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_albums);
        albumRV = findViewById(R.id.idRVAlbums);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Albums");
        albumRVModalArrayList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        albumRVAdapter = new AlbumRVAdapter(albumRVModalArrayList, this, this);
        albumRV.setLayoutManager(new LinearLayoutManager(this));
        albumRV.setAdapter(albumRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListAlbums.this, AddAlbumActivity.class));
            }
        });
        getAllAlbums();
    }


    private void getAllAlbums() {

        albumRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                albumRVModalArrayList.add(snapshot.getValue(AlbumRVModal.class));
                albumRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                albumRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                albumRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                albumRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onAlbumClick(int position) {
        displayBottomSheet(albumRVModalArrayList.get(position));
    }

    private void displayBottomSheet(AlbumRVModal albumRVModal) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottonSheetRL= findViewById(R.id.idRLBSheett);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottonSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView albumName = layout.findViewById(R.id.idTVAlbumName);
        TextView albumDesc = layout.findViewById(R.id.idTVDesc);
        TextView albumSinger = layout.findViewById(R.id.idTVSinger);
        TextView albumGenre = layout.findViewById(R.id.idTVGenre);
        ImageView albumImg = layout.findViewById(R.id.idIVAlbum);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);


        albumName.setText(albumRVModal.getAlbumName());
        albumDesc.setText(albumRVModal.getAlbumDesc());
        albumSinger.setText("By " +albumRVModal.getAlbumSinger());
        albumGenre.setText(albumRVModal.getAlbumGenre());
        Picasso.get().load(albumRVModal.getAlbumImg()).into(albumImg);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListAlbums.this, EditAlbumActivity.class);
                i.putExtra("album", albumRVModal);
                startActivity(i);
            }
        });

        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(albumRVModal.getAlbumLink()));
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.idLogOut:
                Toast.makeText(this, "User Logged Out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(ListAlbums.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}