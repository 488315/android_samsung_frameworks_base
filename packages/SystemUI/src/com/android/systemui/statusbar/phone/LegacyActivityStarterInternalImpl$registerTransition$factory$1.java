package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import com.android.systemui.animation.ActivityTransitionAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LegacyActivityStarterInternalImpl$registerTransition$factory$1 extends ActivityTransitionAnimator.ControllerFactory {
    public final /* synthetic */ ActivityTransitionAnimator.ControllerFactory $controllerFactory;
    public final /* synthetic */ LegacyActivityStarterInternalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyActivityStarterInternalImpl$registerTransition$factory$1(ActivityTransitionAnimator.ControllerFactory controllerFactory, LegacyActivityStarterInternalImpl legacyActivityStarterInternalImpl, ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2) {
        super(transitionCookie, componentName, num, num2);
        this.$controllerFactory = controllerFactory;
        this.this$0 = legacyActivityStarterInternalImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0070 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.animation.ActivityTransitionAnimator.ControllerFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object createController(boolean r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 r0 = (com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 r0 = new com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1 r4 = (com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L43
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.label = r3
            com.android.systemui.animation.ActivityTransitionAnimator$ControllerFactory r6 = r4.$controllerFactory
            java.lang.Object r6 = r6.createController(r5, r0)
            if (r6 != r1) goto L43
            return r1
        L43:
            com.android.systemui.animation.ActivityTransitionAnimator$Controller r6 = (com.android.systemui.animation.ActivityTransitionAnimator.Controller) r6
            android.view.ViewGroup r5 = r6.getTransitionContainer()
            android.view.View r5 = r5.getRootView()
            com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl r4 = r4.this$0
            com.android.systemui.statusbar.window.StatusBarWindowControllerStore r4 = r4.statusBarWindowControllerStore
            java.lang.Object r4 = r4.getDefaultDisplay()
            com.android.systemui.statusbar.window.StatusBarWindowController r4 = (com.android.systemui.statusbar.window.StatusBarWindowController) r4
            r5.getClass()
            com.android.systemui.statusbar.window.StatusBarWindowControllerImpl r4 = (com.android.systemui.statusbar.window.StatusBarWindowControllerImpl) r4
            java.util.Optional r4 = r4.wrapAnimationControllerIfInStatusBar(r5, r6)
            boolean r5 = r4.isPresent()
            if (r5 == 0) goto L70
            java.lang.Object r4 = r4.get()
            r4.getClass()
            com.android.systemui.animation.ActivityTransitionAnimator$Controller r4 = (com.android.systemui.animation.ActivityTransitionAnimator.Controller) r4
            return r4
        L70:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.LegacyActivityStarterInternalImpl$registerTransition$factory$1.createController(boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
