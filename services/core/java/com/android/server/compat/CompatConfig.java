package com.android.server.compat;

import android.app.compat.ChangeIdStateCache;
import android.app.compat.PackageOverride;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.util.LongArray;
import android.util.Slog;

import com.android.internal.compat.AndroidBuildClassifier;
import com.android.internal.compat.CompatibilityOverrideConfig;
import com.android.internal.compat.CompatibilityOverridesToRemoveConfig;
import com.android.internal.compat.OverrideAllowedState;
import com.android.server.compat.config.Change;
import com.android.server.compat.config.Config;
import com.android.server.compat.config.XmlParser;
import com.android.server.compat.overrides.ChangeOverrides;
import com.android.server.compat.overrides.Overrides;
import com.android.server.compat.overrides.XmlWriter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javax.xml.datatype.DatatypeConfigurationException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CompatConfig {
    public final AndroidBuildClassifier mAndroidBuildClassifier;
    public File mBackupOverridesFile;
    public final Context mContext;
    public final OverrideValidatorImpl mOverrideValidator;
    public File mOverridesFile;
    public final ConcurrentHashMap mChanges = new ConcurrentHashMap();
    public final Object mOverridesFileLock = new Object();

    public CompatConfig(AndroidBuildClassifier androidBuildClassifier, Context context) {
        this.mOverrideValidator = new OverrideValidatorImpl(androidBuildClassifier, context, this);
        this.mAndroidBuildClassifier = androidBuildClassifier;
        this.mContext = context;
    }

    public void addChange(CompatChange compatChange) {
        this.mChanges.put(Long.valueOf(compatChange.getId()), compatChange);
    }

    public final boolean addOverrideUnsafe(
            final long j, String str, PackageOverride packageOverride) {
        String str2;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        OverrideAllowedState overrideAllowedStateInternal =
                this.mOverrideValidator.getOverrideAllowedStateInternal(str, j, false);
        overrideAllowedStateInternal.enforce(j, str);
        Long versionCodeOrNull = getVersionCodeOrNull(str);
        CompatChange compatChange =
                (CompatChange)
                        this.mChanges.computeIfAbsent(
                                Long.valueOf(j),
                                new Function() { // from class:
                                                 // com.android.server.compat.CompatConfig$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        AtomicBoolean atomicBoolean2 = atomicBoolean;
                                        long j2 = j;
                                        atomicBoolean2.set(false);
                                        return new CompatChange(j2);
                                    }
                                });
        synchronized (compatChange) {
            if (compatChange.getLoggingOnly()) {
                throw new IllegalArgumentException(
                        "Can't add overrides for a logging only change " + compatChange.toString());
            }
            compatChange.mRawOverrides.put(str, packageOverride);
            compatChange.recheckOverride(str, overrideAllowedStateInternal, versionCodeOrNull);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(packageOverride.isEnabled() ? "Enabled" : "Disabled");
        sb.append(" change ");
        sb.append(j);
        if (compatChange.getName() != null) {
            str2 = " [" + compatChange.getName() + "]";
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append(" for ");
        sb.append(str);
        Slog.d("CompatConfig", sb.toString());
        ChangeIdStateCache.invalidate();
        return atomicBoolean.get();
    }

    public final synchronized void addPackageOverrides(
            CompatibilityOverrideConfig compatibilityOverrideConfig, String str, boolean z) {
        addPackageOverridesWithoutSaving(compatibilityOverrideConfig, str, z);
        saveOverrides();
        ChangeIdStateCache.invalidate();
    }

    public final void addPackageOverridesWithoutSaving(
            CompatibilityOverrideConfig compatibilityOverrideConfig, String str, boolean z) {
        for (Long l : compatibilityOverrideConfig.overrides.keySet()) {
            if (z) {
                l.getClass();
                if (!this.mChanges.containsKey(l)) {
                    Slog.w(
                            "CompatConfig",
                            "Trying to add overrides for unknown Change ID "
                                    + l
                                    + ". Skipping Change ID.");
                }
            }
            addOverrideUnsafe(
                    l.longValue(),
                    str,
                    (PackageOverride) compatibilityOverrideConfig.overrides.get(l));
        }
    }

    public void clearChanges() {
        this.mChanges.clear();
    }

    public void forceNonDebuggableFinalForTest(boolean z) {
        this.mOverrideValidator.mForceNonDebuggableFinalBuild = z;
    }

    public final long[] getAllowedChangesSinceTargetSdkForPackage(int i, String str) {
        LongArray longArray = new LongArray();
        for (CompatChange compatChange : this.mChanges.values()) {
            if (compatChange.getEnableSinceTargetSdk() == i) {
                if (this.mOverrideValidator.getOverrideAllowedStateInternal(
                                        str, compatChange.getId(), false)
                                .state
                        == 0) {
                    longArray.add(compatChange.getId());
                }
            }
        }
        return longArray.toArray();
    }

    public final Long getVersionCodeOrNull(String str) {
        try {
            return Long.valueOf(
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo(str, 4194304)
                            .longVersionCode);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final void initConfigFromLib(File file) {
        BufferedInputStream bufferedInputStream;
        boolean booleanValue;
        String str;
        if (!file.exists() || !file.isDirectory()) {
            Slog.d("CompatConfig", "No directory " + file + ", skipping");
            return;
        }
        for (File file2 : file.listFiles()) {
            Slog.d("CompatConfig", "Found a config file: " + file2.getPath());
            try {
                try {
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(file2));
                } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
                    e = e;
                }
                try {
                    Config read = XmlParser.read(bufferedInputStream);
                    if (read.compatChange == null) {
                        read.compatChange = new ArrayList();
                    }
                    Iterator it = ((ArrayList) read.compatChange).iterator();
                    while (it.hasNext()) {
                        Change change = (Change) it.next();
                        Slog.d("CompatConfig", "Adding: " + change.toString());
                        try {
                            ConcurrentHashMap concurrentHashMap = this.mChanges;
                            Long l = change.id;
                            long j = 0;
                            Long valueOf = Long.valueOf(l == null ? 0L : l.longValue());
                            Long l2 = change.id;
                            if (l2 != null) {
                                j = l2.longValue();
                            }
                            String str2 = change.name;
                            Integer num = change.enableAfterTargetSdk;
                            int intValue = num == null ? 0 : num.intValue();
                            Integer num2 = change.enableSinceTargetSdk;
                            int intValue2 = num2 == null ? 0 : num2.intValue();
                            Boolean bool = change.disabled;
                            boolean booleanValue2 = bool == null ? false : bool.booleanValue();
                            Boolean bool2 = change.loggingOnly;
                            boolean booleanValue3 = bool2 == null ? false : bool2.booleanValue();
                            String str3 = change.description;
                            Boolean bool3 = change.overridable;
                            if (bool3 == null) {
                                str = str3;
                                booleanValue = false;
                            } else {
                                booleanValue = bool3.booleanValue();
                                str = str3;
                            }
                            concurrentHashMap.put(
                                    valueOf,
                                    new CompatChange(
                                            j,
                                            str2,
                                            intValue,
                                            intValue2,
                                            booleanValue2,
                                            booleanValue3,
                                            str,
                                            booleanValue));
                        } catch (Throwable th) {
                            th = th;
                            Throwable th2 = th;
                            try {
                                bufferedInputStream.close();
                            } catch (Throwable th3) {
                                th2.addSuppressed(th3);
                            }
                            throw th2;
                        }
                    }
                    try {
                        bufferedInputStream.close();
                    } catch (IOException
                            | DatatypeConfigurationException
                            | XmlPullParserException e2) {
                        e = e2;
                        Slog.e(
                                "CompatConfig",
                                "Encountered an error while reading/parsing compat config file",
                                e);
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } finally {
                ChangeIdStateCache.invalidate();
            }
        }
    }

    public void initOverrides(File file, File file2) {
        for (CompatChange compatChange : this.mChanges.values()) {
            synchronized (compatChange) {
                compatChange.mRawOverrides.clear();
                compatChange.mEvaluatedOverrides.clear();
            }
        }
        loadOverrides(file2);
        synchronized (this.mOverridesFileLock) {
            try {
                this.mOverridesFile = file;
                File file3 = new File(file.getPath() + ".bak");
                this.mBackupOverridesFile = file3;
                if (file3.exists()) {
                    this.mOverridesFile.delete();
                    this.mBackupOverridesFile.renameTo(this.mOverridesFile);
                }
                loadOverrides(this.mOverridesFile);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (file2.exists()) {
            saveOverrides();
        }
    }

    public final void loadOverrides(File file) {
        if (file.exists()) {
            try {
                try {
                    BufferedInputStream bufferedInputStream =
                            new BufferedInputStream(new FileInputStream(file));
                    try {
                        Overrides read =
                                com.android.server.compat.overrides.XmlParser.read(
                                        bufferedInputStream);
                        if (read == null) {
                            Slog.w("CompatConfig", "Parsing " + file.getPath() + " failed");
                            bufferedInputStream.close();
                            return;
                        }
                        for (ChangeOverrides changeOverrides : read.getChangeOverrides()) {
                            Long l = changeOverrides.changeId;
                            long longValue = l == null ? 0L : l.longValue();
                            CompatChange compatChange =
                                    (CompatChange) this.mChanges.get(Long.valueOf(longValue));
                            if (compatChange == null) {
                                Slog.w(
                                        "CompatConfig",
                                        "Change ID "
                                                + longValue
                                                + " not found. Skipping overrides for it.");
                            } else {
                                compatChange.loadOverrides(changeOverrides);
                            }
                        }
                        bufferedInputStream.close();
                    } catch (Throwable th) {
                        try {
                            bufferedInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
                    Slog.w("CompatConfig", "Error processing " + file + " " + e.toString());
                }
            } catch (Throwable th3) {
                th3.printStackTrace();
            }
        }
    }

    public final boolean removeOverrideUnsafe(CompatChange compatChange, String str, Long l) {
        String str2;
        long id = compatChange.getId();
        boolean z = false;
        OverrideAllowedState overrideAllowedStateInternal =
                this.mOverrideValidator.getOverrideAllowedStateInternal(str, id, false);
        synchronized (compatChange) {
            if (compatChange.mRawOverrides.containsKey(str)) {
                overrideAllowedStateInternal.enforce(compatChange.getId(), str);
                compatChange.mRawOverrides.remove(str);
                compatChange.recheckOverride(str, overrideAllowedStateInternal, l);
                z = true;
            }
        }
        if (z) {
            StringBuilder sb = new StringBuilder("Reset change ");
            sb.append(id);
            if (compatChange.getName() != null) {
                str2 = " [" + compatChange.getName() + "]";
            } else {
                str2 = "";
            }
            Slog.d(
                    "CompatConfig",
                    OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                            sb, str2, " for ", str, " to default value."));
        }
        return z;
    }

    public final synchronized void removePackageOverrides(String str) {
        try {
            Long versionCodeOrNull = getVersionCodeOrNull(str);
            Iterator it = this.mChanges.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                z |= removeOverrideUnsafe((CompatChange) it.next(), str, versionCodeOrNull);
            }
            if (z) {
                saveOverrides();
                ChangeIdStateCache.invalidate();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean removePackageOverridesWithoutSaving(
            CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, String str) {
        boolean z = false;
        for (Long l : compatibilityOverridesToRemoveConfig.changeIds) {
            l.getClass();
            if (this.mChanges.containsKey(l)) {
                Long versionCodeOrNull = getVersionCodeOrNull(str);
                CompatChange compatChange = (CompatChange) this.mChanges.get(l);
                z |=
                        compatChange != null
                                ? removeOverrideUnsafe(compatChange, str, versionCodeOrNull)
                                : false;
            } else {
                Slog.w(
                        "CompatConfig",
                        "Trying to remove overrides for unknown Change ID "
                                + l
                                + ". Skipping Change ID.");
            }
        }
        return z;
    }

    public final void saveOverrides() {
        PrintWriter printWriter;
        synchronized (this.mOverridesFileLock) {
            try {
                if (this.mOverridesFile != null && this.mBackupOverridesFile != null) {
                    Overrides overrides = new Overrides();
                    List changeOverrides = overrides.getChangeOverrides();
                    Iterator it = this.mChanges.values().iterator();
                    while (it.hasNext()) {
                        ChangeOverrides saveOverrides = ((CompatChange) it.next()).saveOverrides();
                        if (saveOverrides != null) {
                            changeOverrides.add(saveOverrides);
                        }
                    }
                    if (this.mOverridesFile.exists()) {
                        if (this.mBackupOverridesFile.exists()) {
                            this.mOverridesFile.delete();
                        } else if (!this.mOverridesFile.renameTo(this.mBackupOverridesFile)) {
                            Slog.e(
                                    "CompatConfig",
                                    "Couldn't rename file "
                                            + this.mOverridesFile
                                            + " to "
                                            + this.mBackupOverridesFile);
                            return;
                        }
                    }
                    try {
                        this.mOverridesFile.createNewFile();
                        try {
                            printWriter = new PrintWriter(this.mOverridesFile);
                        } catch (IOException e) {
                            Slog.e("CompatConfig", e.toString());
                        }
                        try {
                            XmlWriter xmlWriter = new XmlWriter();
                            xmlWriter.out = printWriter;
                            xmlWriter.outBuffer = new StringBuilder();
                            xmlWriter.indent = 0;
                            xmlWriter.startLine = true;
                            XmlWriter.write(xmlWriter, overrides);
                            printWriter.close();
                            this.mBackupOverridesFile.delete();
                        } catch (Throwable th) {
                            try {
                                printWriter.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        Slog.e(
                                "CompatConfig",
                                "Could not create override config file: " + e2.toString());
                    }
                }
            } finally {
            }
        }
    }

    public final boolean willChangeBeEnabled(long j, String str) {
        boolean disabled;
        CompatChange compatChange = (CompatChange) this.mChanges.get(Long.valueOf(j));
        if (compatChange == null) {
            return true;
        }
        if (str == null) {
            disabled = compatChange.getDisabled();
        } else {
            PackageOverride packageOverride = (PackageOverride) compatChange.mRawOverrides.get(str);
            if (packageOverride != null) {
                int evaluateForAllVersions = packageOverride.evaluateForAllVersions();
                if (evaluateForAllVersions == 0) {
                    disabled = compatChange.getDisabled();
                } else {
                    if (evaluateForAllVersions == 1) {
                        return true;
                    }
                    if (evaluateForAllVersions == 2) {
                        return false;
                    }
                }
            }
            disabled = compatChange.getDisabled();
        }
        return true ^ disabled;
    }
}
