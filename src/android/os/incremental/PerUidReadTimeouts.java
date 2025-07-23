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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.minTimeUs);
        parcel.writeLong(this.minPendingTimeUs);
        parcel.writeLong(this.maxPendingTimeUs);
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
                this.uid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.minTimeUs = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.minPendingTimeUs = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxPendingTimeUs = parcel.readLong();
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
}
