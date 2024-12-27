package com.samsung.android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Predicate;

public class AudioParameter {
    public static final String AOSP_BT_A2DP_SUSPENDED = "A2dpSuspended";
    public static final String AOSP_BT_HEADSET_NREC = "bt_headset_nrec";
    public static final String AOSP_BT_SCO_STATE = "BT_SCO";
    public static final String AOSP_CALL_HAC = "HACSetting";
    public static final String AOSP_DEVICE_SHUTDOWN = "dev_shutdown";
    public static final String SEC_GLOBAL_A2DP_DELAY_REPORT = "g_a2dp_delay_report";
    public static final String SEC_GLOBAL_ACH_SUPPORTED = "g_ach_supported";
    public static final String SEC_GLOBAL_BT_REMOTE_VOLUME_CONTROL_SUPPORT = "g_sco_rvc_support";
    public static final String SEC_GLOBAL_BT_SCO_CODEC_TYPE = "g_bt_sco_codec_type";
    public static final String SEC_GLOBAL_CALL_BAND = "g_call_band";
    public static final String SEC_GLOBAL_CALL_EXTRA_VOLUME_ENABLE = "g_call_extra_volume_enable";
    public static final String SEC_GLOBAL_CALL_FORWARDING_ENABLE = "g_call_forwarding_enable";
    public static final String SEC_GLOBAL_CALL_IMS_CODEC_TYPE = "g_call_ims_codec_type";
    public static final String SEC_GLOBAL_CALL_MEMO_STATE = "g_call_memo_state";
    public static final String SEC_GLOBAL_CALL_RINGBACKTONE_STATE = "g_call_ringbacktone_state";
    public static final String SEC_GLOBAL_CALL_SAR_BACKOFF_ENABLE = "g_call_sar_backoff_enable";
    public static final String SEC_GLOBAL_CALL_SATELLITE_ENABLE = "g_call_satellite_enable";
    public static final String SEC_GLOBAL_CALL_SIM_SLOT = "g_call_sim_slot";
    public static final String SEC_GLOBAL_CALL_SPECTRO_ENABLE = "g_call_spectro_enable";
    public static final String SEC_GLOBAL_CALL_STATE = "g_call_state";
    public static final String SEC_GLOBAL_CALL_VOIP_PACKAGE_NAME = "g_call_voip_package_name";
    public static final String SEC_GLOBAL_DMB_SPK_ENABLE = "g_dmb_spk_enable";
    public static final String SEC_GLOBAL_EFFECT_DOLBY_ENABLE = "g_effect_dolby_enable";
    public static final String SEC_GLOBAL_EFFECT_DOLBY_PROFILE = "g_effect_dolby_profile";
    public static final String SEC_GLOBAL_EFFECT_DV_ADAPT_SOUND = "g_effect_dv_adapt_sound";
    public static final String SEC_GLOBAL_EFFECT_GOOGLE_RESAMPLER_ENABLE =
            "g_effect_google_resampler_enable";
    public static final String SEC_GLOBAL_EFFECT_HEADTRACKER_AVAILABLE =
            "g_effect_headtracker_available";
    public static final String SEC_GLOBAL_EFFECT_MYSPACE_TYPE = "g_effect_myspace_type";
    public static final String SEC_GLOBAL_EFFECT_OFFLOAD_VSP_PARAM = "g_effect_offload_vsp_param";
    public static final String SEC_GLOBAL_EFFECT_PARAM_KEY = "g_effect_param_key";
    public static final String SEC_GLOBAL_FACTORY_CALIBRATION_KEY = "spk_calibration";
    public static final String SEC_GLOBAL_FACTORY_LOOPBACK_KEY = "factory_test_loopback";
    public static final String SEC_GLOBAL_FACTORY_MIC_CHECK = "factory_test_mic_check";
    public static final String SEC_GLOBAL_FACTORY_PREFIX = "factory_test";
    public static final String SEC_GLOBAL_FACTORY_ROUTE = "factory_test_route";
    public static final String SEC_GLOBAL_FACTORY_SPK_PATH = "factory_test_spkpath";
    public static final String SEC_GLOBAL_FMRADIO_ENABLE = "g_fmradio_enable";
    public static final String SEC_GLOBAL_FMRADIO_MUTE = "g_fmradio_mute";
    public static final String SEC_GLOBAL_GAME_UID_LIST = "g_game_uid";
    public static final String SEC_GLOBAL_HW_DISPLAY_ROTATION = "g_hw_display_rotation";
    public static final String SEC_GLOBAL_KNOX_AUDIORECORD_ALLOWED = "g_knox_audiorecord_allowed";
    public static final String SEC_GLOBAL_KNOX_MICROPHONE_ALLOWED = "g_knox_microphone_allowed";
    public static final String SEC_GLOBAL_MULTI_SOUND_PIN_APP_NAME = "g_multi_sound_pin_app_name";
    public static final String SEC_GLOBAL_MULTI_SOUND_PRIORITY_DEVICE =
            "g_multi_sound_priority_device";
    public static final String SEC_GLOBAL_PCM_DUMP_AP_CALL_STATE = "g_pcm_dump_ap_call_state";
    public static final String SEC_GLOBAL_PCM_DUMP_STATE = "g_pcm_dump_state";
    public static final String SEC_GLOBAL_PREFIX = "g_";
    public static final String SEC_GLOBAL_PTT_CALL_VOLUME_ENABLE = "g_ptt_call_volume_enable";
    public static final String SEC_GLOBAL_PTT_MODE = "g_ptt_mode";
    public static final String SEC_GLOBAL_PTT_MODE_3RD_PARTY = "ptt_mode";
    public static final String SEC_GLOBAL_RECORD_BEAMFORMING_MODE = "g_record_beamforming_mode";
    public static final String SEC_GLOBAL_RECORD_CONVERSATION_ENERGY_KEY =
            "g_record_conversation_energy_key";
    public static final String SEC_GLOBAL_RECORD_INPUT_LATENCY = "g_record_input_latency";
    public static final String SEC_GLOBAL_RECORD_NSRI_SECURITY_ENABLE =
            "g_record_nsri_security_enable";
    public static final String SEC_GLOBAL_RECORD_SEC_VOICE_RECORDER_ENABLE =
            "g_record_sec_voice_recorder_enable";
    public static final String SEC_GLOBAL_RECORD_TX_INVERSION = "g_record_tx_inversion";
    public static final String SEC_GLOBAL_SCO_SAMPLERATE = "g_sco_samplerate";
    public static final String SEC_GLOBAL_SET_A2DP_AV_SYNC = "g_a2dp_av_sync";
    public static final String SEC_GLOBAL_SHUTDOWN_MUTE = "g_shutdown_mute";
    public static final String SEC_GLOBAL_SHUTDOWN_SUSPEND = "g_shutdown_suspend";
    public static final String SEC_GLOBAL_SOUND_PATH_ACTIVE_ADDRESS = "g_sound_path_active_address";
    public static final String SEC_GLOBAL_SOUND_PATH_AVAILABLE_DEVICES =
            "g_sound_path_available_devices";
    public static final String SEC_GLOBAL_TUNING_RELOAD_VOLUME = "g_tuning_reload_volume";
    public static final String SEC_GLOBAL_TUNING_SOUNDBOOSTER_RELOAD =
            "g_tuning_soundbooster_reload";
    public static final String SEC_GLOBAL_UHQ_RESTORED_TRACK_ENABLE = "g_uhq_restored_track_enable";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_BABYCRY_ENABLE =
            "g_voice_wakeup_babycry_enable";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_ENABLE = "g_voice_wakeup_enable";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_KEYWORD_GRAMMAR_PATH =
            "g_voice_wakeup_keyword_grammar_path";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_KEYWORD_PATH = "g_voice_wakeup_keyword_path";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_REGISTER_VOICE_KEYWORD =
            "g_voice_wakeup_register_voice_keyword";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_SEAMLESS_ENABLE =
            "g_voice_wakeup_seamless_enable";
    public static final String SEC_GLOBAL_VOICE_WAKEUP_TRIGGER_BACKLOG_TIME =
            "g_voice_wakeup_trigger_backlog_time";
    public static final String SEC_GLOBAL_VOLUME_MONITOR_WARNING = "g_volume_monitor_warning_level";
    public static final String SEC_GLOBAL_VOLUME_SITUATION_KEY = "g_volume_situation_key";
    public static final String SEC_LOCAL_ALL_SOUND_MUTE_ENABLE = "l_all_sound_mute_enable";
    public static final String SEC_LOCAL_AURACAST_APP_KEY = "l_auracast_app_key";
    public static final String SEC_LOCAL_AURACAST_ENABLE = "l_auracast_enable";
    public static final String SEC_LOCAL_BIGDATA_APP = "l_bigdata_app";
    public static final String SEC_LOCAL_BIGDATA_LOGGING = "l_bigdata_logging";
    public static final String SEC_LOCAL_BT_GAME_LATENCY = "l_bt_game_latency";
    public static final String SEC_LOCAL_BT_TYPE_HEADSET = "l_bt_type_headset";
    public static final String SEC_LOCAL_CALL_2G_TDMA = "l_call_2g_tdma";
    public static final String SEC_LOCAL_CALL_NB_QUALITY_ENABLE = "l_call_nb_quality_enable";
    public static final String SEC_LOCAL_CALL_RAT_TYPE = "l_call_rat_type";
    public static final String SEC_LOCAL_CALL_RIL_STATE_CONNECTED = "l_call_ril_state_connected";
    public static final String SEC_LOCAL_CALL_TRANSLATION_MODE = "l_call_translation_mode";
    public static final String SEC_LOCAL_CALL_VOIP_EXTRA_VOLUME_ENABLE =
            "l_call_voip_extra_volume_enable";
    public static final String SEC_LOCAL_DEVICE_CURRENT_OUTPUT = "l_device_current_output";
    public static final String SEC_LOCAL_DEX_KEY = "l_dex_key";
    public static final String SEC_LOCAL_DIRECT_POWER_ENABLE = "l_direct_power_enable";
    public static final String SEC_LOCAL_DUAL_SPEAKER_AMP_LEFT_POWER_ENABLE =
            "l_dual_speaker_amp_left_power_enable";
    public static final String SEC_LOCAL_DUAL_SPEAKER_CALLING_PACKAGE =
            "l_dual_speaker_calling_package";
    public static final String SEC_LOCAL_DUAL_SPEAKER_ENABLE = "l_dual_speaker_enable";
    public static final String SEC_LOCAL_DVFS_MIN_LOCK = "l_dvfs_min_lock";
    public static final String SEC_LOCAL_EFFECT_LISTENBACK_KEY = "l_effect_listenback_key";
    public static final String SEC_LOCAL_EFFECT_UPSCALER_MODE = "l_effect_upscaler_mode";
    public static final String SEC_LOCAL_FAST_TRACK_ENABLE = "l_fast_track_enable";
    public static final String SEC_LOCAL_FMRADIO_RECORD_ACTIVE = "l_fmradio_record_active";
    public static final String SEC_LOCAL_GAME_CHAT_ENABLE = "l_game_chat_enable";
    public static final String SEC_LOCAL_GUARD_CALL_MODE = "l_guard_call_mode";
    public static final String SEC_LOCAL_GUARD_CALL_MODE_CALLING_PID =
            "l_guard_call_mode_calling_pid";
    public static final String SEC_LOCAL_GUARD_CALL_MODE_SKIP = "l_guard_call_mode_skip";
    public static final String SEC_LOCAL_HIDDEN_SOUND_KEY = "l_hidden_sound_key";
    public static final String SEC_LOCAL_HW_FLAT_MOTION_STATE = "l_hw_flat_motion_state";
    public static final String SEC_LOCAL_HW_FOLDER_STATE = "l_hw_folder_state";
    public static final String SEC_LOCAL_HW_PROXIMITY_SENSOR_STATE = "l_hw_proximity_sensor_state";
    public static final String SEC_LOCAL_IS_USING_AUDIO = "l_is_using_audio";
    public static final String SEC_LOCAL_KARAOKE_ENABLE = "l_karaoke_enable";
    public static final String SEC_LOCAL_MONO_TYPE = "l_mono_type";
    public static final String SEC_LOCAL_MULTI_SOUND_ACTIVE_TRACK_DEVICE =
            "l_multi_sound_active_track_device";
    public static final String SEC_LOCAL_MULTI_SOUND_ACTIVE_TRACK_UID =
            "l_multi_sound_active_track_uid";
    public static final String SEC_LOCAL_MULTI_SOUND_KEY = "l_multi_sound_key";
    public static final String SEC_LOCAL_PREFIX = "l_";
    public static final String SEC_LOCAL_RECORD_ACTIVE_ENABLE = "l_record_active_enable";
    public static final String SEC_LOCAL_RECOVERY = "l_recovery_restarting";
    public static final String SEC_LOCAL_REMOTE_MIC_ENABLE = "l_remote_mic_enable";
    public static final String SEC_LOCAL_SAFE_MEDIA_VOLUME_ENABLE = "l_safe_media_volume_enable";
    public static final String SEC_LOCAL_SCREEN_CALL = "l_screen_call";
    public static final String SEC_LOCAL_SET_FROM_AUDIOSERVICE = "l_set_from_audioservice";
    public static final String SEC_LOCAL_SET_SAFE_MEDIA_VOLUME = "l_set_safe_media_volume";
    public static final String SEC_LOCAL_SIMULATE_DEVICE_ENABLE = "l_simulate_device_enable";
    public static final String SEC_LOCAL_SMART_VEIW_SPLIT_SOUND_ENABLE =
            "l_smart_view_split_sound_enable";
    public static final String SEC_LOCAL_SMART_VIEW_ENABLE = "l_smart_view_enable";
    public static final String SEC_LOCAL_SMART_VIEW_FIXED_VOLUME_ENABLE =
            "l_smart_view_fixed_volume_enable";
    public static final String SEC_LOCAL_SMART_VIEW_MIRRORING_ACTIVE =
            "l_smart_view_mirroring_active";
    public static final String SEC_LOCAL_SOUND_ASSISTANT_LR_SWITCH_ENABLE =
            "l_sound_assistant_lr_switch_enable";
    public static final String SEC_LOCAL_SOUND_ASSISTANT_RING_VIA_HEADSET_ENABLE =
            "l_sound_assistant_ring_via_headset_enable";
    public static final String SEC_LOCAL_SPEAKER_BALANCE = "l_speaker_balance";
    public static final String SEC_LOCAL_STREAM_ACTIVE = "l_stream_active";
    public static final String SEC_LOCAL_SUPPORT_ABSOLUTE_VOLUME = "l_support_absolute_volume";
    public static final String SEC_LOCAL_SYSTEM_READY = "l_system_ready";
    public static final String SEC_LOCAL_VIDEO_CALL_MONSTER_SOUND = "l_mic_input_control_mode_2mic";
    public static final String SEC_LOCAL_VIDEO_CALL_VOICE_EFFECT = "l_mic_input_control_mode";
    public static final String SEC_LOCAL_VIDEO_CALL_VOICE_EFFECT_CALL =
            "l_mic_input_control_mode_call";
    public static final String SEC_LOCAL_VOICE_CALL_MONSTER_SOUND = "l_call_nc_booster_enable";
    public static final String SEC_LOCAL_VOICE_RX_CONTROL_MODE = "l_voice_rx_control_mode";
    public static final String SEC_LOCAL_VOICE_TX_CONTROL_MODE = "l_voice_tx_control_mode";
    public static final String SEC_LOCAL_VOIP_TRANSLATE_PACKAGE_NAME =
            "l_voip_translate_package_name";
    public static final String SEC_LOCAL_VOLUME_FINE_KEY = "l_volume_fine_key";
    public static final String SEC_LOCAL_VOLUME_LIMIT_KEY = "l_volume_limit_key";
    public static final String SEC_LOCAL_VOLUME_MONITOR_ONOFF = "l_volume_monitor_onoff";
    public static final String SEC_LOCAL_VOLUME_PREVENT_OVERHEAT_KEY =
            "l_volume_prevent_overheat_key";
    public static final String SEC_LOCAL_VOLUME_TABLE = "l_volume_table";
    public static final String SUBKEY_AUDIO_PARAM = "audioParam";
    public static final String SUBKEY_AURACAST_UID_ADD = "uid_add";
    public static final String SUBKEY_AURACAST_UID_LIST = "uid_list";
    public static final String SUBKEY_AURACAST_UID_REMOVE = "uid_remove";
    public static final String SUBKEY_AURACAST_UID_RESET = "uid_reset";
    public static final String SUBKEY_BLUETOOTH_ADDRESS = "address";
    public static final String SUBKEY_DEX_CONNECTED = "connected";
    public static final String SUBKEY_DEX_PATH = "path";
    public static final String SUBKEY_DEX_STATE = "state";
    public static final String SUBKEY_DEX_TYPE = "type";
    public static final String SUBKEY_EFFECT_LISTENBACK_STATE = "state";
    public static final String SUBKEY_EFFECT_PARAM_ADAPT_SOUND = "adapt_sound";
    public static final String SUBKEY_EFFECT_PARAM_ADAPT_SOUND_ENABLE = "adapt_sound_enable";
    public static final String SUBKEY_EFFECT_PARAM_HMT = "hmt";
    public static final String SUBKEY_FACTORY_LOOPBACK_PATH = "factory_test_path";
    public static final String SUBKEY_FACTORY_LOOPBACK_TYPE = "factory_test_type";
    public static final String SUBKEY_HIDDEN_SOUND_ADDRESS = "address";
    public static final String SUBKEY_HIDDEN_SOUND_PID = "pid";
    public static final String SUBKEY_HIDDEN_SOUND_VERSION = "version";
    public static final String SUBKEY_HIDDEN_SOUND_VOLUME = "volume";
    public static final String SUBKEY_MULTI_SOUND_DEVICE = "device";
    public static final String SUBKEY_MULTI_SOUND_ENABLE = "enable";
    public static final String SUBKEY_MULTI_SOUND_EXCLUSIVE = "exclusive";
    public static final String SUBKEY_MULTI_SOUND_PIN_DEVICE = "pin_device_name";
    public static final String SUBKEY_MULTI_SOUND_TYPE = "type";
    public static final String SUBKEY_MULTI_SOUND_UID = "uid";
    public static final String SUBKEY_MULTI_SOUND_VOLUME = "volume";
    public static final String SUBKEY_SUPPORT_VOIP = "support_voip";
    public static final String SUBKEY_VOLUME_FINE_DEVICE = "device";
    public static final String SUBKEY_VOLUME_FINE_INDEX = "index";
    public static final String SUBKEY_VOLUME_LIMIT_ENABLE = "enable";
    public static final String SUBKEY_VOLUME_LIMIT_LEVEL = "level";
    public static final String SUBKEY_VOLUME_LIMIT_PACKAGE_NAME = "package";
    public static final String SUBKEY_VOLUME_PREVENT_OVERHEAT_GAIN = "gain";
    public static final String SUBKEY_VOLUME_PREVENT_OVERHEAT_STATE = "state";
    public static final String SUBKEY_VOLUME_PREVENT_OVERHEAT_UID = "uid";
    public static final String SUBKEY_VOLUME_SITUATION_DEVICE = "device";
    public static final String SUBKEY_VOLUME_SITUATION_TYPE = "type";
    public static final List<String> VALUES_VM_CSD_100_WARNING =
            Arrays.asList("8", "9", "10", "11");
    public static final String VALUE_2MIC = "2mic";
    public static final String VALUE_ACTIVE_SOUND_INFO = "active_sound_info";
    public static final String VALUE_ALWAYS_MIC_ON = "always_mic_on";
    public static final String VALUE_DEX = "dex";
    public static final String VALUE_DEX_PAD = "pad";
    public static final String VALUE_DEX_STATION = "station";
    public static final String VALUE_DOCK = "dock";
    public static final String VALUE_FALSE = "false";
    public static final String VALUE_FB = "fb";
    public static final String VALUE_MOUNT = "mount";
    public static final String VALUE_NB = "nb";
    public static final String VALUE_NONE = "none";
    public static final String VALUE_OFF = "off";
    public static final String VALUE_ON = "on";
    public static final String VALUE_RCV = "rcv";
    public static final String VALUE_RESTORE = "restore";
    public static final String VALUE_SPK = "spk";
    public static final String VALUE_SUSPEND = "suspend";
    public static final String VALUE_SWB = "swb";
    public static final String VALUE_THIRD = "third";
    public static final String VALUE_TRUE = "true";
    public static final String VALUE_UNMOUNT = "unmount";
    public static final String VALUE_VM_CSD_500_WARNING = "12";
    public static final String VALUE_WB = "wb";
    private LinkedHashMap<String, String> mAudioParams;
    private boolean mHasLocalParameter;

