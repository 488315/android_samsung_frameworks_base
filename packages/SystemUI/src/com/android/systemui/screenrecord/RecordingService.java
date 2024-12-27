package com.android.systemui.screenrecord;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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
import com.android.systemui.mediaprojection.MediaProjectionCaptureTarget;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class RecordingService extends Service implements ScreenMediaRecorder.ScreenMediaRecorderListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RecordingController mController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final Executor mLongExecutor;
    public final Handler mMainHandler;
    public final NotificationManager mNotificationManager;
    public boolean mOriginalShowTaps;
    public ScreenMediaRecorder mRecorder;
    public boolean mShowTaps;
    public RecordingServiceStrings mStrings;
    public final UiEventLogger mUiEventLogger;
    public final UserContextProvider mUserContextTracker;
    public ScreenRecordingAudioSource mAudioSource = ScreenRecordingAudioSource.NONE;
    public int mNotificationId = 4273;

    public RecordingService(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil) {
        this.mController = recordingController;
        this.mLongExecutor = executor;
        this.mMainHandler = handler;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationManager = notificationManager;
        this.mUserContextTracker = userContextProvider;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
    }

    public static Intent getStartIntent(Context context, int i, boolean z, MediaProjectionCaptureTarget mediaProjectionCaptureTarget) {
        return new Intent(context, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("extra_resultCode", -1).putExtra("extra_useAudio", i).putExtra("extra_showTaps", z).putExtra("extra_captureTarget", mediaProjectionCaptureTarget);
    }

    public static Intent getStopIntent(Context context) {
        return new Intent(context, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", context.getUserId());
    }

    public void createErrorNotification() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        startForeground(this.mNotificationId, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(strings().getStartError()).addExtras(bundle).build());
    }

    public Notification createProcessingNotification() {
        String ongoingRecording = this.mAudioSource == ScreenRecordingAudioSource.NONE ? strings().getOngoingRecording() : strings().res.getString(R.string.screenrecord_ongoing_screen_and_audio);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        return new Notification.Builder(this, getChannelId()).setContentTitle(ongoingRecording).setContentText(strings().getBackgroundProcessingLabel()).setSmallIcon(R.drawable.ic_screenrecord).setGroup("screen_record_saved").addExtras(bundle).build();
    }

    public void createRecordingNotification() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        startForeground(this.mNotificationId, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(this.mAudioSource == ScreenRecordingAudioSource.NONE ? strings().getOngoingRecording() : strings().res.getString(R.string.screenrecord_ongoing_screen_and_audio)).setUsesChronometer(true).setColorized(true).setColor(getResources().getColor(R.color.GM2_red_700)).setOngoing(true).setForegroundServiceBehavior(1).addAction(new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_android), strings().res.getString(R.string.screenrecord_stop_label), PendingIntent.getService(this, 2, new Intent(this, getClass()).setAction("com.android.systemui.screenrecord.STOP_FROM_NOTIF"), 201326592)).build()).addExtras(bundle).build());
    }

    public Notification createSaveNotification(ScreenMediaRecorder.SavedRecording savedRecording) {
        Uri uri = savedRecording != null ? savedRecording.mUri : null;
        Intent dataAndType = new Intent("android.intent.action.VIEW").setFlags(268435457).setDataAndType(uri, "video/mp4");
        Notification.Action build = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_screenrecord), strings().res.getString(R.string.screenrecord_share_label), PendingIntent.getService(this, 2, new Intent(this, getClass()).setAction("com.android.systemui.screenrecord.SHARE").putExtra("extra_path", uri), 201326592)).build();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        Notification.Builder addExtras = new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(strings().getSaveTitle()).setContentText(strings().res.getString(R.string.screenrecord_save_text)).setContentIntent(PendingIntent.getActivity(this, 2, dataAndType, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)).addAction(build).setAutoCancel(true).setGroup("screen_record_saved").addExtras(bundle);
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
    public final IBinder onBind(Intent intent) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0075, code lost:
    
        if (r4.equals("com.android.systemui.screenrecord.START_NOTIF") == false) goto L7;
     */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onStartCommand(android.content.Intent r12, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenrecord.RecordingService.onStartCommand(android.content.Intent, int, int):int");
    }

    public final void postGroupNotification(UserHandle userHandle) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", strings().getTitle());
        this.mNotificationManager.notifyAsUser(getTag(), 4273, new Notification.Builder(this, getChannelId()).setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(strings().getSaveTitle()).setGroup("screen_record_saved").setGroupSummary(true).setExtras(bundle).build(), userHandle);
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
                int i2 = RecordingService.$r8$clinit;
                recordingService.getClass();
                try {
                    Log.d(recordingService.getTag(), "saving recording");
                    Notification createSaveNotification = recordingService.createSaveNotification(recordingService.getRecorder() != null ? recordingService.getRecorder().save() : null);
                    recordingService.postGroupNotification(userHandle2);
                    recordingService.mNotificationManager.notifyAsUser(null, recordingService.mNotificationId, createSaveNotification, userHandle2);
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

    public final void stopService(int i) {
        if (i == -1) {
            i = ((UserTrackerImpl) this.mUserContextTracker).getUserContext().getUserId();
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "notifying for user ", getTag());
        Settings.System.putInt(getContentResolver(), "show_touches", this.mOriginalShowTaps ? 1 : 0);
        try {
            if (getRecorder() != null) {
                getRecorder().end();
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
            showErrorToast(R.string.screenrecord_start_error);
            Log.e(getTag(), "stopRecording called, but there was an error when endingrecording");
            e.printStackTrace();
            createErrorNotification();
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
