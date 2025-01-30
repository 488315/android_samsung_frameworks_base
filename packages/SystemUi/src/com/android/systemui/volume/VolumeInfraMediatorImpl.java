package com.android.systemui.volume;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.util.AccessibilityManagerWrapper;
import com.android.systemui.volume.util.AudioManagerWrapper;
import com.android.systemui.volume.util.BixbyServiceManager;
import com.android.systemui.volume.util.BluetoothAdapterWrapper;
import com.android.systemui.volume.util.BluetoothAudioCastWrapper;
import com.android.systemui.volume.util.BroadcastSender;
import com.android.systemui.volume.util.ConfigurationWrapper;
import com.android.systemui.volume.util.DesktopManagerWrapper;
import com.android.systemui.volume.util.DeviceProvisionedWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.android.systemui.volume.util.SemPersonaManagerWrapper;
import com.android.systemui.volume.util.SoundAssistantManagerWrapper;
import com.android.systemui.volume.util.SoundPoolWrapper;
import com.android.systemui.volume.util.StatusBarStateControllerWrapper;
import com.android.systemui.volume.util.StatusBarWrapper;
import com.android.systemui.volume.util.SystemClockWrapper;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.android.systemui.volume.util.ZenModeHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeInfraMediatorImpl implements VolumeInfraMediator {
    public final AccessibilityManagerWrapper accessibilityManagerWrapper;
    public final AudioManagerWrapper audioManagerWrapper;
    public final BixbyServiceManager bixbyServiceManager;
    public final BluetoothAdapterWrapper bluetoothAdapterWrapper;
    public final BluetoothAudioCastWrapper bluetoothAudioCastWrapper;
    public final BroadcastSender broadcastSender;
    public final ConfigurationWrapper configurationWrapper;
    public final Context context;
    public final DesktopManagerWrapper desktopManagerWrapper;
    public final DeviceProvisionedWrapper deviceProvisionedWrapper;
    public final DisplayManagerWrapper displayManagerWrapper;
    public final HandlerWrapper handlerWrapper;
    public final LogWrapper logWrapper;
    public final PowerManagerWrapper powerManagerWrapper;
    public final SALoggingWrapper saLoggingWrapper;
    public final SemPersonaManagerWrapper semPersonaManagerWrapper;
    public final SettingsHelper settingsHelper;
    public final SoundAssistantManagerWrapper soundAssistantManagerWrapper;
    public final SoundPoolWrapper soundPoolWrapper;
    public final StatusBarStateControllerWrapper statusBarStateControllerWrapper;
    public final StatusBarWrapper statusBarWrapper;
    public final SystemClockWrapper systemClockWrapper;
    public final VolumeDialogController volumeController;
    public final ZenModeHelper zenModeHelper;

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
            int[] iArr = new int[VolumeInfraMediator.Conditions.values().length];
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SAFE_MEDIA_VOLUME_DEVICE_ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SAFE_MEDIA_VOLUME_PIN_DEVICE_ON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_USER_IN_CALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BLUETOOTH_SCO_ON.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BLE_CALL_DEVICE_ON.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BIXBY_SERVICE_FOREGROUND.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BIXBY_SERVICE_ON.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SMART_VIEW.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_ENABLED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_PRIORITY_ONLY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_NONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_VOICE_CAPABLE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ALL_SOUND_OFF.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.HAS_VIBRATOR.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_MEDIA_DEFAULT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_KEYGUARD_STATE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SHADE_LOCKED_STATE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ORIENTATION_CHANGED.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DENSITY_OR_FONT_CHANGED.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_STANDALONE.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DISPLAY_TYPE_CHANGED.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_LCD_OFF.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DEX_MODE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_KIOSK_MODE_ENABLED.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BUDS_TOGETHER_ENABLED.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SETUP_WIZARD_COMPLETE.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_CAPTION_ENABLED.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_MULTI_SOUND_ON.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_SMART_VIEW_STREAM.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_WARNING_POPUP_WALLET_MINI.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_WARNING_POPUP_SIDE_VIEW.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_BUDS_TOGETHER.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_DUAL_AUDIO.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_AOD_VOLUME_PANEL.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumeInfraMediator.Values.values().length];
            try {
                iArr2[VolumeInfraMediator.Values.EAR_PROTECT_LIMIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.BT_CALL_DEVICE_NAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.DEVICES_FOR_STREAM_MUSIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_APP_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_DEVICE_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_DEVICE.ordinal()] = 6;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.SMART_VIEW_DEVICE_NAME.ordinal()] = 7;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.TIMEOUT_CONTROLS.ordinal()] = 8;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.TIMEOUT_CONTROLS_TEXT.ordinal()] = 9;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.CUTOUT_HEIGHT.ordinal()] = 10;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.SYSTEM_TIME.ordinal()] = 11;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.ACTIVE_BT_DEVICE_NAME.ordinal()] = 12;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.AUDIO_CAST_DEVICE_NAME.ordinal()] = 13;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.MULTI_SOUND_DEVICE.ordinal()] = 14;
            } catch (NoSuchFieldError unused48) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public VolumeInfraMediatorImpl(VolumeDependencyBase volumeDependencyBase) {
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        this.context = (Context) volumeDependency.get(Context.class);
        this.volumeController = (VolumeDialogController) volumeDependency.get(VolumeDialogController.class);
        this.audioManagerWrapper = (AudioManagerWrapper) volumeDependency.get(AudioManagerWrapper.class);
        this.bluetoothAdapterWrapper = (BluetoothAdapterWrapper) volumeDependency.get(BluetoothAdapterWrapper.class);
        this.bixbyServiceManager = (BixbyServiceManager) volumeDependency.get(BixbyServiceManager.class);
        this.logWrapper = (LogWrapper) volumeDependency.get(LogWrapper.class);
        this.displayManagerWrapper = (DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class);
        this.zenModeHelper = (ZenModeHelper) volumeDependency.get(ZenModeHelper.class);
        this.soundPoolWrapper = (SoundPoolWrapper) volumeDependency.get(SoundPoolWrapper.class);
        this.accessibilityManagerWrapper = (AccessibilityManagerWrapper) volumeDependency.get(AccessibilityManagerWrapper.class);
        this.statusBarStateControllerWrapper = (StatusBarStateControllerWrapper) volumeDependency.get(StatusBarStateControllerWrapper.class);
        this.soundAssistantManagerWrapper = (SoundAssistantManagerWrapper) volumeDependency.get(SoundAssistantManagerWrapper.class);
        this.statusBarWrapper = (StatusBarWrapper) volumeDependency.get(StatusBarWrapper.class);
        this.systemClockWrapper = (SystemClockWrapper) volumeDependency.get(SystemClockWrapper.class);
        this.settingsHelper = (SettingsHelper) volumeDependency.get(SettingsHelper.class);
        this.configurationWrapper = (ConfigurationWrapper) volumeDependency.get(ConfigurationWrapper.class);
        this.desktopManagerWrapper = (DesktopManagerWrapper) volumeDependency.get(DesktopManagerWrapper.class);
        this.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        this.powerManagerWrapper = (PowerManagerWrapper) volumeDependency.get(PowerManagerWrapper.class);
        this.broadcastSender = (BroadcastSender) volumeDependency.get(BroadcastSender.class);
        this.saLoggingWrapper = (SALoggingWrapper) volumeDependency.get(SALoggingWrapper.class);
        this.semPersonaManagerWrapper = (SemPersonaManagerWrapper) volumeDependency.get(SemPersonaManagerWrapper.class);
        this.bluetoothAudioCastWrapper = (BluetoothAudioCastWrapper) volumeDependency.get(BluetoothAudioCastWrapper.class);
        this.deviceProvisionedWrapper = (DeviceProvisionedWrapper) volumeDependency.get(DeviceProvisionedWrapper.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void disableSafeMediaVolume() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            audioManagerWrapper.f398am.disableSafeMediaVolume();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_disableSafeMediaVolume");
        try {
            audioManagerWrapper.f398am.disableSafeMediaVolume();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final Object get(final VolumeInfraMediator.Values values, Object... objArr) {
        final Object valueOf;
        switch (WhenMappings.$EnumSwitchMapping$1[values.ordinal()]) {
            case 1:
                valueOf = Integer.valueOf(getEarProtectLimit());
                break;
            case 2:
                valueOf = getBtCallDeviceName();
                break;
            case 3:
                valueOf = Integer.valueOf(getDevicesForStreamMusic());
                break;
            case 4:
                valueOf = getPinAppName(((Integer) objArr[0]).intValue());
                break;
            case 5:
                valueOf = getPinDeviceName(((Integer) objArr[0]).intValue());
                break;
            case 6:
                valueOf = Integer.valueOf(getPinDevice());
                break;
            case 7:
                valueOf = getSmartViewDeviceName();
                break;
            case 8:
                valueOf = Integer.valueOf(getTimeoutControls());
                break;
            case 9:
                valueOf = Integer.valueOf(getTimeoutControlsText());
                break;
            case 10:
                valueOf = Integer.valueOf(getCutoutHeight());
                break;
            case 11:
                valueOf = Long.valueOf(getSystemTime());
                break;
            case 12:
                valueOf = getActiveBtDeviceName();
                break;
            case 13:
                valueOf = getAudioCastDeviceName();
                break;
            case 14:
                valueOf = Integer.valueOf(getMultiSoundDevice());
                break;
            default:
                valueOf = 0;
                break;
        }
        if (valueOf != null && VolumeInfraMediator.Values.SYSTEM_TIME != values) {
            this.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.VolumeInfraMediatorImpl$get$1
                @Override // java.lang.Runnable
                public final void run() {
                    VolumeInfraMediatorImpl.this.logWrapper.m102p(values + "=" + valueOf);
                }
            });
        }
        return valueOf;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getActiveBtDeviceName() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAudioCastWrapper bluetoothAudioCastWrapper = this.bluetoothAudioCastWrapper;
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.bluetoothAdapterWrapper;
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isMusicShareEnabled() ? bluetoothAudioCastWrapper.getCastDeviceConnectedName() : bluetoothAdapterWrapper.getActiveBTDeviceName();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getActiveBtDeviceName");
        try {
            return volumeDialogController.isMusicShareEnabled() ? bluetoothAudioCastWrapper.getCastDeviceConnectedName() : bluetoothAdapterWrapper.getActiveBTDeviceName();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getAudioCastDeviceName() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAudioCastWrapper bluetoothAudioCastWrapper = this.bluetoothAudioCastWrapper;
        if (!isTagEnabled) {
            return bluetoothAudioCastWrapper.getCastDeviceConnectedName();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getAudioCastDeviceName");
        try {
            return bluetoothAudioCastWrapper.getCastDeviceConnectedName();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void getBixbyServiceState() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        final BixbyServiceManager bixbyServiceManager = this.bixbyServiceManager;
        if (!isTagEnabled) {
            bixbyServiceManager.getClass();
            new Thread(new Runnable() { // from class: com.android.systemui.volume.util.BixbyServiceManager$getBixbyServiceState$1
                @Override // java.lang.Runnable
                public final void run() {
                    BixbyServiceManager bixbyServiceManager2 = BixbyServiceManager.this;
                    bixbyServiceManager2.isBixbyServiceChecked = false;
                    bixbyServiceManager2.isBixbyServiceOn = bixbyServiceManager2.checkBixbyServiceEnabled();
                    BixbyServiceManager bixbyServiceManager3 = BixbyServiceManager.this;
                    bixbyServiceManager3.logWrapper.m101i("BixbyServiceManager", "isBixbyServiceOn=" + bixbyServiceManager3.isBixbyServiceOn);
                }
            }, "getBixbyServiceState").start();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getBixbyServiceState");
        try {
            bixbyServiceManager.getClass();
            new Thread(new Runnable() { // from class: com.android.systemui.volume.util.BixbyServiceManager$getBixbyServiceState$1
                @Override // java.lang.Runnable
                public final void run() {
                    BixbyServiceManager bixbyServiceManager2 = BixbyServiceManager.this;
                    bixbyServiceManager2.isBixbyServiceChecked = false;
                    bixbyServiceManager2.isBixbyServiceOn = bixbyServiceManager2.checkBixbyServiceEnabled();
                    BixbyServiceManager bixbyServiceManager3 = BixbyServiceManager.this;
                    bixbyServiceManager3.logWrapper.m101i("BixbyServiceManager", "isBixbyServiceOn=" + bixbyServiceManager3.isBixbyServiceOn);
                }
            }, "getBixbyServiceState").start();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getBtCallDeviceName() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.bluetoothAdapterWrapper;
        if (!isTagEnabled) {
            return bluetoothAdapterWrapper.getBtCallDeviceName();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getBtCallDeviceName");
        try {
            return bluetoothAdapterWrapper.getBtCallDeviceName();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void getCaptionsComponentState(boolean z) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.getCaptionsComponentState(z);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getCaptionsComponentState");
        try {
            volumeDialogController.getCaptionsComponentState(z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getCutoutHeight() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!isTagEnabled) {
            return statusBarWrapper.getCutoutHeight();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getCutoutHeight");
        try {
            return statusBarWrapper.getCutoutHeight();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getDevicesForStreamMusic() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.getDevicesForStream(3);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getDevicesForStreamMusic");
        try {
            return audioManagerWrapper.f398am.getDevicesForStream(3);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getEarProtectLimit() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            audioManagerWrapper.getClass();
            return AudioManager.semGetEarProtectLimit();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getEarProtectLimit");
        try {
            audioManagerWrapper.getClass();
            return AudioManager.semGetEarProtectLimit();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getMultiSoundDevice() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!isTagEnabled) {
            return soundAssistantManagerWrapper.satMananger.getMultiSoundDevice();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getMultiSoundDevice");
        try {
            return soundAssistantManagerWrapper.satMananger.getMultiSoundDevice();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getMusicFineVolume() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.getFineVolume(3, 0);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getMusicFineVolume");
        try {
            return audioManagerWrapper.f398am.getFineVolume(3, 0);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinAppName(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.getPinAppName(i);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinAppName");
        try {
            return audioManagerWrapper.f398am.getPinAppName(i);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getPinDevice() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.semGetPinDevice();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinDevice");
        try {
            return audioManagerWrapper.f398am.semGetPinDevice();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinDeviceName(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.getPinDeviceName(i);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinDeviceName");
        try {
            return audioManagerWrapper.f398am.getPinDeviceName(i);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getSmartViewDeviceName() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        if (!isTagEnabled) {
            return displayManagerWrapper.getSmartViewDeviceName();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getSmartViewDeviceName");
        try {
            return displayManagerWrapper.getSmartViewDeviceName();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final long getSystemTime() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SystemClockWrapper systemClockWrapper = this.systemClockWrapper;
        if (!isTagEnabled) {
            systemClockWrapper.getClass();
            return SystemClock.uptimeMillis();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getSystemTime");
        try {
            systemClockWrapper.getClass();
            return SystemClock.uptimeMillis();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getTimeoutControls() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AccessibilityManagerWrapper accessibilityManagerWrapper = this.accessibilityManagerWrapper;
        if (!isTagEnabled) {
            return accessibilityManagerWrapper.getRecommendedTimeoutMillis(4);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getTimeoutControls");
        try {
            return accessibilityManagerWrapper.getRecommendedTimeoutMillis(4);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getTimeoutControlsText() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AccessibilityManagerWrapper accessibilityManagerWrapper = this.accessibilityManagerWrapper;
        if (!isTagEnabled) {
            return accessibilityManagerWrapper.getRecommendedTimeoutMillis(6);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getTimeoutControlsText");
        try {
            return accessibilityManagerWrapper.getRecommendedTimeoutMillis(6);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void initSound(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (!isTagEnabled) {
            soundPoolWrapper.initSound(i);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_initSound");
        try {
            soundPoolWrapper.initSound(i);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAllSoundOff() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SettingsHelper settingsHelper = this.settingsHelper;
        if (!isTagEnabled) {
            return settingsHelper.mItemLists.get("all_sound_off").getIntValue() == 1;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isAllSoundOff");
        try {
            return settingsHelper.mItemLists.get("all_sound_off").getIntValue() == 1;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAodVolumePanel() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isAODVolumePanel();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isAodVolumePanel");
        try {
            return volumeDialogController.isAODVolumePanel();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAudioMirroring() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isAudioMirroring();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isAudioMirroring");
        try {
            return volumeDialogController.isAudioMirroring();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBixbyServiceForeground() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BixbyServiceManager bixbyServiceManager = this.bixbyServiceManager;
        if (!isTagEnabled) {
            return bixbyServiceManager.isBixbyServiceForeground();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBixbyServiceForeground");
        try {
            return bixbyServiceManager.isBixbyServiceForeground();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBixbyServiceOn() {
        boolean z;
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BixbyServiceManager bixbyServiceManager = this.bixbyServiceManager;
        if (!isTagEnabled) {
            if (bixbyServiceManager.isBixbyServiceChecked) {
                return bixbyServiceManager.isBixbyServiceOn;
            }
            boolean checkBixbyServiceEnabled = bixbyServiceManager.checkBixbyServiceEnabled();
            bixbyServiceManager.isBixbyServiceOn = checkBixbyServiceEnabled;
            return checkBixbyServiceEnabled;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBixbyServiceOn");
        try {
            if (bixbyServiceManager.isBixbyServiceChecked) {
                z = bixbyServiceManager.isBixbyServiceOn;
            } else {
                boolean checkBixbyServiceEnabled2 = bixbyServiceManager.checkBixbyServiceEnabled();
                bixbyServiceManager.isBixbyServiceOn = checkBixbyServiceEnabled2;
                z = checkBixbyServiceEnabled2;
            }
            Trace.traceEnd(4096L);
            return z;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:
    
        if (r6.getType() == 26) goto L13;
     */
    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isBleCallDeviceOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        boolean z = true;
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            AudioDeviceInfo communicationDevice = audioManagerWrapper.f398am.getCommunicationDevice();
            return communicationDevice != null && communicationDevice.getType() == 26;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBleCallDeviceOn");
        try {
            AudioDeviceInfo communicationDevice2 = audioManagerWrapper.f398am.getCommunicationDevice();
            if (communicationDevice2 != null) {
            }
            z = false;
            return z;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBluetoothScoOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.isBluetoothScoOn();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBluetoothScoOn");
        try {
            return audioManagerWrapper.f398am.isBluetoothScoOn();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBudsTogetherEnabled() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isBudsTogetherEnabled();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBudsTogetherEnabled");
        try {
            return volumeDialogController.isBudsTogetherEnabled();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isCaptionEnabled() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.areCaptionsEnabled();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isCaptionEnabled");
        try {
            return volumeDialogController.areCaptionsEnabled();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDensityOrFontChanged() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!isTagEnabled) {
            return configurationWrapper.isDensityOrFontScaleChanged();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isDensityOrFontChanged");
        try {
            return configurationWrapper.isDensityOrFontScaleChanged();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDexMode() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        DesktopManagerWrapper desktopManagerWrapper = this.desktopManagerWrapper;
        if (!isTagEnabled) {
            return ((DesktopManagerImpl) desktopManagerWrapper.desktopManager).isDesktopMode();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isDexMode");
        try {
            return ((DesktopManagerImpl) desktopManagerWrapper.desktopManager).isDesktopMode();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDisplayTypeChanged() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!isTagEnabled) {
            return configurationWrapper.isDisplayTypeChanged();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isDisplayTypeChanged");
        try {
            return configurationWrapper.isDisplayTypeChanged();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isEnabled(final VolumeInfraMediator.Conditions conditions, Object... objArr) {
        final boolean z = false;
        switch (WhenMappings.$EnumSwitchMapping$0[conditions.ordinal()]) {
            case 1:
                z = isSafeMediaVolumeDeviceOn();
                break;
            case 2:
                z = isSafeMediaVolumePinDeviceOn();
                break;
            case 3:
                z = isUserInCall();
                break;
            case 4:
                z = isBluetoothScoOn();
                break;
            case 5:
                z = isBleCallDeviceOn();
                break;
            case 6:
                z = isBixbyServiceForeground();
                break;
            case 7:
                z = isBixbyServiceOn();
                break;
            case 8:
                z = isSmartView();
                break;
            case 9:
                z = isZenModeEnabled(((Integer) objArr[0]).intValue());
                break;
            case 10:
                z = isZenModePriorityOnly(((Integer) objArr[0]).intValue());
                break;
            case 11:
                z = isZenModeNone(((Integer) objArr[0]).intValue());
                break;
            case 12:
                z = isVoiceCapable();
                break;
            case 13:
                z = isAllSoundOff();
                break;
            case 14:
                z = isHasVibrator();
                break;
            case 15:
                z = isMediaDefault();
                break;
            case 16:
                z = isKeyguardState();
                break;
            case 17:
                z = isShadeLockedState();
                break;
            case 18:
                z = isOrientationChanged();
                break;
            case 19:
                z = isDensityOrFontChanged();
                break;
            case 20:
                z = isStandalone();
                break;
            case 21:
                z = isDisplayTypeChanged();
                break;
            case 22:
                z = isLcdOff();
                break;
            case 23:
                z = isDexMode();
                break;
            case 24:
                z = isKioskModeEnabled();
                break;
            case 25:
                z = isBudsTogetherEnabled();
                break;
            case 26:
                z = isSetupWizardComplete();
                break;
            case 27:
                z = isCaptionEnabled();
                break;
            case 28:
                z = isMultiSoundOn();
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                z = true;
                break;
            case 34:
                z = isAodVolumePanel();
                break;
        }
        this.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.VolumeInfraMediatorImpl$isEnabled$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeInfraMediatorImpl.this.logWrapper.m102p(conditions + "=" + z);
            }
        });
        return z;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isHasVibrator() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.hasVibrator();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isHasVibrator");
        try {
            return volumeDialogController.hasVibrator();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isKeyguardState() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarStateControllerWrapper statusBarStateControllerWrapper = this.statusBarStateControllerWrapper;
        if (!isTagEnabled) {
            return ((StatusBarStateControllerImpl) statusBarStateControllerWrapper.statusBarStateController).mState == 1;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isKeyguardState");
        try {
            return ((StatusBarStateControllerImpl) statusBarStateControllerWrapper.statusBarStateController).mState == 1;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isKioskModeEnabled() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SemPersonaManagerWrapper semPersonaManagerWrapper = this.semPersonaManagerWrapper;
        if (!isTagEnabled) {
            return SemPersonaManager.isKioskModeEnabled(semPersonaManagerWrapper.context);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isKioskModeEnabled");
        try {
            return SemPersonaManager.isKioskModeEnabled(semPersonaManagerWrapper.context);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isLcdOff() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        Context context = this.context;
        PowerManagerWrapper powerManagerWrapper = this.powerManagerWrapper;
        if (!isTagEnabled) {
            powerManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            return !((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isLcdOff");
        try {
            powerManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            return true ^ ((PowerManager) context.getSystemService(PowerManager.class)).isInteractive();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isLeBroadcasting() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isLeBroadcasting();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isLeBroadcasting");
        try {
            return volumeDialogController.isLeBroadcasting();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isMediaDefault() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!isTagEnabled) {
            return soundAssistantManagerWrapper.satMananger.getVolumeKeyMode() != 0;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isMediaDefault");
        try {
            return soundAssistantManagerWrapper.satMananger.getVolumeKeyMode() != 0;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isMultiSoundOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!isTagEnabled) {
            return soundAssistantManagerWrapper.satMananger.isMultiSoundOn();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isMultiSoundOn");
        try {
            return soundAssistantManagerWrapper.satMananger.isMultiSoundOn();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isOrientationChanged() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!isTagEnabled) {
            return configurationWrapper.isOrientationChanged();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isOrientationChanged");
        try {
            return configurationWrapper.isOrientationChanged();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSafeMediaVolumeDeviceOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.semIsSafeMediaVolumeDeviceOn();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSafeMediaVolumeDeviceOn");
        try {
            return audioManagerWrapper.f398am.semIsSafeMediaVolumeDeviceOn();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSafeMediaVolumePinDeviceOn() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            AudioManager audioManager = audioManagerWrapper.f398am;
            return audioManager.isSafeMediaVolumeDeviceOn(audioManager.semGetPinDevice());
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSafeMediaVolumePinDeviceOn");
        try {
            AudioManager audioManager2 = audioManagerWrapper.f398am;
            return audioManager2.isSafeMediaVolumeDeviceOn(audioManager2.semGetPinDevice());
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSetupWizardComplete() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        DeviceProvisionedWrapper deviceProvisionedWrapper = this.deviceProvisionedWrapper;
        if (!isTagEnabled) {
            return deviceProvisionedWrapper.isDeviceProvisioned();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSetupWizardComplete");
        try {
            return deviceProvisionedWrapper.isDeviceProvisioned();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isShadeLockedState() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarStateControllerWrapper statusBarStateControllerWrapper = this.statusBarStateControllerWrapper;
        if (!isTagEnabled) {
            return ((StatusBarStateControllerImpl) statusBarStateControllerWrapper.statusBarStateController).mState == 2;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isShadeLockedState");
        try {
            return ((StatusBarStateControllerImpl) statusBarStateControllerWrapper.statusBarStateController).mState == 2;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSmartView() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isSmartViewEnabled();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSmartView");
        try {
            return volumeDialogController.isSmartViewEnabled();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isStandalone() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        DesktopManagerWrapper desktopManagerWrapper = this.desktopManagerWrapper;
        if (!isTagEnabled) {
            return ((DesktopManagerImpl) desktopManagerWrapper.desktopManager).isStandalone();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isStandalone");
        try {
            return ((DesktopManagerImpl) desktopManagerWrapper.desktopManager).isStandalone();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSupportTvVolumeSync() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        LogWrapper logWrapper = this.logWrapper;
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            boolean supportTvVolumeControl = volumeDialogController.supportTvVolumeControl();
            displayManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            boolean z = SystemServiceExtension.getDisplayManager(displayManagerWrapper.context).semGetScreenSharingStatus() != 7;
            boolean z2 = supportTvVolumeControl && z;
            boolean isDLNAEnabled = volumeDialogController.isDLNAEnabled();
            boolean isValidPlayerType = displayManagerWrapper.isValidPlayerType();
            boolean z3 = isDLNAEnabled && isValidPlayerType;
            StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("supportTvVolumeControl=", supportTvVolumeControl, ", screenSharing=", z, ", supportTvVolumeControl=");
            KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, supportTvVolumeControl, ", dlnaEnabled=", isDLNAEnabled, ", validPlayerType=");
            m69m.append(isValidPlayerType);
            logWrapper.m98d("VolumeInfraMediatorImpl", m69m.toString());
            return z2 || z3;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSupportTvVolumeSync");
        try {
            boolean supportTvVolumeControl2 = volumeDialogController.supportTvVolumeControl();
            displayManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            boolean z4 = SystemServiceExtension.getDisplayManager(displayManagerWrapper.context).semGetScreenSharingStatus() != 7;
            boolean z5 = supportTvVolumeControl2 && z4;
            boolean isDLNAEnabled2 = volumeDialogController.isDLNAEnabled();
            boolean isValidPlayerType2 = displayManagerWrapper.isValidPlayerType();
            boolean z6 = isDLNAEnabled2 && isValidPlayerType2;
            logWrapper.m98d("VolumeInfraMediatorImpl", "supportTvVolumeControl=" + supportTvVolumeControl2 + ", screenSharing=" + z4 + ", supportTvVolumeControl=" + supportTvVolumeControl2 + ", dlnaEnabled=" + isDLNAEnabled2 + ", validPlayerType=" + isValidPlayerType2);
            return z5 || z6;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isUserInCall() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            int modeInternal = audioManagerWrapper.f398am.getModeInternal();
            return modeInternal == 3 || modeInternal == 2;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isUserInCall");
        try {
            int modeInternal2 = audioManagerWrapper.f398am.getModeInternal();
            return modeInternal2 == 3 || modeInternal2 == 2;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVoiceCapable() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            return audioManagerWrapper.f398am.shouldShowRingtoneVolume();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isVoiceCapable");
        try {
            return audioManagerWrapper.f398am.shouldShowRingtoneVolume();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVolumeStarEnabled() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            return volumeDialogController.isVolumeStarEnabled();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isVolumeStarEnabled");
        try {
            return volumeDialogController.isVolumeStarEnabled();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModeEnabled(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!isTagEnabled) {
            zenModeHelper.getClass();
            return Settings.Global.isValidZenMode(i) && i != 0;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isZenModeEnabled");
        try {
            zenModeHelper.getClass();
            return Settings.Global.isValidZenMode(i) && i != 0;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModeNone(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!isTagEnabled) {
            zenModeHelper.getClass();
            return i == 2;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isZenModeNone");
        try {
            zenModeHelper.getClass();
            return i == 2;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModePriorityOnly(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!isTagEnabled) {
            zenModeHelper.getClass();
            return i == 1;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isZenModePriorityOnly");
        try {
            zenModeHelper.getClass();
            return i == 1;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void notifyVisible(boolean z) {
        this.volumeController.notifyVisible(z);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void playSound() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        final SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (!isTagEnabled) {
            soundPoolWrapper.getClass();
            soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    char c;
                    SoundPoolWrapper soundPoolWrapper2 = SoundPoolWrapper.this;
                    SoundPool soundPool = soundPoolWrapper2.soundPool;
                    boolean z = false;
                    if (soundPool != null) {
                        if (BasicRune.SUPPORT_SOUND_THEME) {
                            int currentUser = ActivityManager.getCurrentUser();
                            Context context = soundPoolWrapper2.context;
                            String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
                            if (stringForUser == null) {
                                stringForUser = "";
                            }
                            if (Intrinsics.areEqual(stringForUser, "Open_theme")) {
                                String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
                                stringForUser = stringForUser2 != null ? stringForUser2 : "";
                            }
                            int hashCode = stringForUser.hashCode();
                            if (hashCode == 71007) {
                                if (stringForUser.equals("Fun")) {
                                    c = 2;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            } else if (hashCode != 2092671) {
                                if (hashCode == 78852734 && stringForUser.equals("Retro")) {
                                    c = 3;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            } else {
                                if (stringForUser.equals("Calm")) {
                                    c = 1;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            }
                        } else {
                            i = soundPoolWrapper2.soundID;
                        }
                        if (soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                            z = true;
                        }
                    }
                    if (z) {
                        SoundPoolWrapper soundPoolWrapper3 = SoundPoolWrapper.this;
                        SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                        if (soundPool2 != null) {
                            soundPool2.release();
                        }
                        soundPoolWrapper3.soundPool = null;
                    }
                }
            });
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_playSound");
        try {
            soundPoolWrapper.getClass();
            soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    char c;
                    SoundPoolWrapper soundPoolWrapper2 = SoundPoolWrapper.this;
                    SoundPool soundPool = soundPoolWrapper2.soundPool;
                    boolean z = false;
                    if (soundPool != null) {
                        if (BasicRune.SUPPORT_SOUND_THEME) {
                            int currentUser = ActivityManager.getCurrentUser();
                            Context context = soundPoolWrapper2.context;
                            String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "system_sound", currentUser);
                            if (stringForUser == null) {
                                stringForUser = "";
                            }
                            if (Intrinsics.areEqual(stringForUser, "Open_theme")) {
                                String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), "prev_system_sound", currentUser);
                                stringForUser = stringForUser2 != null ? stringForUser2 : "";
                            }
                            int hashCode = stringForUser.hashCode();
                            if (hashCode == 71007) {
                                if (stringForUser.equals("Fun")) {
                                    c = 2;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            } else if (hashCode != 2092671) {
                                if (hashCode == 78852734 && stringForUser.equals("Retro")) {
                                    c = 3;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            } else {
                                if (stringForUser.equals("Calm")) {
                                    c = 1;
                                    i = soundPoolWrapper2.soundIDs[c];
                                }
                                c = 0;
                                i = soundPoolWrapper2.soundIDs[c];
                            }
                        } else {
                            i = soundPoolWrapper2.soundID;
                        }
                        if (soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                            z = true;
                        }
                    }
                    if (z) {
                        SoundPoolWrapper soundPoolWrapper3 = SoundPoolWrapper.this;
                        SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                        if (soundPool2 != null) {
                            soundPool2.release();
                        }
                        soundPoolWrapper3.soundPool = null;
                    }
                }
            });
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void sendEventLog(SALoggingWrapper.Event event) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        SALoggingWrapper sALoggingWrapper = this.saLoggingWrapper;
        if (!isTagEnabled) {
            sALoggingWrapper.getClass();
            SALoggingWrapper.sendEventLog(event);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_sendEventLog");
        try {
            sALoggingWrapper.getClass();
            SALoggingWrapper.sendEventLog(event);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void sendSystemDialogsCloseAction() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        BroadcastSender broadcastSender = this.broadcastSender;
        if (!isTagEnabled) {
            broadcastSender.getClass();
            broadcastSender.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_sendSystemDialogsCloseAction");
        try {
            broadcastSender.getClass();
            broadcastSender.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setActiveStream(int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setActiveStream(i);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setActiveStream");
        try {
            volumeDialogController.setActiveStream(i);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setCaptionEnabled(boolean z) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setCaptionsEnabled(z);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setCaptionEnabled");
        try {
            volumeDialogController.setCaptionsEnabled(z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setRingerMode(int i, boolean z) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setRingerMode(i, z);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setRingerMode");
        try {
            volumeDialogController.setRingerMode(i, z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setSafeMediaVolume() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!isTagEnabled) {
            audioManagerWrapper.f398am.setSafeMediaVolume();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setSafeMediaVolume");
        try {
            audioManagerWrapper.f398am.setSafeMediaVolume();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setSafeVolumeDialogShowing(boolean z) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setSafeVolumeDialogShowing(z);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setSafeVolumeDialogShowing");
        try {
            volumeDialogController.setSafeVolumeDialogShowing(z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setStreamVolume(int i, int i2) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setStreamVolume(i, i2);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setStreamVolume");
        try {
            volumeDialogController.setStreamVolume(i, i2);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setStreamVolumeDualAudio(int i, int i2, String str) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.setStreamVolumeDualAudio(i, i2, str);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setStreamVolumeDualAudio");
        try {
            volumeDialogController.setStreamVolumeDualAudio(i, i2, str);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startHearingEnhancementsActivity() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!isTagEnabled) {
            statusBarWrapper.startHearingEnhancementsActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_startHearingEnhancementsActivity");
        try {
            statusBarWrapper.startHearingEnhancementsActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startLeBroadcastActivity() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!isTagEnabled) {
            statusBarWrapper.startLeBroadcastActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_startLeBroadcastActivity");
        try {
            statusBarWrapper.startLeBroadcastActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startSettingsActivity() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!isTagEnabled) {
            statusBarWrapper.startSettingsActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_startSettingsActivity");
        try {
            statusBarWrapper.startSettingsActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startVolumeSettingsActivity() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!isTagEnabled) {
            sendSystemDialogsCloseAction();
            statusBarWrapper.startVolumeSettingsActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_startVolumeSettingsActivity");
        try {
            sendSystemDialogsCloseAction();
            statusBarWrapper.startVolumeSettingsActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void toggleWifiDisplayMute() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        if (!isTagEnabled) {
            displayManagerWrapper.toggleWifiDisplayMute();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_toggleWifiDisplayMute");
        try {
            displayManagerWrapper.toggleWifiDisplayMute();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void userActivity() {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!isTagEnabled) {
            volumeDialogController.userActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_userActivity");
        try {
            volumeDialogController.userActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void playSound(final int i) {
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        final SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (isTagEnabled) {
            Trace.traceBegin(4096L, "#vol.infraMediator_playSound");
            try {
                soundPoolWrapper.getClass();
                soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoundPoolWrapper soundPoolWrapper2 = SoundPoolWrapper.this;
                        SoundPool soundPool = soundPoolWrapper2.soundPool;
                        if (soundPool != null && soundPool.play(soundPoolWrapper2.soundIDs[i], 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                            SoundPoolWrapper soundPoolWrapper3 = SoundPoolWrapper.this;
                            SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                            if (soundPool2 != null) {
                                soundPool2.release();
                            }
                            soundPoolWrapper3.soundPool = null;
                        }
                    }
                });
                Unit unit = Unit.INSTANCE;
                return;
            } finally {
                Trace.traceEnd(4096L);
            }
        }
        soundPoolWrapper.getClass();
        soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$2
            @Override // java.lang.Runnable
            public final void run() {
                SoundPoolWrapper soundPoolWrapper2 = SoundPoolWrapper.this;
                SoundPool soundPool = soundPoolWrapper2.soundPool;
                if (soundPool != null && soundPool.play(soundPoolWrapper2.soundIDs[i], 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                    SoundPoolWrapper soundPoolWrapper3 = SoundPoolWrapper.this;
                    SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                    if (soundPool2 != null) {
                        soundPool2.release();
                    }
                    soundPoolWrapper3.soundPool = null;
                }
            }
        });
    }
}
