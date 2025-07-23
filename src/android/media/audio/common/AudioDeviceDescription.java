package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioDeviceDescription implements Parcelable {
    public static final String CONNECTION_ANALOG = "analog";
    public static final String CONNECTION_BT_A2DP = "bt-a2dp";
    public static final String CONNECTION_BT_LE = "bt-le";
    public static final String CONNECTION_BT_SCO = "bt-sco";
    public static final String CONNECTION_BUILTIN_MIC3 = "mic3";
    public static final String CONNECTION_BUILTIN_MIC3_MIC4 = "mic3_mic4";
    public static final String CONNECTION_BUILTIN_MIC4 = "mic4";
    public static final String CONNECTION_BUILTIN_MULTI_MIC = "multi_mic";

    @Deprecated
    public static final String CONNECTION_BUS = "bus";
    public static final String CONNECTION_HDMI = "hdmi";
    public static final String CONNECTION_HDMI_ARC = "hdmi-arc";
    public static final String CONNECTION_HDMI_EARC = "hdmi-earc";
    public static final String CONNECTION_IP_V4 = "ip-v4";
    public static final String CONNECTION_SPDIF = "spdif";
    public static final String CONNECTION_USB = "usb";
    public static final String CONNECTION_VIRTUAL = "virtual";
    public static final String CONNECTION_WIRELESS = "wireless";
    public static final Parcelable.Creator<AudioDeviceDescription> CREATOR = new Parcelable.Creator<AudioDeviceDescription>() { // from class: android.media.audio.common.AudioDeviceDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceDescription createFromParcel(Parcel parcel) {
            AudioDeviceDescription audioDeviceDescription = new AudioDeviceDescription();
            audioDeviceDescription.readFromParcel(parcel);
            return audioDeviceDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDeviceDescription[] newArray(int i) {
            return new AudioDeviceDescription[i];
        }
    };
    public static final String VX_SEC_CONNECTION_FM = "fm";
    public String connection;
    public int type = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.connection);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.connection = parcel.readString();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("connection: " + Objects.toString(this.connection));
        return "AudioDeviceDescription" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioDeviceDescription)) {
            return false;
        }
        AudioDeviceDescription audioDeviceDescription = (AudioDeviceDescription) obj;
        return Objects.deepEquals(Integer.valueOf(this.type), Integer.valueOf(audioDeviceDescription.type)) && Objects.deepEquals(this.connection, audioDeviceDescription.connection);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.type), this.connection).toArray());
    }
}
