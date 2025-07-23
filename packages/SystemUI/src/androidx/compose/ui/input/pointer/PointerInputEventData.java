package androidx.compose.ui.input.pointer;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerInputEventData {
    public final boolean activeHover;
    public final boolean down;
    public final List historical;
    public final long id;
    public final long originalEventPosition;
    public final long position;
    public final long positionOnScreen;
    public final float pressure;
    public final long scrollDelta;
    public final int type;
    public final long uptime;

    public /* synthetic */ PointerInputEventData(long j, long j2, long j3, long j4, boolean z, float f, int i, boolean z2, List list, long j5, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, z, f, i, z2, list, j5, j6);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PointerInputEventData)) {
            return false;
        }
        PointerInputEventData pointerInputEventData = (PointerInputEventData) obj;
        if (!PointerId.m591equalsimpl0(this.id, pointerInputEventData.id) || this.uptime != pointerInputEventData.uptime || !Offset.m396equalsimpl0(this.positionOnScreen, pointerInputEventData.positionOnScreen) || !Offset.m396equalsimpl0(this.position, pointerInputEventData.position) || this.down != pointerInputEventData.down || Float.compare(this.pressure, pointerInputEventData.pressure) != 0) {
            return false;
        }
        PointerType.Companion companion = PointerType.Companion;
        return this.type == pointerInputEventData.type && this.activeHover == pointerInputEventData.activeHover && Intrinsics.areEqual(this.historical, pointerInputEventData.historical) && Offset.m396equalsimpl0(this.scrollDelta, pointerInputEventData.scrollDelta) && Offset.m396equalsimpl0(this.originalEventPosition, pointerInputEventData.originalEventPosition);
    }

    public final int hashCode() {
        int m = MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.id) * 31, 31, this.uptime);
        Offset.Companion companion = Offset.Companion;
        int m2 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressure, TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.positionOnScreen), 31, this.position), 31, this.down), 31);
        PointerType.Companion companion2 = PointerType.Companion;
        return Long.hashCode(this.originalEventPosition) + MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.historical, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.type, m2, 31), 31, this.activeHover), 31), 31, this.scrollDelta);
    }

    public final String toString() {
        return "PointerInputEventData(id=" + ((Object) PointerId.m592toStringimpl(this.id)) + ", uptime=" + this.uptime + ", positionOnScreen=" + ((Object) Offset.m403toStringimpl(this.positionOnScreen)) + ", position=" + ((Object) Offset.m403toStringimpl(this.position)) + ", down=" + this.down + ", pressure=" + this.pressure + ", type=" + ((Object) PointerType.m598toStringimpl(this.type)) + ", activeHover=" + this.activeHover + ", historical=" + this.historical + ", scrollDelta=" + ((Object) Offset.m403toStringimpl(this.scrollDelta)) + ", originalEventPosition=" + ((Object) Offset.m403toStringimpl(this.originalEventPosition)) + ')';
    }

    private PointerInputEventData(long j, long j2, long j3, long j4, boolean z, float f, int i, boolean z2, List<HistoricalChange> list, long j5, long j6) {
        this.id = j;
        this.uptime = j2;
        this.positionOnScreen = j3;
        this.position = j4;
        this.down = z;
        this.pressure = f;
        this.type = i;
        this.activeHover = z2;
        this.historical = list;
        this.scrollDelta = j5;
        this.originalEventPosition = j6;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PointerInputEventData(long r22, long r24, long r26, long r28, boolean r30, float r31, int r32, boolean r33, java.util.List r34, long r35, long r37, int r39, kotlin.jvm.internal.DefaultConstructorMarker r40) {
        /*
            r21 = this;
            r0 = r39
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L9
            r1 = 0
            r14 = r1
            goto Lb
        L9:
            r14 = r33
        Lb:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L16
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r15 = r1
            goto L18
        L16:
            r15 = r34
        L18:
            r1 = r0 & 512(0x200, float:7.17E-43)
            r2 = 0
            if (r1 == 0) goto L26
            androidx.compose.ui.geometry.Offset$Companion r1 = androidx.compose.ui.geometry.Offset.Companion
            r1.getClass()
            r16 = r2
            goto L28
        L26:
            r16 = r35
        L28:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L34
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            r18 = r2
            goto L36
        L34:
            r18 = r37
        L36:
            r20 = 0
            r2 = r21
            r3 = r22
            r5 = r24
            r7 = r26
            r9 = r28
            r11 = r30
            r12 = r31
            r13 = r32
            r2.<init>(r3, r5, r7, r9, r11, r12, r13, r14, r15, r16, r18, r20)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInputEventData.<init>(long, long, long, long, boolean, float, int, boolean, java.util.List, long, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
