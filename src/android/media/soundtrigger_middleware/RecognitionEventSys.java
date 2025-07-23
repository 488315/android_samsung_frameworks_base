package android.media.soundtrigger_middleware;

import android.media.soundtrigger.RecognitionEvent;
import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class RecognitionEventSys implements Parcelable {
    public static final Parcelable.Creator<RecognitionEventSys> CREATOR = new Parcelable.Creator<RecognitionEventSys>() { // from class: android.media.soundtrigger_middleware.RecognitionEventSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionEventSys createFromParcel(Parcel parcel) {
            RecognitionEventSys recognitionEventSys = new RecognitionEventSys();
            recognitionEventSys.readFromParcel(parcel);
            return recognitionEventSys;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionEventSys[] newArray(int i) {
            return new RecognitionEventSys[i];
        }
    };
    public long halEventReceivedMillis = -1;
    public RecognitionEvent recognitionEvent;
    public IBinder token;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.recognitionEvent, i);
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
                this.recognitionEvent = (RecognitionEvent) parcel.readTypedObject(RecognitionEvent.CREATOR);
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
        stringJoiner.add("recognitionEvent: " + Objects.toString(this.recognitionEvent));
        stringJoiner.add("halEventReceivedMillis: " + this.halEventReceivedMillis);
        stringJoiner.add("token: " + Objects.toString(this.token));
        return "RecognitionEventSys" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RecognitionEventSys)) {
            return false;
        }
        RecognitionEventSys recognitionEventSys = (RecognitionEventSys) obj;
        return Objects.deepEquals(this.recognitionEvent, recognitionEventSys.recognitionEvent) && Objects.deepEquals(Long.valueOf(this.halEventReceivedMillis), Long.valueOf(recognitionEventSys.halEventReceivedMillis)) && Objects.deepEquals(this.token, recognitionEventSys.token);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.recognitionEvent, Long.valueOf(this.halEventReceivedMillis), this.token).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.recognitionEvent);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
