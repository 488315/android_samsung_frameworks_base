package com.android.systemui.volume.reducer;

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
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumePanelReducer implements VolumePanelReducerBase {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.reducer.VolumePanelReducer$1 */
    public abstract /* synthetic */ class AbstractC36191 {

        /* renamed from: $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelAction$ActionType */
        public static final /* synthetic */ int[] f397x2eb5821b;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            f397x2eb5821b = iArr;
            try {
                iArr[VolumePanelAction.ActionType.ACTION_INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ANIMATION_START.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_TIME_OUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SCREEN_OFF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SWIPE_COLLAPSED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_REQUESTED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_MIRROR_LINK_ON.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_NONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SWIPE_PANEL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_STOP_SLIDER_TRACKING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_TOUCH_PANEL.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ALL_SOUND_OFF_CHANGED.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_USER_SWITCHED.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SETUP_WIZARD_COMPLETE.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ACCESSIBILITY_MODE_CHANGED.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_CONFIGURATION_CHANGED.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_COVER_STATE_CHAGNED.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SMART_VIEW_SEEKBAR_TOUCHED.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_LIMITER_DIALOG.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_OPEN_THEME_CHANGED.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SETTINGS_BUTTON_CLICKED.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SEEKBAR_START_PROGRESS.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_DOWN.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SEEKBAR_TOUCH_UP.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_FOLDER_STATE_CHANGED.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ARROW_RIGHT_CLICKED.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_ARROW_LEFT_CLICKED.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_CAPTION_COMPONENT_CHANGED.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_CAPTION_CHANGED.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DUAL_PLAY_MODE_CHANGED.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_KEY_EVENT.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_VOLUME_CSD_100_WARNING_DIALOG_TIMEOUT.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                f397x2eb5821b[VolumePanelAction.ActionType.ACTION_HEADSET_CONNECTION.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
        }
    }

    public static List<VolumePanelRow> applyActiveState(List<VolumePanelRow> list, int i) {
        return (List) list.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda3(i, 4)).collect(Collectors.toList());
    }

    public static List<VolumePanelRow> applyImportance(List<VolumePanelRow> list, final List<Integer> list2, final List<Integer> list3, final boolean z) {
        return (List) list.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                List list4 = list2;
                List list5 = list3;
                boolean z2 = z;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                boolean anyMatch = list4.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda13(volumePanelRow, 1));
                boolean anyMatch2 = list5.stream().anyMatch(new VolumePanelReducer$$ExternalSyntheticLambda13(volumePanelRow, 2));
                VolumePanelRow.Builder streamType = new VolumePanelRow.Builder(volumePanelRow).setStreamType(volumePanelRow.getStreamType());
                if (volumePanelRow.getStreamType() != 10) {
                    z2 = (volumePanelRow.isImportant() || anyMatch) & (!anyMatch2);
                }
                return streamType.isImportant(z2).isDynamic(volumePanelRow.isDynamic()).build();
            }
        }).collect(Collectors.toList());
    }

    public static List applyRowOrder(List list) {
        return (List) list.stream().sorted(new VolumePanelReducer$$ExternalSyntheticLambda16()).collect(Collectors.toList());
    }

    public static int calcTimeOut(VolumePanelState volumePanelState, int i, int i2) {
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && volumePanelState.isFolded()) {
            return Math.max(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? 3000 : 1000, i);
        }
        return volumePanelState.isShowingVolumeSafetyWarningDialog() ? Math.max(5000, Math.max(i, i2)) : volumePanelState.isExpanded() ? Math.max(5000, i) : Math.max(3000, i);
    }

    public static VolumePanelState checkIfNeedToSetProgress(final VolumePanelState volumePanelState, int i, int i2, final long j) {
        Optional findFirst = volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda2(i, 1)).map(new VolumePanelReducer$$ExternalSyntheticLambda5(1)).findFirst();
        Boolean bool = Boolean.FALSE;
        if (((Boolean) findFirst.orElse(bool)).booleanValue()) {
            return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
        }
        if (((Boolean) volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda2(i, 3)).map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(j - ((VolumePanelRow) obj).getUserAttemptTime() < 1000);
            }
        }).findFirst().orElse(bool)).booleanValue()) {
            return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR_LATER).stream(i).build();
        }
        final int ringerModeInternal = volumePanelState.getRingerModeInternal();
        return ((Boolean) ((List) volumePanelState.getVolumeRowList().stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i3 = ringerModeInternal;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return new VolumePanelRow.Builder(volumePanelRow).realLevel((volumePanelRow.isMuted() || (volumePanelRow.getStreamType() == 2 && (VolumePanelValues.isVibrate(i3) || VolumePanelValues.isSilent(i3)))) ? 0 : (volumePanelRow.getStreamType() != 6 || volumePanelState.isRemoteMic()) ? volumePanelRow.getLevel() : volumePanelRow.getLevel() + 1).build();
            }
        }).collect(Collectors.toList())).stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda2(i, 0)).map(new VolumePanelReducer$$ExternalSyntheticLambda3(i2, 0)).findFirst().orElse(bool)).booleanValue() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR).stream(i).build();
    }

    public static boolean checkZenMuted(VolumeStreamState volumeStreamState, boolean z, boolean z2, boolean z3, boolean z4) {
        if (volumeStreamState.getStreamType() == 3) {
            return z;
        }
        if (volumeStreamState.getStreamType() == 21) {
            return z;
        }
        if (volumeStreamState.getStreamType() == 11) {
            return z;
        }
        if (volumeStreamState.getStreamType() == 22) {
            return z;
        }
        if (z4) {
            if (volumeStreamState.getStreamType() == 5) {
                return z3;
            }
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
        if (!(volumeStreamState.getStreamType() == 3) || !isSafeMediaDeviceOn) {
            if (!(volumeStreamState.getStreamType() == 22) || (!isMultiSoundBt ? !isSafeMediaDeviceOn : !isSafeMediaPinDeviceOn)) {
                if (!(volumeStreamState.getStreamType() == 21) || !isSafeMediaPinDeviceOn) {
                    if (!(volumeStreamState.getStreamType() == 23)) {
                        return -1;
                    }
                }
            }
        }
        return volumePanelState.getEarProtectLevel();
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e5, code lost:
    
        if ((r11.getStreamType() == 11) != false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00eb, code lost:
    
        r13 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00e7, code lost:
    
        if (r13 == 2) goto L90;
     */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0117 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean determineEnabled(VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction, VolumePanelState volumePanelState, boolean z) {
        boolean z2;
        boolean isAllSoundOff;
        boolean z3;
        boolean z4;
        boolean z5;
        VolumeState volumeState = volumePanelAction.getVolumeState();
        boolean isVoiceCapable = volumePanelState.isVoiceCapable();
        boolean z6 = !(isStreamVibrate(5, volumeStreamState, volumeState) || isStreamSilent(5, volumeStreamState, volumeState) || isStreamVibrate(1, volumeStreamState, volumeState) || isStreamSilent(1, volumeStreamState, volumeState) || ((volumeStreamState.getStreamType() >= 100) && volumeStreamState.isDisabledFixedSession())) || (!isVoiceCapable ? volumeStreamState.getStreamType() == 5 : volumeStreamState.getStreamType() == 2);
        boolean z7 = (volumeStreamState.getStreamType() == 6) && volumeState.isFixedScoVolume();
        boolean z8 = z && checkZenMuted(volumeStreamState, volumeState.isDisallowMedia(), volumeState.isDisallowSystem(), volumeState.isDisallowRinger(), isVoiceCapable);
        if (!(volumeStreamState.getStreamType() == 3)) {
            if (!(volumeStreamState.getStreamType() == 21)) {
                z2 = false;
                boolean isZenNone = volumePanelAction.isZenNone();
                isAllSoundOff = volumePanelState.isAllSoundOff();
                z3 = !z2 && (isZenNone || isAllSoundOff);
                if (volumePanelState.isLeBroadcasting()) {
                    int broadcastMode = volumeState.getBroadcastMode();
                    if (!(volumeStreamState.getStreamType() == 5)) {
                        if (!(volumeStreamState.getStreamType() == 1)) {
                            if (!(volumeStreamState.getStreamType() == 3)) {
                            }
                        }
                    }
                    boolean z9 = true;
                    if (z9) {
                        z4 = true;
                        z5 = !isAllSoundOff || z3 || z7 || z8 || z4;
                        if (!z5) {
                            if ((volumeStreamState.getStreamType() == 20) && volumePanelAction.isSupportTvVolumeSync()) {
                                return false;
                            }
                        }
                        return (z6 || z5) ? false : true;
                    }
                }
                z4 = false;
                if (isAllSoundOff) {
                }
                if (!z5) {
                }
                if (z6) {
                    return false;
                }
            }
        }
        z2 = true;
        boolean isZenNone2 = volumePanelAction.isZenNone();
        isAllSoundOff = volumePanelState.isAllSoundOff();
        if (z2) {
        }
        if (volumePanelState.isLeBroadcasting()) {
        }
        z4 = false;
        if (isAllSoundOff) {
        }
        if (!z5) {
        }
        if (z6) {
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

    /* JADX WARN: Code restructure failed: missing block: B:81:0x00f0, code lost:
    
        if (r8.isRemoteMic() == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00f6, code lost:
    
        if (r7.getLevel() != 0) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00fc, code lost:
    
        if (r7.isMuted() != false) goto L94;
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int determineIconState(VolumeStreamState volumeStreamState, VolumeState volumeState) {
        boolean z;
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
            return volumeStreamState.getLevel() == 0 || volumeStreamState.isMuted() ? 7 : 6;
        }
        if ((volumeStreamState.getStreamType() == 21) && volumeStreamState.isRoutedToAppMirroring()) {
            return 8;
        }
        if (!isStreamSilent(2, volumeStreamState, volumeState) && !isStreamSilent(5, volumeStreamState, volumeState)) {
            if (volumeStreamState.getStreamType() == 2) {
                if (volumeState.getRingerModeInternal() == 2) {
                    z = true;
                    if (!z) {
                        if (!(volumeStreamState.getStreamType() == 0)) {
                            if (volumeStreamState.getStreamType() == 6) {
                            }
                        }
                    }
                    return 3;
                }
            }
            z = false;
            if (!z) {
            }
            return 3;
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0060 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int determineRealVolumeLevel(VolumeStreamState volumeStreamState, VolumePanelAction volumePanelAction, boolean z) {
        boolean z2;
        boolean z3;
        VolumeState volumeState = volumePanelAction.getVolumeState();
        if (!(volumeStreamState.getStreamType() == 3)) {
            if (!(volumeStreamState.getStreamType() == 21)) {
                z2 = false;
                boolean z4 = !z2 && (volumePanelAction.isZenNone() || z);
                if (volumeStreamState.getStreamType() != 2) {
                    if (volumeState.getRingerModeInternal() == 2) {
                        z3 = true;
                        if (((!z3) || !volumeStreamState.isMuted()) && !z4) {
                            return ((volumeStreamState.getStreamType() == 6) || volumeState == null || volumeState.isRemoteMic()) ? volumeStreamState.getLevel() : volumeStreamState.getLevel() + 1;
                        }
                        return 0;
                    }
                }
                z3 = false;
                if (!z3) {
                }
                if (volumeStreamState.getStreamType() == 6) {
                }
            }
        }
        z2 = true;
        if (z2) {
        }
        if (volumeStreamState.getStreamType() != 2) {
        }
        z3 = false;
        if (!z3) {
        }
        if (volumeStreamState.getStreamType() == 6) {
        }
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
        return (str.isEmpty() || str2.isEmpty()) ? "" : AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, ") (", str2);
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
        List list = (List) applyImportance(volumePanelState.getVolumeRowList(), volumePanelAction.getImportantStreamList(), volumePanelAction.getUnImportantStreamList(), z).stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                VolumePanelState volumePanelState2 = VolumePanelState.this;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                return new VolumePanelRow.Builder(volumePanelRow).isVisible(VolumePanelReducer.determineVisibility(volumePanelRow, volumePanelState2.getActiveStream(), z2, volumePanelState2.isDualAudio(), volumePanelState2.isMultiSoundBt())).build();
            }
        }).collect(Collectors.toList());
        final int activeStream = volumePanelState.getActiveStream();
        final boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
        return (List) list.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda15
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
        if (volumeStreamState.getStreamType() == i) {
            if (volumeState.getRingerModeInternal() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStreamVibrate(int i, VolumeStreamState volumeStreamState, VolumeState volumeState) {
        if (volumeStreamState.getStreamType() == i) {
            return volumeState.getRingerModeInternal() == 1;
        }
        return false;
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
        return (List) list.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda5(0)).collect(Collectors.toList());
    }

    public static boolean shouldSetStreamVolume(int i, int i2, VolumePanelState volumePanelState) {
        return ((Boolean) volumePanelState.getVolumeRowList().stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda2(i, 2)).map(new VolumePanelReducer$$ExternalSyntheticLambda3(i2, 1)).findFirst().orElse(Boolean.FALSE)).booleanValue();
    }

    public static List<VolumePanelRow> updateAccessibilityRowPriority(List<VolumePanelRow> list) {
        List applyRowOrder = applyRowOrder(list);
        List list2 = (List) applyRowOrder.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(1)).map(new VolumePanelReducer$$ExternalSyntheticLambda5(2)).collect(Collectors.toList());
        return (List) applyRowOrder.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda3(list2.size() >= 5 ? ((Integer) list2.get(4)).intValue() : ((Integer) list2.get(list2.size() - 1)).intValue() + 1, 2)).collect(Collectors.toList());
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
                VolumeStreamState volumeStreamState = (VolumeStreamState) list2.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda13(volumePanelRow, 3)).findFirst().orElse(null);
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
        List list3 = (List) list.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda8(list2, 0)).collect(Collectors.toList());
        List list4 = (List) list2.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda8(list3, 1)).collect(Collectors.toList());
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list3);
        arrayList.addAll((Collection) list4.stream().map(new VolumePanelReducer$$ExternalSyntheticLambda3(((Integer) list.stream().filter(new VolumePanelReducer$$ExternalSyntheticLambda6(2)).map(new VolumePanelReducer$$ExternalSyntheticLambda5(3)).findFirst().orElse(7)).intValue(), 3)).collect(Collectors.toList()));
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:187:0x066e, code lost:
    
        if (com.samsung.systemui.splugins.volume.VolumePanelValues.isSilent(r1) != false) goto L142;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:241:0x08d7  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x08e8  */
    @Override // com.samsung.systemui.splugins.volume.VolumePanelReducerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final VolumePanelState reduce(VolumePanelAction volumePanelAction, final VolumePanelState volumePanelState) {
        List<VolumePanelRow> list;
        List<VolumePanelRow> list2;
        VolumePanelState.Builder builder = new VolumePanelState.Builder(volumePanelState);
        VolumePanelState.StateType stateType = VolumePanelState.StateType.STATE_IDLE;
        VolumePanelState build = builder.setStateType(stateType).build();
        final boolean z = true;
        int i = 0;
        Object[] objArr = 0;
        switch (AbstractC36191.f397x2eb5821b[volumePanelAction.getActionType().ordinal()]) {
            case 1:
                boolean isVoiceCapable = volumePanelAction.isVoiceCapable();
                return new VolumePanelState.Builder(volumePanelState).setVolumeRowList(prepareVolumePanelRow(isVoiceCapable)).isVoiceCapable(isVoiceCapable).isHasVibrator(volumePanelAction.isHasVibrator()).isAllSoundOff(volumePanelAction.isAllSoundOff()).isSetupWizardComplete(volumePanelAction.isSetupWizardComplete()).build();
            case 2:
                return new VolumePanelState.Builder(volumePanelState).setStateType(stateType).build();
            case 3:
                if (volumePanelState.isAnimating()) {
                    return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
                }
                if (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel()) {
                    return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_RESCHEDULE_TIME_OUT).timeOut(calcTimeOut(volumePanelState, volumePanelAction.getTimeOutControls(), volumePanelAction.getTimeOutControlsText())).build();
                }
                int timeOutControls = volumePanelAction.getTimeOutControls();
                int timeOutControlsText = volumePanelAction.getTimeOutControlsText();
                List volumePanelRows = getVolumePanelRows(volumePanelState, volumePanelAction, volumePanelState.isShowA11yStream(), volumePanelState.isExpanded());
                final String pinAppName = volumePanelAction.getPinAppName();
                final String pinDeviceName = volumePanelAction.getPinDeviceName();
                final String btCallDeviceName = volumePanelAction.getBtCallDeviceName();
                final String audioSharingDeviceName = volumePanelAction.getAudioSharingDeviceName();
                final boolean isRemoteMic = volumePanelState.isRemoteMic();
                final boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
                final boolean isDualAudio = volumePanelState.isDualAudio();
                List list3 = (List) volumePanelRows.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda11
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z2 = isMultiSoundBt;
                        boolean z3 = isDualAudio;
                        String str = pinAppName;
                        String str2 = pinDeviceName;
                        boolean z4 = isRemoteMic;
                        String str3 = btCallDeviceName;
                        String str4 = audioSharingDeviceName;
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        if (volumePanelRow.getStreamType() == 21) {
                            boolean z5 = z2 && z3;
                            VolumePanelRow.Builder builder2 = new VolumePanelRow.Builder(volumePanelRow);
                            if (z5) {
                                str2 = volumePanelRow.getDualBtDeviceName();
                            }
                            return builder2.remoteLabel(VolumePanelReducer.getAppDevicePairName(str, str2)).build();
                        }
                        if ((volumePanelRow.getStreamType() == 6 && !z4) || volumePanelRow.getStreamType() == 0) {
                            return new VolumePanelRow.Builder(volumePanelRow).remoteLabel(str3).build();
                        }
                        if (volumePanelRow.getStreamType() == 22) {
                            return new VolumePanelRow.Builder(volumePanelRow).remoteLabel(z2 ? VolumePanelReducer.getAppDevicePairName(str, volumePanelRow.getDualBtDeviceName()) : volumePanelRow.getDualBtDeviceName()).build();
                        }
                        return volumePanelRow.getStreamType() == 23 ? new VolumePanelRow.Builder(volumePanelRow).remoteLabel(str4).build() : volumePanelRow;
                    }
                }).collect(Collectors.toList());
                final boolean isSafeMediaDeviceOn = volumePanelAction.isSafeMediaDeviceOn();
                final boolean isSafeMediaPinDeviceOn = volumePanelAction.isSafeMediaPinDeviceOn();
                List<VolumePanelRow> applyActiveState = applyActiveState((List) list3.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda17
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z2 = isSafeMediaDeviceOn;
                        boolean z3 = isSafeMediaPinDeviceOn;
                        VolumePanelState volumePanelState2 = volumePanelState;
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        return ((volumePanelRow.getStreamType() == 3 && z2) || (volumePanelRow.getStreamType() == 21 && z3) || volumePanelRow.getStreamType() == 23 || (volumePanelRow.getStreamType() == 22 && (!volumePanelState2.isMultiSoundBt() ? !z2 : !z3))) ? new VolumePanelRow.Builder(volumePanelRow).earProtectionLevel(volumePanelState2.getEarProtectLevel()).build() : new VolumePanelRow.Builder(volumePanelRow).earProtectionLevel(-1).build();
                    }
                }).collect(Collectors.toList()), volumePanelState.getActiveStream());
                boolean isFolded = volumePanelState.isFolded();
                return new VolumePanelState.Builder(volumePanelState).setStateType(isFolded ? VolumePanelState.StateType.STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL : VolumePanelState.StateType.STATE_SHOW).isShowing(true).timeOutControls(timeOutControls).timeOutControlsText(timeOutControlsText).timeOut(calcTimeOut(volumePanelState, timeOutControls, timeOutControlsText)).isMediaDefaultEnabled(volumePanelAction.isMediaDefault()).isLockscreen(volumePanelAction.isLockscreen()).cutoutHeight(volumePanelAction.getCutoutHeight()).setVolumeRowList(applyActiveState).pinDevice(volumePanelAction.getPinDevice()).isSafeMediaDeviceOn(isSafeMediaDeviceOn).isSafeMediaPinDeviceOn(isSafeMediaPinDeviceOn).isBtScoOn(volumePanelAction.isBtScoOn()).isShowing(!isFolded).isShowingSubDisplayVolumePanel(isFolded).build();
            case 4:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isAnimating(true).build();
            case 5:
                return (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL).isWithAnimation(true).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 6:
                return (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel() || volumePanelState.isShowingVolumeLimiterDialog() || volumePanelState.isShowingVolumeSafetyWarningDialog() || volumePanelState.isShowingVolumeCsd100WarningDialog()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS).isWithAnimation(false).isAnimating(false).isPendingState(false).isShowing(false).isShowingSubDisplayVolumePanel(false).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isAnimating(false).isPendingState(false).isShowing(false).isShowingSubDisplayVolumePanel(false).build();
            case 7:
            case 8:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL).isWithAnimation(true).build();
            case 9:
            case 10:
                return (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel() || volumePanelState.isShowingVolumeLimiterDialog()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS).isWithAnimation(true).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 11:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 12:
                int activeStream = volumePanelAction.getActiveStream();
                if (activeStream == -1) {
                    activeStream = volumePanelState.getActiveStream();
                }
                VolumeState volumeState = volumePanelAction.getVolumeState();
                if (volumeState == null) {
                    return build;
                }
                boolean isRemoteMic2 = volumeState.isRemoteMic();
                boolean isShowA11yStream = volumePanelState.isShowA11yStream();
                boolean isZenEnabled = volumePanelAction.isZenEnabled();
                boolean isVoiceCapable2 = volumePanelAction.isVoiceCapable();
                long systemTimeNow = volumePanelAction.getSystemTimeNow();
                boolean isAodVolumePanel = volumeState.isAodVolumePanel();
                boolean isLeBroadcasting = volumeState.isLeBroadcasting();
                List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
                if (isVoiceCapable2) {
                    list = volumeRowList;
                    if (volumeRowList.stream().noneMatch(new VolumePanelReducer$$ExternalSyntheticLambda6(0))) {
                        list2 = prepareVolumePanelRow(true);
                        VolumePanelState.Builder earProtectLevel = new VolumePanelState.Builder(volumePanelState).activeStream(activeStream).setVolumeRowList(applyRowOrder(updateVolumeStates(applyImportance(mergeRemoteStream(list2, volumeState.getStreamStates()), volumePanelAction.getImportantStreamList(), volumePanelAction.getUnImportantStreamList(), isShowA11yStream), volumePanelAction, volumePanelState, activeStream))).isRemoteMic(isRemoteMic2).isZenMode(isZenEnabled).isLeBroadcasting(isLeBroadcasting).ringerModeInternal(volumeState.getRingerModeInternal()).systemTimeNow(systemTimeNow).isVoiceCapable(isVoiceCapable2).isAodVolumePanel(isAodVolumePanel).isSafeMediaDeviceOn(volumePanelAction.isSafeMediaDeviceOn()).isSafeMediaPinDeviceOn(volumePanelAction.isSafeMediaPinDeviceOn()).earProtectLevel(volumePanelAction.getEarProtectLevel());
                        earProtectLevel.isDualAudio(volumeState.isDualAudio()).isMultiSoundBt(volumePanelAction.isMultiSoundBt());
                        return !volumePanelState.isAnimating() ? earProtectLevel.setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isPendingState(true).build() : earProtectLevel.setStateType(VolumePanelState.StateType.STATE_UPDATE).build();
                    }
                } else {
                    list = volumeRowList;
                }
                list2 = list;
                VolumePanelState.Builder earProtectLevel2 = new VolumePanelState.Builder(volumePanelState).activeStream(activeStream).setVolumeRowList(applyRowOrder(updateVolumeStates(applyImportance(mergeRemoteStream(list2, volumeState.getStreamStates()), volumePanelAction.getImportantStreamList(), volumePanelAction.getUnImportantStreamList(), isShowA11yStream), volumePanelAction, volumePanelState, activeStream))).isRemoteMic(isRemoteMic2).isZenMode(isZenEnabled).isLeBroadcasting(isLeBroadcasting).ringerModeInternal(volumeState.getRingerModeInternal()).systemTimeNow(systemTimeNow).isVoiceCapable(isVoiceCapable2).isAodVolumePanel(isAodVolumePanel).isSafeMediaDeviceOn(volumePanelAction.isSafeMediaDeviceOn()).isSafeMediaPinDeviceOn(volumePanelAction.isSafeMediaPinDeviceOn()).earProtectLevel(volumePanelAction.getEarProtectLevel());
                earProtectLevel2.isDualAudio(volumeState.isDualAudio()).isMultiSoundBt(volumePanelAction.isMultiSoundBt());
                if (!volumePanelState.isAnimating()) {
                }
            case 13:
                return volumePanelState.isAnimating() ? volumePanelState.isPendingState() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_UPDATE).isPendingState(false).isAnimating(false).isConfigurationChanged(false).isOpenThemeChanged(false).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isAnimating(false).isConfigurationChanged(false).isOpenThemeChanged(false).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 14:
            case 15:
                if (volumePanelState.isAnimating()) {
                    return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
                }
                boolean z2 = !volumePanelState.isExpanded();
                boolean isShowA11yStream2 = volumePanelState.isShowA11yStream();
                List<VolumePanelRow> volumePanelRows2 = getVolumePanelRows(volumePanelState, volumePanelAction, isShowA11yStream2, z2);
                if (z2 && isShowA11yStream2 && volumePanelState.getActiveStream() != 10) {
                    volumePanelRows2 = updateAccessibilityRowPriority(volumePanelRows2);
                }
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_EXPAND_STATE_CHANGED).isExpanded(z2).setVolumeRowList(applyRowOrder(volumePanelRows2)).timeOut(calcTimeOut(volumePanelState, volumePanelState.getTimeOutControls(), volumePanelState.getTimeOutControlsText())).build();
            case 16:
                VolumePanelState.Builder stateType2 = new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_START_SLIDER_TRACKING);
                List<VolumePanelRow> volumeRowList2 = volumePanelState.getVolumeRowList();
                final int stream = volumePanelAction.getStream();
                return stateType2.setVolumeRowList((List) volumeRowList2.stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        return volumePanelRow.getStreamType() == stream ? new VolumePanelRow.Builder(volumePanelRow).isTracking(z).build() : new VolumePanelRow.Builder(volumePanelRow).build();
                    }
                }).collect(Collectors.toList())).isTracking(true).build();
            case 17:
                VolumePanelState.Builder stream2 = new VolumePanelState.Builder(volumePanelState).isTracking(false).setStateType(VolumePanelState.StateType.STATE_STOP_SLIDER_TRACKING).stream(volumePanelAction.getStream());
                List<VolumePanelRow> volumeRowList3 = volumePanelState.getVolumeRowList();
                final int stream3 = volumePanelAction.getStream();
                Stream<VolumePanelRow> stream4 = volumeRowList3.stream();
                final Object[] objArr2 = objArr == true ? 1 : 0;
                return stream2.setVolumeRowList((List) stream4.map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        return volumePanelRow.getStreamType() == stream3 ? new VolumePanelRow.Builder(volumePanelRow).isTracking(objArr2).build() : new VolumePanelRow.Builder(volumePanelRow).build();
                    }
                }).collect(Collectors.toList())).build();
            case 18:
                final int stream5 = volumePanelAction.getStream();
                final int progress = volumePanelAction.getProgress();
                final long systemTimeNow2 = volumePanelAction.getSystemTimeNow();
                return shouldSetStreamVolume(stream5, progress, volumePanelState) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SET_STREAM_VOLUME).stream(stream5).setVolumeRowList((List) volumePanelState.getVolumeRowList().stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda12
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        return volumePanelRow.getStreamType() == stream5 ? new VolumePanelRow.Builder(volumePanelRow).realLevel(VolumePanelReducer.getImpliedLevel(volumePanelRow.getStreamType(), volumePanelRow.getLevelMax(), progress)).userAttemptTime(systemTimeNow2).build() : new VolumePanelRow.Builder(volumePanelRow).build();
                    }
                }).collect(Collectors.toList())).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 19:
                final int stream6 = volumePanelAction.getStream();
                if (stream6 == 20) {
                    return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SMART_VIEW_ICON_CLICKED).build();
                }
                VolumePanelState.Builder volumeRowList4 = new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_ICON_CLICKED).stream(stream6).setVolumeRowList((List) volumePanelState.getVolumeRowList().stream().map(new Function() { // from class: com.android.systemui.volume.reducer.VolumePanelReducer$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        VolumePanelReducer volumePanelReducer = VolumePanelReducer.this;
                        int i2 = stream6;
                        VolumePanelState volumePanelState2 = volumePanelState;
                        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                        volumePanelReducer.getClass();
                        return volumePanelRow.getStreamType() == i2 ? new VolumePanelRow.Builder(volumePanelRow).level(volumePanelReducer.getLastAudibleLevelOrMinLevel(volumePanelState2, volumePanelRow)).userAttemptTime(0L).build() : new VolumePanelRow.Builder(volumePanelRow).build();
                    }
                }).collect(Collectors.toList()));
                if (!volumePanelState.isVoiceCapable() ? stream6 != 5 : stream6 != 2) {
                    z = false;
                }
                int ringerModeInternal = volumePanelState.getRingerModeInternal();
                boolean isHasVibrator = volumePanelState.isHasVibrator();
                if (!z) {
                    i = ringerModeInternal;
                } else if (volumePanelState.isAllSoundOff()) {
                    break;
                } else {
                    if (!VolumePanelValues.isNormal(ringerModeInternal)) {
                        i = 2;
                    }
                    i = isHasVibrator;
                }
                return volumeRowList4.ringerModeInternal(i).build();
            case 20:
                return (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel()) ? checkIfNeedToSetProgress(volumePanelState, volumePanelAction.getStream(), volumePanelAction.getProgress(), volumePanelAction.getSystemTimeNow()) : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 21:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_TOUCH_PANEL).timeOut(calcTimeOut(volumePanelState, volumePanelState.getTimeOutControls(), volumePanelState.getTimeOutControlsText())).build();
            case 22:
            case 23:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isAllSoundOff(volumePanelAction.isAllSoundOff()).build();
            case 24:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isSetupWizardComplete(volumePanelAction.isSetupWizardComplete()).build();
            case 25:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isShowA11yStream(volumePanelAction.isShowA11yStream()).build();
            case 26:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_RESCHEDULE_TIME_OUT).timeOut(calcTimeOut(volumePanelState, volumePanelState.getTimeOutControls(), volumePanelState.getTimeOutControlsText())).build();
            case 27:
                int activeStream2 = volumePanelAction.getActiveStream();
                if (!volumePanelState.isShowing() && !volumePanelState.isShowingSubDisplayVolumePanel()) {
                    z = false;
                }
                boolean isFromKey = volumePanelAction.isFromKey();
                if (BasicRune.VOLUME_HOME_IOT) {
                    return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_PLAY_SOUND_ON).activeStream(activeStream2).volumeDirection(volumePanelAction.getVolumeDirection()).build();
                }
                return (activeStream2 == 20 || (!z && isFromKey) || volumePanelState.isZenMode()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_PLAY_SOUND_ON).activeStream(activeStream2).build();
            case 28:
                return volumePanelAction.isDensityOrFontChanged() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_CONFIGURATION_CHANGED).isConfigurationChanged(true).build() : ((volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel()) && volumePanelAction.isOrientationChanged()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_ORIENTATION_CHANGED).isWithAnimation(false).build() : volumePanelAction.isDisplayTypeChanged() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS).isWithAnimation(false).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 29:
                return (volumePanelState.isShowing() || volumePanelState.isShowingSubDisplayVolumePanel() || volumePanelState.isShowingVolumeLimiterDialog() || volumePanelState.isShowingVolumeSafetyWarningDialog() || volumePanelState.isShowingVolumeCsd100WarningDialog()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_COVER_STATE_CHANGED).isWithAnimation(false).isCoverClosed(volumePanelAction.isCoverClosed()).coverType(volumePanelAction.getCoverType()).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isCoverClosed(volumePanelAction.isCoverClosed()).coverType(volumePanelAction.getCoverType()).build();
            case 30:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SMART_VIEW_SEEKBAR_TOUCHED).stream(volumePanelAction.getStream()).build();
            case 31:
                return volumePanelState.isShowingVolumeLimiterDialog() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : isDisabledWarningDialog(volumePanelState.getCoverType(), volumePanelState.isCoverClosed()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SHOW_VOLUME_LIMITER_DIALOG).isShowingVolumeLimiterDialog(true).build();
            case 32:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED).isWithAnimation(true).build();
            case 33:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED).build();
            case 34:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN).build();
            case 35:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).isShowingVolumeLimiterDialog(false).build();
            case 36:
            case 37:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED).setVolumeRowList(resetActiveState(volumePanelState.getVolumeRowList())).isShowing(false).isShowingSubDisplayVolumePanel(false).isExpanded(false).isAnimating(false).isWithAnimation(false).isConfigurationChanged(false).build();
            case 38:
                return (volumePanelAction.getFlags() & 134217728) != 0 ? volumePanelState.isShowingVolumeSafetyWarningDialog() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : isDisabledWarningDialog(volumePanelState.getCoverType(), volumePanelState.isCoverClosed()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : !volumePanelState.isShowingVolumeSafetyWarningDialog() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG).isShowingVolumeSafetyWarningDialog(true).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 39:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS_VOLUME_SAFETY_WARNING_DIALOG).isShowingVolumeSafetyWarningDialog(false).build();
            case 40:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED).build();
            case 41:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_CANCEL_CLICKED).build();
            case 42:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED).isWithAnimation(true).isOpenThemeChanged(true).build();
            case 43:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SETTINGS_BUTTON_CLICKED).isWithAnimation(true).build();
            case 44:
                return new VolumePanelState.Builder(volumePanelState).stream(volumePanelAction.getStream()).setStateType(VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS).build();
            case 45:
                return new VolumePanelState.Builder(volumePanelState).stream(volumePanelAction.getStream()).setStateType(VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN).build();
            case 46:
                return new VolumePanelState.Builder(volumePanelState).stream(volumePanelAction.getStream()).setStateType(VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP).build();
            case 47:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_STATUS_MESSAGE_CLICKED).isWithAnimation(true).build();
            case 48:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED).isWithAnimation(true).build();
            case 49:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED).isFolded(volumePanelAction.isFolded()).isAnimating(false).isPendingState(false).isShowing(false).isShowingSubDisplayVolumePanel(false).build();
            case 50:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_ARROW_RIGHT_CLICKED).build();
            case 51:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_ARROW_LEFT_CLICKED).build();
            case 52:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_CAPTION_COMPONENT_CHANGED).isCaptionComponentEnabled(volumePanelAction.isCaptionComponentEnabled()).isCaptionEnabled(volumePanelAction.isCaptionEnabled()).build();
            case 53:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_CAPTION_CHANGED).isCaptionEnabled(!volumePanelState.isCaptionEnabled()).build();
            case 54:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SET_VOLUME_STATE).stream(volumePanelAction.getStream()).iconTargetState(volumePanelAction.getIconTargetState()).iconCurrentState(volumePanelAction.getIconCurrentState()).build();
            case 55:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DUAL_PLAY_MODE_CHANGED).isWithAnimation(true).build();
            case 56:
                return (volumePanelAction.getFlags() & 134217728) != 0 ? volumePanelState.isShowingVolumeCsd100WarningDialog() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_FLAG_DISMISS).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : isDisabledWarningDialog(volumePanelState.getCoverType(), volumePanelState.isCoverClosed()) ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build() : !volumePanelState.isShowingVolumeCsd100WarningDialog() ? new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG).isShowingVolumeCsd100WarningDialog(true).build() : new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            case 57:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_DISMISS_VOLUME_CSD_100_WARNING_DIALOG).isShowingVolumeCsd100WarningDialog(false).build();
            case 58:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_OK_CLICKED).build();
            case 59:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_KEY_EVENT).isKeyDown(volumePanelAction.isKeyDown()).isVibrating(volumePanelAction.isVibrating()).build();
            case 60:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_VOLUME_CSD_100_WARNING_DIALOG_SET_SAFETY_VOLUME).build();
            case 61:
                return new VolumePanelState.Builder(volumePanelState).setStateType(VolumePanelState.StateType.STATE_NO_DISPATCH).build();
            default:
                return build;
        }
    }
}
