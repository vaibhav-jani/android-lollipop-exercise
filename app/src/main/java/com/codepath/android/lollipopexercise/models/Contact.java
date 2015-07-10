package com.codepath.android.lollipopexercise.models;

import android.os.Parcel;
import android.os.Parcelable;

// Container class to hold Contact information.
public class Contact implements Parcelable {
    public int mId;
    private String mName;
    private int mThumbnailDrawable;
    private String mNumber;

    public Contact(int id, String name, int thumbnailDrawable, String number) {
        mId = id;
        mName = name;
        mThumbnailDrawable = thumbnailDrawable;
        mNumber = number;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getThumbnailDrawable() {
        return mThumbnailDrawable;
    }

    public String getNumber() {
        return mNumber;
    }

    protected Contact(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mThumbnailDrawable = in.readInt();
        mNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mThumbnailDrawable);
        dest.writeString(mNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
