package com.samsung.android.service.DeviceRootKeyService;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class DeviceRootKeyServiceManager {
    public static final int ERR_SERVICE_ERROR = -10000;
    public static final int KEY_TYPE_EC = 4;
    public static final int KEY_TYPE_RSA = 1;
    public static final int NO_ERROR = 0;
    private static final String TAG = "DEVROOT#MGR";
    private static final String VERSION = "1.1.4";
    private final Context mContext;
    private TlvEx mTlvEx = null;

    private native byte[] createServiceKeySessonInternal(String str, int i, byte[] bArr);

    private native byte[] doSelfTestProvServiceInternal(String str, int i, byte[] bArr);

    private native byte[] getDevInfoInternal();

    public native byte[] getDeviceRootKeyCertificate(int i);

    public native String getDeviceRootKeyUID(int i);

    public native boolean isAliveDeviceRootKeyService();

    public native boolean isExistDeviceRootKey(int i);

    public native int releaseServiceKeySession();

    static {
        System.loadLibrary("_nativeJni.dk.samsung");
    }

    public DeviceRootKeyServiceManager(Context context) {
        this.mContext = context;
        Log.i(TAG, NavigationBarInflaterView.SIZE_MOD_START + context.getPackageName() + "] create DeviceRootKeyServiceManager.");
    }

    public byte[] createServiceKeySession(String str, int i, Tlv tlv) {
        Log.i(TAG, "createServiceKeySession() is called.");
        try {
            if (tlv == null) {
                return createServiceKeySessonInternal(str, i, null);
            }
            return createServiceKeySessonInternal(str, i, tlv.encodeTlv());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int setDeviceRootKey(byte[] bArr) {
        Log.i(TAG, "setDeviceRootKey() has been deprecated.");
        return -10000;
    }

    public byte[] doSelfTestProvService(int i, Tlv tlv) {
        Log.i(TAG, "doSelfTestProvService() is called.");
        try {
            if (tlv != null) {
                return doSelfTestProvServiceInternal("PROV", i, tlv.encodeTlv());
            }
            return doSelfTestProvServiceInternal("PROV", i, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DeviceInfo getDeviceInfo(int i) {
        Log.i(TAG, "getDeviceInfo() is called.");
        if (i <= 0 || i > 14) {
            Log.e(TAG, "Invalid argument");
            return null;
        }
        try {
            if (this.mTlvEx == null) {
                this.mTlvEx = new TlvEx(this, getDevInfoInternal());
            }
            return new DeviceInfo(this, i);
        } catch (RuntimeException e) {
            Log.e(TAG, "Operation failed.");
            e.printStackTrace();
            return null;
        }
    }

    public final class DeviceInfo {
        public static final int DEVICE_INFO_ALL = 14;
        private static final int DEVICE_INFO_EMPTY = 0;
        public static final int DEVICE_INFO_IMEI = 4;
        public static final int DEVICE_INFO_IMEI_MODEM = 6;
        public static final int DEVICE_INFO_IMEI_SERIAL = 12;
        public static final int DEVICE_INFO_INTEGRITY_STATUS = 1;
        public static final int DEVICE_INFO_MODEM = 2;
        public static final int DEVICE_INFO_MODEM_SERIAL = 10;
        public static final int DEVICE_INFO_SERIAL = 8;
        private static final byte DEVICE_STATUS_IS_INVALID = 0;
        private static final byte DEVICE_STATUS_IS_VALID = 1;
        private static final int MAX_SHA256_LENGTH = 32;
        private static final int MAX_STATUS_LENGTH = 1;
        private static final int TLV_EX_BASE = 100;
        private byte[] mImeiHash;
        private boolean mIsHuidMatched;
        private byte[] mModemHash;
        private byte[] mSerialHash;

        private boolean isValidLength(int i, int i2) {
            int i3 = i - 100;
            if (i3 != 1) {
                if ((i3 != 2 && i3 != 4 && i3 != 8) || i2 != 32) {
                    return false;
                }
            } else if (i2 != 1) {
                return false;
            }
            return true;
        }

        public DeviceInfo(DeviceRootKeyServiceManager deviceRootKeyServiceManager, int i) {
            byte[] tlvValue;
            byte[] tlvValue2;
            byte[] tlvValue3;
            this.mImeiHash = null;
            this.mModemHash = null;
            this.mSerialHash = null;
            this.mIsHuidMatched = false;
            if ((i & 2) == 2 && (tlvValue3 = deviceRootKeyServiceManager.mTlvEx.getTlvValue(102)) != null && isValidLength(102, tlvValue3.length)) {
                this.mModemHash = (byte[]) tlvValue3.clone();
            }
            if ((i & 4) == 4 && (tlvValue2 = deviceRootKeyServiceManager.mTlvEx.getTlvValue(104)) != null && isValidLength(104, tlvValue2.length)) {
                this.mImeiHash = (byte[]) tlvValue2.clone();
            }
            if ((i & 8) == 8 && (tlvValue = deviceRootKeyServiceManager.mTlvEx.getTlvValue(108)) != null && isValidLength(108, tlvValue.length)) {
                this.mSerialHash = (byte[]) tlvValue.clone();
            }
            byte[] tlvValue4 = deviceRootKeyServiceManager.mTlvEx.getTlvValue(101);
            if (tlvValue4 == null || !isValidLength(101, tlvValue4.length)) {
                return;
            }
            this.mIsHuidMatched = tlvValue4[0] == 1;
        }

        public byte[] getImei() {
            return this.mImeiHash;
        }

        public byte[] getModem() {
            return this.mModemHash;
        }

        public byte[] getSerial() {
            return this.mSerialHash;
        }

        public boolean isHuidMatched() {
            return this.mIsHuidMatched;
        }
    }

    private final class TlvEx {
        private static final int LENGTH_FIELD_SIZE = 2;
        private static final int TAGLENGTH_FIELD_SIZE = 3;
        private static final int TAG_FIELD_SIZE = 1;
        private static final int TLV_TAG_START = 254;
        private HashMap<Integer, byte[]> mTlvList = new HashMap<>();

        public TlvEx(DeviceRootKeyServiceManager deviceRootKeyServiceManager, byte[] bArr) {
            if (!parseTlv(bArr)) {
                throw new IllegalStateException("Failed to parse Tlv.");
            }
        }

        public byte[] getTlvValue(int i) {
            return this.mTlvList.get(Integer.valueOf(i));
        }

        private int getTag(byte[] bArr, int i) {
            return bArr[i] & 255;
        }

        private int getLength(byte[] bArr, int i) {
            return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
        }

        private boolean parseTlv(byte[] bArr) {
            if (bArr != null) {
                int i = 3;
                if (bArr.length >= 3) {
                    int tag = getTag(bArr, 0);
                    int length = getLength(bArr, 1);
                    if (tag != 254 || length + 3 != bArr.length) {
                        Log.e(DeviceRootKeyServiceManager.TAG, "Failed to read TLV header");
                        return false;
                    }
                    while (i + 3 <= bArr.length) {
                        int tag2 = getTag(bArr, i);
                        int length2 = getLength(bArr, i + 1);
                        int i2 = i + 3;
                        int i3 = i2 + length2;
                        if (i3 <= bArr.length) {
                            byte[] bArr2 = new byte[length2];
                            System.arraycopy(bArr, i2, bArr2, 0, length2);
                            this.mTlvList.put(Integer.valueOf(tag2), bArr2);
                        }
                        i = i3;
                    }
                    return true;
                }
            }
            Log.e(DeviceRootKeyServiceManager.TAG, "Invalid argument");
            return false;
        }
    }
}
