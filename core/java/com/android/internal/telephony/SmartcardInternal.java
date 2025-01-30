package com.android.internal.telephony;

import android.telephony.SubscriptionManager;
import android.util.Log;

/* loaded from: classes5.dex */
public class SmartcardInternal {
    public static final int SMARTCARD_IO_ERROR_ATR_BUFFER = -6;
    public static final int SMARTCARD_IO_ERROR_CARD_NOT_EXIST = -2;
    public static final int SMARTCARD_IO_ERROR_OPEN_CHANNEL = -1;
    public static final int SMARTCARD_IO_ERROR_RESPONSE_BUFFER = -5;
    public static final int SMARTCARD_IO_ERROR_TRANSMIT_BUFFER = -4;
    public static final int SMARTCARD_IO_INVALID_CHANNEL = -3;
    public static final int SMARTCARD_IO_SUCCESS = 0;
    static final String mLogTag = "SmartcardInternal";
    private static volatile SmartcardInternal scInstance = null;
    private static volatile SmartcardInternal scInstance2 = null;
    private IccPcscProvider pcscInstance;

    public static SmartcardInternal getInstance() {
        return getInstance(SubscriptionManager.getDefaultSubscriptionId());
    }

    public static SmartcardInternal getInstance(int subid) {
        int phoneId = SubscriptionManager.getPhoneId(subid);
        if (!SubscriptionManager.isValidPhoneId(phoneId)) {
            Log.m94d(mLogTag, "getInstance fail for invlalid phoneId");
            return null;
        }
        if (phoneId == 0) {
            if (scInstance == null) {
                synchronized (SmartcardInternal.class) {
                    if (scInstance == null) {
                        Log.m94d(mLogTag, "Making an Instance phoneid 0...");
                        scInstance = new SmartcardInternal(phoneId);
                    }
                }
            }
            Log.m94d(mLogTag, "return scInstance : " + scInstance);
            return scInstance;
        }
        if (scInstance2 == null) {
            synchronized (SmartcardInternal.class) {
                if (scInstance2 == null) {
                    Log.m94d(mLogTag, "Making an Instance phoneid 1...");
                    scInstance2 = new SmartcardInternal(phoneId);
                }
            }
        }
        Log.m94d(mLogTag, "return scInstance2 : " + scInstance2);
        return scInstance2;
    }

    private SmartcardInternal(int phoneId) {
        this.pcscInstance = null;
        this.pcscInstance = new IccPcscProvider(phoneId);
        Log.m94d(mLogTag, "init pcscInstance : " + this.pcscInstance);
    }

    protected void finalize() {
        Log.m94d(mLogTag, "finalize");
    }

    public int connect() {
        return this.pcscInstance.connect();
    }

    public int transmit(int channel, byte[] command, byte[] response) {
        return this.pcscInstance.transmit(channel, command, response);
    }

    public int disconnect(int channel) {
        return this.pcscInstance.disconnect(channel);
    }

    public int getATR(byte[] atr) {
        return this.pcscInstance.getATR(atr);
    }
}
