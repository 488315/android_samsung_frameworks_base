package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ImsiEncryptionInfo implements Parcelable {
    public static final Parcelable.Creator<ImsiEncryptionInfo> CREATOR = new Parcelable.Creator<ImsiEncryptionInfo>() { // from class: android.hardware.radio.sim.ImsiEncryptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsiEncryptionInfo createFromParcel(Parcel parcel) {
            ImsiEncryptionInfo imsiEncryptionInfo = new ImsiEncryptionInfo();
            imsiEncryptionInfo.readFromParcel(parcel);
            return imsiEncryptionInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsiEncryptionInfo[] newArray(int i) {
            return new ImsiEncryptionInfo[i];
        }
    };
    public static final byte PUBLIC_KEY_TYPE_EPDG = 1;
    public static final byte PUBLIC_KEY_TYPE_WLAN = 2;
    public byte[] carrierKey;
    public String keyIdentifier;
    public String mcc;
    public String mnc;
    public long expirationTime = 0;
    public byte keyType = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeByteArray(this.carrierKey);
        parcel.writeString(this.keyIdentifier);
        parcel.writeLong(this.expirationTime);
        parcel.writeByte(this.keyType);
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
                this.mcc = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mnc = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.carrierKey = parcel.createByteArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.keyIdentifier = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.expirationTime = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.keyType = parcel.readByte();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        stringJoiner.add("carrierKey: " + Arrays.toString(this.carrierKey));
        stringJoiner.add("keyIdentifier: " + Objects.toString(this.keyIdentifier));
        stringJoiner.add("expirationTime: " + this.expirationTime);
        stringJoiner.add("keyType: " + ((int) this.keyType));
        return "ImsiEncryptionInfo" + stringJoiner.toString();
    }
}
