package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class MediaQualityStatus implements Parcelable {
    public static final Parcelable.Creator<MediaQualityStatus> CREATOR = new Parcelable.Creator<MediaQualityStatus>() { // from class: android.telephony.ims.MediaQualityStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaQualityStatus createFromParcel(Parcel parcel) {
            return new MediaQualityStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaQualityStatus[] newArray(int i) {
            return new MediaQualityStatus[i];
        }
    };
    public static final int MEDIA_SESSION_TYPE_AUDIO = 1;
    public static final int MEDIA_SESSION_TYPE_VIDEO = 2;
    private final String mImsCallSessionId;
    private final int mMediaSessionType;
    private final long mRtpInactivityTimeMillis;
    private final int mRtpJitterMillis;
    private final int mRtpPacketLossRate;
    private final int mTransportType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaSessionType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MediaQualityStatus(String str, int i, int i2, int i3, int i4, long j) {
        this.mImsCallSessionId = str;
        this.mMediaSessionType = i;
        this.mTransportType = i2;
        this.mRtpPacketLossRate = i3;
        this.mRtpJitterMillis = i4;
        this.mRtpInactivityTimeMillis = j;
    }

    public String getCallSessionId() {
        return this.mImsCallSessionId;
    }

    public int getMediaSessionType() {
        return this.mMediaSessionType;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getRtpPacketLossRate() {
        return this.mRtpPacketLossRate;
    }

    public int getRtpJitterMillis() {
        return this.mRtpJitterMillis;
    }

    public long getRtpInactivityMillis() {
        return this.mRtpInactivityTimeMillis;
    }

    private MediaQualityStatus(Parcel parcel) {
        this.mImsCallSessionId = parcel.readString();
        this.mMediaSessionType = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mRtpPacketLossRate = parcel.readInt();
        this.mRtpJitterMillis = parcel.readInt();
        this.mRtpInactivityTimeMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mImsCallSessionId);
        parcel.writeInt(this.mMediaSessionType);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mRtpPacketLossRate);
        parcel.writeInt(this.mRtpJitterMillis);
        parcel.writeLong(this.mRtpInactivityTimeMillis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MediaQualityStatus mediaQualityStatus = (MediaQualityStatus) obj;
            String str = this.mImsCallSessionId;
            if (str != null && str.equals(mediaQualityStatus.mImsCallSessionId) && this.mMediaSessionType == mediaQualityStatus.mMediaSessionType && this.mTransportType == mediaQualityStatus.mTransportType && this.mRtpPacketLossRate == mediaQualityStatus.mRtpPacketLossRate && this.mRtpJitterMillis == mediaQualityStatus.mRtpJitterMillis && this.mRtpInactivityTimeMillis == mediaQualityStatus.mRtpInactivityTimeMillis) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mImsCallSessionId, Integer.valueOf(this.mMediaSessionType), Integer.valueOf(this.mTransportType), Integer.valueOf(this.mRtpPacketLossRate), Integer.valueOf(this.mRtpJitterMillis), Long.valueOf(this.mRtpInactivityTimeMillis));
    }

    public String toString() {
        return "MediaThreshold{mImsCallSessionId=" + this.mImsCallSessionId + ", mMediaSessionType=" + this.mMediaSessionType + ", mTransportType=" + this.mTransportType + ", mRtpPacketLossRate=" + this.mRtpPacketLossRate + ", mRtpJitterMillis=" + this.mRtpJitterMillis + ", mRtpInactivityTimeMillis=" + this.mRtpInactivityTimeMillis + "}";
    }
}
