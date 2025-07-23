package com.android.systemui.biometrics.udfps;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
        return this.pointersOnSensor.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.previousPointerOnSensorId, this.data.hashCode() * 31, 31);
    }

    public final String toString() {
        return "PreprocessedTouch(data=" + this.data + ", previousPointerOnSensorId=" + this.previousPointerOnSensorId + ", pointersOnSensor=" + this.pointersOnSensor + ")";
    }
}
