package com.android.server.enterprise.auditlog;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes2.dex */
public class PartialFileNode {
    public static long FILESIZE = 524288;
    public final Object deleteSync;
    public FileChannel mChannel;
    public volatile File mFile;
    public volatile boolean mMarkAsDeprecated;
    public String mPackageName;
    public RandomAccessFile mRandomAccessFile;
    public long mTimestamp;
    public long mTruncateFileAt;
    public boolean mWasWritten;
    public MappedByteBuffer mWriteBuffer;

    public PartialFileNode(String str, String str2) {
        this.deleteSync = new Object();
        this.mWasWritten = false;
        this.mPackageName = str2;
        this.mFile = new File(str + "/" + String.valueOf(new Date().getTime()));
        this.mTimestamp = 0L;
        this.mMarkAsDeprecated = false;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.mFile, "rwd");
            this.mRandomAccessFile = randomAccessFile;
            randomAccessFile.setLength(FILESIZE);
            FileChannel channel = this.mRandomAccessFile.getChannel();
            this.mChannel = channel;
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0L, (int) channel.size());
            this.mWriteBuffer = map;
            map.mark();
        } catch (Exception e) {
            Log.e("PartialFileNode", "PartialFileNode.Exception: " + e.toString());
            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
        }
    }

    public PartialFileNode(File file, String str) {
        this.deleteSync = new Object();
        this.mWasWritten = false;
        this.mPackageName = str;
        this.mFile = file;
        setTimestamp();
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public boolean write(String str) {
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
                if (!this.mWasWritten) {
                    this.mWasWritten = true;
                }
                long length = this.mTruncateFileAt + str.getBytes().length;
                this.mTruncateFileAt = length;
                this.mRandomAccessFile.setLength(length);
                this.mWriteBuffer.put(str.getBytes());
            }
        }
        return true;
    }

    public void closeFile() {
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

    public void delete() {
        synchronized (this.deleteSync) {
            if (this.mFile != null) {
                this.mFile.delete();
                this.mFile = null;
            }
        }
    }

    public void setTimestamp() {
        if (this.mFile != null) {
            this.mTimestamp = this.mFile.lastModified();
        }
    }

    public long getFileSize() {
        if (this.mFile != null) {
            return this.mFile.length();
        }
        return 0L;
    }

    public File getFile() {
        return this.mFile;
    }

    public synchronized boolean setDeprecated(boolean z) {
        if (this.mMarkAsDeprecated) {
            return false;
        }
        this.mMarkAsDeprecated = z;
        return true;
    }

    public boolean isDeprecated() {
        return this.mMarkAsDeprecated;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0072, code lost:
    
        if (r4 == null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0074, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008c, code lost:
    
        if (r4 == null) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean compressFile() {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        GZIPOutputStream gZIPOutputStream;
        boolean z = false;
        GZIPOutputStream gZIPOutputStream2 = null;
        try {
            try {
                if (this.mFile != null) {
                    File file = new File(this.mFile.getCanonicalPath() + "_tmp");
                    fileInputStream = new FileInputStream(this.mFile);
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        try {
                            gZIPOutputStream = new GZIPOutputStream(fileOutputStream);
                        } catch (Exception e) {
                            e = e;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = null;
                    }
                    try {
                        byte[] bArr = new byte[65536];
                        for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                            gZIPOutputStream.write(bArr, 0, read);
                        }
                        gZIPOutputStream.finish();
                        boolean delete = this.mFile.delete();
                        if (delete) {
                            file.renameTo(this.mFile);
                        }
                        z = delete;
                        gZIPOutputStream2 = gZIPOutputStream;
                    } catch (Exception e3) {
                        e = e3;
                        gZIPOutputStream2 = gZIPOutputStream;
                        e.printStackTrace();
                        if (gZIPOutputStream2 != null) {
                            try {
                                gZIPOutputStream2.close();
                            } catch (Exception unused) {
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception unused2) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        gZIPOutputStream2 = gZIPOutputStream;
                        if (gZIPOutputStream2 != null) {
                            try {
                                gZIPOutputStream2.close();
                            } catch (Exception unused3) {
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception unused4) {
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                throw th;
                            } catch (Exception unused5) {
                                throw th;
                            }
                        }
                        throw th;
                    }
                } else {
                    fileInputStream = null;
                    fileOutputStream = null;
                }
                if (gZIPOutputStream2 != null) {
                    try {
                        gZIPOutputStream2.close();
                    } catch (Exception unused6) {
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused7) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
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
        return z;
    }

    public boolean getWasWritten() {
        return this.mWasWritten;
    }

    public void setWasWritten(boolean z) {
        this.mWasWritten = z;
    }
}
