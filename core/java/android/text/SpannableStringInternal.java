package android.text;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

import libcore.util.EmptyArray;

import java.lang.reflect.Array;

public abstract class SpannableStringInternal {
    private static final int COLUMNS = 3;
    static final Object[] EMPTY = new Object[0];
    private static final int END = 1;
    private static final int FLAGS = 2;
    private static final int START = 0;
    private int mSpanCount;
    private int[] mSpanData;
    private Object[] mSpans;
    private String mText;

    SpannableStringInternal(
            CharSequence charSequence, int start, int end, boolean ignoreNoCopySpan) {
        if (start == 0 && end == charSequence.length()) {
            this.mText = charSequence.toString();
        } else {
            this.mText = charSequence.toString().substring(start, end);
        }
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanData = EmptyArray.INT;
        if (charSequence instanceof Spanned) {
            if (charSequence instanceof SpannableStringInternal) {
                copySpansFromInternal(
                        (SpannableStringInternal) charSequence, start, end, ignoreNoCopySpan);
            } else {
                copySpansFromSpanned((Spanned) charSequence, start, end, ignoreNoCopySpan);
            }
        }
    }

    SpannableStringInternal(CharSequence source, int start, int end) {
        this(source, start, end, false);
    }

    private void copySpansFromSpanned(Spanned src, int start, int end, boolean ignoreNoCopySpan) {
        Object[] spans = src.getSpans(start, end, Object.class);
        for (int i = 0; i < spans.length; i++) {
            if (!ignoreNoCopySpan || !(spans[i] instanceof NoCopySpan)) {
                int st = src.getSpanStart(spans[i]);
                int en = src.getSpanEnd(spans[i]);
                int fl = src.getSpanFlags(spans[i]);
                if (st < start) {
                    st = start;
                }
                if (en > end) {
                    en = end;
                }
                setSpan(spans[i], st - start, en - start, fl, false);
            }
        }
    }

    private void copySpansFromInternal(
            SpannableStringInternal src, int start, int end, boolean ignoreNoCopySpan) {
        int count = 0;
        int[] srcData = src.mSpanData;
        Object[] srcSpans = src.mSpans;
        int limit = src.mSpanCount;
        boolean hasNoCopySpan = false;
        for (int i = 0; i < limit; i++) {
            if (!isOutOfCopyRange(start, end, srcData[(i * 3) + 0], srcData[(i * 3) + 1])) {
                if (srcSpans[i] instanceof NoCopySpan) {
                    hasNoCopySpan = true;
                    if (ignoreNoCopySpan) {}
                }
                count++;
            }
        }
        if (count == 0) {
            return;
        }
        if (!hasNoCopySpan && start == 0 && end == src.length()) {
            this.mSpans = ArrayUtils.newUnpaddedObjectArray(src.mSpans.length);
            this.mSpanData = new int[src.mSpanData.length];
            this.mSpanCount = src.mSpanCount;
            System.arraycopy(src.mSpans, 0, this.mSpans, 0, src.mSpans.length);
            System.arraycopy(src.mSpanData, 0, this.mSpanData, 0, this.mSpanData.length);
            return;
        }
        this.mSpanCount = count;
        this.mSpans = ArrayUtils.newUnpaddedObjectArray(this.mSpanCount);
        this.mSpanData = new int[this.mSpans.length * 3];
        int j = 0;
        for (int i2 = 0; i2 < limit; i2++) {
            int spanStart = srcData[(i2 * 3) + 0];
            int spanEnd = srcData[(i2 * 3) + 1];
            if (!isOutOfCopyRange(start, end, spanStart, spanEnd)
                    && (!ignoreNoCopySpan || !(srcSpans[i2] instanceof NoCopySpan))) {
                if (spanStart < start) {
                    spanStart = start;
                }
                if (spanEnd > end) {
                    spanEnd = end;
                }
                this.mSpans[j] = srcSpans[i2];
                this.mSpanData[(j * 3) + 0] = spanStart - start;
                this.mSpanData[(j * 3) + 1] = spanEnd - start;
                this.mSpanData[(j * 3) + 2] = srcData[(i2 * 3) + 2];
                j++;
            }
        }
    }

