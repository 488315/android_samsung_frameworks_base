package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ ExpandableNotificationRow f$0;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda6(ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = expandableNotificationRow;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = ExpandableNotificationRow.BASE_VALUE;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.performDismiss(false);
        }
    }
}
