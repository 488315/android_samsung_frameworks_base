package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualCameraStreamConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualCameraStreamConfig> CREATOR = new Parcelable.Creator<VirtualCameraStreamConfig>() { // from class: android.companion.virtual.camera.VirtualCameraStreamConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualCameraStreamConfig createFromParcel(Parcel parcel) {
            return new VirtualCameraStreamConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualCameraStreamConfig[] newArray(int i) {
            return new VirtualCameraStreamConfig[i];
        }
    };
    static final int MAX_FPS_UPPER_LIMIT = 60;
    private final int mFormat;
    private final int mHeight;
    private final int mMaxFps;
    private final int mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VirtualCameraStreamConfig(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mMaxFps = i4;
    }

    private VirtualCameraStreamConfig(Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mFormat = parcel.readInt();
        this.mMaxFps = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mFormat);
        parcel.writeInt(this.mMaxFps);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VirtualCameraStreamConfig virtualCameraStreamConfig = (VirtualCameraStreamConfig) obj;
            if (this.mWidth == virtualCameraStreamConfig.mWidth && this.mHeight == virtualCameraStreamConfig.mHeight && this.mFormat == virtualCameraStreamConfig.mFormat && this.mMaxFps == virtualCameraStreamConfig.mMaxFps) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mFormat), Integer.valueOf(this.mMaxFps));
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getMaximumFramesPerSecond() {
        return this.mMaxFps;
    }
}
