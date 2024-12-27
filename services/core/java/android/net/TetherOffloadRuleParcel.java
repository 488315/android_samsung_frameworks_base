package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.net.util.NetworkConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class TetherOffloadRuleParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public byte[] destination;
    public byte[] dstL2Address;
    public byte[] srcL2Address;
    public int inputInterfaceIndex = 0;
    public int outputInterfaceIndex = 0;
    public int prefixLength = 0;
    public int pmtu = NetworkConstants.ETHER_MTU;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.TetherOffloadRuleParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TetherOffloadRuleParcel tetherOffloadRuleParcel = new TetherOffloadRuleParcel();
            tetherOffloadRuleParcel.readFromParcel(parcel);
            return tetherOffloadRuleParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TetherOffloadRuleParcel[i];
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
                this.inputInterfaceIndex = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.outputInterfaceIndex = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.destination = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.prefixLength = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.srcL2Address = parcel.createByteArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.dstL2Address = parcel.createByteArray();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.pmtu = parcel.readInt();
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
        parcel.writeInt(this.inputInterfaceIndex);
        parcel.writeInt(this.outputInterfaceIndex);
        parcel.writeByteArray(this.destination);
        parcel.writeInt(this.prefixLength);
        parcel.writeByteArray(this.srcL2Address);
        parcel.writeByteArray(this.dstL2Address);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.pmtu, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
