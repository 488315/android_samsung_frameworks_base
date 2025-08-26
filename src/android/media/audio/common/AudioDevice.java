package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioDevice implements Parcelable {
    public static final Parcelable.Creator<AudioDevice> CREATOR = new Parcelable.Creator<AudioDevice>() { // from class: android.media.audio.common.AudioDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDevice createFromParcel(Parcel parcel) {
            AudioDevice audioDevice = new AudioDevice();
            audioDevice.readFromParcel(parcel);
            return audioDevice;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioDevice[] newArray(int i) {
            return new AudioDevice[i];
        }
    };
    public AudioDeviceAddress address;
    public AudioDeviceDescription type;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeTypedObject(this.address, i);
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
                this.type = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.address = (AudioDeviceAddress) parcel.readTypedObject(AudioDeviceAddress.CREATOR);
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
        stringJoiner.add("type: " + Objects.toString(this.type));
        stringJoiner.add("address: " + Objects.toString(this.address));
        return "AudioDevice" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioDevice)) {
            return false;
        }
        AudioDevice audioDevice = (AudioDevice) obj;
        return Objects.deepEquals(this.type, audioDevice.type) && Objects.deepEquals(this.address, audioDevice.address);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.type, this.address).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.address) | describeContents(this.type);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
