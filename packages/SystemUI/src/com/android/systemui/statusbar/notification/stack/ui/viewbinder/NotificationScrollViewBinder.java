package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.R;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.common.ui.ConfigurationStateImpl;
import com.android.systemui.common.ui.ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationScrollViewModel;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationScrollViewBinder extends FlowDumperImpl {
    public final ConfigurationState configuration;
    public final ConfigurationStateImpl$getDimensionPixelSize$$inlined$map$1 maxBlurRadius;
    public final NotificationScrollView view;
    public final MutableStateFlow viewLeftOffset;
    public final NotificationScrollViewModel.Factory viewModelFactory;

    public NotificationScrollViewBinder(DumpManager dumpManager, CoroutineDispatcher coroutineDispatcher, NotificationScrollView notificationScrollView, NotificationScrollViewModel.Factory factory, ConfigurationState configurationState) {
        super(dumpManager, null, 2, null);
        this.view = notificationScrollView;
        this.viewModelFactory = factory;
        this.configuration = configurationState;
        this.viewLeftOffset = (MutableStateFlow) dumpValue(StateFlowKt.MutableStateFlow(0), "viewLeftOffset");
        this.maxBlurRadius = ((ConfigurationStateImpl) configurationState).getDimensionPixelSize(R.dimen.max_shade_content_blur_radius);
    }
}
