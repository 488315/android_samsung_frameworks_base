package com.android.systemui.qs.tiles;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ModesTile$handleSecondaryClick$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ModesTile this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModesTile$handleSecondaryClick$1(ModesTile modesTile, Continuation continuation) {
        super(2, continuation);
        this.this$0 = modesTile;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModesTile$handleSecondaryClick$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModesTile$handleSecondaryClick$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0045, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0029, code lost:
    
        if (r5 == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L48
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L2c
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.qs.tiles.ModesTile r5 = r4.this$0
            com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileDataInteractor r5 = r5.dataInteractor
            r4.label = r3
            java.lang.Object r5 = r5.getCurrentTileModel(r4)
            if (r5 != r0) goto L2c
            goto L47
        L2c:
            com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel r5 = (com.android.systemui.qs.tiles.impl.modes.domain.model.ModesTileModel) r5
            com.android.systemui.qs.tiles.ModesTile r5 = r4.this$0
            com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesTileUserActionInteractor r5 = r5.userActionInteractor
            r4.label = r2
            r5.getClass()
            com.android.systemui.flags.RefactorFlagUtils r4 = com.android.systemui.flags.RefactorFlagUtils.INSTANCE
            int r5 = com.android.systemui.qs.flags.QSComposeFragment.$r8$clinit
            r4.getClass()
            java.lang.String r4 = "New code path expects com.android.systemui.qs_ui_refactor_compose_fragment to be enabled."
            com.android.systemui.flags.RefactorFlagUtils.assertOnEngBuild(r4)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            if (r4 != r0) goto L48
        L47:
            return r0
        L48:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.ModesTile$handleSecondaryClick$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
