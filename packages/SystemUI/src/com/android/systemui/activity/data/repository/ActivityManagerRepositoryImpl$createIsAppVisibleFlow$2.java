package com.android.systemui.activity.data.repository;

import com.android.systemui.log.core.Logger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $creationUid;
    final /* synthetic */ String $identifyingLogTag;
    final /* synthetic */ Logger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ActivityManagerRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2(ActivityManagerRepositoryImpl activityManagerRepositoryImpl, int i, Logger logger, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = activityManagerRepositoryImpl;
        this.$creationUid = i;
        this.$logger = logger;
        this.$identifyingLogTag = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2 activityManagerRepositoryImpl$createIsAppVisibleFlow$2 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2(this.this$0, this.$creationUid, this.$logger, this.$identifyingLogTag, continuation);
        activityManagerRepositoryImpl$createIsAppVisibleFlow$2.L$0 = obj;
        return activityManagerRepositoryImpl$createIsAppVisibleFlow$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
    
        if (r11 == r0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00a0, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x009e, code lost:
    
        if (r1.emit(r12, r11) != r0) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2, kotlin.coroutines.Continuation] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [kotlinx.coroutines.flow.FlowCollector] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            r3 = 0
            r4 = 2
            if (r1 == 0) goto L25
            if (r1 == r2) goto L1a
            if (r1 != r4) goto L12
            kotlin.ResultKt.throwOnFailure(r12)
            goto La1
        L12:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1a:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)     // Catch: java.lang.SecurityException -> L23
            goto La1
        L23:
            r12 = move-exception
            goto L72
        L25:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            r1 = r12
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl r12 = r11.this$0     // Catch: java.lang.SecurityException -> L23
            android.app.ActivityManager r12 = r12.activityManager     // Catch: java.lang.SecurityException -> L23
            int r5 = r11.$creationUid     // Catch: java.lang.SecurityException -> L23
            int r12 = r12.getUidImportance(r5)     // Catch: java.lang.SecurityException -> L23
            r5 = 100
            if (r12 > r5) goto L3d
            r12 = r2
            goto L3e
        L3d:
            r12 = 0
        L3e:
            com.android.systemui.log.core.Logger r5 = r11.$logger     // Catch: java.lang.SecurityException -> L23
            com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 r6 = new com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0     // Catch: java.lang.SecurityException -> L23
            r7 = 3
            r6.<init>(r7)     // Catch: java.lang.SecurityException -> L23
            java.lang.String r7 = r11.$identifyingLogTag     // Catch: java.lang.SecurityException -> L23
            com.android.systemui.log.core.LogLevel r8 = com.android.systemui.log.core.LogLevel.DEBUG     // Catch: java.lang.SecurityException -> L23
            com.android.systemui.log.core.MessageBuffer r9 = r5.getBuffer()     // Catch: java.lang.SecurityException -> L23
            java.lang.String r10 = r5.getTag()     // Catch: java.lang.SecurityException -> L23
            com.android.systemui.log.core.LogMessage r6 = r9.obtain(r10, r8, r6, r3)     // Catch: java.lang.SecurityException -> L23
            r6.setStr1(r7)     // Catch: java.lang.SecurityException -> L23
            r6.setBool1(r12)     // Catch: java.lang.SecurityException -> L23
            com.android.systemui.log.core.MessageBuffer r5 = r5.getBuffer()     // Catch: java.lang.SecurityException -> L23
            r5.commit(r6)     // Catch: java.lang.SecurityException -> L23
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)     // Catch: java.lang.SecurityException -> L23
            r11.L$0 = r1     // Catch: java.lang.SecurityException -> L23
            r11.label = r2     // Catch: java.lang.SecurityException -> L23
            java.lang.Object r11 = r1.emit(r12, r11)     // Catch: java.lang.SecurityException -> L23
            if (r11 != r0) goto La1
            goto La0
        L72:
            com.android.systemui.log.core.Logger r2 = r11.$logger
            com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 r5 = new com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0
            r6 = 4
            r5.<init>(r6)
            java.lang.String r6 = r11.$identifyingLogTag
            com.android.systemui.log.core.LogLevel r7 = com.android.systemui.log.core.LogLevel.ERROR
            com.android.systemui.log.core.MessageBuffer r8 = r2.getBuffer()
            java.lang.String r9 = r2.getTag()
            com.android.systemui.log.core.LogMessage r12 = r8.obtain(r9, r7, r5, r12)
            r12.setStr1(r6)
            com.android.systemui.log.core.MessageBuffer r2 = r2.getBuffer()
            r2.commit(r12)
            java.lang.Boolean r12 = java.lang.Boolean.FALSE
            r11.L$0 = r3
            r11.label = r4
            java.lang.Object r11 = r1.emit(r12, r11)
            if (r11 != r0) goto La1
        La0:
            return r0
        La1:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
