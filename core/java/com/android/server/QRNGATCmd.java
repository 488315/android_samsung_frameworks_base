package com.android.server;

import android.content.Context;
import android.util.Log;

/* loaded from: classes5.dex */
public class QRNGATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMAND_QRNG = "QRNGTEST";
    private static final int AT_MAIN_INDEX = 0;
    private static final int AT_MAIN_INDEX_OPERATION = 0;
    private static final int AT_MAIN_INDEX_READ_DATA = 1;
    private static final int AT_MAIN_INDEX_WRITE_DATA = 2;
    private static final int AT_MAIN_OPERATION = 0;
    private static final int AT_MAIN_READ_DATA = 10;
    private static final int AT_MAIN_WRITE_DATA = 20;
    private static final int AT_MID_INDEX = 1;
    private static final int AT_MINOR_INDEX = 2;
    private static final String AT_RESPONSE_INVALID_PARAM = "NG(INVALID_PARAM)";
    private static final String AT_RESPONSE_NOT_SUPPORT_QRNG = "NG(NO_SPT_QRNG)";
    private static final int IOCTL_ERR_FAULT = -14;
    private static final int IOCTL_ERR_INVAL = -22;
    private static final int IOCTL_ERR_TIMEDOUT = -110;
    private static final int NO_ERROR = 0;
    private static final int SAMSUNG_QRNG_DIAG = 0;
    private static final String TAG = "QRNG#StubATCmd";
    private Context mContext;

    public QRNGATCmd(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_QRNG;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String cmd) {
        String[] params = parsingParam(cmd);
        if (params == null) {
            Log.m96e(TAG, "QRNGTEST processCmd wrong param.");
            return AT_RESPONSE_INVALID_PARAM;
        }
        String result = new String(params[0] + ",");
        try {
            Log.m98i(TAG, "QRNGTEST ProcessCmd [" + cmd + "] start");
            int sw = Integer.parseInt(params[0] + params[1]);
            if (sw == 0) {
                result = result + AT_RESPONSE_NOT_SUPPORT_QRNG;
            } else {
                Log.m96e(TAG, "QRNGTEST ProcessCmd wrong command.");
                result = result + AT_RESPONSE_INVALID_PARAM;
            }
            Log.m98i(TAG, "QRNGTEST ProcessCmd [" + cmd + "] end");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result + AT_RESPONSE_INVALID_PARAM;
        }
    }

    private String[] parsingParam(String cmd) {
        try {
            String[] result = cmd.split(",");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
