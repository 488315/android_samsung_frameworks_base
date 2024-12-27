package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.CollapsedStatusBarViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CollapsedStatusBarViewBinderImpl implements CollapsedStatusBarViewBinder {
    public final void bind(View view, CollapsedStatusBarViewModel collapsedStatusBarViewModel, CollapsedStatusBarFragment.AnonymousClass5 anonymousClass5) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new CollapsedStatusBarViewBinderImpl$bind$1(view, collapsedStatusBarViewModel, anonymousClass5, this, null));
    }
}
