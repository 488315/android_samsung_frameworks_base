package android.hardware.graphics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HardwareBufferDescription implements Parcelable {
    public static final Parcelable.Creator<HardwareBufferDescription> CREATOR = new Parcelable.Creator<HardwareBufferDescription>() { // from class: android.hardware.graphics.common.HardwareBufferDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareBufferDescription createFromParcel(Parcel parcel) {
            HardwareBufferDescription hardwareBufferDescription = new HardwareBufferDescription();
            hardwareBufferDescription.readFromParcel(parcel);
            return hardwareBufferDescription;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareBufferDescription[] newArray(int i) {
            return new HardwareBufferDescription[i];
        }
    };
    public int width = 0;
    public int height = 0;
    public int layers = 0;
    public int format = 0;
    public long usage = 0;
    public int stride = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.layers);
        parcel.writeInt(this.format);
        parcel.writeLong(this.usage);
        parcel.writeInt(this.stride);
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
                this.width = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.height = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.layers = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.format = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.usage = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.stride = parcel.readInt();
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
}
