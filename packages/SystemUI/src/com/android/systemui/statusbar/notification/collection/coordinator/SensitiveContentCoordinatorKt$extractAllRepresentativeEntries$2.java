package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ PipelineEntry $pipelineEntry;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(PipelineEntry pipelineEntry, Continuation continuation) {
        super(2, continuation);
        this.$pipelineEntry = pipelineEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 = new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(this.$pipelineEntry, continuation);
        sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2.L$0 = obj;
        return sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
    
        if (r4 == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0060, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0038, code lost:
    
        if (r1.yield(r5, r4) == r0) goto L22;
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
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L61
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            java.lang.Object r1 = r4.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3b
        L20:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            com.android.systemui.statusbar.notification.collection.PipelineEntry r5 = r4.$pipelineEntry
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getRepresentativeEntry()
            if (r5 == 0) goto L3b
            r4.L$0 = r1
            r4.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r5 = r1.yield(r5, r4)
            if (r5 != r0) goto L3b
            goto L60
        L3b:
            com.android.systemui.statusbar.notification.collection.PipelineEntry r5 = r4.$pipelineEntry
            boolean r3 = r5 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
            if (r3 == 0) goto L61
            com.android.systemui.statusbar.notification.collection.GroupEntry r5 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r5
            java.util.List r5 = r5.mUnmodifiableChildren
            kotlin.sequences.Sequence r5 = com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt.access$extractAllRepresentativeEntries(r5)
            r3 = 0
            r4.L$0 = r3
            r4.label = r2
            r1.getClass()
            java.util.Iterator r5 = r5.iterator()
            java.lang.Object r4 = r1.yieldAll(r5, r4)
            if (r4 != r0) goto L5c
            goto L5e
        L5c:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
        L5e:
            if (r4 != r0) goto L61
        L60:
            return r0
        L61:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
