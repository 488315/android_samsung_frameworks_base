package com.android.systemui.communal.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
final class CommunalSceneInteractor$rotatedToPortrait$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;

    public CommunalSceneInteractor$rotatedToPortrait$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int iIntValue = ((Number) obj2).intValue();
        CommunalSceneInteractor$rotatedToPortrait$1 communalSceneInteractor$rotatedToPortrait$1 = new CommunalSceneInteractor$rotatedToPortrait$1((Continuation) obj3);
        communalSceneInteractor$rotatedToPortrait$1.L$0 = obj;
        communalSceneInteractor$rotatedToPortrait$1.I$0 = iIntValue;
        return communalSceneInteractor$rotatedToPortrait$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object obj2 = this.L$0;
        int i = this.I$0;
        if (Intrinsics.areEqual(obj2, new Integer(2))) {
            z = i == 1;
        }
        return Boolean.valueOf(z);
    }
}
