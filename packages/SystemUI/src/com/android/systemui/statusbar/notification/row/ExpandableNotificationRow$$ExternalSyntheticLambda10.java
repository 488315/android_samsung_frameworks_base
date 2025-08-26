package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda10 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
        ((NotificationContentView) obj).invalidate();
    }
}
