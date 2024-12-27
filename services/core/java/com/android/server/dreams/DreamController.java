package com.android.server.dreams;

import android.R;
import android.app.BroadcastOptions;
import android.app.IAppTask;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.dreams.IDreamService;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.UUID;

public final class DreamController {
    public final Intent mCloseNotificationShadeIntent;
    public final Bundle mCloseNotificationShadeOptions;
    public final Context mContext;
    public DreamRecord mCurrentDream;
    public final Bundle mDreamingStartedStoppedOptions;
    public final Handler mHandler;
    public final DreamManagerService.AnonymousClass4 mListener;
    public final PowerManager mPowerManager;
    public final ArrayList mPreviousDreams;
    public final boolean mResetScreenTimeoutOnUnexpectedDreamExit;
    public boolean mSentStartBroadcast;
    public static final String DREAMING_DELIVERY_GROUP_NAMESPACE = UUID.randomUUID().toString();
    public static final String DREAMING_DELIVERY_GROUP_KEY = UUID.randomUUID().toString();
    public final Intent mDreamingStartedIntent =
            new Intent("android.intent.action.DREAMING_STARTED").addFlags(1342177280);
    public final Intent mDreamingStoppedIntent =
            new Intent("android.intent.action.DREAMING_STOPPED").addFlags(1342177280);

    public final class DreamRecord implements IBinder.DeathRecipient, ServiceConnection {
        public IAppTask mAppTask;
        public boolean mBound;
        public final boolean mCanDoze;
        public boolean mConnected;
        public boolean mDreamIsObscured;
        public long mDreamStartTime;
        public final AnonymousClass1 mDreamingStartedCallback;
        public final boolean mIsPreviewMode;
        public final ComponentName mName;
        public final DreamController$DreamRecord$$ExternalSyntheticLambda1 mReleaseWakeLockIfNeeded;
        public IDreamService mService;
        public final DreamController$DreamRecord$$ExternalSyntheticLambda1
                mStopPreviousDreamsIfNeeded =
                        new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 0);
        public String mStopReason;
        public final DreamController$DreamRecord$$ExternalSyntheticLambda1
                mStopStubbornDreamRunnable;
        public final DreamController$DreamRecord$$ExternalSyntheticLambda1
                mStopUnconnectedDreamRunnable;
        public final Binder mToken;
        public final int mUserId;
        public PowerManager.WakeLock mWakeLock;
        public boolean mWakingGently;

