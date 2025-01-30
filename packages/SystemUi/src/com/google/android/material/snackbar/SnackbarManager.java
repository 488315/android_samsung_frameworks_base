package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SnackbarManager {
    public static SnackbarManager snackbarManager;
    public SnackbarRecord currentSnackbar;
    public SnackbarRecord nextSnackbar;
    public final Object lock = new Object();
    public final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.SnackbarManager.1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            SnackbarManager snackbarManager2 = SnackbarManager.this;
            SnackbarRecord snackbarRecord = (SnackbarRecord) message.obj;
            synchronized (snackbarManager2.lock) {
                if (snackbarManager2.currentSnackbar == snackbarRecord || snackbarManager2.nextSnackbar == snackbarRecord) {
                    snackbarManager2.cancelSnackbarLocked(snackbarRecord, 2);
                }
            }
            return true;
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SnackbarRecord {
        public final WeakReference callback;
        public int duration;
        public boolean paused;

        public SnackbarRecord(int i, Callback callback) {
            this.callback = new WeakReference(callback);
            this.duration = i;
        }
    }

    private SnackbarManager() {
    }

    public static SnackbarManager getInstance() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    public final boolean cancelSnackbarLocked(SnackbarRecord snackbarRecord, int i) {
        Callback callback = (Callback) snackbarRecord.callback.get();
        if (callback == null) {
            return false;
        }
        this.handler.removeCallbacksAndMessages(snackbarRecord);
        Handler handler = BaseTransientBottomBar.handler;
        handler.sendMessage(handler.obtainMessage(1, i, 0, BaseTransientBottomBar.this));
        return true;
    }

    public final boolean isCurrentSnackbarLocked(BaseTransientBottomBar.C43685 c43685) {
        SnackbarRecord snackbarRecord = this.currentSnackbar;
        if (snackbarRecord != null) {
            return c43685 != null && snackbarRecord.callback.get() == c43685;
        }
        return false;
    }

    public final void scheduleTimeoutLocked(SnackbarRecord snackbarRecord) {
        int i = snackbarRecord.duration;
        if (i == -2) {
            return;
        }
        if (i <= 0) {
            i = i == -1 ? 1500 : 2750;
        }
        Handler handler = this.handler;
        handler.removeCallbacksAndMessages(snackbarRecord);
        handler.sendMessageDelayed(Message.obtain(handler, 0, snackbarRecord), i);
    }

    public final void showNextSnackbarLocked() {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        if (snackbarRecord != null) {
            this.currentSnackbar = snackbarRecord;
            this.nextSnackbar = null;
            Callback callback = (Callback) snackbarRecord.callback.get();
            if (callback == null) {
                this.currentSnackbar = null;
            } else {
                Handler handler = BaseTransientBottomBar.handler;
                handler.sendMessage(handler.obtainMessage(0, BaseTransientBottomBar.this));
            }
        }
    }
}
