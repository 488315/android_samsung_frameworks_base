package com.android.server.pm;

import android.content.pm.ApkChecksum;
import android.content.pm.Checksum;
import android.content.pm.IOnChecksumsReadyListener;
import android.content.pm.PackageManagerInternal;
import android.content.pm.Signature;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.incremental.IncrementalManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.apk.ApkSignatureSchemeV2Verifier;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.apk.ApkSignatureSchemeV4Verifier;
import android.util.apk.ApkSignatureVerifier;
import android.util.apk.ApkSigningBlockUtils;
import android.util.apk.ByteBufferFactory;
import android.util.apk.SignatureInfo;
import android.util.apk.SignatureNotFoundException;
import android.util.apk.VerityBuilder;

import com.android.internal.security.VerityUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.pm.pkg.AndroidPackage;

import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.security.DigestException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ApkChecksums {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.ApkChecksums$1, reason: invalid class name */
    public final class AnonymousClass1 implements ByteBufferFactory {
        public final ByteBuffer create(int i) {
            return ByteBuffer.allocate(i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final Producer mContext;
        public final Producer mHandlerProducer;
        public final Producer mIncrementalManagerProducer;
        public final Producer mPackageManagerInternalProducer;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        interface Producer {
            Object produce();
        }

        public Injector(
                Producer producer, Producer producer2, Producer producer3, Producer producer4) {
            this.mContext = producer;
            this.mHandlerProducer = producer2;
            this.mIncrementalManagerProducer = producer3;
            this.mPackageManagerInternalProducer = producer4;
        }

        public final PackageManagerInternal getPackageManagerInternal() {
            return (PackageManagerInternal) this.mPackageManagerInternalProducer.produce();
        }
    }

    static {
        Certificate[] certificateArr = new Certificate[0];
    }

    public static String buildDigestsPathForApk(String str) {
        if (!ApkLiteParseUtils.isApkPath(str)) {
            throw new IllegalStateException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Code path is not an apk ", str));
        }
        return str.substring(0, str.length() - 4) + ".digests";
    }

    public static void calculateChecksumIfRequested(Map map, String str, File file, int i, int i2) {
        String str2;
        if ((i & i2) == 0 || map.containsKey(Integer.valueOf(i2))) {
            return;
        }
        int max = (int) Math.max(4096L, Math.min(131072L, file.length()));
        byte[] bArr = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr2 = new byte[max];
                if (i2 == 2) {
                    str2 = "MD5";
                } else if (i2 == 4) {
                    str2 = "SHA1";
                } else if (i2 == 8) {
                    str2 = "SHA256";
                } else {
                    if (i2 != 16) {
                        throw new NoSuchAlgorithmException("Invalid checksum type: " + i2);
                    }
                    str2 = "SHA512";
                }
                MessageDigest messageDigest = MessageDigest.getInstance(str2);
                while (true) {
                    int read = fileInputStream.read(bArr2);
                    if (read == -1) {
                        break;
                    } else {
                        messageDigest.update(bArr2, 0, read);
                    }
                }
                byte[] digest = messageDigest.digest();
                fileInputStream.close();
                bArr = digest;
            } finally {
            }
        } catch (IOException e) {
            Slog.e(
                    "ApkChecksums",
                    "Error reading " + file.getAbsolutePath() + " to compute hash.",
                    e);
        } catch (NoSuchAlgorithmException e2) {
            Slog.e("ApkChecksums", "Device does not support MessageDigest algorithm", e2);
        }
        if (bArr != null) {
            map.put(Integer.valueOf(i2), new ApkChecksum(str, i2, bArr));
        }
    }

    public static Set convertToSet(Certificate[] certificateArr) {
        if (certificateArr == null) {
            return null;
        }
        ArraySet arraySet = new ArraySet(certificateArr.length);
        for (Certificate certificate : certificateArr) {
            arraySet.add(new Signature(certificate.getEncoded()));
        }
        return arraySet;
    }

    public static void getAvailableApkChecksums(
            String str,
            File file,
            int i,
            String str2,
            Certificate[] certificateArr,
            Map map,
            Injector injector) {
        Map map2;
        ArrayMap arrayMap;
        byte[] bArr;
        byte[] bArr2;
        Signature[] signatures;
        Signature[] pastSigningCertificates;
        Signature signature;
        Signature signature2;
        Signature signature3;
        ApkChecksum apkChecksum;
        byte[] fsverityDigest;
        if (file.exists()) {
            String absolutePath = file.getAbsolutePath();
            if (isRequired(1, i, map)) {
                if (!VerityUtils.hasFsverity(absolutePath)
                        || (fsverityDigest = VerityUtils.getFsverityDigest(absolutePath)) == null) {
                    try {
                        byte[] bArr3 =
                                (byte[])
                                        ApkSignatureSchemeV4Verifier.extractCertificates(
                                                        absolutePath)
                                                .contentDigests
                                                .getOrDefault(3, null);
                        if (bArr3 != null) {
                            apkChecksum =
                                    new ApkChecksum(
                                            str,
                                            1,
                                            verityHashForFile(new File(absolutePath), bArr3));
                        }
                    } catch (SignatureNotFoundException unused) {
                    } catch (SecurityException | SignatureException e) {
                        Slog.e("ApkChecksums", "V4 signature error", e);
                    }
                    apkChecksum = null;
                } else {
                    apkChecksum = new ApkChecksum(str, 1, fsverityDigest);
                }
                if (apkChecksum != null) {
                    ((ArrayMap) map).put(Integer.valueOf(apkChecksum.getType()), apkChecksum);
                }
            }
            if (isRequired(32, i, map) || isRequired(64, i, map)) {
                ParseResult verifySignaturesInternal =
                        ApkSignatureVerifier.verifySignaturesInternal(
                                ParseTypeImpl.forDefaultParsing(), absolutePath, 2, false);
                if (verifySignaturesInternal.isError()) {
                    if (!(verifySignaturesInternal.getException()
                            instanceof SignatureNotFoundException)) {
                        Slog.e(
                                "ApkChecksums",
                                "Signature verification error",
                                verifySignaturesInternal.getException());
                    }
                    map2 = null;
                } else {
                    map2 =
                            ((ApkSignatureVerifier.SigningDetailsWithDigests)
                                            verifySignaturesInternal.getResult())
                                    .contentDigests;
                }
                if (map2 == null) {
                    arrayMap = null;
                } else {
                    arrayMap = new ArrayMap();
                    if ((i & 32) != 0 && (bArr2 = (byte[]) map2.getOrDefault(1, null)) != null) {
                        arrayMap.put(32, new ApkChecksum(str, 32, bArr2));
                    }
                    if ((i & 64) != 0 && (bArr = (byte[]) map2.getOrDefault(2, null)) != null) {
                        arrayMap.put(64, new ApkChecksum(str, 64, bArr));
                    }
                }
                if (arrayMap != null) {
                    ((ArrayMap) map).putAll((Map) arrayMap);
                }
            }
            if (PackageManagerServiceUtils.isInstalledByAdb(str2)) {
                return;
            }
            if (certificateArr == null || certificateArr.length != 0) {
                File file2 = new File(buildDigestsPathForApk(file.getAbsolutePath()));
                if (!file2.exists()) {
                    file2 = null;
                }
                if (file2 == null) {
                    return;
                }
                File file3 =
                        new File(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                        file2.getAbsolutePath(), ".signature"));
                if (!file3.exists()) {
                    file3 = null;
                }
                try {
                    Checksum[] readChecksums = readChecksums(file2);
                    if (file3 != null) {
                        Certificate[] verifySignature =
                                verifySignature(readChecksums, Files.readAllBytes(file3.toPath()));
                        if (verifySignature != null && verifySignature.length != 0) {
                            signatures = new Signature[verifySignature.length];
                            int length = verifySignature.length;
                            for (int i2 = 0; i2 < length; i2++) {
                                signatures[i2] = new Signature(verifySignature[i2].getEncoded());
                            }
                            pastSigningCertificates = null;
                        }
                        Slog.e("ApkChecksums", "Error validating signature");
                        return;
                    }
                    AndroidPackage androidPackage =
                            injector.getPackageManagerInternal().getPackage(str2);
                    if (androidPackage == null) {
                        Slog.e("ApkChecksums", "Installer package not found.");
                        return;
                    } else {
                        signatures = androidPackage.getSigningDetails().getSignatures();
                        pastSigningCertificates =
                                androidPackage.getSigningDetails().getPastSigningCertificates();
                    }
                    if (signatures != null
                            && signatures.length != 0
                            && (signature = signatures[0]) != null) {
                        byte[] byteArray = signature.toByteArray();
                        Set convertToSet = convertToSet(certificateArr);
                        if (convertToSet != null && !((ArraySet) convertToSet).isEmpty()) {
                            int length2 = signatures.length;
                            int i3 = 0;
                            while (true) {
                                if (i3 >= length2) {
                                    signature2 = null;
                                    break;
                                }
                                signature2 = signatures[i3];
                                if (((ArraySet) convertToSet).contains(signature2)) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                            if (signature2 == null) {
                                if (pastSigningCertificates != null) {
                                    for (Signature signature4 : pastSigningCertificates) {
                                        if (((ArraySet) convertToSet).contains(signature4)) {
                                            signature3 = signature4;
                                            break;
                                        }
                                    }
                                }
                                signature3 = null;
                                signature2 = signature3;
                            }
                            if (signature2 == null) {
                                return;
                            } else {
                                byteArray = signature2.toByteArray();
                            }
                        }
                        for (Checksum checksum : readChecksums) {
                            ApkChecksum apkChecksum2 =
                                    (ApkChecksum)
                                            ((ArrayMap) map)
                                                    .get(Integer.valueOf(checksum.getType()));
                            if (apkChecksum2 != null
                                    && !Arrays.equals(
                                            apkChecksum2.getValue(), checksum.getValue())) {
                                throw new InvalidParameterException(
                                        "System digest "
                                                + checksum.getType()
                                                + " mismatch, can't bind installer-provided digests"
                                                + " to the APK.");
                            }
                        }
                        for (Checksum checksum2 : readChecksums) {
                            if (isRequired(checksum2.getType(), i, map)) {
                                ((ArrayMap) map)
                                        .put(
                                                Integer.valueOf(checksum2.getType()),
                                                new ApkChecksum(str, checksum2, str2, byteArray));
                            }
                        }
                        return;
                    }
                    Slog.e("ApkChecksums", "Can't obtain certificates.");
                } catch (IOException e2) {
                    Slog.e("ApkChecksums", "Error reading .digests or .signature", e2);
                } catch (InvalidParameterException
                        | NoSuchAlgorithmException
                        | SignatureException e3) {
                    Slog.e(
                            "ApkChecksums",
                            "Error validating digests. Invalid digests will be removed",
                            e3);
                    try {
                        Files.deleteIfExists(file2.toPath());
                        if (file3 != null) {
                            Files.deleteIfExists(file3.toPath());
                        }
                    } catch (IOException unused2) {
                    }
                } catch (CertificateEncodingException e4) {
                    Slog.e("ApkChecksums", "Error encoding trustedInstallers", e4);
                }
            }
        }
    }

    public static void getChecksums(
            List list,
            int i,
            int i2,
            String str,
            Certificate[] certificateArr,
            IOnChecksumsReadyListener iOnChecksumsReadyListener,
            Injector injector) {
        ArrayList arrayList = (ArrayList) list;
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            String str2 = (String) ((Pair) arrayList.get(i3)).first;
            File file = (File) ((Pair) arrayList.get(i3)).second;
            ArrayMap arrayMap = new ArrayMap();
            arrayList2.add(arrayMap);
            try {
                getAvailableApkChecksums(
                        str2, file, i | i2, str, certificateArr, arrayMap, injector);
            } catch (Throwable th) {
                Slog.e("ApkChecksums", "Preferred checksum calculation error", th);
            }
        }
        processRequiredChecksums(
                list,
                arrayList2,
                i2,
                iOnChecksumsReadyListener,
                injector,
                SystemClock.uptimeMillis());
    }

    public static void getRequiredApkChecksums(String str, File file, int i, Map map) {
        String absolutePath = file.getAbsolutePath();
        SignatureInfo signatureInfo = null;
        if (isRequired(1, i, map)) {
            try {
                map.put(
                        1,
                        new ApkChecksum(
                                str,
                                1,
                                verityHashForFile(
                                        file,
                                        VerityBuilder.generateFsVerityRootHash(
                                                absolutePath,
                                                (byte[]) null,
                                                new AnonymousClass1()))));
            } catch (IOException | DigestException | NoSuchAlgorithmException e) {
                Slog.e("ApkChecksums", "Error calculating WHOLE_MERKLE_ROOT_4K_SHA256", e);
            }
        }
        calculateChecksumIfRequested(map, str, file, i, 2);
        calculateChecksumIfRequested(map, str, file, i, 4);
        calculateChecksumIfRequested(map, str, file, i, 8);
        calculateChecksumIfRequested(map, str, file, i, 16);
        boolean z = ((i & 32) == 0 || map.containsKey(32)) ? false : true;
        boolean z2 = ((i & 64) == 0 || map.containsKey(64)) ? false : true;
        if (z || z2) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                try {
                    try {
                        try {
                            signatureInfo =
                                    ApkSignatureSchemeV3Verifier.findSignature(randomAccessFile);
                        } catch (SignatureNotFoundException unused) {
                        }
                    } catch (Throwable th) {
                        try {
                            randomAccessFile.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (SignatureNotFoundException unused2) {
                    signatureInfo = ApkSignatureSchemeV2Verifier.findSignature(randomAccessFile);
                }
                if (signatureInfo == null) {
                    Slog.e(
                            "ApkChecksums",
                            "V2/V3 signatures not found in " + file.getAbsolutePath());
                } else {
                    int[] iArr = (z && z2) ? new int[] {1, 2} : z ? new int[] {1} : new int[] {2};
                    byte[][] computeContentDigestsPer1MbChunk =
                            ApkSigningBlockUtils.computeContentDigestsPer1MbChunk(
                                    iArr, randomAccessFile.getFD(), signatureInfo);
                    int length = iArr.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        int i3 = iArr[i2];
                        int i4 = i3 != 1 ? i3 != 2 ? -1 : 64 : 32;
                        if (i4 != -1) {
                            map.put(
                                    Integer.valueOf(i4),
                                    new ApkChecksum(str, i4, computeContentDigestsPer1MbChunk[i2]));
                        }
                    }
                }
                randomAccessFile.close();
            } catch (IOException | DigestException e2) {
                Slog.e("ApkChecksums", "Error computing hash.", e2);
            }
        }
    }

    public static boolean isRequired(int i, int i2, Map map) {
        return ((i2 & i) == 0 || map.containsKey(Integer.valueOf(i))) ? false : true;
    }

    public static boolean needToWait(File file, int i, Map map, Injector injector) {
        if (!isRequired(1, i, map)
                && !isRequired(2, i, map)
                && !isRequired(4, i, map)
                && !isRequired(8, i, map)
                && !isRequired(16, i, map)
                && !isRequired(32, i, map)
                && !isRequired(64, i, map)) {
            return false;
        }
        String absolutePath = file.getAbsolutePath();
        if (!IncrementalManager.isIncrementalPath(absolutePath)) {
            return false;
        }
        IncrementalManager incrementalManager =
                (IncrementalManager) injector.mIncrementalManagerProducer.produce();
        if (incrementalManager == null) {
            Slog.e("ApkChecksums", "IncrementalManager is missing.");
            return false;
        }
        if (incrementalManager.openStorage(absolutePath) != null) {
            return !r4.isFileFullyLoaded(absolutePath);
        }
        BootReceiver$$ExternalSyntheticOutline0.m(
                "IncrementalStorage is missing for a path on IncFs: ",
                absolutePath,
                "ApkChecksums");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v6 */
    public static void processRequiredChecksums(
            final List list,
            final List list2,
            final int i,
            final IOnChecksumsReadyListener iOnChecksumsReadyListener,
            final Injector injector,
            final long j) {
        String str;
        boolean z;
        Injector injector2 = injector;
        boolean z2 =
                SystemClock.uptimeMillis() - j
                        >= BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            String str2 = (String) ((Pair) arrayList2.get(i2)).first;
            File file = (File) ((Pair) arrayList2.get(i2)).second;
            Map map = (Map) ((ArrayList) list2).get(i2);
            if (z2 && i == 0) {
                z = z2;
            } else {
                try {
                    if (needToWait(file, i, map, injector2)) {
                        Handler handler = (Handler) injector2.mHandlerProducer.produce();
                        injector2 = "ApkChecksums";
                        z = z2;
                        handler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.pm.ApkChecksums$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ApkChecksums.processRequiredChecksums(
                                                list,
                                                list2,
                                                i,
                                                iOnChecksumsReadyListener,
                                                injector,
                                                j);
                                    }
                                },
                                1000L);
                        return;
                    }
                    try {
                        z = z2;
                        getRequiredApkChecksums(str2, file, i, map);
                    } catch (Throwable th) {
                        th = th;
                        str = injector2;
                    }
                    th = th;
                    str = injector2;
                } catch (Throwable th2) {
                    th = th2;
                    str = "ApkChecksums";
                    z = z2;
                }
                Slog.e(str, "Required checksum calculation error", th);
                i2++;
                injector2 = injector;
                z2 = z;
            }
            arrayList.addAll(map.values());
            i2++;
            injector2 = injector;
            z2 = z;
        }
        try {
            iOnChecksumsReadyListener.onChecksumsReady(arrayList);
        } catch (RemoteException e) {
            Slog.w("ApkChecksums", e);
        }
    }

    public static Checksum[] readChecksums(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < 100; i++) {
                    try {
                        arrayList.add(Checksum.readFromStream(dataInputStream));
                    } catch (EOFException unused) {
                    }
                }
                Checksum[] checksumArr =
                        (Checksum[]) arrayList.toArray(new Checksum[arrayList.size()]);
                dataInputStream.close();
                fileInputStream.close();
                return checksumArr;
            } finally {
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static Certificate[] verifySignature(Checksum[] checksumArr, byte[] bArr) {
        if (bArr == null || bArr.length > 35840) {
            throw new SignatureException("Invalid signature");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeChecksums(byteArrayOutputStream, checksumArr);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            PKCS7 pkcs7 = new PKCS7(bArr);
            X509Certificate[] certificates = pkcs7.getCertificates();
            if (certificates == null || certificates.length == 0) {
                throw new SignatureException("Signature missing certificates");
            }
            SignerInfo[] verify = pkcs7.verify(byteArray);
            if (verify == null || verify.length == 0) {
                throw new SignatureException("Verification failed");
            }
            ArrayList arrayList = new ArrayList(verify.length);
            for (SignerInfo signerInfo : verify) {
                ArrayList certificateChain = signerInfo.getCertificateChain(pkcs7);
                if (certificateChain == null) {
                    throw new SignatureException(
                            "Verification passed, but certification chain is empty.");
                }
                arrayList.addAll(certificateChain);
            }
            return (Certificate[]) arrayList.toArray(new Certificate[arrayList.size()]);
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static byte[] verityHashForFile(File file, byte[] bArr) {
        try {
            ByteBuffer allocate = ByteBuffer.allocate(256);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.put((byte) 1);
            allocate.put((byte) 1);
            allocate.put((byte) 12);
            allocate.put((byte) 0);
            allocate.putInt(0);
            allocate.putLong(file.length());
            allocate.put(bArr);
            for (int i = 0; i < 208; i++) {
                allocate.put((byte) 0);
            }
            allocate.flip();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            messageDigest.update(allocate);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            Slog.e("ApkChecksums", "Device does not support MessageDigest algorithm", e);
            return null;
        }
    }

    public static void writeChecksums(OutputStream outputStream, Checksum[] checksumArr) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            for (Checksum checksum : checksumArr) {
                Checksum.writeToStream(dataOutputStream, checksum);
            }
            dataOutputStream.close();
        } catch (Throwable th) {
            try {
                dataOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
