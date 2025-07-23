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
        int dataPosition = parcel.dataPosition();
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
                this.resourceName = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.dataType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.data = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.stringData = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.binaryData = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.configuration = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.binaryDataOffset = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.binaryDataSize = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.isNinePatch = parcel.readBoolean();
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
        return describeContents(this.binaryData);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
