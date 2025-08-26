package com.android.keyguard;

import android.os.Message;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.bio.face.SemBioFaceManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecUpdateMonitorImpl f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecUpdateMonitorImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Message messageObtainMessage;
        SecFaceMsg secFaceMsg;
        int i = this.$r8$classId;
        KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
        switch (i) {
            case 0:
                SecFpMsg secFpMsg = (SecFpMsg) obj;
                keyguardSecUpdateMonitorImpl.mFpMessages.add(secFpMsg);
                Message messageObtainMessage2 = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_SWITCH_REJECTED, secFpMsg);
                messageObtainMessage2.setAsynchronous(true);
                if (secFpMsg.type == 2 && (secFaceMsg = (SecFaceMsg) keyguardSecUpdateMonitorImpl.mFaceMessages.peek()) != null && secFaceMsg.type == 2) {
                    keyguardSecUpdateMonitorImpl.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg);
                    messageObtainMessage = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg);
                    messageObtainMessage.setAsynchronous(true);
                } else {
                    messageObtainMessage = null;
                }
                keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage2);
                if (messageObtainMessage != null) {
                    keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage);
                    break;
                }
                break;
            case 1:
                SecFaceMsg secFaceMsg2 = (SecFaceMsg) obj;
                keyguardSecUpdateMonitorImpl.mFaceMessages.add(secFaceMsg2);
                Message messageObtainMessage3 = keyguardSecUpdateMonitorImpl.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_HOLD_FAILED, secFaceMsg2);
                messageObtainMessage3.setAsynchronous(true);
                keyguardSecUpdateMonitorImpl.mHandler.sendMessageAtFrontOfQueue(messageObtainMessage3);
                break;
            case 2:
                ((KeyguardUpdateMonitorCallback) obj).onLockDisabledChanged(keyguardSecUpdateMonitorImpl.mLockscreenDisabled);
                break;
            case 3:
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onKeyguardBouncerStateChanged(keyguardSecUpdateMonitorImpl.mPrimaryBouncerIsOrWillBeShowing);
                break;
            case 4:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onKeyguardBouncerFullyShowingChanged(keyguardSecUpdateMonitorImpl.mPrimaryBouncerFullyShown);
                break;
            case 5:
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onDreamingStateChanged(keyguardSecUpdateMonitorImpl.mIsDreaming);
                break;
            default:
                SemBioFaceManager semBioFaceManager4 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                ((KeyguardUpdateMonitorCallback) obj).onSimulationFailToUnlock(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId());
                break;
        }
    }
}
