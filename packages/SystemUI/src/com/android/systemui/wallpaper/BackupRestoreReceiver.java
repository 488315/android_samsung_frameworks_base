package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BackupRestoreReceiver {
    public final AnonymousClass1 mHandler = new Handler(this) { // from class: com.android.systemui.wallpaper.BackupRestoreReceiver.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Context context = (Context) message.obj;
            Bundle data = message.getData();
            String string = data.getString("SAVE_PATH");
            String string2 = data.getString("SOURCE");
            String string3 = data.getString("SESSION_KEY");
            String string4 = data.getString("EXPORT_SESSION_TIME");
            int i = data.getInt("SECURITY_LEVEL");
            int i2 = data.getInt("WHICH");
            String string5 = data.getString("EXTRA_RESTORE_VALUE");
            int i3 = message.what;
            if (i3 == 0) {
                WallpaperManager.startBackupWallpaper(context, i2, string, string2, i, string4, string3);
            } else {
                if (i3 != 1) {
                    return;
                }
                WallpaperManager.startRestoreWallpaper(context, i2, string, string2, i, string3, string5);
            }
        }
    };
    public final AnonymousClass2 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.wallpaper.BackupRestoreReceiver.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("WallpaperBackupRestoreReceiver", "onReceive ( action = " + action + ")");
            Message obtainMessage = (action.equals("com.sec.android.intent.action.REQUEST_BACKUP_LOCKSCREEN") || action.equals("com.sec.android.intent.action.REQUEST_BACKUP_WALLPAPER")) ? obtainMessage(0, context) : obtainMessage(1, context);
            BackupRestoreReceiver.this.getClass();
            Bundle bundle = new Bundle();
            bundle.putString("SAVE_PATH", intent.getStringExtra("SAVE_PATH"));
            bundle.putString("SOURCE", intent.getStringExtra("SOURCE"));
            bundle.putString("SESSION_KEY", intent.getStringExtra("SESSION_KEY"));
            bundle.putString("EXPORT_SESSION_TIME", intent.getStringExtra("EXPORT_SESSION_TIME"));
            bundle.putInt("ACTION", intent.getIntExtra("ACTION", 0));
            bundle.putInt("SECURITY_LEVEL", intent.getIntExtra("SECURITY_LEVEL", 0));
            String action2 = intent.getAction();
            bundle.putInt("WHICH", intent.getIntExtra("WHICH", (action2.equals("com.sec.android.intent.action.REQUEST_BACKUP_LOCKSCREEN") || action2.equals("com.sec.android.intent.action.REQUEST_RESTORE_LOCKSCREEN")) ? 2 : 1));
            bundle.putString("EXTRA_RESTORE_VALUE", intent.getStringExtra("EXTRA_RESTORE_VALUE"));
            obtainMessage.setData(bundle);
            sendMessage(obtainMessage);
        }
    };
}
