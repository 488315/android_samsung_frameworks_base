package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.widget.NotificationOptimizedLinearLayout;
import kotlin.jvm.internal.Intrinsics;

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
