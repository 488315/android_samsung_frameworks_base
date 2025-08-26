package kotlinx.coroutines;

import kotlinx.coroutines.internal.Segment;

/* loaded from: classes4.dex */
public interface Waiter {
    void invokeOnCancellation(Segment segment, int i);
}