        public DreamRecord(
                Binder binder,
                ComponentName componentName,
                boolean z,
                boolean z2,
                int i,
                PowerManager.WakeLock wakeLock) {
            DreamController$DreamRecord$$ExternalSyntheticLambda1
                    dreamController$DreamRecord$$ExternalSyntheticLambda1 =
                            new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 1);
            this.mReleaseWakeLockIfNeeded = dreamController$DreamRecord$$ExternalSyntheticLambda1;
            this.mStopUnconnectedDreamRunnable =
                    new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 2);
            this.mStopStubbornDreamRunnable =
                    new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 3);
            this.mDreamingStartedCallback =
                    new IRemoteCallback
                            .Stub() { // from class:
                                      // com.android.server.dreams.DreamController.DreamRecord.1
                        public final void sendResult(Bundle bundle) {
                            DreamRecord dreamRecord = DreamRecord.this;
                            DreamController.this.mHandler.post(
                                    dreamRecord.mStopPreviousDreamsIfNeeded);
                            DreamRecord dreamRecord2 = DreamRecord.this;
                            DreamController.this.mHandler.post(
                                    dreamRecord2.mReleaseWakeLockIfNeeded);
                        }
                    };
            this.mToken = binder;
            this.mName = componentName;
            this.mIsPreviewMode = z;
            this.mCanDoze = z2;
            this.mUserId = i;
            this.mWakeLock = wakeLock;
            if (wakeLock != null) {
                wakeLock.acquire();
            }
            DreamController.this.mHandler.postDelayed(
                    dreamController$DreamRecord$$ExternalSyntheticLambda1, 10000L);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            DreamController.this.mHandler.post(
                    new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 5));
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            DreamController.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.dreams.DreamController$DreamRecord$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamController.DreamRecord dreamRecord =
                                    DreamController.DreamRecord.this;
                            IBinder iBinder2 = iBinder;
                            dreamRecord.mConnected = true;
                            DreamController dreamController = DreamController.this;
                            if (dreamController.mCurrentDream != dreamRecord
                                    || dreamRecord.mService != null) {
                                dreamRecord.releaseWakeLockIfNeeded();
                                return;
                            }
                            IDreamService asInterface = IDreamService.Stub.asInterface(iBinder2);
                            try {
                                asInterface
                                        .asBinder()
                                        .linkToDeath(dreamController.mCurrentDream, 0);
                                DreamController.DreamRecord dreamRecord2 =
                                        dreamController.mCurrentDream;
                                asInterface.attach(
                                        dreamRecord2.mToken,
                                        dreamRecord2.mCanDoze,
                                        dreamRecord2.mIsPreviewMode,
                                        dreamRecord2.mDreamingStartedCallback);
                                DreamController.DreamRecord dreamRecord3 =
                                        dreamController.mCurrentDream;
                                dreamRecord3.mService = asInterface;
                                if (dreamRecord3.mIsPreviewMode
                                        || dreamController.mSentStartBroadcast) {
                                    return;
                                }
                                dreamController.mContext.sendBroadcastAsUser(
                                        dreamController.mDreamingStartedIntent,
                                        UserHandle.ALL,
                                        null,
                                        dreamController.mDreamingStartedStoppedOptions);
                                Binder binder = dreamController.mCurrentDream.mToken;
                                DreamManagerService dreamManagerService = DreamManagerService.this;
                                dreamManagerService.getClass();
                                dreamManagerService.mHandler.post(
                                        new DreamManagerService$$ExternalSyntheticLambda7(
                                                dreamManagerService,
                                                new DreamManagerService$$ExternalSyntheticLambda0(
                                                        0)));
                                dreamController.mSentStartBroadcast = true;
                            } catch (RemoteException e) {
                                Slog.e(
                                        "DreamController",
                                        "The dream service died unexpectedly.",
                                        e);
                                dreamController.stopDream(true, "attach failed");
                            }
                        }
                    });
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            DreamController.this.mHandler.post(
                    new DreamController$DreamRecord$$ExternalSyntheticLambda1(this, 4));
        }

        public final void releaseWakeLockIfNeeded() {
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (wakeLock != null) {
                wakeLock.release();
                this.mWakeLock = null;
                DreamController.this.mHandler.removeCallbacks(this.mReleaseWakeLockIfNeeded);
            }
        }
    }

    public DreamController(
            Context context, Handler handler, DreamManagerService.AnonymousClass4 anonymousClass4) {
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey(
                DREAMING_DELIVERY_GROUP_NAMESPACE, DREAMING_DELIVERY_GROUP_KEY);
        makeBasic.setDeferralPolicy(2);
        this.mDreamingStartedStoppedOptions = makeBasic.toBundle();
        this.mSentStartBroadcast = false;
        this.mPreviousDreams = new ArrayList();
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = anonymousClass4;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Intent intent = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        this.mCloseNotificationShadeIntent = intent;
        intent.putExtra("reason", "dream");
        intent.addFlags(268435456);
        this.mCloseNotificationShadeOptions =
                BroadcastOptions.makeBasic()
                        .setDeliveryGroupPolicy(1)
                        .setDeliveryGroupMatchingKey(
                                "android.intent.action.CLOSE_SYSTEM_DIALOGS", "dream")
                        .setDeferralPolicy(2)
                        .toBundle();
        this.mResetScreenTimeoutOnUnexpectedDreamExit =
                context.getResources().getBoolean(R.bool.config_sf_limitedAlpha);
    }

    public final void stopDream(boolean z, String str) {
        stopPreviousDreams();
        stopDreamInstance(z, str, this.mCurrentDream);
    }

    public final void stopDreamInstance(boolean z, String str, DreamRecord dreamRecord) {
        String str2;
        IAppTask iAppTask;
        if (dreamRecord == null) {
            return;
        }
        Trace.traceBegin(131072L, "stopDream");
        Handler handler = this.mHandler;
        if (!z) {
            try {
                if (dreamRecord.mWakingGently) {
                    return;
                }
                IDreamService iDreamService = dreamRecord.mService;
                if (iDreamService != null) {
                    dreamRecord.mWakingGently = true;
                    try {
                        dreamRecord.mStopReason = str;
                        iDreamService.wakeUp();
                        handler.postDelayed(dreamRecord.mStopStubbornDreamRunnable, 5000L);
                        return;
                    } catch (RemoteException unused) {
                    }
                }
            } finally {
                Trace.traceEnd(131072L);
            }
        }
        StringBuilder sb = new StringBuilder("Stopping dream: name=");
        sb.append(dreamRecord.mName);
        sb.append(", isPreviewMode=");
        sb.append(dreamRecord.mIsPreviewMode);
        sb.append(", canDoze=");
        sb.append(dreamRecord.mCanDoze);
        sb.append(", userId=");
        sb.append(dreamRecord.mUserId);
        sb.append(", reason='");
        sb.append(str);
        sb.append("'");
        if (dreamRecord.mStopReason == null) {
            str2 = "";
        } else {
            str2 = "(from '" + dreamRecord.mStopReason + "')";
        }
        sb.append(str2);
        Slog.i("DreamController", sb.toString());
        MetricsLogger.hidden(
                this.mContext,
                dreamRecord.mCanDoze ? FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED : 222);
        MetricsLogger.histogram(
                this.mContext,
                dreamRecord.mCanDoze ? "dozing_minutes" : "dreaming_minutes",
                (int) ((SystemClock.elapsedRealtime() - dreamRecord.mDreamStartTime) / 60000));
        handler.removeCallbacks(dreamRecord.mStopUnconnectedDreamRunnable);
        handler.removeCallbacks(dreamRecord.mStopStubbornDreamRunnable);
        IDreamService iDreamService2 = dreamRecord.mService;
        if (iDreamService2 != null) {
            try {
                iDreamService2.detach();
            } catch (RemoteException unused2) {
            }
            try {
                dreamRecord.mService.asBinder().unlinkToDeath(dreamRecord, 0);
            } catch (NoSuchElementException unused3) {
            }
            dreamRecord.mService = null;
        }
        if (dreamRecord.mBound) {
            this.mContext.unbindService(dreamRecord);
        }
        dreamRecord.releaseWakeLockIfNeeded();
        if (dreamRecord == this.mCurrentDream) {
            this.mCurrentDream = null;
            if (this.mSentStartBroadcast) {
                this.mContext.sendBroadcastAsUser(
                        this.mDreamingStoppedIntent,
                        UserHandle.ALL,
                        null,
                        this.mDreamingStartedStoppedOptions);
                this.mSentStartBroadcast = false;
            }
            DreamRecord dreamRecord2 = this.mCurrentDream;
            if (dreamRecord2 != null && (iAppTask = dreamRecord2.mAppTask) != null) {
                try {
                    iAppTask.finishAndRemoveTask();
                } catch (RemoteException | RuntimeException unused4) {
                    Slog.e("DreamController", "Unable to stop dream activity.");
                }
            }
            this.mListener.onDreamStopped(dreamRecord.mToken);
        }
    }

    public final void stopPreviousDreams() {
        if (this.mPreviousDreams.isEmpty()) {
            return;
        }
        Iterator it = this.mPreviousDreams.iterator();
        while (it.hasNext()) {
            stopDreamInstance(true, "stop previous dream", (DreamRecord) it.next());
            it.remove();
        }
    }
}
