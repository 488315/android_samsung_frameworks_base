package android.hardware.common.fmq;

import android.hardware.common.NativeHandle;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class MQDescriptor<T, Flavor> implements Parcelable {
    public static final Parcelable.Creator<MQDescriptor> CREATOR = new Parcelable.Creator<MQDescriptor>() { // from class: android.hardware.common.fmq.MQDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MQDescriptor createFromParcel(Parcel parcel) {
            MQDescriptor mQDescriptor = new MQDescriptor();
            mQDescriptor.readFromParcel(parcel);
            return mQDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MQDescriptor[] newArray(int i) {
            return new MQDescriptor[i];
        }
    };
    public GrantorDescriptor[] grantors;
    public NativeHandle handle;
    public int quantum = 0;
    public int flags = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.grantors, i);
        parcel.writeTypedObject(this.handle, i);
        parcel.writeInt(this.quantum);
        parcel.writeInt(this.flags);
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
                this.grantors = (GrantorDescriptor[]) parcel.createTypedArray(GrantorDescriptor.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.handle = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.quantum = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.flags = parcel.readInt();
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
        return describeContents(this.handle) | describeContents(this.grantors);
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
