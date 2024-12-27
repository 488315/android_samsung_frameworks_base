package com.android.systemui.biometrics.ui.viewmodel;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.biometrics.PromptInfo;
import android.util.Log;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractor;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

public final class PromptViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _accessibilityHint;
    public final StateFlowImpl _canTryAgainNow;
    public final StateFlowImpl _fingerprintStartMode;
    public final StateFlowImpl _forceLargeSize;
    public final StateFlowImpl _forceMediumSize;
    public final StateFlowImpl _hapticsToPlay;
    public final StateFlowImpl _isAuthenticated;
    public final StateFlowImpl _isAuthenticating;
    public final StateFlowImpl _isIconViewLoaded;
    public final StateFlowImpl _isOverlayTouched;
    public final StateFlowImpl _message;
    public final ReadonlySharedFlow accessibilityHint;
    public final ActivityTaskManager activityTaskManager;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 canTryAgainNow;
    public final Flow contentView;
    public final Context context;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 credentialKind;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 description;
    public final int faceIconHeight;
    public final int faceIconWidth;
    public final Flow faceMode;
    public final int fingerprintIconHeight;
    public final int fingerprintIconWidth;
    public final int fingerprintSensorHeight;
    public final int fingerprintSensorWidth;
    public final ReadonlyStateFlow fingerprintStartMode;
    public final Flow guidelineBounds;
    public final ReadonlyStateFlow hapticsToPlay;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 hasFingerOnSensor;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 hasOnlyOneLineTitle;
    public final Flow hideSensorIcon;
    public final PromptHistoryImpl history;
    public final Flow iconPosition;
    public final IconProvider iconProvider;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconSize;
    public final PromptIconViewModel iconViewModel;
    public final ReadonlyStateFlow isAuthenticated;
    public final ReadonlyStateFlow isAuthenticating;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 isCancelButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isConfirmButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isConfirmationRequired;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 isCredentialButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isIconConfirmButton;
    public final Flow isIconViewLoaded;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isIndicatorMessageVisible;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 isNegativeButtonVisible;
    public final PromptViewModel$special$$inlined$map$2 isPendingConfirmation;
    public final PromptViewModel$special$$inlined$map$6 isRetrySupported;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isTryAgainButtonVisible;
    public final int landscapeMediumBottomPadding;
    public final int landscapeMediumHorizontalPadding;
    public final int landscapeSmallBottomPadding;
    public final int landscapeSmallHorizontalPadding;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 legacyFingerprintSensorHeight;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 legacyFingerprintSensorWidth;
    public final Flow logo;
    public final Flow logoDescription;
    public final int mediumHorizontalGuidelinePadding;
    public final int mediumTopGuidelinePadding;
    public final ReadonlyStateFlow message;
    public Job messageJob;
    public final Flow modalities;
    public final PromptViewModel$special$$inlined$map$4 negativeButtonText;
    public final int portraitLargeScreenBottomPadding;
    public final int portraitMediumBottomPadding;
    public final int portraitSmallBottomPadding;
    public final Flow position;
    public final ReadonlyStateFlow promptKind;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 promptPadding;
    public final PromptSelectorInteractor promptSelectorInteractor;
    public final Flow showingError;
    public final Flow size;
    public final int smallHorizontalGuidelinePadding;
    public final Flow subtitle;
    public final Flow title;
    public final int udfpsHorizontalGuidelinePadding;
    public final int udfpsHorizontalShorterGuidelinePadding;
    public final UdfpsOverlayInteractor udfpsOverlayInteractor;
    public final UdfpsUtils udfpsUtils;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PromptPosition.values().length];
            try {
                iArr[PromptPosition.Bottom.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PromptPosition.Right.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PromptPosition.Left.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PromptPosition.Top.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public PromptViewModel(DisplayStateInteractor displayStateInteractor, PromptSelectorInteractor promptSelectorInteractor, Context context, UdfpsOverlayInteractor udfpsOverlayInteractor, BiometricStatusInteractor biometricStatusInteractor, UdfpsUtils udfpsUtils, IconProvider iconProvider, ActivityTaskManager activityTaskManager) {
        this.promptSelectorInteractor = promptSelectorInteractor;
        this.context = context;
        this.udfpsOverlayInteractor = udfpsOverlayInteractor;
        this.udfpsUtils = udfpsUtils;
        this.iconProvider = iconProvider;
        this.activityTaskManager = activityTaskManager;
        PromptSelectorInteractorImpl promptSelectorInteractorImpl = (PromptSelectorInteractorImpl) promptSelectorInteractor;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = promptSelectorInteractorImpl.prompt;
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r5
                        if (r5 == 0) goto L3a
                        com.android.systemui.biometrics.shared.model.BiometricModalities r5 = r5.modalities
                        if (r5 != 0) goto L41
                    L3a:
                        com.android.systemui.biometrics.shared.model.BiometricModalities r5 = new com.android.systemui.biometrics.shared.model.BiometricModalities
                        r6 = 3
                        r2 = 0
                        r5.<init>(r2, r2, r6, r2)
                    L41:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.modalities = distinctUntilChanged;
        this.fingerprintIconWidth = context.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_fingerprint_icon_width);
        this.fingerprintIconHeight = context.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_fingerprint_icon_height);
        this.faceIconWidth = context.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_face_icon_size);
        this.faceIconHeight = context.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_face_icon_size);
        this.portraitSmallBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_portrait_small_bottom_padding);
        this.portraitMediumBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_portrait_medium_bottom_padding);
        this.portraitLargeScreenBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_portrait_large_screen_bottom_padding);
        this.landscapeSmallBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_landscape_small_bottom_padding);
        this.landscapeSmallHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_landscape_small_horizontal_padding);
        this.landscapeMediumBottomPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_landscape_medium_bottom_padding);
        this.landscapeMediumHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_landscape_medium_horizontal_padding);
        ReadonlyStateFlow readonlyStateFlow = udfpsOverlayInteractor.udfpsOverlayParams;
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        ReadonlyStateFlow readonlyStateFlow2 = displayStateInteractorImpl.currentRotation;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, new PromptViewModel$udfpsSensorBounds$1(null)));
        this.legacyFingerprintSensorWidth = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, readonlyStateFlow, new PromptViewModel$legacyFingerprintSensorWidth$1(this, null));
        this.legacyFingerprintSensorHeight = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, readonlyStateFlow, new PromptViewModel$legacyFingerprintSensorHeight$1(this, null));
        this.fingerprintSensorWidth = ((UdfpsOverlayParams) readonlyStateFlow.$$delegate_0.getValue()).sensorBounds.width();
        this.fingerprintSensorHeight = ((UdfpsOverlayParams) readonlyStateFlow.$$delegate_0.getValue()).sensorBounds.height();
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._accessibilityHint = MutableSharedFlow$default;
        this.accessibilityHint = FlowKt.asSharedFlow(MutableSharedFlow$default);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticating = MutableStateFlow;
        this.isAuthenticating = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new PromptAuthState(false, null, false, 0L, 14, null));
        this._isAuthenticated = MutableStateFlow2;
        final ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow2);
        this.isAuthenticated = asStateFlow;
        ?? r11 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r5 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r5
                        boolean r6 = r5.isAuthenticated
                        if (r6 == 0) goto L3e
                        boolean r5 = r5.needsUserConfirmation
                        if (r5 == 0) goto L3e
                        r5 = r3
                        goto L3f
                    L3e:
                        r5 = 0
                    L3f:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isPendingConfirmation = r11;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isOverlayTouched = MutableStateFlow3;
        this.credentialKind = promptSelectorInteractorImpl.credentialKind;
        ReadonlyStateFlow readonlyStateFlow3 = promptSelectorInteractorImpl.promptKind;
        this.promptKind = readonlyStateFlow3;
        Flow distinctUntilChanged3 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.shared.model.BiometricModalities r5 = (com.android.systemui.biometrics.shared.model.BiometricModalities) r5
                        boolean r6 = r5.getHasFingerprint()
                        if (r6 != 0) goto L41
                        android.hardware.face.FaceSensorPropertiesInternal r5 = r5.faceProperties
                        if (r5 == 0) goto L3f
                        goto L41
                    L3f:
                        r5 = r3
                        goto L42
                    L41:
                        r5 = 0
                    L42:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.hideSensorIcon = distinctUntilChanged3;
        this.negativeButtonText = new PromptViewModel$special$$inlined$map$4(promptSelectorInteractorImpl.prompt);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(PromptMessage.Empty.INSTANCE);
        this._message = MutableStateFlow4;
        final ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow4);
        this.message = asStateFlow2;
        this.showingError = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.ui.viewmodel.PromptMessage r5 = (com.android.systemui.biometrics.ui.viewmodel.PromptMessage) r5
                        r5.getClass()
                        boolean r5 = r5 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Error
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        Flow flow = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.shared.model.BiometricModalities r5 = (com.android.systemui.biometrics.shared.model.BiometricModalities) r5
                        android.hardware.face.FaceSensorPropertiesInternal r5 = r5.faceProperties
                        if (r5 == 0) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(FingerprintStartMode.Pending);
        this._fingerprintStartMode = MutableStateFlow5;
        ReadonlyStateFlow asStateFlow3 = FlowKt.asStateFlow(MutableStateFlow5);
        this.fingerprintStartMode = asStateFlow3;
        this.hasFingerOnSensor = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((BiometricStatusInteractorImpl) biometricStatusInteractor).fingerprintAcquiredStatus, distinctUntilChanged, new PromptViewModel$hasFingerBeenAcquired$1(null))), MutableStateFlow3, new PromptViewModel$hasFingerOnSensor$1(null));
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._forceLargeSize = MutableStateFlow6;
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._forceMediumSize = MutableStateFlow7;
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(new HapticsToPlay(-1, null));
        this._hapticsToPlay = MutableStateFlow8;
        this.hapticsToPlay = FlowKt.asStateFlow(MutableStateFlow8);
        Flow distinctUntilChanged4 = FlowKt.distinctUntilChanged(FlowKt.combine(MutableStateFlow6, readonlyStateFlow3, displayStateInteractorImpl.isLargeScreen, readonlyStateFlow2, distinctUntilChanged, new PromptViewModel$position$1(null)));
        this.position = distinctUntilChanged4;
        Flow distinctUntilChanged5 = FlowKt.distinctUntilChanged(FlowKt.combine(MutableStateFlow6, MutableStateFlow7, distinctUntilChanged, promptSelectorInteractorImpl.isConfirmationRequired, asStateFlow3, new PromptViewModel$size$1(null)));
        this.size = distinctUntilChanged5;
        this.smallHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_land_small_horizontal_guideline_padding);
        this.udfpsHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_udfps_horizontal_guideline_padding);
        this.udfpsHorizontalShorterGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_udfps_shorter_horizontal_guideline_padding);
        this.mediumTopGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_one_pane_medium_top_guideline_padding);
        this.mediumHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_medium_horizontal_guideline_padding);
        Flow distinctUntilChanged6 = FlowKt.distinctUntilChanged(FlowKt.combine(distinctUntilChanged2, distinctUntilChanged5, distinctUntilChanged4, distinctUntilChanged, new PromptViewModel$iconPosition$1(this, null)));
        this.iconPosition = distinctUntilChanged6;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow3, distinctUntilChanged5, new PromptViewModel$isConfirmationRequired$1(null));
        this.isConfirmationRequired = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.faceMode = FlowKt.distinctUntilChanged(FlowKt.combine(distinctUntilChanged, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, asStateFlow3, new PromptViewModel$faceMode$1(null)));
        PromptIconViewModel promptIconViewModel = new PromptIconViewModel(this, displayStateInteractor, promptSelectorInteractor, udfpsOverlayInteractor);
        this.iconViewModel = promptIconViewModel;
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(bool);
        this._isIconViewLoaded = MutableStateFlow9;
        FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged3, FlowKt.asStateFlow(MutableStateFlow9), new PromptViewModel$isIconViewLoaded$1(null)));
        this.iconSize = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptIconViewModel.activeAuthType, distinctUntilChanged, new PromptViewModel$iconSize$1(this, null));
        this.promptPadding = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged5, readonlyStateFlow2, new PromptViewModel$promptPadding$1(this, null));
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$32 = promptSelectorInteractorImpl.prompt;
        this.logo = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PromptViewModel this$0;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, PromptViewModel promptViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = promptViewModel;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r14, kotlin.coroutines.Continuation r15) {
                    /*
                        Method dump skipped, instructions count: 249
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$33 = promptSelectorInteractorImpl.prompt;
        this.logoDescription = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PromptViewModel this$0;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, PromptViewModel promptViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = promptViewModel;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto La4
                    L28:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L30:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r6 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r6
                        boolean r7 = com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.customBiometricPrompt()
                        java.lang.String r2 = ""
                        if (r7 == 0) goto L99
                        com.android.systemui.Flags.constraintBp()
                        if (r6 != 0) goto L43
                        goto L99
                    L43:
                        java.lang.String r7 = r6.logoDescription
                        if (r7 == 0) goto L50
                        int r4 = r7.length()
                        if (r4 != 0) goto L4e
                        goto L50
                    L4e:
                        r2 = r7
                        goto L99
                    L50:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r7 = r5.this$0
                        android.content.Context r4 = r7.context
                        android.app.ActivityTaskManager r7 = r7.activityTaskManager
                        android.content.ComponentName r7 = com.android.systemui.biometrics.ui.viewmodel.PromptViewModelKt.getComponentNameForLogo(r6, r7)
                        android.content.pm.ApplicationInfo r6 = com.android.systemui.biometrics.ui.viewmodel.PromptViewModelKt.getApplicationInfoForLogo(r6, r4, r7)
                        if (r6 == 0) goto L8e
                        android.content.pm.PackageManager r7 = r4.getPackageManager()
                        java.lang.CharSequence r7 = r7.getApplicationLabel(r6)
                        if (r7 == 0) goto L8e
                        int r7 = r7.length()
                        if (r7 != 0) goto L71
                        goto L8e
                    L71:
                        android.content.pm.PackageManager r7 = r4.getPackageManager()
                        android.content.pm.PackageManager r2 = r4.getPackageManager()
                        java.lang.CharSequence r6 = r2.getApplicationLabel(r6)
                        int r2 = r4.getUserId()
                        android.os.UserHandle r2 = android.os.UserHandle.of(r2)
                        java.lang.CharSequence r6 = r7.getUserBadgedLabel(r6, r2)
                        java.lang.String r2 = r6.toString()
                        goto L99
                    L8e:
                        java.lang.String r6 = r4.getOpPackageName()
                        java.lang.String r7 = "Cannot find app logo for package "
                        java.lang.String r4 = "PromptViewModel"
                        androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m(r7, r6, r4)
                    L99:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r2, r0)
                        if (r5 != r1) goto La4
                        return r1
                    La4:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$34 = promptSelectorInteractorImpl.prompt;
        Flow distinctUntilChanged7 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r5
                        if (r5 == 0) goto L3a
                        java.lang.String r5 = r5.title
                        if (r5 != 0) goto L3c
                    L3a:
                        java.lang.String r5 = ""
                    L3c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.title = distinctUntilChanged7;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$35 = promptSelectorInteractorImpl.prompt;
        Flow distinctUntilChanged8 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r5
                        if (r5 == 0) goto L3a
                        java.lang.String r5 = r5.subtitle
                        if (r5 != 0) goto L3c
                    L3a:
                        java.lang.String r5 = ""
                    L3c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.subtitle = distinctUntilChanged8;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$36 = promptSelectorInteractorImpl.prompt;
        Flow distinctUntilChanged9 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r5
                        boolean r6 = com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.customBiometricPrompt()
                        r2 = 0
                        if (r6 == 0) goto L42
                        com.android.systemui.Flags.constraintBp()
                        if (r5 == 0) goto L42
                        android.hardware.biometrics.PromptContentView r2 = r5.contentView
                    L42:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.contentView = distinctUntilChanged9;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$37 = promptSelectorInteractorImpl.prompt;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged9, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Biometric r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Biometric) r5
                        if (r5 == 0) goto L3a
                        java.lang.String r5 = r5.description
                        if (r5 != 0) goto L3c
                    L3a:
                        java.lang.String r5 = ""
                    L3c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new PromptViewModel$description$1(null));
        this.description = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        final Flow[] flowArr = {distinctUntilChanged6, readonlyStateFlow3, distinctUntilChanged5, distinctUntilChanged4, distinctUntilChanged, FlowKt.combine(distinctUntilChanged7, distinctUntilChanged8, distinctUntilChanged9, flowKt__ZipKt$combine$$inlined$unsafeFlow$12, new PromptViewModel$hasOnlyOneLineTitle$1(this, null))};
        this.guidelineBounds = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ PromptViewModel this$0;

                public AnonymousClass3(Continuation continuation, PromptViewModel promptViewModel) {
                    super(3, continuation);
                    this.this$0 = promptViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    int i;
                    int i2;
                    int i3;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i4 = this.label;
                    if (i4 == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean booleanValue = ((Boolean) objArr[5]).booleanValue();
                        BiometricModalities biometricModalities = (BiometricModalities) obj6;
                        PromptSize promptSize = (PromptSize) obj4;
                        PromptKind promptKind = (PromptKind) obj3;
                        int i5 = PromptViewModel.WhenMappings.$EnumSwitchMapping$0[((PromptPosition) obj5).ordinal()];
                        if (i5 != 1) {
                            if (i5 == 2) {
                                i2 = PromptViewModel.access$getHorizontalPadding(this.this$0, promptSize, biometricModalities, booleanValue);
                                i = 0;
                            } else if (i5 != 3) {
                                i2 = 0;
                                i = 0;
                            } else {
                                i3 = PromptViewModel.access$getHorizontalPadding(this.this$0, promptSize, biometricModalities, booleanValue);
                                i2 = 0;
                                i = 0;
                            }
                            i3 = i;
                        } else {
                            i = promptKind.isOnePaneNoSensorLandscapeBiometric() ? 0 : this.this$0.mediumTopGuidelinePadding;
                            i2 = 0;
                            i3 = 0;
                        }
                        Rect rect = new Rect(i2, i, i3, 0);
                        this.label = 1;
                        if (flowCollector.emit(rect, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i4 != 1) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        });
        this.isIndicatorMessageVisible = FlowKt.combine(distinctUntilChanged5, distinctUntilChanged4, asStateFlow2, new PromptViewModel$isIndicatorMessageVisible$1(null));
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(distinctUntilChanged5, distinctUntilChanged4, r11, new PromptViewModel$isConfirmButtonVisible$1(null));
        this.isConfirmButtonVisible = combine;
        this.isIconConfirmButton = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged, distinctUntilChanged5, new PromptViewModel$isIconConfirmButton$1(null));
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine2 = FlowKt.combine(distinctUntilChanged5, distinctUntilChanged4, asStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isNegativeButtonVisible$1(null));
        this.isNegativeButtonVisible = combine2;
        this.isCancelButtonVisible = FlowKt.combine(distinctUntilChanged5, distinctUntilChanged4, asStateFlow, combine2, combine, new PromptViewModel$isCancelButtonVisible$1(null));
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._canTryAgainNow = MutableStateFlow10;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine3 = FlowKt.combine(MutableStateFlow10, distinctUntilChanged5, distinctUntilChanged4, asStateFlow, flow, new PromptViewModel$canTryAgainNow$1(null));
        this.canTryAgainNow = combine3;
        this.isTryAgainButtonVisible = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(combine3, distinctUntilChanged, new PromptViewModel$isTryAgainButtonVisible$1(null));
        this.isCredentialButtonVisible = FlowKt.combine(distinctUntilChanged5, distinctUntilChanged4, asStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isCredentialButtonVisible$1(null));
        this.history = new PromptHistoryImpl();
    }

    public static final int access$getHorizontalPadding(PromptViewModel promptViewModel, PromptSize promptSize, BiometricModalities biometricModalities, boolean z) {
        int i;
        promptViewModel.getClass();
        if (PromptSizeKt.isSmall(promptSize)) {
            i = promptViewModel.smallHorizontalGuidelinePadding;
        } else if (!biometricModalities.getHasUdfps()) {
            i = promptViewModel.mediumHorizontalGuidelinePadding;
        } else {
            if (!z) {
                return promptViewModel.udfpsHorizontalGuidelinePadding;
            }
            i = promptViewModel.udfpsHorizontalShorterGuidelinePadding;
        }
        return -i;
    }

    public static void showAuthenticating$default(PromptViewModel promptViewModel, String str, boolean z, int i) {
        if ((i & 1) != 0) {
            str = "";
        }
        if ((i & 2) != 0) {
            z = false;
        }
        StateFlowImpl stateFlowImpl = promptViewModel._isAuthenticated;
        if (((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
            Log.w("PromptViewModel", "Cannot show authenticating after authenticated");
            return;
        }
        promptViewModel._isAuthenticating.updateState(null, Boolean.TRUE);
        stateFlowImpl.updateState(null, new PromptAuthState(false, null, false, 0L, 14, null));
        promptViewModel._message.setValue(StringsKt__StringsJVMKt.isBlank(str) ? PromptMessage.Empty.INSTANCE : new PromptMessage.Help(str));
        if (z) {
            promptViewModel._canTryAgainNow.updateState(null, Boolean.FALSE);
        }
        Job job = promptViewModel.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        promptViewModel.messageJob = null;
    }

    public static Object showTemporaryError$default(PromptViewModel promptViewModel, String str, String str2, boolean z, Function2 function2, BiometricModality biometricModality, Continuation continuation, int i) {
        Function2 function22 = (i & 8) != 0 ? new Function2() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$2
            @Override // kotlin.jvm.functions.Function2
            public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Boolean.FALSE;
            }
        } : function2;
        boolean z2 = (i & 16) != 0;
        BiometricModality biometricModality2 = (i & 32) != 0 ? BiometricModality.None : biometricModality;
        promptViewModel.getClass();
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new PromptViewModel$showTemporaryError$3(promptViewModel, z2, biometricModality2, function22, str, z, str2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public final void confirmAuthenticated() {
        StateFlowImpl stateFlowImpl = this._isAuthenticated;
        PromptAuthState promptAuthState = (PromptAuthState) stateFlowImpl.getValue();
        boolean z = promptAuthState.isAuthenticated;
        if (!z) {
            Log.w("PromptViewModel", "Cannot confirm authenticated when not authenticated");
            return;
        }
        PromptAuthState promptAuthState2 = new PromptAuthState(z, promptAuthState.authenticatedModality, false, promptAuthState.delay);
        promptAuthState2.wasConfirmed = true;
        stateFlowImpl.updateState(null, promptAuthState2);
        this._message.setValue(PromptMessage.Empty.INSTANCE);
        this._hapticsToPlay.updateState(null, new HapticsToPlay(16, 2));
        Job job = this.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        this.messageJob = null;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object needsExplicitConfirmation(com.android.systemui.biometrics.shared.model.BiometricModality r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            java.lang.Object r4 = r0.L$0
            r5 = r4
            com.android.systemui.biometrics.shared.model.BiometricModality r5 = (com.android.systemui.biometrics.shared.model.BiometricModality) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L44
        L2c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r4 = r4.isConfirmationRequired
            java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.first(r4, r0)
            if (r6 != r1) goto L44
            return r1
        L44:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            r6.booleanValue()
            com.android.systemui.biometrics.shared.model.BiometricModality r4 = com.android.systemui.biometrics.shared.model.BiometricModality.Face
            if (r5 != r4) goto L4e
            return r6
        L4e:
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel.needsExplicitConfirmation(com.android.systemui.biometrics.shared.model.BiometricModality, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onAnnounceAccessibilityHint(android.view.MotionEvent r12, boolean r13, kotlin.coroutines.Continuation r14) {
        /*
            r11 = this;
            boolean r0 = r14 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1
            if (r0 == 0) goto L13
            r0 = r14
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1
            r0.<init>(r11, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L33
            if (r2 != r3) goto L2b
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lc1
        L2b:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L33:
            boolean r13 = r0.Z$0
            java.lang.Object r11 = r0.L$1
            r12 = r11
            android.view.MotionEvent r12 = (android.view.MotionEvent) r12
            java.lang.Object r11 = r0.L$0
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r11 = (com.android.systemui.biometrics.ui.viewmodel.PromptViewModel) r11
            kotlin.ResultKt.throwOnFailure(r14)
        L41:
            r5 = r13
            goto L5c
        L43:
            kotlin.ResultKt.throwOnFailure(r14)
            com.android.systemui.FeatureFlagsImpl r14 = com.android.systemui.Flags.FEATURE_FLAGS
            r14.getClass()
            r0.L$0 = r11
            r0.L$1 = r12
            r0.Z$0 = r13
            r0.label = r4
            kotlinx.coroutines.flow.Flow r14 = r11.modalities
            java.lang.Object r14 = kotlinx.coroutines.flow.FlowKt.first(r14, r0)
            if (r14 != r1) goto L41
            return r1
        L5c:
            com.android.systemui.biometrics.shared.model.BiometricModalities r14 = (com.android.systemui.biometrics.shared.model.BiometricModalities) r14
            boolean r13 = r14.getHasUdfps()
            if (r13 == 0) goto Lc1
            if (r5 == 0) goto Lc1
            com.android.systemui.biometrics.UdfpsUtils r13 = r11.udfpsUtils
            r14 = 0
            int r2 = r12.getPointerId(r14)
            com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor r6 = r11.udfpsOverlayInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r6.udfpsOverlayParams
            kotlinx.coroutines.flow.StateFlow r7 = r7.$$delegate_0
            java.lang.Object r7 = r7.getValue()
            com.android.systemui.biometrics.shared.model.UdfpsOverlayParams r7 = (com.android.systemui.biometrics.shared.model.UdfpsOverlayParams) r7
            r13.getClass()
            android.graphics.Point r13 = com.android.systemui.biometrics.UdfpsUtils.getTouchInNativeCoordinates(r2, r12, r7, r4)
            int r14 = r12.getPointerId(r14)
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r6.udfpsOverlayParams
            kotlinx.coroutines.flow.StateFlow r6 = r2.$$delegate_0
            java.lang.Object r6 = r6.getValue()
            com.android.systemui.biometrics.shared.model.UdfpsOverlayParams r6 = (com.android.systemui.biometrics.shared.model.UdfpsOverlayParams) r6
            com.android.systemui.biometrics.UdfpsUtils r7 = r11.udfpsUtils
            r7.getClass()
            boolean r12 = com.android.systemui.biometrics.UdfpsUtils.isWithinSensorArea(r14, r12, r6, r4)
            if (r12 != 0) goto Lc1
            kotlinx.coroutines.flow.SharedFlowImpl r12 = r11._accessibilityHint
            android.content.Context r6 = r11.context
            int r11 = r13.x
            int r8 = r13.y
            kotlinx.coroutines.flow.StateFlow r13 = r2.$$delegate_0
            java.lang.Object r13 = r13.getValue()
            r9 = r13
            com.android.systemui.biometrics.shared.model.UdfpsOverlayParams r9 = (com.android.systemui.biometrics.shared.model.UdfpsOverlayParams) r9
            r7.getClass()
            r10 = 1
            r7 = r11
            java.lang.String r11 = com.android.systemui.biometrics.UdfpsUtils.onTouchOutsideOfSensorArea(r5, r6, r7, r8, r9, r10)
            r13 = 0
            r0.L$0 = r13
            r0.L$1 = r13
            r0.label = r3
            java.lang.Object r11 = r12.emit(r11, r0)
            if (r11 != r1) goto Lc1
            return r1
        Lc1:
            java.lang.Boolean r11 = java.lang.Boolean.FALSE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel.onAnnounceAccessibilityHint(android.view.MotionEvent, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void onSwitchToCredential() {
        this._forceLargeSize.updateState(null, Boolean.TRUE);
        PromptSelectorInteractorImpl promptSelectorInteractorImpl = (PromptSelectorInteractorImpl) this.promptSelectorInteractor;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptSelectorInteractorImpl.promptRepository;
        BiometricModalities biometricModalities = ((PromptKind) promptRepositoryImpl.promptKind.$$delegate_0.getValue()).isBiometric() ? ((PromptKind.Biometric) promptRepositoryImpl.promptKind.$$delegate_0.getValue()).activeModalities : new BiometricModalities(null, null, 3, null);
        Object value = promptRepositoryImpl.promptInfo.$$delegate_0.getValue();
        Intrinsics.checkNotNull(value);
        PromptInfo promptInfo = (PromptInfo) value;
        Object value2 = promptRepositoryImpl.userId.$$delegate_0.getValue();
        Intrinsics.checkNotNull(value2);
        int intValue = ((Number) value2).intValue();
        Object value3 = promptRepositoryImpl.requestId.$$delegate_0.getValue();
        Intrinsics.checkNotNull(value3);
        long longValue = ((Number) value3).longValue();
        Object value4 = promptRepositoryImpl.challenge.$$delegate_0.getValue();
        Intrinsics.checkNotNull(value4);
        long longValue2 = ((Number) value4).longValue();
        Object value5 = promptRepositoryImpl.opPackageName.$$delegate_0.getValue();
        Intrinsics.checkNotNull(value5);
        promptSelectorInteractorImpl.setPrompt(promptInfo, intValue, longValue, biometricModalities, longValue2, (String) value5, true, false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object showAuthenticated(com.android.systemui.biometrics.shared.model.BiometricModality r16, long r17, java.lang.String r19, kotlin.coroutines.Continuation r20) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel.showAuthenticated(com.android.systemui.biometrics.shared.model.BiometricModality, long, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Unit showHelp(String str) {
        StateFlowImpl stateFlowImpl = this._isAuthenticated;
        if (!((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
            this._isAuthenticating.updateState(null, Boolean.FALSE);
            stateFlowImpl.updateState(null, new PromptAuthState(false, null, false, 0L, 14, null));
        }
        this._message.setValue(StringsKt__StringsJVMKt.isBlank(str) ^ true ? new PromptMessage.Help(str) : PromptMessage.Empty.INSTANCE);
        this._forceMediumSize.updateState(null, Boolean.TRUE);
        Job job = this.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        this.messageJob = null;
        return Unit.INSTANCE;
    }
}
