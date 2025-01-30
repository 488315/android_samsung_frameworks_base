package com.android.systemui.statusbar.pipeline.shared.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImsRegState implements Diffable {
    public boolean ePDGRegState;
    public boolean voLTERegState;
    public boolean voWifiRegState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public ImsRegState() {
        this(false, false, false, 7, null);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    public final int hashCode() {
        boolean z = this.voWifiRegState;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.voLTERegState;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        boolean z2 = this.ePDGRegState;
        return i3 + (z2 ? 1 : z2 ? 1 : 0);
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("ImsRegState(voWifiRegState=", z, ", voLTERegState=", z2, ", ePDGRegState="), this.ePDGRegState, ")");
    }

    public ImsRegState(boolean z, boolean z2, boolean z3) {
        this.voWifiRegState = z;
        this.voLTERegState = z2;
        this.ePDGRegState = z3;
    }

    public /* synthetic */ ImsRegState(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
