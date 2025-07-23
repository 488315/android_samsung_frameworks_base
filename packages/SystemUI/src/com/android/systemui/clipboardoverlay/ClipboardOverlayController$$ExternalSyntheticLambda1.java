package com.android.systemui.clipboardoverlay;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ClipboardOverlayController f$0;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda1(ClipboardOverlayController clipboardOverlayController, int i) {
        this.$r8$classId = i;
        this.f$0 = clipboardOverlayController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ClipboardOverlayController clipboardOverlayController = this.f$0;
        switch (i) {
            case 0:
                clipboardOverlayController.mClipboardLogger.logSessionComplete(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_DISMISSED_OTHER);
                clipboardOverlayController.hideImmediate();
                break;
            case 1:
                ClipboardOverlayWindow clipboardOverlayWindow = clipboardOverlayController.mWindow;
                ClipboardOverlayView clipboardOverlayView = clipboardOverlayController.mView;
                clipboardOverlayWindow.setContentView(clipboardOverlayView);
                clipboardOverlayView.setInsets(clipboardOverlayWindow.mWindowManager.getCurrentWindowMetrics().getWindowInsets(), clipboardOverlayController.mContext.getResources().getConfiguration().orientation);
                break;
            default:
                clipboardOverlayController.finish(ClipboardOverlayEvent.CLIPBOARD_OVERLAY_TIMED_OUT);
                break;
        }
    }
}
