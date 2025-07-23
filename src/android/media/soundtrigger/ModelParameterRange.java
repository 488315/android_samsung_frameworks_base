package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class ModelParameterRange implements Parcelable {
    public static final Parcelable.Creator<ModelParameterRange> CREATOR = new Parcelable.Creator<ModelParameterRange>() { // from class: android.media.soundtrigger.ModelParameterRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelParameterRange createFromParcel(Parcel parcel) {
            ModelParameterRange modelParameterRange = new ModelParameterRange();
            modelParameterRange.readFromParcel(parcel);
            return modelParameterRange;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ModelParameterRange[] newArray(int i) {
            return new ModelParameterRange[i];
        }
    };
    public int minInclusive = 0;
    public int maxInclusive = 0;

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
        parcel.writeInt(this.minInclusive);
        parcel.writeInt(this.maxInclusive);
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
                this.minInclusive = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.maxInclusive = parcel.readInt();
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
        stringJoiner.add("minInclusive: " + this.minInclusive);
        stringJoiner.add("maxInclusive: " + this.maxInclusive);
        return "ModelParameterRange" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ModelParameterRange)) {
            return false;
        }
        ModelParameterRange modelParameterRange = (ModelParameterRange) obj;
        return Objects.deepEquals(Integer.valueOf(this.minInclusive), Integer.valueOf(modelParameterRange.minInclusive)) && Objects.deepEquals(Integer.valueOf(this.maxInclusive), Integer.valueOf(modelParameterRange.maxInclusive));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.minInclusive), Integer.valueOf(this.maxInclusive)).toArray());
    }
}
