package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ElapsedRealtime implements Parcelable {
    public static final Parcelable.Creator<ElapsedRealtime> CREATOR = new Parcelable.Creator<ElapsedRealtime>() { // from class: android.hardware.gnss.ElapsedRealtime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ElapsedRealtime createFromParcel(Parcel parcel) {
            ElapsedRealtime elapsedRealtime = new ElapsedRealtime();
            elapsedRealtime.readFromParcel(parcel);
            return elapsedRealtime;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ElapsedRealtime[] newArray(int i) {
            return new ElapsedRealtime[i];
        }
    };
    public static final int HAS_TIMESTAMP_NS = 1;
    public static final int HAS_TIME_UNCERTAINTY_NS = 2;
    public int flags = 0;
    public long timestampNs = 0;
    public double timeUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

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
        parcel.writeInt(this.flags);
        parcel.writeLong(this.timestampNs);
        parcel.writeDouble(this.timeUncertaintyNs);
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
                this.flags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.timestampNs = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.timeUncertaintyNs = parcel.readDouble();
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
}
