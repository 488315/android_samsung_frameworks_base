package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardStateControllerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardStateController.Callback) obj).onPrimaryBouncerShowingChanged();
                break;
            case 1:
                ((KeyguardStateController.Callback) obj).onKeyguardGoingAwayChanged();
                break;
            case 2:
                ((KeyguardStateController.Callback) obj).onKeyguardDismissAmountChanged();
                break;
            case 3:
                ((KeyguardStateController.Callback) obj).onLaunchTransitionFadingAwayChanged();
                break;
            case 4:
                ((KeyguardStateController.Callback) obj).onKeyguardShowingChanged();
                break;
            default:
                ((KeyguardStateController.Callback) obj).onUnlockedChanged();
                break;
        }
    }
}
