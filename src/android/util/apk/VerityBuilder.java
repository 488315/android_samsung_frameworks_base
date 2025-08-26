package android.util.apk;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract class VerityBuilder {
    private static final int CHUNK_SIZE_BYTES = 4096;
    private static final byte[] DEFAULT_SALT = new byte[8];
    private static final int DIGEST_SIZE_BYTES = 32;
    private static final int FSVERITY_HEADER_SIZE_BYTES = 64;
    private static final String JCA_DIGEST_ALGORITHM = "SHA-256";
    private static final int MMAP_REGION_SIZE_BYTES = 1048576;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_SIZE = 4;

    private VerityBuilder() {
    }

    public static class VerityResult {
        public final int merkleTreeSize;
        public final byte[] rootHash;
        public final ByteBuffer verityData;

        private VerityResult(ByteBuffer byteBuffer, int i, byte[] bArr) {
            this.verityData = byteBuffer;
            this.merkleTreeSize = i;
            this.rootHash = bArr;
        }
    }

    public static VerityResult generateApkVerityTree(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, ByteBufferFactory byteBufferFactory) throws NoSuchAlgorithmException, DigestException, IOException, SecurityException {
        return generateVerityTreeInternal(randomAccessFile, byteBufferFactory, signatureInfo);
    }

    private static VerityResult generateVerityTreeInternal(RandomAccessFile randomAccessFile, ByteBufferFactory byteBufferFactory, SignatureInfo signatureInfo) throws NoSuchAlgorithmException, DigestException, IOException, SecurityException {
        int[] iArrCalculateVerityLevelOffset = calculateVerityLevelOffset(randomAccessFile.getChannel().size() - (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset));
        int i = iArrCalculateVerityLevelOffset[iArrCalculateVerityLevelOffset.length - 1];
        ByteBuffer byteBufferCreate = byteBufferFactory.create(i + 4096);
        byteBufferCreate.order(ByteOrder.LITTLE_ENDIAN);
        return new VerityResult(byteBufferCreate, i, generateVerityTreeInternal(randomAccessFile, signatureInfo, DEFAULT_SALT, iArrCalculateVerityLevelOffset, slice(byteBufferCreate, 0, i)));
    }

    static void generateApkVerityFooter(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, ByteBuffer byteBuffer) throws IOException {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        generateApkVerityHeader(byteBuffer, randomAccessFile.getChannel().size(), DEFAULT_SALT);
        generateApkVerityExtensions(byteBuffer, signatureInfo.apkSigningBlockOffset, signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset, signatureInfo.eocdOffset);
    }

    public static byte[] generateFsVerityRootHash(String str, byte[] bArr, ByteBufferFactory byteBufferFactory) throws NoSuchAlgorithmException, DigestException, IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            int[] iArrCalculateVerityLevelOffset = calculateVerityLevelOffset(randomAccessFile.length());
            int i = iArrCalculateVerityLevelOffset[iArrCalculateVerityLevelOffset.length - 1];
            ByteBuffer byteBufferCreate = byteBufferFactory.create(i + 4096);
            byteBufferCreate.order(ByteOrder.LITTLE_ENDIAN);
            byte[] bArrGenerateFsVerityTreeInternal = generateFsVerityTreeInternal(randomAccessFile, bArr, iArrCalculateVerityLevelOffset, slice(byteBufferCreate, 0, i));
            randomAccessFile.close();
            return bArrGenerateFsVerityTreeInternal;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static byte[] generateApkVerity(String str, ByteBufferFactory byteBufferFactory, SignatureInfo signatureInfo) throws SignatureNotFoundException, NoSuchAlgorithmException, DigestException, IOException, SecurityException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            VerityResult verityResultGenerateVerityTreeInternal = generateVerityTreeInternal(randomAccessFile, byteBufferFactory, signatureInfo);
            ByteBuffer byteBufferSlice = slice(verityResultGenerateVerityTreeInternal.verityData, verityResultGenerateVerityTreeInternal.merkleTreeSize, verityResultGenerateVerityTreeInternal.verityData.limit());
            generateApkVerityFooter(randomAccessFile, signatureInfo, byteBufferSlice);
            byteBufferSlice.putInt(byteBufferSlice.position() + 4);
            verityResultGenerateVerityTreeInternal.verityData.limit(verityResultGenerateVerityTreeInternal.merkleTreeSize + byteBufferSlice.position());
            byte[] bArr = verityResultGenerateVerityTreeInternal.rootHash;
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

    private static class BufferedDigester implements DataDigester {
        private static final int BUFFER_SIZE = 4096;
        private int mBytesDigestedSinceReset;
        private final byte[] mDigestBuffer;
        private final MessageDigest mMd;
        private final ByteBuffer mOutput;
        private final byte[] mSalt;

        private BufferedDigester(byte[] bArr, ByteBuffer byteBuffer) throws NoSuchAlgorithmException {
            this.mDigestBuffer = new byte[32];
            this.mSalt = bArr;
            this.mOutput = byteBuffer.slice();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            this.mMd = messageDigest;
            if (bArr != null) {
                messageDigest.update(bArr);
            }
            this.mBytesDigestedSinceReset = 0;
        }

        @Override // android.util.apk.DataDigester
        public void consume(ByteBuffer byteBuffer) throws DigestException {
            byteBuffer.position();
            int iRemaining = byteBuffer.remaining();
            while (iRemaining > 0) {
                int iMin = Math.min(iRemaining, 4096 - this.mBytesDigestedSinceReset);
                byteBuffer.limit(byteBuffer.position() + iMin);
                this.mMd.update(byteBuffer);
                iRemaining -= iMin;
                int i = this.mBytesDigestedSinceReset + iMin;
                this.mBytesDigestedSinceReset = i;
                if (i == 4096) {
                    MessageDigest messageDigest = this.mMd;
                    byte[] bArr = this.mDigestBuffer;
                    messageDigest.digest(bArr, 0, bArr.length);
                    this.mOutput.put(this.mDigestBuffer);
                    byte[] bArr2 = this.mSalt;
                    if (bArr2 != null) {
                        this.mMd.update(bArr2);
                    }
                    this.mBytesDigestedSinceReset = 0;
                }
            }
        }

        public void assertEmptyBuffer() throws DigestException {
            if (this.mBytesDigestedSinceReset == 0) {
                return;
            }
            throw new IllegalStateException("Buffer is not empty: " + this.mBytesDigestedSinceReset);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void fillUpLastOutputChunk() {
            int iPosition = this.mOutput.position() % 4096;
            if (iPosition == 0) {
                return;
            }
            this.mOutput.put(ByteBuffer.allocate(4096 - iPosition));
        }
    }

    private static void consumeByChunk(DataDigester dataDigester, DataSource dataSource, int i) throws DigestException, IOException {
        long size = dataSource.size();
        long j = 0;
        while (size > 0) {
            int iMin = (int) Math.min(size, i);
            dataSource.feedIntoDataDigester(dataDigester, j, iMin);
            long j2 = iMin;
            j += j2;
            size -= j2;
        }
    }

    private static void generateFsVerityDigestAtLeafLevel(RandomAccessFile randomAccessFile, byte[] bArr, ByteBuffer byteBuffer) throws NoSuchAlgorithmException, DigestException, IOException {
        BufferedDigester bufferedDigester = new BufferedDigester(bArr, byteBuffer);
        consumeByChunk(bufferedDigester, DataSource.create(randomAccessFile.getFD(), 0L, randomAccessFile.length()), 1048576);
        int length = (int) (randomAccessFile.length() % 4096);
        if (length != 0) {
            bufferedDigester.consume(ByteBuffer.allocate(4096 - length));
        }
        bufferedDigester.assertEmptyBuffer();
        bufferedDigester.fillUpLastOutputChunk();
    }

    private static void generateApkVerityDigestAtLeafLevel(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, byte[] bArr, ByteBuffer byteBuffer) throws NoSuchAlgorithmException, DigestException, IOException {
        BufferedDigester bufferedDigester = new BufferedDigester(bArr, byteBuffer);
        consumeByChunk(bufferedDigester, DataSource.create(randomAccessFile.getFD(), 0L, signatureInfo.apkSigningBlockOffset), 1048576);
        long j = signatureInfo.eocdOffset;
        consumeByChunk(bufferedDigester, DataSource.create(randomAccessFile.getFD(), signatureInfo.centralDirOffset, (16 + j) - signatureInfo.centralDirOffset), 1048576);
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putInt(Math.toIntExact(signatureInfo.apkSigningBlockOffset));
        byteBufferOrder.flip();
        bufferedDigester.consume(byteBufferOrder);
        long j2 = j + 20;
        consumeByChunk(bufferedDigester, DataSource.create(randomAccessFile.getFD(), j2, randomAccessFile.getChannel().size() - j2), 1048576);
        int size = (int) (randomAccessFile.getChannel().size() % 4096);
        if (size != 0) {
            bufferedDigester.consume(ByteBuffer.allocate(4096 - size));
        }
        bufferedDigester.assertEmptyBuffer();
        bufferedDigester.fillUpLastOutputChunk();
    }

    private static byte[] generateFsVerityTreeInternal(RandomAccessFile randomAccessFile, byte[] bArr, int[] iArr, ByteBuffer byteBuffer) throws NoSuchAlgorithmException, DigestException, IOException {
        generateFsVerityDigestAtLeafLevel(randomAccessFile, bArr, slice(byteBuffer, iArr[iArr.length - 2], iArr[iArr.length - 1]));
        int length = iArr.length - 3;
        while (true) {
            if (length >= 0) {
                int i = length + 1;
                ByteBuffer byteBufferSlice = slice(byteBuffer, iArr[i], iArr[length + 2]);
                ByteBuffer byteBufferSlice2 = slice(byteBuffer, iArr[length], iArr[i]);
                ByteBufferDataSource byteBufferDataSource = new ByteBufferDataSource(byteBufferSlice);
                BufferedDigester bufferedDigester = new BufferedDigester(bArr, byteBufferSlice2);
                consumeByChunk(bufferedDigester, byteBufferDataSource, 4096);
                bufferedDigester.assertEmptyBuffer();
                bufferedDigester.fillUpLastOutputChunk();
                length--;
            } else {
                byte[] bArr2 = new byte[32];
                BufferedDigester bufferedDigester2 = new BufferedDigester(bArr, ByteBuffer.wrap(bArr2));
                bufferedDigester2.consume(slice(byteBuffer, 0, 4096));
                bufferedDigester2.assertEmptyBuffer();
                return bArr2;
            }
        }
    }

    private static byte[] generateVerityTreeInternal(RandomAccessFile randomAccessFile, SignatureInfo signatureInfo, byte[] bArr, int[] iArr, ByteBuffer byteBuffer) throws NoSuchAlgorithmException, DigestException, IOException {
        assertSigningBlockAlignedAndHasFullPages(signatureInfo);
        generateApkVerityDigestAtLeafLevel(randomAccessFile, signatureInfo, bArr, slice(byteBuffer, iArr[iArr.length - 2], iArr[iArr.length - 1]));
        int length = iArr.length - 3;
        while (true) {
            if (length >= 0) {
                int i = length + 1;
                ByteBuffer byteBufferSlice = slice(byteBuffer, iArr[i], iArr[length + 2]);
                ByteBuffer byteBufferSlice2 = slice(byteBuffer, iArr[length], iArr[i]);
                ByteBufferDataSource byteBufferDataSource = new ByteBufferDataSource(byteBufferSlice);
                BufferedDigester bufferedDigester = new BufferedDigester(bArr, byteBufferSlice2);
                consumeByChunk(bufferedDigester, byteBufferDataSource, 4096);
                bufferedDigester.assertEmptyBuffer();
                bufferedDigester.fillUpLastOutputChunk();
                length--;
            } else {
                byte[] bArr2 = new byte[32];
                BufferedDigester bufferedDigester2 = new BufferedDigester(bArr, ByteBuffer.wrap(bArr2));
                bufferedDigester2.consume(slice(byteBuffer, 0, 4096));
                bufferedDigester2.assertEmptyBuffer();
                return bArr2;
            }
        }
    }

    private static ByteBuffer generateApkVerityHeader(ByteBuffer byteBuffer, long j, byte[] bArr) {
        if (bArr.length != 8) {
            throw new IllegalArgumentException("salt is not 8 bytes long");
        }
        byteBuffer.put("TrueBrew".getBytes());
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 12);
        byteBuffer.put((byte) 7);
        byteBuffer.putShort((short) 1);
        byteBuffer.putShort((short) 1);
        byteBuffer.putInt(0);
        byteBuffer.putInt(0);
        byteBuffer.putLong(j);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 0);
        byteBuffer.put(bArr);
        skip(byteBuffer, 22);
        return byteBuffer;
    }

    private static ByteBuffer generateApkVerityExtensions(ByteBuffer byteBuffer, long j, long j2, long j3) {
        byteBuffer.putInt(24);
        byteBuffer.putShort((short) 1);
        skip(byteBuffer, 2);
        byteBuffer.putLong(j);
        byteBuffer.putLong(j2);
        byteBuffer.putInt(20);
        byteBuffer.putShort((short) 2);
        skip(byteBuffer, 2);
        byteBuffer.putLong(j3 + 16);
        byteBuffer.putInt(Math.toIntExact(j));
        skip(byteBuffer, 4);
        return byteBuffer;
    }

    private static int[] calculateVerityLevelOffset(long j) {
        ArrayList arrayList = new ArrayList();
        do {
            j = divideRoundup(j, 4096L) * 32;
            arrayList.add(Long.valueOf(divideRoundup(j, 4096L) * 4096));
        } while (j > 4096);
        int[] iArr = new int[arrayList.size() + 1];
        int i = 0;
        iArr[0] = 0;
        while (i < arrayList.size()) {
            int i2 = i + 1;
            iArr[i2] = iArr[i] + Math.toIntExact(((Long) arrayList.get((arrayList.size() - i) - 1)).longValue());
            i = i2;
        }
        return iArr;
    }

    private static void assertSigningBlockAlignedAndHasFullPages(SignatureInfo signatureInfo) {
        if (signatureInfo.apkSigningBlockOffset % 4096 != 0) {
            throw new IllegalArgumentException("APK Signing Block does not start at the page boundary: " + signatureInfo.apkSigningBlockOffset);
        }
        if ((signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset) % 4096 == 0) {
            return;
        }
        throw new IllegalArgumentException("Size of APK Signing Block is not a multiple of 4096: " + (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset));
    }

    private static ByteBuffer slice(ByteBuffer byteBuffer, int i, int i2) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.position(0);
        byteBufferDuplicate.limit(i2);
        byteBufferDuplicate.position(i);
        return byteBufferDuplicate.slice();
    }

    private static void skip(ByteBuffer byteBuffer, int i) {
        byteBuffer.position(byteBuffer.position() + i);
    }

    private static long divideRoundup(long j, long j2) {
        return ((j + j2) - 1) / j2;
    }
}
