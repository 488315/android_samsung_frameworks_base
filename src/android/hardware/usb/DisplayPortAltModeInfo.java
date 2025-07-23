package android.hardware.usb;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class DisplayPortAltModeInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayPortAltModeInfo> CREATOR = new Parcelable.Creator<DisplayPortAltModeInfo>() { // from class: android.hardware.usb.DisplayPortAltModeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayPortAltModeInfo createFromParcel(Parcel parcel) {
            return new DisplayPortAltModeInfo(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayPortAltModeInfo[] newArray(int i) {
            return new DisplayPortAltModeInfo[i];
        }
    };
    public static final int DISPLAYPORT_ALT_MODE_STATUS_CAPABLE_DISABLED = 2;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_ENABLED = 3;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_NOT_CAPABLE = 1;
    public static final int DISPLAYPORT_ALT_MODE_STATUS_UNKNOWN = 0;
    public static final int LINK_TRAINING_STATUS_FAILURE = 2;
    public static final int LINK_TRAINING_STATUS_SUCCESS = 1;
    public static final int LINK_TRAINING_STATUS_UNKNOWN = 0;
    private final int mCableStatus;
    private final boolean mHotPlugDetect;
    private final int mLinkTrainingStatus;
    private final int mNumLanes;
    private final int mPartnerSinkStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayPortAltModeStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkTrainingStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisplayPortAltModeInfo() {
        this.mPartnerSinkStatus = 0;
        this.mCableStatus = 0;
        this.mNumLanes = 0;
        this.mHotPlugDetect = false;
        this.mLinkTrainingStatus = 0;
    }

    public DisplayPortAltModeInfo(int i, int i2, int i3, boolean z, int i4) {
        this.mPartnerSinkStatus = i;
        this.mCableStatus = i2;
        this.mNumLanes = i3;
        this.mHotPlugDetect = z;
        this.mLinkTrainingStatus = i4;
    }

    public int getPartnerSinkStatus() {
        return this.mPartnerSinkStatus;
    }

    public int getCableStatus() {
        return this.mCableStatus;
    }

    public int getNumberOfLanes() {
        return this.mNumLanes;
    }

    public boolean isHotPlugDetectActive() {
        return this.mHotPlugDetect;
    }

    public int getLinkTrainingStatus() {
        return this.mLinkTrainingStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPartnerSinkStatus);
        parcel.writeInt(this.mCableStatus);
        parcel.writeInt(this.mNumLanes);
        parcel.writeBoolean(this.mHotPlugDetect);
        parcel.writeInt(this.mLinkTrainingStatus);
    }

    private String displayPortAltModeStatusToString(int i) {
        if (i == 1) {
            return "not capable";
        }
        if (i == 2) {
            return "capable disabled";
        }
        if (i == 3) {
            return "enabled";
        }
        return "unknown";
    }

    private String linkTrainingStatusToString(int i) {
        if (i == 1) {
            return "success";
        }
        if (i == 2) {
            return "failure";
        }
        return "unknown";
    }

    public String toString() {
        return "DisplayPortAltModeInfo{partnerSink=" + displayPortAltModeStatusToString(this.mPartnerSinkStatus) + ", cable=" + displayPortAltModeStatusToString(this.mCableStatus) + ", numLanes=" + this.mNumLanes + ", hotPlugDetect=" + this.mHotPlugDetect + ", linkTrainingStatus=" + linkTrainingStatusToString(this.mLinkTrainingStatus) + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayPortAltModeInfo)) {
            return false;
        }
        DisplayPortAltModeInfo displayPortAltModeInfo = (DisplayPortAltModeInfo) obj;
        return this.mPartnerSinkStatus == displayPortAltModeInfo.mPartnerSinkStatus && this.mCableStatus == displayPortAltModeInfo.mCableStatus && this.mNumLanes == displayPortAltModeInfo.mNumLanes && this.mHotPlugDetect == displayPortAltModeInfo.mHotPlugDetect && this.mLinkTrainingStatus == displayPortAltModeInfo.mLinkTrainingStatus;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPartnerSinkStatus), Integer.valueOf(this.mCableStatus), Integer.valueOf(this.mNumLanes), Boolean.valueOf(this.mHotPlugDetect), Integer.valueOf(this.mLinkTrainingStatus));
    }
}
