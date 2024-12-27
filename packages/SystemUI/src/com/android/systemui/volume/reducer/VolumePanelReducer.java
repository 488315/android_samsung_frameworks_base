package com.android.systemui.volume.reducer;

import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelReducerBase;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.samsung.systemui.splugins.volume.VolumeState;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class VolumePanelReducer implements VolumePanelReducerBase {

    /* renamed from: com.android.systemui.volume.reducer.VolumePanelReducer$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType = iArr;
            try {
                iArr[VolumePanelAction.ActionType.ACTION_INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ANIMATION_START.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_TIME_OUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SCREEN_OFF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SWIPE_COLLAPSED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_REQUESTED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_MIRROR_LINK_ON.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_NONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SWIPE_PANEL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_TOUCH_PANEL.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ALL_SOUND_OFF_CHANGED.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_USER_SWITCHED.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SETUP_WIZARD_COMPLETE.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ACCESSIBILITY_MODE_CHANGED.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_CONFIGURATION_CHANGED.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_COVER_STATE_CHAGNED.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SMART_VIEW_SEEKBAR_TOUCHED.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_LIMITER_DIALOG.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_OPEN_THEME_CHANGED.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SETTINGS_BUTTON_CLICKED.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SEEKBAR_START_PROGRESS.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_DOWN.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_UP.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_FOLDER_STATE_CHANGED.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ARROW_RIGHT_CLICKED.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_ARROW_LEFT_CLICKED.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_CAPTION_COMPONENT_CHANGED.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_CAPTION_CHANGED.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DUAL_PLAY_MODE_CHANGED.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_KEY_EVENT.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType[VolumePanelAction.ActionType.ACTION_HEADSET_CONNECTION.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
        }
    }

    public static List<VolumePanelRow> applyActiveState(List<VolumePanelRow> list, int i) {
        return (List) list.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda5(i, 1)).collect(Collectors.toList());
    }

    public static List<VolumePanelRow> applyImportance(List<VolumePanelRow> list, final List<Integer> list2, final List<Integer> list3, final boolean z) {
        return (List) list.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List list4 = list2;
                List list5 = list3;
                boolean z2 = z;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                boolean anyMatch = list4.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda29(volumePanelRow, 1));
                boolean anyMatch2 = list5.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda29(volumePanelRow, 2));
                VolumePanelRow.Builder streamType = new VolumePanelRow.Builder(volumePanelRow).setStreamType(volumePanelRow.getStreamType());
                if (volumePanelRow.getStreamType() != 10) {
                    z2 = (volumePanelRow.isImportant() || anyMatch) & (!anyMatch2);
                }
                return streamType.isImportant(z2).isDynamic(volumePanelRow.isDynamic()).build();
            }
        }).collect(Collectors.toList());
    }

    public static List applyRowOrder(List list) {
        return (List) list.stream().sorted(new VolumePanelReducer$$ExternalSyntheticLambda26()).collect(Collectors.toList());
    }

    public static int calcTimeOut(VolumePanelState volumePanelState, int i, int i2) {
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && volumePanelState.isFolded()) {
            return Math.max(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? 3000 : 1000, i);
        }
        return volumePanelState.isShowingVolumeSafetyWarningDialog() ? Math.max(5000, Math.max(i, i2)) : volumePanelState.isExpanded() ? Math.max(5000, i) : Math.max(3000, i);
    }

    public static VolumePanelState checkIfNeedToSetProgress(final VolumePanelState volumePanelState, int i, int i2, final long j) {
        Optional findFirst = volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(i, 2)).map(new VolumePanelReducer$$ExternalSyntheticLambda4(3)).findFirst();
        Boolean bool = Boolean.FALSE;
        if (((Boolean) findFirst.orElse(bool)).booleanValue()) {
            return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
        }
        if (((Boolean) volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(i, 1)).map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(j - ((VolumePanelRow) obj).getUserAttemptTime() < 1000);
            }
        }).findFirst().orElse(bool)).booleanValue()) {
            return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR_LATER).stream(i).build();
        }
        final int ringerModeInternal = volumePanelState.getRingerModeInternal();
        return ((Boolean) ((List) volumePanelState.getVolumeRowList().stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i3 = ringerModeInternal;
                VolumePanelState volumePanelState2 = volumePanelState;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                VolumePanelRow.Builder builder = new VolumePanelRow.Builder(volumePanelRow);
                boolean isRemoteMic = volumePanelState2.isRemoteMic();
                int i4 = 0;
                if (!volumePanelRow.isMuted() && (volumePanelRow.getStreamType() != 2 || (!VolumePanelValues.isVibrate(i3) && !VolumePanelValues.isSilent(i3)))) {
                    i4 = (volumePanelRow.getStreamType() != 6 || isRemoteMic) ? volumePanelRow.getLevel() : volumePanelRow.getLevel() + 1;
                }
                return builder.realLevel(i4).build();
            }
        }).collect(Collectors.toList())).stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(i, 0)).map(new VolumePanelReducer$$ExternalSyntheticLambda5(i2, 3)).findFirst().orElse(bool)).booleanValue() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR).stream(i).build();
    }

    public static boolean checkStream(VolumeStreamState volumeStreamState, int i) {
        return volumeStreamState.getStreamType() == i;
    }

    public static boolean checkZenMuted(VolumeStreamState volumeStreamState, boolean z, boolean z2, boolean z3, boolean z4) {
        if (checkStream(volumeStreamState, 3) || volumeStreamState.getStreamType() == 21 || volumeStreamState.getStreamType() == 11 || volumeStreamState.getStreamType() == 22) {
            return z;
        }
        if (z4 && volumeStreamState.getStreamType() == 5) {
            return z3;
        }
        if (volumeStreamState.getStreamType() == 1) {
            return z2;
        }
        return false;
    }

    public static int determineEarProtectLevel(VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction, VolumePanelState volumePanelState) {
        boolean isSafeMediaDeviceOn = volumePanelAction.isSafeMediaDeviceOn();
        boolean isSafeMediaPinDeviceOn = volumePanelAction.isSafeMediaPinDeviceOn();
        boolean isMultiSoundBt = volumePanelAction.isMultiSoundBt();
        if (!(checkStream(volumeStreamState, 3) && isSafeMediaDeviceOn) && ((volumeStreamState.getStreamType() != 22 || (!isMultiSoundBt ? isSafeMediaDeviceOn : isSafeMediaPinDeviceOn)) && !((volumeStreamState.getStreamType() == 21 && isSafeMediaPinDeviceOn) || volumeStreamState.getStreamType() == 23))) {
            return -1;
        }
        return volumePanelState.getEarProtectLevel();
    }

    public static boolean determineEnabled(VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction, VolumePanelState volumePanelState, boolean z) {
        boolean z2;
        VolumeState volumeState = volumePanelAction.getVolumeState();
        boolean isVoiceCapable = volumePanelState.isVoiceCapable();
        boolean z3 = !(isStreamVibrate(5, volumeStreamState, volumeState) || isStreamSilent(5, volumeStreamState, volumeState) || isStreamVibrate(1, volumeStreamState, volumeState) || isStreamSilent(1, volumeStreamState, volumeState) || (volumeStreamState.getStreamType() >= 100 && volumeStreamState.isDisabledFixedSession())) || (isVoiceCapable ? checkStream(volumeStreamState, 2) : checkStream(volumeStreamState, 5));
        boolean z4 = volumeStreamState.getStreamType() == 6 && volumeState.isFixedScoVolume();
        boolean z5 = z && checkZenMuted(volumeStreamState, volumeState.isDisallowMedia(), volumeState.isDisallowSystem(), volumeState.isDisallowRinger(), isVoiceCapable);
        boolean z6 = volumeStreamState.getStreamType() == 3 || volumeStreamState.getStreamType() == 21;
        boolean isZenNone = volumePanelAction.isZenNone();
        boolean isAllSoundOff = volumePanelState.isAllSoundOff();
        boolean z7 = z6 && (isZenNone || isAllSoundOff);
        if (volumePanelState.isLeBroadcasting()) {
            int broadcastMode = volumeState.getBroadcastMode();
            if (checkStream(volumeStreamState, 5) || volumeStreamState.getStreamType() == 1 || ((volumeStreamState.getStreamType() == 3 || volumeStreamState.getStreamType() == 11) && broadcastMode == 2)) {
                z2 = true;
                boolean z8 = !isAllSoundOff || z7 || z4 || z5 || z2;
                return ((z8 && volumeStreamState.getStreamType() == 20 && volumePanelAction.isSupportTvVolumeSync()) || !z3 || z8) ? false : true;
            }
        }
        z2 = false;
        if (isAllSoundOff) {
        }
        if (z8) {
        }
    }

    public static boolean determineIconClickable(int i, boolean z) {
        if (i == 6 && z) {
            return true;
        }
        return (i == 0 || i == 6) ? false : true;
    }

    public static boolean determineIconEnabled(int i, boolean z) {
        return (i == 2 && z) || i == 20;
    }

    public static int determineIconState(VolumeStreamState volumeStreamState, VolumeState volumeState) {
        if (isStreamVibrate(2, volumeStreamState, volumeState) || isStreamVibrate(5, volumeStreamState, volumeState)) {
            return 0;
        }
        if (volumeStreamState.isRoutedToHeadset()) {
            return 9;
        }
        if (volumeStreamState.isRoutedToBuds() && volumeStreamState.isRoutedToBt()) {
            return 10;
        }
        if (volumeStreamState.isRoutedToBuds3() && volumeStreamState.isRoutedToBt()) {
            return 13;
        }
        if (volumeStreamState.isRoutedToHomeMini() && volumeStreamState.isRoutedToBt()) {
            return 12;
        }
        if (volumeStreamState.isRoutedToHearingAid() && volumeStreamState.isRoutedToBt()) {
            return 14;
        }
        if (volumeStreamState.isRoutedToBt()) {
            return 2;
        }
        if (volumeStreamState.isRoutedToRemoteSpeaker()) {
            return 11;
        }
        if (volumeStreamState.getStreamType() == 20) {
            return 5;
        }
        if (volumeStreamState.isDynamic()) {
            return (volumeStreamState.getLevel() == 0 || volumeStreamState.isMuted()) ? 7 : 6;
        }
        if (volumeStreamState.getStreamType() == 21 && volumeStreamState.isRoutedToAppMirroring()) {
            return 8;
        }
        return (isStreamSilent(2, volumeStreamState, volumeState) || isStreamSilent(5, volumeStreamState, volumeState) || (!(checkStream(volumeStreamState, 2) && volumeState.getRingerModeInternal() == 2) && ((volumeStreamState.getStreamType() != 0 && ((volumeStreamState.getStreamType() != 6 || volumeState.isRemoteMic()) && volumeStreamState.getLevel() == 0)) || volumeStreamState.isMuted()))) ? 1 : 3;
    }

    public static int determineRealVolumeLevel(VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction, boolean z) {
        VolumeState volumeState = volumePanelAction.getVolumeState();
        boolean z2 = (checkStream(volumeStreamState, 3) || volumeStreamState.getStreamType() == 21) && (volumePanelAction.isZenNone() || z);
        if (((!(checkStream(volumeStreamState, 2) && volumeState.getRingerModeInternal() == 2)) && volumeStreamState.isMuted()) || z2) {
            return 0;
        }
        return (volumeStreamState.getStreamType() != 6 || volumeState == null || volumeState.isRemoteMic()) ? volumeStreamState.getLevel() : volumeStreamState.getLevel() + 1;
    }

    public static String determineRemoteLabel(VolumePanelRow volumePanelRow, VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction) {
        VolumeState volumeState = volumePanelAction.getVolumeState();
        if (volumeState == null) {
            return "";
        }
        String activeBtDeviceName = volumePanelAction.getActiveBtDeviceName();
        return volumeStreamState.getStreamType() == 3 ? !volumeStreamState.isRoutedToBt() ? "" : volumeState.isDualAudio() ? volumeStreamState.getDualBtDeviceName() : activeBtDeviceName : (volumeStreamState.getStreamType() == 6 && volumeState.isRemoteMic()) ? activeBtDeviceName : volumeStreamState.isDynamic() ? volumeStreamState.getRemoteLabel() : volumePanelRow.getRemoteLabel();
    }

    public static int determineRowPriority(VolumePanelRow volumePanelRow, int i, boolean z, boolean z2) {
        int originalPriority = volumePanelRow.getOriginalPriority();
        if (i == 22) {
            i = z2 ? 21 : 3;
        }
        if (i == 23) {
            i = 3;
        }
        if (volumePanelRow.getStreamType() == i) {
            return 0;
        }
        if ((!volumePanelRow.isVisible() || z) && !((z && volumePanelRow.getPriority() == 1) || (volumePanelRow.getStreamType() == 23 && i == 3))) {
            return originalPriority;
        }
        return 1;
    }

    public static boolean determineVisibility(VolumePanelRow volumePanelRow, int i, boolean z, boolean z2, boolean z3) {
        boolean isImportant = volumePanelRow.isImportant();
        boolean z4 = i == volumePanelRow.getStreamType();
        if (i == 23) {
            if (volumePanelRow.getStreamType() == 3) {
                z4 = true;
            } else if (volumePanelRow.getStreamType() == 23) {
                z4 = false;
            }
        }
        return (z && (isImportant || volumePanelRow.isActiveShow())) || z4 || (!z && z2 && (i == (z3 ? 21 : 3) || i == 22) && (volumePanelRow.getStreamType() == (z3 ? 21 : 3) || volumePanelRow.getStreamType() == 22) && isImportant);
    }

    public static String getAppDevicePairName(String str, String str2) {
        return (str.isEmpty() || str2.isEmpty()) ? "" : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ") (", str2);
    }

    public static int getImpliedLevel(int i, int i2, int i3) {
        if (i == 3 || i == 21 || i == 22) {
            return (i3 / 10) * 10;
        }
        if (i == 20) {
            return i3;
        }
        int i4 = i2 * 100;
        if (i3 == 0) {
            return 0;
        }
        return i3 == i4 ? i2 : ((int) ((i3 / i4) * (i2 - 1))) + 1;
    }

    public static List getVolumePanelRows(final VolumePanelState volumePanelState, VolumePanelAction volumePanelAction, boolean z, final boolean z2) {
        List list = (List) applyImportance(volumePanelState.getVolumeRowList(), volumePanelAction.getImportantStreamList(), volumePanelAction.getUnImportantStreamList(), z).stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                VolumePanelState volumePanelState2 = VolumePanelState.this;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return new VolumePanelRow.Builder(volumePanelRow).isVisible(VolumePanelReducer.determineVisibility(volumePanelRow, volumePanelState2.getActiveStream(), z2, volumePanelState2.isDualAudio(), volumePanelState2.isMultiSoundBt())).build();
            }
        }).collect(Collectors.toList());
        final int activeStream = volumePanelState.getActiveStream();
        final boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
        return (List) list.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return new VolumePanelRow.Builder(volumePanelRow).priority(VolumePanelReducer.determineRowPriority(volumePanelRow, activeStream, z2, isMultiSoundBt)).build();
            }
        }).collect(Collectors.toList());
    }

    public static boolean isDisabledWarningDialog(int i, boolean z) {
        return (!z || i == 16 || i == 15 || i == 17 || i == 8) ? false : true;
    }

    public static boolean isStreamSilent(int i, VolumeStreamState volumeStreamState, VolumeState volumeState) {
        return checkStream(volumeStreamState, i) && volumeState.getRingerModeInternal() == 0;
    }

    public static boolean isStreamVibrate(int i, VolumeStreamState volumeStreamState, VolumeState volumeState) {
        return checkStream(volumeStreamState, i) && volumeState.getRingerModeInternal() == 1;
    }

    public static List prepareVolumePanelRow(boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new VolumePanelRow.Builder().setStreamType(0).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(6).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(10).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(3).isImportant(true).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(22).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(20).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(21).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(23).isImportant(false).originalPriority(arrayList.size() + 2).build());
        if (z) {
            arrayList.add(new VolumePanelRow.Builder().setStreamType(2).isImportant(true).originalPriority(arrayList.size() + 2).build());
        }
        arrayList.add(new VolumePanelRow.Builder().setStreamType(11).isImportant(false).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(5).isImportant(true).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(1).isImportant(true).originalPriority(arrayList.size() + 2).build());
        arrayList.add(new VolumePanelRow.Builder().setStreamType(4).isImportant(false).originalPriority(arrayList.size() + 2).build());
        return arrayList;
    }

    public static List<VolumePanelRow> resetActiveState(List<VolumePanelRow> list) {
        return (List) list.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda4(1)).collect(Collectors.toList());
    }

    public static boolean shouldSetStreamVolume(int i, int i2, VolumePanelState volumePanelState) {
        return ((Boolean) volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(i, 3)).map(new VolumePanelReducer$$ExternalSyntheticLambda5(i2, 4)).findFirst().orElse(Boolean.FALSE)).booleanValue();
    }

    public static List<VolumePanelRow> updateAccessibilityRowPriority(List<VolumePanelRow> list) {
        List applyRowOrder = applyRowOrder(list);
        List list2 = (List) applyRowOrder.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda3(2)).map(new VolumePanelReducer$$ExternalSyntheticLambda4(2)).collect(Collectors.toList());
        return (List) applyRowOrder.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda5(list2.size() >= 5 ? ((Integer) list2.get(4)).intValue() : ((Integer) PrioritySet$$ExternalSyntheticOutline0.m(1, list2)).intValue() + 1, 2)).collect(Collectors.toList());
    }

    public static int updateAudibleLevel(VolumePanelRow volumePanelRow, VolumeStreamState volumeStreamState) {
        int level = volumeStreamState.getLevel();
        int audibleLevel = volumePanelRow.getAudibleLevel();
        if (level > 0) {
            return level;
        }
        if (audibleLevel > 0) {
            return audibleLevel;
        }
        return 1;
    }

    public static List<VolumePanelRow> updateVolumeStates(List<VolumePanelRow> list, final VolumePanelAction volumePanelAction, final VolumePanelState volumePanelState, final int i) {
        final VolumeState volumeState = volumePanelAction.getVolumeState();
        if (volumeState == null) {
            return list;
        }
        final List<VolumeStreamState> streamStates = volumeState.getStreamStates();
        return (List) list.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List list2 = streamStates;
                VolumePanelAction volumePanelAction2 = volumePanelAction;
                VolumePanelState volumePanelState2 = volumePanelState;
                int i2 = i;
                VolumeState volumeState2 = volumeState;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                VolumeStreamState volumeStreamState = (VolumeStreamState) list2.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda29(volumePanelRow, 0)).findFirst().orElse(null);
                if (volumeStreamState == null) {
                    return volumePanelRow;
                }
                VolumePanelRow.Builder isSliderEnabled = new VolumePanelRow.Builder(volumePanelRow).nameRes(volumeStreamState.getNameRes()).level(volumeStreamState.getLevel()).remoteLabel(VolumePanelReducer.determineRemoteLabel(volumePanelRow, volumeStreamState, volumePanelAction2)).isRoutedToBluetooth(volumeStreamState.isRoutedToBt()).isMuted(volumeStreamState.isMuted()).realLevel(VolumePanelReducer.determineRealVolumeLevel(volumeStreamState, volumePanelAction2, volumePanelState2.isAllSoundOff())).isSliderEnabled(VolumePanelReducer.determineEnabled(volumeStreamState, volumePanelAction2, volumePanelState2, volumePanelAction2.isZenPriorityOnly()));
                int streamType = volumeStreamState.getStreamType();
                int min = volumeStreamState.getMin();
                VolumeState volumeState3 = volumePanelAction2.getVolumeState();
                if (volumeState3 != null && streamType == 6 && !volumeState3.isRemoteMic()) {
                    min = 1;
                }
                VolumePanelRow.Builder levelMin = isSliderEnabled.levelMin(min);
                int streamType2 = volumeStreamState.getStreamType();
                int integerValue = volumeStreamState.getIntegerValue(VolumeStreamState.IntegerStateKey.MAX);
                VolumeState volumeState4 = volumePanelAction2.getVolumeState();
                if (volumeState4 != null && streamType2 == 6 && !volumeState4.isRemoteMic()) {
                    integerValue++;
                }
                VolumePanelRow.Builder isIconEnabled = levelMin.levelMax(integerValue).isVisible(VolumePanelReducer.determineVisibility(volumePanelRow, i2, volumePanelState2.isExpanded(), volumeState2.isDualAudio(), volumePanelAction2.isMultiSoundBt())).iconType(VolumePanelReducer.determineIconState(volumeStreamState, volumeState2)).audibleLevel(VolumePanelReducer.updateAudibleLevel(volumePanelRow, volumeStreamState)).earProtectionLevel(VolumePanelReducer.determineEarProtectLevel(volumeStreamState, volumePanelAction2, volumePanelState2)).priority(VolumePanelReducer.determineRowPriority(volumePanelRow, i2, volumePanelState2.isExpanded(), volumePanelAction2.isMultiSoundBt())).isIconClickable(VolumePanelReducer.determineIconClickable(volumeStreamState.getStreamType(), volumeState2.isRemoteMic())).isIconEnabled(VolumePanelReducer.determineIconEnabled(volumeStreamState.getStreamType(), volumePanelState2.isAllSoundOff()));
                String smartViewDeviceName = volumePanelAction2.getSmartViewDeviceName();
                if (volumePanelRow.getStreamType() != 20) {
                    smartViewDeviceName = "";
                }
                return isIconEnabled.smartViewLabel(smartViewDeviceName).dualBtDeviceAddress(volumeStreamState.getDualBtDeviceAddress()).dualBtDeviceName(volumeStreamState.getDualBtDeviceName()).build();
            }
        }).collect(Collectors.toList());
    }

    public int getLastAudibleLevelOrMinLevel(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        int streamType = volumePanelRow.getStreamType();
        int level = volumePanelRow.getLevel();
        int levelMin = volumePanelRow.getLevelMin();
        int audibleLevel = volumePanelRow.getAudibleLevel();
        if (!volumePanelState.isVoiceCapable() ? streamType == 5 : streamType == 2) {
            if (streamType == 20) {
                return level;
            }
            if (level != levelMin) {
                return levelMin;
            }
        } else {
            if (volumePanelState.isAllSoundOff()) {
                return level;
            }
            if (volumePanelState.getRingerModeInternal() == 2) {
                if (volumePanelState.isHasVibrator()) {
                    return level;
                }
                if (level != 0) {
                    return 0;
                }
            } else if (level != 0) {
                return level;
            }
        }
        return audibleLevel;
    }

    public List<VolumePanelRow> mergeRemoteStream(List<VolumePanelRow> list, List<VolumeStreamState> list2) {
        List list3 = (List) list.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda1(list2, 0)).collect(Collectors.toList());
        List list4 = (List) list2.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda1(list3, 1)).collect(Collectors.toList());
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list3);
        arrayList.addAll((Collection) list4.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda5(((Integer) list.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda3(0)).map(new VolumePanelReducer$$ExternalSyntheticLambda4(0)).findFirst().orElse(7)).intValue(), 0)).collect(Collectors.toList()));
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:189:0x0683, code lost:
    
        if (com.samsung.systemui.splugins.volume.VolumePanelValues.isSilent(r1) != false) goto L139;
     */
    /* JADX WARN: Removed duplicated region for block: B:243:0x08ed  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x08fe  */
    @Override // com.samsung.systemui.splugins.volume.VolumePanelReducerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.systemui.splugins.volume.VolumePanelState reduce(com.samsung.systemui.splugins.volume.VolumePanelAction r18, final com.samsung.systemui.splugins.volume.VolumePanelState r19) {
        /*
            Method dump skipped, instructions count: 3074
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.reducer.VolumePanelReducer.reduce(com.samsung.systemui.splugins.volume.VolumePanelAction, com.samsung.systemui.splugins.volume.VolumePanelState):com.samsung.systemui.splugins.volume.VolumePanelState");
    }
}
