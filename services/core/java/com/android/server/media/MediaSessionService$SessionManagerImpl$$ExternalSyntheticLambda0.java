package com.android.server.media;

import android.view.KeyEvent;

public final /* synthetic */ class MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((MediaSessionService.SessionManagerImpl) obj).mSkippedFirstKeyFromKeyCustomizer =
                        false;
                return;
            default:
                MediaSessionService.SessionManagerImpl.KeyEventHandler keyEventHandler =
                        (MediaSessionService.SessionManagerImpl.KeyEventHandler) obj;
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        KeyEvent keyEvent = keyEventHandler.mTrackingFirstDownKeyEvent;
                        if (keyEvent != null) {
                            MediaSessionService mediaSessionService = MediaSessionService.this;
                            if (mediaSessionService.mVolumeKeyLongPressReceiver != null) {
                                MediaSessionService.m663$$Nest$mdispatchVolumeKeyLongPressLocked(
                                        mediaSessionService, keyEvent);
                                keyEventHandler.mIsLongPressing = true;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
