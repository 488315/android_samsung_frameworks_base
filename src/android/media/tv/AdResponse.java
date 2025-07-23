package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class AdResponse implements Parcelable {
    public static final Parcelable.Creator<AdResponse> CREATOR = new Parcelable.Creator<AdResponse>() { // from class: android.media.tv.AdResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdResponse createFromParcel(Parcel parcel) {
            return new AdResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdResponse[] newArray(int i) {
            return new AdResponse[i];
        }
    };
    public static final int RESPONSE_TYPE_BUFFERING = 5;
    public static final int RESPONSE_TYPE_ERROR = 4;
    public static final int RESPONSE_TYPE_FINISHED = 2;
    public static final int RESPONSE_TYPE_PLAYING = 1;
    public static final int RESPONSE_TYPE_STOPPED = 3;
    private final long mElapsedTime;
    private final int mId;
    private final int mResponseType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResponseType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdResponse(int i, int i2, long j) {
        this.mId = i;
        this.mResponseType = i2;
        this.mElapsedTime = j;
    }

    private AdResponse(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mResponseType = parcel.readInt();
        this.mElapsedTime = parcel.readLong();
    }

    public int getId() {
        return this.mId;
    }

    public int getResponseType() {
        return this.mResponseType;
    }

    public long getElapsedTimeMillis() {
        return this.mElapsedTime;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mResponseType);
        parcel.writeLong(this.mElapsedTime);
    }
}
