package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class SubscreenQuickReplyCoordinatorKt {
    private static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenQuickReplyCoordinatorKt$DEBUG$2
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(Log.isLoggable("SubscreenQuickReplyCoordinator", 3));
        }
    });
    private static final String TAG = "SubscreenQuickReplyCoordinator";

    public static final boolean getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
