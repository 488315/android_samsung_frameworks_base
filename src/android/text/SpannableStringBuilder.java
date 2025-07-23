package android.text;

import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
        char[] newUnpaddedCharArray = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(i3));
        this.mText = newUnpaddedCharArray;
        this.mGapStart = i3;
        this.mGapLength = newUnpaddedCharArray.length - i3;
        TextUtils.getChars(charSequence, i, i2, newUnpaddedCharArray, 0);
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
        char[] newUnpaddedCharArray = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(i));
        System.arraycopy(this.mText, 0, newUnpaddedCharArray, 0, this.mGapStart);
        int length2 = newUnpaddedCharArray.length;
        int i2 = length2 - length;
        int i3 = length - (this.mGapStart + this.mGapLength);
        System.arraycopy(this.mText, length - i3, newUnpaddedCharArray, length2 - i3, i3);
        this.mText = newUnpaddedCharArray;
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void moveGapTo(int r11) {
        /*
            r10 = this;
            int r0 = r10.mGapStart
            if (r11 != r0) goto L5
            return
        L5:
            int r0 = r10.length()
            r1 = 0
            if (r11 != r0) goto Le
            r0 = 1
            goto Lf
        Le:
            r0 = r1
        Lf:
            int r2 = r10.mGapStart
            if (r11 >= r2) goto L1f
            int r3 = r2 - r11
            char[] r4 = r10.mText
            int r5 = r10.mGapLength
            int r2 = r2 + r5
            int r2 = r2 - r3
            java.lang.System.arraycopy(r4, r11, r4, r2, r3)
            goto L2a
        L1f:
            int r3 = r11 - r2
            char[] r4 = r10.mText
            int r5 = r10.mGapLength
            int r5 = r5 + r11
            int r5 = r5 - r3
            java.lang.System.arraycopy(r4, r5, r4, r2, r3)
        L2a:
            int r2 = r10.mSpanCount
            if (r2 == 0) goto L86
        L2e:
            int r2 = r10.mSpanCount
            if (r1 >= r2) goto L7f
            int[] r2 = r10.mSpanStarts
            r3 = r2[r1]
            int[] r4 = r10.mSpanEnds
            r5 = r4[r1]
            int r6 = r10.mGapStart
            if (r3 <= r6) goto L41
            int r7 = r10.mGapLength
            int r3 = r3 - r7
        L41:
            r7 = 3
            r8 = 2
            if (r3 <= r11) goto L49
            int r9 = r10.mGapLength
        L47:
            int r3 = r3 + r9
            goto L5c
        L49:
            if (r3 != r11) goto L5c
            int[] r9 = r10.mSpanFlags
            r9 = r9[r1]
            r9 = r9 & 240(0xf0, float:3.36E-43)
            int r9 = r9 >> 4
            if (r9 == r8) goto L59
            if (r0 == 0) goto L5c
            if (r9 != r7) goto L5c
        L59:
            int r9 = r10.mGapLength
            goto L47
        L5c:
            if (r5 <= r6) goto L61
            int r6 = r10.mGapLength
            int r5 = r5 - r6
        L61:
            if (r5 <= r11) goto L67
            int r6 = r10.mGapLength
        L65:
            int r5 = r5 + r6
            goto L78
        L67:
            if (r5 != r11) goto L78
            int[] r6 = r10.mSpanFlags
            r6 = r6[r1]
            r6 = r6 & 15
            if (r6 == r8) goto L75
            if (r0 == 0) goto L78
            if (r6 != r7) goto L78
        L75:
            int r6 = r10.mGapLength
            goto L65
        L78:
            r2[r1] = r3
            r4[r1] = r5
            int r1 = r1 + 1
            goto L2e
        L7f:
            int r0 = r10.treeRoot()
            r10.calcMax(r0)
        L86:
            r10.mGapStart = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.moveGapTo(int):void");
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
        SpannableStringBuilder replace = replace(i, i2, "", 0, 0);
        if (this.mGapLength > length() * 2) {
            resizeFor(length());
        }
        return replace;
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
        int i7;
        SpannableStringBuilder spannableStringBuilder;
        SpannableStringBuilder spannableStringBuilder2 = this;
        int i8 = i;
        int i9 = i2;
        spannableStringBuilder2.checkRange("replace", i8, i9);
        int length = spannableStringBuilder2.mFilters.length;
        boolean z = false;
        CharSequence charSequence3 = charSequence;
        int i10 = i3;
        int i11 = i4;
        int i12 = 0;
        while (i12 < length) {
            int i13 = i10;
            SpannableStringBuilder spannableStringBuilder3 = spannableStringBuilder2;
            int i14 = i9;
            int i15 = i13;
            int i16 = i11;
            int i17 = i8;
            CharSequence charSequence4 = charSequence3;
            CharSequence filter = spannableStringBuilder2.mFilters[i12].filter(charSequence4, i15, i16, spannableStringBuilder3, i17, i14);
            i8 = i17;
            if (filter != null) {
                charSequence3 = filter;
                i11 = filter.length();
                i15 = 0;
            } else {
                i11 = i16;
                charSequence3 = charSequence4;
            }
            i12++;
            spannableStringBuilder2 = spannableStringBuilder3;
            i10 = i15;
            i9 = i2;
        }
        int i18 = i11;
        CharSequence charSequence5 = charSequence3;
        int i19 = i10;
        SpannableStringBuilder spannableStringBuilder4 = spannableStringBuilder2;
        int i20 = i2 - i8;
        int i21 = i18 - i19;
        if (i20 == 0 && i21 == 0 && !hasNonExclusiveExclusiveSpanAt(charSequence5, i19)) {
            return spannableStringBuilder4;
        }
        TextWatcher[] textWatcherArr = (TextWatcher[]) spannableStringBuilder4.getSpans(i8, i8 + i20, TextWatcher.class);
        spannableStringBuilder4.sendBeforeTextChanged(textWatcherArr, i8, i20, i21);
        boolean z2 = true;
        boolean z3 = (i20 == 0 || i21 == 0) ? false : true;
        if (z3) {
            int selectionStart = Selection.getSelectionStart(spannableStringBuilder4);
            i5 = i18;
            charSequence2 = charSequence5;
            i7 = Selection.getSelectionEnd(spannableStringBuilder4);
            i6 = selectionStart;
        } else {
            i5 = i18;
            charSequence2 = charSequence5;
            i6 = 0;
            i7 = 0;
        }
        spannableStringBuilder4.change(i8, i2, charSequence2, i19, i5);
        int i22 = i8;
        if (z3) {
            if (i6 > i22 && i6 < i2) {
                int intExact = i22 + Math.toIntExact(((i6 - i22) * i21) / i20);
                setSpan(false, Selection.SELECTION_START, intExact, intExact, 34, true);
                z = true;
            }
            if (i7 <= i22 || i7 >= i2) {
                spannableStringBuilder = this;
                z2 = z;
            } else {
                int intExact2 = i22 + Math.toIntExact(((i7 - i22) * i21) / i20);
                spannableStringBuilder = this;
                spannableStringBuilder.setSpan(false, Selection.SELECTION_END, intExact2, intExact2, 34, true);
            }
            if (z2) {
                spannableStringBuilder.restoreInvariants();
            }
        } else {
            spannableStringBuilder = this;
        }
        spannableStringBuilder.sendTextChanged(textWatcherArr, i22, i20, i21);
        spannableStringBuilder.sendAfterTextChanged(textWatcherArr);
        spannableStringBuilder.sendToSpanWatchers(i22, i2, i21 - i20);
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

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0059, code lost:
    
        if ((r2 & 16384) != 16384) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0061, code lost:
    
        if ((r2 & 32768) != 32768) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void sendToSpanWatchers(int r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 176
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.sendToSpanWatchers(int, int, int):void");
    }

    @Override // android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        setSpan(true, obj, i, i2, i3, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setSpan(boolean r10, java.lang.Object r11, int r12, int r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.setSpan(boolean, java.lang.Object, int, int, int, boolean):void");
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
        Integer remove;
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null || (remove = identityHashMap.remove(obj)) == null) {
            return;
        }
        removeSpan(remove.intValue(), i);
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int countSpans(int r6, int r7, java.lang.Class r8, int r9) {
        /*
            r5 = this;
            r0 = r9 & 1
            if (r0 == 0) goto L1a
            int r1 = leftChild(r9)
            int[] r2 = r5.mSpanMax
            r2 = r2[r1]
            int r3 = r5.mGapStart
            if (r2 <= r3) goto L13
            int r3 = r5.mGapLength
            int r2 = r2 - r3
        L13:
            if (r2 < r6) goto L1a
            int r1 = r5.countSpans(r6, r7, r8, r1)
            goto L1b
        L1a:
            r1 = 0
        L1b:
            int r2 = r5.mSpanCount
            if (r9 >= r2) goto L5a
            int[] r2 = r5.mSpanStarts
            r2 = r2[r9]
            int r3 = r5.mGapStart
            if (r2 <= r3) goto L2a
            int r4 = r5.mGapLength
            int r2 = r2 - r4
        L2a:
            if (r2 > r7) goto L5a
            int[] r4 = r5.mSpanEnds
            r4 = r4[r9]
            if (r4 <= r3) goto L35
            int r3 = r5.mGapLength
            int r4 = r4 - r3
        L35:
            if (r4 < r6) goto L4f
            if (r2 == r4) goto L3f
            if (r6 == r7) goto L3f
            if (r2 == r7) goto L4f
            if (r4 == r6) goto L4f
        L3f:
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r2 == r8) goto L4d
            java.lang.Object[] r2 = r5.mSpans
            r2 = r2[r9]
            boolean r2 = r8.isInstance(r2)
            if (r2 == 0) goto L4f
        L4d:
            int r1 = r1 + 1
        L4f:
            if (r0 == 0) goto L5a
            int r9 = rightChild(r9)
            int r5 = r5.countSpans(r6, r7, r8, r9)
            int r1 = r1 + r5
        L5a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.countSpans(int, int, java.lang.Class, int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T> int getSpansRec(int r13, int r14, java.lang.Class<T> r15, int r16, T[] r17, int[] r18, int[] r19, int r20, boolean r21) {
        /*
            r12 = this;
            r0 = r16
            r11 = r0 & 1
            if (r11 == 0) goto L2a
            int r5 = leftChild(r0)
            int[] r1 = r12.mSpanMax
            r1 = r1[r5]
            int r2 = r12.mGapStart
            if (r1 <= r2) goto L15
            int r2 = r12.mGapLength
            int r1 = r1 - r2
        L15:
            if (r1 < r13) goto L2a
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r6 = r17
            r7 = r18
            r8 = r19
            r9 = r20
            r10 = r21
            int r5 = r1.getSpansRec(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            goto L2e
        L2a:
            r6 = r17
            r5 = r20
        L2e:
            int r7 = r12.mSpanCount
            if (r0 < r7) goto L34
            goto Lb3
        L34:
            int[] r7 = r12.mSpanStarts
            r7 = r7[r0]
            int r8 = r12.mGapStart
            if (r7 <= r8) goto L3f
            int r9 = r12.mGapLength
            int r7 = r7 - r9
        L3f:
            if (r7 > r14) goto Lb3
            int[] r9 = r12.mSpanEnds
            r9 = r9[r0]
            if (r9 <= r8) goto L4a
            int r8 = r12.mGapLength
            int r9 = r9 - r8
        L4a:
            if (r9 < r13) goto L97
            if (r7 == r9) goto L54
            if (r13 == r14) goto L54
            if (r7 == r14) goto L97
            if (r9 == r13) goto L97
        L54:
            java.lang.Class<java.lang.Object> r7 = java.lang.Object.class
            if (r7 == r15) goto L62
            java.lang.Object[] r7 = r12.mSpans
            r7 = r7[r0]
            boolean r7 = r15.isInstance(r7)
            if (r7 == 0) goto L97
        L62:
            int[] r7 = r12.mSpanFlags
            r7 = r7[r0]
            r8 = 16711680(0xff0000, float:2.3418052E-38)
            r7 = r7 & r8
            if (r21 == 0) goto L74
            r18[r5] = r7
            int[] r7 = r12.mSpanOrder
            r7 = r7[r0]
            r19[r5] = r7
            goto L8e
        L74:
            if (r7 == 0) goto L8e
            r9 = 0
        L77:
            if (r9 >= r5) goto L86
            r10 = r6[r9]
            int r10 = r12.getSpanFlags(r10)
            r10 = r10 & r8
            if (r7 <= r10) goto L83
            goto L86
        L83:
            int r9 = r9 + 1
            goto L77
        L86:
            int r7 = r9 + 1
            int r8 = r5 - r9
            java.lang.System.arraycopy(r6, r9, r6, r7, r8)
            goto L8f
        L8e:
            r9 = r5
        L8f:
            java.lang.Object[] r7 = r12.mSpans
            r7 = r7[r0]
            r6[r9] = r7
            int r5 = r5 + 1
        L97:
            r8 = r5
            int r5 = r6.length
            if (r8 >= r5) goto Lb2
            if (r11 == 0) goto Lb2
            int r0 = rightChild(r0)
            r1 = r13
            r2 = r14
            r3 = r15
            r7 = r19
            r9 = r21
            r4 = r0
            r5 = r6
            r0 = r12
            r6 = r18
            int r12 = r0.getSpansRec(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            return r12
        Lb2:
            return r8
        Lb3:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.SpannableStringBuilder.getSpansRec(int, int, java.lang.Class, int, java.lang.Object[], int[], int[], int, boolean):int");
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
            int leftChild = leftChild(i3);
            if (resolveGap(this.mSpanMax[leftChild]) > i) {
                i2 = nextSpanTransitionRec(i, i2, cls, leftChild);
            }
        }
        if (i3 >= this.mSpanCount) {
            return i2;
        }
        int resolveGap = resolveGap(this.mSpanStarts[i3]);
        int resolveGap2 = resolveGap(this.mSpanEnds[i3]);
        if (resolveGap > i && resolveGap < i2 && cls.isInstance(this.mSpans[i3])) {
            i2 = resolveGap;
        }
        if (resolveGap2 <= i || resolveGap2 >= i2 || !cls.isInstance(this.mSpans[i3])) {
            resolveGap2 = i2;
        }
        return (resolveGap >= resolveGap2 || i4 == 0) ? resolveGap2 : nextSpanTransitionRec(i, resolveGap2, cls, rightChild(i3));
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
        char[] obtain = TextUtils.obtain(i4);
        getChars(i, i2, obtain, 0);
        baseCanvas.drawText(obtain, 0, i4, f, f2, paint);
        TextUtils.recycle(obtain);
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
            char[] obtain = TextUtils.obtain(i5);
            getChars(i3, i4, obtain, 0);
            baseCanvas.drawTextRun(obtain, i - i3, i6, 0, i5, f, f2, z, paint);
            TextUtils.recycle(obtain);
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
        char[] obtain = TextUtils.obtain(i4);
        getChars(i, i2, obtain, 0);
        float measureText = paint.measureText(obtain, 0, i4);
        TextUtils.recycle(obtain);
        return measureText;
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
        char[] obtain = TextUtils.obtain(i4);
        getChars(i, i2, obtain, 0);
        int textWidths = paint.getTextWidths(obtain, 0, i4, fArr);
        TextUtils.recycle(obtain);
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
        char[] obtain = TextUtils.obtain(i6);
        getChars(i3, i4, obtain, 0);
        float textRunAdvances = paint.getTextRunAdvances(obtain, i - i3, i7, 0, i6, z, fArr, i5);
        TextUtils.recycle(obtain);
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
        char[] obtain = TextUtils.obtain(i5);
        getChars(i, i2, obtain, 0);
        int textRunCursor = paint.getTextRunCursor(obtain, 0, i5, z, i3 - i, i4) + i;
        TextUtils.recycle(obtain);
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
        int hashCode = (toString().hashCode() * 31) + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; i++) {
            Object obj = this.mSpans[i];
            if (obj != this) {
                hashCode = (hashCode * 31) + obj.hashCode();
            }
            hashCode = (((((hashCode * 31) + getSpanStart(obj)) * 31) + getSpanEnd(obj)) * 31) + getSpanFlags(obj);
        }
        return hashCode;
    }

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private int calcMax(int i) {
        int i2 = i & 1;
        int calcMax = i2 != 0 ? calcMax(leftChild(i)) : 0;
        if (i < this.mSpanCount) {
            calcMax = Math.max(calcMax, this.mSpanEnds[i]);
            if (i2 != 0) {
                calcMax = Math.max(calcMax, calcMax(rightChild(i)));
            }
        }
        this.mSpanMax[i] = calcMax;
        return calcMax;
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
