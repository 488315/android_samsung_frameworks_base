package com.android.systemui.volume.middleware;

import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SALogger implements VolumeMiddleware {
    public final VolumeInfraMediator infraMediator;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_EXPAND_STATE_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_MEDIA_VOLUME_DEFAULT_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public SALogger(VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator = (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase).get(VolumeInfraMediator.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        if (i == 1) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.SAFETY_OK);
        } else if (i == 2) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.SAFETY_CANCEL);
        } else if (i == 3) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.VOLUME_LIMITER_SETTING);
        } else if (i == 4) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.VOLUME_LIMITER_CANCEL);
        } else if (i == 5) {
            if (!BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                int stream = volumePanelAction.getStream();
                if (stream == 1) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_SYSTEM);
                } else if (stream == 2) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_RINGTONE);
                } else if (stream == 3) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_MEDIA);
                } else if (stream == 5) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_NOTIFICATION);
                } else if (stream == 10) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_ACCESSIBILITY);
                } else if (stream == 11) {
                    volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.FINE_CONTROL_BIXBY);
                }
            } else {
                volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.SUB_VOLUME_PANEL_FINE_CONTROL);
            }
        }
        return volumePanelAction;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$1[volumePanelState.getStateType().ordinal()];
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        if (i == 1) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.SUB_VOLUME_PANEL_SHOW);
            return;
        }
        if (i == 2) {
            if (volumePanelState.isExpanded()) {
                volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.EXPAND);
                return;
            } else {
                volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.SHRINK);
                return;
            }
        }
        if (i != 3) {
            return;
        }
        if (volumePanelState.isMediaDefaultEnabled()) {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.MEDIA_DEFAULT_ON);
        } else {
            volumeInfraMediator.sendEventLog(SALoggingWrapper.Event.MEDIA_DEFAULT_OFF);
        }
    }
}
