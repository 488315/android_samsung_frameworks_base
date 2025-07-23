package android.content.pm;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ApkChecksum implements Parcelable {
    public static final Parcelable.Creator<ApkChecksum> CREATOR = new Parcelable.Creator<ApkChecksum>() { // from class: android.content.pm.ApkChecksum.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApkChecksum[] newArray(int i) {
            return new ApkChecksum[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApkChecksum createFromParcel(Parcel parcel) {
            return new ApkChecksum(parcel);
        }
    };
    private final Checksum mChecksum;
    private final byte[] mInstallerCertificate;
    private final String mInstallerPackageName;
    private final String mSplitName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApkChecksum(java.lang.String r2, int r3, byte[] r4) {
        /*
            r1 = this;
            android.content.pm.Checksum r0 = new android.content.pm.Checksum
            r0.<init>(r3, r4)
            r3 = 0
            r4 = r3
            java.lang.String r4 = (java.lang.String) r4
            r4 = r3
            byte[] r4 = (byte[]) r4
            r1.<init>(r2, r0, r3, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.ApkChecksum.<init>(java.lang.String, int, byte[]):void");
    }

    public ApkChecksum(String str, int i, byte[] bArr, String str2, Certificate certificate) throws CertificateEncodingException {
        this(str, new Checksum(i, bArr), str2, certificate != null ? certificate.getEncoded() : null);
    }

    public int getType() {
        return this.mChecksum.getType();
    }

    public byte[] getValue() {
        return this.mChecksum.getValue();
    }

    public byte[] getInstallerCertificateBytes() {
        return this.mInstallerCertificate;
    }

    public Certificate getInstallerCertificate() throws CertificateException {
        if (this.mInstallerCertificate == null) {
            return null;
        }
        return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(this.mInstallerCertificate));
    }

    public ApkChecksum(String str, Checksum checksum, String str2, byte[] bArr) {
        this.mSplitName = str;
        this.mChecksum = checksum;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) checksum);
        this.mInstallerPackageName = str2;
        this.mInstallerCertificate = bArr;
    }

    public String getSplitName() {
        return this.mSplitName;
    }

    public String getInstallerPackageName() {
        return this.mInstallerPackageName;
    }

    public String toString() {
        return "ApkChecksum { splitName = " + this.mSplitName + ", checksum = " + this.mChecksum + ", installerPackageName = " + this.mInstallerPackageName + ", installerCertificate = " + Arrays.toString(this.mInstallerCertificate) + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mSplitName != null ? (byte) 1 : (byte) 0;
        if (this.mInstallerPackageName != null) {
            b = (byte) (b | 4);
        }
        if (this.mInstallerCertificate != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        String str = this.mSplitName;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeTypedObject(this.mChecksum, i);
        String str2 = this.mInstallerPackageName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        byte[] bArr = this.mInstallerCertificate;
        if (bArr != null) {
            parcel.writeByteArray(bArr);
        }
    }

    ApkChecksum(Parcel parcel) {
        byte readByte = parcel.readByte();
        String readString = (readByte & 1) == 0 ? null : parcel.readString();
        Checksum checksum = (Checksum) parcel.readTypedObject(Checksum.CREATOR);
        String readString2 = (readByte & 4) == 0 ? null : parcel.readString();
        byte[] createByteArray = (readByte & 8) == 0 ? null : parcel.createByteArray();
        this.mSplitName = readString;
        this.mChecksum = checksum;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) checksum);
        this.mInstallerPackageName = readString2;
        this.mInstallerCertificate = createByteArray;
    }
}
