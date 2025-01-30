package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CollapsedStatusBarViewBinderImpl implements CollapsedStatusBarViewBinder {
    public final void bind(PhoneStatusBarView phoneStatusBarView, CollapsedStatusBarViewModel collapsedStatusBarViewModel, CollapsedStatusBarFragment.C31845 c31845) {
        RepeatWhenAttachedKt.repeatWhenAttached(phoneStatusBarView, EmptyCoroutineContext.INSTANCE, new CollapsedStatusBarViewBinderImpl$bind$1(collapsedStatusBarViewModel, c31845, null));
    }
}
