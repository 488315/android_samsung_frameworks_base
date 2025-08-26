package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import com.android.systemui.util.ui.AnimatableEvent;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class FooterViewModel$clearAllButton$5 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public FooterViewModel$clearAllButton$5(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        FooterViewModel$clearAllButton$5 footerViewModel$clearAllButton$5 = new FooterViewModel$clearAllButton$5((Continuation) obj3);
        footerViewModel$clearAllButton$5.Z$0 = zBooleanValue;
        footerViewModel$clearAllButton$5.L$0 = (Pair) obj2;
        return footerViewModel$clearAllButton$5.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        Pair pair = (Pair) this.L$0;
        return new AnimatableEvent(Boolean.valueOf(z), ((Boolean) pair.component1()).booleanValue() && ((Boolean) pair.component2()).booleanValue());
    }
}
