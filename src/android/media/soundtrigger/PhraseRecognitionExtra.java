package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class PhraseRecognitionExtra implements Parcelable {
    public static final Parcelable.Creator<PhraseRecognitionExtra> CREATOR = new Parcelable.Creator<PhraseRecognitionExtra>() { // from class: android.media.soundtrigger.PhraseRecognitionExtra.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionExtra createFromParcel(Parcel parcel) {
            PhraseRecognitionExtra phraseRecognitionExtra = new PhraseRecognitionExtra();
            phraseRecognitionExtra.readFromParcel(parcel);
            return phraseRecognitionExtra;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionExtra[] newArray(int i) {
            return new PhraseRecognitionExtra[i];
        }
    };
    public ConfidenceLevel[] levels;
    public int id = 0;
    public int recognitionModes = 0;
    public int confidenceLevel = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.recognitionModes);
        parcel.writeInt(this.confidenceLevel);
        parcel.writeTypedArray(this.levels, i);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.recognitionModes = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.confidenceLevel = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.levels = (ConfidenceLevel[]) parcel.createTypedArray(ConfidenceLevel.CREATOR);
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
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("recognitionModes: " + this.recognitionModes);
        stringJoiner.add("confidenceLevel: " + this.confidenceLevel);
        stringJoiner.add("levels: " + Arrays.toString(this.levels));
        return "PhraseRecognitionExtra" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhraseRecognitionExtra)) {
            return false;
        }
        PhraseRecognitionExtra phraseRecognitionExtra = (PhraseRecognitionExtra) obj;
        return Objects.deepEquals(Integer.valueOf(this.id), Integer.valueOf(phraseRecognitionExtra.id)) && Objects.deepEquals(Integer.valueOf(this.recognitionModes), Integer.valueOf(phraseRecognitionExtra.recognitionModes)) && Objects.deepEquals(Integer.valueOf(this.confidenceLevel), Integer.valueOf(phraseRecognitionExtra.confidenceLevel)) && Objects.deepEquals(this.levels, phraseRecognitionExtra.levels);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.id), Integer.valueOf(this.recognitionModes), Integer.valueOf(this.confidenceLevel), this.levels).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.levels);
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
