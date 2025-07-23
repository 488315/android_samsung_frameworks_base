package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda10 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
        ((NotificationContentView) obj).invalidate();
    }
}
