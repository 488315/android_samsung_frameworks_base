package com.google.android.mms.util;

import android.content.Context;
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
    /* JADX WARN: Removed duplicated region for block: B:21:0x004b A[ADDED_TO_REGION] */
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
    */
    public static DrmConvertSession open(Context context, String str) {
        ?? Equals;
        int iOpenConvertSession = -1;
        if (context == null || str == null || (Equals = str.equals("")) != 0) {
            Equals = 0;
        } else {
            try {
                try {
                    Equals = new DrmManagerClient(context);
                } catch (IllegalArgumentException unused) {
                    Log.w(TAG, "DrmManagerClient instance could not be created, context is Illegal.");
                    if (Equals != 0) {
                    }
                    return null;
                } catch (IllegalStateException unused2) {
                    Log.w(TAG, "DrmManagerClient didn't initialize properly.");
                    if (Equals != 0) {
                    }
                    return null;
                }
                try {
                    iOpenConvertSession = Equals.openConvertSession(str);
                } catch (IllegalArgumentException e) {
                    Log.w(TAG, "Conversion of Mimetype: " + str + " is not supported.", e);
                    if (Equals != 0) {
                    }
                    return null;
                } catch (IllegalStateException e2) {
                    Log.w(TAG, "Could not access Open DrmFramework.", e2);
                    if (Equals != 0) {
                    }
                    return null;
                }
            } catch (IllegalArgumentException unused3) {
                Equals = 0;
                Log.w(TAG, "DrmManagerClient instance could not be created, context is Illegal.");
                if (Equals != 0) {
                }
                return null;
            } catch (IllegalStateException unused4) {
                Equals = 0;
                Log.w(TAG, "DrmManagerClient didn't initialize properly.");
                if (Equals != 0) {
                }
                return null;
            }
        }
        if (Equals != 0 || iOpenConvertSession < 0) {
            return null;
        }
        return new DrmConvertSession(Equals, iOpenConvertSession);
    }

    public byte[] convert(byte[] bArr, int i) {
        DrmConvertedStatus drmConvertedStatusConvertData;
        if (bArr != null) {
            try {
                if (i != bArr.length) {
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    drmConvertedStatusConvertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr2);
                } else {
                    drmConvertedStatusConvertData = this.mDrmClient.convertData(this.mConvertSessionId, bArr);
                }
                if (drmConvertedStatusConvertData == null || drmConvertedStatusConvertData.statusCode != 1 || drmConvertedStatusConvertData.convertedData == null) {
                    return null;
                }
                return drmConvertedStatusConvertData.convertedData;
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
    public int close(String str) throws Throwable {
        int i;
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2;
        DrmManagerClient drmManagerClient = this.mDrmClient;
        int i2 = 491;
        if (drmManagerClient == null || (i = this.mConvertSessionId) < 0) {
            return 491;
        }
        try {
            DrmConvertedStatus drmConvertedStatusCloseConvertSession = drmManagerClient.closeConvertSession(i);
            if (drmConvertedStatusCloseConvertSession == null || drmConvertedStatusCloseConvertSession.statusCode != 1 || drmConvertedStatusCloseConvertSession.convertedData == null) {
                return 406;
            }
            RandomAccessFile randomAccessFile3 = null;
            RandomAccessFile randomAccessFile4 = null;
            RandomAccessFile randomAccessFile5 = null;
            RandomAccessFile randomAccessFile6 = null;
            RandomAccessFile randomAccessFile7 = null;
            randomAccessFile3 = null;
            try {
                try {
                    try {
                        try {
                            randomAccessFile2 = new RandomAccessFile(str, "rw");
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (IllegalStateException e) {
                        e = e;
                        i2 = 492;
                        Log.w(TAG, "Could not close convertsession. Convertsession: " + this.mConvertSessionId, e);
                        return i2;
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                } catch (IOException e3) {
                    e = e3;
                } catch (IllegalArgumentException e4) {
                    e = e4;
                } catch (SecurityException e5) {
                    e = e5;
                }
            } catch (Throwable th2) {
                th = th2;
                i2 = 492;
            }
            try {
                int i3 = drmConvertedStatusCloseConvertSession.offset;
                randomAccessFile2.seek(i3);
                randomAccessFile2.write(drmConvertedStatusCloseConvertSession.convertedData);
                i2 = 200;
                try {
                    randomAccessFile2.close();
                    randomAccessFile3 = i3;
                } catch (IOException e6) {
                    e = e6;
                    str = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                    randomAccessFile = i3;
                    Log.w(TAG, str, e);
                    randomAccessFile3 = randomAccessFile;
                    return 492;
                }
            } catch (FileNotFoundException e7) {
                e = e7;
                randomAccessFile4 = randomAccessFile2;
                Log.w(TAG, "File: " + str + " could not be found.", e);
                randomAccessFile3 = randomAccessFile4;
                if (randomAccessFile4 != null) {
                    try {
                        randomAccessFile4.close();
                        randomAccessFile3 = randomAccessFile4;
                    } catch (IOException e8) {
                        e = e8;
                        str = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                        randomAccessFile = randomAccessFile4;
                        Log.w(TAG, str, e);
                        randomAccessFile3 = randomAccessFile;
                        return 492;
                    }
                }
                return 492;
            } catch (IOException e9) {
                e = e9;
                randomAccessFile5 = randomAccessFile2;
                Log.w(TAG, "Could not access File: " + str + " .", e);
                randomAccessFile3 = randomAccessFile5;
                if (randomAccessFile5 != null) {
                    try {
                        randomAccessFile5.close();
                        randomAccessFile3 = randomAccessFile5;
                    } catch (IOException e10) {
                        e = e10;
                        str = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                        randomAccessFile = randomAccessFile5;
                        Log.w(TAG, str, e);
                        randomAccessFile3 = randomAccessFile;
                        return 492;
                    }
                }
                return 492;
            } catch (IllegalArgumentException e11) {
                e = e11;
                randomAccessFile6 = randomAccessFile2;
                Log.w(TAG, "Could not open file in mode: rw", e);
                randomAccessFile3 = randomAccessFile6;
                if (randomAccessFile6 != null) {
                    try {
                        randomAccessFile6.close();
                        randomAccessFile3 = randomAccessFile6;
                    } catch (IOException e12) {
                        e = e12;
                        str = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                        randomAccessFile = randomAccessFile6;
                        Log.w(TAG, str, e);
                        randomAccessFile3 = randomAccessFile;
                        return 492;
                    }
                }
                return 492;
            } catch (SecurityException e13) {
                e = e13;
                randomAccessFile7 = randomAccessFile2;
                Log.w(TAG, "Access to File: " + str + " was denied denied by SecurityManager.", e);
                randomAccessFile3 = randomAccessFile7;
                if (randomAccessFile7 != null) {
                    try {
                        randomAccessFile7.close();
                        randomAccessFile3 = randomAccessFile7;
                    } catch (IOException e14) {
                        e = e14;
                        str = "Failed to close File:" + str + MediaMetrics.SEPARATOR;
                        randomAccessFile = randomAccessFile7;
                        Log.w(TAG, str, e);
                        randomAccessFile3 = randomAccessFile;
                        return 492;
                    }
                }
                return i2;
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile3 = randomAccessFile2;
                if (randomAccessFile3 != null) {
                    try {
                        randomAccessFile3.close();
                    } catch (IOException e15) {
                        Log.w(TAG, "Failed to close File:" + str + MediaMetrics.SEPARATOR, e15);
                        i2 = 492;
                    }
                }
                throw th;
            }
            return i2;
        } catch (IllegalStateException e16) {
            e = e16;
        }
    }
}
