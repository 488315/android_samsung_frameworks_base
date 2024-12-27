package com.android.server.location.gnss;

import android.util.Log;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class GnssPsdsDownloader {
    public static final int CONNECTION_TIMEOUT_MS;
    public static final int READ_TIMEOUT_MS;
    public int mFailReason = 4;
    public final String[] mLongTermPsdsServers;
    public int mNextServerIndex;
    public final String[] mPsdsServers;

    static {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        CONNECTION_TIMEOUT_MS = (int) timeUnit.toMillis(30L);
        READ_TIMEOUT_MS = (int) timeUnit.toMillis(60L);
    }

    public GnssPsdsDownloader(Properties properties) {
        String property = properties.getProperty("LONGTERM_PSDS_SERVER_1");
        String property2 = properties.getProperty("LONGTERM_PSDS_SERVER_2");
        String property3 = properties.getProperty("LONGTERM_PSDS_SERVER_3");
        int i = 1;
        int i2 = property != null ? 1 : 0;
        i2 = property2 != null ? i2 + 1 : i2;
        i2 = property3 != null ? i2 + 1 : i2;
        if (i2 == 0) {
            Log.e(
                    "GnssPsdsDownloader",
                    "No Long-Term PSDS servers were specified in the GnssConfiguration");
            this.mLongTermPsdsServers = null;
        } else {
            String[] strArr = new String[i2];
            this.mLongTermPsdsServers = strArr;
            if (property != null) {
                strArr[0] = property;
            } else {
                i = 0;
            }
            if (property2 != null) {
                strArr[i] = property2;
                i++;
            }
            if (property3 != null) {
                strArr[i] = property3;
                i++;
            }
            this.mNextServerIndex = new Random().nextInt(i);
        }
        String property4 = properties.getProperty("NORMAL_PSDS_SERVER");
        String property5 = properties.getProperty("REALTIME_PSDS_SERVER");
        String[] strArr2 = new String[4];
        this.mPsdsServers = strArr2;
        strArr2[2] = property4;
        strArr2[3] = property5;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] doDownload(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "HTTP error downloading gnss PSDS: "
            java.lang.String r1 = "Downloading PSDS data from "
            java.lang.String r2 = "GnssPsdsDownloader"
            com.android.server.DualAppManagerService$$ExternalSyntheticOutline0.m(r1, r10, r2)
            r1 = 0
            java.net.URL r3 = new java.net.URL     // Catch: java.lang.Throwable -> La0 java.io.IOException -> La2
            r3.<init>(r10)     // Catch: java.lang.Throwable -> La0 java.io.IOException -> La2
            java.net.URLConnection r10 = r3.openConnection()     // Catch: java.lang.Throwable -> La0 java.io.IOException -> La2
            java.net.HttpURLConnection r10 = (java.net.HttpURLConnection) r10     // Catch: java.lang.Throwable -> La0 java.io.IOException -> La2
            java.lang.String r3 = "Accept"
        */
        //  java.lang.String r4 = "*/*, application/vnd.wap.mms-message, application/vnd.wap.sic"
        /*
            r10.setRequestProperty(r3, r4)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            java.lang.String r3 = "x-wap-profile"
            java.lang.String r4 = "http://www.openmobilealliance.org/tech/profiles/UAPROF/ccppschema-20021212#"
            r10.setRequestProperty(r3, r4)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            int r3 = com.android.server.location.gnss.GnssPsdsDownloader.CONNECTION_TIMEOUT_MS     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.setConnectTimeout(r3)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            int r3 = com.android.server.location.gnss.GnssPsdsDownloader.READ_TIMEOUT_MS     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.setReadTimeout(r3)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.connect()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            int r3 = r10.getResponseCode()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 == r4) goto L55
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r4.append(r3)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            android.util.Log.d(r2, r0)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r0 = 2
            r9.mFailReason = r0     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.disconnect()
            return r1
        L50:
            r9 = move-exception
            r1 = r10
            goto Lb2
        L53:
            r0 = move-exception
            goto La4
        L55:
            java.io.InputStream r0 = r10.getInputStream()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L88
            r3.<init>()     // Catch: java.lang.Throwable -> L88
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L88
        L62:
            int r5 = r0.read(r4)     // Catch: java.lang.Throwable -> L88
            r6 = -1
            if (r5 == r6) goto L8a
            r6 = 0
            r3.write(r4, r6, r5)     // Catch: java.lang.Throwable -> L88
            int r5 = r3.size()     // Catch: java.lang.Throwable -> L88
            long r5 = (long) r5     // Catch: java.lang.Throwable -> L88
            r7 = 1000000(0xf4240, double:4.940656E-318)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L62
            java.lang.String r3 = "PSDS file too large"
            android.util.Log.d(r2, r3)     // Catch: java.lang.Throwable -> L88
            r3 = 3
            r9.mFailReason = r3     // Catch: java.lang.Throwable -> L88
            r0.close()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.disconnect()
            return r1
        L88:
            r3 = move-exception
            goto L95
        L8a:
            byte[] r3 = r3.toByteArray()     // Catch: java.lang.Throwable -> L88
            r0.close()     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
            r10.disconnect()
            return r3
        L95:
            if (r0 == 0) goto L9f
            r0.close()     // Catch: java.lang.Throwable -> L9b
            goto L9f
        L9b:
            r0 = move-exception
            r3.addSuppressed(r0)     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
        L9f:
            throw r3     // Catch: java.lang.Throwable -> L50 java.io.IOException -> L53
        La0:
            r9 = move-exception
            goto Lb2
        La2:
            r0 = move-exception
            r10 = r1
        La4:
            java.lang.String r3 = "Error downloading gnss PSDS: "
            android.util.Log.d(r2, r3, r0)     // Catch: java.lang.Throwable -> L50
            if (r10 == 0) goto Lae
            r10.disconnect()
        Lae:
            r10 = 4
            r9.mFailReason = r10
            return r1
        Lb2:
            if (r1 == 0) goto Lb7
            r1.disconnect()
        Lb7:
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.gnss.GnssPsdsDownloader.doDownload(java.lang.String):byte[]");
    }
}
