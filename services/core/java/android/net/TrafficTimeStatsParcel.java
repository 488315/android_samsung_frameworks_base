package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class TrafficTimeStatsParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public long accumulatedWakeTime = 0;
    public long maxTxInterPacketTime = 0;
    public long minTxInterPacketTime = 0;
    public long maxRxInterPacketTime2 = 0;
    public long maxRxInterPacketTime = 0;

    /* renamed from: android.net.TrafficTimeStatsParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TrafficTimeStatsParcel trafficTimeStatsParcel = new TrafficTimeStatsParcel();
            trafficTimeStatsParcel.readFromParcel(parcel);
            return trafficTimeStatsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TrafficTimeStatsParcel[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.accumulatedWakeTime = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.maxTxInterPacketTime = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.minTxInterPacketTime = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxRxInterPacketTime2 = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.maxRxInterPacketTime = parcel.readLong();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                                parcel.setDataPosition(dataPosition + readInt);
                                return;
                            }
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.accumulatedWakeTime);
        parcel.writeLong(this.maxTxInterPacketTime);
        parcel.writeLong(this.minTxInterPacketTime);
        parcel.writeLong(this.maxRxInterPacketTime2);
        parcel.writeLong(this.maxRxInterPacketTime);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
