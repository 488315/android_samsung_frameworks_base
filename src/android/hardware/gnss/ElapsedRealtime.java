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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeLong(this.timestampNs);
        parcel.writeDouble(this.timeUncertaintyNs);
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
                this.flags = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.timestampNs = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.timeUncertaintyNs = parcel.readDouble();
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
}
