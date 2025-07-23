package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.widget.NotificationOptimizedLinearLayout;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationOptimizedLinearLayoutFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (Intrinsics.areEqual(str, LinearLayout.class.getName()) || Intrinsics.areEqual(str, "LinearLayout")) {
            return new NotificationOptimizedLinearLayout(context, attributeSet);
        }
        return null;
    }
}
