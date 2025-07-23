package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ManagedProfileControllerExtKt {
    public static final Flow getHasActiveWorkProfile(ManagedProfileController managedProfileController) {
        return FlowConflatedKt.conflatedCallbackFlow(new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(managedProfileController, null));
    }
}
