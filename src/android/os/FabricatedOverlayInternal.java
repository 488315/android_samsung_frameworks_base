package android.os;

import android.os.Parcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class FabricatedOverlayInternal implements Parcelable {
    public static final Parcelable.Creator<FabricatedOverlayInternal> CREATOR = new Parcelable.Creator<FabricatedOverlayInternal>() { // from class: android.os.FabricatedOverlayInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInternal createFromParcel(Parcel parcel) {
            FabricatedOverlayInternal fabricatedOverlayInternal = new FabricatedOverlayInternal();
            fabricatedOverlayInternal.readFromParcel(parcel);
            return fabricatedOverlayInternal;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInternal[] newArray(int i) {
            return new FabricatedOverlayInternal[i];
        }
    };
    public List<FabricatedOverlayInternalEntry> entries;
    public String overlayName;
    public String packageName;
    public String targetOverlayable;
    public String targetPackageName;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.packageName);
        parcel.writeString(this.overlayName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayable);
        parcel.writeTypedList(this.entries, i);
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
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.overlayName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.targetPackageName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.targetOverlayable = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.entries = parcel.createTypedArrayList(FabricatedOverlayInternalEntry.CREATOR);
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
        return describeContents(this.entries);
    }

    private int describeContents(Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
