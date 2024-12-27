package android.content.pm;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
        public ApkChecksum[] newArray(int size) {
            return new ApkChecksum[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApkChecksum createFromParcel(Parcel in) {
            return new ApkChecksum(in);
        }
    };
    private final Checksum mChecksum;
    private final byte[] mInstallerCertificate;
    private final String mInstallerPackageName;
    private final String mSplitName;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ApkChecksum(String splitName, int type, byte[] value) {
        this(splitName, new Checksum(type, value), null, null);
    }

    public ApkChecksum(String splitName, int type, byte[] value, String sourcePackageName, Certificate sourceCertificate) throws CertificateEncodingException {
        this(splitName, new Checksum(type, value), sourcePackageName, sourceCertificate != null ? sourceCertificate.getEncoded() : null);
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
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(this.mInstallerCertificate);
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        return cert;
    }

    public ApkChecksum(String splitName, Checksum checksum, String installerPackageName, byte[] installerCertificate) {
        this.mSplitName = splitName;
        this.mChecksum = checksum;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mChecksum);
        this.mInstallerPackageName = installerPackageName;
        this.mInstallerCertificate = installerCertificate;
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
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mSplitName != null ? (byte) (0 | 1) : (byte) 0;
        if (this.mInstallerPackageName != null) {
            flg = (byte) (flg | 4);
        }
        if (this.mInstallerCertificate != null) {
            flg = (byte) (flg | 8);
        }
        dest.writeByte(flg);
        if (this.mSplitName != null) {
            dest.writeString(this.mSplitName);
        }
        dest.writeTypedObject(this.mChecksum, flags);
        if (this.mInstallerPackageName != null) {
            dest.writeString(this.mInstallerPackageName);
        }
        if (this.mInstallerCertificate != null) {
            dest.writeByteArray(this.mInstallerCertificate);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ApkChecksum(Parcel in) {
        byte flg = in.readByte();
        String splitName = (flg & 1) == 0 ? null : in.readString();
        Checksum checksum = (Checksum) in.readTypedObject(Checksum.CREATOR);
        String installerPackageName = (flg & 4) == 0 ? null : in.readString();
        byte[] installerCertificate = (flg & 8) == 0 ? null : in.createByteArray();
        this.mSplitName = splitName;
        this.mChecksum = checksum;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mChecksum);
        this.mInstallerPackageName = installerPackageName;
        this.mInstallerCertificate = installerCertificate;
    }

    @Deprecated
    private void __metadata() {
    }
}
