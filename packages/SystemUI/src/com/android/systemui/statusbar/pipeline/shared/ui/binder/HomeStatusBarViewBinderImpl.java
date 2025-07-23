package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.ConnectedDisplaysStatusBarNotificationIconViewStore;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModel;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeStatusBarViewBinderImpl implements HomeStatusBarViewBinder {
    public HomeStatusBarViewBinderImpl(ConnectedDisplaysStatusBarNotificationIconViewStore.Factory factory) {
    }

    public final void bind(int i, View view, HomeStatusBarViewModel homeStatusBarViewModel, Function1 function1, Function1 function12, CollapsedStatusBarFragment.AnonymousClass5 anonymousClass5) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new HomeStatusBarViewBinderImpl$bind$1(this, i, anonymousClass5, view, view.requireViewById(R.id.ongoing_activity_chip_primary), homeStatusBarViewModel, view.requireViewById(R.id.clock), view.requireViewById(R.id.notificationIcons), view.requireViewById(R.id.status_bar_end_side_content), function1, function12, null));
    }
}
