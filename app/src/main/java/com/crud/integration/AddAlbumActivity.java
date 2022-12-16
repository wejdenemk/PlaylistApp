package com.crud.integration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddAlbumActivity extends AppCompatActivity {

   private TextInputEditText albumName,albumPrice , albumSinger , albumImg , albumDesc,albumLink;
   private Spinner albumGenre;
   private Button addAlbumBtn;
   private ProgressBar loadingPB;
   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference databaseReference;
   private String albumID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
         albumName = findViewById(R.id.idEdtAlbumName);
        albumPrice = findViewById(R.id.idEdtAlbumPrice);
        albumSinger = findViewById(R.id.idEdtSingerName);
        albumImg = findViewById(R.id.idEdtImageLink);
        albumDesc = findViewById(R.id.idEdtAlbumDesc);
        albumGenre = findViewById(R.id.idEdtAlbumGenre);
        albumLink = findViewById(R.id.idEdtAlbumLink);
        addAlbumBtn = findViewById(R.id.idbtnAddAlbum);
        loadingPB = findViewById(R.id.idPBL);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Albums");

        addAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String name = albumName.getText().toString();
                String price = albumPrice.getText().toString();
                String singer = albumSinger.getText().toString();
                String image = albumImg.getText().toString();
                String desc = albumDesc.getText().toString();
                String genre = albumGenre.getSelectedItem().toString();
                String link = albumLink.getText().toString();
                albumID = name;
                AlbumRVModal albumRVModal = new AlbumRVModal(name ,desc, price, singer, image, genre,link, albumID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(albumID).setValue(albumRVModal);
                        Toast.makeText(AddAlbumActivity.this, "Album Added ..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddAlbumActivity.this, ListAlbums.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddAlbumActivity.this, "Error is"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}