package com.android.systemui.biometrics.udfps;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class PreprocessedTouch {
    public final List data;
    public final List pointersOnSensor;
    public final int previousPointerOnSensorId;

    public PreprocessedTouch(List<NormalizedTouchData> list, int i, List<Integer> list2) {
        this.data = list;
        this.previousPointerOnSensorId = i;
        this.pointersOnSensor = list2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreprocessedTouch)) {
            return false;
        }
        PreprocessedTouch preprocessedTouch = (PreprocessedTouch) obj;
        return Intrinsics.areEqual(this.data, preprocessedTouch.data) && this.previousPointerOnSensorId == preprocessedTouch.previousPointerOnSensorId && Intrinsics.areEqual(this.pointersOnSensor, preprocessedTouch.pointersOnSensor);
    }

    public final int hashCode() {
        return this.pointersOnSensor.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.previousPointerOnSensorId, this.data.hashCode() * 31, 31);
    }

    public final String toString() {
        return "PreprocessedTouch(data=" + this.data + ", previousPointerOnSensorId=" + this.previousPointerOnSensorId + ", pointersOnSensor=" + this.pointersOnSensor + ")";
    }
}
