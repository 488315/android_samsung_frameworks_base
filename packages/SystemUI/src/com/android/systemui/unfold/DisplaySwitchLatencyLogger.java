package com.android.systemui.unfold;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplaySwitchLatencyLogger {
    public static void log(DisplaySwitchLatencyTracker.DisplaySwitchLatencyEvent displaySwitchLatencyEvent) {
        int i = displaySwitchLatencyEvent.latencyMs;
        int[] intArray = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.fromVisibleAppsUid);
        int[] intArray2 = CollectionsKt___CollectionsKt.toIntArray(displaySwitchLatencyEvent.toVisibleAppsUid);
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(753);
        newBuilder.writeInt(i);
        newBuilder.writeInt(displaySwitchLatencyEvent.fromFoldableDeviceState);
        newBuilder.writeInt(displaySwitchLatencyEvent.fromState);
        newBuilder.writeInt(displaySwitchLatencyEvent.fromFocusedAppUid);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeInt(displaySwitchLatencyEvent.fromPipAppUid);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeIntArray(intArray);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeInt(displaySwitchLatencyEvent.fromDensityDpi);
        newBuilder.writeInt(displaySwitchLatencyEvent.toState);
        newBuilder.writeInt(displaySwitchLatencyEvent.toFoldableDeviceState);
        newBuilder.writeInt(displaySwitchLatencyEvent.toFocusedAppUid);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeInt(displaySwitchLatencyEvent.toPipAppUid);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeIntArray(intArray2);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeInt(displaySwitchLatencyEvent.toDensityDpi);
        newBuilder.writeInt(displaySwitchLatencyEvent.notificationCount);
        newBuilder.writeInt(displaySwitchLatencyEvent.externalDisplayCount);
        newBuilder.writeInt(displaySwitchLatencyEvent.throttlingLevel);
        newBuilder.writeInt(displaySwitchLatencyEvent.vskinTemperatureC);
        newBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs);
        newBuilder.writeInt(displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs);
        newBuilder.writeInt(displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs);
        newBuilder.writeInt(displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs);
        newBuilder.writeInt(displaySwitchLatencyEvent.trackingResult);
        newBuilder.writeInt(displaySwitchLatencyEvent.screenWakelockStatus);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
