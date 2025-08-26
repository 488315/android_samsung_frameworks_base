package com.android.systemui.unfold;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes3.dex */
public final class DisplaySwitchLatencyLogger {
    public static void log(DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent) {
        int i = displaySwitchLatencyEvent.latencyMs;
        int[] intArray = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.fromVisibleAppsUid);
        int[] intArray2 = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.toVisibleAppsUid);
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(753);
        builderNewBuilder.writeInt(i);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.fromFoldableDeviceState);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.fromState);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.fromFocusedAppUid);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.fromPipAppUid);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeIntArray(intArray);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.fromDensityDpi);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.toState);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.toFoldableDeviceState);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.toFocusedAppUid);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.toPipAppUid);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeIntArray(intArray2);
        builderNewBuilder.addBooleanAnnotation((byte) 1, true);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.toDensityDpi);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.notificationCount);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.externalDisplayCount);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.throttlingLevel);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.vskinTemperatureC);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.trackingResult);
        builderNewBuilder.writeInt(displaySwitchLatencyEvent.screenWakelockStatus);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
    }
}