    public AudioParameter(String audioParams) {
        this.mAudioParams = new LinkedHashMap<>();
        this.mHasLocalParameter = false;
        if (audioParams != null) {
            StringTokenizer st1 =
                    new StringTokenizer(audioParams, NavigationBarInflaterView.GRAVITY_SEPARATOR);
            while (st1.hasMoreTokens()) {
                String token = st1.nextToken();
                StringTokenizer st2 = new StringTokenizer(token, "=");
                String key = st2.hasMoreTokens() ? st2.nextToken() : null;
                String value = st2.hasMoreTokens() ? st2.nextToken() : "";
                if (key != null && value != null) {
                    this.mHasLocalParameter |= key.startsWith(SEC_LOCAL_PREFIX);
                    this.mAudioParams.put(key, value);
                }
            }
        }
    }

    public AudioParameter(LinkedHashMap<String, String> audioParameters) {
        this.mAudioParams = new LinkedHashMap<>();
        this.mHasLocalParameter = false;
        this.mAudioParams = audioParameters;
        if (audioParameters != null) {
            this.mHasLocalParameter =
                    audioParameters.keySet().stream()
                            .anyMatch(
                                    new Predicate() { // from class:
                                                      // com.samsung.android.media.AudioParameter$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            boolean startsWith;
                                            startsWith =
                                                    ((String) obj)
                                                            .startsWith(
                                                                    AudioParameter
                                                                            .SEC_LOCAL_PREFIX);
                                            return startsWith;
                                        }
                                    });
        }
    }

    public String get(String strKey) {
        if (strKey != null && this.mAudioParams != null && !this.mAudioParams.isEmpty()) {
            return this.mAudioParams.get(strKey);
        }
        return null;
    }

    public int getInt(String strKey, int defaultVal) {
        if (strKey != null && this.mAudioParams != null && !this.mAudioParams.isEmpty()) {
            String value = this.mAudioParams.get(strKey);
            if (value == null) {
                return defaultVal;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
            }
        }
        return defaultVal;
    }

    public boolean hasKey(String key) {
        if (this.mAudioParams != null) {
            return this.mAudioParams.containsKey(key);
        }
        return false;
    }

    public boolean hasLocalParameter() {
        return this.mHasLocalParameter;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mAudioParams != null && !this.mAudioParams.isEmpty()) {
            Iterator i = this.mAudioParams.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<String, String> m = i.next();
                sb.append(m.getKey());
                if (!"".equals(m.getValue())) {
                    sb.append("=");
                    sb.append(m.getValue());
                }
                if (i.hasNext()) {
                    sb.append(NavigationBarInflaterView.GRAVITY_SEPARATOR);
                }
            }
        }
        return sb.toString();
    }

    public static class Builder {
        private LinkedHashMap<String, String> mAudioParams = new LinkedHashMap<>();

        public Builder setParam(String key) {
            this.mAudioParams.put(key, "");
            return this;
        }

        public Builder setParam(String key, String value) {
            this.mAudioParams.put(key, value);
            return this;
        }

        public Builder setParam(String key, int value) {
            this.mAudioParams.put(key, "" + value);
            return this;
        }

        public Builder setParam(String key, boolean value) {
            this.mAudioParams.put(key, "" + value);
            return this;
        }

        public Builder setParam(String key, float value) {
            this.mAudioParams.put(key, "" + value);
            return this;
        }

        public AudioParameter build() {
            return new AudioParameter(this.mAudioParams);
        }
    }
}
