package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceHapticViewModel {
    public final StateFlowImpl activatedHistory;
    public final Flow quickAffordanceHapticState;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HapticState {
        public static final /* synthetic */ HapticState[] $VALUES;
        public static final HapticState LAUNCH;
        public static final HapticState NO_HAPTICS;
        public static final HapticState TOGGLE_OFF;
        public static final HapticState TOGGLE_ON;

        static {
            HapticState hapticState = new HapticState("TOGGLE_ON", 0);
            TOGGLE_ON = hapticState;
            HapticState hapticState2 = new HapticState("TOGGLE_OFF", 1);
            TOGGLE_OFF = hapticState2;
            HapticState hapticState3 = new HapticState("LAUNCH", 2);
            LAUNCH = hapticState3;
            HapticState hapticState4 = new HapticState("NO_HAPTICS", 3);
            NO_HAPTICS = hapticState4;
            HapticState[] hapticStateArr = {hapticState, hapticState2, hapticState3, hapticState4};
            $VALUES = hapticStateArr;
            EnumEntriesKt.enumEntries(hapticStateArr);
        }

        private HapticState(String str, int i) {
        }

        public static HapticState valueOf(String str) {
            return (HapticState) Enum.valueOf(HapticState.class, str);
        }

        public static HapticState[] values() {
            return (HapticState[]) $VALUES.clone();
        }
    }

    public KeyguardQuickAffordanceHapticViewModel(final Flow flow, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor) {
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new ActivatedHistory(false, null, 2, 0 == true ? 1 : 0));
        this.activatedHistory = MutableStateFlow;
        this.quickAffordanceHapticState = FlowKt.distinctUntilChanged(FlowKt.merge(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel r5 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel) r5
                        java.lang.String r5 = r5.configKey
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, keyguardQuickAffordanceInteractor.launchingFromTriggeredResult, new KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2(null))), FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L61
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$ActivatedHistory r5 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel.ActivatedHistory) r5
                        java.lang.Boolean r6 = r5.previousValue
                        java.lang.Boolean r2 = java.lang.Boolean.FALSE
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        boolean r2 = r5.currentValue
                        if (r6 == 0) goto L45
                        if (r2 == 0) goto L45
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$HapticState r5 = com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel.HapticState.TOGGLE_ON
                        goto L56
                    L45:
                        java.lang.Boolean r6 = java.lang.Boolean.TRUE
                        java.lang.Boolean r5 = r5.previousValue
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 == 0) goto L54
                        if (r2 != 0) goto L54
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$HapticState r5 = com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel.HapticState.TOGGLE_OFF
                        goto L56
                    L54:
                        com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$HapticState r5 = com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel.HapticState.NO_HAPTICS
                    L56:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L61
                        return r1
                    L61:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        })));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ActivatedHistory {
        public final boolean currentValue;
        public final Boolean previousValue;

        public ActivatedHistory(boolean z, Boolean bool) {
            this.currentValue = z;
            this.previousValue = bool;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ActivatedHistory)) {
                return false;
            }
            ActivatedHistory activatedHistory = (ActivatedHistory) obj;
            return this.currentValue == activatedHistory.currentValue && Intrinsics.areEqual(this.previousValue, activatedHistory.previousValue);
        }

        public final int hashCode() {
            int hashCode = Boolean.hashCode(this.currentValue) * 31;
            Boolean bool = this.previousValue;
            return hashCode + (bool == null ? 0 : bool.hashCode());
        }

        public final String toString() {
            return "ActivatedHistory(currentValue=" + this.currentValue + ", previousValue=" + this.previousValue + ")";
        }

        public /* synthetic */ ActivatedHistory(boolean z, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? null : bool);
        }
    }
}
