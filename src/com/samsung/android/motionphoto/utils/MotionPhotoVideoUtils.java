package com.samsung.android.motionphoto.utils;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x0085 A[Catch: Exception -> 0x009c, TRY_ENTER, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:13:0x0069, B:20:0x0085, B:22:0x008a, B:28:0x0093, B:30:0x0098, B:31:0x009b), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008a A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:13:0x0069, B:20:0x0085, B:22:0x008a, B:28:0x0093, B:30:0x0098, B:31:0x009b), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0093 A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:13:0x0069, B:20:0x0085, B:22:0x008a, B:28:0x0093, B:30:0x0098, B:31:0x009b), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0098 A[Catch: Exception -> 0x009c, TryCatch #1 {Exception -> 0x009c, blocks: (B:3:0x000f, B:5:0x0017, B:13:0x0069, B:20:0x0085, B:22:0x008a, B:28:0x0093, B:30:0x0098, B:31:0x009b), top: B:2:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean saveVideo(java.io.File r12, java.io.File r13) {
        /*
            r11 = this;
            java.lang.String r0 = "mv data size : "
            java.lang.String r1 = "size = "
            java.lang.String r2 = "saveVideo"
            java.lang.String r3 = "MotionPhotoVideoUtils"
            android.util.Log.i(r3, r2)
            r2 = 0
            java.lang.String r4 = "MotionPhoto_Data"
            com.samsung.android.motionphoto.utils.MotionPhotoVideoUtils$MotionPhotoInfo r11 = r11.getSEFDataPosition(r12, r4)     // Catch: java.lang.Exception -> L9c
            if (r11 == 0) goto La0
            long r4 = r11.getOffset()     // Catch: java.lang.Exception -> L9c
            long r6 = r11.getLength()     // Catch: java.lang.Exception -> L9c
            r11 = 0
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7c
            r8.<init>(r12)     // Catch: java.lang.Throwable -> L79 java.io.IOException -> L7c
            java.nio.channels.FileChannel r12 = r8.getChannel()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            long r9 = r12.size()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r12.<init>(r1)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r12.append(r9)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            android.util.Log.d(r3, r12)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.nio.channels.FileChannel r12 = r8.getChannel()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r12.position(r4)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            int r12 = (int) r6     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            byte[] r1 = new byte[r12]     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            int r12 = r8.read(r1, r2, r12)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r4.append(r12)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            android.util.Log.i(r3, r0)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r0.<init>(r13)     // Catch: java.lang.Throwable -> L73 java.io.IOException -> L76
            r0.write(r1, r2, r12)     // Catch: java.io.IOException -> L71 java.lang.Throwable -> L8e
            java.nio.channels.FileChannel r11 = r0.getChannel()     // Catch: java.io.IOException -> L71 java.lang.Throwable -> L8e
            long r12 = (long) r12     // Catch: java.io.IOException -> L71 java.lang.Throwable -> L8e
            r11.truncate(r12)     // Catch: java.io.IOException -> L71 java.lang.Throwable -> L8e
            r8.close()     // Catch: java.lang.Exception -> L9c
            r11 = 1
            r0.close()     // Catch: java.lang.Exception -> L9c
            return r11
        L71:
            r11 = move-exception
            goto L80
        L73:
            r12 = move-exception
            r0 = r11
            goto L90
        L76:
            r12 = move-exception
            r0 = r11
            goto L7f
        L79:
            r12 = move-exception
            r0 = r11
            goto L91
        L7c:
            r12 = move-exception
            r0 = r11
            r8 = r0
        L7f:
            r11 = r12
        L80:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L8e
            if (r8 == 0) goto L88
            r8.close()     // Catch: java.lang.Exception -> L9c
        L88:
            if (r0 == 0) goto La0
            r0.close()     // Catch: java.lang.Exception -> L9c
            goto La0
        L8e:
            r11 = move-exception
            r12 = r11
        L90:
            r11 = r8
        L91:
            if (r11 == 0) goto L96
            r11.close()     // Catch: java.lang.Exception -> L9c
        L96:
            if (r0 == 0) goto L9b
            r0.close()     // Catch: java.lang.Exception -> L9c
        L9b:
            throw r12     // Catch: java.lang.Exception -> L9c
        L9c:
            r11 = move-exception
            r11.printStackTrace()
        La0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.motionphoto.utils.MotionPhotoVideoUtils.saveVideo(java.io.File, java.io.File):boolean");
    }

    public final boolean deleteVideo(File srcFile) {
        Log.i(TAG, "deleteVideo");
        try {
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(srcFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } finally {
                        randomAccessFile.close();
                    }
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
            MotionPhotoInfo sEFDataPosition = getSEFDataPosition(outFile, SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
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
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                fileInputStream.close();
                return false;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public final long getXmpPosition() {
        return xmpPosition;
    }

    public final void setXmpPosition(long var1) {
        xmpPosition = var1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a3, code lost:
    
        return 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final long seekToXmpStartPosition(java.io.FileDescriptor r11) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.motionphoto.utils.MotionPhotoVideoUtils.seekToXmpStartPosition(java.io.FileDescriptor):long");
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
                        byteBuffer = ByteBuffer.allocateDirect((int) ((fileInputStream.getChannel().size() - xmpPosition) - j));
                        fileInputStream.getChannel().position(xmpPosition + j);
                        int read = fileInputStream.getChannel().read(byteBuffer);
                        if (read != byteBuffer.capacity()) {
                            Log.i(TAG, "read bytes(" + read + ") differ from buffer size(" + byteBuffer.capacity() + ')');
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
            if (sEFDataPosition != null) {
                j = sEFDataPosition.getOffset();
                j2 = sEFDataPosition.getLength();
            } else {
                j = 0;
                j2 = 0;
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
                                fileInputStream.getChannel().position(4 + j6);
                                fileInputStream.read(bArr, 0, 4);
                                long j8 = bArr[3] & 255;
                                try {
                                    j5 = ((bArr[2] << 8) & 65280) | j8;
                                    try {
                                        j5 = j5 | ((bArr[1] << 16) & 16711680) | (bArr[0] << 24);
                                        fileInputStream.getChannel().position(j6 + 8);
                                        fileInputStream.read(bArr, 0, 4);
                                        long j9 = bArr[3] & 255;
                                        try {
                                            j9 = j9 | ((bArr[2] << 8) & 65280) | ((bArr[1] << 16) & 16711680);
                                            j4 = j9 | (bArr[0] << 24);
                                            try {
                                                Log.d(TAG, "This file is a MotionPhoto V2 format - offset:" + j5 + " length:" + j4);
                                            } catch (Exception e) {
                                                e = e;
                                                j6 = j5;
                                                j3 = j4;
                                                e.printStackTrace();
                                                j4 = j3;
                                                j5 = j6;
                                                fileInputStream.close();
                                                j2 = j4;
                                                z = z2;
                                                j = j5;
                                                return new MotionPhotoInfo(j, j2, z);
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            j4 = j9;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        j6 = j5;
                                        z3 = z2;
                                        z2 = z3;
                                        j3 = 0;
                                        e.printStackTrace();
                                        j4 = j3;
                                        j5 = j6;
                                        fileInputStream.close();
                                        j2 = j4;
                                        z = z2;
                                        j = j5;
                                        return new MotionPhotoInfo(j, j2, z);
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    j6 = j8;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                z3 = z2;
                                j6 = 0;
                                z2 = z3;
                                j3 = 0;
                                e.printStackTrace();
                                j4 = j3;
                                j5 = j6;
                                fileInputStream.close();
                                j2 = j4;
                                z = z2;
                                j = j5;
                                return new MotionPhotoInfo(j, j2, z);
                            }
                        } else {
                            try {
                                Log.d(TAG, "This file is not a MotionPhoto V2 format - offset:" + j6 + " length:" + j7);
                                j5 = j6;
                                j4 = j7;
                            } catch (Exception e6) {
                                e = e6;
                                j3 = j7;
                                e.printStackTrace();
                                j4 = j3;
                                j5 = j6;
                                fileInputStream.close();
                                j2 = j4;
                                z = z2;
                                j = j5;
                                return new MotionPhotoInfo(j, j2, z);
                            }
                        }
                    } catch (Exception e7) {
                        e = e7;
                    }
                    fileInputStream.close();
                    j2 = j4;
                    z = z2;
                    j = j5;
                } finally {
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
