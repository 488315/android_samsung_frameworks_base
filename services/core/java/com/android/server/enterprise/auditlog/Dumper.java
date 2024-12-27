package com.android.server.enterprise.auditlog;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class Dumper extends Thread {
    public long mBegin;
    public List mDeviceInfo;
    public ArrayList mDumpFilesList;
    public boolean mDumpResult;
    public long mEnd;
    public Filter mFilter;
    public StringBuilder mHeader;
    public SimpleDateFormat mHeaderDate;
    public boolean mIsFullDump;
    public Admin mObserver;
    public String mPackageName;
    public ParcelFileDescriptor mPfd;
    public File mTemporaryDirectory;
    public String mTemporaryPath;

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e("Dumper", "Failed to close resource.", e);
            }
        }
    }

    public final boolean appendFileNodeToTemporaryFile(File file) {
        File file2 = this.mTemporaryDirectory;
        if (file2 == null || !file2.exists()) {
            Log.e("Dumper", "Invalid temporary directory, cannot create file");
            return false;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                this = new FileOutputStream(new File(this.mTemporaryDirectory.getAbsolutePath() + "/temp.gz"), true);
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
                        while (true) {
                            int read = fileInputStream2.read(bArr);
                            if (read > 0) {
                                this.write(bArr, 0, read);
                            } else {
                                try {
                                    break;
                                } catch (IOException unused) {
                                }
                            }
                        }
                        fileInputStream2.close();
                        try {
                            this.close();
                        } catch (IOException unused2) {
                        }
                        return true;
                    } catch (IOException e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        Log.e("Dumper", "Failed to append file: " + e.getMessage());
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                        if (this != 0) {
                            try {
                                this.close();
                            } catch (IOException unused4) {
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused5) {
                            }
                        }
                        if (this == 0) {
                            throw th;
                        }
                        try {
                            this.close();
                            throw th;
                        } catch (IOException unused6) {
                            throw th;
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (IOException e3) {
                e = e3;
                this = 0;
            } catch (Throwable th2) {
                th = th2;
                this = 0;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final File concatenateFiles(File file, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        File file2 = new File(this.mTemporaryPath);
        this.mTemporaryDirectory = file2;
        if (!file2.exists()) {
            this.mTemporaryDirectory.mkdir();
        }
        try {
            if (!appendFileNodeToTemporaryFile(file)) {
                Log.e("Dumper", "Failed to append tempHeaderFile");
                removeTempFile();
                return null;
            }
            while (it.hasNext()) {
                PartialFileNode partialFileNode = (PartialFileNode) it.next();
                if (!partialFileNode.mWasWritten) {
                    partialFileNode.delete();
                    it.remove();
                } else if (partialFileNode.mFile.exists() && !appendFileNodeToTemporaryFile(partialFileNode.mFile)) {
                    Log.e("Dumper", "Failed to append file node");
                    removeTempFile();
                    return null;
                }
            }
            if (this.mTemporaryDirectory == null) {
                return null;
            }
            return new File(this.mTemporaryDirectory.getAbsolutePath() + "/temp.gz");
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("concatenateFiles.Exception: "), "Dumper");
            return null;
        }
    }

    public final void createHeader() {
        this.mHeader = new StringBuilder();
        this.mHeaderDate = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        this.mHeader.append("----------------------------------------------\n");
        Iterator it = this.mDeviceInfo.iterator();
        if (it != null) {
            while (it.hasNext()) {
                this.mHeader.append(((String) it.next()).toString() + "\n");
            }
        }
        this.mHeader.append("Dump Log Generated: " + this.mHeaderDate.format(new Date()) + "\n");
        this.mHeader.append("----------------------------------------------\n");
    }

    public final void createHeaderTempFile(File file) {
        FileOutputStream fileOutputStream;
        GZIPOutputStream gZIPOutputStream;
        GZIPOutputStream gZIPOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                gZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream));
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            gZIPOutputStream.write(this.mHeader.toString().getBytes());
            try {
                gZIPOutputStream.finish();
                gZIPOutputStream.close();
            } catch (Exception unused) {
            }
            try {
                fileOutputStream.close();
            } catch (Exception unused2) {
            }
        } catch (Throwable th3) {
            th = th3;
            gZIPOutputStream2 = gZIPOutputStream;
            if (gZIPOutputStream2 != null) {
                try {
                    gZIPOutputStream2.finish();
                    gZIPOutputStream2.close();
                } catch (Exception unused3) {
                }
            }
            if (fileOutputStream == null) {
                throw th;
            }
            try {
                fileOutputStream.close();
                throw th;
            } catch (Exception unused4) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fullDump() {
        /*
            Method dump skipped, instructions count: 669
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.auditlog.Dumper.fullDump():void");
    }

    public final File readFileLineByLine(File file) {
        Object obj;
        FileOutputStream fileOutputStream;
        GZIPOutputStream gZIPOutputStream;
        Closeable closeable;
        ?? r4;
        ?? r5;
        File file2;
        ?? r14;
        GZIPOutputStream gZIPOutputStream2;
        GZIPOutputStream gZIPOutputStream3;
        File file3;
        long longValue;
        long j;
        BufferedReader bufferedReader = null;
        try {
            try {
                File file4 = new File(this.mTemporaryPath);
                this.mTemporaryDirectory = file4;
                if (!file4.exists()) {
                    this.mTemporaryDirectory.mkdir();
                }
                File file5 = new File(this.mTemporaryDirectory.getAbsolutePath() + file.getName() + "Tmp");
                try {
                    fileOutputStream = new FileOutputStream(file5);
                    try {
                        gZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream));
                    } catch (Exception e) {
                        e = e;
                        gZIPOutputStream = null;
                        gZIPOutputStream2 = gZIPOutputStream;
                        r5 = gZIPOutputStream2;
                        gZIPOutputStream3 = gZIPOutputStream2;
                        file2 = file5;
                        closeable = r5;
                        r4 = gZIPOutputStream3;
                        try {
                            Log.e("Dumper", "readFileLineByLine.IOException");
                            InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
                            safeClose(bufferedReader);
                            safeClose(r5);
                            safeClose(closeable);
                            safeClose(r4);
                            safeClose(gZIPOutputStream);
                            safeClose(fileOutputStream);
                            return file2;
                        } catch (Throwable th) {
                            th = th;
                            r14 = closeable;
                            safeClose(bufferedReader);
                            safeClose(r5);
                            safeClose(r14);
                            safeClose(r4);
                            safeClose(gZIPOutputStream);
                            safeClose(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        obj = null;
                        gZIPOutputStream = null;
                        r4 = gZIPOutputStream;
                        r14 = obj;
                        r5 = r4;
                        safeClose(bufferedReader);
                        safeClose(r5);
                        safeClose(r14);
                        safeClose(r4);
                        safeClose(gZIPOutputStream);
                        safeClose(fileOutputStream);
                        throw th;
                    }
                    try {
                        r4 = new FileInputStream(file);
                    } catch (Exception e2) {
                        e = e2;
                        gZIPOutputStream2 = null;
                        r5 = gZIPOutputStream2;
                        gZIPOutputStream3 = gZIPOutputStream2;
                        file2 = file5;
                        closeable = r5;
                        r4 = gZIPOutputStream3;
                        Log.e("Dumper", "readFileLineByLine.IOException");
                        InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
                        safeClose(bufferedReader);
                        safeClose(r5);
                        safeClose(closeable);
                        safeClose(r4);
                        safeClose(gZIPOutputStream);
                        safeClose(fileOutputStream);
                        return file2;
                    } catch (Throwable th3) {
                        th = th3;
                        r14 = 0;
                        r4 = 0;
                        r5 = r4;
                        safeClose(bufferedReader);
                        safeClose(r5);
                        safeClose(r14);
                        safeClose(r4);
                        safeClose(gZIPOutputStream);
                        safeClose(fileOutputStream);
                        throw th;
                    }
                    try {
                        r14 = new GZIPInputStream(r4);
                        try {
                            r5 = new InputStreamReader((InputStream) r14, "UTF-8");
                            try {
                                BufferedReader bufferedReader2 = new BufferedReader(r5);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader2.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        try {
                                            longValue = Long.valueOf(readLine.split(" ")[0]).longValue();
                                            j = this.mEnd;
                                        } catch (NumberFormatException unused) {
                                        }
                                        if (longValue > j) {
                                            break;
                                        }
                                        if (longValue >= this.mBegin && longValue <= j) {
                                            Filter filter = this.mFilter;
                                            if (filter == null) {
                                                gZIPOutputStream.write((readLine + "\n").getBytes());
                                            } else if (filter.mPattern.matcher(readLine).find()) {
                                                gZIPOutputStream.write((readLine + "\n").getBytes());
                                            }
                                        }
                                    } catch (Exception e3) {
                                        file3 = file5;
                                        closeable = r14;
                                        e = e3;
                                        bufferedReader = bufferedReader2;
                                        file2 = file3;
                                        r4 = r4;
                                        Log.e("Dumper", "readFileLineByLine.IOException");
                                        InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
                                        safeClose(bufferedReader);
                                        safeClose(r5);
                                        safeClose(closeable);
                                        safeClose(r4);
                                        safeClose(gZIPOutputStream);
                                        safeClose(fileOutputStream);
                                        return file2;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        bufferedReader = bufferedReader2;
                                        safeClose(bufferedReader);
                                        safeClose(r5);
                                        safeClose(r14);
                                        safeClose(r4);
                                        safeClose(gZIPOutputStream);
                                        safeClose(fileOutputStream);
                                        throw th;
                                    }
                                }
                                safeClose(bufferedReader2);
                                safeClose(r5);
                                safeClose(r14);
                                safeClose(r4);
                                safeClose(gZIPOutputStream);
                                safeClose(fileOutputStream);
                                return file5;
                            } catch (Exception e4) {
                                file3 = file5;
                                closeable = r14;
                                e = e4;
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        } catch (Exception e5) {
                            file2 = file5;
                            closeable = r14;
                            e = e5;
                            r5 = null;
                            r4 = r4;
                        } catch (Throwable th6) {
                            th = th6;
                            r5 = null;
                        }
                    } catch (Exception e6) {
                        e = e6;
                        r5 = null;
                        gZIPOutputStream3 = r4;
                        file2 = file5;
                        closeable = r5;
                        r4 = gZIPOutputStream3;
                        Log.e("Dumper", "readFileLineByLine.IOException");
                        InformFailure.getInstance().broadcastFailure(e, this.mPackageName);
                        safeClose(bufferedReader);
                        safeClose(r5);
                        safeClose(closeable);
                        safeClose(r4);
                        safeClose(gZIPOutputStream);
                        safeClose(fileOutputStream);
                        return file2;
                    } catch (Throwable th7) {
                        th = th7;
                        r14 = 0;
                        r5 = null;
                    }
                } catch (Exception e7) {
                    e = e7;
                    fileOutputStream = null;
                    gZIPOutputStream = null;
                }
            } catch (Throwable th8) {
                th = th8;
                obj = null;
                fileOutputStream = null;
                gZIPOutputStream = null;
            }
        } catch (Exception e8) {
            e = e8;
            closeable = null;
            fileOutputStream = null;
            gZIPOutputStream = null;
            r4 = 0;
            r5 = null;
            file2 = null;
        }
    }

    public final void removeTempFile() {
        if (this.mTemporaryDirectory == null) {
            return;
        }
        try {
            File file = new File(this.mTemporaryDirectory.getAbsolutePath() + "/temp.gz");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception unused) {
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            java.lang.String r0 = "run.IOException"
            java.lang.String r1 = "Dumper"
            java.lang.String r2 = "run.Exception "
            r7.createHeader()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            long r3 = r7.mEnd     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 < 0) goto L22
            long r3 = r7.mBegin     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L1a
            goto L22
        L1a:
            r7.selectDumpInterval()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            goto L2c
        L1e:
            r2 = move-exception
            goto L69
        L20:
            r3 = move-exception
            goto L41
        L22:
            r7.fullDump()     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            com.android.server.enterprise.auditlog.Filter r3 = r7.mFilter     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
            if (r3 != 0) goto L2c
            r3 = 1
            r7.mIsFullDump = r3     // Catch: java.lang.Throwable -> L1e java.lang.Exception -> L20
        L2c:
            android.os.ParcelFileDescriptor r2 = r7.mPfd     // Catch: java.io.IOException -> L34
            if (r2 == 0) goto L37
            r2.close()     // Catch: java.io.IOException -> L34
            goto L37
        L34:
            android.util.Log.e(r1, r0)
        L37:
            com.android.server.enterprise.auditlog.Admin r0 = r7.mObserver
            boolean r1 = r7.mDumpResult
            boolean r7 = r7.mIsFullDump
            r0.notifyDumpFinished(r1, r7)
            goto L68
        L41:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1e
            r4.<init>(r2)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Throwable -> L1e
            r4.append(r2)     // Catch: java.lang.Throwable -> L1e
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L1e
            android.util.Log.e(r1, r2)     // Catch: java.lang.Throwable -> L1e
            r2 = 0
            r7.mDumpResult = r2     // Catch: java.lang.Throwable -> L1e
            com.android.server.enterprise.auditlog.InformFailure r2 = com.android.server.enterprise.auditlog.InformFailure.getInstance()     // Catch: java.lang.Throwable -> L1e
            java.lang.String r4 = r7.mPackageName     // Catch: java.lang.Throwable -> L1e
            r2.broadcastFailure(r3, r4)     // Catch: java.lang.Throwable -> L1e
            android.os.ParcelFileDescriptor r2 = r7.mPfd     // Catch: java.io.IOException -> L34
            if (r2 == 0) goto L37
            r2.close()     // Catch: java.io.IOException -> L34
            goto L37
        L68:
            return
        L69:
            android.os.ParcelFileDescriptor r3 = r7.mPfd     // Catch: java.io.IOException -> L71
            if (r3 == 0) goto L74
            r3.close()     // Catch: java.io.IOException -> L71
            goto L74
        L71:
            android.util.Log.e(r1, r0)
        L74:
            com.android.server.enterprise.auditlog.Admin r0 = r7.mObserver
            boolean r1 = r7.mDumpResult
            boolean r7 = r7.mIsFullDump
            r0.notifyDumpFinished(r1, r7)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.auditlog.Dumper.run():void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void selectDumpInterval() {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.auditlog.Dumper.selectDumpInterval():void");
    }
}
