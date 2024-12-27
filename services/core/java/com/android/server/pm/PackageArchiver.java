package com.android.server.pm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.ArchivedActivityInfo;
import android.content.pm.ArchivedActivityParcel;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.SELinux;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.ExceptionUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.secutil.Slog;
import android.view.inputmethod.InputMethodInfo;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserState;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.PackageUserStateInternal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class PackageArchiver {
    public AppOpsManager mAppOpsManager;
    public final AppStateHelper mAppStateHelper;
    public final HashMap mArchiveVersionMap;
    public final Context mContext;
    public LauncherApps mLauncherApps;
    public final Map mLauncherIntentSenders;
    public final PackageManagerService mPm;
    public UserManager mUserManager;
    public static final PorterDuffColorFilter OPACITY_LAYER_FILTER =
            new PorterDuffColorFilter(
                    Color.argb(
                            0.4f,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE,
                            FullScreenMagnificationGestureHandler.MAX_SCALE),
                    PorterDuff.Mode.SRC_ATOP);
    public static final String FILE_PATH =
            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                    Environment.getDataDirectory().getPath() + "/system/", "package-version.dat");

    public final class FixedSizeBitmapDrawable extends BitmapDrawable {
        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public final int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public final int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }

    public final class UnarchiveIntentSender extends IIntentSender.Stub {
        public UnarchiveIntentSender() {}

        public final void send(
                int i,
                Intent intent,
                String str,
                IBinder iBinder,
                IIntentReceiver iIntentReceiver,
                String str2,
                Bundle bundle) {
            if (intent.getExtras().getInt("android.content.pm.extra.UNARCHIVE_STATUS", -1) == 0) {
                return;
            }
            Intent intent2 =
                    (Intent) intent.getParcelableExtra("android.intent.extra.INTENT", Intent.class);
            UserHandle userHandle =
                    (UserHandle)
                            intent.getParcelableExtra(
                                    "android.intent.extra.USER", UserHandle.class);
            if (intent2 == null || userHandle == null) {
                return;
            }
            PackageArchiver packageArchiver = PackageArchiver.this;
            AppStateHelper appStateHelper = packageArchiver.mAppStateHelper;
            ComponentName defaultHomeActivity =
                    packageArchiver
                            .mPm
                            .snapshotComputer()
                            .getDefaultHomeActivity(userHandle.getIdentifier());
            if (((ActivityManager) appStateHelper.mContext.getSystemService(ActivityManager.class))
                            .getPackageImportance(
                                    defaultHomeActivity != null
                                            ? defaultHomeActivity.getPackageName()
                                            : null)
                    <= 100) {
                intent2.setFlags(268435456);
                PackageArchiver.this.mContext.startActivityAsUser(intent2, userHandle);
            }
        }
    }

    public PackageArchiver(Context context, PackageManagerService packageManagerService) {
        FileInputStream fileInputStream;
        HashMap hashMap = new HashMap();
        this.mArchiveVersionMap = hashMap;
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mAppStateHelper = new AppStateHelper(context);
        this.mLauncherIntentSenders = new HashMap();
        synchronized (hashMap) {
            hashMap.clear();
            try {
                fileInputStream = new FileInputStream(new File(FILE_PATH));
            } catch (Exception unused) {
                Slog.d("PackageArchiverService", "versionMap read error!");
            }
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    hashMap.putAll((HashMap) objectInputStream.readObject());
                    objectInputStream.close();
                    fileInputStream.close();
                    if (Build.isDebuggable()) {
                        Slog.d("PackageArchiverService", "read readAppVersion");
                        this.mArchiveVersionMap.forEach(
                                new PackageArchiver$$ExternalSyntheticLambda7(0));
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public static ArchivedActivityParcel[] createArchivedActivities(int i, List list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("No launcher activities");
        }
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            LauncherActivityInfo launcherActivityInfo = (LauncherActivityInfo) list.get(i2);
            if (launcherActivityInfo != null) {
                ArchivedActivityParcel archivedActivityParcel = new ArchivedActivityParcel();
                archivedActivityParcel.title = launcherActivityInfo.getLabel().toString();
                archivedActivityParcel.originalComponentName =
                        launcherActivityInfo.getComponentName();
                archivedActivityParcel.iconBitmap =
                        launcherActivityInfo.getActivityInfo().getIconResource() == 0
                                ? null
                                : ArchivedActivityInfo.bytesFromBitmap(
                                        ArchivedActivityInfo.drawableToBitmap(
                                                launcherActivityInfo.getIcon(0), i));
                archivedActivityParcel.monochromeIconBitmap = null;
                arrayList.add(archivedActivityParcel);
            }
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException(
                    "Failed to extract title and icon of main activities");
        }
        return (ArchivedActivityParcel[])
                arrayList.toArray(new ArchivedActivityParcel[arrayList.size()]);
    }

    public static ArchivedActivityParcel[] createArchivedActivities(ArchiveState archiveState) {
        List list = archiveState.mActivityInfos;
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("No activities in archive state");
        }
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ArchiveState.ArchiveActivityInfo archiveActivityInfo =
                    (ArchiveState.ArchiveActivityInfo) list.get(i);
            if (archiveActivityInfo != null) {
                ArchivedActivityParcel archivedActivityParcel = new ArchivedActivityParcel();
                archivedActivityParcel.title = archiveActivityInfo.mTitle;
                archivedActivityParcel.originalComponentName =
                        archiveActivityInfo.mOriginalComponentName;
                Path path = archiveActivityInfo.mIconBitmap;
                archivedActivityParcel.iconBitmap =
                        path == null
                                ? null
                                : ArchivedActivityInfo.bytesFromBitmap(
                                        BitmapFactory.decodeFile(path.toString()));
                Path path2 = archiveActivityInfo.mMonochromeIconBitmap;
                archivedActivityParcel.monochromeIconBitmap =
                        path2 != null
                                ? ArchivedActivityInfo.bytesFromBitmap(
                                        BitmapFactory.decodeFile(path2.toString()))
                                : null;
                arrayList.add(archivedActivityParcel);
            }
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException(
                    "Failed to extract title and icon of main activities");
        }
        return (ArchivedActivityParcel[])
                arrayList.toArray(new ArchivedActivityParcel[arrayList.size()]);
    }

    public static PackageStateInternal getPackageState(
            int i, int i2, Computer computer, String str) {
        PackageSetting packageStateFiltered = computer.getPackageStateFiltered(i, i2, str);
        if (packageStateFiltered != null) {
            return packageStateFiltered;
        }
        throw new PackageManager.NameNotFoundException(
                TextUtils.formatSimple("Package %s not found.", new Object[] {str}));
    }

    public static String getResponsibleInstallerPackage(InstallSource installSource) {
        return TextUtils.isEmpty(installSource.mUpdateOwnerPackageName)
                ? installSource.mInstallerPackageName
                : installSource.mUpdateOwnerPackageName;
    }

    public static String getResponsibleInstallerTitle(
            Context context, ApplicationInfo applicationInfo, String str, int i) {
        return applicationInfo
                .loadLabel(
                        context.createPackageContextAsUser(str, 0, new UserHandle(i))
                                .getPackageManager())
                .toString();
    }

    public static SparseArray getResponsibleInstallerTitles(
            Context context, Computer computer, InstallSource installSource, int i, int[] iArr) {
        String responsibleInstallerPackage = getResponsibleInstallerPackage(installSource);
        SparseArray sparseArray = new SparseArray();
        try {
            if (i != -1) {
                ApplicationInfo applicationInfo =
                        computer.getApplicationInfo(responsibleInstallerPackage, 0L, i);
                if (applicationInfo == null) {
                    return null;
                }
                sparseArray.put(
                        i,
                        getResponsibleInstallerTitle(
                                context, applicationInfo, responsibleInstallerPackage, i));
            } else {
                for (int i2 : iArr) {
                    ApplicationInfo applicationInfo2 =
                            computer.getApplicationInfo(responsibleInstallerPackage, 0L, i2);
                    if (applicationInfo2 != null) {
                        sparseArray.put(
                                i2,
                                getResponsibleInstallerTitle(
                                        context,
                                        applicationInfo2,
                                        responsibleInstallerPackage,
                                        i2));
                    }
                }
            }
            return sparseArray;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public static boolean isArchived(PackageUserState packageUserState) {
        return (packageUserState.getArchiveState() == null || packageUserState.isInstalled())
                ? false
                : true;
    }

    public static Path storeAdaptiveDrawable(String str, Drawable drawable, int i, int i2, int i3) {
        if (drawable == null) {
            return null;
        }
        Drawable fixedSizeBitmapDrawable =
                drawable instanceof BitmapDrawable
                        ? new FixedSizeBitmapDrawable(null, ((BitmapDrawable) drawable).getBitmap())
                        : drawable;
        float extraInsetFraction = AdaptiveIconDrawable.getExtraInsetFraction();
        float f = extraInsetFraction / ((2.0f * extraInsetFraction) + 1.0f);
        return storeDrawable(
                str,
                new AdaptiveIconDrawable(
                        new ColorDrawable(-16777216),
                        new InsetDrawable(fixedSizeBitmapDrawable, f, f, f, f)),
                i,
                i2,
                i3);
    }

    public static Path storeDrawable(String str, Drawable drawable, int i, int i2, int i3) {
        if (drawable == null) {
            return null;
        }
        File file =
                new File(
                        new File(Environment.getDataSystemCeDirectory(i), "package_archiver"), str);
        if (!file.isDirectory()) {
            file.delete();
            file.mkdirs();
            if (!file.isDirectory()) {
                throw new IOException(
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                file, "Unable to create directory "));
            }
            android.util.Slog.i(
                    "PackageArchiverService",
                    "Created icons directory at " + file.getAbsolutePath());
        }
        SELinux.restorecon(file);
        File file2 = new File(file, NandswapManager$$ExternalSyntheticOutline0.m(i2, ".png"));
        Bitmap drawableToBitmap = ArchivedActivityInfo.drawableToBitmap(drawable, i3);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            if (!drawableToBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                throw new IOException(
                        TextUtils.formatSimple(
                                "Failure to store icon file %s",
                                new Object[] {file2.getAbsolutePath()}));
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            if (file2.exists()) {
                android.util.Slog.i(
                        "PackageArchiverService", "Stored icon at " + file2.getAbsolutePath());
            }
            return file2.toPath();
        } catch (Throwable th) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void attachListenerToSession(IntentSender intentSender, int i, int i2) {
        PackageInstallerSession session = this.mPm.mInstallerService.getSession(i);
        int i3 = session.mUnarchivalStatus;
        if (i3 == 0) {
            notifyUnarchivalListener(
                    0,
                    session.getInstallSource().mInstallerPackageName,
                    session.params.appPackageName,
                    0L,
                    null,
                    Set.of(intentSender),
                    i2);
        } else {
            if (i3 != -1) {
                throw new IllegalStateException(
                        TextUtils.formatSimple(
                                "Session %s has unarchive status%s but is still active.",
                                new Object[] {
                                    Integer.valueOf(session.sessionId), Integer.valueOf(i3)
                                }));
            }
            synchronized (session.mLock) {
                ((ArraySet) session.mUnarchivalListeners).add(intentSender);
            }
        }
    }

    public final void clearArchiveState(PackageSetting packageSetting, int i) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            if (packageSetting != null) {
                try {
                    if (packageSetting.getUserStateOrDefault(i).getArchiveState() != null) {
                        android.util.Slog.e(
                                "PackageArchiverService",
                                "Clearing archive states for " + packageSetting.mName);
                        PackageUserStateImpl modifyUserState = packageSetting.modifyUserState(i);
                        modifyUserState.mArchiveState = null;
                        modifyUserState.onChanged$4();
                        packageSetting.onChanged$2();
                        File file =
                                new File(
                                        new File(
                                                Environment.getDataSystemCeDirectory(i),
                                                "package_archiver"),
                                        packageSetting.mName);
                        if (!file.exists()) {
                            android.util.Slog.e(
                                    "PackageArchiverService",
                                    "Icons are already deleted at " + file.getAbsolutePath());
                        } else {
                            if (!FileUtils.deleteContentsAndDir(file)) {
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                        new StringBuilder("Failed to clean up archive files for "),
                                        packageSetting.mName,
                                        "PackageArchiverService");
                                return;
                            }
                            android.util.Slog.e(
                                    "PackageArchiverService",
                                    "Deleted icons at " + file.getAbsolutePath());
                        }
                    }
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CompletableFuture createAndStoreArchiveState(final int i, final String str) {
        PackageManagerService packageManagerService = this.mPm;
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        PackageStateInternal packageState =
                getPackageState(Binder.getCallingUid(), i, snapshotComputer, str);
        int i2 = ((SettingBase) packageState).mPkgFlags;
        if ((i2 & 1) != 0 || (i2 & 128) != 0) {
            throw new PackageManager.NameNotFoundException("System apps cannot be archived.");
        }
        if (!packageState.getUserStateOrDefault(i).isInstalled()) {
            throw new PackageManager.NameNotFoundException(
                    TextUtils.formatSimple(
                            "%s is not installed.",
                            new Object[] {((PackageSetting) packageState).mName}));
        }
        PackageSetting packageSetting = (PackageSetting) packageState;
        final String responsibleInstallerPackage =
                getResponsibleInstallerPackage(packageSetting.installSource);
        final ApplicationInfo verifyInstaller =
                verifyInstaller(snapshotComputer, responsibleInstallerPackage, i);
        if (((Boolean)
                        Binder.withCleanCallingIdentity(
                                new PackageArchiver$$ExternalSyntheticLambda0(
                                        this,
                                        UserHandle.getUid(
                                                i, UserHandle.getUid(i, packageSetting.mAppId)),
                                        str)))
                .booleanValue()) {
            throw new PackageManager.NameNotFoundException(
                    TextUtils.formatSimple(
                            "The app %s is opted out of archiving.", new Object[] {str}));
        }
        if (isProtectivePackage(packageState, str, i)) {
            throw new PackageManager.NameNotFoundException(
                    TextUtils.formatSimple(
                            "Protected package %s cannot be archived.", new Object[] {str}));
        }
        final List launcherActivityInfos = getLauncherActivityInfos(i, packageSetting.mName);
        final CompletableFuture completableFuture = new CompletableFuture();
        packageManagerService.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        PackageArchiver packageArchiver = PackageArchiver.this;
                        ApplicationInfo applicationInfo = verifyInstaller;
                        String str2 = responsibleInstallerPackage;
                        int i3 = i;
                        String str3 = str;
                        List list = launcherActivityInfos;
                        CompletableFuture completableFuture2 = completableFuture;
                        packageArchiver.getClass();
                        try {
                            packageArchiver.storeArchiveState(
                                    str3,
                                    packageArchiver.createArchiveStateInternal(
                                            str3,
                                            PackageArchiver.getResponsibleInstallerTitle(
                                                    packageArchiver.mContext,
                                                    applicationInfo,
                                                    str2,
                                                    i3),
                                            i3,
                                            list),
                                    i3);
                            completableFuture2.complete(null);
                        } catch (PackageManager.NameNotFoundException | IOException e) {
                            completableFuture2.completeExceptionally(e);
                        }
                    }
                });
        return completableFuture;
    }

    public final ArchiveState createArchiveStateInternal(
            String str, String str2, int i, List list) {
        int launcherLargeIconSize =
                ((ActivityManager) this.mContext.getSystemService(ActivityManager.class))
                        .getLauncherLargeIconSize();
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            LauncherActivityInfo launcherActivityInfo = (LauncherActivityInfo) list.get(i2);
            arrayList.add(
                    new ArchiveState.ArchiveActivityInfo(
                            launcherActivityInfo.getLabel().toString(),
                            launcherActivityInfo.getComponentName(),
                            storeIcon(str, launcherActivityInfo, i, i2 * 2, launcherLargeIconSize),
                            null));
        }
        return new ArchiveState(str2, arrayList);
    }

    public Bitmap decodeIcon(ArchiveState.ArchiveActivityInfo archiveActivityInfo) {
        Path path = archiveActivityInfo.mIconBitmap;
        if (path == null) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(path.toString());
        if (decodeFile != null) {
            return decodeFile;
        }
        android.util.Slog.e(
                "PackageArchiverService",
                "Archived icon cannot be decoded " + path.toAbsolutePath());
        return null;
    }

    public final AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager =
                    (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        }
        return this.mAppOpsManager;
    }

    public final Bitmap getArchivedAppIcon(String str, UserHandle userHandle, String str2) {
        ArchiveState archiveState;
        Objects.requireNonNull(str);
        Objects.requireNonNull(userHandle);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int callingUid = Binder.getCallingUid();
        int identifier = userHandle.getIdentifier();
        try {
            PackageStateInternal packageState =
                    getPackageState(callingUid, identifier, snapshotComputer, str);
            PackageUserStateInternal userStateOrDefault =
                    packageState.getUserStateOrDefault(identifier);
            if (!isArchived(userStateOrDefault)) {
                int i = 0;
                while (true) {
                    if (i >= packageState.getUserStates().size()) {
                        archiveState = null;
                        break;
                    }
                    PackageUserStateInternal packageUserStateInternal =
                            (PackageUserStateInternal) packageState.getUserStates().valueAt(i);
                    if (isArchived(packageUserStateInternal)) {
                        archiveState = packageUserStateInternal.getArchiveState();
                        break;
                    }
                    i++;
                }
            } else {
                archiveState = userStateOrDefault.getArchiveState();
            }
            if (archiveState == null || archiveState.mActivityInfos.size() == 0) {
                return null;
            }
            Bitmap decodeIcon =
                    decodeIcon(
                            (ArchiveState.ArchiveActivityInfo) archiveState.mActivityInfos.get(0));
            if (decodeIcon == null || getAppOpsManager().checkOp(145, callingUid, str2) != 0) {
                return decodeIcon;
            }
            Drawable drawable =
                    this.mContext
                            .getResources()
                            .getDrawable(
                                    R.drawable.background_leanback_setup, this.mContext.getTheme());
            if (drawable == null) {
                android.util.Slog.e(
                        "PackageArchiverService",
                        "Unable to locate cloud overlay for archived app!");
                return decodeIcon;
            }
            BitmapDrawable bitmapDrawable =
                    new BitmapDrawable(this.mContext.getResources(), decodeIcon);
            bitmapDrawable.setBounds(
                    0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Drawable semGetDrawableForIconTray =
                    this.mContext
                            .getPackageManager()
                            .semGetDrawableForIconTray(
                                    bitmapDrawable, FrameworkStatsLog.EXPRESS_EVENT_REPORTED);
            semGetDrawableForIconTray.setColorFilter(OPACITY_LAYER_FILTER);
            Bitmap drawableToBitmap =
                    ArchivedActivityInfo.drawableToBitmap(
                            new LayerDrawable(new Drawable[] {semGetDrawableForIconTray, drawable}),
                            ((ActivityManager)
                                            this.mContext.getSystemService(ActivityManager.class))
                                    .getLauncherLargeIconSize());
            decodeIcon.recycle();
            return drawableToBitmap;
        } catch (PackageManager.NameNotFoundException e) {
            android.util.Slog.e(
                    "PackageArchiverService",
                    TextUtils.formatSimple("Package %s couldn't be found.", new Object[] {str}),
                    e);
            return null;
        }
    }

    public final List getLauncherActivityInfos(int i, String str) {
        List list =
                (List)
                        Binder.withCleanCallingIdentity(
                                new PackageArchiver$$ExternalSyntheticLambda0(this, str, i, 1));
        if (list.isEmpty()) {
            throw new PackageManager.NameNotFoundException(
                    TextUtils.formatSimple(
                            "The app %s does not have a main activity.", new Object[] {str}));
        }
        return list;
    }

    public final IntentSender getOrCreateLauncherListener(int i, String str) {
        Pair create = Pair.create(Integer.valueOf(i), str);
        synchronized (this.mLauncherIntentSenders) {
            try {
                IntentSender intentSender =
                        (IntentSender) ((HashMap) this.mLauncherIntentSenders).get(create);
                if (intentSender != null) {
                    return intentSender;
                }
                IntentSender intentSender2 = new IntentSender(new UnarchiveIntentSender());
                ((HashMap) this.mLauncherIntentSenders).put(create, intentSender2);
                return intentSender2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isProtectivePackage(
            PackageStateInternal packageStateInternal, final String str, int i) {
        ParcelFileDescriptor parcelFileDescriptor;
        InputMethodManagerInternal inputMethodManagerInternal = InputMethodManagerInternal.get();
        for (int i2 : UserManagerService.this.getUserIds()) {
            if (inputMethodManagerInternal.getEnabledInputMethodListAsUser(i2).stream()
                    .anyMatch(
                            new Predicate() { // from class:
                                              // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda2
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return str.equals(((InputMethodInfo) obj).getPackageName());
                                }
                            })) {
                return true;
            }
        }
        synchronized (this.mArchiveVersionMap) {
            if (this.mArchiveVersionMap.containsKey(str)
                    && ((Integer) this.mArchiveVersionMap.get(str)).intValue()
                            == Build.VERSION.SDK_INT) {
                int i3 = 0;
                for (int i4 = 0; i4 < packageStateInternal.getUserStates().size(); i4++) {
                    i3 +=
                            ((PackageUserStateInternal)
                                                    packageStateInternal
                                                            .getUserStates()
                                                            .valueAt(i4))
                                            .isInstalled()
                                    ? 1
                                    : 0;
                    if (i3 > 1) {
                        return true;
                    }
                }
                PersistableBundle persistableBundle = null;
                try {
                    parcelFileDescriptor =
                            ActivityThread.getPackageManager().getAppMetadataFd(str, i);
                } catch (Exception e) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Failed to get ", str, " metadata fd: ");
                    m.append(e.toString());
                    android.util.Slog.i("PackageArchiverService", m.toString());
                    parcelFileDescriptor = null;
                }
                if (parcelFileDescriptor != null) {
                    try {
                        ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream =
                                new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                        try {
                            persistableBundle =
                                    PersistableBundle.readFromStream(autoCloseInputStream);
                            autoCloseInputStream.close();
                        } finally {
                        }
                    } catch (IOException e2) {
                        StringBuilder m2 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Failed to read ", str, " metadata: ");
                        m2.append(e2.toString());
                        android.util.Slog.i("PackageArchiverService", m2.toString());
                    }
                }
                if (persistableBundle == null) {
                    persistableBundle = new PersistableBundle();
                }
                return persistableBundle.getBoolean(
                                "com.samsung.android.archiving.disallow_archive", false)
                        || AsecInstallHelper.isExternalAsec(packageStateInternal.getPkg());
            }
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0092 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyUnarchivalListener(
            int r17,
            java.lang.String r18,
            java.lang.String r19,
            long r20,
            android.app.PendingIntent r22,
            java.util.Set r23,
            int r24) {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageArchiver.notifyUnarchivalListener(int,"
                    + " java.lang.String, java.lang.String, long, android.app.PendingIntent,"
                    + " java.util.Set, int):void");
    }

    public final void requestArchive(
            final String str,
            final String str2,
            int i,
            final IntentSender intentSender,
            UserHandle userHandle) {
        int packageUid;
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(intentSender);
        Objects.requireNonNull(userHandle);
        android.util.Slog.i(
                "PackageArchiverService",
                TextUtils.formatSimple(
                        "Requested archival of package %s for user %s.",
                        new Object[] {str, Integer.valueOf(userHandle.getIdentifier())}));
        PackageManagerService packageManagerService = this.mPm;
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        final int identifier = userHandle.getIdentifier();
        final int callingUid = Binder.getCallingUid();
        final int callingPid = Binder.getCallingPid();
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)
                && (packageUid = snapshotComputer.getPackageUid(str2, 0L, identifier))
                        != callingUid) {
            throw new SecurityException(
                    TextUtils.formatSimple(
                            "The UID %s of callerPackageName set by the caller doesn't match the"
                                + " caller's actual UID %s.",
                            new Object[] {
                                Integer.valueOf(packageUid), Integer.valueOf(callingUid)
                            }));
        }
        boolean z = (i & 2) != 0;
        int[] userIds = z ? UserManagerService.this.getUserIds() : new int[] {identifier};
        int i2 = 0;
        for (int length = userIds.length; i2 < length; length = length) {
            snapshotComputer.enforceCrossUserPermission(
                    "archiveApp", callingUid, userIds[i2], true, true);
            i2++;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") != 0
                && this.mContext.checkCallingOrSelfPermission(
                                "android.permission.REQUEST_DELETE_PACKAGES")
                        != 0) {
            throw new SecurityException(
                    "You need the com.android.permission.DELETE_PACKAGES or"
                        + " com.android.permission.REQUEST_DELETE_PACKAGES permission to request an"
                        + " archival.");
        }
        CompletableFuture[] completableFutureArr = new CompletableFuture[userIds.length];
        try {
            int length2 = userIds.length;
            for (int i3 = 0; i3 < length2; i3++) {
                completableFutureArr[i3] = createAndStoreArchiveState(userIds[i3], str);
            }
            final int i4 = (z ? 2 : 0) | 17;
            CompletableFuture.allOf(completableFutureArr)
                    .thenAccept(
                            new Consumer() { // from class:
                                             // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda8
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    PackageArchiver packageArchiver = PackageArchiver.this;
                                    String str3 = str;
                                    packageArchiver.mPm.mInstallerService.uninstall(
                                            new VersionedPackage(str3, -1),
                                            str2,
                                            i4,
                                            intentSender,
                                            identifier,
                                            callingUid,
                                            callingPid);
                                }
                            })
                    .exceptionally(
                            new Function() { // from class:
                                             // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda9
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    PackageArchiver packageArchiver = PackageArchiver.this;
                                    String str3 = str;
                                    IntentSender intentSender2 = intentSender;
                                    Throwable th = (Throwable) obj;
                                    packageArchiver.getClass();
                                    android.util.Slog.e(
                                            "PackageArchiverService",
                                            TextUtils.formatSimple(
                                                    "Failed to archive %s with message %s",
                                                    new Object[] {str3, th.getMessage()}));
                                    String message = th.getMessage();
                                    android.util.Slog.d(
                                            "PackageArchiverService",
                                            TextUtils.formatSimple(
                                                    "Failed to archive %s with message %s",
                                                    new Object[] {str3, message}));
                                    Intent intent = new Intent();
                                    intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str3);
                                    intent.putExtra("android.content.pm.extra.STATUS", 1);
                                    intent.putExtra(
                                            "android.content.pm.extra.STATUS_MESSAGE", message);
                                    packageArchiver.sendIntent(
                                            intentSender2, str3, message, intent);
                                    return null;
                                }
                            });
        } catch (PackageManager.NameNotFoundException e) {
            android.util.Slog.e(
                    "PackageArchiverService",
                    TextUtils.formatSimple(
                            "Failed to archive %s with message %s",
                            new Object[] {str, e.getMessage()}));
            throw new ParcelableException(e);
        }
    }

    public void requestArchive(
            String str, String str2, IntentSender intentSender, UserHandle userHandle) {
        requestArchive(str, str2, 0, intentSender, userHandle);
    }

    public final void requestUnarchive(
            final String str,
            final String str2,
            final IntentSender intentSender,
            final UserHandle userHandle,
            boolean z) {
        int packageUid;
        Objects.requireNonNull(str);
        Objects.requireNonNull(str2);
        Objects.requireNonNull(intentSender);
        Objects.requireNonNull(userHandle);
        PackageManagerService packageManagerService = this.mPm;
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        final int identifier = userHandle.getIdentifier();
        int callingUid = Binder.getCallingUid();
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell(callingUid)
                && (packageUid = snapshotComputer.getPackageUid(str2, 0L, identifier))
                        != callingUid) {
            throw new SecurityException(
                    TextUtils.formatSimple(
                            "The UID %s of callerPackageName set by the caller doesn't match the"
                                + " caller's actual UID %s.",
                            new Object[] {
                                Integer.valueOf(packageUid), Integer.valueOf(callingUid)
                            }));
        }
        snapshotComputer.enforceCrossUserPermission(
                "unarchiveApp", callingUid, identifier, true, true);
        try {
            PackageStateInternal packageState =
                    getPackageState(callingUid, identifier, snapshotComputer, str);
            PackageStateInternal packageState2 =
                    getPackageState(callingUid, identifier, snapshotComputer, str2);
            if (!isArchived(packageState.getUserStateOrDefault(identifier))) {
                throw new PackageManager.NameNotFoundException(
                        TextUtils.formatSimple(
                                "Package %s is not currently archived.",
                                new Object[] {((PackageSetting) packageState).mName}));
            }
            final String responsibleInstallerPackage =
                    getResponsibleInstallerPackage(((PackageSetting) packageState).installSource);
            if (responsibleInstallerPackage == null) {
                throw new ParcelableException(
                        new PackageManager.NameNotFoundException(
                                TextUtils.formatSimple(
                                        "No installer found to unarchive app %s.",
                                        new Object[] {str})));
            }
            boolean z2 =
                    this.mContext.checkCallingOrSelfPermission(
                                    "android.permission.INSTALL_PACKAGES")
                            == 0;
            boolean contains =
                    ((PackageSetting) packageState2)
                            .pkg
                            .getRequestedPermissions()
                            .contains("android.permission.REQUEST_INSTALL_PACKAGES");
            if (!z2 && !contains) {
                throw new SecurityException(
                        "You need the com.android.permission.INSTALL_PACKAGES or"
                            + " com.android.permission.REQUEST_INSTALL_PACKAGES permission to"
                            + " request an unarchival.");
            }
            Handler handler = packageManagerService.mHandler;
            if (!z2 || z) {
                handler.post(
                        new Runnable() { // from class:
                                         // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                PackageArchiver packageArchiver = PackageArchiver.this;
                                String str3 = str;
                                IntentSender intentSender2 = intentSender;
                                UserHandle userHandle2 = userHandle;
                                packageArchiver.getClass();
                                Intent intent =
                                        new Intent("com.android.intent.action.UNARCHIVE_DIALOG");
                                intent.putExtra(
                                        "android.content.pm.extra.UNARCHIVE_INTENT_SENDER",
                                        intentSender2);
                                intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str3);
                                Intent intent2 = new Intent();
                                intent2.putExtra("android.content.pm.extra.PACKAGE_NAME", str3);
                                intent2.putExtra("android.content.pm.extra.UNARCHIVE_STATUS", -1);
                                intent2.putExtra("android.intent.extra.INTENT", intent);
                                intent2.putExtra("android.intent.extra.USER", userHandle2);
                                packageArchiver.sendIntent(intentSender2, str3, "", intent2);
                            }
                        });
                return;
            }
            try {
                final int intValue =
                        ((Integer)
                                        Binder.withCleanCallingIdentity(
                                                new FunctionalUtils
                                                        .ThrowingSupplier() { // from class:
                                                                              // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda5
                                                    public final Object getOrThrow() {
                                                        final int existingDraftSessionIdInternal;
                                                        final PackageArchiver packageArchiver =
                                                                PackageArchiver.this;
                                                        String str3 = str;
                                                        String str4 = responsibleInstallerPackage;
                                                        String str5 = str2;
                                                        IntentSender intentSender2 = intentSender;
                                                        int i = identifier;
                                                        packageArchiver.getClass();
                                                        PackageInstaller.SessionParams
                                                                sessionParams =
                                                                        new PackageInstaller
                                                                                .SessionParams(1);
                                                        sessionParams.setAppPackageName(str3);
                                                        sessionParams.setAppLabel(
                                                                packageArchiver.mContext.getString(
                                                                        17043275));
                                                        sessionParams.setAppIcon(
                                                                packageArchiver.getArchivedAppIcon(
                                                                        str3,
                                                                        UserHandle.of(i),
                                                                        str5));
                                                        sessionParams.installFlags = 1610612736;
                                                        PackageManagerService
                                                                packageManagerService2 =
                                                                        packageArchiver.mPm;
                                                        int packageUid2 =
                                                                packageManagerService2
                                                                        .snapshotComputer()
                                                                        .getPackageUid(str4, 0L, i);
                                                        PackageInstallerService
                                                                packageInstallerService =
                                                                        packageManagerService2
                                                                                .mInstallerService;
                                                        synchronized (
                                                                packageInstallerService.mSessions) {
                                                            existingDraftSessionIdInternal =
                                                                    packageInstallerService
                                                                            .getExistingDraftSessionIdInternal(
                                                                                    packageUid2,
                                                                                    sessionParams,
                                                                                    i);
                                                        }
                                                        if (existingDraftSessionIdInternal != -1) {
                                                            packageArchiver.attachListenerToSession(
                                                                    intentSender2,
                                                                    existingDraftSessionIdInternal,
                                                                    i);
                                                        } else {
                                                            existingDraftSessionIdInternal =
                                                                    packageManagerService2
                                                                            .mInstallerService
                                                                            .createSessionInternal(
                                                                                    sessionParams,
                                                                                    str4,
                                                                                    packageArchiver
                                                                                            .mContext
                                                                                            .getAttributionTag(),
                                                                                    packageUid2,
                                                                                    i);
                                                            packageArchiver.attachListenerToSession(
                                                                    intentSender2,
                                                                    existingDraftSessionIdInternal,
                                                                    i);
                                                            packageManagerService2.mHandler
                                                                    .postDelayed(
                                                                            new Runnable() { // from
                                                                                             // class: com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda14
                                                                                @Override // java.lang.Runnable
                                                                                public final void
                                                                                        run() {
                                                                                    PackageArchiver
                                                                                            packageArchiver2 =
                                                                                                    PackageArchiver
                                                                                                            .this;
                                                                                    int i2 =
                                                                                            existingDraftSessionIdInternal;
                                                                                    PackageInstallerService
                                                                                            packageInstallerService2 =
                                                                                                    packageArchiver2
                                                                                                            .mPm
                                                                                                            .mInstallerService;
                                                                                    synchronized (
                                                                                            packageInstallerService2
                                                                                                    .mSessions) {
                                                                                        try {
                                                                                            PackageInstallerSession
                                                                                                    session =
                                                                                                            packageInstallerService2
                                                                                                                    .mPm
                                                                                                                    .mInstallerService
                                                                                                                    .getSession(
                                                                                                                            i2);
                                                                                            if (session
                                                                                                            != null
                                                                                                    && (session.params
                                                                                                                            .installFlags
                                                                                                                    & 536870912)
                                                                                                            != 0) {
                                                                                                session
                                                                                                        .abandon();
                                                                                            }
                                                                                        } catch (
                                                                                                Throwable
                                                                                                        th) {
                                                                                            throw th;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            },
                                                                            120000);
                                                        }
                                                        return Integer.valueOf(
                                                                existingDraftSessionIdInternal);
                                                    }
                                                }))
                                .intValue();
                handler.post(
                        new Runnable() { // from class:
                                         // com.android.server.pm.PackageArchiver$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                PackageArchiver packageArchiver = PackageArchiver.this;
                                String str3 = str;
                                UserHandle userHandle2 = userHandle;
                                String str4 = responsibleInstallerPackage;
                                int i = intValue;
                                packageArchiver.getClass();
                                android.util.Slog.i(
                                        "PackageArchiverService",
                                        "Starting app unarchival for: " + str3);
                                int identifier2 = userHandle2.getIdentifier();
                                Intent intent =
                                        new Intent("android.intent.action.UNARCHIVE_PACKAGE");
                                intent.addFlags(268435456);
                                intent.putExtra("android.content.pm.extra.UNARCHIVE_ID", i);
                                intent.putExtra(
                                        "android.content.pm.extra.UNARCHIVE_PACKAGE_NAME", str3);
                                intent.putExtra(
                                        "android.content.pm.extra.UNARCHIVE_ALL_USERS",
                                        identifier2 == -1);
                                intent.setPackage(str4);
                                if (identifier2 == -1) {
                                    userHandle2 =
                                            UserHandle.of(
                                                    packageArchiver.mPm.mUserManager
                                                            .getCurrentUserId());
                                }
                                Context context = packageArchiver.mContext;
                                BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                                makeBasic.setTemporaryAppAllowlist(120000, 0, 328, "");
                                context.sendOrderedBroadcastAsUser(
                                        intent,
                                        userHandle2,
                                        null,
                                        -1,
                                        makeBasic.toBundle(),
                                        null,
                                        null,
                                        0,
                                        null,
                                        null);
                            }
                        });
            } catch (RuntimeException e) {
                if (!(e.getCause() instanceof IOException)) {
                    throw e;
                }
                throw ExceptionUtils.wrap((IOException) e.getCause());
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new ParcelableException(e2);
        }
    }

    public final void sendIntent(
            IntentSender intentSender, String str, String str2, Intent intent) {
        try {
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityStartMode(2);
            intentSender.sendIntent(
                    this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (IntentSender.SendIntentException e) {
            android.util.Slog.e(
                    "PackageArchiverService",
                    TextUtils.formatSimple(
                            "Failed to send status for %s with message %s",
                            new Object[] {str, str2}),
                    e);
        }
    }

    public final void storeArchiveState(String str, ArchiveState archiveState, int i) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                if (packageLPr == null || !packageLPr.getUserStateOrDefault(i).isInstalled()) {
                    throw new PackageManager.NameNotFoundException(
                            TextUtils.formatSimple("Package %s not found.", new Object[] {str}));
                }
                PackageUserStateImpl modifyUserState = packageLPr.modifyUserState(i);
                modifyUserState.mArchiveState = archiveState;
                modifyUserState.onChanged$4();
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    public Path storeIcon(
            String str, LauncherActivityInfo launcherActivityInfo, int i, int i2, int i3)
            throws IOException {
        if (launcherActivityInfo.getActivityInfo().getIconResource() == 0) {
            return null;
        }
        return storeDrawable(str, launcherActivityInfo.getUnthemedIcon(0), i, i2, i3);
    }

    public final ApplicationInfo verifyInstaller(Computer computer, String str, int i) {
        if (TextUtils.isEmpty(str)) {
            throw new PackageManager.NameNotFoundException("No installer found");
        }
        if (Binder.getCallingUid() != 2000 && !verifySupportsUnarchival(i, str)) {
            throw new PackageManager.NameNotFoundException("Installer does not support unarchival");
        }
        ApplicationInfo applicationInfo = computer.getApplicationInfo(str, 0L, i);
        if (applicationInfo != null) {
            return applicationInfo;
        }
        throw new PackageManager.NameNotFoundException("Failed to obtain Installer info");
    }

    public final boolean verifySupportsUnarchival(int i, String str) {
        ParceledListSlice parceledListSlice;
        return (TextUtils.isEmpty(str)
                        || (parceledListSlice =
                                        (ParceledListSlice)
                                                Binder.withCleanCallingIdentity(
                                                        new PackageArchiver$$ExternalSyntheticLambda0(
                                                                this,
                                                                new Intent(
                                                                                "android.intent.action.UNARCHIVE_PACKAGE")
                                                                        .setPackage(str),
                                                                i,
                                                                2)))
                                == null
                        || parceledListSlice.getList().isEmpty())
                ? false
                : true;
    }
}
