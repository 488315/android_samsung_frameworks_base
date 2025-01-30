package com.samsung.android.localeoverlaymanager;

import android.app.LocaleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.om.ISamsungOverlayCallback;
import android.content.om.OverlayInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.SemUserInfo;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.om.OverlayManagerService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class LocaleOverlayManager extends HandlerThread implements ExtractionCompleteCallback {
    public static final String TAG = LocaleOverlayManager.class.getSimpleName();
    public Context mContext;
    public OverlayChangeObserver mCurrentObserver;
    public Set mDeletedLocales;
    public OverlayHandler mHandler;
    public boolean mInProgress;
    public boolean mIsCleanupInProgress;
    public boolean mIsPackageUpdateTask;
    public Set mLocaleOverlayTargetApks;
    public OMSHelper mOverlayManager;
    public Set mReParseTargets;
    public int mRetryCount;
    public boolean mSendOverlayChangedBroadcast;
    public LocaleOverlayManagerWrapper mService;
    public int mToken;
    public String mUpdatedPackage;
    public Handler progressHandler;
    public Runnable progressResetRunnable;

    public LocaleOverlayManager(String str, LocaleOverlayManagerWrapper localeOverlayManagerWrapper) {
        super(str);
        this.mInProgress = false;
        this.progressResetRunnable = new Runnable() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LocaleOverlayManager.this.checkSanityAndCompleteTask();
            }
        };
        initOverlayManager();
        this.mService = localeOverlayManagerWrapper;
    }

    public final synchronized void initOverlayManager() {
        if (this.mOverlayManager == null) {
            this.mOverlayManager = new OMSHelper();
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setObserver(OverlayChangeObserver overlayChangeObserver) {
        this.mCurrentObserver = overlayChangeObserver;
    }

    @Override // android.os.HandlerThread
    public void onLooperPrepared() {
        super.onLooperPrepared();
        this.mHandler = new OverlayHandler(getLooper());
        this.progressHandler = new Handler();
        Log.i(TAG, "Handler ready " + this.mHandler);
    }

    public void setPackageUpdateTask(boolean z) {
        this.mIsPackageUpdateTask = z;
    }

    public void setUpdatedPackage(String str) {
        this.mUpdatedPackage = str;
    }

    public void installLocalesForPackages(Set set) {
        installLocalesForPackages(set, null);
    }

    public void installLocalesForPackages(Set set, Set set2) {
        Log.i(TAG, "installLocalesForPackages() called with: packages = [" + set + "], processedLanguages = [" + set2 + "]");
        if (set != null) {
            if (set2 == null) {
                set2 = PreferenceUtils.getLocalesForAllUsers(this.mContext);
            }
            if (set2.isEmpty()) {
                handleTaskComplete();
                return;
            }
            ApkExtractionManager apkExtractionManager = ApkExtractionManager.getInstance();
            apkExtractionManager.setCallback(this);
            apkExtractionManager.extractLocaleApks(set, new HashSet(set2), this.mContext, true);
            Set set3 = this.mLocaleOverlayTargetApks;
            if (set3 == null || set3.isEmpty()) {
                return;
            }
            this.mLocaleOverlayTargetApks.addAll(set);
            return;
        }
        handleTaskComplete();
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.samsung.android.localeoverlaymanager.ExtractionCompleteCallback
    public void onExtractionComplete(Set set, boolean z) {
        LogWriter.logDebugInfoAndLogcat(TAG, "onExtractionComplete() called with: extractedLocales = [" + set + "], forceEnable = [" + z + "]");
        if (set == null || set.isEmpty()) {
            this.mSendOverlayChangedBroadcast = false;
            handleTaskComplete();
        } else {
            enableOverlays(new ArrayList(set), z);
        }
    }

    public class OverlayHandler extends Handler {
        public OverlayHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            Log.i(LocaleOverlayManager.TAG, "handleMessage " + LocaleOverlayManager.this.mInProgress + " msg " + message);
            if (LocaleOverlayManager.this.mInProgress) {
                Message obtain = Message.obtain((Handler) null, message.what);
                obtain.obj = message.obj;
                obtain.setData(message.getData());
                sendMessageDelayed(obtain, 100L);
            }
            LocaleOverlayManager.this.mInProgress = true;
            LocaleOverlayManager.this.progressHandler.removeCallbacks(LocaleOverlayManager.this.progressResetRunnable);
            LocaleOverlayManager.this.progressHandler.postDelayed(LocaleOverlayManager.this.progressResetRunnable, 120000L);
            boolean z2 = false;
            switch (message.what) {
                case 1:
                    LocaleOverlayManager.this.mInProgress = false;
                    if (message.obj != null) {
                        handlePendingAction(message);
                        break;
                    }
                    break;
                case 2:
                    ArrayList<String> stringArrayList = message.getData().getStringArrayList("config_locale_list");
                    if (stringArrayList == null) {
                        Log.e(LocaleOverlayManager.TAG, "Locale list from config is null.");
                        LocaleOverlayManager.this.handleTaskComplete();
                        break;
                    } else {
                        Set localesForUser = PreferenceUtils.getLocalesForUser(Utils.getCurrentUserId());
                        Log.i(LocaleOverlayManager.TAG, "Locale list from config: " + stringArrayList + " old list: " + localesForUser);
                        HashSet hashSet = new HashSet(stringArrayList);
                        hashSet.removeAll(localesForUser);
                        Log.i(LocaleOverlayManager.TAG, "AddedLocales list from config - " + hashSet);
                        HashSet hashSet2 = new HashSet(localesForUser);
                        hashSet2.removeAll(stringArrayList);
                        Log.i(LocaleOverlayManager.TAG, "DeletedLocales list from config - " + hashSet2);
                        localesForUser.addAll(hashSet);
                        localesForUser.removeAll(hashSet2);
                        PreferenceUtils.setLocalesForUser(Utils.getCurrentUserId(), localesForUser);
                        if (hashSet2.isEmpty() && hashSet.isEmpty()) {
                            if (LocaleOverlayManager.this.ensureOverlaysEnabled(stringArrayList)) {
                                LocaleOverlayManager.this.handleTaskComplete();
                                break;
                            }
                        } else {
                            PreferenceUtils.getPreferences(LocaleOverlayManager.this.mContext).edit().putBoolean("locale_in_progress", true).commit();
                            LocaleOverlayManager.this.installLocales(hashSet, hashSet2);
                            break;
                        }
                    }
                    break;
                case 3:
                    Object obj = message.obj;
                    if (obj != null) {
                        Bundle bundle = (Bundle) obj;
                        LocaleOverlayManager.this.mToken = bundle.getInt(KnoxCustomManagerService.SPCM_KEY_TOKEN);
                        LocaleOverlayManager.this.mUpdatedPackage = bundle.getString("added_package");
                        Log.i(LocaleOverlayManager.TAG, "handleMessage: MESSAGE_PARSE_SINGLE_PACKAGE. PackageName = [" + LocaleOverlayManager.this.mUpdatedPackage + "]");
                        if (LocaleOverlayManager.this.mUpdatedPackage == null) {
                            LocaleOverlayManager.this.handleTaskComplete();
                            break;
                        } else {
                            PreferenceUtils.getPreferences(LocaleOverlayManager.this.mContext).edit().putString("app_in_progress", LocaleOverlayManager.this.mUpdatedPackage).commit();
                            LocaleOverlayManager.this.setPackageUpdateTask(true);
                            LocaleOverlayManager localeOverlayManager = LocaleOverlayManager.this;
                            if (localeOverlayManager.hasZippedOverlaysPackage(localeOverlayManager.mUpdatedPackage)) {
                                if (LocaleOverlayManager.this.mOverlayManager == null) {
                                    LocaleOverlayManager.this.initOverlayManager();
                                }
                                if (LocaleOverlayManager.this.mOverlayManager != null) {
                                    LocaleOverlayManager.this.mOverlayManager.updatePackageCache(LocaleOverlayManager.this.mUpdatedPackage, Utils.getCurrentUserId());
                                }
                                LocaleOverlayManager localeOverlayManager2 = LocaleOverlayManager.this;
                                localeOverlayManager2.installLocalesForPackages(Collections.singleton(localeOverlayManager2.mUpdatedPackage));
                                break;
                            } else {
                                Log.i(LocaleOverlayManager.TAG, "Package is not supported for Locale Overlays: " + LocaleOverlayManager.this.mUpdatedPackage);
                                LocaleOverlayManager localeOverlayManager3 = LocaleOverlayManager.this;
                                localeOverlayManager3.cleanLocaleOverlayForDisable(localeOverlayManager3.mUpdatedPackage);
                                break;
                            }
                        }
                    }
                    break;
                case 4:
                    Object obj2 = message.obj;
                    if (obj2 != null) {
                        Bundle bundle2 = (Bundle) obj2;
                        z2 = bundle2.getBoolean("safeMode");
                        z = bundle2.getBoolean("startCleanUpOverlay");
                    } else {
                        z = false;
                    }
                    init(z2, z);
                    break;
                case 5:
                    LocaleOverlayManager.this.handleTaskComplete();
                    break;
                case 6:
                    ArrayList arrayList = new ArrayList(Utils.getSystemLocales());
                    Log.i(LocaleOverlayManager.TAG, "handleMessage: MESSAGE_JOB_ENSUREOVERLAYS. Current locales = " + arrayList);
                    if (LocaleOverlayManager.this.ensureOverlaysEnabled(arrayList)) {
                        LocaleOverlayManager.this.handleTaskComplete();
                        break;
                    }
                    break;
                case 7:
                    Object obj3 = message.obj;
                    if (obj3 != null) {
                        Bundle bundle3 = (Bundle) obj3;
                        ArrayList<String> stringArrayList2 = bundle3.getStringArrayList("config_locale_list");
                        String string = bundle3.getString("perAppPackageName");
                        if (stringArrayList2 == null || string == null) {
                            Log.e(LocaleOverlayManager.TAG, "MESSAGE_PERAPP_SUPPORT: Ignored message. currentLocales = " + stringArrayList2 + ", packageName = " + string);
                            LocaleOverlayManager.this.handleTaskComplete();
                            break;
                        } else {
                            HashSet hashSet3 = new HashSet(stringArrayList2);
                            if (LocaleOverlayManager.this.hasZippedOverlaysPackage(string)) {
                                LocaleOverlayManager.this.installLocalesForPackages(Collections.singleton(string), hashSet3);
                                break;
                            } else {
                                Log.i(LocaleOverlayManager.TAG, "Package is not supported for Locale Overlays: " + string);
                                LocaleOverlayManager.this.cleanLocaleOverlayForDisable(string);
                                break;
                            }
                        }
                    }
                    break;
                default:
                    LocaleOverlayManager.this.mInProgress = false;
                    Log.i(LocaleOverlayManager.TAG, "handleMessage: Message not known - " + message);
                    break;
            }
        }

        public final void init(boolean z, boolean z2) {
            boolean updateOverlays;
            Log.i(LocaleOverlayManager.TAG, "init() called");
            LocaleOverlayManager.this.parseTargetApks();
            OnBootInitializer onBootInitializer = new OnBootInitializer(LocaleOverlayManager.this);
            LocaleOverlayManager.this.mIsCleanupInProgress = true;
            if (z2) {
                onBootInitializer.cleanupOverlayDir(LocaleOverlayManager.this.mContext);
                updateOverlays = false;
            } else {
                LocaleOverlayManager.this.progressHandler.removeCallbacks(LocaleOverlayManager.this.progressResetRunnable);
                LocaleOverlayManager.this.progressHandler.postDelayed(LocaleOverlayManager.this.progressResetRunnable, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                updateOverlays = onBootInitializer.updateOverlays(LocaleOverlayManager.this.mLocaleOverlayTargetApks, LocaleOverlayManager.this.mContext, z);
                LocaleOverlayManager.this.cleanLocaleOverlayForDisable(null);
            }
            LocaleOverlayManager.this.mIsCleanupInProgress = false;
            if (updateOverlays) {
                return;
            }
            LocaleOverlayManager.this.handleTaskComplete();
        }

        public boolean hasAnyMessageOrCallbacks() {
            return hasMessages(1) || hasMessages(2) || hasMessages(7) || hasMessages(3) || hasMessages(4) || hasMessages(5) || hasMessages(6);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x008a, code lost:
        
            if (r0.equals("com.samsung.android.localeoverlaymanager.action.JOB_SCHEDULED") == false) goto L8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handlePendingAction(Message message) {
            boolean z = false;
            LocaleOverlayManager.this.mRetryCount = 0;
            Bundle bundle = (Bundle) message.obj;
            String string = bundle.getString("pending_action");
            Utils.setCurrentUserId(bundle.getInt("userId", 0));
            LogWriter.logDebugInfoAndLogcat(LocaleOverlayManager.TAG, "MESSAGE_PENDING_ACTION --" + string);
            if (string == null) {
                Log.e(LocaleOverlayManager.TAG, "handlePendingAction: Pending action is null");
            }
            switch (string.hashCode()) {
                case -1704170454:
                    break;
                case -1060850397:
                    if (string.equals("init_on_boot")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case -803721550:
                    if (string.equals("com.samsung.android.localeoverlaymanager.action.MSG_HANDLE_PER_APP_LOCALE")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                case -19011148:
                    if (string.equals("android.intent.action.LOCALE_CHANGED")) {
                        z = 3;
                        break;
                    }
                    z = -1;
                    break;
                case 85817670:
                    if (string.equals("com.samsung.android.localeoverlaymanager.action.PACKAGE_ADDED")) {
                        z = 4;
                        break;
                    }
                    z = -1;
                    break;
                case 2141912828:
                    if (string.equals("com.samsung.android.localeoverlaymanager.action.SETUP_COMPLETE")) {
                        z = 5;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    LocaleOverlayManager.this.mHandler.sendEmptyMessage(6);
                    break;
                case true:
                    LocaleOverlayManager.this.mHandler.sendMessage(Message.obtain(null, 4, bundle));
                    break;
                case true:
                    LocaleOverlayManager.this.mHandler.sendMessage(Message.obtain(null, 7, bundle));
                    break;
                case true:
                    Message obtain = Message.obtain((Handler) null, 2);
                    obtain.setData(bundle);
                    LocaleOverlayManager.this.mHandler.sendMessage(obtain);
                    break;
                case true:
                    LocaleOverlayManager.this.mHandler.sendMessage(Message.obtain(null, 3, bundle));
                    break;
                case true:
                    LocaleOverlayManager.this.mHandler.sendEmptyMessage(5);
                    break;
            }
        }
    }

    public boolean ensureOverlaysEnabled(ArrayList arrayList) {
        Map localeOverlaysMap;
        String str;
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        if (this.mOverlayManager == null) {
            Log.e(TAG, "ensureOverlaysEnabled() called. mOverlayManager is null!");
            return true;
        }
        HashSet<String> hashSet = new HashSet();
        if (this.mIsPackageUpdateTask && this.mUpdatedPackage != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                hashSet.add(this.mUpdatedPackage + "." + ((String) it.next()));
            }
            List localeOverlayInfosForTarget = this.mOverlayManager.getLocaleOverlayInfosForTarget(this.mUpdatedPackage);
            localeOverlaysMap = new HashMap();
            localeOverlaysMap.put(this.mUpdatedPackage, localeOverlayInfosForTarget);
        } else {
            Set set = this.mLocaleOverlayTargetApks;
            if (set == null || set.isEmpty()) {
                parseTargetApks();
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                Iterator it3 = this.mLocaleOverlayTargetApks.iterator();
                while (it3.hasNext()) {
                    hashSet.add(((String) it3.next()) + "." + str2);
                }
            }
            localeOverlaysMap = this.mOverlayManager.getLocaleOverlaysMap(Utils.getCurrentUserId());
        }
        Log.i(TAG, "ensureOverlaysEnabled: overlayList - " + localeOverlaysMap);
        for (Map.Entry entry : localeOverlaysMap.entrySet()) {
            try {
                str = this.mContext.createPackageContext((String) entry.getKey(), 0).getPackageResourcePath();
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "ensureOverlaysEnabled - NameNotFoundException: " + e.getMessage());
                str = null;
            }
            for (OverlayInfo overlayInfo : (List) entry.getValue()) {
                String targetPath = this.mOverlayManager.getTargetPath(overlayInfo.baseCodePath);
                if ((targetPath != null && targetPath.equals(str)) && overlayInfo.isEnabled()) {
                    hashSet.remove(overlayInfo.packageName);
                }
            }
        }
        Log.i(TAG, "ensureOverlaysEnabled: toAddPackageList = [" + hashSet + "], observer = [" + this.mCurrentObserver + "]");
        if (hashSet.isEmpty()) {
            return true;
        }
        this.mReParseTargets = new HashSet();
        this.mSendOverlayChangedBroadcast = true;
        HashSet hashSet2 = new HashSet();
        for (String str3 : hashSet) {
            this.mReParseTargets.add(str3.substring(0, str3.lastIndexOf(46)));
            hashSet2.add(str3.substring(str3.lastIndexOf(46) + 1));
        }
        Log.i(TAG, "ensureOverlaysEnabled: mReParseTargets = [" + this.mReParseTargets + "], reParseLocales = [" + hashSet2 + "]");
        ApkExtractionManager apkExtractionManager = ApkExtractionManager.getInstance();
        apkExtractionManager.setCallback(this);
        apkExtractionManager.extractLocaleApks(this.mReParseTargets, hashSet2, this.mContext, true);
        return false;
    }

    public final boolean hasZippedOverlaysPackage(String str) {
        Bundle bundle;
        boolean z = false;
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
                if (bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
                    z = true;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i(TAG, "hasZippedOverlaysPackage PackageManager.NameNotFoundException: " + e.getMessage());
        }
        Log.i(TAG, "hasZippedOverlaysPackage: " + str + " -- " + z);
        return z;
    }

    public final void cleanLocaleOverlayForDisable(String str) {
        List list;
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        Log.i(TAG, "cleanLocaleOverlayForDisable: " + this.mOverlayManager + ", packageName : " + str);
        if (this.mOverlayManager != null) {
            if (TextUtils.isEmpty(str)) {
                list = this.mOverlayManager.getDisabledOverlaysPackages(this.mLocaleOverlayTargetApks, this.mContext);
            } else {
                List localeOverlayInfosForTarget = this.mOverlayManager.getLocaleOverlayInfosForTarget(str);
                ArrayList arrayList = new ArrayList();
                Iterator it = localeOverlayInfosForTarget.iterator();
                while (it.hasNext()) {
                    arrayList.add(((OverlayInfo) it.next()).packageName);
                }
                list = arrayList;
            }
            String str2 = TAG;
            Log.i(str2, "cleanLocaleOverlayForDisable() overlayPackages:" + list);
            if (list == null || list.isEmpty()) {
                if (str != null) {
                    handleTaskComplete();
                    return;
                }
                return;
            } else {
                this.mOverlayManager.applySamsungConfigChangeOverlays(list, null, 0, null);
                Utils.deleteDisabledLocaleOverlays(list);
                Log.i(str2, "cleanLocaleOverlayForDisable done for --" + list.size());
            }
        }
        if (str != null) {
            handleTaskComplete();
        }
    }

    public void installLocales(Set set, Set set2) {
        boolean z;
        String str = TAG;
        Log.i(str, "installLocales() called with: addedLocales = [" + set + "], deletedLocales = [" + set2 + "]");
        Set set3 = this.mLocaleOverlayTargetApks;
        if (set3 == null || set3.isEmpty()) {
            parseTargetApks();
        }
        if (this.mLocaleOverlayTargetApks.isEmpty()) {
            Log.e(str, "installLocales: No Locale Target apks");
            handleTaskComplete();
            return;
        }
        if (set == null || set.isEmpty()) {
            z = true;
        } else {
            ApkExtractionManager apkExtractionManager = ApkExtractionManager.getInstance();
            apkExtractionManager.setCallback(this);
            apkExtractionManager.extractLocaleApks(this.mLocaleOverlayTargetApks, set, this.mContext);
            z = false;
        }
        if (set2 == null || set2.isEmpty()) {
            return;
        }
        this.mDeletedLocales = new HashSet(set2);
        disableLocales(set2, z);
    }

    public final void disableLocales(final Set set, boolean z) {
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        final LocaleManager localeManager = (LocaleManager) this.mContext.getSystemService(LocaleManager.class);
        final ArrayList arrayList = new ArrayList();
        OMSHelper oMSHelper = this.mOverlayManager;
        if (oMSHelper != null) {
            oMSHelper.getLocaleOverlaysForUser(Utils.getCurrentUserId()).forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LocaleOverlayManager.lambda$disableLocales$0(localeManager, set, arrayList, (OverlayInfo) obj);
                }
            });
        }
        Log.i(TAG, "Disable locales  -> " + set + " packages -> " + arrayList + " OM -> " + this.mOverlayManager);
        ApplyObserver applyObserver = z ? new ApplyObserver(arrayList) : null;
        if (this.mOverlayManager != null && !arrayList.isEmpty()) {
            this.mOverlayManager.applySamsungConfigChangeOverlays(arrayList, null, Utils.getCurrentUserId(), applyObserver);
        } else if (z) {
            handleTaskComplete();
        }
    }

    public static /* synthetic */ void lambda$disableLocales$0(LocaleManager localeManager, Set set, List list, OverlayInfo overlayInfo) {
        if (new File(overlayInfo.baseCodePath).exists() && overlayInfo.isEnabled()) {
            String str = overlayInfo.packageName;
            String substring = str.substring(str.lastIndexOf(46) + 1);
            LocaleList applicationLocales = localeManager.getApplicationLocales(overlayInfo.targetPackageName);
            if (!set.contains(substring) || overlayInfo.packageName.startsWith("com.android.systemui") || Utils.getLocalesListAsSet(applicationLocales).contains(substring)) {
                return;
            }
            list.add(overlayInfo.packageName);
        }
    }

    public void disableUnRequiredLocaleOverlays(Set set) {
        Log.i(TAG, "disableUnRequiredLocaleOverlays() called");
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        if (this.mOverlayManager != null) {
            Iterator it = ((UserManager) this.mContext.getSystemService("user")).semGetUsers().iterator();
            while (it.hasNext()) {
                int semGetIdentifier = ((SemUserInfo) it.next()).getUserHandle().semGetIdentifier();
                List unReqLocaleOverlaysForUser = this.mOverlayManager.getUnReqLocaleOverlaysForUser(semGetIdentifier, set);
                if (unReqLocaleOverlaysForUser != null && !unReqLocaleOverlaysForUser.isEmpty()) {
                    this.mOverlayManager.applySamsungConfigChangeOverlays(unReqLocaleOverlaysForUser, null, semGetIdentifier, null);
                }
            }
        }
    }

    public final void parseTargetApks() {
        this.mLocaleOverlayTargetApks = new HashSet();
        for (ApplicationInfo applicationInfo : this.mContext.getPackageManager().getInstalledApplicationsAsUser(128, Utils.getCurrentUserId())) {
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null && bundle.getBoolean("com.samsung.android.hasZippedOverlays")) {
                this.mLocaleOverlayTargetApks.add(applicationInfo.packageName);
            }
        }
    }

    public final void checkSanityAndCompleteTask() {
        int i = this.mRetryCount + 1;
        this.mRetryCount = i;
        if (i > 5) {
            LogWriter.logDebugInfoAndLogcat(TAG, "checkSanityAndCompleteTask: Max retries done!");
            handleTaskComplete();
            return;
        }
        ArrayList arrayList = new ArrayList(Utils.getSystemLocales());
        Set set = this.mDeletedLocales;
        if (set != null && !set.isEmpty()) {
            arrayList.removeAll(this.mDeletedLocales);
            this.mDeletedLocales = null;
        }
        if (ensureOverlaysEnabled(arrayList)) {
            Log.d(TAG, "checkSanityAndCompleteTask: Task completed successfully!.");
            handleTaskComplete();
        } else {
            Log.e(TAG, "checkSanityAndCompleteTask: Task did not complete successfully. Retry!");
        }
    }

    public final void handleTaskComplete() {
        String str = TAG;
        LogWriter.logDebugInfoAndLogcat(str, "handleTaskComplete -- callers: " + Debug.getCallers(2));
        Utils.setCurrentUserId(0);
        this.mRetryCount = 0;
        this.mService.setRequestInProgress(false);
        this.progressHandler.removeCallbacks(this.progressResetRunnable);
        SharedPreferences preferences = PreferenceUtils.getPreferences(this.mContext);
        OverlayHandler overlayHandler = this.mHandler;
        if (overlayHandler != null) {
            if (!overlayHandler.hasMessages(2) || !this.mHandler.hasMessages(7)) {
                preferences.edit().putBoolean("locale_in_progress", false).commit();
            }
            if (!this.mHandler.hasMessages(3)) {
                preferences.edit().putString("app_in_progress", "None").commit();
            }
        }
        if (this.mSendOverlayChangedBroadcast) {
            OverlayManagerService.broadcastActionOverlayChangedPublic(this.mReParseTargets, Utils.getCurrentUserId());
            this.mReParseTargets = null;
            this.mSendOverlayChangedBroadcast = false;
        }
        Log.i(str, "callbackObserver. mCurrentObserver: " + this.mCurrentObserver);
        OverlayChangeObserver overlayChangeObserver = this.mCurrentObserver;
        if (overlayChangeObserver != null) {
            overlayChangeObserver.onChangeCompleted(true, this.mToken);
        }
        this.mCurrentObserver = null;
        this.mInProgress = false;
        setPackageUpdateTask(false);
        setUpdatedPackage(null);
        LogWriter.syncLogFile();
        OverlayHandler overlayHandler2 = this.mHandler;
        if (overlayHandler2 == null || overlayHandler2.hasAnyMessageOrCallbacks()) {
            return;
        }
        this.mService.waitAndQuit();
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public boolean isCleanupInProgress() {
        return this.mIsCleanupInProgress;
    }

    public final void enableOverlays(List list, boolean z) {
        enableOverlays(list, z, Utils.getCurrentUserId());
    }

    public final void enableOverlays(List list, boolean z, int i) {
        if (this.mOverlayManager == null) {
            initOverlayManager();
        }
        String str = TAG;
        Log.i(str, "enableOverlays() called with: enableLocalePackages" + list + ", OverlayManger = [" + this.mOverlayManager + "]");
        OMSHelper oMSHelper = this.mOverlayManager;
        if (oMSHelper != null) {
            if (!this.mIsPackageUpdateTask && !z) {
                List localeOverlaysForUser = oMSHelper.getLocaleOverlaysForUser(i);
                final ArrayList arrayList = new ArrayList();
                localeOverlaysForUser.forEach(new Consumer() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        LocaleOverlayManager.lambda$enableOverlays$1(arrayList, (OverlayInfo) obj);
                    }
                });
                list.removeAll(arrayList);
                Log.i(str, "enableOverlays(): Skipping = [" + arrayList + "], enabling = [" + list + "]");
            }
            if (list.isEmpty()) {
                handleTaskComplete();
                return;
            }
            LogWriter.logDebugInfoAndLogcat(str, "enableOverlays() called. enableLocalePackages = " + list);
            this.mOverlayManager.applySamsungConfigChangeOverlays(new ArrayList(), list, i, new ApplyObserver(list));
        }
    }

    public static /* synthetic */ void lambda$enableOverlays$1(List list, OverlayInfo overlayInfo) {
        if (new File(overlayInfo.baseCodePath).exists() && overlayInfo.isEnabled()) {
            list.add(overlayInfo.packageName);
        }
    }

    @Override // android.os.HandlerThread
    public boolean quit() {
        String str = TAG;
        Log.i(str, "quit() called");
        ApkExtractionManager.getInstance().release();
        this.mCurrentObserver = null;
        OverlayHandler overlayHandler = this.mHandler;
        if (overlayHandler != null && overlayHandler.hasAnyMessageOrCallbacks()) {
            Log.i(str, "quit() called :Remove pending message or callback ");
            this.mHandler.removeCallbacksAndMessages(null);
        } else {
            Log.i(str, "quit() called no pending messages to remove ");
        }
        return super.quitSafely();
    }

    public class ApplyObserver extends ISamsungOverlayCallback.Stub {
        public List mObservingPackages = new ArrayList();

        public ApplyObserver(List list) {
            if (list == null || list.isEmpty()) {
                LocaleOverlayManager.this.handleTaskComplete();
            } else {
                this.mObservingPackages.addAll(list);
            }
        }

        public synchronized void onOverlayStateChanged(String str, String str2, int i) {
            LogWriter.logDebugInfoAndLogcat(LocaleOverlayManager.TAG, "onOverlayStateChanged() called with: packageName = [" + str2 + "], enabled = [" + i + "] path = " + str);
            if (str2 == null || "".equals(str2)) {
                str2 = str.replace("/data/overlays/current_locale_apks/files/", "").replace(".apk", "");
            }
            this.mObservingPackages.remove(str2);
            if (this.mObservingPackages.isEmpty()) {
                LogWriter.logDebugInfoAndLogcat(LocaleOverlayManager.TAG, "onOverlayStateChanged(): Trying to call checkSanityAndCompleteTask");
                LocaleOverlayManager.this.checkSanityAndCompleteTask();
            }
        }
    }
}
