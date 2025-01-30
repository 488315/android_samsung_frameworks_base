package com.android.systemui.volume.middleware;

import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AudioManagerController implements VolumeMiddleware {
    public final Lazy infraMediator$delegate;
    public Boolean isHeadsetConnected;
    public boolean isPanelShowing;
    public final Lazy log$delegate;
    public final Lazy store$delegate;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.infraMediator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$infraMediator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumeInfraMediator) ((VolumeDependency) VolumeDependencyBase.this).get(VolumeInfraMediator.class);
            }
        });
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$store$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumePanelStore) ((VolumeDependency) VolumeDependencyBase.this).get(VolumePanelStore.class);
            }
        });
        this.log$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.middleware.AudioManagerController$log$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (LogWrapper) ((VolumeDependency) VolumeDependencyBase.this).get(LogWrapper.class);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x015d  */
    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object apply(Object obj) {
        boolean booleanValue;
        boolean z;
        String str;
        int devicesForStreamMusic;
        int pinDevice;
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        boolean z2 = false;
        if (i == 1) {
            VolumePanelAction.Builder builder = new VolumePanelAction.Builder(volumePanelAction);
            builder.isSafeMediaDeviceOn(getStore().currentState.isSafeMediaDeviceOn()).isSafeMediaPinDeviceOn(getStore().currentState.isSafeMediaPinDeviceOn()).earProtectLevel(((getInfraMediator().getEarProtectLimit() - 1) * 100) + 9);
            Boolean bool = this.isHeadsetConnected;
            if (bool != null && (((booleanValue = bool.booleanValue()) && !getStore().currentState.isSafeMediaDeviceOn() && !getStore().currentState.isSafeMediaPinDeviceOn()) || (!booleanValue && (getStore().currentState.isSafeMediaDeviceOn() || getStore().currentState.isSafeMediaPinDeviceOn())))) {
                builder.isSafeMediaDeviceOn(getInfraMediator().isSafeMediaVolumeDeviceOn()).isSafeMediaPinDeviceOn(getInfraMediator().isSafeMediaVolumePinDeviceOn());
            }
            VolumeState volumeState = volumePanelAction.getVolumeState();
            Intrinsics.checkNotNull(volumeState);
            if (volumeState.isDualAudio() && getInfraMediator().isMultiSoundOn()) {
                int devicesForStreamMusic2 = getInfraMediator().getDevicesForStreamMusic();
                int pinDevice2 = getInfraMediator().getPinDevice();
                if (devicesForStreamMusic2 == pinDevice2) {
                    pinDevice2 = 0;
                }
                if (getInfraMediator().getMultiSoundDevice() == 8 && pinDevice2 != 0) {
                    z2 = true;
                }
            }
            return builder.isMultiSoundBt(z2).build();
        }
        if (i != 2) {
            if (i == 3) {
                getInfraMediator().setActiveStream(volumePanelAction.getStream());
                return volumePanelAction;
            }
            if (i != 4) {
                return volumePanelAction;
            }
            this.isHeadsetConnected = Boolean.valueOf(volumePanelAction.isHeadsetConnected());
            return volumePanelAction;
        }
        if (this.isPanelShowing) {
            return volumePanelAction;
        }
        this.isPanelShowing = true;
        VolumePanelAction.Builder builder2 = new VolumePanelAction.Builder(volumePanelAction);
        builder2.isSafeMediaDeviceOn(getInfraMediator().isSafeMediaVolumeDeviceOn()).isSafeMediaPinDeviceOn(getInfraMediator().isSafeMediaVolumePinDeviceOn());
        List<Integer> importantStreamList = volumePanelAction.getImportantStreamList();
        List<Integer> unImportantStreamList = volumePanelAction.getUnImportantStreamList();
        List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(0);
        List mutableListOf2 = CollectionsKt__CollectionsKt.mutableListOf(6);
        boolean isBluetoothScoOn = getInfraMediator().isBluetoothScoOn();
        boolean isBluetoothScoOn2 = getInfraMediator().isBluetoothScoOn();
        if (!getInfraMediator().isUserInCall()) {
            unImportantStreamList.addAll(mutableListOf);
            unImportantStreamList.addAll(mutableListOf2);
        } else {
            if (isBluetoothScoOn) {
                str = getInfraMediator().getBtCallDeviceName();
                importantStreamList.addAll(mutableListOf2);
                unImportantStreamList.addAll(mutableListOf);
                z = true;
                builder2.isBtScoOn(isBluetoothScoOn).btCallDeviceName(str).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList);
                devicesForStreamMusic = getInfraMediator().getDevicesForStreamMusic();
                pinDevice = getInfraMediator().getPinDevice();
                if (devicesForStreamMusic != pinDevice || z) {
                    pinDevice = 0;
                }
                List mutableListOf3 = CollectionsKt__CollectionsKt.mutableListOf(21);
                if (pinDevice != 0 && !getInfraMediator().isSmartView() && !getInfraMediator().isAudioMirroring() && !getInfraMediator().isLeBroadcasting()) {
                    z2 = true;
                }
                LogWrapper logWrapper = (LogWrapper) this.log$delegate.getValue();
                String hexString = Integer.toHexString(devicesForStreamMusic);
                String hexString2 = Integer.toHexString(pinDevice);
                boolean isSmartView = getInfraMediator().isSmartView();
                boolean isAudioMirroring = getInfraMediator().isAudioMirroring();
                boolean isLeBroadcasting = getInfraMediator().isLeBroadcasting();
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("device=", hexString, ", pinDevice=", hexString2, ", isSmartView=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m87m, isSmartView, ", isAudioMirroring=", isAudioMirroring, ", isLeBroadcasting=");
                logWrapper.m98d("AudioManagerController", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m87m, isLeBroadcasting, ", isImportant=", z2));
                if (z2) {
                    unImportantStreamList.addAll(mutableListOf3);
                } else {
                    importantStreamList.addAll(mutableListOf3);
                }
                builder2.pinAppName(getInfraMediator().getPinAppName(pinDevice)).pinDeviceName(getInfraMediator().getPinDeviceName(pinDevice)).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).pinDevice(pinDevice);
                return builder2.build();
            }
            r9 = isBluetoothScoOn2 ? getInfraMediator().getBtCallDeviceName() : null;
            importantStreamList.addAll(mutableListOf);
            unImportantStreamList.addAll(mutableListOf2);
        }
        z = false;
        str = r9;
        builder2.isBtScoOn(isBluetoothScoOn).btCallDeviceName(str).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList);
        devicesForStreamMusic = getInfraMediator().getDevicesForStreamMusic();
        pinDevice = getInfraMediator().getPinDevice();
        if (devicesForStreamMusic != pinDevice) {
        }
        pinDevice = 0;
        List mutableListOf32 = CollectionsKt__CollectionsKt.mutableListOf(21);
        if (pinDevice != 0) {
            z2 = true;
        }
        LogWrapper logWrapper2 = (LogWrapper) this.log$delegate.getValue();
        String hexString3 = Integer.toHexString(devicesForStreamMusic);
        String hexString22 = Integer.toHexString(pinDevice);
        boolean isSmartView2 = getInfraMediator().isSmartView();
        boolean isAudioMirroring2 = getInfraMediator().isAudioMirroring();
        boolean isLeBroadcasting2 = getInfraMediator().isLeBroadcasting();
        StringBuilder m87m2 = AbstractC0866xb1ce8deb.m87m("device=", hexString3, ", pinDevice=", hexString22, ", isSmartView=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m87m2, isSmartView2, ", isAudioMirroring=", isAudioMirroring2, ", isLeBroadcasting=");
        logWrapper2.m98d("AudioManagerController", KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m87m2, isLeBroadcasting2, ", isImportant=", z2));
        if (z2) {
        }
        builder2.pinAppName(getInfraMediator().getPinAppName(pinDevice)).pinDeviceName(getInfraMediator().getPinDeviceName(pinDevice)).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).pinDevice(pinDevice);
        return builder2.build();
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
            VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream);
            int realLevel = findRow != null ? findRow.getRealLevel() : 0;
            boolean isRemoteMic = volumePanelState.isRemoteMic();
            if (VolumePanelValues.isBluetoothSco(stream) && !isRemoteMic) {
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
            VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, stream2);
            int level = findRow2 != null ? findRow2.getLevel() : 0;
            getInfraMediator().setActiveStream(stream2);
            setStreamVolume(volumePanelState, stream2, level);
            getInfraMediator().setRingerMode(volumePanelState.getRingerModeInternal(), false);
        }
    }

    public final VolumeInfraMediator getInfraMediator() {
        return (VolumeInfraMediator) this.infraMediator$delegate.getValue();
    }

    public final VolumePanelStore getStore() {
        return (VolumePanelStore) this.store$delegate.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0012, code lost:
    
        if (r5 != (r0 ? 21 : 3)) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setStreamVolume(VolumePanelState volumePanelState, int i, int i2) {
        Object obj;
        if (volumePanelState.isDualAudio()) {
            boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
            int i3 = StreamUtil.$r8$clinit;
        }
        if (!VolumePanelValues.isDualAudio(i)) {
            getInfraMediator().setStreamVolume(i, i2);
            return;
        }
        Iterator<T> it = volumePanelState.getVolumeRowList().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((VolumePanelRow) obj).getStreamType() == i) {
                    break;
                }
            }
        }
        VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
        getInfraMediator().setStreamVolumeDualAudio(i, i2, volumePanelRow != null ? volumePanelRow.getDualBtDeviceAddress() : null);
    }
}
