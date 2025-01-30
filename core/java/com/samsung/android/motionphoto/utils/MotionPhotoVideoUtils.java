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

/* loaded from: classes5.dex */
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
        Log.m98i(TAG, TAG);
        isJpeg = false;
        xmpPosition = 0L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009f A[Catch: Exception -> 0x00a8, TryCatch #4 {Exception -> 0x00a8, blocks: (B:3:0x0009, B:5:0x0011, B:15:0x0073, B:35:0x009f, B:37:0x00a4, B:38:0x00a7, B:24:0x008f, B:26:0x0094), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a4 A[Catch: Exception -> 0x00a8, TryCatch #4 {Exception -> 0x00a8, blocks: (B:3:0x0009, B:5:0x0011, B:15:0x0073, B:35:0x009f, B:37:0x00a4, B:38:0x00a7, B:24:0x008f, B:26:0x0094), top: B:2:0x0009 }] */
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
    */
    public final boolean saveVideo(File file, File file2) {
        ?? r13;
        ?? r0;
        FileInputStream fileInputStream;
        byte[] bArr;
        int read;
        Log.m98i(TAG, "saveVideo");
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(file, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition == null) {
                return false;
            }
            long offset = sEFDataPosition.getOffset();
            long length = sEFDataPosition.getLength();
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (IOException e) {
                e = e;
                r0 = 0;
            } catch (Throwable th) {
                th = th;
                r13 = 0;
                if (fileInputStream2 != null) {
                }
                if (r13 != 0) {
                }
                throw th;
            }
            try {
                Log.m94d(TAG, "size = " + fileInputStream.getChannel().size());
                fileInputStream.getChannel().position(offset);
                int i = (int) length;
                bArr = new byte[i];
                read = fileInputStream.read(bArr, 0, i);
                Log.m98i(TAG, "mv data size : " + read);
                r0 = new FileOutputStream(file2);
            } catch (IOException e2) {
                e = e2;
                r0 = 0;
            } catch (Throwable th2) {
                th = th2;
                r13 = fileInputStream2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                }
                if (r13 != 0) {
                }
                throw th;
            }
            try {
                r0.write(bArr, 0, read);
                r0.getChannel().truncate(read);
                fileInputStream.close();
                r0.close();
                return true;
            } catch (IOException e3) {
                e = e3;
                fileInputStream2 = fileInputStream;
                r0 = r0;
                try {
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    if (r0 == 0) {
                        return false;
                    }
                    r0.close();
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = fileInputStream2;
                    fileInputStream2 = r0;
                    r13 = fileInputStream2;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    if (r13 != 0) {
                        r13.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileInputStream2 = r0;
                r13 = fileInputStream2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                }
                if (r13 != 0) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
    }

    public final boolean deleteVideo(File srcFile) {
        Log.m98i(TAG, "deleteVideo");
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean isMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (isMotionPhotoV2) {
                    Log.m98i(TAG, "MotionPhotoV2");
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        randomAccessFile.close();
                    } finally {
                        randomAccessFile.close();
                    }
                } else {
                    Log.m98i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(srcFile, "rw").getFD();
                boolean isJpeg2 = isJpeg(fd);
                isJpeg = isJpeg2;
                if (!isMotionPhotoV2 && !isJpeg2) {
                    Log.m98i(TAG, "There is no xmp");
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
        Log.m98i(TAG, "deleteVideo");
        try {
            Files.copy(srcFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
            if (sEFDataPosition != null) {
                long offset = sEFDataPosition.getOffset();
                long length = sEFDataPosition.getLength();
                boolean isMotionPhotoV2 = sEFDataPosition.isMotionPhotoV2();
                if (isMotionPhotoV2) {
                    Log.m98i(TAG, "MotionPhotoV2");
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
                        } finally {
                            randomAccessFile.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    randomAccessFile.close();
                } else {
                    Log.m98i(TAG, "Not MotionPhotoV2");
                }
                SemExtendedFormat.deleteData(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                FileDescriptor fd = new RandomAccessFile(outFile, "rw").getFD();
                boolean isJpeg2 = isJpeg(fd);
                isJpeg = isJpeg2;
                if (!isMotionPhotoV2 && !isJpeg2) {
                    Log.m98i(TAG, "There is no xmp");
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
            Log.m94d(TAG, "//JPEG//");
            byte[] bArr = new byte[1024];
            try {
                fileInputStream.getChannel().position(0L);
                fileInputStream.read(bArr, 0, 2);
                while (fileInputStream.read(bArr, 0, 2) > 0) {
                    Pair pair = new Pair(Integer.valueOf(bArr[0] & 255), Integer.valueOf(bArr[1] & 255));
                    if (!(((Number) pair.first).intValue() == 255)) {
                        Log.m98i(TAG, "this is not valid markers");
                        return 0L;
                    }
                    if (208 > ((Number) pair.second).intValue() || 215 < ((Number) pair.second).intValue()) {
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
            Log.m98i(TAG, "//HEIF//");
            HEIFParser hEIFParser = new HEIFParser();
            try {
                try {
                    fileInputStream.getChannel().position(0L);
                    HEIFParser.XMPInformation coverImageXMPOffsetAndSize = hEIFParser.getCoverImageXMPOffsetAndSize(fileInputStream);
                    if (coverImageXMPOffsetAndSize != null) {
                        Log.m98i(TAG, "XMP " + coverImageXMPOffsetAndSize.offset + ", " + coverImageXMPOffsetAndSize.size);
                        j = coverImageXMPOffsetAndSize.offset;
                    } else {
                        Log.m98i(TAG, "Fail to get xmp information");
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
        Log.m94d(TAG, "removeXmp");
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
                        byteBuffer = ByteBuffer.allocateDirect((int) ((fileInputStream.getChannel().size() - xmpPosition) - j));
                        fileInputStream.getChannel().position(xmpPosition + j);
                        int read = fileInputStream.getChannel().read(byteBuffer);
                        if (!(read == byteBuffer.capacity())) {
                            Log.m98i(TAG, "read bytes(" + read + ") differ from buffer size(" + byteBuffer.capacity() + ')');
                        }
                        byteBuffer.rewind();
                    }
                } finally {
                    fileInputStream.close();
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
        long j;
        long j2;
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
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
            SemExtendedFormat.DataPosition dataPosition = SemExtendedFormat.getDataPosition(file, tag);
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
                                    j4 = j4 | ((bArr[2] << 8) & 65280) | ((bArr[1] << 16) & 16711680) | (bArr[0] << 24);
                                    fileInputStream.getChannel().position(j8 + 4);
                                    fileInputStream.read(bArr, 0, 4);
                                    long j9 = bArr[3] & 255;
                                    try {
                                        j9 = j9 | ((bArr[2] << 8) & 65280) | ((bArr[1] << 16) & 16711680);
                                        j5 = j9 | (bArr[0] << 24);
                                    } catch (Exception e) {
                                        e = e;
                                        j5 = j9;
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    j6 = j4;
                                    z3 = z2;
                                    z2 = z3;
                                    j3 = 0;
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
                                try {
                                    Log.m94d(TAG, "This file is a MotionPhoto V2 format - offset:" + j4 + " length:" + j5);
                                } catch (Exception e3) {
                                    e = e3;
                                    j3 = j5;
                                    j6 = j4;
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
                                Log.m94d(TAG, "This file is not a MotionPhoto V2 format - offset:" + j6 + " length:" + j7);
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
                    } catch (Exception e6) {
                        e = e6;
                    }
                    fileInputStream.close();
                    j2 = j5;
                    z = z2;
                    j = j4;
                } catch (Throwable th) {
                    fileInputStream.close();
                    throw th;
                }
            } else {
                z = false;
                j = 0;
                j2 = 0;
            }
            return new MotionPhotoInfo(j, j2, z);
        } catch (Exception e7) {
            Log.m94d(TAG, "position is not valid");
            return null;
        }
    }
}
