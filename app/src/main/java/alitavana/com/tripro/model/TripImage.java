package alitavana.com.tripro.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by eric on 5/29/17.
 */

public class TripImage implements Serializable, Parcelable {

    private String hash;
    private String downloadLink;
    private String name;
    private byte[] photoValue;

    public TripImage() {
    }

    public byte[] getPhotoValue() {
        return photoValue;
    }

    public void setPhotoValue(byte[] photoValue) {
        this.photoValue = photoValue;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString (hash);
        parcel.writeString (downloadLink);
        parcel.writeString (name);

        parcel.writeInt(photoValue.length);
        parcel.writeByteArray(photoValue);
    }

    public TripImage (Parcel in)
    {
        //Log.d (TAG, "parcel in");
        hash = in.readString ();
        downloadLink = in.readString ();
        name = in.readString ();

        photoValue = new byte[in.readInt()];
        in.readByteArray(photoValue);
    }

    public static final Parcelable.Creator<TripImage> CREATOR
            = new Parcelable.Creator<TripImage>()
    {
        public TripImage createFromParcel(Parcel in)
        {
            //Log.d (TAG, "createFromParcel()");
            return new TripImage(in);
        }

        public TripImage[] newArray (int size)
        {
            //Log.d (TAG, "createFromParcel() newArray ");
            return new TripImage[size];
        }
    };
}
