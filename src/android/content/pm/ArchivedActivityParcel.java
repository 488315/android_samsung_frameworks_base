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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.title);
        parcel.writeTypedObject(this.originalComponentName, i);
        parcel.writeByteArray(this.iconBitmap);
        parcel.writeByteArray(this.monochromeIconBitmap);
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
                this.title = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.originalComponentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.iconBitmap = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.monochromeIconBitmap = parcel.createByteArray();
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
        return describeContents(this.originalComponentName);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
