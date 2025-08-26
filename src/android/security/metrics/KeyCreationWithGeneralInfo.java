package android.security.metrics;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class KeyCreationWithGeneralInfo implements Parcelable {
    public static final Parcelable.Creator<KeyCreationWithGeneralInfo> CREATOR = new Parcelable.Creator<KeyCreationWithGeneralInfo>() { // from class: android.security.metrics.KeyCreationWithGeneralInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithGeneralInfo createFromParcel(Parcel parcel) {
            KeyCreationWithGeneralInfo keyCreationWithGeneralInfo = new KeyCreationWithGeneralInfo();
            keyCreationWithGeneralInfo.readFromParcel(parcel);
            return keyCreationWithGeneralInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyCreationWithGeneralInfo[] newArray(int i) {
            return new KeyCreationWithGeneralInfo[i];
        }
    };
    public int algorithm;
    public int ec_curve;
    public int key_origin;
    public int key_size = 0;
    public int error_code = 0;
    public boolean attestation_requested = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.algorithm);
        parcel.writeInt(this.key_size);
        parcel.writeInt(this.ec_curve);
        parcel.writeInt(this.key_origin);
        parcel.writeInt(this.error_code);
        parcel.writeBoolean(this.attestation_requested);
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
                    this.key_size = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.ec_curve = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.key_origin = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.error_code = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.attestation_requested = parcel.readBoolean();
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
