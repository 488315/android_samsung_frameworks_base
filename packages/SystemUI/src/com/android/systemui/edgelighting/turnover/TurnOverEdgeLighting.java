package com.android.systemui.edgelighting.turnover;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TurnOverEdgeLighting {
    public CallStateObserver mCallStateObserver;
    public final Context mContext;
    public EdgeLightingScheduler.AnonymousClass5 mListener;
    public EdgeLightingScheduler.AnonymousClass6 mRequestor;
    public final UpsideDownChecker mUpsideDownChecker;
    public int mIsUpsideDown = 0;
    public StateIdle mCurrentTurnMode = new StateIdle(this, 0);
    public final AnonymousClass1 mCallStateListener = new AnonymousClass1();
    public final AnonymousClass2 mUpdateDownListener = new AnonymousClass2();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void onChanged(boolean z) {
            StringBuffer stringBuffer = new StringBuffer("UpsideDownChecker: ");
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            stringBuffer.append(turnOverEdgeLighting.mIsUpsideDown);
            stringBuffer.append(" -> ");
            stringBuffer.append(z);
            Log.d("TurnOverEdgeLighting", stringBuffer.toString());
            int i = z ? 1 : 2;
            if (turnOverEdgeLighting.mIsUpsideDown != i) {
                turnOverEdgeLighting.mIsUpsideDown = i;
                StateIdle stateIdle = turnOverEdgeLighting.mCurrentTurnMode;
                stateIdle.getClass();
                Log.d(stateIdle.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onChangedUpdown: "));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StateIdle {
        public final String TAG;

        public /* synthetic */ StateIdle(TurnOverEdgeLighting turnOverEdgeLighting, int i) {
            this();
        }

        public final int getMode() {
            return 0;
        }

        public final boolean isNeedTurnOverChecker() {
            TurnOverEdgeLighting.this.getClass();
            Log.d(this.TAG, "isNeedTurnOverChecker: not enabled");
            return false;
        }

        public final StateIdle onNotification() {
            if (TurnOverEdgeLighting.this.mUpsideDownChecker.mSupportPositionSensor) {
                isNeedTurnOverChecker();
                return this;
            }
            Log.i(this.TAG, "Device don't support position sensor type.");
            return this;
        }

        private StateIdle() {
            this.TAG = StateIdle.class.getSimpleName();
        }
    }

    public TurnOverEdgeLighting(Context context) {
        this.mContext = context;
        this.mUpsideDownChecker = new UpsideDownChecker(context);
    }

    public final void setEnable() {
        Log.d("TurnOverEdgeLighting", "setEnable: false");
        CallStateObserver callStateObserver = this.mCallStateObserver;
        if (callStateObserver != null) {
            callStateObserver.mStateListener = null;
            this.mCallStateObserver = null;
        }
        this.mUpsideDownChecker.cancel();
    }
}
