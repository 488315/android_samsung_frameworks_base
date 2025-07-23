package com.android.systemui.statusbar.notification.promoted.ui.viewmodel;

import android.util.IndentingPrintWriter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AODPromotedNotificationViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public static final long RECENTLY_ALERTED_THRESHOLD;
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final DistinctFlowImpl contentFlow;
    public final Hydrator hydrator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        RECENTLY_ALERTED_THRESHOLD = DurationKt.toDuration(30, DurationUnit.SECONDS);
    }

    public AODPromotedNotificationViewModel(AODPromotedNotificationInteractor aODPromotedNotificationInteractor, SystemClock systemClock, DumpManager dumpManager) {
        ActivatableFlowDumperImpl activatableFlowDumperImpl = new ActivatableFlowDumperImpl(dumpManager, "AODPromotedNotificationViewModel");
        this.$$delegate_0 = activatableFlowDumperImpl;
        Hydrator hydrator = new Hydrator("AODPromotedNotificationViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        DistinctFlowImpl distinctFlowImpl = aODPromotedNotificationInteractor.content;
        this.contentFlow = distinctFlowImpl;
        hydrator.hydratedStateOf("content", null, distinctFlowImpl);
        final DistinctFlowImpl distinctFlowImpl2 = aODPromotedNotificationInteractor.content;
        hydrator.hydratedStateOf("audiblyAlertedIconVisible", Boolean.FALSE, activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.transformLatest(activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel r7 = (com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel) r7
                        if (r7 == 0) goto L4b
                        kotlin.time.Duration$Companion r8 = kotlin.time.Duration.Companion
                        kotlin.time.DurationUnit r8 = kotlin.time.DurationUnit.MILLISECONDS
                        long r4 = r7.lastAudiblyAlertedMs
                        long r7 = kotlin.time.DurationKt.toDuration(r4, r8)
                        long r4 = com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel.RECENTLY_ALERTED_THRESHOLD
                        long r7 = kotlin.time.Duration.m3441plusLRDsOJo(r7, r4)
                        kotlin.time.Duration r7 = kotlin.time.Duration.m3434boximpl(r7)
                        goto L4c
                    L4b:
                        r7 = 0
                    L4c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "audiblyAlertedIconVisibleUntil"), new AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1(systemClock, null)), -1, 2)), "audiblyAlertedIconVisible"));
    }

    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    public final Object activateFlowDumper(Continuation continuation) {
        return this.$$delegate_0.activateFlowDumper(continuation);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.$$delegate_0.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        this.$$delegate_0.dumpFlows(indentingPrintWriter);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final SharedFlow dumpReplayCache(SharedFlow sharedFlow, String str) {
        return this.$$delegate_0.dumpReplayCache(sharedFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final StateFlow dumpValue(StateFlow stateFlow, String str) {
        return this.$$delegate_0.dumpValue(stateFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final Flow dumpWhileCollecting(Flow flow, String str) {
        return this.$$delegate_0.dumpWhileCollecting(flow, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$2 r5 = new com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
