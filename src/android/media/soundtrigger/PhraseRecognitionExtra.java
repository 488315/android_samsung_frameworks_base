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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.recognitionModes);
        parcel.writeInt(this.confidenceLevel);
        parcel.writeTypedArray(this.levels, i);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.recognitionModes = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.confidenceLevel = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.levels = (ConfidenceLevel[]) parcel.createTypedArray(ConfidenceLevel.CREATOR);
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
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
