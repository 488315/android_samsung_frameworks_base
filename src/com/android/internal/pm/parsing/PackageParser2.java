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

/* loaded from: classes5.dex */
public class PackageParser2 implements AutoCloseable {
    private static final boolean LOG_PARSE_TIMINGS = Build.IS_DEBUGGABLE;
    private static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    private static final String TAG = "PackageParsing";
    protected IPackageCacher mCacher;
    private final ParsingPackageUtils mParsingUtils;
    private final ThreadLocal<ApplicationInfo> mSharedAppInfo = ThreadLocal.withInitial(new Supplier() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return PackageParser2.lambda$new$0();
        }
    });
    private final ThreadLocal<ParseTypeImpl> mSharedResult;

    static /* synthetic */ ApplicationInfo lambda$new$0() {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.uid = -1;
        return applicationInfo;
    }

    public PackageParser2(String[] strArr, DisplayMetrics displayMetrics, IPackageCacher iPackageCacher, final Callback callback) {
        PermissionManager permissionManager;
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            displayMetrics.setToDefaults();
        }
        Application currentApplication = ActivityThread.currentApplication();
        List<PermissionManager.SplitPermissionInfo> splitPermissions = (currentApplication == null || (permissionManager = (PermissionManager) currentApplication.getSystemService(PermissionManager.class)) == null) ? null : permissionManager.getSplitPermissions();
        splitPermissions = splitPermissions == null ? new ArrayList<>() : splitPermissions;
        this.mCacher = iPackageCacher;
        this.mParsingUtils = new ParsingPackageUtils(strArr, displayMetrics, splitPermissions, callback);
        final ParseInput.Callback callback2 = new ParseInput.Callback() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda1
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, String str, int i) {
                boolean lambda$new$1;
                lambda$new$1 = PackageParser2.this.lambda$new$1(callback, j, str, i);
                return lambda$new$1;
            }
        };
        this.mSharedResult = ThreadLocal.withInitial(new Supplier() { // from class: com.android.internal.pm.parsing.PackageParser2$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return PackageParser2.lambda$new$2(ParseInput.Callback.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(Callback callback, long j, String str, int i) {
        ApplicationInfo applicationInfo = this.mSharedAppInfo.get();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        return callback.isChangeEnabled(j, applicationInfo);
    }

    static /* synthetic */ ParseTypeImpl lambda$new$2(ParseInput.Callback callback) {
        return new ParseTypeImpl(callback);
    }

    public ParsedPackage parsePackage(File file, int i, boolean z) throws PackageParserException {
        IPackageCacher iPackageCacher;
        ParsedPackage cachedResult;
        File[] listFiles = file.listFiles();
        if (ArrayUtils.size(listFiles) == 1 && listFiles[0].isDirectory()) {
            file = listFiles[0];
        }
        if (z && (iPackageCacher = this.mCacher) != null && (cachedResult = iPackageCacher.getCachedResult(file, i)) != null) {
            return cachedResult;
        }
        boolean z2 = LOG_PARSE_TIMINGS;
        long uptimeMillis = z2 ? SystemClock.uptimeMillis() : 0L;
        ParseResult<ParsingPackage> parsePackage = this.mParsingUtils.parsePackage(this.mSharedResult.get().reset(), file, i);
        if (parsePackage.isError()) {
            throw new PackageParserException(parsePackage.getErrorCode(), parsePackage.getErrorMessage(), parsePackage.getException());
        }
        ParsedPackage hideAsParsed = parsePackage.getResult().hideAsParsed();
        long uptimeMillis2 = z2 ? SystemClock.uptimeMillis() : 0L;
        IPackageCacher iPackageCacher2 = this.mCacher;
        if (iPackageCacher2 != null) {
            iPackageCacher2.cacheResult(file, i, hideAsParsed);
        }
        if (z2) {
            long j = uptimeMillis2 - uptimeMillis;
            long uptimeMillis3 = SystemClock.uptimeMillis() - uptimeMillis2;
            if (j + uptimeMillis3 > 100) {
                Slog.i("PackageParsing", "Parse times for '" + file + "': parse=" + j + "ms, update_cache=" + uptimeMillis3 + " ms");
            }
        }
        return hideAsParsed;
    }

    public ParsedPackage parsePackageFromPackageLite(PackageLite packageLite, int i) throws PackageParserException {
        ParseResult<ParsingPackage> parsePackageFromPackageLite = this.mParsingUtils.parsePackageFromPackageLite(this.mSharedResult.get().reset(), packageLite, i);
        if (parsePackageFromPackageLite.isError()) {
            throw new PackageParserException(parsePackageFromPackageLite.getErrorCode(), parsePackageFromPackageLite.getErrorMessage(), parsePackageFromPackageLite.getException());
        }
        return parsePackageFromPackageLite.getResult().hideAsParsed();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mSharedResult.remove();
        this.mSharedAppInfo.remove();
    }

    public static abstract class Callback implements ParsingPackageUtils.Callback {
        public abstract boolean isChangeEnabled(long j, ApplicationInfo applicationInfo);

        @Override // com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback
        public final ParsingPackage startParsingPackage(String str, String str2, String str3, TypedArray typedArray, boolean z) {
            return PackageImpl.forParsing(str, str2, str3, typedArray, z, this);
        }
    }
}
