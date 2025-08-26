package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.AccessibilityActionsViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* loaded from: classes2.dex */
public final class AccessibilityActionsViewBinder {
    public static final AccessibilityActionsViewBinder INSTANCE = new AccessibilityActionsViewBinder();

    private AccessibilityActionsViewBinder() {
    }

    public static RepeatWhenAttachedKt.C09181 bind(View view, AccessibilityActionsViewModel accessibilityActionsViewModel) {
        AccessibilityActionsViewBinder$bind$disposableHandle$1 accessibilityActionsViewBinder$bind$disposableHandle$1 = new AccessibilityActionsViewBinder$bind$disposableHandle$1(view, accessibilityActionsViewModel, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, accessibilityActionsViewBinder$bind$disposableHandle$1);
    }
}
