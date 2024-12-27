package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class LocationControllerExtKt {
    public static final Flow isLocationEnabledFlow(LocationController locationController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new LocationControllerExtKt$isLocationEnabledFlow$2(locationController, null), FlowConflatedKt.conflatedCallbackFlow(new LocationControllerExtKt$isLocationEnabledFlow$1(locationController, null)));
    }
}
