package android.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.media.performance.flags.Flags;

/* loaded from: classes2.dex */
public class MediaDescription implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3;
    public static final long BT_FOLDER_TYPE_GENRES = 4;
    public static final long BT_FOLDER_TYPE_MIXED = 0;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5;
    public static final long BT_FOLDER_TYPE_TITLES = 1;
    public static final long BT_FOLDER_TYPE_YEARS = 6;
    public static final Parcelable.Creator<MediaDescription> CREATOR = new Parcelable.Creator<MediaDescription>() { // from class: android.media.MediaDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaDescription createFromParcel(Parcel parcel) {
            return new MediaDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaDescription[] newArray(int i) {
            return new MediaDescription[i];
        }
    };
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    private final CharSequence mDescription;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private MediaDescription(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = str;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    private MediaDescription(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap) parcel.readParcelable(null, Bitmap.class);
        this.mIconUri = (Uri) parcel.readParcelable(null, Uri.class);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri) parcel.readParcelable(null, Uri.class);
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mMediaId);
        TextUtils.writeToParcel(this.mTitle, parcel, 0);
        TextUtils.writeToParcel(this.mSubtitle, parcel, 0);
        TextUtils.writeToParcel(this.mDescription, parcel, 0);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeParcelable(this.mIconUri, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeParcelable(this.mMediaUri, i);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MediaDescription)) {
            return false;
        }
        MediaDescription mediaDescription = (MediaDescription) obj;
        return String.valueOf(this.mTitle).equals(String.valueOf(mediaDescription.mTitle)) && String.valueOf(this.mSubtitle).equals(String.valueOf(mediaDescription.mSubtitle)) && String.valueOf(this.mDescription).equals(String.valueOf(mediaDescription.mDescription));
    }

    public String toString() {
        return ((Object) this.mTitle) + ", " + ((Object) this.mSubtitle) + ", " + ((Object) this.mDescription);
    }

    public static class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public Builder setMediaId(String str) {
            this.mMediaId = str;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setIconBitmap(Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setMediaUri(Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public MediaDescription build() {
            if (Flags.mediaDescriptionAshmemBitmap()) {
                Bitmap bitmap = this.mIcon;
                return new MediaDescription(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, bitmap != null ? bitmap.asShared() : null, this.mIconUri, this.mExtras, this.mMediaUri);
            }
            return new MediaDescription(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }
    }
}
