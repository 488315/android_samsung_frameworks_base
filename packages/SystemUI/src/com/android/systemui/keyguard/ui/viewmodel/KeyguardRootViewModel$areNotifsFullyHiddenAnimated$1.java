package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.ui.AnimatableEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardRootViewModel this$0;

    public KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1(KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardRootViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1 keyguardRootViewModel$areNotifsFullyHiddenAnimated$1 = new KeyguardRootViewModel$areNotifsFullyHiddenAnimated$1(this.this$0, (Continuation) obj3);
        keyguardRootViewModel$areNotifsFullyHiddenAnimated$1.L$0 = (WithPrev) obj;
        keyguardRootViewModel$areNotifsFullyHiddenAnimated$1.Z$0 = booleanValue;
        return keyguardRootViewModel$areNotifsFullyHiddenAnimated$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WithPrev withPrev = (WithPrev) this.L$0;
        boolean z = this.Z$0;
        Boolean bool = (Boolean) withPrev.component1();
        Boolean bool2 = (Boolean) withPrev.component2();
        bool2.booleanValue();
        boolean z2 = false;
        if (bool != null) {
            if (!z) {
                if (this.this$0.dozeParameters.getAlwaysOn() && !this.this$0.dozeParameters.getDisplayNeedsBlanking()) {
                    Flags.FEATURE_FLAGS.getClass();
                }
            }
            z2 = true;
        }
        return new AnimatableEvent(bool2, z2);
    }
}
