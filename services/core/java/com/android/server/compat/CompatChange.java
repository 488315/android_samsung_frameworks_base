package com.android.server.compat;

import android.app.compat.PackageOverride;
import android.content.pm.ApplicationInfo;
import android.util.Slog;

import com.android.internal.compat.AndroidBuildClassifier;
import com.android.internal.compat.CompatibilityChangeInfo;
import com.android.internal.compat.OverrideAllowedState;
import com.android.server.compat.overrides.ChangeOverrides;
import com.android.server.compat.overrides.OverrideValue;
import com.android.server.compat.overrides.RawOverrideValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CompatChange extends CompatibilityChangeInfo {
    public final ConcurrentHashMap mEvaluatedOverrides;
    public ChangeListener mListener;
    public final ConcurrentHashMap mRawOverrides;

    public interface ChangeListener {
        void onCompatChange(String str);
    }

    public CompatChange(long j) {
        this(j, null, -1, -1, false, false, null, false);
    }

    public CompatChange(
            long j, String str, int i, int i2, boolean z, boolean z2, String str2, boolean z3) {
        super(Long.valueOf(j), str, i, i2, z, z2, str2, z3);
        this.mListener = null;
        this.mEvaluatedOverrides = new ConcurrentHashMap();
        this.mRawOverrides = new ConcurrentHashMap();
    }

    public final void addPackageOverrideInternal(String str, boolean z) {
        if (getLoggingOnly()) {
            throw new IllegalArgumentException(
                    "Can't add overrides for a logging only change " + toString());
        }
        this.mEvaluatedOverrides.put(str, Boolean.valueOf(z));
        synchronized (this) {
            ChangeListener changeListener = this.mListener;
            if (changeListener != null) {
                changeListener.onCompatChange(str);
            }
        }
    }

    public final boolean isEnabled(
            ApplicationInfo applicationInfo, AndroidBuildClassifier androidBuildClassifier) {
        Boolean bool;
        if (applicationInfo == null) {
            return !getDisabled();
        }
        String str = applicationInfo.packageName;
        if (str != null && (bool = (Boolean) this.mEvaluatedOverrides.get(str)) != null) {
            return bool.booleanValue();
        }
        if (getDisabled()) {
            return false;
        }
        return getEnableSinceTargetSdk() == -1
                || Math.min(
                                applicationInfo.targetSdkVersion,
                                androidBuildClassifier.platformTargetSdk())
                        >= getEnableSinceTargetSdk();
    }

    public final synchronized void loadOverrides(ChangeOverrides changeOverrides) {
        try {
            ChangeOverrides.Raw raw = changeOverrides.deferred;
            if (raw != null) {
                if (raw.rawOverrideValue == null) {
                    raw.rawOverrideValue = new ArrayList();
                }
                Iterator it = ((ArrayList) raw.rawOverrideValue).iterator();
                while (it.hasNext()) {
                    OverrideValue overrideValue = (OverrideValue) it.next();
                    String str = overrideValue.packageName;
                    if (str == null) {
                        Slog.e("CompatChange", "loadOverrides: PackageName is null in deferred.");
                    } else {
                        ConcurrentHashMap concurrentHashMap = this.mRawOverrides;
                        PackageOverride.Builder builder = new PackageOverride.Builder();
                        Boolean bool = overrideValue.enabled;
                        concurrentHashMap.put(
                                str,
                                builder.setEnabled(bool == null ? false : bool.booleanValue())
                                        .build());
                    }
                }
            }
            ChangeOverrides.Raw raw2 = changeOverrides.validated;
            if (raw2 != null) {
                for (OverrideValue overrideValue2 : raw2.getOverrideValue()) {
                    String str2 = overrideValue2.packageName;
                    if (str2 == null) {
                        Slog.e("CompatChange", "loadOverrides: PackageName is null in validated.");
                    } else {
                        ConcurrentHashMap concurrentHashMap2 = this.mEvaluatedOverrides;
                        Boolean bool2 = overrideValue2.enabled;
                        concurrentHashMap2.put(
                                str2,
                                Boolean.valueOf(bool2 == null ? false : bool2.booleanValue()));
                        ConcurrentHashMap concurrentHashMap3 = this.mRawOverrides;
                        String str3 = overrideValue2.packageName;
                        PackageOverride.Builder builder2 = new PackageOverride.Builder();
                        Boolean bool3 = overrideValue2.enabled;
                        concurrentHashMap3.put(
                                str3,
                                builder2.setEnabled(bool3 == null ? false : bool3.booleanValue())
                                        .build());
                    }
                }
            }
            ChangeOverrides.Raw raw3 = changeOverrides.raw;
            if (raw3 != null) {
                for (RawOverrideValue rawOverrideValue : raw3.getRawOverrideValue()) {
                    if (rawOverrideValue.packageName == null) {
                        Slog.e("CompatChange", "loadOverrides: PackageName is null in raw.");
                    } else {
                        PackageOverride.Builder builder3 = new PackageOverride.Builder();
                        Long l = rawOverrideValue.minVersionCode;
                        long j = 0;
                        PackageOverride.Builder minVersionCode =
                                builder3.setMinVersionCode(l == null ? 0L : l.longValue());
                        Long l2 = rawOverrideValue.maxVersionCode;
                        if (l2 != null) {
                            j = l2.longValue();
                        }
                        PackageOverride.Builder maxVersionCode =
                                minVersionCode.setMaxVersionCode(j);
                        Boolean bool4 = rawOverrideValue.enabled;
                        this.mRawOverrides.put(
                                rawOverrideValue.packageName,
                                maxVersionCode
                                        .setEnabled(bool4 == null ? false : bool4.booleanValue())
                                        .build());
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized boolean recheckOverride(
            String str, OverrideAllowedState overrideAllowedState, Long l) {
        if (str == null) {
            return false;
        }
        try {
            boolean z = overrideAllowedState.state == 0;
            if (l != null && this.mRawOverrides.containsKey(str) && z) {
                int evaluate =
                        ((PackageOverride) this.mRawOverrides.get(str)).evaluate(l.longValue());
                if (evaluate != 0) {
                    if (evaluate == 1) {
                        addPackageOverrideInternal(str, true);
                    } else if (evaluate == 2) {
                        addPackageOverrideInternal(str, false);
                    }
                } else if (this.mEvaluatedOverrides.remove(str) != null) {
                    synchronized (this) {
                        ChangeListener changeListener = this.mListener;
                        if (changeListener != null) {
                            changeListener.onCompatChange(str);
                        }
                    }
                }
                return true;
            }
            if (this.mEvaluatedOverrides.remove(str) != null) {
                synchronized (this) {
                    ChangeListener changeListener2 = this.mListener;
                    if (changeListener2 != null) {
                        changeListener2.onCompatChange(str);
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized ChangeOverrides saveOverrides() {
        try {
            if (this.mRawOverrides.isEmpty()) {
                return null;
            }
            ChangeOverrides changeOverrides = new ChangeOverrides();
            changeOverrides.changeId = Long.valueOf(getId());
            ChangeOverrides.Raw raw = new ChangeOverrides.Raw();
            List rawOverrideValue = raw.getRawOverrideValue();
            for (Map.Entry entry : this.mRawOverrides.entrySet()) {
                RawOverrideValue rawOverrideValue2 = new RawOverrideValue();
                rawOverrideValue2.packageName = (String) entry.getKey();
                rawOverrideValue2.minVersionCode =
                        Long.valueOf(((PackageOverride) entry.getValue()).getMinVersionCode());
                rawOverrideValue2.maxVersionCode =
                        Long.valueOf(((PackageOverride) entry.getValue()).getMaxVersionCode());
                rawOverrideValue2.enabled =
                        Boolean.valueOf(((PackageOverride) entry.getValue()).isEnabled());
                rawOverrideValue.add(rawOverrideValue2);
            }
            changeOverrides.raw = raw;
            ChangeOverrides.Raw raw2 = new ChangeOverrides.Raw();
            List overrideValue = raw2.getOverrideValue();
            for (Map.Entry entry2 : this.mEvaluatedOverrides.entrySet()) {
                OverrideValue overrideValue2 = new OverrideValue();
                overrideValue2.packageName = (String) entry2.getKey();
                Boolean bool = (Boolean) entry2.getValue();
                bool.getClass();
                overrideValue2.enabled = bool;
                overrideValue.add(overrideValue2);
            }
            changeOverrides.validated = raw2;
            return changeOverrides;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ChangeId(");
        sb.append(getId());
        if (getName() != null) {
            sb.append("; name=");
            sb.append(getName());
        }
        if (getEnableSinceTargetSdk() != -1) {
            sb.append("; enableSinceTargetSdk=");
            sb.append(getEnableSinceTargetSdk());
        }
        if (getDisabled()) {
            sb.append("; disabled");
        }
        if (getLoggingOnly()) {
            sb.append("; loggingOnly");
        }
        if (!this.mEvaluatedOverrides.isEmpty()) {
            sb.append("; packageOverrides=");
            sb.append(this.mEvaluatedOverrides);
        }
        if (!this.mRawOverrides.isEmpty()) {
            sb.append("; rawOverrides=");
            sb.append(this.mRawOverrides);
        }
        if (getOverridable()) {
            sb.append("; overridable");
        }
        sb.append(")");
        return sb.toString();
    }
}
