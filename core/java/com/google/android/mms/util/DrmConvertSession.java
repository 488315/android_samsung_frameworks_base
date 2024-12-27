package com.google.android.mms.util;

import android.content.Context;
import android.drm.DrmConvertedStatus;
import android.drm.DrmManagerClient;
import android.media.MediaMetrics;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DrmConvertSession {
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_NOT_ACCEPTABLE = 406;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    private static final String TAG = "DrmConvertSession";
    private int mConvertSessionId;
    private DrmManagerClient mDrmClient;

    private DrmConvertSession(DrmManagerClient drmClient, int convertSessionId) {
        this.mDrmClient = drmClient;
        this.mConvertSessionId = convertSessionId;
    }

    public static DrmConvertSession open(Context context, String str) {
        String str2 = TAG;
        DrmManagerClient drmManagerClient = null;
        int i = -1;
        if (context != null && str != null && !str.equals("")) {
            try {
                drmManagerClient = new DrmManagerClient(context);
                try {
                    str2 = drmManagerClient.openConvertSession(str);
                    i = str2;
                    str2 = str2;
                } catch (IllegalArgumentException e) {
                    Log.w(TAG, "Conversion of Mimetype: " + str + " is not supported.", e);
                    str2 = str2;
                } catch (IllegalStateException e2) {
                    Log.w(TAG, "Could not access Open DrmFramework.", e2);
                    str2 = str2;
                }
            } catch (IllegalArgumentException e3) {
                Log.w(str2, "DrmManagerClient instance could not be created, context is Illegal.");
            } catch (IllegalStateException e4) {
                Log.w(str2, "DrmManagerClient didn't initialize properly.");
            }
        }
        if (drmManagerClient == null || i < 0) {
            return null;
        }
        return new DrmConvertSession(drmManagerClient, i);
    }

    public byte[] convert(byte[] inBuffer, int size) {
        DrmConvertedStatus convertedStatus;
        if (inBuffer != null) {
            try {
                if (size != inBuffer.length) {
                    byte[] buf = new byte[size];
                    System.arraycopy(inBuffer, 0, buf, 0, size);
                    convertedStatus = this.mDrmClient.convertData(this.mConvertSessionId, buf);
                } else {
                    convertedStatus = this.mDrmClient.convertData(this.mConvertSessionId, inBuffer);
                }
                if (convertedStatus == null
                        || convertedStatus.statusCode != 1
                        || convertedStatus.convertedData == null) {
                    return null;
                }
                byte[] result = convertedStatus.convertedData;
                return result;
            } catch (IllegalArgumentException e) {
                Log.w(
                        TAG,
                        "Buffer with data to convert is illegal. Convertsession: "
                                + this.mConvertSessionId,
                        e);
                return null;
            } catch (IllegalStateException e2) {
                Log.w(TAG, "Could not convert data. Convertsession: " + this.mConvertSessionId, e2);
                return null;
            }
        }
        throw new IllegalArgumentException("Parameter inBuffer is null");
    }

    public int close(String filename) {
        int result;
        String str;
        int result2 = 491;
        if (this.mDrmClient == null || this.mConvertSessionId < 0) {
            return 491;
        }
        try {
            DrmConvertedStatus convertedStatus =
                    this.mDrmClient.closeConvertSession(this.mConvertSessionId);
            if (convertedStatus == null
                    || convertedStatus.statusCode != 1
                    || convertedStatus.convertedData == null) {
                return 406;
            }
            RandomAccessFile rndAccessFile = null;
            try {
                try {
                    try {
                        try {
                            try {
                                rndAccessFile = new RandomAccessFile(filename, "rw");
                                rndAccessFile.seek(convertedStatus.offset);
                                rndAccessFile.write(convertedStatus.convertedData);
                                result2 = 200;
                                try {
                                    rndAccessFile.close();
                                    return 200;
                                } catch (IOException e) {
                                    e = e;
                                    result = 492;
                                    str =
                                            "Failed to close File:"
                                                    + filename
                                                    + MediaMetrics.SEPARATOR;
                                    Log.w(TAG, str, e);
                                    return result;
                                }
                            } catch (SecurityException e2) {
                                Log.w(
                                        TAG,
                                        "Access to File: "
                                                + filename
                                                + " was denied denied by SecurityManager.",
                                        e2);
                                if (rndAccessFile == null) {
                                    return 491;
                                }
                                try {
                                    rndAccessFile.close();
                                    return 491;
                                } catch (IOException e3) {
                                    e = e3;
                                    result = 492;
                                    str =
                                            "Failed to close File:"
                                                    + filename
                                                    + MediaMetrics.SEPARATOR;
                                    Log.w(TAG, str, e);
                                    return result;
                                }
                            }
                        } catch (FileNotFoundException e4) {
                            result2 = 492;
                            Log.w(TAG, "File: " + filename + " could not be found.", e4);
                            if (rndAccessFile == null) {
                                return 492;
                            }
                            try {
                                rndAccessFile.close();
                                return 492;
                            } catch (IOException e5) {
                                e = e5;
                                result = 492;
                                str = "Failed to close File:" + filename + MediaMetrics.SEPARATOR;
                                Log.w(TAG, str, e);
                                return result;
                            }
                        }
                    } catch (IOException e6) {
                        result2 = 492;
                        Log.w(TAG, "Could not access File: " + filename + " .", e6);
                        if (rndAccessFile == null) {
                            return 492;
                        }
                        try {
                            rndAccessFile.close();
                            return 492;
                        } catch (IOException e7) {
                            e = e7;
                            result = 492;
                            str = "Failed to close File:" + filename + MediaMetrics.SEPARATOR;
                            Log.w(TAG, str, e);
                            return result;
                        }
                    }
                } catch (IllegalArgumentException e8) {
                    result2 = 492;
                    Log.w(TAG, "Could not open file in mode: rw", e8);
                    if (rndAccessFile == null) {
                        return 492;
                    }
                    try {
                        rndAccessFile.close();
                        return 492;
                    } catch (IOException e9) {
                        e = e9;
                        result = 492;
                        str = "Failed to close File:" + filename + MediaMetrics.SEPARATOR;
                        Log.w(TAG, str, e);
                        return result;
                    }
                }
            } finally {
            }
        } catch (IllegalStateException e10) {
            Log.w(
                    TAG,
                    "Could not close convertsession. Convertsession: " + this.mConvertSessionId,
                    e10);
            return result2;
        }
    }
}
