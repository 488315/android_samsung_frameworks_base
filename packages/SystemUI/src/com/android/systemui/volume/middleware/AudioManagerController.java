package com.android.systemui.volume.middleware;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.StreamUtil;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.samsung.systemui.splugins.volume.VolumeState;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class AudioManagerController implements VolumeMiddleware {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy infraMediator$delegate;
    public Boolean isHeadsetConnected;
    public boolean isPanelShowing;
    public final Lazy log$delegate;
    public final Lazy store$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_START_SLIDER_TRACKING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_HEADSET_CONNECTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_PLAY_SOUND_ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_SET_STREAM_VOLUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_ICON_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_OK_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public AudioManagerController(final VolumeDependencyBase volumeDependencyBase) {
        final int i = 0;
        this.infraMediator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumeDependencyBase volumeDependencyBase2 = volumeDependencyBase;
                switch (i) {
                    case 0:
                        int i2 = AudioManagerController.$r8$clinit;
                        return (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase2).get(VolumeInfraMediator.class);
                    case 1:
                        int i3 = AudioManagerController.$r8$clinit;
                        return (VolumePanelStore) ((VolumeDependency) volumeDependencyBase2).get(VolumePanelStore.class);
                    default:
                        int i4 = AudioManagerController.$r8$clinit;
                        return (LogWrapper) ((VolumeDependency) volumeDependencyBase2).get(LogWrapper.class);
                }
            }
        });
        final int i2 = 1;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumeDependencyBase volumeDependencyBase2 = volumeDependencyBase;
                switch (i2) {
                    case 0:
                        int i22 = AudioManagerController.$r8$clinit;
                        return (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase2).get(VolumeInfraMediator.class);
                    case 1:
                        int i3 = AudioManagerController.$r8$clinit;
                        return (VolumePanelStore) ((VolumeDependency) volumeDependencyBase2).get(VolumePanelStore.class);
                    default:
                        int i4 = AudioManagerController.$r8$clinit;
                        return (LogWrapper) ((VolumeDependency) volumeDependencyBase2).get(LogWrapper.class);
                }
            }
        });
        final int i3 = 2;
        this.log$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumeDependencyBase volumeDependencyBase2 = volumeDependencyBase;
                switch (i3) {
                    case 0:
                        int i22 = AudioManagerController.$r8$clinit;
                        return (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase2).get(VolumeInfraMediator.class);
                    case 1:
                        int i32 = AudioManagerController.$r8$clinit;
                        return (VolumePanelStore) ((VolumeDependency) volumeDependencyBase2).get(VolumePanelStore.class);
                    default:
                        int i4 = AudioManagerController.$r8$clinit;
                        return (LogWrapper) ((VolumeDependency) volumeDependencyBase2).get(LogWrapper.class);
                }
            }
        });
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        boolean zBooleanValue;
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        boolean z = false;
        if (i == 1) {
            VolumePanelAction.Builder builder = new VolumePanelAction.Builder(volumePanelAction);
            builder.isSafeMediaDeviceOn(getStore().currentState.isSafeMediaDeviceOn()).isSafeMediaPinDeviceOn(getStore().currentState.isSafeMediaPinDeviceOn()).earProtectLevel(((getInfraMediator().getEarProtectLimit() - 1) * 100) + 9);
            Boolean bool = this.isHeadsetConnected;
            if (bool != null && (((zBooleanValue = bool.booleanValue()) && !getStore().currentState.isSafeMediaDeviceOn() && !getStore().currentState.isSafeMediaPinDeviceOn()) || (!zBooleanValue && (getStore().currentState.isSafeMediaDeviceOn() || getStore().currentState.isSafeMediaPinDeviceOn())))) {
                builder.isSafeMediaDeviceOn(getInfraMediator().isSafeMediaVolumeDeviceOn()).isSafeMediaPinDeviceOn(getInfraMediator().isSafeMediaVolumePinDeviceOn());
            }
            checkVoiceCallAndScoStream(builder, volumePanelAction.getImportantStreamList(), volumePanelAction.getUnImportantStreamList());
            VolumeState volumeState = volumePanelAction.getVolumeState();
            volumeState.getClass();
            if (volumeState.isDualAudio() && getInfraMediator().isMultiSoundOn()) {
                int devicesForStreamMusic = getInfraMediator().getDevicesForStreamMusic();
                int pinDevice = getInfraMediator().getPinDevice();
                if (devicesForStreamMusic == pinDevice) {
                    pinDevice = 0;
                }
                if (getInfraMediator().getMultiSoundDevice() == 8 && pinDevice != 0) {
                    z = true;
                }
            }
            return builder.isMultiSoundBt(z).build();
        }
        if (i != 2) {
            if (i == 3) {
                getInfraMediator().setActiveStream(volumePanelAction.getStream());
                return volumePanelAction;
            }
            if (i == 4) {
                this.isHeadsetConnected = Boolean.valueOf(volumePanelAction.isHeadsetConnected());
                return volumePanelAction;
            }
        } else if (!this.isPanelShowing) {
            this.isPanelShowing = true;
            VolumePanelAction.Builder builder2 = new VolumePanelAction.Builder(volumePanelAction);
            builder2.isSafeMediaDeviceOn(getInfraMediator().isSafeMediaVolumeDeviceOn()).isSafeMediaPinDeviceOn(getInfraMediator().isSafeMediaVolumePinDeviceOn());
            List<Integer> importantStreamList = volumePanelAction.getImportantStreamList();
            List<Integer> unImportantStreamList = volumePanelAction.getUnImportantStreamList();
            boolean zCheckVoiceCallAndScoStream = checkVoiceCallAndScoStream(builder2, importantStreamList, unImportantStreamList);
            int devicesForStreamMusic2 = getInfraMediator().getDevicesForStreamMusic();
            int pinDevice2 = getInfraMediator().getPinDevice();
            if (devicesForStreamMusic2 == pinDevice2 || zCheckVoiceCallAndScoStream) {
                pinDevice2 = 0;
            }
            List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(21);
            if (pinDevice2 != 0 && !getInfraMediator().isSmartView() && !getInfraMediator().isAudioMirroring() && !getInfraMediator().isLeBroadcasting() && !getInfraMediator().isUserInCall()) {
                z = true;
            }
            LogWrapper logWrapper = (LogWrapper) this.log$delegate.getValue();
            String hexString = Integer.toHexString(devicesForStreamMusic2);
            String hexString2 = Integer.toHexString(pinDevice2);
            boolean zIsSmartView = getInfraMediator().isSmartView();
            boolean zIsAudioMirroring = getInfraMediator().isAudioMirroring();
            boolean zIsLeBroadcasting = getInfraMediator().isLeBroadcasting();
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("device=", hexString, ", pinDevice=", hexString2, ", isSmartView=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zIsSmartView, ", isAudioMirroring=", zIsAudioMirroring, ", isLeBroadcasting=");
            logWrapper.d("AudioManagerController", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zIsLeBroadcasting, ", isImportant=", z));
            if (z) {
                importantStreamList.addAll(listMutableListOf);
            } else {
                unImportantStreamList.addAll(listMutableListOf);
            }
            builder2.pinAppName(getInfraMediator().getPinAppName(pinDevice2)).pinDeviceName(getInfraMediator().getPinDeviceName(pinDevice2)).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).pinDevice(pinDevice2);
            return builder2.build();
        }
        return volumePanelAction;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$1[volumePanelState.getStateType().ordinal()];
        if (i == 1) {
            this.isPanelShowing = false;
            return;
        }
        if (i == 2) {
            if (BasicRune.VOLUME_HOME_IOT) {
                getInfraMediator().initSound(1);
                getInfraMediator().playSound(volumePanelState.getVolumeDirection());
                return;
            } else {
                getInfraMediator().initSound(volumePanelState.getActiveStream());
                getInfraMediator().playSound();
                return;
            }
        }
        if (i == 3) {
            int stream = volumePanelState.getStream();
            VolumePanelRow volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream);
            int realLevel = volumePanelRowFindRow != null ? volumePanelRowFindRow.getRealLevel() : 0;
            boolean zIsRemoteMic = volumePanelState.isRemoteMic();
            if (VolumePanelValues.isBluetoothSco(stream) && !zIsRemoteMic) {
                realLevel--;
            }
            setStreamVolume(volumePanelState, stream, realLevel);
            return;
        }
        if (i != 4) {
            if (i != 5) {
                return;
            }
            getInfraMediator().disableSafeMediaVolume();
        } else {
            int stream2 = volumePanelState.getStream();
            VolumePanelRow volumePanelRowFindRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream2);
            int level = volumePanelRowFindRow2 != null ? volumePanelRowFindRow2.getLevel() : 0;
            getInfraMediator().setActiveStream(stream2);
            setStreamVolume(volumePanelState, stream2, level);
            getInfraMediator().setRingerMode(volumePanelState.getRingerModeInternal(), false);
        }
    }

    public final boolean checkVoiceCallAndScoStream(VolumePanelAction.Builder builder, List list, List list2) {
        boolean z = false;
        List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(0);
        List listMutableListOf2 = CollectionsKt__CollectionsKt.mutableListOf(6);
        boolean zIsBluetoothScoOn = getInfraMediator().isBluetoothScoOn();
        boolean zIsBluetoothScoOn2 = getInfraMediator().isBluetoothScoOn();
        String btCallDeviceName = null;
        if (getInfraMediator().isUserInCall()) {
            if (zIsBluetoothScoOn) {
                btCallDeviceName = getInfraMediator().getBtCallDeviceName();
                z = true;
            } else if (zIsBluetoothScoOn2) {
                btCallDeviceName = getInfraMediator().getBtCallDeviceName();
            }
            list.addAll(listMutableListOf);
            list2.addAll(listMutableListOf2);
        } else {
            list2.addAll(listMutableListOf);
            list2.addAll(listMutableListOf2);
        }
        builder.isBtScoOn(zIsBluetoothScoOn).btCallDeviceName(btCallDeviceName).setImportantStreamList(list).setUnImportantStreamList(list2);
        return z;
    }

    public final VolumeInfraMediator getInfraMediator() {
        return (VolumeInfraMediator) this.infraMediator$delegate.getValue();
    }

    public final VolumePanelStore getStore() {
        return (VolumePanelStore) this.store$delegate.getValue();
    }

    public final void setStreamVolume(VolumePanelState volumePanelState, int i, int i2) {
        Object next;
        if ((!volumePanelState.isDualAudio() || i != StreamUtil.getMusicStream(volumePanelState.isMultiSoundBt())) && !VolumePanelValues.isDualAudio(i)) {
            getInfraMediator().setStreamVolume(i, i2);
            return;
        }
        Iterator<T> it = volumePanelState.getVolumeRowList().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (((VolumePanelRow) next).getStreamType() == i) {
                    break;
                }
            }
        }
        VolumePanelRow volumePanelRow = (VolumePanelRow) next;
        getInfraMediator().setStreamVolumeDualAudio(i, i2, volumePanelRow != null ? volumePanelRow.getDualBtDeviceAddress() : null);
    }
}
