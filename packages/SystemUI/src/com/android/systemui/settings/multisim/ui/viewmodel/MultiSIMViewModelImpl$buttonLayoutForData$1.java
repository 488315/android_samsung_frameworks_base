package com.android.systemui.settings.multisim.ui.viewmodel;

import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiSIMViewModelImpl$buttonLayoutForData$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MultiSIMViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiSIMViewModelImpl$buttonLayoutForData$1(MultiSIMViewModelImpl multiSIMViewModelImpl, Continuation continuation) {
        super(4, continuation);
        this.this$0 = multiSIMViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        MultiSIMViewModelImpl$buttonLayoutForData$1 multiSIMViewModelImpl$buttonLayoutForData$1 = new MultiSIMViewModelImpl$buttonLayoutForData$1(this.this$0, (Continuation) obj4);
        multiSIMViewModelImpl$buttonLayoutForData$1.Z$0 = booleanValue;
        multiSIMViewModelImpl$buttonLayoutForData$1.I$0 = intValue;
        return multiSIMViewModelImpl$buttonLayoutForData$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).isDataBlocked(this.I$0) ? Button.Layout.HIDEDETAILS : this.Z$0 ? Button.Layout.NORMAL : Button.Layout.TEXTONLY_2;
    }
}
