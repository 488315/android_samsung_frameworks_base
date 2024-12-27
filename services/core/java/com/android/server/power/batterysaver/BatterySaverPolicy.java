package com.android.server.power.batterysaver;

import android.R;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.BatterySaverPolicyConfig;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerSaveState;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.KeyValueListParser;

import com.android.internal.os.BackgroundThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;
import com.android.server.power.Slog;
import com.android.server.utils.UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatterySaverPolicy extends ContentObserver
        implements DeviceConfig.OnPropertiesChangedListener {
    public static final Policy DEFAULT_ADAPTIVE_POLICY;
    static final String KEY_ADJUST_BRIGHTNESS_FACTOR = "adjust_brightness_factor";
    static final String KEY_ADVERTISE_IS_ENABLED = "advertise_is_enabled";
    static final String KEY_DEFER_FULL_BACKUP = "defer_full_backup";
    static final String KEY_DEFER_KEYVALUE_BACKUP = "defer_keyvalue_backup";
    static final String KEY_DISABLE_ANIMATION = "disable_animation";
    static final String KEY_DISABLE_AOD = "disable_aod";
    static final String KEY_DISABLE_LAUNCH_BOOST = "disable_launch_boost";
    static final String KEY_DISABLE_OPTIONAL_SENSORS = "disable_optional_sensors";
    static final String KEY_DISABLE_VIBRATION = "disable_vibration";
    static final String KEY_ENABLE_BRIGHTNESS_ADJUSTMENT = "enable_brightness_adjustment";
    static final String KEY_ENABLE_DATASAVER = "enable_datasaver";
    static final String KEY_ENABLE_FIREWALL = "enable_firewall";
    static final String KEY_ENABLE_NIGHT_MODE = "enable_night_mode";
    static final String KEY_ENABLE_QUICK_DOZE = "enable_quick_doze";
    static final String KEY_FORCE_ALL_APPS_STANDBY = "force_all_apps_standby";
    static final String KEY_FORCE_BACKGROUND_CHECK = "force_background_check";
    static final String KEY_LOCATION_MODE = "location_mode";
    static final String KEY_SOUNDTRIGGER_MODE = "soundtrigger_mode";
    static final Policy OFF_POLICY;
    public final Policy DEFAULT_FULL_POLICY;
    final PolicyBoolean mAccessibilityEnabled;
    public Policy mAdaptivePolicy;
    final PolicyBoolean mAutomotiveProjectionActive;
    public final BatterySavingStats mBatterySavingStats;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public Policy mDefaultAdaptivePolicy;
    public Policy mDefaultFullPolicy;
    public String mDeviceSpecificSettings;
    public String mDeviceSpecificSettingsSource;
    public Policy mEffectivePolicyRaw;
    public String mEventLogKeys;
    public Policy mFullPolicy;
    public final Handler mHandler;
    public DeviceConfig.Properties mLastDeviceConfigProperties;
    public final List mListeners;
    public final Object mLock;
    public final BatterySaverPolicy$$ExternalSyntheticLambda1 mOnProjectionStateChangedListener;
    public int mPolicyLevel;
    public String mSettings;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BatterySaverPolicyListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Policy {
        public final float adjustBrightnessFactor;
        public final boolean advertiseIsEnabled;
        public final boolean deferFullBackup;
        public final boolean deferKeyValueBackup;
        public final boolean disableAnimation;
        public final boolean disableAod;
        public final boolean disableLaunchBoost;
        public final boolean disableOptionalSensors;
        public final boolean disableVibration;
        public final boolean enableAdjustBrightness;
        public final boolean enableDataSaver;
        public final boolean enableFirewall;
        public final boolean enableNightMode;
        public final boolean enableQuickDoze;
        public final boolean forceAllAppsStandby;
        public final boolean forceBackgroundCheck;
        public final int locationMode;
        public final int mHashCode;
        public final int soundTriggerMode;

        public Policy(
                float f,
                boolean z,
                boolean z2,
                boolean z3,
                boolean z4,
                boolean z5,
                boolean z6,
                boolean z7,
                boolean z8,
                boolean z9,
                boolean z10,
                boolean z11,
                boolean z12,
                boolean z13,
                boolean z14,
                boolean z15,
                int i,
                int i2) {
            this.adjustBrightnessFactor =
                    Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, f));
            this.advertiseIsEnabled = z;
            this.deferFullBackup = z2;
            this.deferKeyValueBackup = z3;
            this.disableAnimation = z4;
            this.disableAod = z5;
            this.disableLaunchBoost = z6;
            this.disableOptionalSensors = z7;
            this.disableVibration = z8;
            this.enableAdjustBrightness = z9;
            this.enableDataSaver = z10;
            this.enableFirewall = z11;
            this.enableNightMode = z12;
            this.enableQuickDoze = z13;
            this.forceAllAppsStandby = z14;
            this.forceBackgroundCheck = z15;
            if (i < 0 || 4 < i) {
                Slog.e("BatterySaverPolicy", "Invalid location mode: " + i);
                this.locationMode = 0;
            } else {
                this.locationMode = i;
            }
            if (i2 < 0 || i2 > 2) {
                Slog.e("BatterySaverPolicy", "Invalid SoundTrigger mode: " + i2);
                this.soundTriggerMode = 0;
            } else {
                this.soundTriggerMode = i2;
            }
            this.mHashCode =
                    Objects.hash(
                            Float.valueOf(f),
                            Boolean.valueOf(z),
                            Boolean.valueOf(z2),
                            Boolean.valueOf(z3),
                            Boolean.valueOf(z4),
                            Boolean.valueOf(z5),
                            Boolean.valueOf(z6),
                            Boolean.valueOf(z7),
                            Boolean.valueOf(z8),
                            Boolean.valueOf(z9),
                            Boolean.valueOf(z10),
                            Boolean.valueOf(z11),
                            Boolean.valueOf(z12),
                            Boolean.valueOf(z13),
                            Boolean.valueOf(z14),
                            Boolean.valueOf(z15),
                            Integer.valueOf(i),
                            Integer.valueOf(i2));
        }

        public static Policy fromConfig(BatterySaverPolicyConfig batterySaverPolicyConfig) {
            if (batterySaverPolicyConfig == null) {
                Slog.e("BatterySaverPolicy", "Null config passed down to BatterySaverPolicy");
                return BatterySaverPolicy.OFF_POLICY;
            }
            batterySaverPolicyConfig.getDeviceSpecificSettings();
            return new Policy(
                    batterySaverPolicyConfig.getAdjustBrightnessFactor(),
                    batterySaverPolicyConfig.getAdvertiseIsEnabled(),
                    batterySaverPolicyConfig.getDeferFullBackup(),
                    batterySaverPolicyConfig.getDeferKeyValueBackup(),
                    batterySaverPolicyConfig.getDisableAnimation(),
                    batterySaverPolicyConfig.getDisableAod(),
                    batterySaverPolicyConfig.getDisableLaunchBoost(),
                    batterySaverPolicyConfig.getDisableOptionalSensors(),
                    batterySaverPolicyConfig.getDisableVibration(),
                    batterySaverPolicyConfig.getEnableAdjustBrightness(),
                    batterySaverPolicyConfig.getEnableDataSaver(),
                    batterySaverPolicyConfig.getEnableFirewall(),
                    batterySaverPolicyConfig.getEnableNightMode(),
                    batterySaverPolicyConfig.getEnableQuickDoze(),
                    batterySaverPolicyConfig.getForceAllAppsStandby(),
                    batterySaverPolicyConfig.getForceBackgroundCheck(),
                    batterySaverPolicyConfig.getLocationMode(),
                    batterySaverPolicyConfig.getSoundTriggerMode());
        }

        public static Policy fromSettings(
                String str, String str2, DeviceConfig.Properties properties, String str3) {
            return fromSettings(str, str2, properties, str3, BatterySaverPolicy.OFF_POLICY);
        }

        public static Policy fromSettings(
                String str,
                String str2,
                DeviceConfig.Properties properties,
                String str3,
                Policy policy) {
            UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator =
                            new UserSettingDeviceConfigMediator$SettingsOverridesIndividualMediator();
            String emptyIfNull = TextUtils.emptyIfNull(str3);
            try {
                userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mSettingsParser
                        .setString(str2);
            } catch (IllegalArgumentException unused) {
                String m =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Bad device specific battery saver constants: ", str2);
                int i = Slog.$r8$clinit;
                android.util.Slog.wtf("BatterySaverPolicy", m);
            }
            try {
                userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mSettingsParser
                        .setString(str);
                userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mProperties =
                        properties;
            } catch (IllegalArgumentException unused2) {
                String m2 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Bad battery saver constants: ", str);
                int i2 = Slog.$r8$clinit;
                android.util.Slog.wtf("BatterySaverPolicy", m2);
            }
            float f =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getFloat(
                            policy.adjustBrightnessFactor,
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ADJUST_BRIGHTNESS_FACTOR, emptyIfNull));
            boolean z =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ADVERTISE_IS_ENABLED, emptyIfNull),
                            policy.advertiseIsEnabled);
            boolean z2 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DEFER_FULL_BACKUP, emptyIfNull),
                            policy.deferFullBackup);
            boolean z3 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DEFER_KEYVALUE_BACKUP, emptyIfNull),
                            policy.deferKeyValueBackup);
            boolean z4 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DISABLE_ANIMATION, emptyIfNull),
                            policy.disableAnimation);
            boolean z5 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DISABLE_AOD, emptyIfNull),
                            policy.disableAod);
            boolean z6 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DISABLE_LAUNCH_BOOST, emptyIfNull),
                            policy.disableLaunchBoost);
            boolean z7 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DISABLE_OPTIONAL_SENSORS, emptyIfNull),
                            policy.disableOptionalSensors);
            boolean z8 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_DISABLE_VIBRATION, emptyIfNull),
                            policy.disableVibration);
            boolean z9 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ENABLE_BRIGHTNESS_ADJUSTMENT,
                                    emptyIfNull),
                            policy.enableAdjustBrightness);
            boolean z10 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ENABLE_DATASAVER, emptyIfNull),
                            policy.enableDataSaver);
            boolean z11 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ENABLE_FIREWALL, emptyIfNull),
                            policy.enableFirewall);
            boolean z12 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ENABLE_NIGHT_MODE, emptyIfNull),
                            policy.enableNightMode);
            boolean z13 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_ENABLE_QUICK_DOZE, emptyIfNull),
                            policy.enableQuickDoze);
            boolean z14 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_FORCE_ALL_APPS_STANDBY, emptyIfNull),
                            policy.forceAllAppsStandby);
            boolean z15 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.getBoolean(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    BatterySaverPolicy.KEY_FORCE_BACKGROUND_CHECK, emptyIfNull),
                            policy.forceBackgroundCheck);
            String m3 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            BatterySaverPolicy.KEY_LOCATION_MODE, emptyIfNull);
            KeyValueListParser keyValueListParser =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator
                            .mSettingsParser;
            DeviceConfig.Properties properties2 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mProperties;
            int i3 = policy.locationMode;
            if (properties2 != null) {
                i3 = properties2.getInt(m3, i3);
            }
            int i4 = keyValueListParser.getInt(m3, i3);
            String m4 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            BatterySaverPolicy.KEY_SOUNDTRIGGER_MODE, emptyIfNull);
            KeyValueListParser keyValueListParser2 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator
                            .mSettingsParser;
            DeviceConfig.Properties properties3 =
                    userSettingDeviceConfigMediator$SettingsOverridesIndividualMediator.mProperties;
            int i5 = policy.soundTriggerMode;
            if (properties3 != null) {
                i5 = properties3.getInt(m4, i5);
            }
            return new Policy(
                    f,
                    z,
                    z2,
                    z3,
                    z4,
                    z5,
                    z6,
                    z7,
                    z8,
                    z9,
                    z10,
                    z11,
                    z12,
                    z13,
                    z14,
                    z15,
                    i4,
                    keyValueListParser2.getInt(m4, i5));
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Policy)) {
                return false;
            }
            Policy policy = (Policy) obj;
            return Float.compare(policy.adjustBrightnessFactor, this.adjustBrightnessFactor) == 0
                    && this.advertiseIsEnabled == policy.advertiseIsEnabled
                    && this.deferFullBackup == policy.deferFullBackup
                    && this.deferKeyValueBackup == policy.deferKeyValueBackup
                    && this.disableAnimation == policy.disableAnimation
                    && this.disableAod == policy.disableAod
                    && this.disableLaunchBoost == policy.disableLaunchBoost
                    && this.disableOptionalSensors == policy.disableOptionalSensors
                    && this.disableVibration == policy.disableVibration
                    && this.enableAdjustBrightness == policy.enableAdjustBrightness
                    && this.enableDataSaver == policy.enableDataSaver
                    && this.enableFirewall == policy.enableFirewall
                    && this.enableNightMode == policy.enableNightMode
                    && this.enableQuickDoze == policy.enableQuickDoze
                    && this.forceAllAppsStandby == policy.forceAllAppsStandby
                    && this.forceBackgroundCheck == policy.forceBackgroundCheck
                    && this.locationMode == policy.locationMode
                    && this.soundTriggerMode == policy.soundTriggerMode;
        }

        public final int hashCode() {
            return this.mHashCode;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PolicyBoolean {
        public final String mDebugName;
        public boolean mValue;

        /* renamed from: -$$Nest$mget, reason: not valid java name */
        public static boolean m830$$Nest$mget(PolicyBoolean policyBoolean) {
            boolean z;
            synchronized (BatterySaverPolicy.this.mLock) {
                z = policyBoolean.mValue;
            }
            return z;
        }

        public PolicyBoolean(String str) {
            this.mDebugName = str;
        }

        public void update(boolean z) {
            synchronized (BatterySaverPolicy.this.mLock) {
                try {
                    if (this.mValue != z) {
                        Slog.d(
                                "BatterySaverPolicy",
                                this.mDebugName + " changed to " + z + ", updating policy.");
                        this.mValue = z;
                        BatterySaverPolicy.this.updatePolicyDependenciesLocked();
                        BatterySaverPolicy.this.maybeNotifyListenersOfPolicyChange();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static {
        Policy policy =
                new Policy(
                        1.0f, false, false, false, false, false, false, false, false, false, false,
                        false, false, false, false, false, 0, 0);
        OFF_POLICY = policy;
        DEFAULT_ADAPTIVE_POLICY = policy;
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda1] */
    public BatterySaverPolicy(Object obj, Context context, BatterySavingStats batterySavingStats) {
        super(BackgroundThread.getHandler());
        this.mAccessibilityEnabled = new PolicyBoolean("accessibility");
        this.mAutomotiveProjectionActive = new PolicyBoolean("automotiveProjection");
        Policy policy = DEFAULT_ADAPTIVE_POLICY;
        this.mDefaultAdaptivePolicy = policy;
        this.mAdaptivePolicy = policy;
        this.mEffectivePolicyRaw = OFF_POLICY;
        this.mPolicyLevel = 0;
        this.mOnProjectionStateChangedListener =
                new UiModeManager
                        .OnProjectionStateChangedListener() { // from class:
                                                              // com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda1
                    public final void onProjectionStateChanged(int i, Set set) {
                        BatterySaverPolicy.this.mAutomotiveProjectionActive.update(!set.isEmpty());
                    }
                };
        this.mListeners = new ArrayList();
        this.mLock = obj;
        this.mHandler = BackgroundThread.getHandler();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mBatterySavingStats = batterySavingStats;
        Resources resources = context.getResources();
        Policy policy2 =
                new Policy(
                        0.9f,
                        true,
                        resources.getBoolean(R.bool.config_batterySaver_full_disableAod),
                        resources.getBoolean(R.bool.config_batterySaver_full_disableLaunchBoost),
                        resources.getBoolean(
                                R.bool.config_batterySaver_full_disableOptionalSensors),
                        resources.getBoolean(R.bool.config_batterySaver_full_disableVibration),
                        resources.getBoolean(
                                R.bool.config_batterySaver_full_enableAdjustBrightness),
                        resources.getBoolean(R.bool.config_batterySaver_full_enableDataSaver),
                        resources.getBoolean(R.bool.config_batterySaver_full_enableFirewall),
                        true,
                        resources.getBoolean(R.bool.config_batterySaver_full_enableQuickDoze),
                        resources.getBoolean(R.bool.config_batterySaver_full_forceAllAppsStandby),
                        false,
                        resources.getBoolean(R.bool.config_batterySdCardAccessibility),
                        resources.getBoolean(
                                R.bool.config_batteryStatsResetOnUnplugAfterSignificantCharge),
                        resources.getBoolean(
                                R.bool.config_batteryStatsResetOnUnplugHighBatteryLevel),
                        1,
                        resources.getInteger(R.integer.config_criticalBatteryWarningLevel));
        this.DEFAULT_FULL_POLICY = policy2;
        this.mDefaultFullPolicy = policy2;
        this.mFullPolicy = policy2;
    }

    public static void dumpPolicyLocked(
            IndentingPrintWriter indentingPrintWriter, String str, Policy policy) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("Policy '" + str + "'");
        indentingPrintWriter.increaseIndent();
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                DesktopModeService$$ExternalSyntheticOutline0.m(
                                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                                DesktopModeService$$ExternalSyntheticOutline0.m(
                                                        DesktopModeService$$ExternalSyntheticOutline0
                                                                .m(
                                                                        DesktopModeService$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        DesktopModeService$$ExternalSyntheticOutline0
                                                                                                .m(
                                                                                                        DesktopModeService$$ExternalSyntheticOutline0
                                                                                                                .m(
                                                                                                                        new StringBuilder(
                                                                                                                                "advertise_is_enabled="),
                                                                                                                        policy.advertiseIsEnabled,
                                                                                                                        indentingPrintWriter,
                                                                                                                        "disable_vibration="),
                                                                                                        policy.disableVibration,
                                                                                                        indentingPrintWriter,
                                                                                                        "disable_animation="),
                                                                                        policy.disableAnimation,
                                                                                        indentingPrintWriter,
                                                                                        "defer_full_backup="),
                                                                        policy.deferFullBackup,
                                                                        indentingPrintWriter,
                                                                        "defer_keyvalue_backup="),
                                                        policy.deferKeyValueBackup,
                                                        indentingPrintWriter,
                                                        "enable_firewall="),
                                                policy.enableFirewall,
                                                indentingPrintWriter,
                                                "enable_datasaver="),
                                        policy.enableDataSaver,
                                        indentingPrintWriter,
                                        "disable_launch_boost="),
                                policy.disableLaunchBoost,
                                indentingPrintWriter,
                                "enable_brightness_adjustment="),
                        policy.enableAdjustBrightness,
                        indentingPrintWriter,
                        "adjust_brightness_factor=");
        m.append(policy.adjustBrightnessFactor);
        indentingPrintWriter.println(m.toString());
        indentingPrintWriter.println("location_mode=" + policy.locationMode);
        StringBuilder m2 =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                DesktopModeService$$ExternalSyntheticOutline0.m(
                                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("force_all_apps_standby="),
                                                policy.forceAllAppsStandby,
                                                indentingPrintWriter,
                                                "force_background_check="),
                                        policy.forceBackgroundCheck,
                                        indentingPrintWriter,
                                        "disable_optional_sensors="),
                                policy.disableOptionalSensors,
                                indentingPrintWriter,
                                "disable_aod="),
                        policy.disableAod,
                        indentingPrintWriter,
                        "soundtrigger_mode=");
        m2.append(policy.soundTriggerMode);
        indentingPrintWriter.println(m2.toString());
        StringBuilder m3 =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("enable_quick_doze="),
                        policy.enableQuickDoze,
                        indentingPrintWriter,
                        "enable_night_mode=");
        m3.append(policy.enableNightMode);
        indentingPrintWriter.println(m3.toString());
        indentingPrintWriter.decreaseIndent();
    }

    public void addListener(BatterySaverPolicyListener batterySaverPolicyListener) {
        synchronized (this.mLock) {
            ((ArrayList) this.mListeners).add(batterySaverPolicyListener);
        }
    }

    public final void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println();
                this.mBatterySavingStats.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println(
                        "Battery saver policy (*NOTE* they only apply when battery saver is ON):");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Settings: battery_saver_constants");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("value: " + this.mSettings);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("Settings: " + this.mDeviceSpecificSettingsSource);
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("value: " + this.mDeviceSpecificSettings);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("DeviceConfig: battery_saver");
                indentingPrintWriter.increaseIndent();
                Set<String> keyset = this.mLastDeviceConfigProperties.getKeyset();
                if (keyset.size() == 0) {
                    indentingPrintWriter.println("N/A");
                } else {
                    for (String str : keyset) {
                        indentingPrintWriter.print(str);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(
                                this.mLastDeviceConfigProperties.getString(str, (String) null));
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println(
                        "mAccessibilityEnabled="
                                + PolicyBoolean.m830$$Nest$mget(this.mAccessibilityEnabled));
                indentingPrintWriter.println(
                        "mAutomotiveProjectionActive="
                                + PolicyBoolean.m830$$Nest$mget(this.mAutomotiveProjectionActive));
                indentingPrintWriter.println("mPolicyLevel=" + this.mPolicyLevel);
                dumpPolicyLocked(indentingPrintWriter, "default full", this.mDefaultFullPolicy);
                dumpPolicyLocked(indentingPrintWriter, "current full", this.mFullPolicy);
                dumpPolicyLocked(
                        indentingPrintWriter, "default adaptive", this.mDefaultAdaptivePolicy);
                dumpPolicyLocked(indentingPrintWriter, "current adaptive", this.mAdaptivePolicy);
                dumpPolicyLocked(indentingPrintWriter, "effective", this.mEffectivePolicyRaw);
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PowerSaveState getBatterySaverPolicy(int i) {
        synchronized (this.mLock) {
            try {
                Policy policy = this.mEffectivePolicyRaw;
                PowerSaveState.Builder globalBatterySaverEnabled =
                        new PowerSaveState.Builder()
                                .setGlobalBatterySaverEnabled(policy.advertiseIsEnabled);
                boolean z = true;
                switch (i) {
                    case 1:
                        if (!policy.advertiseIsEnabled && policy.locationMode == 0) {
                            z = false;
                        }
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(z)
                                .setLocationMode(policy.locationMode)
                                .build();
                    case 2:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.disableVibration)
                                .build();
                    case 3:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.disableAnimation)
                                .build();
                    case 4:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.deferFullBackup)
                                .build();
                    case 5:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.deferKeyValueBackup)
                                .build();
                    case 6:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.enableFirewall)
                                .build();
                    case 7:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.enableAdjustBrightness)
                                .setBrightnessFactor(policy.adjustBrightnessFactor)
                                .build();
                    case 8:
                        if (!policy.advertiseIsEnabled && policy.soundTriggerMode == 0) {
                            z = false;
                        }
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(z)
                                .setSoundTriggerMode(policy.soundTriggerMode)
                                .build();
                    case 9:
                    default:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.advertiseIsEnabled)
                                .build();
                    case 10:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.enableDataSaver)
                                .build();
                    case 11:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.forceAllAppsStandby)
                                .build();
                    case 12:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.forceBackgroundCheck)
                                .build();
                    case 13:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.disableOptionalSensors)
                                .build();
                    case 14:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.disableAod)
                                .build();
                    case 15:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.enableQuickDoze)
                                .build();
                    case 16:
                        return globalBatterySaverEnabled
                                .setBatterySaverEnabled(policy.enableNightMode)
                                .build();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int getDeviceSpecificConfigResId() {
        return R.string.console_running_notification_message;
    }

    public String getGlobalSetting(String str) {
        return Settings.Global.getString(this.mContentResolver, str);
    }

    public void invalidatePowerSaveModeCaches() {
        PowerManager.invalidatePowerSaveModeCaches();
    }

    public final void maybeNotifyListenersOfPolicyChange() {
        synchronized (this.mLock) {
            try {
                if (this.mPolicyLevel == 0) {
                    return;
                }
                List list = this.mListeners;
                final BatterySaverPolicyListener[] batterySaverPolicyListenerArr =
                        (BatterySaverPolicyListener[])
                                ((ArrayList) list)
                                        .toArray(
                                                new BatterySaverPolicyListener
                                                        [((ArrayList) list).size()]);
                this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.power.batterysaver.BatterySaverPolicy$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BatterySaverPolicy batterySaverPolicy = BatterySaverPolicy.this;
                                BatterySaverPolicy.BatterySaverPolicyListener[]
                                        batterySaverPolicyListenerArr2 =
                                                batterySaverPolicyListenerArr;
                                batterySaverPolicy.getClass();
                                for (BatterySaverPolicy.BatterySaverPolicyListener
                                        batterySaverPolicyListener :
                                                batterySaverPolicyListenerArr2) {
                                    BatterySaverController batterySaverController =
                                            (BatterySaverController) batterySaverPolicyListener;
                                    if (batterySaverController.isPolicyEnabled()) {
                                        batterySaverController.mHandler.postStateChanged(6, true);
                                    }
                                }
                            }
                        });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean maybeUpdateDefaultFullPolicy(Policy policy) {
        boolean z = false;
        if (!this.mDefaultFullPolicy.equals(policy)) {
            if (!(!this.mDefaultFullPolicy.equals(this.mFullPolicy))) {
                this.mFullPolicy = policy;
                if (this.mPolicyLevel == 2) {
                    z = true;
                }
            }
            this.mDefaultFullPolicy = policy;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005b A[Catch: all -> 0x0025, DONT_GENERATE, TryCatch #0 {all -> 0x0025, blocks: (B:4:0x0005, B:6:0x001b, B:9:0x0035, B:11:0x005b, B:15:0x005d, B:18:0x0027), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005d A[Catch: all -> 0x0025, DONT_GENERATE, TRY_LEAVE, TryCatch #0 {all -> 0x0025, blocks: (B:4:0x0005, B:6:0x001b, B:9:0x0035, B:11:0x005b, B:15:0x005d, B:18:0x0027), top: B:3:0x0005 }] */
    @Override // android.database.ContentObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onChange(boolean r5, android.net.Uri r6) {
        /*
            r4 = this;
            java.lang.String r5 = "[api] refreshSettings: deviceSpecificSetting: "
            java.lang.Object r6 = r4.mLock
            monitor-enter(r6)
            java.lang.String r0 = "battery_saver_constants"
            java.lang.String r0 = r4.getGlobalSetting(r0)     // Catch: java.lang.Throwable -> L25
            java.lang.String r1 = "battery_saver_device_specific_constants"
            java.lang.String r1 = r4.getGlobalSetting(r1)     // Catch: java.lang.Throwable -> L25
            java.lang.String r2 = "battery_saver_device_specific_constants"
            r4.mDeviceSpecificSettingsSource = r2     // Catch: java.lang.Throwable -> L25
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L25
            if (r2 != 0) goto L27
            java.lang.String r2 = "null"
            boolean r2 = r2.equals(r1)     // Catch: java.lang.Throwable -> L25
            if (r2 == 0) goto L35
            goto L27
        L25:
            r4 = move-exception
            goto L62
        L27:
            android.content.Context r1 = r4.mContext     // Catch: java.lang.Throwable -> L25
            int r2 = r4.getDeviceSpecificConfigResId()     // Catch: java.lang.Throwable -> L25
            java.lang.String r1 = r1.getString(r2)     // Catch: java.lang.Throwable -> L25
            java.lang.String r2 = "(overlay)"
            r4.mDeviceSpecificSettingsSource = r2     // Catch: java.lang.Throwable -> L25
        L35:
            java.lang.String r2 = "BatterySaverPolicy"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L25
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L25
            r3.append(r1)     // Catch: java.lang.Throwable -> L25
            java.lang.String r5 = " ("
            r3.append(r5)     // Catch: java.lang.Throwable -> L25
            java.lang.String r5 = r4.mDeviceSpecificSettingsSource     // Catch: java.lang.Throwable -> L25
            r3.append(r5)     // Catch: java.lang.Throwable -> L25
            java.lang.String r5 = ")"
            r3.append(r5)     // Catch: java.lang.Throwable -> L25
            java.lang.String r5 = r3.toString()     // Catch: java.lang.Throwable -> L25
            com.android.server.power.Slog.d(r2, r5)     // Catch: java.lang.Throwable -> L25
            boolean r5 = r4.updateConstantsLocked(r0, r1)     // Catch: java.lang.Throwable -> L25
            if (r5 != 0) goto L5d
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L25
            goto L61
        L5d:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L25
            r4.maybeNotifyListenersOfPolicyChange()
        L61:
            return
        L62:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L25
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.batterysaver.BatterySaverPolicy.onChange(boolean,"
                    + " android.net.Uri):void");
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        boolean maybeUpdateDefaultFullPolicy;
        this.mLastDeviceConfigProperties =
                DeviceConfig.getProperties("battery_saver", new String[0]);
        synchronized (this.mLock) {
            try {
                Policy policy = null;
                Policy policy2 = null;
                for (String str : properties.getKeyset()) {
                    if (str != null) {
                        if (str.endsWith("_adaptive")) {
                            if (policy2 == null) {
                                policy2 =
                                        Policy.fromSettings(
                                                "",
                                                "",
                                                this.mLastDeviceConfigProperties,
                                                "_adaptive",
                                                DEFAULT_ADAPTIVE_POLICY);
                            }
                        } else if (policy == null) {
                            policy =
                                    Policy.fromSettings(
                                            this.mSettings,
                                            this.mDeviceSpecificSettings,
                                            this.mLastDeviceConfigProperties,
                                            null,
                                            this.DEFAULT_FULL_POLICY);
                        }
                    }
                }
                maybeUpdateDefaultFullPolicy =
                        policy != null ? maybeUpdateDefaultFullPolicy(policy) : false;
                if (policy2 != null && !this.mAdaptivePolicy.equals(policy2)) {
                    this.mDefaultAdaptivePolicy = policy2;
                    this.mAdaptivePolicy = policy2;
                    maybeUpdateDefaultFullPolicy |= this.mPolicyLevel == 1;
                }
                updatePolicyDependenciesLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (maybeUpdateDefaultFullPolicy) {
            maybeNotifyListenersOfPolicyChange();
        }
    }

    public final boolean setPolicyLevel(int i) {
        synchronized (this.mLock) {
            try {
                int i2 = this.mPolicyLevel;
                if (i2 == i) {
                    return false;
                }
                if (i2 == 2) {
                    this.mFullPolicy = this.mDefaultFullPolicy;
                }
                if (i == 0 || i == 1 || i == 2) {
                    this.mPolicyLevel = i;
                    updatePolicyDependenciesLocked();
                    return true;
                }
                int i3 = Slog.$r8$clinit;
                android.util.Slog.wtf(
                        "BatterySaverPolicy", "setPolicyLevel invalid level given: " + i);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean shouldAdvertiseIsEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEffectivePolicyRaw.advertiseIsEnabled;
        }
        return z;
    }

    public boolean updateConstantsLocked(String str, String str2) {
        String emptyIfNull = TextUtils.emptyIfNull(str);
        String emptyIfNull2 = TextUtils.emptyIfNull(str2);
        if (emptyIfNull.equals(this.mSettings)
                && emptyIfNull2.equals(this.mDeviceSpecificSettings)) {
            return false;
        }
        this.mSettings = emptyIfNull;
        this.mDeviceSpecificSettings = emptyIfNull2;
        boolean maybeUpdateDefaultFullPolicy =
                maybeUpdateDefaultFullPolicy(
                        Policy.fromSettings(
                                emptyIfNull,
                                emptyIfNull2,
                                this.mLastDeviceConfigProperties,
                                null,
                                this.DEFAULT_FULL_POLICY));
        Policy fromSettings =
                Policy.fromSettings(
                        "",
                        "",
                        this.mLastDeviceConfigProperties,
                        "_adaptive",
                        DEFAULT_ADAPTIVE_POLICY);
        this.mDefaultAdaptivePolicy = fromSettings;
        if (this.mPolicyLevel == 1 && !this.mAdaptivePolicy.equals(fromSettings)) {
            maybeUpdateDefaultFullPolicy = true;
        }
        this.mAdaptivePolicy = this.mDefaultAdaptivePolicy;
        updatePolicyDependenciesLocked();
        return maybeUpdateDefaultFullPolicy;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:

       if (r3 != 3) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePolicyDependenciesLocked() {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.batterysaver.BatterySaverPolicy.updatePolicyDependenciesLocked():void");
    }
}
