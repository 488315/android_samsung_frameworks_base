package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class LocaleOverlayManagerWrapper implements ILocaleOverlayManager {
    public static final String TAG = "LocaleOverlayManagerWrapper";
    public static LocaleOverlayManagerWrapper sInstance;
    public final Context mContext;
    public LocaleOverlayManager mManager;
    public boolean mRequestInProgress = false;
    public final ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    public final Queue mPendingRequestQueue = new ConcurrentLinkedQueue();
    public Runnable mPendingActionRunnable = new Runnable() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            LocaleOverlayManagerWrapper.this.requestPendingActions();
        }
    };
    public Runnable mQuitRunnable = new Runnable() { // from class: com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            LocaleOverlayManagerWrapper.this.waitAndQuit();
        }
    };

    public LocaleOverlayManagerWrapper(Context context) {
        this.mContext = context;
        Utils.setCurrentUserId(Process.myUserHandle().semGetIdentifier());
        initManager();
        requestPendingActions();
    }

    public static synchronized LocaleOverlayManagerWrapper getInstance(Context context) {
        LocaleOverlayManagerWrapper localeOverlayManagerWrapper;
        synchronized (LocaleOverlayManagerWrapper.class) {
            if (sInstance == null) {
                sInstance = new LocaleOverlayManagerWrapper(context);
            }
            localeOverlayManagerWrapper = sInstance;
        }
        return localeOverlayManagerWrapper;
    }

    public static void clearInstance() {
        sInstance = null;
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void applyLocales(LocaleList localeList, int i, OverlayChangeObserver overlayChangeObserver) {
        LogWriter.logDebugInfoAndLogcat(TAG, "applyLocales() called with: localeList = [" + localeList + "], userId = [" + i + "], observer = [" + overlayChangeObserver + "]");
        cancelQuit();
        localeChanged(localeList, i, overlayChangeObserver);
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public boolean applyLocalesForPackage(String str, int i, int i2, OverlayChangeObserver overlayChangeObserver) {
        LogWriter.logDebugInfoAndLogcat(TAG, "applyLocalesForPackage() called with: packageName = [" + str + "], token = [" + i + "], observer = [" + overlayChangeObserver + "]");
        cancelQuit();
        packageInstalled(str, i, i2, overlayChangeObserver);
        return true;
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void applyPerAppLocale(LocaleList localeList, String str, int i) {
        LogWriter.logDebugInfoAndLogcat(TAG, "applyPerAppLocale() called with: localeList = [" + localeList + "], packageName = [" + str + "], userId = [" + i + "]");
        if (localeList == null || str == null) {
            return;
        }
        localeChangedPerApp(localeList, str, i);
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void initializeOverlaysForSafeMode() {
        Context context = this.mContext;
        if (context == null || !PreferenceUtils.getPreferences(context).getBoolean("safeMode", false)) {
            return;
        }
        init(true, false);
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void initializeOverlays(boolean z) {
        Context context = this.mContext;
        if (context != null) {
            PreferenceUtils.getPreferences(context).edit().putBoolean("safeMode", z).commit();
            init(false, false);
        }
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void cleanUpOverlays() {
        if (this.mContext != null) {
            init(false, true);
        }
    }

    @Override // com.samsung.android.localeoverlaymanager.ILocaleOverlayManager
    public void checkSanityOfOverlays(int i) {
        LogWriter.logDebugInfoAndLogcat(TAG, "checkSanityOfOverlays() called with: userId = [" + i + "], mContext = " + this.mContext);
        if (this.mContext == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "com.samsung.android.localeoverlaymanager.action.JOB_SCHEDULED");
        bundle.putInt("userId", i);
        this.mPendingRequestQueue.add(new Pair(bundle, null));
        requestPendingActions();
    }

    public final void init(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "init_on_boot");
        bundle.putBoolean("safeMode", z);
        bundle.putBoolean("startCleanUpOverlay", z2);
        this.mPendingRequestQueue.add(new Pair(bundle, null));
        requestPendingActions();
    }

    public final void packageInstalled(String str, int i, int i2, OverlayChangeObserver overlayChangeObserver) {
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "com.samsung.android.localeoverlaymanager.action.PACKAGE_ADDED");
        bundle.putString("added_package", str);
        bundle.putInt(KnoxCustomManagerService.SPCM_KEY_TOKEN, i);
        bundle.putInt("userId", i2);
        this.mPendingRequestQueue.add(new Pair(bundle, overlayChangeObserver));
        requestPendingActions();
    }

    public final void cancelQuit() {
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager == null || localeOverlayManager.getHandler() == null) {
            return;
        }
        this.mManager.getHandler().removeCallbacks(this.mQuitRunnable);
    }

    public final void initManager() {
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null && localeOverlayManager.isAlive()) {
            Log.i(TAG, "initManager thread not getting created");
            return;
        }
        Log.i(TAG, "initManager thread creation");
        LocaleOverlayManager localeOverlayManager2 = new LocaleOverlayManager("ResourceOverlayService", this);
        this.mManager = localeOverlayManager2;
        localeOverlayManager2.start();
        this.mManager.setContext(this.mContext);
    }

    public void setRequestInProgress(boolean z) {
        this.mRequestInProgress = z;
    }

    public void doDestroy() {
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager == null || localeOverlayManager.isInProgress() || !this.mPendingRequestQueue.isEmpty()) {
            return;
        }
        if (this.mManager.getHandler() != null) {
            this.mManager.getHandler().removeCallbacks(this.mQuitRunnable);
        }
        this.mManager.quit();
        this.mManager = null;
    }

    public synchronized void localeChanged(LocaleList localeList, int i, OverlayChangeObserver overlayChangeObserver) {
        LogWriter.logDebugInfoAndLogcat(TAG, "localeChanged localeList " + localeList);
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < localeList.size(); i2++) {
            String language = localeList.get(i2).getLanguage();
            if (language.length() == 3) {
                Log.i(TAG, "localeChanged: trying to get ISO_639_1 mapping for locale: " + language);
                language = (String) OverlayConstants.ISO_639_2_TO_639_1_MAPPING.get(language);
            }
            if (language != null) {
                hashSet.add(language);
            }
        }
        Log.i(TAG, "CurrentLocales list from config - " + hashSet);
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "android.intent.action.LOCALE_CHANGED");
        bundle.putStringArrayList("config_locale_list", new ArrayList<>(hashSet));
        bundle.putInt("userId", i);
        this.mPendingRequestQueue.add(new Pair(bundle, overlayChangeObserver));
        requestPendingActions();
    }

    public synchronized void localeChangedPerApp(LocaleList localeList, String str, int i) {
        String str2 = TAG;
        Log.i(str2, "APK_OPTIMIZATION localeChangedPerApp localeList " + localeList);
        Set localesListAsSet = Utils.getLocalesListAsSet(localeList);
        Log.i(str2, "APK_OPTIMIZATION CurrentLocales list from config - " + localesListAsSet);
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "com.samsung.android.localeoverlaymanager.action.MSG_HANDLE_PER_APP_LOCALE");
        bundle.putString("perAppPackageName", str);
        bundle.putStringArrayList("config_locale_list", new ArrayList<>(localesListAsSet));
        bundle.putInt("userId", i);
        this.mPendingRequestQueue.add(new Pair(bundle, null));
        requestPendingActions();
    }

    public synchronized void requestPendingActions() {
        Bundle bundle;
        if (this.mPendingRequestQueue.isEmpty()) {
            Log.i(TAG, "requestPendingActions() called - No pending actions!");
            return;
        }
        cancelQuit();
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null && localeOverlayManager.isAlive()) {
            if (this.mManager.getHandler() == null) {
                Log.e(TAG, "requestPendingAction delayed, handler -> " + this.mManager.getHandler());
                this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
                return;
            }
            if (!this.mRequestInProgress && !this.mManager.isInProgress()) {
                Pair pair = (Pair) this.mPendingRequestQueue.poll();
                Log.e(TAG, "requestPendingAction sending bundle " + pair);
                if (pair != null && (bundle = (Bundle) pair.first) != null) {
                    this.mRequestInProgress = true;
                    Message obtain = Message.obtain(null, 1, bundle);
                    this.mManager.setObserver((OverlayChangeObserver) pair.second);
                    this.mManager.getHandler().sendMessage(obtain);
                }
            }
            if (this.mRequestInProgress || !this.mPendingRequestQueue.isEmpty()) {
                this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
            }
            return;
        }
        Log.e(TAG, "requestPendingAction delayed: Manager not alive");
        initManager();
        this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
    }

    public void waitAndQuit() {
        Log.i(TAG, "waitAndQuit called");
        synchronized (this) {
            if (!this.mPendingRequestQueue.isEmpty()) {
                requestPendingActions();
                return;
            }
            LocaleOverlayManager localeOverlayManager = this.mManager;
            if (localeOverlayManager != null && localeOverlayManager.getHandler() != null) {
                if (this.mManager.isCleanupInProgress()) {
                    this.mManager.getHandler().removeCallbacks(this.mQuitRunnable);
                    this.mManager.getHandler().postDelayed(this.mQuitRunnable, 60000L);
                } else {
                    clearInstance();
                    this.mManager.getHandler().removeCallbacks(this.mQuitRunnable);
                    doDestroy();
                }
            }
        }
    }
}
