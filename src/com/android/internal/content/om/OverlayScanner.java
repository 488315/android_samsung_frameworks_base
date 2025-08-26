package com.android.internal.content.om;

import android.content.pm.parsing.ApkLite;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.content.om.OverlayConfigParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes5.dex */
public class OverlayScanner {
    private final ArrayMap<String, ParsedOverlayInfo> mParsedOverlayInfos = new ArrayMap<>();
    private final List<Pair<String, File>> mExcludedOverlayPackages = new ArrayList();

    public static class ParsedOverlayInfo {
        public final boolean isStatic;
        public final String packageName;
        public final File path;
        public final File preInstalledApexPath;
        public final int priority;
        public final String targetPackageName;
        public final int targetSdkVersion;

        public ParsedOverlayInfo(String str, String str2, int i, boolean z, int i2, File file, File file2) {
            this.packageName = str;
            this.targetPackageName = str2;
            this.targetSdkVersion = i;
            this.isStatic = z;
            this.priority = i2;
            this.path = file;
            this.preInstalledApexPath = file2;
        }

        public String toString() {
            return getClass().getSimpleName() + String.format("{packageName=%s, targetPackageName=%s, targetSdkVersion=%s, isStatic=%s, priority=%s, path=%s, preInstalledApexPath=%s}", this.packageName, this.targetPackageName, Integer.valueOf(this.targetSdkVersion), Boolean.valueOf(this.isStatic), Integer.valueOf(this.priority), this.path, this.preInstalledApexPath);
        }

        public File getOriginalPartitionPath() {
            File file = this.preInstalledApexPath;
            return file != null ? file : this.path;
        }
    }

    public final ParsedOverlayInfo getParsedInfo(String str) {
        return this.mParsedOverlayInfos.get(str);
    }

    final Collection<ParsedOverlayInfo> getAllParsedInfos() {
        return this.mParsedOverlayInfos.values();
    }

    final boolean isExcludedOverlayPackage(String str, OverlayConfigParser.OverlayPartition overlayPartition) {
        for (int i = 0; i < this.mExcludedOverlayPackages.size(); i++) {
            Pair<String, File> pair = this.mExcludedOverlayPackages.get(i);
            if (pair.first.equals(str) && overlayPartition.containsOverlay(pair.second)) {
                return true;
            }
        }
        return false;
    }

    public void scanDir(File file) {
        ParsedOverlayInfo overlayManifest;
        if (file.exists() && file.isDirectory()) {
            if (!file.canRead()) {
                Log.w("OverlayConfig", "Directory " + file + " cannot be read");
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                return;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    scanDir(file2);
                }
                if (file2.isFile() && file2.getPath().endsWith(".apk") && (overlayManifest = parseOverlayManifest(file2, this.mExcludedOverlayPackages)) != null) {
                    this.mParsedOverlayInfos.put(overlayManifest.packageName, overlayManifest);
                }
            }
        }
    }

    public ParsedOverlayInfo parseOverlayManifest(File file, List<Pair<String, File>> list) {
        ParseResult<ApkLite> apkLite = ApkLiteParseUtils.parseApkLite(ParseTypeImpl.forParsingWithoutPlatformCompat().reset(), file, 128);
        if (apkLite.isError()) {
            Log.w("OverlayConfig", "Got exception loading overlay.", apkLite.getException());
            return null;
        }
        ApkLite result = apkLite.getResult();
        if (result.getTargetPackageName() == null) {
            return null;
        }
        String requiredSystemPropertyName = result.getRequiredSystemPropertyName();
        String requiredSystemPropertyValue = result.getRequiredSystemPropertyValue();
        if ((!TextUtils.isEmpty(requiredSystemPropertyName) || !TextUtils.isEmpty(requiredSystemPropertyValue)) && !FrameworkParsingPackageUtils.checkRequiredSystemProperties(requiredSystemPropertyName, requiredSystemPropertyValue)) {
            list.add(Pair.create(result.getPackageName(), file));
            return null;
        }
        return new ParsedOverlayInfo(result.getPackageName(), result.getTargetPackageName(), result.getTargetSdkVersion(), result.isOverlayIsStatic(), result.getOverlayPriority(), new File(result.getPath()), null);
    }
}
