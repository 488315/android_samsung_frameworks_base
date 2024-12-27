package com.android.server.remoteappmode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelFileDescriptor;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AnrCollector {
    public final Context mContext;
    public SharedPreferences mPrefs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WriteANRInfoThread extends Thread {
        public final File mLastAnrFile;
        public final ParcelFileDescriptor.AutoCloseOutputStream mOut;

        public WriteANRInfoThread(ParcelFileDescriptor parcelFileDescriptor, File file) {
            super("WriteANRInfoThread");
            this.mLastAnrFile = file;
            this.mOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        }

        public static void closeFileStream(BufferedInputStream bufferedInputStream) {
            if (bufferedInputStream != null) {
                try {
                    Log.i("AnrCollector", "buf.close()");
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public final void closeOutStream() {
            try {
                Log.i("AnrCollector", "write : 0");
                this.mOut.write(0);
                this.mOut.flush();
                Log.i("AnrCollector", "mOut.close()");
                this.mOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            BufferedInputStream bufferedInputStream;
            Throwable th;
            IOException e;
            if (this.mLastAnrFile == null) {
                closeOutStream();
                return;
            }
            try {
                byte[] bArr =
                        new byte
                                [EndpointMonitorConst
                                        .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION];
                bufferedInputStream =
                        new BufferedInputStream(new FileInputStream(this.mLastAnrFile));
                while (true) {
                    try {
                        try {
                            int read =
                                    bufferedInputStream.read(
                                            bArr,
                                            0,
                                            EndpointMonitorConst
                                                    .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                            if (read == -1) {
                                break;
                            } else {
                                this.mOut.write(bArr, 0, read);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            closeFileStream(bufferedInputStream);
                            closeOutStream();
                            throw th;
                        }
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        closeFileStream(bufferedInputStream);
                        closeOutStream();
                    }
                }
            } catch (IOException e3) {
                bufferedInputStream = null;
                e = e3;
            } catch (Throwable th3) {
                bufferedInputStream = null;
                th = th3;
                closeFileStream(bufferedInputStream);
                closeOutStream();
                throw th;
            }
            closeFileStream(bufferedInputStream);
            closeOutStream();
        }
    }

    public AnrCollector(Context context) {
        this.mContext = context;
    }

    public final void getLastAnr(final String str, ParcelFileDescriptor parcelFileDescriptor) {
        File file = new File("/data/anr");
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            r3 =
                    listFiles != null
                            ? (File)
                                    Arrays.stream(listFiles)
                                            .filter(
                                                    new Predicate() { // from class:
                                                        // com.android.server.remoteappmode.AnrCollector$$ExternalSyntheticLambda0
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            AnrCollector anrCollector =
                                                                    AnrCollector.this;
                                                            File file2 = (File) obj;
                                                            anrCollector.getClass();
                                                            if (file2.isDirectory()) {
                                                                return false;
                                                            }
                                                            long j = 0;
                                                            try {
                                                                if (anrCollector.mPrefs == null) {
                                                                    anrCollector.mPrefs =
                                                                            anrCollector.mContext
                                                                                    .getSharedPreferences(
                                                                                            "remote_app_mode_prefs",
                                                                                            0);
                                                                }
                                                                j =
                                                                        anrCollector.mPrefs.getLong(
                                                                                "ltw_get_anr_time",
                                                                                0L);
                                                            } catch (Exception e) {
                                                                Log.i("AnrCollector", e.toString());
                                                            }
                                                            Log.i(
                                                                    "AnrCollector",
                                                                    "isAnrFileModifiedLater - "
                                                                            + file2.getName()
                                                                            + "lastModified : "
                                                                            + file2.lastModified()
                                                                            + ", lastTimestamp : "
                                                                            + j);
                                                            return file2.lastModified() > j;
                                                        }
                                                    })
                                            .filter(
                                                    new Predicate() { // from class:
                                                        // com.android.server.remoteappmode.AnrCollector$$ExternalSyntheticLambda1
                                                        /* JADX WARN: Code restructure failed: missing block: B:15:0x006d, code lost:

                                                           com.android.server.remoteappmode.Log.i("AnrCollector", "isAnrFileFromPackage - return true");
                                                        */
                                                        /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:

                                                           r3.close();
                                                        */
                                                        /* JADX WARN: Code restructure failed: missing block: B:21:0x0076, code lost:

                                                           r7 = move-exception;
                                                        */
                                                        /* JADX WARN: Code restructure failed: missing block: B:22:0x0077, code lost:

                                                           r7.printStackTrace();
                                                        */
                                                        /* JADX WARN: Code restructure failed: missing block: B:28:0x0084, code lost:

                                                           r3.close();
                                                        */
                                                        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x0089 -> B:26:0x0098). Please report as a decompilation issue!!! */
                                                        @Override // java.util.function.Predicate
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                        */
                                                        public final boolean test(
                                                                java.lang.Object r8) {
                                                            /*
                                                                r7 = this;
                                                                com.android.server.remoteappmode.AnrCollector r0 = com.android.server.remoteappmode.AnrCollector.this
                                                                java.lang.String r7 = r2
                                                                java.io.File r8 = (java.io.File) r8
                                                                r0.getClass()
                                                                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                                                                java.lang.String r1 = "isAnrFileFromPackage started - target file : "
                                                                r0.<init>(r1)
                                                                java.lang.String r1 = r8.getName()
                                                                r0.append(r1)
                                                                java.lang.String r0 = r0.toString()
                                                                java.lang.String r1 = "AnrCollector"
                                                                com.android.server.remoteappmode.Log.i(r1, r0)
                                                                r0 = 0
                                                                r2 = 0
                                                                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f
                                                                java.io.FileReader r4 = new java.io.FileReader     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f
                                                                java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f
                                                                r4.<init>(r8, r5)     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f
                                                                r3.<init>(r4)     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f
                                                                r2 = r0
                                                            L2f:
                                                                java.lang.String r4 = r3.readLine()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                if (r4 == 0) goto L84
                                                                int r5 = r2 + 1
                                                                r6 = 30
                                                                if (r2 >= r6) goto L84
                                                                java.lang.String r2 = r4.toLowerCase()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                java.lang.String r6 = "cmd"
                                                                boolean r2 = r2.contains(r6)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                if (r2 == 0) goto L82
                                                                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                r2.<init>()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                java.lang.String r6 = "isAnrFileFromPackage - "
                                                                r2.append(r6)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                r2.append(r8)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                java.lang.String r6 = " : "
                                                                r2.append(r6)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                r2.append(r4)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                com.android.server.remoteappmode.Log.i(r1, r2)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                java.lang.String r2 = r4.toLowerCase()     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                boolean r2 = r2.contains(r7)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                if (r2 == 0) goto L82
                                                                java.lang.String r7 = "isAnrFileFromPackage - return true"
                                                                com.android.server.remoteappmode.Log.i(r1, r7)     // Catch: java.lang.Throwable -> L7c java.io.IOException -> L7f
                                                                r3.close()     // Catch: java.io.IOException -> L76
                                                                goto L7a
                                                            L76:
                                                                r7 = move-exception
                                                                r7.printStackTrace()
                                                            L7a:
                                                                r0 = 1
                                                                goto L9d
                                                            L7c:
                                                                r7 = move-exception
                                                                r2 = r3
                                                                goto L9e
                                                            L7f:
                                                                r7 = move-exception
                                                                r2 = r3
                                                                goto L90
                                                            L82:
                                                                r2 = r5
                                                                goto L2f
                                                            L84:
                                                                r3.close()     // Catch: java.io.IOException -> L88
                                                                goto L98
                                                            L88:
                                                                r7 = move-exception
                                                                r7.printStackTrace()
                                                                goto L98
                                                            L8d:
                                                                r7 = move-exception
                                                                goto L9e
                                                            L8f:
                                                                r7 = move-exception
                                                            L90:
                                                                r7.printStackTrace()     // Catch: java.lang.Throwable -> L8d
                                                                if (r2 == 0) goto L98
                                                                r2.close()     // Catch: java.io.IOException -> L88
                                                            L98:
                                                                java.lang.String r7 = "isAnrFileFromPackage - return false"
                                                                com.android.server.remoteappmode.Log.i(r1, r7)
                                                            L9d:
                                                                return r0
                                                            L9e:
                                                                if (r2 == 0) goto La8
                                                                r2.close()     // Catch: java.io.IOException -> La4
                                                                goto La8
                                                            La4:
                                                                r8 = move-exception
                                                                r8.printStackTrace()
                                                            La8:
                                                                throw r7
                                                            */
                                                            throw new UnsupportedOperationException(
                                                                    "Method not decompiled:"
                                                                        + " com.android.server.remoteappmode.AnrCollector$$ExternalSyntheticLambda1.test(java.lang.Object):boolean");
                                                        }
                                                    })
                                            .findFirst()
                                            .orElse(null)
                            : null;
            long currentTimeMillis = System.currentTimeMillis();
            try {
                if (this.mPrefs == null) {
                    this.mPrefs = this.mContext.getSharedPreferences("remote_app_mode_prefs", 0);
                }
                SharedPreferences.Editor edit = this.mPrefs.edit();
                edit.putLong("ltw_get_anr_time", currentTimeMillis);
                edit.commit();
            } catch (Exception e) {
                Log.i("AnrCollector", e.toString());
            }
        }
        Log.i("AnrCollector", "gatLastAnr - lastAnrFile : " + r3);
        new WriteANRInfoThread(parcelFileDescriptor, r3).start();
    }
}
