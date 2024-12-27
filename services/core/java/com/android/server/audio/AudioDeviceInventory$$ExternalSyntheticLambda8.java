package com.android.server.audio;

import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioDeviceInventory$$ExternalSyntheticLambda8
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        AdiDeviceState adiDeviceState = (AdiDeviceState) obj;
        AdiDeviceState adiDeviceState2 = (AdiDeviceState) obj2;
        adiDeviceState.setHasHeadTracker(adiDeviceState2.hasHeadTracker());
        adiDeviceState.setHeadTrackerEnabled(adiDeviceState2.isHeadTrackerEnabled());
        adiDeviceState.setSAEnabled(adiDeviceState2.isSAEnabled());
        return adiDeviceState;
    }
}
