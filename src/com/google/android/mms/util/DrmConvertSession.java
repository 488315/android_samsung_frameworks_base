package com.google.android.mms.util;

import android.drm.DrmConvertedStatus;
import android.drm.DrmManagerClient;
import android.media.MediaMetrics;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* loaded from: classes6.dex */
public class DrmConvertSession {
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_NOT_ACCEPTABLE = 406;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    private static final String TAG = "DrmConvertSession";
    private int mConvertSessionId;
    private DrmManagerClient mDrmClient;

    private DrmConvertSession(DrmManagerClient drmManagerClient, int i) {
        this.mDrmClient = drmManagerClient;
        this.mConvertSessionId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x004b A[ADDED_TO_REGION] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.drm.DrmManagerClient] */
    /* JADX WARN: Type inference failed for: r4v10, types: [android.drm.DrmManagerClient] */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.mms.util.DrmConvertSession open(android.content.Context r6, java.lang.String r7) {
        /*
            java.lang.String r0 = "DrmConvertSession"
            java.lang.String r1 = "Conversion of Mimetype: "
            r2 = 0
            r3 = -1
            if (r6 == 0) goto L48
            if (r7 == 0) goto L48
            java.lang.String r4 = ""
            boolean r4 = r7.equals(r4)
            if (r4 != 0) goto L48
            android.drm.DrmManagerClient r4 = new android.drm.DrmManagerClient     // Catch: java.lang.IllegalStateException -> L3a java.lang.IllegalArgumentException -> L41
            r4.<init>(r6)     // Catch: java.lang.IllegalStateException -> L3a java.lang.IllegalArgumentException -> L41
            int r6 = r4.openConvertSession(r7)     // Catch: java.lang.IllegalStateException -> L1d java.lang.IllegalArgumentException -> L24
            r3 = r6
            goto L49
        L1d:
            r6 = move-exception
            java.lang.String r7 = "Could not access Open DrmFramework."
            android.util.Log.w(r0, r7, r6)     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            goto L49
        L24:
            r6 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            r5.<init>(r1)     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            r5.append(r7)     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            java.lang.String r7 = " is not supported."
            r5.append(r7)     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            java.lang.String r7 = r5.toString()     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            android.util.Log.w(r0, r7, r6)     // Catch: java.lang.IllegalStateException -> L3b java.lang.IllegalArgumentException -> L42
            goto L49
        L3a:
            r4 = r2
        L3b:
            java.lang.String r6 = "DrmManagerClient didn't initialize properly."
            android.util.Log.w(r0, r6)
            goto L49
        L41:
            r4 = r2
        L42:
            java.lang.String r6 = "DrmManagerClient instance could not be created, context is Illegal."
            android.util.Log.w(r0, r6)
            goto L49
        L48:
            r4 = r2
        L49:
            if (r4 == 0) goto L54
            if (r3 >= 0) goto L4e
            goto L54
        L4e:
            com.google.android.mms.util.DrmConvertSession r6 = new com.google.android.mms.util.DrmConvertSession
            r6.<init>(r4, r3)
            return r6
        L54:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.mms.util.DrmConvertSession.open(android.content.Context, java.lang.String):com.google.android.mms.util.DrmConvertSession");
    }

    public byte[] convert(byte[] bArr, int i) {
        DrmConvertedStatus convertData;
        if (bArr != null) {
            try {
                if (i != bArr.length) {
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    convertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr2);
                } else {
                    convertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr);
                }
                if (convertData == null || convertData.statusCode != 1 || convertData.convertedData == null) {
                    return null;
                }
                return convertData.convertedData;
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "Buffer with data to convert is illegal. Convertsession: " + this.mConvertSessionId, e);
                return null;
            } catch (IllegalStateException e2) {
                Log.w(TAG, "Could not convert data. Convertsession: " + this.mConvertSessionId, e2);
                return null;
            }
        }
        throw new IllegalArgumentException("Parameter inBuffer is null");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int close(String str) {
        int i;
        String str2;
        RandomAccessFile randomAccessFile;
        int i2;
        DrmManagerClient drmManagerClient = this.mDrmClient;
        int i3 = 491;
        if (drmManagerClient == null || (i = this.mConvertSessionId) < 0) {
            return 491;
        }
        try {
            DrmConvertedStatus closeConvertSession = drmManagerClient.closeConvertSession(i);
            if (closeConvertSession == null || closeConvertSession.statusCode != 1 || closeConvertSession.convertedData == null) {
                return 406;
            }
            RandomAccessFile randomAccessFile2 = null;
            RandomAccessFile randomAccessFile3 = null;
            RandomAccessFile randomAccessFile4 = null;
            RandomAccessFile randomAccessFile5 = null;
            RandomAccessFile randomAccessFile6 = null;
            randomAccessFile2 = null;
            try {
                try {
                    try {
                        try {
                            randomAccessFile = new RandomAccessFile(str, "rw");
                            try {
                                i2 = closeConvertSession.offset;
                                randomAccessFile.seek(i2);
                                randomAccessFile.write(closeConvertSession.convertedData);
                                i3 = 200;
                            } catch (FileNotFoundException e) {
                                e = e;
                                randomAccessFile3 = randomAccessFile;
                                Log.w(TAG, "File: " + str + " could not be found.", e);
                                randomAccessFile2 = randomAccessFile3;
                                if (randomAccessFile3 != null) {
                                    try {
                                        randomAccessFile3.close();
                                        randomAccessFile2 = randomAccessFile3;
                                    } catch (IOException e2) {
                                        e = e2;
                                        str2 = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                                        Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (IOException e3) {
                                e = e3;
                                randomAccessFile4 = randomAccessFile;
                                Log.w(TAG, "Could not access File: " + str + " .", e);
                                randomAccessFile2 = randomAccessFile4;
                                if (randomAccessFile4 != null) {
                                    try {
                                        randomAccessFile4.close();
                                        randomAccessFile2 = randomAccessFile4;
                                    } catch (IOException e4) {
                                        e = e4;
                                        str2 = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                                        Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (IllegalArgumentException e5) {
                                e = e5;
                                randomAccessFile5 = randomAccessFile;
                                Log.w(TAG, "Could not open file in mode: rw", e);
                                randomAccessFile2 = randomAccessFile5;
                                if (randomAccessFile5 != null) {
                                    try {
                                        randomAccessFile5.close();
                                        randomAccessFile2 = randomAccessFile5;
                                    } catch (IOException e6) {
                                        e = e6;
                                        str2 = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                                        Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return 492;
                            } catch (SecurityException e7) {
                                e = e7;
                                randomAccessFile6 = randomAccessFile;
                                Log.w(TAG, "Access to File: " + str + " was denied denied by SecurityManager.", e);
                                randomAccessFile2 = randomAccessFile6;
                                if (randomAccessFile6 != null) {
                                    try {
                                        randomAccessFile6.close();
                                        randomAccessFile2 = randomAccessFile6;
                                    } catch (IOException e8) {
                                        e = e8;
                                        str2 = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                                        Log.w(TAG, str2, e);
                                        return 492;
                                    }
                                }
                                return i3;
                            } catch (Throwable th) {
                                th = th;
                                randomAccessFile2 = randomAccessFile;
                                if (randomAccessFile2 != null) {
                                    try {
                                        randomAccessFile2.close();
                                    } catch (IOException e9) {
                                        Log.w(TAG, "Failed to close File:" + str + MediaMetrics.SEPARATOR, e9);
                                        i3 = 492;
                                    }
                                }
                                throw th;
                            }
                        } catch (FileNotFoundException e10) {
                            e = e10;
                        } catch (IOException e11) {
                            e = e11;
                        } catch (IllegalArgumentException e12) {
                            e = e12;
                        } catch (SecurityException e13) {
                            e = e13;
                        }
                        try {
                            randomAccessFile.close();
                            randomAccessFile2 = i2;
                            return i3;
                        } catch (IOException e14) {
                            e = e14;
                            str2 = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                            Log.w(TAG, str2, e);
                            return 492;
                        }
                    } catch (IllegalStateException e15) {
                        e = e15;
                        i3 = 492;
                        Log.w(TAG, "Could not close convertsession. Convertsession: " + this.mConvertSessionId, e);
                        return i3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                i3 = 492;
            }
        } catch (IllegalStateException e16) {
            e = e16;
        }
    }
}
