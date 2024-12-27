package android.text;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SpanSet<E> {
    private final Class<? extends E> classType;
    int numberOfSpans = 0;
    int[] spanEnds;
    int[] spanFlags;
    int[] spanStarts;
    E[] spans;

    SpanSet(Class<? extends E> type) {
        this.classType = type;
    }

    public void init(Spanned spanned, int i, int i2) {
        Object[] spans = spanned.getSpans(i, i2, this.classType);
        int length = spans.length;
        if (length > 0 && (this.spans == null || this.spans.length < length)) {
            this.spans = (E[]) ((Object[]) Array.newInstance(this.classType, length));
            this.spanStarts = new int[length];
            this.spanEnds = new int[length];
            this.spanFlags = new int[length];
        }
        int i3 = this.numberOfSpans;
        this.numberOfSpans = 0;
        for (Object obj : spans) {
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (spanStart != spanEnd) {
                int spanFlags = spanned.getSpanFlags(obj);
                ((E[]) this.spans)[this.numberOfSpans] = obj;
                this.spanStarts[this.numberOfSpans] = spanStart;
                this.spanEnds[this.numberOfSpans] = spanEnd;
                this.spanFlags[this.numberOfSpans] = spanFlags;
                this.numberOfSpans++;
            }
        }
        if (this.numberOfSpans < i3) {
            Arrays.fill(this.spans, this.numberOfSpans, i3, (Object) null);
        }
    }

    public boolean hasSpansIntersecting(int start, int end) {
        for (int i = 0; i < this.numberOfSpans; i++) {
            if (this.spanStarts[i] < end && this.spanEnds[i] > start) {
                return true;
            }
        }
        return false;
    }

    int getNextTransition(int start, int limit) {
        for (int i = 0; i < this.numberOfSpans; i++) {
            int spanStart = this.spanStarts[i];
            int spanEnd = this.spanEnds[i];
            if (spanStart > start && spanStart < limit) {
                limit = spanStart;
            }
            if (spanEnd > start && spanEnd < limit) {
                limit = spanEnd;
            }
        }
        return limit;
    }

    public void recycle() {
        if (this.spans != null) {
            Arrays.fill(this.spans, 0, this.numberOfSpans, (Object) null);
        }
    }
}
