package com.android.systemui.wallpaper;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.log.WallpaperLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DlsRestoreDispatcher {
    public DlsRestoreHandler mHandler;
    public KeyguardWallpaperController.C36678 mOnRestoreDlsListener;
    public final PluginLockUtils mPluginLockUtils;
    public int mRetryCount = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DlsRestoreHandler extends Handler {
        public /* synthetic */ DlsRestoreHandler(DlsRestoreDispatcher dlsRestoreDispatcher, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Bundle data = message.getData();
            if (message.what != 0 || data == null) {
                return;
            }
            Bundle callProvider = DlsRestoreDispatcher.this.mPluginLockUtils.callProvider("restore_dls", data);
            if (callProvider != null ? callProvider.getBoolean("result", false) : false) {
                post(new Runnable() { // from class: com.android.systemui.wallpaper.DlsRestoreDispatcher$DlsRestoreHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardWallpaperController.C36678 c36678 = DlsRestoreDispatcher.this.mOnRestoreDlsListener;
                        c36678.getClass();
                        String concat = "onDlsRestored reason = ".concat("RESTORE_DLS_RESULT_SUCCESS");
                        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                        KeyguardWallpaperController.this.printLognAddHistory(concat);
                    }
                });
            } else {
                DlsRestoreDispatcher dlsRestoreDispatcher = DlsRestoreDispatcher.this;
                if (dlsRestoreDispatcher.mRetryCount < 20) {
                    Message message2 = new Message();
                    Bundle bundle = new Bundle(data);
                    message2.what = message.what;
                    message2.setData(bundle);
                    DlsRestoreDispatcher.this.mRetryCount++;
                    sendMessageDelayed(message2, 700L);
                } else {
                    KeyguardWallpaperController.C36678 c36678 = dlsRestoreDispatcher.mOnRestoreDlsListener;
                    if (c36678 != null) {
                        String concat = "onDlsRestored reason = ".concat("UNKNOWN");
                        KeyguardWallpaperController keyguardWallpaperController = KeyguardWallpaperController.sController;
                        KeyguardWallpaperController.this.printLognAddHistory(concat);
                    }
                }
            }
            super.handleMessage(message);
        }

        private DlsRestoreHandler() {
        }
    }

    public DlsRestoreDispatcher(Context context, WallpaperLogger wallpaperLogger, PluginLockUtils pluginLockUtils) {
        this.mPluginLockUtils = pluginLockUtils;
    }
}
