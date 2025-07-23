package com.android.systemui.statusbar.events;

import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.util.Assert;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
            boolean booleanValue = ((Boolean) ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) systemStatusAnimationSchedulerImpl.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode.$$delegate_0.getValue()).booleanValue();
            StatusBarWindowControllerStore statusBarWindowControllerStore = systemStatusAnimationSchedulerImpl.statusBarWindowControllerStore;
            if (booleanValue) {
                boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
                ArrayList arrayList = new ArrayList();
                Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
                while (it.hasNext()) {
                    Animator onPrepareSystemEventAnimation = ((SystemStatusAnimationCallback) it.next()).onPrepareSystemEventAnimation(z);
                    if (onPrepareSystemEventAnimation != null) {
                        arrayList.add(onPrepareSystemEventAnimation);
                    }
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 0.0f);
                if (systemStatusAnimationSchedulerImpl.hasPersistentDot || (systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent)) {
                    ((StatusBarWindowControllerImpl) ((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(true);
                }
                arrayList.add(ofFloat);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipAppearAnimationUsingAnimator$1
                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                        systemStatusAnimationSchedulerImpl2._animationState.setValue(SystemEventAnimationState.AnimatingIn);
                        SpringAnimatorSet collectStartAnimations = systemStatusAnimationSchedulerImpl2.collectStartAnimations(systemStatusAnimationSchedulerImpl2.statusBarHidden);
                        if (collectStartAnimations.getTotalDuration() > 500) {
                            throw new IllegalStateException(ValueAnimator$$ExternalSyntheticOutline0.m("System animation total length exceeds budget. Expected: 500, actual: ", collectStartAnimations.getTotalDuration()));
                        }
                        collectStartAnimations.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipAppearAnimationUsingAnimator$1$onAnimationEnd$1
                            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator2) {
                                SystemStatusAnimationSchedulerImpl.this._animationState.setValue(SystemEventAnimationState.RunningChipAnim);
                            }
                        });
                        collectStartAnimations.start();
                    }
                });
                animatorSet.start();
            } else {
                if (systemStatusAnimationSchedulerImpl.hasPersistentDot || (systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent)) {
                    ((StatusBarWindowControllerImpl) ((StatusBarWindowController) statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(true);
                }
                systemStatusAnimationSchedulerImpl._animationState.setValue(SystemEventAnimationState.AnimatingIn);
                SpringAnimatorSet collectStartAnimations = systemStatusAnimationSchedulerImpl.collectStartAnimations(systemStatusAnimationSchedulerImpl.statusBarHidden);
                if (collectStartAnimations.getTotalDuration() > 500) {
                    throw new IllegalStateException(ValueAnimator$$ExternalSyntheticOutline0.m("System animation total length exceeds budget. Expected: 500, actual: ", collectStartAnimations.getTotalDuration()));
                }
                collectStartAnimations.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipAppearAnimation$1
                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SystemStatusAnimationSchedulerImpl.this._animationState.setValue(SystemEventAnimationState.RunningChipAnim);
                    }
                });
                collectStartAnimations.start();
            }
            SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = this.this$0;
            StatusEvent statusEvent = this.$event;
            systemStatusAnimationSchedulerImpl2.getClass();
            statusEvent.getContentDescription();
            long j = this.this$0.currentlyDisplayedEvent instanceof BatteryEvent ? 3000L : 1500L;
            this.label = 1;
            if (DelayKt.delay(j + 500, this) == coroutineSingletons) {
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
