package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$1;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$2;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindFooter$2$disposableHandle$3;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FooterViewBinder {
    public static final FooterViewBinder INSTANCE = new FooterViewBinder();

    private FooterViewBinder() {
    }

    public static RepeatWhenAttachedKt$repeatWhenAttached$1 bindWhileAttached(FooterView footerView, FooterViewModel footerViewModel, NotificationListViewBinder$bindFooter$2$disposableHandle$1 notificationListViewBinder$bindFooter$2$disposableHandle$1, NotificationListViewBinder$bindFooter$2$disposableHandle$2 notificationListViewBinder$bindFooter$2$disposableHandle$2, NotificationListViewBinder$bindFooter$2$disposableHandle$3 notificationListViewBinder$bindFooter$2$disposableHandle$3) {
        FooterViewBinder$bindWhileAttached$1 footerViewBinder$bindWhileAttached$1 = new FooterViewBinder$bindWhileAttached$1(footerView, footerViewModel, notificationListViewBinder$bindFooter$2$disposableHandle$1, notificationListViewBinder$bindFooter$2$disposableHandle$2, notificationListViewBinder$bindFooter$2$disposableHandle$3, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(footerView, EmptyCoroutineContext.INSTANCE, footerViewBinder$bindWhileAttached$1);
    }
}
