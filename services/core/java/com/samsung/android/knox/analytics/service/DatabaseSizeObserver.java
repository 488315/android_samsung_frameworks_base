package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.model.EventList;
import com.samsung.android.knox.analytics.util.DatabaseCleanResult;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.UploaderBroadcaster;

public final class DatabaseSizeObserver {
    public static final String COMPRESSED_RESULT_KEY = "performCompressedEventsTransaction";
    public static final String DB_CLEAN_EVENT_COUNT = "rev";
    public static final String DB_CLEAN_EVENT_EVENT_NAME = "databaseCleaned";
    public static final String DB_CLEAN_EVENT_FEATURE = "KNOX_ANALYTICS";
    public static final int DB_CLEAN_EVENT_SCHEMA_VERSION = 1;
    public static final String DB_CLEAN_EVENT_SIZE_PARAMETER = "rsz";
    public static final int DB_MAX_MAX_SIZE_BYTES = 5242880;
    public static final double FACTOR_ALERT = 0.9d;
    public static final double FACTOR_CLEANUP = 0.85d;
    public static final String HT_NAME = "KnoxAnalyticsDatabaseSizeObserver";
    public static final String TAG = "[KnoxAnalytics] DatabaseSizeObserver";
    public final Context mContext;
    public DatabaseSizeContentObserver mDatabaseSizeContentObserver;
    public long mDbAlertSizeInBytes;
    public long mDbMaxSizeInBytes;
    public long mDbTargetSizeInBytes;
    public EventQueue mEventQueue;
    public HandlerThread mHandlerThread;
    public int mFailure = 0;
    public boolean mHasAlertedUploader = false;

    public final class DatabaseSizeContentObserver extends ContentObserver {
        public DatabaseSizeContentObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            long databaseSize =
                    KnoxAnalyticsQueryResolver.getDatabaseSize(DatabaseSizeObserver.this.mContext);
            String str = DatabaseSizeObserver.TAG;
            Log.d(str, "onChange(): currentSize = " + databaseSize);
            DatabaseSizeObserver databaseSizeObserver = DatabaseSizeObserver.this;
            if (!databaseSizeObserver.mHasAlertedUploader
                    && databaseSize > databaseSizeObserver.mDbAlertSizeInBytes) {
                Log.d(str, "onChange(): alert triggered");
                UploaderBroadcaster.broadcastDbSizeWarning(DatabaseSizeObserver.this.mContext);
                DatabaseSizeObserver.this.mHasAlertedUploader = true;
            }
            if (databaseSize >= DatabaseSizeObserver.this.mDbMaxSizeInBytes) {
                Log.d(str, "onChange(): full triggered");
                DatabaseSizeObserver databaseSizeObserver2 = DatabaseSizeObserver.this;
                databaseSizeObserver2.mHasAlertedUploader = false;
                databaseSizeObserver2.startCompression();
            }
        }
    }

    public DatabaseSizeObserver(Context context, EventQueue eventQueue) {
        Log.d(TAG, "DatabaseSizeObserver()");
        this.mContext = context;
        this.mEventQueue = eventQueue;
        calculateDbMaxDbSize();
    }

    public final void calculateDbMaxDbSize() {
        this.mDbMaxSizeInBytes = 5242880L;
        this.mDbAlertSizeInBytes = (int) (5242880 * 0.9d);
        this.mDbTargetSizeInBytes = (int) (5242880 * 0.85d);
        Log.d(
                TAG,
                "calculateDbMaxDbSize(): dbMaxSize = "
                        + this.mDbMaxSizeInBytes
                        + " , dbAlertSize = "
                        + this.mDbAlertSizeInBytes
                        + ", mDbTargetSizeInBytes = "
                        + this.mDbTargetSizeInBytes);
    }

    public final void createDatabaseCleanEvent(long j, long j2) {
        Log.d(
                TAG,
                String.format(
                        "createDatabaseCleanEvent(lastDeletedSize=%d, lastDeletedEventsCount=%d",
                        Long.valueOf(j), Long.valueOf(j2)));
        KnoxAnalyticsData knoxAnalyticsData =
                new KnoxAnalyticsData("KNOX_ANALYTICS", 1, DB_CLEAN_EVENT_EVENT_NAME);
        knoxAnalyticsData.setProperty(DB_CLEAN_EVENT_SIZE_PARAMETER, j);
        knoxAnalyticsData.setProperty(DB_CLEAN_EVENT_COUNT, j2);
        this.mEventQueue.postMessage(3, knoxAnalyticsData);
    }

    public final void createDatabaseCleanEvent(DatabaseCleanResult databaseCleanResult) {
        createDatabaseCleanEvent(
                databaseCleanResult.getDeletedSizeBytes(),
                databaseCleanResult.getDeletedEventsCount());
    }

    public final int getFailureCount() {
        return this.mFailure;
    }

    public final void increaseFailureCount() {
        this.mFailure++;
    }

    public final void resetFailureCount() {
        this.mFailure = 0;
    }

    public final void start() {
        Log.d(TAG, "start()");
        HandlerThread handlerThread = new HandlerThread(HT_NAME);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mDatabaseSizeContentObserver =
                new DatabaseSizeContentObserver(this.mHandlerThread.getThreadHandler());
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Contract.CONTENT_URI, true, this.mDatabaseSizeContentObserver, 0);
    }

    public final void startCleanDatabase() {
        Log.d(TAG, "startCleanDatabase()");
        createDatabaseCleanEvent(
                KnoxAnalyticsQueryResolver.callDatabaseClean(
                        this.mContext, this.mDbTargetSizeInBytes));
    }

    public final void startCompression() {
        if (KnoxAnalyticsQueryResolver.getEventCount(this.mContext) <= 1000 || this.mFailure > 3) {
            Log.d(
                    TAG,
                    "startCompression(): Database is full and there is no sufficient data to"
                        + " compress");
            this.mFailure = 0;
            startCleanDatabase();
            return;
        }
        EventList queryEventChunk = KnoxAnalyticsQueryResolver.queryEventChunk(this.mContext);
        if (queryEventChunk == null || queryEventChunk.length() <= 0) {
            Log.d(TAG, "startCompression(): There is no data in Events table");
            return;
        }
        Bundle performCompressedEventsTransaction =
                KnoxAnalyticsQueryResolver.performCompressedEventsTransaction(
                        this.mContext, queryEventChunk);
        if (performCompressedEventsTransaction == null
                || !performCompressedEventsTransaction.getBoolean(COMPRESSED_RESULT_KEY)) {
            Log.d(TAG, "startCompression(): Some error occurred when adding compressed data.");
            increaseFailureCount();
        } else {
            this.mFailure = 0;
            Log.d(TAG, "startCompression(): Data was compressed successfully.");
        }
    }

    public final void stop() {
        Log.d(TAG, "stop()");
        if (this.mDatabaseSizeContentObserver != null) {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mDatabaseSizeContentObserver);
        }
        this.mDatabaseSizeContentObserver = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mHandlerThread = null;
    }
}
