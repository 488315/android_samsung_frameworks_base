package android.text;

import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import libcore.util.EmptyArray;

/* loaded from: classes4.dex */
public class SpannableStringBuilder implements CharSequence, GetChars, Spannable, Editable, Appendable, GraphicsOperations {
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
    private static final int[][] sCachedIntBuffer = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 0);

    private static int leftChild(int i) {
        return i - (((i + 1) & (~i)) >> 1);
    }

    private static int rightChild(int i) {
        return i + (((i + 1) & (~i)) >> 1);
    }

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public SpannableStringBuilder(CharSequence charSequence, int i, int i2) {
        this.mFilters = NO_FILTERS;
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new StringIndexOutOfBoundsException();
        }
        char[] cArrNewUnpaddedCharArray = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(i3));
        this.mText = cArrNewUnpaddedCharArray;
        this.mGapStart = i3;
        this.mGapLength = cArrNewUnpaddedCharArray.length - i3;
        TextUtils.getChars(charSequence, i, i2, cArrNewUnpaddedCharArray, 0);
        this.mSpanCount = 0;
        this.mSpanInsertCount = 0;
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanStarts = EmptyArray.INT;
        this.mSpanEnds = EmptyArray.INT;
        this.mSpanFlags = EmptyArray.INT;
        this.mSpanMax = EmptyArray.INT;
        this.mSpanOrder = EmptyArray.INT;
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            Object[] spans = spanned.getSpans(i, i2, Object.class);
            for (int i4 = 0; i4 < spans.length; i4++) {
                Object obj = spans[i4];
                if (!(obj instanceof NoCopySpan)) {
                    int spanStart = spanned.getSpanStart(obj) - i;
                    int spanEnd = spanned.getSpanEnd(spans[i4]) - i;
                    int spanFlags = spanned.getSpanFlags(spans[i4]);
                    spanStart = spanStart < 0 ? 0 : spanStart;
                    int i5 = spanStart > i3 ? i3 : spanStart;
                    spanEnd = spanEnd < 0 ? 0 : spanEnd;
                    setSpan(false, spans[i4], i5, spanEnd > i3 ? i3 : spanEnd, spanFlags, false);
                }
            }
            restoreInvariants();
        }
    }

    public static SpannableStringBuilder valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder) charSequence;
        }
        return new SpannableStringBuilder(charSequence);
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        int length = length();
        if (i < 0) {
            throw new IndexOutOfBoundsException("charAt: " + i + " < 0");
        }
        if (i >= length) {
            throw new IndexOutOfBoundsException("charAt: " + i + " >= length " + length);
        }
        if (i >= this.mGapStart) {
            return this.mText[i + this.mGapLength];
        }
        return this.mText[i];
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length - this.mGapLength;
    }

    private void resizeFor(int i) {
        int length = this.mText.length;
        if (i + 1 <= length) {
            return;
        }
        char[] cArrNewUnpaddedCharArray = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(i));
        System.arraycopy(this.mText, 0, cArrNewUnpaddedCharArray, 0, this.mGapStart);
        int length2 = cArrNewUnpaddedCharArray.length;
        int i2 = length2 - length;
        int i3 = length - (this.mGapStart + this.mGapLength);
        System.arraycopy(this.mText, length - i3, cArrNewUnpaddedCharArray, length2 - i3, i3);
        this.mText = cArrNewUnpaddedCharArray;
        int i4 = this.mGapLength + i2;
        this.mGapLength = i4;
        if (i4 < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        if (this.mSpanCount != 0) {
            for (int i5 = 0; i5 < this.mSpanCount; i5++) {
                int[] iArr = this.mSpanStarts;
                int i6 = iArr[i5];
                int i7 = this.mGapStart;
                if (i6 > i7) {
                    iArr[i5] = i6 + i2;
                }
                int[] iArr2 = this.mSpanEnds;
                int i8 = iArr2[i5];
                if (i8 > i7) {
                    iArr2[i5] = i8 + i2;
                }
            }
            calcMax(treeRoot());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void moveGapTo(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (i == this.mGapStart) {
            return;
        }
        boolean z = i == length();
        int i6 = this.mGapStart;
        if (i < i6) {
            int i7 = i6 - i;
            char[] cArr = this.mText;
            System.arraycopy(cArr, i, cArr, (i6 + this.mGapLength) - i7, i7);
        } else {
            int i8 = i - i6;
            char[] cArr2 = this.mText;
            System.arraycopy(cArr2, (this.mGapLength + i) - i8, cArr2, i6, i8);
        }
        if (this.mSpanCount != 0) {
            for (int i9 = 0; i9 < this.mSpanCount; i9++) {
                int[] iArr = this.mSpanStarts;
                int i10 = iArr[i9];
                int[] iArr2 = this.mSpanEnds;
                int i11 = iArr2[i9];
                int i12 = this.mGapStart;
                if (i10 > i12) {
                    i10 -= this.mGapLength;
                }
                if (i10 > i) {
                    i3 = this.mGapLength;
                } else {
                    if (i10 == i && ((i2 = (this.mSpanFlags[i9] & 240) >> 4) == 2 || (z && i2 == 3))) {
                        i3 = this.mGapLength;
                    }
                    if (i11 > i12) {
                        i11 -= this.mGapLength;
                    }
                    if (i11 <= i) {
                        i5 = this.mGapLength;
                    } else if (i11 == i && ((i4 = this.mSpanFlags[i9] & 15) == 2 || (z && i4 == 3))) {
                        i5 = this.mGapLength;
                    } else {
                        iArr[i9] = i10;
                        iArr2[i9] = i11;
                    }
                    i11 += i5;
                    iArr[i9] = i10;
                    iArr2[i9] = i11;
                }
                i10 += i3;
                if (i11 > i12) {
                }
                if (i11 <= i) {
                }
                i11 += i5;
                iArr[i9] = i10;
                iArr2[i9] = i11;
            }
            calcMax(treeRoot());
        }
        this.mGapStart = i;
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int i, CharSequence charSequence, int i2, int i3) {
        return replace(i, i, charSequence, i2, i3);
    }

    @Override // android.text.Editable
    public SpannableStringBuilder insert(int i, CharSequence charSequence) {
        return replace(i, i, charSequence, 0, charSequence.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder delete(int i, int i2) {
        SpannableStringBuilder spannableStringBuilderReplace = replace(i, i2, "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return spannableStringBuilderReplace;
    }

    @Override // android.text.Editable
    public void clear() {
        replace(0, length(), "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable
    public void clearSpans() {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            Object[] objArr = this.mSpans;
            Object obj = objArr[i];
            int i2 = this.mSpanStarts[i];
            int i3 = this.mSpanEnds[i];
            int i4 = this.mGapStart;
            if (i2 > i4) {
                i2 -= this.mGapLength;
            }
            if (i3 > i4) {
                i3 -= this.mGapLength;
            }
            this.mSpanCount = i;
            objArr[i] = null;
            sendSpanRemoved(obj, i2, i3);
        }
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap != null) {
            identityHashMap.clear();
        }
        this.mSpanInsertCount = 0;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence charSequence) {
        int length = length();
        return replace(length, length, charSequence, 0, charSequence.length());
    }

    public SpannableStringBuilder append(CharSequence charSequence, Object obj, int i) {
        int length = length();
        append(charSequence);
        setSpan(obj, length, length(), i);
        return this;
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(CharSequence charSequence, int i, int i2) {
        int length = length();
        return replace(length, length, charSequence, i, i2);
    }

    @Override // android.text.Editable, java.lang.Appendable
    public SpannableStringBuilder append(char c) {
        return append((CharSequence) String.valueOf(c));
    }

    private boolean removeSpansForChange(int i, int i2, boolean z, int i3) {
        int i4;
        int i5;
        int i6 = i3 & 1;
        if (i6 != 0 && resolveGap(this.mSpanMax[i3]) >= i && removeSpansForChange(i, i2, z, leftChild(i3))) {
            return true;
        }
        if (i3 < this.mSpanCount) {
            if ((this.mSpanFlags[i3] & 33) == 33 && (i4 = this.mSpanStarts[i3]) >= i) {
                int i7 = this.mGapStart;
                int i8 = this.mGapLength;
                if (i4 < i7 + i8 && (i5 = this.mSpanEnds[i3]) >= i && i5 < i8 + i7 && (z || i4 > i || i5 < i7)) {
                    this.mIndexOfSpan.remove(this.mSpans[i3]);
                    removeSpan(i3, 0);
                    return true;
                }
            }
            if (resolveGap(this.mSpanStarts[i3]) <= i2 && i6 != 0 && removeSpansForChange(i, i2, z, rightChild(i3))) {
                return true;
            }
        }
        return false;
    }

    private void change(int i, int i2, CharSequence charSequence, int i3, int i4) {
        int i5;
        boolean z;
        int i6;
        SpannableStringBuilder spannableStringBuilder = this;
        int i7 = i;
        int i8 = i2 - i7;
        int i9 = i4 - i3;
        int i10 = i9 - i8;
        boolean z2 = false;
        for (int i11 = spannableStringBuilder.mSpanCount - 1; i11 >= 0; i11--) {
            int i12 = spannableStringBuilder.mSpanStarts[i11];
            int i13 = spannableStringBuilder.mGapStart;
            if (i12 > i13) {
                i12 -= spannableStringBuilder.mGapLength;
            }
            int i14 = spannableStringBuilder.mSpanEnds[i11];
            if (i14 > i13) {
                i14 -= spannableStringBuilder.mGapLength;
            }
            if ((spannableStringBuilder.mSpanFlags[i11] & 51) == 51) {
                int length = spannableStringBuilder.length();
                if (i12 <= i7 || i12 > i2) {
                    i5 = i12;
                } else {
                    i5 = i2;
                    while (i5 < length && (i5 <= i2 || spannableStringBuilder.charAt(i5 - 1) != '\n')) {
                        i5++;
                    }
                }
                if (i14 > i7 && i14 <= i2) {
                    i6 = i2;
                    while (true) {
                        if (i6 >= length) {
                            z = z2;
                            break;
                        }
                        if (i6 > i2) {
                            z = z2;
                            if (spannableStringBuilder.charAt(i6 - 1) == '\n') {
                                break;
                            }
                        } else {
                            z = z2;
                        }
                        i6++;
                        z2 = z;
                    }
                } else {
                    z = z2;
                    i6 = i14;
                }
                if (i5 == i12 && i6 == i14) {
                    i12 = i5;
                    i14 = i6;
                    z2 = z;
                } else {
                    int i15 = i5;
                    i14 = i6;
                    spannableStringBuilder.setSpan(false, spannableStringBuilder.mSpans[i11], i15, i14, spannableStringBuilder.mSpanFlags[i11], true);
                    i12 = i15;
                    z2 = true;
                }
            }
            int i16 = i12 == i7 ? 4096 : i12 == i2 + i10 ? 8192 : 0;
            if (i14 == i7) {
                i16 |= 16384;
            } else if (i14 == i2 + i10) {
                i16 |= 32768;
            }
            int[] iArr = spannableStringBuilder.mSpanFlags;
            iArr[i11] = i16 | iArr[i11];
        }
        if (z2) {
            spannableStringBuilder.restoreInvariants();
        }
        spannableStringBuilder.moveGapTo(i2);
        int i17 = spannableStringBuilder.mGapLength;
        if (i10 >= i17) {
            spannableStringBuilder.resizeFor((spannableStringBuilder.mText.length + i10) - i17);
        }
        boolean z3 = i9 == 0;
        if (i8 > 0) {
            while (spannableStringBuilder.mSpanCount > 0 && spannableStringBuilder.removeSpansForChange(i7, i2, z3, spannableStringBuilder.treeRoot())) {
            }
        }
        spannableStringBuilder.mGapStart += i10;
        int i18 = spannableStringBuilder.mGapLength - i10;
        spannableStringBuilder.mGapLength = i18;
        if (i18 < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        TextUtils.getChars(charSequence, i3, i4, spannableStringBuilder.mText, i7);
        if (i8 > 0) {
            boolean z4 = spannableStringBuilder.mGapStart + spannableStringBuilder.mGapLength == spannableStringBuilder.mText.length;
            int i19 = 0;
            while (i19 < spannableStringBuilder.mSpanCount) {
                int i20 = (spannableStringBuilder.mSpanFlags[i19] & 240) >> 4;
                int[] iArr2 = spannableStringBuilder.mSpanStarts;
                iArr2[i19] = spannableStringBuilder.updatedIntervalBound(iArr2[i19], i7, i10, i20, z4, z3);
                int i21 = spannableStringBuilder.mSpanFlags[i19] & 15;
                int[] iArr3 = spannableStringBuilder.mSpanEnds;
                iArr3[i19] = spannableStringBuilder.updatedIntervalBound(iArr3[i19], i, i10, i21, z4, z3);
                i19++;
                i7 = i;
            }
            spannableStringBuilder.restoreInvariants();
        }
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            Object[] spans = spanned.getSpans(i3, i4, Object.class);
            int i22 = 0;
            while (i22 < spans.length) {
                int spanStart = spanned.getSpanStart(spans[i22]);
                int spanEnd = spanned.getSpanEnd(spans[i22]);
                if (spanStart < i3) {
                    spanStart = i3;
                }
                if (spanEnd > i4) {
                    spanEnd = i4;
                }
                if (spannableStringBuilder.getSpanStart(spans[i22]) < 0) {
                    spannableStringBuilder.setSpan(false, spans[i22], (spanStart - i3) + i, (spanEnd - i3) + i, spanned.getSpanFlags(spans[i22]) | 2048, false);
                }
                i22++;
                spannableStringBuilder = this;
            }
            restoreInvariants();
        }
    }

    private int updatedIntervalBound(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        if (i >= i2) {
            int i5 = this.mGapStart;
            int i6 = this.mGapLength;
            if (i < i5 + i6) {
                if (i4 == 2) {
                    if (z2 || i > i2) {
                        return i5 + i6;
                    }
                } else {
                    if (i4 != 3) {
                        return (z2 || i < i5 - i3) ? i2 : i5;
                    }
                    if (z) {
                        return i5 + i6;
                    }
                }
            }
        }
        return i;
    }

    private void removeSpan(int i, int i2) {
        Object[] objArr = this.mSpans;
        Object obj = objArr[i];
        int i3 = this.mSpanStarts[i];
        int i4 = this.mSpanEnds[i];
        int i5 = this.mGapStart;
        if (i3 > i5) {
            i3 -= this.mGapLength;
        }
        if (i4 > i5) {
            i4 -= this.mGapLength;
        }
        int i6 = i + 1;
        int i7 = this.mSpanCount - i6;
        System.arraycopy(objArr, i6, objArr, i, i7);
        int[] iArr = this.mSpanStarts;
        System.arraycopy(iArr, i6, iArr, i, i7);
        int[] iArr2 = this.mSpanEnds;
        System.arraycopy(iArr2, i6, iArr2, i, i7);
        int[] iArr3 = this.mSpanFlags;
        System.arraycopy(iArr3, i6, iArr3, i, i7);
        int[] iArr4 = this.mSpanOrder;
        System.arraycopy(iArr4, i6, iArr4, i, i7);
        this.mSpanCount--;
        invalidateIndex(i);
        this.mSpans[this.mSpanCount] = null;
        restoreInvariants();
        if ((i2 & 512) == 0) {
            sendSpanRemoved(obj, i3, i4);
        }
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(int i, int i2, CharSequence charSequence) {
        return replace(i, i2, charSequence, 0, charSequence.length());
    }

    @Override // android.text.Editable
    public SpannableStringBuilder replace(int i, int i2, CharSequence charSequence, int i3, int i4) {
        int i5;
        CharSequence charSequence2;
        int i6;
        int selectionEnd;
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2 = this;
        int i7 = i;
        int i8 = i2;
        spannableStringBuilder2.checkRange("replace", i7, i8);
        int length = spannableStringBuilder2.mFilters.length;
        boolean z = false;
        CharSequence charSequence3 = charSequence;
        int i9 = i3;
        int length2 = i4;
        int i10 = 0;
        while (i10 < length) {
            int i11 = i9;
            SpannableStringBuilder spannableStringBuilder3 = spannableStringBuilder2;
            int i12 = i8;
            int i13 = i11;
            int i14 = length2;
            int i15 = i7;
            CharSequence charSequence4 = charSequence3;
            CharSequence charSequenceFilter = spannableStringBuilder2.mFilters[i10].filter(charSequence4, i13, i14, spannableStringBuilder3, i15, i12);
            i7 = i15;
            if (charSequenceFilter != null) {
                charSequence3 = charSequenceFilter;
                length2 = charSequenceFilter.length();
                i13 = 0;
            } else {
                length2 = i14;
                charSequence3 = charSequence4;
            }
            i10++;
            spannableStringBuilder2 = spannableStringBuilder3;
            i9 = i13;
            i8 = i2;
        }
        int i16 = length2;
        CharSequence charSequence5 = charSequence3;
        int i17 = i9;
        SpannableStringBuilder spannableStringBuilder4 = spannableStringBuilder2;
        int i18 = i2 - i7;
        int i19 = i16 - i17;
        if (i18 == 0 && i19 == 0 && !hasNonExclusiveExclusiveSpanAt(charSequence5, i17)) {
            return spannableStringBuilder4;
        }
        TextWatcher[] textWatcherArr = (TextWatcher[]) spannableStringBuilder4.getSpans(i7, i7 + i18, TextWatcher.class);
        spannableStringBuilder4.sendBeforeTextChanged(textWatcherArr, i7, i18, i19);
        boolean z2 = true;
        boolean z3 = (i18 == 0 || i19 == 0) ? false : true;
        if (z3) {
            int selectionStart = Selection.getSelectionStart(spannableStringBuilder4);
            i5 = i16;
            charSequence2 = charSequence5;
            selectionEnd = Selection.getSelectionEnd(spannableStringBuilder4);
            i6 = selectionStart;
        } else {
            i5 = i16;
            charSequence2 = charSequence5;
            i6 = 0;
            selectionEnd = 0;
        }
        spannableStringBuilder4.change(i7, i2, charSequence2, i17, i5);
        int i20 = i7;
        if (z3) {
            if (i6 > i20 && i6 < i2) {
                int intExact = i20 + Math.toIntExact(((i6 - i20) * i19) / i18);
                setSpan(false, Selection.SELECTION_START, intExact, intExact, 34, true);
                z = true;
            }
            if (selectionEnd <= i20 || selectionEnd >= i2) {
                spannableStringBuilder = this;
                z2 = z;
            } else {
                int intExact2 = i20 + Math.toIntExact(((selectionEnd - i20) * i19) / i18);
                spannableStringBuilder = this;
                spannableStringBuilder.setSpan(false, Selection.SELECTION_END, intExact2, intExact2, 34, true);
            }
            if (z2) {
                spannableStringBuilder.restoreInvariants();
            }
        } else {
            spannableStringBuilder = this;
        }
        spannableStringBuilder.sendTextChanged(textWatcherArr, i20, i18, i19);
        spannableStringBuilder.sendAfterTextChanged(textWatcherArr);
        spannableStringBuilder.sendToSpanWatchers(i20, i2, i19 - i18);
        return spannableStringBuilder;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(CharSequence charSequence, int i) {
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned) charSequence;
            for (Object obj : spanned.getSpans(i, i, Object.class)) {
                if (spanned.getSpanFlags(obj) != 33) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void sendToSpanWatchers(int i, int i2, int i3) {
        boolean z;
        int i4;
        int i5;
        SpannableStringBuilder spannableStringBuilder;
        int i6 = 0;
        while (i6 < this.mSpanCount) {
            int i7 = this.mSpanFlags[i6];
            if ((i7 & 2048) != 0) {
                spannableStringBuilder = this;
            } else {
                int i8 = this.mSpanStarts[i6];
                int i9 = this.mSpanEnds[i6];
                int i10 = this.mGapStart;
                if (i8 > i10) {
                    i8 -= this.mGapLength;
                }
                int i11 = i8;
                if (i9 > i10) {
                    i9 -= this.mGapLength;
                }
                int i12 = i9;
                int i13 = i2 + i3;
                boolean z2 = true;
                if (i11 <= i13) {
                    if (i11 >= i && ((i11 != i || (i7 & 4096) != 4096) && (i11 != i13 || (i7 & 8192) != 8192))) {
                        z = true;
                    }
                    i4 = i11;
                    if (i12 <= i13) {
                    }
                    z2 = z;
                    i5 = i12;
                    if (z2) {
                    }
                    int[] iArr = spannableStringBuilder.mSpanFlags;
                    iArr[i6] = iArr[i6] & (-61441);
                } else if (i3 != 0) {
                    i4 = i11 - i3;
                    z = true;
                    if (i12 <= i13) {
                        if (i12 < i || ((i12 == i && (i7 & 16384) == 16384) || (i12 == i13 && (i7 & 32768) == 32768))) {
                        }
                        i5 = i12;
                        if (z2) {
                        }
                        int[] iArr2 = spannableStringBuilder.mSpanFlags;
                        iArr2[i6] = iArr2[i6] & (-61441);
                    } else if (i3 != 0) {
                        i5 = i12 - i3;
                        if (z2) {
                            spannableStringBuilder = this;
                            spannableStringBuilder.sendSpanChanged(this.mSpans[i6], i4, i5, i11, i12);
                        } else {
                            spannableStringBuilder = this;
                        }
                        int[] iArr22 = spannableStringBuilder.mSpanFlags;
                        iArr22[i6] = iArr22[i6] & (-61441);
                    }
                    z2 = z;
                    i5 = i12;
                    if (z2) {
                    }
                    int[] iArr222 = spannableStringBuilder.mSpanFlags;
                    iArr222[i6] = iArr222[i6] & (-61441);
                }
                z = false;
                i4 = i11;
                if (i12 <= i13) {
                }
                z2 = z;
                i5 = i12;
                if (z2) {
                }
                int[] iArr2222 = spannableStringBuilder.mSpanFlags;
                iArr2222[i6] = iArr2222[i6] & (-61441);
            }
            i6++;
            this = spannableStringBuilder;
        }
        SpannableStringBuilder spannableStringBuilder2 = this;
        for (int i14 = 0; i14 < spannableStringBuilder2.mSpanCount; i14++) {
            int[] iArr3 = spannableStringBuilder2.mSpanFlags;
            int i15 = iArr3[i14];
            if ((i15 & 2048) != 0) {
                iArr3[i14] = i15 & (-2049);
                int i16 = spannableStringBuilder2.mSpanStarts[i14];
                int i17 = spannableStringBuilder2.mSpanEnds[i14];
                int i18 = spannableStringBuilder2.mGapStart;
                if (i16 > i18) {
                    i16 -= spannableStringBuilder2.mGapLength;
                }
                if (i17 > i18) {
                    i17 -= spannableStringBuilder2.mGapLength;
                }
                spannableStringBuilder2.sendSpanAdded(spannableStringBuilder2.mSpans[i14], i16, i17);
            }
        }
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        setSpan(true, obj, i, i2, i3, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setSpan(boolean z, Object obj, int i, int i2, int i3, boolean z2) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        IdentityHashMap<Object, Integer> identityHashMap;
        int iTreeRoot;
        Integer num;
        checkRange("setSpan", i, i2);
        int i9 = (i3 & 240) >> 4;
        if (isInvalidParagraph(i, i9)) {
            if (z2) {
                throw new RuntimeException("PARAGRAPH span must start at paragraph boundary (" + i + " follows " + charAt(i - 1) + NavigationBarInflaterView.KEY_CODE_END);
            }
            return;
        }
        int i10 = i3 & 15;
        if (isInvalidParagraph(i2, i10)) {
            if (z2) {
                throw new RuntimeException("PARAGRAPH span must end at paragraph boundary (" + i2 + " follows " + charAt(i2 - 1) + NavigationBarInflaterView.KEY_CODE_END);
            }
            return;
        }
        if (i9 == 2 && i10 == 1 && i == i2) {
            if (z) {
                Log.e(TAG, "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length");
                return;
            }
            return;
        }
        int i11 = this.mGapStart;
        if (i > i11) {
            i5 = this.mGapLength;
        } else if (i == i11 && (i9 == 2 || (i9 == 3 && i == length()))) {
            i5 = this.mGapLength;
        } else {
            i4 = i;
            i6 = this.mGapStart;
            if (i2 <= i6) {
                i8 = this.mGapLength;
            } else if (i2 == i6 && (i10 == 2 || (i10 == 3 && i2 == length()))) {
                i8 = this.mGapLength;
            } else {
                i7 = i2;
                identityHashMap = this.mIndexOfSpan;
                if (identityHashMap == null && (num = identityHashMap.get(obj)) != null) {
                    int iIntValue = num.intValue();
                    int[] iArr = this.mSpanStarts;
                    int i12 = iArr[iIntValue];
                    int[] iArr2 = this.mSpanEnds;
                    int i13 = iArr2[iIntValue];
                    int i14 = this.mGapStart;
                    if (i12 > i14) {
                        i12 -= this.mGapLength;
                    }
                    if (i13 > i14) {
                        i13 -= this.mGapLength;
                    }
                    iArr[iIntValue] = i4;
                    iArr2[iIntValue] = i7;
                    this.mSpanFlags[iIntValue] = i3;
                    if (z) {
                        restoreInvariants();
                        sendSpanChanged(obj, i12, i13, i, i2);
                        return;
                    }
                    return;
                }
                this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, obj);
                this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, i4);
                this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, i7);
                this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, i3);
                this.mSpanOrder = GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
                invalidateIndex(this.mSpanCount);
                this.mSpanCount++;
                this.mSpanInsertCount++;
                iTreeRoot = (treeRoot() * 2) + 1;
                if (this.mSpanMax.length < iTreeRoot) {
                    this.mSpanMax = new int[iTreeRoot];
                }
                if (z) {
                    restoreInvariants();
                    sendSpanAdded(obj, i, i2);
                    return;
                }
                return;
            }
            i7 = i8 + i2;
            identityHashMap = this.mIndexOfSpan;
            if (identityHashMap == null) {
            }
            this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, obj);
            this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, i4);
            this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, i7);
            this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, i3);
            this.mSpanOrder = GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
            invalidateIndex(this.mSpanCount);
            this.mSpanCount++;
            this.mSpanInsertCount++;
            iTreeRoot = (treeRoot() * 2) + 1;
            if (this.mSpanMax.length < iTreeRoot) {
            }
            if (z) {
            }
        }
        i4 = i5 + i;
        i6 = this.mGapStart;
        if (i2 <= i6) {
        }
        i7 = i8 + i2;
        identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null) {
        }
        this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, obj);
        this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, i4);
        this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, i7);
        this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, i3);
        this.mSpanOrder = GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
        invalidateIndex(this.mSpanCount);
        this.mSpanCount++;
        this.mSpanInsertCount++;
        iTreeRoot = (treeRoot() * 2) + 1;
        if (this.mSpanMax.length < iTreeRoot) {
        }
        if (z) {
        }
    }

    private boolean isInvalidParagraph(int i, int i2) {
        return (i2 != 3 || i == 0 || i == length() || charAt(i - 1) == '\n') ? false : true;
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj) {
        removeSpan(obj, 0);
    }

    @Override // android.text.Spannable
    public void removeSpan(Object obj, int i) {
        Integer numRemove;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (numRemove = identityHashMap.remove(obj)) == null) {
            return;
        }
        removeSpan(numRemove.intValue(), i);
    }

    private int resolveGap(int i) {
        return i > this.mGapStart ? i - this.mGapLength : i;
    }

    @Override // android.text.Spanned
    public int getSpanStart(Object obj) {
        Integer num;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (num = identityHashMap.get(obj)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanStarts[num.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(Object obj) {
        Integer num;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (num = identityHashMap.get(obj)) == null) {
            return -1;
        }
        return resolveGap(this.mSpanEnds[num.intValue()]);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(Object obj) {
        Integer num;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (num = identityHashMap.get(obj)) == null) {
            return 0;
        }
        return this.mSpanFlags[num.intValue()];
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
        int iCountSpans = countSpans(i, i2, cls, treeRoot());
        if (iCountSpans == 0) {
            return (T[]) ArrayUtils.emptyArray(cls);
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, iCountSpans));
        int[] iArrObtain = z ? obtain(iCountSpans) : EmptyArray.INT;
        int[] iArrObtain2 = z ? obtain(iCountSpans) : EmptyArray.INT;
        getSpansRec(i, i2, cls, treeRoot(), tArr, iArrObtain, iArrObtain2, 0, z);
        if (z) {
            sort(tArr, iArrObtain, iArrObtain2);
            recycle(iArrObtain);
            recycle(iArrObtain2);
        }
        return tArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int countSpans(int i, int i2, Class cls, int i3) {
        int iCountSpans;
        int i4 = i3 & 1;
        if (i4 == 0) {
            iCountSpans = 0;
        } else {
            int iLeftChild = leftChild(i3);
            int i5 = this.mSpanMax[iLeftChild];
            if (i5 > this.mGapStart) {
                i5 -= this.mGapLength;
            }
            if (i5 >= i) {
                iCountSpans = countSpans(i, i2, cls, iLeftChild);
            }
        }
        if (i3 >= this.mSpanCount) {
            return iCountSpans;
        }
        int i6 = this.mSpanStarts[i3];
        int i7 = this.mGapStart;
        if (i6 > i7) {
            i6 -= this.mGapLength;
        }
        if (i6 > i2) {
            return iCountSpans;
        }
        int i8 = this.mSpanEnds[i3];
        if (i8 > i7) {
            i8 -= this.mGapLength;
        }
        if (i8 >= i && ((i6 == i8 || i == i2 || (i6 != i2 && i8 != i)) && (Object.class == cls || cls.isInstance(this.mSpans[i3])))) {
            iCountSpans++;
        }
        return i4 != 0 ? iCountSpans + countSpans(i, i2, cls, rightChild(i3)) : iCountSpans;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private <T> int getSpansRec(int i, int i2, Class<T> cls, int i3, T[] tArr, int[] iArr, int[] iArr2, int i4, boolean z) {
        Object[] objArr;
        int spansRec;
        int i5;
        int i6 = i3 & 1;
        if (i6 == 0) {
            objArr = tArr;
            spansRec = i4;
        } else {
            int iLeftChild = leftChild(i3);
            int i7 = this.mSpanMax[iLeftChild];
            if (i7 > this.mGapStart) {
                i7 -= this.mGapLength;
            }
            if (i7 >= i) {
                objArr = tArr;
                spansRec = getSpansRec(i, i2, cls, iLeftChild, objArr, iArr, iArr2, i4, z);
            }
        }
        if (i3 < this.mSpanCount) {
            int i8 = this.mSpanStarts[i3];
            int i9 = this.mGapStart;
            if (i8 > i9) {
                i8 -= this.mGapLength;
            }
            if (i8 <= i2) {
                int i10 = this.mSpanEnds[i3];
                if (i10 > i9) {
                    i10 -= this.mGapLength;
                }
                if (i10 >= i && ((i8 == i10 || i == i2 || (i8 != i2 && i10 != i)) && (Object.class == cls || cls.isInstance(this.mSpans[i3])))) {
                    int i11 = this.mSpanFlags[i3] & Spanned.SPAN_PRIORITY;
                    if (z) {
                        iArr[spansRec] = i11;
                        iArr2[spansRec] = this.mSpanOrder[i3];
                    } else {
                        if (i11 != 0) {
                            i5 = 0;
                            while (i5 < spansRec && i11 <= (getSpanFlags(objArr[i5]) & Spanned.SPAN_PRIORITY)) {
                                i5++;
                            }
                            System.arraycopy(objArr, i5, objArr, i5 + 1, spansRec - i5);
                        }
                        objArr[i5] = this.mSpans[i3];
                        spansRec++;
                    }
                    i5 = spansRec;
                    objArr[i5] = this.mSpans[i3];
                    spansRec++;
                }
                int i12 = spansRec;
                return (i12 >= objArr.length || i6 == 0) ? i12 : getSpansRec(i, i2, cls, rightChild(i3), objArr, iArr, iArr2, i12, z);
            }
        }
        return spansRec;
    }

    private static int[] obtain(int i) {
        int[] iArr;
        int[][] iArr2 = sCachedIntBuffer;
        synchronized (iArr2) {
            int length = iArr2.length - 1;
            int i2 = -1;
            while (true) {
                if (length < 0) {
                    length = i2;
                    break;
                }
                int[] iArr3 = sCachedIntBuffer[length];
                if (iArr3 != null) {
                    if (iArr3.length >= i) {
                        break;
                    }
                    if (i2 == -1) {
                        i2 = length;
                    }
                }
                length--;
            }
            iArr = null;
            if (length != -1) {
                int[][] iArr4 = sCachedIntBuffer;
                int[] iArr5 = iArr4[length];
                iArr4[length] = null;
                iArr = iArr5;
            }
        }
        return checkSortBuffer(iArr, i);
    }

    private static void recycle(int[] iArr) {
        int[][] iArr2;
        synchronized (sCachedIntBuffer) {
            int i = 0;
            while (true) {
                iArr2 = sCachedIntBuffer;
                if (i < iArr2.length) {
                    int[] iArr3 = iArr2[i];
                    if (iArr3 == null || iArr.length > iArr3.length) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    break;
                }
            }
            iArr2[i] = iArr;
        }
    }

    private static int[] checkSortBuffer(int[] iArr, int i) {
        return (iArr == null || i > iArr.length) ? ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(i)) : iArr;
    }

    private final <T> void sort(T[] tArr, int[] iArr, int[] iArr2) {
        int length = tArr.length;
        for (int i = (length / 2) - 1; i >= 0; i--) {
            siftDown(i, tArr, length, iArr, iArr2);
        }
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        for (int i2 = length - 1; i2 > 0; i2--) {
            T t = tArr[0];
            tArr[0] = tArr[i2];
            tArr[i2] = t;
            int i3 = iArr3[0];
            iArr3[0] = iArr3[i2];
            iArr3[i2] = i3;
            int i4 = iArr4[0];
            iArr4[0] = iArr4[i2];
            iArr4[i2] = i4;
            int[] iArr5 = iArr4;
            int[] iArr6 = iArr3;
            siftDown(0, tArr, i2, iArr6, iArr5);
            iArr3 = iArr6;
            iArr4 = iArr5;
        }
    }

    private final <T> void siftDown(int i, T[] tArr, int i2, int[] iArr, int[] iArr2) {
        int i3 = (i * 2) + 1;
        while (i3 < i2) {
            if (i3 < i2 - 1) {
                int i4 = i3 + 1;
                if (compareSpans(i3, i4, iArr, iArr2) < 0) {
                    i3 = i4;
                }
            }
            if (compareSpans(i, i3, iArr, iArr2) >= 0) {
                return;
            }
            T t = tArr[i];
            tArr[i] = tArr[i3];
            tArr[i3] = t;
            int i5 = iArr[i];
            iArr[i] = iArr[i3];
            iArr[i3] = i5;
            int i6 = iArr2[i];
            iArr2[i] = iArr2[i3];
            iArr2[i3] = i6;
            int i7 = i3;
            i3 = (i3 * 2) + 1;
            i = i7;
        }
    }

    private final int compareSpans(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = iArr[i];
        int i4 = iArr[i2];
        if (i3 == i4) {
            return Integer.compare(iArr2[i], iArr2[i2]);
        }
        return Integer.compare(i4, i3);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, Class cls) {
        if (this.mSpanCount == 0) {
            return i2;
        }
        if (cls == null) {
            cls = Object.class;
        }
        return nextSpanTransitionRec(i, i2, cls, treeRoot());
    }

    private int nextSpanTransitionRec(int i, int i2, Class cls, int i3) {
        int i4 = i3 & 1;
        if (i4 != 0) {
            int iLeftChild = leftChild(i3);
            if (resolveGap(this.mSpanMax[iLeftChild]) > i) {
                i2 = nextSpanTransitionRec(i, i2, cls, iLeftChild);
            }
        }
        if (i3 >= this.mSpanCount) {
            return i2;
        }
        int iResolveGap = resolveGap(this.mSpanStarts[i3]);
        int iResolveGap2 = resolveGap(this.mSpanEnds[i3]);
        if (iResolveGap > i && iResolveGap < i2 && cls.isInstance(this.mSpans[i3])) {
            i2 = iResolveGap;
        }
        if (iResolveGap2 <= i || iResolveGap2 >= i2 || !cls.isInstance(this.mSpans[i3])) {
            iResolveGap2 = i2;
        }
        return (iResolveGap >= iResolveGap2 || i4 == 0) ? iResolveGap2 : nextSpanTransitionRec(i, iResolveGap2, cls, rightChild(i3));
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return new SpannableStringBuilder(this, i, i2);
    }

    @Override // android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        checkRange("getChars", i, i2);
        int i4 = this.mGapStart;
        if (i2 <= i4) {
            System.arraycopy(this.mText, i, cArr, i3, i2 - i);
            return;
        }
        if (i >= i4) {
            System.arraycopy(this.mText, this.mGapLength + i, cArr, i3, i2 - i);
            return;
        }
        System.arraycopy(this.mText, i, cArr, i3, i4 - i);
        char[] cArr2 = this.mText;
        int i5 = this.mGapStart;
        System.arraycopy(cArr2, this.mGapLength + i5, cArr, i3 + (i5 - i), i2 - i5);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        int length = length();
        char[] cArr = new char[length];
        getChars(0, length, cArr, 0);
        return new String(cArr);
    }

    public String substring(int i, int i2) {
        char[] cArr = new char[i2 - i];
        getChars(i, i2, cArr, 0);
        return new String(cArr);
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(TextWatcher[] textWatcherArr, int i, int i2, int i3) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : textWatcherArr) {
            textWatcher.beforeTextChanged(this, i, i2, i3);
        }
        this.mTextWatcherDepth--;
    }

    private void sendTextChanged(TextWatcher[] textWatcherArr, int i, int i2, int i3) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : textWatcherArr) {
            textWatcher.onTextChanged(this, i, i2, i3);
        }
        this.mTextWatcherDepth--;
    }

    private void sendAfterTextChanged(TextWatcher[] textWatcherArr) {
        this.mTextWatcherDepth++;
        for (TextWatcher textWatcher : textWatcherArr) {
            textWatcher.afterTextChanged(this);
        }
        this.mTextWatcherDepth--;
    }

    private void sendSpanAdded(Object obj, int i, int i2) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(i, i2, SpanWatcher.class)) {
            spanWatcher.onSpanAdded(this, obj, i, i2);
        }
    }

    private void sendSpanRemoved(Object obj, int i, int i2) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(i, i2, SpanWatcher.class)) {
            spanWatcher.onSpanRemoved(this, obj, i, i2);
        }
    }

    private void sendSpanChanged(Object obj, int i, int i2, int i3, int i4) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(Math.min(i, i3), Math.min(Math.max(i2, i4), length()), SpanWatcher.class)) {
            spanWatcher.onSpanChanged(this, obj, i, i2, i3, i4);
        }
    }

    private static String region(int i, int i2) {
        return NavigationBarInflaterView.KEY_CODE_START + i + " ... " + i2 + NavigationBarInflaterView.KEY_CODE_END;
    }

    private void checkRange(String str, int i, int i2) {
        if (i2 < i) {
            throw new IndexOutOfBoundsException(str + " " + region(i, i2) + " has end before start");
        }
        int length = length();
        if (i > length || i2 > length) {
            throw new IndexOutOfBoundsException(str + " " + region(i, i2) + " ends beyond length " + length);
        }
        if (i < 0 || i2 < 0) {
            throw new IndexOutOfBoundsException(str + " " + region(i, i2) + " starts before 0");
        }
    }

    @Override // android.text.GraphicsOperations
    public void drawText(BaseCanvas baseCanvas, int i, int i2, float f, float f2, Paint paint) {
        checkRange("drawText", i, i2);
        int i3 = this.mGapStart;
        if (i2 <= i3) {
            baseCanvas.drawText(this.mText, i, i2 - i, f, f2, paint);
            return;
        }
        if (i >= i3) {
            baseCanvas.drawText(this.mText, i + this.mGapLength, i2 - i, f, f2, paint);
            return;
        }
        int i4 = i2 - i;
        char[] cArrObtain = TextUtils.obtain(i4);
        getChars(i, i2, cArrObtain, 0);
        baseCanvas.drawText(cArrObtain, 0, i4, f, f2, paint);
        TextUtils.recycle(cArrObtain);
    }

    @Override // android.text.GraphicsOperations
    public void drawTextRun(BaseCanvas baseCanvas, int i, int i2, int i3, int i4, float f, float f2, boolean z, Paint paint) {
        checkRange("drawTextRun", i, i2);
        int i5 = i4 - i3;
        int i6 = i2 - i;
        int i7 = this.mGapStart;
        if (i4 <= i7) {
            baseCanvas.drawTextRun(this.mText, i, i6, i3, i5, f, f2, z, paint);
            return;
        }
        if (i3 >= i7) {
            char[] cArr = this.mText;
            int i8 = this.mGapLength;
            baseCanvas.drawTextRun(cArr, i + i8, i6, i3 + i8, i5, f, f2, z, paint);
        } else {
            char[] cArrObtain = TextUtils.obtain(i5);
            getChars(i3, i4, cArrObtain, 0);
            baseCanvas.drawTextRun(cArrObtain, i - i3, i6, 0, i5, f, f2, z, paint);
            TextUtils.recycle(cArrObtain);
        }
    }

    @Override // android.text.GraphicsOperations
    public float measureText(int i, int i2, Paint paint) {
        checkRange("measureText", i, i2);
        int i3 = this.mGapStart;
        if (i2 <= i3) {
            return paint.measureText(this.mText, i, i2 - i);
        }
        if (i >= i3) {
            return paint.measureText(this.mText, this.mGapLength + i, i2 - i);
        }
        int i4 = i2 - i;
        char[] cArrObtain = TextUtils.obtain(i4);
        getChars(i, i2, cArrObtain, 0);
        float fMeasureText = paint.measureText(cArrObtain, 0, i4);
        TextUtils.recycle(cArrObtain);
        return fMeasureText;
    }

    @Override // android.text.GraphicsOperations
    public int getTextWidths(int i, int i2, float[] fArr, Paint paint) {
        checkRange("getTextWidths", i, i2);
        int i3 = this.mGapStart;
        if (i2 <= i3) {
            return paint.getTextWidths(this.mText, i, i2 - i, fArr);
        }
        if (i >= i3) {
            return paint.getTextWidths(this.mText, this.mGapLength + i, i2 - i, fArr);
        }
        int i4 = i2 - i;
        char[] cArrObtain = TextUtils.obtain(i4);
        getChars(i, i2, cArrObtain, 0);
        int textWidths = paint.getTextWidths(cArrObtain, 0, i4, fArr);
        TextUtils.recycle(cArrObtain);
        return textWidths;
    }

    @Override // android.text.GraphicsOperations
    public float getTextRunAdvances(int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, Paint paint) {
        int i6 = i4 - i3;
        int i7 = i2 - i;
        int i8 = this.mGapStart;
        if (i2 <= i8) {
            return paint.getTextRunAdvances(this.mText, i, i7, i3, i6, z, fArr, i5);
        }
        if (i >= i8) {
            char[] cArr = this.mText;
            int i9 = this.mGapLength;
            return paint.getTextRunAdvances(cArr, i + i9, i7, i3 + i9, i6, z, fArr, i5);
        }
        char[] cArrObtain = TextUtils.obtain(i6);
        getChars(i3, i4, cArrObtain, 0);
        float textRunAdvances = paint.getTextRunAdvances(cArrObtain, i - i3, i7, 0, i6, z, fArr, i5);
        TextUtils.recycle(cArrObtain);
        return textRunAdvances;
    }

    @Deprecated
    public int getTextRunCursor(int i, int i2, int i3, int i4, int i5, Paint paint) {
        return getTextRunCursor(i, i2, i3 == 1, i4, i5, paint);
    }

    @Override // android.text.GraphicsOperations
    public int getTextRunCursor(int i, int i2, boolean z, int i3, int i4, Paint paint) {
        int i5 = i2 - i;
        int i6 = this.mGapStart;
        if (i2 <= i6) {
            return paint.getTextRunCursor(this.mText, i, i5, z, i3, i4);
        }
        if (i >= i6) {
            char[] cArr = this.mText;
            int i7 = this.mGapLength;
            return paint.getTextRunCursor(cArr, i + i7, i5, z, i3 + i7, i4) - this.mGapLength;
        }
        char[] cArrObtain = TextUtils.obtain(i5);
        getChars(i, i2, cArrObtain, 0);
        int textRunCursor = paint.getTextRunCursor(cArrObtain, 0, i5, z, i3 - i, i4) + i;
        TextUtils.recycle(cArrObtain);
        return textRunCursor;
    }

    @Override // android.text.Editable
    public void setFilters(InputFilter[] inputFilterArr) {
        if (inputFilterArr == null) {
            throw new IllegalArgumentException();
        }
        this.mFilters = inputFilterArr;
    }

    @Override // android.text.Editable
    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof Spanned) && toString().equals(obj.toString())) {
            Spanned spanned = (Spanned) obj;
            Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
            Object[] spans2 = getSpans(0, length(), Object.class);
            if (this.mSpanCount == spans.length) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    Object obj2 = spans2[i];
                    Object obj3 = spans[i];
                    if (obj2 == this) {
                        if (spanned != obj3 || getSpanStart(obj2) != spanned.getSpanStart(obj3) || getSpanEnd(obj2) != spanned.getSpanEnd(obj3) || getSpanFlags(obj2) != spanned.getSpanFlags(obj3)) {
                            return false;
                        }
                    } else if (!obj2.equals(obj3) || getSpanStart(obj2) != spanned.getSpanStart(obj3) || getSpanEnd(obj2) != spanned.getSpanEnd(obj3) || getSpanFlags(obj2) != spanned.getSpanFlags(obj3)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (toString().hashCode() * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object obj = this.mSpans[i];
            if (obj != this) {
                iHashCode = (iHashCode * 31) + obj.hashCode();
            }
            iHashCode = (((((iHashCode * 31) + getSpanStart(obj)) * 31) + getSpanEnd(obj)) * 31) + getSpanFlags(obj);
        }
        return iHashCode;
    }

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private int calcMax(int i) {
        int i2 = i & 1;
        int iCalcMax = i2 != 0 ? calcMax(leftChild(i)) : 0;
        if (i < this.mSpanCount) {
            iCalcMax = Math.max(iCalcMax, this.mSpanEnds[i]);
            if (i2 != 0) {
                iCalcMax = Math.max(iCalcMax, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = iCalcMax;
        return iCalcMax;
    }

    private void restoreInvariants() {
        Object[] objArr;
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int i;
        if (this.mSpanCount == 0) {
            return;
        }
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int[] iArr5 = this.mSpanStarts;
            int i3 = iArr5[i2];
            if (i3 < iArr5[i2 - 1]) {
                Object obj = this.mSpans[i2];
                int i4 = this.mSpanEnds[i2];
                int i5 = this.mSpanFlags[i2];
                int i6 = this.mSpanOrder[i2];
                int i7 = i2;
                while (true) {
                    objArr = this.mSpans;
                    int i8 = i7 - 1;
                    objArr[i7] = objArr[i8];
                    iArr = this.mSpanStarts;
                    iArr[i7] = iArr[i8];
                    iArr2 = this.mSpanEnds;
                    iArr2[i7] = iArr2[i8];
                    iArr3 = this.mSpanFlags;
                    iArr3[i7] = iArr3[i8];
                    iArr4 = this.mSpanOrder;
                    iArr4[i7] = iArr4[i8];
                    i = i7 - 1;
                    if (i <= 0 || i3 >= iArr[i7 - 2]) {
                        break;
                    } else {
                        i7 = i;
                    }
                }
                objArr[i] = obj;
                iArr[i] = i3;
                iArr2[i] = i4;
                iArr3[i] = i5;
                iArr4[i] = i6;
                invalidateIndex(i);
            }
        }
        calcMax(treeRoot());
        if (this.mIndexOfSpan == null) {
            this.mIndexOfSpan = new IdentityHashMap<>();
        }
        for (int i9 = this.mLowWaterMark; i9 < this.mSpanCount; i9++) {
            Integer num = this.mIndexOfSpan.get(this.mSpans[i9]);
            if (num == null || num.intValue() != i9) {
                this.mIndexOfSpan.put(this.mSpans[i9], Integer.valueOf(i9));
            }
        }
        this.mLowWaterMark = Integer.MAX_VALUE;
    }

    private void invalidateIndex(int i) {
        this.mLowWaterMark = Math.min(i, this.mLowWaterMark);
    }
}
