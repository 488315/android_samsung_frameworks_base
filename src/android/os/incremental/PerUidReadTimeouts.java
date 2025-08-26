package android.os.incremental;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class PerUidReadTimeouts implements Parcelable {
    public static final Parcelable.Creator<PerUidReadTimeouts> CREATOR = new Parcelable.Creator<PerUidReadTimeouts>() { // from class: android.os.incremental.PerUidReadTimeouts.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PerUidReadTimeouts createFromParcel(Parcel parcel) {
            PerUidReadTimeouts perUidReadTimeouts = new PerUidReadTimeouts();
            perUidReadTimeouts.readFromParcel(parcel);
            return perUidReadTimeouts;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PerUidReadTimeouts[] newArray(int i) {
            return new PerUidReadTimeouts[i];
        }
    };
    public int uid = 0;
    public long minTimeUs = 0;
    public long minPendingTimeUs = 0;
    public long maxPendingTimeUs = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.minTimeUs);
        parcel.writeLong(this.minPendingTimeUs);
        parcel.writeLong(this.maxPendingTimeUs);
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
                this.uid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.minTimeUs = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.minPendingTimeUs = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxPendingTimeUs = parcel.readLong();
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
}
