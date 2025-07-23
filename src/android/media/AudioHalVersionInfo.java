package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes2.dex */
public final class AudioHalVersionInfo implements Parcelable, Comparable<AudioHalVersionInfo> {
    public static final AudioHalVersionInfo AIDL_1_0;
    public static final int AUDIO_HAL_TYPE_AIDL = 1;
    public static final int AUDIO_HAL_TYPE_HIDL = 0;
    public static final Parcelable.Creator<AudioHalVersionInfo> CREATOR;
    public static final AudioHalVersionInfo HIDL_2_0;
    public static final AudioHalVersionInfo HIDL_4_0;
    public static final AudioHalVersionInfo HIDL_5_0;
    public static final AudioHalVersionInfo HIDL_6_0;
    public static final AudioHalVersionInfo HIDL_7_0;
    public static final AudioHalVersionInfo HIDL_7_1;
    private static final String TAG = "AudioHalVersionInfo";
    public static final List<AudioHalVersionInfo> VERSIONS;
    private AudioHalVersion mHalVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioHalType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        AudioHalVersionInfo audioHalVersionInfo = new AudioHalVersionInfo(1, 1, 0);
        AIDL_1_0 = audioHalVersionInfo;
        AudioHalVersionInfo audioHalVersionInfo2 = new AudioHalVersionInfo(0, 7, 1);
        HIDL_7_1 = audioHalVersionInfo2;
        AudioHalVersionInfo audioHalVersionInfo3 = new AudioHalVersionInfo(0, 7, 0);
        HIDL_7_0 = audioHalVersionInfo3;
        AudioHalVersionInfo audioHalVersionInfo4 = new AudioHalVersionInfo(0, 6, 0);
        HIDL_6_0 = audioHalVersionInfo4;
        HIDL_5_0 = new AudioHalVersionInfo(0, 5, 0);
        HIDL_4_0 = new AudioHalVersionInfo(0, 4, 0);
        HIDL_2_0 = new AudioHalVersionInfo(0, 2, 0);
        VERSIONS = List.of(audioHalVersionInfo, audioHalVersionInfo2, audioHalVersionInfo3, audioHalVersionInfo4);
        CREATOR = new Parcelable.Creator<AudioHalVersionInfo>() { // from class: android.media.AudioHalVersionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioHalVersionInfo createFromParcel(Parcel parcel) {
                return new AudioHalVersionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioHalVersionInfo[] newArray(int i) {
                return new AudioHalVersionInfo[i];
            }
        };
    }

    public int getHalType() {
        return this.mHalVersion.type;
    }

    public int getMajorVersion() {
        return this.mHalVersion.major;
    }

    public int getMinorVersion() {
        return this.mHalVersion.minor;
    }

    private static String typeToString(int i) {
        if (i == 0) {
            return "HIDL";
        }
        if (i == 1) {
            return "AIDL";
        }
        return "INVALID";
    }

    private static String toString(int i, int i2, int i3) {
        return typeToString(i) + ":" + Integer.toString(i2) + MediaMetrics.SEPARATOR + Integer.toString(i3);
    }

    private AudioHalVersionInfo(int i, int i2, int i3) {
        AudioHalVersion audioHalVersion = new AudioHalVersion();
        this.mHalVersion = audioHalVersion;
        audioHalVersion.type = i;
        this.mHalVersion.major = i2;
        this.mHalVersion.minor = i3;
    }

    private AudioHalVersionInfo(Parcel parcel) {
        this.mHalVersion = new AudioHalVersion();
        this.mHalVersion = (AudioHalVersion) parcel.readTypedObject(AudioHalVersion.CREATOR);
    }

    public String toString() {
        return toString(this.mHalVersion.type, this.mHalVersion.major, this.mHalVersion.minor);
    }

    @Override // java.lang.Comparable
    public int compareTo(AudioHalVersionInfo audioHalVersionInfo) {
        List<AudioHalVersionInfo> list = VERSIONS;
        int indexOf = list.indexOf(audioHalVersionInfo);
        int indexOf2 = list.indexOf(this);
        return (indexOf2 < 0 || indexOf < 0) ? indexOf2 - indexOf : indexOf - indexOf2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mHalVersion, i);
    }
}
