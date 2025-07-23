package com.android.internal.security;

import android.os.Build;
import android.os.incremental.V4Signature;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.cms.CMSException;
import com.android.internal.org.bouncycastle.cms.CMSProcessableByteArray;
import com.android.internal.org.bouncycastle.cms.CMSSignedData;
import com.android.internal.org.bouncycastle.cms.SignerInformation;
import com.android.internal.org.bouncycastle.cms.SignerInformationVerifier;
import com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/* loaded from: classes3.dex */
public abstract class VerityUtils {
    private static final int HASH_SIZE_BYTES = 32;
    private static final String TAG = "VerityUtils";

    private static native int enableFsverityForFdNative(int i);

    private static native int enableFsverityNative(String str);

    private static native int measureFsverityNative(String str, byte[] bArr);

    private static native int statxForFsverityNative(String str);

    public static boolean isFsVeritySupported() {
        return Build.VERSION.DEVICE_INITIAL_SDK_INT >= 30;
    }

    public static void setUpFsverity(String str) throws IOException {
        int enableFsverityNative = enableFsverityNative(str);
        if (enableFsverityNative == 0) {
            return;
        }
        throw new IOException("Failed to enable fs-verity on " + str + ": " + Os.strerror(enableFsverityNative));
    }

    public static void setUpFsverity(int i) throws IOException {
        int enableFsverityForFdNative = enableFsverityForFdNative(i);
        if (enableFsverityForFdNative == 0) {
            return;
        }
        throw new IOException("Failed to enable fs-verity on FD(" + i + "): " + Os.strerror(enableFsverityForFdNative));
    }

    public static boolean hasFsverity(String str) {
        int statxForFsverityNative = statxForFsverityNative(str);
        if (statxForFsverityNative >= 0) {
            return statxForFsverityNative == 1;
        }
        Slog.e(TAG, "Failed to check whether fs-verity is enabled, errno " + (-statxForFsverityNative) + ": " + str);
        return false;
    }

    public static boolean verifyPkcs7DetachedSignature(byte[] bArr, byte[] bArr2, InputStream inputStream) {
        if (bArr2.length != 32) {
            Slog.w(TAG, "Only sha256 is currently supported");
            return false;
        }
        try {
            CMSSignedData cMSSignedData = new CMSSignedData(new CMSProcessableByteArray(toFormattedDigest(bArr2)), bArr);
            if (!cMSSignedData.isDetachedSignature()) {
                Slog.w(TAG, "Expect only detached siganture");
                return false;
            }
            if (!cMSSignedData.getCertificates().getMatches(null).isEmpty()) {
                Slog.w(TAG, "Expect no certificate in signature");
                return false;
            }
            if (!cMSSignedData.getCRLs().getMatches(null).isEmpty()) {
                Slog.w(TAG, "Expect no CRL in signature");
                return false;
            }
            SignerInformationVerifier build = new JcaSimpleSignerInfoVerifierBuilder().build((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(inputStream));
            for (SignerInformation signerInformation : cMSSignedData.getSignerInfos().getSigners()) {
                if (signerInformation.getSignedAttributes() != null && signerInformation.getSignedAttributes().size() > 0) {
                    Slog.w(TAG, "Unexpected signed attributes");
                    return false;
                }
                if (signerInformation.getUnsignedAttributes() != null && signerInformation.getUnsignedAttributes().size() > 0) {
                    Slog.w(TAG, "Unexpected unsigned attributes");
                    return false;
                }
                if (!NISTObjectIdentifiers.id_sha256.getId().equals(signerInformation.getDigestAlgOID())) {
                    Slog.w(TAG, "Unsupported digest algorithm OID: " + signerInformation.getDigestAlgOID());
                    return false;
                }
                if (!PKCSObjectIdentifiers.rsaEncryption.getId().equals(signerInformation.getEncryptionAlgOID())) {
                    Slog.w(TAG, "Unsupported encryption algorithm OID: " + signerInformation.getEncryptionAlgOID());
                    return false;
                }
                if (signerInformation.verify(build)) {
                    return true;
                }
            }
            return false;
        } catch (CMSException | OperatorCreationException | CertificateException e) {
            Slog.w(TAG, "Error occurred during the PKCS#7 signature verification", e);
            return false;
        }
    }

    public static byte[] getFsverityDigest(String str) {
        byte[] bArr = new byte[32];
        int measureFsverityNative = measureFsverityNative(str, bArr);
        if (measureFsverityNative >= 0) {
            return bArr;
        }
        if (measureFsverityNative == (-OsConstants.ENODATA)) {
            return null;
        }
        Slog.e(TAG, "Failed to measure fs-verity, errno " + (-measureFsverityNative) + ": " + str);
        return null;
    }

    public static byte[] generateFsVerityDigest(long j, V4Signature.HashingInfo hashingInfo) throws DigestException, NoSuchAlgorithmException {
        if (hashingInfo.rawRootHash == null || hashingInfo.rawRootHash.length != 32) {
            throw new IllegalArgumentException("Expect a 32-byte rootHash for SHA256");
        }
        if (hashingInfo.log2BlockSize != 12) {
            throw new IllegalArgumentException("Unsupported log2BlockSize: " + ((int) hashingInfo.log2BlockSize));
        }
        ByteBuffer allocate = ByteBuffer.allocate(256);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.put((byte) 1);
        allocate.put((byte) 1);
        allocate.put(hashingInfo.log2BlockSize);
        allocate.put((byte) 0);
        allocate.putInt(0);
        allocate.putLong(j);
        allocate.put(hashingInfo.rawRootHash);
        return MessageDigest.getInstance("SHA-256").digest(allocate.array());
    }

    public static byte[] toFormattedDigest(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 12);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.put("FSVerity".getBytes(StandardCharsets.US_ASCII));
        allocate.putShort((short) 1);
        allocate.putShort((short) bArr.length);
        allocate.put(bArr);
        return allocate.array();
    }
}
