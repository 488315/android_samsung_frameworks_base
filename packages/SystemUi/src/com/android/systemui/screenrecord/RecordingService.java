package com.android.systemui.screenrecord;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RecordingService extends Service implements ScreenMediaRecorder.ScreenMediaRecorderListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ScreenRecordingAudioSource mAudioSource;
    public final RecordingController mController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final Executor mLongExecutor;
    public final Handler mMainHandler;
    public int mNotificationId = 4273;
    public final NotificationManager mNotificationManager;
    public boolean mOriginalShowTaps;
    public ScreenMediaRecorder mRecorder;
    public boolean mShowTaps;
    public final UiEventLogger mUiEventLogger;
    public final UserContextProvider mUserContextTracker;

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
        Resources resources = getResources();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(R.string.screenrecord_title));
        startForeground(this.mNotificationId, new Notification.Builder(this, "screen_record").setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(resources.getString(R.string.screenrecord_start_error)).addExtras(bundle).build());
    }

    public Notification createProcessingNotification() {
        Resources resources = getApplicationContext().getResources();
        String string = this.mAudioSource == ScreenRecordingAudioSource.NONE ? resources.getString(R.string.screenrecord_ongoing_screen_only) : resources.getString(R.string.screenrecord_ongoing_screen_and_audio);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(R.string.screenrecord_title));
        return new Notification.Builder(this, "screen_record").setContentTitle(string).setContentText(getResources().getString(R.string.screenrecord_background_processing_label)).setSmallIcon(R.drawable.ic_screenrecord).setGroup("screen_record_saved").addExtras(bundle).build();
    }

    public void createRecordingNotification() {
        Resources resources = getResources();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(R.string.screenrecord_title));
        startForeground(this.mNotificationId, new Notification.Builder(this, "screen_record").setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(this.mAudioSource == ScreenRecordingAudioSource.NONE ? resources.getString(R.string.screenrecord_ongoing_screen_only) : resources.getString(R.string.screenrecord_ongoing_screen_and_audio)).setUsesChronometer(true).setColorized(true).setColor(getResources().getColor(R.color.GM2_red_700)).setOngoing(true).setForegroundServiceBehavior(1).addAction(new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_android), getResources().getString(R.string.screenrecord_stop_label), PendingIntent.getService(this, 2, new Intent(this, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.STOP_FROM_NOTIF"), 201326592)).build()).addExtras(bundle).build());
    }

    public Notification createSaveNotification(ScreenMediaRecorder.SavedRecording savedRecording) {
        Uri uri = savedRecording.mUri;
        Intent dataAndType = new Intent("android.intent.action.VIEW").setFlags(268435457).setDataAndType(uri, "video/mp4");
        Notification.Action build = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.ic_screenrecord), getResources().getString(R.string.screenrecord_share_label), PendingIntent.getService(this, 2, new Intent(this, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.SHARE").putExtra("extra_path", uri.toString()), 201326592)).build();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", getResources().getString(R.string.screenrecord_title));
        Notification.Builder addExtras = new Notification.Builder(this, "screen_record").setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(getResources().getString(R.string.screenrecord_save_title)).setContentText(getResources().getString(R.string.screenrecord_save_text)).setContentIntent(PendingIntent.getActivity(this, 2, dataAndType, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)).addAction(build).setAutoCancel(true).setGroup("screen_record_saved").addExtras(bundle);
        Bitmap bitmap = savedRecording.mThumbnailBitmap;
        if (bitmap != null) {
            addExtras.setStyle(new Notification.BigPictureStyle().bigPicture(bitmap).showBigPictureWhenCollapsed(true));
        }
        return addExtras.build();
    }

    public ScreenMediaRecorder getRecorder() {
        return this.mRecorder;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        if (r1.equals("com.android.systemui.screenrecord.STOP_FROM_NOTIF") == false) goto L7;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int onStartCommand(Intent intent, int i, int i2) {
        Object[] objArr;
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        Log.d("RecordingService", "onStartCommand " + action);
        char c = 3;
        NotificationChannel notificationChannel = new NotificationChannel("screen_record", getString(R.string.screenrecord_title), 3);
        notificationChannel.setDescription(getString(R.string.screenrecord_channel_description));
        notificationChannel.enableVibration(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        int userId = ((UserTrackerImpl) this.mUserContextTracker).getUserContext().getUserId();
        final UserHandle userHandle = new UserHandle(userId);
        action.getClass();
        switch (action.hashCode()) {
            case -1688140755:
                if (action.equals("com.android.systemui.screenrecord.SHARE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1687783248:
                if (action.equals("com.android.systemui.screenrecord.START")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -470086188:
                if (action.equals("com.android.systemui.screenrecord.STOP")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -288359034:
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                final Intent putExtra = new Intent("android.intent.action.SEND").setType("video/mp4").putExtra("android.intent.extra.STREAM", Uri.parse(intent.getStringExtra("extra_path")));
                this.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        int i3 = RecordingService.$r8$clinit;
                        RecordingService recordingService = RecordingService.this;
                        recordingService.startActivity(Intent.createChooser(putExtra, recordingService.getResources().getString(R.string.screenrecord_share_label)).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE));
                        recordingService.mNotificationManager.cancelAsUser(null, recordingService.mNotificationId, userHandle);
                        return false;
                    }
                }, false, false);
                closeSystemDialogs();
                return 1;
            case 1:
                this.mNotificationId = ((int) SystemClock.uptimeMillis()) + 4273;
                this.mAudioSource = ScreenRecordingAudioSource.values()[intent.getIntExtra("extra_useAudio", 0)];
                Log.d("RecordingService", "recording with audio source " + this.mAudioSource);
                this.mShowTaps = intent.getBooleanExtra("extra_showTaps", false);
                MediaProjectionCaptureTarget mediaProjectionCaptureTarget = (MediaProjectionCaptureTarget) intent.getParcelableExtra("extra_captureTarget", MediaProjectionCaptureTarget.class);
                this.mOriginalShowTaps = Settings.System.getInt(getApplicationContext().getContentResolver(), "show_touches", 0) != 0;
                Settings.System.putInt(getContentResolver(), "show_touches", this.mShowTaps ? 1 : 0);
                this.mRecorder = new ScreenMediaRecorder(((UserTrackerImpl) this.mUserContextTracker).getUserContext(), this.mMainHandler, userId, this.mAudioSource, mediaProjectionCaptureTarget, this);
                try {
                    getRecorder().start();
                    objArr = true;
                } catch (RemoteException | IOException | RuntimeException e) {
                    showErrorToast(R.string.screenrecord_start_error);
                    e.printStackTrace();
                    objArr = false;
                }
                if (objArr == true) {
                    updateState(true);
                    createRecordingNotification();
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_START);
                    return 1;
                }
                updateState(false);
                createErrorNotification();
                stopForeground(2);
                stopSelf();
                return 2;
            case 2:
            case 3:
                if ("com.android.systemui.screenrecord.STOP_FROM_NOTIF".equals(action)) {
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_END_NOTIFICATION);
                } else {
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_END_QS_TILE);
                }
                stopService(intent.getIntExtra("android.intent.extra.user_handle", -1));
                return 1;
            default:
                return 1;
        }
    }

    public final void postGroupNotification(UserHandle userHandle) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", getResources().getString(R.string.screenrecord_title));
        this.mNotificationManager.notifyAsUser("RecordingService", 4273, new Notification.Builder(this, "screen_record").setSmallIcon(R.drawable.ic_screenrecord).setContentTitle(getResources().getString(R.string.screenrecord_save_title)).setGroup("screen_record_saved").setGroupSummary(true).setExtras(bundle).build(), userHandle);
    }

    public final void saveRecording(int i) {
        final UserHandle userHandle = new UserHandle(i);
        this.mNotificationManager.notifyAsUser(null, this.mNotificationId, createProcessingNotification(), userHandle);
        this.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RecordingService recordingService = RecordingService.this;
                UserHandle userHandle2 = userHandle;
                int i2 = RecordingService.$r8$clinit;
                recordingService.getClass();
                try {
                    Log.d("RecordingService", "saving recording");
                    Notification createSaveNotification = recordingService.createSaveNotification(recordingService.getRecorder().save());
                    recordingService.postGroupNotification(userHandle2);
                    recordingService.mNotificationManager.notifyAsUser(null, recordingService.mNotificationId, createSaveNotification, userHandle2);
                } catch (IOException | IllegalStateException e) {
                    Log.e("RecordingService", "Error saving screen recording: " + e.getMessage());
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
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("notifying for user ", i, "RecordingService");
        Settings.System.putInt(getContentResolver(), "show_touches", this.mOriginalShowTaps ? 1 : 0);
        if (getRecorder() != null) {
            try {
                getRecorder().end();
                saveRecording(i);
            } catch (RuntimeException e) {
                ScreenMediaRecorder recorder = getRecorder();
                File file = recorder.mTempVideoFile;
                if (file != null) {
                    file.delete();
                }
                File file2 = recorder.mTempAudioFile;
                if (file2 != null) {
                    file2.delete();
                }
                showErrorToast(R.string.screenrecord_start_error);
                Log.e("RecordingService", "stopRecording called, but there was an error when endingrecording");
                e.printStackTrace();
                createErrorNotification();
            } catch (Throwable th) {
                ScreenMediaRecorder recorder2 = getRecorder();
                File file3 = recorder2.mTempVideoFile;
                if (file3 != null) {
                    file3.delete();
                }
                File file4 = recorder2.mTempAudioFile;
                if (file4 != null) {
                    file4.delete();
                }
                throw new RuntimeException(th);
            }
        } else {
            Log.e("RecordingService", "stopRecording called, but recorder was null");
        }
        updateState(false);
        stopForeground(2);
        stopSelf();
    }

    public final void updateState(boolean z) {
        if (((UserTrackerImpl) this.mUserContextTracker).getUserContext().getUserId() == 0) {
            this.mController.updateState(z);
            return;
        }
        Intent intent = new Intent("com.android.systemui.screenrecord.UPDATE_STATE");
        intent.putExtra("extra_state", z);
        intent.addFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }
}
