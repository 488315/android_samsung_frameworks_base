package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class NanoappInfo implements Parcelable {
    public static final Parcelable.Creator<NanoappInfo> CREATOR = new Parcelable.Creator<NanoappInfo>() { // from class: android.hardware.contexthub.NanoappInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoappInfo createFromParcel(Parcel parcel) {
            NanoappInfo nanoappInfo = new NanoappInfo();
            nanoappInfo.readFromParcel(parcel);
            return nanoappInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoappInfo[] newArray(int i) {
            return new NanoappInfo[i];
        }
    };
    public String[] permissions;
    public NanoappRpcService[] rpcServices;
    public long nanoappId = 0;
    public int nanoappVersion = 0;
    public boolean enabled = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.nanoappVersion);
        parcel.writeBoolean(this.enabled);
        parcel.writeStringArray(this.permissions);
        parcel.writeTypedArray(this.rpcServices, i);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.nanoappVersion = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.enabled = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.permissions = parcel.createStringArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.rpcServices = (NanoappRpcService[]) parcel.createTypedArray(NanoappRpcService.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.rpcServices);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
