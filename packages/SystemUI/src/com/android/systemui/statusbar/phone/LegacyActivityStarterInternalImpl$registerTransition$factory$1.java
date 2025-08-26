package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import android.view.View;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import java.util.Optional;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.animation.ActivityTransitionAnimator.ControllerFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object createController(boolean z, ContinuationImpl continuationImpl) {
        LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1;
        if (continuationImpl instanceof LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1) {
            legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 = (LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1) continuationImpl;
            int i = legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.label = i - Integer.MIN_VALUE;
            } else {
                legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1 = new LegacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1(this, continuationImpl);
            }
        }
        Object objCreateController = legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objCreateController);
            legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.L$0 = this;
            legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.label = 1;
            objCreateController = this.$controllerFactory.createController(z, legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1);
            if (objCreateController == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (LegacyActivityStarterInternalImpl$registerTransition$factory$1) legacyActivityStarterInternalImpl$registerTransition$factory$1$createController$1.L$0;
            ResultKt.throwOnFailure(objCreateController);
        }
        ActivityTransitionAnimator.Controller controller = (ActivityTransitionAnimator.Controller) objCreateController;
        View rootView = controller.getTransitionContainer().getRootView();
        StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.this$0.statusBarWindowControllerStore.getDefaultDisplay();
        rootView.getClass();
        Optional optionalWrapAnimationControllerIfInStatusBar = ((StatusBarWindowControllerImpl) statusBarWindowController).wrapAnimationControllerIfInStatusBar(rootView, controller);
        if (!optionalWrapAnimationControllerIfInStatusBar.isPresent()) {
            return controller;
        }
        Object obj = optionalWrapAnimationControllerIfInStatusBar.get();
        obj.getClass();
        return (ActivityTransitionAnimator.Controller) obj;
    }
}
