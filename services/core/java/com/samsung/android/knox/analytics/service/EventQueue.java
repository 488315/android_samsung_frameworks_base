package com.samsung.android.knox.analytics.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.BlacklistedFeature;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.UserManagerHelper;
import com.samsung.android.knox.analytics.util.WhitelistedFeature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EventQueue {
    public static final String API_USAGE_FEATURE_NAME = "API_USAGE";
    public static final String API_USAGE_GET_KEY = "isGetterApi";
    public static final String EVENT_QUEUE_ANALYTICS_DATA_KEY = "KnoxAnalyticsData";
    public static final int EVENT_QUEUE_MSG_CLEANED_LOG_API = 3;
    public static final int EVENT_QUEUE_MSG_KA_DEACTIVATION = 2;
    public static final int EVENT_QUEUE_MSG_LOG_API = 1;
    public static final String HT_NAME = "EventQueue";
    public static final int LOG_WAIT_TIME = 300000;
    public static final int MAX_CACHED_EVENTS = 100;
    public static final String PACKAGE_NAME_FLAG_PROPERTY_NAME = "ReservedKey_Pid_PackageNameFlag";
    public static final String PACKAGE_NAME_KEY = "pN";
    public static final String TAG = "[KnoxAnalytics] EventQueue";
    public static final String USER_TYPE_FLAG_PROPERTY_NAME = "ReservedKey_UserId_UserTypeFlag";
    public static final String USER_TYPE_KEY = "uT";
    public final Context mContext;
    public FeatureBlacklistObserver mFeatureBlacklistObserver;
    public FeatureWhitelistObserver mFeatureWhitelistObserver;
    public EventHandler mHandler;
    public HandlerThread mHandlerThread;
    public Handler mMessageDelayHandler;
    public final Object mDeactivationLock = new Object();
    public boolean mIsStarted = false;
    public final Object mVersioningCompletedLock = new Object();
    public boolean mVersioningCompleted = false;
    public List mEventsCache = new ArrayList();
    public BroadcastReceiver mShutdownReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.knox.analytics.service.EventQueue.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    EventQueue.this.saveCachedLogs();
                }
            };

    public final class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message == null) {
                Log.e(EventQueue.TAG, "handleMessage(): msg is null!");
                return;
            }
            EventQueue.this.waitVersioningCompleted();
            int i = message.what;
            if (i == 1) {
                Log.d(EventQueue.TAG, "EventQueue.handleMessage() - EVENT_QUEUE_MSG_LOG_API");
                EventQueue.this.enqueueEvent(
                        message.getData().getParcelable(EventQueue.EVENT_QUEUE_ANALYTICS_DATA_KEY));
            } else {
                if (i == 2) {
                    Log.d(
                            EventQueue.TAG,
                            "EventQueue.handleMessage() - EVENT_QUEUE_MSG_KA_DEACTIVATION");
                    EventQueue.this.enqueueEvent(
                            message.getData()
                                    .getParcelable(EventQueue.EVENT_QUEUE_ANALYTICS_DATA_KEY));
                    EventQueue.this.stop();
                    return;
                }
                if (i != 3) {
                    Log.e(EventQueue.TAG, "handleMessage(): invalid msg.what");
                    return;
                }
                Log.d(
                        EventQueue.TAG,
                        "EventQueue.handleMessage() - EVENT_QUEUE_MSG_CLEANED_LOG_API");
                KnoxAnalyticsData parcelable =
                        message.getData().getParcelable(EventQueue.EVENT_QUEUE_ANALYTICS_DATA_KEY);
                EventQueue.this.saveCachedLogs();
                EventQueue.this.addEvent(parcelable, 0, false);
            }
        }
    }

    public EventQueue(Context context) {
        Log.d(TAG, "constructor()");
        this.mContext = context;
        context.registerReceiver(
                this.mShutdownReceiver, new IntentFilter("android.intent.action.ACTION_SHUTDOWN"));
    }

    public final void addBulkEvents() {
        String str = TAG;
        Log.d(str, "addBulkEvents()");
        long lastEventId = KnoxAnalyticsQueryResolver.getLastEventId(this.mContext) + 1;
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(
                "eventsList",
                (ArrayList)
                        KnoxAnalyticsDataConverter.formatBulkEvents(
                                lastEventId, this.mEventsCache));
        if (KnoxAnalyticsQueryResolver.addBulkEvents(this.mContext, lastEventId, bundle, 1) == -1) {
            Log.e(str, "addBulkEvents(): error adding events, aborting.");
        } else {
            this.mEventsCache.clear();
        }
    }

    public final void addEvent(KnoxAnalyticsData knoxAnalyticsData, int i) {
        addEvent(knoxAnalyticsData, i, false);
    }

    public final void addEvent(KnoxAnalyticsData knoxAnalyticsData, int i, boolean z) {
        if (!z) {
            if (!checkEventFeatureWhitelisted(knoxAnalyticsData)
                    && checkEventFeatureBlacklisted(knoxAnalyticsData)) {
                Log.d(TAG, "addEvent(): feature blacklisted, discarding event.");
                return;
            } else {
                checkFillUserType(knoxAnalyticsData);
                checkFillPackageName(knoxAnalyticsData);
            }
        }
        knoxAnalyticsData.eventId = KnoxAnalyticsQueryResolver.getLastEventId(this.mContext) + 1;
        if (KnoxAnalyticsQueryResolver.addEvent(
                        this.mContext,
                        knoxAnalyticsData.eventId,
                        KnoxAnalyticsDataConverter.toJSONString(knoxAnalyticsData),
                        i)
                == -1) {
            Log.e(TAG, "addEvent(): error adding event, aborting.");
        }
    }

    public final void cancelMessageHandler() {
        this.mMessageDelayHandler.removeCallbacksAndMessages(null);
    }

    public final boolean checkEventFeatureBlacklisted(KnoxAnalyticsData knoxAnalyticsData) {
        String str = TAG;
        Log.d(str, "checkEventFeatureBlacklisted()");
        FeatureBlacklistObserver featureBlacklistObserver = this.mFeatureBlacklistObserver;
        if (featureBlacklistObserver == null) {
            Log.e(str, "Features Blacklist Observer is null, can't check!");
            return false;
        }
        List list = featureBlacklistObserver.mFeaturesBlacklistCache;
        if (list == null) {
            Log.e(str, "Features Blacklist is null, can't check!");
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((BlacklistedFeature) it.next())
                    .isBlacklisted(knoxAnalyticsData.getFeature(), knoxAnalyticsData.getEvent())) {
                return true;
            }
        }
        return false;
    }

    public final boolean checkEventFeatureWhitelisted(KnoxAnalyticsData knoxAnalyticsData) {
        String str = TAG;
        Log.d(str, "checkEventFeatureWhitelisted()");
        FeatureWhitelistObserver featureWhitelistObserver = this.mFeatureWhitelistObserver;
        if (featureWhitelistObserver == null) {
            Log.e(str, "Features Whitelist Observer is null, can't check!");
            return false;
        }
        List list = featureWhitelistObserver.mFeaturesWhitelistCache;
        if (list == null) {
            Log.e(str, "Features Whitelist is null, can't check!");
            return false;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WhitelistedFeature whitelistedFeature = (WhitelistedFeature) it.next();
            if (whitelistedFeature.hasFeatureName(knoxAnalyticsData.getFeature())) {
                if (whitelistedFeature.getEnableApi() == WhitelistedFeature.EnableApi.ALL) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void checkFillPackageName(KnoxAnalyticsData knoxAnalyticsData) {
        String str = TAG;
        Log.d(str, "checkFillPackageName()");
        if (knoxAnalyticsData.getPayload().containsKey(PACKAGE_NAME_FLAG_PROPERTY_NAME)) {
            int i = knoxAnalyticsData.getPayload().getInt(PACKAGE_NAME_FLAG_PROPERTY_NAME, -1);
            knoxAnalyticsData.getPayload().remove(PACKAGE_NAME_FLAG_PROPERTY_NAME);
            if (i == -1) {
                Log.e(str, "checkFillPackageName() - invalid pid.");
                return;
            }
            String appNameByPID = getAppNameByPID(i);
            if (appNameByPID == null || appNameByPID.isEmpty()) {
                return;
            }
            knoxAnalyticsData.setProperty("pN", appNameByPID);
        }
    }

    public final void checkFillUserType(KnoxAnalyticsData knoxAnalyticsData) {
        String str = TAG;
        Log.d(str, "checkFillUserType()");
        if (knoxAnalyticsData.getPayload().containsKey(USER_TYPE_FLAG_PROPERTY_NAME)) {
            int i = knoxAnalyticsData.getPayload().getInt(USER_TYPE_FLAG_PROPERTY_NAME, -1);
            knoxAnalyticsData.getPayload().remove(USER_TYPE_FLAG_PROPERTY_NAME);
            if (i == -1) {
                Log.e(str, "checkFillUserType() - invalid userId " + i);
            } else {
                int userType = new UserManagerHelper(this.mContext).getUserType(i);
                if (userType == -1) {
                    Log.e(str, "checkFillUserType() - Couldn't get userType");
                } else {
                    knoxAnalyticsData.setProperty(USER_TYPE_KEY, userType);
                }
            }
        }
    }

    public final void enqueueEvent(KnoxAnalyticsData knoxAnalyticsData) {
        if (!checkEventFeatureWhitelisted(knoxAnalyticsData)
                && checkEventFeatureBlacklisted(knoxAnalyticsData)) {
            Log.d(TAG, "enqueueEvent(): feature blacklisted, discarding event.");
            return;
        }
        checkFillUserType(knoxAnalyticsData);
        checkFillPackageName(knoxAnalyticsData);
        this.mEventsCache.add(knoxAnalyticsData);
        if (this.mEventsCache.size() == 100) {
            saveCachedLogs();
        } else {
            restartMessageHandler();
        }
    }

    public final String getAppNameByPID(int i) {
        String str = TAG;
        Log.d(str, "getAppNameByPID(" + i + ")");
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses =
                ((ActivityManager) this.mContext.getSystemService("activity"))
                        .getRunningAppProcesses();
        if (runningAppProcesses == null) {
            Log.e(str, "getAppNameByPID(): nill processInfoList");
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo != null && runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        Log.e(TAG, "getAppNameByPID(): not found");
        return null;
    }

    public final boolean isStarted() {
        return this.mIsStarted;
    }

    public final void notifyVersioningCompleted() {
        Log.d(TAG, "notifyVersioningCompleted()");
        synchronized (this.mVersioningCompletedLock) {
            this.mVersioningCompleted = true;
            this.mVersioningCompletedLock.notifyAll();
        }
    }

    public final void postMessage(int i, KnoxAnalyticsData knoxAnalyticsData) {
        synchronized (this.mDeactivationLock) {
            try {
                EventHandler eventHandler = this.mHandler;
                if (eventHandler != null) {
                    Message obtainMessage = eventHandler.obtainMessage(i);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EVENT_QUEUE_ANALYTICS_DATA_KEY, knoxAnalyticsData);
                    obtainMessage.setData(bundle);
                    this.mHandler.sendMessage(obtainMessage);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void restartMessageHandler() {
        cancelMessageHandler();
        this.mMessageDelayHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.knox.analytics.service.EventQueue.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        EventQueue.this.saveCachedLogs();
                    }
                },
                300000L);
    }

    public final synchronized void saveCachedLogs() {
        try {
            Log.d(TAG, "saveCachedLogs(): Number of events: " + this.mEventsCache.size());
            if (this.mEventsCache.size() == 0) {
                return;
            }
            cancelMessageHandler();
            if (this.mEventsCache.size() > 1) {
                addBulkEvents();
            } else {
                addEvent((KnoxAnalyticsData) this.mEventsCache.get(0), 1, true);
                this.mEventsCache.clear();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void start() {
        synchronized (this.mDeactivationLock) {
            try {
                Log.d(TAG, "start()");
                if (this.mHandlerThread == null) {
                    HandlerThread handlerThread = new HandlerThread(HT_NAME);
                    this.mHandlerThread = handlerThread;
                    handlerThread.start();
                }
                if (this.mHandler == null) {
                    this.mHandler = new EventHandler(this.mHandlerThread.getLooper());
                }
                if (this.mMessageDelayHandler == null) {
                    this.mMessageDelayHandler = new Handler(this.mHandlerThread.getLooper());
                }
                if (this.mFeatureBlacklistObserver == null) {
                    FeatureBlacklistObserver featureBlacklistObserver =
                            new FeatureBlacklistObserver(this.mContext);
                    this.mFeatureBlacklistObserver = featureBlacklistObserver;
                    featureBlacklistObserver.start();
                }
                if (this.mFeatureWhitelistObserver == null) {
                    FeatureWhitelistObserver featureWhitelistObserver =
                            new FeatureWhitelistObserver(this.mContext);
                    this.mFeatureWhitelistObserver = featureWhitelistObserver;
                    featureWhitelistObserver.start();
                }
                this.mIsStarted = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stop() {
        synchronized (this.mDeactivationLock) {
            try {
                Log.d(TAG, "stop()");
                this.mIsStarted = false;
                EventHandler eventHandler = this.mHandler;
                if (eventHandler != null) {
                    eventHandler.removeMessages(1);
                }
                saveCachedLogs();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void waitVersioningCompleted() {
        while (!this.mVersioningCompleted) {
            String str = TAG;
            Log.d(str, "waitVersioningCompleted(): waiting");
            try {
                synchronized (this.mVersioningCompletedLock) {
                    this.mVersioningCompletedLock.wait(10000L);
                }
                Log.d(str, "waitVersioningCompleted(): after wait");
            } catch (InterruptedException unused) {
                Log.e(TAG, "waitVersioningCompleted(): Interrupted exception");
            }
        }
        Log.d(TAG, "waitVersioningCompleted(): done");
    }
}
