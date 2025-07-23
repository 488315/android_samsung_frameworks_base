package android.security.metrics;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyOperationWithGeneralInfo implements Parcelable {
    public static final Parcelable.Creator<KeyOperationWithGeneralInfo> CREATOR = new Parcelable.Creator<KeyOperationWithGeneralInfo>() { // from class: android.security.metrics.KeyOperationWithGeneralInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyOperationWithGeneralInfo createFromParcel(Parcel parcel) {
            KeyOperationWithGeneralInfo keyOperationWithGeneralInfo = new KeyOperationWithGeneralInfo();
            keyOperationWithGeneralInfo.readFromParcel(parcel);
            return keyOperationWithGeneralInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyOperationWithGeneralInfo[] newArray(int i) {
            return new KeyOperationWithGeneralInfo[i];
        }
    };
    public int error_code = 0;
    public boolean key_upgraded = false;
    public int outcome;
    public int security_level;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.outcome);
        parcel.writeInt(this.error_code);
        parcel.writeBoolean(this.key_upgraded);
        parcel.writeInt(this.security_level);
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
                this.outcome = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.error_code = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.key_upgraded = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.security_level = parcel.readInt();
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
