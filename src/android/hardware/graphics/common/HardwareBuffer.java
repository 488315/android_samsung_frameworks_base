package android.hardware.graphics.common;

import android.hardware.common.NativeHandle;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HardwareBuffer implements Parcelable {
    public static final Parcelable.Creator<HardwareBuffer> CREATOR = new Parcelable.Creator<HardwareBuffer>() { // from class: android.hardware.graphics.common.HardwareBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareBuffer createFromParcel(Parcel parcel) {
            HardwareBuffer hardwareBuffer = new HardwareBuffer();
            hardwareBuffer.readFromParcel(parcel);
            return hardwareBuffer;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareBuffer[] newArray(int i) {
            return new HardwareBuffer[i];
        }
    };
    public HardwareBufferDescription description;
    public NativeHandle handle;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.description, i);
        parcel.writeTypedObject(this.handle, i);
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
                this.description = (HardwareBufferDescription) parcel.readTypedObject(HardwareBufferDescription.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.handle = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
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
        return describeContents(this.handle) | describeContents(this.description);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
