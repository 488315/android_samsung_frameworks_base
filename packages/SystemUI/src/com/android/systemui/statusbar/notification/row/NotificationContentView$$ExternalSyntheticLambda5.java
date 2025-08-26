package com.android.systemui.statusbar.notification.row;

import android.R;
import android.view.View;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationContentView$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ NotificationContentView f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        View viewFindViewById;
        View viewFindViewById2;
        NotificationContentView notificationContentView = this.f$0;
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        notificationContentView.mRemoteInputVisible = zBooleanValue;
        notificationContentView.setClipChildren(!zBooleanValue);
        int i = zBooleanValue ? 4 : 0;
        View view = notificationContentView.mExpandedChild;
        if (view != null && (viewFindViewById2 = view.findViewById(R.id.animation)) != null) {
            viewFindViewById2.setImportantForAccessibility(i);
        }
        View view2 = notificationContentView.mHeadsUpChild;
        if (view2 == null || (viewFindViewById = view2.findViewById(R.id.animation)) == null) {
            return;
        }
        viewFindViewById.setImportantForAccessibility(i);
    }
}
