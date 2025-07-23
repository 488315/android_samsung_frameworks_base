package com.android.systemui.deviceentry.domain.interactor;

import android.util.Log;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.log.table.DiffableKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceUnlockedInteractor$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceUnlockedInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ DeviceUnlockedInteractor this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C00941 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ DeviceUnlockedInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00941(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = deviceUnlockedInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00941 c00941 = new C00941(this.this$0, continuation);
                c00941.L$0 = obj;
                return c00941;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00941) create((AuthenticationMethodModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) this.L$0;
                    if (!authenticationMethodModel.isSecure) {
                        Log.d(DeviceUnlockedInteractor.TAG, "remaining unlocked because auth method not secure");
                        ((DeviceEntryRepositoryImpl) this.this$0.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(true, null));
                    } else if (authenticationMethodModel.equals(AuthenticationMethodModel.Sim.INSTANCE)) {
                        Log.d(DeviceUnlockedInteractor.TAG, "remaining locked because SIM locked");
                        ((DeviceEntryRepositoryImpl) this.this$0.repository).deviceUnlockStatus.updateState(null, new DeviceUnlockStatus(false, null));
                    } else {
                        DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                        this.label = 1;
                        if (DeviceUnlockedInteractor.access$handleLockAndUnlockEvents(deviceUnlockedInteractor, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = deviceUnlockedInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                Flow flow = deviceUnlockedInteractor.authenticationInteractor.authenticationMethod;
                C00941 c00941 = new C00941(deviceUnlockedInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, c00941, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ DeviceUnlockedInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = deviceUnlockedInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ReadonlyStateFlow readonlyStateFlow = this.this$0.deviceUnlockStatus;
                Flow flow = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L45
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
                                boolean r5 = r5.isUnlocked
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L45
                                return r1
                            L45:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                DeviceUnlockedInteractor deviceUnlockedInteractor = this.this$0;
                Flow logDiffsForTable = DiffableKt.logDiffsForTable(flow, deviceUnlockedInteractor.tableLogBuffer, "", "isUnlocked", ((DeviceUnlockStatus) deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked);
                this.label = 1;
                if (FlowKt.collect(logDiffsForTable, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceUnlockedInteractor$onActivated$2(DeviceUnlockedInteractor deviceUnlockedInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceUnlockedInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceUnlockedInteractor$onActivated$2 deviceUnlockedInteractor$onActivated$2 = new DeviceUnlockedInteractor$onActivated$2(this.this$0, continuation);
        deviceUnlockedInteractor$onActivated$2.L$0 = obj;
        return deviceUnlockedInteractor$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceUnlockedInteractor$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
    }
}
