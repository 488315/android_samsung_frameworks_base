package com.android.server.location.injector;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.PackageTagsList;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.SystemConfig;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.injector.SettingsHelper;
import java.io.FileDescriptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class SystemSettingsHelper extends SettingsHelper {
    public final LongGlobalSetting mBackgroundThrottleIntervalMs;
    public final StringSetCachedGlobalSetting mBackgroundThrottlePackageWhitelist;
    public final Context mContext;
    public final BooleanGlobalSetting mGnssMeasurementFullTracking;
    public final IntegerSecureSetting mLocationMode;
    public final StringListCachedSecureSetting mLocationPackageBlacklist;
    public final StringListCachedSecureSetting mLocationPackageWhitelist;
    public final PackageTagsListSetting mAdasPackageAllowlist = new PackageTagsListSetting("adas_settings_allowlist", new Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            ArrayMap lambda$new$1;
            lambda$new$1 = SystemSettingsHelper.lambda$new$1();
            return lambda$new$1;
        }
    });
    public final PackageTagsListSetting mIgnoreSettingsPackageAllowlist = new PackageTagsListSetting("ignore_settings_allowlist", new Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            ArrayMap lambda$new$2;
            lambda$new$2 = SystemSettingsHelper.lambda$new$2();
            return lambda$new$2;
        }
    });
    public final HashSet mBackgroundThrottlePackageAllowlistByNsflp = new HashSet();

    public SystemSettingsHelper(Context context) {
        this.mContext = context;
        this.mLocationMode = new IntegerSecureSetting(context, "location_mode", LocationServiceThread.getHandler());
        this.mBackgroundThrottleIntervalMs = new LongGlobalSetting(context, "location_background_throttle_interval_ms", LocationServiceThread.getHandler());
        this.mGnssMeasurementFullTracking = new BooleanGlobalSetting(context, "enable_gnss_raw_meas_full_tracking", LocationServiceThread.getHandler());
        this.mLocationPackageBlacklist = new StringListCachedSecureSetting(context, "locationPackagePrefixBlacklist", LocationServiceThread.getHandler());
        this.mLocationPackageWhitelist = new StringListCachedSecureSetting(context, "locationPackagePrefixWhitelist", LocationServiceThread.getHandler());
        this.mBackgroundThrottlePackageWhitelist = new StringSetCachedGlobalSetting(context, "location_background_throttle_package_whitelist", new Supplier() { // from class: com.android.server.location.injector.SystemSettingsHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                ArraySet lambda$new$0;
                lambda$new$0 = SystemSettingsHelper.lambda$new$0();
                return lambda$new$0;
            }
        }, LocationServiceThread.getHandler());
    }

    public static /* synthetic */ ArraySet lambda$new$0() {
        return SystemConfig.getInstance().getAllowUnthrottledLocation();
    }

    public static /* synthetic */ ArrayMap lambda$new$1() {
        return SystemConfig.getInstance().getAllowAdasLocationSettings();
    }

    public static /* synthetic */ ArrayMap lambda$new$2() {
        return SystemConfig.getInstance().getAllowIgnoreLocationSettings();
    }

    public void onSystemReady() {
        this.mLocationMode.register();
        this.mBackgroundThrottleIntervalMs.register();
        this.mLocationPackageBlacklist.register();
        this.mLocationPackageWhitelist.register();
        this.mBackgroundThrottlePackageWhitelist.register();
        this.mIgnoreSettingsPackageAllowlist.register();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isLocationEnabled(int i) {
        return this.mLocationMode.getValueForUser(0, i) != 0;
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void setLocationEnabled(boolean z, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "location_mode", z ? 3 : 0, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnLocationEnabledChangedListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationMode.addListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnLocationEnabledChangedListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationMode.removeListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public long getBackgroundThrottleIntervalMs() {
        return this.mBackgroundThrottleIntervalMs.getValue(1800000L);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnBackgroundThrottleIntervalChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottleIntervalMs.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnBackgroundThrottleIntervalChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottleIntervalMs.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isLocationPackageBlacklisted(int i, String str) {
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

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnLocationPackageBlacklistChangedListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationPackageBlacklist.addListener(userSettingChangedListener);
        this.mLocationPackageWhitelist.addListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnLocationPackageBlacklistChangedListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
        this.mLocationPackageBlacklist.removeListener(userSettingChangedListener);
        this.mLocationPackageWhitelist.removeListener(userSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public Set getBackgroundThrottlePackageWhitelist() {
        return this.mBackgroundThrottlePackageWhitelist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnBackgroundThrottlePackageWhitelistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottlePackageWhitelist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnBackgroundThrottlePackageWhitelistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mBackgroundThrottlePackageWhitelist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isGnssMeasurementsFullTrackingEnabled() {
        return this.mGnssMeasurementFullTracking.getValue(false);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addOnGnssMeasurementsFullTrackingEnabledChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mGnssMeasurementFullTracking.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeOnGnssMeasurementsFullTrackingEnabledChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mGnssMeasurementFullTracking.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public PackageTagsList getAdasAllowlist() {
        return this.mAdasPackageAllowlist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addAdasAllowlistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mAdasPackageAllowlist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeAdasAllowlistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mAdasPackageAllowlist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public PackageTagsList getIgnoreSettingsAllowlist() {
        return this.mIgnoreSettingsPackageAllowlist.getValue();
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void addIgnoreSettingsAllowlistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mIgnoreSettingsPackageAllowlist.addListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void removeIgnoreSettingsAllowlistChangedListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
        this.mIgnoreSettingsPackageAllowlist.removeListener(globalSettingChangedListener);
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public long getBackgroundThrottleProximityAlertIntervalMs() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Settings.Global.getLong(this.mContext.getContentResolver(), "location_background_throttle_proximity_alert_interval_ms", 1800000L);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public float getCoarseLocationAccuracyM() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        try {
            return Settings.Secure.getFloatForUser(contentResolver, "locationCoarseAccuracy", 2000.0f, contentResolver.getUserId());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void dump(FileDescriptor fileDescriptor, IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        try {
            int[] runningUserIds = ActivityManager.getService().getRunningUserIds();
            indentingPrintWriter.print("Location Setting: ");
            indentingPrintWriter.increaseIndent();
            if (runningUserIds.length > 1) {
                indentingPrintWriter.println();
                for (int i : runningUserIds) {
                    indentingPrintWriter.print("[u");
                    indentingPrintWriter.print(i);
                    indentingPrintWriter.print("] ");
                    indentingPrintWriter.println(isLocationEnabled(i));
                }
            } else {
                indentingPrintWriter.println(isLocationEnabled(runningUserIds[0]));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Location Allow/Deny Packages:");
            indentingPrintWriter.increaseIndent();
            if (runningUserIds.length > 1) {
                for (int i2 : runningUserIds) {
                    List<String> valueForUser = this.mLocationPackageBlacklist.getValueForUser(i2);
                    if (!valueForUser.isEmpty()) {
                        indentingPrintWriter.print("user ");
                        indentingPrintWriter.print(i2);
                        indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                        indentingPrintWriter.increaseIndent();
                        for (String str : valueForUser) {
                            indentingPrintWriter.print("[deny] ");
                            indentingPrintWriter.println(str);
                        }
                        for (String str2 : this.mLocationPackageWhitelist.getValueForUser(i2)) {
                            indentingPrintWriter.print("[allow] ");
                            indentingPrintWriter.println(str2);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            } else {
                for (String str3 : this.mLocationPackageBlacklist.getValueForUser(runningUserIds[0])) {
                    indentingPrintWriter.print("[deny] ");
                    indentingPrintWriter.println(str3);
                }
                for (String str4 : this.mLocationPackageWhitelist.getValueForUser(runningUserIds[0])) {
                    indentingPrintWriter.print("[allow] ");
                    indentingPrintWriter.println(str4);
                }
            }
            indentingPrintWriter.decreaseIndent();
            Set value = this.mBackgroundThrottlePackageWhitelist.getValue();
            if (!value.isEmpty()) {
                indentingPrintWriter.println("Throttling Allow Packages:");
                indentingPrintWriter.increaseIndent();
                Iterator it = value.iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println((String) it.next());
                }
                indentingPrintWriter.decreaseIndent();
            }
            if (!this.mBackgroundThrottlePackageAllowlistByNsflp.isEmpty()) {
                indentingPrintWriter.println("Throttling Allow Packages by nsflp:");
                indentingPrintWriter.increaseIndent();
                Iterator it2 = this.mBackgroundThrottlePackageAllowlistByNsflp.iterator();
                while (it2.hasNext()) {
                    indentingPrintWriter.println((String) it2.next());
                }
                indentingPrintWriter.decreaseIndent();
            }
            PackageTagsList value2 = this.mIgnoreSettingsPackageAllowlist.getValue();
            if (!value2.isEmpty()) {
                indentingPrintWriter.println("Emergency Bypass Allow Packages:");
                indentingPrintWriter.increaseIndent();
                value2.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            }
            PackageTagsList value3 = this.mAdasPackageAllowlist.getValue();
            if (value3.isEmpty()) {
                return;
            }
            indentingPrintWriter.println("ADAS Bypass Allow Packages:");
            indentingPrintWriter.increaseIndent();
            value3.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public abstract class ObservingSetting extends ContentObserver {
        public final CopyOnWriteArrayList mListeners;
        public boolean mRegistered;

        public ObservingSetting(Handler handler) {
            super(handler);
            this.mListeners = new CopyOnWriteArrayList();
        }

        public synchronized boolean isRegistered() {
            return this.mRegistered;
        }

        public synchronized void register(Context context, Uri uri) {
            if (this.mRegistered) {
                return;
            }
            context.getContentResolver().registerContentObserver(uri, false, this, -1);
            this.mRegistered = true;
        }

        public void addListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
            this.mListeners.add(userSettingChangedListener);
        }

        public void removeListener(SettingsHelper.UserSettingChangedListener userSettingChangedListener) {
            this.mListeners.remove(userSettingChangedListener);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            Log.d("LocationManagerService", "location setting changed [u" + i + "]: " + uri);
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((SettingsHelper.UserSettingChangedListener) it.next()).onSettingChanged(i);
            }
        }

        public void onChangedByNsflp() {
            Log.i("LocationManagerService", "location setting is changed by nsflp");
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((SettingsHelper.UserSettingChangedListener) it.next()).onSettingChanged(-1);
            }
        }
    }

    public class IntegerSecureSetting extends ObservingSetting {
        public final Context mContext;
        public final String mSettingName;

        public IntegerSecureSetting(Context context, String str, Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public void register() {
            register(this.mContext, Settings.Secure.getUriFor(this.mSettingName));
        }

        public int getValueForUser(int i, int i2) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), this.mSettingName, i, i2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public class StringListCachedSecureSetting extends ObservingSetting {
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

        public void register() {
            register(this.mContext, Settings.Secure.getUriFor(this.mSettingName));
        }

        public synchronized List getValueForUser(int i) {
            List list;
            List asList;
            Preconditions.checkArgument(i != -10000);
            list = this.mCachedValue;
            if (i != this.mCachedUserId) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), this.mSettingName, i);
                    if (TextUtils.isEmpty(stringForUser)) {
                        asList = Collections.emptyList();
                    } else {
                        asList = Arrays.asList(stringForUser.split(","));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (isRegistered()) {
                        this.mCachedUserId = i;
                        this.mCachedValue = asList;
                    }
                    list = asList;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            return list;
        }

        public synchronized void invalidateForUser(int i) {
            if (this.mCachedUserId == i) {
                this.mCachedUserId = -10000;
                this.mCachedValue = null;
            }
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting, android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            invalidateForUser(i);
            super.onChange(z, uri, i);
        }
    }

    public class BooleanGlobalSetting extends ObservingSetting {
        public final Context mContext;
        public final String mSettingName;

        public BooleanGlobalSetting(Context context, String str, Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public boolean getValue(boolean z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Global.getInt(this.mContext.getContentResolver(), this.mSettingName, z ? 1 : 0) != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public class LongGlobalSetting extends ObservingSetting {
        public final Context mContext;
        public final String mSettingName;

        public LongGlobalSetting(Context context, String str, Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
        }

        public void register() {
            register(this.mContext, Settings.Global.getUriFor(this.mSettingName));
        }

        public long getValue(long j) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Global.getLong(this.mContext.getContentResolver(), this.mSettingName, j);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public class StringSetCachedGlobalSetting extends ObservingSetting {
        public final Supplier mBaseValuesSupplier;
        public ArraySet mCachedValue;
        public final Context mContext;
        public final String mSettingName;
        public boolean mValid;

        public StringSetCachedGlobalSetting(Context context, String str, Supplier supplier, Handler handler) {
            super(handler);
            this.mContext = context;
            this.mSettingName = str;
            this.mBaseValuesSupplier = supplier;
            this.mValid = false;
        }

        public void register() {
            register(this.mContext, Settings.Global.getUriFor(this.mSettingName));
        }

        public synchronized Set getValue() {
            ArraySet arraySet;
            arraySet = this.mCachedValue;
            if (!this.mValid) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ArraySet arraySet2 = new ArraySet((ArraySet) this.mBaseValuesSupplier.get());
                    String string = Settings.Global.getString(this.mContext.getContentResolver(), this.mSettingName);
                    if (!TextUtils.isEmpty(string)) {
                        arraySet2.addAll(Arrays.asList(string.split(",")));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (isRegistered()) {
                        this.mValid = true;
                        this.mCachedValue = arraySet2;
                    }
                    arraySet = arraySet2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            return arraySet;
        }

        public synchronized void invalidate() {
            this.mValid = false;
            this.mCachedValue = null;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.ObservingSetting, android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            invalidate();
            super.onChange(z, uri, i);
        }
    }

    public abstract class DeviceConfigSetting implements DeviceConfig.OnPropertiesChangedListener {
        public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
        public final String mName;
        public boolean mRegistered;

        public DeviceConfigSetting(String str) {
            this.mName = str;
        }

        public synchronized boolean isRegistered() {
            return this.mRegistered;
        }

        public synchronized void register() {
            if (this.mRegistered) {
                return;
            }
            DeviceConfig.addOnPropertiesChangedListener("location", LocationServiceThread.getExecutor(), this);
            this.mRegistered = true;
        }

        public void addListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
            this.mListeners.add(globalSettingChangedListener);
        }

        public void removeListener(SettingsHelper.GlobalSettingChangedListener globalSettingChangedListener) {
            this.mListeners.remove(globalSettingChangedListener);
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains(this.mName)) {
                onPropertiesChanged();
            }
        }

        public void onPropertiesChanged() {
            Log.d("LocationManagerService", "location device config setting changed: " + this.mName);
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                ((SettingsHelper.UserSettingChangedListener) it.next()).onSettingChanged(-1);
            }
        }
    }

    public class PackageTagsListSetting extends DeviceConfigSetting {
        public final Supplier mBaseValuesSupplier;
        public PackageTagsList mCachedValue;
        public boolean mValid;

        public PackageTagsListSetting(String str, Supplier supplier) {
            super(str);
            this.mBaseValuesSupplier = supplier;
        }

        public synchronized PackageTagsList getValue() {
            PackageTagsList packageTagsList;
            packageTagsList = this.mCachedValue;
            if (!this.mValid) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    PackageTagsList.Builder add = new PackageTagsList.Builder().add((Map) this.mBaseValuesSupplier.get());
                    String property = DeviceConfig.getProperty("location", this.mName);
                    if (!TextUtils.isEmpty(property)) {
                        for (String str : property.split(",")) {
                            if (!TextUtils.isEmpty(str)) {
                                String[] split = str.split(KnoxVpnFirewallHelper.DELIMITER);
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
                    if (isRegistered()) {
                        this.mValid = true;
                        this.mCachedValue = build;
                    }
                    packageTagsList = build;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            return packageTagsList;
        }

        public synchronized void invalidate() {
            this.mValid = false;
            this.mCachedValue = null;
        }

        @Override // com.android.server.location.injector.SystemSettingsHelper.DeviceConfigSetting
        public void onPropertiesChanged() {
            invalidate();
            super.onPropertiesChanged();
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public void updateBackgroundThrottlingAllowlist(List list) {
        this.mBackgroundThrottlePackageAllowlistByNsflp.clear();
        if (list == null) {
            Log.w("LocationManagerService", "Null package is updated, so just clear");
        } else {
            this.mBackgroundThrottlePackageAllowlistByNsflp.addAll(list);
            this.mBackgroundThrottlePackageWhitelist.onChangedByNsflp();
        }
    }

    @Override // com.android.server.location.injector.SettingsHelper
    public boolean isBackgroundThrottlingAllowlistByNsflp(String str) {
        return this.mBackgroundThrottlePackageAllowlistByNsflp.contains(str);
    }
}
