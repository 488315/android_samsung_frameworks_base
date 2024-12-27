package com.samsung.android.motionphoto.utils;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.media.SemExtendedFormat;

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009c A[Catch: Exception -> 0x00a5, TryCatch #5 {Exception -> 0x00a5, blocks: (B:3:0x0008, B:5:0x0010, B:14:0x0070, B:34:0x009c, B:36:0x00a1, B:37:0x00a4, B:23:0x008c, B:25:0x0091), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a1 A[Catch: Exception -> 0x00a5, TryCatch #5 {Exception -> 0x00a5, blocks: (B:3:0x0008, B:5:0x0010, B:14:0x0070, B:34:0x009c, B:36:0x00a1, B:37:0x00a4, B:23:0x008c, B:25:0x0091), top: B:2:0x0008 }] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean saveVideo(java.io.File r12, java.io.File r13) {
        /*
            r11 = this;
            java.lang.String r0 = "MotionPhotoVideoUtils"
            java.lang.String r1 = "saveVideo"
            android.util.Log.i(r0, r1)
            r1 = 0
            java.lang.String r2 = "MotionPhoto_Data"
            com.samsung.android.motionphoto.utils.MotionPhotoVideoUtils$MotionPhotoInfo r2 = r11.getSEFDataPosition(r12, r2)     // Catch: java.lang.Exception -> La5
            if (r2 == 0) goto La9
            long r3 = r2.getOffset()     // Catch: java.lang.Exception -> La5
            long r5 = r2.getLength()     // Catch: java.lang.Exception -> La5
            r2 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L82 java.io.IOException -> L85
            r7.<init>(r12)     // Catch: java.lang.Throwable -> L82 java.io.IOException -> L85
            java.nio.channels.FileChannel r12 = r7.getChannel()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            long r8 = r12.size()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r12.<init>()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.String r10 = "size = "
            java.lang.StringBuilder r12 = r12.append(r10)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.StringBuilder r12 = r12.append(r8)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            android.util.Log.d(r0, r12)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.nio.channels.FileChannel r12 = r7.getChannel()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r12.position(r3)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            int r12 = (int) r5     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            byte[] r3 = new byte[r12]     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            int r12 = r7.read(r3, r1, r12)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r4.<init>()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.String r5 = "mv data size : "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.StringBuilder r4 = r4.append(r12)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            android.util.Log.i(r0, r4)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r0.<init>(r13)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7e
            r0.write(r3, r1, r12)     // Catch: java.lang.Throwable -> L78 java.io.IOException -> L7a
            java.nio.channels.FileChannel r13 = r0.getChannel()     // Catch: java.lang.Throwable -> L78 java.io.IOException -> L7a
            long r2 = (long) r12     // Catch: java.lang.Throwable -> L78 java.io.IOException -> L7a
            r13.truncate(r2)     // Catch: java.lang.Throwable -> L78 java.io.IOException -> L7a
            r7.close()     // Catch: java.lang.Exception -> La5
            r0.close()     // Catch: java.lang.Exception -> La5
            r1 = 1
            goto La9
        L78:
            r12 = move-exception
            goto L97
        L7a:
            r12 = move-exception
            goto L80
        L7c:
            r12 = move-exception
            goto L98
        L7e:
            r12 = move-exception
            r0 = r2
        L80:
            r2 = r7
            goto L87
        L82:
            r12 = move-exception
            r13 = r2
            goto L9a
        L85:
            r12 = move-exception
            r0 = r2
        L87:
            r12.printStackTrace()     // Catch: java.lang.Throwable -> L95
            if (r2 == 0) goto L8f
            r2.close()     // Catch: java.lang.Exception -> La5
        L8f:
            if (r0 == 0) goto La9
            r0.close()     // Catch: java.lang.Exception -> La5
            goto La9
        L95:
            r12 = move-exception
            r7 = r2
        L97:
            r2 = r0
        L98:
            r13 = r2
            r2 = r7
        L9a:
            if (r2 == 0) goto L9f
            r2.close()     // Catch: java.lang.Exception -> La5
        L9f:
            if (r13 == 0) goto La4
            r13.close()     // Catch: java.lang.Exception -> La5
        La4:
            throw r12     // Catch: java.lang.Exception -> La5
        La5:
            r12 = move-exception
            r12.printStackTrace()
        La9:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.motionphoto.utils.MotionPhotoVideoUtils.saveVideo(java.io.File,"
                    + " java.io.File):boolean");
    }

    public final boolean deleteVideo(File srcFile) {
        Log.i(TAG, "deleteVideo");
        try {
            MotionPhotoInfo sEFDataPosition =
                    getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean isMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (isMotionPhotoV2) {
                    Log.i(TAG, "MotionPhotoV2");
                    RandomAccessFile randomAccessFile = new RandomAccessFile(srcFile, "rw");
                    try {
                        try {
                            long j = length + offset;
                            long length2 = srcFile.length() - j;
                            ByteBuffer allocate = ByteBuffer.allocate((int) length2);
                            FileChannel channel = randomAccessFile.getChannel();
                            channel.read(allocate, j);
                            allocate.flip();
                            long j2 = offset - 8;
                            channel.write(allocate, j2);
                            channel.truncate(j2 + length2);
                            channel.close();
                        } finally {
                            randomAccessFile.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    randomAccessFile.close();
                } else {
                    Log.i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(srcFile, "rw").getFD();
                boolean isJpeg2 = isJpeg(fd);
                isJpeg = isJpeg2;
                if (!isMotionPhotoV2 && !isJpeg2) {
                    Log.i(TAG, "There is no xmp");
                }
                removeXmp(fd);
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean deleteVideo(File srcFile, File outFile) {
        Log.i(TAG, "deleteVideo");
        try {
            Files.copy(srcFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            MotionPhotoInfo sEFDataPosition =
                    getSEFDataPosition(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean isMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (isMotionPhotoV2) {
                    Log.i(TAG, "MotionPhotoV2");
                    RandomAccessFile randomAccessFile = new RandomAccessFile(outFile, "rw");
                    try {
                        try {
                            long j = length + offset;
                            long length2 = outFile.length() - j;
                            ByteBuffer allocate = ByteBuffer.allocate((int) length2);
                            FileChannel channel = randomAccessFile.getChannel();
                            channel.read(allocate, j);
                            allocate.flip();
                            long j2 = offset - 8;
                            channel.write(allocate, j2);
                            channel.truncate(j2 + length2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        randomAccessFile.close();
                    } finally {
                        randomAccessFile.close();
                    }
                } else {
                    Log.i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(outFile, "rw").getFD();
                boolean isJpeg2 = isJpeg(fd);
                isJpeg = isJpeg2;
                if (!isMotionPhotoV2 && !isJpeg2) {
                    Log.i(TAG, "There is no xmp");
                }
                removeXmp(fd);
            }
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private final boolean isJpeg(FileDescriptor fd) {
        FileInputStream fileInputStream = new FileInputStream(fd);
        boolean z = false;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final long getXmpPosition() {
        return xmpPosition;
    }

    public final void setXmpPosition(long var1) {
        xmpPosition = var1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final long seekToXmpStartPosition(FileDescriptor fd) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fd);
        long j = 0;
        if (isJpeg) {
            Log.d(TAG, "//JPEG//");
            byte[] bArr = new byte[1024];
            try {
                fileInputStream.getChannel().position(0L);
                fileInputStream.read(bArr, 0, 2);
                while (fileInputStream.read(bArr, 0, 2) > 0) {
                    Pair pair =
                            new Pair(
                                    Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255));
                    if (!(((Number) pair.first).intValue() == 255)) {
                        Log.i(TAG, "this is not valid markers");
                        return 0L;
                    }
                    if (208 > ((Number) pair.second).intValue()
                            || 215 < ((Number) pair.second).intValue()) {
                        fileInputStream.read(bArr, 0, 2);
                        if (((Number) pair.second).intValue() != 221) {
                            fileInputStream.skip((((bArr[0] & 255) << 8) | (255 & bArr[1])) - 2);
                            if (((Number) pair.second).intValue() == 225) {
                                long position = fileInputStream.getChannel().position();
                                try {
                                    return position;
                                } catch (Exception e) {
                                    e = e;
                                    j = position;
                                    e.printStackTrace();
                                    return j;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
        } else {
            Log.i(TAG, "//HEIF//");
            HEIFParser hEIFParser = new HEIFParser();
            try {
                try {
                    fileInputStream.getChannel().position(0L);
                    HEIFParser.XMPInformation coverImageXMPOffsetAndSize =
                            hEIFParser.getCoverImageXMPOffsetAndSize(fileInputStream);
                    if (coverImageXMPOffsetAndSize != null) {
                        Log.i(
                                TAG,
                                "XMP "
                                        + coverImageXMPOffsetAndSize.offset
                                        + ", "
                                        + coverImageXMPOffsetAndSize.size);
                        j = coverImageXMPOffsetAndSize.offset;
                    } else {
                        Log.i(TAG, "Fail to get xmp information");
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
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
            ByteBuffer byteBuffer = null;
            try {
                try {
                    byte[] bArr = new byte[2];
                    fileInputStream.read(bArr);
                    if ((bArr[0] & 255) == 255 && (bArr[1] & 255) == 225) {
                        byte[] bArr2 = new byte[2];
                        fileInputStream.getChannel().read(ByteBuffer.wrap(bArr2));
                        long j = ((bArr2[1] & 255) | ((bArr2[0] & 255) << 8)) + 2;
                        byteBuffer =
                                ByteBuffer.allocateDirect(
                                        (int)
                                                ((fileInputStream.getChannel().size() - xmpPosition)
                                                        - j));
                        fileInputStream.getChannel().position(xmpPosition + j);
                        int read = fileInputStream.getChannel().read(byteBuffer);
                        if (!(read == byteBuffer.capacity())) {
                            Log.i(
                                    TAG,
                                    "read bytes("
                                            + read
                                            + ") differ from buffer size("
                                            + byteBuffer.capacity()
                                            + ')');
                        }
                        byteBuffer.rewind();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (byteBuffer != null) {
                    FileChannel channel = new FileOutputStream(fd).getChannel();
                    try {
                        channel.position(xmpPosition);
                        channel.write(byteBuffer);
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
        ByteBuffer allocate = ByteBuffer.allocate(1280);
        if (allocate != null) {
            FileChannel channel2 = new FileOutputStream(fd).getChannel();
            try {
                channel2.position(xmpPosition);
                channel2.write(allocate);
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
            return "VideoInfo(videoOffset="
                    + this.videoOffset
                    + ", videoLength="
                    + this.videoLength
                    + NavigationBarInflaterView.KEY_CODE_END;
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
            return "MotionPhotoInfo(offset="
                    + this.offset
                    + ", length="
                    + this.length
                    + ", isMotionPhotoV2="
                    + this.isMotionPhotoV2
                    + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public final VideoInfo getVideoDataPosition(File srcFile) {
        long j;
        long j2;
        try {
            MotionPhotoInfo sEFDataPosition =
                    getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition == null) {
                j = 0;
                j2 = 0;
            } else {
                j = sEFDataPosition.getOffset();
                j2 = sEFDataPosition.getLength();
            }
            return new VideoInfo(j, j2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final MotionPhotoInfo getSEFDataPosition(File file, String tag) {
        boolean z;
        long j;
        long j2;
        boolean z2;
        long j3;
        long j4;
        long j5;
        try {
            SemExtendedFormat.DataPosition dataPosition =
                    SemExtendedFormat.getDataPosition(file, tag);
            boolean z3 = false;
            if (dataPosition != null) {
                long j6 = dataPosition.offset;
                long j7 = dataPosition.length;
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    try {
                        fileInputStream.getChannel().position(j6);
                        byte[] bArr = new byte[4];
                        fileInputStream.read(bArr, 0, 4);
                        z2 = Arrays.equals(bArr, MOTION_PHOTO_V2_SIGNATURE.getBytes("utf-8"));
                        if (z2) {
                            try {
                                long j8 = j6 + 4;
                                fileInputStream.getChannel().position(j8);
                                fileInputStream.read(bArr, 0, 4);
                                j4 = bArr[3] & 255;
                                try {
                                    j4 =
                                            j4
                                                    | ((bArr[2] << 8) & 65280)
                                                    | ((bArr[1] << 16) & 16711680)
                                                    | (bArr[0] << 24);
                                    fileInputStream.getChannel().position(j8 + 4);
                                    fileInputStream.read(bArr, 0, 4);
                                    long j9 = bArr[3] & 255;
                                    try {
                                        j9 =
                                                j9
                                                        | ((bArr[2] << 8) & 65280)
                                                        | ((bArr[1] << 16) & 16711680);
                                        j5 = j9 | (bArr[0] << 24);
                                        try {
                                            Log.d(
                                                    TAG,
                                                    "This file is a MotionPhoto V2 format - offset:"
                                                            + j4
                                                            + " length:"
                                                            + j5);
                                        } catch (Exception e) {
                                            e = e;
                                            j3 = j5;
                                            j6 = j4;
                                            e.printStackTrace();
                                            long j10 = j3;
                                            j4 = j6;
                                            j5 = j10;
                                            fileInputStream.close();
                                            j2 = j5;
                                            z = z2;
                                            j = j4;
                                            return new MotionPhotoInfo(j, j2, z);
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        j5 = j9;
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    j6 = j4;
                                    z3 = z2;
                                    z2 = z3;
                                    j3 = 0;
                                    e.printStackTrace();
                                    long j102 = j3;
                                    j4 = j6;
                                    j5 = j102;
                                    fileInputStream.close();
                                    j2 = j5;
                                    z = z2;
                                    j = j4;
                                    return new MotionPhotoInfo(j, j2, z);
                                }
                            } catch (Exception e4) {
                                e = e4;
                                z3 = z2;
                                j6 = 0;
                                z2 = z3;
                                j3 = 0;
                                e.printStackTrace();
                                long j1022 = j3;
                                j4 = j6;
                                j5 = j1022;
                                fileInputStream.close();
                                j2 = j5;
                                z = z2;
                                j = j4;
                                return new MotionPhotoInfo(j, j2, z);
                            }
                        } else {
                            try {
                                Log.d(
                                        TAG,
                                        "This file is not a MotionPhoto V2 format - offset:"
                                                + j6
                                                + " length:"
                                                + j7);
                                j4 = j6;
                                j5 = j7;
                            } catch (Exception e5) {
                                e = e5;
                                j3 = j7;
                                e.printStackTrace();
                                long j10222 = j3;
                                j4 = j6;
                                j5 = j10222;
                                fileInputStream.close();
                                j2 = j5;
                                z = z2;
                                j = j4;
                                return new MotionPhotoInfo(j, j2, z);
                            }
                        }
                    } catch (Throwable th) {
                        fileInputStream.close();
                        throw th;
                    }
                } catch (Exception e6) {
                    e = e6;
                }
                fileInputStream.close();
                j2 = j5;
                z = z2;
                j = j4;
            } else {
                z = false;
                j = 0;
                j2 = 0;
            }
            return new MotionPhotoInfo(j, j2, z);
        } catch (Exception e7) {
            Log.d(TAG, "position is not valid");
            return null;
        }
    }
}
