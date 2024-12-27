package com.samsung.android.service.EngineeringMode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.service.EngineeringMode.token.EngineeringModeToken;
import com.samsung.android.service.ProtectedATCommand.PACMError;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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

        private native byte[] getRequestMsg(
                Context context, String str, String str2, byte[] bArr, int i);

        private native String getServerTime(Context context);

        private native int getStatus(Context context, int i);

        private native int getStatusWithSignature(int i, String str, int i2, int i3);

        private native int getTUC(Context context, int i);

        private native byte[] getToken(Context context);

        private native byte[] getTokenInfoForJanus(Context context, byte[] bArr);

        private native int installToken(Context context, byte[] bArr);

        private native int isTokenInstalled(Context context);

        private native byte[] makeITLReq(Context context, String str, String str2);

        private native byte[] makeTokenReq(
                Context context, String str, String str2, byte[] bArr, String str3);

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

        public int _getStatus(int mode, String packageName) {
            Log.i(
                    EngineeringModeManager.TAG,
                    packageName
                            + NavigationBarInflaterView.KEY_CODE_START
                            + EngineeringModeManager.this.mCallerUid
                            + ", "
                            + EngineeringModeManager.this.mSignature
                            + ") call em("
                            + mode
                            + NavigationBarInflaterView.KEY_CODE_END);
            if (EngineeringModeManager.this.mSignature == 0) {
                return getStatusWithSignature(
                        mode,
                        packageName,
                        EngineeringModeManager.this.mCallerUid,
                        EngineeringModeManager.this.mSignature);
            }
            return getStatus(this.mClientContext, mode);
        }

        public byte[] _getRequestMsg(String singleId, String otp, byte[] mode, int cnt) {
            return getRequestMsg(this.mClientContext, singleId, otp, mode, cnt);
        }

        public int _installToken(byte[] token) {
            return installToken(this.mClientContext, token);
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

        public byte[] _makeITLReq(String singleId, String otp) {
            return makeITLReq(this.mClientContext, singleId, otp);
        }

        public int _recoveryITL(byte[] recoveryMsg) {
            return recoveryITL(this.mClientContext, recoveryMsg);
        }

        public byte[] _makeTokenReq(
                String singleId, String otp, byte[] modeSet, String expiryDate) {
            return makeTokenReq(this.mClientContext, singleId, otp, modeSet, expiryDate);
        }

        public byte[] _commandForESS(String cmd) {
            return commandForESS(this.mClientContext, cmd);
        }

        public byte[] _getToken() {
            return getToken(this.mClientContext);
        }

        public long _getServerTime() {
            String stringTime = getServerTime(this.mClientContext);
            if (stringTime == null) {
                return -1000L;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date date = sdf.parse(stringTime);
                long returnTime = date.getTime();
                return returnTime;
            } catch (Exception e) {
                Log.i(EngineeringModeManager.TAG, "Failed to change time");
                e.printStackTrace();
                return -1000L;
            }
        }

        public int _getTUC(int mode) {
            return getTUC(this.mClientContext, mode);
        }

        public byte[] _makeTimeReq() {
            return getTokenInfoForJanus(
                    this.mClientContext, "9,9,8".getBytes(Charset.forName("UTF-8")));
        }

        public byte[] _updateTime(byte[] resTime) {
            if (resTime == null) {
                return null;
            }
            try {
                byte[] prefix = "9,9,9,".getBytes(Charset.forName("UTF-8"));
                byte[] command = new byte[prefix.length + resTime.length];
                System.arraycopy(prefix, 0, command, 0, prefix.length);
                System.arraycopy(resTime, 0, command, prefix.length, resTime.length);
                return getTokenInfoForJanus(this.mClientContext, command);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public EngineeringModeManager(Context context) {
        this.mContext = context;
        this.mPkgName = this.mContext.getPackageName();
        this.mNative = new EngineeringModeNative(context);
        try {
            this.mPkgMgr = this.mContext.getPackageManager();
            this.mCallerUid = this.mPkgMgr.getApplicationInfo(this.mPkgName, 0).uid;
            this.mSignature = this.mPkgMgr.checkSignatures("android", this.mPkgName);
        } catch (Exception e) {
            this.mPkgMgr = null;
            this.mCallerUid = -1;
            this.mSignature = -1;
            Log.e(TAG, "PackageManager Exception occued");
            e.printStackTrace();
        }
        if (this.mNative.isSupport()) {
            Log.i(
                    TAG,
                    this.mPkgName
                            + NavigationBarInflaterView.KEY_CODE_START
                            + this.mCallerUid
                            + ", "
                            + this.mSignature
                            + ") connects to EngineeringModeNative");
        } else {
            Log.e(
                    TAG,
                    this.mPkgName
                            + NavigationBarInflaterView.KEY_CODE_START
                            + this.mCallerUid
                            + ", "
                            + this.mSignature
                            + ") can't be connect..");
        }
    }

    public boolean isConnected() {
        if (this.mNative.isSupport()) {
            return true;
        }
        return false;
    }

    public int getStatus(int mode) {
        Log.i(TAG, "getStatus() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._getStatus(mode, this.mPkgName);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public byte[] getRequestMsg(String singleID, String OTP, byte[] modeSet) {
        Log.i(TAG, "getRequestMsg() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getRequestMsg(singleID, OTP, modeSet, 0);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] getRequestMsg(String singleID, String OTP, byte[] modeSet, int validityCount) {
        Log.i(TAG, "getRequestMsg() is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getRequestMsg(singleID, OTP, modeSet, validityCount);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int installToken(byte[] token) {
        Log.i(TAG, "installToken() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._installToken(token);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public int isTokenInstalled() {
        Log.i(TAG, "isTokenInstalled() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._isTokenInstalled();
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public int removeToken() {
        Log.i(TAG, "removeToken() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._removeToken();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public String getExpiryDate() {
        Log.i(TAG, "getExpiryDate() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return null;
            }
            return this.mNative._getExpiryDate();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNumOfModes() {
        Log.i(TAG, "getNumOfModes() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._getNumOfModes();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public int sendFuseCmd() {
        Log.i(TAG, "sendFuseCmd() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._sendFuseCmd();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service.");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public byte[] makeITLReq(String singleID, String OTP) {
        Log.i(TAG, "makeITLReq() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._makeITLReq(singleID, OTP);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int recoveryITL(byte[] recoveryMsg) {
        Log.i(TAG, "restoreITL() is called");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return this.mNative._recoveryITL(recoveryMsg);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public byte[] makeTokenReq(String singleID, String OTP, byte[] modeSet, String expiryDate) {
        Log.i(TAG, "makeTokenReq() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._makeTokenReq(singleID, OTP, modeSet, expiryDate);
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeDelTokenForESS(String cmd) {
        Log.i(TAG, "makeDelTokenForESS() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeTokenReqForESS(String cmd) {
        Log.i(TAG, "makeTokenReqForESS() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] makeITLReqForESS(String cmd) {
        Log.i(TAG, "makeITLReqForESS is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public int recoveryITLForESS(String cmd) {
        Log.i(TAG, "recoveryITLForESS() is called.");
        try {
            if (!this.mNative.isSupport()) {
                return -1000;
            }
            return 0;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public byte[] installTokenForESS(String cmd) {
        Log.i(TAG, "installTokenForESS() is called.");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public byte[] essCommand(String cmd) {
        Log.i(TAG, "essCommand is called.");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._commandForESS(cmd);
            }
            return null;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        }
    }

    public EngineeringModeToken getToken(int type, byte[] bytes) {
        Log.i(TAG, "getToken() is called");
        try {
            EmPacketManager epm = new EmPacketManager();
            if (type == 0) {
                return epm.parseToken(type, bytes);
            }
            if (type == 1) {
                if (!this.mNative.isSupport()) {
                    return null;
                }
                byte[] token = this.mNative._getToken();
                return epm.parseToken(token);
            }
            if (type != 2) {
                return null;
            }
            return epm.parseToken(bytes);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getServerTime() {
        Log.i(TAG, "getServerTime() is called");
        try {
            if (!this.mNative.isSupport()) {
                return -1000L;
            }
            return this.mNative._getServerTime();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return -1000L;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000L;
        }
    }

    public int getTUC(int mode) {
        Log.i(TAG, "getTUC() is called");
        try {
            if (this.mNative.isSupport()) {
                return this.mNative._getTUC(mode);
            }
            return 0;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return -1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1000;
        }
    }

    public byte[] setPriorityTime(String time) {
        Log.i(TAG, "setPriorityTime() is called");
        try {
            if (this.mNative.isSupport()) {
                return null;
            }
            return ERRORBYTE_EM_SERVICE;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORBYTE_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORINTARR_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORINTARR_EM_SERVICE;
        }
    }

    public String getStringModes() {
        Log.i(TAG, "getStringModes() is called");
        try {
            if (!this.mNative.isSupport()) {
                return ERRORSTRING_EM_SERVICE;
            }
            return null;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        }
    }

    public String getLastTokenStatus() {
        Log.i(TAG, "getLastTokenStatus() is called");
        try {
            if (!this.mNative.isSupport()) {
                return ERRORSTRING_EM_SERVICE;
            }
            return null;
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        } catch (Exception e) {
            e.printStackTrace();
            return ERRORSTRING_EM_SERVICE;
        }
    }

    public byte[] makeTimeReq() {
        Log.i(TAG, "makeTimeReq() is called");
        try {
            if (!this.mNative.isSupport()) {
                return null;
            }
            return this.mNative._makeTimeReq();
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] updateTime(byte[] resTime) {
        Log.i(TAG, "updateTime");
        try {
            if (!this.mNative.isSupport()) {
                return null;
            }
            return this.mNative._updateTime(resTime);
        } catch (NullPointerException npe) {
            Log.e(TAG, "Failed to connect service");
            npe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
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

            EmType() {}
        }

        EmPacketManager() {
            this.mToken = null;
            this.mToken = new EngineeringModeToken();
        }

        private String getStringFromBytes(byte[] in) {
            try {
                String out = new String(in, "UTF-8");
                return out;
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        private byte[] getBytes(byte[] msg, int pos, int length) {
            return Arrays.copyOfRange(msg, pos, pos + length);
        }

        private int getInt(byte[] msg, int pos) {
            return ByteBuffer.wrap(msg, pos, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
        }

        private int getShort(byte[] msg, int pos) {
            return ByteBuffer.wrap(msg, pos, 2).order(ByteOrder.LITTLE_ENDIAN).getShort() & 65535;
        }

        private String byteArrayToHex(byte[] input) {
            StringBuilder sb = new StringBuilder(input.length * 2);
            for (byte b : input) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString();
        }

        public EngineeringModeToken parseToken(byte[] buf) {
            this.mPos = 0;
            if (buf == null) {
                Log.e(EngineeringModeManager.TAG, "Error Invalid Argument");
                return null;
            }
            String prefix = new String(buf, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            if (!prefix.equals("ENG")) {
                Log.e(EngineeringModeManager.TAG, "Error prefix");
                return null;
            }
            String type = new String(buf, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            String version = new String(buf, this.mPos, 4, Charset.forName("UTF-8"));
            this.mPos += 4;
            this.mToken.setPrefix(prefix);
            this.mToken.setType(type);
            this.mToken.setVersion(version);
            Log.d(
                    EngineeringModeManager.TAG,
                    "Prefix : " + prefix + ", Type : " + type + ", Version : " + version);
            this.headerLen = getInt(buf, this.mPos);
            this.mPos += 4;
            this.mPosTokenInfo = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret = parseTokenInfo(buf, this.mPosTokenInfo);
            if (ret < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseTokenInfo");
                return null;
            }
            Log.d(EngineeringModeManager.TAG, "headerLen : " + this.headerLen);
            for (int i = 0; i < this.mNumOfDevice; i++) {
                this.mPosDeviceInfo[i] = getInt(buf, this.mPos);
                this.mPos += 4;
                int ret2 = parseDeviceInfo(buf, this.mPosDeviceInfo[i]);
                if (ret2 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error parseDeviceInfo " + i);
                    return null;
                }
            }
            int i2 = this.mPos;
            this.mPosIssuerInfo = getInt(buf, i2);
            this.mPos += 4;
            int ret3 = parseIssuerInfo(buf, this.mPosIssuerInfo);
            if (ret3 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseIssuerInfo");
                return null;
            }
            this.mPosModeInfo = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret4 = parseModeInfo(buf, this.mPosModeInfo);
            if (ret4 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeInfo");
                return null;
            }
            this.mPosValidityInfo = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret5 = parseValidityInfo(buf, this.mPosValidityInfo);
            if (ret5 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseValidityInfo");
                return null;
            }
            this.mPosIntegrityInfo = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret6 = parseIntegrityInfo(buf, this.mPosIntegrityInfo);
            if (ret6 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseIntegrityInfo");
                return null;
            }
            this.mPosModeDb = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret7 = parseModeDb(buf, this.mPosModeDb);
            if (ret7 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeDB");
                return null;
            }
            this.mPosGroupDb = getInt(buf, this.mPos);
            this.mPos += 4;
            int ret8 = parseGroupDb(buf, this.mPosGroupDb);
            if (ret8 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupDB");
                return null;
            }
            return this.mToken;
        }

        public EngineeringModeToken parseToken(int type_, byte[] buf) {
            this.mPos = 0;
            if (buf == null) {
                Log.e(EngineeringModeManager.TAG, "Error Invalid Argument");
                return null;
            }
            String prefix = new String(buf, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            if (!prefix.equals("ENG")) {
                Log.e(EngineeringModeManager.TAG, "Error prefix");
                return null;
            }
            String type = new String(buf, this.mPos, 3, Charset.forName("UTF-8"));
            this.mPos += 3;
            String version = new String(buf, this.mPos, 4, Charset.forName("UTF-8"));
            this.mPos += 4;
            this.mToken.setPrefix(prefix);
            this.mToken.setType(type);
            this.mToken.setVersion(version);
            Log.d(
                    EngineeringModeManager.TAG,
                    "Prefix : " + prefix + ", Type : " + type + ", Version : " + version);
            int ret = parseModeDb(buf, this.mPos);
            if (ret < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeDB");
                return null;
            }
            this.mPos += 4;
            int sizeOfInfo = getInt(buf, this.mPos);
            this.mPos += 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return null;
            }
            this.mPos += sizeOfInfo;
            this.mPos += 4;
            Log.d(EngineeringModeManager.TAG, "Pos Offset : " + this.mPos);
            int ret2 = parseGroupDb(buf, this.mPos);
            if (ret2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupDB");
                return null;
            }
            this.mPos += 4;
            int sizeOfInfo2 = getInt(buf, this.mPos);
            this.mPos += 4;
            if (sizeOfInfo2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return null;
            }
            this.mPos += sizeOfInfo2;
            this.mPos += 4;
            parseOTPtime(buf, this.mPos);
            return this.mToken;
        }

        private int parseTokenInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            int i = -1;
            if (!magic.equals("TOKE")) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error tokenInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Token Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            int i2 = 1;
            while (i2 <= numOfData) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                int pos6 = pos5 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error tokenInfo item len : type : " + type);
                    return i;
                }
                if (type != 1 && type != 2 && type != 3) {
                    Log.e(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos6, len);
                pos4 = pos6 + len;
                this.mToken.pushTokenInfo(type, len, value);
                if (type == 3) {
                    this.mNumOfDevice = getShort(value, 0);
                }
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : TokenID, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                    }
                } else if (type == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Device Unique Info, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(value, 0));
                    }
                } else if (type == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : Number Of Device Info, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(value, 0));
                    }
                }
                i2++;
                i = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseDeviceInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            int i = -1;
            if (!magic.equals("DEVI")) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error deviceInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Device Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            int i2 = 1;
            while (i2 <= numOfData) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                int pos6 = pos5 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error deviceInfo item len : type : " + type);
                    return i;
                }
                if (type != 1 && type != 2 && type != 3) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos6, len);
                pos4 = pos6 + len;
                this.mToken.pushDeviceInfo(type, len, value);
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Model Name, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : DID, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : IMEI, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                }
                i2++;
                i = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseIssuerInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            int i = -1;
            if (!magic.equals("ISSU")) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error issuerInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Issuer Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            int i2 = 1;
            while (i2 <= numOfData) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                int pos6 = pos5 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error issuerInfo item len : type : " + type);
                    return i;
                }
                if (type != 1
                        && type != 2
                        && type != 3
                        && type != 4097
                        && type != 4098
                        && type != 4099) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos6, len);
                pos4 = pos6 + len;
                this.mToken.pushIssuerInfo(type, len, value);
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Single ID, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : OTP, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 3) {
                    Log.d(EngineeringModeManager.TAG, "type : Nonce, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                    }
                } else if (type == 4097) {
                    Log.d(EngineeringModeManager.TAG, "type : System ID, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 4098) {
                    Log.d(EngineeringModeManager.TAG, "type : IP, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 4099) {
                    Log.d(EngineeringModeManager.TAG, "type : MAC, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                }
                i2++;
                i = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseModeInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            if (!magic.equals(SizeCompatInfo.Key.MODE)) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i = 1; i <= numOfData; i++) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                pos4 = pos5 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error modeInfo item len : type : " + type);
                    return -1;
                }
                this.mToken.pushModeInfo(type, len, null);
                Log.d(EngineeringModeManager.TAG, "Mode " + i + " -> " + type);
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseValidityInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            if (!magic.equals("VALI")) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error validityInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Validity Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i = 1; i <= numOfData; i++) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                int pos6 = pos5 + 2;
                if (len < 0) {
                    Log.e(
                            EngineeringModeManager.TAG,
                            "Error validityInfo item len : type : " + type);
                    return -1;
                }
                if (type != 1 && type != 2) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos6, len);
                pos4 = pos6 + len;
                this.mToken.pushValidityInfo(type, len, value);
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Issued Date, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                } else if (type == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Expiry Date, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getStringFromBytes(value));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseIntegrityInfo(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            if (!magic.equals("INTE")) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error integInfo numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Integrity Info]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i = 1; i <= numOfData; i++) {
                int type = getShort(buf, pos4);
                int pos5 = pos4 + 2;
                int len = getShort(buf, pos5);
                int pos6 = pos5 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error integInfo item len : type : " + type);
                    return -1;
                }
                if (type != 1 && type != 2) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos6, len);
                pos4 = pos6 + len;
                this.mToken.pushIntegrityInfo(type, len, value);
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : Signature, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                    }
                } else if (type == 2) {
                    Log.d(EngineeringModeManager.TAG, "type : Server Cert, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                    }
                }
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return 0;
        }

        private int parseModeData(byte[] buf, int pos) {
            int modeIndex;
            byte[] bArr = buf;
            int modeIndex2 = getShort(buf, pos);
            int i = 2;
            int pos2 = pos + 2;
            int i2 = -1;
            if (modeIndex2 < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData modeIndex");
                return -1;
            }
            String modeName = new String(bArr, pos2, 32, Charset.forName("UTF-8"));
            int pos3 = pos2 + 32;
            String modeDesc = new String(bArr, pos3, 128, Charset.forName("UTF-8"));
            int pos4 = pos3 + 128;
            int groupIndex = getShort(bArr, pos4);
            int pos5 = pos4 + 2;
            if (groupIndex < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData groupIndex");
                return -1;
            }
            int sizeOfAttrInfo = getInt(bArr, pos5);
            int pos6 = pos5 + 4;
            if (sizeOfAttrInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData sizeOfAttrInfo");
                return -1;
            }
            int numOfAttr = getInt(bArr, pos6);
            int pos7 = pos6 + 4;
            if (numOfAttr < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseModeData numOfAttr");
                return -1;
            }
            this.mToken.pushModeDB(modeIndex2, modeName, modeDesc, groupIndex);
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode DB Attr]");
            Log.d(EngineeringModeManager.TAG, "modeIndex : " + modeIndex2);
            Log.d(EngineeringModeManager.TAG, "modeName : " + modeName);
            Log.d(EngineeringModeManager.TAG, "modeDesc : " + modeDesc);
            Log.d(EngineeringModeManager.TAG, "groupIndex : " + groupIndex);
            Log.d(EngineeringModeManager.TAG, "sizeOfAttrInfo : " + sizeOfAttrInfo);
            Log.d(EngineeringModeManager.TAG, "numOfAttr : " + numOfAttr);
            Log.d(EngineeringModeManager.TAG, "");
            int i3 = 1;
            while (i3 <= numOfAttr) {
                int type = getShort(bArr, pos7);
                int pos8 = pos7 + 2;
                int len = getShort(bArr, pos8);
                int pos9 = pos8 + i;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error modeData item len : type : " + type);
                    return i2;
                }
                if (type != 1 && type != i && type != 3 && type != 4 && type != 5 && type != 6) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(bArr, pos9, len);
                pos7 = pos9 + len;
                this.mToken.pushAttrToModeItem(modeIndex2, type, len, value);
                if (type == 1) {
                    modeIndex = modeIndex2;
                    Log.d(EngineeringModeManager.TAG, "type : Device Unique Info, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + getShort(value, 0));
                    }
                } else {
                    modeIndex = modeIndex2;
                    if (type == 2) {
                        Log.d(EngineeringModeManager.TAG, "type : Hidden, len : " + len);
                        if (len > 0) {
                            Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                        }
                    } else if (type == 3) {
                        Log.d(EngineeringModeManager.TAG, "type : MTUC, len : " + len);
                        if (len > 0) {
                            Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                        }
                    } else if (type == 4) {
                        Log.d(EngineeringModeManager.TAG, "type : MTUC Value, len : " + len);
                        if (len > 0) {
                            Log.d(EngineeringModeManager.TAG, "Value : " + getInt(value, 0));
                        }
                    } else if (type == 5) {
                        Log.d(EngineeringModeManager.TAG, "type : Exclusive, len : " + len);
                        if (len > 0) {
                            Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                        }
                    } else if (type == 6) {
                        Log.d(EngineeringModeManager.TAG, "type : Used Once, len : " + len);
                        if (len > 0) {
                            Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                        }
                    }
                }
                i3++;
                bArr = buf;
                modeIndex2 = modeIndex;
                i = 2;
                i2 = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return pos7;
        }

        private int parseModeDb(byte[] buf, int pos) {
            String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
            int pos2 = pos + 4;
            if (!magic.equals("MODB")) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB magic");
                return -1;
            }
            int sizeOfInfo = getInt(buf, pos2);
            int pos3 = pos2 + 4;
            if (sizeOfInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB sizeOfInfo");
                return -1;
            }
            int numOfData = getInt(buf, pos3);
            int pos4 = pos3 + 4;
            if (numOfData < 0) {
                Log.e(EngineeringModeManager.TAG, "Error modeDB numOfData");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Mode DB]");
            Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
            Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
            Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
            Log.d(EngineeringModeManager.TAG, "");
            for (int i = 0; i < numOfData; i++) {
                pos4 = parseModeData(buf, pos4);
                if (pos4 < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error parseModeData : " + i);
                    return -1;
                }
            }
            return 0;
        }

        private int parseGroupData(byte[] buf, int pos) {
            int groupIndex = getShort(buf, pos);
            int pos2 = pos + 2;
            int i = -1;
            if (groupIndex < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData groupIndex");
                return -1;
            }
            String groupName = new String(buf, pos2, 32, Charset.forName("UTF-8"));
            int pos3 = pos2 + 32;
            String groupDesc = new String(buf, pos3, 128, Charset.forName("UTF-8"));
            int pos4 = pos3 + 128;
            int sizeOfAttrInfo = getInt(buf, pos4);
            int pos5 = pos4 + 4;
            if (sizeOfAttrInfo < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData sizeOfAttrInfo");
                return -1;
            }
            int numOfAttr = getInt(buf, pos5);
            int pos6 = pos5 + 4;
            if (numOfAttr < 0) {
                Log.e(EngineeringModeManager.TAG, "Error parseGroupData numOfAttr");
                return -1;
            }
            this.mToken.pushGroupDB(groupIndex, groupName, groupDesc);
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "[Groupe DB Attr]");
            Log.d(EngineeringModeManager.TAG, "groupIndex : " + groupIndex);
            Log.d(EngineeringModeManager.TAG, "groupName : " + groupName);
            Log.d(EngineeringModeManager.TAG, "groupDesc : " + groupDesc);
            Log.d(EngineeringModeManager.TAG, "sizeOfAttrInfo : " + sizeOfAttrInfo);
            Log.d(EngineeringModeManager.TAG, "numOfAttr : " + numOfAttr);
            Log.d(EngineeringModeManager.TAG, "");
            int i2 = 1;
            while (i2 <= numOfAttr) {
                int type = getShort(buf, pos6);
                int pos7 = pos6 + 2;
                int len = getShort(buf, pos7);
                int pos8 = pos7 + 2;
                if (len < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupData item len : type : " + type);
                    return i;
                }
                if (type != 1) {
                    Log.d(
                            EngineeringModeManager.TAG,
                            "Unregistered type but it can be new one : " + type);
                }
                byte[] value = getBytes(buf, pos8, len);
                pos6 = pos8 + len;
                this.mToken.pushAttrToGroupItem(groupIndex, type, len, value);
                if (type == 1) {
                    Log.d(EngineeringModeManager.TAG, "type : No Duplicate, len : " + len);
                    if (len > 0) {
                        Log.d(EngineeringModeManager.TAG, "Value : " + byteArrayToHex(value));
                    }
                }
                i2++;
                i = -1;
            }
            Log.d(EngineeringModeManager.TAG, "");
            Log.d(EngineeringModeManager.TAG, "");
            return pos6;
        }

        private int parseGroupDb(byte[] buf, int pos) {
            Log.d(EngineeringModeManager.TAG, "Enter praseGroupDb");
            Log.d(EngineeringModeManager.TAG, "Buf Lengh : " + buf.length);
            Log.d(EngineeringModeManager.TAG, "POS Offset : " + pos);
            try {
                String magic = new String(buf, pos, 4, Charset.forName("UTF-8"));
                int pos2 = pos + 4;
                Log.d(EngineeringModeManager.TAG, "magic : " + magic);
                if (!magic.equals("GRDB")) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB magic");
                    return -1;
                }
                int sizeOfInfo = getInt(buf, pos2);
                int pos3 = pos2 + 4;
                if (sizeOfInfo < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB sizeOfInfo");
                    return -1;
                }
                int numOfData = getInt(buf, pos3);
                int pos4 = pos3 + 4;
                if (numOfData < 0) {
                    Log.e(EngineeringModeManager.TAG, "Error groupDB numOfData");
                    return -1;
                }
                Log.d(EngineeringModeManager.TAG, "");
                Log.d(EngineeringModeManager.TAG, "[Group DB]");
                Log.d(EngineeringModeManager.TAG, "Magic : " + magic);
                Log.d(EngineeringModeManager.TAG, "sizeOfInfo : " + sizeOfInfo);
                Log.d(EngineeringModeManager.TAG, "numOfdata : " + numOfData);
                Log.d(EngineeringModeManager.TAG, "");
                for (int i = 0; i < numOfData; i++) {
                    pos4 = parseGroupData(buf, pos4);
                    if (pos4 < 0) {
                        Log.e(EngineeringModeManager.TAG, "Error parseGroupData : " + i);
                        return -1;
                    }
                }
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        private int parseOTPtime(byte[] buf, int pos) {
            this.mOTPtime = getInt(buf, pos);
            if (this.mOTPtime < 0) {
                Log.e(EngineeringModeManager.TAG, "Error OTP remain time");
                return -1;
            }
            Log.d(EngineeringModeManager.TAG, "OTP Time : " + this.mOTPtime);
            this.mToken.pushOTPTime(this.mOTPtime);
            return 0;
        }
    }
}
