package com.android.systemui.bouncer.domain.interactor;

import android.R;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.telecom.TelecomManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.EmergencyServicesRepository;
import com.android.systemui.bouncer.shared.model.BouncerActionButtonModel;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes.dex */
public final class BouncerActionButtonInteractor {
    public final Flow actionButton;
    public final ActivityTaskManager activityTaskManager;
    public final Context applicationContext;
    public final AuthenticationInteractor authenticationInteractor;
    public final CoroutineDispatcher backgroundDispatcher;
    public final DozeLogger dozeLogger;
    public final EmergencyAffordanceManager emergencyAffordanceManager;
    public final EmergencyDialerIntentFactory emergencyDialerIntentFactory;
    public final MetricsLogger metricsLogger;
    public final MobileConnectionsRepository mobileConnectionsRepository;
    public final EmergencyServicesRepository repository;
    public final SelectedUserInteractor selectedUserInteractor;
    public final TelecomManager telecomManager;
    public final TelephonyInteractor telephonyInteractor;

    public BouncerActionButtonInteractor(Context context, CoroutineDispatcher coroutineDispatcher, EmergencyServicesRepository emergencyServicesRepository, MobileConnectionsRepository mobileConnectionsRepository, TelephonyInteractor telephonyInteractor, AuthenticationInteractor authenticationInteractor, SelectedUserInteractor selectedUserInteractor, ActivityTaskManager activityTaskManager, TelecomManager telecomManager, EmergencyAffordanceManager emergencyAffordanceManager, EmergencyDialerIntentFactory emergencyDialerIntentFactory, MetricsLogger metricsLogger, DozeLogger dozeLogger, Lazy lazy) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = emergencyServicesRepository;
        this.mobileConnectionsRepository = mobileConnectionsRepository;
        this.telephonyInteractor = telephonyInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.activityTaskManager = activityTaskManager;
        this.telecomManager = telecomManager;
        this.emergencyAffordanceManager = emergencyAffordanceManager;
        this.emergencyDialerIntentFactory = emergencyDialerIntentFactory;
        this.metricsLogger = metricsLogger;
        this.dozeLogger = dozeLogger;
        if (telecomManager == null || !((TelephonyRepositoryImpl) telephonyInteractor.repository).hasTelephonyRadio) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        } else {
            final ReadonlyStateFlow readonlyStateFlow = telephonyInteractor.isInCall;
            Flow flow = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                            Unit unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
            final Flow flowIsAnySimSecure = mobileConnectionsRepository.isAnySimSecure();
            Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                            Unit unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowIsAnySimSecure.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final Flow flow3 = authenticationInteractor.authenticationMethod;
            Flow flow4 = new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                            Unit unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            final ReadonlyStateFlow readonlyStateFlow2 = emergencyServicesRepository.enableEmergencyCallWhileSimLocked;
            final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(flow, flow2, flow4, new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                            Unit unit = Unit.INSTANCE;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1

                /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ BouncerActionButtonInteractor this$0;

                    /* renamed from: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, BouncerActionButtonInteractor bouncerActionButtonInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = bouncerActionButtonInteractor;
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:24:0x0089, code lost:
                    
                        if (r8 == r1) goto L32;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a6, code lost:
                    
                        if (r6.emit(r7, r0) != r1) goto L33;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a8, code lost:
                    
                        return r1;
                     */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) throws Throwable {
                        AnonymousClass1 anonymousClass1;
                        FlowCollector flowCollector;
                        Object objWithContext;
                        Object returnToCallButtonModel;
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
                            BouncerActionButtonInteractor bouncerActionButtonInteractor = this.this$0;
                            boolean zBooleanValue = ((Boolean) bouncerActionButtonInteractor.telephonyInteractor.isInCall.$$delegate_0.getValue()).booleanValue();
                            flowCollector = this.$this_unsafeFlow;
                            if (zBooleanValue) {
                                returnToCallButtonModel = new BouncerActionButtonModel.ReturnToCallButtonModel(R.string.permlab_callCompanionApp);
                                anonymousClass1.L$0 = null;
                                anonymousClass1.label = 2;
                            } else {
                                anonymousClass1.L$0 = flowCollector;
                                anonymousClass1.label = 1;
                                if (bouncerActionButtonInteractor.mobileConnectionsRepository.getIsAnySimSecure()) {
                                    objWithContext = (Boolean) bouncerActionButtonInteractor.repository.enableEmergencyCallWhileSimLocked.$$delegate_0.getValue();
                                    objWithContext.getClass();
                                } else {
                                    objWithContext = BuildersKt.withContext(bouncerActionButtonInteractor.backgroundDispatcher, new BouncerActionButtonInteractor$isEmergencyCallButton$2(bouncerActionButtonInteractor, null), anonymousClass1);
                                }
                                obj2 = objWithContext;
                            }
                        } else {
                            if (i2 != 1) {
                                if (i2 != 2) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                                return Unit.INSTANCE;
                            }
                            flowCollector = (FlowCollector) anonymousClass1.L$0;
                            ResultKt.throwOnFailure(obj2);
                        }
                        returnToCallButtonModel = ((Boolean) obj2).booleanValue() ? new BouncerActionButtonModel.EmergencyButtonModel(R.string.permlab_accessNetworkConditions) : null;
                        anonymousClass1.L$0 = null;
                        anonymousClass1.label = 2;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
        }
        this.actionButton = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
    }
}
