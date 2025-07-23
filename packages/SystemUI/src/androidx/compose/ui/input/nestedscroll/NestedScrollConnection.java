package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.Velocity;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface NestedScrollConnection {
    /* renamed from: onPostFling-RZ2iAVY */
    default Object mo77onPostFlingRZ2iAVY(long j, long j2, Continuation continuation) {
        Velocity.Companion.getClass();
        return Velocity.m876boximpl(0L);
    }

    /* renamed from: onPostScroll-DzOQY0M */
    default long mo78onPostScrollDzOQY0M(int i, long j, long j2) {
        Offset.Companion.getClass();
        return 0L;
    }

    /* renamed from: onPreFling-QWom1Mo */
    default Object mo288onPreFlingQWom1Mo(long j, Continuation continuation) {
        Velocity.Companion.getClass();
        return Velocity.m876boximpl(0L);
    }

    /* renamed from: onPreScroll-OzD1aCk */
    default long mo175onPreScrollOzD1aCk(int i, long j) {
        Offset.Companion.getClass();
        return 0L;
    }
}
