package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalCapDomain implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapDomain> CREATOR = new Parcelable.Creator<AudioHalCapDomain>() { // from class: android.media.audio.common.AudioHalCapDomain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapDomain createFromParcel(Parcel parcel) {
            AudioHalCapDomain audioHalCapDomain = new AudioHalCapDomain();
            audioHalCapDomain.readFromParcel(parcel);
            return audioHalCapDomain;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapDomain[] newArray(int i) {
            return new AudioHalCapDomain[i];
        }
    };
    public AudioHalCapConfiguration[] configurations;
    public String name;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeTypedArray(this.configurations, i);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.configurations = (AudioHalCapConfiguration[]) parcel.createTypedArray(AudioHalCapConfiguration.CREATOR);
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
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("configurations: " + Arrays.toString(this.configurations));
        return "AudioHalCapDomain" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalCapDomain)) {
            return false;
        }
        AudioHalCapDomain audioHalCapDomain = (AudioHalCapDomain) obj;
        return Objects.deepEquals(this.name, audioHalCapDomain.name) && Objects.deepEquals(this.configurations, audioHalCapDomain.configurations);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, this.configurations).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.configurations);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
