package android.os.incremental;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class IncrementalFileSystemControlParcel implements Parcelable {
    public static final Parcelable.Creator<IncrementalFileSystemControlParcel> CREATOR = new Parcelable.Creator<IncrementalFileSystemControlParcel>() { // from class: android.os.incremental.IncrementalFileSystemControlParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalFileSystemControlParcel createFromParcel(Parcel parcel) {
            IncrementalFileSystemControlParcel incrementalFileSystemControlParcel = new IncrementalFileSystemControlParcel();
            incrementalFileSystemControlParcel.readFromParcel(parcel);
            return incrementalFileSystemControlParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalFileSystemControlParcel[] newArray(int i) {
            return new IncrementalFileSystemControlParcel[i];
        }
    };
    public ParcelFileDescriptor blocksWritten;
    public ParcelFileDescriptor cmd;
    public ParcelFileDescriptor log;
    public ParcelFileDescriptor pendingReads;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cmd, i);
        parcel.writeTypedObject(this.pendingReads, i);
        parcel.writeTypedObject(this.log, i);
        parcel.writeTypedObject(this.blocksWritten, i);
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
                this.cmd = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.pendingReads = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.log = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.blocksWritten = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
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
        return describeContents(this.blocksWritten) | describeContents(this.cmd) | describeContents(this.pendingReads) | describeContents(this.log);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
