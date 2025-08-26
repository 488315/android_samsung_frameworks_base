package com.samsung.android.sume.core;

import android.media.ExifInterface;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.media.SemBitmapFactory;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes6.dex */
public class UniExifInterface extends ExifInterface {
    ByteBuffer originExifBuffer;
    File tempFile;
    private static final String TAG = Def.tagOf((Class<?>) UniExifInterface.class);
    static final byte[] JPEG_PREFIX = {-1, -40};
    static final byte[] EXIF_PREFIX = {-1, -31};
    static final byte[] JPEG_POSTFIX = {-1, -39};

    public UniExifInterface(File file) throws IOException {
        super(file);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    public UniExifInterface(String str) throws IOException {
        super(str);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    public UniExifInterface(FileDescriptor fileDescriptor) throws IOException {
        super(fileDescriptor);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    public UniExifInterface(InputStream inputStream) throws IOException {
        super(inputStream);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    public UniExifInterface(InputStream inputStream, int i) throws IOException {
        super(inputStream, i);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    private UniExifInterface(ByteBuffer byteBuffer, File file) throws IOException {
        this(file);
        this.tempFile = file;
        this.originExifBuffer = byteBuffer;
        file.deleteOnExit();
    }

    private void reset() {
        this.originExifBuffer.clear();
        if (this.tempFile.exists()) {
            this.tempFile.delete();
        }
    }

    public ByteBuffer getOriginExifBuffer() {
        return this.originExifBuffer;
    }

    public File getTempFile() {
        return this.tempFile;
    }

    private static boolean isJpegPrefix(ByteBuffer byteBuffer) {
        byteBuffer.rewind();
        if (byteBuffer.limit() < 2) {
            return false;
        }
        return Arrays.equals(new byte[]{byteBuffer.get(), byteBuffer.get()}, JPEG_PREFIX);
    }

    private static File toJpegExifFile(ByteBuffer byteBuffer) throws IOException {
        byte[] bArr;
        if (isJpegPrefix(byteBuffer)) {
            byteBuffer.rewind();
            bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
        } else {
            byteBuffer.rewind();
            int iRemaining = byteBuffer.remaining();
            byte[] bArr2 = new byte[iRemaining];
            byte[] bArr3 = new byte[byteBuffer.limit() + 8];
            byteBuffer.get(bArr2);
            System.arraycopy(JPEG_PREFIX, 0, bArr3, 0, 2);
            System.arraycopy(EXIF_PREFIX, 0, bArr3, 2, 2);
            int i = iRemaining + 2;
            bArr3[4] = (byte) ((i >>> 8) & 255);
            bArr3[5] = (byte) (i & 255);
            System.arraycopy(bArr2, 0, bArr3, 6, iRemaining);
            System.arraycopy(JPEG_POSTFIX, 0, bArr3, iRemaining + 6, 2);
            bArr = bArr3;
        }
        File fileCreateTempFile = File.createTempFile("UniExifInterface.jpg", "tmp");
        FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
        return fileCreateTempFile;
    }

    public ByteBuffer toExifByteBuffer() throws IOException {
        byte[] allBytes = new byte[0];
        try {
            allBytes = Files.readAllBytes(this.tempFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = allBytes.length - 8;
        byte[] bArr = new byte[length];
        System.arraycopy(allBytes, 6, bArr, 0, length);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(length);
        byteBufferAllocateDirect.put(bArr);
        byteBufferAllocateDirect.rewind();
        reset();
        return byteBufferAllocateDirect;
    }

    public static UniExifInterface emptyOf() {
        return of(ByteBuffer.allocate(0));
    }

    public static UniExifInterface of(ByteBuffer byteBuffer) {
        try {
            return new UniExifInterface(byteBuffer, toJpegExifFile(byteBuffer));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UniExifInterface of(File file) {
        return (UniExifInterface) Optional.ofNullable(parseExif(file)).map(new Function() { // from class: com.samsung.android.sume.core.UniExifInterface$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UniExifInterface.of((ByteBuffer) obj);
            }
        }).orElse(null);
    }

    public static UniExifInterface of(String str) {
        return of(new File(str));
    }

    private static ByteBuffer parseExif(File file) throws IOException {
        ByteBuffer jpegExif = parseJpegExif(file);
        return jpegExif == null ? parseHeifExif(file) : jpegExif;
    }

    private static ByteBuffer parseHeifExif(File file) {
        return (ByteBuffer) Optional.ofNullable(SemBitmapFactory.getExifDataFile(file.getPath())).map(new Function() { // from class: com.samsung.android.sume.core.UniExifInterface$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ByteBuffer.wrap((byte[]) obj);
            }
        }).orElse(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static ByteBuffer parseJpegExif(File file) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[2];
                fileInputStream.getChannel().position(0L);
                fileInputStream.read(bArr, 0, 2);
                long j = 2;
                while (fileInputStream.read(bArr, 0, 2) > 0) {
                    long j2 = j + 2;
                    Pair pair = new Pair(Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255));
                    if (((Number) pair.first).intValue() != 255) {
                        Log.d(TAG, "this is not valid markers");
                    } else if ((208 > ((Number) pair.second).intValue() || 215 < ((Number) pair.second).intValue()) && ((Number) pair.second).intValue() == 225) {
                        int i = (fileInputStream.read() << 8) | fileInputStream.read();
                        byte[] bArr2 = new byte[4];
                        if (fileInputStream.read(bArr2) < 4) {
                            Log.e(TAG, "Fail to read exif Tag");
                        } else {
                            long j3 = 8 + j;
                            if (new String(bArr2, "UTF-8").equals("Exif")) {
                                int i2 = i - 2;
                                byte[] bArr3 = new byte[i2];
                                fileInputStream.getChannel().position(j + 4);
                                fileInputStream.read(bArr3, 0, i2);
                                ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr3);
                                fileInputStream.close();
                                return byteBufferWrap;
                            }
                            Log.d(TAG, "Not exif " + i);
                            long j4 = (long) (i + (-6));
                            fileInputStream.skip(j4);
                            j = j3 + j4;
                        }
                    } else {
                        j = j2;
                    }
                    fileInputStream.close();
                    return null;
                }
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
