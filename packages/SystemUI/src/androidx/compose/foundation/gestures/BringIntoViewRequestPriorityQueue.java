package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.ContentInViewNode;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.Result;
import kotlin.Unit;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CancellableContinuation;

/* loaded from: classes.dex */
public final class BringIntoViewRequestPriorityQueue {
    public final MutableVector requests = new MutableVector(new ContentInViewNode.Request[16], 0);

    public final void cancelAndRemoveAll(Throwable th) {
        MutableVector mutableVector = this.requests;
        int i = mutableVector.size;
        CancellableContinuation[] cancellableContinuationArr = new CancellableContinuation[i];
        for (int i2 = 0; i2 < i; i2++) {
            cancellableContinuationArr[i2] = ((ContentInViewNode.Request) mutableVector.content[i2]).continuation;
        }
        for (int i3 = 0; i3 < i; i3++) {
            cancellableContinuationArr[i3].cancel(th);
        }
        if (mutableVector.size == 0) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("uncancelled requests present");
    }

    public final void resumeAndRemoveAll() {
        MutableVector mutableVector = this.requests;
        IntRange intRangeUntil = RangesKt___RangesKt.until(0, mutableVector.size);
        int i = intRangeUntil.first;
        int i2 = intRangeUntil.last;
        if (i <= i2) {
            while (true) {
                CancellableContinuation cancellableContinuation = ((ContentInViewNode.Request) mutableVector.content[i]).continuation;
                Unit unit = Unit.INSTANCE;
                int i3 = Result.$r8$clinit;
                cancellableContinuation.resumeWith(unit);
                if (i == i2) {
                    break;
                } else {
                    i++;
                }
            }
        }
        mutableVector.clear();
    }
}
