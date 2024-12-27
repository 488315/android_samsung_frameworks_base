package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LocationControllerExtKt {
    public static final Flow isLocationEnabledFlow(LocationController locationController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new LocationControllerExtKt$isLocationEnabledFlow$2(locationController, null), FlowConflatedKt.conflatedCallbackFlow(new LocationControllerExtKt$isLocationEnabledFlow$1(locationController, null)));
    }
}
