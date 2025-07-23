package com.android.systemui.communal.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int intValue = ((Number) obj2).intValue();
        CommunalSceneInteractor$rotatedToPortrait$1 communalSceneInteractor$rotatedToPortrait$1 = new CommunalSceneInteractor$rotatedToPortrait$1((Continuation) obj3);
        communalSceneInteractor$rotatedToPortrait$1.L$0 = obj;
        communalSceneInteractor$rotatedToPortrait$1.I$0 = intValue;
        return communalSceneInteractor$rotatedToPortrait$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001a, code lost:
    
        if (r2 == 1) goto L10;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r3) {
        /*
            r2 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r2.label
            if (r0 != 0) goto L23
            kotlin.ResultKt.throwOnFailure(r3)
            java.lang.Object r3 = r2.L$0
            int r2 = r2.I$0
            java.lang.Integer r0 = new java.lang.Integer
            r1 = 2
            r0.<init>(r1)
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            if (r3 == 0) goto L1d
            r3 = 1
            if (r2 != r3) goto L1d
            goto L1e
        L1d:
            r3 = 0
        L1e:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r3)
            return r2
        L23:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.CommunalSceneInteractor$rotatedToPortrait$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
