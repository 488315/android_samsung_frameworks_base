package com.android.systemui.edgelighting.turnover;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.systemui.edgelighting.device.EdgeLightingCoverManager;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import com.android.systemui.edgelighting.utils.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TurnOverEdgeLighting {
    public final AnonymousClass1 mCallStateListener;
    public CallStateObserver mCallStateObserver;
    public final Context mContext;
    public EdgeLightingScheduler.AnonymousClass5 mListener;
    public EdgeLightingScheduler.AnonymousClass6 mRequestor;
    public final AnonymousClass2 mUpdateDownListener;
    public final UpsideDownChecker mUpsideDownChecker;
    public int mIsUpsideDown = 0;
    public boolean mIsEnabled = false;
    public ITurnModeState mCurrentTurnMode = new StateIdle(this, 0);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting$2, reason: invalid class name */
    public final class AnonymousClass2 {
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
                turnOverEdgeLighting.mCurrentTurnMode.onChangedUpdown(i);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ITurnModeState {
        int getMode();

        void onChangedUpdown(int i);

        ITurnModeState onNotification();

        ITurnModeState onNotificationEnd();

        ITurnModeState onRinging();

        ITurnModeState onRingingEnd();
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateCall implements ITurnModeState {
        public final String TAG = StateCall.class.getSimpleName();
        public int currentIsUpSide;

        public StateCall(int i) {
            this.currentIsUpSide = i;
            if (i == 1) {
                TurnOverEdgeLighting.this.mListener.onTurnOver(true);
            }
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final int getMode() {
            return 2;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final void onChangedUpdown(int i) {
            Log.d(this.TAG, "onChangedUpdown: " + this.currentIsUpSide + " -> " + i);
            int i2 = this.currentIsUpSide;
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            if (i2 != 0 && i2 != i) {
                turnOverEdgeLighting.stopTurnOver();
            } else if (i == 1) {
                turnOverEdgeLighting.mListener.onTurnOver(true);
            }
            this.currentIsUpSide = i;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotification() {
            Log.d(this.TAG, "onNotification");
            return this;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotificationEnd() {
            Log.d(this.TAG, "onNotificationEnd");
            return this;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRinging() {
            Log.d(this.TAG, "onRinging");
            return this;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRingingEnd() {
            Log.d(this.TAG, "onRingingEnd");
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            turnOverEdgeLighting.mUpsideDownChecker.cancel();
            turnOverEdgeLighting.mIsUpsideDown = 0;
            turnOverEdgeLighting.mListener.onIdle();
            return new StateIdle(turnOverEdgeLighting, 0);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateIdle implements ITurnModeState {
        public final String TAG;

        public /* synthetic */ StateIdle(TurnOverEdgeLighting turnOverEdgeLighting, int i) {
            this();
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final int getMode() {
            return 0;
        }

        public final boolean isNeedTurnOverChecker() {
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            boolean z = turnOverEdgeLighting.mIsEnabled;
            String str = this.TAG;
            if (!z) {
                Log.d(str, "isNeedTurnOverChecker: not enabled");
                return false;
            }
            if (EdgeLightingScheduler.this.mIsScreenOnReceived) {
                Log.d(str, "isNeedTurnOverChecker: not support screen on");
                return false;
            }
            if (!EdgeLightingCoverManager.getInstance().mSwitchState) {
                Log.d(str, "isNeedTurnOverChecker: not support in cover closed");
                return false;
            }
            Context context = turnOverEdgeLighting.mContext;
            int i = Utils.$r8$clinit;
            if (context.getResources().getConfiguration().semDesktopModeEnabled != 1) {
                return true;
            }
            Log.d(str, "isNeedTurnOverChecker: not support desktop mode");
            return false;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final void onChangedUpdown(int i) {
            Log.d(this.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onChangedUpdown: "));
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotification() {
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            if (!turnOverEdgeLighting.mUpsideDownChecker.mSupportPositionSensor) {
                Log.i(this.TAG, "Device don't support position sensor type.");
                return this;
            }
            if (!isNeedTurnOverChecker()) {
                return this;
            }
            turnOverEdgeLighting.mUpsideDownChecker.run(turnOverEdgeLighting.mUpdateDownListener);
            return turnOverEdgeLighting.new StateNoti();
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRinging() {
            Log.d(this.TAG, "onRinging");
            if (!isNeedTurnOverChecker()) {
                return this;
            }
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            turnOverEdgeLighting.mUpsideDownChecker.run(turnOverEdgeLighting.mUpdateDownListener);
            return turnOverEdgeLighting.new StateCall(0);
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRingingEnd() {
            Log.d(this.TAG, "onRingingEnd");
            return this;
        }

        private StateIdle() {
            this.TAG = StateIdle.class.getSimpleName();
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotificationEnd() {
            return this;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateNoti implements ITurnModeState {
        public final String TAG = StateNoti.class.getSimpleName();
        public int currentIsUpSide = 0;

        public StateNoti() {
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final int getMode() {
            return 1;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final void onChangedUpdown(int i) {
            Log.d(this.TAG, "onChangedUpdown: " + this.currentIsUpSide + " -> " + i);
            int i2 = this.currentIsUpSide;
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            if (i2 != 0 && i2 != i) {
                turnOverEdgeLighting.stopTurnOver();
            } else if (i == 1) {
                turnOverEdgeLighting.mListener.onTurnOver(false);
            } else {
                turnOverEdgeLighting.mListener.onTurnRight();
            }
            this.currentIsUpSide = i;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotification() {
            new Exception("StateNoti$onNotification: should not reach here at normal case").printStackTrace();
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            if (turnOverEdgeLighting.mIsUpsideDown == 1) {
                turnOverEdgeLighting.mListener.onTurnOver(false);
            } else {
                turnOverEdgeLighting.mListener.onTurnRight();
            }
            return this;
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onNotificationEnd() {
            Log.d(this.TAG, "onNotificationEnd");
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            turnOverEdgeLighting.mUpsideDownChecker.cancel();
            turnOverEdgeLighting.mIsUpsideDown = 0;
            turnOverEdgeLighting.mListener.onIdle();
            return new StateIdle(turnOverEdgeLighting, 0);
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRinging() {
            Log.d(this.TAG, "onRinging. So hide edge effect");
            TurnOverEdgeLighting turnOverEdgeLighting = TurnOverEdgeLighting.this;
            turnOverEdgeLighting.mListener.onIdle();
            return turnOverEdgeLighting.new StateCall(turnOverEdgeLighting.mIsUpsideDown);
        }

        @Override // com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting.ITurnModeState
        public final ITurnModeState onRingingEnd() {
            new Exception("StateNoti$onRingingEnd: should not reach here at normal case").printStackTrace();
            return this;
        }
    }

    public TurnOverEdgeLighting(Context context) {
        new AnonymousClass1();
        this.mUpdateDownListener = new AnonymousClass2();
        this.mContext = context;
        this.mUpsideDownChecker = new UpsideDownChecker(context);
    }

    public final void setEnable() {
        Log.d("TurnOverEdgeLighting", "setEnable: false");
        this.mIsEnabled = false;
        CallStateObserver callStateObserver = this.mCallStateObserver;
        if (callStateObserver != null) {
            callStateObserver.mStateListener = null;
            this.mCallStateObserver = null;
        }
        this.mUpsideDownChecker.cancel();
    }

    public final void stopTurnOver() {
        int i = 0;
        this.mIsUpsideDown = 0;
        int mode = this.mCurrentTurnMode.getMode();
        if (mode == 1 || mode == 2) {
            this.mUpsideDownChecker.cancel();
            this.mCurrentTurnMode = new StateIdle(this, i);
            this.mListener.onIdle();
        }
    }
}
