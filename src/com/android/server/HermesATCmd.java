package com.android.server;

import android.content.Context;
import android.util.Log;
import com.samsung.android.service.HermesService.HermesServiceManager;

/* loaded from: classes6.dex */
public class HermesATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_COMMAND_HERMES = "ISOSECHW";
    private static final int AT_LAST_INDEX = 3;
    private static final int AT_MAIN_INDEX = 0;
    private static final int AT_MAIN_INDEX_OPERATION = 0;
    private static final int AT_MAIN_INDEX_READ_DATA = 1;
    private static final int AT_MAIN_INDEX_SELFTEST_DATA = 0;
    private static final int AT_MAIN_INDEX_TEST_DATA = 9;
    private static final int AT_MAIN_INDEX_WRITE_DATA = 2;
    private static final int AT_MAIN_OPERATION = 0;
    private static final int AT_MAIN_READ_DATA = 10;
    private static final int AT_MAIN_SELFTEST_DATA = 0;
    private static final int AT_MAIN_TEST_DATA = 90;
    private static final int AT_MAIN_WRITE_DATA = 20;
    private static final int AT_MID_INDEX = 1;
    private static final int AT_MINOR_INDEX = 2;
    private static final String AT_RESPONSE_FAILED = "NG";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG(INVALID_PARAM)";
    private static final String AT_RESPONSE_NONE = "NONE";
    private static final String AT_RESPONSE_OK = "OK";
    private static final int NOT_PROVISIONED = 10000;
    private static final int NO_ERROR = 0;
    private static final int SAMSUNG_HERMES2_BIST_SPECIFIC = 3;
    private static final int SAMSUNG_HERMES_CLOSE = 91;
    private static final int SAMSUNG_HERMES_COS_PATCH = 93;
    private static final int SAMSUNG_HERMES_GET_APPLET_VERSION = 12;
    private static final int SAMSUNG_HERMES_GET_SECUREHW_INFO = 10;
    private static final int SAMSUNG_HERMES_GET_SEID = 94;
    private static final int SAMSUNG_HERMES_OPEN = 90;
    private static final int SAMSUNG_HERMES_PROVISIONING = 1;
    private static final int SAMSUNG_HERMES_SELFTEST = 0;
    private static final int SAMSUNG_HERMES_SEND_APDU = 92;
    private static final int SAMSUNG_HERMES_UPDATE_CRYPTO_FW = 2;
    private static final int SAMSUNG_HERMES_VERIFY_PROVISONING = 11;
    private static final String TAG = "HERMES#ATCmd";
    private Context mContext;
    private HermesServiceManager mHermesServiceManager;

    private HermesServiceManager bindHermesServiceManager() {
        if (this.mHermesServiceManager == null) {
            Log.i(TAG, "bindHermesServiceManager() is called.");
            this.mHermesServiceManager = new HermesServiceManager(this.mContext.getApplicationContext());
        }
        return this.mHermesServiceManager;
    }

    public HermesATCmd(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_HERMES;
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x02de A[Catch: Exception -> 0x0390, TryCatch #0 {Exception -> 0x0390, blocks: (B:8:0x0026, B:18:0x006a, B:19:0x037b, B:22:0x0080, B:24:0x008a, B:25:0x009f, B:26:0x00b0, B:28:0x00c0, B:29:0x00d8, B:30:0x00e9, B:32:0x00f9, B:33:0x010e, B:34:0x011f, B:36:0x0129, B:37:0x013a, B:38:0x014e, B:40:0x0158, B:41:0x0169, B:42:0x017d, B:44:0x0187, B:45:0x019f, B:46:0x01b0, B:48:0x01ba, B:51:0x01cf, B:52:0x01e2, B:53:0x02d4, B:55:0x02de, B:56:0x02f6, B:57:0x01f6, B:59:0x01fa, B:61:0x021a, B:63:0x022c, B:64:0x0246, B:65:0x025c, B:67:0x0264, B:69:0x026e, B:70:0x0286, B:71:0x0297, B:73:0x02a1, B:75:0x02ab, B:76:0x02c3, B:77:0x0307, B:79:0x0311, B:80:0x0321, B:81:0x0334, B:83:0x033c, B:85:0x0346, B:86:0x0357, B:87:0x0372), top: B:7:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x02f6 A[Catch: Exception -> 0x0390, TryCatch #0 {Exception -> 0x0390, blocks: (B:8:0x0026, B:18:0x006a, B:19:0x037b, B:22:0x0080, B:24:0x008a, B:25:0x009f, B:26:0x00b0, B:28:0x00c0, B:29:0x00d8, B:30:0x00e9, B:32:0x00f9, B:33:0x010e, B:34:0x011f, B:36:0x0129, B:37:0x013a, B:38:0x014e, B:40:0x0158, B:41:0x0169, B:42:0x017d, B:44:0x0187, B:45:0x019f, B:46:0x01b0, B:48:0x01ba, B:51:0x01cf, B:52:0x01e2, B:53:0x02d4, B:55:0x02de, B:56:0x02f6, B:57:0x01f6, B:59:0x01fa, B:61:0x021a, B:63:0x022c, B:64:0x0246, B:65:0x025c, B:67:0x0264, B:69:0x026e, B:70:0x0286, B:71:0x0297, B:73:0x02a1, B:75:0x02ab, B:76:0x02c3, B:77:0x0307, B:79:0x0311, B:80:0x0321, B:81:0x0334, B:83:0x033c, B:85:0x0346, B:86:0x0357, B:87:0x0372), top: B:7:0x0026 }] */
    @Override // com.android.server.IWorkOnAt
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String processCmd(java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.HermesATCmd.processCmd(java.lang.String):java.lang.String");
    }

    private String[] parsingParam(String str) {
        try {
            return str.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    private String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
