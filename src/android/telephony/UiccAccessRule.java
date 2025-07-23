package android.telephony;

import android.annotation.SystemApi;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class UiccAccessRule implements Parcelable {
    public static final Parcelable.Creator<UiccAccessRule> CREATOR = new Parcelable.Creator<UiccAccessRule>() { // from class: android.telephony.UiccAccessRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccAccessRule createFromParcel(Parcel parcel) {
            return new UiccAccessRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UiccAccessRule[] newArray(int i) {
            return new UiccAccessRule[i];
        }
    };
    private static final String DELIMITER_CERTIFICATE_HASH_PACKAGE_NAMES = ":";
    private static final String DELIMITER_INDIVIDUAL_PACKAGE_NAMES = ",";
    private static final int ENCODING_VERSION = 1;
    private static final String TAG = "UiccAccessRule";
    private final long mAccessType;
    private final byte[] mCertificateHash;
    private final int mCertificateHashHashCode;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static byte[] encodeRules(UiccAccessRule[] uiccAccessRuleArr) {
        if (uiccAccessRuleArr == null) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(1);
            dataOutputStream.writeInt(uiccAccessRuleArr.length);
            for (UiccAccessRule uiccAccessRule : uiccAccessRuleArr) {
                dataOutputStream.writeInt(uiccAccessRule.mCertificateHash.length);
                dataOutputStream.write(uiccAccessRule.mCertificateHash);
                if (uiccAccessRule.mPackageName != null) {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeUTF(uiccAccessRule.mPackageName);
                } else {
                    dataOutputStream.writeBoolean(false);
                }
                dataOutputStream.writeLong(uiccAccessRule.mAccessType);
            }
            dataOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("ByteArrayOutputStream should never lead to an IOException", e);
        }
    }

    public static UiccAccessRule[] decodeRulesFromCarrierConfig(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            String[] split = str.split(":");
            byte[] hexStringToBytes = IccUtils.hexStringToBytes(split[0]);
            if (split.length == 1) {
                arrayList.add(new UiccAccessRule(hexStringToBytes, null, 0L));
            } else {
                for (String str2 : split[1].split(",")) {
                    arrayList.add(new UiccAccessRule(hexStringToBytes, str2, 0L));
                }
            }
        }
        return (UiccAccessRule[]) arrayList.toArray(new UiccAccessRule[arrayList.size()]);
    }

    public static UiccAccessRule[] decodeRules(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
            try {
                dataInputStream.readInt();
                int readInt = dataInputStream.readInt();
                UiccAccessRule[] uiccAccessRuleArr = new UiccAccessRule[readInt];
                for (int i = 0; i < readInt; i++) {
                    byte[] bArr2 = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(bArr2);
                    uiccAccessRuleArr[i] = new UiccAccessRule(bArr2, dataInputStream.readBoolean() ? dataInputStream.readUTF() : null, dataInputStream.readLong());
                }
                dataInputStream.close();
                dataInputStream.close();
                return uiccAccessRuleArr;
            } finally {
            }
        } catch (IOException e) {
            throw new IllegalStateException("ByteArrayInputStream should never lead to an IOException", e);
        }
    }

    public UiccAccessRule(byte[] bArr, String str, long j) {
        this.mCertificateHash = bArr;
        this.mCertificateHashHashCode = getCertificateHashHashCode(bArr);
        this.mPackageName = str;
        this.mAccessType = j;
    }

    UiccAccessRule(Parcel parcel) {
        byte[] createByteArray = parcel.createByteArray();
        this.mCertificateHash = createByteArray;
        this.mCertificateHashHashCode = getCertificateHashHashCode(createByteArray);
        this.mPackageName = parcel.readString();
        this.mAccessType = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(this.mCertificateHash);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mAccessType);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getCertificateHexString() {
        return IccUtils.bytesToHexString(this.mCertificateHash);
    }

    public int getCarrierPrivilegeStatus(PackageInfo packageInfo) {
        List<Signature> signatures = getSignatures(packageInfo);
        if (signatures.isEmpty()) {
            throw new IllegalArgumentException("Must use GET_SIGNING_CERTIFICATES when looking up package info");
        }
        Iterator<Signature> it = signatures.iterator();
        while (it.hasNext()) {
            int carrierPrivilegeStatus = getCarrierPrivilegeStatus(it.next(), packageInfo.packageName);
            if (carrierPrivilegeStatus != 0) {
                return carrierPrivilegeStatus;
            }
        }
        return 0;
    }

    public int getCarrierPrivilegeStatus(Signature signature, String str) {
        if (hasMatchingCertificateHashAndPackageName(getCertHash(signature, "SHA-256"), str)) {
            return 1;
        }
        return (this.mCertificateHash.length == 20 && hasMatchingCertificateHashAndPackageName(getCertHash(signature, "SHA-1"), str)) ? 1 : 0;
    }

    public boolean hasMatchingCertificateHashAndPackageName(String str, String str2) {
        return hasMatchingCertificateHashAndPackageName(IccUtils.hexStringToBytes(str), str2);
    }

    public boolean hasMatchingCertificateHashAndPackageName(byte[] bArr, String str) {
        if (bArr == null || !Arrays.equals(this.mCertificateHash, bArr)) {
            return false;
        }
        return TextUtils.isEmpty(this.mPackageName) || this.mPackageName.equals(str);
    }

    public boolean hasMatchingCertificateHashHashAndPackageName(int i, String str) {
        if (i == this.mCertificateHashHashCode) {
            return TextUtils.isEmpty(this.mPackageName) || this.mPackageName.equals(str);
        }
        return false;
    }

    public static int getCertificateHashHashCode(byte[] bArr) {
        return Arrays.hashCode(bArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UiccAccessRule uiccAccessRule = (UiccAccessRule) obj;
            if (Arrays.equals(this.mCertificateHash, uiccAccessRule.mCertificateHash) && Objects.equals(this.mPackageName, uiccAccessRule.mPackageName) && this.mAccessType == uiccAccessRule.mAccessType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((Arrays.hashCode(this.mCertificateHash) + 31) * 31) + Objects.hashCode(this.mPackageName)) * 31) + Objects.hashCode(Long.valueOf(this.mAccessType));
    }

    public String toString() {
        return "cert: " + IccUtils.bytesToHexString(this.mCertificateHash) + " pkg: " + this.mPackageName + " access: " + this.mAccessType;
    }

    public static List<Signature> getSignatures(PackageInfo packageInfo) {
        Signature[] signatureArr = packageInfo.signatures;
        SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo != null) {
            signatureArr = signingInfo.getSigningCertificateHistory();
            if (signingInfo.hasMultipleSigners()) {
                signatureArr = signingInfo.getApkContentsSigners();
            }
        }
        return signatureArr == null ? Collections.EMPTY_LIST : Arrays.asList(signatureArr);
    }

    public static byte[] getCertHash(Signature signature, String str) {
        try {
            return MessageDigest.getInstance(str).digest(signature.toByteArray());
        } catch (NoSuchAlgorithmException e) {
            com.android.telephony.Rlog.e(TAG, "NoSuchAlgorithmException: " + e);
            return null;
        }
    }
}
