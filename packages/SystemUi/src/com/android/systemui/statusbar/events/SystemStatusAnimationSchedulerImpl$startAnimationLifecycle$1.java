package com.android.systemui.statusbar.events;

import android.util.Log;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1", m277f = "SystemStatusAnimationSchedulerImpl.kt", m278l = {318}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SystemStatusAnimationSchedulerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl, Continuation<? super SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1> continuation) {
        super(2, continuation);
        this.this$0 = systemStatusAnimationSchedulerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(this.this$0, continuation);
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
            systemStatusAnimationSchedulerImpl.isKeyguardVisible = systemStatusAnimationSchedulerImpl.keyguardStateController.isVisible();
            if (systemStatusAnimationSchedulerImpl.hasPersistentDot) {
                systemStatusAnimationSchedulerImpl.statusBarWindowController.setForceStatusBarVisible(true);
            }
            systemStatusAnimationSchedulerImpl.animationState.setValue(2);
            boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
            ArrayList arrayList = new ArrayList();
            if (systemStatusAnimationSchedulerImpl.isKeyguardVisible) {
                Log.d("SystemStatusAnimationSchedulerImpl", "Privacy chip animation is skipped because keyguard is visible");
            } else {
                Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
                while (it.hasNext()) {
                    Animator onSystemEventAnimationBegin = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationBegin(z);
                    if (onSystemEventAnimationBegin != null) {
                        arrayList.add(onSystemEventAnimationBegin);
                    }
                }
                arrayList.add(systemStatusAnimationSchedulerImpl.chipAnimationController.onSystemEventAnimationBegin(z));
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(arrayList);
            if (animatorSet.getTotalDuration() > 500) {
                throw new IllegalStateException(ValueAnimator$$ExternalSyntheticOutline0.m25m("System animation total length exceeds budget. Expected: 500, actual: ", animatorSet.getTotalDuration()));
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipAppearAnimation$1
                @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                public final void onAnimationEnd$1(Animator animator) {
                    SystemStatusAnimationSchedulerImpl.this.animationState.setValue(3);
                }
            });
            animatorSet.start();
            long j = SystemStatusAnimationSchedulerKt.DISPLAY_LENGTH + 500;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
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
