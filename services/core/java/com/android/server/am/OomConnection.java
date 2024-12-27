package com.android.server.am;

import android.os.OomKillRecord;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OomConnection {
    public final ProcessList.AnonymousClass1 mOomListener;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
