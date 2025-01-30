package com.android.systemui.screenrecord;

import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenRecordDialog extends SystemUIDialog {
    public static final List MODES = Arrays.asList(ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL);
    public final ActivityStarter mActivityStarter;
    public Switch mAudioSwitch;
    public final RecordingController mController;
    public final FeatureFlags mFlags;
    public final Runnable mOnStartRecordingClicked;
    public Spinner mOptions;
    public Switch mTapsSwitch;
    public final UserContextProvider mUserContextProvider;

    public ScreenRecordDialog(Context context, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, FeatureFlags featureFlags, DialogLaunchAnimator dialogLaunchAnimator, Runnable runnable) {
        super(context);
        this.mController = recordingController;
        this.mUserContextProvider = userContextProvider;
        this.mActivityStarter = activityStarter;
        this.mFlags = featureFlags;
        this.mOnStartRecordingClicked = runnable;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.addPrivateFlags(16);
        window.setGravity(17);
        setTitle(R.string.screenrecord_title);
        setContentView(R.layout.screen_record_dialog);
        final int i = 0;
        ((TextView) findViewById(R.id.button_cancel)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ ScreenRecordDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.dismiss();
                        break;
                    default:
                        ScreenRecordDialog screenRecordDialog = this.f$0;
                        Runnable runnable = screenRecordDialog.mOnStartRecordingClicked;
                        if (runnable != null) {
                            runnable.run();
                        }
                        screenRecordDialog.requestScreenCapture(null);
                        screenRecordDialog.dismiss();
                        break;
                }
            }
        });
        final int i2 = 1;
        ((TextView) findViewById(R.id.button_start)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ ScreenRecordDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.dismiss();
                        break;
                    default:
                        ScreenRecordDialog screenRecordDialog = this.f$0;
                        Runnable runnable = screenRecordDialog.mOnStartRecordingClicked;
                        if (runnable != null) {
                            runnable.run();
                        }
                        screenRecordDialog.requestScreenCapture(null);
                        screenRecordDialog.dismiss();
                        break;
                }
            }
        });
        FeatureFlags featureFlags = this.mFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
        this.mAudioSwitch = (Switch) findViewById(R.id.screenrecord_audio_switch);
        this.mTapsSwitch = (Switch) findViewById(R.id.screenrecord_taps_switch);
        this.mOptions = (Spinner) findViewById(R.id.screen_recording_options);
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(getContext().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, MODES);
        screenRecordingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mOptions.setAdapter((SpinnerAdapter) screenRecordingAdapter);
        this.mOptions.setOnItemClickListenerInt(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialog$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                ScreenRecordDialog.this.mAudioSwitch.setChecked(true);
            }
        });
    }

    public final void requestScreenCapture(MediaProjectionCaptureTarget mediaProjectionCaptureTarget) {
        Context userContext = ((UserTrackerImpl) this.mUserContextProvider).getUserContext();
        PendingIntent foregroundService = PendingIntent.getForegroundService(userContext, 2, RecordingService.getStartIntent(userContext, (this.mAudioSwitch.isChecked() ? (ScreenRecordingAudioSource) this.mOptions.getSelectedItem() : ScreenRecordingAudioSource.NONE).ordinal(), this.mTapsSwitch.isChecked(), mediaProjectionCaptureTarget), 201326592);
        PendingIntent service = PendingIntent.getService(userContext, 2, RecordingService.getStopIntent(userContext), 201326592);
        RecordingController recordingController = this.mController;
        recordingController.mIsStarting = true;
        recordingController.mStopIntent = service;
        RecordingController.CountDownTimerC23213 countDownTimerC23213 = new CountDownTimer(3000L, 1000L) { // from class: com.android.systemui.screenrecord.RecordingController.3
            public final /* synthetic */ PendingIntent val$startIntent;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public CountDownTimerC23213(long j, long j2, PendingIntent foregroundService2) {
                super(j, j2);
                r6 = foregroundService2;
            }

            @Override // android.os.CountDownTimer
            public final void onFinish() {
                RecordingController recordingController2 = RecordingController.this;
                recordingController2.mIsStarting = false;
                recordingController2.mIsRecording = true;
                Iterator it = recordingController2.mListeners.iterator();
                while (it.hasNext()) {
                    ((RecordingStateChangeCallback) it.next()).onCountdownEnd();
                }
                try {
                    r6.send(RecordingController.this.mInteractiveBroadcastOption);
                    RecordingController recordingController3 = RecordingController.this;
                    ((UserTrackerImpl) recordingController3.mUserTracker).addCallback(recordingController3.mUserChangedCallback, recordingController3.mMainExecutor);
                    IntentFilter intentFilter = new IntentFilter("com.android.systemui.screenrecord.UPDATE_STATE");
                    RecordingController recordingController4 = RecordingController.this;
                    recordingController4.mBroadcastDispatcher.registerReceiver(recordingController4.mStateChangeReceiver, intentFilter, null, UserHandle.ALL);
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
        };
        recordingController.mCountDownTimer = countDownTimerC23213;
        countDownTimerC23213.start();
    }
}
