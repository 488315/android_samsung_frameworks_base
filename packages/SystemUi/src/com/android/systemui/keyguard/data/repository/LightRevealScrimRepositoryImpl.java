package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.PowerButtonReveal;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LightRevealScrimRepositoryImpl implements LightRevealScrimRepository {
    public final ChannelFlowTransformLatest biometricRevealEffect;
    public final Context context;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$3 faceRevealEffect;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$2 fingerprintRevealEffect;
    public final ChannelFlowTransformLatest nonBiometricRevealEffect;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 powerButtonRevealEffect;
    public final Flow revealEffect;
    public final LightRevealScrimRepositoryImpl$special$$inlined$map$1 tapRevealEffect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BiometricUnlockSource.values().length];
            try {
                iArr[BiometricUnlockSource.FINGERPRINT_SENSOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BiometricUnlockSource.FACE_SENSOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r5v7, types: [com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3] */
    public LightRevealScrimRepositoryImpl(KeyguardRepository keyguardRepository, Context context) {
        this.context = context;
        this.powerButtonRevealEffect = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new PowerButtonReveal(context.getResources().getDimensionPixelSize(R.dimen.physical_power_button_center_screen_location_y)));
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        final ReadonlyStateFlow readonlyStateFlow = keyguardRepositoryImpl.lastDozeTapToWakePosition;
        this.tapRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2 */
            public final class C16352 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$1$2", m277f = "LightRevealScrimRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16352.this.emit(null, this);
                    }
                }

                public C16352(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Point point = (Point) obj;
                                CircleReveal access$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : null;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(access$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16352(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow = keyguardRepositoryImpl.fingerprintSensorLocation;
        this.fingerprintRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2 */
            public final class C16362 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$2$2", m277f = "LightRevealScrimRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16362.this.emit(null, this);
                    }
                }

                public C16362(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Point point = (Point) obj;
                                CircleReveal access$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : null;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(access$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16362(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = keyguardRepositoryImpl.faceSensorLocation;
        this.faceRevealEffect = new Flow() { // from class: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2 */
            public final class C16372 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$map$3$2", m277f = "LightRevealScrimRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C16372.this.emit(null, this);
                    }
                }

                public C16372(FlowCollector flowCollector, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = lightRevealScrimRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Point point = (Point) obj;
                                CircleReveal access$constructCircleRevealFromPoint = point != null ? LightRevealScrimRepositoryImpl.access$constructCircleRevealFromPoint(this.this$0, point) : null;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(access$constructCircleRevealFromPoint, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C16372(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(keyguardRepositoryImpl.biometricUnlockSource, new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(null, this));
        this.biometricRevealEffect = transformLatest;
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(keyguardRepositoryImpl.wakefulness, new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$2(null, this));
        this.nonBiometricRevealEffect = transformLatest2;
        this.revealEffect = FlowKt.distinctUntilChanged(FlowKt.combine(keyguardRepositoryImpl.biometricUnlockState, transformLatest, transformLatest2, new LightRevealScrimRepositoryImpl$revealEffect$1(null)));
    }

    public static final CircleReveal access$constructCircleRevealFromPoint(LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl, Point point) {
        lightRevealScrimRepositoryImpl.getClass();
        int i = point.x;
        int i2 = point.y;
        Context context = lightRevealScrimRepositoryImpl.context;
        return new CircleReveal(i, i2, 0, Math.max(Math.max(i, context.getDisplay().getWidth() - point.x), Math.max(point.y, context.getDisplay().getHeight() - point.y)));
    }
}
