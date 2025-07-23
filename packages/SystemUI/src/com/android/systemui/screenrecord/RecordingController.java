package com.android.systemui.screenrecord;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.SessionCreationSource;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.screenrecord.ScreenRecordPermissionDialogDelegate;
import com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.CallbackController;
import dagger.Lazy;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class RecordingController implements CallbackController {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy mDevicePolicyResolver;
    public final Bundle mInteractiveBroadcastOption;
    public boolean mIsRecording;
    public boolean mIsStarting;
    public final Executor mMainExecutor;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public final RecordingControllerLogger mRecordingControllerLogger;
    public final ScreenCaptureDisabledDialogDelegate mScreenCaptureDisabledDialogDelegate;
    public final ScreenRecordPermissionDialogDelegate.Factory mScreenRecordPermissionDialogDelegateFactory;
    public PendingIntent mStopIntent;
    public final UserTracker mUserTracker;
    public int mStopReason = 0;
    public AnonymousClass3 mCountDownTimer = null;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.screenrecord.RecordingController.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            RecordingController.this.stopRecording(6);
        }
    };
    protected final BroadcastReceiver mStateChangeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.screenrecord.RecordingController.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || !"com.android.systemui.screenrecord.UPDATE_STATE".equals(intent.getAction())) {
                return;
            }
            if (!intent.hasExtra("extra_state")) {
                RecordingControllerLogger recordingControllerLogger = RecordingController.this.mRecordingControllerLogger;
                recordingControllerLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda0 = new RecordingControllerLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = recordingControllerLogger.logger;
                logBuffer.commit(logBuffer.obtain("RecordingController", logLevel, recordingControllerLogger$$ExternalSyntheticLambda0, null));
                return;
            }
            boolean booleanExtra = intent.getBooleanExtra("extra_state", false);
            RecordingControllerLogger recordingControllerLogger2 = RecordingController.this.mRecordingControllerLogger;
            recordingControllerLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda02 = new RecordingControllerLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer2 = recordingControllerLogger2.logger;
            LogMessage obtain = logBuffer2.obtain("RecordingController", logLevel2, recordingControllerLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) obtain).bool1 = booleanExtra;
            logBuffer2.commit(obtain);
            RecordingController.this.updateState(booleanExtra);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.screenrecord.RecordingController$3, reason: invalid class name */
    public class AnonymousClass3 extends CountDownTimer {
        public final /* synthetic */ PendingIntent val$startIntent;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(long j, long j2, PendingIntent pendingIntent) {
            super(j, j2);
            this.val$startIntent = pendingIntent;
        }

        @Override // android.os.CountDownTimer
        public final void onFinish() {
            RecordingController recordingController = RecordingController.this;
            recordingController.mIsStarting = false;
            recordingController.mIsRecording = true;
            Iterator it = recordingController.mListeners.iterator();
            while (it.hasNext()) {
                ((RecordingStateChangeCallback) it.next()).onCountdownEnd();
            }
            try {
                this.val$startIntent.send(RecordingController.this.mInteractiveBroadcastOption);
                RecordingController recordingController2 = RecordingController.this;
                ((UserTrackerImpl) recordingController2.mUserTracker).addCallback(recordingController2.mUserChangedCallback, recordingController2.mMainExecutor);
                IntentFilter intentFilter = new IntentFilter("com.android.systemui.screenrecord.UPDATE_STATE");
                RecordingController recordingController3 = RecordingController.this;
                recordingController3.mBroadcastDispatcher.registerReceiver(recordingController3.mStateChangeReceiver, intentFilter, null, UserHandle.ALL);
                RecordingControllerLogger recordingControllerLogger = RecordingController.this.mRecordingControllerLogger;
                recordingControllerLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda0 = new RecordingControllerLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = recordingControllerLogger.logger;
                logBuffer.commit(logBuffer.obtain("RecordingController", logLevel, recordingControllerLogger$$ExternalSyntheticLambda0, null));
            } catch (PendingIntent.CanceledException e) {
                RecordingControllerLogger recordingControllerLogger2 = RecordingController.this.mRecordingControllerLogger;
                recordingControllerLogger2.getClass();
                LogLevel logLevel2 = LogLevel.ERROR;
                RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda02 = new RecordingControllerLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer2 = recordingControllerLogger2.logger;
                logBuffer2.commit(logBuffer2.obtain("RecordingController", logLevel2, recordingControllerLogger$$ExternalSyntheticLambda02, e));
            }
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            Iterator it = RecordingController.this.mListeners.iterator();
            while (it.hasNext()) {
                ((RecordingStateChangeCallback) it.next()).onCountdown(j);
            }
        }
    }

    public RecordingController(Executor executor, BroadcastDispatcher broadcastDispatcher, Lazy lazy, UserTracker userTracker, RecordingControllerLogger recordingControllerLogger, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate, ScreenRecordPermissionDialogDelegate.Factory factory, ScreenRecordPermissionViewBinder.Factory factory2) {
        this.mMainExecutor = executor;
        this.mDevicePolicyResolver = lazy;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
        this.mRecordingControllerLogger = recordingControllerLogger;
        this.mMediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.mScreenCaptureDisabledDialogDelegate = screenCaptureDisabledDialogDelegate;
        this.mScreenRecordPermissionDialogDelegateFactory = factory;
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setInteractive(true);
        this.mInteractiveBroadcastOption = makeBasic.toBundle();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mListeners.add((RecordingStateChangeCallback) obj);
    }

    public final void cancelCountdown$1() {
        AnonymousClass3 anonymousClass3 = this.mCountDownTimer;
        RecordingControllerLogger recordingControllerLogger = this.mRecordingControllerLogger;
        if (anonymousClass3 != null) {
            recordingControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda0 = new RecordingControllerLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer = recordingControllerLogger.logger;
            logBuffer.commit(logBuffer.obtain("RecordingController", logLevel, recordingControllerLogger$$ExternalSyntheticLambda0, null));
            this.mCountDownTimer.cancel();
        } else {
            recordingControllerLogger.getClass();
            LogLevel logLevel2 = LogLevel.ERROR;
            RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda02 = new RecordingControllerLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer2 = recordingControllerLogger.logger;
            logBuffer2.commit(logBuffer2.obtain("RecordingController", logLevel2, recordingControllerLogger$$ExternalSyntheticLambda02, null));
        }
        this.mIsStarting = false;
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((RecordingStateChangeCallback) it.next()).onCountdownEnd();
        }
    }

    public final SystemUIDialog createScreenRecordDialog(Runnable runnable) {
        if (((ScreenCaptureDevicePolicyResolver) this.mDevicePolicyResolver.get()).isScreenCaptureCompletelyDisabled(UserHandle.of(UserHandle.myUserId()))) {
            ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate = this.mScreenCaptureDisabledDialogDelegate;
            screenCaptureDisabledDialogDelegate.getClass();
            SystemUIDialog systemUIDialog = new SystemUIDialog(screenCaptureDisabledDialogDelegate.context);
            screenCaptureDisabledDialogDelegate.initDialog(systemUIDialog);
            return systemUIDialog;
        }
        this.mMediaProjectionMetricsLogger.notifyProjectionInitiated(Process.myUid(), SessionCreationSource.SYSTEM_UI_SCREEN_RECORDER);
        return this.mScreenRecordPermissionDialogDelegateFactory.create(this, UserHandle.of(UserHandle.myUserId()), Process.myUid(), runnable).createDialog();
    }

    public final synchronized boolean isRecording() {
        return this.mIsRecording;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mListeners.remove((RecordingStateChangeCallback) obj);
    }

    public final void stopRecording(int i) {
        RecordingControllerLogger recordingControllerLogger = this.mRecordingControllerLogger;
        this.mStopReason = i;
        try {
            if (this.mStopIntent != null) {
                recordingControllerLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda0 = new RecordingControllerLogger$$ExternalSyntheticLambda0(7);
                LogBuffer logBuffer = recordingControllerLogger.logger;
                logBuffer.commit(logBuffer.obtain("RecordingController", logLevel, recordingControllerLogger$$ExternalSyntheticLambda0, null));
                this.mStopIntent.send(this.mInteractiveBroadcastOption);
            } else {
                recordingControllerLogger.getClass();
                LogLevel logLevel2 = LogLevel.ERROR;
                RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda02 = new RecordingControllerLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer2 = recordingControllerLogger.logger;
                logBuffer2.commit(logBuffer2.obtain("RecordingController", logLevel2, recordingControllerLogger$$ExternalSyntheticLambda02, null));
            }
            updateState(false);
        } catch (PendingIntent.CanceledException e) {
            recordingControllerLogger.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda03 = new RecordingControllerLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer3 = recordingControllerLogger.logger;
            logBuffer3.commit(logBuffer3.obtain("RecordingController", logLevel3, recordingControllerLogger$$ExternalSyntheticLambda03, e));
        }
    }

    public final synchronized void updateState(boolean z) {
        try {
            RecordingControllerLogger recordingControllerLogger = this.mRecordingControllerLogger;
            recordingControllerLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            RecordingControllerLogger$$ExternalSyntheticLambda0 recordingControllerLogger$$ExternalSyntheticLambda0 = new RecordingControllerLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = recordingControllerLogger.logger;
            LogMessage obtain = logBuffer.obtain("RecordingController", logLevel, recordingControllerLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
            if (!z && this.mIsRecording) {
                ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
                this.mBroadcastDispatcher.unregisterReceiver(this.mStateChangeReceiver);
            }
            this.mIsRecording = z;
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                RecordingStateChangeCallback recordingStateChangeCallback = (RecordingStateChangeCallback) it.next();
                if (z) {
                    recordingStateChangeCallback.onRecordingStart();
                } else {
                    recordingStateChangeCallback.onRecordingEnd();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface RecordingStateChangeCallback {
        default void onCountdown(long j) {
        }

        default void onCountdownEnd() {
        }

        default void onRecordingEnd() {
        }

        default void onRecordingStart() {
        }
    }
}
