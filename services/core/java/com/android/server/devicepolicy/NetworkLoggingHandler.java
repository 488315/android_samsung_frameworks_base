package com.android.server.devicepolicy;

import android.app.AlarmManager;
import android.app.admin.NetworkEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.LongSparseArray;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public final class NetworkLoggingHandler extends Handler {
    public static final long FORCE_FETCH_THROTTLE_NS = TimeUnit.SECONDS.toNanos(10);
    static final int LOG_NETWORK_EVENT_MSG = 1;
    public final AlarmManager mAlarmManager;
    public final AnonymousClass1 mBatchTimeoutAlarmListener;
    public final LongSparseArray mBatches;
    public long mCurrentBatchToken;
    public final DevicePolicyManagerService mDpm;
    public long mId;
    public long mLastFinalizationNanos;
    public long mLastRetrievedBatchToken;
    public ArrayList mNetworkEvents;
    public boolean mPaused;
    public final int mTargetUserId;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.server.devicepolicy.NetworkLoggingHandler$1] */
    public NetworkLoggingHandler(
            Looper looper, DevicePolicyManagerService devicePolicyManagerService, long j, int i) {
        super(looper);
        this.mLastFinalizationNanos = -1L;
        this.mBatchTimeoutAlarmListener =
                new AlarmManager
                        .OnAlarmListener() { // from class:
                                             // com.android.server.devicepolicy.NetworkLoggingHandler.1
                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        Bundle finalizeBatchAndBuildAdminMessageLocked;
                        int i2 = NetworkLoggingHandler.LOG_NETWORK_EVENT_MSG;
                        Slog.d(
                                "NetworkLoggingHandler",
                                "Received a batch finalization timeout alarm, finalizing "
                                        + NetworkLoggingHandler.this.mNetworkEvents.size()
                                        + " pending events.");
                        synchronized (NetworkLoggingHandler.this) {
                            finalizeBatchAndBuildAdminMessageLocked =
                                    NetworkLoggingHandler.this
                                            .finalizeBatchAndBuildAdminMessageLocked();
                        }
                        if (finalizeBatchAndBuildAdminMessageLocked != null) {
                            NetworkLoggingHandler.this.notifyDeviceOwnerOrProfileOwner(
                                    finalizeBatchAndBuildAdminMessageLocked);
                        }
                    }
                };
        this.mNetworkEvents = new ArrayList();
        this.mBatches = new LongSparseArray(5);
        this.mPaused = false;
        this.mDpm = devicePolicyManagerService;
        this.mAlarmManager =
                (AlarmManager)
                        devicePolicyManagerService.mInjector.mContext.getSystemService(
                                AlarmManager.class);
        this.mId = j;
        this.mTargetUserId = i;
    }

    public final Bundle buildAdminMessageLocked() {
        Bundle bundle = new Bundle();
        int size = ((ArrayList) this.mBatches.valueAt(r1.size() - 1)).size();
        bundle.putLong("android.app.extra.EXTRA_NETWORK_LOGS_TOKEN", this.mCurrentBatchToken);
        bundle.putInt("android.app.extra.EXTRA_NETWORK_LOGS_COUNT", size);
        return bundle;
    }

    public final Bundle finalizeBatchAndBuildAdminMessageLocked() {
        Bundle bundle;
        this.mLastFinalizationNanos = System.nanoTime();
        if (this.mNetworkEvents.size() > 0) {
            Iterator it = this.mNetworkEvents.iterator();
            while (it.hasNext()) {
                ((NetworkEvent) it.next()).setId(this.mId);
                long j = this.mId;
                if (j == Long.MAX_VALUE) {
                    Slog.i(
                            "NetworkLoggingHandler",
                            "Reached maximum id value; wrapping around ."
                                    + this.mCurrentBatchToken);
                    this.mId = 0L;
                } else {
                    this.mId = j + 1;
                }
            }
            if (this.mBatches.size() >= 5) {
                this.mBatches.removeAt(0);
            }
            long j2 = this.mCurrentBatchToken + 1;
            this.mCurrentBatchToken = j2;
            this.mBatches.append(j2, this.mNetworkEvents);
            this.mNetworkEvents = new ArrayList();
            if (!this.mPaused) {
                bundle = buildAdminMessageLocked();
                scheduleBatchFinalization();
                return bundle;
            }
        } else {
            BatteryService$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "Was about to finalize the batch, but there were no events to send to"
                                + " the DPC, the batchToken of last available batch: "),
                    this.mCurrentBatchToken,
                    "NetworkLoggingHandler");
        }
        bundle = null;
        scheduleBatchFinalization();
        return bundle;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        Bundle finalizeBatchAndBuildAdminMessageLocked;
        if (message.what != 1) {
            Slog.d(
                    "NetworkLoggingHandler",
                    "NetworkLoggingHandler received an unknown of message.");
            return;
        }
        NetworkEvent networkEvent =
                (NetworkEvent) message.getData().getParcelable("network_event", NetworkEvent.class);
        if (networkEvent != null) {
            synchronized (this) {
                try {
                    this.mNetworkEvents.add(networkEvent);
                    finalizeBatchAndBuildAdminMessageLocked =
                            this.mNetworkEvents.size() >= 1200
                                    ? finalizeBatchAndBuildAdminMessageLocked()
                                    : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (finalizeBatchAndBuildAdminMessageLocked != null) {
                notifyDeviceOwnerOrProfileOwner(finalizeBatchAndBuildAdminMessageLocked);
            }
        }
    }

    public final void notifyDeviceOwnerOrProfileOwner(Bundle bundle) {
        if (Thread.holdsLock(this)) {
            Slog.wtfStack(
                    "NetworkLoggingHandler",
                    "Shouldn't be called with NetworkLoggingHandler lock held");
            return;
        }
        Slog.d(
                "NetworkLoggingHandler",
                "Sending network logging batch broadcast to device owner or profile owner,"
                    + " batchToken: "
                        + bundle.getLong("android.app.extra.EXTRA_NETWORK_LOGS_TOKEN", -1L));
        this.mDpm.sendDeviceOwnerOrProfileOwnerCommand(
                this.mTargetUserId, "android.app.action.NETWORK_LOGS_AVAILABLE", bundle);
    }

    public final void scheduleBatchFinalization() {
        this.mAlarmManager.setWindow(
                2,
                SystemClock.elapsedRealtime() + 5400000,
                1800000L,
                "NetworkLogging.batchTimeout",
                this.mBatchTimeoutAlarmListener,
                this);
        Slog.d(
                "NetworkLoggingHandler",
                "Scheduled a new batch finalization alarm 5400000ms from now.");
    }
}
