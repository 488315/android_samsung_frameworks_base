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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.server.enterprise.auditlog.Dumper] */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.FileOutputStream] */
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

    /* JADX WARN: Removed duplicated region for block: B:103:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x028c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0283 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x027a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0271 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0254 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x023a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0231 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0228 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x021f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0216 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0295 A[EXC_TOP_SPLITTER, SYNTHETIC] */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v18, types: [java.io.Closeable, java.io.InputStream, java.util.zip.GZIPInputStream] */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v11, types: [java.io.Closeable, java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.io.Closeable, java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030 A[Catch: IOException -> 0x0034, TRY_LEAVE, TryCatch #1 {IOException -> 0x0034, blocks: (B:10:0x002c, B:12:0x0030, B:22:0x0060, B:24:0x0064, B:4:0x0008, B:6:0x0013, B:9:0x001a, B:16:0x0022, B:18:0x0029), top: B:2:0x0008, inners: #0 }] */
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

    /* JADX WARN: Removed duplicated region for block: B:102:0x0176 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:109:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x016d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x014c A[Catch: Exception -> 0x0109, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x0109, blocks: (B:42:0x0105, B:81:0x014c), top: B:26:0x009f }] */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0143 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0168  */
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
