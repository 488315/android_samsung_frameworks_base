package com.android.server.locales;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ILocaleManager;
import android.app.LocaleConfig;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.PackageConfigurationUpdaterImpl;

import libcore.io.IoUtils;

import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class LocaleManagerService extends SystemService {
    public final ActivityManagerInternal mActivityManagerInternal;
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final LocaleManagerBackupHelper mBackupHelper;
    public final LocaleManagerBinderService mBinderService;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final Object mWriteLock;

    public final class LocaleManagerBinderService extends ILocaleManager.Stub {
        public LocaleManagerBinderService() {}

        public final LocaleList getApplicationLocales(String str, int i) {
            return LocaleManagerService.this.getApplicationLocales(str, i);
        }

        public final LocaleConfig getOverrideLocaleConfig(String str, int i) {
            return LocaleManagerService.this.getOverrideLocaleConfig(str, i);
        }

        public final LocaleList getSystemLocales() {
            LocaleManagerService.this.getClass();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Configuration configuration = ActivityManager.getService().getConfiguration();
                LocaleList locales = configuration != null ? configuration.getLocales() : null;
                if (locales == null) {
                    locales = LocaleList.getEmptyLocaleList();
                }
                return locales;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new LocaleManagerShellCommand(LocaleManagerService.this.mBinderService)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void setApplicationLocales(
                String str, int i, LocaleList localeList, boolean z) {
            LocaleManagerService.this.setApplicationLocales(str, i, localeList, z, z ? 1 : 2);
        }

        public final void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) {
            LocaleManagerService localeManagerService = LocaleManagerService.this;
            localeManagerService.getClass();
            if (SystemProperties.getBoolean("i18n.feature.dynamic_locales_change", true)) {
                int callingUid = Binder.getCallingUid();
                AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord =
                        new AppSupportedLocalesChangedAtomRecord(callingUid);
                try {
                    Objects.requireNonNull(str);
                    int handleIncomingUser =
                            localeManagerService.mActivityManagerInternal.handleIncomingUser(
                                    Binder.getCallingPid(),
                                    Binder.getCallingUid(),
                                    i,
                                    false,
                                    0,
                                    "setOverrideLocaleConfig",
                                    (String) null);
                    if (!localeManagerService.isPackageOwnedByCaller(
                            str, handleIncomingUser, null, appSupportedLocalesChangedAtomRecord)) {
                        try {
                            localeManagerService.mContext.enforceCallingOrSelfPermission(
                                    "android.permission.SET_APP_SPECIFIC_LOCALECONFIG",
                                    "setOverrideLocaleConfig");
                        } catch (SecurityException e) {
                            appSupportedLocalesChangedAtomRecord.mStatus = 4;
                            throw e;
                        }
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        localeManagerService.setOverrideLocaleConfigUnchecked(
                                str,
                                handleIncomingUser,
                                localeConfig,
                                appSupportedLocalesChangedAtomRecord);
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.APP_SUPPORTED_LOCALES_CHANGED,
                                callingUid,
                                appSupportedLocalesChangedAtomRecord.mTargetUid,
                                appSupportedLocalesChangedAtomRecord.mNumLocales,
                                appSupportedLocalesChangedAtomRecord.mOverrideRemoved,
                                appSupportedLocalesChangedAtomRecord.mSameAsResConfig,
                                appSupportedLocalesChangedAtomRecord.mSameAsPrevConfig,
                                appSupportedLocalesChangedAtomRecord.mStatus);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.APP_SUPPORTED_LOCALES_CHANGED,
                            appSupportedLocalesChangedAtomRecord.mCallingUid,
                            appSupportedLocalesChangedAtomRecord.mTargetUid,
                            appSupportedLocalesChangedAtomRecord.mNumLocales,
                            appSupportedLocalesChangedAtomRecord.mOverrideRemoved,
                            appSupportedLocalesChangedAtomRecord.mSameAsResConfig,
                            appSupportedLocalesChangedAtomRecord.mSameAsPrevConfig,
                            appSupportedLocalesChangedAtomRecord.mStatus);
                    throw th;
                }
            }
        }
    }

    public final class LocaleManagerInternalImpl {
        public LocaleManagerInternalImpl() {}

        public final byte[] getBackupPayload(int i) {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Caller is not system.");
            }
            LocaleManagerBackupHelper localeManagerBackupHelper =
                    LocaleManagerService.this.mBackupHelper;
            synchronized (localeManagerBackupHelper.mStagedDataLock) {
                localeManagerBackupHelper.cleanStagedDataForOldEntriesLocked(i);
            }
            HashMap hashMap = new HashMap();
            for (ApplicationInfo applicationInfo :
                    localeManagerBackupHelper.mPackageManager.getInstalledApplicationsAsUser(
                            PackageManager.ApplicationInfoFlags.of(0L), i)) {
                try {
                    LocaleList applicationLocales =
                            localeManagerBackupHelper.mLocaleManagerService.getApplicationLocales(
                                    applicationInfo.packageName, i);
                    if (!applicationLocales.isEmpty()) {
                        SharedPreferences sharedPreferences =
                                localeManagerBackupHelper.mDelegateAppLocalePackages;
                        hashMap.put(
                                applicationInfo.packageName,
                                new LocaleManagerBackupHelper.LocalesInfo(
                                        applicationLocales.toLanguageTags(),
                                        sharedPreferences != null
                                                ? sharedPreferences
                                                        .getStringSet(
                                                                Integer.toString(i),
                                                                Collections.emptySet())
                                                        .contains(applicationInfo.packageName)
                                                : false));
                    }
                } catch (RemoteException | IllegalArgumentException e) {
                    Slog.e(
                            "LocaleManagerBkpHelper",
                            "Exception when getting locales for package: "
                                    + applicationInfo.packageName,
                            e);
                }
            }
            if (hashMap.isEmpty()) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                LocaleManagerBackupHelper.writeToXml(byteArrayOutputStream, hashMap);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e2) {
                Slog.e("LocaleManagerBkpHelper", "Could not write to xml for backup ", e2);
                return null;
            }
        }

        public final void stageAndApplyRestoredPayload(int i, byte[] bArr) {
            PackageInfo packageInfo;
            LocaleManagerBackupHelper localeManagerBackupHelper =
                    LocaleManagerService.this.mBackupHelper;
            localeManagerBackupHelper.getClass();
            if (bArr == null) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        i,
                        "stageAndApplyRestoredPayload: no payload to restore for user ",
                        "LocaleManagerBkpHelper");
                return;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
                newFastPullParser.setInput(byteArrayInputStream, StandardCharsets.UTF_8.name());
                XmlUtils.beginDocument(newFastPullParser, "locales");
                HashMap readFromXml = LocaleManagerBackupHelper.readFromXml(newFastPullParser);
                synchronized (localeManagerBackupHelper.mStagedDataLock) {
                    try {
                        for (String str : readFromXml.keySet()) {
                            LocaleManagerBackupHelper.LocalesInfo localesInfo =
                                    (LocaleManagerBackupHelper.LocalesInfo) readFromXml.get(str);
                            try {
                                packageInfo =
                                        localeManagerBackupHelper
                                                .mContext
                                                .getPackageManager()
                                                .getPackageInfoAsUser(str, 0, i);
                            } catch (PackageManager.NameNotFoundException unused) {
                                packageInfo = null;
                            }
                            if (packageInfo != null) {
                                localeManagerBackupHelper.removeFromArchivedPackagesInfo(i, str);
                                localeManagerBackupHelper.checkExistingLocalesAndApplyRestore(
                                        i, localesInfo, str);
                            } else {
                                localeManagerBackupHelper.storeStagedDataInfo(i, localesInfo, str);
                            }
                        }
                        if (!localeManagerBackupHelper.getStagedDataSp(i).getAll().isEmpty()
                                && !localeManagerBackupHelper
                                        .getStagedDataSp(i)
                                        .edit()
                                        .putLong(
                                                "staged_data_time",
                                                localeManagerBackupHelper.mClock.millis())
                                        .commit()) {
                            Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
                        }
                    } finally {
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                Slog.e("LocaleManagerBkpHelper", "Could not parse payload ", e);
            }
        }
    }

    public LocaleManagerService(Context context) {
        super(context);
        this.mWriteLock = new Object();
        this.mContext = context;
        this.mBinderService = new LocaleManagerBinderService();
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        HandlerThread handlerThread = new HandlerThread("LocaleManagerService", 10);
        handlerThread.start();
        final SystemAppUpdateTracker systemAppUpdateTracker =
                new SystemAppUpdateTracker(
                        context,
                        this,
                        new AtomicFile(
                                new File(
                                        Environment.getDataSystemDirectory(),
                                        "locale_manager_service_updated_system_apps.xml")));
        handlerThread
                .getThreadHandler()
                .postAtFrontOfQueue(
                        new Runnable() { // from class:
                            // com.android.server.locales.LocaleManagerService.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemAppUpdateTracker systemAppUpdateTracker2 =
                                        SystemAppUpdateTracker.this;
                                if (systemAppUpdateTracker2
                                        .mUpdatedAppsFile
                                        .getBaseFile()
                                        .exists()) {
                                    FileInputStream fileInputStream = null;
                                    try {
                                        try {
                                            fileInputStream =
                                                    systemAppUpdateTracker2.mUpdatedAppsFile
                                                            .openRead();
                                            systemAppUpdateTracker2.readFromXml(fileInputStream);
                                        } catch (IOException | XmlPullParserException e) {
                                            Slog.e(
                                                    "SystemAppUpdateTracker",
                                                    "loadUpdatedSystemApps: Could not parse storage"
                                                        + " file ",
                                                    e);
                                        }
                                    } finally {
                                        IoUtils.closeQuietly(fileInputStream);
                                    }
                                }
                            }
                        });
        LocaleManagerBackupHelper localeManagerBackupHelper =
                new LocaleManagerBackupHelper(
                        context,
                        this,
                        packageManager,
                        Clock.systemUTC(),
                        handlerThread,
                        null,
                        null,
                        null);
        this.mBackupHelper = localeManagerBackupHelper;
        LocaleManagerServicePackageMonitor localeManagerServicePackageMonitor =
                new LocaleManagerServicePackageMonitor();
        localeManagerServicePackageMonitor.mBackupHelper = localeManagerBackupHelper;
        localeManagerServicePackageMonitor.mSystemAppUpdateTracker = systemAppUpdateTracker;
        localeManagerServicePackageMonitor.mLocaleManagerService = this;
        localeManagerServicePackageMonitor.register(
                context, handlerThread.getLooper(), UserHandle.ALL, true);
    }

    public LocaleManagerService(
            Context context,
            ActivityTaskManagerInternal activityTaskManagerInternal,
            ActivityManagerInternal activityManagerInternal,
            PackageManager packageManager,
            LocaleManagerBackupHelper localeManagerBackupHelper,
            PackageMonitor packageMonitor) {
        super(context);
        this.mWriteLock = new Object();
        this.mContext = context;
        this.mBinderService = new LocaleManagerBinderService();
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mPackageManager = packageManager;
        this.mBackupHelper = localeManagerBackupHelper;
    }

    public static Intent createBaseIntent(String str, String str2, LocaleList localeList) {
        return new Intent(str)
                .putExtra("android.intent.extra.PACKAGE_NAME", str2)
                .putExtra("android.intent.extra.LOCALE_LIST", localeList)
                .addFlags(285212672);
    }

    public static File getXmlFileNameForUser(int i, String str) {
        return new File(
                new File(Environment.getDataSystemCeDirectory(i), "locale_configs"),
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".xml"));
    }

    public static List loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        ArrayList arrayList = new ArrayList();
        XmlUtils.beginDocument(typedXmlPullParser, "locale-config");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            String name = typedXmlPullParser.getName();
            if ("locale".equals(name)) {
                arrayList.add(typedXmlPullParser.getAttributeValue((String) null, "name"));
            } else {
                Slog.w("LocaleManagerService", "Unexpected tag name: " + name);
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        return arrayList;
    }

    public static byte[] toXmlByteArray(LocaleList localeList) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                newFastSerializer.startDocument((String) null, Boolean.TRUE);
                newFastSerializer.startTag((String) null, "locale-config");
                Iterator it =
                        new ArrayList(Arrays.asList(localeList.toLanguageTags().split(",")))
                                .iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    newFastSerializer.startTag((String) null, "locale");
                    newFastSerializer.attribute((String) null, "name", str);
                    newFastSerializer.endTag((String) null, "locale");
                }
                newFastSerializer.endTag((String) null, "locale-config");
                newFastSerializer.endDocument();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0081, code lost:

       if (r8.mActivityManagerInternal.isAppForeground(getPackageUid(r9, r10)) != false) goto L26;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.LocaleList getApplicationLocales(java.lang.String r9, int r10) {
        /*
            r8 = this;
            java.util.Objects.requireNonNull(r9)
            android.app.ActivityManagerInternal r0 = r8.mActivityManagerInternal
            int r1 = android.os.Binder.getCallingPid()
            int r2 = android.os.Binder.getCallingUid()
            java.lang.String r6 = "getApplicationLocales"
            r7 = 0
            r4 = 0
            r5 = 0
            r3 = r10
            int r10 = r0.handleIncomingUser(r1, r2, r3, r4, r5, r6, r7)
            r0 = 0
            boolean r0 = r8.isPackageOwnedByCaller(r9, r10, r0, r0)
            if (r0 != 0) goto L8d
            java.lang.String r0 = r8.getInstallingPackageName(r10, r9)
            if (r0 == 0) goto L36
            int r0 = r8.getPackageUid(r0, r10)
            if (r0 < 0) goto L36
            int r1 = android.os.Binder.getCallingUid()
            boolean r0 = android.os.UserHandle.isSameApp(r1, r0)
            if (r0 == 0) goto L36
            goto L8d
        L36:
            java.lang.String r0 = "i18n.feature.allow_ime_query_app_locale"
            r1 = 1
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r1)
            if (r0 != 0) goto L41
            goto L83
        L41:
            android.content.Context r0 = r8.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "default_input_method"
            java.lang.String r0 = android.provider.Settings.Secure.getStringForUser(r0, r1, r10)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L83
            android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r0)
            if (r0 != 0) goto L63
            java.lang.String r0 = "LocaleManagerService"
            java.lang.String r1 = "inValid input method"
            android.util.Slog.d(r0, r1)
            goto L83
        L63:
            java.lang.String r0 = r0.getPackageName()
            int r0 = r8.getPackageUid(r0, r10)
            if (r0 < 0) goto L83
            int r1 = android.os.Binder.getCallingUid()
            boolean r0 = android.os.UserHandle.isSameApp(r1, r0)
            if (r0 == 0) goto L83
            android.app.ActivityManagerInternal r0 = r8.mActivityManagerInternal
            int r1 = r8.getPackageUid(r9, r10)
            boolean r0 = r0.isAppForeground(r1)
            if (r0 != 0) goto L8d
        L83:
            android.content.Context r0 = r8.mContext
            java.lang.String r1 = "android.permission.READ_APP_SPECIFIC_LOCALES"
            java.lang.String r2 = "getApplicationLocales"
            r0.enforceCallingOrSelfPermission(r1, r2)
        L8d:
            long r0 = android.os.Binder.clearCallingIdentity()
            android.os.LocaleList r8 = r8.getApplicationLocalesUnchecked(r10, r9)     // Catch: java.lang.Throwable -> L99
            android.os.Binder.restoreCallingIdentity(r0)
            return r8
        L99:
            r8 = move-exception
            android.os.Binder.restoreCallingIdentity(r0)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locales.LocaleManagerService.getApplicationLocales(java.lang.String,"
                    + " int):android.os.LocaleList");
    }

    public final LocaleList getApplicationLocalesUnchecked(int i, String str) {
        LocaleList localeList;
        ActivityTaskManagerInternal.PackageConfig findPackageConfiguration =
                ActivityTaskManagerService.this.mPackageConfigPersister.findPackageConfiguration(
                        i, str);
        return (findPackageConfiguration == null
                        || (localeList = findPackageConfiguration.mLocales) == null)
                ? LocaleList.getEmptyLocaleList()
                : localeList;
    }

    public final String getInstallingPackageName(int i, String str) {
        try {
            return this.mContext
                    .createContextAsUser(UserHandle.of(i), 0)
                    .getPackageManager()
                    .getInstallSourceInfo(str)
                    .getInstallingPackageName();
        } catch (PackageManager.NameNotFoundException unused) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Package not found ", str, "LocaleManagerService");
            return null;
        }
    }

    public final LocaleConfig getOverrideLocaleConfig(String str, int i) {
        if (!SystemProperties.getBoolean("i18n.feature.dynamic_locales_change", true)) {
            return null;
        }
        Objects.requireNonNull(str);
        File xmlFileNameForUser =
                getXmlFileNameForUser(
                        this.mActivityManagerInternal.handleIncomingUser(
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                i,
                                false,
                                0,
                                "getOverrideLocaleConfig",
                                (String) null),
                        str);
        if (!xmlFileNameForUser.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(xmlFileNameForUser);
            try {
                LocaleConfig localeConfig =
                        new LocaleConfig(
                                LocaleList.forLanguageTags(
                                        String.join(
                                                ",",
                                                loadFromXml(
                                                        Xml.resolvePullParser(fileInputStream)))));
                fileInputStream.close();
                return localeConfig;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | XmlPullParserException e) {
            Slog.e(
                    "LocaleManagerService",
                    "Failed to parse XML configuration from " + xmlFileNameForUser,
                    e);
            return null;
        }
    }

    public final int getPackageUid(String str, int i) {
        try {
            return this.mPackageManager.getPackageUidAsUser(
                    str, PackageManager.PackageInfoFlags.of(0L), i);
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public final boolean isPackageOwnedByCaller(
            String str,
            int i,
            AppLocaleChangedAtomRecord appLocaleChangedAtomRecord,
            AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        int packageUid = getPackageUid(str, i);
        if (packageUid >= 0) {
            if (appLocaleChangedAtomRecord != null) {
                appLocaleChangedAtomRecord.mTargetUid = packageUid;
            } else if (appSupportedLocalesChangedAtomRecord != null) {
                appSupportedLocalesChangedAtomRecord.mTargetUid = packageUid;
            }
            return UserHandle.isSameApp(Binder.getCallingUid(), packageUid);
        }
        Slog.w("LocaleManagerService", "Unknown package " + str + " for user " + i);
        if (appLocaleChangedAtomRecord != null) {
            appLocaleChangedAtomRecord.mStatus = 3;
        } else if (appSupportedLocalesChangedAtomRecord != null) {
            appSupportedLocalesChangedAtomRecord.mStatus = 3;
        }
        throw new IllegalArgumentException(
                SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                        .m(i, "Unknown package: ", str, " for user "));
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("locale", this.mBinderService);
        LocalServices.addService(LocaleManagerInternalImpl.class, new LocaleManagerInternalImpl());
    }

    public final void removeUnsupportedAppLocales(
            String str, int i, LocaleConfig localeConfig, int i2) {
        LocaleList applicationLocalesUnchecked = getApplicationLocalesUnchecked(i, str);
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        if (localeConfig == null) {
            Slog.i("LocaleManagerService", "There is no LocaleConfig, reset app locales");
        } else {
            boolean z2 = false;
            for (int i3 = 0; i3 < applicationLocalesUnchecked.size(); i3++) {
                if (localeConfig.containsLocale(applicationLocalesUnchecked.get(i3))) {
                    arrayList.add(applicationLocalesUnchecked.get(i3));
                } else {
                    Slog.i(
                            "LocaleManagerService",
                            "Missing from the LocaleConfig, reset app locales");
                    z2 = true;
                }
            }
            z = z2;
        }
        if (z) {
            try {
                setApplicationLocales(
                        str,
                        i,
                        new LocaleList((Locale[]) arrayList.toArray(new Locale[arrayList.size()])),
                        this.mBackupHelper.areLocalesSetFromDelegate(i, str),
                        i2);
            } catch (RemoteException | IllegalArgumentException e) {
                Slog.e("LocaleManagerService", "Could not set locales for " + str, e);
            }
        }
    }

    public final void setApplicationLocales(
            String str, int i, LocaleList localeList, boolean z, int i2) {
        AppLocaleChangedAtomRecord appLocaleChangedAtomRecord =
                new AppLocaleChangedAtomRecord(Binder.getCallingUid());
        try {
            Objects.requireNonNull(str);
            Objects.requireNonNull(localeList);
            appLocaleChangedAtomRecord.mCaller = i2;
            appLocaleChangedAtomRecord.mNewLocales =
                    AppLocaleChangedAtomRecord.convertEmptyLocales(localeList.toLanguageTags());
            int handleIncomingUser =
                    this.mActivityManagerInternal.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            0,
                            "setApplicationLocales",
                            (String) null);
            if (!isPackageOwnedByCaller(
                    str, handleIncomingUser, appLocaleChangedAtomRecord, null)) {
                try {
                    this.mContext.enforceCallingOrSelfPermission(
                            "android.permission.CHANGE_CONFIGURATION", "setApplicationLocales");
                } catch (SecurityException e) {
                    appLocaleChangedAtomRecord.mStatus = 4;
                    throw e;
                }
            }
            this.mBackupHelper.persistLocalesModificationInfo(
                    str, z, localeList.isEmpty(), handleIncomingUser);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setApplicationLocalesUnchecked(
                        str, handleIncomingUser, localeList, appLocaleChangedAtomRecord);
                try {
                    LocaleOverlayManagerWrapper.getInstance(this.mContext)
                            .applyPerAppLocale(localeList, str, handleIncomingUser);
                } catch (Exception e2) {
                    Slog.e("LocaleManagerService", "Error while starting LOM: " + e2);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.APPLICATION_LOCALES_CHANGED,
                    appLocaleChangedAtomRecord.mCallingUid,
                    appLocaleChangedAtomRecord.mTargetUid,
                    appLocaleChangedAtomRecord.mNewLocales,
                    appLocaleChangedAtomRecord.mPrevLocales,
                    appLocaleChangedAtomRecord.mStatus,
                    appLocaleChangedAtomRecord.mCaller);
        }
    }

    public final void setApplicationLocalesUnchecked(
            String str,
            int i,
            LocaleList localeList,
            AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        appLocaleChangedAtomRecord.mPrevLocales =
                AppLocaleChangedAtomRecord.convertEmptyLocales(
                        getApplicationLocalesUnchecked(i, str).toLanguageTags());
        PackageConfigurationUpdaterImpl packageConfigurationUpdaterImpl =
                new PackageConfigurationUpdaterImpl(i, ActivityTaskManagerService.this, str);
        synchronized (packageConfigurationUpdaterImpl) {
            packageConfigurationUpdaterImpl.mLocales = localeList;
        }
        if (!packageConfigurationUpdaterImpl.commit()) {
            appLocaleChangedAtomRecord.mStatus = 2;
            return;
        }
        Intent createBaseIntent =
                createBaseIntent("android.intent.action.LOCALE_CHANGED", str, localeList);
        createBaseIntent.setPackage(str);
        createBaseIntent.addFlags(2097152);
        this.mContext.sendBroadcastAsUser(createBaseIntent, UserHandle.of(i));
        String installingPackageName = getInstallingPackageName(i, str);
        if (installingPackageName != null) {
            Intent createBaseIntent2 =
                    createBaseIntent(
                            "android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList);
            createBaseIntent2.setPackage(installingPackageName);
            this.mContext.sendBroadcastAsUser(createBaseIntent2, UserHandle.of(i));
        }
        this.mContext.sendBroadcastAsUser(
                createBaseIntent(
                        "android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList),
                UserHandle.of(i),
                "android.permission.READ_APP_SPECIFIC_LOCALES");
        this.mBackupHelper.getClass();
        BackupManager.dataChanged("android");
        appLocaleChangedAtomRecord.mStatus = 1;
    }

    public final void setOverrideLocaleConfigUnchecked(
            String str,
            int i,
            LocaleConfig localeConfig,
            AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        FileOutputStream fileOutputStream;
        synchronized (this.mWriteLock) {
            try {
                try {
                    LocaleConfig fromContextIgnoringOverride =
                            LocaleConfig.fromContextIgnoringOverride(
                                    this.mContext.createPackageContext(str, 0));
                    File xmlFileNameForUser = getXmlFileNameForUser(i, str);
                    if (localeConfig == null) {
                        if (xmlFileNameForUser.exists()) {
                            Slog.d("LocaleManagerService", "remove the override LocaleConfig");
                            xmlFileNameForUser.delete();
                        }
                        removeUnsupportedAppLocales(str, i, fromContextIgnoringOverride, 5);
                        appSupportedLocalesChangedAtomRecord.mOverrideRemoved = true;
                        appSupportedLocalesChangedAtomRecord.mStatus = 1;
                        return;
                    }
                    if (localeConfig.isSameLocaleConfig(getOverrideLocaleConfig(str, i))) {
                        Slog.d("LocaleManagerService", "the same override, ignore it");
                        appSupportedLocalesChangedAtomRecord.mSameAsPrevConfig = true;
                        return;
                    }
                    LocaleList supportedLocales = localeConfig.getSupportedLocales();
                    if (supportedLocales == null) {
                        supportedLocales = LocaleList.getEmptyLocaleList();
                    }
                    appSupportedLocalesChangedAtomRecord.mNumLocales = supportedLocales.size();
                    AtomicFile atomicFile = new AtomicFile(xmlFileNameForUser);
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        try {
                            fileOutputStream.write(toXmlByteArray(supportedLocales));
                            atomicFile.finishWrite(fileOutputStream);
                            removeUnsupportedAppLocales(str, i, localeConfig, 5);
                            if (localeConfig.isSameLocaleConfig(fromContextIgnoringOverride)) {
                                Slog.d(
                                        "LocaleManagerService",
                                        "setOverrideLocaleConfig, same as the app's LocaleConfig");
                                appSupportedLocalesChangedAtomRecord.mSameAsResConfig = true;
                            }
                            appSupportedLocalesChangedAtomRecord.mStatus = 1;
                        } catch (Exception e) {
                            e = e;
                            Slog.e("LocaleManagerService", "Failed to write file " + atomicFile, e);
                            if (fileOutputStream != null) {
                                atomicFile.failWrite(fileOutputStream);
                            }
                            appSupportedLocalesChangedAtomRecord.mStatus = 2;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.e("LocaleManagerService", "Unknown package name " + str);
            }
        }
    }
}
