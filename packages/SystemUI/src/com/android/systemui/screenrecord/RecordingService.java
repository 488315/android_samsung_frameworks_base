package com.android.systemui.screenrecord;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.recordissue.ScreenRecordingStartTimeStore;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class RecordingService extends Service implements ScreenMediaRecorder.ScreenMediaRecorderListener {
    static final String GROUP_KEY_ERROR_SAVING = "screen_record_error_saving";
    static final String GROUP_KEY_SAVED = "screen_record_saved";
    public final RecordingController mController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final Executor mLongExecutor;
    public final Handler mMainHandler;
    public final NotificationManager mNotificationManager;
    public boolean mOriginalShowTaps;
    public ScreenMediaRecorder mRecorder;
    public final ScreenRecordingStartTimeStore mScreenRecordingStartTimeStore;
    public boolean mShowTaps;
    public RecordingServiceStrings mStrings;
    public final UiEventLogger mUiEventLogger;
    public final UserContextProvider mUserContextTracker;
    public ScreenRecordingAudioSource mAudioSource = ScreenRecordingAudioSource.NONE;
    public int mNotificationId = 4273;

    public RecordingService(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil, ScreenRecordingStartTimeStore screenRecordingStartTimeStore) {
        this.mController = recordingController;
        this.mLongExecutor = executor;
        this.mMainHandler = handler;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationManager = notificationManager;
        this.mUserContextTracker = userContextProvider;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mScreenRecordingStartTimeStore = screenRecordingStartTimeStore;
    }

    public final void createErrorNotification(UserHandle userHandle, String str, String str2, int i) {
        postGroupSummaryNotification(userHandle, str, str2, i);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        startForeground(this.mNotificationId, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(str).setGroup(str2).addExtras(bundle).build());
    }

    public void createErrorSavingNotification(UserHandle userHandle) {
        createErrorNotification(userHandle, strings().getSaveError(), GROUP_KEY_ERROR_SAVING, 4275);
    }

    public void createErrorStartingNotification(UserHandle userHandle) {
        createErrorNotification(userHandle, strings().getStartError(), "screen_record_error_starting", 4276);
    }

    public Notification createProcessingNotification() {
        String ongoingRecording = this.mAudioSource == ScreenRecordingAudioSource.NONE ? strings().getOngoingRecording() : strings().res.getString(R.string.screenrecord_ongoing_screen_and_audio);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        return new Notification.Builder(this, getChannelId()).setContentTitle(ongoingRecording).setContentText(strings().getBackgroundProcessingLabel()).setSmallIcon(R.drawable.ic_screenrecord).setGroup(GROUP_KEY_SAVED).addExtras(bundle).build();
    }

    public void createRecordingNotification() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        startForeground(this.mNotificationId, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(this.mAudioSource == ScreenRecordingAudioSource.NONE ? strings().getOngoingRecording() : strings().res.getString(R.string.screenrecord_ongoing_screen_and_audio)).setUsesChronometer(true).setColorized(true).setColor(getResources().getColor(R.color.GM2_red_700)).setOngoing(true).setForegroundServiceBehavior(1).addAction(new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_android), strings().res.getString(R.string.screenrecord_stop_label), PendingIntent.getService(this, 2, new Intent(this, getClass()).setAction("com.android.systemui.screenrecord.STOP_FROM_NOTIF").putExtra("extra_stopReason", 1), 201326592)).build()).addExtras(bundle).build());
    }

    public Notification createSaveNotification(ScreenMediaRecorder.SavedRecording savedRecording) {
        Uri uri = savedRecording != null ? savedRecording.mUri : null;
        Intent dataAndType = new Intent("android.intent.action.VIEW").setFlags(268435457).setDataAndType(uri, "video/mp4");
        Notification.Action build = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_screenrecord), strings().res.getString(R.string.screenrecord_share_label), PendingIntent.getService(this, 2, new Intent(this, getClass()).setAction("com.android.systemui.screenrecord.SHARE").putExtra("extra_path", uri).putExtra("notification_id", this.mNotificationId), 201326592)).build();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        Notification.Builder addExtras = new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(strings().getSaveTitle()).setContentText(strings().res.getString(R.string.screenrecord_save_text)).setContentIntent(PendingIntent.getActivity(this, 2, dataAndType, 67108864)).addAction(build).setAutoCancel(true).setGroup(GROUP_KEY_SAVED).addExtras(bundle);
        Icon icon = savedRecording != null ? savedRecording.mThumbnailIcon : null;
        if (icon != null) {
            addExtras.setStyle(new Notification.BigPictureStyle().bigPicture(icon).showBigPictureWhenCollapsed(true));
        }
        return addExtras.build();
    }

    public String getChannelId() {
        return "screen_record";
    }

    public ScreenMediaRecorder getRecorder() {
        return this.mRecorder;
    }

    public String getTag() {
        return "RecordingService";
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onRecordingSaved(ScreenMediaRecorder.SavedRecording savedRecording, UserHandle userHandle) {
        this.mNotificationManager.notifyAsUser(null, this.mNotificationId, createSaveNotification(savedRecording), userHandle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0076, code lost:
    
        if (r4.equals("com.android.systemui.screenrecord.START_NOTIF") == false) goto L7;
     */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onStartCommand(android.content.Intent r14, int r15, int r16) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenrecord.RecordingService.onStartCommand(android.content.Intent, int, int):int");
    }

    public final void postGroupSummaryNotification(UserHandle userHandle, String str, String str2, int i) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        this.mNotificationManager.notifyAsUser(getTag(), i, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(str).setGroup(str2).setGroupSummary(true).setExtras(bundle).build(), userHandle);
    }

    public RecordingServiceStrings provideRecordingServiceStrings() {
        return new RecordingServiceStrings(getResources());
    }

    public final void saveRecording(int i) {
        final UserHandle userHandle = new UserHandle(i);
        this.mNotificationManager.notifyAsUser(null, this.mNotificationId, createProcessingNotification(), userHandle);
        this.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RecordingService recordingService = RecordingService.this;
                UserHandle userHandle2 = userHandle;
                String str = RecordingService.GROUP_KEY_SAVED;
                recordingService.getClass();
                try {
                    Log.d(recordingService.getTag(), "saving recording");
                    ScreenMediaRecorder.SavedRecording save = recordingService.getRecorder() != null ? recordingService.getRecorder().save() : null;
                    recordingService.postGroupSummaryNotification(userHandle2, recordingService.strings().getSaveTitle(), "screen_record_saved", 4274);
                    recordingService.onRecordingSaved(save, userHandle2);
                } catch (IOException | IllegalStateException e) {
                    Log.e(recordingService.getTag(), "Error saving screen recording: " + e.getMessage());
                    e.printStackTrace();
                    recordingService.showErrorToast(R.string.screenrecord_save_error);
                    recordingService.mNotificationManager.cancelAsUser(null, recordingService.mNotificationId, userHandle2);
                }
            }
        });
    }

    public void showErrorToast(int i) {
        Toast.makeText(this, i, 1).show();
    }

    public final void stopService(int i, int i2) {
        if (i == -1) {
            i = ((UserTrackerImpl) this.mUserContextTracker).getUserContext().getUserId();
        }
        UserHandle userHandle = new UserHandle(i);
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "notifying for user ", getTag());
        Settings.System.putInt(getContentResolver(), "show_touches", this.mOriginalShowTaps ? 1 : 0);
        try {
            if (getRecorder() != null) {
                getRecorder().end(i2);
            }
            saveRecording(i);
        } catch (RuntimeException e) {
            if (getRecorder() != null) {
                ScreenMediaRecorder recorder = getRecorder();
                File file = recorder.mTempVideoFile;
                if (file != null) {
                    file.delete();
                }
                File file2 = recorder.mTempAudioFile;
                if (file2 != null) {
                    file2.delete();
                }
            }
            showErrorToast(R.string.screenrecord_save_error);
            Log.e(getTag(), "stopRecording called, but there was an error when endingrecording");
            e.printStackTrace();
            createErrorSavingNotification(userHandle);
        } catch (Throwable th) {
            if (getRecorder() != null) {
                ScreenMediaRecorder recorder2 = getRecorder();
                File file3 = recorder2.mTempVideoFile;
                if (file3 != null) {
                    file3.delete();
                }
                File file4 = recorder2.mTempAudioFile;
                if (file4 != null) {
                    file4.delete();
                }
            }
            throw new RuntimeException(th);
        }
        updateState(false);
        stopForeground(2);
        stopSelf();
    }

    public final RecordingServiceStrings strings() {
        if (this.mStrings == null) {
            this.mStrings = provideRecordingServiceStrings();
        }
        return this.mStrings;
    }

    public final void updateState(boolean z) {
        if (((UserTrackerImpl) this.mUserContextTracker).getUserContext().getUserId() == 0) {
            this.mController.updateState(z);
            return;
        }
        Intent intent = new Intent("com.android.systemui.screenrecord.UPDATE_STATE");
        intent.putExtra("extra_state", z);
        intent.addFlags(1073741824);
        sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }
}
