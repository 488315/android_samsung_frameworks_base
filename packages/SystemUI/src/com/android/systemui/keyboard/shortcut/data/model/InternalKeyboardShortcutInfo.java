package com.android.systemui.keyboard.shortcut.data.model;

import android.graphics.drawable.Icon;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class InternalKeyboardShortcutInfo {
    public final char baseCharacter;
    public final Icon icon;
    public final boolean isCustomShortcut;
    public final int keycode;
    public final String label;
    public final int modifiers;

    public InternalKeyboardShortcutInfo(String str, int i, int i2, char c, Icon icon, boolean z) {
        this.label = str;
        this.keycode = i;
        this.modifiers = i2;
        this.baseCharacter = c;
        this.icon = icon;
        this.isCustomShortcut = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InternalKeyboardShortcutInfo)) {
            return false;
        }
        InternalKeyboardShortcutInfo internalKeyboardShortcutInfo = (InternalKeyboardShortcutInfo) obj;
        return Intrinsics.areEqual(this.label, internalKeyboardShortcutInfo.label) && this.keycode == internalKeyboardShortcutInfo.keycode && this.modifiers == internalKeyboardShortcutInfo.modifiers && this.baseCharacter == internalKeyboardShortcutInfo.baseCharacter && Intrinsics.areEqual(this.icon, internalKeyboardShortcutInfo.icon) && this.isCustomShortcut == internalKeyboardShortcutInfo.isCustomShortcut;
    }

    public final int hashCode() {
        int iHashCode = (Character.hashCode(this.baseCharacter) + ReorderTile$$ExternalSyntheticOutline0.m(this.modifiers, ReorderTile$$ExternalSyntheticOutline0.m(this.keycode, this.label.hashCode() * 31, 31), 31)) * 31;
        Icon icon = this.icon;
        return Boolean.hashCode(this.isCustomShortcut) + ((iHashCode + (icon == null ? 0 : icon.hashCode())) * 31);
    }

    public final String toString() {
        Icon icon = this.icon;
        StringBuilder sb = new StringBuilder("InternalKeyboardShortcutInfo(label=");
        sb.append(this.label);
        sb.append(", keycode=");
        sb.append(this.keycode);
        sb.append(", modifiers=");
        sb.append(this.modifiers);
        sb.append(", baseCharacter=");
        sb.append(this.baseCharacter);
        sb.append(", icon=");
        sb.append(icon);
        sb.append(", isCustomShortcut=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCustomShortcut, ")");
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ InternalKeyboardShortcutInfo(java.lang.String r2, int r3, int r4, char r5, android.graphics.drawable.Icon r6, boolean r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
        /*
            r1 = this;
            r9 = r8 & 1
            if (r9 == 0) goto L6
            java.lang.String r2 = ""
        L6:
            r9 = r8 & 8
            r0 = 0
            if (r9 == 0) goto Lc
            r5 = r0
        Lc:
            r9 = r8 & 16
            if (r9 == 0) goto L11
            r6 = 0
        L11:
            r8 = r8 & 32
            if (r8 == 0) goto L1d
            r9 = r0
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            goto L24
        L1d:
            r9 = r7
            r8 = r6
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
        L24:
            r3.<init>(r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo.<init>(java.lang.String, int, int, char, android.graphics.drawable.Icon, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
