package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class TetherStatsParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String iface;
    public long rxBytes = 0;
    public long rxPackets = 0;
    public long txBytes = 0;
    public long txPackets = 0;
    public int ifIndex = 0;

    /* renamed from: android.net.TetherStatsParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TetherStatsParcel tetherStatsParcel = new TetherStatsParcel();
            tetherStatsParcel.readFromParcel(parcel);
            return tetherStatsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TetherStatsParcel[i];
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
                this.iface = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.rxBytes = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.rxPackets = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.txBytes = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.txPackets = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ifIndex = parcel.readInt();
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
        parcel.writeString(this.iface);
        parcel.writeLong(this.rxBytes);
        parcel.writeLong(this.rxPackets);
        parcel.writeLong(this.txBytes);
        parcel.writeLong(this.txPackets);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.ifIndex, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
