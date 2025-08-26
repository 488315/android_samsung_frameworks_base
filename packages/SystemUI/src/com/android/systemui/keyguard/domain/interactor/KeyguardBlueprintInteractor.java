package com.android.systemui.keyguard.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import java.util.Collections;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class KeyguardBlueprintInteractor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final StateFlowImpl blueprint;
    public final KeyguardBlueprintInteractor$special$$inlined$map$1 blueprintId;
    public final ConfigurationInteractor configurationInteractor;
    public final FingerprintPropertyInteractor fingerprintPropertyInteractor;
    public final KeyguardBlueprintRepository keyguardBlueprintRepository;
    public final SharedFlowImpl refreshTransition;
    public final SmartspaceSection smartspaceSection;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardBlueprintInteractor.this.new AnonymousClass1(continuation);
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
                final KeyguardBlueprintInteractor keyguardBlueprintInteractor = KeyguardBlueprintInteractor.this;
                KeyguardBlueprintInteractor$special$$inlined$map$1 keyguardBlueprintInteractor$special$$inlined$map$1 = keyguardBlueprintInteractor.blueprintId;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        keyguardBlueprintInteractor.keyguardBlueprintRepository.applyBlueprint((String) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (keyguardBlueprintInteractor$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardBlueprintInteractor.this.new AnonymousClass2(continuation);
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
                final Flow flow = KeyguardBlueprintInteractor.this.fingerprintPropertyInteractor.propertiesInitialized;
                Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$2$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                if (((Boolean) obj).booleanValue()) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final KeyguardBlueprintInteractor keyguardBlueprintInteractor = KeyguardBlueprintInteractor.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor.start.2.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        int i2 = KeyguardBlueprintInteractor.$r8$clinit;
                        keyguardBlueprintInteractor.refreshBlueprint(IntraBlueprintTransition.Type.NoTransition);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardBlueprintInteractor.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final IntraBlueprintTransition.Config config = new IntraBlueprintTransition.Config(IntraBlueprintTransition.Type.NoTransition, false, false, Collections.singletonList(KeyguardBlueprintInteractor.this.smartspaceSection), 6, null);
                final KeyguardBlueprintInteractor keyguardBlueprintInteractor = KeyguardBlueprintInteractor.this;
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = ((ConfigurationInteractorImpl) keyguardBlueprintInteractor.configurationInteractor).onAnyConfigurationChange;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor.start.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        keyguardBlueprintInteractor.refreshBlueprint(config);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public KeyguardBlueprintInteractor(KeyguardBlueprintRepository keyguardBlueprintRepository, CoroutineScope coroutineScope, ShadeModeInteractor shadeModeInteractor, ConfigurationInteractor configurationInteractor, FingerprintPropertyInteractor fingerprintPropertyInteractor, SmartspaceSection smartspaceSection) {
        this.keyguardBlueprintRepository = keyguardBlueprintRepository;
        this.applicationScope = coroutineScope;
        this.configurationInteractor = configurationInteractor;
        this.fingerprintPropertyInteractor = fingerprintPropertyInteractor;
        this.smartspaceSection = smartspaceSection;
        this.blueprint = keyguardBlueprintRepository.blueprint;
        this.refreshTransition = keyguardBlueprintRepository.refreshTransition;
        this.blueprintId = new KeyguardBlueprintInteractor$special$$inlined$map$1(((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide);
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Type type) {
        refreshBlueprint(new IntraBlueprintTransition.Config(type, false, false, null, 14, null));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        CoroutineScope coroutineScope = this.applicationScope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, anonymousClass1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 7);
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Config config) {
        IntraBlueprintTransition.Type type;
        final KeyguardBlueprintRepository keyguardBlueprintRepository = this.keyguardBlueprintRepository;
        keyguardBlueprintRepository.f48assert.isMainThread();
        IntraBlueprintTransition.Config config2 = keyguardBlueprintRepository.targetTransitionConfig;
        if (((config2 == null || (type = config2.type) == null) ? Integer.MIN_VALUE : type.getPriority()) < config.type.getPriority()) {
            if (keyguardBlueprintRepository.targetTransitionConfig == null) {
                keyguardBlueprintRepository.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository$refreshBlueprint$scheduleCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        keyguardBlueprintRepository.f48assert.isMainThread();
                        KeyguardBlueprintRepository keyguardBlueprintRepository2 = keyguardBlueprintRepository;
                        IntraBlueprintTransition.Config config3 = keyguardBlueprintRepository2.targetTransitionConfig;
                        if (config3 != null && !keyguardBlueprintRepository2.refreshTransition.tryEmit(config3)) {
                            Logger logger = keyguardBlueprintRepository2.logger;
                            KeyguardBlueprintRepository$$ExternalSyntheticLambda0 keyguardBlueprintRepository$$ExternalSyntheticLambda0 = new KeyguardBlueprintRepository$$ExternalSyntheticLambda0(2);
                            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, keyguardBlueprintRepository$$ExternalSyntheticLambda0, null);
                            logMessageObtain.setStr1(String.valueOf(config3));
                            logger.getBuffer().commit(logMessageObtain);
                        }
                        keyguardBlueprintRepository.targetTransitionConfig = null;
                    }
                });
            }
            keyguardBlueprintRepository.targetTransitionConfig = config;
        } else {
            Logger logger = keyguardBlueprintRepository.logger;
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new KeyguardBlueprintRepository$$ExternalSyntheticLambda0(1), null);
            logMessageObtain.setStr1(String.valueOf(config));
            logger.getBuffer().commit(logMessageObtain);
        }
    }
}
