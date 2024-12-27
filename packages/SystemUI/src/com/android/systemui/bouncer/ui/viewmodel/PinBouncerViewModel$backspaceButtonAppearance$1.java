package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PinBouncerViewModel$backspaceButtonAppearance$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ PinBouncerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinBouncerViewModel$backspaceButtonAppearance$1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = pinBouncerViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        PinBouncerViewModel$backspaceButtonAppearance$1 pinBouncerViewModel$backspaceButtonAppearance$1 = new PinBouncerViewModel$backspaceButtonAppearance$1(this.this$0, (Continuation) obj3);
        pinBouncerViewModel$backspaceButtonAppearance$1.L$0 = (PinInputViewModel) obj;
        pinBouncerViewModel$backspaceButtonAppearance$1.Z$0 = booleanValue;
        return pinBouncerViewModel$backspaceButtonAppearance$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PinInputViewModel pinInputViewModel = (PinInputViewModel) this.L$0;
        boolean z = this.Z$0;
        this.this$0.getClass();
        return (z && (CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll)) ? ActionButtonAppearance.Hidden : z ? ActionButtonAppearance.Subtle : ActionButtonAppearance.Shown;
    }
}
