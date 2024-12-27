package android.text;

import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;

import libcore.util.EmptyArray;

import java.lang.reflect.Array;
import java.util.IdentityHashMap;

/* loaded from: classes4.dex */
public class SpannableStringBuilder
        implements CharSequence, GetChars, Spannable, Editable, Appendable, GraphicsOperations {
    private static final int END_MASK = 15;
    private static final int MARK = 1;
    private static final int PARAGRAPH = 3;
    private static final int POINT = 2;
    private static final int SPAN_ADDED = 2048;
    private static final int SPAN_END_AT_END = 32768;
    private static final int SPAN_END_AT_START = 16384;
    private static final int SPAN_START_AT_END = 8192;
    private static final int SPAN_START_AT_START = 4096;
    private static final int SPAN_START_END_MASK = 61440;
    private static final int START_MASK = 240;
    private static final int START_SHIFT = 4;
    private static final String TAG = "SpannableStringBuilder";
    private InputFilter[] mFilters;
    private int mGapLength;
    private int mGapStart;
    private IdentityHashMap<Object, Integer> mIndexOfSpan;
    private int mLowWaterMark;
    private int mSpanCount;
    private int[] mSpanEnds;
    private int[] mSpanFlags;
    private int mSpanInsertCount;
    private int[] mSpanMax;
    private int[] mSpanOrder;
    private int[] mSpanStarts;
    private Object[] mSpans;
    private char[] mText;
    private int mTextWatcherDepth;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int[][] sCachedIntBuffer =
            (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 0);

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(CharSequence text) {
        this(text, 0, text.length());
    }

    public SpannableStringBuilder(CharSequence text, int start, int end) {
        int st;
        int en;
        this.mFilters = NO_FILTERS;
        int srclen = end - start;
        if (srclen >= 0) {
            this.mText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(srclen));
            this.mGapStart = srclen;
            this.mGapLength = this.mText.length - srclen;
            TextUtils.getChars(text, start, end, this.mText, 0);
            this.mSpanCount = 0;
            this.mSpanInsertCount = 0;
            this.mSpans = EmptyArray.OBJECT;
            this.mSpanStarts = EmptyArray.INT;
            this.mSpanEnds = EmptyArray.INT;
            this.mSpanFlags = EmptyArray.INT;
            this.mSpanMax = EmptyArray.INT;
            this.mSpanOrder = EmptyArray.INT;
            if (text instanceof Spanned) {
                Spanned sp = (Spanned) text;
                Object[] spans = sp.getSpans(start, end, Object.class);
                for (int i = 0; i < spans.length; i++) {
                    if (!(spans[i] instanceof NoCopySpan)) {
                        int st2 = sp.getSpanStart(spans[i]) - start;
                        int en2 = sp.getSpanEnd(spans[i]) - start;
                        int fl = sp.getSpanFlags(spans[i]);
                        st2 = st2 < 0 ? 0 : st2;
                        if (st2 <= end - start) {
                            st = st2;
                        } else {
                            st = end - start;
                        }
                        en2 = en2 < 0 ? 0 : en2;
                        if (en2 <= end - start) {
                            en = en2;
                        } else {
                            en = end - start;
                        }
                        setSpan(false, spans[i], st, en, fl, false);
                    }
                }
                restoreInvariants();
                return;
            }
            return;
        }
        throw new StringIndexOutOfBoundsException();
    }

    public static SpannableStringBuilder valueOf(CharSequence source) {
        if (source instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder) source;
        }
        return new SpannableStringBuilder(source);
    }

    @Override // java.lang.CharSequence
    public char charAt(int where) {
        int len = length();
        if (where < 0) {
            throw new IndexOutOfBoundsException("charAt: " + where + " < 0");
        }
        if (where >= len) {
            throw new IndexOutOfBoundsException("charAt: " + where + " >= length " + len);
        }
        if (where >= this.mGapStart) {
            return this.mText[this.mGapLength + where];
        }
        return this.mText[where];
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length - this.mGapLength;
    }

    private void resizeFor(int size) {
        int oldLength = this.mText.length;
        if (size + 1 <= oldLength) {
            return;
        }
        char[] newText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(size));
        System.arraycopy(this.mText, 0, newText, 0, this.mGapStart);
        int newLength = newText.length;
        int delta = newLength - oldLength;
        int after = oldLength - (this.mGapStart + this.mGapLength);
        System.arraycopy(this.mText, oldLength - after, newText, newLength - after, after);
        this.mText = newText;
        this.mGapLength += delta;
        if (this.mGapLength < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        if (this.mSpanCount != 0) {
            for (int i = 0; i < this.mSpanCount; i++) {
                if (this.mSpanStarts[i] > this.mGapStart) {
                    int[] iArr = this.mSpanStarts;
                    iArr[i] = iArr[i] + delta;
                }
                if (this.mSpanEnds[i] > this.mGapStart) {
                    int[] iArr2 = this.mSpanEnds;
                    iArr2[i] = iArr2[i] + delta;
                }
            }
            int i2 = treeRoot();
            calcMax(i2);
        }
    }

    private void moveGapTo(int where) {
        int flag;
        int flag2;
        if (where == this.mGapStart) {
            return;
        }
        boolean atEnd = where == length();
        if (where < this.mGapStart) {
            int overlap = this.mGapStart - where;
            System.arraycopy(
                    this.mText,
                    where,
                    this.mText,
                    (this.mGapStart + this.mGapLength) - overlap,
                    overlap);
        } else {
            int overlap2 = where - this.mGapStart;
            System.arraycopy(
                    this.mText,
                    (this.mGapLength + where) - overlap2,
                    this.mText,
                    this.mGapStart,
                    overlap2);
        }
        if (this.mSpanCount != 0) {
            for (int i = 0; i < this.mSpanCount; i++) {
                int start = this.mSpanStarts[i];
                int end = this.mSpanEnds[i];
                if (start > this.mGapStart) {
                    start -= this.mGapLength;
                }
                if (start > where) {
                    start += this.mGapLength;
                } else if (start == where
                        && ((flag = (this.mSpanFlags[i] & 240) >> 4) == 2
                                || (atEnd && flag == 3))) {
                    start += this.mGapLength;
                }
                if (end > this.mGapStart) {
                    end -= this.mGapLength;
                }
                if (end > where) {
                    end += this.mGapLength;
                } else if (end == where
                        && ((flag2 = this.mSpanFlags[i] & 15) == 2 || (atEnd && flag2 == 3))) {
                    end += this.mGapLength;
                }
                this.mSpanStarts[i] = start;
                this.mSpanEnds[i] = end;
            }
            int i2 = treeRoot();
            calcMax(i2);
        }
        this.mGapStart = where;
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int where, CharSequence tb, int start, int end) {
        return replace(where, where, tb, start, end);
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int where, CharSequence tb) {
        return replace(where, where, tb, 0, tb.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder delete(int start, int end) {
        SpannableStringBuilder ret = replace(start, end, "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return ret;
    }

    @Override // android.text.Editable
    public void clear() {
        replace(0, length(), "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable
    public void clearSpans() {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            Object what = this.mSpans[i];
            int ostart = this.mSpanStarts[i];
            int oend = this.mSpanEnds[i];
            if (ostart > this.mGapStart) {
                ostart -= this.mGapLength;
            }
            if (oend > this.mGapStart) {
                oend -= this.mGapLength;
            }
            this.mSpanCount = i;
            this.mSpans[i] = null;
            sendSpanRemoved(what, ostart, oend);
        }
        if (this.mIndexOfSpan != null) {
            this.mIndexOfSpan.clear();
        }
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence text) {
        int length = length();
        return replace(length, length, text, 0, text.length());
    }

    public SpannableStringBuilder append(CharSequence text, Object what, int flags) {
        int start = length();
        append(text);
        setSpan(what, start, length(), flags);
        return this;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence text, int start, int end) {
        int length = length();
        return replace(length, length, text, start, end);
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(char text) {
        return append((CharSequence) String.valueOf(text));
    }

    private boolean removeSpansForChange(int start, int end, boolean textIsRemoved, int i) {
        if ((i & 1) != 0
                && resolveGap(this.mSpanMax[i]) >= start
                && removeSpansForChange(start, end, textIsRemoved, leftChild(i))) {
            return true;
        }
        if (i >= this.mSpanCount) {
            return false;
        }
        if ((this.mSpanFlags[i] & 33) != 33
                || this.mSpanStarts[i] < start
                || this.mSpanStarts[i] >= this.mGapStart + this.mGapLength
                || this.mSpanEnds[i] < start
                || this.mSpanEnds[i] >= this.mGapStart + this.mGapLength
                || (!textIsRemoved
                        && this.mSpanStarts[i] <= start
                        && this.mSpanEnds[i] >= this.mGapStart)) {
            return resolveGap(this.mSpanStarts[i]) <= end
                    && (i & 1) != 0
                    && removeSpansForChange(start, end, textIsRemoved, rightChild(i));
        }
        this.mIndexOfSpan.remove(this.mSpans[i]);
        removeSpan(i, 0);
        return true;
    }

    private void change(int start, int end, CharSequence cs, int csStart, int csEnd) {
        int i;
        int i2;
        CharSequence charSequence;
        int spanEnd;
        CharSequence charSequence2 = cs;
        int i3 = csStart;
        int i4 = csEnd;
        int replacedLength = end - start;
        int replacementLength = i4 - i3;
        int nbNewChars = replacementLength - replacedLength;
        boolean changed = false;
        for (int i5 = this.mSpanCount - 1; i5 >= 0; i5--) {
            int spanStart = this.mSpanStarts[i5];
            if (spanStart > this.mGapStart) {
                spanStart -= this.mGapLength;
            }
            int spanEnd2 = this.mSpanEnds[i5];
            if (spanEnd2 > this.mGapStart) {
                spanEnd2 -= this.mGapLength;
            }
            if ((this.mSpanFlags[i5] & 51) == 51) {
                int ost = spanStart;
                int oen = spanEnd2;
                int clen = length();
                if (spanStart > start && spanStart <= end) {
                    spanStart = end;
                    while (spanStart < clen
                            && (spanStart <= end || charAt(spanStart - 1) != '\n')) {
                        spanStart++;
                    }
                }
                int spanStart2 = spanStart;
                if (spanEnd2 <= start || spanEnd2 > end) {
                    spanEnd = spanEnd2;
                } else {
                    int spanEnd3 = end;
                    while (spanEnd3 < clen && (spanEnd3 <= end || charAt(spanEnd3 - 1) != '\n')) {
                        spanEnd3++;
                    }
                    spanEnd = spanEnd3;
                }
                if (spanStart2 == ost && spanEnd == oen) {
                    spanEnd2 = spanEnd;
                    spanStart = spanStart2;
                } else {
                    Object obj = this.mSpans[i5];
                    int ost2 = this.mSpanFlags[i5];
                    int spanEnd4 = spanEnd;
                    setSpan(false, obj, spanStart2, spanEnd4, ost2, true);
                    changed = true;
                    spanStart = spanStart2;
                    spanEnd2 = spanEnd4;
                }
            }
            int flags = 0;
            if (spanStart == start) {
                flags = 0 | 4096;
            } else if (spanStart == end + nbNewChars) {
                flags = 0 | 8192;
            }
            if (spanEnd2 == start) {
                flags |= 16384;
            } else if (spanEnd2 == end + nbNewChars) {
                flags |= 32768;
            }
            int[] iArr = this.mSpanFlags;
            iArr[i5] = iArr[i5] | flags;
        }
        if (changed) {
            restoreInvariants();
        }
        moveGapTo(end);
        if (nbNewChars >= this.mGapLength) {
            resizeFor((this.mText.length + nbNewChars) - this.mGapLength);
        }
        boolean textIsRemoved = replacementLength == 0;
        if (replacedLength > 0) {
            while (this.mSpanCount > 0
                    && removeSpansForChange(start, end, textIsRemoved, treeRoot())) {}
        }
        this.mGapStart += nbNewChars;
        this.mGapLength -= nbNewChars;
        if (this.mGapLength < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        TextUtils.getChars(charSequence2, i3, i4, this.mText, start);
        if (replacedLength > 0) {
            boolean atEnd = this.mGapStart + this.mGapLength == this.mText.length;
            int i6 = 0;
            while (i6 < this.mSpanCount) {
                int startFlag = (this.mSpanFlags[i6] & 240) >> 4;
                this.mSpanStarts[i6] =
                        updatedIntervalBound(
                                this.mSpanStarts[i6],
                                start,
                                nbNewChars,
                                startFlag,
                                atEnd,
                                textIsRemoved);
                int endFlag = this.mSpanFlags[i6] & 15;
                int i7 = i6;
                boolean textIsRemoved2 = textIsRemoved;
                this.mSpanEnds[i7] =
                        updatedIntervalBound(
                                this.mSpanEnds[i6],
                                start,
                                nbNewChars,
                                endFlag,
                                atEnd,
                                textIsRemoved2);
                i6 = i7 + 1;
                i3 = i3;
                i4 = i4;
                charSequence2 = charSequence2;
                textIsRemoved = textIsRemoved2;
            }
            i = i4;
            i2 = i3;
            charSequence = charSequence2;
            restoreInvariants();
        } else {
            i = i4;
            i2 = i3;
            charSequence = charSequence2;
        }
        if (charSequence instanceof Spanned) {
            Spanned sp = (Spanned) charSequence;
            Object[] spans = sp.getSpans(i2, i, Object.class);
            int i8 = 0;
            while (i8 < spans.length) {
                int st = sp.getSpanStart(spans[i8]);
                int en = sp.getSpanEnd(spans[i8]);
                if (st < i2) {
                    st = csStart;
                }
                int st2 = st;
                if (en > i) {
                    en = csEnd;
                }
                int en2 = en;
                if (getSpanStart(spans[i8]) < 0) {
                    int copySpanStart = (st2 - i2) + start;
                    int copySpanEnd = (en2 - i2) + start;
                    int copySpanFlags = sp.getSpanFlags(spans[i8]) | 2048;
                    setSpan(false, spans[i8], copySpanStart, copySpanEnd, copySpanFlags, false);
                }
                i8++;
                i2 = csStart;
                i = csEnd;
            }
            restoreInvariants();
        }
    }

    private int updatedIntervalBound(
            int offset, int start, int nbNewChars, int flag, boolean atEnd, boolean textIsRemoved) {
        if (offset >= start && offset < this.mGapStart + this.mGapLength) {
            if (flag == 2) {
                if (textIsRemoved || offset > start) {
                    return this.mGapStart + this.mGapLength;
                }
            } else if (flag == 3) {
                if (atEnd) {
                    return this.mGapStart + this.mGapLength;
                }
            } else {
                if (textIsRemoved || offset < this.mGapStart - nbNewChars) {
                    return start;
                }
                return this.mGapStart;
            }
        }
        return offset;
    }

    private void removeSpan(int i, int flags) {
        Object object = this.mSpans[i];
        int start = this.mSpanStarts[i];
        int end = this.mSpanEnds[i];
        if (start > this.mGapStart) {
            start -= this.mGapLength;
        }
        if (end > this.mGapStart) {
            end -= this.mGapLength;
        }
        int count = this.mSpanCount - (i + 1);
        System.arraycopy(this.mSpans, i + 1, this.mSpans, i, count);
        System.arraycopy(this.mSpanStarts, i + 1, this.mSpanStarts, i, count);
        System.arraycopy(this.mSpanEnds, i + 1, this.mSpanEnds, i, count);
        System.arraycopy(this.mSpanFlags, i + 1, this.mSpanFlags, i, count);
        System.arraycopy(this.mSpanOrder, i + 1, this.mSpanOrder, i, count);
        this.mSpanCount--;
        invalidateIndex(i);
        this.mSpans[this.mSpanCount] = null;
        restoreInvariants();
        if ((flags & 512) == 0) {
            sendSpanRemoved(object, start, end);
        }
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(int start, int end, CharSequence tb) {
        return replace(start, end, tb, 0, tb.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(
            int start, int end, CharSequence tb, int tbstart, int tbend) {
        int selectionStart;
        int selectionEnd;
        TextWatcher[] textWatchers;
        int selectionStart2;
        boolean changed;
        checkRange("replace", start, end);
        int filtercount = this.mFilters.length;
        CharSequence tb2 = tb;
        int tbstart2 = tbstart;
        int tbend2 = tbend;
        for (int i = 0; i < filtercount; i++) {
            CharSequence repl = this.mFilters[i].filter(tb2, tbstart2, tbend2, this, start, end);
            if (repl != null) {
                tb2 = repl;
                tbstart2 = 0;
                tbend2 = repl.length();
            }
        }
        int i2 = end - start;
        int newLen = tbend2 - tbstart2;
        if (i2 != 0 || newLen != 0 || hasNonExclusiveExclusiveSpanAt(tb2, tbstart2)) {
            TextWatcher[] textWatchers2 =
                    (TextWatcher[]) getSpans(start, start + i2, TextWatcher.class);
            sendBeforeTextChanged(textWatchers2, start, i2, newLen);
            boolean adjustSelection = (i2 == 0 || newLen == 0) ? false : true;
            if (!adjustSelection) {
                selectionStart = 0;
                selectionEnd = 0;
            } else {
                int selectionStart3 = Selection.getSelectionStart(this);
                int selectionEnd2 = Selection.getSelectionEnd(this);
                selectionStart = selectionStart3;
                selectionEnd = selectionEnd2;
            }
            int selectionEnd3 = selectionEnd;
            int selectionEnd4 = tbstart2;
            int selectionStart4 = selectionStart;
            int selectionStart5 = tbend2;
            change(start, end, tb2, selectionEnd4, selectionStart5);
            if (adjustSelection) {
                boolean changed2 = false;
                if (selectionStart4 > start && selectionStart4 < end) {
                    long diff = selectionStart4 - start;
                    int offset = Math.toIntExact((newLen * diff) / i2);
                    selectionStart2 = start + offset;
                    textWatchers = textWatchers2;
                    setSpan(
                            false,
                            Selection.SELECTION_START,
                            selectionStart2,
                            selectionStart2,
                            34,
                            true);
                    changed2 = true;
                } else {
                    textWatchers = textWatchers2;
                    selectionStart2 = selectionStart4;
                }
                if (selectionEnd3 > start && selectionEnd3 < end) {
                    long diff2 = selectionEnd3 - start;
                    int offset2 = Math.toIntExact((newLen * diff2) / i2);
                    int selectionEnd5 = start + offset2;
                    setSpan(false, Selection.SELECTION_END, selectionEnd5, selectionEnd5, 34, true);
                    changed = true;
                } else {
                    changed = changed2;
                }
                if (changed) {
                    restoreInvariants();
                }
            } else {
                textWatchers = textWatchers2;
            }
            sendTextChanged(textWatchers, start, i2, newLen);
            sendAfterTextChanged(textWatchers);
            sendToSpanWatchers(start, end, newLen - i2);
            return this;
        }
        return this;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(CharSequence text, int offset) {
        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            Object[] spans = spanned.getSpans(offset, offset, Object.class);
            for (Object span : spans) {
                int flags = spanned.getSpanFlags(span);
                if (flags != 33) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendToSpanWatchers(int r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 198
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.SpannableStringBuilder.sendToSpanWatchers(int,"
                    + " int, int):void");
    }

    @Override // android.text.Spannable
    public void setSpan(Object what, int start, int end, int flags) {
        setSpan(true, what, start, end, flags, true);
    }

    private void setSpan(
            boolean send, Object what, int start, int end, int flags, boolean enforceParagraph) {
        int start2;
        int end2;
        Integer index;
        int ostart;
        int oend;
        checkRange("setSpan", start, end);
        int flagsStart = (flags & 240) >> 4;
        if (isInvalidParagraph(start, flagsStart)) {
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
        }
        int flagsEnd = flags & 15;
        if (isInvalidParagraph(end, flagsEnd)) {
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
        if (flagsStart == 2 && flagsEnd == 1 && start == end) {
            if (send) {
                Log.e(TAG, "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length");
                return;
            }
            return;
        }
        if (start > this.mGapStart) {
            start2 = start + this.mGapLength;
        } else if (start == this.mGapStart
                && (flagsStart == 2 || (flagsStart == 3 && start == length()))) {
            start2 = start + this.mGapLength;
        } else {
            start2 = start;
        }
        if (end > this.mGapStart) {
            end2 = this.mGapLength + end;
        } else if (end == this.mGapStart && (flagsEnd == 2 || (flagsEnd == 3 && end == length()))) {
            end2 = this.mGapLength + end;
        } else {
            end2 = end;
        }
        if (this.mIndexOfSpan == null || (index = this.mIndexOfSpan.get(what)) == null) {
            this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, what);
            this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, start2);
            this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, end2);
            this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, flags);
            this.mSpanOrder =
                    GrowingArrayUtils.append(
                            this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
            invalidateIndex(this.mSpanCount);
            this.mSpanCount++;
            this.mSpanInsertCount++;
            int sizeOfMax = (treeRoot() * 2) + 1;
            if (this.mSpanMax.length < sizeOfMax) {
                this.mSpanMax = new int[sizeOfMax];
            }
            if (send) {
                restoreInvariants();
                sendSpanAdded(what, start, end);
                return;
            }
            return;
        }
        int i = index.intValue();
        int ostart2 = this.mSpanStarts[i];
        int oend2 = this.mSpanEnds[i];
        if (ostart2 <= this.mGapStart) {
            ostart = ostart2;
        } else {
            ostart = ostart2 - this.mGapLength;
        }
        if (oend2 <= this.mGapStart) {
            oend = oend2;
        } else {
            oend = oend2 - this.mGapLength;
        }
        this.mSpanStarts[i] = start2;
        this.mSpanEnds[i] = end2;
        this.mSpanFlags[i] = flags;
        if (send) {
            restoreInvariants();
            sendSpanChanged(what, ostart, oend, start, end);
        }
    }

    private boolean isInvalidParagraph(int index, int flag) {
        return (flag != 3 || index == 0 || index == length() || charAt(index + (-1)) == '\n')
                ? false
                : true;
    }

    @Override // android.text.Spannable
    public void removeSpan(Object what) {
        removeSpan(what, 0);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object what, int flags) {
        Integer i;
        if (this.mIndexOfSpan != null && (i = this.mIndexOfSpan.remove(what)) != null) {
            removeSpan(i.intValue(), flags);
        }
    }

    private int resolveGap(int i) {
        return i > this.mGapStart ? i - this.mGapLength : i;
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanStarts[i.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanEnds[i.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object what) {
        Integer i;
        if (this.mIndexOfSpan == null || (i = this.mIndexOfSpan.get(what)) == null) {
            return 0;
        }
        return this.mSpanFlags[i.intValue()];
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        return (T[]) getSpans(i, i2, cls, true);
    }

    public <T> T[] getSpans(int i, int i2, Class<T> cls, boolean z) {
        if (cls == null) {
            return (T[]) ArrayUtils.emptyArray(Object.class);
        }
        if (this.mSpanCount == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        int countSpans = countSpans(i, i2, cls, treeRoot());
        if (countSpans == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, countSpans));
        int[] obtain = z ? obtain(countSpans) : EmptyArray.INT;
        int[] obtain2 = z ? obtain(countSpans) : EmptyArray.INT;
        getSpansRec(i, i2, cls, treeRoot(), tArr, obtain, obtain2, 0, z);
        if (z) {
            sort(tArr, obtain, obtain2);
            recycle(obtain);
            recycle(obtain2);
        }
        return tArr;
    }

    private int countSpans(int queryStart, int queryEnd, Class kind, int i) {
        int count = 0;
        if ((i & 1) != 0) {
            int left = leftChild(i);
            int spanMax = this.mSpanMax[left];
            if (spanMax > this.mGapStart) {
                spanMax -= this.mGapLength;
            }
            if (spanMax >= queryStart) {
                count = countSpans(queryStart, queryEnd, kind, left);
            }
        }
        if (i < this.mSpanCount) {
            int spanStart = this.mSpanStarts[i];
            if (spanStart > this.mGapStart) {
                spanStart -= this.mGapLength;
            }
            if (spanStart <= queryEnd) {
                int spanEnd = this.mSpanEnds[i];
                if (spanEnd > this.mGapStart) {
                    spanEnd -= this.mGapLength;
                }
                if (spanEnd >= queryStart
                        && ((spanStart == spanEnd
                                        || queryStart == queryEnd
                                        || (spanStart != queryEnd && spanEnd != queryStart))
                                && (Object.class == kind || kind.isInstance(this.mSpans[i])))) {
                    count++;
                }
                if ((i & 1) != 0) {
                    return count + countSpans(queryStart, queryEnd, kind, rightChild(i));
                }
                return count;
            }
            return count;
        }
        return count;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> int getSpansRec(
            int r20,
            int r21,
            java.lang.Class<T> r22,
            int r23,
            T[] r24,
            int[] r25,
            int[] r26,
            int r27,
            boolean r28) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.text.SpannableStringBuilder.getSpansRec(int, int,"
                    + " java.lang.Class, int, java.lang.Object[], int[], int[], int, boolean):int");
    }

    private static int[] obtain(int elementCount) {
        int[] result = null;
        synchronized (sCachedIntBuffer) {
            int candidateIndex = -1;
            int i = sCachedIntBuffer.length - 1;
            while (true) {
                if (i < 0) {
                    break;
                }
                if (sCachedIntBuffer[i] != null) {
                    if (sCachedIntBuffer[i].length >= elementCount) {
                        candidateIndex = i;
                        break;
                    }
                    if (candidateIndex == -1) {
                        candidateIndex = i;
                    }
                }
                i--;
            }
            if (candidateIndex != -1) {
                result = sCachedIntBuffer[candidateIndex];
                sCachedIntBuffer[candidateIndex] = null;
            }
        }
        return checkSortBuffer(result, elementCount);
    }

    private static void recycle(int[] buffer) {
        synchronized (sCachedIntBuffer) {
            for (int i = 0; i < sCachedIntBuffer.length; i++) {
                if (sCachedIntBuffer[i] != null && buffer.length <= sCachedIntBuffer[i].length) {}
                sCachedIntBuffer[i] = buffer;
            }
        }
    }

    private static int[] checkSortBuffer(int[] buffer, int size) {
        if (buffer == null || size > buffer.length) {
            return ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(size));
        }
        return buffer;
    }

    private final <T> void sort(T[] array, int[] priority, int[] insertionOrder) {
        int size = array.length;
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i, array, size, priority, insertionOrder);
        }
        for (int i2 = size - 1; i2 > 0; i2--) {
            T tmpSpan = array[0];
            array[0] = array[i2];
            array[i2] = tmpSpan;
            int tmpPriority = priority[0];
            priority[0] = priority[i2];
            priority[i2] = tmpPriority;
            int tmpOrder = insertionOrder[0];
            insertionOrder[0] = insertionOrder[i2];
            insertionOrder[i2] = tmpOrder;
            siftDown(0, array, i2, priority, insertionOrder);
        }
    }

    private final <T> void siftDown(
            int index, T[] array, int size, int[] priority, int[] insertionOrder) {
        int left = (index * 2) + 1;
        while (left < size) {
            if (left < size - 1 && compareSpans(left, left + 1, priority, insertionOrder) < 0) {
                left++;
            }
            if (compareSpans(index, left, priority, insertionOrder) < 0) {
                T tmpSpan = array[index];
                array[index] = array[left];
                array[left] = tmpSpan;
                int tmpPriority = priority[index];
                priority[index] = priority[left];
                priority[left] = tmpPriority;
                int tmpOrder = insertionOrder[index];
                insertionOrder[index] = insertionOrder[left];
                insertionOrder[left] = tmpOrder;
                index = left;
                left = (index * 2) + 1;
            } else {
                return;
            }
        }
    }

    private final int compareSpans(int left, int right, int[] priority, int[] insertionOrder) {
        int priority1 = priority[left];
        int priority2 = priority[right];
        if (priority1 == priority2) {
            return Integer.compare(insertionOrder[left], insertionOrder[right]);
        }
        return Integer.compare(priority2, priority1);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int start, int limit, Class kind) {
        if (this.mSpanCount == 0) {
            return limit;
        }
        if (kind == null) {
            kind = Object.class;
        }
        return nextSpanTransitionRec(start, limit, kind, treeRoot());
    }

    private int nextSpanTransitionRec(int start, int limit, Class kind, int i) {
        if ((i & 1) != 0) {
            int left = leftChild(i);
            if (resolveGap(this.mSpanMax[left]) > start) {
                limit = nextSpanTransitionRec(start, limit, kind, left);
            }
        }
        if (i < this.mSpanCount) {
            int st = resolveGap(this.mSpanStarts[i]);
            int en = resolveGap(this.mSpanEnds[i]);
            if (st > start && st < limit && kind.isInstance(this.mSpans[i])) {
                limit = st;
            }
            if (en > start && en < limit && kind.isInstance(this.mSpans[i])) {
                limit = en;
            }
            if (st < limit && (i & 1) != 0) {
                return nextSpanTransitionRec(start, limit, kind, rightChild(i));
            }
            return limit;
        }
        return limit;
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return new SpannableStringBuilder(this, start, end);
    }

    @Override // android.text.GetChars
    public void getChars(int start, int end, char[] dest, int destoff) {
        checkRange("getChars", start, end);
        if (end <= this.mGapStart) {
            System.arraycopy(this.mText, start, dest, destoff, end - start);
        } else if (start >= this.mGapStart) {
            System.arraycopy(this.mText, this.mGapLength + start, dest, destoff, end - start);
        } else {
            System.arraycopy(this.mText, start, dest, destoff, this.mGapStart - start);
            System.arraycopy(
                    this.mText,
                    this.mGapStart + this.mGapLength,
                    dest,
                    (this.mGapStart - start) + destoff,
                    end - this.mGapStart);
        }
    }

    @Override // java.lang.CharSequence
    public String toString() {
        int len = length();
        char[] buf = new char[len];
        getChars(0, len, buf, 0);
        return new String(buf);
    }

    public String substring(int start, int end) {
        char[] buf = new char[end - start];
        getChars(start, end, buf, 0);
        return new String(buf);
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.beforeTextChanged(this, start, before, after);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendTextChanged(TextWatcher[] watchers, int start, int before, int after) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.onTextChanged(this, start, before, after);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendAfterTextChanged(TextWatcher[] watchers) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : watchers) {
            textWatcher.afterTextChanged(this);
        }
        int i = this.mTextWatcherDepth;
        this.mTextWatcherDepth = i - 1;
    }

    private void sendSpanAdded(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanAdded(this, what, start, end);
        }
    }

    private void sendSpanRemoved(Object what, int start, int end) {
        SpanWatcher[] recip = (SpanWatcher[]) getSpans(start, end, SpanWatcher.class);
        for (SpanWatcher spanWatcher : recip) {
            spanWatcher.onSpanRemoved(this, what, start, end);
        }
    }

    private void sendSpanChanged(Object what, int oldStart, int oldEnd, int start, int end) {
        SpanWatcher[] spanWatchers =
                (SpanWatcher[])
                        getSpans(
                                Math.min(oldStart, start),
                                Math.min(Math.max(oldEnd, end), length()),
                                SpanWatcher.class);
        for (SpanWatcher spanWatcher : spanWatchers) {
            spanWatcher.onSpanChanged(this, what, oldStart, oldEnd, start, end);
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

    @Override // android.text.GraphicsOperations
    public void drawText(BaseCanvas c, int start, int end, float x, float y, Paint p) {
        checkRange("drawText", start, end);
        if (end <= this.mGapStart) {
            c.drawText(this.mText, start, end - start, x, y, p);
            return;
        }
        if (start >= this.mGapStart) {
            c.drawText(this.mText, start + this.mGapLength, end - start, x, y, p);
            return;
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        c.drawText(buf, 0, end - start, x, y, p);
        TextUtils.recycle(buf);
    }

    @Override // android.text.GraphicsOperations
    public void drawTextRun(
            BaseCanvas c,
            int start,
            int end,
            int contextStart,
            int contextEnd,
            float x,
            float y,
            boolean isRtl,
            Paint p) {
        checkRange("drawTextRun", start, end);
        int contextLen = contextEnd - contextStart;
        int len = end - start;
        if (contextEnd <= this.mGapStart) {
            c.drawTextRun(this.mText, start, len, contextStart, contextLen, x, y, isRtl, p);
            return;
        }
        if (contextStart >= this.mGapStart) {
            c.drawTextRun(
                    this.mText,
                    start + this.mGapLength,
                    len,
                    contextStart + this.mGapLength,
                    contextLen,
                    x,
                    y,
                    isRtl,
                    p);
            return;
        }
        char[] buf = TextUtils.obtain(contextLen);
        getChars(contextStart, contextEnd, buf, 0);
        c.drawTextRun(buf, start - contextStart, len, 0, contextLen, x, y, isRtl, p);
        TextUtils.recycle(buf);
    }

    @Override // android.text.GraphicsOperations
    public float measureText(int start, int end, Paint p) {
        checkRange("measureText", start, end);
        if (end <= this.mGapStart) {
            float ret = p.measureText(this.mText, start, end - start);
            return ret;
        }
        if (start >= this.mGapStart) {
            float ret2 = p.measureText(this.mText, this.mGapLength + start, end - start);
            return ret2;
        }
        char[] buf = TextUtils.obtain(end - start);
        getChars(start, end, buf, 0);
        float ret3 = p.measureText(buf, 0, end - start);
        TextUtils.recycle(buf);
        return ret3;
    }

    @Override // android.text.GraphicsOperations
    public int getTextWidths(int start, int end, float[] widths, Paint p) {
        checkRange("getTextWidths", start, end);
        if (end <= this.mGapStart) {
            int ret = p.getTextWidths(this.mText, start, end - start, widths);
            return ret;
        }
        int ret2 = this.mGapStart;
        if (start >= ret2) {
            int ret3 = p.getTextWidths(this.mText, this.mGapLength + start, end - start, widths);
            return ret3;
        }
        int ret4 = end - start;
        char[] buf = TextUtils.obtain(ret4);
        getChars(start, end, buf, 0);
        int ret5 = p.getTextWidths(buf, 0, end - start, widths);
        TextUtils.recycle(buf);
        return ret5;
    }

    @Override // android.text.GraphicsOperations
    public float getTextRunAdvances(
            int start,
            int end,
            int contextStart,
            int contextEnd,
            boolean isRtl,
            float[] advances,
            int advancesPos,
            Paint p) {
        int contextLen = contextEnd - contextStart;
        int len = end - start;
        if (end <= this.mGapStart) {
            float ret =
                    p.getTextRunAdvances(
                            this.mText,
                            start,
                            len,
                            contextStart,
                            contextLen,
                            isRtl,
                            advances,
                            advancesPos);
            return ret;
        }
        if (start >= this.mGapStart) {
            float ret2 =
                    p.getTextRunAdvances(
                            this.mText,
                            start + this.mGapLength,
                            len,
                            contextStart + this.mGapLength,
                            contextLen,
                            isRtl,
                            advances,
                            advancesPos);
            return ret2;
        }
        char[] buf = TextUtils.obtain(contextLen);
        getChars(contextStart, contextEnd, buf, 0);
        float ret3 =
                p.getTextRunAdvances(
                        buf,
                        start - contextStart,
                        len,
                        0,
                        contextLen,
                        isRtl,
                        advances,
                        advancesPos);
        TextUtils.recycle(buf);
        return ret3;
    }

    @Deprecated
    public int getTextRunCursor(
            int contextStart, int contextEnd, int dir, int offset, int cursorOpt, Paint p) {
        return getTextRunCursor(contextStart, contextEnd, dir == 1, offset, cursorOpt, p);
    }

    @Override // android.text.GraphicsOperations
    public int getTextRunCursor(
            int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt, Paint p) {
        int contextLen = contextEnd - contextStart;
        if (contextEnd <= this.mGapStart) {
            int ret =
                    p.getTextRunCursor(
                            this.mText, contextStart, contextLen, isRtl, offset, cursorOpt);
            return ret;
        }
        int ret2 = this.mGapStart;
        if (contextStart >= ret2) {
            int ret3 =
                    p.getTextRunCursor(
                                    this.mText,
                                    contextStart + this.mGapLength,
                                    contextLen,
                                    isRtl,
                                    offset + this.mGapLength,
                                    cursorOpt)
                            - this.mGapLength;
            return ret3;
        }
        char[] buf = TextUtils.obtain(contextLen);
        getChars(contextStart, contextEnd, buf, 0);
        int ret4 =
                p.getTextRunCursor(buf, 0, contextLen, isRtl, offset - contextStart, cursorOpt)
                        + contextStart;
        TextUtils.recycle(buf);
        return ret4;
    }

    @Override // android.text.Editable
    public void setFilters(InputFilter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException();
        }
        this.mFilters = filters;
    }

    @Override // android.text.Editable
    public InputFilter[] getFilters() {
        return this.mFilters;
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

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private static int leftChild(int i) {
        return i - (((i + 1) & (~i)) >> 1);
    }

    private static int rightChild(int i) {
        return (((i + 1) & (~i)) >> 1) + i;
    }

    private int calcMax(int i) {
        int max = 0;
        if ((i & 1) != 0) {
            max = calcMax(leftChild(i));
        }
        if (i < this.mSpanCount) {
            max = Math.max(max, this.mSpanEnds[i]);
            if ((i & 1) != 0) {
                max = Math.max(max, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = max;
        return max;
    }

    private void restoreInvariants() {
        if (this.mSpanCount == 0) {
            return;
        }
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpanStarts[i] < this.mSpanStarts[i - 1]) {
                Object span = this.mSpans[i];
                int start = this.mSpanStarts[i];
                int end = this.mSpanEnds[i];
                int flags = this.mSpanFlags[i];
                int insertionOrder = this.mSpanOrder[i];
                int j = i;
                do {
                    this.mSpans[j] = this.mSpans[j - 1];
                    this.mSpanStarts[j] = this.mSpanStarts[j - 1];
                    this.mSpanEnds[j] = this.mSpanEnds[j - 1];
                    this.mSpanFlags[j] = this.mSpanFlags[j - 1];
                    this.mSpanOrder[j] = this.mSpanOrder[j - 1];
                    j--;
                    if (j <= 0) {
                        break;
                    }
                } while (start < this.mSpanStarts[j - 1]);
                this.mSpans[j] = span;
                this.mSpanStarts[j] = start;
                this.mSpanEnds[j] = end;
                this.mSpanFlags[j] = flags;
                this.mSpanOrder[j] = insertionOrder;
                invalidateIndex(j);
            }
        }
        int i2 = treeRoot();
        calcMax(i2);
        if (this.mIndexOfSpan == null) {
            this.mIndexOfSpan = new IdentityHashMap<>();
        }
        for (int i3 = this.mLowWaterMark; i3 < this.mSpanCount; i3++) {
            Integer existing = this.mIndexOfSpan.get(this.mSpans[i3]);
            if (existing == null || existing.intValue() != i3) {
                this.mIndexOfSpan.put(this.mSpans[i3], Integer.valueOf(i3));
            }
        }
        this.mLowWaterMark = Integer.MAX_VALUE;
    }

    private void invalidateIndex(int i) {
        this.mLowWaterMark = Math.min(i, this.mLowWaterMark);
    }
}
