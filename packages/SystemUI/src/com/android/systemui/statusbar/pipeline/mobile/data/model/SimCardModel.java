package com.android.systemui.statusbar.pipeline.mobile.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SimCardModel implements Diffable {
    public final String simState;
    public final SimType simType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SimCardModel(SimType simType, String str) {
        this.simType = simType;
        this.simState = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimCardModel)) {
            return false;
        }
        SimCardModel simCardModel = (SimCardModel) obj;
        return this.simType == simCardModel.simType && Intrinsics.areEqual(this.simState, simCardModel.simState);
    }

    public final int hashCode() {
        return this.simState.hashCode() + (this.simType.hashCode() * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        SimCardModel simCardModel = (SimCardModel) diffable;
        SimType simType = simCardModel.simType;
        SimType simType2 = this.simType;
        if (simType != simType2) {
            tableRowLoggerImpl.logChange("simType", simType2.toString());
        }
        String str = simCardModel.simState;
        String str2 = this.simState;
        if (Intrinsics.areEqual(str, str2)) {
            return;
        }
        tableRowLoggerImpl.logChange("simState", str2);
    }

    public final String toString() {
        return "SimCardModel(simType=" + this.simType + ", simState=" + this.simState + ")";
    }
}
