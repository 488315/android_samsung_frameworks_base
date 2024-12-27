package com.samsung.android.server.audio;

import android.content.Context;
import android.media.AudioSystem;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PhoneStateHelper {
    public static PhoneStateHelper sInstance;
    public PhoneStateListener[] mPhoneStateListener;
    public TelephonyManager[] mSpecifiedTm;
    public final TelephonyManager mTelephonyManager;
    public int mRilState = -1;
    public int mSimCount = 0;
    public boolean mIs2GTDMANetwork = false;

    public PhoneStateHelper(Context context) {
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public final void registerPhoneStateListener() {
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
                Log.i(
                        "AS.PhoneStateHelper",
                        "registerPhoneStateListener mSimCount: "
                                + this.mSimCount
                                + " , subId: "
                                + i2);
                this.mPhoneStateListener[i] =
                        new PhoneStateListener() { // from class:
                                                   // com.samsung.android.server.audio.PhoneStateHelper.1
                            @Override // android.telephony.PhoneStateListener
                            public final void onCallStateChanged(int i3, String str) {}

                            @Override // android.telephony.PhoneStateListener
                            public final void onServiceStateChanged(ServiceState serviceState) {
                                if (serviceState != null) {
                                    int state = serviceState.getState();
                                    boolean z = true;
                                    if (state != PhoneStateHelper.this.mRilState) {
                                        Log.w(
                                                "AS.PhoneStateHelper",
                                                "RIL State is changed: "
                                                        + PhoneStateHelper.this.mRilState
                                                        + " -> "
                                                        + state);
                                        if (state != 1) {
                                            AudioSystem.setParameters(
                                                    "l_call_ril_state_connected=true");
                                        }
                                        PhoneStateHelper.this.mRilState = state;
                                    }
                                    PhoneStateHelper phoneStateHelper = PhoneStateHelper.this;
                                    int voiceNetworkType = serviceState.getVoiceNetworkType();
                                    phoneStateHelper.getClass();
                                    if (voiceNetworkType != 1
                                            && voiceNetworkType != 16
                                            && voiceNetworkType != 2) {
                                        z = false;
                                    }
                                    if (PhoneStateHelper.this.mIs2GTDMANetwork != z) {
                                        AudioSystem.setParameters(
                                                "l_call_2g_tdma=".concat(z ? "true" : "false"));
                                        PhoneStateHelper.this.mIs2GTDMANetwork = z;
                                    }
                                }
                            }
                        };
                this.mSpecifiedTm[i] = this.mTelephonyManager.createForSubscriptionId(i2);
                this.mSpecifiedTm[i].listen(this.mPhoneStateListener[i], 33);
            }
        }
    }
}
