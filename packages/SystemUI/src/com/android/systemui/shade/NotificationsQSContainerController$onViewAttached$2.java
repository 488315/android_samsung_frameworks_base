package com.android.systemui.shade;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationsQSContainerController$onViewAttached$2 implements Consumer {
    public final /* synthetic */ NotificationsQSContainerController this$0;

    public NotificationsQSContainerController$onViewAttached$2(NotificationsQSContainerController notificationsQSContainerController) {
        this.this$0 = notificationsQSContainerController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.this$0.updateResources$1();
    }
}
