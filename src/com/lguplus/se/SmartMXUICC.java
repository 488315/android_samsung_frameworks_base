package com.lguplus.se;

import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.IccPcscProvider;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes6.dex */
public class SmartMXUICC {
    static final boolean DBG = true;
    public static final String EXTRA_ID = "android.nfc.extra.ID";
    public static final int RESPONSE_MAX_SIZE = 262;
    public static final int SMARTCARD_IO_ERROR_ATR_BUFFER = -6;
    public static final int SMARTCARD_IO_ERROR_CARD_NOT_EXIST = -2;
    public static final int SMARTCARD_IO_ERROR_OPEN_CHANNEL = -1;
    public static final int SMARTCARD_IO_ERROR_RESPONSE_BUFFER = -5;
    public static final int SMARTCARD_IO_ERROR_TRANSMIT_BUFFER = -4;
    public static final int SMARTCARD_IO_INVALID_CHANNEL = -3;
    public static final int SMARTCARD_IO_SUCCESS = 0;
    public static final String SMART_MX_ID = "android.nfc.smart_mx.ID";
    public static final String UICC_ID = "android.nfc.uicc.ID";
    static final String mLogTag = "SmartMXUICC";
    private static IccPcscProvider pcscInstance;
    private static SmartMXUICC sInstance;
    private static int[] handleId = {0, 0, 0, 0, 0};
    static Random random = new Random();

    public int[] getSecureElementTechList(int i) throws IOException {
        return null;
    }

    public byte[] getSecureElementUid(int i) throws IOException {
        return null;
    }

    public boolean isSmartMX() {
        return false;
    }

    public static SmartMXUICC createSmartMXUICC() {
        synchronized (SmartMXUICC.class) {
            Log.d(mLogTag, "Making an Instance...");
            if (sInstance == null) {
                sInstance = new SmartMXUICC();
            }
        }
        return sInstance;
    }

    private SmartMXUICC() {
        Log.d(mLogTag, mLogTag);
        pcscInstance = new IccPcscProvider(0);
        Log.d(mLogTag, "SmartMXUICC pcscInstance retrun : " + pcscInstance);
    }

    private int openLogicalChannel() {
        int connect = pcscInstance.connect();
        if (connect > 1 && connect < 4) {
            Log.d(mLogTag, "openLogicalChannel channel[" + connect);
            return connect;
        }
        Log.d(mLogTag, "openLogicalChannel Failed : " + connect);
        return connect;
    }

    public int openSecureElementConnection(String str) throws IOException {
        int openLogicalChannel;
        if (str == null) {
            throw new NullPointerException("seType must not be null");
        }
        if (TelephonyManager.getDefault().getSimState() == 1) {
            openLogicalChannel = -2;
        } else {
            openLogicalChannel = str.equals(UICC_ID) ? openLogicalChannel() : -1;
        }
        Log.d(mLogTag, "openSecureElementConnection, retVal:" + openLogicalChannel);
        if (openLogicalChannel != -1) {
            return openLogicalChannel;
        }
        throw new IOException("Fail to open channel");
    }

    public byte[] exchangeAPDU(int i, byte[] bArr) throws IOException {
        byte[] bArr2;
        byte[] bArr3 = new byte[262];
        Log.d(mLogTag, "exchangeAPDU channel : " + i);
        bArr[0] = (byte) (bArr[0] | i);
        int transmit = pcscInstance.transmit(i, bArr, bArr3);
        if (transmit > 0) {
            bArr2 = new byte[transmit];
            System.arraycopy(bArr3, 0, bArr2, 0, transmit);
        } else {
            bArr2 = null;
        }
        if (bArr2 != null) {
            return bArr2;
        }
        Log.d(mLogTag, "exchangeAPDU return null");
        throw new IOException("Response is NULL");
    }

    public void closeSecureElementConnection(int i) throws IOException {
        Log.d(mLogTag, "closeSecureElementConnection channel : " + i);
        if (i < 1) {
            Log.d(mLogTag, "closeSecureElementConnection channel is wrong");
        } else {
            pcscInstance.disconnect(i);
            Log.d(mLogTag, "closeSecureElementConnection done!");
        }
    }

    public byte[] getATR() {
        byte[] bArr = new byte[262];
        try {
            int atr = pcscInstance.getATR(bArr);
            if (atr <= 0) {
                return null;
            }
            byte[] bArr2 = new byte[atr];
            System.arraycopy(bArr, 0, bArr2, 0, atr);
            return bArr2;
        } catch (Exception unused) {
            Log.d(mLogTag, "getATR Errors");
            return null;
        }
    }
}
