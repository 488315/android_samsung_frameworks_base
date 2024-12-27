package com.android.server.enterprise.auditlog;

import android.util.Log;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PartialFileNode {
    public final Object deleteSync;
    public final FileChannel mChannel;
    public volatile File mFile;
    public volatile boolean mMarkAsDeprecated;
    public final String mPackageName;
    public final RandomAccessFile mRandomAccessFile;
    public long mTimestamp;
    public long mTruncateFileAt;
    public boolean mWasWritten;
    public final MappedByteBuffer mWriteBuffer;

    public PartialFileNode(File file, String str) {
        this.deleteSync = new Object();
        this.mWasWritten = false;
        this.mPackageName = str;
        this.mFile = file;
        if (this.mFile != null) {
            this.mTimestamp = this.mFile.lastModified();
        }
    }

    public PartialFileNode(String str, String str2) {
        this.deleteSync = new Object();
        this.mWasWritten = false;
        this.mPackageName = str2;
        this.mFile =
                new File(
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                str, "/", String.valueOf(new Date().getTime())));
        this.mTimestamp = 0L;
        this.mMarkAsDeprecated = false;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.mFile, "rwd");
            this.mRandomAccessFile = randomAccessFile;
            randomAccessFile.setLength(524288L);
            FileChannel channel = randomAccessFile.getChannel();
            this.mChannel = channel;
            MappedByteBuffer map =
                    channel.map(FileChannel.MapMode.READ_WRITE, 0L, (int) channel.size());
            this.mWriteBuffer = map;
            map.mark();
        } catch (Exception e) {
            Log.e("PartialFileNode", "PartialFileNode.Exception: " + e.toString());
            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
        }
    }

    public final void closeFile() {
        try {
            if (this.mFile == null || !this.mChannel.isOpen()) {
                return;
            }
            synchronized (this.mFile) {
                this.mRandomAccessFile.setLength(this.mTruncateFileAt);
                this.mWriteBuffer.force();
                this.mRandomAccessFile.close();
                this.mChannel.close();
            }
        } catch (Exception e) {
            Log.e("PartialFileNode", "closeFile.Exception: " + e.toString());
            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
        }
    }

    public final void compressFile() {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        GZIPOutputStream gZIPOutputStream = null;
        try {
            if (this.mFile != null) {
                File file = new File(this.mFile.getCanonicalPath() + "_tmp");
                fileInputStream = new FileInputStream(this.mFile);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        try {
                            GZIPOutputStream gZIPOutputStream2 =
                                    new GZIPOutputStream(fileOutputStream);
                            try {
                                byte[] bArr =
                                        new byte
                                                [EndpointMonitorConst
                                                        .FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
                                for (int read = fileInputStream.read(bArr);
                                        read > 0;
                                        read = fileInputStream.read(bArr)) {
                                    gZIPOutputStream2.write(bArr, 0, read);
                                }
                                gZIPOutputStream2.finish();
                                if (this.mFile.delete()) {
                                    file.renameTo(this.mFile);
                                }
                                gZIPOutputStream = gZIPOutputStream2;
                            } catch (Exception e) {
                                e = e;
                                gZIPOutputStream = gZIPOutputStream2;
                                e.printStackTrace();
                                if (gZIPOutputStream != null) {
                                    try {
                                        gZIPOutputStream.close();
                                    } catch (Exception unused) {
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused2) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    return;
                                }
                                fileOutputStream.close();
                            } catch (Throwable th) {
                                th = th;
                                gZIPOutputStream = gZIPOutputStream2;
                                if (gZIPOutputStream != null) {
                                    try {
                                        gZIPOutputStream.close();
                                    } catch (Exception unused3) {
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (Exception unused4) {
                                    }
                                }
                                if (fileOutputStream == null) {
                                    throw th;
                                }
                                try {
                                    fileOutputStream.close();
                                    throw th;
                                } catch (Exception unused5) {
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                }
            } else {
                fileInputStream = null;
                fileOutputStream = null;
            }
            if (gZIPOutputStream != null) {
                try {
                    gZIPOutputStream.close();
                } catch (Exception unused6) {
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception unused7) {
                }
            }
            if (fileOutputStream == null) {
                return;
            }
        } catch (Exception e4) {
            e = e4;
            fileInputStream = null;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            fileOutputStream = null;
        }
        try {
            fileOutputStream.close();
        } catch (Exception unused8) {
        }
    }

    public final void delete() {
        synchronized (this.deleteSync) {
            try {
                if (this.mFile != null) {
                    this.mFile.delete();
                    this.mFile = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getFileSize() {
        if (this.mFile != null) {
            return this.mFile.length();
        }
        return 0L;
    }

    public final boolean write(String str) {
        try {
        } catch (Exception e) {
            Log.e("PartialFileNode", "write.Exception: " + e.toString());
            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
        }
        if (str.getBytes().length > this.mWriteBuffer.remaining()) {
            return false;
        }
        if (this.mFile != null) {
            synchronized (this.mFile) {
                try {
                    if (!this.mWasWritten) {
                        this.mWasWritten = true;
                    }
                    long length = this.mTruncateFileAt + str.getBytes().length;
                    this.mTruncateFileAt = length;
                    this.mRandomAccessFile.setLength(length);
                    this.mWriteBuffer.put(str.getBytes());
                } finally {
                }
            }
        }
        return true;
    }
}
