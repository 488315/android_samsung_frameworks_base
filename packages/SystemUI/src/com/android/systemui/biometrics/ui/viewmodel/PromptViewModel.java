package com.android.systemui.biometrics.ui.viewmodel;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptContentView;
import android.hardware.biometrics.PromptInfo;
import android.os.UserHandle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.AuthInteractionProperties;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractor;
import com.android.systemui.biometrics.domain.interactor.BiometricStatusInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.shared.model.PromptKind;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.domain.InteractionProperties;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.StandaloneCoroutine;
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

/* loaded from: classes.dex */
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
    public final ReadonlyStateFlow fingerprintStartMode;
    public final Flow guidelineBounds;
    public final ReadonlyStateFlow hapticsToPlay;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 hasFingerOnSensor;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 hasOnlyOneLineTitle;
    public final Flow hideSensorIcon;
    public final PromptHistoryImpl history;
    public final Flow iconPosition;
    public final IconProvider iconProvider;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 iconSize;
    public final PromptIconViewModel iconViewModel;
    public final ReadonlyStateFlow isAuthenticated;
    public final ReadonlyStateFlow isAuthenticating;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 isCancelButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isConfirmButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isConfirmationRequired;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 isCredentialButtonVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isIconConfirmButton;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isIndicatorMessageVisible;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 isNegativeButtonVisible;
    public final PromptViewModel$special$$inlined$map$4 isPendingConfirmation;
    public final PromptViewModel$special$$inlined$map$8 isRetrySupported;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isTryAgainButtonVisible;
    public final int landscapeMediumBottomPadding;
    public final int landscapeMediumHorizontalPadding;
    public final int landscapeSmallBottomPadding;
    public final int landscapeSmallHorizontalPadding;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 legacyFingerprintSensorHeight;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 legacyFingerprintSensorWidth;
    public final Flow logoInfo;
    public final int mediumHorizontalGuidelinePadding;
    public final int mediumTopGuidelinePadding;
    public final ReadonlyStateFlow message;
    public final long messageDelay;
    public StandaloneCoroutine messageJob;
    public final Flow modalities;
    public final PromptViewModel$special$$inlined$map$6 negativeButtonText;
    public final int portraitLargeScreenBottomPadding;
    public final int portraitMediumBottomPadding;
    public final int portraitSmallBottomPadding;
    public final Flow position;
    public final ReadonlyStateFlow promptKind;
    public final PromptSelectorInteractor promptSelectorInteractor;
    public final Flow showingError;
    public final Flow size;
    public final int smallHorizontalGuidelinePadding;
    public final Flow subtitle;
    public final Flow title;
    public final int udfpsHorizontalGuidelinePadding;
    public final int udfpsHorizontalShorterGuidelinePadding;
    public final ReadonlyStateFlow udfpsOverlayParams;
    public final PromptViewModel$special$$inlined$map$3 udfpsSensorHeight;
    public final PromptViewModel$special$$inlined$map$2 udfpsSensorWidth;
    public final UdfpsUtils udfpsUtils;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface HapticsToPlay {

        public final class HapticConstant implements HapticsToPlay {
            public final int constant;
            public final Integer flag;

            public HapticConstant(int i, Integer num) {
                this.constant = i;
                this.flag = num;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof HapticConstant)) {
                    return false;
                }
                HapticConstant hapticConstant = (HapticConstant) obj;
                return this.constant == hapticConstant.constant && Intrinsics.areEqual(this.flag, hapticConstant.flag);
            }

            public final int hashCode() {
                int iHashCode = Integer.hashCode(this.constant) * 31;
                Integer num = this.flag;
                return iHashCode + (num == null ? 0 : num.hashCode());
            }

            public final String toString() {
                return "HapticConstant(constant=" + this.constant + ", flag=" + this.flag + ")";
            }
        }

        public final class MSDL implements HapticsToPlay {
            public final InteractionProperties properties;
            public final MSDLToken token;

            public MSDL(MSDLToken mSDLToken, InteractionProperties interactionProperties) {
                this.token = mSDLToken;
                this.properties = interactionProperties;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MSDL)) {
                    return false;
                }
                MSDL msdl = (MSDL) obj;
                return this.token == msdl.token && Intrinsics.areEqual(this.properties, msdl.properties);
            }

            public final int hashCode() {
                int iHashCode = this.token.hashCode() * 31;
                InteractionProperties interactionProperties = this.properties;
                return iHashCode + (interactionProperties == null ? 0 : interactionProperties.hashCode());
            }

            public final String toString() {
                return "MSDL(token=" + this.token + ", properties=" + this.properties + ")";
            }
        }

        public final class None implements HapticsToPlay {
            public static final None INSTANCE = new None();

            private None() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof None);
            }

            public final int hashCode() {
                return -1012995335;
            }

            public final String toString() {
                return "None";
            }
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

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$needsExplicitConfirmation$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            PromptViewModel promptViewModel = PromptViewModel.this;
            int i = PromptViewModel.$r8$clinit;
            return promptViewModel.needsExplicitConfirmation(null, this);
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$onAnnounceAccessibilityHint$1, reason: invalid class name and case insensitive filesystem */
    final class C08111 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C08111(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PromptViewModel.this.onAnnounceAccessibilityHint(null, false, this);
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showAuthenticated$1, reason: invalid class name and case insensitive filesystem */
    final class C08121 extends ContinuationImpl {
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C08121(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PromptViewModel.this.showAuthenticated(null, 0L, null, this);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r13v1, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r6v35, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public PromptViewModel(DisplayStateInteractor displayStateInteractor, PromptSelectorInteractor promptSelectorInteractor, Context context, UdfpsOverlayInteractor udfpsOverlayInteractor, BiometricStatusInteractor biometricStatusInteractor, UdfpsUtils udfpsUtils, IconProvider iconProvider, ActivityTaskManager activityTaskManager, AccessibilityManager accessibilityManager) {
        this.promptSelectorInteractor = promptSelectorInteractor;
        this.context = context;
        this.udfpsUtils = udfpsUtils;
        this.iconProvider = iconProvider;
        this.activityTaskManager = activityTaskManager;
        this.messageDelay = accessibilityManager.getRecommendedTimeoutMillis(2000, 6);
        PromptSelectorInteractorImpl promptSelectorInteractorImpl = (PromptSelectorInteractorImpl) promptSelectorInteractor;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 = promptSelectorInteractorImpl.prompt;
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    BiometricModalities biometricModalities;
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
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        if (biometric == null || (biometricModalities = biometric.modalities) == null) {
                            biometricModalities = new BiometricModalities(null, null, 3, null);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(biometricModalities, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.modalities = flowDistinctUntilChanged;
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
        final ReadonlyStateFlow readonlyStateFlow = udfpsOverlayInteractor.udfpsOverlayParams;
        this.udfpsOverlayParams = readonlyStateFlow;
        DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) displayStateInteractor;
        Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, displayStateInteractorImpl.currentRotation, new PromptViewModel$udfpsSensorBounds$1(null)));
        ?? r9 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2

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
                        Integer num = new Integer(((UdfpsOverlayParams) obj).sensorBounds.width());
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.udfpsSensorWidth = r9;
        ?? r11 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3

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
                        Integer num = new Integer(((UdfpsOverlayParams) obj).sensorBounds.height());
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.udfpsSensorHeight = r11;
        this.legacyFingerprintSensorWidth = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged, r9, new PromptViewModel$legacyFingerprintSensorWidth$1(this, null));
        this.legacyFingerprintSensorHeight = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged, r11, new PromptViewModel$legacyFingerprintSensorHeight$1(this, null));
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._accessibilityHint = sharedFlowImplMutableSharedFlow$default;
        this.accessibilityHint = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticating = stateFlowImplMutableStateFlow;
        this.isAuthenticating = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(new PromptAuthState(false, null, false, 0L, 14, null));
        this._isAuthenticated = stateFlowImplMutableStateFlow2;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.isAuthenticated = readonlyStateFlowAsStateFlow;
        ?? r13 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        PromptAuthState promptAuthState = (PromptAuthState) obj;
                        Boolean boolValueOf = Boolean.valueOf(promptAuthState.isAuthenticated && promptAuthState.needsUserConfirmation);
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
                Object objCollect = readonlyStateFlowAsStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isPendingConfirmation = r13;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isOverlayTouched = stateFlowImplMutableStateFlow3;
        this.credentialKind = promptSelectorInteractorImpl.credentialKind;
        ReadonlyStateFlow readonlyStateFlow2 = promptSelectorInteractorImpl.promptKind;
        this.promptKind = readonlyStateFlow2;
        Flow flowDistinctUntilChanged3 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5

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
                        BiometricModalities biometricModalities = (BiometricModalities) obj;
                        Boolean boolValueOf = Boolean.valueOf(!biometricModalities.getHasFingerprint() && biometricModalities.faceProperties == null);
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.hideSensorIcon = flowDistinctUntilChanged3;
        this.negativeButtonText = new PromptViewModel$special$$inlined$map$6(promptSelectorInteractorImpl.prompt);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(PromptMessage.Empty.INSTANCE);
        this._message = stateFlowImplMutableStateFlow4;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow2 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.message = readonlyStateFlowAsStateFlow2;
        this.showingError = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        PromptMessage promptMessage = (PromptMessage) obj;
                        promptMessage.getClass();
                        Boolean boolValueOf = Boolean.valueOf(promptMessage instanceof PromptMessage.Error);
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
                Object objCollect = readonlyStateFlowAsStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        ?? r6 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        Boolean boolValueOf = Boolean.valueOf(((BiometricModalities) obj).faceProperties != null);
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isRetrySupported = r6;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(FingerprintStartMode.Pending);
        this._fingerprintStartMode = stateFlowImplMutableStateFlow5;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow3 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        this.fingerprintStartMode = readonlyStateFlowAsStateFlow3;
        this.hasFingerOnSensor = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((BiometricStatusInteractorImpl) biometricStatusInteractor).fingerprintAcquiredStatus, flowDistinctUntilChanged, new PromptViewModel$hasFingerBeenAcquired$1(null))), stateFlowImplMutableStateFlow3, new PromptViewModel$hasFingerOnSensor$1(null));
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._forceLargeSize = stateFlowImplMutableStateFlow6;
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._forceMediumSize = stateFlowImplMutableStateFlow7;
        new AuthInteractionProperties(null, 1, null);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(HapticsToPlay.None.INSTANCE);
        this._hapticsToPlay = stateFlowImplMutableStateFlow8;
        this.hapticsToPlay = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        Flow flowDistinctUntilChanged4 = FlowKt.distinctUntilChanged(FlowKt.combine(stateFlowImplMutableStateFlow6, readonlyStateFlow2, displayStateInteractorImpl.isLargeScreen, displayStateInteractorImpl.currentRotation, flowDistinctUntilChanged, new PromptViewModel$position$1(null)));
        this.position = flowDistinctUntilChanged4;
        Flow flowDistinctUntilChanged5 = FlowKt.distinctUntilChanged(FlowKt.combine(stateFlowImplMutableStateFlow6, stateFlowImplMutableStateFlow7, flowDistinctUntilChanged, promptSelectorInteractorImpl.isConfirmationRequired, readonlyStateFlowAsStateFlow3, new PromptViewModel$size$1(null)));
        this.size = flowDistinctUntilChanged5;
        this.smallHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_land_small_horizontal_guideline_padding);
        this.udfpsHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_udfps_horizontal_guideline_padding);
        this.udfpsHorizontalShorterGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_udfps_shorter_horizontal_guideline_padding);
        this.mediumTopGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_one_pane_medium_top_guideline_padding);
        this.mediumHorizontalGuidelinePadding = context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_medium_horizontal_guideline_padding);
        Flow flowDistinctUntilChanged6 = FlowKt.distinctUntilChanged(FlowKt.combine(flowDistinctUntilChanged2, flowDistinctUntilChanged5, flowDistinctUntilChanged4, flowDistinctUntilChanged, new PromptViewModel$iconPosition$1(this, null)));
        this.iconPosition = flowDistinctUntilChanged6;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow3, flowDistinctUntilChanged5, new PromptViewModel$isConfirmationRequired$1(null));
        this.isConfirmationRequired = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.faceMode = FlowKt.distinctUntilChanged(FlowKt.combine(flowDistinctUntilChanged, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, readonlyStateFlowAsStateFlow3, new PromptViewModel$faceMode$1(null)));
        PromptIconViewModel promptIconViewModel = new PromptIconViewModel(this, displayStateInteractor, promptSelectorInteractor);
        this.iconViewModel = promptIconViewModel;
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(bool);
        this._isIconViewLoaded = stateFlowImplMutableStateFlow9;
        FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged3, FlowKt.asStateFlow(stateFlowImplMutableStateFlow9), new PromptViewModel$isIconViewLoaded$1(null)));
        this.iconSize = FlowKt.combine(promptIconViewModel.activeAuthType, flowDistinctUntilChanged, r9, r11, new PromptViewModel$iconSize$1(this, null));
        new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged5, displayStateInteractorImpl.currentRotation, new PromptViewModel$promptPadding$1(this, null));
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$32 = promptSelectorInteractorImpl.prompt;
        this.logoInfo = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PromptViewModel this$0;

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

                public AnonymousClass2(FlowCollector flowCollector, PromptViewModel promptViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = promptViewModel;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:103:0x0184  */
                /* JADX WARN: Removed duplicated region for block: B:116:0x01aa  */
                /* JADX WARN: Removed duplicated region for block: B:126:0x01d3  */
                /* JADX WARN: Removed duplicated region for block: B:134:0x01f6  */
                /* JADX WARN: Removed duplicated region for block: B:137:0x0209  */
                /* JADX WARN: Removed duplicated region for block: B:62:0x00e2  */
                /* JADX WARN: Removed duplicated region for block: B:63:0x00e4  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
                /* JADX WARN: Removed duplicated region for block: B:86:0x0151  */
                /* JADX WARN: Removed duplicated region for block: B:95:0x0174  */
                /* JADX WARN: Removed duplicated region for block: B:98:0x0178  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    int i2;
                    int iEquals;
                    int i3;
                    Drawable applicationIcon;
                    String packageName;
                    ApplicationInfo applicationInfo;
                    UserHandle userHandleOf;
                    Pair pair;
                    String str;
                    ActivityInfo activityInfo;
                    Pair pair2;
                    int i4 = 1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i5 = anonymousClass1.label;
                        if ((i5 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i5 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i6 = anonymousClass1.label;
                    if (i6 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        if (biometric == null) {
                            pair2 = new Pair(null, "");
                        } else {
                            PromptViewModel promptViewModel = this.this$0;
                            Context context = promptViewModel.context;
                            ActivityTaskManager activityTaskManager = promptViewModel.activityTaskManager;
                            BitmapDrawable bitmapDrawable = biometric.logoBitmap != null ? new BitmapDrawable(context.getResources(), biometric.logoBitmap) : null;
                            String string = biometric.logoDescription;
                            if (string == null) {
                                string = "";
                            }
                            if (bitmapDrawable == null || string.length() <= 0) {
                                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(activityTaskManager.getTasks(1));
                                ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
                                ComponentName componentName2 = biometric.componentNameForConfirmDeviceCredentialActivity;
                                String str2 = biometric.opPackageName;
                                if (componentName2 != null) {
                                    i = 1;
                                } else {
                                    String packageName2 = componentName != null ? componentName.getPackageName() : null;
                                    if (packageName2 != null && str2 != null) {
                                        iEquals = packageName2.contentEquals(str2);
                                    } else if (packageName2 != null && str2 != null) {
                                        iEquals = packageName2.equals(str2);
                                    } else if (packageName2 != str2) {
                                        if (packageName2 == null || str2 == null || packageName2.length() != str2.length()) {
                                            i = 1;
                                        } else {
                                            int length = packageName2.length();
                                            int i7 = 0;
                                            while (i7 < length) {
                                                i = i4;
                                                if (packageName2.charAt(i7) == str2.charAt(i7)) {
                                                    i7++;
                                                    i4 = i;
                                                }
                                            }
                                            i = i4;
                                            i2 = i;
                                            if (i2 == 0) {
                                                componentName2 = componentName;
                                            } else {
                                                Log.w("PromptViewModel", "Top activity " + componentName + " is not the client " + str2);
                                                componentName2 = null;
                                            }
                                        }
                                        i2 = 0;
                                        if (i2 == 0) {
                                        }
                                    } else {
                                        i = i4;
                                        i2 = i;
                                        if (i2 == 0) {
                                        }
                                    }
                                    i = 1;
                                    i2 = iEquals;
                                    if (i2 == 0) {
                                    }
                                }
                                if (componentName2 != null) {
                                    String[] stringArray = context.getResources().getStringArray(R.array.config_useActivityLogoForBiometricPrompt);
                                    int length2 = stringArray.length;
                                    int i8 = 0;
                                    while (true) {
                                        if (i8 >= length2) {
                                            str = null;
                                            break;
                                        }
                                        str = stringArray[i8];
                                        String packageName3 = componentName2.getPackageName();
                                        str.getClass();
                                        if (packageName3.contentEquals(str)) {
                                            break;
                                        }
                                        i8++;
                                    }
                                    if ((str != null ? i : 0) != 0) {
                                        try {
                                            i3 = 0;
                                        } catch (PackageManager.NameNotFoundException e) {
                                            e = e;
                                            i3 = 0;
                                        }
                                        try {
                                            activityInfo = context.getPackageManager().getActivityInfo(componentName2, 0);
                                        } catch (PackageManager.NameNotFoundException e2) {
                                            e = e2;
                                            Log.w("PromptViewModel", "Cannot find activity info for " + context.getOpPackageName(), e);
                                            activityInfo = null;
                                            if (activityInfo == null) {
                                                applicationIcon = bitmapDrawable;
                                            }
                                            if (applicationIcon == null) {
                                            }
                                            return Unit.INSTANCE;
                                        }
                                        if (activityInfo == null) {
                                            applicationIcon = bitmapDrawable == null ? promptViewModel.iconProvider.getIcon(activityInfo) : bitmapDrawable;
                                            if ((string.length() == 0 ? i : i3) != 0) {
                                                string = activityInfo.loadLabel(context.getPackageManager()).toString();
                                            }
                                        }
                                        if (applicationIcon == null) {
                                            if (componentName2 != null) {
                                                packageName = componentName2.getPackageName();
                                            } else if (!biometric.allowBackgroundAuthentication) {
                                                int i9 = Utils.$r8$clinit;
                                                packageName = ((context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") != 0 || !"android".equals(str2)) ? i3 : i) != 0 ? str2 : null;
                                            }
                                            if (packageName == null) {
                                                MotionLayout$$ExternalSyntheticOutline0.m("Cannot find application info for ", str2, "PromptViewModel");
                                            } else {
                                                try {
                                                    applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 4194816);
                                                } catch (PackageManager.NameNotFoundException e3) {
                                                    Log.w("PromptViewModel", "Cannot find application info for " + str2, e3);
                                                }
                                                if (applicationInfo == null) {
                                                    if (applicationIcon == null) {
                                                        applicationIcon = context.getPackageManager().getApplicationIcon(applicationInfo);
                                                    }
                                                    if ((string.length() == 0 ? i : i3) != 0) {
                                                        string = context.getPackageManager().getApplicationLabel(applicationInfo).toString();
                                                    }
                                                } else {
                                                    MotionLayout$$ExternalSyntheticOutline0.m("Cannot find app logo for package ", context.getOpPackageName(), "PromptViewModel");
                                                }
                                                userHandleOf = UserHandle.of(biometric.userInfo.userId);
                                                if (applicationIcon != null && !applicationIcon.equals(bitmapDrawable)) {
                                                    applicationIcon = context.getPackageManager().getUserBadgedIcon(applicationIcon, userHandleOf);
                                                }
                                                pair = new Pair(applicationIcon, string);
                                            }
                                            applicationInfo = null;
                                            if (applicationInfo == null) {
                                            }
                                            userHandleOf = UserHandle.of(biometric.userInfo.userId);
                                            if (applicationIcon != null) {
                                                applicationIcon = context.getPackageManager().getUserBadgedIcon(applicationIcon, userHandleOf);
                                            }
                                            pair = new Pair(applicationIcon, string);
                                        } else {
                                            if ((string.length() == 0 ? i : i3) != 0) {
                                            }
                                            userHandleOf = UserHandle.of(biometric.userInfo.userId);
                                            if (applicationIcon != null) {
                                            }
                                            pair = new Pair(applicationIcon, string);
                                        }
                                    } else {
                                        i3 = 0;
                                    }
                                    applicationIcon = bitmapDrawable;
                                    if (applicationIcon == null) {
                                    }
                                }
                            } else {
                                pair = new Pair(bitmapDrawable, string);
                                i = 1;
                            }
                            pair2 = pair;
                            i4 = i;
                        }
                        anonymousClass1.label = i4;
                        if (this.$this_unsafeFlow.emit(pair2, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i6 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$32.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$33 = promptSelectorInteractorImpl.prompt;
        Flow flowDistinctUntilChanged7 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$10

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    String str;
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
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        if (biometric == null || (str = biometric.title) == null) {
                            str = "";
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$33.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.title = flowDistinctUntilChanged7;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$34 = promptSelectorInteractorImpl.prompt;
        Flow flowDistinctUntilChanged8 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$11

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    String str;
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
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        if (biometric == null || (str = biometric.subtitle) == null) {
                            str = "";
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$34.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.subtitle = flowDistinctUntilChanged8;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$35 = promptSelectorInteractorImpl.prompt;
        Flow flowDistinctUntilChanged9 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$12

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
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        PromptContentView promptContentView = biometric != null ? biometric.contentView : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(promptContentView, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$35.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.contentView = flowDistinctUntilChanged9;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$36 = promptSelectorInteractorImpl.prompt;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged9, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$13

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$13$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$13$2$1, reason: invalid class name */
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
                    String str;
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
                        BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                        if (biometric == null || (str = biometric.description) == null) {
                            str = "";
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$36.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new PromptViewModel$description$1(null));
        this.description = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine = FlowKt.combine(flowDistinctUntilChanged7, flowDistinctUntilChanged8, flowDistinctUntilChanged9, flowKt__ZipKt$combine$$inlined$unsafeFlow$12, new PromptViewModel$hasOnlyOneLineTitle$1(this, null));
        this.hasOnlyOneLineTitle = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine;
        final Flow[] flowArr = {flowDistinctUntilChanged6, readonlyStateFlow2, flowDistinctUntilChanged5, flowDistinctUntilChanged4, flowDistinctUntilChanged, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine};
        this.guidelineBounds = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1

            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ PromptViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    int iAccess$getHorizontalPadding;
                    int iAccess$getHorizontalPadding2;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = this.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                        BiometricModalities biometricModalities = (BiometricModalities) obj6;
                        PromptSize promptSize = (PromptSize) obj4;
                        PromptKind promptKind = (PromptKind) obj3;
                        int i3 = PromptViewModel.WhenMappings.$EnumSwitchMapping$0[((PromptPosition) obj5).ordinal()];
                        if (i3 != 1) {
                            if (i3 == 2) {
                                iAccess$getHorizontalPadding = PromptViewModel.access$getHorizontalPadding(this.this$0, promptSize, biometricModalities, zBooleanValue);
                                i = 0;
                            } else if (i3 == 3) {
                                iAccess$getHorizontalPadding2 = PromptViewModel.access$getHorizontalPadding(this.this$0, promptSize, biometricModalities, zBooleanValue);
                                iAccess$getHorizontalPadding = 0;
                                i = 0;
                            } else {
                                if (i3 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                iAccess$getHorizontalPadding = 0;
                                i = 0;
                            }
                            iAccess$getHorizontalPadding2 = i;
                        } else {
                            i = promptKind.isOnePaneNoSensorLandscapeBiometric() ? 0 : this.this$0.mediumTopGuidelinePadding;
                            iAccess$getHorizontalPadding = 0;
                            iAccess$getHorizontalPadding2 = 0;
                        }
                        Rect rect = new Rect(iAccess$getHorizontalPadding, i, iAccess$getHorizontalPadding2, 0);
                        this.label = 1;
                        if (flowCollector.emit(rect, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        });
        this.isIndicatorMessageVisible = FlowKt.combine(flowDistinctUntilChanged5, flowDistinctUntilChanged4, readonlyStateFlowAsStateFlow2, new PromptViewModel$isIndicatorMessageVisible$1(null));
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(flowDistinctUntilChanged5, flowDistinctUntilChanged4, r13, new PromptViewModel$isConfirmButtonVisible$1(null));
        this.isConfirmButtonVisible = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine;
        this.isIconConfirmButton = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowDistinctUntilChanged, flowDistinctUntilChanged5, new PromptViewModel$isIconConfirmButton$1(null));
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine2 = FlowKt.combine(flowDistinctUntilChanged5, flowDistinctUntilChanged4, readonlyStateFlowAsStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isNegativeButtonVisible$1(null));
        this.isNegativeButtonVisible = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine2;
        this.isCancelButtonVisible = FlowKt.combine(flowDistinctUntilChanged5, flowDistinctUntilChanged4, readonlyStateFlowAsStateFlow, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine2, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, new PromptViewModel$isCancelButtonVisible$1(null));
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(bool);
        this._canTryAgainNow = stateFlowImplMutableStateFlow10;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine = FlowKt.combine(stateFlowImplMutableStateFlow10, flowDistinctUntilChanged5, flowDistinctUntilChanged4, readonlyStateFlowAsStateFlow, r6, new PromptViewModel$canTryAgainNow$1(null));
        this.canTryAgainNow = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine;
        this.isTryAgainButtonVisible = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine, flowDistinctUntilChanged, new PromptViewModel$isTryAgainButtonVisible$1(null));
        this.isCredentialButtonVisible = FlowKt.combine(flowDistinctUntilChanged5, flowDistinctUntilChanged4, readonlyStateFlowAsStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isCredentialButtonVisible$1(null));
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

    public static void showAuthenticating$default(PromptViewModel promptViewModel, String str, int i) {
        if ((i & 1) != 0) {
            str = "";
        }
        boolean z = (i & 2) == 0;
        StateFlowImpl stateFlowImpl = promptViewModel._isAuthenticated;
        if (((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
            Log.w("PromptViewModel", "Cannot show authenticating after authenticated");
            return;
        }
        promptViewModel._isAuthenticating.updateState(null, Boolean.TRUE);
        stateFlowImpl.updateState(null, new PromptAuthState(false, null, false, 0L, 14, null));
        promptViewModel._message.setValue(StringsKt__StringsKt.isBlank(str) ? PromptMessage.Empty.INSTANCE : new PromptMessage.Help(str));
        if (z) {
            promptViewModel._canTryAgainNow.updateState(null, Boolean.FALSE);
        }
        StandaloneCoroutine standaloneCoroutine = promptViewModel.messageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        promptViewModel.messageJob = null;
    }

    public static Object showTemporaryError$default(PromptViewModel promptViewModel, String str, String str2, boolean z, Spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0 spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0, BiometricModality biometricModality, SuspendLambda suspendLambda, int i) {
        Function2 promptViewModel$$ExternalSyntheticLambda0 = spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0;
        if ((i & 8) != 0) {
            promptViewModel$$ExternalSyntheticLambda0 = new PromptViewModel$$ExternalSyntheticLambda0();
        }
        Function2 function2 = promptViewModel$$ExternalSyntheticLambda0;
        boolean z2 = (i & 16) != 0;
        if ((i & 32) != 0) {
            biometricModality = BiometricModality.None;
        }
        promptViewModel.getClass();
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new PromptViewModel$showTemporaryError$3(promptViewModel, z2, biometricModality, function2, str, z, str2, null), suspendLambda);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
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
        this._hapticsToPlay.updateState(null, new HapticsToPlay.HapticConstant(10004, null));
        StandaloneCoroutine standaloneCoroutine = this.messageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.messageJob = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object needsExplicitConfirmation(BiometricModality biometricModality, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objFirst = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            anonymousClass1.L$0 = biometricModality;
            anonymousClass1.label = 1;
            objFirst = FlowKt.first(this.isConfirmationRequired, anonymousClass1);
            if (objFirst == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            biometricModality = (BiometricModality) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objFirst);
        }
        Boolean bool = (Boolean) objFirst;
        bool.booleanValue();
        return biometricModality == BiometricModality.Face ? bool : Boolean.FALSE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00e0, code lost:
    
        if (r13.emit(r12, r0) == r1) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onAnnounceAccessibilityHint(MotionEvent motionEvent, boolean z, ContinuationImpl continuationImpl) {
        C08111 c08111;
        boolean z2;
        PromptViewModel promptViewModel;
        if (continuationImpl instanceof C08111) {
            c08111 = (C08111) continuationImpl;
            int i = c08111.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08111.label = i - Integer.MIN_VALUE;
            } else {
                c08111 = new C08111(continuationImpl);
            }
        }
        Object objFirst = c08111.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08111.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            c08111.L$0 = this;
            c08111.L$1 = motionEvent;
            c08111.Z$0 = z;
            c08111.label = 1;
            objFirst = FlowKt.first(this.modalities, c08111);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirst);
                return Boolean.FALSE;
            }
            boolean z3 = c08111.Z$0;
            motionEvent = (MotionEvent) c08111.L$1;
            promptViewModel = (PromptViewModel) c08111.L$0;
            ResultKt.throwOnFailure(objFirst);
            z2 = z3;
            if (!((PromptAuthState) objFirst).isAuthenticated) {
                UdfpsUtils udfpsUtils = promptViewModel.udfpsUtils;
                int pointerId = motionEvent.getPointerId(0);
                ReadonlyStateFlow readonlyStateFlow = promptViewModel.udfpsOverlayParams;
                UdfpsOverlayParams udfpsOverlayParams = (UdfpsOverlayParams) readonlyStateFlow.$$delegate_0.getValue();
                udfpsUtils.getClass();
                Point touchInNativeCoordinates = UdfpsUtils.getTouchInNativeCoordinates(pointerId, motionEvent, udfpsOverlayParams, true);
                int pointerId2 = motionEvent.getPointerId(0);
                UdfpsOverlayParams udfpsOverlayParams2 = (UdfpsOverlayParams) readonlyStateFlow.$$delegate_0.getValue();
                UdfpsUtils udfpsUtils2 = promptViewModel.udfpsUtils;
                udfpsUtils2.getClass();
                if (!UdfpsUtils.isWithinSensorArea(pointerId2, motionEvent, udfpsOverlayParams2, true)) {
                    SharedFlowImpl sharedFlowImpl = promptViewModel._accessibilityHint;
                    Context context = promptViewModel.context;
                    int i3 = touchInNativeCoordinates.x;
                    int i4 = touchInNativeCoordinates.y;
                    UdfpsOverlayParams udfpsOverlayParams3 = (UdfpsOverlayParams) readonlyStateFlow.$$delegate_0.getValue();
                    udfpsUtils2.getClass();
                    String strOnTouchOutsideOfSensorArea = UdfpsUtils.onTouchOutsideOfSensorArea(z2, context, i3, i4, udfpsOverlayParams3, true);
                    c08111.L$0 = null;
                    c08111.L$1 = null;
                    c08111.label = 3;
                }
            }
            return Boolean.FALSE;
        }
        z = c08111.Z$0;
        motionEvent = (MotionEvent) c08111.L$1;
        this = (PromptViewModel) c08111.L$0;
        ResultKt.throwOnFailure(objFirst);
        if (((BiometricModalities) objFirst).getHasUdfps() && z) {
            ReadonlyStateFlow readonlyStateFlow2 = this.isAuthenticated;
            c08111.L$0 = this;
            c08111.L$1 = motionEvent;
            c08111.Z$0 = z;
            c08111.label = 2;
            objFirst = FlowKt.first(readonlyStateFlow2, c08111);
            if (objFirst != coroutineSingletons) {
                z2 = z;
                promptViewModel = this;
                if (!((PromptAuthState) objFirst).isAuthenticated) {
                }
            }
            return coroutineSingletons;
        }
        return Boolean.FALSE;
    }

    public final void onSwitchToCredential() {
        this._forceLargeSize.updateState(null, Boolean.TRUE);
        PromptSelectorInteractorImpl promptSelectorInteractorImpl = (PromptSelectorInteractorImpl) this.promptSelectorInteractor;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptSelectorInteractorImpl.promptRepository;
        BiometricModalities biometricModalities = ((PromptKind) promptRepositoryImpl.promptKind.$$delegate_0.getValue()).isBiometric() ? ((PromptKind.Biometric) promptRepositoryImpl.promptKind.$$delegate_0.getValue()).activeModalities : new BiometricModalities(null, null, 3, null);
        Object value = promptRepositoryImpl.promptInfo.$$delegate_0.getValue();
        value.getClass();
        PromptInfo promptInfo = (PromptInfo) value;
        Object value2 = promptRepositoryImpl.userId.$$delegate_0.getValue();
        value2.getClass();
        int iIntValue = ((Number) value2).intValue();
        Object value3 = promptRepositoryImpl.requestId.$$delegate_0.getValue();
        value3.getClass();
        long jLongValue = ((Number) value3).longValue();
        Object value4 = promptRepositoryImpl.challenge.$$delegate_0.getValue();
        value4.getClass();
        long jLongValue2 = ((Number) value4).longValue();
        Object value5 = promptRepositoryImpl.opPackageName.$$delegate_0.getValue();
        value5.getClass();
        promptSelectorInteractorImpl.setPrompt(promptInfo, iIntValue, jLongValue, biometricModalities, jLongValue2, (String) value5, true, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00e1, code lost:
    
        if (r13.showHelp(r1) == r3) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object showAuthenticated(BiometricModality biometricModality, long j, String str, ContinuationImpl continuationImpl) {
        C08121 c08121;
        String str2;
        Object objNeedsExplicitConfirmation;
        long j2;
        BiometricModality biometricModality2;
        if (continuationImpl instanceof C08121) {
            c08121 = (C08121) continuationImpl;
            int i = c08121.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08121.label = i - Integer.MIN_VALUE;
            } else {
                c08121 = new C08121(continuationImpl);
            }
        }
        Object obj = c08121.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08121.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl = this._isAuthenticated;
            if (((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
                if (!((PromptAuthState) stateFlowImpl.getValue()).needsUserConfirmation || biometricModality == ((PromptAuthState) stateFlowImpl.getValue()).authenticatedModality) {
                    Log.w("PromptViewModel", "Cannot show authenticated after authenticated");
                    return Unit.INSTANCE;
                }
                confirmAuthenticated();
                return Unit.INSTANCE;
            }
            this._isAuthenticating.updateState(null, Boolean.FALSE);
            c08121.L$0 = this;
            c08121.L$1 = biometricModality;
            str2 = str;
            c08121.L$2 = str2;
            c08121.J$0 = j;
            c08121.label = 1;
            objNeedsExplicitConfirmation = needsExplicitConfirmation(biometricModality, c08121);
            if (objNeedsExplicitConfirmation != coroutineSingletons) {
                j2 = j;
                biometricModality2 = biometricModality;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        long j3 = c08121.J$0;
        String str3 = (String) c08121.L$2;
        BiometricModality biometricModality3 = (BiometricModality) c08121.L$1;
        PromptViewModel promptViewModel = (PromptViewModel) c08121.L$0;
        ResultKt.throwOnFailure(obj);
        str2 = str3;
        this = promptViewModel;
        objNeedsExplicitConfirmation = obj;
        biometricModality2 = biometricModality3;
        j2 = j3;
        boolean zBooleanValue = ((Boolean) objNeedsExplicitConfirmation).booleanValue();
        this._isAuthenticated.updateState(null, new PromptAuthState(true, biometricModality2, zBooleanValue, j2));
        this._message.setValue(PromptMessage.Empty.INSTANCE);
        if (!zBooleanValue) {
            this._hapticsToPlay.updateState(null, new HapticsToPlay.HapticConstant(10004, null));
        }
        StandaloneCoroutine standaloneCoroutine = this.messageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.messageJob = null;
        if (StringsKt__StringsKt.isBlank(str2) || !zBooleanValue) {
            return Unit.INSTANCE;
        }
        c08121.L$0 = null;
        c08121.L$1 = null;
        c08121.L$2 = null;
        c08121.label = 2;
    }

    public final Unit showHelp(String str) {
        StateFlowImpl stateFlowImpl = this._isAuthenticated;
        if (!((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
            this._isAuthenticating.updateState(null, Boolean.FALSE);
            stateFlowImpl.updateState(null, new PromptAuthState(false, null, false, 0L, 14, null));
        }
        this._message.setValue(!StringsKt__StringsKt.isBlank(str) ? new PromptMessage.Help(str) : PromptMessage.Empty.INSTANCE);
        this._forceMediumSize.updateState(null, Boolean.TRUE);
        StandaloneCoroutine standaloneCoroutine = this.messageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.messageJob = null;
        return Unit.INSTANCE;
    }
}
