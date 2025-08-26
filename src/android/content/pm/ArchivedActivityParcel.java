package android.content.pm;

import android.content.ComponentName;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ArchivedActivityParcel implements Parcelable {
    public static final Parcelable.Creator<ArchivedActivityParcel> CREATOR = new Parcelable.Creator<ArchivedActivityParcel>() { // from class: android.content.pm.ArchivedActivityParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ArchivedActivityParcel createFromParcel(Parcel parcel) {
            ArchivedActivityParcel archivedActivityParcel = new ArchivedActivityParcel();
            archivedActivityParcel.readFromParcel(parcel);
            return archivedActivityParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ArchivedActivityParcel[] newArray(int i) {
            return new ArchivedActivityParcel[i];
        }
    };
    public byte[] iconBitmap;
    public byte[] monochromeIconBitmap;
    public ComponentName originalComponentName;
    public String title;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.title);
        parcel.writeTypedObject(this.originalComponentName, i);
        parcel.writeByteArray(this.iconBitmap);
        parcel.writeByteArray(this.monochromeIconBitmap);
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
                this.title = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.originalComponentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.iconBitmap = parcel.createByteArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.monochromeIconBitmap = parcel.createByteArray();
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
        return describeContents(this.originalComponentName);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
