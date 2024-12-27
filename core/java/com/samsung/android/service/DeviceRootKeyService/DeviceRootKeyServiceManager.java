package com.samsung.android.service.DeviceRootKeyService;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import java.util.HashMap;

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
        Log.i(
                TAG,
                NavigationBarInflaterView.SIZE_MOD_START
                        + this.mContext.getPackageName()
                        + "] create DeviceRootKeyServiceManager.");
    }

    public byte[] createServiceKeySession(String serviceName, int keyType, Tlv tlv) {
        Log.i(TAG, "createServiceKeySession() is called.");
        try {
            if (tlv == null) {
                return createServiceKeySessonInternal(serviceName, keyType, null);
            }
            return createServiceKeySessonInternal(serviceName, keyType, tlv.encodeTlv());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int setDeviceRootKey(byte[] keyBlob) {
        Log.i(TAG, "setDeviceRootKey() has been deprecated.");
        return -10000;
    }

    public byte[] doSelfTestProvService(int keyType, Tlv tlv) {
        Log.i(TAG, "doSelfTestProvService() is called.");
        try {
            if (tlv == null) {
                return doSelfTestProvServiceInternal("PROV", keyType, null);
            }
            return doSelfTestProvServiceInternal("PROV", keyType, tlv.encodeTlv());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DeviceInfo getDeviceInfo(int infoType) {
        Log.i(TAG, "getDeviceInfo() is called.");
        if (infoType <= 0 || infoType > 14) {
            Log.e(TAG, "Invalid argument");
            return null;
        }
        try {
            if (this.mTlvEx == null) {
                this.mTlvEx = new TlvEx(getDevInfoInternal());
            }
            return new DeviceInfo(infoType);
        } catch (RuntimeException re) {
            Log.e(TAG, "Operation failed.");
            re.printStackTrace();
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

        public DeviceInfo(int infoType) {
            byte[] tByteArray;
            byte[] tByteArray2;
            byte[] tByteArray3;
            this.mImeiHash = null;
            this.mModemHash = null;
            this.mSerialHash = null;
            this.mIsHuidMatched = false;
            if ((infoType & 2) == 2
                    && (tByteArray3 = DeviceRootKeyServiceManager.this.mTlvEx.getTlvValue(102))
                            != null
                    && isValidLength(102, tByteArray3.length)) {
                this.mModemHash = (byte[]) tByteArray3.clone();
            }
            if ((infoType & 4) == 4
                    && (tByteArray2 = DeviceRootKeyServiceManager.this.mTlvEx.getTlvValue(104))
                            != null
                    && isValidLength(104, tByteArray2.length)) {
                this.mImeiHash = (byte[]) tByteArray2.clone();
            }
            if ((infoType & 8) == 8
                    && (tByteArray = DeviceRootKeyServiceManager.this.mTlvEx.getTlvValue(108))
                            != null
                    && isValidLength(108, tByteArray.length)) {
                this.mSerialHash = (byte[]) tByteArray.clone();
            }
            byte[] tByteArray4 = DeviceRootKeyServiceManager.this.mTlvEx.getTlvValue(101);
            if (tByteArray4 != null && isValidLength(101, tByteArray4.length)) {
                this.mIsHuidMatched = tByteArray4[0] == 1;
            }
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

        /* JADX WARN: Removed duplicated region for block: B:8:0x0010 A[RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean isValidLength(int r4, int r5) {
            /*
                r3 = this;
                int r0 = r4 + (-100)
                r1 = 1
                r2 = 0
                switch(r0) {
                    case 1: goto Ld;
                    case 2: goto L8;
                    case 4: goto L8;
                    case 8: goto L8;
                    default: goto L7;
                }
            L7:
                return r2
            L8:
                r0 = 32
                if (r5 == r0) goto L10
                return r2
            Ld:
                if (r5 == r1) goto L10
                return r2
            L10:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.service.DeviceRootKeyService.DeviceRootKeyServiceManager.DeviceInfo.isValidLength(int,"
                        + " int):boolean");
        }
    }

    private final class TlvEx {
        private static final int LENGTH_FIELD_SIZE = 2;
        private static final int TAGLENGTH_FIELD_SIZE = 3;
        private static final int TAG_FIELD_SIZE = 1;
        private static final int TLV_TAG_START = 254;
        private HashMap<Integer, byte[]> mTlvList = new HashMap<>();

        public TlvEx(byte[] tlvBuffer) {
            if (!parseTlv(tlvBuffer)) {
                throw new IllegalStateException("Failed to parse Tlv.");
            }
        }

        public byte[] getTlvValue(int tlvTag) {
            return this.mTlvList.get(Integer.valueOf(tlvTag));
        }

        private int getTag(byte[] tlvBuffer, int pos) {
            return tlvBuffer[pos] & 255;
        }

        private int getLength(byte[] tlvBuffer, int pos) {
            return ((tlvBuffer[pos + 1] & 255) << 8) | (tlvBuffer[pos] & 255);
        }

        private boolean parseTlv(byte[] tlvBuffer) {
            if (tlvBuffer == null || tlvBuffer.length < 3) {
                Log.e(DeviceRootKeyServiceManager.TAG, "Invalid argument");
                return false;
            }
            int tag = getTag(tlvBuffer, 0);
            int pos = 0 + 1;
            int len = getLength(tlvBuffer, pos);
            int pos2 = pos + 2;
            if (tag != 254 || pos2 + len != tlvBuffer.length) {
                Log.e(DeviceRootKeyServiceManager.TAG, "Failed to read TLV header");
                return false;
            }
            while (pos2 + 3 <= tlvBuffer.length) {
                int tag2 = getTag(tlvBuffer, pos2);
                int pos3 = pos2 + 1;
                int len2 = getLength(tlvBuffer, pos3);
                int pos4 = pos3 + 2;
                if (pos4 + len2 <= tlvBuffer.length) {
                    byte[] temp = new byte[len2];
                    System.arraycopy(tlvBuffer, pos4, temp, 0, len2);
                    this.mTlvList.put(Integer.valueOf(tag2), temp);
                }
                pos2 = pos4 + len2;
            }
            return true;
        }
    }
}
