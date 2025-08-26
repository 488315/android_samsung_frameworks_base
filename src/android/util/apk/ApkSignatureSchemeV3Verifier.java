package android.util.apk;

import android.os.Build;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.apk.ApkSigningBlockUtils;
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
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/* loaded from: classes4.dex */
public class ApkSignatureSchemeV3Verifier {
    static final int APK_SIGNATURE_SCHEME_V31_BLOCK_ID = 462663009;
    static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    private static final int PROOF_OF_ROTATION_ATTR_ID = 1000370060;
    private static final int ROTATION_MIN_SDK_VERSION_ATTR_ID = 1436519170;
    private static final int ROTATION_ON_DEV_RELEASE_ATTR_ID = -1029262406;
    public static final int SF_ATTRIBUTE_ANDROID_APK_SIGNED_ID = 3;
    private final RandomAccessFile mApk;
    private int mBlockId;
    private OptionalInt mOptionalRotationMinSdkVersion = OptionalInt.empty();
    private int mSignerMinSdkVersion;
    private final boolean mVerifyIntegrity;

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

    public static VerifiedSigner verify(String str) throws SignatureNotFoundException, SecurityException, IOException {
        return verify(str, true);
    }

    public static VerifiedSigner unsafeGetCertsWithoutVerification(String str) throws SignatureNotFoundException, SecurityException, IOException {
        return verify(str, false);
    }

