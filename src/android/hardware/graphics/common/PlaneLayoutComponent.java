package android.hardware.graphics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PlaneLayoutComponent implements Parcelable {
    public static final Parcelable.Creator<PlaneLayoutComponent> CREATOR = new Parcelable.Creator<PlaneLayoutComponent>() { // from class: android.hardware.graphics.common.PlaneLayoutComponent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaneLayoutComponent createFromParcel(Parcel parcel) {
            PlaneLayoutComponent planeLayoutComponent = new PlaneLayoutComponent();
            planeLayoutComponent.readFromParcel(parcel);
            return planeLayoutComponent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaneLayoutComponent[] newArray(int i) {
            return new PlaneLayoutComponent[i];
        }
    };
    public long offsetInBits = 0;
    public long sizeInBits = 0;
    public ExtendableType type;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeLong(this.offsetInBits);
        parcel.writeLong(this.sizeInBits);
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
                this.type = (ExtendableType) parcel.readTypedObject(ExtendableType.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.offsetInBits = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sizeInBits = parcel.readLong();
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
    public int describeContents() {
        return describeContents(this.type);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
