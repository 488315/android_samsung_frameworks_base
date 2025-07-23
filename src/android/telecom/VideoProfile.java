package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class VideoProfile implements Parcelable {
    public static final Parcelable.Creator<VideoProfile> CREATOR = new Parcelable.Creator<VideoProfile>() { // from class: android.telecom.VideoProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoProfile createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            VideoProfile.class.getClassLoader();
            return new VideoProfile(readInt, readInt2);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VideoProfile[] newArray(int i) {
            return new VideoProfile[i];
        }
    };
    public static final int QUALITY_DEFAULT = 4;
    public static final int QUALITY_HIGH = 1;
    public static final int QUALITY_LOW = 3;
    public static final int QUALITY_MEDIUM = 2;
    public static final int QUALITY_UNKNOWN = 0;
    public static final int STATE_AUDIO_ONLY = 0;
    public static final int STATE_BIDIRECTIONAL = 3;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_RX_ENABLED = 2;
    public static final int STATE_TX_ENABLED = 1;
    private final int mQuality;
    private final int mVideoState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoQuality {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoState {
    }

    private static boolean hasState(int i, int i2) {
        return (i & i2) == i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VideoProfile(int i) {
        this(i, 4);
    }

    public VideoProfile(int i, int i2) {
        this.mVideoState = i;
        this.mQuality = i2;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public int getQuality() {
        return this.mQuality;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mVideoState);
        parcel.writeInt(this.mQuality);
    }

    public String toString() {
        return "[VideoProfile videoState = " + videoStateToString(this.mVideoState) + " videoQuality = " + this.mQuality + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static String videoStateToString(int i) {
        StringBuilder sb = new StringBuilder("Audio");
        if (i == 0) {
            sb.append(" Only");
        } else {
            if (isTransmissionEnabled(i)) {
                sb.append(" Tx");
            }
            if (isReceptionEnabled(i)) {
                sb.append(" Rx");
            }
            if (isPaused(i)) {
                sb.append(" Pause");
            }
        }
        return sb.toString();
    }

    public static boolean isAudioOnly(int i) {
        return (hasState(i, 1) || hasState(i, 2)) ? false : true;
    }

    public static boolean isVideo(int i) {
        return hasState(i, 1) || hasState(i, 2) || hasState(i, 3);
    }

    public static boolean isTransmissionEnabled(int i) {
        return hasState(i, 1);
    }

    public static boolean isReceptionEnabled(int i) {
        return hasState(i, 2);
    }

    public static boolean isBidirectional(int i) {
        return hasState(i, 3);
    }

    public static boolean isPaused(int i) {
        return hasState(i, 4);
    }

    public static final class CameraCapabilities implements Parcelable {
        public static final Parcelable.Creator<CameraCapabilities> CREATOR = new Parcelable.Creator<CameraCapabilities>() { // from class: android.telecom.VideoProfile.CameraCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CameraCapabilities createFromParcel(Parcel parcel) {
                return new CameraCapabilities(parcel.readInt(), parcel.readInt(), parcel.readByte() != 0, parcel.readFloat());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CameraCapabilities[] newArray(int i) {
                return new CameraCapabilities[i];
            }
        };
        private final int mHeight;
        private final float mMaxZoom;
        private final int mWidth;
        private final boolean mZoomSupported;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public CameraCapabilities(int i, int i2) {
            this(i, i2, false, 1.0f);
        }

        public CameraCapabilities(int i, int i2, boolean z, float f) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mZoomSupported = z;
            this.mMaxZoom = f;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(getWidth());
            parcel.writeInt(getHeight());
            parcel.writeByte(isZoomSupported() ? (byte) 1 : (byte) 0);
            parcel.writeFloat(getMaxZoom());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public boolean isZoomSupported() {
            return this.mZoomSupported;
        }

        public float getMaxZoom() {
            return this.mMaxZoom;
        }
    }
}
