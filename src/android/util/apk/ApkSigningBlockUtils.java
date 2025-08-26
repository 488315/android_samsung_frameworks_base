package android.util.apk;

import android.security.keystore.KeyProperties;
import android.util.ArrayMap;
import android.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ApkSigningBlockUtils {
    private static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    private static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    private static final int CHUNK_SIZE_BYTES = 1048576;
    public static final int CONTENT_DIGEST_CHUNKED_SHA256 = 1;
    public static final int CONTENT_DIGEST_CHUNKED_SHA512 = 2;
    public static final int CONTENT_DIGEST_SHA256 = 4;
    public static final int CONTENT_DIGEST_VERITY_CHUNKED_SHA256 = 3;
    static final int SIGNATURE_DSA_WITH_SHA256 = 769;
    static final int SIGNATURE_ECDSA_WITH_SHA256 = 513;
    static final int SIGNATURE_ECDSA_WITH_SHA512 = 514;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA256 = 259;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA512 = 260;
    static final int SIGNATURE_RSA_PSS_WITH_SHA256 = 257;
    static final int SIGNATURE_RSA_PSS_WITH_SHA512 = 258;
    static final int SIGNATURE_VERITY_DSA_WITH_SHA256 = 1061;
    static final int SIGNATURE_VERITY_ECDSA_WITH_SHA256 = 1059;
    static final int SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256 = 1057;

    static boolean isSupportedSignatureAlgorithm(int i) {
        if (i == 513 || i == 514 || i == 769 || i == 1057 || i == SIGNATURE_VERITY_ECDSA_WITH_SHA256 || i == 1061) {
            return true;
        }
        switch (i) {
            case 257:
            case 258:
            case 259:
            case 260:
                return true;
            default:
                return false;
        }
    }

    private ApkSigningBlockUtils() {
    }

    static SignatureInfo findSignature(RandomAccessFile randomAccessFile, int i) throws SignatureNotFoundException, IOException {
        Pair<ByteBuffer, Long> eocd = getEocd(randomAccessFile);
        ByteBuffer byteBuffer = eocd.first;
        long jLongValue = eocd.second.longValue();
        if (ZipUtils.isZip64EndOfCentralDirectoryLocatorPresent(randomAccessFile, jLongValue)) {
            throw new SignatureNotFoundException("ZIP64 APK not supported");
        }
        long centralDirOffset = getCentralDirOffset(byteBuffer, jLongValue);
        Pair<ByteBuffer, Long> pairFindApkSigningBlock = findApkSigningBlock(randomAccessFile, centralDirOffset);
        ByteBuffer byteBuffer2 = pairFindApkSigningBlock.first;
        return new SignatureInfo(findApkSignatureSchemeBlock(byteBuffer2, i), pairFindApkSigningBlock.second.longValue(), centralDirOffset, jLongValue, byteBuffer);
    }

    static void verifyIntegrity(Map<Integer, byte[]> map, RandomAccessFile randomAccessFile, SignatureInfo signatureInfo) throws SecurityException {
        if (map.isEmpty()) {
            throw new SecurityException("No digests provided");
        }
        ArrayMap arrayMap = new ArrayMap();
        boolean z = true;
        if (map.containsKey(1)) {
            arrayMap.put(1, map.get(1));
        }
        if (map.containsKey(2)) {
            arrayMap.put(2, map.get(2));
        }
        boolean z2 = false;
        if (!arrayMap.isEmpty()) {
            try {
                verifyIntegrityFor1MbChunkBasedAlgorithm(arrayMap, randomAccessFile.getFD(), signatureInfo);
                z = false;
            } catch (IOException e) {
                throw new SecurityException("Cannot get FD", e);
            }
        }
        if (map.containsKey(3)) {
            verifyIntegrityForVerityBasedAlgorithm(map.get(3), randomAccessFile, signatureInfo);
        } else {
            z2 = z;
        }
        if (z2) {
            throw new SecurityException("No known digest exists for integrity check");
        }
    }

    private static void verifyIntegrityFor1MbChunkBasedAlgorithm(Map<Integer, byte[]> map, FileDescriptor fileDescriptor, SignatureInfo signatureInfo) throws SecurityException {
        int size = map.size();
        int[] iArr = new int[size];
        Iterator<Integer> it = map.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        try {
            byte[][] bArrComputeContentDigestsPer1MbChunk = computeContentDigestsPer1MbChunk(iArr, fileDescriptor, signatureInfo);
            for (int i2 = 0; i2 < size; i2++) {
                int i3 = iArr[i2];
                if (!MessageDigest.isEqual(map.get(Integer.valueOf(i3)), bArrComputeContentDigestsPer1MbChunk[i2])) {
                    throw new SecurityException(getContentDigestAlgorithmJcaDigestAlgorithm(i3) + " digest of contents did not verify");
                }
            }
        } catch (DigestException e) {
            throw new SecurityException("Failed to compute digest(s) of contents", e);
        }
    }

    public static byte[][] computeContentDigestsPer1MbChunk(int[] iArr, FileDescriptor fileDescriptor, SignatureInfo signatureInfo) throws DigestException {
        DataSource dataSourceCreate = DataSource.create(fileDescriptor, 0L, signatureInfo.apkSigningBlockOffset);
        DataSource dataSourceCreate2 = DataSource.create(fileDescriptor, signatureInfo.centralDirOffset, signatureInfo.eocdOffset - signatureInfo.centralDirOffset);
        ByteBuffer byteBufferDuplicate = signatureInfo.eocd.duplicate();
        byteBufferDuplicate.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferDuplicate, signatureInfo.apkSigningBlockOffset);
        return computeContentDigestsPer1MbChunk(iArr, new DataSource[]{dataSourceCreate, dataSourceCreate2, new ByteBufferDataSource(byteBufferDuplicate)});
    }

    private static byte[][] computeContentDigestsPer1MbChunk(int[] iArr, DataSource[] dataSourceArr) throws DigestException {
        char c;
        DataSource[] dataSourceArr2 = dataSourceArr;
        long chunkCount = 0;
        for (DataSource dataSource : dataSourceArr2) {
            chunkCount += getChunkCount(dataSource.size());
        }
        if (chunkCount >= 2097151) {
            throw new DigestException("Too many chunks: " + chunkCount);
        }
        int i = (int) chunkCount;
        byte[][] bArr = new byte[iArr.length][];
        int i2 = 0;
        while (true) {
            c = 5;
            if (i2 >= iArr.length) {
                break;
            }
            byte[] bArr2 = new byte[(getContentDigestAlgorithmOutputSizeBytes(iArr[i2]) * i) + 5];
            bArr2[0] = 90;
            setUnsignedInt32LittleEndian(i, bArr2, 1);
            bArr[i2] = bArr2;
            i2++;
        }
        byte[] bArr3 = new byte[5];
        bArr3[0] = -91;
        int length = iArr.length;
        MessageDigest[] messageDigestArr = new MessageDigest[length];
        for (int i3 = 0; i3 < iArr.length; i3++) {
            String contentDigestAlgorithmJcaDigestAlgorithm = getContentDigestAlgorithmJcaDigestAlgorithm(iArr[i3]);
            try {
                messageDigestArr[i3] = MessageDigest.getInstance(contentDigestAlgorithmJcaDigestAlgorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(contentDigestAlgorithmJcaDigestAlgorithm + " digest not supported", e);
            }
        }
        MultipleDigestDataDigester multipleDigestDataDigester = new MultipleDigestDataDigester(messageDigestArr);
        int length2 = dataSourceArr2.length;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i5 < length2) {
            DataSource dataSource2 = dataSourceArr2[i5];
            byte[][] bArr4 = bArr;
            long size = dataSource2.size();
            long j = 0;
            while (size > 0) {
                char c2 = c;
                int iMin = (int) Math.min(size, 1048576L);
                setUnsignedInt32LittleEndian(iMin, bArr3, 1);
                for (int i7 = 0; i7 < length; i7++) {
                    messageDigestArr[i7].update(bArr3);
                }
                byte[] bArr5 = bArr3;
                long j2 = j;
                try {
                    dataSource2.feedIntoDataDigester(multipleDigestDataDigester, j2, iMin);
                    int i8 = 0;
                    while (i8 < iArr.length) {
                        int i9 = iArr[i8];
                        byte[] bArr6 = bArr4[i8];
                        int contentDigestAlgorithmOutputSizeBytes = getContentDigestAlgorithmOutputSizeBytes(i9);
                        DataSource dataSource3 = dataSource2;
                        MessageDigest messageDigest = messageDigestArr[i8];
                        long j3 = size;
                        int iDigest = messageDigest.digest(bArr6, (i6 * contentDigestAlgorithmOutputSizeBytes) + 5, contentDigestAlgorithmOutputSizeBytes);
                        if (iDigest != contentDigestAlgorithmOutputSizeBytes) {
                            throw new RuntimeException("Unexpected output size of " + messageDigest.getAlgorithm() + " digest: " + iDigest);
                        }
                        i8++;
                        dataSource2 = dataSource3;
                        size = j3;
                    }
                    long j4 = iMin;
                    j = j2 + j4;
                    size -= j4;
                    i6++;
                    c = c2;
                    bArr3 = bArr5;
                } catch (IOException e2) {
                    throw new DigestException("Failed to digest chunk #" + i6 + " of section #" + i4, e2);
                }
            }
            i4++;
            i5++;
            dataSourceArr2 = dataSourceArr;
            bArr = bArr4;
        }
        byte[][] bArr7 = bArr;
        byte[][] bArr8 = new byte[iArr.length][];
        for (int i10 = 0; i10 < iArr.length; i10++) {
            int i11 = iArr[i10];
            byte[] bArr9 = bArr7[i10];
            String contentDigestAlgorithmJcaDigestAlgorithm2 = getContentDigestAlgorithmJcaDigestAlgorithm(i11);
            try {
                bArr8[i10] = MessageDigest.getInstance(contentDigestAlgorithmJcaDigestAlgorithm2).digest(bArr9);
            } catch (NoSuchAlgorithmException e3) {
                throw new RuntimeException(contentDigestAlgorithmJcaDigestAlgorithm2 + " digest not supported", e3);
            }
        }
        return bArr8;
    }

    static byte[] parseVerityDigestAndVerifySourceLength(byte[] bArr, long j, SignatureInfo signatureInfo) throws SecurityException {
        if (bArr.length != 40) {
            throw new SecurityException("Verity digest size is wrong: " + bArr.length);
        }
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.position(32);
        if (byteBufferOrder.getLong() != j - (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset)) {
            throw new SecurityException("APK content size did not verify");
        }
        return Arrays.copyOfRange(bArr, 0, 32);
    }

    private static void verifyIntegrityForVerityBasedAlgorithm(byte[] bArr, RandomAccessFile randomAccessFile, SignatureInfo signatureInfo) throws SecurityException {
        try {
            if (Arrays.equals(parseVerityDigestAndVerifySourceLength(bArr, randomAccessFile.getChannel().size(), signatureInfo), VerityBuilder.generateApkVerityTree(randomAccessFile, signatureInfo, new ByteBufferFactory() { // from class: android.util.apk.ApkSigningBlockUtils.1
                @Override // android.util.apk.ByteBufferFactory
                public ByteBuffer create(int i) {
                    return ByteBuffer.allocate(i);
                }
            }).rootHash)) {
            } else {
                throw new SecurityException("APK verity digest of contents did not verify");
            }
        } catch (IOException | DigestException | NoSuchAlgorithmException e) {
            throw new SecurityException("Error during verification", e);
        }
    }

    static Pair<ByteBuffer, Long> getEocd(RandomAccessFile randomAccessFile) throws SignatureNotFoundException, IOException {
        Pair<ByteBuffer, Long> pairFindZipEndOfCentralDirectoryRecord = ZipUtils.findZipEndOfCentralDirectoryRecord(randomAccessFile);
        if (pairFindZipEndOfCentralDirectoryRecord != null) {
            return pairFindZipEndOfCentralDirectoryRecord;
        }
        throw new SignatureNotFoundException("Not an APK file: ZIP End of Central Directory record not found");
    }

    static long getCentralDirOffset(ByteBuffer byteBuffer, long j) throws SignatureNotFoundException {
        long zipEocdCentralDirectoryOffset = ZipUtils.getZipEocdCentralDirectoryOffset(byteBuffer);
        if (zipEocdCentralDirectoryOffset <= j) {
            if (ZipUtils.getZipEocdCentralDirectorySizeBytes(byteBuffer) + zipEocdCentralDirectoryOffset == j) {
                return zipEocdCentralDirectoryOffset;
            }
            throw new SignatureNotFoundException("ZIP Central Directory is not immediately followed by End of Central Directory");
        }
        throw new SignatureNotFoundException("ZIP Central Directory offset out of range: " + zipEocdCentralDirectoryOffset + ". ZIP End of Central Directory offset: " + j);
    }

    private static long getChunkCount(long j) {
        return (j + 1048575) / 1048576;
    }

    static int compareSignatureAlgorithm(int i, int i2) {
        return compareContentDigestAlgorithm(getSignatureAlgorithmContentDigestAlgorithm(i), getSignatureAlgorithmContentDigestAlgorithm(i2));
    }

    private static int compareContentDigestAlgorithm(int i, int i2) {
        if (i == 1) {
            if (i2 == 1) {
                return 0;
            }
            if (i2 == 2 || i2 == 3) {
                return -1;
            }
            throw new IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
        }
        if (i == 2) {
            if (i2 != 1) {
                if (i2 == 2) {
                    return 0;
                }
                if (i2 != 3) {
                    throw new IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
                }
            }
            return 1;
        }
        if (i != 3) {
            throw new IllegalArgumentException("Unknown digestAlgorithm1: " + i);
        }
        if (i2 == 1) {
            return 1;
        }
        if (i2 == 2) {
            return -1;
        }
        if (i2 == 3) {
            return 0;
        }
        throw new IllegalArgumentException("Unknown digestAlgorithm2: " + i2);
    }

    static int getSignatureAlgorithmContentDigestAlgorithm(int i) {
        if (i == 513) {
            return 1;
        }
        if (i == 514) {
            return 2;
        }
        if (i == 769) {
            return 1;
        }
        if (i == 1057 || i == SIGNATURE_VERITY_ECDSA_WITH_SHA256 || i == 1061) {
            return 3;
        }
        switch (i) {
            case 257:
            case 259:
                return 1;
            case 258:
            case 260:
                return 2;
            default:
                throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i));
        }
    }

    static String getContentDigestAlgorithmJcaDigestAlgorithm(int i) {
        if (i == 1) {
            return "SHA-256";
        }
        if (i == 2) {
            return KeyProperties.DIGEST_SHA512;
        }
        if (i == 3) {
            return "SHA-256";
        }
        throw new IllegalArgumentException("Unknown content digest algorthm: " + i);
    }

    private static int getContentDigestAlgorithmOutputSizeBytes(int i) {
        if (i == 1) {
            return 32;
        }
        if (i == 2) {
            return 64;
        }
        if (i == 3) {
            return 32;
        }
        throw new IllegalArgumentException("Unknown content digest algorthm: " + i);
    }

    static String getSignatureAlgorithmJcaKeyAlgorithm(int i) {
        if (i == 513 || i == 514) {
            return KeyProperties.KEY_ALGORITHM_EC;
        }
        if (i == 769) {
            return "DSA";
        }
        if (i == 1057) {
            return "RSA";
        }
        if (i == SIGNATURE_VERITY_ECDSA_WITH_SHA256) {
            return KeyProperties.KEY_ALGORITHM_EC;
        }
        if (i != 1061) {
            switch (i) {
                case 257:
                case 258:
                case 259:
                case 260:
                    return "RSA";
                default:
                    throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i));
            }
        }
        return "DSA";
    }

    static Pair<String, ? extends AlgorithmParameterSpec> getSignatureAlgorithmJcaSignatureAlgorithm(int i) {
        if (i != 513) {
            if (i == 514) {
                return Pair.create("SHA512withECDSA", null);
            }
            if (i != 769) {
                if (i != 1057) {
                    if (i != SIGNATURE_VERITY_ECDSA_WITH_SHA256) {
                        if (i != 1061) {
                            switch (i) {
                                case 257:
                                    return Pair.create("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                                case 258:
                                    return Pair.create("SHA512withRSA/PSS", new PSSParameterSpec(KeyProperties.DIGEST_SHA512, "MGF1", MGF1ParameterSpec.SHA512, 64, 1));
                                case 259:
                                    break;
                                case 260:
                                    return Pair.create("SHA512withRSA", null);
                                default:
                                    throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString(i));
                            }
                        }
                    }
                }
                return Pair.create("SHA256withRSA", null);
            }
            return Pair.create("SHA256withDSA", null);
        }
        return Pair.create("SHA256withECDSA", null);
    }

    static ByteBuffer sliceFromTo(ByteBuffer byteBuffer, int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("start: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("end < start: " + i2 + " < " + i);
        }
        int iCapacity = byteBuffer.capacity();
        if (i2 > byteBuffer.capacity()) {
            throw new IllegalArgumentException("end > capacity: " + i2 + " > " + iCapacity);
        }
        int iLimit = byteBuffer.limit();
        int iPosition = byteBuffer.position();
        try {
            byteBuffer.position(0);
            byteBuffer.limit(i2);
            byteBuffer.position(i);
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            byteBufferSlice.order(byteBuffer.order());
            return byteBufferSlice;
        } finally {
            byteBuffer.position(0);
            byteBuffer.limit(iLimit);
            byteBuffer.position(iPosition);
        }
    }

    static ByteBuffer getByteBuffer(ByteBuffer byteBuffer, int i) throws BufferUnderflowException {
        if (i < 0) {
            throw new IllegalArgumentException("size: " + i);
        }
        int iLimit = byteBuffer.limit();
        int iPosition = byteBuffer.position();
        int i2 = i + iPosition;
        if (i2 < iPosition || i2 > iLimit) {
            throw new BufferUnderflowException();
        }
        byteBuffer.limit(i2);
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            byteBufferSlice.order(byteBuffer.order());
            byteBuffer.position(i2);
            return byteBufferSlice;
        } finally {
            byteBuffer.limit(iLimit);
        }
    }

    static ByteBuffer getLengthPrefixedSlice(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() < 4) {
            throw new IOException("Remaining buffer too short to contain length of length-prefixed field. Remaining: " + byteBuffer.remaining());
        }
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (i > byteBuffer.remaining()) {
            throw new IOException("Length-prefixed field longer than remaining buffer. Field length: " + i + ", remaining: " + byteBuffer.remaining());
        }
        return getByteBuffer(byteBuffer, i);
    }

    static byte[] readLengthPrefixedByteArray(ByteBuffer byteBuffer) throws IOException {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IOException("Negative length");
        }
        if (i > byteBuffer.remaining()) {
            throw new IOException("Underflow while reading length-prefixed value. Length: " + i + ", available: " + byteBuffer.remaining());
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }

    static void setUnsignedInt32LittleEndian(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((i >>> 8) & 255);
        bArr[i2 + 2] = (byte) ((i >>> 16) & 255);
        bArr[i2 + 3] = (byte) ((i >>> 24) & 255);
    }

    static Pair<ByteBuffer, Long> findApkSigningBlock(RandomAccessFile randomAccessFile, long j) throws SignatureNotFoundException, IOException {
        if (j < 32) {
            throw new SignatureNotFoundException("APK too small for APK Signing Block. ZIP Central Directory offset: " + j);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(24);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        randomAccessFile.seek(j - byteBufferAllocate.capacity());
        randomAccessFile.readFully(byteBufferAllocate.array(), byteBufferAllocate.arrayOffset(), byteBufferAllocate.capacity());
        if (byteBufferAllocate.getLong(8) != APK_SIG_BLOCK_MAGIC_LO || byteBufferAllocate.getLong(16) != APK_SIG_BLOCK_MAGIC_HI) {
            throw new SignatureNotFoundException("No APK Signing Block before ZIP Central Directory");
        }
        long j2 = byteBufferAllocate.getLong(0);
        if (j2 < byteBufferAllocate.capacity() || j2 > 2147483639) {
            throw new SignatureNotFoundException("APK Signing Block size out of range: " + j2);
        }
        int i = (int) (8 + j2);
        long j3 = j - i;
        if (j3 < 0) {
            throw new SignatureNotFoundException("APK Signing Block offset out of range: " + j3);
        }
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(i);
        byteBufferAllocate2.order(ByteOrder.LITTLE_ENDIAN);
        randomAccessFile.seek(j3);
        randomAccessFile.readFully(byteBufferAllocate2.array(), byteBufferAllocate2.arrayOffset(), byteBufferAllocate2.capacity());
        long j4 = byteBufferAllocate2.getLong(0);
        if (j4 != j2) {
            throw new SignatureNotFoundException("APK Signing Block sizes in header and footer do not match: " + j4 + " vs " + j2);
        }
        return Pair.create(byteBufferAllocate2, Long.valueOf(j3));
    }

    static ByteBuffer findApkSignatureSchemeBlock(ByteBuffer byteBuffer, int i) throws SignatureNotFoundException {
        checkByteOrderLittleEndian(byteBuffer);
        ByteBuffer byteBufferSliceFromTo = sliceFromTo(byteBuffer, 8, byteBuffer.capacity() - 24);
        int i2 = 0;
        while (byteBufferSliceFromTo.hasRemaining()) {
            i2++;
            if (byteBufferSliceFromTo.remaining() < 8) {
                throw new SignatureNotFoundException("Insufficient data to read size of APK Signing Block entry #" + i2);
            }
            long j = byteBufferSliceFromTo.getLong();
            if (j < 4 || j > 2147483647L) {
                throw new SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + j);
            }
            int i3 = (int) j;
            int iPosition = byteBufferSliceFromTo.position() + i3;
            if (i3 > byteBufferSliceFromTo.remaining()) {
                throw new SignatureNotFoundException("APK Signing Block entry #" + i2 + " size out of range: " + i3 + ", available: " + byteBufferSliceFromTo.remaining());
            }
            if (byteBufferSliceFromTo.getInt() == i) {
                return getByteBuffer(byteBufferSliceFromTo, i3 - 4);
            }
            byteBufferSliceFromTo.position(iPosition);
        }
        throw new SignatureNotFoundException("No block with ID " + i + " in APK Signing Block.");
    }

    private static void checkByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    private static class MultipleDigestDataDigester implements DataDigester {
        private final MessageDigest[] mMds;

        MultipleDigestDataDigester(MessageDigest[] messageDigestArr) {
            this.mMds = messageDigestArr;
        }

        @Override // android.util.apk.DataDigester
        public void consume(ByteBuffer byteBuffer) {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            for (MessageDigest messageDigest : this.mMds) {
                byteBufferSlice.position(0);
                messageDigest.update(byteBufferSlice);
            }
        }
    }

    static VerifiedProofOfRotation verifyProofOfRotationStruct(ByteBuffer byteBuffer, CertificateFactory certificateFactory) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException, SecurityException, InvalidAlgorithmParameterException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        try {
            byteBuffer.getInt();
            HashSet hashSet = new HashSet();
            int i2 = -1;
            VerbatimX509Certificate verbatimX509Certificate = null;
            while (byteBuffer.hasRemaining()) {
                i++;
                ByteBuffer lengthPrefixedSlice = getLengthPrefixedSlice(byteBuffer);
                ByteBuffer lengthPrefixedSlice2 = getLengthPrefixedSlice(lengthPrefixedSlice);
                int i3 = lengthPrefixedSlice.getInt();
                int i4 = lengthPrefixedSlice.getInt();
                byte[] lengthPrefixedByteArray = readLengthPrefixedByteArray(lengthPrefixedSlice);
                if (verbatimX509Certificate != null) {
                    Pair<String, ? extends AlgorithmParameterSpec> signatureAlgorithmJcaSignatureAlgorithm = getSignatureAlgorithmJcaSignatureAlgorithm(i2);
                    PublicKey publicKey = verbatimX509Certificate.getPublicKey();
                    Signature signature = Signature.getInstance(signatureAlgorithmJcaSignatureAlgorithm.first);
                    signature.initVerify(publicKey);
                    if (signatureAlgorithmJcaSignatureAlgorithm.second != 0) {
                        signature.setParameter((AlgorithmParameterSpec) signatureAlgorithmJcaSignatureAlgorithm.second);
                    }
                    signature.update(lengthPrefixedSlice2);
                    if (!signature.verify(lengthPrefixedByteArray)) {
                        throw new SecurityException("Unable to verify signature of certificate #" + i + " using " + signatureAlgorithmJcaSignatureAlgorithm.first + " when verifying Proof-of-rotation record");
                    }
                }
                lengthPrefixedSlice2.rewind();
                byte[] lengthPrefixedByteArray2 = readLengthPrefixedByteArray(lengthPrefixedSlice2);
                int i5 = lengthPrefixedSlice2.getInt();
                if (verbatimX509Certificate != null && i2 != i5) {
                    throw new SecurityException("Signing algorithm ID mismatch for certificate #" + i + " when verifying Proof-of-rotation record");
                }
                verbatimX509Certificate = new VerbatimX509Certificate((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(lengthPrefixedByteArray2)), lengthPrefixedByteArray2);
                if (hashSet.contains(verbatimX509Certificate)) {
                    throw new SecurityException("Encountered duplicate entries in Proof-of-rotation record at certificate #" + i + ".  All signing certificates should be unique");
                }
                hashSet.add(verbatimX509Certificate);
                arrayList.add(verbatimX509Certificate);
                arrayList2.add(Integer.valueOf(i3));
                i2 = i4;
            }
            return new VerifiedProofOfRotation(arrayList, arrayList2);
        } catch (IOException | BufferUnderflowException e) {
            throw new IOException("Failed to parse Proof-of-rotation record", e);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
            throw new SecurityException("Failed to verify signature over signed data for certificate #0 when verifying Proof-of-rotation record", e2);
        } catch (CertificateException e3) {
            throw new SecurityException("Failed to decode certificate #0 when verifying Proof-of-rotation record", e3);
        }
    }

    public static class VerifiedProofOfRotation {
        public final List<X509Certificate> certs;
        public final List<Integer> flagsList;

        public VerifiedProofOfRotation(List<X509Certificate> list, List<Integer> list2) {
            this.certs = list;
            this.flagsList = list2;
        }
    }
}
