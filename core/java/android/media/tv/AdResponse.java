package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AdResponse implements Parcelable {
    public static final Parcelable.Creator<AdResponse> CREATOR =
            new Parcelable.Creator<AdResponse>() { // from class: android.media.tv.AdResponse.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AdResponse createFromParcel(Parcel source) {
                    return new AdResponse(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AdResponse[] newArray(int size) {
                    return new AdResponse[size];
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
    public @interface ResponseType {}

    public AdResponse(int id, int responseType, long elapsedTime) {
        this.mId = id;
        this.mResponseType = responseType;
        this.mElapsedTime = elapsedTime;
    }

    private AdResponse(Parcel source) {
        this.mId = source.readInt();
        this.mResponseType = source.readInt();
        this.mElapsedTime = source.readLong();
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mResponseType);
        dest.writeLong(this.mElapsedTime);
    }
}
