package android.security.metrics;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyOperationWithPurposeAndModesInfo implements Parcelable {
    public static final Parcelable.Creator<KeyOperationWithPurposeAndModesInfo> CREATOR = new Parcelable.Creator<KeyOperationWithPurposeAndModesInfo>() { // from class: android.security.metrics.KeyOperationWithPurposeAndModesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyOperationWithPurposeAndModesInfo createFromParcel(Parcel parcel) {
            KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo = new KeyOperationWithPurposeAndModesInfo();
            keyOperationWithPurposeAndModesInfo.readFromParcel(parcel);
            return keyOperationWithPurposeAndModesInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyOperationWithPurposeAndModesInfo[] newArray(int i) {
            return new KeyOperationWithPurposeAndModesInfo[i];
        }
    };
    public int purpose;
    public int padding_mode_bitmap = 0;
    public int digest_bitmap = 0;
    public int block_mode_bitmap = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.purpose);
        parcel.writeInt(this.padding_mode_bitmap);
        parcel.writeInt(this.digest_bitmap);
        parcel.writeInt(this.block_mode_bitmap);
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
                this.purpose = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.padding_mode_bitmap = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.digest_bitmap = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.block_mode_bitmap = parcel.readInt();
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
