package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioHalCapCriterion implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapCriterion> CREATOR = new Parcelable.Creator<AudioHalCapCriterion>() { // from class: android.media.audio.common.AudioHalCapCriterion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterion createFromParcel(Parcel parcel) {
            AudioHalCapCriterion audioHalCapCriterion = new AudioHalCapCriterion();
            audioHalCapCriterion.readFromParcel(parcel);
            return audioHalCapCriterion;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterion[] newArray(int i) {
            return new AudioHalCapCriterion[i];
        }
    };
    public String criterionTypeName;
    public String defaultLiteralValue;
    public String name;

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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeString(this.criterionTypeName);
        parcel.writeString(this.defaultLiteralValue);
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
                    this.criterionTypeName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.defaultLiteralValue = parcel.readString();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
        stringJoiner.add("criterionTypeName: " + Objects.toString(this.criterionTypeName));
        stringJoiner.add("defaultLiteralValue: " + Objects.toString(this.defaultLiteralValue));
        return "AudioHalCapCriterion" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioHalCapCriterion)) {
            return false;
        }
        AudioHalCapCriterion audioHalCapCriterion = (AudioHalCapCriterion) obj;
        return Objects.deepEquals(this.name, audioHalCapCriterion.name) && Objects.deepEquals(this.criterionTypeName, audioHalCapCriterion.criterionTypeName) && Objects.deepEquals(this.defaultLiteralValue, audioHalCapCriterion.defaultLiteralValue);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.name, this.criterionTypeName, this.defaultLiteralValue).toArray());
    }
}
