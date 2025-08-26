package com.android.systemui.deviceentry.domain.interactor;

import android.hardware.face.FaceManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.shared.model.FaceAuthenticationStatus;
import com.android.systemui.deviceentry.shared.model.SuccessFaceAuthenticationStatus;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardBypassInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.SuccessFingerprintAuthenticationStatus;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.kotlin.Utils;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class DeviceEntrySourceInteractor extends FlowDumperImpl {
    public final SharedFlowImpl _attemptEnterDeviceFromDeviceEntryIcon;
    public final SharedFlowImpl attemptEnterDeviceFromDeviceEntryIcon;
    public final Flow deviceEntryFromBiometricSource;

    public DeviceEntrySourceInteractor(AuthenticationInteractor authenticationInteractor, final AuthController authController, AlternateBouncerInteractor alternateBouncerInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, final DozeScrimController dozeScrimController, KeyguardBypassInteractor keyguardBypassInteractor, final KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardInteractor keyguardInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, SceneInteractor sceneInteractor, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        final ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        Flow flowDumpWhileCollecting = dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        ObservableTransitionState observableTransitionState = (ObservableTransitionState) obj;
                        OverlayKey overlayKey = Overlays.Bouncer;
                        Boolean boolValueOf = Boolean.valueOf(ObservableTransitionState.isIdle$default(observableTransitionState, null, overlayKey, 1) || observableTransitionState.isTransitioning(null, overlayKey));
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
        }), "isShowingBouncerOverlay");
        final Flow authenticationStatus = deviceEntryFaceAuthInteractor.getAuthenticationStatus();
        Flow flowDumpWhileCollecting2 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                    FaceManager.AuthenticationResult authenticationResult;
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
                        FaceAuthenticationStatus faceAuthenticationStatus = (FaceAuthenticationStatus) obj;
                        SuccessFaceAuthenticationStatus successFaceAuthenticationStatus = faceAuthenticationStatus instanceof SuccessFaceAuthenticationStatus ? (SuccessFaceAuthenticationStatus) faceAuthenticationStatus : null;
                        Boolean boolValueOf = Boolean.valueOf((successFaceAuthenticationStatus == null || (authenticationResult = successFaceAuthenticationStatus.successResult) == null) ? false : authenticationResult.isStrongBiometric());
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
                Object objCollect = authenticationStatus.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, "unlockedWithStrongFaceUnlock");
        final Flow flow = deviceEntryFingerprintAuthInteractor.authenticationStatus;
        Flow flowDumpWhileCollecting3 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        FingerprintAuthenticationStatus fingerprintAuthenticationStatus = (FingerprintAuthenticationStatus) obj;
                        SuccessFingerprintAuthenticationStatus successFingerprintAuthenticationStatus = fingerprintAuthenticationStatus instanceof SuccessFingerprintAuthenticationStatus ? (SuccessFingerprintAuthenticationStatus) fingerprintAuthenticationStatus : null;
                        Boolean boolValueOf = Boolean.valueOf(successFingerprintAuthenticationStatus != null ? successFingerprintAuthenticationStatus.isStrongBiometric : false);
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, "unlockedWithStrongFingerprintUnlock");
        final Flow[] flowArr = {alternateBouncerInteractor.isVisible, keyguardBypassInteractor.isBypassAvailable, flowDumpWhileCollecting2, sceneContainerOcclusionInteractor.isOccludingActivityShown, sceneInteractor.currentScene, flowDumpWhileCollecting};
        final Flow flow2 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ AuthController $authController$inlined;
                final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardUpdateMonitor keyguardUpdateMonitor, AuthController authController) {
                    super(3, continuation);
                    this.$keyguardUpdateMonitor$inlined = keyguardUpdateMonitor;
                    this.$authController$inlined = authController;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$keyguardUpdateMonitor$inlined, this.$authController$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
                /* JADX WARN: Removed duplicated region for block: B:20:0x0070  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x0074  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x007a  */
                /* JADX WARN: Removed duplicated region for block: B:26:0x007c  */
                /* JADX WARN: Removed duplicated region for block: B:33:0x008e  */
                /* JADX WARN: Removed duplicated region for block: B:45:0x00ad A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    boolean z;
                    Integer num;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        int i2 = 0;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                        SceneKey sceneKey = (SceneKey) obj6;
                        boolean zBooleanValue2 = ((Boolean) obj5).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) obj3).booleanValue();
                        boolean zBooleanValue5 = ((Boolean) obj2).booleanValue();
                        boolean zIsUnlockingWithBiometricAllowed = this.$keyguardUpdateMonitor$inlined.isUnlockingWithBiometricAllowed(zBooleanValue3);
                        if (zBooleanValue4) {
                            z = true;
                            if (this.$keyguardUpdateMonitor$inlined.mDeviceInteractive) {
                                if (zIsUnlockingWithBiometricAllowed) {
                                    i2 = z ? 2 : 4;
                                } else if (z) {
                                    i2 = 3;
                                }
                                num = new Integer(i2);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (zIsUnlockingWithBiometricAllowed && Intrinsics.areEqual(sceneKey, Scenes.Dream)) {
                                    if (z) {
                                        i2 = 6;
                                    }
                                } else if (!zIsUnlockingWithBiometricAllowed || !zBooleanValue2) {
                                    if ((zBooleanValue || zBooleanValue5) && zIsUnlockingWithBiometricAllowed) {
                                        i2 = 7;
                                    } else if (zIsUnlockingWithBiometricAllowed && z) {
                                        i2 = 5;
                                    } else if (z) {
                                    }
                                }
                                num = new Integer(i2);
                                this.label = 1;
                                if (flowCollector.emit(num, this) == coroutineSingletons) {
                                }
                            }
                        } else {
                            UdfpsController udfpsController = this.$authController$inlined.mUdfpsController;
                            if (!(udfpsController == null ? false : udfpsController.mOnFingerDown)) {
                                z = false;
                            }
                            if (this.$keyguardUpdateMonitor$inlined.mDeviceInteractive) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, keyguardUpdateMonitor, authController), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        final Flow flowDumpWhileCollecting4 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntrySourceInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntrySourceInteractor deviceEntrySourceInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntrySourceInteractor;
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
                        BiometricUnlockMode biometricUnlockModeAccess$biometricModeIntToObject = DeviceEntrySourceInteractor.access$biometricModeIntToObject(this.this$0, ((Number) obj).intValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricUnlockModeAccess$biometricModeIntToObject, anonymousClass1) == coroutineSingletons) {
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
        }, "faceWakeAndUnlockMode");
        final Flow[] flowArr2 = {alternateBouncerInteractor.isVisible, authenticationInteractor.authenticationMethod, sceneInteractor.currentScene, sceneInteractor.currentOverlays, flowDumpWhileCollecting3, flowDumpWhileCollecting};
        final Flow flow3 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ DozeScrimController $dozeScrimController$inlined;
                final /* synthetic */ KeyguardUpdateMonitor $keyguardUpdateMonitor$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeScrimController dozeScrimController) {
                    super(3, continuation);
                    this.$keyguardUpdateMonitor$inlined = keyguardUpdateMonitor;
                    this.$dozeScrimController$inlined = dozeScrimController;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$keyguardUpdateMonitor$inlined, this.$dozeScrimController$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:18:0x0067  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x0099 A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    Integer num;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        int i2 = 0;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj6).booleanValue();
                        Set set = (Set) obj5;
                        SceneKey sceneKey = (SceneKey) obj4;
                        AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) obj3;
                        boolean zBooleanValue3 = ((Boolean) obj2).booleanValue();
                        boolean zIsUnlockingWithBiometricAllowed = this.$keyguardUpdateMonitor$inlined.isUnlockingWithBiometricAllowed(zBooleanValue2);
                        if (this.$keyguardUpdateMonitor$inlined.mDeviceInteractive) {
                            if (zIsUnlockingWithBiometricAllowed && Intrinsics.areEqual(sceneKey, Scenes.Dream)) {
                                i2 = 6;
                            } else if (zBooleanValue && zIsUnlockingWithBiometricAllowed) {
                                i2 = 7;
                            } else if (zIsUnlockingWithBiometricAllowed) {
                                i2 = 5;
                            } else if (!set.contains(Overlays.Bouncer) && !zBooleanValue3) {
                            }
                            num = new Integer(i2);
                            this.label = 1;
                            if (flowCollector.emit(num, this) == coroutineSingletons) {
                            }
                        } else {
                            i2 = (this.$dozeScrimController$inlined.mPulseCallback == null || !zIsUnlockingWithBiometricAllowed) ? (zIsUnlockingWithBiometricAllowed || !authenticationMethodModel.isSecure) ? 1 : 3 : 2;
                            num = new Integer(i2);
                            this.label = 1;
                            if (flowCollector.emit(num, this) == coroutineSingletons) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr3 = flowArr2;
                Object objCombineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$combine$2.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new AnonymousClass3(null, keyguardUpdateMonitor, dozeScrimController), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        final Flow flowDumpWhileCollecting5 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DeviceEntrySourceInteractor this$0;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DeviceEntrySourceInteractor deviceEntrySourceInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = deviceEntrySourceInteractor;
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
                        BiometricUnlockMode biometricUnlockModeAccess$biometricModeIntToObject = DeviceEntrySourceInteractor.access$biometricModeIntToObject(this.this$0, ((Number) obj).intValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricUnlockModeAccess$biometricModeIntToObject, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow3.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, "fingerprintWakeAndUnlockMode");
        final Flow flow4 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        BiometricUnlockMode.Companion.getClass();
                        if (BiometricUnlockMode.dismissesKeyguardModes.contains((BiometricUnlockMode) obj)) {
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
                Object objCollect = flowDumpWhileCollecting5.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow5 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        BiometricUnlockModel biometricUnlockModel = new BiometricUnlockModel((BiometricUnlockMode) obj, BiometricUnlockSource.FINGERPRINT_SENSOR);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricUnlockModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow6 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        BiometricUnlockMode.Companion.getClass();
                        if (BiometricUnlockMode.dismissesKeyguardModes.contains((BiometricUnlockMode) obj)) {
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
                Object objCollect = flowDumpWhileCollecting4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        dumpWhileCollecting(FlowKt.merge(flow5, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        BiometricUnlockModel biometricUnlockModel = new BiometricUnlockModel((BiometricUnlockMode) obj, BiometricUnlockSource.FACE_SENSOR);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricUnlockModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow6.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "biometricUnlockState");
        final Flow flow7 = deviceEntryFingerprintAuthInteractor.authenticationStatus;
        Flow flowDumpWhileCollecting6 = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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
                        if (obj instanceof SuccessFingerprintAuthenticationStatus) {
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
                Object objCollect = flow7.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, "deviceEntryFingerprintAuthSuccessEvents");
        Utils.Companion companion = Utils.Companion;
        final Flow authenticationStatus2 = deviceEntryFaceAuthInteractor.getAuthenticationStatus();
        Flow flow8 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filterIsInstance$2$2$1, reason: invalid class name */
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
                        if (obj instanceof SuccessFaceAuthenticationStatus) {
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
                Object objCollect = authenticationStatus2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        DeviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3 deviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3 = DeviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3.INSTANCE;
        dumpWhileCollecting(FlowKt.merge(flowDumpWhileCollecting6, dumpWhileCollecting(companion.sampleFilter(flow8, FlowKt.combine(sceneContainerOcclusionInteractor.isOccludingActivityShown, keyguardBypassInteractor.isBypassAvailable, keyguardBypassInteractor.canBypass, deviceEntrySourceInteractor$deviceEntryFaceAuthWakeAndUnlockEvents$3), new DeviceEntrySourceInteractor$$ExternalSyntheticLambda0()), "deviceEntryFaceAuthSuccessEvents")), "deviceEntryBiometricAuthSuccessEvents");
        final ReadonlyStateFlow readonlyStateFlow2 = keyguardInteractor.biometricUnlockState;
        final Flow flow9 = new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                        BiometricUnlockMode.Companion companion = BiometricUnlockMode.Companion;
                        BiometricUnlockMode biometricUnlockMode = ((BiometricUnlockModel) obj).mode;
                        companion.getClass();
                        if (BiometricUnlockMode.dismissesKeyguardModes.contains(biometricUnlockMode)) {
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.deviceEntryFromBiometricSource = dumpWhileCollecting(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntrySourceInteractor$special$$inlined$map$9$2$1, reason: invalid class name */
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
                        BiometricUnlockSource biometricUnlockSource = ((BiometricUnlockModel) obj).source;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricUnlockSource, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow9.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "deviceEntryFromBiometricSource");
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._attemptEnterDeviceFromDeviceEntryIcon = sharedFlowImplMutableSharedFlow$default;
        this.attemptEnterDeviceFromDeviceEntryIcon = sharedFlowImplMutableSharedFlow$default;
    }

    public static final BiometricUnlockMode access$biometricModeIntToObject(DeviceEntrySourceInteractor deviceEntrySourceInteractor, int i) {
        deviceEntrySourceInteractor.getClass();
        switch (i) {
            case 0:
                return BiometricUnlockMode.NONE;
            case 1:
                return BiometricUnlockMode.WAKE_AND_UNLOCK;
            case 2:
                return BiometricUnlockMode.WAKE_AND_UNLOCK_PULSING;
            case 3:
                return BiometricUnlockMode.SHOW_BOUNCER;
            case 4:
                return BiometricUnlockMode.ONLY_WAKE;
            case 5:
                return BiometricUnlockMode.UNLOCK_COLLAPSING;
            case 6:
                return BiometricUnlockMode.WAKE_AND_UNLOCK_FROM_DREAM;
            case 7:
                return BiometricUnlockMode.DISMISS_BOUNCER;
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid BiometricUnlockModel value: "));
        }
    }

    private static /* synthetic */ void getBiometricUnlockStateOnKeyguardDismissed$annotations() {
    }
}
