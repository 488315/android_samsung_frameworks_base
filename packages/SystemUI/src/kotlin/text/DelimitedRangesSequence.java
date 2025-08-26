package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.Sequence;

/* loaded from: classes4.dex */
public final class DelimitedRangesSequence implements Sequence {
    public final Function2 getNextMatch;
    public final CharSequence input;
    public final int limit;
    public final int startIndex;

    /* renamed from: kotlin.text.DelimitedRangesSequence$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int counter;
        public int currentStartIndex;
        public IntRange nextItem;
        public int nextSearchIndex;
        public int nextState = -1;

        public AnonymousClass1() {
            int iCoerceIn = RangesKt___RangesKt.coerceIn(DelimitedRangesSequence.this.startIndex, 0, DelimitedRangesSequence.this.input.length());
            this.currentStartIndex = iCoerceIn;
            this.nextSearchIndex = iCoerceIn;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x001a  */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void calcNext$3() {
            int i = this.nextSearchIndex;
            if (i < 0) {
                this.nextState = 0;
                this.nextItem = null;
                return;
            }
            DelimitedRangesSequence delimitedRangesSequence = DelimitedRangesSequence.this;
            int i2 = delimitedRangesSequence.limit;
            if (i2 > 0) {
                int i3 = this.counter + 1;
                this.counter = i3;
                if (i3 < i2) {
                    if (i > delimitedRangesSequence.input.length()) {
                        this.nextItem = new IntRange(this.currentStartIndex, StringsKt__StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                        this.nextSearchIndex = -1;
                    } else {
                        DelimitedRangesSequence delimitedRangesSequence2 = DelimitedRangesSequence.this;
                        Pair pair = (Pair) delimitedRangesSequence2.getNextMatch.invoke(delimitedRangesSequence2.input, Integer.valueOf(this.nextSearchIndex));
                        if (pair == null) {
                            this.nextItem = new IntRange(this.currentStartIndex, StringsKt__StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                            this.nextSearchIndex = -1;
                        } else {
                            int iIntValue = ((Number) pair.component1()).intValue();
                            int iIntValue2 = ((Number) pair.component2()).intValue();
                            this.nextItem = RangesKt___RangesKt.until(this.currentStartIndex, iIntValue);
                            int i4 = iIntValue + iIntValue2;
                            this.currentStartIndex = i4;
                            this.nextSearchIndex = i4 + (iIntValue2 == 0 ? 1 : 0);
                        }
                    }
                }
            }
            this.nextState = 1;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            if (this.nextState == -1) {
                calcNext$3();
            }
            return this.nextState == 1;
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.nextState == -1) {
                calcNext$3();
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

    public DelimitedRangesSequence(CharSequence charSequence, int i, int i2, Function2 function2) {
        this.input = charSequence;
        this.startIndex = i;
        this.limit = i2;
        this.getNextMatch = function2;
    }

    @Override // kotlin.sequences.Sequence
    public final Iterator iterator() {
        return new AnonymousClass1();
    }
}