    private final boolean isOutOfCopyRange(int start, int end, int spanStart, int spanEnd) {
        if (spanStart > end || spanEnd < start) {
            return true;
        }
        if (spanStart != spanEnd && start != end) {
            if (spanStart == end || spanEnd == start) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int length() {
        return this.mText.length();
    }

    public final char charAt(int i) {
        return this.mText.charAt(i);
    }

    public final String toString() {
        return this.mText;
    }

    public final void getChars(int start, int end, char[] dest, int off) {
        this.mText.getChars(start, end, dest, off);
    }

    public void setSpan(Object what, int start, int end, int flags) {
        setSpan(what, start, end, flags, true);
    }

    private boolean isIndexFollowsNextLine(int index) {
        return (index == 0 || index == length() || charAt(index + (-1)) == '\n') ? false : true;
    }

    private void setSpan(Object what, int start, int end, int flags, boolean enforceParagraph) {
        checkRange("setSpan", start, end);
        if ((flags & 51) == 51) {
            if (isIndexFollowsNextLine(start)) {
                if (!enforceParagraph) {
                    return;
                } else {
                    throw new RuntimeException(
                            "PARAGRAPH span must start at paragraph boundary ("
                                    + start
                                    + " follows "
                                    + charAt(start - 1)
                                    + NavigationBarInflaterView.KEY_CODE_END);
                }
            } else if (isIndexFollowsNextLine(end)) {
                if (!enforceParagraph) {
                    return;
                } else {
                    throw new RuntimeException(
                            "PARAGRAPH span must end at paragraph boundary ("
                                    + end
                                    + " follows "
                                    + charAt(end - 1)
                                    + NavigationBarInflaterView.KEY_CODE_END);
                }
            }
        }
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = 0; i < count; i++) {
            if (spans[i] == what) {
                int ostart = data[(i * 3) + 0];
                int oend = data[(i * 3) + 1];
                data[(i * 3) + 0] = start;
                data[(i * 3) + 1] = end;
                data[(i * 3) + 2] = flags;
                sendSpanChanged(what, ostart, oend, start, end);
                return;
            }
        }
        if (this.mSpanCount + 1 >= this.mSpans.length) {
            Object[] newtags =
                    ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(this.mSpanCount));
            int[] newdata = new int[newtags.length * 3];
            System.arraycopy(this.mSpans, 0, newtags, 0, this.mSpanCount);
            System.arraycopy(this.mSpanData, 0, newdata, 0, this.mSpanCount * 3);
            this.mSpans = newtags;
            this.mSpanData = newdata;
        }
        this.mSpans[this.mSpanCount] = what;
        this.mSpanData[(this.mSpanCount * 3) + 0] = start;
        this.mSpanData[(this.mSpanCount * 3) + 1] = end;
        this.mSpanData[(this.mSpanCount * 3) + 2] = flags;
        this.mSpanCount++;
        if (this instanceof Spannable) {
            sendSpanAdded(what, start, end);
        }
    }

    public void removeSpan(Object what) {
        removeSpan(what, 0);
    }

