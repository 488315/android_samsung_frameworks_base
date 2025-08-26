package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import com.android.keyguard.ClockEventController;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardClockRepositoryImpl implements KeyguardClockRepository {
    public final StateFlowImpl _clockSize;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ClockEventController clockEventController;
    public final ClockRegistry clockRegistry;
    public final ReadonlyStateFlow clockSize;
    public final ReadonlyStateFlow currentClock;
    public final KeyguardClockRepositoryImpl$special$$inlined$mapNotNull$1 currentClockId;
    public final FeatureFlagsClassic featureFlags;
    public final KeyguardClockRepositoryImpl$special$$inlined$map$3 previewClock;
    public final SecureSettings secureSettings;
    public final ReadonlyStateFlow selectedClockSize;

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$mapNotNull$1, kotlinx.coroutines.flow.Flow] */
    public KeyguardClockRepositoryImpl(SecureSettings secureSettings, ClockRegistry clockRegistry, ClockEventController clockEventController, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, Context context, FeatureFlagsClassic featureFlagsClassic) {
        this.secureSettings = secureSettings;
        this.clockRegistry = clockRegistry;
        this.clockEventController = clockEventController;
        this.backgroundDispatcher = coroutineDispatcher;
        this.featureFlags = featureFlagsClassic;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(ClockSize.LARGE);
        this._clockSize = stateFlowImplMutableStateFlow;
        this.clockSize = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardClockRepositoryImpl$selectedClockSize$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, -1, "lockscreen_use_double_line_clock"));
        Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardClockRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardClockRepositoryImpl keyguardClockRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardClockRepositoryImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x005e, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L21;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objWithContext = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objWithContext);
                        KeyguardClockRepositoryImpl keyguardClockRepositoryImpl = this.this$0;
                        CoroutineDispatcher coroutineDispatcher = keyguardClockRepositoryImpl.backgroundDispatcher;
                        KeyguardClockRepositoryImpl$selectedClockSize$2$1 keyguardClockRepositoryImpl$selectedClockSize$2$1 = new KeyguardClockRepositoryImpl$selectedClockSize$2$1(keyguardClockRepositoryImpl, null);
                        flowCollector = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector;
                        anonymousClass1.label = 1;
                        objWithContext = BuildersKt.withContext(coroutineDispatcher, keyguardClockRepositoryImpl$selectedClockSize$2$1, anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objWithContext);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.selectedClockSize = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), getClockSize());
        final CallbackFlowBuilder callbackFlowBuilderCallbackFlow = FlowKt.callbackFlow(new KeyguardClockRepositoryImpl$currentClockId$1(this, null));
        final ?? r6 = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                        String str = (String) obj;
                        if (str != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = callbackFlowBuilderCallbackFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.currentClockId = r6;
        this.currentClock = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardClockRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardClockRepositoryImpl keyguardClockRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardClockRepositoryImpl;
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
                        KeyguardClockRepositoryImpl keyguardClockRepositoryImpl = this.this$0;
                        keyguardClockRepositoryImpl.clockEventController.setClock(keyguardClockRepositoryImpl.clockRegistry.createCurrentClock());
                        ClockController clockController = keyguardClockRepositoryImpl.clockEventController.clock;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(clockController, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = r6.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.previewClock = new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardClockRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardClockRepositoryImpl keyguardClockRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardClockRepositoryImpl;
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
                        ClockController clockControllerCreateCurrentClock = this.this$0.clockRegistry.createCurrentClock();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(clockControllerCreateCurrentClock, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = r6.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final ClockSizeSetting getClockSize() {
        ClockSizeSetting.Companion companion = ClockSizeSetting.Companion;
        int intForUser = this.secureSettings.getIntForUser("lockscreen_use_double_line_clock", 1, -2);
        companion.getClass();
        for (ClockSizeSetting clockSizeSetting : ClockSizeSetting.values()) {
            if (clockSizeSetting.getSettingValue() == intForUser) {
                return clockSizeSetting;
            }
        }
        ClockEventController$$ExternalSyntheticOutline0.m(intForUser, "Unrecognized clock setting value: ", ClockSizeSetting.TAG);
        return ClockSizeSetting.DYNAMIC;
    }
}
