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
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.SessionCreationSource;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver;
import com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDisabledDialogDelegate;
import com.android.systemui.screenrecord.ScreenRecordDialogDelegate;
import com.android.systemui.screenrecord.ScreenRecordPermissionDialogDelegate;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.CallbackController;
import dagger.Lazy;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

public final class RecordingController implements CallbackController {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy mDevicePolicyResolver;
    public final FeatureFlags mFlags;
    public final Bundle mInteractiveBroadcastOption;
    public boolean mIsRecording;
    public boolean mIsStarting;
    public final Executor mMainExecutor;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public final ScreenCaptureDisabledDialogDelegate mScreenCaptureDisabledDialogDelegate;
    public final ScreenRecordDialogDelegate.Factory mScreenRecordDialogFactory;
    public final ScreenRecordPermissionDialogDelegate.Factory mScreenRecordPermissionDialogDelegateFactory;
    public PendingIntent mStopIntent;
    public final UserTracker mUserTracker;
    public AnonymousClass3 mCountDownTimer = null;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.screenrecord.RecordingController.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            RecordingController.this.stopRecording$1();
        }
    };
    protected final BroadcastReceiver mStateChangeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.screenrecord.RecordingController.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || !"com.android.systemui.screenrecord.UPDATE_STATE".equals(intent.getAction())) {
                return;
            }
            if (!intent.hasExtra("extra_state")) {
                Log.e("RecordingController", "Received update intent with no state");
            } else {
                RecordingController.this.updateState(intent.getBooleanExtra("extra_state", false));
            }
        }
    };

    /* renamed from: com.android.systemui.screenrecord.RecordingController$3, reason: invalid class name */
    public final class AnonymousClass3 extends CountDownTimer {
        public final /* synthetic */ PendingIntent val$startIntent;

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
                Log.d("RecordingController", "sent start intent");
            } catch (PendingIntent.CanceledException e) {
                Log.e("RecordingController", "Pending intent was cancelled: " + e.getMessage());
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

    public RecordingController(Executor executor, BroadcastDispatcher broadcastDispatcher, FeatureFlags featureFlags, Lazy lazy, UserTracker userTracker, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate, ScreenRecordDialogDelegate.Factory factory, ScreenRecordPermissionDialogDelegate.Factory factory2) {
        this.mMainExecutor = executor;
        this.mFlags = featureFlags;
        this.mDevicePolicyResolver = lazy;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserTracker = userTracker;
        this.mMediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.mScreenCaptureDisabledDialogDelegate = screenCaptureDisabledDialogDelegate;
        this.mScreenRecordDialogFactory = factory;
        this.mScreenRecordPermissionDialogDelegateFactory = factory2;
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
        if (anonymousClass3 != null) {
            anonymousClass3.cancel();
        } else {
            Log.e("RecordingController", "Timer was null");
        }
        this.mIsStarting = false;
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((RecordingStateChangeCallback) it.next()).onCountdownEnd();
        }
    }

    public final SystemUIDialog createScreenRecordDialog(FeatureFlags featureFlags, Runnable runnable) {
        SystemUIDialog.Delegate create;
        if (((FeatureFlagsClassicRelease) this.mFlags).isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING_ENTERPRISE_POLICIES) && ((ScreenCaptureDevicePolicyResolver) this.mDevicePolicyResolver.get()).isScreenCaptureCompletelyDisabled(UserHandle.of(UserHandle.myUserId()))) {
            ScreenCaptureDisabledDialogDelegate screenCaptureDisabledDialogDelegate = this.mScreenCaptureDisabledDialogDelegate;
            screenCaptureDisabledDialogDelegate.getClass();
            SystemUIDialog systemUIDialog = new SystemUIDialog(screenCaptureDisabledDialogDelegate.context);
            screenCaptureDisabledDialogDelegate.initDialog(systemUIDialog);
            return systemUIDialog;
        }
        this.mMediaProjectionMetricsLogger.notifyProjectionInitiated(Process.myUid(), SessionCreationSource.SYSTEM_UI_SCREEN_RECORDER);
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING)) {
            create = this.mScreenRecordPermissionDialogDelegateFactory.create(this, UserHandle.of(UserHandle.myUserId()), Process.myUid(), runnable);
        } else {
            create = this.mScreenRecordDialogFactory.create(this, runnable);
        }
        return create.createDialog();
    }

    public final synchronized boolean isRecording() {
        return this.mIsRecording;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mListeners.remove((RecordingStateChangeCallback) obj);
    }

    public final void stopRecording$1() {
        try {
            PendingIntent pendingIntent = this.mStopIntent;
            if (pendingIntent != null) {
                pendingIntent.send(this.mInteractiveBroadcastOption);
            } else {
                Log.e("RecordingController", "Stop intent was null");
            }
            updateState(false);
        } catch (PendingIntent.CanceledException e) {
            Log.e("RecordingController", "Error stopping: " + e.getMessage());
        }
    }

    public final synchronized void updateState(boolean z) {
        if (!z) {
            try {
                if (this.mIsRecording) {
                    ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
                    this.mBroadcastDispatcher.unregisterReceiver(this.mStateChangeReceiver);
                }
            } catch (Throwable th) {
                throw th;
            }
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
    }

    public interface RecordingStateChangeCallback {
        default void onCountdownEnd() {
        }

        default void onRecordingEnd() {
        }

        default void onRecordingStart() {
        }

        default void onCountdown(long j) {
        }
    }
}
