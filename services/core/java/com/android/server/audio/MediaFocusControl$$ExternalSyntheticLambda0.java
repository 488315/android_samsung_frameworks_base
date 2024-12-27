package com.android.server.audio;

public final /* synthetic */ class MediaFocusControl$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        Object obj = MediaFocusControl.mAudioFocusLock;
        synchronized (obj) {
            obj.notify();
        }
    }
}
