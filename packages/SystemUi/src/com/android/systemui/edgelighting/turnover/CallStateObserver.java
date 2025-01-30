package com.android.systemui.edgelighting.turnover;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CallStateObserver {
    public final C13772 mPhoneStateListener;
    public TurnOverEdgeLighting.C13781 mStateListener;
    public final TelephonyManager mTelephonyManager;
    public int mState = 0;
    public final HandlerC13761 mHandler = new Handler() { // from class: com.android.systemui.edgelighting.turnover.CallStateObserver.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            TurnOverEdgeLighting.C13781 c13781;
            super.handleMessage(message);
            if (message.what == 0 && (c13781 = CallStateObserver.this.mStateListener) != null) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.edgelighting.turnover.CallStateObserver$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.telephony.PhoneStateListener, com.android.systemui.edgelighting.turnover.CallStateObserver$2] */
    public CallStateObserver(Context context) {
        ?? r0 = new PhoneStateListener() { // from class: com.android.systemui.edgelighting.turnover.CallStateObserver.2
            @Override // android.telephony.PhoneStateListener
            public final void onCallStateChanged(int i, String str) {
                CallStateObserver callStateObserver = CallStateObserver.this;
                if (i != callStateObserver.mState) {
                    HandlerC13761 handlerC13761 = callStateObserver.mHandler;
                    handlerC13761.sendMessage(handlerC13761.obtainMessage(0, i, 0, str));
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
