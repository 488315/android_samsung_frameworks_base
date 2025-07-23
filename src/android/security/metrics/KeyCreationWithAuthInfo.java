package android.security.metrics;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyCreationWithAuthInfo implements Parcelable {
    public static final Parcelable.Creator<KeyCreationWithAuthInfo> CREATOR = new Parcelable.Creator<KeyCreationWithAuthInfo>() { // from class: android.security.metrics.KeyCreationWithAuthInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithAuthInfo createFromParcel(Parcel parcel) {
            KeyCreationWithAuthInfo keyCreationWithAuthInfo = new KeyCreationWithAuthInfo();
            keyCreationWithAuthInfo.readFromParcel(parcel);
            return keyCreationWithAuthInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithAuthInfo[] newArray(int i) {
            return new KeyCreationWithAuthInfo[i];
        }
    };
    public int log10_auth_key_timeout_seconds = 0;
    public int security_level;
    public int user_auth_type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.user_auth_type);
        parcel.writeInt(this.log10_auth_key_timeout_seconds);
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
                this.user_auth_type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.log10_auth_key_timeout_seconds = parcel.readInt();
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
