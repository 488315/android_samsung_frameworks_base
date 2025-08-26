package android.media.tv;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class AdRequest implements Parcelable {
    public static final Parcelable.Creator<AdRequest> CREATOR = new Parcelable.Creator<AdRequest>() { // from class: android.media.tv.AdRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdRequest createFromParcel(Parcel parcel) {
            return new AdRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdRequest[] newArray(int i) {
            return new AdRequest[i];
        }
    };
    public static final String KEY_AUDIO_METADATA = "key_audio_metadata";
    public static final String KEY_VIDEO_METADATA = "key_video_metadata";
    public static final int REQUEST_TYPE_START = 1;
    public static final int REQUEST_TYPE_STOP = 2;
    private final long mEchoInterval;
    private final ParcelFileDescriptor mFileDescriptor;
    private final int mId;
    private final String mMediaFileType;
    private final Bundle mMetadata;
    private final int mRequestType;
    private final long mStartTime;
    private final long mStopTime;
    private final Uri mUri;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdRequest(int i, int i2, ParcelFileDescriptor parcelFileDescriptor, long j, long j2, long j3, String str, Bundle bundle) {
        this(i, i2, parcelFileDescriptor, null, j, j2, j3, str, bundle);
    }

    public AdRequest(int i, int i2, Uri uri, long j, long j2, long j3, Bundle bundle) {
        this(i, i2, null, uri, j, j2, j3, null, bundle);
    }

    private AdRequest(int i, int i2, ParcelFileDescriptor parcelFileDescriptor, Uri uri, long j, long j2, long j3, String str, Bundle bundle) {
        this.mId = i;
        this.mRequestType = i2;
        this.mFileDescriptor = parcelFileDescriptor;
        this.mStartTime = j;
        this.mStopTime = j2;
        this.mEchoInterval = j3;
        this.mMediaFileType = str;
        this.mMetadata = bundle;
        this.mUri = uri;
    }

    private AdRequest(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mRequestType = parcel.readInt();
        int i = parcel.readInt();
        if (i == 1) {
            this.mFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
            this.mUri = null;
        } else if (i == 2) {
            String string = parcel.readString();
            this.mUri = string == null ? null : Uri.parse(string);
            this.mFileDescriptor = null;
        } else {
            this.mFileDescriptor = null;
            this.mUri = null;
        }
        this.mStartTime = parcel.readLong();
        this.mStopTime = parcel.readLong();
        this.mEchoInterval = parcel.readLong();
        this.mMediaFileType = parcel.readString();
        this.mMetadata = parcel.readBundle();
    }

    public int getId() {
        return this.mId;
    }

    public int getRequestType() {
        return this.mRequestType;
    }

    public ParcelFileDescriptor getFileDescriptor() {
        return this.mFileDescriptor;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public long getStartTimeMillis() {
        return this.mStartTime;
    }

    public long getStopTimeMillis() {
        return this.mStopTime;
    }

    public long getEchoIntervalMillis() {
        return this.mEchoInterval;
    }

    public String getMediaFileType() {
        return this.mMediaFileType;
    }

    public Bundle getMetadata() {
        return this.mMetadata;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mRequestType);
        if (this.mFileDescriptor != null) {
            parcel.writeInt(1);
            this.mFileDescriptor.writeToParcel(parcel, i);
        } else if (this.mUri != null) {
            parcel.writeInt(2);
            parcel.writeString(this.mUri.toString());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mStartTime);
        parcel.writeLong(this.mStopTime);
        parcel.writeLong(this.mEchoInterval);
        parcel.writeString(this.mMediaFileType);
        parcel.writeBundle(this.mMetadata);
    }
}
