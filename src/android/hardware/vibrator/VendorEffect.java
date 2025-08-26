package android.hardware.vibrator;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

/* loaded from: classes2.dex */
public class VendorEffect implements Parcelable {
    public static final Parcelable.Creator<VendorEffect> CREATOR = new Parcelable.Creator<VendorEffect>() { // from class: android.hardware.vibrator.VendorEffect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorEffect createFromParcel(Parcel parcel) {
            VendorEffect vendorEffect = new VendorEffect();
            vendorEffect.readFromParcel(parcel);
            return vendorEffect;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorEffect[] newArray(int i) {
            return new VendorEffect[i];
        }
    };
    public PersistableBundle vendorData;
    public byte strength = 1;
    public float scale = 0.0f;
    public float vendorScale = 0.0f;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.vendorData, i);
        parcel.writeByte(this.strength);
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.vendorScale);
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
                this.vendorData = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.strength = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.scale = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.vendorScale = parcel.readFloat();
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
        return describeContents(this.vendorData);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
