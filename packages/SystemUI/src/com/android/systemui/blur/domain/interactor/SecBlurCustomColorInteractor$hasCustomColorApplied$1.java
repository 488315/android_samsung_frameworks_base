package com.android.systemui.blur.domain.interactor;

import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecBlurCustomColorInteractor$hasCustomColorApplied$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SecBlurCustomColorInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecBlurCustomColorInteractor$hasCustomColorApplied$1(SecBlurCustomColorInteractor secBlurCustomColorInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = secBlurCustomColorInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        SecBlurCustomColorInteractor$hasCustomColorApplied$1 secBlurCustomColorInteractor$hasCustomColorApplied$1 = new SecBlurCustomColorInteractor$hasCustomColorApplied$1(this.this$0, (Continuation) obj3);
        secBlurCustomColorInteractor$hasCustomColorApplied$1.L$0 = (Triple) obj;
        secBlurCustomColorInteractor$hasCustomColorApplied$1.Z$0 = booleanValue;
        return secBlurCustomColorInteractor$hasCustomColorApplied$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0090, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(java.lang.Integer.toHexString(r6.context.getColor(com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor.bgOverlayColorId)), java.lang.Integer.toHexString(r6.context.getColor(r0))) != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0095, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x009a, code lost:
    
        return java.lang.Boolean.valueOf(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0092, code lost:
    
        if (r3 != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0035, code lost:
    
        if ((r4 & 32) != 0) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0076  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r6.label
            if (r0 != 0) goto L9b
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlin.Triple r7 = (kotlin.Triple) r7
            boolean r0 = r6.Z$0
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L3a
            com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor r3 = r6.this$0
            android.content.Context r3 = r3.context
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131034257(0x7f050091, float:1.7679026E38)
            boolean r3 = r3.getBoolean(r4)
            if (r3 != 0) goto L38
            com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor r3 = r6.this$0
            java.lang.Object r4 = r7.getFirst()
            java.lang.Number r4 = (java.lang.Number) r4
            int r4 = r4.intValue()
            r3.getClass()
            r3 = r4 & 32
            if (r3 == 0) goto L38
            goto L3a
        L38:
            r3 = r2
            goto L3b
        L3a:
            r3 = r1
        L3b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "configurationChanged = "
            r4.<init>(r5)
            r4.append(r7)
            java.lang.String r7 = " , minimalBatteryUse = "
            r4.append(r7)
            r4.append(r0)
            java.lang.String r7 = ", needToIgnore = "
            r4.append(r7)
            java.lang.String r7 = "SecBlurCustomColorInteractor"
            androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(r4, r3, r7)
            com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor r7 = r6.this$0
            kotlinx.coroutines.flow.SharedFlowImpl r7 = r7.updateBackgroundColor
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r7.tryEmit(r0)
            com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor r6 = r6.this$0
            android.content.Context r7 = r6.context
            int r0 = com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor.backgroundColorId
            int r7 = r7.getColor(r0)
            java.lang.String r7 = java.lang.Integer.toHexString(r7)
            java.lang.String r4 = "ff000000"
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r4)
            if (r7 == 0) goto L92
            android.content.Context r7 = r6.context
            int r7 = r7.getColor(r0)
            java.lang.String r7 = java.lang.Integer.toHexString(r7)
            android.content.Context r6 = r6.context
            int r0 = com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor.bgOverlayColorId
            int r6 = r6.getColor(r0)
            java.lang.String r6 = java.lang.Integer.toHexString(r6)
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
            if (r6 == 0) goto L95
        L92:
            if (r3 != 0) goto L95
            goto L96
        L95:
            r1 = r2
        L96:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r1)
            return r6
        L9b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor$hasCustomColorApplied$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
