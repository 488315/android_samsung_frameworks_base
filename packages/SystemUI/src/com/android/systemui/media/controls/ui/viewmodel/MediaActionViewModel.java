package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaActionViewModel {
    public final Drawable background;
    public final Integer buttonId;
    public final CharSequence contentDescription;
    public final Drawable icon;
    public final boolean isEnabled;
    public final boolean isVisibleWhenScrubbing;
    public final int notVisibleValue;
    public final Function1 onClicked;
    public final Integer rebindId;
    public final boolean showInCollapsed;

    public MediaActionViewModel(Drawable drawable, CharSequence charSequence, Drawable drawable2, boolean z, int i, boolean z2, Integer num, Integer num2, boolean z3, Function1 function1) {
        this.icon = drawable;
        this.contentDescription = charSequence;
        this.background = drawable2;
        this.isVisibleWhenScrubbing = z;
        this.notVisibleValue = i;
        this.showInCollapsed = z2;
        this.rebindId = num;
        this.buttonId = num2;
        this.isEnabled = z3;
        this.onClicked = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaActionViewModel)) {
            return false;
        }
        MediaActionViewModel mediaActionViewModel = (MediaActionViewModel) obj;
        return Intrinsics.areEqual(this.icon, mediaActionViewModel.icon) && Intrinsics.areEqual(this.contentDescription, mediaActionViewModel.contentDescription) && Intrinsics.areEqual(this.background, mediaActionViewModel.background) && this.isVisibleWhenScrubbing == mediaActionViewModel.isVisibleWhenScrubbing && this.notVisibleValue == mediaActionViewModel.notVisibleValue && this.showInCollapsed == mediaActionViewModel.showInCollapsed && Intrinsics.areEqual(this.rebindId, mediaActionViewModel.rebindId) && Intrinsics.areEqual(this.buttonId, mediaActionViewModel.buttonId) && this.isEnabled == mediaActionViewModel.isEnabled && Intrinsics.areEqual(this.onClicked, mediaActionViewModel.onClicked);
    }

    public final int hashCode() {
        Drawable drawable = this.icon;
        int iHashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        CharSequence charSequence = this.contentDescription;
        int iHashCode2 = (iHashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Drawable drawable2 = this.background;
        int iM = TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.notVisibleValue, TransitionData$$ExternalSyntheticOutline0.m((iHashCode2 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31, 31, this.isVisibleWhenScrubbing), 31), 31, this.showInCollapsed);
        Integer num = this.rebindId;
        int iHashCode3 = (iM + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.buttonId;
        return this.onClicked.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((iHashCode3 + (num2 != null ? num2.hashCode() : 0)) * 31, 31, this.isEnabled);
    }

    public final String toString() {
        Drawable drawable = this.icon;
        CharSequence charSequence = this.contentDescription;
        return "MediaActionViewModel(icon=" + drawable + ", contentDescription=" + ((Object) charSequence) + ", background=" + this.background + ", isVisibleWhenScrubbing=" + this.isVisibleWhenScrubbing + ", notVisibleValue=" + this.notVisibleValue + ", showInCollapsed=" + this.showInCollapsed + ", rebindId=" + this.rebindId + ", buttonId=" + this.buttonId + ", isEnabled=" + this.isEnabled + ", onClicked=" + this.onClicked + ")";
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ MediaActionViewModel(android.graphics.drawable.Drawable r14, java.lang.CharSequence r15, android.graphics.drawable.Drawable r16, boolean r17, int r18, boolean r19, java.lang.Integer r20, java.lang.Integer r21, boolean r22, kotlin.jvm.functions.Function1 r23, int r24, kotlin.jvm.internal.DefaultConstructorMarker r25) {
        /*
            r13 = this;
            r0 = r24
            r1 = r0 & 8
            if (r1 == 0) goto L9
            r1 = 1
            r6 = r1
            goto Lb
        L9:
            r6 = r17
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L13
            r1 = 8
            r7 = r1
            goto L15
        L13:
            r7 = r18
        L15:
            r1 = r0 & 64
            r2 = 0
            if (r1 == 0) goto L1c
            r9 = r2
            goto L1e
        L1c:
            r9 = r20
        L1e:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L2f
            r10 = r2
            r3 = r14
            r4 = r15
            r5 = r16
            r8 = r19
            r11 = r22
            r12 = r23
            r2 = r13
            goto L3c
        L2f:
            r10 = r21
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r8 = r19
            r11 = r22
            r12 = r23
        L3c:
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaActionViewModel.<init>(android.graphics.drawable.Drawable, java.lang.CharSequence, android.graphics.drawable.Drawable, boolean, int, boolean, java.lang.Integer, java.lang.Integer, boolean, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
