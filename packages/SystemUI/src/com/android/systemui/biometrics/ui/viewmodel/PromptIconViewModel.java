package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class PromptIconViewModel {
    public final StateFlowImpl _previousIconOverlayWasError;
    public final StateFlowImpl _previousIconWasError;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 activeAuthType;
    public final ChannelFlowTransformLatest contentDescriptionId;
    public final DisplayStateInteractor displayStateInteractor;
    public final ChannelFlowTransformLatest iconAsset;
    public final ChannelFlowTransformLatest iconOverlayAsset;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 iconSize;
    public final ChannelFlowTransformLatest shouldAnimateIconOverlay;
    public final ChannelFlowTransformLatest shouldAnimateIconView;
    public final ChannelFlowTransformLatest shouldFlipIconView;
    public final Flow showingError;

    public final class AuthType {
        public static final /* synthetic */ AuthType[] $VALUES;
        public static final AuthType Coex;
        public static final AuthType Face;
        public static final AuthType Fingerprint;

        static {
            AuthType authType = new AuthType("Fingerprint", 0);
            Fingerprint = authType;
            AuthType authType2 = new AuthType("Face", 1);
            Face = authType2;
            AuthType authType3 = new AuthType("Coex", 2);
            Coex = authType3;
            AuthType[] authTypeArr = {authType, authType2, authType3};
            $VALUES = authTypeArr;
            EnumEntriesKt.enumEntries(authTypeArr);
        }

        private AuthType(String str, int i) {
        }

        public static AuthType valueOf(String str) {
            return (AuthType) Enum.valueOf(AuthType.class, str);
        }

        public static AuthType[] values() {
            return (AuthType[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[FingerprintSensorType.values().length];
            try {
                iArr[FingerprintSensorType.POWER_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[AuthType.values().length];
            try {
                iArr2[AuthType.Fingerprint.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[AuthType.Face.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[AuthType.Coex.ordinal()] = 3;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[DisplayRotation.values().length];
            try {
                iArr3[DisplayRotation.ROTATION_90.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_270.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_0.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_180.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public PromptIconViewModel(PromptViewModel promptViewModel, DisplayStateInteractor displayStateInteractor, PromptSelectorInteractor promptSelectorInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        this.displayStateInteractor = displayStateInteractor;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(promptViewModel.modalities), FlowKt.distinctUntilChanged(promptViewModel.faceMode), new PromptIconViewModel$activeAuthType$1(null));
        this.activeAuthType = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(udfpsOverlayInteractor.udfpsOverlayParams, ((DisplayStateInteractorImpl) displayStateInteractor).currentRotation, new PromptIconViewModel$udfpsSensorBounds$1(null)));
        this.showingError = promptViewModel.showingError;
        Boolean bool = Boolean.FALSE;
        this._previousIconWasError = StateFlowKt.MutableStateFlow(bool);
        this._previousIconOverlayWasError = StateFlowKt.MutableStateFlow(bool);
        FlowKt.combine(promptViewModel.position, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, promptViewModel.legacyFingerprintSensorWidth, promptViewModel.legacyFingerprintSensorHeight, new PromptIconViewModel$iconSize$1(promptViewModel, null));
        this.iconAsset = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$1(null, this, promptSelectorInteractor, promptViewModel));
        this.iconOverlayAsset = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$2(null, this, promptSelectorInteractor, promptViewModel));
        this.contentDescriptionId = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$3(null, promptSelectorInteractor, promptViewModel, this));
        this.shouldAnimateIconView = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$4(null, promptSelectorInteractor, promptViewModel, this));
        this.shouldAnimateIconOverlay = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$5(null, promptSelectorInteractor, promptViewModel, this));
        this.shouldFlipIconView = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$6(null, promptSelectorInteractor, this));
    }

    public static final int access$getSfpsIconViewAsset(PromptIconViewModel promptIconViewModel, DisplayRotation displayRotation, boolean z, boolean z2) {
        promptIconViewModel.getClass();
        int i = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
        return i != 1 ? i != 2 ? z2 ? R.raw.biometricprompt_rear_landscape_base : z ? R.raw.biometricprompt_folded_base_default : R.raw.biometricprompt_landscape_base : z2 ? R.raw.biometricprompt_rear_portrait_base : z ? R.raw.biometricprompt_folded_base_bottomright : R.raw.biometricprompt_portrait_base_bottomright : z2 ? R.raw.biometricprompt_rear_portrait_reverse_base : z ? R.raw.biometricprompt_folded_base_topleft : R.raw.biometricprompt_portrait_base_topleft;
    }
}
