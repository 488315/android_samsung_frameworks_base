package com.android.systemui.statusbar.pipeline.airplane.domain.interactor;

import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.samsung.android.knox.foresight.KnoxForesight;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class AirplaneModeInteractor {
    public final AirplaneModeRepository airplaneModeRepository;
    public final StateFlow isAirplaneMode;
    public final AirplaneModeInteractor$special$$inlined$map$1 isForceHidden;
    public final MobileConnectionsRepository mobileConnectionsRepository;

    public final class SetResult {
        public static final /* synthetic */ SetResult[] $VALUES;
        public static final SetResult BLOCKED_BY_ECM;
        public static final SetResult SUCCESS;

        static {
            SetResult setResult = new SetResult(KnoxForesight.SUCCESS, 0);
            SUCCESS = setResult;
            SetResult setResult2 = new SetResult("BLOCKED_BY_ECM", 1);
            BLOCKED_BY_ECM = setResult2;
            SetResult[] setResultArr = {setResult, setResult2};
            $VALUES = setResultArr;
            EnumEntriesKt.enumEntries(setResultArr);
        }

        private SetResult(String str, int i) {
        }

        public static SetResult valueOf(String str) {
            return (SetResult) Enum.valueOf(SetResult.class, str);
        }

        public static SetResult[] values() {
            return (SetResult[]) $VALUES.clone();
        }
    }

    public AirplaneModeInteractor(AirplaneModeRepository airplaneModeRepository, ConnectivityRepository connectivityRepository, MobileConnectionsRepository mobileConnectionsRepository) {
        this.airplaneModeRepository = airplaneModeRepository;
        this.mobileConnectionsRepository = mobileConnectionsRepository;
        this.isAirplaneMode = ((AirplaneModeRepositoryImpl) airplaneModeRepository).isAirplaneMode;
        final ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).forceHiddenSlots;
        this.isForceHidden = new Flow() { // from class: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot r6 = com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot.AIRPLANE
                        boolean r5 = r5.contains(r6)
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object setIsAirplaneMode(boolean r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1 r0 = (com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1 r0 = new com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3c
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L69
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            boolean r6 = r0.Z$0
            java.lang.Object r5 = r0.L$0
            com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor r5 = (com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L3c:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.Z$0 = r6
            r0.label = r4
            com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository r7 = r5.mobileConnectionsRepository
            java.lang.Object r7 = r7.isInEcmMode(r0)
            if (r7 != r1) goto L4e
            return r1
        L4e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L59
            com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$SetResult r5 = com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor.SetResult.BLOCKED_BY_ECM
            goto L6b
        L59:
            com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository r5 = r5.airplaneModeRepository
            r7 = 0
            r0.L$0 = r7
            r0.label = r3
            com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl r5 = (com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl) r5
            java.lang.Object r5 = r5.setIsAirplaneMode(r6, r0)
            if (r5 != r1) goto L69
            return r1
        L69:
            com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$SetResult r5 = com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor.SetResult.SUCCESS
        L6b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor.setIsAirplaneMode(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
