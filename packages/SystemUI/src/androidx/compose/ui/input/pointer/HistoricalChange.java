package androidx.compose.ui.input.pointer;

import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HistoricalChange {
    public final long originalEventPosition;
    public final long position;
    public final long uptimeMillis;

    public /* synthetic */ HistoricalChange(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
    }

    public final String toString() {
        return "HistoricalChange(uptimeMillis=" + this.uptimeMillis + ", position=" + ((Object) Offset.m403toStringimpl(this.position)) + ')';
    }

    public /* synthetic */ HistoricalChange(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    private HistoricalChange(long j, long j2) {
        this.uptimeMillis = j;
        this.position = j2;
        Offset.Companion.getClass();
        this.originalEventPosition = 0L;
    }

    private HistoricalChange(long j, long j2, long j3) {
        this(j, j2, (DefaultConstructorMarker) null);
        this.originalEventPosition = j3;
    }
}
