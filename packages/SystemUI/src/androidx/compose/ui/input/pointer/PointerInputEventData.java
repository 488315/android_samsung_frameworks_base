package androidx.compose.ui.input.pointer;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        if (!PointerId.m593equalsimpl0(this.id, pointerInputEventData.id) || this.uptime != pointerInputEventData.uptime || !Offset.m398equalsimpl0(this.positionOnScreen, pointerInputEventData.positionOnScreen) || !Offset.m398equalsimpl0(this.position, pointerInputEventData.position) || this.down != pointerInputEventData.down || Float.compare(this.pressure, pointerInputEventData.pressure) != 0) {
            return false;
        }
        PointerType.Companion companion = PointerType.Companion;
        return this.type == pointerInputEventData.type && this.activeHover == pointerInputEventData.activeHover && Intrinsics.areEqual(this.historical, pointerInputEventData.historical) && Offset.m398equalsimpl0(this.scrollDelta, pointerInputEventData.scrollDelta) && Offset.m398equalsimpl0(this.originalEventPosition, pointerInputEventData.originalEventPosition);
    }

    public final int hashCode() {
        int iM = MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.id) * 31, 31, this.uptime);
        Offset.Companion companion = Offset.Companion;
        int iM2 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.pressure, TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(iM, 31, this.positionOnScreen), 31, this.position), 31, this.down), 31);
        PointerType.Companion companion2 = PointerType.Companion;
        return Long.hashCode(this.originalEventPosition) + MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.historical, TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.type, iM2, 31), 31, this.activeHover), 31), 31, this.scrollDelta);
    }

    public final String toString() {
        return "PointerInputEventData(id=" + ((Object) PointerId.m594toStringimpl(this.id)) + ", uptime=" + this.uptime + ", positionOnScreen=" + ((Object) Offset.m405toStringimpl(this.positionOnScreen)) + ", position=" + ((Object) Offset.m405toStringimpl(this.position)) + ", down=" + this.down + ", pressure=" + this.pressure + ", type=" + ((Object) PointerType.m600toStringimpl(this.type)) + ", activeHover=" + this.activeHover + ", historical=" + this.historical + ", scrollDelta=" + ((Object) Offset.m405toStringimpl(this.scrollDelta)) + ", originalEventPosition=" + ((Object) Offset.m405toStringimpl(this.originalEventPosition)) + ')';
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
    public PointerInputEventData(long j, long j2, long j3, long j4, boolean z, float f, int i, boolean z2, List list, long j5, long j6, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        long j7;
        long j8;
        boolean z3 = (i2 & 128) != 0 ? false : z2;
        List arrayList = (i2 & 256) != 0 ? new ArrayList() : list;
        if ((i2 & 512) != 0) {
            Offset.Companion.getClass();
            j7 = 0;
        } else {
            j7 = j5;
        }
        if ((i2 & 1024) != 0) {
            Offset.Companion.getClass();
            j8 = 0;
        } else {
            j8 = j6;
        }
        this(j, j2, j3, j4, z, f, i, z3, arrayList, j7, j8, null);
    }
}
