package com.android.internal.telephony;

import android.media.MediaMetrics;
import android.p009os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.internal.telephony.ISemTelephony;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
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

    public IccPcscProvider(int phoneId) {
        this.isInitiated = false;
        this.mPhoneId = phoneId;
        pscsPowerup();
    }

    private void pscsPowerup() {
        Log.m94d(mLogTag, "pscsPowerup");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeByte(22);
            dos.writeByte(38);
            dos.writeShort(4);
            try {
                byte[] bArr = new byte[262];
                getTelephonyService().sendRequestToRIL(bos.toByteArray(), bArr, 2, this.mPhoneId);
                int i = bArr[0];
                Log.m94d(mLogTag, "pscsPowerup ATR:" + bytesToHexString(bArr));
                Log.m94d(mLogTag, "pscsPowerup atrLength:" + i);
                byte[] bArr2 = new byte[i];
                this._atr = bArr2;
                System.arraycopy(bArr, 2, bArr2, 0, i);
                this.isInitiated = true;
            } catch (Exception e) {
                e.printStackTrace();
                this.isInitiated = false;
            }
            try {
                dos.close();
                bos.close();
            } catch (IOException e2) {
                Log.m102w("pscsPowerup", "close fail!!!");
            }
        } catch (IOException e3) {
            Log.m94d(mLogTag, "IOException - connect");
        }
    }

    protected void finalize() {
        pcscPowerdown();
    }

    private void pcscPowerdown() {
        Log.m94d(mLogTag, "pcscPowerdown");
    }

    public int connect() {
        if (!this.isInitiated) {
            pscsPowerup();
            this.isInitiated = true;
        }
        Log.m94d(mLogTag, MediaMetrics.Value.CONNECT);
        return connectToRIL();
    }

    private int connectToRIL() {
        Log.m94d(mLogTag, "connectToRIL");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeByte(22);
            dos.writeByte(39);
            dos.writeShort(9);
            dos.writeByte(0);
            dos.writeByte(112);
            dos.writeByte(0);
            dos.writeByte(0);
            dos.writeByte(1);
            try {
                byte[] response = new byte[1];
                int val = getTelephonyService().sendRequestToRIL(bos.toByteArray(), response, 4, this.mPhoneId);
                dos.close();
                bos.close();
                return val;
            } catch (Exception e) {
                Log.m94d(mLogTag, "Exception - connect");
                try {
                    dos.close();
                    bos.close();
                } catch (IOException e2) {
                }
                return -1;
            }
        } catch (IOException e3) {
            Log.m94d(mLogTag, "IOException - connect");
            return -1;
        }
    }

    public int transmit(int channel, byte[] command, byte[] response) {
        if (command == null) {
            return -4;
        }
        if (response == null) {
            return -5;
        }
        return transmitToRIL(channel, command, response);
    }

    private int transmitToRIL(int channel, byte[] command, byte[] response) {
        Log.m94d(mLogTag, "transmitToRIL");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            int fileSize = command.length + 4;
            dos.writeByte(22);
            dos.writeByte(39);
            dos.writeShort(fileSize);
            for (byte b : command) {
                dos.writeByte(b);
            }
            try {
                int val = getTelephonyService().sendRequestToRIL(bos.toByteArray(), response, 6, this.mPhoneId);
                dos.close();
                bos.close();
                return val;
            } catch (Exception e) {
                try {
                    dos.close();
                    bos.close();
                } catch (IOException e2) {
                }
                e.printStackTrace();
                return -1;
            }
        } catch (IOException e3) {
            Log.m94d(mLogTag, "IOException - transmit");
            return -1;
        }
    }

    public int disconnect(int channel) {
        Log.m94d(mLogTag, MediaMetrics.Value.DISCONNECT);
        return disconnectFromRIL(channel);
    }

    private int disconnectFromRIL(int channel) {
        Log.m94d(mLogTag, "disconnectFromRIL");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            dos.writeByte(22);
            dos.writeByte(39);
            dos.writeShort(8);
            dos.writeByte(0);
            dos.writeByte(112);
            dos.writeByte(128);
            dos.writeByte(channel);
            try {
                byte[] response = new byte[1];
                int val = getTelephonyService().sendRequestToRIL(bos.toByteArray(), response, 5, this.mPhoneId);
                dos.close();
                bos.close();
                return val;
            } catch (Exception e) {
                try {
                    dos.close();
                    bos.close();
                } catch (IOException e2) {
                }
                e.printStackTrace();
                return -1;
            }
        } catch (IOException e3) {
            Log.m94d(mLogTag, "IO Exception - Disconnect");
            return -1;
        }
    }

    public int getATR(byte[] atr) {
        byte[] bArr = this._atr;
        int size = bArr.length;
        if (atr == null || atr.length < size) {
            Log.m94d(mLogTag, "getATR SMARTCARD_IO_ERROR_ATR_BUFFER");
            return -6;
        }
        System.arraycopy(bArr, 0, atr, 0, size);
        return size;
    }

    private ISemTelephony getTelephonyService() {
        ISemTelephony semTelephony = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
        if (semTelephony == null) {
            Log.m102w(mLogTag, "Unable to find ISemTelephony interface.");
        }
        return semTelephony;
    }

    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int b = (bytes[i] >> 4) & 15;
            ret.append("0123456789abcdef".charAt(b));
            int b2 = bytes[i] & 15;
            ret.append("0123456789abcdef".charAt(b2));
        }
        return ret.toString();
    }
}
