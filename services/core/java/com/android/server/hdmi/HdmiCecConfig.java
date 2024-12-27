package com.android.server.hdmi;

import android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class HdmiCecConfig {
    public final Context mContext;
    public final Object mLock;
    public final ArrayMap mSettingChangeListeners;
    public final LinkedHashMap mSettings;
    public final StorageAdapter mStorageAdapter;

    public final class Setting {
        public final Context mContext;
        public final String mName;
        public final boolean mUserConfigurable;
        public final /* synthetic */ HdmiCecConfig this$0;
        public Value mDefaultValue = null;
        public final List mAllowedValues = new ArrayList();

        public Setting(HdmiCecConfig hdmiCecConfig, Context context, String str, int i) {
            this.mContext = context;
            this.mName = str;
            this.mUserConfigurable = context.getResources().getBoolean(i);
        }

        public final Value getDefaultValue() {
            Value value = this.mDefaultValue;
            if (value != null) {
                return value;
            }
            throw new VerificationException(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Invalid CEC setup for '"),
                            this.mName,
                            "' setting. Setting has no default value."));
        }

        public final String getValueType() {
            return getDefaultValue().mStringValue != null ? "string" : "int";
        }

        public final void registerValue(int i, int i2, int i3) {
            registerValue(new Value(Integer.valueOf(i)), i2, i3);
        }

        public final void registerValue(int i, int i2, String str) {
            registerValue(new Value(str), i, i2);
        }

        public final void registerValue(Value value, int i, int i2) {
            if (this.mContext.getResources().getBoolean(i)) {
                ((ArrayList) this.mAllowedValues).add(value);
                if (this.mContext.getResources().getBoolean(i2)) {
                    if (this.mDefaultValue == null) {
                        this.mDefaultValue = value;
                        return;
                    }
                    Slog.e(
                            "HdmiCecConfig",
                            "Failed to set '"
                                    + value
                                    + "' as a default for '"
                                    + this.mName
                                    + "': Setting already has a default ('"
                                    + this.mDefaultValue
                                    + "').");
                }
            }
        }
    }

    public final class StorageAdapter {
        public final SharedPreferences mSharedPrefs;

        public StorageAdapter(Context context) {
            this.mSharedPrefs =
                    context.createDeviceProtectedStorageContext()
                            .getSharedPreferences(
                                    new File(
                                            new File(
                                                    Environment.getDataSystemDirectory(),
                                                    "shared_prefs"),
                                            "cec_config.xml"),
                                    0);
        }
    }

    public final class Value {
        public final Integer mIntValue;
        public final String mStringValue;

        public Value(Integer num) {
            this.mStringValue = null;
            this.mIntValue = num;
        }

        public Value(String str) {
            this.mStringValue = str;
            this.mIntValue = null;
        }
    }

    public class VerificationException extends RuntimeException {
        public VerificationException(String str) {
            super(str);
        }
    }

    public HdmiCecConfig(Context context) {
        this(context, new StorageAdapter(context));
    }

    public HdmiCecConfig(Context context, StorageAdapter storageAdapter) {
        this.mLock = new Object();
        this.mSettingChangeListeners = new ArrayMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mSettings = linkedHashMap;
        this.mContext = context;
        this.mStorageAdapter = storageAdapter;
        Setting registerSetting =
                registerSetting(R.bool.config_cecHdmiCecVersion20_default, "hdmi_cec_enabled");
        registerSetting.registerValue(
                1,
                R.bool.config_cecHdmiCecVersion14b_default,
                R.bool.config_cecHdmiCecVersion20_allowed);
        registerSetting.registerValue(
                0,
                R.bool.config_cecHdmiCecEnabled_userConfigurable,
                R.bool.config_cecHdmiCecVersion14b_allowed);
        Setting registerSetting2 =
                registerSetting(R.bool.config_cecPowerControlModeNone_default, "hdmi_cec_version");
        registerSetting2.registerValue(
                5,
                R.bool.config_cecHdmiCecVersion_userConfigurable,
                R.bool.config_cecPowerControlModeBroadcast_allowed);
        registerSetting2.registerValue(
                6,
                R.bool.config_cecPowerControlModeBroadcast_default,
                R.bool.config_cecPowerControlModeNone_allowed);
        Setting registerSetting3 =
                registerSetting(R.bool.config_cecSetMenuLanguageEnabled_default, "routing_control");
        registerSetting3.registerValue(
                1,
                R.bool.config_cecSetMenuLanguageDisabled_default,
                R.bool.config_cecSetMenuLanguageEnabled_allowed);
        registerSetting3.registerValue(
                0,
                R.bool.config_cecRoutingControl_userConfigurable,
                R.bool.config_cecSetMenuLanguageDisabled_allowed);
        Setting registerSetting4 =
                registerSetting(
                        R.bool.config_cecSystemAudioControlEnabled_default, "soundbar_mode");
        registerSetting4.registerValue(
                1,
                R.bool.config_cecSystemAudioControlDisabled_default,
                R.bool.config_cecSystemAudioControlEnabled_allowed);
        registerSetting4.registerValue(
                0,
                R.bool.config_cecSoundbarMode_userConfigurable,
                R.bool.config_cecSystemAudioControlDisabled_allowed);
        Setting registerSetting5 =
                registerSetting(
                        R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_default,
                        "power_control_mode");
        registerSetting5.registerValue(
                R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_default,
                R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_allowed,
                "to_tv");
        registerSetting5.registerValue(
                R.bool.config_cecPowerControlModeTvAndAudioSystem_allowed,
                R.bool.config_cecPowerControlModeTvAndAudioSystem_default,
                INetd.IF_FLAG_BROADCAST);
        registerSetting5.registerValue(
                R.bool.config_cecPowerControlModeTv_allowed,
                R.bool.config_cecPowerControlModeTv_default,
                "none");
        registerSetting5.registerValue(
                R.bool.config_cecPowerControlMode_userConfigurable,
                R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_allowed,
                "to_tv_and_audio_system");
        Setting registerSetting6 =
                registerSetting(
                        R.bool.config_cecQuerySadAacEnabled_default,
                        "power_state_change_on_active_source_lost");
        registerSetting6.registerValue(
                R.bool.config_cecPowerStateChangeOnActiveSourceLost_userConfigurable,
                R.bool.config_cecQuerySadAacDisabled_allowed,
                "none");
        registerSetting6.registerValue(
                R.bool.config_cecQuerySadAacDisabled_default,
                R.bool.config_cecQuerySadAacEnabled_allowed,
                "standby_now");
        Setting registerSetting7 =
                registerSetting(
                        R.bool.config_cecSystemAudioModeMutingEnabled_default,
                        "system_audio_control");
        registerSetting7.registerValue(
                1,
                R.bool.config_cecSystemAudioModeMutingDisabled_default,
                R.bool.config_cecSystemAudioModeMutingEnabled_allowed);
        registerSetting7.registerValue(
                0,
                R.bool.config_cecSystemAudioControl_userConfigurable,
                R.bool.config_cecSystemAudioModeMutingDisabled_allowed);
        Setting registerSetting8 =
                registerSetting(
                        R.bool.config_cecTvSendStandbyOnSleepEnabled_default,
                        "system_audio_mode_muting");
        registerSetting8.registerValue(
                1,
                R.bool.config_cecTvSendStandbyOnSleepDisabled_default,
                R.bool.config_cecTvSendStandbyOnSleepEnabled_allowed);
        registerSetting8.registerValue(
                0,
                R.bool.config_cecSystemAudioModeMuting_userConfigurable,
                R.bool.config_cecTvSendStandbyOnSleepDisabled_allowed);
        Setting registerSetting9 =
                registerSetting(R.bool.config_customBugreport, "volume_control_enabled");
        registerSetting9.registerValue(
                1, R.bool.config_checkWallpaperAtBoot, R.bool.config_closeDialogWhenTouchOutside);
        registerSetting9.registerValue(
                0,
                R.bool.config_cecVolumeControlMode_userConfigurable,
                R.bool.config_cellBroadcastAppLinks);
        Setting registerSetting10 =
                registerSetting(
                        R.bool.config_cecVolumeControlModeEnabled_default,
                        "tv_wake_on_one_touch_play");
        registerSetting10.registerValue(
                1,
                R.bool.config_cecVolumeControlModeDisabled_default,
                R.bool.config_cecVolumeControlModeEnabled_allowed);
        registerSetting10.registerValue(
                0,
                R.bool.config_cecTvWakeOnOneTouchPlay_userConfigurable,
                R.bool.config_cecVolumeControlModeDisabled_allowed);
        Setting registerSetting11 =
                registerSetting(
                        R.bool.config_cecTvWakeOnOneTouchPlayEnabled_default,
                        "tv_send_standby_on_sleep");
        registerSetting11.registerValue(
                1,
                R.bool.config_cecTvWakeOnOneTouchPlayDisabled_default,
                R.bool.config_cecTvWakeOnOneTouchPlayEnabled_allowed);
        registerSetting11.registerValue(
                0,
                R.bool.config_cecTvSendStandbyOnSleep_userConfigurable,
                R.bool.config_cecTvWakeOnOneTouchPlayDisabled_allowed);
        Setting registerSetting12 =
                registerSetting(R.bool.config_cecSoundbarModeEnabled_default, "set_menu_language");
        registerSetting12.registerValue(
                1,
                R.bool.config_cecSoundbarModeDisabled_default,
                R.bool.config_cecSoundbarModeEnabled_allowed);
        registerSetting12.registerValue(
                0,
                R.bool.config_cecSetMenuLanguage_userConfigurable,
                R.bool.config_cecSoundbarModeDisabled_allowed);
        Setting registerSetting13 =
                registerSetting(R.bool.config_cecRoutingControlEnabled_default, "rc_profile_tv");
        registerSetting13.registerValue(
                0,
                R.bool.config_cecRcProfileTvThree_allowed,
                R.bool.config_cecRcProfileTvThree_default);
        registerSetting13.registerValue(
                2,
                R.bool.config_cecRcProfileTvTwo_allowed,
                R.bool.config_cecRcProfileTvTwo_default);
        registerSetting13.registerValue(
                6,
                R.bool.config_cecRoutingControlDisabled_default,
                R.bool.config_cecRoutingControlEnabled_allowed);
        registerSetting13.registerValue(
                10,
                R.bool.config_cecRcProfileTv_userConfigurable,
                R.bool.config_cecRoutingControlDisabled_allowed);
        registerSetting13.registerValue(
                14,
                R.bool.config_cecRcProfileTvOne_allowed,
                R.bool.config_cecRcProfileTvOne_default);
        Setting registerSetting14 =
                registerSetting(
                        R.bool.config_cecRcProfileSourceSetupMenuNotHandled_default,
                        "rc_profile_source_handles_root_menu");
        registerSetting14.registerValue(
                1,
                R.bool.config_cecRcProfileSourceRootMenu_userConfigurable,
                R.bool.config_cecRcProfileSourceSetupMenuHandled_allowed);
        registerSetting14.registerValue(
                0,
                R.bool.config_cecRcProfileSourceSetupMenuHandled_default,
                R.bool.config_cecRcProfileSourceSetupMenuNotHandled_allowed);
        Setting registerSetting15 =
                registerSetting(
                        R.bool.config_cecRcProfileSourceTopMenuNotHandled_default,
                        "rc_profile_source_handles_setup_menu");
        registerSetting15.registerValue(
                1,
                R.bool.config_cecRcProfileSourceSetupMenu_userConfigurable,
                R.bool.config_cecRcProfileSourceTopMenuHandled_allowed);
        registerSetting15.registerValue(
                0,
                R.bool.config_cecRcProfileSourceTopMenuHandled_default,
                R.bool.config_cecRcProfileSourceTopMenuNotHandled_allowed);
        Setting registerSetting16 =
                registerSetting(
                        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_default,
                        "rc_profile_source_handles_contents_menu");
        registerSetting16.registerValue(
                1,
                R.bool.config_cecRcProfileSourceContentsMenu_userConfigurable,
                R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_allowed);
        registerSetting16.registerValue(
                0,
                R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_default,
                R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_allowed);
        Setting registerSetting17 =
                registerSetting(
                        R.bool.config_cecRcProfileTvNone_default,
                        "rc_profile_source_handles_top_menu");
        registerSetting17.registerValue(
                1,
                R.bool.config_cecRcProfileSourceTopMenu_userConfigurable,
                R.bool.config_cecRcProfileTvFour_allowed);
        registerSetting17.registerValue(
                0,
                R.bool.config_cecRcProfileTvFour_default,
                R.bool.config_cecRcProfileTvNone_allowed);
        Setting registerSetting18 =
                registerSetting(
                        R.bool.config_cecRcProfileSourceRootMenuNotHandled_default,
                        "rc_profile_source_handles_media_context_sensitive_menu");
        registerSetting18.registerValue(
                1,
                R.bool.config_cecRcProfileSourceMediaContextSensitiveMenu_userConfigurable,
                R.bool.config_cecRcProfileSourceRootMenuHandled_allowed);
        registerSetting18.registerValue(
                0,
                R.bool.config_cecRcProfileSourceRootMenuHandled_default,
                R.bool.config_cecRcProfileSourceRootMenuNotHandled_allowed);
        Setting registerSetting19 =
                registerSetting(R.bool.config_cecQuerySadMaxEnabled_default, "query_sad_lpcm");
        registerSetting19.registerValue(
                1,
                R.bool.config_cecQuerySadMaxDisabled_default,
                R.bool.config_cecQuerySadMaxEnabled_allowed);
        registerSetting19.registerValue(
                0,
                R.bool.config_cecQuerySadLpcm_userConfigurable,
                R.bool.config_cecQuerySadMaxDisabled_allowed);
        Setting registerSetting20 =
                registerSetting(R.bool.config_cecQuerySadDdpEnabled_default, "query_sad_dd");
        registerSetting20.registerValue(
                1,
                R.bool.config_cecQuerySadDdpDisabled_default,
                R.bool.config_cecQuerySadDdpEnabled_allowed);
        registerSetting20.registerValue(
                0,
                R.bool.config_cecQuerySadDd_userConfigurable,
                R.bool.config_cecQuerySadDdpDisabled_allowed);
        Setting registerSetting21 =
                registerSetting(R.bool.config_cecQuerySadMpeg2Enabled_default, "query_sad_mpeg1");
        registerSetting21.registerValue(
                1,
                R.bool.config_cecQuerySadMpeg2Disabled_default,
                R.bool.config_cecQuerySadMpeg2Enabled_allowed);
        registerSetting21.registerValue(
                0,
                R.bool.config_cecQuerySadMpeg1_userConfigurable,
                R.bool.config_cecQuerySadMpeg2Disabled_allowed);
        Setting registerSetting22 =
                registerSetting(R.bool.config_cecQuerySadMpeg1Enabled_default, "query_sad_mp3");
        registerSetting22.registerValue(
                1,
                R.bool.config_cecQuerySadMpeg1Disabled_default,
                R.bool.config_cecQuerySadMpeg1Enabled_allowed);
        registerSetting22.registerValue(
                0,
                R.bool.config_cecQuerySadMp3_userConfigurable,
                R.bool.config_cecQuerySadMpeg1Disabled_allowed);
        Setting registerSetting23 =
                registerSetting(
                        R.bool.config_cecQuerySadOnebitaudioEnabled_default, "query_sad_mpeg2");
        registerSetting23.registerValue(
                1,
                R.bool.config_cecQuerySadOnebitaudioDisabled_default,
                R.bool.config_cecQuerySadOnebitaudioEnabled_allowed);
        registerSetting23.registerValue(
                0,
                R.bool.config_cecQuerySadMpeg2_userConfigurable,
                R.bool.config_cecQuerySadOnebitaudioDisabled_allowed);
        Setting registerSetting24 =
                registerSetting(R.bool.config_cecQuerySadAtracEnabled_default, "query_sad_aac");
        registerSetting24.registerValue(
                1,
                R.bool.config_cecQuerySadAtracDisabled_default,
                R.bool.config_cecQuerySadAtracEnabled_allowed);
        registerSetting24.registerValue(
                0,
                R.bool.config_cecQuerySadAac_userConfigurable,
                R.bool.config_cecQuerySadAtracDisabled_allowed);
        Setting registerSetting25 =
                registerSetting(R.bool.config_cecQuerySadDtshdEnabled_default, "query_sad_dts");
        registerSetting25.registerValue(
                1,
                R.bool.config_cecQuerySadDtshdDisabled_default,
                R.bool.config_cecQuerySadDtshdEnabled_allowed);
        registerSetting25.registerValue(
                0,
                R.bool.config_cecQuerySadDts_userConfigurable,
                R.bool.config_cecQuerySadDtshdDisabled_allowed);
        Setting registerSetting26 =
                registerSetting(R.bool.config_cecQuerySadDdEnabled_default, "query_sad_atrac");
        registerSetting26.registerValue(
                1,
                R.bool.config_cecQuerySadDdDisabled_default,
                R.bool.config_cecQuerySadDdEnabled_allowed);
        registerSetting26.registerValue(
                0,
                R.bool.config_cecQuerySadAtrac_userConfigurable,
                R.bool.config_cecQuerySadDdDisabled_allowed);
        Setting registerSetting27 =
                registerSetting(
                        R.bool.config_cecQuerySadTruehdEnabled_default, "query_sad_onebitaudio");
        registerSetting27.registerValue(
                1,
                R.bool.config_cecQuerySadTruehdDisabled_default,
                R.bool.config_cecQuerySadTruehdEnabled_allowed);
        registerSetting27.registerValue(
                0,
                R.bool.config_cecQuerySadOnebitaudio_userConfigurable,
                R.bool.config_cecQuerySadTruehdDisabled_allowed);
        Setting registerSetting28 =
                registerSetting(R.bool.config_cecQuerySadDstEnabled_default, "query_sad_ddp");
        registerSetting28.registerValue(
                1,
                R.bool.config_cecQuerySadDstDisabled_default,
                R.bool.config_cecQuerySadDstEnabled_allowed);
        registerSetting28.registerValue(
                0,
                R.bool.config_cecQuerySadDdp_userConfigurable,
                R.bool.config_cecQuerySadDstDisabled_allowed);
        Setting registerSetting29 =
                registerSetting(R.bool.config_cecQuerySadLpcmEnabled_default, "query_sad_dtshd");
        registerSetting29.registerValue(
                1,
                R.bool.config_cecQuerySadLpcmDisabled_default,
                R.bool.config_cecQuerySadLpcmEnabled_allowed);
        registerSetting29.registerValue(
                0,
                R.bool.config_cecQuerySadDtshd_userConfigurable,
                R.bool.config_cecQuerySadLpcmDisabled_allowed);
        Setting registerSetting30 =
                registerSetting(R.bool.config_cecQuerySadWmaproEnabled_default, "query_sad_truehd");
        registerSetting30.registerValue(
                1,
                R.bool.config_cecQuerySadWmaproDisabled_default,
                R.bool.config_cecQuerySadWmaproEnabled_allowed);
        registerSetting30.registerValue(
                0,
                R.bool.config_cecQuerySadTruehd_userConfigurable,
                R.bool.config_cecQuerySadWmaproDisabled_allowed);
        Setting registerSetting31 =
                registerSetting(R.bool.config_cecQuerySadDtsEnabled_default, "query_sad_dst");
        registerSetting31.registerValue(
                1,
                R.bool.config_cecQuerySadDtsDisabled_default,
                R.bool.config_cecQuerySadDtsEnabled_allowed);
        registerSetting31.registerValue(
                0,
                R.bool.config_cecQuerySadDst_userConfigurable,
                R.bool.config_cecQuerySadDtsDisabled_allowed);
        Setting registerSetting32 =
                registerSetting(
                        R.bool.config_cecRcProfileSourceContentsMenuNotHandled_default,
                        "query_sad_wmapro");
        registerSetting32.registerValue(
                1,
                R.bool.config_cecRcProfileSourceContentsMenuHandled_default,
                R.bool.config_cecRcProfileSourceContentsMenuNotHandled_allowed);
        registerSetting32.registerValue(
                0,
                R.bool.config_cecQuerySadWmapro_userConfigurable,
                R.bool.config_cecRcProfileSourceContentsMenuHandled_allowed);
        Setting registerSetting33 =
                registerSetting(R.bool.config_cecQuerySadMp3Enabled_default, "query_sad_max");
        registerSetting33.registerValue(
                1,
                R.bool.config_cecQuerySadMp3Disabled_default,
                R.bool.config_cecQuerySadMp3Enabled_allowed);
        registerSetting33.registerValue(
                0,
                R.bool.config_cecQuerySadMax_userConfigurable,
                R.bool.config_cecQuerySadMp3Disabled_allowed);
        Setting registerSetting34 =
                registerSetting(
                        R.bool.config_enableActivityRecognitionHardwareOverlay, "earc_enabled");
        registerSetting34.registerValue(
                1, R.bool.config_enableAutoPowerModes, R.bool.config_enableBackSound);
        registerSetting34.registerValue(
                0,
                R.bool.config_enableAppCloningBuildingBlocks,
                R.bool.config_enableAppWidgetService);
        for (Setting setting : linkedHashMap.values()) {
            setting.getDefaultValue();
            getStorage(setting);
            getStorageKey(setting);
        }
    }

    public static void getStorage(Setting setting) {
        String str;
        str = setting.mName;
        str.getClass();
        switch (str) {
            case "hdmi_cec_version":
            case "system_audio_mode_muting":
            case "set_menu_language":
            case "rc_profile_source_handles_media_context_sensitive_menu":
            case "rc_profile_source_handles_top_menu":
            case "rc_profile_source_handles_root_menu":
            case "query_sad_atrac":
            case "query_sad_dtshd":
            case "query_sad_mpeg1":
            case "query_sad_mpeg2":
            case "earc_enabled":
            case "query_sad_truehd":
            case "rc_profile_source_handles_contents_menu":
            case "query_sad_wmapro":
            case "routing_control":
            case "query_sad_lpcm":
            case "rc_profile_source_handles_setup_menu":
            case "power_state_change_on_active_source_lost":
            case "power_control_mode":
            case "soundbar_mode":
            case "volume_control_enabled":
            case "hdmi_cec_enabled":
            case "rc_profile_tv":
            case "query_sad_onebitaudio":
            case "query_sad_dd":
            case "tv_wake_on_one_touch_play":
            case "query_sad_aac":
            case "query_sad_ddp":
            case "query_sad_dst":
            case "query_sad_dts":
            case "query_sad_max":
            case "query_sad_mp3":
            case "tv_send_standby_on_sleep":
            case "system_audio_control":
                return;
            default:
                throw new VerificationException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Invalid CEC setting '", str, "' storage."));
        }
    }

    public static String getStorageKey(Setting setting) {
        String str;
        str = setting.mName;
        str.getClass();
        switch (str) {
            case "hdmi_cec_version":
            case "system_audio_mode_muting":
            case "set_menu_language":
            case "rc_profile_source_handles_media_context_sensitive_menu":
            case "rc_profile_source_handles_top_menu":
            case "rc_profile_source_handles_root_menu":
            case "query_sad_atrac":
            case "query_sad_dtshd":
            case "query_sad_mpeg1":
            case "query_sad_mpeg2":
            case "earc_enabled":
            case "query_sad_truehd":
            case "rc_profile_source_handles_contents_menu":
            case "query_sad_wmapro":
            case "routing_control":
            case "query_sad_lpcm":
            case "rc_profile_source_handles_setup_menu":
            case "power_state_change_on_active_source_lost":
            case "power_control_mode":
            case "soundbar_mode":
            case "volume_control_enabled":
            case "hdmi_cec_enabled":
            case "rc_profile_tv":
            case "query_sad_onebitaudio":
            case "query_sad_dd":
            case "tv_wake_on_one_touch_play":
            case "query_sad_aac":
            case "query_sad_ddp":
            case "query_sad_dst":
            case "query_sad_dts":
            case "query_sad_max":
            case "query_sad_mp3":
            case "tv_send_standby_on_sleep":
            case "system_audio_control":
                return str;
            default:
                throw new VerificationException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Invalid CEC setting '", str, "' storage key."));
        }
    }

    public final List getAllowedIntValues(String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.getValueType().equals("int")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a string-type setting."));
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) setting.mAllowedValues).iterator();
        while (it.hasNext()) {
            arrayList.add(((Value) it.next()).mIntValue);
        }
        return arrayList;
    }

    public final List getAllowedStringValues(String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.getValueType().equals("string")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a string-type setting."));
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) setting.mAllowedValues).iterator();
        while (it.hasNext()) {
            arrayList.add(((Value) it.next()).mStringValue);
        }
        return arrayList;
    }

    public final int getIntValue(String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.getValueType().equals("int")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a int-type setting."));
        }
        HdmiLogger.debug(
                XmlUtils$$ExternalSyntheticOutline0.m("Getting CEC setting value '", str, "'."),
                new Object[0]);
        return Integer.parseInt(
                retrieveValue(
                        setting, Integer.toString(setting.getDefaultValue().mIntValue.intValue())));
    }

    public final Setting getSetting(String str) {
        if (this.mSettings.containsKey(str)) {
            return (Setting) this.mSettings.get(str);
        }
        return null;
    }

    public final String getStringValue(String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.getValueType().equals("string")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a string-type setting."));
        }
        HdmiLogger.debug(
                XmlUtils$$ExternalSyntheticOutline0.m("Getting CEC setting value '", str, "'."),
                new Object[0]);
        return retrieveValue(setting, setting.getDefaultValue().mStringValue);
    }

    public final List getUserSettings() {
        ArrayList arrayList = new ArrayList();
        for (Setting setting : this.mSettings.values()) {
            if (setting.mUserConfigurable) {
                arrayList.add(setting.mName);
            }
        }
        return arrayList;
    }

    public final void registerChangeListener(
            String str, HdmiControlService.AnonymousClass2 anonymousClass2, Executor executor) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        getStorage(setting);
        synchronized (this.mLock) {
            try {
                if (!this.mSettingChangeListeners.containsKey(setting)) {
                    this.mSettingChangeListeners.put(setting, new ArrayMap());
                }
                ((ArrayMap) this.mSettingChangeListeners.get(setting))
                        .put(anonymousClass2, executor);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Setting registerSetting(int i, String str) {
        Setting setting = new Setting(this, this.mContext, str, i);
        this.mSettings.put(str, setting);
        return setting;
    }

    public final void removeChangeListener(
            HdmiControlService.AnonymousClass2 anonymousClass2, String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        synchronized (this.mLock) {
            try {
                if (this.mSettingChangeListeners.containsKey(setting)) {
                    ArrayMap arrayMap = (ArrayMap) this.mSettingChangeListeners.get(setting);
                    arrayMap.remove(anonymousClass2);
                    if (arrayMap.isEmpty()) {
                        this.mSettingChangeListeners.remove(setting);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String retrieveValue(Setting setting, String str) {
        getStorage(setting);
        String storageKey = getStorageKey(setting);
        HdmiLogger.debug(
                XmlUtils$$ExternalSyntheticOutline0.m(
                        "Reading '", storageKey, "' shared preference."),
                new Object[0]);
        return this.mStorageAdapter.mSharedPrefs.getString(storageKey, str);
    }

    public final void setIntValue(int i, String str) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.mUserConfigurable) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Updating CEC setting '", str, "' prohibited."));
        }
        if (!setting.getValueType().equals("int")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a int-type setting."));
        }
        if (!((ArrayList) getAllowedIntValues(str)).contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException(
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            i, "Invalid CEC setting '", str, "' value: '", "'."));
        }
        HdmiLogger.debug(
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        i, "Updating CEC setting '", str, "' to '", "'."),
                new Object[0]);
        storeValue(setting, Integer.toString(i));
    }

    public final void setStringValue(String str, String str2) {
        Setting setting = getSetting(str);
        if (setting == null) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Setting '", str, "' does not exist."));
        }
        if (!setting.mUserConfigurable) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Updating CEC setting '", str, "' prohibited."));
        }
        if (!setting.getValueType().equals("string")) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Setting '", str, "' is not a string-type setting."));
        }
        if (!((ArrayList) getAllowedStringValues(str)).contains(str2)) {
            throw new IllegalArgumentException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Invalid CEC setting '", str, "' value: '", str2, "'."));
        }
        HdmiLogger.debug(
                XmlUtils$$ExternalSyntheticOutline0.m(
                        "Updating CEC setting '", str, "' to '", str2, "'."),
                new Object[0]);
        storeValue(setting, str2);
    }

    public final void storeValue(final Setting setting, String str) {
        getStorage(setting);
        String storageKey = getStorageKey(setting);
        HdmiLogger.debug(
                XmlUtils$$ExternalSyntheticOutline0.m("Setting '", storageKey, "' shared pref."),
                new Object[0]);
        this.mStorageAdapter.mSharedPrefs.edit().putString(storageKey, str).apply();
        synchronized (this.mLock) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mSettingChangeListeners.get(setting);
                if (arrayMap == null) {
                    return;
                }
                for (Map.Entry entry : arrayMap.entrySet()) {
                    final HdmiControlService.AnonymousClass2 anonymousClass2 =
                            (HdmiControlService.AnonymousClass2) entry.getKey();
                    ((Executor) entry.getValue())
                            .execute(
                                    new Runnable() { // from class:
                                                     // com.android.server.hdmi.HdmiCecConfig.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            HdmiControlService.AnonymousClass2.this.onChange(
                                                    setting.mName);
                                        }
                                    });
                }
            } finally {
            }
        }
    }
}
