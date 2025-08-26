package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TopAppBarColors {
    public final long actionIconContentColor;
    public final long containerColor;
    public final long navigationIconContentColor;
    public final long scrolledContainerColor;
    public final long subtitleContentColor;
    public final long titleContentColor;

    public /* synthetic */ TopAppBarColors(long j, long j2, long j3, long j4, long j5, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6);
    }

    /* renamed from: copy-tNS2XkQ, reason: not valid java name */
    public final TopAppBarColors m319copytNS2XkQ(long j, long j2, long j3, long j4, long j5, long j6) {
        return new TopAppBarColors(j != 16 ? j : this.containerColor, j2 != 16 ? j2 : this.scrolledContainerColor, j3 != 16 ? j3 : this.navigationIconContentColor, j4 != 16 ? j4 : this.titleContentColor, j5 != 16 ? j5 : this.actionIconContentColor, j6 != 16 ? j6 : this.subtitleContentColor, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TopAppBarColors)) {
            return false;
        }
        TopAppBarColors topAppBarColors = (TopAppBarColors) obj;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.containerColor, topAppBarColors.containerColor) && ULong.m3447equalsimpl0(this.scrolledContainerColor, topAppBarColors.scrolledContainerColor) && ULong.m3447equalsimpl0(this.navigationIconContentColor, topAppBarColors.navigationIconContentColor) && ULong.m3447equalsimpl0(this.titleContentColor, topAppBarColors.titleContentColor) && ULong.m3447equalsimpl0(this.actionIconContentColor, topAppBarColors.actionIconContentColor) && ULong.m3447equalsimpl0(this.subtitleContentColor, topAppBarColors.subtitleContentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.subtitleContentColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.containerColor) * 31, 31, this.scrolledContainerColor), 31, this.navigationIconContentColor), 31, this.titleContentColor), 31, this.actionIconContentColor);
    }

    public /* synthetic */ TopAppBarColors(long j, long j2, long j3, long j4, long j5, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5);
    }

    private TopAppBarColors(long j, long j2, long j3, long j4, long j5, long j6) {
        this.containerColor = j;
        this.scrolledContainerColor = j2;
        this.navigationIconContentColor = j3;
        this.titleContentColor = j4;
        this.actionIconContentColor = j5;
        this.subtitleContentColor = j6;
    }

    private TopAppBarColors(long j, long j2, long j3, long j4, long j5) {
        this(j, j2, j3, j4, j5, j4, null);
    }
}
