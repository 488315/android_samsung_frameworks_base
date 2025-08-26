package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SoundDoseRecord implements Parcelable {
    public static final Parcelable.Creator<SoundDoseRecord> CREATOR = new Parcelable.Creator<SoundDoseRecord>() { // from class: android.media.SoundDoseRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundDoseRecord createFromParcel(Parcel parcel) {
            SoundDoseRecord soundDoseRecord = new SoundDoseRecord();
            soundDoseRecord.readFromParcel(parcel);
            return soundDoseRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundDoseRecord[] newArray(int i) {
            return new SoundDoseRecord[i];
        }
    };
    public long timestamp = 0;
    public int duration = 0;
    public float value = 0.0f;
    public float averageMel = 0.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestamp);
        parcel.writeInt(this.duration);
        parcel.writeFloat(this.value);
        parcel.writeFloat(this.averageMel);
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
                this.timestamp = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.duration = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.value = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.averageMel = parcel.readFloat();
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
        stringJoiner.add("timestamp: " + this.timestamp);
        stringJoiner.add("duration: " + this.duration);
        stringJoiner.add("value: " + this.value);
        stringJoiner.add("averageMel: " + this.averageMel);
        return "SoundDoseRecord" + stringJoiner.toString();
    }
}
