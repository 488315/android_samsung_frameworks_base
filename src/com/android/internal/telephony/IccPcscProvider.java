package com.android.internal.telephony;

import android.media.MediaMetrics;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.internal.telephony.ISemTelephony;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class IccPcscProvider {
    public static final int CONNECT = 4;
    public static final int DISCONNECT = 5;
    public static final int INIT = 1;
    private static final int OEM_AUTH_ATR = 13;
    private static final int OEM_AUTH_OPEN_CHANNEL = 9;
    private static final int OEM_AUTH_SEND_APDU = 8;
    private static final int OEM_DOMESTIC_PCSC_POWERDOWN = 40;
    private static final int OEM_DOMESTIC_PCSC_POWERUP = 38;
    private static final int OEM_DOMESTIC_PCSC_TRANSMIT = 39;
    private static final int OEM_FUNCTION_ID_AUTH = 21;
    private static final int OEM_FUNCTION_ID_DOMESTIC = 22;
    public static final int POWERDOWN = 3;
    public static final int POWERUP = 2;
    public static final int RESPONSE_MAX_SIZE = 262;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_PIN_REQUIRED = 2;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_UNKNOWN = 0;
    public static final int SMARTCARD_IO_ERROR_ATR_BUFFER = -6;
    public static final int SMARTCARD_IO_ERROR_CARD_NOT_EXIST = -2;
    public static final int SMARTCARD_IO_ERROR_OPEN_CHANNEL = -1;
    public static final int SMARTCARD_IO_ERROR_RESPONSE_BUFFER = -5;
    public static final int SMARTCARD_IO_ERROR_TRANSMIT_BUFFER = -4;
    public static final int SMARTCARD_IO_INVALID_CHANNEL = -3;
    public static final int SMARTCARD_IO_SUCCESS = 0;
    public static final int TRANSMIT = 6;
    public static final int USIMAUTH = 7;
    static final String mLogTag = "RIL_IccPcscProvider";
    private byte[] _atr;
    private boolean isInitiated;
    private int mPhoneId;

    public IccPcscProvider() {
        this.isInitiated = false;
        this.mPhoneId = SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultSubscriptionId());
        pscsPowerup();
    }

    public IccPcscProvider(int i) {
        this.isInitiated = false;
        this.mPhoneId = i;
        pscsPowerup();
    }

    private void pscsPowerup() {
        Log.d(mLogTag, "pscsPowerup");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(22);
            dataOutputStream.writeByte(38);
            dataOutputStream.writeShort(4);
            try {
                byte[] bArr = new byte[262];
                getTelephonyService().sendRequestToRIL(byteArrayOutputStream.toByteArray(), bArr, 2, this.mPhoneId);
                int i = bArr[0];
                Log.d(mLogTag, "pscsPowerup ATR:" + bytesToHexString(bArr));
                Log.d(mLogTag, "pscsPowerup atrLength:" + i);
                byte[] bArr2 = new byte[i];
                this._atr = bArr2;
                System.arraycopy(bArr, 2, bArr2, 0, i);
                this.isInitiated = true;
            } catch (Exception e) {
                e.printStackTrace();
                this.isInitiated = false;
            }
            try {
                dataOutputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException unused) {
                Log.w("pscsPowerup", "close fail!!!");
            }
        } catch (IOException unused2) {
            Log.d(mLogTag, "IOException - connect");
        }
    }

    protected void finalize() {
        pcscPowerdown();
    }

    private void pcscPowerdown() {
        Log.d(mLogTag, "pcscPowerdown");
    }

    public int connect() {
        if (!this.isInitiated) {
            pscsPowerup();
            this.isInitiated = true;
        }
        Log.d(mLogTag, MediaMetrics.Value.CONNECT);
        return connectToRIL();
    }

    private int connectToRIL() {
        Log.d(mLogTag, "connectToRIL");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(22);
            dataOutputStream.writeByte(39);
            dataOutputStream.writeShort(9);
            dataOutputStream.writeByte(0);
            dataOutputStream.writeByte(112);
            dataOutputStream.writeByte(0);
            dataOutputStream.writeByte(0);
            dataOutputStream.writeByte(1);
            try {
                int sendRequestToRIL = getTelephonyService().sendRequestToRIL(byteArrayOutputStream.toByteArray(), new byte[1], 4, this.mPhoneId);
                dataOutputStream.close();
                byteArrayOutputStream.close();
                return sendRequestToRIL;
            } catch (Exception unused) {
                Log.d(mLogTag, "Exception - connect");
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused2) {
                }
                return -1;
            }
        } catch (IOException unused3) {
            Log.d(mLogTag, "IOException - connect");
            return -1;
        }
    }

    public int transmit(int i, byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return -4;
        }
        if (bArr2 == null) {
            return -5;
        }
        return transmitToRIL(i, bArr, bArr2);
    }

    private int transmitToRIL(int i, byte[] bArr, byte[] bArr2) {
        Log.d(mLogTag, "transmitToRIL");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            int length = bArr.length + 4;
            dataOutputStream.writeByte(22);
            dataOutputStream.writeByte(39);
            dataOutputStream.writeShort(length);
            for (byte b : bArr) {
                dataOutputStream.writeByte(b);
            }
            try {
                int sendRequestToRIL = getTelephonyService().sendRequestToRIL(byteArrayOutputStream.toByteArray(), bArr2, 6, this.mPhoneId);
                dataOutputStream.close();
                byteArrayOutputStream.close();
                return sendRequestToRIL;
            } catch (Exception e) {
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
                e.printStackTrace();
                return -1;
            }
        } catch (IOException unused2) {
            Log.d(mLogTag, "IOException - transmit");
            return -1;
        }
    }

    public int disconnect(int i) {
        Log.d(mLogTag, MediaMetrics.Value.DISCONNECT);
        return disconnectFromRIL(i);
    }

    private int disconnectFromRIL(int i) {
        Log.d(mLogTag, "disconnectFromRIL");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeByte(22);
            dataOutputStream.writeByte(39);
            dataOutputStream.writeShort(8);
            dataOutputStream.writeByte(0);
            dataOutputStream.writeByte(112);
            dataOutputStream.writeByte(128);
            dataOutputStream.writeByte(i);
            try {
                int sendRequestToRIL = getTelephonyService().sendRequestToRIL(byteArrayOutputStream.toByteArray(), new byte[1], 5, this.mPhoneId);
                dataOutputStream.close();
                byteArrayOutputStream.close();
                return sendRequestToRIL;
            } catch (Exception e) {
                try {
                    dataOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException unused) {
                }
                e.printStackTrace();
                return -1;
            }
        } catch (IOException unused2) {
            Log.d(mLogTag, "IO Exception - Disconnect");
            return -1;
        }
    }

    public int getATR(byte[] bArr) {
        byte[] bArr2 = this._atr;
        int length = bArr2.length;
        if (bArr == null || bArr.length < length) {
            Log.d(mLogTag, "getATR SMARTCARD_IO_ERROR_ATR_BUFFER");
            return -6;
        }
        System.arraycopy(bArr2, 0, bArr, 0, length);
        return length;
    }

    private ISemTelephony getTelephonyService() {
        ISemTelephony asInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
        if (asInterface == null) {
            Log.w(mLogTag, "Unable to find ISemTelephony interface.");
        }
        return asInterface;
    }

    private static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append("0123456789abcdef".charAt((bArr[i] >> 4) & 15));
            sb.append("0123456789abcdef".charAt(bArr[i] & 15));
        }
        return sb.toString();
    }
}
