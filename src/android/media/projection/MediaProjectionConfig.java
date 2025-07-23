package android.media.projection;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.media.projection.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class MediaProjectionConfig implements Parcelable {
    public static final int CAPTURE_REGION_FIXED_DISPLAY = 1;
    public static final int CAPTURE_REGION_USER_CHOICE = 0;
    public static final int DEFAULT_PROJECTION_SOURCES = 10;
    public static final int PROJECTION_SOURCE_APP = 8;
    public static final int PROJECTION_SOURCE_APP_CONTENT = 16;
    public static final int PROJECTION_SOURCE_DISPLAY = 2;
    public static final int PROJECTION_SOURCE_DISPLAY_REGION = 4;
    private final int mDisplayToCapture;
    private final int mInitialSelection;
    private final int mProjectionSources;

    @Deprecated
    private int mRegionToCapture;
    private final String mRequesterHint;
    private static final int[] PROJECTION_SOURCES = {2, 4, 8, 16};
    private static final String[] PROJECTION_SOURCES_STRING = {"PROJECTION_SOURCE_DISPLAY", "PROJECTION_SOURCE_DISPLAY_REGION", "PROJECTION_SOURCE_APP", "PROJECTION_SOURCE_APP_CONTENT"};
    private static final int VALID_PROJECTION_SOURCES = createValidSourcesMask();
    public static final Parcelable.Creator<MediaProjectionConfig> CREATOR = new Parcelable.Creator<MediaProjectionConfig>() { // from class: android.media.projection.MediaProjectionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionConfig[] newArray(int i) {
            return new MediaProjectionConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionConfig createFromParcel(Parcel parcel) {
            return new MediaProjectionConfig(parcel);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    @Deprecated
    public @interface CaptureRegion {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaProjectionSource {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    private MediaProjectionConfig(int i) {
        if (Flags.appContentSharing()) {
            throw new UnsupportedOperationException("Flag FLAG_APP_CONTENT_SHARING enabled. This method must not be called.");
        }
        this.mRegionToCapture = i;
        this.mDisplayToCapture = 0;
        this.mRequesterHint = null;
        this.mInitialSelection = -1;
        this.mProjectionSources = -1;
    }

    private MediaProjectionConfig(int i, String str, int i2, int i3) {
        if (!Flags.appContentSharing()) {
            throw new UnsupportedOperationException("Flag FLAG_APP_CONTENT_SHARING disabled. This method must not be called");
        }
        if (i == 0) {
            this.mProjectionSources = 10;
        } else {
            this.mProjectionSources = i;
        }
        this.mRequesterHint = str;
        this.mDisplayToCapture = i2;
        this.mInitialSelection = i3;
    }

    public static MediaProjectionConfig createConfigForDefaultDisplay() {
        if (Flags.appContentSharing()) {
            return new Builder().setSourceEnabled(2, true).build();
        }
        return new MediaProjectionConfig(1);
    }

    public static MediaProjectionConfig createConfigForUserChoice() {
        if (Flags.appContentSharing()) {
            return new Builder().build();
        }
        return new MediaProjectionConfig(0);
    }

    @Deprecated
    private static String captureRegionToString(int i) {
        if (i == 0) {
            return "CAPTURE_REGION_USERS_CHOICE";
        }
        if (i == 1) {
            return "CAPTURE_REGION_GIVEN_DISPLAY";
        }
        return Integer.toHexString(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String projectionSourceToString(int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (true) {
            int[] iArr = PROJECTION_SOURCES;
            if (i2 >= iArr.length) {
                break;
            }
            if ((iArr[i2] & i) > 0) {
                sb.append(PROJECTION_SOURCES_STRING[i2]);
                sb.append(" ");
                i &= ~iArr[i2];
            }
            i2++;
        }
        if (i > 0) {
            sb.append("Unknown projection sources: ");
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    public String toString() {
        if (Flags.appContentSharing()) {
            return "MediaProjectionConfig{mInitialSelection=%d, mDisplayToCapture=%d, mProjectionSource=%s, mRequesterHint='%s'}".formatted(Integer.valueOf(this.mInitialSelection), Integer.valueOf(this.mDisplayToCapture), projectionSourceToString(this.mProjectionSources), this.mRequesterHint);
        }
        return "MediaProjectionConfig { displayToCapture = " + this.mDisplayToCapture + ", regionToCapture = " + captureRegionToString(this.mRegionToCapture) + " }";
    }

    public int getDisplayToCapture() {
        return this.mDisplayToCapture;
    }

    public int getRegionToCapture() {
        return this.mRegionToCapture;
    }

    public int getProjectionSources() {
        return this.mProjectionSources;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MediaProjectionConfig mediaProjectionConfig = (MediaProjectionConfig) obj;
            if (Flags.appContentSharing()) {
                return this.mDisplayToCapture == mediaProjectionConfig.mDisplayToCapture && this.mProjectionSources == mediaProjectionConfig.mProjectionSources && this.mInitialSelection == mediaProjectionConfig.mInitialSelection && Objects.equals(this.mRequesterHint, mediaProjectionConfig.mRequesterHint);
            }
            if (this.mDisplayToCapture == mediaProjectionConfig.mDisplayToCapture && this.mRegionToCapture == mediaProjectionConfig.mRegionToCapture) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if (Flags.appContentSharing()) {
            return Objects.hash(Integer.valueOf(this.mDisplayToCapture), Integer.valueOf(this.mProjectionSources), Integer.valueOf(this.mInitialSelection), this.mRequesterHint);
        }
        return ((this.mDisplayToCapture + 31) * 31) + this.mRegionToCapture;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayToCapture);
        if (Flags.appContentSharing()) {
            parcel.writeInt(this.mProjectionSources);
            parcel.writeString(this.mRequesterHint);
            parcel.writeInt(this.mInitialSelection);
            return;
        }
        parcel.writeInt(this.mRegionToCapture);
    }

    MediaProjectionConfig(Parcel parcel) {
        this.mDisplayToCapture = parcel.readInt();
        if (Flags.appContentSharing()) {
            this.mProjectionSources = parcel.readInt();
            this.mRequesterHint = parcel.readString();
            this.mInitialSelection = parcel.readInt();
        } else {
            this.mRegionToCapture = parcel.readInt();
            this.mProjectionSources = -1;
            this.mRequesterHint = null;
            this.mInitialSelection = -1;
        }
    }

    public boolean isSourceEnabled(int i) {
        return (this.mProjectionSources & i) > 0;
    }

    public int getInitiallySelectedSource() {
        return this.mInitialSelection;
    }

    public CharSequence getRequesterHint() {
        return this.mRequesterHint;
    }

    private static int createValidSourcesMask() {
        int i = 0;
        for (int i2 : PROJECTION_SOURCES) {
            i |= i2;
        }
        return i;
    }

    public static final class Builder {
        private int mInitialSelection;
        private int mOptions = 0;
        private String mRequesterHint = null;

        public Builder() {
            if (!Flags.appContentSharing()) {
                throw new UnsupportedOperationException("Flag FLAG_APP_CONTENT_SHARING disabled");
            }
        }

        public Builder setInitiallySelectedSource(int i) {
            for (int i2 : MediaProjectionConfig.PROJECTION_SOURCES) {
                if (i == i2) {
                    this.mInitialSelection = i;
                    return this;
                }
            }
            throw new IllegalArgumentException("projectionSource is no a valid projection source. projectionSource must be one of %s but was %s".formatted(Arrays.toString(MediaProjectionConfig.PROJECTION_SOURCES_STRING), MediaProjectionConfig.projectionSourceToString(i)));
        }

        public Builder setRequesterHint(String str) {
            this.mRequesterHint = str;
            return this;
        }

        public Builder setSourceEnabled(int i, boolean z) {
            int i2;
            if ((MediaProjectionConfig.VALID_PROJECTION_SOURCES & i) == 0) {
                throw new IllegalArgumentException("source is no a valid projection source. source must be any of %s but was %s".formatted(Arrays.toString(MediaProjectionConfig.PROJECTION_SOURCES_STRING), MediaProjectionConfig.projectionSourceToString(i)));
            }
            if (z) {
                i2 = i | this.mOptions;
            } else {
                i2 = (~i) & this.mOptions;
            }
            this.mOptions = i2;
            return this;
        }

        public MediaProjectionConfig build() {
            return new MediaProjectionConfig(this.mOptions, this.mRequesterHint, 0, this.mInitialSelection);
        }
    }
}
