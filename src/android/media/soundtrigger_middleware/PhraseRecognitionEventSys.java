package android.media.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class PhraseRecognitionEventSys implements Parcelable {
    public static final Parcelable.Creator<PhraseRecognitionEventSys> CREATOR = new Parcelable.Creator<PhraseRecognitionEventSys>() { // from class: android.media.soundtrigger_middleware.PhraseRecognitionEventSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEventSys createFromParcel(Parcel parcel) {
            PhraseRecognitionEventSys phraseRecognitionEventSys = new PhraseRecognitionEventSys();
            phraseRecognitionEventSys.readFromParcel(parcel);
            return phraseRecognitionEventSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhraseRecognitionEventSys[] newArray(int i) {
            return new PhraseRecognitionEventSys[i];
        }
    };
    public long halEventReceivedMillis = -1;
    public PhraseRecognitionEvent phraseRecognitionEvent;
    public IBinder token;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.phraseRecognitionEvent, i);
        parcel.writeLong(this.halEventReceivedMillis);
        parcel.writeStrongBinder(this.token);
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
                this.phraseRecognitionEvent = (PhraseRecognitionEvent) parcel.readTypedObject(PhraseRecognitionEvent.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.halEventReceivedMillis = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.token = parcel.readStrongBinder();
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
        stringJoiner.add("phraseRecognitionEvent: " + Objects.toString(this.phraseRecognitionEvent));
        stringJoiner.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        stringJoiner.add("token: " + Objects.toString(this.token));
        return "PhraseRecognitionEventSys" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PhraseRecognitionEventSys)) {
            return false;
        }
        PhraseRecognitionEventSys phraseRecognitionEventSys = (PhraseRecognitionEventSys) obj;
        return Objects.deepEquals(this.phraseRecognitionEvent, phraseRecognitionEventSys.phraseRecognitionEvent) && Objects.deepEquals(Long.valueOf(this.halEventReceivedMillis), Long.valueOf(phraseRecognitionEventSys.halEventReceivedMillis)) && Objects.deepEquals(this.token, phraseRecognitionEventSys.token);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.phraseRecognitionEvent, Long.valueOf(this.halEventReceivedMillis), this.token).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.phraseRecognitionEvent);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
