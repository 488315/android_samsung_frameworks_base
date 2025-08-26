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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ActionExecutor {
    public final CoroutineScope applicationScope;
    public final Function0 finishDismiss;
    public final ActionIntentExecutor intentExecutor;
    public boolean isPendingSharedTransition;
    public final ScreenshotShelfViewProxy viewProxy;
    public final Window window;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        ActionExecutor create(Window window, ScreenshotShelfViewProxy screenshotShelfViewProxy, Function0 function0);
    }

    /* renamed from: com.android.systemui.screenshot.ActionExecutor$startSharedTransition$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Intent $intent;
        final /* synthetic */ boolean $overrideTransition;
        final /* synthetic */ UserHandle $user;
        final /* synthetic */ Pair<ActivityOptions, ExitTransitionCoordinator> $windowTransition;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Intent intent, UserHandle userHandle, boolean z, Pair<ActivityOptions, ExitTransitionCoordinator> pair, Continuation continuation) {
            super(2, continuation);
            this.$intent = intent;
            this.$user = userHandle;
            this.$overrideTransition = z;
            this.$windowTransition = pair;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActionExecutor.this.new AnonymousClass1(this.$intent, this.$user, this.$overrideTransition, this.$windowTransition, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ActionIntentExecutor actionIntentExecutor = ActionExecutor.this.intentExecutor;
                Intent intent = this.$intent;
                UserHandle userHandle = this.$user;
                boolean z = this.$overrideTransition;
                Pair<ActivityOptions, ExitTransitionCoordinator> pair = this.$windowTransition;
                ActivityOptions activityOptions = (ActivityOptions) pair.first;
                ExitTransitionCoordinator exitTransitionCoordinator = (ExitTransitionCoordinator) pair.second;
                this.label = 1;
                if (actionIntentExecutor.launchIntent(intent, userHandle, z, activityOptions, exitTransitionCoordinator, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
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
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$fadeForSharedTransition$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Iterator it = screenshotAnimationController.fadeUI.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            }
        });
        screenshotAnimationController.animator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.start();
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(intent, userHandle, z, ActivityOptions.startSharedElementAnimation(this.window, new ExitTransitionCoordinator.ExitTransitionCallbacks() { // from class: com.android.systemui.screenshot.ActionExecutor$createWindowTransition$callbacks$1
            public final void hideSharedElements() {
                ActionExecutor actionExecutor = this.this$0;
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
