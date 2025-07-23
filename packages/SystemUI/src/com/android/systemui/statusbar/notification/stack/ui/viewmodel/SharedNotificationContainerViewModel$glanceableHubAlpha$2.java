package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$glanceableHubAlpha$2 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public SharedNotificationContainerViewModel$glanceableHubAlpha$2(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        float floatValue = ((Number) obj5).floatValue();
        SharedNotificationContainerViewModel$glanceableHubAlpha$2 sharedNotificationContainerViewModel$glanceableHubAlpha$2 = new SharedNotificationContainerViewModel$glanceableHubAlpha$2((Continuation) obj6);
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.L$0 = (FlowCollector) obj;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$0 = booleanValue;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$1 = booleanValue2;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.Z$2 = booleanValue3;
        sharedNotificationContainerViewModel$glanceableHubAlpha$2.F$0 = floatValue;
        return sharedNotificationContainerViewModel$glanceableHubAlpha$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x005f, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003d, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1c
            if (r1 == r4) goto L18
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            goto L18
        L10:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L18:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L60
        L1c:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            boolean r1 = r8.Z$0
            boolean r5 = r8.Z$1
            boolean r6 = r8.Z$2
            float r7 = r8.F$0
            if (r1 != 0) goto L2f
            if (r6 == 0) goto L40
        L2f:
            if (r5 != 0) goto L40
            java.lang.Float r1 = new java.lang.Float
            r2 = 0
            r1.<init>(r2)
            r8.label = r4
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L60
            goto L5f
        L40:
            if (r1 == 0) goto L50
            java.lang.Float r1 = new java.lang.Float
            r1.<init>(r7)
            r8.label = r3
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L60
            goto L5f
        L50:
            java.lang.Float r1 = new java.lang.Float
            r3 = 1065353216(0x3f800000, float:1.0)
            r1.<init>(r3)
            r8.label = r2
            java.lang.Object r8 = r9.emit(r1, r8)
            if (r8 != r0) goto L60
        L5f:
            return r0
        L60:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$glanceableHubAlpha$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
