package com.lguplus.p035se;

import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.IccPcscProvider;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes5.dex */
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
    private static SmartMXUICC sInstance = null;
    private static IccPcscProvider pcscInstance = null;
    private static int[] handleId = {0, 0, 0, 0, 0};
    static Random random = new Random();

    public static SmartMXUICC createSmartMXUICC() {
        synchronized (SmartMXUICC.class) {
            Log.m94d(mLogTag, "Making an Instance...");
            if (sInstance == null) {
                sInstance = new SmartMXUICC();
            }
        }
        return sInstance;
    }

    private SmartMXUICC() {
        Log.m94d(mLogTag, mLogTag);
        pcscInstance = new IccPcscProvider(0);
        Log.m94d(mLogTag, "SmartMXUICC pcscInstance retrun : " + pcscInstance);
    }

    private int openLogicalChannel() {
        int channel = pcscInstance.connect();
        if (channel > 1 && channel < 4) {
            Log.m94d(mLogTag, "openLogicalChannel channel[" + channel);
            return channel;
        }
        Log.m94d(mLogTag, "openLogicalChannel Failed : " + channel);
        return channel;
    }

    public int openSecureElementConnection(String seType) throws IOException {
        int retVal;
        if (seType == null) {
            throw new NullPointerException("seType must not be null");
        }
        if (TelephonyManager.getDefault().getSimState() == 1) {
            retVal = -2;
        } else if (seType.equals(UICC_ID)) {
            retVal = openLogicalChannel();
        } else {
            retVal = -1;
        }
        Log.m94d(mLogTag, "openSecureElementConnection, retVal:" + retVal);
        if (retVal == -1) {
            throw new IOException("Fail to open channel");
        }
        return retVal;
    }

    public byte[] exchangeAPDU(int ch, byte[] data) throws IOException {
        byte[] apdu_res = new byte[262];
        byte[] response = null;
        Log.m94d(mLogTag, "exchangeAPDU channel : " + ch);
        data[0] = (byte) (data[0] | ch);
        int resSize = pcscInstance.transmit(ch, data, apdu_res);
        if (resSize > 0) {
            response = new byte[resSize];
            System.arraycopy(apdu_res, 0, response, 0, resSize);
        }
        if (response == null) {
            Log.m94d(mLogTag, "exchangeAPDU return null");
            throw new IOException("Response is NULL");
        }
        return response;
    }

    public void closeSecureElementConnection(int ch) throws IOException {
        Log.m94d(mLogTag, "closeSecureElementConnection channel : " + ch);
        if (ch >= 1) {
            pcscInstance.disconnect(ch);
            Log.m94d(mLogTag, "closeSecureElementConnection done!");
        } else {
            Log.m94d(mLogTag, "closeSecureElementConnection channel is wrong");
        }
    }

    public int[] getSecureElementTechList(int ch) throws IOException {
        return null;
    }

    public byte[] getSecureElementUid(int ch) throws IOException {
        return null;
    }

    public byte[] getATR() {
        byte[] apdu_res = new byte[262];
        try {
            int resSize = pcscInstance.getATR(apdu_res);
            if (resSize <= 0) {
                return null;
            }
            byte[] response = new byte[resSize];
            System.arraycopy(apdu_res, 0, response, 0, resSize);
            return response;
        } catch (Exception e) {
            Log.m94d(mLogTag, "getATR Errors");
            return null;
        }
    }

    public boolean isSmartMX() {
        return false;
    }
}
