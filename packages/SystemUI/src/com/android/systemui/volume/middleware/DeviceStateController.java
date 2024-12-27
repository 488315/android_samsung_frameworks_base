package com.android.systemui.volume.middleware;

import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumeState;
import kotlin.jvm.internal.Intrinsics;

public final class DeviceStateController implements VolumeMiddleware {
    public final VolumeInfraMediator infraMediator;
    public boolean isCoverClosed;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_COVER_STATE_CHAGNED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_CONFIGURATION_CHANGED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_USER_SWITCHED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_SETUP_WIZARD_COMPLETE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_CAPTION_COMPONENT_CHANGED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_CAPTION_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public DeviceStateController(VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator = (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase).get(VolumeInfraMediator.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        switch (i) {
            case 1:
                this.isCoverClosed = volumePanelAction.isCoverClosed();
                return volumePanelAction;
            case 2:
                if (volumeInfraMediator.isLcdOff() || this.isCoverClosed) {
                    if (volumeInfraMediator.isDexMode()) {
                        if (!volumeInfraMediator.isVolumeStarEnabled()) {
                            return new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_NONE).build();
                        }
                    } else if (!volumeInfraMediator.isAodVolumePanel()) {
                        return new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_IDLE).build();
                    }
                }
                VolumePanelAction.Builder builder = new VolumePanelAction.Builder(volumePanelAction);
                builder.cutoutHeight(volumeInfraMediator.getCutoutHeight()).isMediaDefault(volumeInfraMediator.isMediaDefault()).timeOutControls(volumeInfraMediator.getTimeoutControls()).timeOutControlsText(volumeInfraMediator.getTimeoutControlsText()).isLockscreen(volumeInfraMediator.isKeyguardState() || volumeInfraMediator.isShadeLockedState());
                volumeInfraMediator.getCaptionsComponentState(false);
                return builder.build();
            case 3:
                VolumeState volumeState = volumePanelAction.getVolumeState();
                Intrinsics.checkNotNull(volumeState);
                int zenMode = volumeState.getZenMode();
                boolean isZenModeEnabled = volumeInfraMediator.isZenModeEnabled(zenMode);
                return new VolumePanelAction.Builder(volumePanelAction).isZenEnabled(isZenModeEnabled).isZenPriorityOnly(volumeInfraMediator.isZenModePriorityOnly(zenMode)).isZenNone(volumeInfraMediator.isZenModeNone(zenMode)).systemTimeNow(volumeInfraMediator.getSystemTime()).build();
            case 4:
            case 5:
                return new VolumePanelAction.Builder(volumePanelAction).systemTimeNow(volumeInfraMediator.getSystemTime()).build();
            case 6:
            case 7:
                return volumeInfraMediator.isStandalone() ? new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_NONE).build() : volumePanelAction;
            case 8:
                return new VolumePanelAction.Builder(volumePanelAction).isOrientationChanged(volumeInfraMediator.isOrientationChanged()).isDensityOrFontChanged(volumeInfraMediator.isDensityOrFontChanged()).isDisplayTypeChanged(volumeInfraMediator.isDisplayTypeChanged()).build();
            case 9:
                return new VolumePanelAction.Builder(volumePanelAction).isAllSoundOff(volumeInfraMediator.isAllSoundOff()).build();
            case 10:
                return new VolumePanelAction.Builder(volumePanelAction).isSetupWizardComplete(volumeInfraMediator.isSetupWizardComplete()).build();
            case 11:
                return new VolumePanelAction.Builder(volumePanelAction).isCaptionEnabled(volumeInfraMediator.isCaptionEnabled()).build();
            default:
                return volumePanelAction;
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$1[volumePanelState.getStateType().ordinal()];
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        if (i == 1 || i == 2) {
            volumeInfraMediator.setSafeVolumeDialogShowing(volumePanelState.isShowingVolumeSafetyWarningDialog());
        } else {
            if (i != 3) {
                return;
            }
            volumeInfraMediator.setCaptionEnabled(volumePanelState.isCaptionEnabled());
        }
    }
}
