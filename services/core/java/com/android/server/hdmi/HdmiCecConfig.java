package com.android.server.hdmi;

import android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.INetd;
import android.os.Environment;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.ConcurrentUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class HdmiCecConfig {
  public final Context mContext;
  public final Object mLock;
  public final ArrayMap mSettingChangeListeners;
  public LinkedHashMap mSettings;
  public final StorageAdapter mStorageAdapter;

  public interface SettingChangeListener {
    void onChange(String str);
  }

  public class VerificationException extends RuntimeException {
    public VerificationException(String str) {
      super(str);
    }
  }

  public class StorageAdapter {
    public final Context mContext;
    public final SharedPreferences mSharedPrefs;

    public StorageAdapter(Context context) {
      this.mContext = context;
      this.mSharedPrefs =
          context
              .createDeviceProtectedStorageContext()
              .getSharedPreferences(
                  new File(
                      new File(Environment.getDataSystemDirectory(), "shared_prefs"),
                      "cec_config.xml"),
                  0);
    }

    public String retrieveSystemProperty(String str, String str2) {
      return SystemProperties.get(str, str2);
    }

    public void storeSystemProperty(String str, String str2) {
      SystemProperties.set(str, str2);
    }

    public String retrieveGlobalSetting(String str, String str2) {
      String string = Settings.Global.getString(this.mContext.getContentResolver(), str);
      return string != null ? string : str2;
    }

    public void storeGlobalSetting(String str, String str2) {
      Settings.Global.putString(this.mContext.getContentResolver(), str, str2);
    }

    public String retrieveSharedPref(String str, String str2) {
      return this.mSharedPrefs.getString(str, str2);
    }

    public void storeSharedPref(String str, String str2) {
      this.mSharedPrefs.edit().putString(str, str2).apply();
    }
  }

  public class Value {
    public final Integer mIntValue;
    public final String mStringValue;

    public Value(String str) {
      this.mStringValue = str;
      this.mIntValue = null;
    }

    public Value(Integer num) {
      this.mStringValue = null;
      this.mIntValue = num;
    }

    public String getStringValue() {
      return this.mStringValue;
    }

    public Integer getIntValue() {
      return this.mIntValue;
    }
  }

  public class Setting {
    public final Context mContext;
    public final String mName;
    public final boolean mUserConfigurable;
    public Value mDefaultValue = null;
    public List mAllowedValues = new ArrayList();

    public Setting(Context context, String str, int i) {
      this.mContext = context;
      this.mName = str;
      this.mUserConfigurable = context.getResources().getBoolean(i);
    }

    public String getName() {
      return this.mName;
    }

    public String getValueType() {
      return getDefaultValue().getStringValue() != null ? "string" : "int";
    }

    public Value getDefaultValue() {
      Value value = this.mDefaultValue;
      if (value != null) {
        return value;
      }
      throw new VerificationException(
          "Invalid CEC setup for '" + getName() + "' setting. Setting has no default value.");
    }

    public boolean getUserConfigurable() {
      return this.mUserConfigurable;
    }

    public final void registerValue(Value value, int i, int i2) {
      if (this.mContext.getResources().getBoolean(i)) {
        this.mAllowedValues.add(value);
        if (this.mContext.getResources().getBoolean(i2)) {
          if (this.mDefaultValue != null) {
            Slog.e(
                "HdmiCecConfig",
                "Failed to set '"
                    + value
                    + "' as a default for '"
                    + getName()
                    + "': Setting already has a default ('"
                    + this.mDefaultValue
                    + "').");
            return;
          }
          this.mDefaultValue = value;
        }
      }
    }

    public void registerValue(String str, int i, int i2) {
      registerValue(HdmiCecConfig.this.new Value(str), i, i2);
    }

    public void registerValue(int i, int i2, int i3) {
      registerValue(HdmiCecConfig.this.new Value(Integer.valueOf(i)), i2, i3);
    }

    public List getAllowedValues() {
      return this.mAllowedValues;
    }
  }

  public HdmiCecConfig(Context context, StorageAdapter storageAdapter) {
    this.mLock = new Object();
    this.mSettingChangeListeners = new ArrayMap();
    this.mSettings = new LinkedHashMap();
    this.mContext = context;
    this.mStorageAdapter = storageAdapter;
    Setting registerSetting =
        registerSetting("hdmi_cec_enabled", R.bool.config_bluetooth_sco_off_call);
    registerSetting.registerValue(
        1,
        R.bool.config_biometricFrrNotificationEnabled,
        R.bool.config_bluetooth_address_validation);
    registerSetting.registerValue(
        0,
        R.bool.config_bg_prompt_abusive_apps_to_bg_restricted,
        R.bool.config_bg_prompt_fgs_with_noti_to_bg_restricted);
    Setting registerSetting2 =
        registerSetting(
            "hdmi_cec_version", R.bool.config_callNotificationActionColorsRequireColorized);
    registerSetting2.registerValue(
        5, R.bool.config_brightWhenDozing, R.bool.config_bugReportHandlerEnabled);
    registerSetting2.registerValue(
        6, R.bool.config_built_in_sip_phone, R.bool.config_buttonTextAllCaps);
    Setting registerSetting3 =
        registerSetting(
            "routing_control", R.bool.config_cecRcProfileSourceSetupMenu_userConfigurable);
    registerSetting3.registerValue(
        1,
        R.bool.config_cecRcProfileSourceSetupMenuNotHandled_allowed,
        R.bool.config_cecRcProfileSourceSetupMenuNotHandled_default);
    registerSetting3.registerValue(
        0,
        R.bool.config_cecRcProfileSourceSetupMenuHandled_allowed,
        R.bool.config_cecRcProfileSourceSetupMenuHandled_default);
    Setting registerSetting4 =
        registerSetting("soundbar_mode", R.bool.config_cecRcProfileTvOne_allowed);
    registerSetting4.registerValue(
        1, R.bool.config_cecRcProfileTvNone_allowed, R.bool.config_cecRcProfileTvNone_default);
    registerSetting4.registerValue(
        0, R.bool.config_cecRcProfileTvFour_allowed, R.bool.config_cecRcProfileTvFour_default);
    Setting registerSetting5 =
        registerSetting("power_control_mode", R.bool.config_carrier_vt_available);
    registerSetting5.registerValue(
        "to_tv", R.bool.config_carrier_volte_available, R.bool.config_carrier_volte_tty_supported);
    registerSetting5.registerValue(
        INetd.IF_FLAG_BROADCAST,
        R.bool.config_cameraDoubleTapPowerGestureEnabled,
        R.bool.config_camera_autorotate);
    registerSetting5.registerValue(
        "none", R.bool.config_camera_sound_forced, R.bool.config_canRemoveFirstAccount);
    registerSetting5.registerValue(
        "to_tv_and_audio_system",
        R.bool.config_canSwitchToHeadlessSystemUser,
        R.bool.config_carDockEnablesAccelerometer);
    Setting registerSetting6 =
        registerSetting(
            "power_state_change_on_active_source_lost",
            R.bool.config_cecHdmiCecControlEnabled_allowed);
    registerSetting6.registerValue(
        "none", R.bool.config_carrier_wfc_ims_available, R.bool.config_cbrs_supported);
    registerSetting6.registerValue(
        "standby_now",
        R.bool.config_cecHdmiCecControlDisabled_allowed,
        R.bool.config_cecHdmiCecControlDisabled_default);
    Setting registerSetting7 =
        registerSetting("system_audio_control", R.bool.config_cecRcProfileTvTwo_default);
    registerSetting7.registerValue(
        1, R.bool.config_cecRcProfileTvThree_default, R.bool.config_cecRcProfileTvTwo_allowed);
    registerSetting7.registerValue(
        0, R.bool.config_cecRcProfileTvOne_default, R.bool.config_cecRcProfileTvThree_allowed);
    Setting registerSetting8 =
        registerSetting("system_audio_mode_muting", R.bool.config_cecRoutingControlEnabled_default);
    registerSetting8.registerValue(
        1,
        R.bool.config_cecRoutingControlDisabled_default,
        R.bool.config_cecRoutingControlEnabled_allowed);
    registerSetting8.registerValue(
        0,
        R.bool.config_cecRcProfileTv_userConfigurable,
        R.bool.config_cecRoutingControlDisabled_allowed);
    Setting registerSetting9 =
        registerSetting(
            "volume_control_enabled", R.bool.config_cecSystemAudioControlEnabled_default);
    registerSetting9.registerValue(
        1,
        R.bool.config_cecSystemAudioControlDisabled_default,
        R.bool.config_cecSystemAudioControlEnabled_allowed);
    registerSetting9.registerValue(
        0,
        R.bool.config_cecSoundbarMode_userConfigurable,
        R.bool.config_cecSystemAudioControlDisabled_allowed);
    Setting registerSetting10 =
        registerSetting("tv_wake_on_one_touch_play", R.bool.config_cecSoundbarModeEnabled_default);
    registerSetting10.registerValue(
        1,
        R.bool.config_cecSoundbarModeDisabled_default,
        R.bool.config_cecSoundbarModeEnabled_allowed);
    registerSetting10.registerValue(
        0,
        R.bool.config_cecSetMenuLanguage_userConfigurable,
        R.bool.config_cecSoundbarModeDisabled_allowed);
    Setting registerSetting11 =
        registerSetting(
            "tv_send_standby_on_sleep", R.bool.config_cecSetMenuLanguageEnabled_default);
    registerSetting11.registerValue(
        1,
        R.bool.config_cecSetMenuLanguageDisabled_default,
        R.bool.config_cecSetMenuLanguageEnabled_allowed);
    registerSetting11.registerValue(
        0,
        R.bool.config_cecRoutingControl_userConfigurable,
        R.bool.config_cecSetMenuLanguageDisabled_allowed);
    Setting registerSetting12 =
        registerSetting(
            "set_menu_language", R.bool.config_cecRcProfileSourceTopMenu_userConfigurable);
    registerSetting12.registerValue(
        1,
        R.bool.config_cecRcProfileSourceTopMenuNotHandled_allowed,
        R.bool.config_cecRcProfileSourceTopMenuNotHandled_default);
    registerSetting12.registerValue(
        0,
        R.bool.config_cecRcProfileSourceTopMenuHandled_allowed,
        R.bool.config_cecRcProfileSourceTopMenuHandled_default);
    Setting registerSetting13 =
        registerSetting("rc_profile_tv", R.bool.config_cecRcProfileSourceRootMenu_userConfigurable);
    registerSetting13.registerValue(
        0,
        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_default,
        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_allowed);
    registerSetting13.registerValue(
        2,
        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuNotHandled_default,
        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenu_userConfigurable);
    registerSetting13.registerValue(
        6,
        R.bool.config_cecRcProfileSourceRootMenuNotHandled_allowed,
        R.bool.config_cecRcProfileSourceRootMenuNotHandled_default);
    registerSetting13.registerValue(
        10,
        R.bool.config_cecRcProfileSourceRootMenuHandled_allowed,
        R.bool.config_cecRcProfileSourceRootMenuHandled_default);
    registerSetting13.registerValue(
        14,
        R.bool.config_cecRcProfileSourceContentsMenu_userConfigurable,
        R.bool.config_cecRcProfileSourceMediaContextSensitiveMenuHandled_allowed);
    Setting registerSetting14 =
        registerSetting(
            "rc_profile_source_handles_root_menu", R.bool.config_cecQuerySadTruehdEnabled_default);
    registerSetting14.registerValue(
        1,
        R.bool.config_cecQuerySadOnebitaudio_userConfigurable,
        R.bool.config_cecQuerySadTruehdDisabled_allowed);
    registerSetting14.registerValue(
        0,
        R.bool.config_cecQuerySadTruehdDisabled_default,
        R.bool.config_cecQuerySadTruehdEnabled_allowed);
    Setting registerSetting15 =
        registerSetting(
            "rc_profile_source_handles_setup_menu", R.bool.config_cecQuerySadWmaproEnabled_default);
    registerSetting15.registerValue(
        1,
        R.bool.config_cecQuerySadTruehd_userConfigurable,
        R.bool.config_cecQuerySadWmaproDisabled_allowed);
    registerSetting15.registerValue(
        0,
        R.bool.config_cecQuerySadWmaproDisabled_default,
        R.bool.config_cecQuerySadWmaproEnabled_allowed);
    Setting registerSetting16 =
        registerSetting(
            "rc_profile_source_handles_contents_menu",
            R.bool.config_cecQuerySadMpeg2Enabled_default);
    registerSetting16.registerValue(
        1,
        R.bool.config_cecQuerySadMpeg1_userConfigurable,
        R.bool.config_cecQuerySadMpeg2Disabled_allowed);
    registerSetting16.registerValue(
        0,
        R.bool.config_cecQuerySadMpeg2Disabled_default,
        R.bool.config_cecQuerySadMpeg2Enabled_allowed);
    Setting registerSetting17 =
        registerSetting(
            "rc_profile_source_handles_top_menu",
            R.bool.config_cecRcProfileSourceContentsMenuNotHandled_default);
    registerSetting17.registerValue(
        1,
        R.bool.config_cecQuerySadWmapro_userConfigurable,
        R.bool.config_cecRcProfileSourceContentsMenuHandled_allowed);
    registerSetting17.registerValue(
        0,
        R.bool.config_cecRcProfileSourceContentsMenuHandled_default,
        R.bool.config_cecRcProfileSourceContentsMenuNotHandled_allowed);
    Setting registerSetting18 =
        registerSetting(
            "rc_profile_source_handles_media_context_sensitive_menu",
            R.bool.config_cecQuerySadOnebitaudioEnabled_default);
    registerSetting18.registerValue(
        1,
        R.bool.config_cecQuerySadMpeg2_userConfigurable,
        R.bool.config_cecQuerySadOnebitaudioDisabled_allowed);
    registerSetting18.registerValue(
        0,
        R.bool.config_cecQuerySadOnebitaudioDisabled_default,
        R.bool.config_cecQuerySadOnebitaudioEnabled_allowed);
    Setting registerSetting19 =
        registerSetting("query_sad_lpcm", R.bool.config_cecQuerySadDdpEnabled_default);
    registerSetting19.registerValue(
        1,
        R.bool.config_cecQuerySadDdpDisabled_default,
        R.bool.config_cecQuerySadDdpEnabled_allowed);
    registerSetting19.registerValue(
        0,
        R.bool.config_cecQuerySadDd_userConfigurable,
        R.bool.config_cecQuerySadDdpDisabled_allowed);
    Setting registerSetting20 =
        registerSetting("query_sad_dd", R.bool.config_cecPowerControlModeTv_default);
    registerSetting20.registerValue(
        1,
        R.bool.config_cecPowerControlModeTvAndAudioSystem_default,
        R.bool.config_cecPowerControlModeTv_allowed);
    registerSetting20.registerValue(
        0,
        R.bool.config_cecPowerControlModeNone_default,
        R.bool.config_cecPowerControlModeTvAndAudioSystem_allowed);
    Setting registerSetting21 =
        registerSetting("query_sad_mpeg1", R.bool.config_cecQuerySadDtshdEnabled_default);
    registerSetting21.registerValue(
        1,
        R.bool.config_cecQuerySadDtshdDisabled_default,
        R.bool.config_cecQuerySadDtshdEnabled_allowed);
    registerSetting21.registerValue(
        0,
        R.bool.config_cecQuerySadDts_userConfigurable,
        R.bool.config_cecQuerySadDtshdDisabled_allowed);
    Setting registerSetting22 =
        registerSetting("query_sad_mp3", R.bool.config_cecQuerySadDtsEnabled_default);
    registerSetting22.registerValue(
        1,
        R.bool.config_cecQuerySadDtsDisabled_default,
        R.bool.config_cecQuerySadDtsEnabled_allowed);
    registerSetting22.registerValue(
        0,
        R.bool.config_cecQuerySadDst_userConfigurable,
        R.bool.config_cecQuerySadDtsDisabled_allowed);
    Setting registerSetting23 =
        registerSetting("query_sad_mpeg2", R.bool.config_cecQuerySadLpcmEnabled_default);
    registerSetting23.registerValue(
        1,
        R.bool.config_cecQuerySadLpcmDisabled_default,
        R.bool.config_cecQuerySadLpcmEnabled_allowed);
    registerSetting23.registerValue(
        0,
        R.bool.config_cecQuerySadDtshd_userConfigurable,
        R.bool.config_cecQuerySadLpcmDisabled_allowed);
    Setting registerSetting24 =
        registerSetting("query_sad_aac", R.bool.config_cecHdmiCecVersion20_allowed);
    registerSetting24.registerValue(
        1, R.bool.config_cecHdmiCecVersion14b_allowed, R.bool.config_cecHdmiCecVersion14b_default);
    registerSetting24.registerValue(
        0,
        R.bool.config_cecHdmiCecControlEnabled_default,
        R.bool.config_cecHdmiCecEnabled_userConfigurable);
    Setting registerSetting25 =
        registerSetting("query_sad_dts", R.bool.config_cecQuerySadAtracEnabled_default);
    registerSetting25.registerValue(
        1,
        R.bool.config_cecQuerySadAtracDisabled_default,
        R.bool.config_cecQuerySadAtracEnabled_allowed);
    registerSetting25.registerValue(
        0,
        R.bool.config_cecQuerySadAac_userConfigurable,
        R.bool.config_cecQuerySadAtracDisabled_allowed);
    Setting registerSetting26 =
        registerSetting("query_sad_atrac", R.bool.config_cecPowerControlModeNone_allowed);
    registerSetting26.registerValue(
        1,
        R.bool.config_cecPowerControlModeBroadcast_allowed,
        R.bool.config_cecPowerControlModeBroadcast_default);
    registerSetting26.registerValue(
        0,
        R.bool.config_cecHdmiCecVersion20_default,
        R.bool.config_cecHdmiCecVersion_userConfigurable);
    Setting registerSetting27 =
        registerSetting("query_sad_onebitaudio", R.bool.config_cecQuerySadMaxEnabled_default);
    registerSetting27.registerValue(
        1,
        R.bool.config_cecQuerySadMaxDisabled_default,
        R.bool.config_cecQuerySadMaxEnabled_allowed);
    registerSetting27.registerValue(
        0,
        R.bool.config_cecQuerySadLpcm_userConfigurable,
        R.bool.config_cecQuerySadMaxDisabled_allowed);
    Setting registerSetting28 =
        registerSetting(
            "query_sad_ddp", R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_default);
    registerSetting28.registerValue(
        1,
        R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_default,
        R.bool.config_cecPowerStateChangeOnActiveSourceLostStandbyNow_allowed);
    registerSetting28.registerValue(
        0,
        R.bool.config_cecPowerControlMode_userConfigurable,
        R.bool.config_cecPowerStateChangeOnActiveSourceLostNone_allowed);
    Setting registerSetting29 =
        registerSetting("query_sad_dtshd", R.bool.config_cecQuerySadDdEnabled_default);
    registerSetting29.registerValue(
        1, R.bool.config_cecQuerySadDdDisabled_default, R.bool.config_cecQuerySadDdEnabled_allowed);
    registerSetting29.registerValue(
        0,
        R.bool.config_cecQuerySadAtrac_userConfigurable,
        R.bool.config_cecQuerySadDdDisabled_allowed);
    Setting registerSetting30 =
        registerSetting("query_sad_truehd", R.bool.config_cecQuerySadMp3Enabled_default);
    registerSetting30.registerValue(
        1,
        R.bool.config_cecQuerySadMp3Disabled_default,
        R.bool.config_cecQuerySadMp3Enabled_allowed);
    registerSetting30.registerValue(
        0,
        R.bool.config_cecQuerySadMax_userConfigurable,
        R.bool.config_cecQuerySadMp3Disabled_allowed);
    Setting registerSetting31 =
        registerSetting("query_sad_dst", R.bool.config_cecQuerySadAacEnabled_default);
    registerSetting31.registerValue(
        1,
        R.bool.config_cecQuerySadAacDisabled_default,
        R.bool.config_cecQuerySadAacEnabled_allowed);
    registerSetting31.registerValue(
        0,
        R.bool.config_cecPowerStateChangeOnActiveSourceLost_userConfigurable,
        R.bool.config_cecQuerySadAacDisabled_allowed);
    Setting registerSetting32 =
        registerSetting("query_sad_wmapro", R.bool.config_cecQuerySadMpeg1Enabled_default);
    registerSetting32.registerValue(
        1,
        R.bool.config_cecQuerySadMpeg1Disabled_default,
        R.bool.config_cecQuerySadMpeg1Enabled_allowed);
    registerSetting32.registerValue(
        0,
        R.bool.config_cecQuerySadMp3_userConfigurable,
        R.bool.config_cecQuerySadMpeg1Disabled_allowed);
    Setting registerSetting33 =
        registerSetting("query_sad_max", R.bool.config_cecQuerySadDstEnabled_default);
    registerSetting33.registerValue(
        1,
        R.bool.config_cecQuerySadDstDisabled_default,
        R.bool.config_cecQuerySadDstEnabled_allowed);
    registerSetting33.registerValue(
        0,
        R.bool.config_cecQuerySadDdp_userConfigurable,
        R.bool.config_cecQuerySadDstDisabled_allowed);
    Setting registerSetting34 =
        registerSetting("earc_enabled", R.bool.config_displayBrightnessBucketsInDoze);
    registerSetting34.registerValue(
        1,
        R.bool.config_displayWhiteBalanceEnabledDefault,
        R.bool.config_displayWhiteBalanceLightModeAllowed);
    registerSetting34.registerValue(
        0, R.bool.config_displayColorFadeDisabled, R.bool.config_displayWhiteBalanceAvailable);
    verifySettings();
  }

  public HdmiCecConfig(Context context) {
    this(context, new StorageAdapter(context));
  }

  public final Setting registerSetting(String str, int i) {
    Setting setting = new Setting(this.mContext, str, i);
    this.mSettings.put(str, setting);
    return setting;
  }

  public final void verifySettings() {
    for (Setting setting : this.mSettings.values()) {
      setting.getDefaultValue();
      getStorage(setting);
      getStorageKey(setting);
    }
  }

  public final Setting getSetting(String str) {
    if (this.mSettings.containsKey(str)) {
      return (Setting) this.mSettings.get(str);
    }
    return null;
  }

  public final int getStorage(Setting setting) {
    String name = setting.getName();
    name.hashCode();
    switch (name) {
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
        return 2;
      default:
        throw new VerificationException("Invalid CEC setting '" + setting.getName() + "' storage.");
    }
  }

  public final String getStorageKey(Setting setting) {
    String name = setting.getName();
    name.hashCode();
    switch (name) {
      case "hdmi_cec_version":
        return setting.getName();
      case "system_audio_mode_muting":
        return setting.getName();
      case "set_menu_language":
        return setting.getName();
      case "rc_profile_source_handles_media_context_sensitive_menu":
        return setting.getName();
      case "rc_profile_source_handles_top_menu":
        return setting.getName();
      case "rc_profile_source_handles_root_menu":
        return setting.getName();
      case "query_sad_atrac":
        return setting.getName();
      case "query_sad_dtshd":
        return setting.getName();
      case "query_sad_mpeg1":
        return setting.getName();
      case "query_sad_mpeg2":
        return setting.getName();
      case "earc_enabled":
        return setting.getName();
      case "query_sad_truehd":
        return setting.getName();
      case "rc_profile_source_handles_contents_menu":
        return setting.getName();
      case "query_sad_wmapro":
        return setting.getName();
      case "routing_control":
        return setting.getName();
      case "query_sad_lpcm":
        return setting.getName();
      case "rc_profile_source_handles_setup_menu":
        return setting.getName();
      case "power_state_change_on_active_source_lost":
        return setting.getName();
      case "power_control_mode":
        return setting.getName();
      case "soundbar_mode":
        return setting.getName();
      case "volume_control_enabled":
        return setting.getName();
      case "hdmi_cec_enabled":
        return setting.getName();
      case "rc_profile_tv":
        return setting.getName();
      case "query_sad_onebitaudio":
        return setting.getName();
      case "query_sad_dd":
        return setting.getName();
      case "tv_wake_on_one_touch_play":
        return setting.getName();
      case "query_sad_aac":
        return setting.getName();
      case "query_sad_ddp":
        return setting.getName();
      case "query_sad_dst":
        return setting.getName();
      case "query_sad_dts":
        return setting.getName();
      case "query_sad_max":
        return setting.getName();
      case "query_sad_mp3":
        return setting.getName();
      case "tv_send_standby_on_sleep":
        return setting.getName();
      case "system_audio_control":
        return setting.getName();
      default:
        throw new VerificationException(
            "Invalid CEC setting '" + setting.getName() + "' storage key.");
    }
  }

  public String retrieveValue(Setting setting, String str) {
    int storage = getStorage(setting);
    String storageKey = getStorageKey(setting);
    if (storage == 0) {
      HdmiLogger.debug("Reading '" + storageKey + "' sysprop.", new Object[0]);
      return this.mStorageAdapter.retrieveSystemProperty(storageKey, str);
    }
    if (storage == 1) {
      HdmiLogger.debug("Reading '" + storageKey + "' global setting.", new Object[0]);
      return this.mStorageAdapter.retrieveGlobalSetting(storageKey, str);
    }
    if (storage != 2) {
      return null;
    }
    HdmiLogger.debug("Reading '" + storageKey + "' shared preference.", new Object[0]);
    return this.mStorageAdapter.retrieveSharedPref(storageKey, str);
  }

  public void storeValue(Setting setting, String str) {
    int storage = getStorage(setting);
    String storageKey = getStorageKey(setting);
    if (storage == 0) {
      HdmiLogger.debug("Setting '" + storageKey + "' sysprop.", new Object[0]);
      this.mStorageAdapter.storeSystemProperty(storageKey, str);
      return;
    }
    if (storage == 1) {
      HdmiLogger.debug("Setting '" + storageKey + "' global setting.", new Object[0]);
      this.mStorageAdapter.storeGlobalSetting(storageKey, str);
      return;
    }
    if (storage == 2) {
      HdmiLogger.debug("Setting '" + storageKey + "' shared pref.", new Object[0]);
      this.mStorageAdapter.storeSharedPref(storageKey, str);
      notifySettingChanged(setting);
    }
  }

  public void notifySettingChanged(final Setting setting) {
    synchronized (this.mLock) {
      ArrayMap arrayMap = (ArrayMap) this.mSettingChangeListeners.get(setting);
      if (arrayMap == null) {
        return;
      }
      for (Map.Entry entry : arrayMap.entrySet()) {
        final SettingChangeListener settingChangeListener = (SettingChangeListener) entry.getKey();
        ((Executor) entry.getValue())
            .execute(
                new Runnable() { // from class: com.android.server.hdmi.HdmiCecConfig.1
                  @Override // java.lang.Runnable
                  public void run() {
                    settingChangeListener.onChange(setting.getName());
                  }
                });
      }
    }
  }

  public void registerChangeListener(String str, SettingChangeListener settingChangeListener) {
    registerChangeListener(str, settingChangeListener, ConcurrentUtils.DIRECT_EXECUTOR);
  }

  public void registerChangeListener(
      String str, SettingChangeListener settingChangeListener, Executor executor) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    int storage = getStorage(setting);
    if (storage != 1 && storage != 2) {
      throw new IllegalArgumentException(
          "Change listeners for setting '" + str + "' not supported.");
    }
    synchronized (this.mLock) {
      if (!this.mSettingChangeListeners.containsKey(setting)) {
        this.mSettingChangeListeners.put(setting, new ArrayMap());
      }
      ((ArrayMap) this.mSettingChangeListeners.get(setting)).put(settingChangeListener, executor);
    }
  }

  public void removeChangeListener(String str, SettingChangeListener settingChangeListener) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    synchronized (this.mLock) {
      if (this.mSettingChangeListeners.containsKey(setting)) {
        ArrayMap arrayMap = (ArrayMap) this.mSettingChangeListeners.get(setting);
        arrayMap.remove(settingChangeListener);
        if (arrayMap.isEmpty()) {
          this.mSettingChangeListeners.remove(setting);
        }
      }
    }
  }

  public List getAllSettings() {
    return new ArrayList(this.mSettings.keySet());
  }

  public List getUserSettings() {
    ArrayList arrayList = new ArrayList();
    for (Setting setting : this.mSettings.values()) {
      if (setting.getUserConfigurable()) {
        arrayList.add(setting.getName());
      }
    }
    return arrayList;
  }

  public boolean isStringValueType(String str) {
    if (getSetting(str) == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    return getSetting(str).getValueType().equals("string");
  }

  public boolean isIntValueType(String str) {
    if (getSetting(str) == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    return getSetting(str).getValueType().equals("int");
  }

  public List getAllowedStringValues(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("string")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    ArrayList arrayList = new ArrayList();
    Iterator it = setting.getAllowedValues().iterator();
    while (it.hasNext()) {
      arrayList.add(((Value) it.next()).getStringValue());
    }
    return arrayList;
  }

  public List getAllowedIntValues(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("int")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    ArrayList arrayList = new ArrayList();
    Iterator it = setting.getAllowedValues().iterator();
    while (it.hasNext()) {
      arrayList.add(((Value) it.next()).getIntValue());
    }
    return arrayList;
  }

  public String getDefaultStringValue(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("string")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    return getSetting(str).getDefaultValue().getStringValue();
  }

  public int getDefaultIntValue(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("int")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    return getSetting(str).getDefaultValue().getIntValue().intValue();
  }

  public String getStringValue(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("string")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    HdmiLogger.debug("Getting CEC setting value '" + str + "'.", new Object[0]);
    return retrieveValue(setting, setting.getDefaultValue().getStringValue());
  }

  public int getIntValue(String str) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getValueType().equals("int")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a int-type setting.");
    }
    HdmiLogger.debug("Getting CEC setting value '" + str + "'.", new Object[0]);
    return Integer.parseInt(
        retrieveValue(
            setting, Integer.toString(setting.getDefaultValue().getIntValue().intValue())));
  }

  public void setStringValue(String str, String str2) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getUserConfigurable()) {
      throw new IllegalArgumentException("Updating CEC setting '" + str + "' prohibited.");
    }
    if (!setting.getValueType().equals("string")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a string-type setting.");
    }
    if (!getAllowedStringValues(str).contains(str2)) {
      throw new IllegalArgumentException(
          "Invalid CEC setting '" + str + "' value: '" + str2 + "'.");
    }
    HdmiLogger.debug("Updating CEC setting '" + str + "' to '" + str2 + "'.", new Object[0]);
    storeValue(setting, str2);
  }

  public void setIntValue(String str, int i) {
    Setting setting = getSetting(str);
    if (setting == null) {
      throw new IllegalArgumentException("Setting '" + str + "' does not exist.");
    }
    if (!setting.getUserConfigurable()) {
      throw new IllegalArgumentException("Updating CEC setting '" + str + "' prohibited.");
    }
    if (!setting.getValueType().equals("int")) {
      throw new IllegalArgumentException("Setting '" + str + "' is not a int-type setting.");
    }
    if (!getAllowedIntValues(str).contains(Integer.valueOf(i))) {
      throw new IllegalArgumentException("Invalid CEC setting '" + str + "' value: '" + i + "'.");
    }
    HdmiLogger.debug("Updating CEC setting '" + str + "' to '" + i + "'.", new Object[0]);
    storeValue(setting, Integer.toString(i));
  }
}
