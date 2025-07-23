package androidx.compose.ui.input.pointer;

import androidx.compose.ui.geometry.Offset;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerInputChange {
    public final List _historical;
    public PointerInputChange consumedDelegate;
    public boolean downChange;
    public final long id;
    public final long originalEventPosition;
    public final long position;
    public boolean positionChange;
    public final boolean pressed;
    public final float pressure;
    public final long previousPosition;
    public final boolean previousPressed;
    public final long previousUptimeMillis;
    public final long scrollDelta;
    public final int type;
    public final long uptimeMillis;

    public /* synthetic */ PointerInputChange(long j, long j2, long j3, boolean z, float f, long j4, long j5, boolean z2, boolean z3, int i, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, z, f, j4, j5, z2, z3, i, j6);
    }

    public final void consume() {
        PointerInputChange pointerInputChange = this.consumedDelegate;
        if (pointerInputChange == null) {
            this.downChange = true;
            this.positionChange = true;
        } else if (pointerInputChange != null) {
            pointerInputChange.consume();
        }
    }

    public final boolean isConsumed() {
        PointerInputChange pointerInputChange = this.consumedDelegate;
        return pointerInputChange != null ? pointerInputChange.isConsumed() : this.downChange || this.positionChange;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PointerInputChange(id=");
        sb.append((Object) PointerId.m592toStringimpl(this.id));
        sb.append(", uptimeMillis=");
        sb.append(this.uptimeMillis);
        sb.append(", position=");
        sb.append((Object) Offset.m403toStringimpl(this.position));
        sb.append(", pressed=");
        sb.append(this.pressed);
        sb.append(", pressure=");
        sb.append(this.pressure);
        sb.append(", previousUptimeMillis=");
        sb.append(this.previousUptimeMillis);
        sb.append(", previousPosition=");
        sb.append((Object) Offset.m403toStringimpl(this.previousPosition));
        sb.append(", previousPressed=");
        sb.append(this.previousPressed);
        sb.append(", isConsumed=");
        sb.append(isConsumed());
        sb.append(", type=");
        sb.append((Object) PointerType.m598toStringimpl(this.type));
        sb.append(", historical=");
        Object obj = this._historical;
        if (obj == null) {
            obj = EmptyList.INSTANCE;
        }
        sb.append(obj);
        sb.append(",scrollDelta=");
        sb.append((Object) Offset.m403toStringimpl(this.scrollDelta));
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ PointerInputChange(long j, long j2, long j3, boolean z, float f, long j4, long j5, boolean z2, boolean z3, int i, List list, long j6, long j7, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, z, f, j4, j5, z2, z3, i, (List<HistoricalChange>) list, j6, j7);
    }

    public /* synthetic */ PointerInputChange(long j, long j2, long j3, boolean z, long j4, long j5, boolean z2, ConsumedData consumedData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, z, j4, j5, z2, consumedData, i);
    }

    public /* synthetic */ PointerInputChange(long j, long j2, long j3, boolean z, long j4, long j5, boolean z2, boolean z3, int i, long j6, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, z, j4, j5, z2, z3, i, j6);
    }

    private PointerInputChange(long j, long j2, long j3, boolean z, float f, long j4, long j5, boolean z2, boolean z3, int i, long j6) {
        this.id = j;
        this.uptimeMillis = j2;
        this.position = j3;
        this.pressed = z;
        this.pressure = f;
        this.previousUptimeMillis = j4;
        this.previousPosition = j5;
        this.previousPressed = z2;
        this.type = i;
        this.scrollDelta = j6;
        Offset.Companion.getClass();
        this.originalEventPosition = 0L;
        this.downChange = z3;
        this.positionChange = z3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PointerInputChange(long r22, long r24, long r26, boolean r28, float r29, long r30, long r32, boolean r34, boolean r35, int r36, long r37, int r39, kotlin.jvm.internal.DefaultConstructorMarker r40) {
        /*
            r21 = this;
            r0 = r39
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L10
            androidx.compose.ui.input.pointer.PointerType$Companion r1 = androidx.compose.ui.input.pointer.PointerType.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.input.pointer.PointerType.Touch
            r17 = r1
            goto L12
        L10:
            r17 = r36
        L12:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L20
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            r0 = 0
            r18 = r0
            goto L22
        L20:
            r18 = r37
        L22:
            r20 = 0
            r2 = r21
            r3 = r22
            r5 = r24
            r7 = r26
            r9 = r28
            r10 = r29
            r11 = r30
            r13 = r32
            r15 = r34
            r16 = r35
            r2.<init>(r3, r5, r7, r9, r10, r11, r13, r15, r16, r17, r18, r20)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInputChange.<init>(long, long, long, boolean, float, long, long, boolean, boolean, int, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PointerInputChange(long r21, long r23, long r25, boolean r27, long r28, long r30, boolean r32, boolean r33, int r34, long r35, int r37, kotlin.jvm.internal.DefaultConstructorMarker r38) {
        /*
            r20 = this;
            r0 = r37
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L10
            androidx.compose.ui.input.pointer.PointerType$Companion r1 = androidx.compose.ui.input.pointer.PointerType.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.input.pointer.PointerType.Touch
            r16 = r1
            goto L12
        L10:
            r16 = r34
        L12:
            r0 = r0 & 512(0x200, float:7.17E-43)
            if (r0 == 0) goto L20
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            r0 = 0
            r17 = r0
            goto L22
        L20:
            r17 = r35
        L22:
            r19 = 0
            r2 = r20
            r3 = r21
            r5 = r23
            r7 = r25
            r9 = r27
            r10 = r28
            r12 = r30
            r14 = r32
            r15 = r33
            r2.<init>(r3, r5, r7, r9, r10, r12, r14, r15, r16, r17, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInputChange.<init>(long, long, long, boolean, long, long, boolean, boolean, int, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private PointerInputChange(long j, long j2, long j3, boolean z, long j4, long j5, boolean z2, boolean z3, int i, long j6) {
        this(j, j2, j3, z, 1.0f, j4, j5, z2, z3, i, j6, (DefaultConstructorMarker) null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PointerInputChange(long r18, long r20, long r22, boolean r24, long r25, long r27, boolean r29, androidx.compose.ui.input.pointer.ConsumedData r30, int r31, int r32, kotlin.jvm.internal.DefaultConstructorMarker r33) {
        /*
            r17 = this;
            r0 = r32
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto Lf
            androidx.compose.ui.input.pointer.PointerType$Companion r0 = androidx.compose.ui.input.pointer.PointerType.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.input.pointer.PointerType.Touch
            r15 = r0
            goto L11
        Lf:
            r15 = r31
        L11:
            r16 = 0
            r1 = r17
            r2 = r18
            r4 = r20
            r6 = r22
            r8 = r24
            r9 = r25
            r11 = r27
            r13 = r29
            r14 = r30
            r1.<init>(r2, r4, r6, r8, r9, r11, r13, r14, r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInputChange.<init>(long, long, long, boolean, long, long, boolean, androidx.compose.ui.input.pointer.ConsumedData, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private PointerInputChange(long j, long j2, long j3, boolean z, float f, long j4, long j5, boolean z2, boolean z3, int i, List<HistoricalChange> list, long j6, long j7) {
        this(j, j2, j3, z, f, j4, j5, z2, z3, i, j6, (DefaultConstructorMarker) null);
        this._historical = list;
        this.originalEventPosition = j7;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private PointerInputChange(long r21, long r23, long r25, boolean r27, long r28, long r30, boolean r32, androidx.compose.ui.input.pointer.ConsumedData r33, int r34) {
        /*
            r20 = this;
            r0 = r33
            androidx.compose.ui.input.pointer.PointerInputChange r1 = r0.change
            if (r1 == 0) goto Ld
            androidx.compose.ui.input.pointer.PointerInputChange r2 = r1.consumedDelegate
            if (r2 == 0) goto Ld
            boolean r2 = r2.downChange
            goto L14
        Ld:
            if (r1 == 0) goto L12
            boolean r2 = r1.downChange
            goto L14
        L12:
            boolean r2 = r0.downChange
        L14:
            if (r2 != 0) goto L2c
            if (r1 == 0) goto L1f
            androidx.compose.ui.input.pointer.PointerInputChange r2 = r1.consumedDelegate
            if (r2 == 0) goto L1f
            boolean r0 = r2.positionChange
            goto L26
        L1f:
            if (r1 == 0) goto L24
            boolean r0 = r1.positionChange
            goto L26
        L24:
            boolean r0 = r0.positionChange
        L26:
            if (r0 == 0) goto L29
            goto L2c
        L29:
            r0 = 0
        L2a:
            r15 = r0
            goto L2e
        L2c:
            r0 = 1
            goto L2a
        L2e:
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            r17 = 0
            r19 = 0
            r9 = 1065353216(0x3f800000, float:1.0)
            r1 = r20
            r2 = r21
            r4 = r23
            r6 = r25
            r8 = r27
            r10 = r28
            r12 = r30
            r14 = r32
            r16 = r34
            r1.<init>(r2, r4, r6, r8, r9, r10, r12, r14, r15, r16, r17, r19)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInputChange.<init>(long, long, long, boolean, long, long, boolean, androidx.compose.ui.input.pointer.ConsumedData, int):void");
    }
}
