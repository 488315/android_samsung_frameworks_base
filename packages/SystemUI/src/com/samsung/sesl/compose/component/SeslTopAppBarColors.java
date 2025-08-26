package com.samsung.sesl.compose.component;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTopAppBarColors {
    public final long actionIconContentColor;
    public final long containerColor;
    public final long navigationIconContentColor;
    public final long scrolledContainerColor;
    public final long subtitleContentColor;
    public final long titleContentColor;

    public /* synthetic */ SeslTopAppBarColors(long j, long j2, long j3, long j4, long j5, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTopAppBarColors)) {
            return false;
        }
        SeslTopAppBarColors seslTopAppBarColors = (SeslTopAppBarColors) obj;
        long j = seslTopAppBarColors.containerColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.containerColor, j) && ULong.m3447equalsimpl0(this.scrolledContainerColor, seslTopAppBarColors.scrolledContainerColor) && ULong.m3447equalsimpl0(this.navigationIconContentColor, seslTopAppBarColors.navigationIconContentColor) && ULong.m3447equalsimpl0(this.titleContentColor, seslTopAppBarColors.titleContentColor) && ULong.m3447equalsimpl0(this.actionIconContentColor, seslTopAppBarColors.actionIconContentColor) && ULong.m3447equalsimpl0(this.subtitleContentColor, seslTopAppBarColors.subtitleContentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.subtitleContentColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.containerColor) * 31, 31, this.scrolledContainerColor), 31, this.navigationIconContentColor), 31, this.titleContentColor), 31, this.actionIconContentColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.containerColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.scrolledContainerColor);
        String strM464toStringimpl3 = Color.m464toStringimpl(this.navigationIconContentColor);
        String strM464toStringimpl4 = Color.m464toStringimpl(this.titleContentColor);
        String strM464toStringimpl5 = Color.m464toStringimpl(this.actionIconContentColor);
        String strM464toStringimpl6 = Color.m464toStringimpl(this.subtitleContentColor);
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslTopAppBarColors(containerColor=", strM464toStringimpl, ", scrolledContainerColor=", strM464toStringimpl2, ", navigationIconContentColor=");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl3, ", titleContentColor=", strM464toStringimpl4, ", actionIconContentColor=");
        return NotificationController$$ExternalSyntheticOutline0.m(sbM, strM464toStringimpl5, ", subtitleContentColor=", strM464toStringimpl6, ")");
    }

    private SeslTopAppBarColors(long j, long j2, long j3, long j4, long j5, long j6) {
        this.containerColor = j;
        this.scrolledContainerColor = j2;
        this.navigationIconContentColor = j3;
        this.titleContentColor = j4;
        this.actionIconContentColor = j5;
        this.subtitleContentColor = j6;
    }
}
