package com.samsung.android.server.audio;

import android.content.Context;
import android.media.AudioSystem;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/* loaded from: classes2.dex */
public class PhoneStateHelper {
    public static PhoneStateHelper sInstance;
    public PhoneStateListener[] mPhoneStateListener;
    public TelephonyManager[] mSpecifiedTm;
    public final TelephonyManager mTelephonyManager;
    public int mRilState = -1;
    public int mSimCount = 0;
    public boolean mIs2GTDMANetwork = false;

    public final boolean is2GTDMANetwork(int i) {
        return i == 1 || i == 16 || i == 2;
    }

    public PhoneStateHelper(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public static PhoneStateHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneStateHelper(context);
        }
        return sInstance;
    }

    public void registerPhoneStateListener() {
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        this.mSimCount = activeModemCount;
        if (activeModemCount == 0) {
            Log.w("AS.PhoneStateHelper", "do not register PhoneStateListener on NO_SIM state");
            return;
        }
        this.mPhoneStateListener = new PhoneStateListener[activeModemCount];
        this.mSpecifiedTm = new TelephonyManager[activeModemCount];
        for (int i = 0; i < this.mSimCount; i++) {
            int[] subId = SubscriptionManager.getSubId(i);
            if (subId != null && subId.length > 0) {
                int i2 = subId[0];
                Log.i("AS.PhoneStateHelper", "registerPhoneStateListener mSimCount: " + this.mSimCount + " , subId: " + i2);
                this.mPhoneStateListener[i] = getPhoneStateListener();
                this.mSpecifiedTm[i] = this.mTelephonyManager.createForSubscriptionId(i2);
                this.mSpecifiedTm[i].listen(this.mPhoneStateListener[i], 33);
            }
        }
    }

    public void unregisterPhoneStateListener() {
        if (this.mTelephonyManager != null) {
            for (int i = 0; i < this.mSimCount; i++) {
                PhoneStateListener phoneStateListener = this.mPhoneStateListener[i];
                if (phoneStateListener != null) {
                    this.mSpecifiedTm[i].listen(phoneStateListener, 0);
                    this.mRilState = -1;
                    Log.i("AS.PhoneStateHelper", "call unregisterPhoneStateListener : " + i);
                }
            }
        }
    }

    public final PhoneStateListener getPhoneStateListener() {
        return new PhoneStateListener() { // from class: com.samsung.android.server.audio.PhoneStateHelper.1
            @Override // android.telephony.PhoneStateListener
            public void onCallStateChanged(int i, String str) {
            }

            @Override // android.telephony.PhoneStateListener
            public void onServiceStateChanged(ServiceState serviceState) {
                if (serviceState != null) {
                    int state = serviceState.getState();
                    if (state != PhoneStateHelper.this.mRilState) {
                        Log.w("AS.PhoneStateHelper", "RIL State is changed: " + PhoneStateHelper.this.mRilState + " -> " + state);
                        if (state != 1) {
                            AudioSystem.setParameters("l_call_ril_state_connected=true");
                        }
                        PhoneStateHelper.this.mRilState = state;
                    }
                    boolean is2GTDMANetwork = PhoneStateHelper.this.is2GTDMANetwork(serviceState.getVoiceNetworkType());
                    if (PhoneStateHelper.this.mIs2GTDMANetwork != is2GTDMANetwork) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("l_call_2g_tdma=");
                        sb.append(is2GTDMANetwork ? "true" : "false");
                        AudioSystem.setParameters(sb.toString());
                        PhoneStateHelper.this.mIs2GTDMANetwork = is2GTDMANetwork;
                    }
                }
            }
        };
    }
}
