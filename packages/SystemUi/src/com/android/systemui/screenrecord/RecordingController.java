package com.android.systemui.screenrecord;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.CallbackController;
import dagger.Lazy;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RecordingController implements CallbackController {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final FeatureFlags mFlags;
    public final Bundle mInteractiveBroadcastOption;
    public boolean mIsRecording;
    public boolean mIsStarting;
    public final Executor mMainExecutor;
    public PendingIntent mStopIntent;
    public final UserContextProvider mUserContextProvider;
    public final UserTracker mUserTracker;
    public CountDownTimerC23213 mCountDownTimer = null;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.screenrecord.RecordingController.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            RecordingController.this.stopRecording();
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

    public RecordingController(Executor executor, BroadcastDispatcher broadcastDispatcher, Context context, FeatureFlags featureFlags, UserContextProvider userContextProvider, Lazy lazy, UserTracker userTracker) {
        this.mMainExecutor = executor;
        this.mFlags = featureFlags;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserContextProvider = userContextProvider;
        this.mUserTracker = userTracker;
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setInteractive(true);
        this.mInteractiveBroadcastOption = makeBasic.toBundle();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mListeners.add((RecordingStateChangeCallback) obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mListeners.remove((RecordingStateChangeCallback) obj);
    }

    public final void stopRecording() {
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
            if (this.mIsRecording) {
                ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mUserChangedCallback);
                this.mBroadcastDispatcher.unregisterReceiver(this.mStateChangeReceiver);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
