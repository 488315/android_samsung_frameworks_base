package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* loaded from: classes3.dex */
public final class RemoteInputCoordinatorKt {
    private static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(new RemoteInputCoordinatorKt$$ExternalSyntheticLambda0());
    private static final long REMOTE_INPUT_ACTIVE_EXTENDER_AUTO_CANCEL_DELAY = 500;
    private static final long REMOTE_INPUT_EXTENDER_RELEASE_DELAY = 200;
    private static final String TAG = "RemoteInputCoordinator";

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean DEBUG_delegate$lambda$0() {
        return Log.isLoggable(TAG, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
