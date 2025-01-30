package com.android.systemui.dreams.touch.scrim;

import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScrimManager {
    public final ScrimController mBouncerScrimController;
    public final ScrimController mBouncerlessScrimController;
    public final HashSet mCallbacks;
    public ScrimController mCurrentController;
    public final Executor mExecutor;
    public final C12941 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.dreams.touch.scrim.ScrimManager$1 */
    public final class C12941 implements KeyguardStateController.Callback {
        public C12941() {
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            ScrimManager.this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.touch.scrim.ScrimManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScrimManager.this.updateController();
                }
            });
        }
    }

    public ScrimManager(Executor executor, ScrimController scrimController, ScrimController scrimController2, KeyguardStateController keyguardStateController) {
        C12941 c12941 = new C12941();
        this.mKeyguardStateCallback = c12941;
        this.mExecutor = executor;
        this.mCallbacks = new HashSet();
        this.mBouncerlessScrimController = scrimController2;
        this.mBouncerScrimController = scrimController;
        this.mKeyguardStateController = keyguardStateController;
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(c12941);
        updateController();
    }

    public final void updateController() {
        ScrimController scrimController = this.mCurrentController;
        ScrimController scrimController2 = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mCanDismissLockScreen ? this.mBouncerlessScrimController : this.mBouncerScrimController;
        this.mCurrentController = scrimController2;
        if (scrimController == scrimController2) {
            return;
        }
        this.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.dreams.touch.scrim.ScrimManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ScrimController scrimController3 = ScrimManager.this.mCurrentController;
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = ((BouncerSwipeTouchHandler.C12851) obj).this$0;
                ScrimController scrimController4 = bouncerSwipeTouchHandler.mCurrentScrimController;
                if (scrimController4 != null) {
                    scrimController4.reset();
                }
                bouncerSwipeTouchHandler.mCurrentScrimController = scrimController3;
            }
        });
    }
}
