package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalCapCriterionType implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapCriterionType> CREATOR = new Parcelable.Creator<AudioHalCapCriterionType>() { // from class: android.media.audio.common.AudioHalCapCriterionType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterionType createFromParcel(Parcel parcel) {
            AudioHalCapCriterionType audioHalCapCriterionType = new AudioHalCapCriterionType();
            audioHalCapCriterionType.readFromParcel(parcel);
            return audioHalCapCriterionType;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterionType[] newArray(int i) {
            return new AudioHalCapCriterionType[i];
        }
    };
    public boolean isInclusive = false;
    public String name;
    public String[] values;

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
        parcel.writeString(this.name);
        parcel.writeBoolean(this.isInclusive);
        parcel.writeStringArray(this.values);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.isInclusive = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.values = parcel.createStringArray();
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
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("isInclusive: " + this.isInclusive);
        stringJoiner.add("values: " + Arrays.toString(this.values));
        return "AudioHalCapCriterionType" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalCapCriterionType)) {
            return false;
        }
        AudioHalCapCriterionType audioHalCapCriterionType = (AudioHalCapCriterionType) obj;
        return Objects.deepEquals(this.name, audioHalCapCriterionType.name) && Objects.deepEquals(java.lang.Boolean.valueOf(this.isInclusive), java.lang.Boolean.valueOf(audioHalCapCriterionType.isInclusive)) && Objects.deepEquals(this.values, audioHalCapCriterionType.values);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, java.lang.Boolean.valueOf(this.isInclusive), this.values).toArray());
    }
}
