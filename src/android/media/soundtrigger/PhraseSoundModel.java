package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class PhraseSoundModel implements Parcelable {
    public static final Parcelable.Creator<PhraseSoundModel> CREATOR = new Parcelable.Creator<PhraseSoundModel>() { // from class: android.media.soundtrigger.PhraseSoundModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseSoundModel createFromParcel(Parcel parcel) {
            PhraseSoundModel phraseSoundModel = new PhraseSoundModel();
            phraseSoundModel.readFromParcel(parcel);
            return phraseSoundModel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseSoundModel[] newArray(int i) {
            return new PhraseSoundModel[i];
        }
    };
    public SoundModel common;
    public Phrase[] phrases;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.common, i);
        parcel.writeTypedArray(this.phrases, i);
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
                this.common = (SoundModel) parcel.readTypedObject(SoundModel.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.phrases = (Phrase[]) parcel.createTypedArray(Phrase.CREATOR);
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
        stringJoiner.add("common: " + Objects.toString(this.common));
        stringJoiner.add("phrases: " + Arrays.toString(this.phrases));
        return "PhraseSoundModel" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhraseSoundModel)) {
            return false;
        }
        PhraseSoundModel phraseSoundModel = (PhraseSoundModel) obj;
        return Objects.deepEquals(this.common, phraseSoundModel.common) && Objects.deepEquals(this.phrases, phraseSoundModel.phrases);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.common, this.phrases).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phrases) | describeContents(this.common);
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
