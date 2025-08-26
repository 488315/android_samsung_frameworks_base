package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/* loaded from: classes.dex */
public class Signature implements Parcelable {
    public static final Parcelable.Creator<Signature> CREATOR = new Parcelable.Creator<Signature>() { // from class: android.content.pm.Signature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Signature createFromParcel(Parcel parcel) {
            return new Signature(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Signature[] newArray(int i) {
            return new Signature[i];
        }
    };
    private Certificate[] mCertificateChain;
    private int mFlags;
    private int mHashCode;
    private boolean mHaveHashCode;
    private final byte[] mSignature;
    private SoftReference<String> mStringRef;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Signature(byte[] bArr) {
        this.mSignature = (byte[]) bArr.clone();
        this.mCertificateChain = null;
    }

    public Signature(Certificate[] certificateArr) throws CertificateEncodingException {
        this.mSignature = certificateArr[0].getEncoded();
        if (certificateArr.length > 1) {
            this.mCertificateChain = (Certificate[]) Arrays.copyOfRange(certificateArr, 1, certificateArr.length);
        }
    }

    private static final int parseHexDigit(int i) {
        if (48 <= i && i <= 57) {
            return i - 48;
        }
        if (97 <= i && i <= 102) {
            return i - 87;
        }
        if (65 <= i && i <= 70) {
            return i - 55;
        }
        throw new IllegalArgumentException("Invalid character " + i + " in hex string");
    }

    public Signature(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("text size " + length + " is not even");
        }
        byte[] bArr = new byte[length / 2];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            int hexDigit = parseHexDigit(bytes[i]);
            i += 2;
            bArr[i2] = (byte) (parseHexDigit(bytes[i3]) | (hexDigit << 4));
            i2++;
        }
        this.mSignature = bArr;
    }

    public Signature(Signature signature) {
        this.mSignature = (byte[]) signature.mSignature.clone();
        Certificate[] certificateArr = signature.mCertificateChain;
        if (certificateArr != null && certificateArr.length > 1) {
            this.mCertificateChain = (Certificate[]) Arrays.copyOfRange(certificateArr, 1, certificateArr.length);
        }
        this.mFlags = signature.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public char[] toChars() {
        return toChars(null, null);
    }

    public char[] toChars(char[] cArr, int[] iArr) {
        byte[] bArr = this.mSignature;
        int length = bArr.length;
        int i = length * 2;
        if (cArr == null || i > cArr.length) {
            cArr = new char[i];
        }
        for (int i2 = 0; i2 < length; i2++) {
            byte b = bArr[i2];
            int i3 = (b >> 4) & 15;
            int i4 = i2 * 2;
            cArr[i4] = (char) (i3 >= 10 ? i3 + 87 : i3 + 48);
            int i5 = b & 15;
            cArr[i4 + 1] = (char) (i5 >= 10 ? i5 + 87 : i5 + 48);
        }
        if (iArr != null) {
            iArr[0] = length;
        }
        return cArr;
    }

    public String toCharsString() {
        SoftReference<String> softReference = this.mStringRef;
        String str = softReference == null ? null : softReference.get();
        if (str != null) {
            return str;
        }
        String str2 = new String(toChars());
        this.mStringRef = new SoftReference<>(str2);
        return str2;
    }

    public byte[] toByteArray() {
        byte[] bArr = this.mSignature;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public PublicKey getPublicKey() throws CertificateException {
        return CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(this.mSignature)).getPublicKey();
    }

    public Signature[] getChainSignatures() throws CertificateEncodingException {
        Certificate[] certificateArr = this.mCertificateChain;
        if (certificateArr == null) {
            return new Signature[]{this};
        }
        int i = 1;
        Signature[] signatureArr = new Signature[certificateArr.length + 1];
        int i2 = 0;
        signatureArr[0] = this;
        int length = certificateArr.length;
        while (i2 < length) {
            signatureArr[i] = new Signature(certificateArr[i2].getEncoded());
            i2++;
            i++;
        }
        return signatureArr;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            try {
                Signature signature = (Signature) obj;
                if (this != signature) {
                    return Arrays.equals(this.mSignature, signature.mSignature);
                }
                return true;
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.mHaveHashCode) {
            return this.mHashCode;
        }
        int iHashCode = Arrays.hashCode(this.mSignature);
        this.mHashCode = iHashCode;
        this.mHaveHashCode = true;
        return iHashCode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.mSignature);
    }

    public void writeToXmlAttributeBytesHex(TypedXmlSerializer typedXmlSerializer, String str, String str2) throws IOException {
        typedXmlSerializer.attributeBytesHex(str, str2, this.mSignature);
    }

    private Signature(Parcel parcel) {
        this.mSignature = parcel.createByteArray();
    }

    public static boolean areExactMatch(SigningDetails signingDetails, SigningDetails signingDetails2) {
        return areExactArraysMatch(signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    public static boolean areExactMatch(SigningDetails signingDetails, Signature[] signatureArr) {
        return areExactArraysMatch(signingDetails.getSignatures(), signatureArr);
    }

    static boolean areExactArraysMatch(Signature[] signatureArr, Signature[] signatureArr2) {
        return ArrayUtils.size(signatureArr) == ArrayUtils.size(signatureArr2) && ArrayUtils.containsAll(signatureArr, signatureArr2) && ArrayUtils.containsAll(signatureArr2, signatureArr);
    }

    public static boolean areEffectiveMatch(SigningDetails signingDetails, SigningDetails signingDetails2) throws CertificateException {
        return areEffectiveArraysMatch(signingDetails.getSignatures(), signingDetails2.getSignatures());
    }

    static boolean areEffectiveArraysMatch(Signature[] signatureArr, Signature[] signatureArr2) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Signature[] signatureArr3 = new Signature[signatureArr.length];
        for (int i = 0; i < signatureArr.length; i++) {
            signatureArr3[i] = bounce(certificateFactory, signatureArr[i]);
        }
        Signature[] signatureArr4 = new Signature[signatureArr2.length];
        for (int i2 = 0; i2 < signatureArr2.length; i2++) {
            signatureArr4[i2] = bounce(certificateFactory, signatureArr2[i2]);
        }
        return areExactArraysMatch(signatureArr3, signatureArr4);
    }

    public static boolean areEffectiveMatch(Signature signature, Signature signature2) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return bounce(certificateFactory, signature).equals(bounce(certificateFactory, signature2));
    }

    public static Signature bounce(CertificateFactory certificateFactory, Signature signature) throws CertificateException {
        Signature signature2 = new Signature(((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(signature.mSignature))).getEncoded());
        if (Math.abs(signature2.mSignature.length - signature.mSignature.length) <= 2) {
            return signature2;
        }
        throw new CertificateException("Bounced cert length looks fishy; before " + signature.mSignature.length + ", after " + signature2.mSignature.length);
    }
}
