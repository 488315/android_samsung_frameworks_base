package com.android.server.timezonedetector;

import android.R;
import android.app.time.TimeZoneConfiguration;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IUserRestrictionsListener;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.timedetector.ServerFlags;

import com.samsung.android.feature.SemCscFeature;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class ServiceConfigAccessorImpl implements ServiceConfigAccessor {
    public static ServiceConfigAccessorImpl sInstance;
    public final List mConfigurationInternalListeners = new ArrayList();
    public final Context mContext;
    public final ContentResolver mCr;
    public final LocationManager mLocationManager;
    public boolean mRecordStateChangesForTests;
    public final ServerFlags mServerFlags;
    public String mTestPrimaryLocationTimeZoneProviderMode;
    public String mTestPrimaryLocationTimeZoneProviderPackageName;
    public String mTestSecondaryLocationTimeZoneProviderMode;
    public String mTestSecondaryLocationTimeZoneProviderPackageName;
    public final UserManager mUserManager;
    public static final Set CONFIGURATION_INTERNAL_SERVER_FLAGS_KEYS_TO_WATCH =
            Set.of(
                    "location_time_zone_detection_feature_supported",
                    "primary_location_time_zone_provider_mode_override",
                    "secondary_location_time_zone_provider_mode_override",
                    "location_time_zone_detection_run_in_background_enabled",
                    "enhanced_metrics_collection_enabled",
                    "location_time_zone_detection_setting_enabled_default",
                    "location_time_zone_detection_setting_enabled_override",
                    "time_zone_detector_auto_detection_enabled_default",
                    "time_zone_detector_telephony_fallback_supported");
    public static final Set LOCATION_TIME_ZONE_MANAGER_SERVER_FLAGS_KEYS_TO_WATCH =
            Set.of(
                    "location_time_zone_detection_feature_supported",
                    "location_time_zone_detection_run_in_background_enabled",
                    "location_time_zone_detection_setting_enabled_default",
                    "location_time_zone_detection_setting_enabled_override",
                    "primary_location_time_zone_provider_mode_override",
                    "secondary_location_time_zone_provider_mode_override",
                    "ltzp_init_timeout_millis",
                    "ltzp_init_timeout_fuzz_millis",
                    "ltzp_event_filtering_age_threshold_millis",
                    "location_time_zone_detection_uncertainty_delay_millis");
    public static final Duration DEFAULT_LTZP_INITIALIZATION_TIMEOUT = Duration.ofMinutes(5);
    public static final Duration DEFAULT_LTZP_INITIALIZATION_TIMEOUT_FUZZ = Duration.ofMinutes(1);
    public static final Duration DEFAULT_LTZP_UNCERTAINTY_DELAY = Duration.ofMinutes(5);
    public static final Duration DEFAULT_LTZP_EVENT_FILTER_AGE_THRESHOLD = Duration.ofMinutes(1);
    public static final Object SLOCK = new Object();

    /* renamed from: com.android.server.timezonedetector.ServiceConfigAccessorImpl$3, reason: invalid class name */
    public final class AnonymousClass3 extends IUserRestrictionsListener.Stub {
        public final /* synthetic */ Handler val$mainThreadHandler;

        public AnonymousClass3(Handler handler) {
            this.val$mainThreadHandler = handler;
        }

        public final void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) {
            this.val$mainThreadHandler.post(
                    new ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(
                            this, i, bundle, bundle2));
        }
    }

    public ServiceConfigAccessorImpl(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mCr = context.getContentResolver();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUserManager = userManager;
        this.mLocationManager = (LocationManager) context.getSystemService(LocationManager.class);
        ServerFlags serverFlags = ServerFlags.getInstance(context);
        this.mServerFlags = serverFlags;
        context.registerReceiverForAllUsers(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.timezonedetector.ServiceConfigAccessorImpl.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        ServiceConfigAccessorImpl.this
                                .handleConfigurationInternalChangeOnMainThread();
                    }
                },
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.USER_SWITCHED", "android.location.MODE_CHANGED"),
                null,
                null);
        Handler mainThreadHandler = context.getMainThreadHandler();
        ContentResolver contentResolver = context.getContentResolver();
        ContentObserver contentObserver =
                new ContentObserver(
                        mainThreadHandler) { // from class:
                                             // com.android.server.timezonedetector.ServiceConfigAccessorImpl.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ServiceConfigAccessorImpl.this
                                .handleConfigurationInternalChangeOnMainThread();
                    }
                };
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("auto_time_zone"), true, contentObserver);
        contentResolver.registerContentObserver(
                Settings.Global.getUriFor("auto_time_zone_explicit"), true, contentObserver);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("location_time_zone_detection_enabled"),
                true,
                contentObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("preferred_time_zone_detection_method"),
                true,
                contentObserver,
                -1);
        serverFlags.addListener(
                new StateChangeListener() { // from class:
                                            // com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda0
                    @Override // com.android.server.timezonedetector.StateChangeListener
                    public final void onChange() {
                        ServiceConfigAccessorImpl.this
                                .handleConfigurationInternalChangeOnMainThread();
                    }
                },
                CONFIGURATION_INTERNAL_SERVER_FLAGS_KEYS_TO_WATCH);
        userManager.addUserRestrictionsListener(new AnonymousClass3(mainThreadHandler));
    }

    public static ServiceConfigAccessor getInstance(Context context) {
        ServiceConfigAccessorImpl serviceConfigAccessorImpl;
        synchronized (SLOCK) {
            try {
                if (sInstance == null) {
                    sInstance = new ServiceConfigAccessorImpl(context);
                }
                serviceConfigAccessorImpl = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return serviceConfigAccessorImpl;
    }

    public final boolean getAutoDetectionEnabledSetting() {
        boolean z = Settings.Global.getInt(this.mCr, "auto_time_zone", 1) > 0;
        this.mServerFlags.getClass();
        String property =
                DeviceConfig.getProperty(
                        "system_time", "time_zone_detector_auto_detection_enabled_default");
        Optional empty =
                property == null
                        ? Optional.empty()
                        : Boolean.parseBoolean(property)
                                ? ServerFlags.OPTIONAL_TRUE
                                : ServerFlags.OPTIONAL_FALSE;
        if (!empty.isPresent()
                || Settings.Global.getInt(this.mCr, "auto_time_zone_explicit", 0) != 0) {
            return z;
        }
        boolean booleanValue = ((Boolean) empty.get()).booleanValue();
        if (booleanValue != z) {
            Settings.Global.putInt(this.mCr, "auto_time_zone", booleanValue ? 1 : 0);
        }
        return booleanValue;
    }

    public final synchronized ConfigurationInternal getConfigurationInternal(int i) {
        ConfigurationInternal.Builder builder;
        builder = new ConfigurationInternal.Builder();
        builder.mUserId = Integer.valueOf(i);
        builder.mTelephonyDetectionSupported = isTelephonyTimeZoneDetectionFeatureSupported();
        builder.mGeoDetectionSupported = isGeoTimeZoneDetectionFeatureSupported();
        boolean z =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_swipe_up_gesture_setting_available);
        this.mServerFlags.getClass();
        builder.mTelephonyFallbackSupported =
                DeviceConfig.getBoolean(
                        "system_time", "time_zone_detector_telephony_fallback_supported", z);
        this.mServerFlags.getClass();
        builder.mGeoDetectionRunInBackgroundEnabled =
                DeviceConfig.getBoolean(
                        "system_time",
                        "location_time_zone_detection_run_in_background_enabled",
                        false);
        this.mServerFlags.getClass();
        builder.mEnhancedMetricsCollectionEnabled =
                DeviceConfig.getBoolean(
                        "system_time", "enhanced_metrics_collection_enabled", false);
        builder.mAutoDetectionEnabledSetting = getAutoDetectionEnabledSetting();
        builder.mUserConfigAllowed =
                !this.mUserManager.hasUserRestriction("no_config_date_time", UserHandle.of(i));
        builder.mLocationEnabledSetting =
                this.mLocationManager.isLocationEnabledForUser(UserHandle.of(i));
        builder.mGeoDetectionEnabledSetting = getGeoDetectionEnabledSetting(i);
        builder.mGeoLocationFbEnabledSetting =
                SemCscFeature.getInstance()
                                .getBoolean("CscFeature_Common_SupportGeolocationFallback", false)
                        ? "mobile"
                                .equals(
                                        Settings.Secure.getString(
                                                this.mCr, "preferred_time_zone_detection_method"))
                        : false;
        return new ConfigurationInternal(builder);
    }

    public final boolean getGeoDetectionEnabledSetting(int i) {
        ServerFlags serverFlags = this.mServerFlags;
        serverFlags.getClass();
        String property =
                DeviceConfig.getProperty(
                        "system_time", "location_time_zone_detection_setting_enabled_override");
        Optional empty =
                property == null
                        ? Optional.empty()
                        : Boolean.parseBoolean(property)
                                ? ServerFlags.OPTIONAL_TRUE
                                : ServerFlags.OPTIONAL_FALSE;
        if (empty.isPresent()) {
            return ((Boolean) empty.get()).booleanValue();
        }
        serverFlags.getClass();
        return Settings.Secure.getIntForUser(
                        this.mCr,
                        "location_time_zone_detection_enabled",
                        DeviceConfig.getBoolean(
                                        "system_time",
                                        "location_time_zone_detection_setting_enabled_default",
                                        false)
                                ? 1
                                : 0,
                        i)
                != 0;
    }

    public final synchronized String getPrimaryLocationTimeZoneProviderMode() {
        String str;
        String str2 = this.mTestPrimaryLocationTimeZoneProviderMode;
        if (str2 != null) {
            return str2;
        }
        this.mServerFlags.getClass();
        Optional ofNullable =
                Optional.ofNullable(
                        DeviceConfig.getProperty(
                                "system_time",
                                "primary_location_time_zone_provider_mode_override"));
        synchronized (this) {
            try {
                str =
                        (String)
                                ofNullable.orElse(
                                        this.mContext
                                                        .getResources()
                                                        .getBoolean(
                                                                R.bool
                                                                        .config_enableVirtualDeviceManager)
                                                ? "enabled"
                                                : "disabled");
            } finally {
            }
        }
        return str;
    }

    public final synchronized String getSecondaryLocationTimeZoneProviderMode() {
        String str;
        String str2 = this.mTestSecondaryLocationTimeZoneProviderMode;
        if (str2 != null) {
            return str2;
        }
        this.mServerFlags.getClass();
        Optional ofNullable =
                Optional.ofNullable(
                        DeviceConfig.getProperty(
                                "system_time",
                                "secondary_location_time_zone_provider_mode_override"));
        synchronized (this) {
            try {
                str =
                        (String)
                                ofNullable.orElse(
                                        this.mContext
                                                        .getResources()
                                                        .getBoolean(
                                                                R.bool
                                                                        .config_enable_a11y_magnification_single_panning)
                                                ? "enabled"
                                                : "disabled");
            } finally {
            }
        }
        return str;
    }

    public final void handleConfigurationInternalChangeOnMainThread() {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.mConfigurationInternalListeners);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((StateChangeListener) it.next()).onChange();
        }
    }

    public final boolean isGeoTimeZoneDetectionFeatureSupported() {
        if (this.mContext.getResources().getBoolean(R.bool.config_enableMultipleAdmins)) {
            this.mServerFlags.getClass();
            if (DeviceConfig.getBoolean(
                            "system_time", "location_time_zone_detection_feature_supported", true)
                    && (!Objects.equals(getPrimaryLocationTimeZoneProviderMode(), "disabled")
                            || !Objects.equals(
                                    getSecondaryLocationTimeZoneProviderMode(), "disabled"))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isTelephonyTimeZoneDetectionFeatureSupported() {
        return this.mContext.getResources().getBoolean(R.bool.config_enable_iwlan_handover_policy)
                && (this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony")
                        || !"wifi-only"
                                .equalsIgnoreCase(SystemProperties.get("ro.carrier", "Unknown")));
    }

    public final void storeConfiguration(
            int i,
            TimeZoneConfiguration timeZoneConfiguration,
            TimeZoneConfiguration timeZoneConfiguration2) {
        boolean isGeoDetectionEnabled;
        if (isTelephonyTimeZoneDetectionFeatureSupported()
                || isGeoTimeZoneDetectionFeatureSupported()) {
            if (timeZoneConfiguration.hasIsAutoDetectionEnabled()) {
                Settings.Global.putInt(this.mCr, "auto_time_zone_explicit", 1);
            }
            boolean isAutoDetectionEnabled = timeZoneConfiguration2.isAutoDetectionEnabled();
            if (getAutoDetectionEnabledSetting() != isAutoDetectionEnabled) {
                Settings.Global.putInt(this.mCr, "auto_time_zone", isAutoDetectionEnabled ? 1 : 0);
            }
            this.mServerFlags.getClass();
            String property =
                    DeviceConfig.getProperty(
                            "system_time", "location_time_zone_detection_setting_enabled_override");
            if ((property == null
                                    ? Optional.empty()
                                    : Boolean.parseBoolean(property)
                                            ? ServerFlags.OPTIONAL_TRUE
                                            : ServerFlags.OPTIONAL_FALSE)
                            .isEmpty()
                    && isGeoTimeZoneDetectionFeatureSupported()
                    && isTelephonyTimeZoneDetectionFeatureSupported()
                    && getGeoDetectionEnabledSetting(i)
                            != (isGeoDetectionEnabled =
                                    timeZoneConfiguration2.isGeoDetectionEnabled())) {
                Settings.Secure.putIntForUser(
                        this.mCr,
                        "location_time_zone_detection_enabled",
                        isGeoDetectionEnabled ? 1 : 0,
                        i);
            }
        }
    }
}
