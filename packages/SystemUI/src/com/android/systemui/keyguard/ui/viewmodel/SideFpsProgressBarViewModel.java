package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractor;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.SideFpsSensorInteractor;
import com.android.systemui.biometrics.domain.model.SideFpsSensorLocation;
import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SideFpsProgressBarViewModel {
    public final StateFlowImpl _progress;
    public final StateFlowImpl _visible;
    public final CoroutineScope applicationScope;
    public final Context context;
    public final DozeServiceHost dozeServiceHost;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isFingerprintAuthRunning;
    public final Flow isProlongedTouchRequiredForAuthentication;
    public final ReadonlyStateFlow isVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final SideFpsProgressBarViewModel$special$$inlined$filter$1 mergedFingerprintAuthenticationStatus;
    public final PowerInteractor powerInteractor;
    public final ReadonlyStateFlow progress;
    public final Flow progressBarLength;
    public final SideFpsProgressBarViewModel$special$$inlined$map$2 progressBarLocation;
    public final int progressBarThickness;
    public final SideFpsProgressBarViewModel$special$$inlined$map$3 rotation;
    public final SideFpsSensorInteractor sfpsSensorInteractor;

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1] */
    public SideFpsProgressBarViewModel(Context context, BiometricStatusInteractor biometricStatusInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, SideFpsSensorInteractor sideFpsSensorInteractor, DozeServiceHost dozeServiceHost, KeyguardInteractor keyguardInteractor, DisplayStateInteractor displayStateInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, PowerInteractor powerInteractor) {
        this.context = context;
        this.sfpsSensorInteractor = sideFpsSensorInteractor;
        this.dozeServiceHost = dozeServiceHost;
        this.keyguardInteractor = keyguardInteractor;
        this.mainDispatcher = coroutineDispatcher;
        this.applicationScope = coroutineScope;
        this.powerInteractor = powerInteractor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._progress = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._visible = stateFlowImplMutableStateFlow2;
        BiometricStatusInteractorImpl biometricStatusInteractorImpl = (BiometricStatusInteractorImpl) biometricStatusInteractor;
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.merge(biometricStatusInteractorImpl.fingerprintAcquiredStatus, deviceEntryFingerprintAuthInteractor.authenticationStatus));
        this.mergedFingerprintAuthenticationStatus = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
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
                        FingerprintAuthenticationStatus fingerprintAuthenticationStatus = (FingerprintAuthenticationStatus) obj;
                        if (fingerprintAuthenticationStatus instanceof AcquiredFingerprintAuthenticationStatus) {
                            AcquiredFingerprintAuthenticationStatus acquiredFingerprintAuthenticationStatus = (AcquiredFingerprintAuthenticationStatus) fingerprintAuthenticationStatus;
                            if (Intrinsics.areEqual(acquiredFingerprintAuthenticationStatus.authenticationReason, AuthenticationReason.DeviceEntryAuthentication.INSTANCE) || Intrinsics.areEqual(acquiredFingerprintAuthenticationStatus.authenticationReason, AuthenticationReason.BiometricPromptAuthentication.INSTANCE)) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.progress = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = sideFpsSensorInteractor.sensorLocation;
        this.progressBarLength = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((SideFpsSensorLocation) obj).length);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.progressBarThickness = (int) context.getResources().getDimension(R.dimen.sfps_progress_bar_thickness);
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        ReadonlyStateFlow readonlyStateFlow = displayStateInteractorImpl.currentRotation;
        SideFpsProgressBarViewModel$progressBarLocation$3 sideFpsProgressBarViewModel$progressBarLocation$3 = SideFpsProgressBarViewModel$progressBarLocation$3.INSTANCE;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = sideFpsSensorInteractor.sensorLocation;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, sideFpsProgressBarViewModel$progressBarLocation$3);
        this.progressBarLocation = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SideFpsProgressBarViewModel this$0;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SideFpsProgressBarViewModel sideFpsProgressBarViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = sideFpsProgressBarViewModel;
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
                        Pair pair = (Pair) obj;
                        DisplayRotation displayRotation = (DisplayRotation) pair.component1();
                        SideFpsSensorLocation sideFpsSensorLocation = (SideFpsSensorLocation) pair.component2();
                        SideFpsProgressBarViewModel sideFpsProgressBarViewModel = this.this$0;
                        int dimension = (int) sideFpsProgressBarViewModel.context.getResources().getDimension(R.dimen.sfps_progress_bar_padding_from_edge);
                        Point point = new Point(sideFpsSensorLocation.left, sideFpsSensorLocation.top);
                        int i3 = sideFpsProgressBarViewModel.progressBarThickness + dimension;
                        DisplayRotation displayRotation2 = DisplayRotation.ROTATION_0;
                        if (sideFpsSensorLocation.isSensorVerticalInDefaultOrientation == (displayRotation == displayRotation2 || displayRotation == DisplayRotation.ROTATION_180)) {
                            point.y += sideFpsSensorLocation.length;
                            if (displayRotation == DisplayRotation.ROTATION_180 || displayRotation == DisplayRotation.ROTATION_90) {
                                point.x += dimension;
                            } else {
                                point.x -= i3;
                            }
                        } else if (displayRotation == displayRotation2 || displayRotation == DisplayRotation.ROTATION_90) {
                            point.y += dimension;
                        } else {
                            point.y -= i3;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(point, anonymousClass1) == coroutineSingletons) {
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
        };
        this.isFingerprintAuthRunning = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(deviceEntryFingerprintAuthInteractor.isRunning, biometricStatusInteractorImpl.sfpsAuthenticationReason, new SideFpsProgressBarViewModel$isFingerprintAuthRunning$1(null));
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayStateInteractorImpl.currentRotation, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, SideFpsProgressBarViewModel$rotation$3.INSTANCE);
        this.rotation = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        Pair pair = (Pair) obj;
                        DisplayRotation displayRotation = (DisplayRotation) pair.component1();
                        Float f = new Float((displayRotation == DisplayRotation.ROTATION_0 || displayRotation == DisplayRotation.ROTATION_180) == ((SideFpsSensorLocation) pair.component2()).isSensorVerticalInDefaultOrientation ? 270.0f : 0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$12.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isProlongedTouchRequiredForAuthentication = sideFpsSensorInteractor.isProlongedTouchRequiredForAuthentication;
    }

    public final void setVisible(boolean z) {
        this._visible.updateState(null, Boolean.valueOf(z));
    }
}
