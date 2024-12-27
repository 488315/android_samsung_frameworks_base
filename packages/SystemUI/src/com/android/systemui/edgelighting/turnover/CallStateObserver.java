package com.android.systemui.edgelighting.turnover;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;

public final class CallStateObserver {
    public final AnonymousClass2 mPhoneStateListener;
    public TurnOverEdgeLighting.AnonymousClass1 mStateListener;
    public final TelephonyManager mTelephonyManager;
    public int mState = 0;
    public final AnonymousClass1 mHandler = new Handler() { // from class: com.android.systemui.edgelighting.turnover.CallStateObserver.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            TurnOverEdgeLighting.AnonymousClass1 anonymousClass1;
            super.handleMessage(message);
            if (message.what == 0 && (anonymousClass1 = CallStateObserver.this.mStateListener) != null) {
                int i = message.arg1;
                TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
                turnOverEdgeLighting.getClass();
                if (i != 1) {
                    turnOverEdgeLighting.mCurrentTurnMode = turnOverEdgeLighting.mCurrentTurnMode.onRingingEnd();
                } else {
                    turnOverEdgeLighting.mCurrentTurnMode = turnOverEdgeLighting.mCurrentTurnMode.onRinging();
                }
            }
        }
    };

    public CallStateObserver(Context context) {
        ?? r0 = new PhoneStateListener() { // from class: com.android.systemui.edgelighting.turnover.CallStateObserver.2
            @Override // android.telephony.PhoneStateListener
            public final void onCallStateChanged(int i, String str) {
                CallStateObserver callStateObserver = CallStateObserver.this;
                if (i != callStateObserver.mState) {
                    AnonymousClass1 anonymousClass1 = callStateObserver.mHandler;
                    anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, i, 0, str));
                    CallStateObserver.this.mState = i;
                }
                super.onCallStateChanged(i, str);
            }
        };
        this.mPhoneStateListener = r0;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        telephonyManager.listen(r0, 32);
    }
}
