package android.os.incremental;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class IncrementalNewFileParams implements Parcelable {
    public static final Parcelable.Creator<IncrementalNewFileParams> CREATOR = new Parcelable.Creator<IncrementalNewFileParams>() { // from class: android.os.incremental.IncrementalNewFileParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalNewFileParams createFromParcel(Parcel parcel) {
            IncrementalNewFileParams incrementalNewFileParams = new IncrementalNewFileParams();
            incrementalNewFileParams.readFromParcel(parcel);
            return incrementalNewFileParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncrementalNewFileParams[] newArray(int i) {
            return new IncrementalNewFileParams[i];
        }
    };
    public byte[] fileId;
    public byte[] metadata;
    public byte[] signature;
    public long size = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.size);
        parcel.writeByteArray(this.fileId);
        parcel.writeByteArray(this.metadata);
        parcel.writeByteArray(this.signature);
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
                this.size = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.fileId = parcel.createByteArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.metadata = parcel.createByteArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.signature = parcel.createByteArray();
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
}
