package com.android.server;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemProperties;
import android.provider.Settings;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Slog;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class AuthUnlockATCmd implements IWorkOnAt {
    private static final String AT_COMMAND_FRPUNLCK = "FRPUNLCK";
    private static final String AT_COMMAND_HEADER = "AT";
    private static final String AT_RESPONSE_CONN_FAILED = "NG (FAILED CONNECTION)";
    private static final String AT_RESPONSE_END = "\r\n\r\nOK\r\n";
    private static final String AT_RESPONSE_INVALID_PARAM = "NG (INVALID_PARAM)";
    private static final String AT_RESPONSE_NA = "NA";
    private static final String AT_RESPONSE_NG_FAIL = "NG,NOK";
    private static final String AT_RESPONSE_NG_NOTOKEN = "NG,NO_TOK";
    private static final String AT_RESPONSE_OK = "OK";
    private static final String AT_RESPONSE_START = "\r\n";
    private static final int ERR_SERVICE_INTERNAL = 1;
    private static final int ERR_SERVICE_NOT_SUPPORTED = 0;
    private static final int MODE_RUN_UBIS_AGENT2_APP = 60;
    private static final String PERSISTENT_DATA_BLOCK_PROP = "ro.frp.pst";
    private static final String TAG = "AuthUnlockATCmd";
    private static final Object mLock = new Object();
    private Context mContext;
    private final String mDataBlockFile = SystemProperties.get(PERSISTENT_DATA_BLOCK_PROP);
    private EngineeringModeManager mEMMgr;
    private PersistentDataBlockManager mPDB;
    private int mServiceSupport;

    private native byte[] nativeSessionAccept(byte[] bArr);

    private native int nativeSessionComplete(byte[] bArr);

    private native int nativeWipe(String str);

    static {
        System.loadLibrary("frpunlock");
    }

    public AuthUnlockATCmd(Context context) {
        this.mContext = context;
        this.mEMMgr = new EngineeringModeManager(context.getApplicationContext());
    }

    @Override // com.android.server.IWorkOnAt
    public String getCmd() {
        return AT_COMMAND_FRPUNLCK;
    }

    @Override // com.android.server.IWorkOnAt
    public String processCmd(String str) {
        String str2;
        String str3;
        String str4;
        byte[] bArrNativeSessionAccept;
        String str5 = "";
        String[] strArrParsingParam = parsingParam(str);
        String[] strArr = {"1,0,", "1,1,", "3,0,0", "3,0,1"};
        if (strArrParsingParam == null) {
            return AT_RESPONSE_INVALID_PARAM;
        }
        PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) this.mContext.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
        this.mPDB = persistentDataBlockManager;
        if (persistentDataBlockManager == null) {
            return AT_RESPONSE_CONN_FAILED;
        }
        try {
            str5 = strArrParsingParam[0] + ",";
            String str6 = strArr[0];
            if (str6.equals(str.substring(0, str6.length()))) {
                byte[] bytes = strArrParsingParam[2].trim().getBytes(StandardCharsets.UTF_8);
                synchronized (mLock) {
                    try {
                        bArrNativeSessionAccept = nativeSessionAccept(bytes);
                    } catch (Exception unused) {
                        bArrNativeSessionAccept = null;
                    }
                }
                if (bArrNativeSessionAccept != null) {
                    return str5 + new String(bArrNativeSessionAccept, StandardCharsets.UTF_8);
                }
                return str5 + "NG(1)";
            }
            int iNativeSessionComplete = 1;
            String str7 = strArr[1];
            if (str7.equals(str.substring(0, str7.length()))) {
                byte[] bytes2 = strArrParsingParam[2].trim().getBytes(StandardCharsets.UTF_8);
                synchronized (mLock) {
                    try {
                        iNativeSessionComplete = nativeSessionComplete(bytes2);
                    } catch (Exception unused2) {
                    }
                }
                if (iNativeSessionComplete == 0) {
                    if (nativeWipe(this.mDataBlockFile) == 0) {
                        if (this.mPDB.deactivateFactoryResetProtection(new byte[32])) {
                            Slog.i(TAG, "FRP is deactivated!");
                        } else {
                            Slog.e(TAG, "FRP partition is wiped, but can't update the FRP status");
                        }
                        Settings.Secure.putInt(this.mContext.getContentResolver(), "secure_frp_mode", 0);
                        return str5 + "UNLOCK SUCCESS";
                    }
                    Slog.i(TAG, "FRP deactivating FAILED!");
                    return str5 + "NG(1)";
                }
                str3 = str5 + "NG(" + iNativeSessionComplete + NavigationBarInflaterView.KEY_CODE_END;
                Slog.i(TAG, "FRP Unlocking process FAILED.");
            } else {
                String str8 = strArr[2];
                if (str8.equals(str.substring(0, str8.length()))) {
                    if (this.mPDB.isFactoryResetProtectionActive()) {
                        return str5 + "LOCK";
                    }
                    return str5 + "UNLOCK";
                }
                String str9 = strArr[3];
                if (str9.equals(str.substring(0, str9.length()))) {
                    Slog.i(TAG, "AT+FRPUNLCK=3,0,1");
                    EngineeringModeManager engineeringModeManager = this.mEMMgr;
                    if (engineeringModeManager != null && engineeringModeManager.isConnected()) {
                        if (this.mEMMgr.getStatus(60) == 1) {
                            if (nativeWipe(this.mDataBlockFile) == 0) {
                                if (this.mPDB.deactivateFactoryResetProtection(new byte[32])) {
                                    Slog.i(TAG, "FRP is deactivated!");
                                    str4 = str5 + "OK";
                                } else {
                                    Slog.e(TAG, "FRP partition is wiped, but can't update the FRP status");
                                    str4 = str5 + AT_RESPONSE_NG_FAIL;
                                }
                                str3 = str4;
                                Settings.Secure.putInt(this.mContext.getContentResolver(), "secure_frp_mode", 0);
                                Slog.i(TAG, "AT+FRPUNLCK=3,0,1 is complete.");
                            } else {
                                Slog.i(TAG, "FRP deactivating FAILED!");
                                str2 = str5 + AT_RESPONSE_NG_FAIL;
                            }
                        } else {
                            str2 = str5 + AT_RESPONSE_NG_NOTOKEN;
                        }
                        str3 = str2;
                        Slog.i(TAG, "AT+FRPUNLCK=3,0,1 is complete.");
                    }
                    Slog.i(TAG, "Cannot connect to em service");
                    return AT_RESPONSE_NG_FAIL;
                }
                return str5 + AT_RESPONSE_INVALID_PARAM;
            }
            return str3;
        } catch (Exception e) {
            String str10 = str5 + AT_RESPONSE_INVALID_PARAM;
            e.printStackTrace();
            return str10;
        }
        String str102 = str5 + AT_RESPONSE_INVALID_PARAM;
        e.printStackTrace();
        return str102;
    }

    private String[] parsingParam(String str) {
        try {
            return str.substring(0, str.length()).split(",");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
