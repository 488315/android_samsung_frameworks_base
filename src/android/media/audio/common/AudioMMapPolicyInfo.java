package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioMMapPolicyInfo implements Parcelable {
    public static final Parcelable.Creator<AudioMMapPolicyInfo> CREATOR = new Parcelable.Creator<AudioMMapPolicyInfo>() { // from class: android.media.audio.common.AudioMMapPolicyInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMMapPolicyInfo createFromParcel(Parcel parcel) {
            AudioMMapPolicyInfo audioMMapPolicyInfo = new AudioMMapPolicyInfo();
            audioMMapPolicyInfo.readFromParcel(parcel);
            return audioMMapPolicyInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioMMapPolicyInfo[] newArray(int i) {
            return new AudioMMapPolicyInfo[i];
        }
    };
    public AudioDevice device;
    public int mmapPolicy = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.device, i);
        parcel.writeInt(this.mmapPolicy);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.device = (AudioDevice) parcel.readTypedObject(AudioDevice.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mmapPolicy = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("device: " + Objects.toString(this.device));
        stringJoiner.add("mmapPolicy: " + this.mmapPolicy);
        return "AudioMMapPolicyInfo" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioMMapPolicyInfo)) {
            return false;
        }
        AudioMMapPolicyInfo audioMMapPolicyInfo = (AudioMMapPolicyInfo) obj;
        return Objects.deepEquals(this.device, audioMMapPolicyInfo.device) && Objects.deepEquals(Integer.valueOf(this.mmapPolicy), Integer.valueOf(audioMMapPolicyInfo.mmapPolicy));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.device, Integer.valueOf(this.mmapPolicy)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.device);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
