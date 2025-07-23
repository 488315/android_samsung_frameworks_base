package com.android.systemui.media.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputDialogReceiver extends BroadcastReceiver {
    public final MediaOutputDialogManager mediaOutputDialogManager;

    public MediaOutputDialogReceiver(MediaOutputDialogManager mediaOutputDialogManager, MediaOutputBroadcastDialogManager mediaOutputBroadcastDialogManager) {
        this.mediaOutputDialogManager = mediaOutputDialogManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            int hashCode = action.hashCode();
            if (hashCode == -2095758866) {
                if (action.equals("com.android.systemui.action.LAUNCH_SYSTEM_MEDIA_OUTPUT_DIALOG")) {
                    MediaOutputDialogManager mediaOutputDialogManager = this.mediaOutputDialogManager;
                    int i = MediaOutputDialogManager.$r8$clinit;
                    mediaOutputDialogManager.createAndShow(null, false, null, false, null, null);
                    return;
                }
                return;
            }
            if (hashCode != 1575256440) {
                if (hashCode != 2052997846) {
                    return;
                }
                action.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG");
            } else if (action.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")) {
                String stringExtra = intent.getStringExtra("package_name");
                if (stringExtra == null || stringExtra.length() == 0) {
                    if (MediaOutputDialogReceiverKt.DEBUG) {
                        Log.e("MediaOutputDlgReceiver", "Unable to launch media output dialog. Package name is empty.");
                    }
                } else {
                    MediaOutputDialogManager mediaOutputDialogManager2 = this.mediaOutputDialogManager;
                    int i2 = MediaOutputDialogManager.$r8$clinit;
                    mediaOutputDialogManager2.createAndShow(stringExtra, false, null, true, null, null);
                }
            }
        }
    }
}
