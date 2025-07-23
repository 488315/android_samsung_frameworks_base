package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import java.util.Arrays;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PromptIconViewModel {
    public final StateFlowImpl _previousIconWasError;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 activeAuthType;
    public final List assetsReusedAcrossRotations;
    public final ChannelFlowTransformLatest contentDescriptionId;
    public final DisplayStateInteractor displayStateInteractor;
    public final ChannelFlowTransformLatest iconAsset;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 iconViewRotation;
    public final ChannelFlowTransformLatest shouldAnimateIconView;
    public final ChannelFlowTransformLatest shouldLoopIconView;
    public final Flow showingError;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                iArr3[DisplayRotation.ROTATION_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_90.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_180.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr3[DisplayRotation.ROTATION_270.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public PromptIconViewModel(PromptViewModel promptViewModel, DisplayStateInteractor displayStateInteractor, PromptSelectorInteractor promptSelectorInteractor) {
        this.displayStateInteractor = displayStateInteractor;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(promptViewModel.modalities), FlowKt.distinctUntilChanged(promptViewModel.faceMode), new PromptIconViewModel$activeAuthType$1(null));
        this.activeAuthType = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.showingError = promptViewModel.showingError;
        this._previousIconWasError = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        FlowKt.combine(promptViewModel.position, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, promptViewModel.legacyFingerprintSensorWidth, promptViewModel.legacyFingerprintSensorHeight, new PromptIconViewModel$iconSize$1(promptViewModel, null));
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$1(null, this, promptSelectorInteractor, promptViewModel));
        this.iconAsset = transformLatest;
        this.contentDescriptionId = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$2(null, promptSelectorInteractor, promptViewModel, this));
        this.shouldAnimateIconView = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$3(null, promptSelectorInteractor, promptViewModel, this));
        this.shouldLoopIconView = FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new PromptIconViewModel$special$$inlined$flatMapLatest$4(null, promptViewModel));
        this.iconViewRotation = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(transformLatest, ((DisplayStateInteractorImpl) displayStateInteractor).currentRotation, new PromptIconViewModel$iconViewRotation$1(this, null));
        this.assetsReusedAcrossRotations = Arrays.asList(Integer.valueOf(R.raw.biometricprompt_sfps_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating), Integer.valueOf(R.raw.biometricprompt_sfps_rear_display_fingerprint_authenticating));
    }

    public static int getSfpsAsset_errorToFingerprint(DisplayRotation displayRotation, boolean z) {
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
            if (i == 1) {
                return R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint;
            }
            if (i == 2) {
                return R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_90;
            }
            if (i == 3) {
                return R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_180;
            }
            if (i == 4) {
                return R.raw.biometricprompt_sfps_rear_display_error_to_fingerprint_270;
            }
            throw new NoWhenBranchMatchedException();
        }
        int i2 = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
        if (i2 == 1) {
            return R.raw.biometricprompt_sfps_error_to_fingerprint;
        }
        if (i2 == 2) {
            return R.raw.biometricprompt_sfps_error_to_fingerprint_90;
        }
        if (i2 == 3) {
            return R.raw.biometricprompt_sfps_error_to_fingerprint_180;
        }
        if (i2 == 4) {
            return R.raw.biometricprompt_sfps_error_to_fingerprint_270;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static int getSfpsAsset_fingerprintToError(DisplayRotation displayRotation, boolean z) {
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
            if (i == 1) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error;
            }
            if (i == 2) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_90;
            }
            if (i == 3) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_180;
            }
            if (i == 4) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_error_270;
            }
            throw new NoWhenBranchMatchedException();
        }
        int i2 = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
        if (i2 == 1) {
            return R.raw.biometricprompt_sfps_fingerprint_to_error;
        }
        if (i2 == 2) {
            return R.raw.biometricprompt_sfps_fingerprint_to_error_90;
        }
        if (i2 == 3) {
            return R.raw.biometricprompt_sfps_fingerprint_to_error_180;
        }
        if (i2 == 4) {
            return R.raw.biometricprompt_sfps_fingerprint_to_error_270;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static int getSfpsAsset_fingerprintToSuccess(DisplayRotation displayRotation, boolean z) {
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
            if (i == 1) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success;
            }
            if (i == 2) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_90;
            }
            if (i == 3) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_180;
            }
            if (i == 4) {
                return R.raw.biometricprompt_sfps_rear_display_fingerprint_to_success_270;
            }
            throw new NoWhenBranchMatchedException();
        }
        int i2 = WhenMappings.$EnumSwitchMapping$2[displayRotation.ordinal()];
        if (i2 == 1) {
            return R.raw.biometricprompt_sfps_fingerprint_to_success;
        }
        if (i2 == 2) {
            return R.raw.biometricprompt_sfps_fingerprint_to_success_90;
        }
        if (i2 == 3) {
            return R.raw.biometricprompt_sfps_fingerprint_to_success_180;
        }
        if (i2 == 4) {
            return R.raw.biometricprompt_sfps_fingerprint_to_success_270;
        }
        throw new NoWhenBranchMatchedException();
    }
}
