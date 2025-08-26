package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.shared.customization.data.SensorLocation;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes.dex */
public final class FingerprintPropertyInteractor {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final ReadonlyStateFlow isUdfps;
    public final Flow propertiesInitialized;
    public final ReadonlyStateFlow sensorLocation;
    public final Flow udfpsSensorBounds;
    public final ReadonlyStateFlow uniqueDisplayId;
    public final ReadonlyStateFlow unscaledSensorLocation;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final ReadonlyStateFlow access$combineStates(Companion companion, ReadonlyStateFlow readonlyStateFlow, ReadonlyStateFlow readonlyStateFlow2, CoroutineScope coroutineScope, Function2 function2) {
            companion.getClass();
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, new FingerprintPropertyInteractor$Companion$combineStates$1(function2, null));
            SharingStarted.Companion.getClass();
            return FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, function2.invoke(readonlyStateFlow.$$delegate_0.getValue(), readonlyStateFlow2.$$delegate_0.getValue()));
        }

        private Companion() {
        }
    }

    public FingerprintPropertyInteractor(CoroutineScope coroutineScope, Context context, FingerprintPropertyRepository fingerprintPropertyRepository, ConfigurationInteractor configurationInteractor, DisplayStateInteractor displayStateInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        this.context = context;
        FingerprintPropertyRepositoryImpl fingerprintPropertyRepositoryImpl = (FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository;
        this.propertiesInitialized = fingerprintPropertyRepositoryImpl.propertiesInitialized;
        final ReadonlyStateFlow readonlyStateFlow = fingerprintPropertyRepositoryImpl.sensorType;
        Flow flow = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((FingerprintSensorType) obj).isUdfps());
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
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.isUdfps = FlowKt.stateIn(flow, coroutineScope, startedEagerly, Boolean.valueOf(((FingerprintSensorType) fingerprintPropertyRepositoryImpl.sensorType.$$delegate_0.getValue()).isUdfps()));
        final Flow flow2 = ((DisplayStateInteractorImpl) displayStateInteractor).displayChanges;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FingerprintPropertyInteractor this$0;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, FingerprintPropertyInteractor fingerprintPropertyInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = fingerprintPropertyInteractor;
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
                        ((Number) obj).intValue();
                        String uniqueId = this.this$0.context.getDisplay().getUniqueId();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(uniqueId, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        })), coroutineScope, startedEagerly, "");
        this.uniqueDisplayId = readonlyStateFlowStateIn;
        ReadonlyStateFlow readonlyStateFlow2 = fingerprintPropertyRepositoryImpl.sensorLocations;
        final int i = 0;
        Function2 function2 = new Function2() { // from class: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        Map map = (Map) obj;
                        FingerprintPropertyInteractor.Companion companion = FingerprintPropertyInteractor.Companion;
                        return (SensorLocationInternal) map.getOrDefault((String) obj2, map.getOrDefault("", SensorLocationInternal.DEFAULT));
                    default:
                        SensorLocationInternal sensorLocationInternal = (SensorLocationInternal) obj;
                        float fFloatValue = ((Float) obj2).floatValue();
                        FingerprintPropertyInteractor.Companion companion2 = FingerprintPropertyInteractor.Companion;
                        return new SensorLocation(sensorLocationInternal.sensorLocationX, sensorLocationInternal.sensorLocationY, sensorLocationInternal.sensorRadius, fFloatValue);
                }
            }
        };
        Companion companion = Companion;
        ReadonlyStateFlow readonlyStateFlowAccess$combineStates = Companion.access$combineStates(companion, readonlyStateFlow2, readonlyStateFlowStateIn, coroutineScope, function2);
        this.unscaledSensorLocation = readonlyStateFlowAccess$combineStates;
        final int i2 = 1;
        this.sensorLocation = Companion.access$combineStates(companion, readonlyStateFlowAccess$combineStates, ((ConfigurationInteractorImpl) configurationInteractor).scaleForResolution, coroutineScope, new Function2() { // from class: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                switch (i2) {
                    case 0:
                        Map map = (Map) obj;
                        FingerprintPropertyInteractor.Companion companion2 = FingerprintPropertyInteractor.Companion;
                        return (SensorLocationInternal) map.getOrDefault((String) obj2, map.getOrDefault("", SensorLocationInternal.DEFAULT));
                    default:
                        SensorLocationInternal sensorLocationInternal = (SensorLocationInternal) obj;
                        float fFloatValue = ((Float) obj2).floatValue();
                        FingerprintPropertyInteractor.Companion companion22 = FingerprintPropertyInteractor.Companion;
                        return new SensorLocation(sensorLocationInternal.sensorLocationX, sensorLocationInternal.sensorLocationY, sensorLocationInternal.sensorRadius, fFloatValue);
                }
            }
        });
        final ReadonlyStateFlow readonlyStateFlow3 = udfpsOverlayInteractor.udfpsOverlayParams;
        this.udfpsSensorBounds = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        Rect rect = ((UdfpsOverlayParams) obj).sensorBounds;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(rect, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }
}
