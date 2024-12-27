package com.android.systemui.statusbar.phone;

import com.android.systemui.util.DelayableMarqueeTextView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecUnlockedScreenOffAnimationHelper this$0;

    public SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secUnlockedScreenOffAnimationHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
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
        this.this$0.setSkipAnimationInOthers(false);
        this.this$0.job = null;
        return Unit.INSTANCE;
    }
}
