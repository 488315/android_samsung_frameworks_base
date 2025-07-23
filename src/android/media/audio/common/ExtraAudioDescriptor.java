package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ExtraAudioDescriptor implements Parcelable {
    public static final Parcelable.Creator<ExtraAudioDescriptor> CREATOR = new Parcelable.Creator<ExtraAudioDescriptor>() { // from class: android.media.audio.common.ExtraAudioDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtraAudioDescriptor createFromParcel(Parcel parcel) {
            ExtraAudioDescriptor extraAudioDescriptor = new ExtraAudioDescriptor();
            extraAudioDescriptor.readFromParcel(parcel);
            return extraAudioDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExtraAudioDescriptor[] newArray(int i) {
            return new ExtraAudioDescriptor[i];
        }
    };
    public byte[] audioDescriptor;
    public int standard = 0;
    public int encapsulationType = 0;

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
        parcel.writeInt(this.standard);
        parcel.writeByteArray(this.audioDescriptor);
        parcel.writeInt(this.encapsulationType);
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
                this.standard = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.audioDescriptor = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.encapsulationType = parcel.readInt();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        stringJoiner.add("standard: " + this.standard);
        stringJoiner.add("audioDescriptor: " + Arrays.toString(this.audioDescriptor));
        stringJoiner.add("encapsulationType: " + this.encapsulationType);
        return "ExtraAudioDescriptor" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ExtraAudioDescriptor)) {
            return false;
        }
        ExtraAudioDescriptor extraAudioDescriptor = (ExtraAudioDescriptor) obj;
        return Objects.deepEquals(Integer.valueOf(this.standard), Integer.valueOf(extraAudioDescriptor.standard)) && Objects.deepEquals(this.audioDescriptor, extraAudioDescriptor.audioDescriptor) && Objects.deepEquals(Integer.valueOf(this.encapsulationType), Integer.valueOf(extraAudioDescriptor.encapsulationType));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.standard), this.audioDescriptor, Integer.valueOf(this.encapsulationType)).toArray());
    }
}