    public void removeSpan(Object what, int flags) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                int ostart = data[(i * 3) + 0];
                int oend = data[(i * 3) + 1];
                int c = count - (i + 1);
                System.arraycopy(spans, i + 1, spans, i, c);
                System.arraycopy(data, (i + 1) * 3, data, i * 3, c * 3);
                this.mSpanCount--;
                if ((flags & 512) == 0) {
                    sendSpanRemoved(what, ostart, oend);
                    return;
                }
                return;
            }
        }
    }

    public int getSpanStart(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 0];
            }
        }
        return -1;
    }

    public int getSpanEnd(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 1];
            }
        }
        return -1;
    }

    public int getSpanFlags(Object what) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        for (int i = count - 1; i >= 0; i--) {
            if (spans[i] == what) {
                return data[(i * 3) + 2];
            }
        }
        return 0;
    }

    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        int i3 = i;
        int i4 = 0;
        int i5 = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        Object[] objArr2 = (T[]) null;
        Object obj = null;
        int i6 = 0;
        while (i6 < i5) {
            int i7 = iArr[(i6 * 3) + 0];
            int i8 = iArr[(i6 * 3) + 1];
            if (i7 <= i2
                    && i8 >= i3
                    && ((i7 == i8 || i3 == i2 || (i7 != i2 && i8 != i3))
                            && (cls == null
                                    || cls == Object.class
                                    || cls.isInstance(objArr[i6])))) {
                if (i4 == 0) {
                    obj = objArr[i6];
                    i4++;
                } else {
                    if (i4 == 1) {
                        objArr2 =
                                (T[]) ((Object[]) Array.newInstance((Class<?>) cls, (i5 - i6) + 1));
                        objArr2[0] = obj;
                    }
                    int i9 = iArr[(i6 * 3) + 2] & Spanned.SPAN_PRIORITY;
                    if (i9 != 0) {
                        int i10 = 0;
                        while (i10 < i4
                                && i9 <= (getSpanFlags(objArr2[i10]) & Spanned.SPAN_PRIORITY)) {
                            i10++;
                        }
                        System.arraycopy(objArr2, i10, objArr2, i10 + 1, i4 - i10);
                        objArr2[i10] = objArr[i6];
                        i4++;
                    } else {
                        objArr2[i4] = objArr[i6];
                        i4++;
                    }
                }
            }
            i6++;
            i3 = i;
        }
        if (i4 == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        if (i4 == 1) {
            T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, 1));
            tArr[0] = obj;
            return tArr;
        }
        if (i4 == objArr2.length) {
            return (T[]) objArr2;
        }
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i4));
        System.arraycopy(objArr2, 0, tArr2, 0, i4);
        return tArr2;
    }

    public int nextSpanTransition(int start, int limit, Class kind) {
        int count = this.mSpanCount;
        Object[] spans = this.mSpans;
        int[] data = this.mSpanData;
        if (kind == null) {
            kind = Object.class;
        }
        for (int i = 0; i < count; i++) {
            int st = data[(i * 3) + 0];
            int en = data[(i * 3) + 1];
            if (st > start && st < limit && kind.isInstance(spans[i])) {
                limit = st;
            }
            if (en > start && en < limit && kind.isInstance(spans[i])) {
                limit = en;
            }
        }
        return limit;
    }

    private void sendSpanAdded(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanAdded((Spannable) this, what, start, end);
        }
    }

    private void sendSpanRemoved(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanRemoved((Spannable) this, what, start, end);
        }
    }

    private void sendSpanChanged(Object what, int s, int e, int st, int en) {
        SpanWatcher[] recip =
                (SpanWatcher[]) getSpans(Math.min(s, st), Math.max(e, en), SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanChanged((Spannable) this, what, s, e, st, en);
        }
    }

    private static String region(int start, int end) {
        return NavigationBarInflaterView.KEY_CODE_START
                + start
                + " ... "
                + end
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    private void checkRange(String operation, int start, int end) {
        if (end < start) {
            throw new IndexOutOfBoundsException(
                    operation + " " + region(start, end) + " has end before start");
        }
        int len = length();
        if (start > len || end > len) {
            throw new IndexOutOfBoundsException(
                    operation + " " + region(start, end) + " ends beyond length " + len);
        }
        if (start < 0 || end < 0) {
            throw new IndexOutOfBoundsException(
                    operation + " " + region(start, end) + " starts before 0");
        }
    }

    public boolean equals(Object o) {
        if ((o instanceof Spanned) && toString().equals(o.toString())) {
            Spanned other = (Spanned) o;
            Object[] otherSpans = other.getSpans(0, other.length(), Object.class);
            Object[] thisSpans = getSpans(0, length(), Object.class);
            if (this.mSpanCount == otherSpans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    Object thisSpan = thisSpans[i];
                    Object otherSpan = otherSpans[i];
                    if (thisSpan == this) {
                        if (other != otherSpan
                                || getSpanStart(thisSpan) != other.getSpanStart(otherSpan)
                                || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan)
                                || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                            return false;
                        }
                    } else if (!thisSpan.equals(otherSpan)
                            || getSpanStart(thisSpan) != other.getSpanStart(otherSpan)
                            || getSpanEnd(thisSpan) != other.getSpanEnd(otherSpan)
                            || getSpanFlags(thisSpan) != other.getSpanFlags(otherSpan)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = toString().hashCode();
        int hash2 = (hash * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object span = this.mSpans[i];
            if (span != this) {
                hash2 = (hash2 * 31) + span.hashCode();
            }
            hash2 =
                    (((((hash2 * 31) + getSpanStart(span)) * 31) + getSpanEnd(span)) * 31)
                            + getSpanFlags(span);
        }
        return hash2;
    }

    private void copySpans(Spanned src, int start, int end) {
        copySpansFromSpanned(src, start, end, false);
    }

    private void copySpans(SpannableStringInternal src, int start, int end) {
        copySpansFromInternal(src, start, end, false);
    }
}
