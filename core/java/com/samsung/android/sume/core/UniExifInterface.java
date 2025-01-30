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

/* loaded from: classes4.dex */
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

    public UniExifInterface(String filename) throws IOException {
        super(filename);
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

    public UniExifInterface(InputStream inputStream, int streamType) throws IOException {
        super(inputStream, streamType);
        this.tempFile = null;
        this.originExifBuffer = null;
    }

    private UniExifInterface(ByteBuffer exifBuffer, File tempFile) throws IOException {
        this(tempFile);
        this.tempFile = tempFile;
        this.originExifBuffer = exifBuffer;
        tempFile.deleteOnExit();
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
        byte[] prefix = {byteBuffer.get(), byteBuffer.get()};
        return Arrays.equals(prefix, JPEG_PREFIX);
    }

    private static File toJpegExifFile(ByteBuffer exifBuffer) throws IOException {
        byte[] tempJpegBuffer;
        if (isJpegPrefix(exifBuffer)) {
            exifBuffer.rewind();
            tempJpegBuffer = new byte[exifBuffer.remaining()];
            exifBuffer.get(tempJpegBuffer);
        } else {
            exifBuffer.rewind();
            byte[] exifData = new byte[exifBuffer.remaining()];
            tempJpegBuffer = new byte[exifBuffer.limit() + 8];
            exifBuffer.get(exifData);
            System.arraycopy(JPEG_PREFIX, 0, tempJpegBuffer, 0, 2);
            System.arraycopy(EXIF_PREFIX, 0, tempJpegBuffer, 2, 2);
            tempJpegBuffer[4] = (byte) (((exifData.length + 2) >>> 8) & 255);
            tempJpegBuffer[5] = (byte) ((exifData.length + 2) & 255);
            System.arraycopy(exifData, 0, tempJpegBuffer, 6, exifData.length);
            System.arraycopy(JPEG_POSTFIX, 0, tempJpegBuffer, exifData.length + 6, 2);
        }
        File newTempFile = File.createTempFile("UniExifInterface.jpg", "tmp");
        FileOutputStream outputStream = new FileOutputStream(newTempFile);
        outputStream.write(tempJpegBuffer);
        outputStream.close();
        return newTempFile;
    }

    public ByteBuffer toExifByteBuffer() {
        byte[] newJpegBuf = new byte[0];
        try {
            newJpegBuf = Files.readAllBytes(this.tempFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] newExifBuf = new byte[newJpegBuf.length - 8];
        System.arraycopy(newJpegBuf, 6, newExifBuf, 0, newExifBuf.length);
        ByteBuffer newExifByteBuffer = ByteBuffer.allocateDirect(newExifBuf.length);
        newExifByteBuffer.put(newExifBuf);
        newExifByteBuffer.rewind();
        reset();
        return newExifByteBuffer;
    }

    public static UniExifInterface emptyOf() {
        ByteBuffer exifBuffer = ByteBuffer.allocate(0);
        return m309of(exifBuffer);
    }

    /* renamed from: of */
    public static UniExifInterface m309of(ByteBuffer exifBuffer) {
        try {
            File file = toJpegExifFile(exifBuffer);
            return new UniExifInterface(exifBuffer, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: of */
    public static UniExifInterface m307of(File file) {
        ByteBuffer exifBuffer = parseExif(file);
        return (UniExifInterface) Optional.ofNullable(exifBuffer).map(new Function() { // from class: com.samsung.android.sume.core.UniExifInterface$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UniExifInterface.m309of((ByteBuffer) obj);
            }
        }).orElse(null);
    }

    /* renamed from: of */
    public static UniExifInterface m308of(String path) {
        File file = new File(path);
        return m307of(file);
    }

    private static ByteBuffer parseExif(File file) {
        ByteBuffer exifBuffer = parseJpegExif(file);
        if (exifBuffer == null) {
            return parseHeifExif(file);
        }
        return exifBuffer;
    }

    private static ByteBuffer parseHeifExif(File file) {
        byte[] exifBuf = SemBitmapFactory.getExifDataFile(file.getPath());
        return (ByteBuffer) Optional.ofNullable(exifBuf).map(new Function() { // from class: com.samsung.android.sume.core.UniExifInterface$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ByteBuffer.wrap((byte[]) obj);
            }
        }).orElse(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static ByteBuffer parseJpegExif(File file) {
        ByteBuffer byteBuffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            int i = 2;
            try {
                byte[] buffer = new byte[2];
                fis.getChannel().position(0L);
                fis.read(buffer, 0, 2);
                long j = 2;
                long offset = 0 + 2;
                while (fis.read(buffer, 0, i) > 0) {
                    offset += j;
                    boolean z = true;
                    Pair markers = new Pair(Integer.valueOf(buffer[0] & 255), Integer.valueOf(buffer[1] & 255));
                    if (((Number) markers.first).intValue() != 255) {
                        z = false;
                    }
                    boolean first = z;
                    if (!first) {
                        Log.m94d(TAG, "this is not valid markers");
                        fis.close();
                        return byteBuffer;
                    }
                    if (208 > ((Number) markers.second).intValue() || 215 < ((Number) markers.second).intValue()) {
                        if (((Number) markers.second).intValue() == 225) {
                            int byte_first = fis.read();
                            int byte_second = fis.read();
                            long offset2 = offset + j;
                            int length = (byte_first << 8) | byte_second;
                            byte[] exifTag = new byte[4];
                            int readBytes = fis.read(exifTag);
                            if (readBytes < 4) {
                                Log.m96e(TAG, "Fail to read exif Tag");
                                fis.close();
                                return byteBuffer;
                            }
                            long offset3 = offset2 + 4;
                            if (new String(exifTag, "UTF-8").equals("Exif")) {
                                byte[] exifbuffer = new byte[length - 2];
                                fis.getChannel().position(offset3 - 4);
                                fis.read(exifbuffer, 0, exifbuffer.length);
                                ByteBuffer wrap = ByteBuffer.wrap(exifbuffer);
                                fis.close();
                                return wrap;
                            }
                            Log.m94d(TAG, "Not exif " + length);
                            fis.skip(length - 6);
                            offset = offset3 + (length - 6);
                        }
                        i = 2;
                        byteBuffer = null;
                        j = 2;
                    }
                }
                fis.close();
                return null;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
