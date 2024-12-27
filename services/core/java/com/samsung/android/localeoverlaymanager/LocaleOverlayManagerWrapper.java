package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.util.Pair;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.am.OverlayChangeObserverImpl;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class LocaleOverlayManagerWrapper {
    public static LocaleOverlayManagerWrapper sInstance;
    public final Context mContext;
    public LocaleOverlayManager mManager;
    public final LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0 mPendingActionRunnable;
    public final LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0 mQuitRunnable;
    public boolean mRequestInProgress = false;
    public final ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    public final Queue mPendingRequestQueue = new ConcurrentLinkedQueue();

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0] */
    public LocaleOverlayManagerWrapper(Context context) {
        final int i = 0;
        this.mPendingActionRunnable =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0
                    public final /* synthetic */ LocaleOverlayManagerWrapper f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        LocaleOverlayManagerWrapper localeOverlayManagerWrapper = this.f$0;
                        switch (i2) {
                            case 0:
                                localeOverlayManagerWrapper.requestPendingActions();
                                break;
                            default:
                                localeOverlayManagerWrapper.waitAndQuit();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mQuitRunnable =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.localeoverlaymanager.LocaleOverlayManagerWrapper$$ExternalSyntheticLambda0
                    public final /* synthetic */ LocaleOverlayManagerWrapper f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        LocaleOverlayManagerWrapper localeOverlayManagerWrapper = this.f$0;
                        switch (i22) {
                            case 0:
                                localeOverlayManagerWrapper.requestPendingActions();
                                break;
                            default:
                                localeOverlayManagerWrapper.waitAndQuit();
                                break;
                        }
                    }
                };
        this.mContext = context;
        int semGetIdentifier = Process.myUserHandle().semGetIdentifier();
        Utils.setCurrentUserId(semGetIdentifier);
        LogWriter.logDebugInfoAndLogcat(
                "LocaleOverlayManagerWrapper", "onCreate() called. UserId: " + semGetIdentifier);
        initManager();
        requestPendingActions();
    }

    public static synchronized LocaleOverlayManagerWrapper getInstance(Context context) {
        LocaleOverlayManagerWrapper localeOverlayManagerWrapper;
        synchronized (LocaleOverlayManagerWrapper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new LocaleOverlayManagerWrapper(context);
                }
                localeOverlayManagerWrapper = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localeOverlayManagerWrapper;
    }

    public final void applyLocales(
            LocaleList localeList, int i, OverlayChangeObserverImpl overlayChangeObserverImpl) {
        LocaleOverlayManager.OverlayHandler overlayHandler;
        LogWriter.logDebugInfoAndLogcat(
                "LocaleOverlayManagerWrapper",
                "applyLocales() called with: localeList = ["
                        + localeList
                        + "], userId = ["
                        + i
                        + "], observer = ["
                        + overlayChangeObserverImpl
                        + "]");
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null
                && (overlayHandler = localeOverlayManager.mHandler) != null) {
            overlayHandler.removeCallbacks(this.mQuitRunnable);
        }
        synchronized (this) {
            try {
                LogWriter.logDebugInfoAndLogcat(
                        "LocaleOverlayManagerWrapper", "localeChanged localeList " + localeList);
                HashSet hashSet = new HashSet();
                for (int i2 = 0; i2 < localeList.size(); i2++) {
                    String language = localeList.get(i2).getLanguage();
                    if (language.length() == 3) {
                        Log.i(
                                "LocaleOverlayManagerWrapper",
                                "localeChanged: trying to get ISO_639_1 mapping for locale: "
                                        + language);
                        language =
                                (String)
                                        ((HashMap) OverlayConstants.ISO_639_2_TO_639_1_MAPPING)
                                                .get(language);
                    }
                    if (language != null) {
                        hashSet.add(language);
                    }
                }
                Utils.handleNewLocaleCodes(hashSet);
                Log.i(
                        "LocaleOverlayManagerWrapper",
                        "CurrentLocales list from config - " + hashSet);
                Bundle bundle = new Bundle();
                bundle.putString("pending_action", "android.intent.action.LOCALE_CHANGED");
                bundle.putStringArrayList("config_locale_list", new ArrayList<>(hashSet));
                bundle.putInt("userId", i);
                ((ConcurrentLinkedQueue) this.mPendingRequestQueue)
                        .add(new Pair(bundle, overlayChangeObserverImpl));
                requestPendingActions();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void applyLocalesForPackage(
            String str,
            int i,
            int i2,
            com.android.server.pm.OverlayChangeObserverImpl overlayChangeObserverImpl) {
        LocaleOverlayManager.OverlayHandler overlayHandler;
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "applyLocalesForPackage() called with: packageName = [",
                        str,
                        "], token = [",
                        "], observer = [");
        m.append(overlayChangeObserverImpl);
        m.append("]");
        LogWriter.logDebugInfoAndLogcat("LocaleOverlayManagerWrapper", m.toString());
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null
                && (overlayHandler = localeOverlayManager.mHandler) != null) {
            overlayHandler.removeCallbacks(this.mQuitRunnable);
        }
        Bundle bundle = new Bundle();
        bundle.putString(
                "pending_action", "com.samsung.android.localeoverlaymanager.action.PACKAGE_ADDED");
        bundle.putString("added_package", str);
        bundle.putInt(KnoxCustomManagerService.SPCM_KEY_TOKEN, i);
        bundle.putInt("userId", i2);
        ((ConcurrentLinkedQueue) this.mPendingRequestQueue)
                .add(new Pair(bundle, overlayChangeObserverImpl));
        requestPendingActions();
    }

    public final void applyPerAppLocale(LocaleList localeList, String str, int i) {
        LogWriter.logDebugInfoAndLogcat(
                "LocaleOverlayManagerWrapper",
                "applyPerAppLocale() called with: localeList = ["
                        + localeList
                        + "], packageName = ["
                        + str
                        + "], userId = ["
                        + i
                        + "]");
        if (localeList == null || str == null) {
            return;
        }
        synchronized (this) {
            Log.i(
                    "LocaleOverlayManagerWrapper",
                    "APK_OPTIMIZATION localeChangedPerApp localeList " + localeList);
            Set localesListAsSet = Utils.getLocalesListAsSet(localeList);
            Log.i(
                    "LocaleOverlayManagerWrapper",
                    "APK_OPTIMIZATION CurrentLocales list from config - " + localesListAsSet);
            Bundle bundle = new Bundle();
            bundle.putString(
                    "pending_action",
                    "com.samsung.android.localeoverlaymanager.action.MSG_HANDLE_PER_APP_LOCALE");
            bundle.putString("perAppPackageName", str);
            bundle.putStringArrayList("config_locale_list", new ArrayList<>(localesListAsSet));
            bundle.putInt("userId", i);
            ((ConcurrentLinkedQueue) this.mPendingRequestQueue).add(new Pair(bundle, null));
            requestPendingActions();
        }
    }

    public final void checkSanityOfOverlays(int i) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "checkSanityOfOverlays() called with: userId = [", "], mContext = ");
        m.append(this.mContext);
        LogWriter.logDebugInfoAndLogcat("LocaleOverlayManagerWrapper", m.toString());
        if (this.mContext == null) {
            return;
        }
        Bundle m2 =
                FreecessController$$ExternalSyntheticOutline0.m(
                        i,
                        "pending_action",
                        "com.samsung.android.localeoverlaymanager.action.JOB_SCHEDULED",
                        "userId");
        ((ConcurrentLinkedQueue) this.mPendingRequestQueue).add(new Pair(m2, null));
        requestPendingActions();
    }

    public final void init(boolean z, boolean z2) {
        Bundle bundle = new Bundle();
        bundle.putString("pending_action", "init_on_boot");
        bundle.putBoolean("safeMode", z);
        bundle.putBoolean("startCleanUpOverlay", z2);
        ((ConcurrentLinkedQueue) this.mPendingRequestQueue).add(new Pair(bundle, null));
        requestPendingActions();
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda0] */
    public final void initManager() {
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null && localeOverlayManager.isAlive()) {
            Log.i("LocaleOverlayManagerWrapper", "initManager thread not getting created");
            return;
        }
        Log.i("LocaleOverlayManagerWrapper", "initManager thread creation");
        final LocaleOverlayManager localeOverlayManager2 =
                new LocaleOverlayManager("ResourceOverlayService");
        localeOverlayManager2.mInProgress = false;
        localeOverlayManager2.progressResetRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.localeoverlaymanager.LocaleOverlayManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LocaleOverlayManager.this.checkSanityAndCompleteTask();
                    }
                };
        localeOverlayManager2.initOverlayManager();
        localeOverlayManager2.mService = this;
        this.mManager = localeOverlayManager2;
        localeOverlayManager2.start();
        this.mManager.mContext = this.mContext;
    }

    public final synchronized void requestPendingActions() {
        Bundle bundle;
        LocaleOverlayManager.OverlayHandler overlayHandler;
        if (((ConcurrentLinkedQueue) this.mPendingRequestQueue).isEmpty()) {
            Log.i(
                    "LocaleOverlayManagerWrapper",
                    "requestPendingActions() called - No pending actions!");
            return;
        }
        LocaleOverlayManager localeOverlayManager = this.mManager;
        if (localeOverlayManager != null
                && (overlayHandler = localeOverlayManager.mHandler) != null) {
            overlayHandler.removeCallbacks(this.mQuitRunnable);
        }
        LocaleOverlayManager localeOverlayManager2 = this.mManager;
        if (localeOverlayManager2 != null && localeOverlayManager2.isAlive()) {
            LocaleOverlayManager localeOverlayManager3 = this.mManager;
            if (localeOverlayManager3.mHandler == null) {
                Log.e(
                        "LocaleOverlayManagerWrapper",
                        "requestPendingAction delayed, handler -> " + this.mManager.mHandler);
                this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
                return;
            }
            if (!this.mRequestInProgress && !localeOverlayManager3.mInProgress) {
                Pair pair = (Pair) ((ConcurrentLinkedQueue) this.mPendingRequestQueue).poll();
                Log.e("LocaleOverlayManagerWrapper", "requestPendingAction sending bundle " + pair);
                if (pair != null && (bundle = (Bundle) pair.first) != null) {
                    this.mRequestInProgress = true;
                    Message obtain = Message.obtain(null, 1, bundle);
                    LocaleOverlayManager localeOverlayManager4 = this.mManager;
                    localeOverlayManager4.mCurrentObserver = (OverlayChangeObserver) pair.second;
                    localeOverlayManager4.mHandler.sendMessage(obtain);
                }
            }
            if (this.mRequestInProgress
                    || !((ConcurrentLinkedQueue) this.mPendingRequestQueue).isEmpty()) {
                this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
            }
            return;
        }
        Log.e("LocaleOverlayManagerWrapper", "requestPendingAction delayed: Manager not alive");
        initManager();
        this.mExecutor.schedule(this.mPendingActionRunnable, 100L, TimeUnit.MILLISECONDS);
    }

    public final void waitAndQuit() {
        LocaleOverlayManager.OverlayHandler overlayHandler;
        Log.i("LocaleOverlayManagerWrapper", "waitAndQuit called");
        synchronized (this) {
            try {
                if (!((ConcurrentLinkedQueue) this.mPendingRequestQueue).isEmpty()) {
                    requestPendingActions();
                    return;
                }
                LocaleOverlayManager localeOverlayManager = this.mManager;
                if (localeOverlayManager != null
                        && (overlayHandler = localeOverlayManager.mHandler) != null) {
                    if (localeOverlayManager.mIsCleanupInProgress) {
                        overlayHandler.removeCallbacks(this.mQuitRunnable);
                        this.mManager.mHandler.postDelayed(this.mQuitRunnable, 60000L);
                    } else {
                        sInstance = null;
                        overlayHandler.removeCallbacks(this.mQuitRunnable);
                        LocaleOverlayManager localeOverlayManager2 = this.mManager;
                        if (localeOverlayManager2 != null
                                && !localeOverlayManager2.mInProgress
                                && ((ConcurrentLinkedQueue) this.mPendingRequestQueue).isEmpty()) {
                            LocaleOverlayManager.OverlayHandler overlayHandler2 =
                                    this.mManager.mHandler;
                            if (overlayHandler2 != null) {
                                overlayHandler2.removeCallbacks(this.mQuitRunnable);
                            }
                            this.mManager.quit();
                            this.mManager = null;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
