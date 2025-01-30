package com.android.systemui.volume;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.KeyEvent;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CsdWarningDialog extends SystemUIDialog implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
    public static final String TAG = Util.logTag(CsdWarningDialog.class);
    public final AudioManager mAudioManager;
    public ExecutorImpl.ExecutionToken mCancelScheduledNoUserActionRunnable;
    public final Context mContext;
    public final int mCsdWarning;
    public final DelayableExecutor mDelayableExecutor;
    public final CsdWarningDialog$$ExternalSyntheticLambda0 mNoUserActionRunnable;
    public final NotificationManager mNotificationManager;
    public final Runnable mOnCleanup;
    public final C35981 mReceiver;
    public long mShowTime;
    public final Object mTimerLock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        CsdWarningDialog create(int i, Runnable runnable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0077  */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.systemui.volume.CsdWarningDialog$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.volume.CsdWarningDialog$$ExternalSyntheticLambda0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CsdWarningDialog(int i, Context context, AudioManager audioManager, NotificationManager notificationManager, DelayableExecutor delayableExecutor, Runnable runnable) {
        super(context);
        int i2;
        this.mTimerLock = new Object();
        this.mCancelScheduledNoUserActionRunnable = null;
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.volume.CsdWarningDialog.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    if (C3599D.BUG) {
                        Log.d(CsdWarningDialog.TAG, "Received ACTION_CLOSE_SYSTEM_DIALOGS");
                    }
                    CsdWarningDialog.this.cancel();
                    Runnable runnable2 = CsdWarningDialog.this.mOnCleanup;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }
        };
        this.mReceiver = r1;
        this.mCsdWarning = i;
        this.mContext = context;
        this.mAudioManager = audioManager;
        this.mNotificationManager = notificationManager;
        this.mOnCleanup = runnable;
        this.mDelayableExecutor = delayableExecutor;
        getWindow().setType(2010);
        SystemUIDialog.setShowForAllUsers(this);
        if (i != 1) {
            if (i == 3) {
                i2 = R.string.face_error_unable_to_process;
                setMessage(context.getString(i2));
                setButton(-1, context.getString(com.android.systemui.R.string.csd_button_keep_listening), this);
                setButton(-2, context.getString(com.android.systemui.R.string.csd_button_lower_volume), this);
                setOnDismissListener(this);
                context.registerReceiver(r1, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
                if (i != 1) {
                    this.mNoUserActionRunnable = new Runnable() { // from class: com.android.systemui.volume.CsdWarningDialog$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CsdWarningDialog csdWarningDialog = CsdWarningDialog.this;
                            if (csdWarningDialog.mCsdWarning == 1) {
                                csdWarningDialog.mAudioManager.lowerVolumeToRs1();
                                csdWarningDialog.sendNotification(false);
                            }
                        }
                    };
                    return;
                } else {
                    this.mNoUserActionRunnable = null;
                    return;
                }
            }
            Log.e(TAG, AbstractC0000x2c234b15.m0m("Invalid CSD warning event ", i), new Exception());
        }
        i2 = R.string.face_error_timeout;
        setMessage(context.getString(i2));
        setButton(-1, context.getString(com.android.systemui.R.string.csd_button_keep_listening), this);
        setButton(-2, context.getString(com.android.systemui.R.string.csd_button_lower_volume), this);
        setOnDismissListener(this);
        context.registerReceiver(r1, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
        if (i != 1) {
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Log.d(TAG, "Lower volume pressed for CSD warning " + this.mCsdWarning);
            dismiss();
        }
        if (C3599D.BUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("on click ", i, TAG);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mCsdWarning == 2) {
            this.mAudioManager.lowerVolumeToRs1();
        }
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error unregistering broadcast receiver", e);
        }
        Runnable runnable = this.mOnCleanup;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 24) {
            return true;
        }
        if (i == 25 && System.currentTimeMillis() - this.mShowTime > 1000) {
            Log.i(TAG, "Confirmed CSD exposure warning via VOLUME_DOWN");
            dismiss();
        }
        return super.onKeyUp(i, keyEvent);
    }

    public final void sendNotification(boolean z) {
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, new Intent("android.settings.SOUND_SETTINGS"), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        String string = z ? this.mContext.getString(com.android.systemui.R.string.csd_500_system_lowered_text) : this.mContext.getString(com.android.systemui.R.string.csd_system_lowered_text);
        this.mNotificationManager.notify(1007, new Notification.Builder(this.mContext, "ALR").setSmallIcon(com.android.systemui.R.drawable.hearing).setContentTitle(this.mContext.getString(com.android.systemui.R.string.csd_lowered_title)).setContentText(string).setContentIntent(activity).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setLocalOnly(true).setAutoCancel(true).setCategory("sys").build());
    }

    @Override // android.app.Dialog
    public final void show() {
        int i = this.mCsdWarning;
        if (i != 2) {
            super.show();
            return;
        }
        if (i == 2) {
            this.mAudioManager.lowerVolumeToRs1();
            sendNotification(true);
        } else {
            Log.w(TAG, "Notification dose repeat 5x is not shown for " + this.mCsdWarning);
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        this.mShowTime = System.currentTimeMillis();
        synchronized (this.mTimerLock) {
            CsdWarningDialog$$ExternalSyntheticLambda0 csdWarningDialog$$ExternalSyntheticLambda0 = this.mNoUserActionRunnable;
            if (csdWarningDialog$$ExternalSyntheticLambda0 != null) {
                this.mCancelScheduledNoUserActionRunnable = this.mDelayableExecutor.executeDelayed(5000L, csdWarningDialog$$ExternalSyntheticLambda0);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        synchronized (this.mTimerLock) {
            ExecutorImpl.ExecutionToken executionToken = this.mCancelScheduledNoUserActionRunnable;
            if (executionToken != null) {
                executionToken.run();
            }
        }
    }
}
