package com.android.systemui.statusbar.notification.row;

import android.R;
import android.view.View;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationContentView$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ NotificationContentView f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        View findViewById;
        View findViewById2;
        NotificationContentView notificationContentView = this.f$0;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        notificationContentView.mRemoteInputVisible = booleanValue;
        notificationContentView.setClipChildren(!booleanValue);
        int i = booleanValue ? 4 : 0;
        View view = notificationContentView.mExpandedChild;
        if (view != null && (findViewById2 = view.findViewById(R.id.animation)) != null) {
            findViewById2.setImportantForAccessibility(i);
        }
        View view2 = notificationContentView.mHeadsUpChild;
        if (view2 == null || (findViewById = view2.findViewById(R.id.animation)) == null) {
            return;
        }
        findViewById.setImportantForAccessibility(i);
    }
}
