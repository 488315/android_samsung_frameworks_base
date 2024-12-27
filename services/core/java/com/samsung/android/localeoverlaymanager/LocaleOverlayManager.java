package com.samsung.android.localeoverlaymanager;

import android.app.LocaleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.SemUserInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.Looper;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.om.OverlayManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LocaleOverlayManager extends HandlerThread implements ExtractionCompleteCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public OverlayChangeObserver mCurrentObserver;
    public Set mDeletedLocales;
    public OverlayHandler mHandler;
    public boolean mInProgress;
    public boolean mIsCleanupInProgress;
    public boolean mIsPackageUpdateTask;
    public Set mLocaleOverlayTargetApks;
    public OMSHelper mOverlayManager;
    public Set mProcessedLocales;
    public Set mReParseTargets;
    public int mRetryCount;
    public boolean mSendOverlayChangedBroadcast;
    public LocaleOverlayManagerWrapper mService;
    public int mToken;
    public String mUpdatedPackage;
    public Handler progressHandler;
    public LocaleOverlayManager$$ExternalSyntheticLambda0 progressResetRunnable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ApplyObserver extends ISamsungOverlayCallback.Stub {
        public final List mObservingPackages;

        public ApplyObserver(List list) {
            ArrayList arrayList = new ArrayList();
            this.mObservingPackages = arrayList;
            if (((ArrayList) list).isEmpty()) {
                LocaleOverlayManager.this.handleTaskComplete();
            } else {
                arrayList.addAll(list);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0055 A[Catch: all -> 0x0032, TRY_LEAVE, TryCatch #0 {all -> 0x0032, blocks: (B:4:0x0004, B:6:0x0029, B:9:0x0044, B:11:0x0055, B:16:0x0034), top: B:3:0x0004 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final synchronized void onOverlayStateChanged(java.lang.String r4, java.lang.String r5, int r6) {
            /*
                r3 = this;
                java.lang.String r0 = "onOverlayStateChanged() called with: packageName = ["
                monitor-enter(r3)
                int r1 = com.samsung.android.localeoverlaymanager.LocaleOverlayManager.$r8$clinit     // Catch: java.lang.Throwable -> L32
                java.lang.String r1 = "LocaleOverlayManager"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
                r2.<init>(r0)     // Catch: java.lang.Throwable -> L32
                r2.append(r5)     // Catch: java.lang.Throwable -> L32
                java.lang.String r0 = "], enabled = ["
                r2.append(r0)     // Catch: java.lang.Throwable -> L32
                r2.append(r6)     // Catch: java.lang.Throwable -> L32
                java.lang.String r6 = "] path = "
                r2.append(r6)     // Catch: java.lang.Throwable -> L32
                r2.append(r4)     // Catch: java.lang.Throwable -> L32
                java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L32
                com.samsung.android.localeoverlaymanager.LogWriter.logDebugInfoAndLogcat(r1, r6)     // Catch: java.lang.Throwable -> L32
                if (r5 == 0) goto L34
                java.lang.String r6 = ""
                boolean r6 = r6.equals(r5)     // Catch: java.lang.Throwable -> L32
                if (r6 == 0) goto L44
                goto L34
            L32:
                r4 = move-exception
                goto L64
            L34:
                java.lang.String r5 = "/data/overlays/current_locale_apks/files/"
                java.lang.String r6 = ""
                java.lang.String r4 = r4.replace(r5, r6)     // Catch: java.lang.Throwable -> L32
                java.lang.String r5 = ".apk"
                java.lang.String r6 = ""
                java.lang.String r5 = r4.replace(r5, r6)     // Catch: java.lang.Throwable -> L32
            L44:
                java.util.List r4 = r3.mObservingPackages     // Catch: java.lang.Throwable -> L32
                java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.lang.Throwable -> L32
                r4.remove(r5)     // Catch: java.lang.Throwable -> L32
                java.util.List r4 = r3.mObservingPackages     // Catch: java.lang.Throwable -> L32
                java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.lang.Throwable -> L32
                boolean r4 = r4.isEmpty()     // Catch: java.lang.Throwable -> L32
                if (r4 == 0) goto L62
                java.lang.String r4 = "LocaleOverlayManager"
                java.lang.String r5 = "onOverlayStateChanged(): Trying to call checkSanityAndCompleteTask"
                com.samsung.android.localeoverlaymanager.LogWriter.logDebugInfoAndLogcat(r4, r5)     // Catch: java.lang.Throwable -> L32
                com.samsung.android.localeoverlaymanager.LocaleOverlayManager r4 = com.samsung.android.localeoverlaymanager.LocaleOverlayManager.this     // Catch: java.lang.Throwable -> L32
                r4.checkSanityAndCompleteTask()     // Catch: java.lang.Throwable -> L32
            L62:
                monitor-exit(r3)
                return
            L64:
                monitor-exit(r3)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.localeoverlaymanager.LocaleOverlayManager.ApplyObserver.onOverlayStateChanged(java.lang.String, java.lang.String, int):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverlayHandler extends Handler {
        public OverlayHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:131:0x02c9, code lost:
        
            if (r4.equals(r15) == false) goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:229:0x06dd, code lost:
        
            if (r1.equals("init_on_boot") == false) goto L171;
         */
        /* JADX WARN: Removed duplicated region for block: B:133:0x04b0  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0539  */
        /* JADX WARN: Removed duplicated region for block: B:44:? A[ADDED_TO_REGION, REMOVE, RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x033f  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x039d  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x03a4  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0432  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r28) {
            /*
                Method dump skipped, instructions count: 1896
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.localeoverlaymanager.LocaleOverlayManager.OverlayHandler.handleMessage(android.os.Message):void");
        }

        public final boolean hasAnyMessageOrCallbacks() {
            return hasMessages(1) || hasMessages(2) || hasMessages(7) || hasMessages(3) || hasMessages(4) || hasMessages(5) || hasMessages(6);
        }
    }

    /* renamed from: -$$Nest$mcleanLocaleOverlayForDisable, reason: not valid java name */
    public static void m1219$$Nest$mcleanLocaleOverlayForDisable(LocaleOverlayManager localeOverlayManager, String str) {
        ArrayList arrayList;
        ApplicationInfo applicationInfo;
        if (localeOverlayManager.mOverlayManager == null) {
            localeOverlayManager.initOverlayManager();
        }
        Log.i("LocaleOverlayManager", "cleanLocaleOverlayForDisable: " + localeOverlayManager.mOverlayManager + ", packageName : " + str);
        if (localeOverlayManager.mOverlayManager != null) {
            if (TextUtils.isEmpty(str)) {
                OMSHelper oMSHelper = localeOverlayManager.mOverlayManager;
                Set set = localeOverlayManager.mLocaleOverlayTargetApks;
                Context context = localeOverlayManager.mContext;
                oMSHelper.getClass();
                Log.i("OMSHelper", "getDisabledOverlaysPackages, localeOverlayTargetApks: " + set);
                List localeOverlaysForUser = oMSHelper.getLocaleOverlaysForUser(0);
                arrayList = new ArrayList();
                ArrayList arrayList2 = (ArrayList) localeOverlaysForUser;
                if (arrayList2.size() > 0) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        OverlayInfo overlayInfo = (OverlayInfo) it.next();
                        if (overlayInfo != null) {
                            if (!((HashSet) set).contains(overlayInfo.targetPackageName)) {
                                StringBuilder sb = new StringBuilder("package name: ");
                                sb.append(overlayInfo.targetPackageName);
                                sb.append(", overPackage: ");
                                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, overlayInfo.packageName, "OMSHelper");
                                try {
                                    applicationInfo = context.getPackageManager().getApplicationInfo(overlayInfo.targetPackageName, 41472);
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                                if (applicationInfo != null) {
                                    Log.i("OMSHelper", "app hidden: skip disable " + applicationInfo);
                                } else {
                                    arrayList.add(overlayInfo.packageName);
                                }
                            }
                        }
                    }
                }
            } else {
                List localeOverlayInfosForTarget = localeOverlayManager.mOverlayManager.getLocaleOverlayInfosForTarget(str);
                arrayList = new ArrayList();
                Iterator it2 = ((ArrayList) localeOverlayInfosForTarget).iterator();
                while (it2.hasNext()) {
                    arrayList.add(((OverlayInfo) it2.next()).packageName);
                }
            }
            Log.i("LocaleOverlayManager", "cleanLocaleOverlayForDisable() overlayPackages:" + arrayList);
            if (arrayList.isEmpty()) {
                if (str != null) {
                    localeOverlayManager.handleTaskComplete();
                    return;
                }
                return;
            }
            localeOverlayManager.mOverlayManager.getClass();
            OMSHelper.applySamsungConfigChangeOverlays(arrayList, null, 0, null);
            Log.i("LOMUtils", "deleteDisabledLocaleOverlays");
            File file = new File("/data/overlays/current_locale_apks/files/");
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                File file2 = new File(file, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1((String) it3.next(), ".apk"));
                if (file2.exists()) {
                    Log.i("LOMUtils", "Deleting Overlay: " + file2.getName());
                    Utils.deleteFile(file2);
                }
            }
            Log.i("LocaleOverlayManager", "cleanLocaleOverlayForDisable done for --" + arrayList.size());
        }
        if (str != null) {
            localeOverlayManager.handleTaskComplete();
        }
    }

    /* renamed from: -$$Nest$mhasZippedOverlaysPackage, reason: not valid java name */
    public static boolean m1220$$Nest$mhasZippedOverlaysPackage(LocaleOverlayManager localeOverlayManager, String str) {
        Bundle bundle;
        localeOverlayManager.getClass();
        boolean z = false;
        try {
            ApplicationInfo applicationInfo = localeOverlayManager.mContext.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                if (bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
                    z = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("LocaleOverlayManager", "hasZippedOverlaysPackage PackageManager.NameNotFoundException: " + e.getMessage());
        }
        Log.i("LocaleOverlayManager", "hasZippedOverlaysPackage: " + str + " -- " + z);
        return z;
    }

    public final void checkSanityAndCompleteTask() {
        int i = this.mRetryCount + 1;
        this.mRetryCount = i;
        if (i > 5) {
            LogWriter.logDebugInfoAndLogcat("LocaleOverlayManager", "checkSanityAndCompleteTask: Max retries done!");
            handleTaskComplete();
            return;
        }
        ArrayList arrayList = new ArrayList(Utils.getSystemLocales());
        Set set = this.mDeletedLocales;
        if (set != null && !((HashSet) set).isEmpty()) {
            arrayList.removeAll(this.mDeletedLocales);
            this.mDeletedLocales = null;
        }
        if (!ensureOverlaysEnabled(arrayList)) {
            Log.e("LocaleOverlayManager", "checkSanityAndCompleteTask: Task did not complete successfully. Retry!");
        } else {
            Log.d("LocaleOverlayManager", "checkSanityAndCompleteTask: Task completed successfully!.");
            handleTaskComplete();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v7, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v23, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.lang.Object, java.util.Map] */
    public final boolean ensureOverlaysEnabled(ArrayList arrayList) {
        ?? allOverlays;
        String str;
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        if (this.mOverlayManager == null) {
            Log.e("LocaleOverlayManager", "ensureOverlaysEnabled() called. mOverlayManager is null!");
            return true;
        }
        HashSet hashSet = new HashSet();
        if (!this.mIsPackageUpdateTask || this.mUpdatedPackage == null) {
            Set set = this.mLocaleOverlayTargetApks;
            if (set == null || ((HashSet) set).isEmpty()) {
                parseTargetApks();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                Iterator it2 = ((HashSet) this.mLocaleOverlayTargetApks).iterator();
                while (it2.hasNext()) {
                    hashSet.add(((String) it2.next()) + "." + str2);
                }
            }
            allOverlays = this.mOverlayManager.mService.getAllOverlays(Utils.sCurrentUserId);
            if (!allOverlays.isEmpty()) {
                Iterator it3 = allOverlays.entrySet().iterator();
                while (it3.hasNext()) {
                    ((List) ((Map.Entry) it3.next()).getValue()).removeIf(new OMSHelper$$ExternalSyntheticLambda2());
                }
            }
        } else {
            Iterator it4 = arrayList.iterator();
            while (it4.hasNext()) {
                hashSet.add(this.mUpdatedPackage + "." + ((String) it4.next()));
            }
            List localeOverlayInfosForTarget = this.mOverlayManager.getLocaleOverlayInfosForTarget(this.mUpdatedPackage);
            allOverlays = new HashMap();
            allOverlays.put(this.mUpdatedPackage, localeOverlayInfosForTarget);
        }
        Log.i("LocaleOverlayManager", "ensureOverlaysEnabled: overlayList - " + allOverlays);
        for (Map.Entry entry : allOverlays.entrySet()) {
            try {
                str = this.mContext.createPackageContext((String) entry.getKey(), 0).getPackageResourcePath();
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("LocaleOverlayManager", "ensureOverlaysEnabled - NameNotFoundException: " + e.getMessage());
                str = null;
            }
            for (OverlayInfo overlayInfo : (List) entry.getValue()) {
                String targetPath = this.mOverlayManager.mService.getTargetPath(overlayInfo.baseCodePath);
                if (targetPath != null && targetPath.equals(str) && overlayInfo.isEnabled()) {
                    hashSet.remove(overlayInfo.packageName);
                }
            }
        }
        Log.i("LocaleOverlayManager", "ensureOverlaysEnabled: toAddPackageList = [" + hashSet + "], observer = [" + this.mCurrentObserver + "]");
        if (hashSet.isEmpty()) {
            return true;
        }
        this.mReParseTargets = new HashSet();
        this.mSendOverlayChangedBroadcast = true;
        HashSet hashSet2 = new HashSet();
        Iterator it5 = hashSet.iterator();
        while (it5.hasNext()) {
            String str3 = (String) it5.next();
            ((HashSet) this.mReParseTargets).add(str3.substring(0, str3.lastIndexOf(46)));
            hashSet2.add(str3.substring(str3.lastIndexOf(46) + 1));
        }
        Log.i("LocaleOverlayManager", "ensureOverlaysEnabled: mReParseTargets = [" + this.mReParseTargets + "], reParseLocales = [" + hashSet2 + "]");
        ApkExtractionManager apkExtractionManager = ApkExtractionManager.sInstance;
        apkExtractionManager.mCallback = this;
        apkExtractionManager.extractLocaleApks(this.mReParseTargets, hashSet2, this.mContext, true);
        return false;
    }

    public final void handleTaskComplete() {
        LogWriter.logDebugInfoAndLogcat("LocaleOverlayManager", "handleTaskComplete -- callers: " + Debug.getCallers(2));
        Utils.setCurrentUserId(0);
        this.mRetryCount = 0;
        this.mService.mRequestInProgress = false;
        this.progressHandler.removeCallbacks(this.progressResetRunnable);
        SharedPreferences preferences = PreferenceUtils.getPreferences(this.mContext);
        Set set = this.mProcessedLocales;
        if (set != null && !set.isEmpty()) {
            PreferenceUtils.setLocalesForUser(Utils.sCurrentUserId, this.mProcessedLocales);
            this.mProcessedLocales.clear();
        }
        OverlayHandler overlayHandler = this.mHandler;
        if (overlayHandler != null) {
            if (!overlayHandler.hasMessages(2) || !this.mHandler.hasMessages(7)) {
                preferences.edit().putBoolean("locale_in_progress", false).commit();
            }
            if (!this.mHandler.hasMessages(3)) {
                preferences.edit().putString("app_in_progress", KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG).commit();
            }
        }
        if (this.mSendOverlayChangedBroadcast) {
            OverlayManagerService.broadcastActionOverlayChangedPublic(this.mReParseTargets, Utils.sCurrentUserId);
            this.mReParseTargets = null;
            this.mSendOverlayChangedBroadcast = false;
        }
        Log.i("LocaleOverlayManager", "callbackObserver. mCurrentObserver: " + this.mCurrentObserver);
        OverlayChangeObserver overlayChangeObserver = this.mCurrentObserver;
        if (overlayChangeObserver != null) {
            overlayChangeObserver.onChangeCompleted(this.mToken);
        }
        this.mCurrentObserver = null;
        this.mInProgress = false;
        this.mIsPackageUpdateTask = false;
        this.mUpdatedPackage = null;
        LogWriter.syncLogFile();
        OverlayHandler overlayHandler2 = this.mHandler;
        if (overlayHandler2 == null || overlayHandler2.hasAnyMessageOrCallbacks()) {
            return;
        }
        this.mService.waitAndQuit();
    }

    public final synchronized void initOverlayManager() {
        if (this.mOverlayManager == null) {
            this.mOverlayManager = new OMSHelper();
        }
    }

    public final void installLocales(Set set, final Set set2) {
        boolean z;
        Log.i("LocaleOverlayManager", "installLocales() called with: addedLocales = [" + set + "], deletedLocales = [" + set2 + "]");
        Set set3 = this.mLocaleOverlayTargetApks;
        if (set3 == null || ((HashSet) set3).isEmpty()) {
            parseTargetApks();
        }
        if (((HashSet) this.mLocaleOverlayTargetApks).isEmpty()) {
            Log.e("LocaleOverlayManager", "installLocales: No Locale Target apks");
            handleTaskComplete();
            return;
        }
        if (((HashSet) set).isEmpty()) {
            z = true;
        } else {
            ApkExtractionManager apkExtractionManager = ApkExtractionManager.sInstance;
            apkExtractionManager.mCallback = this;
            z = false;
            apkExtractionManager.extractLocaleApks(this.mLocaleOverlayTargetApks, set, this.mContext, false);
        }
        if (set2 == null || ((HashSet) set2).isEmpty()) {
            return;
        }
        this.mDeletedLocales = new HashSet(set2);
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        final LocaleManager localeManager = (LocaleManager) this.mContext.getSystemService(LocaleManager.class);
        final ArrayList arrayList = new ArrayList();
        OMSHelper oMSHelper = this.mOverlayManager;
        if (oMSHelper != null) {
            ((ArrayList) oMSHelper.getLocaleOverlaysForUser(Utils.sCurrentUserId)).forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LocaleManager localeManager2 = localeManager;
                    Set set4 = set2;
                    List list = arrayList;
                    OverlayInfo overlayInfo = (OverlayInfo) obj;
                    if (new File(overlayInfo.baseCodePath).exists() && overlayInfo.isEnabled()) {
                        String str = overlayInfo.packageName;
                        String substring = str.substring(str.lastIndexOf(46) + 1);
                        LocaleList applicationLocales = localeManager2.getApplicationLocales(overlayInfo.targetPackageName);
                        if (!set4.contains(substring) || overlayInfo.packageName.startsWith(Constants.SYSTEMUI_PACKAGE_NAME) || ((HashSet) Utils.getLocalesListAsSet(applicationLocales)).contains(substring)) {
                            return;
                        }
                        list.add(overlayInfo.packageName);
                    }
                }
            });
        }
        Log.i("LocaleOverlayManager", "Disable locales  -> " + set2 + " packages -> " + arrayList + " OM -> " + this.mOverlayManager);
        ApplyObserver applyObserver = z ? new ApplyObserver(arrayList) : null;
        if (this.mOverlayManager == null || arrayList.isEmpty()) {
            if (z) {
                handleTaskComplete();
            }
        } else {
            OMSHelper oMSHelper2 = this.mOverlayManager;
            int i = Utils.sCurrentUserId;
            oMSHelper2.getClass();
            OMSHelper.applySamsungConfigChangeOverlays(arrayList, null, i, applyObserver);
        }
    }

    public final void installLocalesForPackages(Set set, Set set2) {
        Log.i("LocaleOverlayManager", "installLocalesForPackages() called with: packages = [" + set + "], processedLanguages = [" + set2 + "]");
        if (set == null) {
            handleTaskComplete();
            return;
        }
        if (set2 == null) {
            List<SemUserInfo> semGetUsers = ((UserManager) this.mContext.getSystemService("user")).semGetUsers();
            HashSet hashSet = new HashSet();
            Log.i("PreferenceUtils", "getLocalesForAllUsers: UserInfos - " + semGetUsers);
            for (SemUserInfo semUserInfo : semGetUsers) {
                Set localesForUser = PreferenceUtils.getLocalesForUser(semUserInfo.getUserHandle().semGetIdentifier());
                Log.i("PreferenceUtils", "getLocalesForAllUsers: UserId - " + semUserInfo.getUserHandle().semGetIdentifier() + " has locales - " + localesForUser);
                hashSet.addAll(localesForUser);
            }
            set2 = hashSet;
        }
        if (set2.isEmpty()) {
            handleTaskComplete();
            return;
        }
        ApkExtractionManager apkExtractionManager = ApkExtractionManager.sInstance;
        apkExtractionManager.mCallback = this;
        apkExtractionManager.extractLocaleApks(set, new HashSet(set2), this.mContext, true);
        Set set3 = this.mLocaleOverlayTargetApks;
        if (set3 == null || ((HashSet) set3).isEmpty()) {
            return;
        }
        this.mLocaleOverlayTargetApks.addAll(set);
    }

    public final void onExtractionComplete(Set set, boolean z) {
        LogWriter.logDebugInfoAndLogcat("LocaleOverlayManager", "onExtractionComplete() called with: extractedLocales = [" + set + "], forceEnable = [" + z + "]");
        if (set == null || set.isEmpty()) {
            this.mSendOverlayChangedBroadcast = false;
            handleTaskComplete();
            return;
        }
        ArrayList arrayList = new ArrayList(set);
        int i = Utils.sCurrentUserId;
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        Log.i("LocaleOverlayManager", "enableOverlays() called with: enableLocalePackages" + arrayList + ", OverlayManger = [" + this.mOverlayManager + "]");
        OMSHelper oMSHelper = this.mOverlayManager;
        if (oMSHelper != null) {
            if (!this.mIsPackageUpdateTask && !z) {
                List localeOverlaysForUser = oMSHelper.getLocaleOverlaysForUser(i);
                final ArrayList arrayList2 = new ArrayList();
                ((ArrayList) localeOverlaysForUser).forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        List list = arrayList2;
                        OverlayInfo overlayInfo = (OverlayInfo) obj;
                        if (new File(overlayInfo.baseCodePath).exists() && overlayInfo.isEnabled()) {
                            list.add(overlayInfo.packageName);
                        }
                    }
                });
                arrayList.removeAll(arrayList2);
                Log.i("LocaleOverlayManager", "enableOverlays(): Skipping = [" + arrayList2 + "], enabling = [" + arrayList + "]");
            }
            if (arrayList.isEmpty()) {
                handleTaskComplete();
                return;
            }
            LogWriter.logDebugInfoAndLogcat("LocaleOverlayManager", "enableOverlays() called. enableLocalePackages = " + arrayList);
            OMSHelper oMSHelper2 = this.mOverlayManager;
            ArrayList arrayList3 = new ArrayList();
            ApplyObserver applyObserver = new ApplyObserver(arrayList);
            oMSHelper2.getClass();
            OMSHelper.applySamsungConfigChangeOverlays(arrayList3, arrayList, i, applyObserver);
        }
    }

    @Override // android.os.HandlerThread
    public final void onLooperPrepared() {
        super.onLooperPrepared();
        this.mHandler = new OverlayHandler(getLooper());
        this.progressHandler = new Handler();
        Log.i("LocaleOverlayManager", "Handler ready " + this.mHandler);
    }

    public final void parseTargetApks() {
        this.mLocaleOverlayTargetApks = new HashSet();
        for (ApplicationInfo applicationInfo : this.mContext.getPackageManager().getInstalledApplicationsAsUser(128, Utils.sCurrentUserId)) {
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null && bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
                this.mLocaleOverlayTargetApks.add(applicationInfo.packageName);
            }
        }
    }

    @Override // android.os.HandlerThread
    public final boolean quit() {
        Log.i("LocaleOverlayManager", "quit() called");
        ApkExtractionManager apkExtractionManager = ApkExtractionManager.sInstance;
        ((LinkedBlockingQueue) apkExtractionManager.mTasks).clear();
        apkExtractionManager.mCallback = null;
        this.mCurrentObserver = null;
        OverlayHandler overlayHandler = this.mHandler;
        if (overlayHandler == null || !overlayHandler.hasAnyMessageOrCallbacks()) {
            Log.i("LocaleOverlayManager", "quit() called no pending messages to remove ");
        } else {
            Log.i("LocaleOverlayManager", "quit() called :Remove pending message or callback ");
            this.mHandler.removeCallbacksAndMessages(null);
        }
        return quitSafely();
    }
}
