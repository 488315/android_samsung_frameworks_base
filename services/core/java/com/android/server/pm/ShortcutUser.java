package com.android.server.pm;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.AppSearchSession;
import android.content.pm.UserPackage;
import android.metrics.LogMaker;
import android.os.Binder;
import android.os.FileUtils;
import android.os.UserHandle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.logging.MetricsLogger;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.FgThread;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ShortcutUser {
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
    this.mAppSearchManager =
        (AppSearchManager)
            shortcutService
                .mContext
                .createContextAsUser(UserHandle.of(i), 0)
                .getSystemService(AppSearchManager.class);
  }

  public int getUserId() {
    return this.mUserId;
  }

  public long getLastAppScanTime() {
    return this.mLastAppScanTime;
  }

  public void setLastAppScanTime(long j) {
    this.mLastAppScanTime = j;
  }

  public String getLastAppScanOsFingerprint() {
    return this.mLastAppScanOsFingerprint;
  }

  public void setLastAppScanOsFingerprint(String str) {
    this.mLastAppScanOsFingerprint = str;
  }

  public ArrayMap getAllPackagesForTest() {
    return this.mPackages;
  }

  public final void addPackage(ShortcutPackage shortcutPackage) {
    shortcutPackage.replaceUser(this);
    this.mPackages.put(shortcutPackage.getPackageName(), shortcutPackage);
  }

  public ShortcutPackage removePackage(String str) {
    ShortcutPackage shortcutPackage = (ShortcutPackage) this.mPackages.remove(str);
    if (shortcutPackage != null) {
      shortcutPackage.removeAllShortcutsAsync();
    }
    this.mService.cleanupBitmapsForPackage(this.mUserId, str);
    return shortcutPackage;
  }

  public ArrayMap getAllLaunchersForTest() {
    return this.mLaunchers;
  }

  public final void addLauncher(ShortcutLauncher shortcutLauncher) {
    shortcutLauncher.replaceUser(this);
    this.mLaunchers.put(
        UserPackage.of(shortcutLauncher.getPackageUserId(), shortcutLauncher.getPackageName()),
        shortcutLauncher);
  }

  public ShortcutLauncher removeLauncher(int i, String str) {
    return (ShortcutLauncher) this.mLaunchers.remove(UserPackage.of(i, str));
  }

  public ShortcutPackage getPackageShortcutsIfExists(String str) {
    ShortcutPackage shortcutPackage = (ShortcutPackage) this.mPackages.get(str);
    if (shortcutPackage != null) {
      shortcutPackage.attemptToRestoreIfNeededAndSave();
    }
    return shortcutPackage;
  }

  public ShortcutPackage getPackageShortcuts(String str) {
    ShortcutPackage packageShortcutsIfExists = getPackageShortcutsIfExists(str);
    if (packageShortcutsIfExists != null) {
      return packageShortcutsIfExists;
    }
    ShortcutPackage shortcutPackage = new ShortcutPackage(this, this.mUserId, str);
    this.mPackages.put(str, shortcutPackage);
    return shortcutPackage;
  }

  public ShortcutLauncher getLauncherShortcuts(String str, int i) {
    UserPackage of = UserPackage.of(i, str);
    ShortcutLauncher shortcutLauncher = (ShortcutLauncher) this.mLaunchers.get(of);
    if (shortcutLauncher == null) {
      ShortcutLauncher shortcutLauncher2 = new ShortcutLauncher(this, this.mUserId, str, i);
      this.mLaunchers.put(of, shortcutLauncher2);
      return shortcutLauncher2;
    }
    shortcutLauncher.attemptToRestoreIfNeededAndSave();
    return shortcutLauncher;
  }

  public void forAllPackages(Consumer consumer) {
    int size = this.mPackages.size();
    for (int i = 0; i < size; i++) {
      consumer.accept(this.mPackages.valueAt(i));
    }
  }

  public void forAllLaunchers(Consumer consumer) {
    int size = this.mLaunchers.size();
    for (int i = 0; i < size; i++) {
      consumer.accept(this.mLaunchers.valueAt(i));
    }
  }

  public void forAllPackageItems(Consumer consumer) {
    forAllLaunchers(consumer);
    forAllPackages(consumer);
  }

  public void forPackageItem(final String str, final int i, final Consumer consumer) {
    forAllPackageItems(
        new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ShortcutUser.lambda$forPackageItem$0(i, str, consumer, (ShortcutPackageItem) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$forPackageItem$0(
      int i, String str, Consumer consumer, ShortcutPackageItem shortcutPackageItem) {
    if (shortcutPackageItem.getPackageUserId() == i
        && shortcutPackageItem.getPackageName().equals(str)) {
      consumer.accept(shortcutPackageItem);
    }
  }

  public void onCalledByPublisher(String str) {
    detectLocaleChange();
    rescanPackageIfNeeded(str, false);
  }

  public void detectLocaleChange() {
    detectLocaleChange(false);
  }

  public void detectLocaleChange(boolean z) {
    String injectGetLocaleTagsForUser = this.mService.injectGetLocaleTagsForUser(this.mUserId);
    if (z
        || TextUtils.isEmpty(this.mKnownLocales)
        || !this.mKnownLocales.equals(injectGetLocaleTagsForUser)) {
      this.mKnownLocales = injectGetLocaleTagsForUser;
      forAllPackages(
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutUser.lambda$detectLocaleChange$1((ShortcutPackage) obj);
            }
          });
      this.mService.scheduleSaveUser(this.mUserId);
    }
  }

  public static /* synthetic */ void lambda$detectLocaleChange$1(ShortcutPackage shortcutPackage) {
    shortcutPackage.resetRateLimiting();
    shortcutPackage.resolveResourceStrings();
  }

  public void rescanPackageIfNeeded(String str, boolean z) {
    boolean z2 = !this.mPackages.containsKey(str);
    ShortcutPackage packageShortcuts = getPackageShortcuts(str);
    if (packageShortcuts.rescanPackageIfNeeded(z2, z) || !z2) {
      return;
    }
    this.mPackages.remove(str);
    packageShortcuts.removeShortcutPackageItem();
  }

  public void attemptToRestoreIfNeededAndSave(ShortcutService shortcutService, String str, int i) {
    forPackageItem(
        str,
        i,
        new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda5
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((ShortcutPackageItem) obj).attemptToRestoreIfNeededAndSave();
          }
        });
  }

  public void saveToXml(TypedXmlSerializer typedXmlSerializer, boolean z) {
    typedXmlSerializer.startTag((String) null, "user");
    if (!z) {
      ShortcutService.writeAttr(typedXmlSerializer, "locales", this.mKnownLocales);
      ShortcutService.writeAttr(typedXmlSerializer, "last-app-scan-time2", this.mLastAppScanTime);
      ShortcutService.writeAttr(
          typedXmlSerializer, "last-app-scan-fp", this.mLastAppScanOsFingerprint);
      ShortcutService.writeAttr(
          typedXmlSerializer, "restore-from-fp", this.mRestoreFromOsFingerprint);
    } else {
      ShortcutService.writeAttr(
          typedXmlSerializer, "restore-from-fp", this.mService.injectBuildFingerprint());
    }
    int size = this.mLaunchers.size();
    for (int i = 0; i < size; i++) {
      saveShortcutPackageItem(
          typedXmlSerializer, (ShortcutPackageItem) this.mLaunchers.valueAt(i), z);
      if (this.mService.needRescheduleLocked()) {
        return;
      }
    }
    int size2 = this.mPackages.size();
    for (int i2 = 0; i2 < size2; i2++) {
      saveShortcutPackageItem(
          typedXmlSerializer, (ShortcutPackageItem) this.mPackages.valueAt(i2), z);
      if (this.mService.needRescheduleLocked()) {
        return;
      }
    }
    typedXmlSerializer.endTag((String) null, "user");
  }

  public final void saveShortcutPackageItem(
      TypedXmlSerializer typedXmlSerializer, ShortcutPackageItem shortcutPackageItem, boolean z) {
    shortcutPackageItem.waitForBitmapSaves();
    if (z) {
      if (shortcutPackageItem.getPackageUserId() != shortcutPackageItem.getOwnerUserId()) {
        return;
      }
      shortcutPackageItem.saveToXml(typedXmlSerializer, z);
      return;
    }
    shortcutPackageItem.saveShortcutPackageItem();
  }

  /* JADX WARN: Removed duplicated region for block: B:31:0x007e  */
  /* JADX WARN: Removed duplicated region for block: B:41:0x008a A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static ShortcutUser loadFromXml(
      final ShortcutService shortcutService,
      TypedXmlPullParser typedXmlPullParser,
      final int i,
      final boolean z) {
    char c;
    final ShortcutUser shortcutUser = new ShortcutUser(shortcutService, i);
    try {
      shortcutUser.mKnownLocales =
          ShortcutService.parseStringAttribute(typedXmlPullParser, "locales");
      long parseLongAttribute =
          ShortcutService.parseLongAttribute(typedXmlPullParser, "last-app-scan-time2");
      if (parseLongAttribute >= shortcutService.injectCurrentTimeMillis()) {
        parseLongAttribute = 0;
      }
      shortcutUser.mLastAppScanTime = parseLongAttribute;
      shortcutUser.mLastAppScanOsFingerprint =
          ShortcutService.parseStringAttribute(typedXmlPullParser, "last-app-scan-fp");
      shortcutUser.mRestoreFromOsFingerprint =
          ShortcutService.parseStringAttribute(typedXmlPullParser, "restore-from-fp");
      int depth = typedXmlPullParser.getDepth();
      boolean z2 = false;
      while (true) {
        int next = typedXmlPullParser.next();
        if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
          break;
        }
        if (next == 2) {
          int depth2 = typedXmlPullParser.getDepth();
          String name = typedXmlPullParser.getName();
          if (depth2 == depth + 1) {
            int hashCode = name.hashCode();
            if (hashCode != -1146595445) {
              if (hashCode == -807062458 && name.equals("package")) {
                c = 0;
                if (c != 0) {
                  ShortcutPackage loadFromXml =
                      ShortcutPackage.loadFromXml(
                          shortcutService, shortcutUser, typedXmlPullParser, z);
                  shortcutUser.mPackages.put(loadFromXml.getPackageName(), loadFromXml);
                } else if (c == 1) {
                  shortcutUser.addLauncher(
                      ShortcutLauncher.loadFromXml(typedXmlPullParser, shortcutUser, i, z));
                }
                z2 = true;
              }
              c = 65535;
              if (c != 0) {}
              z2 = true;
            } else {
              if (name.equals("launcher-pins")) {
                c = 1;
                if (c != 0) {}
                z2 = true;
              }
              c = 65535;
              if (c != 0) {}
              z2 = true;
            }
          }
          ShortcutService.warnForInvalidTag(depth2, name);
        }
      }
      if (z2) {
        shortcutService.scheduleSaveUser(i);
      } else {
        File injectUserDataPath = shortcutService.injectUserDataPath(i);
        forMainFilesIn(
            new File(injectUserDataPath, "packages"),
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutUser.lambda$loadFromXml$3(
                    ShortcutService.this, shortcutUser, z, (File) obj);
              }
            });
        forMainFilesIn(
            new File(injectUserDataPath, "launchers"),
            new Consumer() { // from class:
              // com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda1
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ShortcutUser.lambda$loadFromXml$4(ShortcutUser.this, i, z, (File) obj);
              }
            });
      }
      return shortcutUser;
    } catch (RuntimeException e) {
      throw new ShortcutService.InvalidFileFormatException("Unable to parse file", e);
    }
  }

  public static /* synthetic */ void lambda$loadFromXml$3(
      ShortcutService shortcutService, ShortcutUser shortcutUser, boolean z, File file) {
    ShortcutPackage loadFromFile =
        ShortcutPackage.loadFromFile(shortcutService, shortcutUser, file, z);
    if (loadFromFile != null) {
      shortcutUser.mPackages.put(loadFromFile.getPackageName(), loadFromFile);
    }
  }

  public static /* synthetic */ void lambda$loadFromXml$4(
      ShortcutUser shortcutUser, int i, boolean z, File file) {
    ShortcutLauncher loadFromFile = ShortcutLauncher.loadFromFile(file, shortcutUser, i, z);
    if (loadFromFile != null) {
      shortcutUser.addLauncher(loadFromFile);
    }
  }

  public static void forMainFilesIn(File file, Consumer consumer) {
    if (file.exists()) {
      for (File file2 : file.listFiles()) {
        if ("reserves".equals(file2.getName()) && file2.isDirectory()) {
          Slog.i("ShortcutService", "Prune the reserves dir");
          FileUtils.deleteContentsAndDir(file2);
        } else if (!file2.getName().endsWith(".reservecopy")
            && !file2.getName().endsWith(".backup")) {
          consumer.accept(file2);
        }
      }
    }
  }

  public void setCachedLauncher(String str) {
    this.mCachedLauncher = str;
  }

  public String getCachedLauncher() {
    return this.mCachedLauncher;
  }

  public void resetThrottling() {
    for (int size = this.mPackages.size() - 1; size >= 0; size--) {
      ((ShortcutPackage) this.mPackages.valueAt(size)).resetThrottling();
    }
  }

  public void mergeRestoredFile(ShortcutUser shortcutUser) {
    final ShortcutService shortcutService = this.mService;
    final int[] iArr = new int[1];
    final int[] iArr2 = new int[1];
    final int[] iArr3 = new int[1];
    this.mLaunchers.clear();
    shortcutUser.forAllLaunchers(
        new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ShortcutUser.this.lambda$mergeRestoredFile$5(
                shortcutService, iArr, (ShortcutLauncher) obj);
          }
        });
    shortcutUser.forAllPackages(
        new Consumer() { // from class: com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda4
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ShortcutUser.this.lambda$mergeRestoredFile$6(
                shortcutService, iArr2, iArr3, (ShortcutPackage) obj);
          }
        });
    shortcutUser.mLaunchers.clear();
    shortcutUser.mPackages.clear();
    this.mRestoreFromOsFingerprint = shortcutUser.mRestoreFromOsFingerprint;
    Slog.i("ShortcutService", "Restored: L=" + iArr[0] + " P=" + iArr2[0] + " S=" + iArr3[0]);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$mergeRestoredFile$5(
      ShortcutService shortcutService, int[] iArr, ShortcutLauncher shortcutLauncher) {
    if (!shortcutService.isPackageInstalled(shortcutLauncher.getPackageName(), getUserId())
        || shortcutService.shouldBackupApp(shortcutLauncher.getPackageName(), getUserId())
        || this.mService.isSmartSwitchBackupAllowed()) {
      addLauncher(shortcutLauncher);
      iArr[0] = iArr[0] + 1;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$mergeRestoredFile$6(
      ShortcutService shortcutService, int[] iArr, int[] iArr2, ShortcutPackage shortcutPackage) {
    if (!shortcutService.isPackageInstalled(shortcutPackage.getPackageName(), getUserId())
        || shortcutService.shouldBackupApp(shortcutPackage.getPackageName(), getUserId())
        || this.mService.isSmartSwitchBackupAllowed()) {
      ShortcutPackage packageShortcutsIfExists =
          getPackageShortcutsIfExists(shortcutPackage.getPackageName());
      if (packageShortcutsIfExists != null && packageShortcutsIfExists.hasNonManifestShortcuts()) {
        Log.w(
            "ShortcutService",
            "Shortcuts for package "
                + shortcutPackage.getPackageName()
                + " are being restored. Existing non-manifeset shortcuts will be overwritten.");
      }
      if (packageShortcutsIfExists != null) {
        packageShortcutsIfExists.mergeNonManifestShortcuts(shortcutPackage);
      }
      shortcutPackage.removeAllShortcutsAsync();
      addPackage(shortcutPackage);
      iArr[0] = iArr[0] + 1;
      iArr2[0] = iArr2[0] + shortcutPackage.getShortcutCount();
    }
  }

  public void dump(PrintWriter printWriter, String str, ShortcutService.DumpFilter dumpFilter) {
    if (dumpFilter.shouldDumpDetails()) {
      printWriter.print(str);
      printWriter.print("User: ");
      printWriter.print(this.mUserId);
      printWriter.print("  Known locales: ");
      printWriter.print(this.mKnownLocales);
      printWriter.print("  Last app scan: [");
      printWriter.print(this.mLastAppScanTime);
      printWriter.print("] ");
      printWriter.println(ShortcutService.formatTime(this.mLastAppScanTime));
      str = str + str + "  ";
      printWriter.print(str);
      printWriter.print("Last app scan FP: ");
      printWriter.println(this.mLastAppScanOsFingerprint);
      printWriter.print(str);
      printWriter.print("Restore from FP: ");
      printWriter.print(this.mRestoreFromOsFingerprint);
      printWriter.println();
      printWriter.print(str);
      printWriter.print("Cached launcher: ");
      printWriter.print(this.mCachedLauncher);
      printWriter.println();
    }
    for (int i = 0; i < this.mLaunchers.size(); i++) {
      ShortcutLauncher shortcutLauncher = (ShortcutLauncher) this.mLaunchers.valueAt(i);
      if (dumpFilter.isPackageMatch(shortcutLauncher.getPackageName())) {
        shortcutLauncher.dump(printWriter, str, dumpFilter);
      }
    }
    for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
      ShortcutPackage shortcutPackage = (ShortcutPackage) this.mPackages.valueAt(i2);
      if (dumpFilter.isPackageMatch(shortcutPackage.getPackageName())) {
        shortcutPackage.dump(printWriter, str, dumpFilter);
      }
    }
    if (dumpFilter.shouldDumpDetails()) {
      printWriter.println();
      printWriter.print(str);
      printWriter.println("Bitmap directories: ");
      dumpDirectorySize(printWriter, str + "  ", this.mService.getUserBitmapFilePath(this.mUserId));
    }
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
          j2 += file2.length();
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

  public JSONObject dumpCheckin(boolean z) {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("userId", this.mUserId);
    JSONArray jSONArray = new JSONArray();
    for (int i = 0; i < this.mLaunchers.size(); i++) {
      jSONArray.put(((ShortcutLauncher) this.mLaunchers.valueAt(i)).dumpCheckin(z));
    }
    jSONObject.put("launchers", jSONArray);
    JSONArray jSONArray2 = new JSONArray();
    for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
      jSONArray2.put(((ShortcutPackage) this.mPackages.valueAt(i2)).dumpCheckin(z));
    }
    jSONObject.put("packages", jSONArray2);
    return jSONObject;
  }

  public void logSharingShortcutStats(MetricsLogger metricsLogger) {
    int i = 0;
    int i2 = 0;
    for (int i3 = 0; i3 < this.mPackages.size(); i3++) {
      if (((ShortcutPackage) this.mPackages.valueAt(i3)).hasShareTargets()) {
        i++;
        i2 += ((ShortcutPackage) this.mPackages.valueAt(i3)).getSharingShortcutCount();
      }
    }
    LogMaker logMaker = new LogMaker(1717);
    metricsLogger.write(logMaker.setType(1).setSubtype(this.mUserId));
    metricsLogger.write(logMaker.setType(2).setSubtype(i));
    metricsLogger.write(logMaker.setType(3).setSubtype(i2));
  }

  public AndroidFuture getAppSearch(AppSearchManager.SearchContext searchContext) {
    final AndroidFuture androidFuture = new AndroidFuture();
    synchronized (this.mLock) {
      this.mInFlightSessions.removeIf(
          new Predicate() { // from class:
            // com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              return ((AndroidFuture) obj).isDone();
            }
          });
      this.mInFlightSessions.add(androidFuture);
    }
    if (this.mAppSearchManager == null) {
      androidFuture.completeExceptionally(new RuntimeException("app search manager is null"));
      return androidFuture;
    }
    if (!this.mService.mUserManagerInternal.isUserUnlockingOrUnlocked(getUserId())) {
      androidFuture.completeExceptionally(new RuntimeException("User " + getUserId() + " is "));
      return androidFuture;
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mAppSearchManager.createSearchSession(
          searchContext,
          this.mExecutor,
          new Consumer() { // from class:
            // com.android.server.pm.ShortcutUser$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ShortcutUser.lambda$getAppSearch$7(androidFuture, (AppSearchResult) obj);
            }
          });
      return androidFuture;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$getAppSearch$7(
      AndroidFuture androidFuture, AppSearchResult appSearchResult) {
    if (!appSearchResult.isSuccess()) {
      androidFuture.completeExceptionally(new RuntimeException(appSearchResult.getErrorMessage()));
    } else {
      androidFuture.complete((AppSearchSession) appSearchResult.getResultValue());
    }
  }

  public void cancelAllInFlightTasks() {
    synchronized (this.mLock) {
      Iterator it = this.mInFlightSessions.iterator();
      while (it.hasNext()) {
        ((AndroidFuture) it.next()).cancel(true);
      }
      this.mInFlightSessions.clear();
    }
  }
}
