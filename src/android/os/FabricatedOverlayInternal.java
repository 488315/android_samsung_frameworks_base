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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.packageName);
        parcel.writeString(this.overlayName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayable);
        parcel.writeTypedList(this.entries, i);
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
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.overlayName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.targetPackageName = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.targetOverlayable = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.entries = parcel.createTypedArrayList(FabricatedOverlayInternalEntry.CREATOR);
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
        return describeContents(this.entries);
    }

    private int describeContents(Object obj) {
        int iDescribeContents = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                iDescribeContents |= describeContents(it.next());
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
