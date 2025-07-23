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
            int remaining = byteBuffer.remaining();
            byte[] bArr2 = new byte[remaining];
            byte[] bArr3 = new byte[byteBuffer.limit() + 8];
            byteBuffer.get(bArr2);
            System.arraycopy(JPEG_PREFIX, 0, bArr3, 0, 2);
            System.arraycopy(EXIF_PREFIX, 0, bArr3, 2, 2);
            int i = remaining + 2;
            bArr3[4] = (byte) ((i >>> 8) & 255);
            bArr3[5] = (byte) (i & 255);
            System.arraycopy(bArr2, 0, bArr3, 6, remaining);
            System.arraycopy(JPEG_POSTFIX, 0, bArr3, remaining + 6, 2);
            bArr = bArr3;
        }
        File createTempFile = File.createTempFile("UniExifInterface.jpg", "tmp");
        FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
        return createTempFile;
    }

    public ByteBuffer toExifByteBuffer() {
        byte[] bArr = new byte[0];
        try {
            bArr = Files.readAllBytes(this.tempFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = bArr.length - 8;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 6, bArr2, 0, length);
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(length);
        allocateDirect.put(bArr2);
        allocateDirect.rewind();
        reset();
        return allocateDirect;
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

    private static ByteBuffer parseExif(File file) {
        ByteBuffer parseJpegExif = parseJpegExif(file);
        return parseJpegExif == null ? parseHeifExif(file) : parseJpegExif;
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
    private static ByteBuffer parseJpegExif(File file) {
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
                        int read = (fileInputStream.read() << 8) | fileInputStream.read();
                        byte[] bArr2 = new byte[4];
                        if (fileInputStream.read(bArr2) < 4) {
                            Log.e(TAG, "Fail to read exif Tag");
                        } else {
                            long j3 = 8 + j;
                            if (new String(bArr2, "UTF-8").equals("Exif")) {
                                int i = read - 2;
                                byte[] bArr3 = new byte[i];
                                fileInputStream.getChannel().position(j + 4);
                                fileInputStream.read(bArr3, 0, i);
                                ByteBuffer wrap = ByteBuffer.wrap(bArr3);
                                fileInputStream.close();
                                return wrap;
                            }
                            Log.d(TAG, "Not exif " + read);
                            long j4 = (long) (read + (-6));
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
