package com.android.systemui.mediaprojection.permission;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenShareOption {
    public final int displayId;
    public final String displayName;
    public final int mode;
    public final String spinnerDisabledText;
    public final int spinnerText;
    public final int startButtonText;
    public final int warningText;

    public ScreenShareOption(int i, int i2, int i3, int i4, int i5, String str, String str2) {
        this.mode = i;
        this.spinnerText = i2;
        this.warningText = i3;
        this.startButtonText = i4;
        this.displayId = i5;
        this.spinnerDisabledText = str;
        this.displayName = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenShareOption)) {
            return false;
        }
        ScreenShareOption screenShareOption = (ScreenShareOption) obj;
        return this.mode == screenShareOption.mode && this.spinnerText == screenShareOption.spinnerText && this.warningText == screenShareOption.warningText && this.startButtonText == screenShareOption.startButtonText && this.displayId == screenShareOption.displayId && Intrinsics.areEqual(this.spinnerDisabledText, screenShareOption.spinnerDisabledText) && Intrinsics.areEqual(this.displayName, screenShareOption.displayName);
    }

    public final int hashCode() {
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ReorderTile$$ExternalSyntheticOutline0.m(this.startButtonText, ReorderTile$$ExternalSyntheticOutline0.m(this.warningText, ReorderTile$$ExternalSyntheticOutline0.m(this.spinnerText, Integer.hashCode(this.mode) * 31, 31), 31), 31), 31);
        String str = this.spinnerDisabledText;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.displayName;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ScreenShareOption(mode=");
        sb.append(this.mode);
        sb.append(", spinnerText=");
        sb.append(this.spinnerText);
        sb.append(", warningText=");
        sb.append(this.warningText);
        sb.append(", startButtonText=");
        sb.append(this.startButtonText);
        sb.append(", displayId=");
        sb.append(this.displayId);
        sb.append(", spinnerDisabledText=");
        sb.append(this.spinnerDisabledText);
        sb.append(", displayName=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.displayName, ")");
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ ScreenShareOption(int r9, int r10, int r11, int r12, int r13, java.lang.String r14, java.lang.String r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
        /*
            r8 = this;
            r0 = r16 & 16
            if (r0 == 0) goto L5
            r13 = 0
        L5:
            r5 = r13
            r13 = r16 & 32
            r0 = 0
            if (r13 == 0) goto Ld
            r6 = r0
            goto Le
        Ld:
            r6 = r14
        Le:
            r13 = r16 & 64
            if (r13 == 0) goto L19
            r7 = r0
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r0 = r8
            goto L1f
        L19:
            r7 = r15
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
        L1f:
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.permission.ScreenShareOption.<init>(int, int, int, int, int, java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
