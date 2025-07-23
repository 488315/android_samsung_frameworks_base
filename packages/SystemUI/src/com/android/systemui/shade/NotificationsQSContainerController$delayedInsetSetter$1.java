package com.android.systemui.shade;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsQSContainerController$delayedInsetSetter$1 implements Runnable, Consumer {
    public Runnable canceller;
    public final /* synthetic */ NotificationsQSContainerController this$0;

    public NotificationsQSContainerController$delayedInsetSetter$1(NotificationsQSContainerController notificationsQSContainerController) {
        this.this$0 = notificationsQSContainerController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        WindowInsets windowInsets = (WindowInsets) obj;
        windowInsets.getStableInsetBottom();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            displayCutout.getSafeInsetBottom();
        }
        Runnable runnable = this.canceller;
        if (runnable != null) {
            runnable.run();
        }
        this.canceller = this.this$0.delayableExecutor.executeDelayed(this, 500L);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.getClass();
        this.this$0.getClass();
        this.this$0.getClass();
    }
}
