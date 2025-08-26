package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ DeviceEntryFaceAuthInteractor $faceAuthInteractor$inlined;
    final /* synthetic */ DeviceEntryFingerprintAuthInteractor $fingerprintAuthInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceUnlockedInteractor deviceUnlockedInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor) {
        super(3, continuation);
        this.this$0 = deviceUnlockedInteractor;
        this.$faceAuthInteractor$inlined = deviceEntryFaceAuthInteractor;
        this.$fingerprintAuthInteractor$inlined = deviceEntryFingerprintAuthInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1 deviceUnlockedInteractor$special$$inlined$flatMapLatest$1 = new DeviceUnlockedInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$faceAuthInteractor$inlined, this.$fingerprintAuthInteractor$inlined);
        deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceUnlockedInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowCombine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Triple triple = (Triple) this.L$1;
            boolean zBooleanValue = ((Boolean) triple.component1()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) triple.component2()).booleanValue();
            boolean zBooleanValue3 = ((Boolean) triple.component3()).booleanValue();
            if (zBooleanValue || zBooleanValue2 || zBooleanValue3) {
                ChannelFlowTransformLatest channelFlowTransformLatest = this.this$0.biometricSettingsInteractor.authenticationFlags;
                StateFlow stateFlowIsLockedOut = this.$faceAuthInteractor$inlined.isLockedOut();
                StateFlow stateFlow = this.$fingerprintAuthInteractor$inlined.isLockedOut;
                DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                flowCombine = FlowKt.combine(channelFlowTransformLatest, stateFlowIsLockedOut, stateFlow, deviceUnlockedInteractor.trustInteractor.isTrustAgentCurrentlyAllowed, new DeviceUnlockedInteractor$deviceEntryRestrictionReason$1$1(deviceUnlockedInteractor, this.$faceAuthInteractor$inlined, zBooleanValue3, null));
            } else {
                final ChannelFlowTransformLatest channelFlowTransformLatest2 = this.this$0.biometricSettingsInteractor.authenticationFlags;
                flowCombine = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$deviceEntryRestrictionReason$lambda$9$$inlined$map$1

                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$deviceEntryRestrictionReason$lambda$9$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$deviceEntryRestrictionReason$lambda$9$$inlined$map$1$2$1, reason: invalid class name */
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
                                AuthenticationFlags authenticationFlags = (AuthenticationFlags) obj;
                                DeviceEntryRestrictionReason deviceEntryRestrictionReason = authenticationFlags.isInUserLockdown ? DeviceEntryRestrictionReason.UserLockdown : authenticationFlags.isPrimaryAuthRequiredAfterDpmLockdown ? DeviceEntryRestrictionReason.PolicyLockdown : null;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(deviceEntryRestrictionReason, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = channelFlowTransformLatest2.collect(new AnonymousClass2(flowCollector2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowCombine, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
