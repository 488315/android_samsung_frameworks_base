package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import androidx.core.animation.ValueAnimator;
import com.android.keyguard.logging.ScrimLogger;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.PowerButtonReveal;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class LightRevealScrimRepositoryImpl implements LightRevealScrimRepository {
    public static final Companion Companion = new Companion(null);
    public static final float DEFAULT_MAX_ALPHA;
    public static final String TAG;
    public final Context context;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$3 faceRevealEffect;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$2 fingerprintRevealEffect;
    public final StateFlowImpl maxAlpha;
    public final ChannelFlowTransformLatest nonBiometricRevealEffect;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 powerButtonRevealEffect;
    public final CallbackFlowBuilder revealAmount;
    public final ValueAnimator revealAmountAnimator;
    public final Flow revealEffect;
    public final ScrimLogger scrimLogger;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$1 tapRevealEffect;
    public Boolean willBeOrIsRevealed;

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
            int[] iArr = new int[BiometricUnlockMode.values().length];
            try {
                iArr[BiometricUnlockMode.WAKE_AND_UNLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BiometricUnlockMode.WAKE_AND_UNLOCK_PULSING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BiometricUnlockMode.WAKE_AND_UNLOCK_FROM_DREAM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(LightRevealScrimRepository.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
        DEFAULT_MAX_ALPHA = 1.0f;
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1] */
    public LightRevealScrimRepositoryImpl(KeyguardRepository keyguardRepository, Context context, PowerRepository powerRepository, ScrimLogger scrimLogger) {
        this.context = context;
        this.scrimLogger = scrimLogger;
        this.powerButtonRevealEffect = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new PowerButtonReveal(context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y)));
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        final ReadonlyStateFlow readonlyStateFlow = keyguardRepositoryImpl.lastDozeTapToWakePosition;
        this.tapRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                        Point point = (Point) obj;
                        Object objAccess$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : LightRevealScrimRepositoryKt.DEFAULT_REVEAL_EFFECT;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objAccess$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow = keyguardRepositoryImpl.fingerprintSensorLocation;
        this.fingerprintRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                        Point point = (Point) obj;
                        Object objAccess$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : LightRevealScrimRepositoryKt.DEFAULT_REVEAL_EFFECT;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objAccess$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final ReadonlyStateFlow readonlyStateFlow2 = keyguardRepositoryImpl.faceSensorLocation;
        this.faceRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
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
                        Point point = (Point) obj;
                        Object objAccess$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : LightRevealScrimRepositoryKt.DEFAULT_REVEAL_EFFECT;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objAccess$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.nonBiometricRevealEffect = FlowKt.transformLatest(((PowerRepositoryImpl) powerRepository).wakefulness, new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(null, this));
        this.revealAmountAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.maxAlpha = StateFlowKt.MutableStateFlow(Float.valueOf(DEFAULT_MAX_ALPHA));
        this.revealAmount = FlowKt.callbackFlow(new LightRevealScrimRepositoryImpl$revealAmount$1(this, null));
        this.revealEffect = FlowKt.distinctUntilChanged(FlowKt.transformLatest(keyguardRepositoryImpl.biometricUnlockState, new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2(null, this)));
    }

    public static final CircleReveal access$constructCircleRevealFromPoint(LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl, Point point) {
        Display display = lightRevealScrimRepositoryImpl.context.getDisplay();
        if (display == null) {
            throw new IllegalStateException("Required value was null.");
        }
        int i = point.x;
        return new CircleReveal(i, point.y, 0, Math.max(Math.max(i, display.getWidth() - point.x), Math.max(point.y, display.getHeight() - point.y)));
    }
}
