package android.app;

import android.hardware.input.KeyboardLayout;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class CameraCompatTaskInfo implements Parcelable {
    public static final int CAMERA_COMPAT_FREEFORM_LANDSCAPE_DEVICE_IN_LANDSCAPE = 3;
    public static final int CAMERA_COMPAT_FREEFORM_LANDSCAPE_DEVICE_IN_PORTRAIT = 5;
    public static final int CAMERA_COMPAT_FREEFORM_NONE = 1;
    public static final int CAMERA_COMPAT_FREEFORM_PORTRAIT_DEVICE_IN_LANDSCAPE = 2;
    public static final int CAMERA_COMPAT_FREEFORM_PORTRAIT_DEVICE_IN_PORTRAIT = 4;
    public static final int CAMERA_COMPAT_FREEFORM_UNSPECIFIED = 0;
    public static final Parcelable.Creator<CameraCompatTaskInfo> CREATOR = new Parcelable.Creator<CameraCompatTaskInfo>() { // from class: android.app.CameraCompatTaskInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraCompatTaskInfo createFromParcel(Parcel parcel) {
            return new CameraCompatTaskInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraCompatTaskInfo[] newArray(int i) {
            return new CameraCompatTaskInfo[i];
        }
    };
    public int freeformCameraCompatMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FreeformCameraCompatMode {
    }

    public static int getDisplayRotationFromCameraCompatMode(int i) {
        if (i == 2) {
            return 0;
        }
        if (i == 3) {
            return 1;
        }
        if (i != 4) {
            return i != 5 ? -1 : 1;
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CameraCompatTaskInfo() {
    }

    static CameraCompatTaskInfo create() {
        return new CameraCompatTaskInfo();
    }

    private CameraCompatTaskInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    void readFromParcel(Parcel parcel) {
        this.freeformCameraCompatMode = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.freeformCameraCompatMode);
    }

    public boolean equalsForTaskOrganizer(CameraCompatTaskInfo cameraCompatTaskInfo) {
        return cameraCompatTaskInfo != null && this.freeformCameraCompatMode == cameraCompatTaskInfo.freeformCameraCompatMode;
    }

    public boolean equalsForCompatUi(CameraCompatTaskInfo cameraCompatTaskInfo) {
        return cameraCompatTaskInfo != null && this.freeformCameraCompatMode == cameraCompatTaskInfo.freeformCameraCompatMode;
    }

    public String toString() {
        return "CameraCompatTaskInfo { freeformCameraCompatMode=" + freeformCameraCompatModeToString(this.freeformCameraCompatMode) + "}";
    }

    public static String freeformCameraCompatModeToString(int i) {
        if (i == 0) {
            return KeyboardLayout.LAYOUT_TYPE_UNDEFINED;
        }
        if (i == 1) {
            return "inactive";
        }
        if (i == 2) {
            return "app-portrait-device-landscape";
        }
        if (i == 3) {
            return "app-landscape-device-landscape";
        }
        if (i == 4) {
            return "app-portrait-device-portrait";
        }
        if (i == 5) {
            return "app-landscape-device-portrait";
        }
        throw new AssertionError("Unexpected camera compat mode: " + i);
    }
}
