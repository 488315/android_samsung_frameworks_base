package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Consumer;

public final /* synthetic */ class KeyguardStateControllerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardStateControllerImpl$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        switch (this.$r8$classId) {
            case 0:
                callback.onKeyguardFadingAwayChanged();
                break;
            case 1:
                callback.onPrimaryBouncerShowingChanged();
                break;
            case 2:
                callback.onKeyguardShowingChanged();
                break;
            case 3:
                callback.onUnlockedChanged();
                break;
            case 4:
                callback.onKeyguardDismissAmountChanged();
                break;
            case 5:
                callback.onKeyguardGoingAwayChanged();
                break;
            case 6:
                callback.onLaunchTransitionFadingAwayChanged();
                break;
            default:
                callback.onFaceEnrolledChanged();
                break;
        }
    }
}
