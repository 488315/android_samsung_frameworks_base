package com.android.systemui.shade;

import android.view.MotionEvent;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQsMediaTouchHelper$onTouch$1 implements Runnable {
    public final /* synthetic */ MotionEvent $event;
    public final /* synthetic */ SecQsMediaTouchHelper this$0;

    public SecQsMediaTouchHelper$onTouch$1(SecQsMediaTouchHelper secQsMediaTouchHelper, MotionEvent motionEvent) {
        this.this$0 = secQsMediaTouchHelper;
        this.$event = motionEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.this$0.notificationStackScrollLayoutController;
        notificationStackScrollLayoutController.mView.onMediaPlayerScroll(this.$event);
    }
}
