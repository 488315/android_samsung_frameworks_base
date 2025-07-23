package com.android.systemui.statusbar.pipeline.shared.data.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ImsRegState implements Diffable {
    public boolean ePDGRegState;
    public boolean voLTERegState;
    public boolean voWifiRegState;

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

    public ImsRegState() {
        this(false, false, false, 7, null);
    }

    public static ImsRegState copy$default(ImsRegState imsRegState) {
        boolean z = imsRegState.voWifiRegState;
        boolean z2 = imsRegState.voLTERegState;
        boolean z3 = imsRegState.ePDGRegState;
        imsRegState.getClass();
        return new ImsRegState(z, z2, z3);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImsRegState)) {
            return false;
        }
        ImsRegState imsRegState = (ImsRegState) obj;
        return this.voWifiRegState == imsRegState.voWifiRegState && this.voLTERegState == imsRegState.voLTERegState && this.ePDGRegState == imsRegState.ePDGRegState;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.ePDGRegState) + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.voWifiRegState) * 31, 31, this.voLTERegState);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        ImsRegState imsRegState = (ImsRegState) diffable;
        boolean z = imsRegState.voWifiRegState;
        boolean z2 = this.voWifiRegState;
        if (z != z2) {
            tableRowLoggerImpl.logChange("voWifiRegState", z2);
        }
        boolean z3 = imsRegState.voLTERegState;
        boolean z4 = this.voLTERegState;
        if (z3 != z4) {
            tableRowLoggerImpl.logChange("voLTERegState", z4);
        }
        boolean z5 = imsRegState.ePDGRegState;
        boolean z6 = this.ePDGRegState;
        if (z5 != z6) {
            tableRowLoggerImpl.logChange("ePDGRegState", z6);
        }
    }

    public final String toString() {
        boolean z = this.voWifiRegState;
        boolean z2 = this.voLTERegState;
        return MoveResult$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("ImsRegState(voWifiRegState=", ", voLTERegState=", ", ePDGRegState=", z, z2), this.ePDGRegState, ")");
    }

    public ImsRegState(boolean z, boolean z2, boolean z3) {
        this.voWifiRegState = z;
        this.voLTERegState = z2;
        this.ePDGRegState = z3;
    }

    public /* synthetic */ ImsRegState(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3);
    }
}
