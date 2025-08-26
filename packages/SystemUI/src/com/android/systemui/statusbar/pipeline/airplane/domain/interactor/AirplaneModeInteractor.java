package com.android.systemui.statusbar.pipeline.airplane.domain.interactor;

import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.samsung.android.knox.foresight.KnoxForesight;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class AirplaneModeInteractor {
    public final AirplaneModeRepository airplaneModeRepository;
    public final ReadonlyStateFlow isAirplaneMode;
    public final AirplaneModeInteractor$special$$inlined$map$1 isForceHidden;
    public final MobileConnectionsRepository mobileConnectionsRepository;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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

    /* renamed from: com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$setIsAirplaneMode$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AirplaneModeInteractor.this.setIsAirplaneMode(false, this);
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor$special$$inlined$map$1] */
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
                        Boolean boolValueOf = Boolean.valueOf(((Set) obj).contains(ConnectivitySlot.AIRPLANE));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
    
        if (((com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl) r5).setIsAirplaneMode(r6, r0) == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object setIsAirplaneMode(boolean z, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objIsInEcmMode = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsInEcmMode);
            anonymousClass1.L$0 = this;
            anonymousClass1.Z$0 = z;
            anonymousClass1.label = 1;
            objIsInEcmMode = this.mobileConnectionsRepository.isInEcmMode(anonymousClass1);
            if (objIsInEcmMode != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objIsInEcmMode);
            return SetResult.SUCCESS;
        }
        z = anonymousClass1.Z$0;
        this = (AirplaneModeInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objIsInEcmMode);
        if (((Boolean) objIsInEcmMode).booleanValue()) {
            return SetResult.BLOCKED_BY_ECM;
        }
        AirplaneModeRepository airplaneModeRepository = this.airplaneModeRepository;
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
    }
}
