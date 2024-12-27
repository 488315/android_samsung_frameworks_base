package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import com.android.systemui.util.ui.AnimatableEvent;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class FooterViewModel$clearAllButton$4 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public FooterViewModel$clearAllButton$4(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        FooterViewModel$clearAllButton$4 footerViewModel$clearAllButton$4 = new FooterViewModel$clearAllButton$4((Continuation) obj3);
        footerViewModel$clearAllButton$4.Z$0 = booleanValue;
        footerViewModel$clearAllButton$4.L$0 = (Pair) obj2;
        return footerViewModel$clearAllButton$4.invokeSuspend(Unit.INSTANCE);
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
