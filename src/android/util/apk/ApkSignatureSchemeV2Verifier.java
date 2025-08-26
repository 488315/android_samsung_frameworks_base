package android.util.apk;

import android.util.ArrayMap;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public class ApkSignatureSchemeV2Verifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final int MAX_V2_SIGNERS = 10;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 2;
    private static final int STRIPPING_PROTECTION_ATTR_ID = -1091571699;

    public static boolean hasSignature(String str) throws IOException {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
            try {
                findSignature(randomAccessFile);
                randomAccessFile.close();
                return true;
            } finally {
            }
        } catch (SignatureNotFoundException unused) {
            return false;
        }
    }

    public static X509Certificate[][] verify(String str) throws SignatureNotFoundException, SecurityException, IOException {
        return verify(str, true).certs;
    }

    public static X509Certificate[][] unsafeGetCertsWithoutVerification(String str) throws SignatureNotFoundException, SecurityException, IOException {
        return verify(str, false).certs;
    }

    public static VerifiedSigner verify(String str, boolean z) throws SignatureNotFoundException, IOException, SecurityException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            VerifiedSigner verifiedSignerVerify = verify(randomAccessFile, z);
            randomAccessFile.close();
            return verifiedSignerVerify;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static VerifiedSigner verify(RandomAccessFile randomAccessFile, boolean z) throws SignatureNotFoundException, SecurityException, IOException {
        return verify(randomAccessFile, findSignature(randomAccessFile), z);
    }

    public static SignatureInfo findSignature(RandomAccessFile randomAccessFile) throws SignatureNotFoundException, IOException {
        return ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V2_BLOCK_ID);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static VerifiedSigner verify(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, boolean z) throws SecurityException, IOException, CertificateException {
        ArrayMap arrayMap = new ArrayMap();
        ArrayList arrayList = new ArrayList();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
                int i = 0;
                while (lengthPrefixedSlice.hasRemaining()) {
                    i++;
                    if (i > 10) {
                        throw new SecurityException("APK Signature Scheme v2 only supports a maximum of 10 signers");
                    }
                    try {
                        arrayList.add(verifySigner(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayMap, certificateFactory));
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        throw new SecurityException("Failed to parse/verify signer #" + i + " block", e);
                    }
                }
                if (i < 1) {
                    throw new SecurityException("No signers found");
                }
                if (arrayMap.isEmpty()) {
                    throw new SecurityException("No content digests found");
                }
                if (z) {
                    ApkSigningBlockUtils.verifyIntegrity(arrayMap, randomAccessFile, signatureInfo);
                }
                return new VerifiedSigner((X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]), arrayMap.containsKey(3) ? ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength((byte[]) arrayMap.get(3), randomAccessFile.getChannel().size(), signatureInfo) : null, arrayMap);
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    private static X509Certificate[] verifySigner(ByteBuffer byteBuffer, Map<Integer, byte[]> map, CertificateFactory certificateFactory) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, SecurityException, InvalidAlgorithmParameterException {
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        byte[] lengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
        ArrayList arrayList = new ArrayList();
        byte[] lengthPrefixedByteArray2 = null;
        int i = 0;
        int i2 = -1;
        byte[] lengthPrefixedByteArray3 = null;
        while (lengthPrefixedSlice2.hasRemaining()) {
            i++;
            try {
                ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                if (lengthPrefixedSlice3.remaining() < 8) {
                    throw new SecurityException("Signature record too short");
                }
                int i3 = lengthPrefixedSlice3.getInt();
                arrayList.add(Integer.valueOf(i3));
                if (ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i3) && (i2 == -1 || ApkSigningBlockUtils.compareSignatureAlgorithm(i3, i2) > 0)) {
                    lengthPrefixedByteArray3 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3);
                    i2 = i3;
                }
            } catch (IOException | BufferUnderflowException e) {
                throw new SecurityException("Failed to parse signature record #" + i, e);
            }
        }
        if (i2 == -1) {
            if (i == 0) {
                throw new SecurityException("No signatures found");
            }
            throw new SecurityException("No supported signatures found");
        }
        String signatureAlgorithmJcaKeyAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i2);
        Pair<String, ? extends AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i2);
        String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(signatureAlgorithmJcaKeyAlgorithm).generatePublic(new X509EncodedKeySpec(lengthPrefixedByteArray));
            Signature signature = Signature.getInstance(str);
            signature.initVerify(publicKeyGeneratePublic);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(lengthPrefixedSlice);
            if (!signature.verify(lengthPrefixedByteArray3)) {
                throw new SecurityException(str + " signature did not verify");
            }
            lengthPrefixedSlice.clear();
            ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            ArrayList arrayList2 = new ArrayList();
            int i4 = 0;
            while (lengthPrefixedSlice4.hasRemaining()) {
                i4++;
                try {
                    ByteBuffer lengthPrefixedSlice5 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice4);
                    if (lengthPrefixedSlice5.remaining() < 8) {
                        throw new IOException("Record too short");
                    }
                    int i5 = lengthPrefixedSlice5.getInt();
                    arrayList2.add(Integer.valueOf(i5));
                    if (i5 == i2) {
                        lengthPrefixedByteArray2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5);
                    }
                } catch (IOException | BufferUnderflowException e2) {
                    throw new IOException("Failed to parse digest record #" + i4, e2);
                }
            }
            if (!arrayList.equals(arrayList2)) {
                throw new SecurityException("Signature algorithms don't match between digests and signatures records");
            }
            int signatureAlgorithmContentDigestAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i2);
            byte[] bArrPut = map.put(Integer.valueOf(signatureAlgorithmContentDigestAlgorithm), lengthPrefixedByteArray2);
            if (bArrPut != null && !MessageDigest.isEqual(bArrPut, lengthPrefixedByteArray2)) {
                throw new SecurityException(ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(signatureAlgorithmContentDigestAlgorithm) + " contents digest does not match the digest specified by a preceding signer");
            }
            ByteBuffer lengthPrefixedSlice6 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            ArrayList arrayList3 = new ArrayList();
            int i6 = 0;
            while (lengthPrefixedSlice6.hasRemaining()) {
                i6++;
                byte[] lengthPrefixedByteArray4 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice6);
                try {
                    arrayList3.add(new VerbatimX509Certificate((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(lengthPrefixedByteArray4)), lengthPrefixedByteArray4));
                } catch (CertificateException e3) {
                    throw new SecurityException("Failed to decode certificate #" + i6, e3);
                }
            }
            if (arrayList3.isEmpty()) {
                throw new SecurityException("No certificates listed");
            }
            if (!Arrays.equals(lengthPrefixedByteArray, ((X509Certificate) arrayList3.get(0)).getPublicKey().getEncoded())) {
                throw new SecurityException("Public key mismatch between certificate and signature record");
            }
            verifyAdditionalAttributes(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice));
            return (X509Certificate[]) arrayList3.toArray(new X509Certificate[arrayList3.size()]);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e4) {
            throw new SecurityException("Failed to verify " + str + " signature", e4);
        }
    }

    private static void verifyAdditionalAttributes(ByteBuffer byteBuffer) throws IOException, SecurityException {
        while (byteBuffer.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            if (lengthPrefixedSlice.remaining() < 4) {
                throw new IOException("Remaining buffer too short to contain additional attribute ID. Remaining: " + lengthPrefixedSlice.remaining());
            }
            if (lengthPrefixedSlice.getInt() == STRIPPING_PROTECTION_ATTR_ID) {
                if (lengthPrefixedSlice.remaining() < 4) {
                    throw new IOException("V2 Signature Scheme Stripping Protection Attribute  value too small.  Expected 4 bytes, but found " + lengthPrefixedSlice.remaining());
                }
                if (lengthPrefixedSlice.getInt() == 3) {
                    throw new SecurityException("V2 signature indicates APK is signed using APK Signature Scheme v3, but none was found. Signature stripped?");
                }
            }
        }
    }

    static byte[] getVerityRootHash(String str) throws SignatureNotFoundException, IOException, SecurityException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            findSignature(randomAccessFile);
            byte[] bArr = verify(randomAccessFile, false).verityRootHash;
            randomAccessFile.close();
            return bArr;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static byte[] generateApkVerity(String str, ByteBufferFactory byteBufferFactory) throws SignatureNotFoundException, NoSuchAlgorithmException, DigestException, IOException, SecurityException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            byte[] bArrGenerateApkVerity = VerityBuilder.generateApkVerity(str, byteBufferFactory, findSignature(randomAccessFile));
            randomAccessFile.close();
            return bArrGenerateApkVerity;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static class VerifiedSigner {
        public final X509Certificate[][] certs;
        public final Map<Integer, byte[]> contentDigests;
        public final byte[] verityRootHash;

        public VerifiedSigner(X509Certificate[][] x509CertificateArr, byte[] bArr, Map<Integer, byte[]> map) {
            this.certs = x509CertificateArr;
            this.verityRootHash = bArr;
            this.contentDigests = map;
        }
    }
}
