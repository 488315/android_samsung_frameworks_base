package com.android.server.locales;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ILocaleManager;
import android.app.LocaleConfig;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class LocaleManagerService extends SystemService {
    public ActivityManagerInternal mActivityManagerInternal;
    public ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public LocaleManagerBackupHelper mBackupHelper;
    public final LocaleManagerBinderService mBinderService;
    public final Context mContext;
    public PackageManager mPackageManager;
    public final PackageMonitor mPackageMonitor;
    public final Object mWriteLock;

    public LocaleManagerService(Context context) {
        super(context);
        this.mWriteLock = new Object();
        this.mContext = context;
        this.mBinderService = new LocaleManagerBinderService();
        this.mActivityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mPackageManager = context.getPackageManager();
        HandlerThread handlerThread = new HandlerThread("LocaleManagerService", 10);
        handlerThread.start();
        final SystemAppUpdateTracker systemAppUpdateTracker = new SystemAppUpdateTracker(this);
        handlerThread.getThreadHandler().postAtFrontOfQueue(new Runnable() { // from class: com.android.server.locales.LocaleManagerService.1
            @Override // java.lang.Runnable
            public void run() {
                systemAppUpdateTracker.init();
            }
        });
        this.mBackupHelper = new LocaleManagerBackupHelper(this, this.mPackageManager, handlerThread);
        LocaleManagerServicePackageMonitor localeManagerServicePackageMonitor = new LocaleManagerServicePackageMonitor(this.mBackupHelper, systemAppUpdateTracker, this);
        this.mPackageMonitor = localeManagerServicePackageMonitor;
        localeManagerServicePackageMonitor.register(context, handlerThread.getLooper(), UserHandle.ALL, true);
    }

    public LocaleManagerService(Context context, ActivityTaskManagerInternal activityTaskManagerInternal, ActivityManagerInternal activityManagerInternal, PackageManager packageManager, LocaleManagerBackupHelper localeManagerBackupHelper, PackageMonitor packageMonitor) {
        super(context);
        this.mWriteLock = new Object();
        this.mContext = context;
        this.mBinderService = new LocaleManagerBinderService();
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mPackageManager = packageManager;
        this.mBackupHelper = localeManagerBackupHelper;
        this.mPackageMonitor = packageMonitor;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("locale", this.mBinderService);
        LocalServices.addService(LocaleManagerInternal.class, new LocaleManagerInternalImpl());
    }

    public final class LocaleManagerInternalImpl extends LocaleManagerInternal {
        public LocaleManagerInternalImpl() {
        }

        @Override // com.android.server.locales.LocaleManagerInternal
        public byte[] getBackupPayload(int i) {
            checkCallerIsSystem();
            return LocaleManagerService.this.mBackupHelper.getBackupPayload(i);
        }

        @Override // com.android.server.locales.LocaleManagerInternal
        public void stageAndApplyRestoredPayload(byte[] bArr, int i) {
            LocaleManagerService.this.mBackupHelper.stageAndApplyRestoredPayload(bArr, i);
        }

        public final void checkCallerIsSystem() {
            if (Binder.getCallingUid() != 1000) {
                throw new SecurityException("Caller is not system.");
            }
        }
    }

    public final class LocaleManagerBinderService extends ILocaleManager.Stub {
        public LocaleManagerBinderService() {
        }

        public void setApplicationLocales(String str, int i, LocaleList localeList, boolean z) {
            LocaleManagerService.this.setApplicationLocales(str, i, localeList, z, z ? 1 : 2);
        }

        public LocaleList getApplicationLocales(String str, int i) {
            return LocaleManagerService.this.getApplicationLocales(str, i);
        }

        public LocaleList getSystemLocales() {
            return LocaleManagerService.this.getSystemLocales();
        }

        public void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) {
            LocaleManagerService.this.setOverrideLocaleConfig(str, i, localeConfig);
        }

        public LocaleConfig getOverrideLocaleConfig(String str, int i) {
            return LocaleManagerService.this.getOverrideLocaleConfig(str, i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new LocaleManagerShellCommand(LocaleManagerService.this.mBinderService).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }
    }

    public void setApplicationLocales(String str, int i, LocaleList localeList, boolean z, int i2) {
        AppLocaleChangedAtomRecord appLocaleChangedAtomRecord = new AppLocaleChangedAtomRecord(Binder.getCallingUid());
        try {
            Objects.requireNonNull(str);
            Objects.requireNonNull(localeList);
            appLocaleChangedAtomRecord.setCaller(i2);
            appLocaleChangedAtomRecord.setNewLocales(localeList.toLanguageTags());
            int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "setApplicationLocales", (String) null);
            if (!isPackageOwnedByCaller(str, handleIncomingUser, appLocaleChangedAtomRecord, null)) {
                enforceChangeConfigurationPermission(appLocaleChangedAtomRecord);
            }
            this.mBackupHelper.persistLocalesModificationInfo(handleIncomingUser, str, z, localeList.isEmpty());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setApplicationLocalesUnchecked(str, handleIncomingUser, localeList, appLocaleChangedAtomRecord);
                try {
                    LocaleOverlayManagerWrapper.getInstance(this.mContext).applyPerAppLocale(localeList, str, handleIncomingUser);
                } catch (Exception e) {
                    Slog.e("LocaleManagerService", "Error while starting LOM: " + e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            logAppLocalesMetric(appLocaleChangedAtomRecord);
        }
    }

    public static void startResourceOverlayServiceForCleanUp(Context context) {
        try {
            LocaleOverlayManagerWrapper.getInstance(context).cleanUpOverlays();
        } catch (Exception e) {
            Slog.e("LocaleManagerService", "Error while starting LOM: " + e);
        }
    }

    public final void setApplicationLocalesUnchecked(String str, int i, LocaleList localeList, AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        appLocaleChangedAtomRecord.setPrevLocales(getApplicationLocalesUnchecked(str, i).toLanguageTags());
        if (this.mActivityTaskManagerInternal.createPackageConfigurationUpdater(str, i).setLocales(localeList).commit()) {
            notifyAppWhoseLocaleChanged(str, i, localeList);
            notifyInstallerOfAppWhoseLocaleChanged(str, i, localeList);
            notifyRegisteredReceivers(str, i, localeList);
            this.mBackupHelper.notifyBackupManager();
            appLocaleChangedAtomRecord.setStatus(1);
            return;
        }
        appLocaleChangedAtomRecord.setStatus(2);
    }

    public final void notifyRegisteredReceivers(String str, int i, LocaleList localeList) {
        this.mContext.sendBroadcastAsUser(createBaseIntent("android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList), UserHandle.of(i), "android.permission.READ_APP_SPECIFIC_LOCALES");
    }

    public void notifyInstallerOfAppWhoseLocaleChanged(String str, int i, LocaleList localeList) {
        String installingPackageName = getInstallingPackageName(str, i);
        if (installingPackageName != null) {
            Intent createBaseIntent = createBaseIntent("android.intent.action.APPLICATION_LOCALE_CHANGED", str, localeList);
            createBaseIntent.setPackage(installingPackageName);
            this.mContext.sendBroadcastAsUser(createBaseIntent, UserHandle.of(i));
        }
    }

    public final void notifyAppWhoseLocaleChanged(String str, int i, LocaleList localeList) {
        Intent createBaseIntent = createBaseIntent("android.intent.action.LOCALE_CHANGED", str, localeList);
        createBaseIntent.setPackage(str);
        createBaseIntent.addFlags(2097152);
        this.mContext.sendBroadcastAsUser(createBaseIntent, UserHandle.of(i));
    }

    public static Intent createBaseIntent(String str, String str2, LocaleList localeList) {
        return new Intent(str).putExtra("android.intent.extra.PACKAGE_NAME", str2).putExtra("android.intent.extra.LOCALE_LIST", localeList).addFlags(285212672);
    }

    public final boolean isPackageOwnedByCaller(String str, int i, AppLocaleChangedAtomRecord appLocaleChangedAtomRecord, AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        int packageUid = getPackageUid(str, i);
        if (packageUid < 0) {
            Slog.w("LocaleManagerService", "Unknown package " + str + " for user " + i);
            if (appLocaleChangedAtomRecord != null) {
                appLocaleChangedAtomRecord.setStatus(3);
            } else if (appSupportedLocalesChangedAtomRecord != null) {
                appSupportedLocalesChangedAtomRecord.setStatus(3);
            }
            throw new IllegalArgumentException("Unknown package: " + str + " for user " + i);
        }
        if (appLocaleChangedAtomRecord != null) {
            appLocaleChangedAtomRecord.setTargetUid(packageUid);
        } else if (appSupportedLocalesChangedAtomRecord != null) {
            appSupportedLocalesChangedAtomRecord.setTargetUid(packageUid);
        }
        return UserHandle.isSameApp(Binder.getCallingUid(), packageUid);
    }

    public final void enforceChangeConfigurationPermission(AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.CHANGE_CONFIGURATION", "setApplicationLocales");
        } catch (SecurityException e) {
            appLocaleChangedAtomRecord.setStatus(4);
            throw e;
        }
    }

    public LocaleList getApplicationLocales(String str, int i) {
        Objects.requireNonNull(str);
        int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "getApplicationLocales", (String) null);
        if (!isPackageOwnedByCaller(str, handleIncomingUser, null, null) && !isCallerInstaller(str, handleIncomingUser) && (!isCallerFromCurrentInputMethod(handleIncomingUser) || !this.mActivityManagerInternal.isAppForeground(getPackageUid(str, handleIncomingUser)))) {
            enforceReadAppSpecificLocalesPermission();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getApplicationLocalesUnchecked(str, handleIncomingUser);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final LocaleList getApplicationLocalesUnchecked(String str, int i) {
        ActivityTaskManagerInternal.PackageConfig applicationConfig = this.mActivityTaskManagerInternal.getApplicationConfig(str, i);
        if (applicationConfig == null) {
            return LocaleList.getEmptyLocaleList();
        }
        LocaleList localeList = applicationConfig.mLocales;
        return localeList != null ? localeList : LocaleList.getEmptyLocaleList();
    }

    public final boolean isCallerInstaller(String str, int i) {
        int packageUid;
        String installingPackageName = getInstallingPackageName(str, i);
        return installingPackageName != null && (packageUid = getPackageUid(installingPackageName, i)) >= 0 && UserHandle.isSameApp(Binder.getCallingUid(), packageUid);
    }

    public final boolean isCallerFromCurrentInputMethod(int i) {
        if (!SystemProperties.getBoolean("i18n.feature.allow_ime_query_app_locale", true)) {
            return false;
        }
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "default_input_method", i);
        if (TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        int packageUid = getPackageUid(ComponentName.unflattenFromString(stringForUser).getPackageName(), i);
        return packageUid >= 0 && UserHandle.isSameApp(Binder.getCallingUid(), packageUid);
    }

    public final void enforceReadAppSpecificLocalesPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_APP_SPECIFIC_LOCALES", "getApplicationLocales");
    }

    public final int getPackageUid(String str, int i) {
        try {
            return this.mPackageManager.getPackageUidAsUser(str, PackageManager.PackageInfoFlags.of(0L), i);
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    public String getInstallingPackageName(String str, int i) {
        try {
            return this.mContext.createContextAsUser(UserHandle.of(i), 0).getPackageManager().getInstallSourceInfo(str).getInstallingPackageName();
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w("LocaleManagerService", "Package not found " + str);
            return null;
        }
    }

    public LocaleList getSystemLocales() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getSystemLocalesUnchecked();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final LocaleList getSystemLocalesUnchecked() {
        Configuration configuration = ActivityManager.getService().getConfiguration();
        LocaleList locales = configuration != null ? configuration.getLocales() : null;
        return locales == null ? LocaleList.getEmptyLocaleList() : locales;
    }

    public final void logAppLocalesMetric(AppLocaleChangedAtomRecord appLocaleChangedAtomRecord) {
        FrameworkStatsLog.write(FrameworkStatsLog.APPLICATION_LOCALES_CHANGED, appLocaleChangedAtomRecord.mCallingUid, appLocaleChangedAtomRecord.mTargetUid, appLocaleChangedAtomRecord.mNewLocales, appLocaleChangedAtomRecord.mPrevLocales, appLocaleChangedAtomRecord.mStatus, appLocaleChangedAtomRecord.mCaller);
    }

    public void setOverrideLocaleConfig(String str, int i, LocaleConfig localeConfig) {
        if (SystemProperties.getBoolean("i18n.feature.dynamic_locales_change", true)) {
            AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord = new AppSupportedLocalesChangedAtomRecord(Binder.getCallingUid());
            try {
                Objects.requireNonNull(str);
                int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "setOverrideLocaleConfig", (String) null);
                if (!isPackageOwnedByCaller(str, handleIncomingUser, null, appSupportedLocalesChangedAtomRecord)) {
                    enforceSetAppSpecificLocaleConfigPermission(appSupportedLocalesChangedAtomRecord);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    setOverrideLocaleConfigUnchecked(str, handleIncomingUser, localeConfig, appSupportedLocalesChangedAtomRecord);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
                logAppSupportedLocalesChangedMetric(appSupportedLocalesChangedAtomRecord);
            }
        }
    }

    public final void setOverrideLocaleConfigUnchecked(String str, int i, LocaleConfig localeConfig, AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        FileOutputStream fileOutputStream;
        synchronized (this.mWriteLock) {
            try {
                try {
                    LocaleConfig fromContextIgnoringOverride = LocaleConfig.fromContextIgnoringOverride(this.mContext.createPackageContext(str, 0));
                    File xmlFileNameForUser = getXmlFileNameForUser(str, i);
                    if (localeConfig == null) {
                        if (xmlFileNameForUser.exists()) {
                            Slog.d("LocaleManagerService", "remove the override LocaleConfig");
                            xmlFileNameForUser.delete();
                        }
                        removeUnsupportedAppLocales(str, i, fromContextIgnoringOverride, 5);
                        appSupportedLocalesChangedAtomRecord.setOverrideRemoved(true);
                        appSupportedLocalesChangedAtomRecord.setStatus(1);
                        return;
                    }
                    if (localeConfig.isSameLocaleConfig(getOverrideLocaleConfig(str, i))) {
                        Slog.d("LocaleManagerService", "the same override, ignore it");
                        appSupportedLocalesChangedAtomRecord.setSameAsPrevConfig(true);
                        return;
                    }
                    LocaleList supportedLocales = localeConfig.getSupportedLocales();
                    if (supportedLocales == null) {
                        supportedLocales = LocaleList.getEmptyLocaleList();
                    }
                    appSupportedLocalesChangedAtomRecord.setNumLocales(supportedLocales.size());
                    AtomicFile atomicFile = new AtomicFile(xmlFileNameForUser);
                    try {
                        fileOutputStream = atomicFile.startWrite();
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = null;
                    }
                    try {
                        fileOutputStream.write(toXmlByteArray(supportedLocales));
                        atomicFile.finishWrite(fileOutputStream);
                        removeUnsupportedAppLocales(str, i, localeConfig, 5);
                        if (localeConfig.isSameLocaleConfig(fromContextIgnoringOverride)) {
                            Slog.d("LocaleManagerService", "setOverrideLocaleConfig, same as the app's LocaleConfig");
                            appSupportedLocalesChangedAtomRecord.setSameAsResConfig(true);
                        }
                        appSupportedLocalesChangedAtomRecord.setStatus(1);
                    } catch (Exception e2) {
                        e = e2;
                        Slog.e("LocaleManagerService", "Failed to write file " + atomicFile, e);
                        if (fileOutputStream != null) {
                            atomicFile.failWrite(fileOutputStream);
                        }
                        appSupportedLocalesChangedAtomRecord.setStatus(2);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("LocaleManagerService", "Unknown package name " + str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void removeUnsupportedAppLocales(String str, int i, LocaleConfig localeConfig, int i2) {
        LocaleList applicationLocalesUnchecked = getApplicationLocalesUnchecked(str, i);
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        if (localeConfig == null) {
            Slog.i("LocaleManagerService", "There is no LocaleConfig, reset app locales");
        } else {
            boolean z2 = false;
            for (int i3 = 0; i3 < applicationLocalesUnchecked.size(); i3++) {
                if (!localeConfig.containsLocale(applicationLocalesUnchecked.get(i3))) {
                    Slog.i("LocaleManagerService", "Missing from the LocaleConfig, reset app locales");
                    z2 = true;
                } else {
                    arrayList.add(applicationLocalesUnchecked.get(i3));
                }
            }
            z = z2;
        }
        if (z) {
            try {
                setApplicationLocales(str, i, new LocaleList((Locale[]) arrayList.toArray(new Locale[arrayList.size()])), this.mBackupHelper.areLocalesSetFromDelegate(i, str), i2);
            } catch (RemoteException | IllegalArgumentException e) {
                Slog.e("LocaleManagerService", "Could not set locales for " + str, e);
            }
        }
    }

    public final void enforceSetAppSpecificLocaleConfigPermission(AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.SET_APP_SPECIFIC_LOCALECONFIG", "setOverrideLocaleConfig");
        } catch (SecurityException e) {
            appSupportedLocalesChangedAtomRecord.setStatus(4);
            throw e;
        }
    }

    public LocaleConfig getOverrideLocaleConfig(String str, int i) {
        if (!SystemProperties.getBoolean("i18n.feature.dynamic_locales_change", true)) {
            return null;
        }
        Objects.requireNonNull(str);
        File xmlFileNameForUser = getXmlFileNameForUser(str, this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "getOverrideLocaleConfig", (String) null));
        if (!xmlFileNameForUser.exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(xmlFileNameForUser);
            try {
                LocaleConfig localeConfig = new LocaleConfig(LocaleList.forLanguageTags(String.join(",", loadFromXml(Xml.resolvePullParser(fileInputStream)))));
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
            Slog.e("LocaleManagerService", "Failed to parse XML configuration from " + xmlFileNameForUser, e);
            return null;
        }
    }

    public void deleteOverrideLocaleConfig(String str, int i) {
        File xmlFileNameForUser = getXmlFileNameForUser(str, i);
        if (xmlFileNameForUser.exists()) {
            Slog.d("LocaleManagerService", "Delete the override LocaleConfig.");
            xmlFileNameForUser.delete();
        }
    }

    public final byte[] toXmlByteArray(LocaleList localeList) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
                newFastSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                newFastSerializer.startDocument((String) null, Boolean.TRUE);
                newFastSerializer.startTag((String) null, "locale-config");
                for (String str : new ArrayList(Arrays.asList(localeList.toLanguageTags().split(",")))) {
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

    public final List loadFromXml(TypedXmlPullParser typedXmlPullParser) {
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

    public final File getXmlFileNameForUser(String str, int i) {
        return new File(new File(Environment.getDataSystemCeDirectory(i), "locale_configs"), str + ".xml");
    }

    public final void logAppSupportedLocalesChangedMetric(AppSupportedLocalesChangedAtomRecord appSupportedLocalesChangedAtomRecord) {
        FrameworkStatsLog.write(FrameworkStatsLog.APP_SUPPORTED_LOCALES_CHANGED, appSupportedLocalesChangedAtomRecord.mCallingUid, appSupportedLocalesChangedAtomRecord.mTargetUid, appSupportedLocalesChangedAtomRecord.mNumLocales, appSupportedLocalesChangedAtomRecord.mOverrideRemoved, appSupportedLocalesChangedAtomRecord.mSameAsResConfig, appSupportedLocalesChangedAtomRecord.mSameAsPrevConfig, appSupportedLocalesChangedAtomRecord.mStatus);
    }
}
