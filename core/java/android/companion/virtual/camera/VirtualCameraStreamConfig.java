package android.companion.virtual.camera;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualCameraStreamConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualCameraStreamConfig> CREATOR =
            new Parcelable.Creator<VirtualCameraStreamConfig>() { // from class:
                // android.companion.virtual.camera.VirtualCameraStreamConfig.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualCameraStreamConfig createFromParcel(Parcel in) {
                    return new VirtualCameraStreamConfig(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualCameraStreamConfig[] newArray(int size) {
                    return new VirtualCameraStreamConfig[size];
                }
            };
    static final int MAX_FPS_UPPER_LIMIT = 60;
    private final int mFormat;
    private final int mHeight;
    private final int mMaxFps;
    private final int mWidth;

    public VirtualCameraStreamConfig(int width, int height, int format, int maxFps) {
        this.mWidth = width;
        this.mHeight = height;
        this.mFormat = format;
        this.mMaxFps = maxFps;
    }

    private VirtualCameraStreamConfig(Parcel in) {
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        this.mFormat = in.readInt();
        this.mMaxFps = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
        dest.writeInt(this.mFormat);
        dest.writeInt(this.mMaxFps);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VirtualCameraStreamConfig that = (VirtualCameraStreamConfig) o;
        if (this.mWidth == that.mWidth
                && this.mHeight == that.mHeight
                && this.mFormat == that.mFormat
                && this.mMaxFps == that.mMaxFps) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mWidth),
                Integer.valueOf(this.mHeight),
                Integer.valueOf(this.mFormat),
                Integer.valueOf(this.mMaxFps));
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getMaximumFramesPerSecond() {
        return this.mMaxFps;
    }
}
