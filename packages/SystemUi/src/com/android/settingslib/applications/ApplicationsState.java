package com.android.settingslib.applications;

import android.app.ActivityManager;
import android.app.Application;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.hardware.usb.IUsbManager;
import android.icu.text.DecimalFormat;
import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.SparseArray;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.settingslib.applications.ApplicationsState;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.applications.AppFileSizeFormatter;
import com.samsung.android.settingslib.applications.cachedb.AppListCachePackageData;
import com.samsung.android.settingslib.applications.cachedb.AppListCacheProviderContract;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ApplicationsState {
    public static final C08831 ALPHA_COMPARATOR;
    public static final C089823 FILTER_AUDIO;
    public static final C088410 FILTER_DOWNLOADED_AND_LAUNCHER;
    public static final C089722 FILTER_GAMES;
    public static final C089924 FILTER_MOVIES;
    public static final C090025 FILTER_PHOTOS;
    public static HashMap mAppLabelCache;
    public static AppWidgetManager mAppWidgetManager;
    public static final ArrayList mNewAppListForAppLabelCache;
    public static PackageManager mPm;
    public static UsageStatsManager mUsageStatsManager;
    public static IUsbManager mUsbManager;
    static ApplicationsState sInstance;
    public static final Object sLock = new Object();
    public final int mAdminRetrieveFlags;
    public long mAppLoadStartTime;
    public final BackgroundHandler mBackgroundHandler;
    public PackageIntentReceiver mClonePackageIntentReceiver;
    public final Context mContext;
    public String mCurComputingSizePkg;
    public int mCurComputingSizeUserId;
    public UUID mCurComputingSizeUuid;
    public final IconDrawableFactory mDrawableFactory;
    public boolean mHaveDisabledApps;
    public final IPackageManager mIpm;
    public PackageIntentReceiver mPackageIntentReceiver;
    public boolean mResumed;
    public final int mRetrieveFlags;
    public boolean mSessionsChanged;
    public final StorageStatsManager mStats;
    public final UserManager mUm;
    public final int mWorkUserId = -10000;
    public String mRefreshCandidatePkgName = "";
    public final ArrayList mSessions = new ArrayList();
    public final ArrayList mRebuildingSessions = new ArrayList();
    public InterestingConfigChanges mInterestingConfigChanges = new InterestingConfigChanges();
    public final SparseArray mEntriesMap = new SparseArray();
    public final ArrayList mAppEntries = new ArrayList();
    public List mApplications = new ArrayList();
    public long mCurId = 1;
    public final HashMap mSystemModules = new HashMap();
    public final ArrayList mActiveSessions = new ArrayList();
    public final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.applications.ApplicationsState$1 */
    public final class C08831 implements Comparator {
        public final Collator sCollator = Collator.getInstance();

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ApplicationInfo applicationInfo;
            int compare;
            AppEntry appEntry = (AppEntry) obj;
            AppEntry appEntry2 = (AppEntry) obj2;
            int compare2 = this.sCollator.compare(appEntry.label, appEntry2.label);
            if (compare2 != 0) {
                return compare2;
            }
            ApplicationInfo applicationInfo2 = appEntry.info;
            return (applicationInfo2 == null || (applicationInfo = appEntry2.info) == null || (compare = this.sCollator.compare(applicationInfo2.packageName, applicationInfo.packageName)) == 0) ? appEntry.info.uid - appEntry2.info.uid : compare;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppEntry extends SizeInfo {
        public File apkFile;
        public long externalSize;
        public boolean hasLauncherEntry;
        public Drawable icon;

        /* renamed from: id */
        public final long f218id;
        public ApplicationInfo info;
        public long internalSize;
        public boolean isHomeApp;
        public String label;
        public boolean mounted;
        public long size;
        public long sizeLoadStart;
        public boolean sizeStale;

        public AppEntry(Context context, ApplicationInfo applicationInfo, long j) {
            if (applicationInfo == null) {
                this.apkFile = null;
            } else if (applicationInfo.sourceDir == null) {
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("SRC null : "), applicationInfo.packageName, "ApplicationsState");
                this.apkFile = null;
            } else {
                this.apkFile = new File(applicationInfo.sourceDir);
            }
            this.f218id = j;
            this.info = applicationInfo;
            this.size = -1L;
            this.sizeStale = true;
            ensureLabel(context);
            UserManager userManager = UserManager.get(context);
            if (applicationInfo != null) {
                shouldShowInPersonalTab(userManager, applicationInfo.uid);
                UserInfo userInfo = userManager.getUserInfo(UserHandle.getUserId(applicationInfo.uid));
                if (userInfo != null) {
                    userInfo.isCloneProfile();
                }
            }
        }

        public final boolean ensureIconLocked(Context context, IconDrawableFactory iconDrawableFactory) {
            File file;
            Object obj = ApplicationsState.sLock;
            if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(context.getPackageName())) {
                return false;
            }
            if (this.icon == null) {
                File file2 = this.apkFile;
                if (file2 != null && file2.exists()) {
                    this.icon = iconDrawableFactory.getBadgedIcon(this.info);
                    return true;
                }
                this.mounted = false;
                this.icon = context.getDrawable(17304333);
            } else if (!this.mounted && (file = this.apkFile) != null && file.exists()) {
                this.mounted = true;
                this.icon = iconDrawableFactory.getBadgedIcon(this.info);
                return true;
            }
            return false;
        }

        public final void ensureLabel(Context context) {
            if (this.f218id == -1) {
                this.label = "\u200b";
                return;
            }
            if (this.label == null || !this.mounted) {
                File file = this.apkFile;
                if (file != null && !file.exists()) {
                    this.mounted = false;
                    this.label = this.info.packageName;
                    return;
                }
                boolean z = true;
                this.mounted = true;
                HashMap hashMap = ApplicationsState.mAppLabelCache;
                CharSequence charSequence = null;
                if (hashMap != null) {
                    AppListCachePackageData appListCachePackageData = (AppListCachePackageData) hashMap.get(this.info.packageName);
                    if (appListCachePackageData != null) {
                        try {
                            if (this.info.sourceDir != null) {
                                if (appListCachePackageData.lastUpdateTime == new File(this.info.sourceDir).lastModified()) {
                                    charSequence = appListCachePackageData.label;
                                } else {
                                    Log.i("ApplicationsState", this.info.packageName + " updated recently");
                                }
                            }
                        } catch (Exception e) {
                            Log.e("ApplicationsState", e.getMessage());
                        }
                    }
                    if (this.info.sourceDir == null) {
                        Log.i("ApplicationsState", this.info.packageName + " info.sourceDir == null");
                    }
                }
                if (TextUtils.isEmpty(charSequence)) {
                    charSequence = this.info.loadLabel(context.getPackageManager());
                } else {
                    z = false;
                }
                if (ApplicationsState.mAppLabelCache != null && z) {
                    Log.i("ApplicationsState", "mNewAppListForAppLabelCache.add : " + this.info.packageName);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("package_name", this.info.packageName);
                    contentValues.put("app_title", charSequence.toString());
                    try {
                        if (this.info.sourceDir != null) {
                            contentValues.put("last_updated", Long.valueOf(new File(this.info.sourceDir).lastModified()));
                        } else {
                            contentValues.put("last_updated", (Integer) 0);
                        }
                    } catch (Exception unused) {
                        contentValues.put("last_updated", (Integer) 0);
                    }
                    ApplicationsState.mNewAppListForAppLabelCache.add(contentValues);
                }
                this.label = charSequence != null ? charSequence.toString() : this.info.packageName;
            }
        }

        public boolean shouldShowInPersonalTab(UserManager userManager, int i) {
            int userId = UserHandle.getUserId(i);
            return userId == ActivityManager.getCurrentUser() || userManager.getUserProperties(UserHandle.of(userId)).getShowInSettings() == 0;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BackgroundHandler extends Handler {
        public int mIconLoaded;
        public boolean mRunning;
        public final C09111 mStatsObserver;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.settingslib.applications.ApplicationsState$BackgroundHandler$1 */
        public final class C09111 extends IPackageStatsObserver.Stub {
            public C09111() {
            }

            public final void onGetStatsCompleted(PackageStats packageStats, boolean z) {
                PackageStats packageStats2;
                long j;
                boolean z2;
                C09111 c09111 = this;
                if (z) {
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        HashMap hashMap = (HashMap) ApplicationsState.this.mEntriesMap.get(packageStats.userHandle);
                        if (hashMap == null) {
                            return;
                        }
                        AppEntry appEntry = (AppEntry) hashMap.get(packageStats.packageName);
                        if (appEntry != null) {
                            synchronized (appEntry) {
                                appEntry.sizeStale = false;
                                appEntry.sizeLoadStart = 0L;
                                long j2 = packageStats.externalCodeSize + packageStats.externalObbSize;
                                long j3 = packageStats.externalDataSize + packageStats.externalMediaSize;
                                ApplicationsState.this.getClass();
                                long j4 = packageStats.codeSize;
                                long j5 = packageStats.dataSize;
                                long j6 = packageStats.cacheSize;
                                long j7 = j2 + j3 + ((j4 + j5) - j6);
                                if (appEntry.size == j7 && appEntry.cacheSize == j6 && appEntry.codeSize == j4 && appEntry.dataSize == j5 && appEntry.externalCodeSize == j2 && appEntry.externalDataSize == j3) {
                                    packageStats2 = packageStats;
                                    j = j3;
                                    if (appEntry.externalCacheSize == packageStats2.externalCacheSize) {
                                        z2 = false;
                                        c09111 = this;
                                    }
                                } else {
                                    packageStats2 = packageStats;
                                    j = j3;
                                }
                                appEntry.size = j7;
                                appEntry.cacheSize = j6;
                                appEntry.codeSize = j4;
                                appEntry.dataSize = j5;
                                appEntry.externalCodeSize = j2;
                                appEntry.externalDataSize = j;
                                appEntry.externalCacheSize = packageStats2.externalCacheSize;
                                c09111 = this;
                                ApplicationsState.m366$$Nest$mgetSizeStr(ApplicationsState.this, j7);
                                ApplicationsState.this.getClass();
                                long j8 = (packageStats2.codeSize + packageStats2.dataSize) - packageStats2.cacheSize;
                                appEntry.internalSize = j8;
                                ApplicationsState.m366$$Nest$mgetSizeStr(ApplicationsState.this, j8);
                                ApplicationsState.this.getClass();
                                long j9 = packageStats2.externalCodeSize + packageStats2.externalDataSize + packageStats2.externalCacheSize + packageStats2.externalMediaSize + packageStats2.externalObbSize;
                                appEntry.externalSize = j9;
                                ApplicationsState.m366$$Nest$mgetSizeStr(ApplicationsState.this, j9);
                                z2 = true;
                            }
                            if (z2) {
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(4, packageStats2.packageName));
                            }
                        } else {
                            packageStats2 = packageStats;
                        }
                        String str = ApplicationsState.this.mCurComputingSizePkg;
                        if (str != null && str.equals(packageStats2.packageName)) {
                            BackgroundHandler backgroundHandler = BackgroundHandler.this;
                            ApplicationsState applicationsState = ApplicationsState.this;
                            if (applicationsState.mCurComputingSizeUserId == packageStats2.userHandle) {
                                applicationsState.mCurComputingSizePkg = null;
                                backgroundHandler.sendEmptyMessage(7);
                            }
                        }
                    }
                }
            }
        }

        public BackgroundHandler(Looper looper) {
            super(looper);
            this.mStatsObserver = new C09111();
        }

        public final int getCombinedSessionFlags(List list) {
            int i;
            synchronized (ApplicationsState.this.mEntriesMap) {
                Iterator it = list.iterator();
                i = 0;
                while (it.hasNext()) {
                    i |= ((Session) it.next()).mFlags;
                }
            }
            return i;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList arrayList;
            int i;
            int i2;
            boolean z;
            synchronized (ApplicationsState.this.mRebuildingSessions) {
                if (ApplicationsState.this.mRebuildingSessions.size() > 0) {
                    arrayList = new ArrayList(ApplicationsState.this.mRebuildingSessions);
                    ApplicationsState.this.mRebuildingSessions.clear();
                } else {
                    arrayList = null;
                }
            }
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((Session) it.next()).handleRebuildList();
                }
            }
            int combinedSessionFlags = getCombinedSessionFlags(ApplicationsState.this.mSessions);
            int i3 = message.what;
            int i4 = 0;
            boolean z2 = true;
            switch (i3) {
                case 2:
                    synchronized (ApplicationsState.this.mEntriesMap) {
                        i = 0;
                        for (int i5 = 0; i5 < ((ArrayList) ApplicationsState.this.mApplications).size() && i < 6; i5++) {
                            if (!this.mRunning) {
                                this.mRunning = true;
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                            }
                            ApplicationInfo applicationInfo = (ApplicationInfo) ((ArrayList) ApplicationsState.this.mApplications).get(i5);
                            int userId = UserHandle.getUserId(applicationInfo.uid);
                            if (ApplicationsState.this.mEntriesMap.get(userId) == null || ((HashMap) ApplicationsState.this.mEntriesMap.get(userId)).get(applicationInfo.packageName) != null) {
                                try {
                                    AppEntry appEntry = (AppEntry) ((HashMap) ApplicationsState.this.mEntriesMap.get(userId)).get(applicationInfo.packageName);
                                    if (!appEntry.apkFile.getAbsolutePath().equals(applicationInfo.sourceDir)) {
                                        appEntry.apkFile = new File(applicationInfo.sourceDir);
                                        Log.i("ApplicationsState", "MSG_LOAD_ENTRIES Update info.apkFile : " + applicationInfo.packageName + " , " + applicationInfo.sourceDir);
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                i++;
                                ApplicationsState.m365$$Nest$mgetEntryLocked(ApplicationsState.this, applicationInfo);
                            }
                            if (userId != 0) {
                                if (ApplicationsState.this.mEntriesMap.indexOfKey(0) >= 0) {
                                    AppEntry appEntry2 = (AppEntry) ((HashMap) ApplicationsState.this.mEntriesMap.get(0)).get(applicationInfo.packageName);
                                    if (appEntry2 != null && !ApplicationsState.hasFlag(appEntry2.info.flags, QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) {
                                        ((HashMap) ApplicationsState.this.mEntriesMap.get(0)).remove(applicationInfo.packageName);
                                        ApplicationsState.this.mAppEntries.remove(appEntry2);
                                    }
                                }
                            }
                        }
                    }
                    if (i >= 6) {
                        sendEmptyMessage(2);
                        return;
                    }
                    Log.i("ApplicationsState", "MSG_LOAD_ENTRIES took : " + (System.currentTimeMillis() - ApplicationsState.this.mAppLoadStartTime));
                    if (!ApplicationsState.this.mMainHandler.hasMessages(8)) {
                        ApplicationsState.this.mMainHandler.sendEmptyMessage(8);
                    }
                    sendEmptyMessage(3);
                    return;
                case 3:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 1)) {
                        ArrayList arrayList2 = new ArrayList();
                        ApplicationsState.mPm.getHomeActivities(arrayList2);
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            int size = ApplicationsState.this.mEntriesMap.size();
                            for (int i6 = 0; i6 < size; i6++) {
                                HashMap hashMap = (HashMap) ApplicationsState.this.mEntriesMap.valueAt(i6);
                                Iterator it2 = arrayList2.iterator();
                                while (it2.hasNext()) {
                                    AppEntry appEntry3 = (AppEntry) hashMap.get(((ResolveInfo) it2.next()).activityInfo.packageName);
                                    if (appEntry3 != null) {
                                        appEntry3.isHomeApp = true;
                                    }
                                }
                            }
                        }
                    }
                    sendEmptyMessage(4);
                    return;
                case 4:
                case 5:
                    if ((i3 == 4 && ApplicationsState.hasFlag(combinedSessionFlags, 8)) || (message.what == 5 && ApplicationsState.hasFlag(combinedSessionFlags, 16))) {
                        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                        intent.addCategory(message.what == 4 ? "android.intent.category.LAUNCHER" : "android.intent.category.LEANBACK_LAUNCHER");
                        int i7 = 0;
                        while (i7 < ApplicationsState.this.mEntriesMap.size()) {
                            int keyAt = ApplicationsState.this.mEntriesMap.keyAt(i7);
                            List queryIntentActivitiesAsUser = ApplicationsState.mPm.queryIntentActivitiesAsUser(intent, 786944, keyAt);
                            synchronized (ApplicationsState.this.mEntriesMap) {
                                HashMap hashMap2 = (HashMap) ApplicationsState.this.mEntriesMap.valueAt(i7);
                                int size2 = queryIntentActivitiesAsUser.size();
                                int i8 = i4;
                                while (i8 < size2) {
                                    ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesAsUser.get(i8);
                                    String str = resolveInfo.activityInfo.packageName;
                                    AppEntry appEntry4 = (AppEntry) hashMap2.get(str);
                                    if (appEntry4 != null) {
                                        appEntry4.hasLauncherEntry = z2;
                                        boolean z3 = resolveInfo.activityInfo.enabled;
                                    } else {
                                        Log.w("ApplicationsState", "Cannot find pkg: " + str + " on user " + keyAt);
                                    }
                                    i8++;
                                    z2 = true;
                                }
                            }
                            i7++;
                            i4 = 0;
                            z2 = true;
                        }
                        if (!ApplicationsState.this.mMainHandler.hasMessages(7)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(7);
                        }
                    }
                    if (message.what == 4) {
                        sendEmptyMessage(5);
                        return;
                    } else {
                        this.mIconLoaded = 0;
                        sendEmptyMessage(6);
                        return;
                    }
                case 6:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 2)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            i2 = 0;
                            while (i4 < ApplicationsState.this.mAppEntries.size() && i2 < 2) {
                                AppEntry appEntry5 = (AppEntry) ApplicationsState.this.mAppEntries.get(i4);
                                if (appEntry5.icon == null || !appEntry5.mounted) {
                                    synchronized (appEntry5) {
                                        ApplicationsState applicationsState = ApplicationsState.this;
                                        if (appEntry5.ensureIconLocked(applicationsState.mContext, applicationsState.mDrawableFactory)) {
                                            if (!this.mRunning) {
                                                this.mRunning = true;
                                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                            }
                                            i2++;
                                        }
                                    }
                                }
                                i4++;
                            }
                        }
                        this.mIconLoaded += i2;
                        if (i2 >= 2) {
                            sendEmptyMessage(6);
                            return;
                        }
                    }
                    if (this.mIconLoaded > 0 && !ApplicationsState.this.mMainHandler.hasMessages(3)) {
                        ApplicationsState.this.mMainHandler.sendEmptyMessage(3);
                    }
                    sendEmptyMessage(7);
                    return;
                case 7:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 4)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            if (ApplicationsState.this.mCurComputingSizePkg != null) {
                                return;
                            }
                            long uptimeMillis = SystemClock.uptimeMillis();
                            for (int i9 = 0; i9 < ApplicationsState.this.mAppEntries.size(); i9++) {
                                AppEntry appEntry6 = (AppEntry) ApplicationsState.this.mAppEntries.get(i9);
                                if (ApplicationsState.hasFlag(appEntry6.info.flags, QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) && (appEntry6.size == -1 || appEntry6.sizeStale)) {
                                    long j = appEntry6.sizeLoadStart;
                                    if (j == 0 || j < uptimeMillis - 20000) {
                                        if (!this.mRunning) {
                                            this.mRunning = true;
                                            ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 1));
                                        }
                                        appEntry6.sizeLoadStart = uptimeMillis;
                                        ApplicationsState applicationsState2 = ApplicationsState.this;
                                        ApplicationInfo applicationInfo2 = appEntry6.info;
                                        applicationsState2.mCurComputingSizeUuid = applicationInfo2.storageUuid;
                                        applicationsState2.mCurComputingSizePkg = applicationInfo2.packageName;
                                        applicationsState2.mCurComputingSizeUserId = UserHandle.getUserId(applicationInfo2.uid);
                                        ApplicationsState.this.mBackgroundHandler.post(new Runnable() { // from class: com.android.settingslib.applications.ApplicationsState$BackgroundHandler$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ApplicationsState.BackgroundHandler backgroundHandler = ApplicationsState.BackgroundHandler.this;
                                                backgroundHandler.getClass();
                                                try {
                                                    try {
                                                        ApplicationsState applicationsState3 = ApplicationsState.this;
                                                        StorageStats queryStatsForPackage = applicationsState3.mStats.queryStatsForPackage(applicationsState3.mCurComputingSizeUuid, applicationsState3.mCurComputingSizePkg, UserHandle.of(applicationsState3.mCurComputingSizeUserId));
                                                        ApplicationsState applicationsState4 = ApplicationsState.this;
                                                        PackageStats packageStats = new PackageStats(applicationsState4.mCurComputingSizePkg, applicationsState4.mCurComputingSizeUserId);
                                                        packageStats.codeSize = queryStatsForPackage.getAppBytes();
                                                        packageStats.dataSize = queryStatsForPackage.getDataBytes();
                                                        packageStats.cacheSize = queryStatsForPackage.getCacheBytes();
                                                        backgroundHandler.mStatsObserver.onGetStatsCompleted(packageStats, true);
                                                    } catch (PackageManager.NameNotFoundException | IOException | NullPointerException e2) {
                                                        Log.w("ApplicationsState", "Failed to query stats: " + e2);
                                                        backgroundHandler.mStatsObserver.getClass();
                                                    }
                                                } catch (RemoteException unused) {
                                                }
                                            }
                                        });
                                    }
                                    return;
                                }
                            }
                            if (!ApplicationsState.this.mMainHandler.hasMessages(5)) {
                                ApplicationsState.this.mMainHandler.sendEmptyMessage(5);
                                this.mRunning = false;
                                ApplicationsState.this.mMainHandler.sendMessage(ApplicationsState.this.mMainHandler.obtainMessage(6, 0));
                            }
                        }
                    }
                    ApplicationsState applicationsState3 = ApplicationsState.this;
                    System.currentTimeMillis();
                    applicationsState3.getClass();
                    sendEmptyMessage(8);
                    return;
                case 8:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 32)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(2, -6);
                            Map<String, UsageStats> queryAndAggregateUsageStats = ApplicationsState.mUsageStatsManager.queryAndAggregateUsageStats(calendar.getTimeInMillis(), System.currentTimeMillis());
                            while (i4 < ApplicationsState.this.mAppEntries.size()) {
                                AppEntry appEntry7 = (AppEntry) ApplicationsState.this.mAppEntries.get(i4);
                                UsageStats usageStats = queryAndAggregateUsageStats.get(appEntry7.info.packageName);
                                if (usageStats != null) {
                                    appEntry7.lastUsed = usageStats.getLastTimeUsed();
                                }
                                i4++;
                            }
                        }
                        if (!ApplicationsState.this.mMainHandler.hasMessages(9)) {
                            ApplicationsState.this.mMainHandler.sendEmptyMessage(9);
                        }
                    }
                    sendEmptyMessage(9);
                    return;
                case 9:
                    if (ApplicationsState.hasFlag(combinedSessionFlags, 32)) {
                        synchronized (ApplicationsState.this.mEntriesMap) {
                            z = false;
                            while (i4 < ApplicationsState.this.mAppEntries.size()) {
                                AppEntry appEntry8 = (AppEntry) ApplicationsState.this.mAppEntries.get(i4);
                                String str2 = appEntry8.info.sourceDir;
                                if (!TextUtils.isEmpty(str2)) {
                                    long lastModified = new File(str2).lastModified();
                                    if (lastModified != appEntry8.lastUpdated) {
                                        appEntry8.lastUpdated = lastModified;
                                        z = true;
                                    }
                                }
                                i4++;
                            }
                        }
                        if (!z || ApplicationsState.this.mMainHandler.hasMessages(10)) {
                            return;
                        }
                        ApplicationsState.this.mMainHandler.sendEmptyMessage(10);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callbacks {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            ApplicationsState applicationsState = ApplicationsState.this;
            synchronized (applicationsState.mEntriesMap) {
                i = 0;
                if (applicationsState.mSessionsChanged) {
                    applicationsState.mActiveSessions.clear();
                    for (int i2 = 0; i2 < applicationsState.mSessions.size(); i2++) {
                        Session session = (Session) applicationsState.mSessions.get(i2);
                        if (session.mResumed) {
                            applicationsState.mActiveSessions.add(new WeakReference(session));
                        }
                    }
                }
            }
            switch (message.what) {
                case 1:
                    ArrayList arrayList = ApplicationsState.mNewAppListForAppLabelCache;
                    if (!arrayList.isEmpty()) {
                        ApplicationsState applicationsState2 = ApplicationsState.this;
                        applicationsState2.getClass();
                        if (arrayList.isEmpty()) {
                            Log.e("ApplicationsState", "bulkInsertAppLabelList : contentValuesList is empty");
                        } else {
                            Log.d("ApplicationsState", "bulkInsertAppLabelList : build count = " + arrayList.size());
                            ContentValues[] contentValuesArr = (ContentValues[]) arrayList.toArray(new ContentValues[arrayList.size()]);
                            arrayList.clear();
                            if (contentValuesArr.length == 0) {
                                Log.e("ApplicationsState", "bulkInsertAppLabelList : contentValuesArray is empty");
                            } else {
                                try {
                                    Log.d("ApplicationsState", "bulkInsertAppLabelList : insert count = " + applicationsState2.mContext.getContentResolver().bulkInsert(AppListCacheProviderContract.URI_APP_LIST, contentValuesArr));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if (UserHandle.myUserId() == 0) {
                        ApplicationsState applicationsState3 = ApplicationsState.this;
                        applicationsState3.getClass();
                        HashSet hashSet = new HashSet();
                        while (true) {
                            ArrayList arrayList2 = applicationsState3.mAppEntries;
                            if (i < arrayList2.size()) {
                                hashSet.add(((AppEntry) arrayList2.get(i)).info.packageName);
                                i++;
                            } else if (((ArrayList) applicationsState3.mApplications).size() != hashSet.size()) {
                                Log.i("ApplicationsState", "List is not matched size, so skip removeRemovedAppLabelList()");
                            } else {
                                ArrayList arrayList3 = new ArrayList();
                                Iterator it = ApplicationsState.mAppLabelCache.entrySet().iterator();
                                while (it.hasNext()) {
                                    AppListCachePackageData appListCachePackageData = (AppListCachePackageData) ((Map.Entry) it.next()).getValue();
                                    if (!hashSet.contains(appListCachePackageData.packageName)) {
                                        Log.i("ApplicationsState", appListCachePackageData.packageName + " maybe deleted, so remove from applist label cache");
                                        arrayList3.add(appListCachePackageData.packageName);
                                    }
                                }
                                ArrayList<ContentProviderOperation> arrayList4 = new ArrayList<>();
                                Iterator it2 = arrayList3.iterator();
                                while (it2.hasNext()) {
                                    arrayList4.add(ContentProviderOperation.newDelete(Uri.withAppendedPath(AppListCacheProviderContract.URI_APP_LIST, (String) it2.next())).build());
                                }
                                try {
                                    applicationsState3.mContext.getContentResolver().applyBatch("com.samsung.android.settings.applist", arrayList4);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
                    Session session2 = (Session) message.obj;
                    Iterator it3 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it3.hasNext()) {
                        Session session3 = (Session) ((WeakReference) it3.next()).get();
                        if (session3 != null && session3 == session2) {
                            session2.getClass();
                            throw null;
                        }
                    }
                    return;
                case 2:
                    Iterator it4 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it4.hasNext()) {
                        if (((Session) ((WeakReference) it4.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 3:
                    Iterator it5 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it5.hasNext()) {
                        if (((Session) ((WeakReference) it5.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 4:
                    Iterator it6 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it6.hasNext()) {
                        if (((Session) ((WeakReference) it6.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 5:
                    Iterator it7 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it7.hasNext()) {
                        if (((Session) ((WeakReference) it7.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 6:
                    Iterator it8 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it8.hasNext()) {
                        if (((Session) ((WeakReference) it8.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 7:
                    Iterator it9 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it9.hasNext()) {
                        if (((Session) ((WeakReference) it9.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 8:
                    Iterator it10 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it10.hasNext()) {
                        if (((Session) ((WeakReference) it10.next()).get()) != null) {
                            throw null;
                        }
                    }
                    return;
                case 9:
                    Iterator it11 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it11.hasNext()) {
                        ((Session) ((WeakReference) it11.next()).get()).getClass();
                    }
                    return;
                case 10:
                    Iterator it12 = ApplicationsState.this.mActiveSessions.iterator();
                    while (it12.hasNext()) {
                        ((Session) ((WeakReference) it12.next()).get()).getClass();
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PackageIntentReceiver extends BroadcastReceiver {
        public /* synthetic */ PackageIntentReceiver(ApplicationsState applicationsState, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = 0;
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.addPackage(applicationsState.mEntriesMap.keyAt(i), encodedSchemeSpecificPart);
                    i++;
                }
                return;
            }
            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                String encodedSchemeSpecificPart2 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    applicationsState2.removePackage(applicationsState2.mEntriesMap.keyAt(i), encodedSchemeSpecificPart2);
                    i++;
                }
                return;
            }
            if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                String encodedSchemeSpecificPart3 = intent.getData().getEncodedSchemeSpecificPart();
                while (i < ApplicationsState.this.mEntriesMap.size()) {
                    ApplicationsState applicationsState3 = ApplicationsState.this;
                    int keyAt = applicationsState3.mEntriesMap.keyAt(i);
                    applicationsState3.removePackage(keyAt, encodedSchemeSpecificPart3);
                    applicationsState3.addPackage(keyAt, encodedSchemeSpecificPart3);
                    i++;
                }
                return;
            }
            if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action) || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (stringArrayExtra == null || stringArrayExtra.length == 0 || !"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                    return;
                }
                for (String str : stringArrayExtra) {
                    for (int i2 = 0; i2 < ApplicationsState.this.mEntriesMap.size(); i2++) {
                        ApplicationsState applicationsState4 = ApplicationsState.this;
                        int keyAt2 = applicationsState4.mEntriesMap.keyAt(i2);
                        applicationsState4.removePackage(keyAt2, str);
                        applicationsState4.addPackage(keyAt2, str);
                    }
                }
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action)) {
                ApplicationsState applicationsState5 = ApplicationsState.this;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (ArrayUtils.contains(applicationsState5.mUm.getProfileIdsWithDisabled(UserHandle.myUserId()), intExtra)) {
                    synchronized (applicationsState5.mEntriesMap) {
                        applicationsState5.mEntriesMap.put(intExtra, new HashMap());
                        if (applicationsState5.mResumed) {
                            applicationsState5.doPauseLocked();
                            applicationsState5.doResumeIfNeededLocked();
                        }
                        if (!applicationsState5.mMainHandler.hasMessages(2)) {
                            applicationsState5.mMainHandler.sendEmptyMessage(2);
                        }
                    }
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                ApplicationsState applicationsState6 = ApplicationsState.this;
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                synchronized (applicationsState6.mEntriesMap) {
                    HashMap hashMap = (HashMap) applicationsState6.mEntriesMap.get(intExtra2);
                    if (hashMap != null) {
                        for (AppEntry appEntry : hashMap.values()) {
                            applicationsState6.mAppEntries.remove(appEntry);
                            ((ArrayList) applicationsState6.mApplications).remove(appEntry.info);
                        }
                        applicationsState6.mEntriesMap.remove(intExtra2);
                        if (!applicationsState6.mMainHandler.hasMessages(2)) {
                            applicationsState6.mMainHandler.sendEmptyMessage(2);
                        }
                    }
                }
            }
        }

        private PackageIntentReceiver() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Session implements LifecycleObserver {
        public final int mFlags;
        public final boolean mHasLifecycle;
        public final Object mRebuildSync = new Object();
        public boolean mResumed;

        public Session(Callbacks callbacks, Lifecycle lifecycle) {
            this.mFlags = 13;
            if (lifecycle != null) {
                lifecycle.addObserver(this);
                this.mHasLifecycle = true;
            } else {
                this.mHasLifecycle = false;
            }
            if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(ApplicationsState.this.mContext.getPackageName())) {
                this.mFlags = 13;
            }
        }

        public final void handleRebuildList() {
            if (this.mResumed) {
                synchronized (this.mRebuildSync) {
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            if (!this.mHasLifecycle) {
                onPause();
            }
            synchronized (ApplicationsState.this.mEntriesMap) {
                ApplicationsState.this.mSessions.remove(this);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            synchronized (ApplicationsState.this.mEntriesMap) {
                if (this.mResumed) {
                    int i = 0;
                    this.mResumed = false;
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.mBackgroundHandler.removeMessages(1, this);
                    ApplicationsState applicationsState2 = ApplicationsState.this;
                    if (applicationsState2.mResumed) {
                        while (true) {
                            ArrayList arrayList = applicationsState2.mSessions;
                            if (i >= arrayList.size()) {
                                applicationsState2.doPauseLocked();
                                break;
                            } else if (((Session) arrayList.get(i)).mResumed) {
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            synchronized (ApplicationsState.this.mEntriesMap) {
                if (!this.mResumed) {
                    this.mResumed = true;
                    ApplicationsState applicationsState = ApplicationsState.this;
                    applicationsState.mSessionsChanged = true;
                    applicationsState.doPauseLocked();
                    ApplicationsState.this.doResumeIfNeededLocked();
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class SizeInfo {
        public long cacheSize;
        public long codeSize;
        public long dataSize;
        public long externalCacheSize;
        public long externalCodeSize;
        public long externalDataSize;
        public long lastUpdated;
        public long lastUsed;
    }

    /* renamed from: -$$Nest$mgetEntryLocked, reason: not valid java name */
    public static void m365$$Nest$mgetEntryLocked(ApplicationsState applicationsState, ApplicationInfo applicationInfo) {
        applicationsState.getClass();
        int userId = UserHandle.getUserId(applicationInfo.uid);
        SparseArray sparseArray = applicationsState.mEntriesMap;
        HashMap hashMap = (HashMap) sparseArray.get(userId);
        AppEntry appEntry = hashMap != null ? (AppEntry) hashMap.get(applicationInfo.packageName) : null;
        if (appEntry != null) {
            if (appEntry.info != applicationInfo) {
                appEntry.info = applicationInfo;
                return;
            }
            return;
        }
        Boolean bool = (Boolean) applicationsState.mSystemModules.get(applicationInfo.packageName);
        if (bool == null ? false : bool.booleanValue()) {
            return;
        }
        Context context = applicationsState.mContext;
        long j = applicationsState.mCurId;
        applicationsState.mCurId = 1 + j;
        AppEntry appEntry2 = new AppEntry(context, applicationInfo, j);
        HashMap hashMap2 = (HashMap) sparseArray.get(userId);
        if (hashMap2 != null) {
            hashMap2.put(applicationInfo.packageName, appEntry2);
            applicationsState.mAppEntries.add(appEntry2);
        }
    }

    /* renamed from: -$$Nest$mgetSizeStr, reason: not valid java name */
    public static void m366$$Nest$mgetSizeStr(ApplicationsState applicationsState, long j) {
        String format;
        if (j < 0) {
            applicationsState.getClass();
            return;
        }
        Context context = applicationsState.mContext;
        MeasureUnit measureUnit = AppFileSizeFormatter.PETABYTE;
        if (context == null) {
            return;
        }
        AppFileSizeFormatter.RoundedBytesResult roundBytes = AppFileSizeFormatter.RoundedBytesResult.roundBytes(j);
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        int i = roundBytes.fractionDigits;
        numberFormat.setMinimumFractionDigits(i);
        numberFormat.setMaximumFractionDigits(i);
        numberFormat.setGroupingUsed(false);
        if (numberFormat instanceof DecimalFormat) {
            numberFormat.setRoundingMode(4);
        }
        MeasureUnit measureUnit2 = MeasureUnit.BYTE;
        MeasureUnit measureUnit3 = roundBytes.units;
        boolean z = measureUnit3 == measureUnit2 || measureUnit3 == MeasureUnit.KILOBYTE || measureUnit3 == MeasureUnit.MEGABYTE || measureUnit3 == MeasureUnit.GIGABYTE || measureUnit3 == MeasureUnit.TERABYTE || measureUnit3 == AppFileSizeFormatter.PETABYTE;
        float f = roundBytes.value;
        if (z) {
            Object[] objArr = new Object[2];
            objArr[0] = numberFormat.format(f);
            Resources resources = context.getResources();
            objArr[1] = measureUnit3 == MeasureUnit.BYTE ? resources.getString(R.string.byteShort) : measureUnit3 == MeasureUnit.KILOBYTE ? resources.getString(R.string.kilobyteShort) : measureUnit3 == MeasureUnit.MEGABYTE ? resources.getString(R.string.megabyteShort) : measureUnit3 == MeasureUnit.GIGABYTE ? resources.getString(R.string.gigabyteShort) : measureUnit3 == MeasureUnit.TERABYTE ? resources.getString(R.string.terabyteShort) : resources.getString(R.string.petabyteShort);
            format = context.getString(android.R.string.kg_reordering_delete_drop_target_text, objArr);
        } else {
            format = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.SHORT, numberFormat).format(new Measure(Float.valueOf(f), measureUnit3));
        }
        if (TextUtils.getLayoutDirectionFromLocale(context.getResources().getConfiguration().getLocales().get(0)) == 1) {
            BidiFormatter.getInstance(true).unicodeWrap(format);
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.settingslib.applications.ApplicationsState$10] */
    /* JADX WARN: Type inference failed for: r0v24, types: [com.android.settingslib.applications.ApplicationsState$22] */
    /* JADX WARN: Type inference failed for: r0v25, types: [com.android.settingslib.applications.ApplicationsState$23] */
    /* JADX WARN: Type inference failed for: r0v26, types: [com.android.settingslib.applications.ApplicationsState$24] */
    /* JADX WARN: Type inference failed for: r0v27, types: [com.android.settingslib.applications.ApplicationsState$25] */
    static {
        Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        mNewAppListForAppLabelCache = new ArrayList();
        ALPHA_COMPARATOR = new C08831();
        new Comparator() { // from class: com.android.settingslib.applications.ApplicationsState.2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppEntry appEntry = (AppEntry) obj;
                AppEntry appEntry2 = (AppEntry) obj2;
                long j = appEntry.size;
                long j2 = appEntry2.size;
                if (j < j2) {
                    return 1;
                }
                if (j > j2) {
                    return -1;
                }
                return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
            }
        };
        new Comparator() { // from class: com.android.settingslib.applications.ApplicationsState.3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppEntry appEntry = (AppEntry) obj;
                AppEntry appEntry2 = (AppEntry) obj2;
                long j = appEntry.internalSize;
                long j2 = appEntry2.internalSize;
                if (j < j2) {
                    return 1;
                }
                if (j > j2) {
                    return -1;
                }
                return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
            }
        };
        new Comparator() { // from class: com.android.settingslib.applications.ApplicationsState.4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppEntry appEntry = (AppEntry) obj;
                AppEntry appEntry2 = (AppEntry) obj2;
                long j = appEntry.externalSize;
                long j2 = appEntry2.externalSize;
                if (j < j2) {
                    return 1;
                }
                if (j > j2) {
                    return -1;
                }
                return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
            }
        };
        new Comparator() { // from class: com.android.settingslib.applications.ApplicationsState.5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppEntry appEntry = (AppEntry) obj;
                AppEntry appEntry2 = (AppEntry) obj2;
                long j = appEntry.lastUsed;
                long j2 = appEntry2.lastUsed;
                if (j < j2) {
                    return 1;
                }
                if (j > j2) {
                    return -1;
                }
                return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
            }
        };
        new Comparator() { // from class: com.android.settingslib.applications.ApplicationsState.6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppEntry appEntry = (AppEntry) obj;
                AppEntry appEntry2 = (AppEntry) obj2;
                long j = appEntry.lastUpdated;
                long j2 = appEntry2.lastUpdated;
                if (j < j2) {
                    return 1;
                }
                if (j > j2) {
                    return -1;
                }
                return ApplicationsState.ALPHA_COMPARATOR.compare(appEntry, appEntry2);
            }
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.7
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.8
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.9
        };
        FILTER_DOWNLOADED_AND_LAUNCHER = new Object() { // from class: com.android.settingslib.applications.ApplicationsState.10
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.11
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.12
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.13
            public final HashMap mRestrictInfoMap = new HashMap();
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.14
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.15
            public final HashMap mRestrictInfoMap = new HashMap();
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.16
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.17
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.18
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.19
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.20
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.21
        };
        FILTER_GAMES = new Object() { // from class: com.android.settingslib.applications.ApplicationsState.22
        };
        FILTER_AUDIO = new Object() { // from class: com.android.settingslib.applications.ApplicationsState.23
        };
        FILTER_MOVIES = new Object() { // from class: com.android.settingslib.applications.ApplicationsState.24
        };
        FILTER_PHOTOS = new Object() { // from class: com.android.settingslib.applications.ApplicationsState.25
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.26
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.27
        };
        new Object() { // from class: com.android.settingslib.applications.ApplicationsState.28
        };
    }

    private ApplicationsState(Application application, IPackageManager iPackageManager) {
        this.mContext = application;
        mPm = application.getPackageManager();
        this.mIpm = iPackageManager;
        UserManager userManager = (UserManager) application.getSystemService(UserManager.class);
        this.mUm = userManager;
        this.mStats = (StorageStatsManager) application.getSystemService(StorageStatsManager.class);
        for (int i : userManager.getProfileIdsWithDisabled(UserHandle.myUserId())) {
            this.mEntriesMap.put(i, new HashMap());
        }
        HandlerThread handlerThread = new HandlerThread("ApplicationsState.Loader");
        handlerThread.start();
        this.mBackgroundHandler = new BackgroundHandler(handlerThread.getLooper());
        this.mAdminRetrieveFlags = 4227712;
        this.mRetrieveFlags = 33408;
        for (ModuleInfo moduleInfo : mPm.getInstalledModules(0)) {
            this.mSystemModules.put(moduleInfo.getPackageName(), Boolean.valueOf(moduleInfo.isHidden()));
        }
        this.mDrawableFactory = IconDrawableFactory.newInstance(this.mContext);
        mUsageStatsManager = (UsageStatsManager) this.mContext.getSystemService("usagestats");
        mAppWidgetManager = AppWidgetManager.getInstance(this.mContext);
        mUsbManager = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
        synchronized (this.mEntriesMap) {
            try {
                this.mEntriesMap.wait(1L);
            } catch (InterruptedException unused) {
            }
        }
    }

    public static ApplicationsState getInstance(Application application, IPackageManager iPackageManager) {
        ApplicationsState applicationsState;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ApplicationsState(application, iPackageManager);
            }
            applicationsState = sInstance;
        }
        return applicationsState;
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public final void addPackage(int i, String str) {
        try {
            synchronized (this.mEntriesMap) {
                if (this.mResumed) {
                    if (indexOfApplicationInfoLocked(i, str) >= 0) {
                        return;
                    }
                    ApplicationInfo applicationInfo = this.mIpm.getApplicationInfo(str, this.mUm.isUserAdmin(i) ? this.mAdminRetrieveFlags : this.mRetrieveFlags, i);
                    if (applicationInfo == null) {
                        return;
                    }
                    if (!applicationInfo.enabled) {
                        if (applicationInfo.enabledSetting != 3) {
                            return;
                        } else {
                            this.mHaveDisabledApps = true;
                        }
                    }
                    if (!this.mHaveDisabledApps && (AppUtils.isAutoDisabled(applicationInfo) || AppUtils.isManualDisabled(applicationInfo))) {
                        this.mHaveDisabledApps = true;
                    }
                    AppUtils.isInstant(applicationInfo);
                    this.mApplications.add(applicationInfo);
                    if (!this.mBackgroundHandler.hasMessages(2)) {
                        this.mBackgroundHandler.sendEmptyMessage(2);
                    }
                    if (!this.mMainHandler.hasMessages(2)) {
                        this.mMainHandler.sendEmptyMessage(2);
                    }
                }
            }
        } catch (RemoteException unused) {
        }
    }

    public void clearEntries() {
        int i = 0;
        while (true) {
            SparseArray sparseArray = this.mEntriesMap;
            if (i >= sparseArray.size()) {
                this.mAppEntries.clear();
                return;
            } else {
                ((HashMap) sparseArray.valueAt(i)).clear();
                i++;
            }
        }
    }

    public final void doPauseLocked() {
        this.mResumed = false;
        PackageIntentReceiver packageIntentReceiver = this.mPackageIntentReceiver;
        if (packageIntentReceiver != null) {
            ApplicationsState.this.mContext.unregisterReceiver(packageIntentReceiver);
            this.mPackageIntentReceiver = null;
        }
        PackageIntentReceiver packageIntentReceiver2 = this.mClonePackageIntentReceiver;
        if (packageIntentReceiver2 != null) {
            ApplicationsState.this.mContext.unregisterReceiver(packageIntentReceiver2);
            this.mClonePackageIntentReceiver = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x0364 A[Catch: all -> 0x033b, TryCatch #4 {, blocks: (B:134:0x0324, B:136:0x032c, B:138:0x0344, B:140:0x034c, B:141:0x035c, B:143:0x0364, B:144:0x0369, B:145:0x036a, B:149:0x033e), top: B:133:0x0324, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doResumeIfNeededLocked() {
        boolean z;
        int i;
        AppEntry appEntry;
        boolean z2;
        int i2;
        boolean z3;
        int i3;
        if (this.mResumed) {
            return;
        }
        this.mResumed = true;
        int i4 = 0;
        if (this.mPackageIntentReceiver == null) {
            PackageIntentReceiver packageIntentReceiver = new PackageIntentReceiver(this, i4);
            this.mPackageIntentReceiver = packageIntentReceiver;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            ApplicationsState.this.mContext.registerReceiver(packageIntentReceiver, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
            intentFilter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
            ApplicationsState.this.mContext.registerReceiver(packageIntentReceiver, intentFilter2);
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.intent.action.USER_ADDED");
            intentFilter3.addAction("android.intent.action.USER_REMOVED");
            ApplicationsState.this.mContext.registerReceiver(packageIntentReceiver, intentFilter3);
        }
        if (this.mClonePackageIntentReceiver == null) {
            Context context = this.mContext;
            int i5 = AppUtils.$r8$clinit;
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            Iterator<UserHandle> it = userManager.getUserProfiles().iterator();
            while (true) {
                if (!it.hasNext()) {
                    i3 = -1;
                    break;
                }
                UserHandle next = it.next();
                if (userManager.getUserInfo(next.getIdentifier()).isCloneProfile()) {
                    i3 = next.getIdentifier();
                    break;
                }
            }
            if (i3 != -1) {
                PackageIntentReceiver packageIntentReceiver2 = new PackageIntentReceiver(this, i4);
                this.mClonePackageIntentReceiver = packageIntentReceiver2;
                IntentFilter intentFilter4 = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter4.addDataScheme("package");
                ApplicationsState.this.mContext.registerReceiverAsUser(packageIntentReceiver2, UserHandle.of(i3), intentFilter4, null, null);
            }
        }
        this.mApplications = new ArrayList();
        for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
            try {
                if (this.mEntriesMap.indexOfKey(userInfo.id) < 0) {
                    this.mEntriesMap.put(userInfo.id, new HashMap());
                }
                ((ArrayList) this.mApplications).addAll(this.mIpm.getInstalledApplications(userInfo.isAdmin() ? this.mAdminRetrieveFlags : this.mRetrieveFlags, userInfo.id).getList());
                if (SemSystemProperties.getInt("persist.log.seclevel", 0) == 1) {
                    z3 = true;
                    i2 = 0;
                } else {
                    i2 = 0;
                    z3 = false;
                }
                while (i2 < ((ArrayList) this.mApplications).size()) {
                    ApplicationInfo applicationInfo = (ApplicationInfo) ((ArrayList) this.mApplications).get(i2);
                    if (AppUtils.isLanguagePackApk(this.mContext, applicationInfo)) {
                        if (z3) {
                            Log.i("ApplicationsState", "doResumeIfNeededLocked: don't hidden language pack apk= " + applicationInfo.packageName + " because secLevel");
                        } else {
                            Log.i("ApplicationsState", "doResumeIfNeededLocked: hidden language pack apk= " + applicationInfo.packageName);
                            ((ArrayList) this.mApplications).remove(i2);
                            i2--;
                            i2++;
                        }
                    }
                    if (hasFlag(applicationInfo.flags, QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) {
                        i2++;
                    } else {
                        ((ArrayList) this.mApplications).remove(i2);
                        i2--;
                        i2++;
                    }
                }
            } catch (Exception e) {
                Log.e("ApplicationsState", "Error during doResumeIfNeededLocked", e);
            }
        }
        InterestingConfigChanges interestingConfigChanges = this.mInterestingConfigChanges;
        Context context2 = this.mContext;
        interestingConfigChanges.getClass();
        Resources resources = context2.getResources();
        int updateFrom = interestingConfigChanges.mLastConfiguration.updateFrom(resources.getConfiguration());
        if ((interestingConfigChanges.mLastDensity != resources.getDisplayMetrics().densityDpi) || (updateFrom & 772) != 0) {
            interestingConfigChanges.mLastDensity = resources.getDisplayMetrics().densityDpi;
            Log.d("InterestingConfigChanges", "isConfigChanged=true");
            z = true;
        } else {
            z = false;
        }
        if (Settings.System.getInt(context2.getContentResolver(), "settings_locale_changed", 0) == 1) {
            Log.d("InterestingConfigChanges", "isLocaleChanged=true");
            Settings.System.putInt(context2.getContentResolver(), "settings_locale_changed", 0);
            z = true;
        }
        String string = Settings.System.getString(context2.getContentResolver(), "current_sec_appicon_theme_package");
        String str = InterestingConfigChanges.mCachedAppIconPkg;
        if ((str != null && !str.equals(string)) || (string != null && !string.equals(InterestingConfigChanges.mCachedAppIconPkg))) {
            Log.d("InterestingConfigChanges", "isIconThemeChanged=true");
            z = true;
        }
        InterestingConfigChanges.mCachedAppIconPkg = string;
        if (z) {
            clearEntries();
        } else {
            for (int i6 = 0; i6 < this.mAppEntries.size(); i6++) {
                ((AppEntry) this.mAppEntries.get(i6)).sizeStale = true;
            }
        }
        this.mHaveDisabledApps = false;
        int myUserId = UserHandle.myUserId();
        while (i < ((ArrayList) this.mApplications).size()) {
            ApplicationInfo applicationInfo2 = (ApplicationInfo) ((ArrayList) this.mApplications).get(i);
            if (!SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigAppListForHidingAppMgr").isEmpty()) {
                String[] split = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigAppListForHidingAppMgr").split(",");
                int length = split.length;
                int i7 = 0;
                while (true) {
                    if (i7 >= length) {
                        z2 = false;
                        break;
                    }
                    String str2 = split[i7];
                    if (str2.equals(applicationInfo2.packageName)) {
                        AbstractC0689x6838b71d.m68m("hideAppList : ", str2, " is removed from mApplications", "ApplicationsState");
                        ((ArrayList) this.mApplications).remove(i);
                        z2 = true;
                        break;
                    }
                    i7++;
                }
                i = z2 ? i + 1 : 0;
            }
            if (!applicationInfo2.enabled) {
                if (applicationInfo2.enabledSetting != 3) {
                    ((ArrayList) this.mApplications).remove(i);
                    i--;
                } else {
                    int i8 = this.mWorkUserId;
                    if ((i8 <= 0 || i8 != UserHandle.getUserId(applicationInfo2.uid)) && myUserId == UserHandle.getUserId(applicationInfo2.uid)) {
                        this.mHaveDisabledApps = true;
                    }
                }
            }
            Boolean bool = (Boolean) this.mSystemModules.get(applicationInfo2.packageName);
            if (bool == null ? false : bool.booleanValue()) {
                ((ArrayList) this.mApplications).remove(i);
                i--;
            } else {
                if (this.mRefreshCandidatePkgName.equals(applicationInfo2.packageName)) {
                    this.mRefreshCandidatePkgName = "";
                }
                AppEntry appEntry2 = (AppEntry) ((HashMap) this.mEntriesMap.get(UserHandle.getUserId(applicationInfo2.uid))).get(applicationInfo2.packageName);
                if (appEntry2 != null) {
                    appEntry2.info = applicationInfo2;
                }
            }
        }
        if (this.mRefreshCandidatePkgName.length() > 0) {
            for (int i9 = 0; i9 < this.mEntriesMap.size(); i9++) {
                String str3 = this.mRefreshCandidatePkgName;
                int keyAt = this.mEntriesMap.keyAt(i9);
                synchronized (this.mEntriesMap) {
                    try {
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    }
                    if (this.mEntriesMap.get(keyAt) != null) {
                        appEntry = (AppEntry) ((HashMap) this.mEntriesMap.get(keyAt)).get(str3);
                        if (appEntry != null && this.mEntriesMap.get(keyAt) != null) {
                            ((HashMap) this.mEntriesMap.get(keyAt)).remove(str3);
                            this.mAppEntries.remove(appEntry);
                        }
                        if (!this.mMainHandler.hasMessages(2)) {
                            this.mMainHandler.sendEmptyMessage(2);
                        }
                    }
                    appEntry = null;
                    if (appEntry != null) {
                        ((HashMap) this.mEntriesMap.get(keyAt)).remove(str3);
                        this.mAppEntries.remove(appEntry);
                    }
                    if (!this.mMainHandler.hasMessages(2)) {
                    }
                }
            }
        }
        if (this.mAppEntries.size() > ((ArrayList) this.mApplications).size()) {
            clearEntries();
        }
        this.mCurComputingSizePkg = null;
        if (this.mBackgroundHandler.hasMessages(2)) {
            return;
        }
        this.mAppLoadStartTime = System.currentTimeMillis();
        Context context3 = this.mContext;
        HashMap hashMap = new HashMap();
        String locale = Locale.getDefault().toString();
        String string2 = context3.getSharedPreferences("AppListPreference", 0).getString("CachedLocale", "");
        Log.d("AppListCacheManager", "isValidCache() cached : " + string2 + " / current " + locale);
        if (string2.equals(locale)) {
            try {
                Cursor query = context3.getContentResolver().query(AppListCacheProviderContract.URI_APP_LIST, null, null, null, null);
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            AppListCachePackageData appListCachePackageData = new AppListCachePackageData();
                            appListCachePackageData.packageName = query.getString(query.getColumnIndexOrThrow("package_name"));
                            appListCachePackageData.label = query.getString(query.getColumnIndexOrThrow("app_title"));
                            appListCachePackageData.lastUpdateTime = query.getLong(query.getColumnIndexOrThrow("last_updated"));
                            hashMap.put(appListCachePackageData.packageName, appListCachePackageData);
                        } finally {
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            Log.d("AppListCacheManager", "getAppLabelCache data size: " + hashMap.size());
        } else {
            Log.d("AppListCacheManager", "getAppLabelCache : invalid cache!!");
        }
        mAppLabelCache = hashMap;
        mNewAppListForAppLabelCache.clear();
        this.mBackgroundHandler.sendEmptyMessage(2);
    }

    public final int indexOfApplicationInfoLocked(int i, String str) {
        for (int size = this.mApplications.size() - 1; size >= 0; size--) {
            ApplicationInfo applicationInfo = (ApplicationInfo) this.mApplications.get(size);
            if (applicationInfo.packageName.equals(str) && UserHandle.getUserId(applicationInfo.uid) == i) {
                return size;
            }
        }
        return -1;
    }

    public final void removePackage(int i, String str) {
        synchronized (this.mEntriesMap) {
            int indexOfApplicationInfoLocked = indexOfApplicationInfoLocked(i, str);
            if (indexOfApplicationInfoLocked >= 0) {
                AppEntry appEntry = (AppEntry) ((HashMap) this.mEntriesMap.get(i)).get(str);
                if (appEntry != null) {
                    ((HashMap) this.mEntriesMap.get(i)).remove(str);
                    this.mAppEntries.remove(appEntry);
                }
                ApplicationInfo applicationInfo = (ApplicationInfo) this.mApplications.get(indexOfApplicationInfoLocked);
                this.mApplications.remove(indexOfApplicationInfoLocked);
                if (!applicationInfo.enabled) {
                    this.mHaveDisabledApps = false;
                    Iterator it = this.mApplications.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ApplicationInfo applicationInfo2 = (ApplicationInfo) it.next();
                        if (!applicationInfo2.enabled) {
                            this.mHaveDisabledApps = true;
                            break;
                        } else if (!this.mHaveDisabledApps && (AppUtils.isAutoDisabled(applicationInfo2) || AppUtils.isManualDisabled(applicationInfo2))) {
                            this.mHaveDisabledApps = true;
                        }
                    }
                }
                if (AppUtils.isInstant(applicationInfo)) {
                    Iterator it2 = this.mApplications.iterator();
                    while (it2.hasNext() && !AppUtils.isInstant((ApplicationInfo) it2.next())) {
                    }
                }
                if (!this.mMainHandler.hasMessages(2)) {
                    this.mMainHandler.sendEmptyMessage(2);
                }
            }
        }
    }

    public void setInterestingConfigChanges(InterestingConfigChanges interestingConfigChanges) {
        this.mInterestingConfigChanges = interestingConfigChanges;
    }
}
