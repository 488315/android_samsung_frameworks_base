package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import android.view.Display;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor;
import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1;
import com.android.systemui.log.SideFpsLogger;
import java.util.Map;
import java.util.Optional;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
public final class SideFpsSensorInteractor {
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 authenticationDuration;
    public final Context context;
    public final SideFpsSensorInteractor$special$$inlined$map$2 isAvailable;
    public final Flow isProlongedTouchRequiredForAuthentication;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 isSettingEnabled;
    public final SideFpsLogger logger;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 sensorLocation;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sensorLocationForCurrentDisplay;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DisplayRotation.values().length];
            try {
                iArr[DisplayRotation.ROTATION_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DisplayRotation.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DisplayRotation.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DisplayRotation.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r11v4, types: [com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public SideFpsSensorInteractor(Context context, FingerprintPropertyRepository fingerprintPropertyRepository, final WindowManager windowManager, DisplayStateInteractor displayStateInteractor, Optional<Object> optional, BiometricSettingsRepository biometricSettingsRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, SideFpsLogger sideFpsLogger) throws Resources.NotFoundException {
        this.context = context;
        this.logger = sideFpsLogger;
        boolean z = context.getResources().getBoolean(R.bool.config_restToUnlockSupported);
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        FingerprintPropertyRepositoryImpl fingerprintPropertyRepositoryImpl = (FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.displayChanges, fingerprintPropertyRepositoryImpl.sensorLocations, SideFpsSensorInteractor$sensorLocationForCurrentDisplay$3.INSTANCE);
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsSensorInteractor this$0;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsSensorInteractor sideFpsSensorInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsSensorInteractor;
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
                        Map map = (Map) ((Pair) obj).component2();
                        Display display = this.this$0.context.getDisplay();
                        Object obj3 = map.get(display != null ? display.getUniqueId() : null);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.sensorLocationForCurrentDisplay = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        final ReadonlyStateFlow readonlyStateFlow = fingerprintPropertyRepositoryImpl.sensorType;
        ?? r11 = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((FingerprintSensorType) obj) == FingerprintSensorType.POWER_BUTTON);
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
        this.isAvailable = r11;
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new KeyguardTransitionInteractor$isFinishedInStateWhere$$inlined$map$1(keyguardTransitionInteractor.finishedKeyguardState, new SideFpsSensorInteractor$$ExternalSyntheticLambda0()));
        final Flow flow = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsSensorInteractor this$0;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsSensorInteractor sideFpsSensorInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsSensorInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:25:0x0074 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Long l;
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
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        Long l2 = null;
                        SideFpsSensorInteractor sideFpsSensorInteractor = this.this$0;
                        if (zBooleanValue) {
                            if (sideFpsSensorInteractor.context.getResources() != null) {
                                l = new Long(r5.getInteger(R.integer.config_restToUnlockDurationScreenOff));
                                l2 = l;
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(l2, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (sideFpsSensorInteractor.context.getResources() != null) {
                                l = new Long(r5.getInteger(R.integer.config_restToUnlockDurationDefault));
                                l2 = l;
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(l2, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.authenticationDuration = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4

            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        Long l = (Long) obj;
                        Long l2 = new Long(l != null ? l.longValue() : 0L);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(l2, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new SideFpsSensorInteractor$authenticationDuration$4(this, null));
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.transformLatest(((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFingerprintEnrolledAndEnabled, new SideFpsSensorInteractor$special$$inlined$flatMapLatest$1(null, optional)), new SideFpsSensorInteractor$isSettingEnabled$2(this, null));
        this.isSettingEnabled = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        this.isProlongedTouchRequiredForAuthentication = !z ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(r11, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, new SideFpsSensorInteractor$isProlongedTouchRequiredForAuthentication$1(null));
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.currentRotation, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, SideFpsSensorInteractor$sensorLocation$3.INSTANCE);
        this.sensorLocation = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5

            /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ WindowManager $windowManager$inlined;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, WindowManager windowManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$windowManager$inlined = windowManager;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Pair pair;
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
                        Pair pair2 = (Pair) obj;
                        DisplayRotation displayRotation = (DisplayRotation) pair2.component1();
                        SensorLocationInternal sensorLocationInternal = (SensorLocationInternal) pair2.component2();
                        boolean z = sensorLocationInternal.sensorLocationY != 0;
                        Rect bounds = this.$windowManager$inlined.getMaximumWindowMetrics().getBounds();
                        boolean z2 = displayRotation == DisplayRotation.ROTATION_0 || displayRotation == DisplayRotation.ROTATION_180;
                        int iWidth = z2 ? bounds.width() : bounds.height();
                        int iHeight = z2 ? bounds.height() : bounds.width();
                        int i3 = sensorLocationInternal.sensorRadius * 2;
                        if (z) {
                            int i4 = SideFpsSensorInteractor.WhenMappings.$EnumSwitchMapping$0[displayRotation.ordinal()];
                            if (i4 == 1) {
                                pair = new Pair(new Integer(iWidth), new Integer(sensorLocationInternal.sensorLocationY));
                            } else if (i4 == 2) {
                                pair = new Pair(new Integer(sensorLocationInternal.sensorLocationY), new Integer(0));
                            } else if (i4 == 3) {
                                pair = new Pair(new Integer(0), new Integer((iHeight - sensorLocationInternal.sensorLocationY) - i3));
                            } else {
                                if (i4 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                pair = new Pair(new Integer((iHeight - sensorLocationInternal.sensorLocationY) - i3), new Integer(iWidth));
                            }
                        } else {
                            int i5 = SideFpsSensorInteractor.WhenMappings.$EnumSwitchMapping$0[displayRotation.ordinal()];
                            if (i5 == 1) {
                                pair = new Pair(new Integer(sensorLocationInternal.sensorLocationX), new Integer(0));
                            } else if (i5 == 2) {
                                pair = new Pair(new Integer(0), new Integer((iWidth - sensorLocationInternal.sensorLocationX) - i3));
                            } else if (i5 == 3) {
                                pair = new Pair(new Integer((iWidth - sensorLocationInternal.sensorLocationX) - i3), new Integer(iHeight));
                            } else {
                                if (i5 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                pair = new Pair(new Integer(iHeight), new Integer(sensorLocationInternal.sensorLocationX));
                            }
                        }
                        SideFpsSensorLocation sideFpsSensorLocation = new SideFpsSensorLocation(((Number) pair.component1()).intValue(), ((Number) pair.component2()).intValue(), i3, z);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sideFpsSensorLocation, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$12.collect(new AnonymousClass2(flowCollector, windowManager), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new SideFpsSensorInteractor$$ExternalSyntheticLambda1()), new SideFpsSensorInteractor$sensorLocation$6(this, null));
    }
}
