package com.android.server.location.injector;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.PackageTagsList;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.util.Preconditions;
import com.android.server.SystemConfig;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationServiceThread;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemSettingsHelper {
    public final PackageTagsListSetting mAdasPackageAllowlist;
    public final LongGlobalSetting mBackgroundThrottleIntervalMs;
    public final HashSet mBackgroundThrottlePackageAllowlistByNsflp = new HashSet();
    public final StringSetCachedGlobalSetting mBackgroundThrottlePackageWhitelist;
    public final Context mContext;
    public final LongGlobalSetting mGnssMeasurementFullTracking;
    public final PackageTagsListSetting mIgnoreSettingsPackageAllowlist;
    public final LongGlobalSetting mLocationMode;
    public final StringListCachedSecureSetting mLocationPackageBlacklist;
    public final StringListCachedSecureSetting mLocationPackageWhitelist;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LongGlobalSetting extends ObservingSetting {
        public final Context mContext;
        public final String mSettingName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LongGlobalSetting(Context context, Handler handler, int i) {
            super(handler);
            switch (i) {
                case 1:
                    super(handler);
                    this.mContext = context;
                    this.mSettingName = "enable_gnss_raw_meas_full_tracking";
                    break;
                case 2:
                    super(handler);
                    this.mContext = context;
                    this.mSettingName = "location_mode";
                    break;
                default:
                    this.mContext = context;
                    this.mSettingName = "location_background_throttle_interval_ms";
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ObservingSetting extends ContentObserver {
        public final CopyOnWriteArrayList mListeners;
        public boolean mRegistered;

        public ObservingSetting(Handler handler) {
            super(handler);
            this.mListeners = new CopyOnWriteArrayList();
        }

        public final void addListener(
                SettingsHelper$UserSettingChangedListener
                        settingsHelper$UserSettingChangedListener) {
            this.mListeners.add(settingsHelper$UserSettingChangedListener);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            Log.d("LocationManagerService", "location setting changed [u" + i + "]: " + uri);
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((SettingsHelper$UserSettingChangedListener) it.next()).onSettingChanged(i);
            }
        }

        public final synchronized void register(Context context, Uri uri) {
            if (this.mRegistered) {
                return;
            }
            context.getContentResolver().registerContentObserver(uri, false, this, -1);
            this.mRegistered = true;
        }

        public final void removeListener(
                SettingsHelper$UserSettingChangedListener
                        settingsHelper$UserSettingChangedListener) {
            this.mListeners.remove(settingsHelper$UserSettingChangedListener);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageTagsListSetting implements DeviceConfig.OnPropertiesChangedListener {
        public final Supplier mBaseValuesSupplier;
        public PackageTagsList mCachedValue;
        public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
        public final String mName;
        public boolean mRegistered;
        public boolean mValid;

        public PackageTagsListSetting(String str, Supplier supplier) {
            this.mName = str;
            this.mBaseValuesSupplier = supplier;
        }

        public final synchronized PackageTagsList getValue() {
            PackageTagsList packageTagsList;
            try {
                packageTagsList = this.mCachedValue;
                if (!this.mValid) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        PackageTagsList.Builder add =
                                new PackageTagsList.Builder()
                                        .add((Map) this.mBaseValuesSupplier.get());
                        String property = DeviceConfig.getProperty("location", this.mName);
                        if (!TextUtils.isEmpty(property)) {
                            for (String str : property.split(",")) {
                                if (!TextUtils.isEmpty(str)) {
                                    String[] split = str.split(";");
                                    String str2 = split[0];
                                    if (split.length == 1) {
                                        add.add(str2);
                                    } else {
                                        for (int i = 1; i < split.length; i++) {
                                            String str3 = split[i];
                                            if ("null".equals(str3)) {
                                                str3 = null;
                                            }
                                            if ("*".equals(str3)) {
                                                add.add(str2);
                                            } else {
                                                add.add(str2, str3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        PackageTagsList build = add.build();
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        synchronized (this) {
                            if (this.mRegistered) {
                                this.mValid = true;
                                this.mCachedValue = build;
                            }
                            packageTagsList = build;
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
            return packageTagsList;
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains(this.mName)) {
                synchronized (this) {
                    this.mValid = false;
                    this.mCachedValue = null;
                }
                VpnManagerService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("location device config setting changed: "),
                        this.mName,
                        "LocationManagerService");
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    ((SettingsHelper$UserSettingChangedListener) it.next()).onSettingChanged(-1);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StringListCachedSecureSetting extends ObservingSetting {
        public int mCachedUserId;
        public List mCachedValue;
        public final Context mContext;
        public final String mSettingName;

        public StringListCachedSecureSetting(Context context, String str, Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
            this.mCachedUserId = -10000;
        }

        public final synchronized List getValueForUser(int i) {
            List list;
            try {
                Preconditions.checkArgument(i != -10000);
                list = this.mCachedValue;
                if (i != this.mCachedUserId) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        String stringForUser =
                                Settings.Secure.getStringForUser(
                                        this.mContext.getContentResolver(), this.mSettingName, i);
                        List emptyList =
                                TextUtils.isEmpty(stringForUser)
                                        ? Collections.emptyList()
                                        : Arrays.asList(stringForUser.split(","));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        synchronized (this) {
                            if (this.mRegistered) {
                                this.mCachedUserId = i;
                                this.mCachedValue = emptyList;
                            }
                            list = emptyList;
                        }
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
            return list;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting,
                  // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            synchronized (this) {
                if (this.mCachedUserId == i) {
                    this.mCachedUserId = -10000;
                    this.mCachedValue = null;
                }
            }
            super.onChange(z, uri, i);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StringSetCachedGlobalSetting extends ObservingSetting {
        public final Supplier mBaseValuesSupplier;
        public ArraySet mCachedValue;
        public final Context mContext;
        public final String mSettingName;
        public boolean mValid;

        public StringSetCachedGlobalSetting(
                Context context,
                SystemSettingsHelper$$ExternalSyntheticLambda0
                        systemSettingsHelper$$ExternalSyntheticLambda0,
                Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = "location_background_throttle_package_whitelist";
            this.mBaseValuesSupplier = systemSettingsHelper$$ExternalSyntheticLambda0;
            this.mValid = false;
        }

        public final synchronized Set getValue() {
            ArraySet arraySet;
            try {
                arraySet = this.mCachedValue;
                if (!this.mValid) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        ArraySet arraySet2 =
                                new ArraySet((ArraySet) this.mBaseValuesSupplier.get());
                        String string =
                                Settings.Global.getString(
                                        this.mContext.getContentResolver(), this.mSettingName);
                        if (!TextUtils.isEmpty(string)) {
                            arraySet2.addAll(Arrays.asList(string.split(",")));
                        }
                        synchronized (this) {
                            if (this.mRegistered) {
                                this.mValid = true;
                                this.mCachedValue = arraySet2;
                            }
                            arraySet = arraySet2;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
            return arraySet;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting,
                  // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            synchronized (this) {
                this.mValid = false;
                this.mCachedValue = null;
            }
            super.onChange(z, uri, i);
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0] */
    public SystemSettingsHelper(Context context) {
        this.mContext = context;
        this.mLocationMode = new LongGlobalSetting(context, LocationServiceThread.getHandler(), 2);
        this.mBackgroundThrottleIntervalMs =
                new LongGlobalSetting(context, LocationServiceThread.getHandler(), 0);
        this.mGnssMeasurementFullTracking =
                new LongGlobalSetting(context, LocationServiceThread.getHandler(), 1);
        this.mLocationPackageBlacklist =
                new StringListCachedSecureSetting(
                        context,
                        "locationPackagePrefixBlacklist",
                        LocationServiceThread.getHandler());
        this.mLocationPackageWhitelist =
                new StringListCachedSecureSetting(
                        context,
                        "locationPackagePrefixWhitelist",
                        LocationServiceThread.getHandler());
        final int i = 0;
        this.mBackgroundThrottlePackageWhitelist =
                new StringSetCachedGlobalSetting(
                        context,
                        new Supplier() { // from class:
                                         // com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i) {
                                    case 0:
                                        return SystemConfig.getInstance().mAllowUnthrottledLocation;
                                    case 1:
                                        return SystemConfig.getInstance().mAllowAdasSettings;
                                    default:
                                        return SystemConfig.getInstance()
                                                .mAllowIgnoreLocationSettings;
                                }
                            }
                        },
                        LocationServiceThread.getHandler());
        final int i2 = 1;
        this.mAdasPackageAllowlist =
                new PackageTagsListSetting(
                        "adas_settings_allowlist",
                        new Supplier() { // from class:
                                         // com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        return SystemConfig.getInstance().mAllowUnthrottledLocation;
                                    case 1:
                                        return SystemConfig.getInstance().mAllowAdasSettings;
                                    default:
                                        return SystemConfig.getInstance()
                                                .mAllowIgnoreLocationSettings;
                                }
                            }
                        });
        final int i3 = 2;
        this.mIgnoreSettingsPackageAllowlist =
                new PackageTagsListSetting(
                        "ignore_settings_allowlist",
                        new Supplier() { // from class:
                                         // com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i3) {
                                    case 0:
                                        return SystemConfig.getInstance().mAllowUnthrottledLocation;
                                    case 1:
                                        return SystemConfig.getInstance().mAllowAdasSettings;
                                    default:
                                        return SystemConfig.getInstance()
                                                .mAllowIgnoreLocationSettings;
                                }
                            }
                        });
    }

    public final long getBackgroundThrottleProximityAlertIntervalMs() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Settings.Global.getLong(
                    this.mContext.getContentResolver(),
                    "location_background_throttle_proximity_alert_interval_ms",
                    1800000L);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isLocationEnabled(int i) {
        LongGlobalSetting longGlobalSetting = this.mLocationMode;
        longGlobalSetting.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Settings.Secure.getIntForUser(
                            longGlobalSetting.mContext.getContentResolver(),
                            longGlobalSetting.mSettingName,
                            0,
                            i)
                    != 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isLocationPackageBlacklisted(int i, String str) {
        List valueForUser = this.mLocationPackageBlacklist.getValueForUser(i);
        if (valueForUser.isEmpty()) {
            return false;
        }
        Iterator it = this.mLocationPackageWhitelist.getValueForUser(i).iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return false;
            }
        }
        Iterator it2 = valueForUser.iterator();
        while (it2.hasNext()) {
            if (str.startsWith((String) it2.next())) {
                return true;
            }
        }
        return false;
    }

    public final void onSystemReady() {
        LongGlobalSetting longGlobalSetting = this.mLocationMode;
        longGlobalSetting.register(
                longGlobalSetting.mContext,
                Settings.Secure.getUriFor(longGlobalSetting.mSettingName));
        LongGlobalSetting longGlobalSetting2 = this.mBackgroundThrottleIntervalMs;
        longGlobalSetting2.register(
                longGlobalSetting2.mContext,
                Settings.Global.getUriFor(longGlobalSetting2.mSettingName));
        StringListCachedSecureSetting stringListCachedSecureSetting =
                this.mLocationPackageBlacklist;
        stringListCachedSecureSetting.register(
                stringListCachedSecureSetting.mContext,
                Settings.Secure.getUriFor(stringListCachedSecureSetting.mSettingName));
        StringListCachedSecureSetting stringListCachedSecureSetting2 =
                this.mLocationPackageWhitelist;
        stringListCachedSecureSetting2.register(
                stringListCachedSecureSetting2.mContext,
                Settings.Secure.getUriFor(stringListCachedSecureSetting2.mSettingName));
        StringSetCachedGlobalSetting stringSetCachedGlobalSetting =
                this.mBackgroundThrottlePackageWhitelist;
        stringSetCachedGlobalSetting.register(
                stringSetCachedGlobalSetting.mContext,
                Settings.Global.getUriFor(stringSetCachedGlobalSetting.mSettingName));
        PackageTagsListSetting packageTagsListSetting = this.mIgnoreSettingsPackageAllowlist;
        synchronized (packageTagsListSetting) {
            if (packageTagsListSetting.mRegistered) {
                return;
            }
            DeviceConfig.addOnPropertiesChangedListener(
                    "location", LocationServiceThread.getExecutor(), packageTagsListSetting);
            packageTagsListSetting.mRegistered = true;
        }
    }
}
