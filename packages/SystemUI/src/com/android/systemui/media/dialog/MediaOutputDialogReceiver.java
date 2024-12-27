package com.android.systemui.media.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.settingslib.flags.Flags;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                if (hashCode == 2052997846 && action.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG")) {
                    Flags.legacyLeAudioSharing();
                    return;
                }
                return;
            }
            if (action.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")) {
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
