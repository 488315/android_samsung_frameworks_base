package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableHolder;

/* loaded from: classes2.dex */
public class Service implements Parcelable {
    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() { // from class: android.hardware.contexthub.Service.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Service createFromParcel(Parcel parcel) {
            Service service = new Service();
            service.readFromParcel(parcel);
            return service;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Service[] newArray(int i) {
            return new Service[i];
        }
    };
    public int format;
    public String serviceDescriptor;
    public int majorVersion = 0;
    public int minorVersion = 0;
    public final ParcelableHolder extendedInfo = new ParcelableHolder(1);

    public @interface RpcFormat {
        public static final int AIDL = 1;
        public static final int CUSTOM = 0;
        public static final int PW_RPC_PROTOBUF = 2;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.format);
        parcel.writeString(this.serviceDescriptor);
        parcel.writeInt(this.majorVersion);
        parcel.writeInt(this.minorVersion);
        parcel.writeTypedObject(this.extendedInfo, 0);
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
                this.format = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.serviceDescriptor = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.majorVersion = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.minorVersion = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                if (parcel.readInt() != 0) {
                                    this.extendedInfo.readFromParcel(parcel);
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
        return describeContents(this.extendedInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
