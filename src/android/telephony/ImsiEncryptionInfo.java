package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsiEncryptionInfo implements Parcelable {
    public static final Parcelable.Creator<ImsiEncryptionInfo> CREATOR = new Parcelable.Creator<ImsiEncryptionInfo>() { // from class: android.telephony.ImsiEncryptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsiEncryptionInfo createFromParcel(Parcel parcel) {
            return new ImsiEncryptionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsiEncryptionInfo[] newArray(int i) {
            return new ImsiEncryptionInfo[i];
        }
    };
    private static final String LOG_TAG = "ImsiEncryptionInfo";
    private final int carrierId;
    private final Date expirationTime;
    private final String keyIdentifier;
    private final int keyType;
    private final String mcc;
    private final String mnc;
    private final PublicKey publicKey;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsiEncryptionInfo(String str, String str2, int i, String str3, byte[] bArr, Date date, int i2) {
        this(str, str2, i, str3, makeKeyObject(bArr), date, i2);
    }

    public ImsiEncryptionInfo(String str, String str2, int i, String str3, PublicKey publicKey, Date date, int i2) {
        this.mcc = str;
        this.mnc = str2;
        this.keyType = i;
        this.publicKey = publicKey;
        this.keyIdentifier = str3;
        this.expirationTime = date;
        this.carrierId = i2;
    }

    public ImsiEncryptionInfo(Parcel parcel) {
        byte[] bArr = new byte[parcel.readInt()];
        parcel.readByteArray(bArr);
        this.publicKey = makeKeyObject(bArr);
        this.mcc = parcel.readString();
        this.mnc = parcel.readString();
        this.keyIdentifier = parcel.readString();
        this.keyType = parcel.readInt();
        this.expirationTime = new Date(parcel.readLong());
        this.carrierId = parcel.readInt();
    }

    public String getMnc() {
        return this.mnc;
    }

    public String getMcc() {
        return this.mcc;
    }

    public int getCarrierId() {
        return this.carrierId;
    }

    public String getKeyIdentifier() {
        return this.keyIdentifier;
    }

    public int getKeyType() {
        return this.keyType;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    private static PublicKey makeKeyObject(byte[] bArr) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.e(LOG_TAG, "Error makeKeyObject: unable to convert into PublicKey", e);
            throw new IllegalArgumentException();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte[] encoded = this.publicKey.getEncoded();
        parcel.writeInt(encoded.length);
        parcel.writeByteArray(encoded);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.keyIdentifier);
        parcel.writeInt(this.keyType);
        parcel.writeLong(this.expirationTime.getTime());
        parcel.writeInt(this.carrierId);
    }

    public String toString() {
        return "[ImsiEncryptionInfo mcc=" + this.mcc + " mnc=" + this.mnc + ", publicKey=" + this.publicKey + ", keyIdentifier=" + this.keyIdentifier + ", keyType=" + this.keyType + ", expirationTime=" + this.expirationTime + ", carrier_id=" + this.carrierId + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
