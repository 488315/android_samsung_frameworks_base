package com.android.systemui.screenrecord;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingController.AnonymousClass3;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Arrays;
import java.util.List;

public final class ScreenRecordDialogDelegate implements SystemUIDialog.Delegate {
    public static final List MODES = Arrays.asList(ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL);
    public Switch mAudioSwitch;
    public final RecordingController mController;
    public final Runnable mOnStartRecordingClicked;
    public Spinner mOptions;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public Switch mTapsSwitch;
    public final UserContextProvider mUserContextProvider;

    public interface Factory {
        ScreenRecordDialogDelegate create(RecordingController recordingController, Runnable runnable);
    }

    public ScreenRecordDialogDelegate(SystemUIDialog.Factory factory, UserContextProvider userContextProvider, RecordingController recordingController, Runnable runnable) {
        this.mSystemUIDialogFactory = factory;
        this.mUserContextProvider = userContextProvider;
        this.mController = recordingController;
        this.mOnStartRecordingClicked = runnable;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        return factory.create(this, factory.mContext);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        Window window = systemUIDialog.getWindow();
        window.addPrivateFlags(16);
        window.setGravity(17);
        systemUIDialog.setTitle(R.string.screenrecord_title);
        systemUIDialog.setContentView(R.layout.screen_record_dialog);
        ((TextView) systemUIDialog.findViewById(R.id.button_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialogDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog.this.dismiss();
            }
        });
        ((TextView) systemUIDialog.findViewById(R.id.button_start)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialogDelegate$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ScreenRecordDialogDelegate screenRecordDialogDelegate = ScreenRecordDialogDelegate.this;
                SystemUIDialog systemUIDialog2 = systemUIDialog;
                Runnable runnable = screenRecordDialogDelegate.mOnStartRecordingClicked;
                if (runnable != null) {
                    runnable.run();
                }
                Context userContext = ((UserTrackerImpl) screenRecordDialogDelegate.mUserContextProvider).getUserContext();
                PendingIntent foregroundService = PendingIntent.getForegroundService(userContext, 2, RecordingService.getStartIntent(userContext, (screenRecordDialogDelegate.mAudioSwitch.isChecked() ? (ScreenRecordingAudioSource) screenRecordDialogDelegate.mOptions.getSelectedItem() : ScreenRecordingAudioSource.NONE).ordinal(), screenRecordDialogDelegate.mTapsSwitch.isChecked(), null), 201326592);
                PendingIntent service = PendingIntent.getService(userContext, 2, RecordingService.getStopIntent(userContext), 201326592);
                RecordingController recordingController = screenRecordDialogDelegate.mController;
                recordingController.mIsStarting = true;
                recordingController.mStopIntent = service;
                RecordingController.AnonymousClass3 anonymousClass3 = recordingController.new AnonymousClass3(3000L, 1000L, foregroundService);
                recordingController.mCountDownTimer = anonymousClass3;
                anonymousClass3.start();
                systemUIDialog2.dismiss();
            }
        });
        this.mAudioSwitch = (Switch) systemUIDialog.findViewById(R.id.screenrecord_audio_switch);
        this.mTapsSwitch = (Switch) systemUIDialog.findViewById(R.id.screenrecord_taps_switch);
        this.mOptions = (Spinner) systemUIDialog.findViewById(R.id.screen_recording_options);
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(systemUIDialog.getContext().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, MODES);
        screenRecordingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mOptions.setAdapter((SpinnerAdapter) screenRecordingAdapter);
        this.mOptions.setOnItemClickListenerInt(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordDialogDelegate$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                ScreenRecordDialogDelegate.this.mAudioSwitch.setChecked(true);
            }
        });
        this.mOptions.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.screenrecord.ScreenRecordDialogDelegate.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            }
        });
        this.mOptions.setLongClickable(false);
    }
}
