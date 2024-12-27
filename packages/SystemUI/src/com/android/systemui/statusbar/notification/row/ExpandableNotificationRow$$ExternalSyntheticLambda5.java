package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
        ((NotificationContentView) obj).invalidate();
    }
}
