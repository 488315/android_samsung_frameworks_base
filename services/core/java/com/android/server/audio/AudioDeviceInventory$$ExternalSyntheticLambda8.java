package com.android.server.audio;

import java.util.function.BiFunction;

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
