package com.android.systemui.keyguard.shared.model;

import android.content.Intent;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordancePickerRepresentation {
    public final Intent actionIntent;
    public final String actionText;
    public final Intent configureIntent;
    public final String explanation;
    public final int iconResourceId;
    public final String id;
    public final boolean isEnabled;
    public final String name;

    public KeyguardQuickAffordancePickerRepresentation(String str, String str2, int i, boolean z, String str3, String str4, Intent intent, Intent intent2) {
        this.id = str;
        this.name = str2;
        this.iconResourceId = i;
        this.isEnabled = z;
        this.explanation = str3;
        this.actionText = str4;
        this.actionIntent = intent;
        this.configureIntent = intent2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardQuickAffordancePickerRepresentation)) {
            return false;
        }
        KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation = (KeyguardQuickAffordancePickerRepresentation) obj;
        return Intrinsics.areEqual(this.id, keyguardQuickAffordancePickerRepresentation.id) && Intrinsics.areEqual(this.name, keyguardQuickAffordancePickerRepresentation.name) && this.iconResourceId == keyguardQuickAffordancePickerRepresentation.iconResourceId && this.isEnabled == keyguardQuickAffordancePickerRepresentation.isEnabled && Intrinsics.areEqual(this.explanation, keyguardQuickAffordancePickerRepresentation.explanation) && Intrinsics.areEqual(this.actionText, keyguardQuickAffordancePickerRepresentation.actionText) && Intrinsics.areEqual(this.actionIntent, keyguardQuickAffordancePickerRepresentation.actionIntent) && Intrinsics.areEqual(this.configureIntent, keyguardQuickAffordancePickerRepresentation.configureIntent);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.iconResourceId, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31), 31, this.isEnabled);
        String str = this.explanation;
        int hashCode = (m + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.actionText;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Intent intent = this.actionIntent;
        int hashCode3 = (hashCode2 + (intent == null ? 0 : intent.hashCode())) * 31;
        Intent intent2 = this.configureIntent;
        return hashCode3 + (intent2 != null ? intent2.hashCode() : 0);
    }

    public final String toString() {
        return "KeyguardQuickAffordancePickerRepresentation(id=" + this.id + ", name=" + this.name + ", iconResourceId=" + this.iconResourceId + ", isEnabled=" + this.isEnabled + ", explanation=" + this.explanation + ", actionText=" + this.actionText + ", actionIntent=" + this.actionIntent + ", configureIntent=" + this.configureIntent + ")";
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ KeyguardQuickAffordancePickerRepresentation(java.lang.String r10, java.lang.String r11, int r12, boolean r13, java.lang.String r14, java.lang.String r15, android.content.Intent r16, android.content.Intent r17, int r18, kotlin.jvm.internal.DefaultConstructorMarker r19) {
        /*
            r9 = this;
            r0 = r18
            r1 = r0 & 8
            if (r1 == 0) goto L7
            r13 = 1
        L7:
            r4 = r13
            r13 = r0 & 16
            r1 = 0
            if (r13 == 0) goto Lf
            r5 = r1
            goto L10
        Lf:
            r5 = r14
        L10:
            r13 = r0 & 32
            if (r13 == 0) goto L16
            r6 = r1
            goto L17
        L16:
            r6 = r15
        L17:
            r13 = r0 & 64
            if (r13 == 0) goto L1d
            r7 = r1
            goto L1f
        L1d:
            r7 = r16
        L1f:
            r13 = r0 & 128(0x80, float:1.8E-43)
            if (r13 == 0) goto L29
            r8 = r1
            r0 = r9
            r2 = r11
            r3 = r12
            r1 = r10
            goto L2f
        L29:
            r8 = r17
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
        L2f:
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation.<init>(java.lang.String, java.lang.String, int, boolean, java.lang.String, java.lang.String, android.content.Intent, android.content.Intent, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
