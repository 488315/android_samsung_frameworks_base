package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.AccessibilityActionsViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AccessibilityActionsViewBinder {
    public static final AccessibilityActionsViewBinder INSTANCE = new AccessibilityActionsViewBinder();

    private AccessibilityActionsViewBinder() {
    }

    public static RepeatWhenAttachedKt$repeatWhenAttached$1 bind(View view, AccessibilityActionsViewModel accessibilityActionsViewModel) {
        AccessibilityActionsViewBinder$bind$disposableHandle$1 accessibilityActionsViewBinder$bind$disposableHandle$1 = new AccessibilityActionsViewBinder$bind$disposableHandle$1(view, accessibilityActionsViewModel, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, accessibilityActionsViewBinder$bind$disposableHandle$1);
    }
}
