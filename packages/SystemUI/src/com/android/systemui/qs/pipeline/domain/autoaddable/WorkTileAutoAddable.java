package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.os.UserHandle;
import com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WorkTileAutoAddable implements AutoAddable {
    public final AutoAddTracking.Always autoAddTracking;
    public final String description;
    public final TileSpec spec;
    public final UserTracker userTracker;
    public final WorkTileRestoreProcessor workTileRestoreProcessor;

    public WorkTileAutoAddable(UserTracker userTracker, WorkTileRestoreProcessor workTileRestoreProcessor) {
        this.userTracker = userTracker;
        this.workTileRestoreProcessor = workTileRestoreProcessor;
        TileSpec.Companion.getClass();
        this.spec = TileSpec.Companion.create("WorkMode");
        AutoAddTracking.Always always = AutoAddTracking.Always.INSTANCE;
        this.autoAddTracking = always;
        this.description = "WorkTileAutoAddable (" + always + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final Flow autoAddSignal(final int i) {
        final UserHandle of = UserHandle.of(i);
        final SharedFlowImpl sharedFlowImpl = this.workTileRestoreProcessor._removeTrackingForUser;
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $userHandle$inlined;

                /* renamed from: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserHandle userHandle) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$userHandle$inlined = userHandle;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        android.os.UserHandle r2 = r4.$userHandle$inlined
                        int r2 = r2.getIdentifier()
                        if (r6 != r2) goto L4c
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, of), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        r5.intValue()
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor$removeTrackingForUser$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        return FlowKt.merge(new Flow() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ int $userId$inlined;
                public final /* synthetic */ WorkTileAutoAddable this$0;

                /* renamed from: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, WorkTileAutoAddable workTileAutoAddable, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = workTileAutoAddable;
                    this.$userId$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L9e
                    L28:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        kotlin.Unit r8 = (kotlin.Unit) r8
                        com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable r8 = r7.this$0
                        com.android.systemui.settings.UserTracker r9 = r8.userTracker
                        com.android.systemui.settings.UserTrackerImpl r9 = (com.android.systemui.settings.UserTrackerImpl) r9
                        java.util.List r9 = r9.getUserProfiles()
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        boolean r2 = r9 instanceof java.util.Collection
                        if (r2 == 0) goto L4f
                        r4 = r9
                        java.util.Collection r4 = (java.util.Collection) r4
                        boolean r4 = r4.isEmpty()
                        if (r4 == 0) goto L4f
                        goto L90
                    L4f:
                        java.util.Iterator r4 = r9.iterator()
                    L53:
                        boolean r5 = r4.hasNext()
                        if (r5 == 0) goto L90
                        java.lang.Object r5 = r4.next()
                        android.content.pm.UserInfo r5 = (android.content.pm.UserInfo) r5
                        int r5 = r5.id
                        int r6 = r7.$userId$inlined
                        if (r5 != r6) goto L53
                        if (r2 == 0) goto L71
                        r2 = r9
                        java.util.Collection r2 = (java.util.Collection) r2
                        boolean r2 = r2.isEmpty()
                        if (r2 == 0) goto L71
                        goto L88
                    L71:
                        java.util.Iterator r9 = r9.iterator()
                    L75:
                        boolean r2 = r9.hasNext()
                        if (r2 == 0) goto L88
                        java.lang.Object r2 = r9.next()
                        android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2
                        boolean r2 = r2.isManagedProfile()
                        if (r2 == 0) goto L75
                        goto L90
                    L88:
                        com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$RemoveTracking r9 = new com.android.systemui.qs.pipeline.domain.model.AutoAddSignal$RemoveTracking
                        com.android.systemui.qs.pipeline.shared.TileSpec r8 = r8.spec
                        r9.<init>(r8)
                        goto L91
                    L90:
                        r9 = 0
                    L91:
                        if (r9 == 0) goto L9e
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L9e
                        return r1
                    L9e:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, FlowConflatedKt.conflatedCallbackFlow(new WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1(this, i, null)));
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final AutoAddTracking getAutoAddTracking() {
        return this.autoAddTracking;
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }
}
