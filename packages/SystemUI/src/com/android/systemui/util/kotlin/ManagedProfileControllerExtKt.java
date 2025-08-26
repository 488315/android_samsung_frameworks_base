package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class ManagedProfileControllerExtKt {
    public static final Flow getHasActiveWorkProfile(ManagedProfileController managedProfileController) {
        return FlowConflatedKt.conflatedCallbackFlow(new ManagedProfileControllerExtKt$hasActiveWorkProfile$1(managedProfileController, null));
    }
}
