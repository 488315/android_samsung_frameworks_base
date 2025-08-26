package com.samsung.android.motionphoto.utils;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.media.SemExtendedFormat;
import com.samsung.android.motionphoto.utils.HEIFParser;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class MotionPhotoVideoUtils {
    private static final int JPEG_LENGTH_SIZE = 2;
    private static final int JPEG_MARKER_SIZE = 2;
    private static final String MOTION_PHOTO_V2_SIGNATURE = "mpv2";
    private static final int MOTION_PHOTO_V2_SIGNATURE_SIZE = 4;
    public static final int SEF_DATA_MOTION_PHOTO = 2608;
    private static final String TAG = "MotionPhotoVideoUtils";
    private static final int XMP_RESERVED_SIZE = 1280;
    private static boolean isJpeg;
    private static long xmpPosition;

    public MotionPhotoVideoUtils() {
        Log.i(TAG, TAG);
        isJpeg = false;
        xmpPosition = 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0085 A[Catch: Exception -> 0x009c, TRY_ENTER, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:10:0x0069, B:25:0x0085, B:27:0x008a, B:32:0x0093, B:34:0x0098, B:35:0x009b), top: B:39:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:10:0x0069, B:25:0x0085, B:27:0x008a, B:32:0x0093, B:34:0x0098, B:35:0x009b), top: B:39:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0093 A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:10:0x0069, B:25:0x0085, B:27:0x008a, B:32:0x0093, B:34:0x0098, B:35:0x009b), top: B:39:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0098 A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:10:0x0069, B:25:0x0085, B:27:0x008a, B:32:0x0093, B:34:0x0098, B:35:0x009b), top: B:39:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean saveVideo(File srcFile, File outFile) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        IOException e;
        Log.i(TAG, "saveVideo");
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                FileInputStream fileInputStream2 = null;
                try {
                    fileInputStream = new FileInputStream(srcFile);
                    try {
                        Log.d(TAG, "size = " + fileInputStream.getChannel().size());
                        fileInputStream.getChannel().position(offset);
                        int i = (int) length;
                        byte[] bArr = new byte[i];
                        int i2 = fileInputStream.read(bArr, 0, i);
                        Log.i(TAG, "mv data size : " + i2);
                        fileOutputStream = new FileOutputStream(outFile);
                        try {
                            try {
                                fileOutputStream.write(bArr, 0, i2);
                                fileOutputStream.getChannel().truncate(i2);
                                fileInputStream.close();
                                fileOutputStream.close();
                                return true;
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                return false;
                            }
                        } catch (Throwable th) {
                            th = th;
                            fileInputStream2 = fileInputStream;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        fileOutputStream = null;
                        e = e;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                    fileOutputStream = null;
                    fileInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                    if (fileInputStream2 != null) {
                    }
                    if (fileOutputStream != null) {
                    }
                    throw th;
                }
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        return false;
    }

    public final boolean deleteVideo(File srcFile) throws IOException {
        Log.i(TAG, "deleteVideo");
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean zIsMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (zIsMotionPhotoV2) {
                    Log.i(TAG, "MotionPhotoV2");
                    RandomAccessFile randomAccessFile = new RandomAccessFile(srcFile, "rw");
                    try {
                        try {
                            long j = length + offset;
                            long length2 = srcFile.length() - j;
                            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) length2);
                            FileChannel channel = randomAccessFile.getChannel();
                            channel.read(byteBufferAllocate, j);
                            byteBufferAllocate.flip();
                            long j2 = offset - 8;
                            channel.write(byteBufferAllocate, j2);
                            channel.truncate(j2 + length2);
                            channel.close();
                        } finally {
                            randomAccessFile.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(srcFile, "rw").getFD();
                boolean zIsJpeg = isJpeg(fd);
                isJpeg = zIsJpeg;
                if (zIsMotionPhotoV2 || zIsJpeg) {
                    removeXmp(fd);
                } else {
                    Log.i(TAG, "There is no xmp");
                }
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean deleteVideo(File srcFile, File outFile) throws IOException {
        Log.i(TAG, "deleteVideo");
        try {
            Files.copy(srcFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean zIsMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (zIsMotionPhotoV2) {
                    Log.i(TAG, "MotionPhotoV2");
                    RandomAccessFile randomAccessFile = new RandomAccessFile(outFile, "rw");
                    try {
                        try {
                            long j = length + offset;
                            long length2 = outFile.length() - j;
                            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) length2);
                            FileChannel channel = randomAccessFile.getChannel();
                            channel.read(byteBufferAllocate, j);
                            byteBufferAllocate.flip();
                            long j2 = offset - 8;
                            channel.write(byteBufferAllocate, j2);
                            channel.truncate(j2 + length2);
                        } finally {
                            randomAccessFile.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(outFile, "rw").getFD();
                boolean zIsJpeg = isJpeg(fd);
                isJpeg = zIsJpeg;
                if (zIsMotionPhotoV2 || zIsJpeg) {
                    removeXmp(fd);
                } else {
                    Log.i(TAG, "There is no xmp");
                }
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private final boolean isJpeg(FileDescriptor fd) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fd);
        boolean z = false;
        try {
            try {
                try {
                    fileInputStream.getChannel().position(0L);
                    byte[] bArr = new byte[2];
                    fileInputStream.read(bArr, 0, 2);
                    if ((bArr[0] & 255) == 255) {
                        if ((bArr[1] & 255) == 216) {
                            z = true;
                        }
                    }
                    fileInputStream.close();
                    return z;
                } catch (Exception e) {
                    e.printStackTrace();
                    fileInputStream.close();
                    return false;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                return false;
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
    }

    public final long getXmpPosition() {
        return xmpPosition;
    }

    public final void setXmpPosition(long var1) {
        xmpPosition = var1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a3, code lost:
    
        return 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final long seekToXmpStartPosition(FileDescriptor fd) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fd);
        long j = 0;
        if (isJpeg) {
            Log.d(TAG, "//JPEG//");
            byte[] bArr = new byte[1024];
            try {
                fileInputStream.getChannel().position(0L);
                fileInputStream.read(bArr, 0, 2);
                while (true) {
                    if (fileInputStream.read(bArr, 0, 2) <= 0) {
                        break;
                    }
                    Pair pair = new Pair(Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255));
                    if (((Number) pair.first).intValue() != 255) {
                        Log.i(TAG, "this is not valid markers");
                        break;
                    }
                    if (208 > ((Number) pair.second).intValue() || 215 < ((Number) pair.second).intValue()) {
                        fileInputStream.read(bArr, 0, 2);
                        if (((Number) pair.second).intValue() != 221) {
                            fileInputStream.skip((((bArr[0] & 255) << 8) | (255 & bArr[1])) - 2);
                            if (((Number) pair.second).intValue() == 225) {
                                return fileInputStream.getChannel().position();
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "//HEIF//");
            HEIFParser hEIFParser = new HEIFParser();
            try {
                try {
                    fileInputStream.getChannel().position(0L);
                    HEIFParser.XMPInformation coverImageXMPOffsetAndSize = hEIFParser.getCoverImageXMPOffsetAndSize(fileInputStream);
                    if (coverImageXMPOffsetAndSize != null) {
                        Log.i(TAG, "XMP " + coverImageXMPOffsetAndSize.offset + ", " + coverImageXMPOffsetAndSize.size);
                        j = coverImageXMPOffsetAndSize.offset;
                    } else {
                        Log.i(TAG, "Fail to get xmp information");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                fileInputStream.close();
            }
        }
        return j;
    }

    private final void removeXmp(FileDescriptor fd) throws IOException {
        Log.d(TAG, "removeXmp");
        xmpPosition = seekToXmpStartPosition(fd);
        if (isJpeg) {
            FileInputStream fileInputStream = new FileInputStream(fd);
            ByteBuffer byteBufferAllocateDirect = null;
            try {
                try {
                    byte[] bArr = new byte[2];
                    fileInputStream.read(bArr);
                    if ((bArr[0] & 255) == 255 && (bArr[1] & 255) == 225) {
                        byte[] bArr2 = new byte[2];
                        fileInputStream.getChannel().read(ByteBuffer.wrap(bArr2));
                        long j = ((bArr2[1] & 255) | ((bArr2[0] & 255) << 8)) + 2;
                        byteBufferAllocateDirect = ByteBuffer.allocateDirect((int) ((fileInputStream.getChannel().size() - xmpPosition) - j));
                        fileInputStream.getChannel().position(xmpPosition + j);
                        int i = fileInputStream.getChannel().read(byteBufferAllocateDirect);
                        if (i != byteBufferAllocateDirect.capacity()) {
                            Log.i(TAG, "read bytes(" + i + ") differ from buffer size(" + byteBufferAllocateDirect.capacity() + ')');
                        }
                        byteBufferAllocateDirect.rewind();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (byteBufferAllocateDirect != null) {
                    FileChannel channel = new FileOutputStream(fd).getChannel();
                    try {
                        channel.position(xmpPosition);
                        channel.write(byteBufferAllocateDirect);
                        channel.truncate(channel.size() - 1280);
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
                return;
            } finally {
                fileInputStream.close();
            }
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(1280);
        if (byteBufferAllocate != null) {
            FileChannel channel2 = new FileOutputStream(fd).getChannel();
            try {
                channel2.position(xmpPosition);
                channel2.write(byteBufferAllocate);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public static final class VideoInfo {
        private long videoLength;
        private long videoOffset;

        public final long getVideoOffset() {
            return this.videoOffset;
        }

        public final long getVideoLength() {
            return this.videoLength;
        }

        public VideoInfo(long videoOffset, long videoLength) {
            this.videoOffset = videoOffset;
            this.videoLength = videoLength;
        }

        public String toString() {
            return "VideoInfo(videoOffset=" + this.videoOffset + ", videoLength=" + this.videoLength + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static final class MotionPhotoInfo {
        private boolean isMotionPhotoV2;
        private long length;
        private long offset;

        public final long getOffset() {
            return this.offset;
        }

        public final long getLength() {
            return this.length;
        }

        public final boolean isMotionPhotoV2() {
            return this.isMotionPhotoV2;
        }

        public MotionPhotoInfo(long offset, long length, boolean isMotionPhotoV2) {
            this.offset = offset;
            this.length = length;
            this.isMotionPhotoV2 = isMotionPhotoV2;
        }

        public String toString() {
            return "MotionPhotoInfo(offset=" + this.offset + ", length=" + this.length + ", isMotionPhotoV2=" + this.isMotionPhotoV2 + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public final VideoInfo getVideoDataPosition(File srcFile) {
        long offset;
        long length;
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                offset = sEFDataPosition.getOffset();
                length = sEFDataPosition.getLength();
            } else {
                offset = 0;
                length = 0;
            }
            return new VideoInfo(offset, length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final MotionPhotoInfo getSEFDataPosition(File file, String tag) throws IOException {
        boolean z;
        long j;
        long j2;
        boolean zEquals;
        long j3;
        long j4;
        long j5;
        byte[] bArr;
        try {
            SemExtendedFormat.DataPosition dataPosition = SemExtendedFormat.getDataPosition(file, tag);
            boolean z2 = false;
            if (dataPosition != null) {
                long j6 = dataPosition.offset;
                long j7 = dataPosition.length;
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    try {
                        fileInputStream.getChannel().position(j6);
                        bArr = new byte[4];
                        fileInputStream.read(bArr, 0, 4);
                        zEquals = Arrays.equals(bArr, MOTION_PHOTO_V2_SIGNATURE.getBytes("utf-8"));
                    } finally {
                    }
                } catch (Exception e) {
                    e = e;
                }
                if (zEquals) {
                    try {
                        fileInputStream.getChannel().position(4 + j6);
                        fileInputStream.read(bArr, 0, 4);
                        long j8 = bArr[3] & 255;
                        try {
                            j5 = ((bArr[2] << 8) & 65280) | j8;
                        } catch (Exception e2) {
                            e = e2;
                            j6 = j8;
                        }
                        try {
                            j5 = j5 | ((bArr[1] << 16) & 16711680) | (bArr[0] << 24);
                            fileInputStream.getChannel().position(j6 + 8);
                            fileInputStream.read(bArr, 0, 4);
                            long j9 = bArr[3] & 255;
                            try {
                                j9 = j9 | ((bArr[2] << 8) & 65280) | ((bArr[1] << 16) & 16711680);
                                j4 = j9 | (bArr[0] << 24);
                            } catch (Exception e3) {
                                e = e3;
                                j4 = j9;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            j6 = j5;
                            z2 = zEquals;
                            zEquals = z2;
                            j3 = 0;
                            e.printStackTrace();
                            j4 = j3;
                            j5 = j6;
                            fileInputStream.close();
                            j2 = j4;
                            z = zEquals;
                            j = j5;
                            return new MotionPhotoInfo(j, j2, z);
                        }
                    } catch (Exception e5) {
                        e = e5;
                        z2 = zEquals;
                        j6 = 0;
                        zEquals = z2;
                        j3 = 0;
                        e.printStackTrace();
                        j4 = j3;
                        j5 = j6;
                        fileInputStream.close();
                        j2 = j4;
                        z = zEquals;
                        j = j5;
                        return new MotionPhotoInfo(j, j2, z);
                    }
                    try {
                        Log.d(TAG, "This file is a MotionPhoto V2 format - offset:" + j5 + " length:" + j4);
                    } catch (Exception e6) {
                        e = e6;
                        j6 = j5;
                        j3 = j4;
                        e.printStackTrace();
                        j4 = j3;
                        j5 = j6;
                        fileInputStream.close();
                        j2 = j4;
                        z = zEquals;
                        j = j5;
                        return new MotionPhotoInfo(j, j2, z);
                    }
                    fileInputStream.close();
                    j2 = j4;
                    z = zEquals;
                    j = j5;
                } else {
                    try {
                        Log.d(TAG, "This file is not a MotionPhoto V2 format - offset:" + j6 + " length:" + j7);
                        j5 = j6;
                        j4 = j7;
                    } catch (Exception e7) {
                        e = e7;
                        j3 = j7;
                        e.printStackTrace();
                        j4 = j3;
                        j5 = j6;
                        fileInputStream.close();
                        j2 = j4;
                        z = zEquals;
                        j = j5;
                        return new MotionPhotoInfo(j, j2, z);
                    }
                    fileInputStream.close();
                    j2 = j4;
                    z = zEquals;
                    j = j5;
                }
            } else {
                z = false;
                j = 0;
                j2 = 0;
            }
            return new MotionPhotoInfo(j, j2, z);
        } catch (Exception unused) {
            Log.d(TAG, "position is not valid");
            return null;
        }
    }
}
