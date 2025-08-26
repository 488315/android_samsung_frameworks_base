package com.android.systemui.statusbar.pipeline.battery.data.repository;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class BatteryCallbackState {
    public final boolean isBatteryDefenderEnabled;
    public final boolean isPluggedIn;
    public final boolean isPowerSaveEnabled;
    public final boolean isStateUnknown;
    public final Integer level;

    public BatteryCallbackState() {
        this(null, false, false, false, false, 31, null);
    }

    public static BatteryCallbackState copy$default(BatteryCallbackState batteryCallbackState, Integer num, boolean z, boolean z2, boolean z3, int i) {
        if ((i & 1) != 0) {
            num = batteryCallbackState.level;
        }
        Integer num2 = num;
        if ((i & 2) != 0) {
            z = batteryCallbackState.isPluggedIn;
        }
        boolean z4 = z;
        if ((i & 4) != 0) {
            z2 = batteryCallbackState.isPowerSaveEnabled;
        }
        boolean z5 = z2;
        if ((i & 8) != 0) {
            z3 = batteryCallbackState.isBatteryDefenderEnabled;
        }
        boolean z6 = z3;
        boolean z7 = (i & 16) != 0 ? batteryCallbackState.isStateUnknown : false;
        batteryCallbackState.getClass();
        return new BatteryCallbackState(num2, z4, z5, z6, z7);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryCallbackState)) {
            return false;
        }
        BatteryCallbackState batteryCallbackState = (BatteryCallbackState) obj;
        return Intrinsics.areEqual(this.level, batteryCallbackState.level) && this.isPluggedIn == batteryCallbackState.isPluggedIn && this.isPowerSaveEnabled == batteryCallbackState.isPowerSaveEnabled && this.isBatteryDefenderEnabled == batteryCallbackState.isBatteryDefenderEnabled && this.isStateUnknown == batteryCallbackState.isStateUnknown;
    }

    public final int hashCode() {
        Integer num = this.level;
        return Boolean.hashCode(this.isStateUnknown) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((num == null ? 0 : num.hashCode()) * 31, 31, this.isPluggedIn), 31, this.isPowerSaveEnabled), 31, this.isBatteryDefenderEnabled);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BatteryCallbackState(level=");
        sb.append(this.level);
        sb.append(", isPluggedIn=");
        sb.append(this.isPluggedIn);
        sb.append(", isPowerSaveEnabled=");
        sb.append(this.isPowerSaveEnabled);
        sb.append(", isBatteryDefenderEnabled=");
        sb.append(this.isBatteryDefenderEnabled);
        sb.append(", isStateUnknown=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isStateUnknown, ")");
    }

    public BatteryCallbackState(Integer num, boolean z, boolean z2, boolean z3, boolean z4) {
        this.level = num;
        this.isPluggedIn = z;
        this.isPowerSaveEnabled = z2;
        this.isBatteryDefenderEnabled = z3;
        this.isStateUnknown = z4;
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ BatteryCallbackState(java.lang.Integer r2, boolean r3, boolean r4, boolean r5, boolean r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
        /*
            r1 = this;
            r8 = r7 & 1
            if (r8 == 0) goto L5
            r2 = 0
        L5:
            r8 = r7 & 2
            r0 = 0
            if (r8 == 0) goto Lb
            r3 = r0
        Lb:
            r8 = r7 & 4
            if (r8 == 0) goto L10
            r4 = r0
        L10:
            r8 = r7 & 8
            if (r8 == 0) goto L15
            r5 = r0
        L15:
            r7 = r7 & 16
            if (r7 == 0) goto L20
            r8 = r0
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
            goto L26
        L20:
            r8 = r6
            r7 = r5
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
        L26:
            r3.<init>(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.battery.data.repository.BatteryCallbackState.<init>(java.lang.Integer, boolean, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
