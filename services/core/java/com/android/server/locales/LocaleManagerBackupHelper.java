package com.android.server.locales;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LocaleManagerBackupHelper {
    public static final Duration STAGE_DATA_RETENTION_PERIOD = Duration.ofDays(3);
    public final File mArchivedPackagesFile;
    public final Clock mClock;
    public final Context mContext;
    public final SharedPreferences mDelegateAppLocalePackages;
    public final LocaleManagerService mLocaleManagerService;
    public final PackageManager mPackageManager;
    public final SparseArray mStagedDataFiles;
    public final Object mStagedDataLock = new Object();
    public final UserMonitor mUserMonitor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalesInfo {
        public final String mLocales;
        public final boolean mSetFromDelegate;

        public LocalesInfo(String str, boolean z) {
            this.mLocales = str;
            this.mSetFromDelegate = z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserMonitor extends BroadcastReceiver {
        public UserMonitor() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().equals("android.intent.action.USER_REMOVED")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                    synchronized (LocaleManagerBackupHelper.this.mStagedDataLock) {
                        LocaleManagerBackupHelper.this.deleteStagedDataLocked(intExtra);
                        LocaleManagerBackupHelper localeManagerBackupHelper =
                                LocaleManagerBackupHelper.this;
                        localeManagerBackupHelper.getClass();
                        String num = Integer.toString(intExtra);
                        SharedPreferences sharedPreferences =
                                localeManagerBackupHelper.mDelegateAppLocalePackages;
                        if (sharedPreferences != null && sharedPreferences.contains(num)) {
                            if (!localeManagerBackupHelper
                                    .mDelegateAppLocalePackages
                                    .edit()
                                    .remove(num)
                                    .commit()) {
                                Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
                            }
                            LocaleManagerBackupHelper.m638$$Nest$mremoveArchivedPackagesForUser(
                                    LocaleManagerBackupHelper.this, intExtra);
                        }
                        Slog.w(
                                "LocaleManagerBkpHelper",
                                "The profile is not existed in the persisted info");
                        LocaleManagerBackupHelper.m638$$Nest$mremoveArchivedPackagesForUser(
                                LocaleManagerBackupHelper.this, intExtra);
                    }
                }
            } catch (Exception e) {
                Slog.e("LocaleManagerBkpHelper", "Exception in user monitor.", e);
            }
        }
    }

    /* renamed from: -$$Nest$mremoveArchivedPackagesForUser, reason: not valid java name */
    public static void m638$$Nest$mremoveArchivedPackagesForUser(
            LocaleManagerBackupHelper localeManagerBackupHelper, int i) {
        localeManagerBackupHelper.getClass();
        String num = Integer.toString(i);
        File archivedPackagesFile = localeManagerBackupHelper.getArchivedPackagesFile();
        SharedPreferences archivedPackagesSp =
                localeManagerBackupHelper.getArchivedPackagesSp(archivedPackagesFile);
        if (archivedPackagesSp == null || !archivedPackagesSp.contains(num)) {
            Slog.w(
                    "LocaleManagerBkpHelper",
                    "The profile is not existed in the archived package info");
            return;
        }
        if (!archivedPackagesSp.edit().remove(num).commit()) {
            Slog.e("LocaleManagerBkpHelper", "Failed to remove user");
        }
        if (archivedPackagesSp.getAll().isEmpty() && archivedPackagesFile.exists()) {
            archivedPackagesFile.delete();
        }
    }

    public LocaleManagerBackupHelper(
            Context context,
            LocaleManagerService localeManagerService,
            PackageManager packageManager,
            Clock clock,
            HandlerThread handlerThread,
            SparseArray sparseArray,
            File file,
            SharedPreferences sharedPreferences) {
        this.mContext = context;
        this.mLocaleManagerService = localeManagerService;
        this.mPackageManager = packageManager;
        this.mClock = clock;
        if (sharedPreferences == null) {
            sharedPreferences =
                    context.createDeviceProtectedStorageContext()
                            .getSharedPreferences(
                                    new File(
                                            Environment.getDataSystemDeDirectory(0),
                                            "LocalesFromDelegatePrefs.xml"),
                                    0);
        }
        this.mDelegateAppLocalePackages = sharedPreferences;
        this.mArchivedPackagesFile = file;
        this.mStagedDataFiles = sparseArray;
        UserMonitor userMonitor = new UserMonitor();
        this.mUserMonitor = userMonitor;
        context.registerReceiverAsUser(
                userMonitor,
                UserHandle.ALL,
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_REMOVED"),
                null,
                handlerThread.getThreadHandler());
    }

    public static HashMap readFromXml(TypedXmlPullParser typedXmlPullParser) {
        HashMap hashMap = new HashMap();
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("package")) {
                String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
                String attributeValue2 =
                        typedXmlPullParser.getAttributeValue((String) null, "locales");
                boolean attributeBoolean =
                        typedXmlPullParser.getAttributeBoolean(
                                (String) null, "delegate_selector", false);
                if (!TextUtils.isEmpty(attributeValue) && !TextUtils.isEmpty(attributeValue2)) {
                    hashMap.put(attributeValue, new LocalesInfo(attributeValue2, attributeBoolean));
                }
            }
        }
        return hashMap;
    }

    public static void writeToXml(OutputStream outputStream, HashMap hashMap) {
        if (hashMap.isEmpty()) {
            return;
        }
        TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((String) null, Boolean.TRUE);
        newFastSerializer.startTag((String) null, "locales");
        for (String str : hashMap.keySet()) {
            newFastSerializer.startTag((String) null, "package");
            newFastSerializer.attribute((String) null, "name", str);
            newFastSerializer.attribute(
                    (String) null, "locales", ((LocalesInfo) hashMap.get(str)).mLocales);
            newFastSerializer.attributeBoolean(
                    (String) null,
                    "delegate_selector",
                    ((LocalesInfo) hashMap.get(str)).mSetFromDelegate);
            newFastSerializer.endTag((String) null, "package");
        }
        newFastSerializer.endTag((String) null, "locales");
        newFastSerializer.endDocument();
    }

    public final boolean areLocalesSetFromDelegate(int i, String str) {
        if (this.mDelegateAppLocalePackages == null) {
            Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
            return false;
        }
        return new ArraySet(
                        this.mDelegateAppLocalePackages.getStringSet(
                                Integer.toString(i), new ArraySet()))
                .contains(str);
    }

    public final void checkExistingLocalesAndApplyRestore(
            int i, LocalesInfo localesInfo, String str) {
        if (localesInfo == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "No locales info for ", str, "LocaleManagerBkpHelper");
            return;
        }
        try {
            if (!this.mLocaleManagerService.getApplicationLocales(str, i).isEmpty()) {
                return;
            }
        } catch (RemoteException | IllegalArgumentException e) {
            Slog.e(
                    "LocaleManagerBkpHelper",
                    "Could not check for current locales before restoring",
                    e);
        }
        try {
            this.mLocaleManagerService.setApplicationLocales(
                    str,
                    i,
                    LocaleList.forLanguageTags(localesInfo.mLocales),
                    localesInfo.mSetFromDelegate,
                    3);
        } catch (RemoteException | IllegalArgumentException e2) {
            Slog.e("LocaleManagerBkpHelper", "Could not restore locales for " + str, e2);
        }
    }

    public final void checkStageDataAndApplyRestore(int i, String str) {
        try {
            synchronized (this.mStagedDataLock) {
                try {
                    cleanStagedDataForOldEntriesLocked(i);
                    if (!getStagedDataSp(i).getString(str, "").isEmpty()) {
                        removeFromArchivedPackagesInfo(i, str);
                        doLazyRestoreLocked(i, str);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Exception e) {
            Slog.e("LocaleManagerBkpHelper", "Exception in onPackageAdded.", e);
        }
    }

    public final void cleanStagedDataForOldEntriesLocked(int i) {
        long j = getStagedDataSp(i).getLong("staged_data_time", -1L);
        if (j == -1 || j >= this.mClock.millis() - STAGE_DATA_RETENTION_PERIOD.toMillis()) {
            return;
        }
        deleteStagedDataLocked(i);
    }

    public final void deleteStagedDataLocked(int i) {
        SparseArray sparseArray = this.mStagedDataFiles;
        File file =
                sparseArray == null
                        ? new File(
                                Environment.getDataSystemDeDirectory(i),
                                "LocalesStagedDataPrefs.xml")
                        : (File) sparseArray.get(i);
        if (!(this.mStagedDataFiles == null
                        ? this.mContext
                                .createDeviceProtectedStorageContext()
                                .getSharedPreferences(file, 0)
                        : this.mContext.getSharedPreferences(file, 0))
                .edit()
                .clear()
                .commit()) {
            Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
        }
        if (file.exists()) {
            file.delete();
        }
    }

    public final void doLazyRestoreLocked(int i, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = this.mContext.getPackageManager().getPackageInfoAsUser(str, 0, i);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        if (!(packageInfo != null)) {
            Slog.e(
                    "LocaleManagerBkpHelper",
                    str
                            + " not installed for user "
                            + i
                            + ". Could not restore locales from stage data");
            return;
        }
        SharedPreferences stagedDataSp = getStagedDataSp(i);
        String string = stagedDataSp.getString(str, "");
        if (!string.isEmpty()) {
            String[] split = string.split(" s:");
            if (split == null || split.length != 2) {
                Slog.e("LocaleManagerBkpHelper", "Failed to restore data");
                return;
            } else {
                checkExistingLocalesAndApplyRestore(
                        i, new LocalesInfo(split[0], Boolean.parseBoolean(split[1])), str);
                if (!stagedDataSp.edit().remove(str).commit()) {
                    Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
                }
            }
        }
        if (stagedDataSp.getAll().size() != 1
                || stagedDataSp.getLong("staged_data_time", -1L) == -1) {
            return;
        }
        deleteStagedDataLocked(i);
    }

    public final File getArchivedPackagesFile() {
        File file = this.mArchivedPackagesFile;
        return file == null
                ? new File(Environment.getDataSystemDeDirectory(0), "ArchivedPackagesPrefs.xml")
                : file;
    }

    public final SharedPreferences getArchivedPackagesSp(File file) {
        return this.mArchivedPackagesFile == null
                ? this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(file, 0)
                : this.mContext.getSharedPreferences(file, 0);
    }

    public final SharedPreferences getStagedDataSp(int i) {
        SparseArray sparseArray = this.mStagedDataFiles;
        if (sparseArray != null) {
            return this.mContext.getSharedPreferences((File) sparseArray.get(i), 0);
        }
        Context createDeviceProtectedStorageContext =
                this.mContext.createDeviceProtectedStorageContext();
        SparseArray sparseArray2 = this.mStagedDataFiles;
        return createDeviceProtectedStorageContext.getSharedPreferences(
                sparseArray2 == null
                        ? new File(
                                Environment.getDataSystemDeDirectory(i),
                                "LocalesStagedDataPrefs.xml")
                        : (File) sparseArray2.get(i),
                0);
    }

    public BroadcastReceiver getUserMonitor() {
        return this.mUserMonitor;
    }

    public final void persistLocalesModificationInfo(String str, boolean z, boolean z2, int i) {
        SharedPreferences sharedPreferences = this.mDelegateAppLocalePackages;
        if (sharedPreferences == null) {
            Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String num = Integer.toString(i);
        ArraySet arraySet =
                new ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new ArraySet()));
        if (!z || z2) {
            if (arraySet.contains(str)) {
                arraySet.remove(str);
                edit.putStringSet(num, arraySet);
            }
        } else if (!arraySet.contains(str)) {
            arraySet.add(str);
            edit.putStringSet(num, arraySet);
        }
        if (edit.commit()) {
            return;
        }
        Slog.e("LocaleManagerBkpHelper", "failed to commit locale setter info");
    }

    public final void removeFromArchivedPackagesInfo(int i, String str) {
        File archivedPackagesFile = getArchivedPackagesFile();
        if (archivedPackagesFile.exists()) {
            String num = Integer.toString(i);
            SharedPreferences archivedPackagesSp = getArchivedPackagesSp(getArchivedPackagesFile());
            ArraySet arraySet = new ArraySet(archivedPackagesSp.getStringSet(num, new ArraySet()));
            if (arraySet.remove(str)) {
                SharedPreferences.Editor edit = archivedPackagesSp.edit();
                if (!arraySet.isEmpty()) {
                    if (edit.putStringSet(num, arraySet).commit()) {
                        return;
                    }
                    Slog.e("LocaleManagerBkpHelper", "failed to remove the package");
                } else {
                    if (!edit.remove(num).commit()) {
                        Slog.e("LocaleManagerBkpHelper", "Failed to remove user");
                    }
                    if (archivedPackagesSp.getAll().isEmpty()) {
                        archivedPackagesFile.delete();
                    }
                }
            }
        }
    }

    public final void removePackageFromPersistedInfo(int i, String str) {
        if (this.mDelegateAppLocalePackages == null) {
            Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
            return;
        }
        String num = Integer.toString(i);
        ArraySet arraySet =
                new ArraySet(this.mDelegateAppLocalePackages.getStringSet(num, new ArraySet()));
        if (arraySet.contains(str)) {
            arraySet.remove(str);
            SharedPreferences.Editor edit = this.mDelegateAppLocalePackages.edit();
            edit.putStringSet(num, arraySet);
            if (edit.commit()) {
                return;
            }
            Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
        }
    }

    public final void storeStagedDataInfo(int i, LocalesInfo localesInfo, String str) {
        if (getStagedDataSp(i)
                .edit()
                .putString(
                        str,
                        localesInfo.mLocales + " s:" + String.valueOf(localesInfo.mSetFromDelegate))
                .commit()) {
            return;
        }
        Slog.e("LocaleManagerBkpHelper", "Failed to commit data!");
    }
}
