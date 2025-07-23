package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenQuickReplyCoordinatorKt {
    private static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(new SubscreenQuickReplyCoordinatorKt$$ExternalSyntheticLambda0());
    private static final String TAG = "SubscreenQuickReplyCoordinator";

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean DEBUG_delegate$lambda$0() {
        return Log.isLoggable(TAG, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getDEBUG() {
        return ((Boolean) DEBUG$delegate.getValue()).booleanValue();
    }
}
