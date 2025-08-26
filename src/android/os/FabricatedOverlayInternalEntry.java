package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class FabricatedOverlayInternalEntry implements Parcelable {
    public static final Parcelable.Creator<FabricatedOverlayInternalEntry> CREATOR = new Parcelable.Creator<FabricatedOverlayInternalEntry>() { // from class: android.os.FabricatedOverlayInternalEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInternalEntry createFromParcel(Parcel parcel) {
            FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new FabricatedOverlayInternalEntry();
            fabricatedOverlayInternalEntry.readFromParcel(parcel);
            return fabricatedOverlayInternalEntry;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FabricatedOverlayInternalEntry[] newArray(int i) {
            return new FabricatedOverlayInternalEntry[i];
        }
    };
    public ParcelFileDescriptor binaryData;
    public String configuration;
    public String resourceName;
    public String stringData;
    public int dataType = 0;
    public int data = 0;
    public long binaryDataOffset = 0;
    public long binaryDataSize = 0;
    public boolean isNinePatch = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.resourceName);
        parcel.writeInt(this.dataType);
        parcel.writeInt(this.data);
        parcel.writeString(this.stringData);
        parcel.writeTypedObject(this.binaryData, i);
        parcel.writeString(this.configuration);
        parcel.writeLong(this.binaryDataOffset);
        parcel.writeLong(this.binaryDataSize);
        parcel.writeBoolean(this.isNinePatch);
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
                this.resourceName = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.dataType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.data = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.stringData = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.binaryData = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.configuration = parcel.readString();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.binaryDataOffset = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.binaryDataSize = parcel.readLong();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.isNinePatch = parcel.readBoolean();
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
        return describeContents(this.binaryData);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
