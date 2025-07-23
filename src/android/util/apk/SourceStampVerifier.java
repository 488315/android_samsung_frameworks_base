package android.util.apk;

import android.util.Pair;
import android.util.Slog;
import android.util.apk.ApkSigningBlockUtils;
import android.util.jar.StrictJarFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import libcore.io.Streams;

/* loaded from: classes4.dex */
public abstract class SourceStampVerifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    private static final int PROOF_OF_ROTATION_ATTR_ID = -1654455305;
    private static final int SOURCE_STAMP_BLOCK_ID = 1845461005;
    private static final String SOURCE_STAMP_CERTIFICATE_HASH_ZIP_ENTRY_NAME = "stamp-cert-sha256";
    private static final String TAG = "SourceStampVerifier";
    private static final int VERSION_APK_SIGNATURE_SCHEME_V2 = 2;
    private static final int VERSION_APK_SIGNATURE_SCHEME_V3 = 3;
    private static final int VERSION_JAR_SIGNATURE_SCHEME = 1;

    private SourceStampVerifier() {
    }

    public static SourceStampVerificationResult verify(List<String> list) {
        List<? extends Certificate> list2 = Collections.EMPTY_LIST;
        Iterator<String> it = list.iterator();
        Certificate certificate = null;
        while (it.hasNext()) {
            SourceStampVerificationResult verify = verify(it.next());
            if (!verify.isPresent() || !verify.isVerified()) {
                return verify;
            }
            if (certificate != null && (!certificate.equals(verify.getCertificate()) || !list2.equals(verify.getCertificateLineage()))) {
                return SourceStampVerificationResult.notVerified();
            }
            certificate = verify.getCertificate();
            list2 = verify.getCertificateLineage();
        }
        return SourceStampVerificationResult.verified(certificate, list2);
    }

    public static SourceStampVerificationResult verify(String str) {
        StrictJarFile strictJarFile;
        StrictJarFile strictJarFile2 = null;
        try {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
                try {
                    strictJarFile = new StrictJarFile(str, false, false);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    byte[] sourceStampCertificateDigest = getSourceStampCertificateDigest(strictJarFile);
                    try {
                        if (sourceStampCertificateDigest == null) {
                            SourceStampVerificationResult notPresent = SourceStampVerificationResult.notPresent();
                            randomAccessFile.close();
                            closeApkJar(strictJarFile);
                            return notPresent;
                        }
                        SourceStampVerificationResult verify = verify(randomAccessFile, sourceStampCertificateDigest, getManifestBytes(strictJarFile));
                        randomAccessFile.close();
                        closeApkJar(strictJarFile);
                        return verify;
                    } catch (IOException unused) {
                        strictJarFile2 = strictJarFile;
                        SourceStampVerificationResult notPresent2 = SourceStampVerificationResult.notPresent();
                        closeApkJar(strictJarFile2);
                        return notPresent2;
                    } catch (Throwable th2) {
                        th = th2;
                        strictJarFile2 = strictJarFile;
                        closeApkJar(strictJarFile2);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    strictJarFile2 = strictJarFile;
                    try {
                        randomAccessFile.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    private static SourceStampVerificationResult verify(RandomAccessFile randomAccessFile, byte[] bArr, byte[] bArr2) {
        try {
            try {
                return verify(ApkSigningBlockUtils.findSignature(randomAccessFile, SOURCE_STAMP_BLOCK_ID), getSignatureSchemeDigests(getSignatureSchemeApkContentDigests(randomAccessFile, bArr2)), bArr);
            } catch (IOException | RuntimeException unused) {
                return SourceStampVerificationResult.notVerified();
            }
        } catch (SignatureNotFoundException | IOException | RuntimeException unused2) {
            return SourceStampVerificationResult.notPresent();
        }
    }

    private static SourceStampVerificationResult verify(SignatureInfo signatureInfo, Map<Integer, byte[]> map, byte[] bArr) throws SecurityException, IOException {
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(signatureInfo.signatureBlock);
        X509Certificate verifySourceStampCertificate = verifySourceStampCertificate(lengthPrefixedSlice, bArr);
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
        HashMap hashMap = new HashMap();
        while (lengthPrefixedSlice2.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
            hashMap.put(Integer.valueOf(lengthPrefixedSlice3.getInt()), lengthPrefixedSlice3);
        }
        for (Map.Entry<Integer, byte[]> entry : map.entrySet()) {
            if (!hashMap.containsKey(entry.getKey())) {
                throw new SecurityException(String.format("No signatures found for signature scheme %d", entry.getKey()));
            }
            verifySourceStampSignature(entry.getValue(), verifySourceStampCertificate, ApkSigningBlockUtils.getLengthPrefixedSlice((ByteBuffer) hashMap.get(entry.getKey())));
        }
        List<X509Certificate> list = Collections.EMPTY_LIST;
        if (lengthPrefixedSlice.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            ByteBuffer lengthPrefixedSlice5 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            byte[] bArr2 = new byte[lengthPrefixedSlice4.remaining()];
            lengthPrefixedSlice4.get(bArr2);
            lengthPrefixedSlice4.flip();
            verifySourceStampSignature(bArr2, verifySourceStampCertificate, lengthPrefixedSlice5);
            ApkSigningBlockUtils.VerifiedProofOfRotation verifySourceStampAttributes = verifySourceStampAttributes(lengthPrefixedSlice4, verifySourceStampCertificate);
            if (verifySourceStampAttributes != null) {
                list = verifySourceStampAttributes.certs;
            }
        }
        return SourceStampVerificationResult.verified(verifySourceStampCertificate, list);
    }

    private static X509Certificate verifySourceStampCertificate(ByteBuffer byteBuffer, byte[] bArr) throws IOException {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            byte[] readLengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(byteBuffer);
            try {
                X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(readLengthPrefixedByteArray));
                if (!Arrays.equals(bArr, computeSha256Digest(readLengthPrefixedByteArray))) {
                    throw new SecurityException("Certificate mismatch between APK and signature block");
                }
                return new VerbatimX509Certificate(x509Certificate, readLengthPrefixedByteArray);
            } catch (CertificateException e) {
                throw new SecurityException("Failed to decode certificate", e);
            }
        } catch (CertificateException e2) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
        }
    }

    private static void verifySourceStampSignature(byte[] bArr, X509Certificate x509Certificate, ByteBuffer byteBuffer) throws IOException {
        int i = 0;
        byte[] bArr2 = null;
        int i2 = -1;
        while (byteBuffer.hasRemaining()) {
            i++;
            try {
                ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
                if (lengthPrefixedSlice.remaining() < 8) {
                    throw new SecurityException("Signature record too short");
                }
                int i3 = lengthPrefixedSlice.getInt();
                if (ApkSigningBlockUtils.isSupportedSignatureAlgorithm(i3) && (i2 == -1 || ApkSigningBlockUtils.compareSignatureAlgorithm(i3, i2) > 0)) {
                    bArr2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice);
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
        Pair<String, ? extends AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = ApkSigningBlockUtils.getSignatureAlgorithmJcaSignatureAlgorithm(i2);
        String str = signatureAlgorithmJcaSignatureAlgorithm.first;
        AlgorithmParameterSpec algorithmParameterSpec = (AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second;
        PublicKey publicKey = x509Certificate.getPublicKey();
        try {
            Signature signature = Signature.getInstance(str);
            signature.initVerify(publicKey);
            if (algorithmParameterSpec != null) {
                signature.setParameter(algorithmParameterSpec);
            }
            signature.update(bArr);
            if (signature.verify(bArr2)) {
                return;
            }
            throw new SecurityException(str + " signature did not verify");
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
            throw new SecurityException("Failed to verify " + str + " signature", e2);
        }
    }

    private static Map<Integer, Map<Integer, byte[]>> getSignatureSchemeApkContentDigests(RandomAccessFile randomAccessFile, byte[] bArr) throws IOException {
        HashMap hashMap = new HashMap();
        try {
            hashMap.put(3, getApkContentDigestsFromSignatureBlock(ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V3_BLOCK_ID).signatureBlock));
        } catch (SignatureNotFoundException unused) {
        }
        try {
            hashMap.put(2, getApkContentDigestsFromSignatureBlock(ApkSigningBlockUtils.findSignature(randomAccessFile, APK_SIGNATURE_SCHEME_V2_BLOCK_ID).signatureBlock));
        } catch (SignatureNotFoundException unused2) {
        }
        if (bArr != null) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put(4, computeSha256Digest(bArr));
            hashMap.put(1, hashMap2);
        }
        return hashMap;
    }

    private static Map<Integer, byte[]> getApkContentDigestsFromSignatureBlock(ByteBuffer byteBuffer) throws IOException {
        HashMap hashMap = new HashMap();
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
        while (lengthPrefixedSlice.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(ApkSigningBlockUtils.getLengthPrefixedSlice(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice)));
            while (lengthPrefixedSlice2.hasRemaining()) {
                ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                int i = lengthPrefixedSlice3.getInt();
                hashMap.put(Integer.valueOf(ApkSigningBlockUtils.getSignatureAlgorithmContentDigestAlgorithm(i)), ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3));
            }
        }
        return hashMap;
    }

    private static Map<Integer, byte[]> getSignatureSchemeDigests(Map<Integer, Map<Integer, byte[]>> map) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<Integer, Map<Integer, byte[]>> entry : map.entrySet()) {
            hashMap.put(entry.getKey(), encodeApkContentDigests(getApkDigests(entry.getValue())));
        }
        return hashMap;
    }

    private static List<Pair<Integer, byte[]>> getApkDigests(Map<Integer, byte[]> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Integer, byte[]> entry : map.entrySet()) {
            arrayList.add(Pair.create(entry.getKey(), entry.getValue()));
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: android.util.apk.SourceStampVerifier$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SourceStampVerifier.lambda$getApkDigests$0((Pair) obj);
            }
        }));
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Integer lambda$getApkDigests$0(Pair pair) {
        return (Integer) pair.first;
    }

    private static byte[] getSourceStampCertificateDigest(StrictJarFile strictJarFile) throws IOException {
        ZipEntry findEntry = strictJarFile.findEntry(SOURCE_STAMP_CERTIFICATE_HASH_ZIP_ENTRY_NAME);
        if (findEntry == null) {
            return null;
        }
        return Streams.readFully(strictJarFile.getInputStream(findEntry));
    }

    private static byte[] getManifestBytes(StrictJarFile strictJarFile) throws IOException {
        ZipEntry findEntry = strictJarFile.findEntry("META-INF/MANIFEST.MF");
        if (findEntry == null) {
            return null;
        }
        return Streams.readFully(strictJarFile.getInputStream(findEntry));
    }

    private static byte[] encodeApkContentDigests(List<Pair<Integer, byte[]>> list) {
        Iterator<Pair<Integer, byte[]>> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().second.length + 12;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        for (Pair<Integer, byte[]> pair : list) {
            byte[] bArr = pair.second;
            allocate.putInt(bArr.length + 8);
            allocate.putInt(pair.first.intValue());
            allocate.putInt(bArr.length);
            allocate.put(bArr);
        }
        return allocate.array();
    }

    private static ApkSigningBlockUtils.VerifiedProofOfRotation verifySourceStampAttributes(ByteBuffer byteBuffer, X509Certificate x509Certificate) throws IOException {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(byteBuffer);
            ApkSigningBlockUtils.VerifiedProofOfRotation verifiedProofOfRotation = null;
            while (lengthPrefixedSlice.hasRemaining()) {
                ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
                if (lengthPrefixedSlice2.getInt() == PROOF_OF_ROTATION_ATTR_ID) {
                    if (verifiedProofOfRotation != null) {
                        throw new SecurityException("Encountered multiple Proof-of-rotation records when verifying source stamp signature");
                    }
                    verifiedProofOfRotation = ApkSigningBlockUtils.verifyProofOfRotationStruct(lengthPrefixedSlice2, certificateFactory);
                    try {
                        if (verifiedProofOfRotation.certs.size() > 0 && !Arrays.equals(verifiedProofOfRotation.certs.get(verifiedProofOfRotation.certs.size() - 1).getEncoded(), x509Certificate.getEncoded())) {
                            throw new SecurityException("Terminal certificate in Proof-of-rotation record does not match source stamp certificate");
                        }
                    } catch (CertificateEncodingException e) {
                        throw new SecurityException("Failed to encode certificate when comparing Proof-of-rotation record and source stamp certificate", e);
                    }
                }
            }
            return verifiedProofOfRotation;
        } catch (CertificateException e2) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e2);
        }
    }

    private static byte[] computeSha256Digest(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to find SHA-256", e);
        }
    }

    private static void closeApkJar(StrictJarFile strictJarFile) {
        if (strictJarFile == null) {
            return;
        }
        try {
            strictJarFile.close();
        } catch (IOException e) {
            Slog.e(TAG, "Could not close APK jar", e);
        }
    }
}
