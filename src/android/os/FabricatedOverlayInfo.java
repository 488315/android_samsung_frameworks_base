package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class FabricatedOverlayInfo implements Parcelable {
    public static final Parcelable.Creator<FabricatedOverlayInfo> CREATOR = new Parcelable.Creator<FabricatedOverlayInfo>() { // from class: android.os.FabricatedOverlayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInfo createFromParcel(Parcel parcel) {
            FabricatedOverlayInfo fabricatedOverlayInfo = new FabricatedOverlayInfo();
            fabricatedOverlayInfo.readFromParcel(parcel);
            return fabricatedOverlayInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInfo[] newArray(int i) {
            return new FabricatedOverlayInfo[i];
        }
    };
    public String overlayName;
    public String packageName;
    public String path;
    public String targetOverlayable;
    public String targetPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.path);
        parcel.writeString(this.packageName);
        parcel.writeString(this.overlayName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayable);
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
                this.path = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.overlayName = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.targetPackageName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.targetOverlayable = parcel.readString();
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
}
