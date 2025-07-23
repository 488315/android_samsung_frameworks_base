package android.media.quality;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class AmbientBacklightSettings implements Parcelable {
    public static final Parcelable.Creator<AmbientBacklightSettings> CREATOR = new Parcelable.Creator<AmbientBacklightSettings>() { // from class: android.media.quality.AmbientBacklightSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightSettings createFromParcel(Parcel parcel) {
            return new AmbientBacklightSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AmbientBacklightSettings[] newArray(int i) {
            return new AmbientBacklightSettings[i];
        }
    };
    public static final int SOURCE_AUDIO = 1;
    public static final int SOURCE_AUDIO_VIDEO = 3;
    public static final int SOURCE_NONE = 0;
    public static final int SOURCE_VIDEO = 2;
    private final int mColorFormat;
    private final int mHorizontalZonesNumber;
    private final boolean mIsLetterboxOmitted;
    private final int mMaxFps;
    private final int mSource;
    private final int mThreshold;
    private final int mVerticalZonesNumber;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AmbientBacklightSettings(int i, int i2, int i3, int i4, int i5, boolean z, int i6) {
        this.mSource = i;
        this.mMaxFps = i2;
        this.mColorFormat = i3;
        this.mHorizontalZonesNumber = i4;
        this.mVerticalZonesNumber = i5;
        this.mIsLetterboxOmitted = z;
        this.mThreshold = i6;
    }

    private AmbientBacklightSettings(Parcel parcel) {
        this.mSource = parcel.readInt();
        this.mMaxFps = parcel.readInt();
        this.mColorFormat = parcel.readInt();
        this.mHorizontalZonesNumber = parcel.readInt();
        this.mVerticalZonesNumber = parcel.readInt();
        this.mIsLetterboxOmitted = parcel.readBoolean();
        this.mThreshold = parcel.readInt();
    }

    public int getSource() {
        return this.mSource;
    }

    public int getMaxFps() {
        return this.mMaxFps;
    }

    public int getColorFormat() {
        return this.mColorFormat;
    }

    public int getHorizontalZonesCount() {
        return this.mHorizontalZonesNumber;
    }

    public int getVerticalZonesCount() {
        return this.mVerticalZonesNumber;
    }

    public boolean isLetterboxOmitted() {
        return this.mIsLetterboxOmitted;
    }

    public int getThreshold() {
        return this.mThreshold;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mMaxFps);
        parcel.writeInt(this.mColorFormat);
        parcel.writeInt(this.mHorizontalZonesNumber);
        parcel.writeInt(this.mVerticalZonesNumber);
        parcel.writeBoolean(this.mIsLetterboxOmitted);
        parcel.writeInt(this.mThreshold);
    }

    public String toString() {
        return "AmbientBacklightSettings{Source=" + this.mSource + ", MaxFps=" + this.mMaxFps + ", ColorFormat=" + this.mColorFormat + ", HorizontalZonesNumber=" + this.mHorizontalZonesNumber + ", VerticalZonesNumber=" + this.mVerticalZonesNumber + ", IsLetterboxOmitted=" + this.mIsLetterboxOmitted + ", Threshold=" + this.mThreshold + "}";
    }
}
