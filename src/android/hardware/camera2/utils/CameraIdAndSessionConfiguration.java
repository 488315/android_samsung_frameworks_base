package android.hardware.camera2.utils;

import android.hardware.camera2.params.SessionConfiguration;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CameraIdAndSessionConfiguration implements Parcelable {
    public static final Parcelable.Creator<CameraIdAndSessionConfiguration> CREATOR = new Parcelable.Creator<CameraIdAndSessionConfiguration>() { // from class: android.hardware.camera2.utils.CameraIdAndSessionConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraIdAndSessionConfiguration createFromParcel(Parcel parcel) {
            return new CameraIdAndSessionConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraIdAndSessionConfiguration[] newArray(int i) {
            return new CameraIdAndSessionConfiguration[i];
        }
    };
    private String mCameraId;
    private SessionConfiguration mSessionConfiguration;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CameraIdAndSessionConfiguration(String str, SessionConfiguration sessionConfiguration) {
        this.mCameraId = str;
        this.mSessionConfiguration = sessionConfiguration;
    }

    private CameraIdAndSessionConfiguration(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCameraId);
        this.mSessionConfiguration.writeToParcel(parcel, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mCameraId = parcel.readString();
        this.mSessionConfiguration = SessionConfiguration.CREATOR.createFromParcel(parcel);
    }

    public String getCameraId() {
        return this.mCameraId;
    }

    public SessionConfiguration getSessionConfiguration() {
        return this.mSessionConfiguration;
    }
}
