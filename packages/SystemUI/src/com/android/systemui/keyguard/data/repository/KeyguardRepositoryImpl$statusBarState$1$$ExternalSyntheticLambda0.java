package com.android.systemui.keyguard.data.repository;

import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardRepositoryImpl$statusBarState$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((StatusBarStateController) this.f$0).removeCallback((KeyguardRepositoryImpl$statusBarState$1$callback$1) this.f$1);
                return Unit.INSTANCE;
            case 1:
                ((StatusBarStateController) this.f$0).removeCallback((KeyguardRepositoryImpl$_preSceneLinearDozeAmount$1$callback$1) this.f$1);
                return Unit.INSTANCE;
            case 2:
                DozeTransitionListener dozeTransitionListener = ((KeyguardRepositoryImpl) this.f$0).dozeTransitionListener;
                KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 = (KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1) this.f$1;
                synchronized (dozeTransitionListener) {
                    dozeTransitionListener.callbacks.remove(keyguardRepositoryImpl$dozeTransitionModel$1$callback$1);
                }
                return Unit.INSTANCE;
            case 3:
                ((KeyguardRepositoryImpl) this.f$0).authController.removeCallback((KeyguardRepositoryImpl$fingerprintSensorLocation$1$callback$1) this.f$1);
                return Unit.INSTANCE;
            case 4:
                DreamOverlayCallbackController dreamOverlayCallbackController = ((KeyguardRepositoryImpl) this.f$0).dreamOverlayCallbackController;
                dreamOverlayCallbackController.callbacks.remove((KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1) this.f$1);
                return Unit.INSTANCE;
            default:
                ((KeyguardRepositoryImpl) this.f$0).keyguardUpdateMonitor.removeCallback((KeyguardRepositoryImpl$isEncryptedOrLockdown$1$callback$1) this.f$1);
                return Unit.INSTANCE;
        }
    }
}
