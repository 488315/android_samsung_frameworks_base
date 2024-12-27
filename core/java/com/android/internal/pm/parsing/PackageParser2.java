package com.android.internal.pm.parsing;

import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseInput;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.SystemClock;
import android.permission.PermissionManager;
import android.util.DisplayMetrics;
import android.util.Slog;

import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackage;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.util.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PackageParser2 implements AutoCloseable {
    private static final boolean LOG_PARSE_TIMINGS = Build.IS_DEBUGGABLE;
    private static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    private static final String TAG = "PackageParsing";
    protected IPackageCacher mCacher;
    private final ParsingPackageUtils mParsingUtils;
    private final ThreadLocal<ApplicationInfo> mSharedAppInfo =
            ThreadLocal.withInitial(
                    new Supplier() { // from class:
                                     // com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return PackageParser2.lambda$new$0();
                        }
                    });
    private final ThreadLocal<ParseTypeImpl> mSharedResult;

    static /* synthetic */ ApplicationInfo lambda$new$0() {
        ApplicationInfo appInfo = new ApplicationInfo();
        appInfo.uid = -1;
        return appInfo;
    }

    public PackageParser2(
            String[] separateProcesses,
            DisplayMetrics displayMetrics,
            IPackageCacher cacher,
            final Callback callback) {
        PermissionManager permissionManager;
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
        }
        List<PermissionManager.SplitPermissionInfo> splitPermissions = null;
        Application application = ActivityThread.currentApplication();
        if (application != null
                && (permissionManager =
                                (PermissionManager)
                                        application.getSystemService(PermissionManager.class))
                        != null) {
            splitPermissions = permissionManager.getSplitPermissions();
        }
        splitPermissions = splitPermissions == null ? new ArrayList() : splitPermissions;
        this.mCacher = cacher;
        this.mParsingUtils =
                new ParsingPackageUtils(
                        separateProcesses, displayMetrics, splitPermissions, callback);
        final ParseInput.Callback enforcementCallback =
                new ParseInput
                        .Callback() { // from class:
                                      // com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda1
                    @Override // android.content.pm.parsing.result.ParseInput.Callback
                    public final boolean isChangeEnabled(long j, String str, int i) {
                        boolean lambda$new$1;
                        lambda$new$1 = PackageParser2.this.lambda$new$1(callback, j, str, i);
                        return lambda$new$1;
                    }
                };
        this.mSharedResult =
                ThreadLocal.withInitial(
                        new Supplier() { // from class:
                                         // com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda2
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                return PackageParser2.lambda$new$2(ParseInput.Callback.this);
                            }
                        });
    }

    public /* synthetic */ boolean lambda$new$1(
            Callback callback, long changeId, String packageName, int targetSdkVersion) {
        ApplicationInfo appInfo = this.mSharedAppInfo.get();
        appInfo.packageName = packageName;
        appInfo.targetSdkVersion = targetSdkVersion;
        return callback.isChangeEnabled(changeId, appInfo);
    }

    static /* synthetic */ ParseTypeImpl lambda$new$2(ParseInput.Callback enforcementCallback) {
        return new ParseTypeImpl(enforcementCallback);
    }

    public ParsedPackage parsePackage(File packageFile, int flags, boolean useCaches)
            throws PackageParserException {
        ParsedPackage parsed;
        File[] files = packageFile.listFiles();
        if (ArrayUtils.size(files) == 1 && files[0].isDirectory()) {
            packageFile = files[0];
        }
        if (useCaches
                && this.mCacher != null
                && (parsed = this.mCacher.getCachedResult(packageFile, flags)) != null) {
            return parsed;
        }
        long parseTime = LOG_PARSE_TIMINGS ? SystemClock.uptimeMillis() : 0L;
        ParseInput input = this.mSharedResult.get().reset();
        ParseResult<ParsingPackage> result =
                this.mParsingUtils.parsePackage(input, packageFile, flags);
        if (result.isError()) {
            throw new PackageParserException(
                    result.getErrorCode(), result.getErrorMessage(), result.getException());
        }
        ParsedPackage parsed2 = result.getResult().hideAsParsed();
        long cacheTime = LOG_PARSE_TIMINGS ? SystemClock.uptimeMillis() : 0L;
        if (this.mCacher != null) {
            this.mCacher.cacheResult(packageFile, flags, parsed2);
        }
        if (LOG_PARSE_TIMINGS) {
            long parseTime2 = cacheTime - parseTime;
            long cacheTime2 = SystemClock.uptimeMillis() - cacheTime;
            if (parseTime2 + cacheTime2 > 100) {
                Slog.i(
                        "PackageParsing",
                        "Parse times for '"
                                + packageFile
                                + "': parse="
                                + parseTime2
                                + "ms, update_cache="
                                + cacheTime2
                                + " ms");
            }
        }
        return parsed2;
    }

    public ParsedPackage parsePackageFromPackageLite(PackageLite packageLite, int flags)
            throws PackageParserException {
        ParseInput input = this.mSharedResult.get().reset();
        ParseResult<ParsingPackage> result =
                this.mParsingUtils.parsePackageFromPackageLite(input, packageLite, flags);
        if (result.isError()) {
            throw new PackageParserException(
                    result.getErrorCode(), result.getErrorMessage(), result.getException());
        }
        return result.getResult().hideAsParsed();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mSharedResult.remove();
        this.mSharedAppInfo.remove();
    }

    public abstract static class Callback implements ParsingPackageUtils.Callback {
        public abstract boolean isChangeEnabled(long j, ApplicationInfo applicationInfo);

        @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
        public final ParsingPackage startParsingPackage(
                String packageName,
                String baseCodePath,
                String codePath,
                TypedArray manifestArray,
                boolean isCoreApp) {
            return PackageImpl.forParsing(
                    packageName, baseCodePath, codePath, manifestArray, isCoreApp, this);
        }
    }
}
