package com.android.server.am;

import android.os.OomKillRecord;
import android.util.Slog;

public final class OomConnection {
    public final ProcessList.AnonymousClass1 mOomListener;

    public final class OomConnectionThread extends Thread {
        public OomConnectionThread() {}

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            while (true) {
                try {
                    OomConnection.this.mOomListener.handleOomEvent(OomConnection.waitOom());
                } catch (RuntimeException e) {
                    Slog.e("OomConnection", "failed waiting for OOM events: " + e);
                    return;
                }
            }
        }
    }

    public OomConnection(ProcessList.AnonymousClass1 anonymousClass1) {
        this.mOomListener = anonymousClass1;
        new OomConnectionThread().start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native OomKillRecord[] waitOom();
}
