package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DelayableMarqueeTextView;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ StatusEvent $event;
    int label;
    final /* synthetic */ SystemStatusAnimationSchedulerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl, StatusEvent statusEvent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = systemStatusAnimationSchedulerImpl;
        this.$event = statusEvent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(this.this$0, this.$event, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = this.this$0;
            int i2 = SystemStatusAnimationSchedulerImpl.$r8$clinit;
            systemStatusAnimationSchedulerImpl.getClass();
            Assert.isMainThread();
            if (systemStatusAnimationSchedulerImpl.hasPersistentDot || (systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent)) {
                systemStatusAnimationSchedulerImpl.statusBarWindowController.setForceStatusBarVisible(true);
            }
            systemStatusAnimationSchedulerImpl.animationState.updateState(null, 2);
            boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
            ArrayList arrayList = new ArrayList();
            boolean z2 = systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent;
            Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
            while (it.hasNext()) {
                Animator onSystemEventAnimationBegin = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationBegin(z, z2);
                if (onSystemEventAnimationBegin != null) {
                    arrayList.add(onSystemEventAnimationBegin);
                }
            }
            arrayList.add(systemStatusAnimationSchedulerImpl.chipAnimationController.onSystemEventAnimationBegin(z, z2));
            systemStatusAnimationSchedulerImpl.headerBatteryChipController.onSystemEventAnimationBegin(z, z2);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            if (animatorSet.getTotalDuration() > 500) {
                throw new IllegalStateException(ValueAnimator$$ExternalSyntheticOutline0.m("System animation total length exceeds budget. Expected: 500, actual: ", animatorSet.getTotalDuration()));
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipAppearAnimation$1
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SystemStatusAnimationSchedulerImpl.this.animationState.updateState(null, 3);
                }
            });
            animatorSet.start();
            SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = this.this$0;
            StatusEvent statusEvent = this.$event;
            systemStatusAnimationSchedulerImpl2.getClass();
            statusEvent.getContentDescription();
            this.label = 1;
            if (DelayKt.delay(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        SystemStatusAnimationSchedulerImpl.access$runChipDisappearAnimation(this.this$0);
        return Unit.INSTANCE;
    }
}
