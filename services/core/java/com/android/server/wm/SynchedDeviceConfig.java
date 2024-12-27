package com.android.server.wm;

import android.provider.DeviceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public final class SynchedDeviceConfig implements DeviceConfig.OnPropertiesChangedListener {
    public final Map mDeviceConfigEntries;
    public final Executor mExecutor;
    public final String mNamespace;

    public final class SynchedDeviceConfigBuilder {
        public final Executor mExecutor;
        public final Map mDeviceConfigEntries = new ConcurrentHashMap();
        public final String mNamespace = "window_manager";

        public SynchedDeviceConfigBuilder(Executor executor) {
            this.mExecutor = executor;
        }

        public final void addDeviceConfigEntry(String str, boolean z, boolean z2) {
            if (((ConcurrentHashMap) this.mDeviceConfigEntries).containsKey(str)) {
                throw new AssertionError("Key already present: ".concat(str));
            }
            ((ConcurrentHashMap) this.mDeviceConfigEntries)
                    .put(str, new SynchedDeviceConfigEntry(str, z, z2));
        }
    }

    public final class SynchedDeviceConfigEntry {
        public final boolean mBuildTimeFlagEnabled;
        public final boolean mDefaultValue;
        public final String mFlagKey;
        public volatile boolean mOverrideValue;

        public SynchedDeviceConfigEntry(String str, boolean z, boolean z2) {
            this.mFlagKey = str;
            this.mDefaultValue = z;
            this.mOverrideValue = z;
            this.mBuildTimeFlagEnabled = z2;
        }
    }

    public SynchedDeviceConfig(String str, Executor executor, Map map) {
        this.mNamespace = str;
        this.mExecutor = executor;
        this.mDeviceConfigEntries = map;
    }

    public final boolean getFlagValue(String str) {
        SynchedDeviceConfigEntry synchedDeviceConfigEntry =
                (SynchedDeviceConfigEntry) this.mDeviceConfigEntries.get(str);
        if (synchedDeviceConfigEntry != null) {
            return synchedDeviceConfigEntry.mBuildTimeFlagEnabled
                    && synchedDeviceConfigEntry.mOverrideValue;
        }
        throw new IllegalArgumentException("Unexpected flag name: ".concat(str));
    }

    public final boolean isBuildTimeFlagEnabled(String str) {
        SynchedDeviceConfigEntry synchedDeviceConfigEntry =
                (SynchedDeviceConfigEntry) this.mDeviceConfigEntries.get(str);
        if (synchedDeviceConfigEntry != null) {
            return synchedDeviceConfigEntry.mBuildTimeFlagEnabled;
        }
        throw new IllegalArgumentException("Unexpected flag name: ".concat(str));
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        for (SynchedDeviceConfigEntry synchedDeviceConfigEntry :
                this.mDeviceConfigEntries.values()) {
            if (properties.getKeyset().contains(synchedDeviceConfigEntry.mFlagKey)) {
                synchedDeviceConfigEntry.mOverrideValue =
                        properties.getBoolean(
                                synchedDeviceConfigEntry.mFlagKey,
                                synchedDeviceConfigEntry.mDefaultValue);
            }
        }
    }
}
