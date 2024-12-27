package android.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraCompatTaskInfo implements Parcelable {
    public static final int CAMERA_COMPAT_FREEFORM_LANDSCAPE = 2;
    public static final int CAMERA_COMPAT_FREEFORM_NONE = 0;
    public static final int CAMERA_COMPAT_FREEFORM_PORTRAIT = 1;
    public static final Parcelable.Creator<CameraCompatTaskInfo> CREATOR =
            new Parcelable.Creator<
                    CameraCompatTaskInfo>() { // from class: android.app.CameraCompatTaskInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CameraCompatTaskInfo createFromParcel(Parcel in) {
                    return new CameraCompatTaskInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CameraCompatTaskInfo[] newArray(int size) {
                    return new CameraCompatTaskInfo[size];
                }
            };
    public int freeformCameraCompatMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FreeformCameraCompatMode {}

    private CameraCompatTaskInfo() {}

    static CameraCompatTaskInfo create() {
        return new CameraCompatTaskInfo();
    }

    private CameraCompatTaskInfo(Parcel source) {
        readFromParcel(source);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    void readFromParcel(Parcel source) {
        this.freeformCameraCompatMode = source.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.freeformCameraCompatMode);
    }

    public boolean equalsForTaskOrganizer(CameraCompatTaskInfo that) {
        return that != null && this.freeformCameraCompatMode == that.freeformCameraCompatMode;
    }

    public boolean equalsForCompatUi(CameraCompatTaskInfo that) {
        return that != null && this.freeformCameraCompatMode == that.freeformCameraCompatMode;
    }

    public String toString() {
        return "CameraCompatTaskInfo { freeformCameraCompatMode="
                + freeformCameraCompatModeToString(this.freeformCameraCompatMode)
                + "}";
    }

    public static String freeformCameraCompatModeToString(int freeformCameraCompatMode) {
        switch (freeformCameraCompatMode) {
            case 0:
                return "inactive";
            case 1:
                return "portrait";
            case 2:
                return "landscape";
            default:
                throw new AssertionError(
                        "Unexpected camera compat mode: " + freeformCameraCompatMode);
        }
    }
}
