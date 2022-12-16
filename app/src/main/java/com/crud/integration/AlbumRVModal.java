package com.crud.integration;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumRVModal implements Parcelable {

    private String albumName;
    private String albumDesc;
    private String albumPrice;
    private String albumSinger;
    private String albumImg;
    private String albumGenre;
    private String albumID;
    private String albumLink;

    protected AlbumRVModal(Parcel in) {
        albumName = in.readString();
        albumDesc = in.readString();
        albumPrice = in.readString();
        albumSinger = in.readString();
        albumImg = in.readString();
        albumGenre = in.readString();
        albumID = in.readString();
        albumLink = in.readString();
    }

    public AlbumRVModal() {

    }

    public static final Creator<AlbumRVModal> CREATOR = new Creator<AlbumRVModal>() {
        @Override
        public AlbumRVModal createFromParcel(Parcel in) {
            return new AlbumRVModal(in);
        }

        @Override
        public AlbumRVModal[] newArray(int size) {
            return new AlbumRVModal[size];
        }
    };

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public String getAlbumPrice() {
        return albumPrice;
    }

    public void setAlbumPrice(String albumPrice) {
        this.albumPrice = albumPrice;
    }

    public String getAlbumSinger() {
        return albumSinger;
    }

    public void setAlbumSinger(String albumSinger) {
        this.albumSinger = albumSinger;
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public String getAlbumGenre() {
        return albumGenre;
    }

    public void setAlbumGenre(String albumGenre) {
        this.albumGenre = albumGenre;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public AlbumRVModal(String albumName, String albumDesc, String albumPrice, String albumSinger, String albumImg, String albumGenre,String albumLink, String albumID) {
        this.albumName = albumName;
        this.albumDesc = albumDesc;
        this.albumPrice = albumPrice;
        this.albumSinger = albumSinger;
        this.albumImg = albumImg;
        this.albumGenre = albumGenre;
        this.albumLink = albumLink;
        this.albumID = albumID;
    }

    public String getAlbumLink() {
        return albumLink;
    }

    public void setAlbumLink(String albumLink) {
        this.albumLink = albumLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(albumName);
        parcel.writeString(albumDesc);
        parcel.writeString(albumPrice);
        parcel.writeString(albumSinger);
        parcel.writeString(albumImg);
        parcel.writeString(albumGenre);
        parcel.writeString(albumID);
        parcel.writeString(albumLink);
    }
}
