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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.popup.util.PopupUIUtil;
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

/* loaded from: classes3.dex */
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
    private final SettingsHelper settingsHelper;
    public final SoundAssistantManagerWrapper soundAssistantManagerWrapper;
    public final SoundPoolWrapper soundPoolWrapper;
    public final StatusBarStateControllerWrapper statusBarStateControllerWrapper;
    public final StatusBarWrapper statusBarWrapper;
    public final SystemClockWrapper systemClockWrapper;
    public final VolumeDialogController volumeController;
    public final ZenModeHelper zenModeHelper;

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
                iArr[VolumeInfraMediator.Conditions.IS_SMART_VIEW.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_ENABLED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_PRIORITY_ONLY.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ZEN_MODE_NONE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_VOICE_CAPABLE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ALL_SOUND_OFF.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.HAS_VIBRATOR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_MEDIA_DEFAULT.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_KEYGUARD_STATE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SHADE_LOCKED_STATE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_ORIENTATION_CHANGED.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DENSITY_OR_FONT_CHANGED.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_STANDALONE.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DISPLAY_TYPE_CHANGED.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_LCD_OFF.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_DEX_MODE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_KIOSK_MODE_ENABLED.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_BUDS_TOGETHER_ENABLED.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_SETUP_WIZARD_COMPLETE.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_MULTI_SOUND_ON.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_SMART_VIEW_STREAM.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_WARNING_POPUP_WALLET_MINI.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_WARNING_POPUP_SIDE_VIEW.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_BUDS_TOGETHER.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.VOLUME_DUAL_AUDIO.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[VolumeInfraMediator.Conditions.IS_AOD_VOLUME_PANEL.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumeInfraMediator.Values.values().length];
            try {
                iArr2[VolumeInfraMediator.Values.EAR_PROTECT_LIMIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.BT_CALL_DEVICE_NAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.DEVICES_FOR_STREAM_MUSIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_APP_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_DEVICE_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.PIN_DEVICE.ordinal()] = 6;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.SMART_VIEW_DEVICE_NAME.ordinal()] = 7;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.TIMEOUT_CONTROLS.ordinal()] = 8;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.TIMEOUT_CONTROLS_TEXT.ordinal()] = 9;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.CUTOUT_HEIGHT.ordinal()] = 10;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.SYSTEM_TIME.ordinal()] = 11;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.ACTIVE_BT_DEVICE_NAME.ordinal()] = 12;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.AUDIO_CAST_DEVICE_NAME.ordinal()] = 13;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr2[VolumeInfraMediator.Values.MULTI_SOUND_DEVICE.ordinal()] = 14;
            } catch (NoSuchFieldError unused46) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            audioManagerWrapper.am.disableSafeMediaVolume();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_disableSafeMediaVolume");
        try {
            audioManagerWrapper.am.disableSafeMediaVolume();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final Object get(final VolumeInfraMediator.Values values, Object... objArr) {
        final Object objValueOf = 0;
        switch (WhenMappings.$EnumSwitchMapping$1[values.ordinal()]) {
            case 1:
                objValueOf = Integer.valueOf(getEarProtectLimit());
                break;
            case 2:
                objValueOf = getBtCallDeviceName();
                break;
            case 3:
                objValueOf = Integer.valueOf(getDevicesForStreamMusic());
                break;
            case 4:
                objValueOf = getPinAppName(((Integer) objArr[0]).intValue());
                break;
            case 5:
                objValueOf = getPinDeviceName(((Integer) objArr[0]).intValue());
                break;
            case 6:
                objValueOf = Integer.valueOf(getPinDevice());
                break;
            case 7:
                objValueOf = getSmartViewDeviceName();
                break;
            case 8:
                objValueOf = Integer.valueOf(getTimeoutControls());
                break;
            case 9:
                objValueOf = Integer.valueOf(getTimeoutControlsText());
                break;
            case 10:
                getCutoutHeight();
                break;
            case 11:
                objValueOf = Long.valueOf(getSystemTime());
                break;
            case 12:
                objValueOf = getActiveBtDeviceName();
                break;
            case 13:
                objValueOf = getAudioCastDeviceName();
                break;
            case 14:
                objValueOf = Integer.valueOf(getMultiSoundDevice());
                break;
        }
        if (objValueOf != null && VolumeInfraMediator.Values.SYSTEM_TIME != values) {
            this.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.VolumeInfraMediatorImpl.get.1
                @Override // java.lang.Runnable
                public final void run() {
                    VolumeInfraMediatorImpl.this.logWrapper.p(values + "=" + objValueOf);
                }
            });
        }
        return objValueOf;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getActiveBtDeviceName() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.bluetoothAdapterWrapper;
        BluetoothAudioCastWrapper bluetoothAudioCastWrapper = this.bluetoothAudioCastWrapper;
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
            return volumeDialogController.isMusicShareEnabled() ? bluetoothAudioCastWrapper.getCastDeviceConnectedName() : bluetoothAdapterWrapper.getActiveBTDeviceName();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getActiveBtDeviceName");
        try {
            String castDeviceConnectedName = volumeDialogController.isMusicShareEnabled() ? bluetoothAudioCastWrapper.getCastDeviceConnectedName() : bluetoothAdapterWrapper.getActiveBTDeviceName();
            Trace.traceEnd(4096L);
            return castDeviceConnectedName;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getAudioCastDeviceName() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAudioCastWrapper bluetoothAudioCastWrapper = this.bluetoothAudioCastWrapper;
        if (!zIsTagEnabled) {
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
    public final String getBtCallDeviceName() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.bluetoothAdapterWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
            statusBarWrapper.getClass();
            return 0;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getCutoutHeight");
        try {
            statusBarWrapper.getClass();
            return 0;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getDevicesForStreamMusic() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.getDevicesForStream(3);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getDevicesForStreamMusic");
        try {
            return audioManagerWrapper.am.getDevicesForStream(3);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getEarProtectLimit() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.getFineVolume(3, 0);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getMusicFineVolume");
        try {
            return audioManagerWrapper.am.getFineVolume(3, 0);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinAppName(int i) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.getPinAppName(i);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinAppName");
        try {
            return audioManagerWrapper.am.getPinAppName(i);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final int getPinDevice() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.semGetPinDevice();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinDevice");
        try {
            return audioManagerWrapper.am.semGetPinDevice();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getPinDeviceName(int i) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.getPinDeviceName(i);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_getPinDeviceName");
        try {
            return audioManagerWrapper.am.getPinDeviceName(i);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final String getSmartViewDeviceName() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SystemClockWrapper systemClockWrapper = this.systemClockWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AccessibilityManagerWrapper accessibilityManagerWrapper = this.accessibilityManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AccessibilityManagerWrapper accessibilityManagerWrapper = this.accessibilityManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (!zIsTagEnabled) {
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
        if (!Trace.isTagEnabled(4096L)) {
            return this.settingsHelper.isAllSoundOff();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isAllSoundOff");
        try {
            return this.settingsHelper.isAllSoundOff();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isAodVolumePanel() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        BixbyServiceManager bixbyServiceManager = this.bixbyServiceManager;
        if (!zIsTagEnabled) {
            return bixbyServiceManager.isBixbyServiceForeground();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBixbyServiceForeground");
        try {
            return bixbyServiceManager.isBixbyServiceForeground();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isBleCallDeviceOn() {
        boolean z;
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            AudioDeviceInfo communicationDevice = audioManagerWrapper.am.getCommunicationDevice();
            return communicationDevice != null && communicationDevice.getType() == 26;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBleCallDeviceOn");
        try {
            AudioDeviceInfo communicationDevice2 = audioManagerWrapper.am.getCommunicationDevice();
            if (communicationDevice2 != null) {
                z = communicationDevice2.getType() == 26;
            }
            return z;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBluetoothScoOn() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.isBluetoothScoOn();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isBluetoothScoOn");
        try {
            return audioManagerWrapper.am.isBluetoothScoOn();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isBudsTogetherEnabled() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        DesktopManagerWrapper desktopManagerWrapper = this.desktopManagerWrapper;
        if (!zIsTagEnabled) {
            desktopManagerWrapper.getClass();
            return false;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isDexMode");
        try {
            desktopManagerWrapper.getClass();
            return false;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isDisplayTypeChanged() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!zIsTagEnabled) {
            return configurationWrapper.isDisplayTypeChanged();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isDisplayTypeChanged");
        try {
            return configurationWrapper.isDisplayTypeChanged();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isEnabled(final VolumeInfraMediator.Conditions conditions, Object... objArr) {
        final boolean zIsSafeMediaVolumeDeviceOn = true;
        switch (WhenMappings.$EnumSwitchMapping$0[conditions.ordinal()]) {
            case 1:
                zIsSafeMediaVolumeDeviceOn = isSafeMediaVolumeDeviceOn();
                break;
            case 2:
                zIsSafeMediaVolumeDeviceOn = isSafeMediaVolumePinDeviceOn();
                break;
            case 3:
                zIsSafeMediaVolumeDeviceOn = isUserInCall();
                break;
            case 4:
                zIsSafeMediaVolumeDeviceOn = isBluetoothScoOn();
                break;
            case 5:
                zIsSafeMediaVolumeDeviceOn = isBleCallDeviceOn();
                break;
            case 6:
                zIsSafeMediaVolumeDeviceOn = isBixbyServiceForeground();
                break;
            case 7:
                zIsSafeMediaVolumeDeviceOn = isSmartView();
                break;
            case 8:
                zIsSafeMediaVolumeDeviceOn = isZenModeEnabled(((Integer) objArr[0]).intValue());
                break;
            case 9:
                zIsSafeMediaVolumeDeviceOn = isZenModePriorityOnly(((Integer) objArr[0]).intValue());
                break;
            case 10:
                zIsSafeMediaVolumeDeviceOn = isZenModeNone(((Integer) objArr[0]).intValue());
                break;
            case 11:
                zIsSafeMediaVolumeDeviceOn = isVoiceCapable();
                break;
            case 12:
                zIsSafeMediaVolumeDeviceOn = isAllSoundOff();
                break;
            case 13:
                zIsSafeMediaVolumeDeviceOn = isHasVibrator();
                break;
            case 14:
                zIsSafeMediaVolumeDeviceOn = isMediaDefault();
                break;
            case 15:
                zIsSafeMediaVolumeDeviceOn = isKeyguardState();
                break;
            case 16:
                zIsSafeMediaVolumeDeviceOn = isShadeLockedState();
                break;
            case 17:
                zIsSafeMediaVolumeDeviceOn = isOrientationChanged();
                break;
            case 18:
                zIsSafeMediaVolumeDeviceOn = isDensityOrFontChanged();
                break;
            case 19:
                isStandalone();
                zIsSafeMediaVolumeDeviceOn = false;
                break;
            case 20:
                zIsSafeMediaVolumeDeviceOn = isDisplayTypeChanged();
                break;
            case 21:
                zIsSafeMediaVolumeDeviceOn = isLcdOff();
                break;
            case 22:
                isDexMode();
                zIsSafeMediaVolumeDeviceOn = false;
                break;
            case 23:
                zIsSafeMediaVolumeDeviceOn = isKioskModeEnabled();
                break;
            case 24:
                zIsSafeMediaVolumeDeviceOn = isBudsTogetherEnabled();
                break;
            case 25:
                zIsSafeMediaVolumeDeviceOn = isSetupWizardComplete();
                break;
            case 26:
                zIsSafeMediaVolumeDeviceOn = isMultiSoundOn();
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                break;
            case 32:
                zIsSafeMediaVolumeDeviceOn = isAodVolumePanel();
                break;
            default:
                zIsSafeMediaVolumeDeviceOn = false;
                break;
        }
        this.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.VolumeInfraMediatorImpl.isEnabled.1
            @Override // java.lang.Runnable
            public final void run() {
                VolumeInfraMediatorImpl.this.logWrapper.p(conditions + "=" + zIsSafeMediaVolumeDeviceOn);
            }
        });
        return zIsSafeMediaVolumeDeviceOn;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isHasVibrator() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarStateControllerWrapper statusBarStateControllerWrapper = this.statusBarStateControllerWrapper;
        if (!zIsTagEnabled) {
            return statusBarStateControllerWrapper.statusBarStateController.getState() == 1;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isKeyguardState");
        try {
            return statusBarStateControllerWrapper.statusBarStateController.getState() == 1;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isKioskModeEnabled() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SemPersonaManagerWrapper semPersonaManagerWrapper = this.semPersonaManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        PowerManagerWrapper powerManagerWrapper = this.powerManagerWrapper;
        if (!zIsTagEnabled) {
            Context context = this.context;
            powerManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            Object systemService = context.getSystemService((Class<Object>) PowerManager.class);
            systemService.getClass();
            return !((PowerManager) systemService).isInteractive();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isLcdOff");
        try {
            Context context2 = this.context;
            powerManagerWrapper.getClass();
            SystemServiceExtension.INSTANCE.getClass();
            context2.getSystemService((Class<Object>) PowerManager.class).getClass();
            return !((PowerManager) r5).isInteractive();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isLeBroadcasting() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!zIsTagEnabled) {
            return soundAssistantManagerWrapper.satMananger.getVolumeMode(1);
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isMediaDefault");
        try {
            return soundAssistantManagerWrapper.satMananger.getVolumeMode(1);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isMultiSoundOn() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.soundAssistantManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        ConfigurationWrapper configurationWrapper = this.configurationWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.semIsSafeMediaVolumeDeviceOn();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSafeMediaVolumeDeviceOn");
        try {
            return audioManagerWrapper.am.semIsSafeMediaVolumeDeviceOn();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSafeMediaVolumePinDeviceOn() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            AudioManager audioManager = audioManagerWrapper.am;
            return audioManager.isSafeMediaVolumeDeviceOn(audioManager.semGetPinDevice());
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSafeMediaVolumePinDeviceOn");
        try {
            AudioManager audioManager2 = audioManagerWrapper.am;
            return audioManager2.isSafeMediaVolumeDeviceOn(audioManager2.semGetPinDevice());
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSetupWizardComplete() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        DeviceProvisionedWrapper deviceProvisionedWrapper = this.deviceProvisionedWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarStateControllerWrapper statusBarStateControllerWrapper = this.statusBarStateControllerWrapper;
        if (!zIsTagEnabled) {
            return statusBarStateControllerWrapper.statusBarStateController.getState() == 2;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isShadeLockedState");
        try {
            return statusBarStateControllerWrapper.statusBarStateController.getState() == 2;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSmartView() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        DesktopManagerWrapper desktopManagerWrapper = this.desktopManagerWrapper;
        if (!zIsTagEnabled) {
            desktopManagerWrapper.getClass();
            return false;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isStandalone");
        try {
            desktopManagerWrapper.getClass();
            return false;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isSupportTvVolumeSync() throws Throwable {
        long j;
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        LogWrapper logWrapper = this.logWrapper;
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
            boolean zSupportTvVolumeControl = volumeDialogController.supportTvVolumeControl();
            displayManagerWrapper.getClass();
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            boolean z = SystemServiceExtension.getDisplayManager(context).semGetScreenSharingStatus() != 7;
            boolean z2 = zSupportTvVolumeControl && z;
            boolean zIsDLNAEnabled = volumeDialogController.isDLNAEnabled();
            boolean zIsValidPlayerType = displayManagerWrapper.isValidPlayerType();
            boolean z3 = zIsDLNAEnabled && zIsValidPlayerType;
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("supportTvVolumeControl=", ", screenSharing=", ", supportTvVolumeControl=", zSupportTvVolumeControl, z);
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zSupportTvVolumeControl, ", dlnaEnabled=", zIsDLNAEnabled, ", validPlayerType=");
            sbM.append(zIsValidPlayerType);
            logWrapper.d("VolumeInfraMediatorImpl", sbM.toString());
            return z2 || z3;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isSupportTvVolumeSync");
        try {
            boolean zSupportTvVolumeControl2 = volumeDialogController.supportTvVolumeControl();
            displayManagerWrapper.getClass();
            SystemServiceExtension systemServiceExtension2 = SystemServiceExtension.INSTANCE;
            j = 4096;
            try {
                Context context2 = displayManagerWrapper.context;
                systemServiceExtension2.getClass();
                boolean z4 = SystemServiceExtension.getDisplayManager(context2).semGetScreenSharingStatus() != 7;
                boolean z5 = zSupportTvVolumeControl2 && z4;
                boolean zIsDLNAEnabled2 = volumeDialogController.isDLNAEnabled();
                boolean zIsValidPlayerType2 = displayManagerWrapper.isValidPlayerType();
                boolean z6 = zIsDLNAEnabled2 && zIsValidPlayerType2;
                logWrapper.d("VolumeInfraMediatorImpl", "supportTvVolumeControl=" + zSupportTvVolumeControl2 + ", screenSharing=" + z4 + ", supportTvVolumeControl=" + zSupportTvVolumeControl2 + ", dlnaEnabled=" + zIsDLNAEnabled2 + ", validPlayerType=" + zIsValidPlayerType2);
                boolean z7 = z5 || z6;
                Trace.traceEnd(4096L);
                return z7;
            } catch (Throwable th) {
                th = th;
                Trace.traceEnd(j);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            j = 4096;
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isUserInCall() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        boolean z = true;
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            int modeInternal = audioManagerWrapper.am.getModeInternal();
            return modeInternal == 3 || modeInternal == 2;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isUserInCall");
        try {
            int modeInternal2 = audioManagerWrapper.am.getModeInternal();
            if (modeInternal2 != 3 && modeInternal2 != 2) {
                z = false;
            }
            return z;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVoiceCapable() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            return audioManagerWrapper.am.shouldShowRingtoneVolume();
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isVoiceCapable");
        try {
            return audioManagerWrapper.am.shouldShowRingtoneVolume();
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isVolumeStarEnabled() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        boolean z = false;
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!zIsTagEnabled) {
            zenModeHelper.getClass();
            return Settings.Global.isValidZenMode(i) && i != 0;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_isZenModeEnabled");
        try {
            zenModeHelper.getClass();
            if (Settings.Global.isValidZenMode(i) && i != 0) {
                z = true;
            }
            return z;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final boolean isZenModeNone(int i) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        ZenModeHelper zenModeHelper = this.zenModeHelper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        final SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (!zIsTagEnabled) {
            soundPoolWrapper.getClass();
            soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$1
                /* JADX WARN: Removed duplicated region for block: B:33:0x006e  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    int i;
                    char c;
                    SoundPoolWrapper soundPoolWrapper2 = soundPoolWrapper;
                    SoundPool soundPool = soundPoolWrapper2.soundPool;
                    if (soundPool != null) {
                        if (BasicRune.SUPPORT_SOUND_THEME) {
                            int[] iArr = soundPoolWrapper2.soundIDs;
                            int currentUser = ActivityManager.getCurrentUser();
                            String stringForUser = Settings.System.getStringForUser(soundPoolWrapper2.context.getContentResolver(), "system_sound", currentUser);
                            if (stringForUser == null) {
                                stringForUser = "";
                            }
                            if (stringForUser.equals("Open_theme")) {
                                String stringForUser2 = Settings.System.getStringForUser(soundPoolWrapper2.context.getContentResolver(), "prev_system_sound", currentUser);
                                stringForUser = stringForUser2 != null ? stringForUser2 : "";
                            }
                            int iHashCode = stringForUser.hashCode();
                            if (iHashCode == 71007) {
                                if (stringForUser.equals("Fun")) {
                                    c = 2;
                                }
                                i = iArr[c];
                            } else if (iHashCode != 2092671) {
                                c = (iHashCode == 78852734 && stringForUser.equals("Retro")) ? (char) 3 : (char) 0;
                                i = iArr[c];
                            } else {
                                if (stringForUser.equals("Calm")) {
                                    c = 1;
                                }
                                i = iArr[c];
                            }
                        } else {
                            i = soundPoolWrapper2.soundID;
                        }
                        if (soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                            SoundPoolWrapper soundPoolWrapper3 = soundPoolWrapper;
                            SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                            if (soundPool2 != null) {
                                soundPool2.release();
                            }
                            soundPoolWrapper3.soundPool = null;
                        }
                    }
                }
            });
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_playSound");
        try {
            soundPoolWrapper.getClass();
            soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$1
                /* JADX WARN: Removed duplicated region for block: B:33:0x006e  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    int i;
                    char c;
                    SoundPoolWrapper soundPoolWrapper2 = soundPoolWrapper;
                    SoundPool soundPool = soundPoolWrapper2.soundPool;
                    if (soundPool != null) {
                        if (BasicRune.SUPPORT_SOUND_THEME) {
                            int[] iArr = soundPoolWrapper2.soundIDs;
                            int currentUser = ActivityManager.getCurrentUser();
                            String stringForUser = Settings.System.getStringForUser(soundPoolWrapper2.context.getContentResolver(), "system_sound", currentUser);
                            if (stringForUser == null) {
                                stringForUser = "";
                            }
                            if (stringForUser.equals("Open_theme")) {
                                String stringForUser2 = Settings.System.getStringForUser(soundPoolWrapper2.context.getContentResolver(), "prev_system_sound", currentUser);
                                stringForUser = stringForUser2 != null ? stringForUser2 : "";
                            }
                            int iHashCode = stringForUser.hashCode();
                            if (iHashCode == 71007) {
                                if (stringForUser.equals("Fun")) {
                                    c = 2;
                                }
                                i = iArr[c];
                            } else if (iHashCode != 2092671) {
                                c = (iHashCode == 78852734 && stringForUser.equals("Retro")) ? (char) 3 : (char) 0;
                                i = iArr[c];
                            } else {
                                if (stringForUser.equals("Calm")) {
                                    c = 1;
                                }
                                i = iArr[c];
                            }
                        } else {
                            i = soundPoolWrapper2.soundID;
                        }
                        if (soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f) == 0) {
                            SoundPoolWrapper soundPoolWrapper3 = soundPoolWrapper;
                            SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                            if (soundPool2 != null) {
                                soundPool2.release();
                            }
                            soundPoolWrapper3.soundPool = null;
                        }
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        SALoggingWrapper sALoggingWrapper = this.saLoggingWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        BroadcastSender broadcastSender = this.broadcastSender;
        if (!zIsTagEnabled) {
            broadcastSender.context.sendBroadcast(new Intent(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS));
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_sendSystemDialogsCloseAction");
        try {
            broadcastSender.context.sendBroadcast(new Intent(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS));
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setActiveStream(int i) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
            volumeDialogController.setCaptionsEnabledState(z);
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setCaptionEnabled");
        try {
            volumeDialogController.setCaptionsEnabledState(z);
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setRingerMode(int i, boolean z) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        AudioManagerWrapper audioManagerWrapper = this.audioManagerWrapper;
        if (!zIsTagEnabled) {
            audioManagerWrapper.am.setSafeMediaVolume();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_setSafeMediaVolume");
        try {
            audioManagerWrapper.am.setSafeMediaVolume();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void setSafeVolumeDialogShowing(boolean z) {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
    public final void startDoNotDisturbActivity() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
            statusBarWrapper.startDoNotDisturbActivity();
            return;
        }
        Trace.traceBegin(4096L, "#vol.infraMediator_startDoNotDisturbActivity");
        try {
            statusBarWrapper.startDoNotDisturbActivity();
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeInfraMediator
    public final void startHearingEnhancementsActivity() {
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        StatusBarWrapper statusBarWrapper = this.statusBarWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        DisplayManagerWrapper displayManagerWrapper = this.displayManagerWrapper;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumeDialogController volumeDialogController = this.volumeController;
        if (!zIsTagEnabled) {
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
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        final SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        if (zIsTagEnabled) {
            Trace.traceBegin(4096L, "#vol.infraMediator_playSound");
            try {
                soundPoolWrapper.getClass();
                soundPoolWrapper.handlerWrapper.postInBgThread(new Runnable() { // from class: com.android.systemui.volume.util.SoundPoolWrapper$playSound$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SoundPoolWrapper soundPoolWrapper2 = soundPoolWrapper;
                        SoundPool soundPool = soundPoolWrapper2.soundPool;
                        if (soundPool == null || soundPool.play(soundPoolWrapper2.soundIDs[i], 1.0f, 1.0f, 0, 0, 1.0f) != 0) {
                            return;
                        }
                        SoundPoolWrapper soundPoolWrapper3 = soundPoolWrapper;
                        SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                        if (soundPool2 != null) {
                            soundPool2.release();
                        }
                        soundPoolWrapper3.soundPool = null;
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
                SoundPoolWrapper soundPoolWrapper2 = soundPoolWrapper;
                SoundPool soundPool = soundPoolWrapper2.soundPool;
                if (soundPool == null || soundPool.play(soundPoolWrapper2.soundIDs[i], 1.0f, 1.0f, 0, 0, 1.0f) != 0) {
                    return;
                }
                SoundPoolWrapper soundPoolWrapper3 = soundPoolWrapper;
                SoundPool soundPool2 = soundPoolWrapper3.soundPool;
                if (soundPool2 != null) {
                    soundPool2.release();
                }
                soundPoolWrapper3.soundPool = null;
            }
        });
    }
}
