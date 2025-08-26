package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationListViewModel$$ExternalSyntheticLambda10 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(((NotificationListViewModel.VisibilityChange) obj).getVisible() == ((NotificationListViewModel.VisibilityChange) obj2).getVisible());
    }
}
