package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.ExitTransitionCoordinator;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActionExecutor {
    public final CoroutineScope applicationScope;
    public final Function0 finishDismiss;
    public final ActionIntentExecutor intentExecutor;
    public boolean isPendingSharedTransition;
    public final ScreenshotShelfViewProxy viewProxy;
    public final Window window;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        ActionExecutor create(Window window, ScreenshotShelfViewProxy screenshotShelfViewProxy, Function0 function0);
    }

    static {
        new Companion(null);
    }

    public ActionExecutor(ActionIntentExecutor actionIntentExecutor, CoroutineScope coroutineScope, Window window, ScreenshotShelfViewProxy screenshotShelfViewProxy, Function0 function0) {
        this.intentExecutor = actionIntentExecutor;
        this.applicationScope = coroutineScope;
        this.window = window;
        this.viewProxy = screenshotShelfViewProxy;
        this.finishDismiss = function0;
    }

    public final void startSharedTransition(Intent intent, UserHandle userHandle, boolean z) {
        this.isPendingSharedTransition = true;
        ScreenshotShelfViewProxy screenshotShelfViewProxy = this.viewProxy;
        final ScreenshotAnimationController screenshotAnimationController = screenshotShelfViewProxy.animationController;
        Animator animator = screenshotAnimationController.animator;
        if (animator != null) {
            animator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$fadeForSharedTransition$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Iterator it = ScreenshotAnimationController.this.fadeUI.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            }
        });
        screenshotAnimationController.animator = ofFloat;
        ofFloat.start();
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new ActionExecutor$startSharedTransition$1(this, intent, userHandle, z, ActivityOptions.startSharedElementAnimation(this.window, new ExitTransitionCoordinator.ExitTransitionCallbacks() { // from class: com.android.systemui.screenshot.ActionExecutor$createWindowTransition$callbacks$1
            public final void hideSharedElements() {
                ActionExecutor actionExecutor = ActionExecutor.this;
                actionExecutor.isPendingSharedTransition = false;
                actionExecutor.finishDismiss.invoke();
            }

            public final boolean isReturnTransitionAllowed() {
                return false;
            }

            public final void onFinish() {
            }
        }, null, new Pair[]{Pair.create(screenshotShelfViewProxy.screenshotPreview, "screenshot_preview_image")}), null), 6);
    }
}
