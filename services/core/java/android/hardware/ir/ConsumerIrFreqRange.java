package android.hardware.ir;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public final class ConsumerIrFreqRange implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int maxHz;
    public int minHz;

    /* renamed from: android.hardware.ir.ConsumerIrFreqRange$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ConsumerIrFreqRange consumerIrFreqRange = new ConsumerIrFreqRange();
            consumerIrFreqRange.minHz = 0;
            consumerIrFreqRange.maxHz = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    consumerIrFreqRange.minHz = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        consumerIrFreqRange.maxHz = parcel.readInt();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return consumerIrFreqRange;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ConsumerIrFreqRange[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.minHz);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.maxHz, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
