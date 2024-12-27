package com.android.server.pm;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.content.pm.ApplicationInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserPackage;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.LocaleList;
import android.os.UserHandle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.format.TimeMigrationUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.pm.ShortcutService;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShortcutUser {
    public final AppSearchManager mAppSearchManager;
    public String mCachedLauncher;
    public String mKnownLocales;
    public String mLastAppScanOsFingerprint;
    public long mLastAppScanTime;
    public String mRestoreFromOsFingerprint;
    public final ShortcutService mService;
    public final int mUserId;
    public final ArrayMap mPackages = new ArrayMap();
    public final ArrayMap mLaunchers = new ArrayMap();
    public final Object mLock = new Object();
    public final ArrayList mInFlightSessions = new ArrayList();
    public final Executor mExecutor = FgThread.getExecutor();

    public ShortcutUser(ShortcutService shortcutService, int i) {
        this.mService = shortcutService;
        this.mUserId = i;
        this.mAppSearchManager = (AppSearchManager) shortcutService.mContext.createContextAsUser(UserHandle.of(i), 0).getSystemService(AppSearchManager.class);
    }

    public static void forMainFilesIn(File file, Consumer consumer) {
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (!file2.getName().endsWith(".reservecopy") && !file2.getName().endsWith(".backup")) {
                    consumer.accept(file2);
                }
            }
        }
    }

    public final void cancelAllInFlightTasks() {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mInFlightSessions.iterator();
                while (it.hasNext()) {
                    ((AndroidFuture) it.next()).cancel(true);
                }
                this.mInFlightSessions.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void detectLocaleChange(boolean z) {
        ShortcutService shortcutService = this.mService;
        shortcutService.getClass();
        String languageTags = LocaleList.getDefault().toLanguageTags();
        if (z || TextUtils.isEmpty(this.mKnownLocales) || !this.mKnownLocales.equals(languageTags)) {
            this.mKnownLocales = languageTags;
            forAllPackages(new ShortcutUser$$ExternalSyntheticLambda0(0));
            shortcutService.scheduleSaveInner(this.mUserId);
        }
    }

    public final void dump(PrintWriter printWriter, ShortcutService.DumpFilter dumpFilter) {
        ArrayMap arrayMap;
        int i = 1;
        String str = "  ";
        if (dumpFilter.mDumpDetails) {
            printWriter.print("  ");
            printWriter.print("User: ");
            printWriter.print(this.mUserId);
            printWriter.print("  Known locales: ");
            printWriter.print(this.mKnownLocales);
            printWriter.print("  Last app scan: [");
            printWriter.print(this.mLastAppScanTime);
            printWriter.print("] ");
            long j = this.mLastAppScanTime;
            AtomicBoolean atomicBoolean = ShortcutService.sIsEmergencyMode;
            printWriter.println(TimeMigrationUtils.formatMillisWithFixedFormat(j));
            str = "      ";
            printWriter.print("      ");
            printWriter.print("Last app scan FP: ");
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, this.mLastAppScanOsFingerprint, "      ", "Restore from FP: ");
            printWriter.print(this.mRestoreFromOsFingerprint);
            printWriter.println();
            printWriter.print("      ");
            printWriter.print("Cached launcher: ");
            printWriter.print(this.mCachedLauncher);
            printWriter.println();
        }
        for (int i2 = 0; i2 < this.mLaunchers.size(); i2++) {
            ShortcutLauncher shortcutLauncher = (ShortcutLauncher) this.mLaunchers.valueAt(i2);
            if (dumpFilter.isPackageMatch(shortcutLauncher.mPackageName)) {
                printWriter.println();
                printWriter.print(str);
                printWriter.print("Launcher: ");
                printWriter.print(shortcutLauncher.mPackageName);
                printWriter.print("  Package user: ");
                printWriter.print(shortcutLauncher.mPackageUserId);
                printWriter.print("  Owner user: ");
                printWriter.print(shortcutLauncher.mOwnerUserId);
                printWriter.println();
                shortcutLauncher.mPackageInfo.dump(printWriter, str + "  ");
                printWriter.println();
                synchronized (shortcutLauncher.mPackageItemLock) {
                    arrayMap = new ArrayMap(shortcutLauncher.mPinnedShortcuts);
                }
                int size = arrayMap.size();
                for (int i3 = 0; i3 < size; i3++) {
                    printWriter.println();
                    UserPackage userPackage = (UserPackage) arrayMap.keyAt(i3);
                    printWriter.print(str);
                    printWriter.print("  ");
                    printWriter.print("Package: ");
                    printWriter.print(userPackage.packageName);
                    printWriter.print("  User: ");
                    printWriter.println(userPackage.userId);
                    ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
                    int size2 = arraySet.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        printWriter.print(str);
                        printWriter.print("    Pinned: ");
                        printWriter.print((String) arraySet.valueAt(i4));
                        printWriter.println();
                    }
                }
            }
        }
        for (int i5 = 0; i5 < this.mPackages.size(); i5++) {
            ShortcutPackage shortcutPackage = (ShortcutPackage) this.mPackages.valueAt(i5);
            if (dumpFilter.isPackageMatch(shortcutPackage.mPackageName)) {
                printWriter.println();
                printWriter.print(str);
                printWriter.print("Package: ");
                printWriter.print(shortcutPackage.mPackageName);
                printWriter.print("  UID: ");
                printWriter.print(shortcutPackage.mPackageUid);
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print("Calls: ");
                printWriter.print(shortcutPackage.getApiCallCount(false));
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print("Last known FG: ");
                printWriter.print(shortcutPackage.mLastKnownForegroundElapsedTime);
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print("Last reset: [");
                printWriter.print(shortcutPackage.mLastResetTime);
                printWriter.print("] ");
                printWriter.print(TimeMigrationUtils.formatMillisWithFixedFormat(shortcutPackage.mLastResetTime));
                printWriter.println();
                shortcutPackage.mPackageInfo.dump(printWriter, str + "  ");
                printWriter.println();
                printWriter.print(str);
                printWriter.println("  Shortcuts:");
                long[] jArr = new long[1];
                shortcutPackage.forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda2(printWriter, str, jArr, i));
                printWriter.print(str);
                printWriter.print("  ");
                printWriter.print("Total bitmap size: ");
                printWriter.print(jArr[0]);
                printWriter.print(" (");
                printWriter.print(Formatter.formatFileSize(shortcutPackage.mShortcutUser.mService.mContext, jArr[0]));
                printWriter.println(")");
                printWriter.println();
                synchronized (shortcutPackage.mPackageItemLock) {
                    shortcutPackage.mShortcutBitmapSaver.dumpLocked(printWriter);
                }
            }
        }
        if (dumpFilter.mDumpDetails) {
            printWriter.println();
            printWriter.print(str);
            printWriter.println("Bitmap directories: ");
            dumpDirectorySize(printWriter, str + "  ", this.mService.getUserBitmapFilePath(this.mUserId));
        }
    }

    public final JSONObject dumpCheckin(boolean z) {
        ShortcutUser shortcutUser = this;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("userId", shortcutUser.mUserId);
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        for (int i2 = 0; i2 < shortcutUser.mLaunchers.size(); i2++) {
            ShortcutLauncher shortcutLauncher = (ShortcutLauncher) shortcutUser.mLaunchers.valueAt(i2);
            shortcutLauncher.getClass();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("name", shortcutLauncher.mPackageName);
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("launchers", jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        while (i < shortcutUser.mPackages.size()) {
            ShortcutPackage shortcutPackage = (ShortcutPackage) shortcutUser.mPackages.valueAt(i);
            shortcutPackage.getClass();
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("name", shortcutPackage.mPackageName);
            final int[] iArr = new int[1];
            final int[] iArr2 = new int[1];
            final int[] iArr3 = new int[1];
            final int[] iArr4 = new int[1];
            final long[] jArr = new long[1];
            shortcutPackage.forEachShortcut(new Consumer() { // from class: com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int[] iArr5 = iArr;
                    int[] iArr6 = iArr3;
                    int[] iArr7 = iArr2;
                    int[] iArr8 = iArr4;
                    long[] jArr2 = jArr;
                    ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                    if (shortcutInfo.isDynamic()) {
                        iArr5[0] = iArr5[0] + 1;
                    }
                    if (shortcutInfo.isDeclaredInManifest()) {
                        iArr6[0] = iArr6[0] + 1;
                    }
                    if (shortcutInfo.isPinned()) {
                        iArr7[0] = iArr7[0] + 1;
                    }
                    if (shortcutInfo.getBitmapPath() != null) {
                        iArr8[0] = iArr8[0] + 1;
                        jArr2[0] = new File(shortcutInfo.getBitmapPath()).length() + jArr2[0];
                    }
                }
            });
            jSONObject3.put("dynamic", iArr[0]);
            jSONObject3.put("manifest", iArr3[0]);
            jSONObject3.put("pinned", iArr2[0]);
            jSONObject3.put("bitmaps", iArr4[0]);
            jSONObject3.put("bitmapBytes", jArr[0]);
            jSONArray2.put(jSONObject3);
            i++;
            shortcutUser = this;
        }
        jSONObject.put("packages", jSONArray2);
        return jSONObject;
    }

    public final void dumpDirectorySize(PrintWriter printWriter, String str, File file) {
        int i = 0;
        long j = 0;
        if (file.listFiles() != null) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            long j2 = 0;
            int i2 = 0;
            while (i < length) {
                File file2 = listFiles[i];
                if (file2.isFile()) {
                    i2++;
                    j2 = file2.length() + j2;
                } else if (file2.isDirectory()) {
                    dumpDirectorySize(printWriter, str + "  ", file2);
                }
                i++;
            }
            i = i2;
            j = j2;
        }
        printWriter.print(str);
        printWriter.print("Path: ");
        printWriter.print(file.getName());
        printWriter.print("/ has ");
        printWriter.print(i);
        printWriter.print(" files, size=");
        printWriter.print(j);
        printWriter.print(" (");
        printWriter.print(Formatter.formatFileSize(this.mService.mContext, j));
        printWriter.println(")");
    }

    public final void forAllLaunchers(Consumer consumer) {
        int size = this.mLaunchers.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mLaunchers.valueAt(i));
        }
    }

    public final void forAllPackageItems(Consumer consumer) {
        forAllLaunchers(consumer);
        forAllPackages(consumer);
    }

    public final void forAllPackages(Consumer consumer) {
        int size = this.mPackages.size();
        for (int i = 0; i < size; i++) {
            consumer.accept(this.mPackages.valueAt(i));
        }
    }

    public ArrayMap getAllLaunchersForTest() {
        return this.mLaunchers;
    }

    public ArrayMap getAllPackagesForTest() {
        return this.mPackages;
    }

    public final AndroidFuture getAppSearch(AppSearchManager.SearchContext searchContext) {
        final AndroidFuture androidFuture = new AndroidFuture();
        synchronized (this.mLock) {
            this.mInFlightSessions.removeIf(new ShortcutUser$$ExternalSyntheticLambda7());
            this.mInFlightSessions.add(androidFuture);
        }
        if (this.mAppSearchManager == null) {
            androidFuture.completeExceptionally(new RuntimeException("app search manager is null"));
            return androidFuture;
        }
        if (!this.mService.mUserManagerInternal.isUserUnlockingOrUnlocked(this.mUserId)) {
            androidFuture.completeExceptionally(new RuntimeException(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, new StringBuilder("User "), " is ")));
            return androidFuture;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppSearchManager.createSearchSession(searchContext, this.mExecutor, new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AndroidFuture androidFuture2 = androidFuture;
                    AppSearchResult appSearchResult = (AppSearchResult) obj;
                    if (appSearchResult.isSuccess()) {
                        androidFuture2.complete((AppSearchSession) appSearchResult.getResultValue());
                    } else {
                        androidFuture2.completeExceptionally(new RuntimeException(appSearchResult.getErrorMessage()));
                    }
                }
            });
            return androidFuture;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ShortcutPackage getPackageShortcuts(String str) {
        ShortcutPackage packageShortcutsIfExists = getPackageShortcutsIfExists(str);
        if (packageShortcutsIfExists != null) {
            return packageShortcutsIfExists;
        }
        ShortcutPackage shortcutPackage = new ShortcutPackage(this, this.mUserId, str);
        this.mPackages.put(str, shortcutPackage);
        return shortcutPackage;
    }

    public final ShortcutPackage getPackageShortcutsIfExists(String str) {
        ShortcutPackage shortcutPackage = (ShortcutPackage) this.mPackages.get(str);
        if (shortcutPackage != null) {
            shortcutPackage.attemptToRestoreIfNeededAndSave();
        }
        return shortcutPackage;
    }

    public final void mergeRestoredFile(ShortcutUser shortcutUser) {
        final int[] iArr = new int[1];
        final int[] iArr2 = new int[1];
        final int[] iArr3 = new int[1];
        this.mLaunchers.clear();
        final ShortcutService shortcutService = this.mService;
        shortcutUser.forAllLaunchers(new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ApplicationInfo injectApplicationInfoWithUninstalled;
                ShortcutUser shortcutUser2 = ShortcutUser.this;
                ShortcutService shortcutService2 = shortcutService;
                int[] iArr4 = iArr;
                ShortcutLauncher shortcutLauncher = (ShortcutLauncher) obj;
                shortcutUser2.getClass();
                String str = shortcutLauncher.mPackageName;
                int i = shortcutUser2.mUserId;
                boolean isPackageInstalled = shortcutService2.isPackageInstalled(i, str);
                String str2 = shortcutLauncher.mPackageName;
                if (!isPackageInstalled || (((injectApplicationInfoWithUninstalled = shortcutService2.injectApplicationInfoWithUninstalled(str2, i)) != null && (injectApplicationInfoWithUninstalled.flags & 32768) == 32768) || shortcutUser2.mService.mSmartSwitchBackupAllowed.get())) {
                    shortcutLauncher.mShortcutUser = shortcutUser2;
                    shortcutUser2.mLaunchers.put(UserPackage.of(shortcutLauncher.mPackageUserId, str2), shortcutLauncher);
                    iArr4[0] = iArr4[0] + 1;
                }
            }
        });
        shortcutUser.forAllPackages(new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int size;
                ApplicationInfo injectApplicationInfoWithUninstalled;
                ShortcutUser shortcutUser2 = ShortcutUser.this;
                ShortcutService shortcutService2 = shortcutService;
                int[] iArr4 = iArr2;
                int[] iArr5 = iArr3;
                ShortcutPackage shortcutPackage = (ShortcutPackage) obj;
                shortcutUser2.getClass();
                String str = shortcutPackage.mPackageName;
                int i = shortcutUser2.mUserId;
                boolean isPackageInstalled = shortcutService2.isPackageInstalled(i, str);
                String str2 = shortcutPackage.mPackageName;
                if (!isPackageInstalled || (((injectApplicationInfoWithUninstalled = shortcutService2.injectApplicationInfoWithUninstalled(str2, i)) != null && (injectApplicationInfoWithUninstalled.flags & 32768) == 32768) || shortcutUser2.mService.mSmartSwitchBackupAllowed.get())) {
                    ShortcutPackage packageShortcutsIfExists = shortcutUser2.getPackageShortcutsIfExists(str2);
                    if (packageShortcutsIfExists != null) {
                        boolean[] zArr = new boolean[1];
                        packageShortcutsIfExists.forEachShortcutStopWhen(new ShortcutPackage$$ExternalSyntheticLambda3(1, zArr));
                        if (zArr[0]) {
                            Log.w("ShortcutService", "Shortcuts for package " + str2 + " are being restored. Existing non-manifeset shortcuts will be overwritten.");
                        }
                    }
                    if (packageShortcutsIfExists != null) {
                        for (int size2 = packageShortcutsIfExists.mShortcuts.size() - 1; size2 >= 0; size2--) {
                            ShortcutInfo shortcutInfo = (ShortcutInfo) packageShortcutsIfExists.mShortcuts.valueAt(size2);
                            if (shortcutInfo != null && !shortcutInfo.isDeclaredInManifest() && shortcutInfo.isDynamic() && !shortcutInfo.isPinned()) {
                                DualAppManagerService$$ExternalSyntheticOutline0.m("Shortcuts for package ", str2, " - dynamic shortcut are being kept.", "ShortcutService");
                                shortcutPackage.forceReplaceShortcutInner(shortcutInfo);
                            }
                        }
                    }
                    if (shortcutPackage.isAppSearchEnabled()) {
                        ShortcutPackage.runAsSystem(new ShortcutPackage$$ExternalSyntheticLambda26(shortcutPackage));
                    }
                    shortcutPackage.mShortcutUser = shortcutUser2;
                    shortcutUser2.mPackages.put(str2, shortcutPackage);
                    iArr4[0] = iArr4[0] + 1;
                    int i2 = iArr5[0];
                    synchronized (shortcutPackage.mPackageItemLock) {
                        size = shortcutPackage.mShortcuts.size();
                    }
                    iArr5[0] = size + i2;
                }
            }
        });
        shortcutUser.mLaunchers.clear();
        shortcutUser.mPackages.clear();
        this.mRestoreFromOsFingerprint = shortcutUser.mRestoreFromOsFingerprint;
        StringBuilder sb = new StringBuilder("Restored: L=");
        sb.append(iArr[0]);
        sb.append(" P=");
        sb.append(iArr2[0]);
        sb.append(" S=");
        SystemServiceManager$$ExternalSyntheticOutline0.m(sb, iArr3[0], "ShortcutService");
    }

    public final void rescanPackageIfNeeded(String str, boolean z) {
        boolean z2 = !this.mPackages.containsKey(str);
        StringBuilder sb = new StringBuilder("rescanPackageIfNeeded ");
        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(this.mUserId, "@", str, ", forceRescan=", sb);
        sb.append(z);
        sb.append(" , isNewApp=");
        sb.append(z2);
        Slog.d("ShortcutService", sb.toString());
        ShortcutPackage packageShortcuts = getPackageShortcuts(str);
        if (packageShortcuts.rescanPackageIfNeeded(z2, z) || !z2) {
            return;
        }
        this.mPackages.remove(str);
        packageShortcuts.removeShortcutPackageItem();
    }
}
