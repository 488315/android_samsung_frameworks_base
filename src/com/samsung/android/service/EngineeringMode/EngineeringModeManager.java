package com.samsung.android.service.EngineeringMode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.android.service.EngineeringMode.token.EngineeringModeToken;
import com.samsung.android.service.ProtectedATCommand.PACMError;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class EngineeringModeManager {
    public static final int ALLOWED = 1;
    public static final int DEV_OK = -16777064;
    public static final int DISABLE = 1;
    public static final int ENABLE = 0;
    public static final int ENG_KERNEL = 0;
    public static final String ERRORSTRING_EM_SERVICE = "ERROR_EM_SERVICE";
    public static final String ERRORSTRING_INTERNAL = "ERROR_INTERNAL";
    public static final String ERRORSTRING_NOT_INSTALLED = "ERROR_TOKEN_NOT_INSATLLED";
    public static final String ERRORSTRING_NO_PERMISSION = "ERROR_NO_PERMISSION";
    public static final int ERROR_COUNTER = -268435436;
    public static final int ERROR_EM_SERVICE = -1000;
    public static final int ERROR_INVALID_ESI = -1400;
    public static final int ERROR_INVALID_PARAM = -1700;
    public static final int ERROR_NOT_SUPPORTED = -1600;
    public static final int ERROR_NO_PERMISSION = -1300;
    public static final int ERROR_TUC_ZERO = -1500;
    public static final int MODE_CUST_KERNEL = 3;
    public static final int MODE_DEBUG_LOG = 2;
    public static final int MODE_ENG_KERNEL = 0;
    public static final int MODE_KNOX_TEST = 4;
    public static final int MODE_TEST_ENV = 1;
    public static final int MODE_USB_DEBUG = 1;
    public static final int NATIVE_SUCCESS = 0;
    public static final int NOK = 0;
    public static final int NOT_ALLOWED = 0;
    public static final int OK = 1;
    private static final String TAG = "engmode_java_manager";
    public static final int USB_DEBUG = 1;
    public static final int USB_DEBUG_ALLOWED = 1;
    public static final int USB_DEBUG_NOT_ALLOWED = 0;
    private int mCallerUid;
    private final Context mContext;
    private EngineeringModeNative mNative;
    private PackageManager mPkgMgr;
    private final String mPkgName;
    private int mSignature;
    public static final byte[] ERRORBYTE_EM_SERVICE = {-1};
    public static final byte[] ERRORBYTE_NO_PERMISSION = null;
    public static final byte[] ERRORBYTE_NOT_SUPPORTED = {-2};
    public static final byte[] ERRORBYTE_INVAILD_PARAM = {-3};
    public static final byte[] ERRORBYTE_NOT_INSATALLED = {-4};
    public static final int[] ERRORINTARR_INTERNAL = {PACMError.PAC_ERROR_COMMAND_NOT_FOUND};
    public static final int[] ERRORINTARR_EM_SERVICE = {PACMError.PAC_ERROR_NOT_PROTECTED_CMD};
    public static final int[] ERRORINTARR_NO_PERMISSION = {PACMError.PAC_ERROR_UNKNOWN_CMD};
    public static final int NATIVE_NO_PERMISSION = -268435452;
    public static final int[] ERRORINTARR_NOT_SUPPORTED = {NATIVE_NO_PERMISSION};
    public static final int[] ERRORINTARR_NOT_INSTALLED = {-268435451};

    private class EngineeringModeNative {
        private Context mClientContext;
        private boolean mSupportJNI;

        private native byte[] commandForESS(Context context, String str);

        private native String getExpiryDate(Context context);

        private native byte[] getID(Context context);

        private native int getNumOfModes(Context context);

        private native byte[] getRequestMsg(Context context, String str, String str2, byte[] bArr, int i);

        private native String getServerTime(Context context);

        private native int getStatus(Context context, int i);

        private native int getStatusWithSignature(int i, String str, int i2, int i3);

        private native int getTUC(Context context, int i);

        private native byte[] getToken(Context context);

        private native byte[] getTokenInfoForJanus(Context context, byte[] bArr);

        private native int installToken(Context context, byte[] bArr);

        private native int isTokenInstalled(Context context);

        private native byte[] makeITLReq(Context context, String str, String str2);

        private native byte[] makeTokenReq(Context context, String str, String str2, byte[] bArr, String str3);

        private native int recoveryITL(Context context, byte[] bArr);

        private native int removeToken(Context context);

        private native int sendFuseCmd(Context context);

        public EngineeringModeNative(Context context) {
            try {
                System.loadLibrary(".engmodejni.samsung");
                this.mClientContext = context;
                this.mSupportJNI = true;
                Log.i(EngineeringModeManager.TAG, "em library is enabled, will use library");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(EngineeringModeManager.TAG, "em library is disabled, will use service");
                this.mSupportJNI = false;
            }
        }

        public boolean isSupport() {
            return this.mSupportJNI;
        }

        public int _getStatus(int i, String str) {
            Log.i(EngineeringModeManager.TAG, str + NavigationBarInflaterView.KEY_CODE_START + EngineeringModeManager.this.mCallerUid + ", " + EngineeringModeManager.this.mSignature + ") call em(" + i + NavigationBarInflaterView.KEY_CODE_END);
            if (EngineeringModeManager.this.mSignature == 0) {
                return getStatusWithSignature(i, str, EngineeringModeManager.this.mCallerUid, EngineeringModeManager.this.mSignature);
            }
            return getStatus(this.mClientContext, i);
        }

        public byte[] _getRequestMsg(String str, String str2, byte[] bArr, int i) {
            return getRequestMsg(this.mClientContext, str, str2, bArr, i);
        }

        public int _installToken(byte[] bArr) {
            return installToken(this.mClientContext, bArr);
        }

        public int _isTokenInstalled() {
            return isTokenInstalled(this.mClientContext);
        }

        public int _removeToken() {
            return removeToken(this.mClientContext);
        }

        public byte[] _getID() {
            return getID(this.mClientContext);
        }

        public String _getExpiryDate() {
            return getExpiryDate(this.mClientContext);
        }

        public int _getNumOfModes() {
            return getNumOfModes(this.mClientContext);
        }

        public int _sendFuseCmd() {
            return sendFuseCmd(this.mClientContext);
        }

        public byte[] _makeITLReq(String str, String str2) {
            return makeITLReq(this.mClientContext, str, str2);
        }

        public int _recoveryITL(byte[] bArr) {
            return recoveryITL(this.mClientContext, bArr);
        }

        public byte[] _makeTokenReq(String str, String str2, byte[] bArr, String str3) {
            return makeTokenReq(this.mClientContext, str, str2, bArr, str3);
        }

        public byte[] _commandForESS(String str) {
            return commandForESS(this.mClientContext, str);
        }

        public byte[] _getToken() {
            return getToken(this.mClientContext);
        }

        public long _getServerTime() {
            String serverTime = getServerTime(this.mClientContext);
            if (serverTime != null) {
                try {
                    return new SimpleDateFormat("yyyyMMdd").parse(serverTime).getTime();
                } catch (Exception e) {
                    Log.i(EngineeringModeManager.TAG, "Failed to change time");
                    e.printStackTrace();
                }
            }
            return -1000L;
        }

        public int _getTUC(int i) {
            return getTUC(this.mClientContext, i);
        }

        public byte[] _makeTimeReq() {
            return getTokenInfoForJanus(this.mClientContext, "9,9,8".getBytes(Charset.forName("UTF-8")));
        }

        public byte[] _updateTime(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            try {
                byte[] bytes = "9,9,9,".getBytes(Charset.forName("UTF-8"));
                byte[] bArr2 = new byte[bytes.length + bArr.length];
                System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
                System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
                return getTokenInfoForJanus(this.mClientContext, bArr2);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public EngineeringModeManager(Context context) {
        this.mContext = context;
        String packageName = context.getPackageName();
        this.mPkgName = packageName;
        this.mNative = new EngineeringModeNative(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            this.mPkgMgr = packageManager;
            this.mCallerUid = packageManager.getApplicationInfo(packageName, 0).uid;
            this.mSignature = this.mPkgMgr.checkSignatures("android", packageName);
        } catch (Exception e) {
            this.mPkgMgr = null;
            this.mCallerUid = -1;
            this.mSignature = -1;
            Log.e(TAG, "PackageManager Exception occued");
            e.printStackTrace();
        }
        if (this.mNative.isSupport()) {
            Log.i(TAG, this.mPkgName + NavigationBarInflaterView.KEY_CODE_START + this.mCallerUid + ", " + this.mSignature + ") connects to EngineeringModeNative");
            return;
        }
        Log.e(TAG, this.mPkgName + NavigationBarInflaterView.KEY_CODE_START + this.mCallerUid + ", " + this.mSignature + ") can't be connect..");
    }

    public boolean isConnected() {
        return this.mNative.isSupport();
    }

    public int getStatus(int i) {
        Log.i(TAG, "getStatus() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getStatus(i, this.mPkgName);
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] getRequestMsg(String str, String str2, byte[] bArr) {
        Log.i(TAG, "getRequestMsg() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getRequestMsg(str, str2, bArr, 0);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] getRequestMsg(String str, String str2, byte[] bArr, int i) {
        Log.i(TAG, "getRequestMsg() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getRequestMsg(str, str2, bArr, i);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int installToken(byte[] bArr) {
        Log.i(TAG, "installToken() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._installToken(bArr);
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public int isTokenInstalled() {
        Log.i(TAG, "isTokenInstalled() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._isTokenInstalled();
            }
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public int removeToken() {
        Log.i(TAG, "removeToken() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._removeToken();
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] getID() {
        Log.i(TAG, "getID() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getID();
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public String getExpiryDate() {
        Log.i(TAG, "getExpiryDate() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getExpiryDate();
            }
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int getNumOfModes() {
        Log.i(TAG, "getNumOfModes() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getNumOfModes();
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public int sendFuseCmd() {
        Log.i(TAG, "sendFuseCmd() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._sendFuseCmd();
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service.");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] makeITLReq(String str, String str2) {
        Log.i(TAG, "makeITLReq() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._makeITLReq(str, str2);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int recoveryITL(byte[] bArr) {
        Log.i(TAG, "restoreITL() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._recoveryITL(bArr);
            }
            return -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] makeTokenReq(String str, String str2, byte[] bArr, String str3) {
        Log.i(TAG, "makeTokenReq() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._makeTokenReq(str, str2, bArr, str3);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeDelTokenForESS(String str) {
        Log.i(TAG, "makeDelTokenForESS() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeTokenReqForESS(String str) {
        Log.i(TAG, "makeTokenReqForESS() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeITLReqForESS(String str) {
        Log.i(TAG, "makeITLReqForESS is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int recoveryITLForESS(String str) {
        Log.i(TAG, "recoveryITLForESS() is called.");
        try {
            return this.mNative.isSupport() ? 0 : -1000;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] installTokenForESS(String str) {
        Log.i(TAG, "installTokenForESS() is called.");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] essCommand(String str) {
        Log.i(TAG, "essCommand is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._commandForESS(str);
            }
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public EngineeringModeToken getToken(int i, byte[] bArr) {
        Log.i(TAG, "getToken() is called");
        try {
            EmPacketManager emPacketManager = new EmPacketManager();
            if (i == 0) {
                return emPacketManager.parseToken(i, bArr);
            }
            if (i == 1) {
                if (this.mNative.isSupport()) {
                    return emPacketManager.parseToken(this.mNative._getToken());
                }
                return null;
            }
            if (i == 2) {
                return emPacketManager.parseToken(bArr);
            }
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public long getServerTime() {
        Log.i(TAG, "getServerTime() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getServerTime();
            }
            return -1000L;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return -1000L;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000L;
        }
    }

    public int getTUC(int i) {
        Log.i(TAG, "getTUC() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getTUC(i);
            }
            return 0;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return -1000;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1000;
        }
    }

    public byte[] setPriorityTime(String str) {
        Log.i(TAG, "setPriorityTime() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] getPriorityTime() {
        Log.i(TAG, "setPriorityTime() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int[] getModes() {
        Log.i(TAG, "getModes() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORINTARR_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORINTARR_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORINTARR_EM_SERVICE;
        }
    }

    public String getStringModes() {
        Log.i(TAG, "getStringModes() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORSTRING_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        }
    }

    public String getLastTokenStatus() {
        Log.i(TAG, "getLastTokenStatus() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORSTRING_EM_SERVICE;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        } catch (Exception e2) {
            e2.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        }
    }

    public byte[] makeTimeReq() {
        Log.i(TAG, "makeTimeReq() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._makeTimeReq();
            }
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public byte[] updateTime(byte[] bArr) {
        Log.i(TAG, "updateTime");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._updateTime(bArr);
            }
            return null;
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to connect service");
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static class EmPacketManager {
        private static final int EMP_2BYTES = 2;
        private static final int EMP_3BYTES = 3;
        private static final int EMP_4BYTES = 4;
        private static final int EMP_MAGIC_SIZE = 4;
        private static final int EMP_MAX_DEVICE_NUM = 500;
        private static final int EMP_MAX_MODE_DESC = 128;
        private static final int EMP_MAX_MODE_NAME = 32;
        private int headerLen;
        private int mOTPtime;
        private int mPos;
        private int mPosGroupDb;
        private int mPosIntegrityInfo;
        private int mPosIssuerInfo;
        private int mPosModeDb;
        private int mPosModeInfo;
        private int mPosTokenInfo;
        private int mPosValidityInfo;
        private EngineeringModeToken mToken;
        private int[] mPosDeviceInfo = new int[500];
        private int mNumOfDevice = 0;

        static class EmType {
            public static final int DEVI_DID = 2;
            public static final int DEVI_IMEI = 3;
            public static final int DEVI_MODEL_NAME = 1;
            public static final int GRDB_NO_DUPLICATE = 1;
            public static final int INTE_SERVER_CERT = 2;
            public static final int INTE_SIGNATURE = 1;
            public static final int ISSU_IP = 4098;
            public static final int ISSU_MAC = 4099;
            public static final int ISSU_NONCE = 3;
            public static final int ISSU_OTP = 2;
            public static final int ISSU_SINGLE_ID = 1;
            public static final int ISSU_SYSTEM_ID = 4097;
            public static final int MODB_DEVICE_INFO = 1;
            public static final int MODB_EXCLUSIVE = 5;
            public static final int MODB_HIDDEN = 2;
            public static final int MODB_MTUC = 3;
            public static final int MODB_MTUC_VALUE = 4;
            public static final int MODB_USED_ONCE = 6;
            public static final int TOKE_DEVICE_INFO = 2;
            public static final int TOKE_ID = 1;
            public static final int TOKE_NUM_DEVICES = 3;
            public static final int VALI_EXPIRY_DATE = 2;
            public static final int VALI_ISSUED_DATE = 1;

            EmType() {
            }
        }

        EmPacketManager() {
            this.mToken = null;
            this.mToken = new EngineeringModeToken();
        }

        private String getStringFromBytes(byte[] bArr) {
            try {
                return new String(bArr, "UTF-8");
            } catch (UnsupportedEncodingException unused) {
                return null;
            }
        }

        private byte[] getBytes(byte[] bArr, int i, int i2) {
            return Arrays.copyOfRange(bArr, i, i2 + i);
        }

        private int getInt(byte[] bArr, int i) {
            return ByteBuffer.wrap(bArr, i, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }

        private int getShort(byte[] bArr, int i) {
            return ByteBuffer.wrap(bArr, i, 2).order(ByteOrder.LITTLE_ENDIAN).getShort() & 65535;
        }

        private String byteArrayToHex(byte[] bArr) {
            StringBuilder sb = new StringBuilder(bArr.length * 2);
            for (byte b : bArr) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString();
        }

        public EngineeringModeToken parseToken(byte[] bArr) {
            this.mPos = 0;
            if (bArr == null) {
                Log.e(EngineeringModeManager.TAG, "Error Invalid Argument");
                return null;
            }
            String str = new String(bArr, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            if (!str.equals("ENG")) {
                Log.e(EngineeringModeManager.TAG, "Error prefix");
                return null;
            }
            String str2 = new String(bArr, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            String str3 = new String(bArr, this.mPos, 4, Charset.forName("UTF-8"));
            this.mPos += 4;
            this.mToken.setPrefix(str);
            this.mToken.setType(str2);
            this.mToken.setVersion(str3);
            Log.d(EngineeringModeManager.TAG, "Prefix : " + str + ", Type : " + str2 + ", Version : " + str3);
            this.headerLen = getInt(bArr, this.mPos);
            int i = this.mPos + 4;
            this.mPos = i;
            int i2 = getInt(bArr, i);
            this.mPosTokenInfo = i2;
            this.mPos = this.mPos + 4;
            if (parseTokenInfo(bArr, i2) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseTokenInfo");
                return null;
            }
            Log.d(EngineeringModeManager.TAG, "headerLen : " + this.headerLen);
            for (int i3 = 0; i3 < this.mNumOfDevice; i3++) {
                this.mPosDeviceInfo[i3] = getInt(bArr, this.mPos);
                this.mPos += 4;
                if (parseDeviceInfo(bArr, this.mPosDeviceInfo[i3]) < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error parseDeviceInfo " + i3);
                    return null;
                }
            }
            int i4 = getInt(bArr, this.mPos);
            this.mPosIssuerInfo = i4;
            this.mPos += 4;
            if (parseIssuerInfo(bArr, i4) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseIssuerInfo");
                return null;
            }
            int i5 = getInt(bArr, this.mPos);
            this.mPosModeInfo = i5;
            this.mPos += 4;
            if (parseModeInfo(bArr, i5) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeInfo");
                return null;
            }
            int i6 = getInt(bArr, this.mPos);
            this.mPosValidityInfo = i6;
            this.mPos += 4;
            if (parseValidityInfo(bArr, i6) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseValidityInfo");
                return null;
            }
            int i7 = getInt(bArr, this.mPos);
            this.mPosIntegrityInfo = i7;
            this.mPos += 4;
            if (parseIntegrityInfo(bArr, i7) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseIntegrityInfo");
                return null;
            }
            int i8 = getInt(bArr, this.mPos);
            this.mPosModeDb = i8;
            this.mPos += 4;
            if (parseModeDb(bArr, i8) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeDB");
                return null;
            }
            int i9 = getInt(bArr, this.mPos);
            this.mPosGroupDb = i9;
            this.mPos += 4;
            if (parseGroupDb(bArr, i9) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupDB");
                return null;
            }
            return this.mToken;
        }

        public EngineeringModeToken parseToken(int i, byte[] bArr) {
            this.mPos = 0;
            if (bArr == null) {
                Log.e(EngineeringModeManager.TAG, "Error Invalid Argument");
                return null;
            }
            String str = new String(bArr, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            if (!str.equals("ENG")) {
                Log.e(EngineeringModeManager.TAG, "Error prefix");
                return null;
            }
            String str2 = new String(bArr, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            String str3 = new String(bArr, this.mPos, 4, Charset.forName("UTF-8"));
            this.mPos += 4;
            this.mToken.setPrefix(str);
            this.mToken.setType(str2);
            this.mToken.setVersion(str3);
            Log.d(EngineeringModeManager.TAG, "Prefix : " + str + ", Type : " + str2 + ", Version : " + str3);
            if (parseModeDb(bArr, this.mPos) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeDB");
                return null;
            }
            int i2 = this.mPos + 4;
            this.mPos = i2;
            int i3 = getInt(bArr, i2);
            int i4 = this.mPos + 4;
            this.mPos = i4;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return null;
            }
            this.mPos = i4 + i3 + 4;
            Log.d(EngineeringModeManager.TAG, "Pos Offset : " + this.mPos);
            if (parseGroupDb(bArr, this.mPos) < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupDB");
                return null;
            }
            int i5 = this.mPos + 4;
            this.mPos = i5;
            int i6 = getInt(bArr, i5);
            int i7 = this.mPos + 4;
            this.mPos = i7;
            if (i6 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return null;
            }
            int i8 = i7 + i6 + 4;
            this.mPos = i8;
            parseOTPtime(bArr, i8);
            return this.mToken;
        }

        private int parseTokenInfo(byte[] bArr, int i) {
            int i2 = 4;
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i3 = i + 4;
            if (!str.equals("TOKE")) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo magic");
                return -1;
            }
            int i4 = getInt(bArr, i3);
            int i5 = i + 8;
            if (i4 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo sizeOfInfo");
                return -1;
            }
            int i6 = getInt(bArr, i5);
            int i7 = i + 12;
            if (i6 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Token Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i4);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i6);
            Log.d(EngineeringModeManager.TAG, "");
            int i8 = 1;
            while (i8 <= i6) {
                int i9 = getShort(bArr, i7);
                int i10 = getShort(bArr, i7 + 2);
                int i11 = i7 + i2;
                if (i10 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error tokenInfo item len : type : " + i9);
                    return -1;
                }
                if (i9 != 1 && i9 != 2 && i9 != 3) {
                    Log.e(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i9);
                }
                byte[] bytes = getBytes(bArr, i11, i10);
                i7 = i11 + i10;
                this.mToken.pushTokenInfo(i9, i10, bytes);
                if (i9 == 3) {
                    this.mNumOfDevice = getShort(bytes, 0);
                }
                if (i9 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : TokenID, len : " + i10);
                    if (i10 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i9 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Device Unique Info, len : " + i10);
                    if (i10 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(bytes, 0));
                    }
                } else if (i9 == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : Number Of Device Info, len : " + i10);
                    if (i10 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(bytes, 0));
                    }
                }
                i8++;
                i2 = 4;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseDeviceInfo(byte[] bArr, int i) {
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i2 = i + 4;
            if (!str.equals("DEVI")) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo magic");
                return -1;
            }
            int i3 = getInt(bArr, i2);
            int i4 = i + 8;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo sizeOfInfo");
                return -1;
            }
            int i5 = getInt(bArr, i4);
            int i6 = i + 12;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Device Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i7 = 1; i7 <= i5; i7++) {
                int i8 = getShort(bArr, i6);
                int i9 = getShort(bArr, i6 + 2);
                int i10 = i6 + 4;
                if (i9 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error deviceInfo item len : type : " + i8);
                    return -1;
                }
                if (i8 != 1 && i8 != 2 && i8 != 3) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i8);
                }
                byte[] bytes = getBytes(bArr, i10, i9);
                i6 = i10 + i9;
                this.mToken.pushDeviceInfo(i8, i9, bytes);
                if (i8 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Model Name, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i8 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : DID, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i8 == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : IMEI, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseIssuerInfo(byte[] bArr, int i) {
            int i2 = 4;
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i3 = i + 4;
            int i4 = -1;
            if (!str.equals("ISSU")) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo magic");
                return -1;
            }
            int i5 = getInt(bArr, i3);
            int i6 = i + 8;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo sizeOfInfo");
                return -1;
            }
            int i7 = getInt(bArr, i6);
            int i8 = i + 12;
            if (i7 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Issuer Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i5);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i7);
            Log.d(EngineeringModeManager.TAG, "");
            int i9 = 1;
            while (i9 <= i7) {
                int i10 = getShort(bArr, i8);
                int i11 = getShort(bArr, i8 + 2);
                int i12 = i8 + i2;
                if (i11 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error issuerInfo item len : type : " + i10);
                    return i4;
                }
                if (i10 != 1 && i10 != 2 && i10 != 3 && i10 != 4097 && i10 != 4098 && i10 != 4099) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i10);
                }
                byte[] bytes = getBytes(bArr, i12, i11);
                i8 = i12 + i11;
                this.mToken.pushIssuerInfo(i10, i11, bytes);
                if (i10 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Single ID, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i10 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : OTP, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i10 == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : Nonce, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i10 == 4097) {
                    Log.d(EngineeringModeManager.TAG, "type : System ID, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i10 == 4098) {
                    Log.d(EngineeringModeManager.TAG, "type : IP, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i10 == 4099) {
                    Log.d(EngineeringModeManager.TAG, "type : MAC, len : " + i11);
                    if (i11 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                }
                i9++;
                i2 = 4;
                i4 = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseModeInfo(byte[] bArr, int i) {
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i2 = i + 4;
            if (!str.equals("MODE")) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo magic");
                return -1;
            }
            int i3 = getInt(bArr, i2);
            int i4 = i + 8;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo sizeOfInfo");
                return -1;
            }
            int i5 = getInt(bArr, i4);
            int i6 = i + 12;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i7 = 1; i7 <= i5; i7++) {
                int i8 = getShort(bArr, i6);
                int i9 = getShort(bArr, i6 + 2);
                i6 += 4;
                if (i9 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error modeInfo item len : type : " + i8);
                    return -1;
                }
                this.mToken.pushModeInfo(i8, i9, null);
                Log.d(EngineeringModeManager.TAG, "Mode " + i7 + " -> " + i8);
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseValidityInfo(byte[] bArr, int i) {
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i2 = i + 4;
            if (!str.equals("VALI")) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo magic");
                return -1;
            }
            int i3 = getInt(bArr, i2);
            int i4 = i + 8;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo sizeOfInfo");
                return -1;
            }
            int i5 = getInt(bArr, i4);
            int i6 = i + 12;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Validity Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i7 = 1; i7 <= i5; i7++) {
                int i8 = getShort(bArr, i6);
                int i9 = getShort(bArr, i6 + 2);
                int i10 = i6 + 4;
                if (i9 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error validityInfo item len : type : " + i8);
                    return -1;
                }
                if (i8 != 1 && i8 != 2) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i8);
                }
                byte[] bytes = getBytes(bArr, i10, i9);
                i6 = i10 + i9;
                this.mToken.pushValidityInfo(i8, i9, bytes);
                if (i8 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Issued Date, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                } else if (i8 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Expiry Date, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(bytes));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseIntegrityInfo(byte[] bArr, int i) {
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i2 = i + 4;
            if (!str.equals("INTE")) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo magic");
                return -1;
            }
            int i3 = getInt(bArr, i2);
            int i4 = i + 8;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo sizeOfInfo");
                return -1;
            }
            int i5 = getInt(bArr, i4);
            int i6 = i + 12;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Integrity Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i7 = 1; i7 <= i5; i7++) {
                int i8 = getShort(bArr, i6);
                int i9 = getShort(bArr, i6 + 2);
                int i10 = i6 + 4;
                if (i9 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error integInfo item len : type : " + i8);
                    return -1;
                }
                if (i8 != 1 && i8 != 2) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i8);
                }
                byte[] bytes = getBytes(bArr, i10, i9);
                i6 = i10 + i9;
                this.mToken.pushIntegrityInfo(i8, i9, bytes);
                if (i8 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Signature, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i8 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Server Cert, len : " + i9);
                    if (i9 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseModeData(byte[] bArr, int i) {
            int i2 = getShort(bArr, i);
            int i3 = i + 2;
            int i4 = -1;
            if (i2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData modeIndex");
                return -1;
            }
            String str = new String(bArr, i3, 32, Charset.forName("UTF-8"));
            String str2 = new String(bArr, i + 34, 128, Charset.forName("UTF-8"));
            int i5 = getShort(bArr, i + 162);
            int i6 = i + 164;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData groupIndex");
                return -1;
            }
            int i7 = getInt(bArr, i6);
            int i8 = i + 168;
            if (i7 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData sizeOfAttrInfo");
                return -1;
            }
            int i9 = getInt(bArr, i8);
            int i10 = i + 172;
            if (i9 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData numOfAttr");
                return -1;
            }
            this.mToken.pushModeDB(i2, str, str2, i5);
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode DB Attr]");
            Log.d(EngineeringModeManager.TAG, "modeIndex : " + i2);
            Log.d(EngineeringModeManager.TAG, "modeName : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "modeDesc : ".concat(str2));
            Log.d(EngineeringModeManager.TAG, "groupIndex : " + i5);
            Log.d(EngineeringModeManager.TAG, "sizeOfAttrInfo : " + i7);
            Log.d(EngineeringModeManager.TAG, "numOfAttr : " + i9);
            Log.d(EngineeringModeManager.TAG, "");
            int i11 = 1;
            while (i11 <= i9) {
                int i12 = getShort(bArr, i10);
                int i13 = getShort(bArr, i10 + 2);
                int i14 = i10 + 4;
                if (i13 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error modeData item len : type : " + i12);
                    return i4;
                }
                if (i12 != 1 && i12 != 2 && i12 != 3 && i12 != 4 && i12 != 5 && i12 != 6) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i12);
                }
                byte[] bytes = getBytes(bArr, i14, i13);
                i10 = i14 + i13;
                this.mToken.pushAttrToModeItem(i2, i12, i13, bytes);
                if (i12 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Device Unique Info, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(bytes, 0));
                    }
                } else if (i12 == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Hidden, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i12 == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : MTUC, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i12 == 4) {
                    Log.d(EngineeringModeManager.TAG, "type : MTUC Value, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getInt(bytes, 0));
                    }
                } else if (i12 == 5) {
                    Log.d(EngineeringModeManager.TAG, "type : Exclusive, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                } else if (i12 == 6) {
                    Log.d(EngineeringModeManager.TAG, "type : Used Once, len : " + i13);
                    if (i13 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                }
                i11++;
                i4 = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return i10;
        }

        private int parseModeDb(byte[] bArr, int i) {
            String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
            int i2 = i + 4;
            if (!str.equals("MODB")) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB magic");
                return -1;
            }
            int i3 = getInt(bArr, i2);
            int i4 = i + 8;
            if (i3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return -1;
            }
            int i5 = getInt(bArr, i4);
            int i6 = i + 12;
            if (i5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode DB]");
            Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i7 = 0; i7 < i5; i7++) {
                i6 = parseModeData(bArr, i6);
                if (i6 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error parseModeData : " + i7);
                    return -1;
                }
            }
            return 0;
        }

        private int parseGroupData(byte[] bArr, int i) {
            int i2 = getShort(bArr, i);
            int i3 = i + 2;
            if (i2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData groupIndex");
                return -1;
            }
            String str = new String(bArr, i3, 32, Charset.forName("UTF-8"));
            String str2 = new String(bArr, i + 34, 128, Charset.forName("UTF-8"));
            int i4 = getInt(bArr, i + 162);
            int i5 = i + 166;
            if (i4 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData sizeOfAttrInfo");
                return -1;
            }
            int i6 = getInt(bArr, i5);
            int i7 = i + 170;
            if (i6 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData numOfAttr");
                return -1;
            }
            this.mToken.pushGroupDB(i2, str, str2);
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Groupe DB Attr]");
            Log.d(EngineeringModeManager.TAG, "groupIndex : " + i2);
            Log.d(EngineeringModeManager.TAG, "groupName : ".concat(str));
            Log.d(EngineeringModeManager.TAG, "groupDesc : ".concat(str2));
            Log.d(EngineeringModeManager.TAG, "sizeOfAttrInfo : " + i4);
            Log.d(EngineeringModeManager.TAG, "numOfAttr : " + i6);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i8 = 1; i8 <= i6; i8++) {
                int i9 = getShort(bArr, i7);
                int i10 = getShort(bArr, i7 + 2);
                int i11 = i7 + 4;
                if (i10 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupData item len : type : " + i9);
                    return -1;
                }
                if (i9 != 1) {
                    Log.d(EngineeringModeManager.TAG, "Unregistered type but it can be new one : " + i9);
                }
                byte[] bytes = getBytes(bArr, i11, i10);
                i7 = i11 + i10;
                this.mToken.pushAttrToGroupItem(i2, i9, i10, bytes);
                if (i9 == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : No Duplicate, len : " + i10);
                    if (i10 > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(bytes));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return i7;
        }

        private int parseGroupDb(byte[] bArr, int i) {
            Log.d(EngineeringModeManager.TAG, "Enter praseGroupDb");
            Log.d(EngineeringModeManager.TAG, "Buf Lengh : " + bArr.length);
            Log.d(EngineeringModeManager.TAG, "POS Offset : " + i);
            try {
                String str = new String(bArr, i, 4, Charset.forName("UTF-8"));
                int i2 = i + 4;
                Log.d(EngineeringModeManager.TAG, "magic : ".concat(str));
                if (!str.equals("GRDB")) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB magic");
                    return -1;
                }
                int i3 = getInt(bArr, i2);
                int i4 = i + 8;
                if (i3 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB sizeOfInfo");
                    return -1;
                }
                int i5 = getInt(bArr, i4);
                int i6 = i + 12;
                if (i5 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB numOfData");
                    return -1;
                }
                Log.d(EngineeringModeManager.TAG, "");
                Log.d(EngineeringModeManager.TAG, "[Group DB]");
                Log.d(EngineeringModeManager.TAG, "Magic : ".concat(str));
                Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + i3);
                Log.d(EngineeringModeManager.TAG, "numOfdata : " + i5);
                Log.d(EngineeringModeManager.TAG, "");
                for (int i7 = 0; i7 < i5; i7++) {
                    i6 = parseGroupData(bArr, i6);
                    if (i6 < 0) {
                        Log.e(EngineeringModeManager.TAG, "Error parseGroupData : " + i7);
                        return -1;
                    }
                }
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private int parseOTPtime(byte[] bArr, int i) {
            int i2 = getInt(bArr, i);
            this.mOTPtime = i2;
            if (i2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error OTP remain time");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "OTP Time : " + this.mOTPtime);
            this.mToken.pushOTPTime(this.mOTPtime);
            return 0;
        }
    }
}
