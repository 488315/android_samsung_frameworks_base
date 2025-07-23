package android.hardware.camera2.impl;

import android.hardware.camera2.CameraMetadataInfo;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PhysicalCaptureResultInfo implements Parcelable {
    public static final Parcelable.Creator<PhysicalCaptureResultInfo> CREATOR = new Parcelable.Creator<PhysicalCaptureResultInfo>() { // from class: android.hardware.camera2.impl.PhysicalCaptureResultInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalCaptureResultInfo createFromParcel(Parcel parcel) {
            return new PhysicalCaptureResultInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhysicalCaptureResultInfo[] newArray(int i) {
            return new PhysicalCaptureResultInfo[i];
        }
    };
    private String cameraId;
    private CameraMetadataInfo cameraMetadataInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PhysicalCaptureResultInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public PhysicalCaptureResultInfo(String str, CameraMetadataNative cameraMetadataNative) {
        this.cameraId = str;
        this.cameraMetadataInfo = CameraMetadataInfo.metadata(cameraMetadataNative);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cameraId);
        this.cameraMetadataInfo.writeToParcel(parcel, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.cameraId = parcel.readString();
        this.cameraMetadataInfo = CameraMetadataInfo.CREATOR.createFromParcel(parcel);
    }

    public String getCameraId() {
        return this.cameraId;
    }

    public CameraMetadataNative getCameraMetadata() {
        return this.cameraMetadataInfo.getMetadata();
    }

    public CameraMetadataInfo getCameraMetadataInfo() {
        return this.cameraMetadataInfo;
    }
}
