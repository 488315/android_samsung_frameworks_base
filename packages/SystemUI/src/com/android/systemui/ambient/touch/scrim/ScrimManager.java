package com.android.systemui.ambient.touch.scrim;

import com.android.systemui.ambient.touch.BouncerSwipeTouchHandler;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ScrimManager {
    public final ScrimController mBouncerScrimController;
    public final ScrimController mBouncerlessScrimController;
    public final HashSet mCallbacks;
    public ScrimController mCurrentController;
    public final Executor mExecutor;
    public final AnonymousClass1 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.ambient.touch.scrim.ScrimManager$1, reason: invalid class name */
    public final class AnonymousClass1 implements KeyguardStateController.Callback {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            ScrimManager.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ambient.touch.scrim.ScrimManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScrimManager.this.updateController();
                }
            });
        }
    }

    public ScrimManager(Executor executor, ScrimController scrimController, ScrimController scrimController2, KeyguardStateController keyguardStateController) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mExecutor = executor;
        this.mCallbacks = new HashSet();
        this.mBouncerlessScrimController = scrimController2;
        this.mBouncerScrimController = scrimController;
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(anonymousClass1);
        updateController();
    }

    public final void updateController() {
        ScrimController scrimController = this.mCurrentController;
        ScrimController scrimController2 = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mCanDismissLockScreen ? this.mBouncerlessScrimController : this.mBouncerScrimController;
        this.mCurrentController = scrimController2;
        if (scrimController == scrimController2) {
            return;
        }
        this.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.ambient.touch.scrim.ScrimManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ScrimController scrimController3 = ScrimManager.this.mCurrentController;
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = ((BouncerSwipeTouchHandler.AnonymousClass1) obj).this$0;
                ScrimController scrimController4 = bouncerSwipeTouchHandler.mCurrentScrimController;
                if (scrimController4 != null) {
                    scrimController4.reset$1();
                }
                bouncerSwipeTouchHandler.mCurrentScrimController = scrimController3;
            }
        });
    }
}
