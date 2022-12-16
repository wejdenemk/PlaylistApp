package com.crud.integration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditAlbumActivity extends AppCompatActivity {

    private TextInputEditText albumName,albumPrice , albumSinger , albumImg , albumDesc , albumLink;
    private Button updateAlbumBtn, deleteAlbumBtn;
    private Spinner albumGenre;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String albumID;
    private AlbumRVModal albumRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_album);
        firebaseDatabase= FirebaseDatabase.getInstance();
        albumName = findViewById(R.id.idEdtAlbumName);
        albumPrice = findViewById(R.id.idEdtAlbumPrice);
        albumSinger = findViewById(R.id.idEdtSingerName);
        albumImg = findViewById(R.id.idEdtImageLink);
        albumDesc = findViewById(R.id.idEdtAlbumDesc);
        albumGenre = findViewById(R.id.idEdtAlbumGenre);
        albumLink = findViewById(R.id.idEdtAlbumLink);
        updateAlbumBtn = findViewById(R.id.idbtnUpdateAlbum);
        deleteAlbumBtn = findViewById(R.id.idbtnDeleteAlbum);
        loadingPB = findViewById(R.id.idPBL);
        albumRVModal = getIntent().getParcelableExtra("album");
        if(albumRVModal!=null){
            albumName.setText(albumRVModal.getAlbumName());
            albumPrice.setText(albumRVModal.getAlbumPrice());
            albumSinger.setText(albumRVModal.getAlbumSinger());
            albumImg.setText(albumRVModal.getAlbumImg());
            albumDesc.setText(albumRVModal.getAlbumDesc());

            albumLink.setText(albumRVModal.getAlbumLink());
            albumID = albumRVModal.getAlbumID();



        }

        databaseReference = firebaseDatabase.getReference("Albums").child(albumID);
        updateAlbumBtn.setOnClickListener(new View.OnClickListener() {
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

                Map<String, Object> map = new HashMap<>();
                map.put("albumName",albumName);
                map.put("albumDesc",albumDesc);
                map.put("albumPrice",albumPrice);
                map.put("albumSinger",albumSinger);
                map.put("albumGenre",albumGenre);
                map.put("albumImg",albumImg);
                map.put("albumID",albumID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);


                            DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Albums").child(albumID);
                            AlbumRVModal album = new AlbumRVModal(name, desc,price,singer,image,genre,link,albumID);
                            DbRef.setValue(album);



                        Toast.makeText(EditAlbumActivity.this, "Course Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditAlbumActivity.this, ListAlbums.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditAlbumActivity.this, "Fail to update", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        deleteAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlbum();
            }


        });

    }

    private void deleteAlbum(){
        databaseReference.removeValue();
        Toast.makeText(this, "Album deleted ..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditAlbumActivity.this ,  ListAlbums.class));
    }
}