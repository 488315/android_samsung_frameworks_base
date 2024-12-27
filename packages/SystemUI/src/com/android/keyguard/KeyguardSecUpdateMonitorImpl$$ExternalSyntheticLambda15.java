package com.android.keyguard;

import android.os.Message;
import com.android.systemui.settings.UserTrackerImpl;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecUpdateMonitorImpl f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecUpdateMonitorImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Message message;
        SecFaceMsg secFaceMsg;
        int i = this.$r8$classId;
        KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
        switch (i) {
            case 0:
                SecFpMsg secFpMsg = (SecFpMsg) obj;
                keyguardSecUpdateMonitorImpl.mFpMessages.add(secFpMsg);
                Message obtainMessage = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_SWITCH_REJECTED, secFpMsg);
                obtainMessage.setAsynchronous(true);
                if (secFpMsg.type == 2 && (secFaceMsg = (SecFaceMsg) keyguardSecUpdateMonitorImpl.mFaceMessages.peek()) != null && secFaceMsg.type == 2) {
                    keyguardSecUpdateMonitorImpl.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg);
                    message = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg);
                    message.setAsynchronous(true);
                } else {
                    message = null;
                }
                keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(obtainMessage);
                if (message != null) {
                    keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(message);
                    break;
                }
                break;
            case 1:
                SecFaceMsg secFaceMsg2 = (SecFaceMsg) obj;
                keyguardSecUpdateMonitorImpl.mFaceMessages.add(secFaceMsg2);
                Message obtainMessage2 = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg2);
                obtainMessage2.setAsynchronous(true);
                keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(obtainMessage2);
                break;
            case 2:
                ((KeyguardUpdateMonitorCallback) obj).onLockDisabledChanged(keyguardSecUpdateMonitorImpl.mLockscreenDisabled);
                break;
            case 3:
                ((KeyguardUpdateMonitorCallback) obj).onDreamingStateChanged(keyguardSecUpdateMonitorImpl.mIsDreaming);
                break;
            case 4:
                ((KeyguardUpdateMonitorCallback) obj).onSimulationFailToUnlock(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId());
                break;
            case 5:
                ((KeyguardUpdateMonitorCallback) obj).onKeyguardBouncerStateChanged(keyguardSecUpdateMonitorImpl.mPrimaryBouncerIsOrWillBeShowing);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onKeyguardBouncerFullyShowingChanged(keyguardSecUpdateMonitorImpl.mPrimaryBouncerFullyShown);
                break;
        }
    }
}
