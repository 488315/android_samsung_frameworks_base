package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class RemoteInputCoordinatorKt {
    private static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt$DEBUG$2
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            return Boolean.valueOf(Log.isLoggable("RemoteInputCoordinator", 3));
        }
    });
    private static final long REMOTE_INPUT_ACTIVE_EXTENDER_AUTO_CANCEL_DELAY = 500;
    private static final long REMOTE_INPUT_EXTENDER_RELEASE_DELAY = 200;
    private static final String TAG = "RemoteInputCoordinator";

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
