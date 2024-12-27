package android.hardware.security.keymint;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RpcHardwareInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String rpcAuthorName;
    public int supportedEekCurve;
    public int supportedNumKeysInCsr;
    public String uniqueId;
    public int versionNumber;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.security.keymint.RpcHardwareInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            RpcHardwareInfo rpcHardwareInfo = new RpcHardwareInfo();
            rpcHardwareInfo.versionNumber = 0;
            rpcHardwareInfo.supportedEekCurve = 0;
            rpcHardwareInfo.supportedNumKeysInCsr = 4;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    rpcHardwareInfo.versionNumber = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        rpcHardwareInfo.rpcAuthorName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            rpcHardwareInfo.supportedEekCurve = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                rpcHardwareInfo.uniqueId = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    rpcHardwareInfo.supportedNumKeysInCsr = parcel.readInt();
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
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return rpcHardwareInfo;
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
            return new RpcHardwareInfo[i];
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
        parcel.writeInt(this.versionNumber);
        parcel.writeString(this.rpcAuthorName);
        parcel.writeInt(this.supportedEekCurve);
        parcel.writeString(this.uniqueId);
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.supportedNumKeysInCsr, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
