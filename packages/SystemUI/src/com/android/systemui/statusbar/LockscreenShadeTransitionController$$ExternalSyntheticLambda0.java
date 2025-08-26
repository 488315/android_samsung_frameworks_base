package com.android.systemui.statusbar;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger$$ExternalSyntheticLambda0;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class LockscreenShadeTransitionController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LockscreenShadeTransitionController f$0;

    public /* synthetic */ LockscreenShadeTransitionController$$ExternalSyntheticLambda0(LockscreenShadeTransitionController lockscreenShadeTransitionController, int i) {
        this.$r8$classId = i;
        this.f$0 = lockscreenShadeTransitionController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                LockscreenShadeTransitionController lockscreenShadeTransitionController = this.f$0;
                LSShadeTransitionLogger lSShadeTransitionLogger = lockscreenShadeTransitionController.logger;
                lSShadeTransitionLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                LSShadeTransitionLogger$$ExternalSyntheticLambda0 lSShadeTransitionLogger$$ExternalSyntheticLambda0 = new LSShadeTransitionLogger$$ExternalSyntheticLambda0(11);
                LogBuffer logBuffer = lSShadeTransitionLogger.buffer;
                logBuffer.commit(logBuffer.obtain("LockscreenShadeTransitionController", logLevel, lSShadeTransitionLogger$$ExternalSyntheticLambda0, null));
                lockscreenShadeTransitionController.setDragDownAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(0.0f);
                lockscreenShadeTransitionController.forceApplyAmount = false;
                return Unit.INSTANCE;
            case 1:
                return this.f$0.qS;
            case 2:
                return this.f$0.qS;
            case 3:
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0.nsslController;
                if (notificationStackScrollLayoutController == null) {
                    return null;
                }
                return notificationStackScrollLayoutController;
            case 4:
                LockscreenShadeTransitionController lockscreenShadeTransitionController2 = this.f$0;
                return lockscreenShadeTransitionController2.splitShadeOverScrollerFactory.create(new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(lockscreenShadeTransitionController2, 2), new LockscreenShadeTransitionController$$ExternalSyntheticLambda0(lockscreenShadeTransitionController2, 3));
            case 5:
                LockscreenShadeTransitionController lockscreenShadeTransitionController3 = this.f$0;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = lockscreenShadeTransitionController3.nsslController;
                if (notificationStackScrollLayoutController2 == null) {
                    notificationStackScrollLayoutController2 = null;
                }
                return lockscreenShadeTransitionController3.singleShadeOverScrollerFactory.create(notificationStackScrollLayoutController2);
            default:
                LockscreenShadeTransitionController lockscreenShadeTransitionController4 = this.f$0;
                return lockscreenShadeTransitionController4.keyguardTransitionControllerFactory.create((ShadeLockscreenInteractor) lockscreenShadeTransitionController4.shadeLockscreenInteractorLazy.get());
        }
    }
}
