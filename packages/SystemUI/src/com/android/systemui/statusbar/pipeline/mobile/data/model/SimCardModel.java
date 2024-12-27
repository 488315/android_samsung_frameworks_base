package com.android.systemui.statusbar.pipeline.mobile.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SimCardModel implements Diffable {
    public final String simState;
    public final SimType simType;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        SimCardModel simCardModel = (SimCardModel) obj;
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
