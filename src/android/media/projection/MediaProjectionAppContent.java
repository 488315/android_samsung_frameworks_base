package android.media.projection;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaProjectionAppContent implements Parcelable {
    public static final Parcelable.Creator<MediaProjectionAppContent> CREATOR = new Parcelable.Creator<MediaProjectionAppContent>() { // from class: android.media.projection.MediaProjectionAppContent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionAppContent createFromParcel(Parcel parcel) {
            return new MediaProjectionAppContent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionAppContent[] newArray(int i) {
            return new MediaProjectionAppContent[i];
        }
    };
    private final int mId;
    private final Bitmap mThumbnail;
    private final CharSequence mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaProjectionAppContent(Bitmap bitmap, CharSequence charSequence, int i) {
        this.mThumbnail = ((Bitmap) Objects.requireNonNull(bitmap, "thumbnail can't be null")).asShared();
        this.mTitle = (CharSequence) Objects.requireNonNull(charSequence, "title can't be null");
        this.mId = i;
    }

    public Bitmap getThumbnail() {
        return this.mThumbnail;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public int getId() {
        return this.mId;
    }

    private MediaProjectionAppContent(Parcel parcel) {
        this.mThumbnail = (Bitmap) parcel.readParcelable(getClass().getClassLoader(), Bitmap.class);
        this.mTitle = parcel.readCharSequence();
        this.mId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mThumbnail, i);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeInt(this.mId);
    }
}
