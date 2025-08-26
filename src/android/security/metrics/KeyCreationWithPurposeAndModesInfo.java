package android.security.metrics;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyCreationWithPurposeAndModesInfo implements Parcelable {
    public static final Parcelable.Creator<KeyCreationWithPurposeAndModesInfo> CREATOR = new Parcelable.Creator<KeyCreationWithPurposeAndModesInfo>() { // from class: android.security.metrics.KeyCreationWithPurposeAndModesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithPurposeAndModesInfo createFromParcel(Parcel parcel) {
            KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo = new KeyCreationWithPurposeAndModesInfo();
            keyCreationWithPurposeAndModesInfo.readFromParcel(parcel);
            return keyCreationWithPurposeAndModesInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithPurposeAndModesInfo[] newArray(int i) {
            return new KeyCreationWithPurposeAndModesInfo[i];
        }
    };
    public int algorithm;
    public int purpose_bitmap = 0;
    public int padding_mode_bitmap = 0;
    public int digest_bitmap = 0;
    public int block_mode_bitmap = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.algorithm);
        parcel.writeInt(this.purpose_bitmap);
        parcel.writeInt(this.padding_mode_bitmap);
        parcel.writeInt(this.digest_bitmap);
        parcel.writeInt(this.block_mode_bitmap);
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
                this.algorithm = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.purpose_bitmap = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.padding_mode_bitmap = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.digest_bitmap = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.block_mode_bitmap = parcel.readInt();
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
