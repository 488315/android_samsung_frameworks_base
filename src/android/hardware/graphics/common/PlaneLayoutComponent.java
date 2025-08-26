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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.type, i);
        parcel.writeLong(this.offsetInBits);
        parcel.writeLong(this.sizeInBits);
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
                this.type = (ExtendableType) parcel.readTypedObject(ExtendableType.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.offsetInBits = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sizeInBits = parcel.readLong();
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
        return describeContents(this.type);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
