package com.android.systemui.keyguard.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.keyguard.logging.ScrimLogger;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepository;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class LightRevealScrimInteractor {
    public static final Companion Companion = new Companion(null);
    public static final String TAG;
    public final Flow lightRevealEffect;
    public final Flow maxAlpha;
    public final Lazy powerInteractor;
    public final LightRevealScrimRepository repository;
    public final LightRevealScrimInteractor$special$$inlined$filter$1 revealAmount;
    public final ScrimLogger scrimLogger;
    public final KeyguardTransitionInteractor transitionInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardState.values().length];
            try {
                iArr[KeyguardState.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardState.DOZING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardState.AOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardState.DREAMING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardState.GLANCEABLE_HUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardState.PRIMARY_BOUNCER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardState.LOCKSCREEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardState.GONE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardState.OCCLUDED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardState.UNDEFINED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(LightRevealScrimInteractor.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1] */
    public LightRevealScrimInteractor(KeyguardTransitionInteractor keyguardTransitionInteractor, LightRevealScrimRepository lightRevealScrimRepository, CoroutineScope coroutineScope, ScrimLogger scrimLogger, Lazy lazy, CoroutineDispatcher coroutineDispatcher) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.repository = lightRevealScrimRepository;
        this.scrimLogger = scrimLogger;
        this.powerInteractor = lazy;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new LightRevealScrimInteractor$listenForStartedKeyguardTransitionStep$1(this, null), 7);
        LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl = (LightRevealScrimRepositoryImpl) lightRevealScrimRepository;
        this.lightRevealEffect = FlowKt.sample(keyguardTransitionInteractor.startedKeyguardTransitionStep, lightRevealScrimRepositoryImpl.revealEffect);
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.AOD;
        companion.getClass();
        this.maxAlpha = kotlinx.coroutines.flow.FlowKt.flowOn(kotlinx.coroutines.flow.FlowKt.transformLatest(booleanFlowOperators.anyOf(keyguardTransitionInteractor.isInTransition(new Edge.ContentToState(sceneKey, keyguardState), new Edge.StateToState(KeyguardState.GONE, keyguardState)), keyguardTransitionInteractor.isInTransition(new Edge.StateToState(KeyguardState.OCCLUDED, keyguardState), null)), new LightRevealScrimInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineDispatcher);
        final CallbackFlowBuilder callbackFlowBuilder = lightRevealScrimRepositoryImpl.revealAmount;
        this.revealAmount = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimInteractor lightRevealScrimInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimInteractor;
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
                        float fFloatValue = ((Number) obj).floatValue();
                        Lazy lazy = this.this$0.powerInteractor;
                        if ((((PowerInteractor) lazy.get()).screenPowerState.$$delegate_0.getValue() != ScreenPowerState.SCREEN_OFF && ((PowerInteractor) lazy.get()).screenPowerState.$$delegate_0.getValue() != ScreenPowerState.SCREEN_TURNING_ON) || fFloatValue == 1.0f || fFloatValue == 0.0f) {
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
                Object objCollect = callbackFlowBuilder.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
