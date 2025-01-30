package android.text;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes3.dex */
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
    E[] eArr;
    Object[] spans = spanned.getSpans(i, i2, this.classType);
    int length = spans.length;
    if (length > 0 && ((eArr = this.spans) == null || eArr.length < length)) {
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
        Object[] objArr = (E[]) this.spans;
        int i4 = this.numberOfSpans;
        objArr[i4] = obj;
        this.spanStarts[i4] = spanStart;
        this.spanEnds[i4] = spanEnd;
        this.spanFlags[i4] = spanFlags;
        this.numberOfSpans = i4 + 1;
      }
    }
    int i5 = this.numberOfSpans;
    if (i5 < i3) {
      Arrays.fill(this.spans, i5, i3, (Object) null);
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
    E[] eArr = this.spans;
    if (eArr != null) {
      Arrays.fill(eArr, 0, this.numberOfSpans, (Object) null);
    }
  }
}
