package com.android.systemui.bouncer.domain.interactor;

import android.R;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.EmergencyServicesRepository;
import com.android.systemui.bouncer.shared.model.BouncerActionButtonModel;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.EmergencyDialerConstants;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class BouncerActionButtonInteractor {
    public final Flow actionButton;
    public final ActivityTaskManager activityTaskManager;
    public final Context applicationContext;
    public final AuthenticationInteractor authenticationInteractor;
    public final CoroutineDispatcher backgroundDispatcher;
    public final DozeLogger dozeLogger;
    public final EmergencyAffordanceManager emergencyAffordanceManager;
    public final Lazy emergencyCallButtonModel$delegate;
    public final EmergencyDialerIntentFactory emergencyDialerIntentFactory;
    public final MetricsLogger metricsLogger;
    public final MobileConnectionsRepository mobileConnectionsRepository;
    public final EmergencyServicesRepository repository;
    public final Lazy returnToCallButtonModel$delegate;
    public final SelectedUserInteractor selectedUserInteractor;
    public final TelecomManager telecomManager;
    public final TelephonyInteractor telephonyInteractor;

    public BouncerActionButtonInteractor(Context context, CoroutineDispatcher coroutineDispatcher, EmergencyServicesRepository emergencyServicesRepository, MobileConnectionsRepository mobileConnectionsRepository, TelephonyInteractor telephonyInteractor, AuthenticationInteractor authenticationInteractor, SelectedUserInteractor selectedUserInteractor, ActivityTaskManager activityTaskManager, TelecomManager telecomManager, EmergencyAffordanceManager emergencyAffordanceManager, EmergencyDialerIntentFactory emergencyDialerIntentFactory, MetricsLogger metricsLogger, DozeLogger dozeLogger) {
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
        if (telecomManager == null || !telephonyInteractor.hasTelephonyRadio) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
        } else {
            final StateFlow stateFlow = telephonyInteractor.isInCall;
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

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                        /*
                            r3 = this;
                            boolean r4 = r5 instanceof com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r4 == 0) goto L13
                            r4 = r5
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r4
                            int r0 = r4.label
                            r1 = -2147483648(0xffffffff80000000, float:-0.0)
                            r2 = r0 & r1
                            if (r2 == 0) goto L13
                            int r0 = r0 - r1
                            r4.label = r0
                            goto L18
                        L13:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1
                            r4.<init>(r5)
                        L18:
                            java.lang.Object r5 = r4.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r1 = r4.label
                            r2 = 1
                            if (r1 == 0) goto L2f
                            if (r1 != r2) goto L27
                            kotlin.ResultKt.throwOnFailure(r5)
                            goto L3f
                        L27:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                            r3.<init>(r4)
                            throw r3
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r5)
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            r4.label = r2
                            kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                            java.lang.Object r3 = r3.emit(r5, r4)
                            if (r3 != r0) goto L3f
                            return r0
                        L3f:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final Flow isAnySimSecure = mobileConnectionsRepository.isAnySimSecure();
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj, Continuation continuation) {
                        /*
                            this = this;
                            boolean r4 = r5 instanceof com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r4 == 0) goto L13
                            r4 = r5
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r4
                            int r0 = r4.label
                            r1 = -2147483648(0xffffffff80000000, float:-0.0)
                            r2 = r0 & r1
                            if (r2 == 0) goto L13
                            int r0 = r0 - r1
                            r4.label = r0
                            goto L18
                        L13:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1
                            r4.<init>(r5)
                        L18:
                            java.lang.Object r5 = r4.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r1 = r4.label
                            r2 = 1
                            if (r1 == 0) goto L2f
                            if (r1 != r2) goto L27
                            kotlin.ResultKt.throwOnFailure(r5)
                            goto L3f
                        L27:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                            r3.<init>(r4)
                            throw r3
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r5)
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            r4.label = r2
                            kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                            java.lang.Object r3 = r3.emit(r5, r4)
                            if (r3 != r0) goto L3f
                            return r0
                        L3f:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj, Continuation continuation) {
                        /*
                            this = this;
                            boolean r4 = r5 instanceof com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r4 == 0) goto L13
                            r4 = r5
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r4
                            int r0 = r4.label
                            r1 = -2147483648(0xffffffff80000000, float:-0.0)
                            r2 = r0 & r1
                            if (r2 == 0) goto L13
                            int r0 = r0 - r1
                            r4.label = r0
                            goto L18
                        L13:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1
                            r4.<init>(r5)
                        L18:
                            java.lang.Object r5 = r4.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r1 = r4.label
                            r2 = 1
                            if (r1 == 0) goto L2f
                            if (r1 != r2) goto L27
                            kotlin.ResultKt.throwOnFailure(r5)
                            goto L3f
                        L27:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                            r3.<init>(r4)
                            throw r3
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r5)
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            r4.label = r2
                            kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                            java.lang.Object r3 = r3.emit(r5, r4)
                            if (r3 != r0) goto L3f
                            return r0
                        L3f:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final ReadonlyStateFlow readonlyStateFlow = emergencyServicesRepository.enableEmergencyCallWhileSimLocked;
            final ChannelLimitedFlowMerge merge = FlowKt.merge(flow, flow2, flow4, new Flow() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2

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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj, Continuation continuation) {
                        /*
                            this = this;
                            boolean r4 = r5 instanceof com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r4 == 0) goto L13
                            r4 = r5
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r4
                            int r0 = r4.label
                            r1 = -2147483648(0xffffffff80000000, float:-0.0)
                            r2 = r0 & r1
                            if (r2 == 0) goto L13
                            int r0 = r0 - r1
                            r4.label = r0
                            goto L18
                        L13:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1 r4 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2$2$1
                            r4.<init>(r5)
                        L18:
                            java.lang.Object r5 = r4.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r1 = r4.label
                            r2 = 1
                            if (r1 == 0) goto L2f
                            if (r1 != r2) goto L27
                            kotlin.ResultKt.throwOnFailure(r5)
                            goto L3f
                        L27:
                            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                            r3.<init>(r4)
                            throw r3
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r5)
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            r4.label = r2
                            kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                            java.lang.Object r3 = r3.emit(r5, r4)
                            if (r3 != r0) goto L3f
                            return r0
                        L3f:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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

                    public AnonymousClass2(FlowCollector flowCollector, BouncerActionButtonInteractor bouncerActionButtonInteractor) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = bouncerActionButtonInteractor;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:19:0x009a  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x00b2 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:23:0x00a5  */
                    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2$1
                            r0.<init>(r8)
                        L18:
                            java.lang.Object r8 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 2
                            r4 = 1
                            r5 = 0
                            if (r2 == 0) goto L42
                            if (r2 == r4) goto L34
                            if (r2 != r3) goto L2c
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto Lb3
                        L2c:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L34:
                            java.lang.Object r6 = r0.L$1
                            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                            java.lang.Object r7 = r0.L$0
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1$2 r7 = (com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1.AnonymousClass2) r7
                            kotlin.ResultKt.throwOnFailure(r8)
                            r2 = r6
                            r6 = r7
                            goto L92
                        L42:
                            kotlin.ResultKt.throwOnFailure(r8)
                            kotlin.Unit r7 = (kotlin.Unit) r7
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor r7 = r6.this$0
                            com.android.systemui.telephony.domain.interactor.TelephonyInteractor r8 = r7.telephonyInteractor
                            kotlinx.coroutines.flow.StateFlow r8 = r8.isInCall
                            java.lang.Object r8 = r8.getValue()
                            java.lang.Boolean r8 = (java.lang.Boolean) r8
                            boolean r8 = r8.booleanValue()
                            kotlinx.coroutines.flow.FlowCollector r2 = r6.$this_unsafeFlow
                            if (r8 == 0) goto L64
                            kotlin.Lazy r6 = r7.returnToCallButtonModel$delegate
                            java.lang.Object r6 = r6.getValue()
                            com.android.systemui.bouncer.shared.model.BouncerActionButtonModel r6 = (com.android.systemui.bouncer.shared.model.BouncerActionButtonModel) r6
                            goto La6
                        L64:
                            r0.L$0 = r6
                            r0.L$1 = r2
                            r0.label = r4
                            com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository r8 = r7.mobileConnectionsRepository
                            boolean r8 = r8.getIsAnySimSecure()
                            if (r8 == 0) goto L83
                            com.android.systemui.bouncer.data.repository.EmergencyServicesRepository r7 = r7.repository
                            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r7.enableEmergencyCallWhileSimLocked
                            kotlinx.coroutines.flow.StateFlow r7 = r7.$$delegate_0
                            java.lang.Object r7 = r7.getValue()
                            java.lang.Boolean r7 = (java.lang.Boolean) r7
                            r7.getClass()
                        L81:
                            r8 = r7
                            goto L8f
                        L83:
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$isEmergencyCallButton$2 r8 = new com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$isEmergencyCallButton$2
                            r8.<init>(r7, r5)
                            kotlinx.coroutines.CoroutineDispatcher r7 = r7.backgroundDispatcher
                            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r8, r0)
                            goto L81
                        L8f:
                            if (r8 != r1) goto L92
                            return r1
                        L92:
                            java.lang.Boolean r8 = (java.lang.Boolean) r8
                            boolean r7 = r8.booleanValue()
                            if (r7 == 0) goto La5
                            com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor r6 = r6.this$0
                            kotlin.Lazy r6 = r6.emergencyCallButtonModel$delegate
                            java.lang.Object r6 = r6.getValue()
                            com.android.systemui.bouncer.shared.model.BouncerActionButtonModel r6 = (com.android.systemui.bouncer.shared.model.BouncerActionButtonModel) r6
                            goto La6
                        La5:
                            r6 = r5
                        La6:
                            r0.L$0 = r5
                            r0.L$1 = r5
                            r0.label = r3
                            java.lang.Object r6 = r2.emit(r6, r0)
                            if (r6 != r1) goto Lb3
                            return r1
                        Lb3:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
        }
        this.actionButton = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.returnToCallButtonModel$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$returnToCallButtonModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String string = BouncerActionButtonInteractor.this.applicationContext.getString(R.string.permdesc_uwb_ranging);
                final BouncerActionButtonInteractor bouncerActionButtonInteractor = BouncerActionButtonInteractor.this;
                return new BouncerActionButtonModel(string, new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$returnToCallButtonModel$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BouncerActionButtonInteractor bouncerActionButtonInteractor2 = BouncerActionButtonInteractor.this;
                        bouncerActionButtonInteractor2.metricsLogger.action(200);
                        bouncerActionButtonInteractor2.activityTaskManager.stopSystemLockTaskMode();
                        TelecomManager telecomManager2 = BouncerActionButtonInteractor.this.telecomManager;
                        if (telecomManager2 != null) {
                            telecomManager2.showInCallScreen(false);
                        }
                        return Unit.INSTANCE;
                    }
                }, null);
            }
        });
        this.emergencyCallButtonModel$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$emergencyCallButtonModel$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String string = BouncerActionButtonInteractor.this.applicationContext.getString(R.string.permdesc_sdcardRead);
                final BouncerActionButtonInteractor bouncerActionButtonInteractor = BouncerActionButtonInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$emergencyCallButtonModel$2.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BouncerActionButtonInteractor bouncerActionButtonInteractor2 = BouncerActionButtonInteractor.this;
                        bouncerActionButtonInteractor2.metricsLogger.action(200);
                        bouncerActionButtonInteractor2.activityTaskManager.stopSystemLockTaskMode();
                        BouncerActionButtonInteractor.this.dozeLogger.logEmergencyCall();
                        BouncerActionButtonInteractor bouncerActionButtonInteractor3 = BouncerActionButtonInteractor.this;
                        TelecomManager telecomManager2 = ((BouncerInteractorModule$emergencyDialerIntentFactory$1) bouncerActionButtonInteractor3.emergencyDialerIntentFactory).$telecomManager;
                        Intent createLaunchEmergencyDialerIntent = telecomManager2 != null ? telecomManager2.createLaunchEmergencyDialerIntent(null) : null;
                        if (createLaunchEmergencyDialerIntent != null) {
                            createLaunchEmergencyDialerIntent.setFlags(343932928);
                            createLaunchEmergencyDialerIntent.putExtra(EmergencyDialerConstants.EXTRA_ENTRY_TYPE, 1);
                            Context context2 = bouncerActionButtonInteractor3.applicationContext;
                            context2.startActivityAsUser(createLaunchEmergencyDialerIntent, ActivityOptions.makeCustomAnimation(context2, 0, 0).toBundle(), new UserHandle(bouncerActionButtonInteractor3.selectedUserInteractor.getSelectedUserId(false)));
                        }
                        return Unit.INSTANCE;
                    }
                };
                final BouncerActionButtonInteractor bouncerActionButtonInteractor2 = BouncerActionButtonInteractor.this;
                return new BouncerActionButtonModel(string, function0, new Function0() { // from class: com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor$emergencyCallButtonModel$2.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        if (BouncerActionButtonInteractor.this.emergencyAffordanceManager.needsEmergencyAffordance()) {
                            BouncerActionButtonInteractor bouncerActionButtonInteractor3 = BouncerActionButtonInteractor.this;
                            bouncerActionButtonInteractor3.metricsLogger.action(200);
                            bouncerActionButtonInteractor3.activityTaskManager.stopSystemLockTaskMode();
                            BouncerActionButtonInteractor.this.emergencyAffordanceManager.performEmergencyCall();
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
        });
    }
}