    private static VerifiedSigner verify(String str, boolean z) throws SignatureNotFoundException, IOException, SecurityException {
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
        ApkSignatureSchemeV3Verifier apkSignatureSchemeV3Verifier = new ApkSignatureSchemeV3Verifier(randomAccessFile, z);
        try {
            return apkSignatureSchemeV3Verifier.verify(findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V31_BLOCK_ID), APK_SIGNATURE_SCHEME_V31_BLOCK_ID);
        } catch (PlatformNotSupportedException | SignatureNotFoundException unused) {
            try {
                return apkSignatureSchemeV3Verifier.verify(findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID), APK_SIGNATURE_SCHEME_V3_BLOCK_ID);
            } catch (PlatformNotSupportedException e) {
                throw new SecurityException(e);
            }
        }
    }

    public static SignatureInfo findSignature(RandomAccessFile randomAccessFile) throws SignatureNotFoundException, IOException {
        return findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID);
    }

    private static SignatureInfo findSignature(RandomAccessFile randomAccessFile, int i) throws SignatureNotFoundException, IOException {
        return ApkSigningBlockUtils.findSignature(randomAccessFile, i);
    }

    private ApkSignatureSchemeV3Verifier(RandomAccessFile randomAccessFile, boolean z) {
        this.mApk = randomAccessFile;
        this.mVerifyIntegrity = z;
    }

    private VerifiedSigner verify(SignatureInfo signatureInfo, int i) throws SecurityException, IOException, CertificateException, PlatformNotSupportedException {
        this.mBlockId = i;
        ArrayMap arrayMap = new ArrayMap();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
                int i2 = 0;
                Pair<X509Certificate[], ApkSigningBlockUtils.VerifiedProofOfRotation> pairVerifySigner = null;
                while (lengthPrefixedSlice.hasRemaining()) {
                    try {
                        pairVerifySigner = verifySigner(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayMap, certificateFactory);
                        i2++;
                    } catch (PlatformNotSupportedException unused) {
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        throw new SecurityException("Failed to parse/verify signer #" + i2 + " block", e);
                    }
                }
                if (i2 < 1 || pairVerifySigner == null) {
                    if (i == APK_SIGNATURE_SCHEME_V3_BLOCK_ID) {
                        throw new SecurityException("No signers found");
                    }
                    throw new PlatformNotSupportedException("None of the signers support the current platform version");
                }
                if (i2 != 1) {
                    throw new SecurityException("APK Signature Scheme V3 only supports one signer: multiple signers found.");
                }
                if (arrayMap.isEmpty()) {
                    throw new SecurityException("No content digests found");
                }
                if (this.mVerifyIntegrity) {
                    ApkSigningBlockUtils.verifyIntegrity(arrayMap, this.mApk, signatureInfo);
                }
                return new VerifiedSigner(pairVerifySigner.first, pairVerifySigner.second, arrayMap.containsKey(3) ? ApkSigningBlockUtils.parseVerityDigestAndVerifySourceLength(arrayMap.get(3), this.mApk.getChannel().size(), signatureInfo) : null, arrayMap, i);
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    private Pair<X509Certificate[], ApkSigningBlockUtils.VerifiedProofOfRotation> verifySigner(ByteBuffer byteBuffer, Map<Integer, byte[]> map, CertificateFactory certificateFactory) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, SecurityException, PlatformNotSupportedException, InvalidAlgorithmParameterException {
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        int i = byteBuffer.getInt();
        int i2 = byteBuffer.getInt();
        if (Build.VERSION.SDK_INT < i || Build.VERSION.SDK_INT > i2) {
            if (this.mBlockId == APK_SIGNATURE_SCHEME_V31_BLOCK_ID && (!this.mOptionalRotationMinSdkVersion.isPresent() || this.mOptionalRotationMinSdkVersion.getAsInt() > i)) {
                this.mOptionalRotationMinSdkVersion = OptionalInt.of(i);
            }
            throw new PlatformNotSupportedException("Signer not supported by this platform version. This platform: " + Build.VERSION.SDK_INT + ", signer minSdkVersion: " + i + ", maxSdkVersion: " + i2);
        }
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        byte[] lengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
        ArrayList arrayList = new ArrayList();
        int i3 = -1;
        int i4 = 0;
        byte[] lengthPrefixedByteArray2 = null;
        while (true) {
            int i5 = 8;
            if (!lengthPrefixedSlice2.hasRemaining()) {
                if (i3 == -1) {
                    if (i4 == 0) {
                        throw new SecurityException("No signatures found");
                    }
                    throw new SecurityException("No supported signatures found");
                }
                String signatureAlgorithmJcaKeyAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaKeyAlgorithm(i3);
                Pair<String, ? extends AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i3);
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
                    if (!signature.verify(lengthPrefixedByteArray2)) {
                        throw new SecurityException(str + " signature did not verify");
                    }
                    lengthPrefixedSlice.clear();
                    ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                    ArrayList arrayList2 = new ArrayList();
                    int i6 = 0;
                    byte[] lengthPrefixedByteArray3 = null;
                    while (lengthPrefixedSlice3.hasRemaining()) {
                        i6++;
                        try {
                            ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice3);
                            if (lengthPrefixedSlice4.remaining() < i5) {
                                throw new IOException("Record too short");
                            }
                            int i7 = lengthPrefixedSlice4.getInt();
                            arrayList2.add(Integer.valueOf(i7));
                            if (i7 == i3) {
                                lengthPrefixedByteArray3 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice4);
                            }
                            i5 = 8;
                        } catch (IOException | BufferUnderflowException e) {
                            throw new IOException("Failed to parse digest record #" + i6, e);
                        }
                    }
                    if (!arrayList.equals(arrayList2)) {
                        throw new SecurityException("Signature algorithms don't match between digests and signatures records");
                    }
                    int signatureAlgorithmContentDigestAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i3);
                    byte[] bArrPut = map.put(Integer.valueOf(signatureAlgorithmContentDigestAlgorithm), lengthPrefixedByteArray3);
                    if (bArrPut != null && !MessageDigest.isEqual(bArrPut, lengthPrefixedByteArray3)) {
                        throw new SecurityException(ApkSigningBlockUtils.getContentDigestAlgorithmJcaDigestAlgorithm(signatureAlgorithmContentDigestAlgorithm) + " contents digest does not match the digest specified by a preceding signer");
                    }
                    ByteBuffer lengthPrefixedSlice5 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                    ArrayList arrayList3 = new ArrayList();
                    int i8 = 0;
                    while (lengthPrefixedSlice5.hasRemaining()) {
                        i8++;
                        byte[] lengthPrefixedByteArray4 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5);
                        try {
                            arrayList3.add(new VerbatimX509Certificate((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(lengthPrefixedByteArray4)), lengthPrefixedByteArray4));
                        } catch (CertificateException e2) {
                            throw new SecurityException("Failed to decode certificate #" + i8, e2);
                        }
                    }
                    if (arrayList3.isEmpty()) {
                        throw new SecurityException("No certificates listed");
                    }
                    if (!Arrays.equals(lengthPrefixedByteArray, arrayList3.get(0).getPublicKey().getEncoded())) {
                        throw new SecurityException("Public key mismatch between certificate and signature record");
                    }
                    int i9 = lengthPrefixedSlice.getInt();
                    if (i9 != i) {
                        throw new SecurityException("minSdkVersion mismatch between signed and unsigned in v3 signer block.");
                    }
                    this.mSignerMinSdkVersion = i9;
                    if (lengthPrefixedSlice.getInt() != i2) {
                        throw new SecurityException("maxSdkVersion mismatch between signed and unsigned in v3 signer block.");
                    }
                    return verifyAdditionalAttributes(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), arrayList3, certificateFactory);
                } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e3) {
                    throw new SecurityException("Failed to verify " + str + " signature", e3);
                }
            }
            i4++;
            try {
                ByteBuffer lengthPrefixedSlice6 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                if (lengthPrefixedSlice6.remaining() < 8) {
                    throw new SecurityException("Signature record too short");
                }
                int i10 = lengthPrefixedSlice6.getInt();
                arrayList.add(Integer.valueOf(i10));
                if (ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i10) && (i3 == -1 || ApkSigningBlockUtils.compareSignatureAlgorithm(i10, i3) > 0)) {
                    lengthPrefixedByteArray2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice6);
                    i3 = i10;
                }
            } catch (IOException | BufferUnderflowException e4) {
                throw new SecurityException("Failed to parse signature record #" + i4, e4);
            }
        }
    }

    private Pair<X509Certificate[], ApkSigningBlockUtils.VerifiedProofOfRotation> verifyAdditionalAttributes(ByteBuffer byteBuffer, List<X509Certificate> list, CertificateFactory certificateFactory) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, SecurityException, PlatformNotSupportedException, InvalidAlgorithmParameterException {
        X509Certificate[] x509CertificateArr = (X509Certificate[]) list.toArray(new X509Certificate[list.size()]);
        ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotationVerifyProofOfRotationStruct = null;
        while (byteBuffer.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            if (lengthPrefixedSlice.remaining() < 4) {
                throw new IOException("Remaining buffer too short to contain additional attribute ID. Remaining: " + lengthPrefixedSlice.remaining());
            }
            int i = lengthPrefixedSlice.getInt();
            if (i != ROTATION_ON_DEV_RELEASE_ATTR_ID) {
                if (i != PROOF_OF_ROTATION_ATTR_ID) {
                    if (i != ROTATION_MIN_SDK_VERSION_ATTR_ID) {
                        continue;
                    } else {
                        if (lengthPrefixedSlice.remaining() < 4) {
                            throw new IOException("Remaining buffer too short to contain rotation minSdkVersion value. Remaining: " + lengthPrefixedSlice.remaining());
                        }
                        int i2 = lengthPrefixedSlice.getInt();
                        if (!this.mOptionalRotationMinSdkVersion.isPresent()) {
                            throw new SecurityException("Expected a v3.1 signing block targeting SDK version " + i2 + ", but a v3.1 block was not found");
                        }
                        int asInt = this.mOptionalRotationMinSdkVersion.getAsInt();
                        if (asInt != i2) {
                            throw new SecurityException("Expected a v3.1 signing block targeting SDK version " + i2 + ", but the v3.1 block was targeting " + asInt);
                        }
                    }
                } else {
                    if (verifiedProofOfRotationVerifyProofOfRotationStruct != null) {
                        throw new SecurityException("Encountered multiple Proof-of-rotation records when verifying APK Signature Scheme v3 signature");
                    }
                    verifiedProofOfRotationVerifyProofOfRotationStruct = ApkSigningBlockUtils.verifyProofOfRotationStruct(lengthPrefixedSlice, certificateFactory);
                    try {
                        if (verifiedProofOfRotationVerifyProofOfRotationStruct.certs.size() > 0 && !Arrays.equals(verifiedProofOfRotationVerifyProofOfRotationStruct.certs.get(verifiedProofOfRotationVerifyProofOfRotationStruct.certs.size() - 1).getEncoded(), x509CertificateArr[0].getEncoded())) {
                            throw new SecurityException("Terminal certificate in Proof-of-rotation record does not match APK signing certificate");
                        }
                    } catch (CertificateEncodingException e) {
                        throw new SecurityException("Failed to encode certificate when comparing Proof-of-rotation record and signing certificate", e);
                    }
                }
            } else if (this.mBlockId == APK_SIGNATURE_SCHEME_V31_BLOCK_ID && Build.VERSION.SDK_INT == this.mSignerMinSdkVersion && "REL".equals(Build.VERSION.CODENAME)) {
                this.mOptionalRotationMinSdkVersion = OptionalInt.of(this.mSignerMinSdkVersion);
                throw new PlatformNotSupportedException("The device is running a release version of " + this.mSignerMinSdkVersion + ", but the signer is targeting a dev release");
            }
        }
        return Pair.create(x509CertificateArr, verifiedProofOfRotationVerifyProofOfRotationStruct);
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
        public final int blockId;
        public final X509Certificate[] certs;
        public final Map<Integer, byte[]> contentDigests;
        public final ApkSigningBlockUtils.VerifiedProofOfRotation por;
        public final byte[] verityRootHash;

        public VerifiedSigner(X509Certificate[] x509CertificateArr, ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotation, byte[] bArr, Map<Integer, byte[]> map, int i) {
            this.certs = x509CertificateArr;
            this.por = verifiedProofOfRotation;
            this.verityRootHash = bArr;
            this.contentDigests = map;
            this.blockId = i;
        }
    }

    private static class PlatformNotSupportedException extends Exception {
        PlatformNotSupportedException(String str) {
            super(str);
        }
    }
}
