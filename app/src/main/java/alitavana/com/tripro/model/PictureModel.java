package alitavana.com.tripro.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ali Tavana on 07/05/2017.
 */

public class PictureModel implements Parcelable {
    String imagePath, imagePathBig, imageInformation, authorName, authorImage, id;

    public PictureModel() {
    }

    public PictureModel(String imagePath, String imagePathBig, String imageInformation, String authorName, String authorImage, String id) {
        this.imagePath = imagePath;
        this.imagePathBig = imagePathBig;
        this.imageInformation = imageInformation;
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.id = id;
    }

    public PictureModel (Parcel in)
    {
        //Log.d (TAG, "parcel in");
        imagePath = in.readString ();
        imagePathBig = in.readString ();
        imageInformation = in.readString ();
        authorName = in.readString ();
        authorImage = in.readString ();
        id = in.readString ();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePathBig() {
        return imagePathBig;
    }

    public void setImagePathBig(String imagePathBig) {
        this.imagePathBig = imagePathBig;
    }

    public String getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(String imageInformation) {
        this.imageInformation = imageInformation;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Parcelable.Creator<PictureModel> CREATOR
            = new Parcelable.Creator<PictureModel>()
    {
        public PictureModel createFromParcel(Parcel in)
        {
            //Log.d (TAG, "createFromParcel()");
            return new PictureModel(in);
        }

        public PictureModel[] newArray (int size)
        {
            //Log.d (TAG, "createFromParcel() newArray ");
            return new PictureModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString (imagePath);
        parcel.writeString (imagePathBig);
        parcel.writeString (imageInformation);
        parcel.writeString (authorName);
        parcel.writeString (authorImage);
        parcel.writeString (id);
    }
}
