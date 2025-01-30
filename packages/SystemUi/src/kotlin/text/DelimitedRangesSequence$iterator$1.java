package kotlin.text;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DelimitedRangesSequence$iterator$1 implements Iterator, KMappedMarker {
    public int counter;
    public int currentStartIndex;
    public IntRange nextItem;
    public int nextSearchIndex;
    public int nextState = -1;
    public final /* synthetic */ DelimitedRangesSequence this$0;

    public DelimitedRangesSequence$iterator$1(DelimitedRangesSequence delimitedRangesSequence) {
        this.this$0 = delimitedRangesSequence;
        int i = delimitedRangesSequence.startIndex;
        int length = delimitedRangesSequence.input.length();
        if (length < 0) {
            throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Cannot coerce value to an empty range: maximum ", length, " is less than minimum 0."));
        }
        if (i < 0) {
            i = 0;
        } else if (i > length) {
            i = length;
        }
        this.currentStartIndex = i;
        this.nextSearchIndex = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
    
        if (r6 < r3) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void calcNext() {
        int i = this.nextSearchIndex;
        if (i < 0) {
            this.nextState = 0;
            this.nextItem = null;
            return;
        }
        DelimitedRangesSequence delimitedRangesSequence = this.this$0;
        int i2 = delimitedRangesSequence.limit;
        if (i2 > 0) {
            int i3 = this.counter + 1;
            this.counter = i3;
        }
        if (i <= delimitedRangesSequence.input.length()) {
            DelimitedRangesSequence delimitedRangesSequence2 = this.this$0;
            Pair pair = (Pair) delimitedRangesSequence2.getNextMatch.invoke(delimitedRangesSequence2.input, Integer.valueOf(this.nextSearchIndex));
            if (pair == null) {
                this.nextItem = new IntRange(this.currentStartIndex, StringsKt__StringsKt.getLastIndex(this.this$0.input));
                this.nextSearchIndex = -1;
            } else {
                int intValue = ((Number) pair.component1()).intValue();
                int intValue2 = ((Number) pair.component2()).intValue();
                this.nextItem = RangesKt___RangesKt.until(this.currentStartIndex, intValue);
                int i4 = intValue + intValue2;
                this.currentStartIndex = i4;
                this.nextSearchIndex = i4 + (intValue2 == 0 ? 1 : 0);
            }
            this.nextState = 1;
        }
        this.nextItem = new IntRange(this.currentStartIndex, StringsKt__StringsKt.getLastIndex(this.this$0.input));
        this.nextSearchIndex = -1;
        this.nextState = 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.nextState == -1) {
            calcNext();
        }
        return this.nextState == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.nextState == -1) {
            calcNext();
        }
        if (this.nextState == 0) {
            throw new NoSuchElementException();
        }
        IntRange intRange = this.nextItem;
        this.nextItem = null;
        this.nextState = -1;
        return intRange;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
