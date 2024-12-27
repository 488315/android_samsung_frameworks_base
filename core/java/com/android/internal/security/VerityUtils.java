package com.android.internal.security;

import android.os.Build;
import android.os.SystemProperties;
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

import java.io.File;
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

/* loaded from: classes5.dex */
public abstract class VerityUtils {
    public static final String FSVERITY_SIGNATURE_FILE_EXTENSION = ".fsv_sig";
    private static final int HASH_SIZE_BYTES = 32;
    private static final String TAG = "VerityUtils";

    private static native int enableFsverityForFdNative(int i);

    private static native int enableFsverityNative(String str);

    private static native int measureFsverityNative(String str, byte[] bArr);

    private static native int statxForFsverityNative(String str);

    public static boolean isFsVeritySupported() {
        return Build.VERSION.DEVICE_INITIAL_SDK_INT >= 30
                || SystemProperties.getInt("ro.apk_verity.mode", 0) == 2;
    }

    public static boolean isFsveritySignatureFile(File file) {
        return file.getName().endsWith(FSVERITY_SIGNATURE_FILE_EXTENSION);
    }

    public static String getFsveritySignatureFilePath(String filePath) {
        return filePath + FSVERITY_SIGNATURE_FILE_EXTENSION;
    }

    public static void setUpFsverity(String filePath) throws IOException {
        int errno = enableFsverityNative(filePath);
        if (errno != 0) {
            throw new IOException(
                    "Failed to enable fs-verity on " + filePath + ": " + Os.strerror(errno));
        }
    }

    public static void setUpFsverity(int fd) throws IOException {
        int errno = enableFsverityForFdNative(fd);
        if (errno != 0) {
            throw new IOException(
                    "Failed to enable fs-verity on FD(" + fd + "): " + Os.strerror(errno));
        }
    }

    public static boolean hasFsverity(String filePath) {
        int retval = statxForFsverityNative(filePath);
        if (retval < 0) {
            Slog.e(
                    TAG,
                    "Failed to check whether fs-verity is enabled, errno "
                            + (-retval)
                            + ": "
                            + filePath);
            return false;
        }
        if (retval != 1) {
            return false;
        }
        return true;
    }

    public static boolean verifyPkcs7DetachedSignature(
            byte[] signatureBlock, byte[] digest, InputStream derCertInputStream) {
        if (digest.length != 32) {
            Slog.w(TAG, "Only sha256 is currently supported");
            return false;
        }
        try {
            CMSSignedData signedData =
                    new CMSSignedData(
                            new CMSProcessableByteArray(toFormattedDigest(digest)), signatureBlock);
            if (!signedData.isDetachedSignature()) {
                Slog.w(TAG, "Expect only detached siganture");
                return false;
            }
            if (!signedData.getCertificates().getMatches(null).isEmpty()) {
                Slog.w(TAG, "Expect no certificate in signature");
                return false;
            }
            if (!signedData.getCRLs().getMatches(null).isEmpty()) {
                Slog.w(TAG, "Expect no CRL in signature");
                return false;
            }
            X509Certificate trustedCert =
                    (X509Certificate)
                            CertificateFactory.getInstance("X.509")
                                    .generateCertificate(derCertInputStream);
            SignerInformationVerifier verifier =
                    new JcaSimpleSignerInfoVerifierBuilder().build(trustedCert);
            for (SignerInformation si : signedData.getSignerInfos().getSigners()) {
                if (si.getSignedAttributes() != null && si.getSignedAttributes().size() > 0) {
                    Slog.w(TAG, "Unexpected signed attributes");
                    return false;
                }
                if (si.getUnsignedAttributes() != null && si.getUnsignedAttributes().size() > 0) {
                    Slog.w(TAG, "Unexpected unsigned attributes");
                    return false;
                }
                if (!NISTObjectIdentifiers.id_sha256.getId().equals(si.getDigestAlgOID())) {
                    Slog.w(TAG, "Unsupported digest algorithm OID: " + si.getDigestAlgOID());
                    return false;
                }
                if (!PKCSObjectIdentifiers.rsaEncryption.getId().equals(si.getEncryptionAlgOID())) {
                    Slog.w(
                            TAG,
                            "Unsupported encryption algorithm OID: " + si.getEncryptionAlgOID());
                    return false;
                }
                if (si.verify(verifier)) {
                    return true;
                }
            }
            return false;
        } catch (CMSException | OperatorCreationException | CertificateException e) {
            Slog.w(TAG, "Error occurred during the PKCS#7 signature verification", e);
            return false;
        }
    }

    public static byte[] getFsverityDigest(String filePath) {
        byte[] result = new byte[32];
        int retval = measureFsverityNative(filePath, result);
        if (retval < 0) {
            if (retval != (-OsConstants.ENODATA)) {
                Slog.e(TAG, "Failed to measure fs-verity, errno " + (-retval) + ": " + filePath);
                return null;
            }
            return null;
        }
        return result;
    }

    public static byte[] generateFsVerityDigest(long fileSize, V4Signature.HashingInfo hashingInfo)
            throws DigestException, NoSuchAlgorithmException {
        if (hashingInfo.rawRootHash == null || hashingInfo.rawRootHash.length != 32) {
            throw new IllegalArgumentException("Expect a 32-byte rootHash for SHA256");
        }
        if (hashingInfo.log2BlockSize != 12) {
            throw new IllegalArgumentException(
                    "Unsupported log2BlockSize: " + ((int) hashingInfo.log2BlockSize));
        }
        ByteBuffer buffer = ByteBuffer.allocate(256);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.put(hashingInfo.log2BlockSize);
        buffer.put((byte) 0);
        buffer.putInt(0);
        buffer.putLong(fileSize);
        buffer.put(hashingInfo.rawRootHash);
        return MessageDigest.getInstance("SHA-256").digest(buffer.array());
    }

    public static byte[] toFormattedDigest(byte[] digest) {
        ByteBuffer buffer = ByteBuffer.allocate(digest.length + 12);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put("FSVerity".getBytes(StandardCharsets.US_ASCII));
        buffer.putShort((short) 1);
        buffer.putShort((short) digest.length);
        buffer.put(digest);
        return buffer.array();
    }
}
