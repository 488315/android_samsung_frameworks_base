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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.nanoappVersion);
        parcel.writeBoolean(this.enabled);
        parcel.writeStringArray(this.permissions);
        parcel.writeTypedArray(this.rpcServices, i);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.nanoappVersion = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.enabled = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.permissions = parcel.createStringArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.rpcServices = (NanoappRpcService[]) parcel.createTypedArray(NanoappRpcService.CREATOR);
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
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
