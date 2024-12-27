package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public class RouteInfoParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String destination;
    public String ifName;
    public int mtu = 0;
    public String nextHop;

    /* renamed from: android.net.RouteInfoParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            RouteInfoParcel routeInfoParcel = new RouteInfoParcel();
            routeInfoParcel.readFromParcel(parcel);
            return routeInfoParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new RouteInfoParcel[i];
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
                this.destination = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.ifName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.nextHop = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mtu = parcel.readInt();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
                            }
                            parcel.setDataPosition(dataPosition + readInt);
                            return;
                        }
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

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.destination);
        parcel.writeString(this.ifName);
        parcel.writeString(this.nextHop);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.mtu, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
