package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel;
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceHapticViewModel {
    public final StateFlowImpl activatedHistory;
    public final Flow quickAffordanceHapticState;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;

    public interface Factory {
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    /* JADX WARN: Multi-variable type inference failed */
    public KeyguardQuickAffordanceHapticViewModel(final Flow flow, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor) {
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new ActivatedHistory(false, null, 2, 0 == true ? 1 : 0));
        this.activatedHistory = stateFlowImplMutableStateFlow;
        this.quickAffordanceHapticState = FlowKt.distinctUntilChanged(FlowKt.merge(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        String str = ((KeyguardQuickAffordanceViewModel) obj).configKey;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, keyguardQuickAffordanceInteractor.launchingFromTriggeredResult, new KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2(null))), FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    KeyguardQuickAffordanceHapticViewModel.HapticState hapticState;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        KeyguardQuickAffordanceHapticViewModel.ActivatedHistory activatedHistory = (KeyguardQuickAffordanceHapticViewModel.ActivatedHistory) obj;
                        boolean zAreEqual = Intrinsics.areEqual(activatedHistory.previousValue, Boolean.FALSE);
                        boolean z = activatedHistory.currentValue;
                        if (zAreEqual && z) {
                            hapticState = KeyguardQuickAffordanceHapticViewModel.HapticState.TOGGLE_ON;
                        } else {
                            hapticState = (!Intrinsics.areEqual(activatedHistory.previousValue, Boolean.TRUE) || z) ? KeyguardQuickAffordanceHapticViewModel.HapticState.NO_HAPTICS : KeyguardQuickAffordanceHapticViewModel.HapticState.TOGGLE_OFF;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(hapticState, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlowImplMutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        })));
    }

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
            int iHashCode = Boolean.hashCode(this.currentValue) * 31;
            Boolean bool = this.previousValue;
            return iHashCode + (bool == null ? 0 : bool.hashCode());
        }

        public final String toString() {
            return "ActivatedHistory(currentValue=" + this.currentValue + ", previousValue=" + this.previousValue + ")";
        }

        public /* synthetic */ ActivatedHistory(boolean z, Boolean bool, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? null : bool);
        }
    }
}
