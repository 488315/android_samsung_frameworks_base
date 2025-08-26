package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepository;
import com.android.systemui.keyguard.data.repository.DeviceEntryFingerprintAuthRepositoryImpl;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes2.dex */
public final class DeviceEntrySideFpsOverlayInteractor {
    public final Context context;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isBouncerOverlayActive;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 showIndicatorForAlternateBouncer;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 showIndicatorForPrimaryBouncer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DeviceEntrySideFpsOverlayInteractor(Context context, DeviceEntryFingerprintAuthRepository deviceEntryFingerprintAuthRepository, SceneInteractor sceneInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.isBouncerOverlayActive = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        ReadonlyStateFlow readonlyStateFlow = primaryBouncerInteractor.isShowing;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(primaryBouncerInteractor.startingDisappearAnimation);
        final ReadonlySharedFlow readonlySharedFlow = ((DeviceEntryFingerprintAuthRepositoryImpl) deviceEntryFingerprintAuthRepository).shouldUpdateIndicatorVisibility;
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(readonlyStateFlow, primaryBouncerInteractor.startingToHide, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntrySideFpsOverlayInteractor this$0;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.DeviceEntrySideFpsOverlayInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntrySideFpsOverlayInteractor deviceEntrySideFpsOverlayInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntrySideFpsOverlayInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x0079 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
                    Boolean boolValueOf;
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
                        DeviceEntrySideFpsOverlayInteractor deviceEntrySideFpsOverlayInteractor = this.this$0;
                        PrimaryBouncerInteractor primaryBouncerInteractor = deviceEntrySideFpsOverlayInteractor.primaryBouncerInteractor;
                        if (primaryBouncerInteractor.isBouncerShowing() && !((KeyguardBouncerRepositoryImpl) primaryBouncerInteractor.repository).isPrimaryBouncerStartingDisappearAnimation() && deviceEntrySideFpsOverlayInteractor.context.getResources().getBoolean(R.bool.config_show_sidefps_hint_on_bouncer)) {
                            KeyguardUpdateMonitor keyguardUpdateMonitor = deviceEntrySideFpsOverlayInteractor.keyguardUpdateMonitor;
                            if (keyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                                keyguardUpdateMonitor.getClass();
                                if (keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT)) {
                                    z = true;
                                }
                                boolValueOf = Boolean.valueOf(z);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                }
                            }
                        } else {
                            z = false;
                            boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new DeviceEntrySideFpsOverlayInteractor$showIndicatorForPrimaryBouncer$3(null));
        this.showIndicatorForPrimaryBouncer = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(alternateBouncerInteractor.isVisible, new DeviceEntrySideFpsOverlayInteractor$showIndicatorForAlternateBouncer$1(null));
        this.showIndicatorForAlternateBouncer = flowKt__TransformKt$onEach$$inlined$unsafeTransform$12;
        new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, new DeviceEntrySideFpsOverlayInteractor$showIndicatorForDeviceEntry$1(null))), new DeviceEntrySideFpsOverlayInteractor$showIndicatorForDeviceEntry$2(null));
    }
}
