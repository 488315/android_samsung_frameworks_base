package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationScrollViewBinder extends FlowDumperImpl {
    public final ConfigurationState configuration;
    public final NotificationScrollView view;
    public final MutableStateFlow viewLeftOffset;
    public final NotificationScrollViewModel viewModel;

    public NotificationScrollViewBinder(DumpManager dumpManager, CoroutineDispatcher coroutineDispatcher, NotificationScrollView notificationScrollView, NotificationScrollViewModel notificationScrollViewModel, ConfigurationState configurationState) {
        super(dumpManager, null, 2, null);
    }
}
