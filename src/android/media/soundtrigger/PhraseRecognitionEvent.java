package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class PhraseRecognitionEvent implements Parcelable {
    public static final Parcelable.Creator<PhraseRecognitionEvent> CREATOR = new Parcelable.Creator<PhraseRecognitionEvent>() { // from class: android.media.soundtrigger.PhraseRecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEvent createFromParcel(Parcel parcel) {
            PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
            phraseRecognitionEvent.readFromParcel(parcel);
            return phraseRecognitionEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEvent[] newArray(int i) {
            return new PhraseRecognitionEvent[i];
        }
    };
    public RecognitionEvent common;
    public PhraseRecognitionExtra[] phraseExtras;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.common, i);
        parcel.writeTypedArray(this.phraseExtras, i);
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
                this.common = (RecognitionEvent) parcel.readTypedObject(RecognitionEvent.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.phraseExtras = (PhraseRecognitionExtra[]) parcel.createTypedArray(PhraseRecognitionExtra.CREATOR);
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
        stringJoiner.add("phraseExtras: " + Arrays.toString(this.phraseExtras));
        return "PhraseRecognitionEvent" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhraseRecognitionEvent)) {
            return false;
        }
        PhraseRecognitionEvent phraseRecognitionEvent = (PhraseRecognitionEvent) obj;
        return Objects.deepEquals(this.common, phraseRecognitionEvent.common) && Objects.deepEquals(this.phraseExtras, phraseRecognitionEvent.phraseExtras);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.common, this.phraseExtras).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phraseExtras) | describeContents(this.common);
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
