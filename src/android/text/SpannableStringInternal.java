package android.text;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.reflect.Array;
import libcore.util.EmptyArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
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

    private final boolean isOutOfCopyRange(int i, int i2, int i3, int i4) {
        if (i3 > i2 || i4 < i) {
            return true;
        }
        return (i3 == i4 || i == i2 || (i3 != i2 && i4 != i)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    SpannableStringInternal(CharSequence charSequence, int i, int i2, boolean z) {
        if (i == 0 && i2 == charSequence.length()) {
            this.mText = charSequence.toString();
        } else {
            this.mText = charSequence.toString().substring(i, i2);
        }
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanData = EmptyArray.INT;
        if (charSequence instanceof Spanned) {
            if (charSequence instanceof SpannableStringInternal) {
                copySpansFromInternal((SpannableStringInternal) charSequence, i, i2, z);
            } else {
                copySpansFromSpanned((Spanned) charSequence, i, i2, z);
            }
        }
    }

    SpannableStringInternal(CharSequence charSequence, int i, int i2) {
        this(charSequence, i, i2, false);
    }

    private void copySpansFromSpanned(Spanned spanned, int i, int i2, boolean z) {
        SpannableStringInternal spannableStringInternal;
        Object[] spans = spanned.getSpans(i, i2, Object.class);
        int i3 = 0;
        while (i3 < spans.length) {
            if (z && (spans[i3] instanceof NoCopySpan)) {
                spannableStringInternal = this;
            } else {
                int spanStart = spanned.getSpanStart(spans[i3]);
                int spanEnd = spanned.getSpanEnd(spans[i3]);
                int spanFlags = spanned.getSpanFlags(spans[i3]);
                if (spanStart < i) {
                    spanStart = i;
                }
                if (spanEnd > i2) {
                    spanEnd = i2;
                }
                spannableStringInternal = this;
                spannableStringInternal.setSpan(spans[i3], spanStart - i, spanEnd - i, spanFlags, false);
            }
            i3++;
            this = spannableStringInternal;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0025 A[PHI: r6
      0x0025: PHI (r6v8 boolean) = (r6v1 boolean), (r6v9 boolean) binds: [B:8:0x001f, B:10:0x0022] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void copySpansFromInternal(SpannableStringInternal spannableStringInternal, int i, int i2, boolean z) {
        int[] iArr = spannableStringInternal.mSpanData;
        Object[] objArr = spannableStringInternal.mSpans;
        int i3 = spannableStringInternal.mSpanCount;
        int i4 = 0;
        boolean z2 = false;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i5 * 3;
            if (!isOutOfCopyRange(i, i2, iArr[i6], iArr[i6 + 1])) {
                if (objArr[i5] instanceof NoCopySpan) {
                    z2 = true;
                    if (!z) {
                        i4++;
                    }
                }
            }
        }
        if (i4 == 0) {
            return;
        }
        if (!z2 && i == 0 && i2 == spannableStringInternal.length()) {
            Object[] objArrNewUnpaddedObjectArray = ArrayUtils.newUnpaddedObjectArray(spannableStringInternal.mSpans.length);
            this.mSpans = objArrNewUnpaddedObjectArray;
            this.mSpanData = new int[spannableStringInternal.mSpanData.length];
            this.mSpanCount = spannableStringInternal.mSpanCount;
            Object[] objArr2 = spannableStringInternal.mSpans;
            System.arraycopy(objArr2, 0, objArrNewUnpaddedObjectArray, 0, objArr2.length);
            int[] iArr2 = spannableStringInternal.mSpanData;
            int[] iArr3 = this.mSpanData;
            System.arraycopy(iArr2, 0, iArr3, 0, iArr3.length);
            return;
        }
        this.mSpanCount = i4;
        Object[] objArrNewUnpaddedObjectArray2 = ArrayUtils.newUnpaddedObjectArray(i4);
        this.mSpans = objArrNewUnpaddedObjectArray2;
        this.mSpanData = new int[objArrNewUnpaddedObjectArray2.length * 3];
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            int i9 = i8 * 3;
            int i10 = iArr[i9];
            int i11 = iArr[i9 + 1];
            if (!isOutOfCopyRange(i, i2, i10, i11) && (!z || !(objArr[i8] instanceof NoCopySpan))) {
                if (i10 < i) {
                    i10 = i;
                }
                if (i11 > i2) {
                    i11 = i2;
                }
                this.mSpans[i7] = objArr[i8];
                int[] iArr4 = this.mSpanData;
                int i12 = i7 * 3;
                iArr4[i12] = i10 - i;
                iArr4[i12 + 1] = i11 - i;
                iArr4[i12 + 2] = iArr[i9 + 2];
                i7++;
            }
        }
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

    public final void getChars(int i, int i2, char[] cArr, int i3) {
        this.mText.getChars(i, i2, cArr, i3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSpan(Object obj, int i, int i2, int i3) {
        setSpan(obj, i, i2, i3, true);
    }

    private boolean isIndexFollowsNextLine(int i) {
        return (i == 0 || i == length() || charAt(i - 1) == '\n') ? false : true;
    }

    private void setSpan(Object obj, int i, int i2, int i3, boolean z) {
        checkRange("setSpan", i, i2);
        if ((i3 & 51) == 51) {
            if (isIndexFollowsNextLine(i)) {
                if (z) {
                    throw new RuntimeException("PARAGRAPH span must start at paragraph boundary (" + i + " follows " + charAt(i - 1) + NavigationBarInflaterView.KEY_CODE_END);
                }
                return;
            }
            if (isIndexFollowsNextLine(i2)) {
                if (z) {
                    throw new RuntimeException("PARAGRAPH span must end at paragraph boundary (" + i2 + " follows " + charAt(i2 - 1) + NavigationBarInflaterView.KEY_CODE_END);
                }
                return;
            }
        }
        int i4 = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        int i5 = 0;
        while (i5 < i4) {
            if (objArr[i5] == obj) {
                int i6 = i5 * 3;
                int i7 = i3;
                int i8 = i;
                int i9 = iArr[i6];
                int i10 = i6 + 1;
                int i11 = iArr[i10];
                iArr[i6] = i8;
                iArr[i10] = i2;
                iArr[i6 + 2] = i7;
                sendSpanChanged(obj, i9, i11, i8, i2);
                return;
            }
            i5++;
            i = i;
            i3 = i3;
        }
        int i12 = i3;
        int i13 = i;
        int i14 = this.mSpanCount;
        if (i14 + 1 >= this.mSpans.length) {
            Object[] objArrNewUnpaddedObjectArray = ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(i14));
            int[] iArr2 = new int[objArrNewUnpaddedObjectArray.length * 3];
            System.arraycopy(this.mSpans, 0, objArrNewUnpaddedObjectArray, 0, this.mSpanCount);
            System.arraycopy(this.mSpanData, 0, iArr2, 0, this.mSpanCount * 3);
            this.mSpans = objArrNewUnpaddedObjectArray;
            this.mSpanData = iArr2;
        }
        Object[] objArr2 = this.mSpans;
        int i15 = this.mSpanCount;
        objArr2[i15] = obj;
        int[] iArr3 = this.mSpanData;
        iArr3[i15 * 3] = i13;
        iArr3[(i15 * 3) + 1] = i2;
        iArr3[(i15 * 3) + 2] = i12;
        this.mSpanCount = i15 + 1;
        if (this instanceof Spannable) {
            sendSpanAdded(obj, i13, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeSpan(Object obj) {
        removeSpan(obj, 0);
    }

    public void removeSpan(Object obj, int i) {
        int i2 = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            if (objArr[i3] == obj) {
                int i4 = i3 * 3;
                int i5 = iArr[i4];
                int i6 = iArr[i4 + 1];
                int i7 = i3 + 1;
                int i8 = i2 - i7;
                System.arraycopy(objArr, i7, objArr, i3, i8);
                System.arraycopy(iArr, i7 * 3, iArr, i4, i8 * 3);
                this.mSpanCount--;
                if ((i & 512) == 0) {
                    sendSpanRemoved(obj, i5, i6);
                    return;
                }
                return;
            }
        }
    }

    public int getSpanStart(Object obj) {
        int i = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[i2 * 3];
            }
        }
        return -1;
    }

    public int getSpanEnd(Object obj) {
        int i = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[(i2 * 3) + 1];
            }
        }
        return -1;
    }

    public int getSpanFlags(Object obj) {
        int i = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (objArr[i2] == obj) {
                return iArr[(i2 * 3) + 2];
            }
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T[] getSpans(int i, int i2, Class<T> cls) {
        int i3 = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        Object[] objArr2 = (T[]) null;
        int i4 = 0;
        Object obj = null;
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = i5 * 3;
            int i7 = iArr[i6];
            int i8 = iArr[i6 + 1];
            if (i7 <= i2 && i8 >= i && ((i7 == i8 || i == i2 || (i7 != i2 && i8 != i)) && (cls == null || cls == Object.class || cls.isInstance(objArr[i5])))) {
                if (i4 == 0) {
                    obj = objArr[i5];
                } else {
                    if (i4 == 1) {
                        objArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, (i3 - i5) + 1));
                        objArr2[0] = obj;
                    }
                    int i9 = iArr[i6 + 2] & Spanned.SPAN_PRIORITY;
                    if (i9 != 0) {
                        int i10 = 0;
                        while (i10 < i4 && i9 <= (getSpanFlags(objArr2[i10]) & Spanned.SPAN_PRIORITY)) {
                            i10++;
                        }
                        System.arraycopy(objArr2, i10, objArr2, i10 + 1, i4 - i10);
                        objArr2[i10] = objArr[i5];
                    } else {
                        objArr2[i4] = objArr[i5];
                        i4++;
                    }
                }
                i4++;
            }
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

    public int nextSpanTransition(int i, int i2, Class cls) {
        int i3 = this.mSpanCount;
        Object[] objArr = this.mSpans;
        int[] iArr = this.mSpanData;
        if (cls == null) {
            cls = Object.class;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * 3;
            int i6 = iArr[i5];
            int i7 = iArr[i5 + 1];
            if (i6 > i && i6 < i2 && cls.isInstance(objArr[i4])) {
                i2 = i6;
            }
            if (i7 > i && i7 < i2 && cls.isInstance(objArr[i4])) {
                i2 = i7;
            }
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanAdded(Object obj, int i, int i2) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(i, i2, SpanWatcher.class)) {
            spanWatcher.onSpanAdded((Spannable) this, obj, i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanRemoved(Object obj, int i, int i2) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(i, i2, SpanWatcher.class)) {
            spanWatcher.onSpanRemoved((Spannable) this, obj, i, i2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void sendSpanChanged(Object obj, int i, int i2, int i3, int i4) {
        for (SpanWatcher spanWatcher : (SpanWatcher[]) getSpans(Math.min(i, i3), Math.max(i2, i4), SpanWatcher.class)) {
            spanWatcher.onSpanChanged((Spannable) this, obj, i, i2, i3, i4);
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

    private void copySpans(Spanned spanned, int i, int i2) {
        copySpansFromSpanned(spanned, i, i2, false);
    }

    private void copySpans(SpannableStringInternal spannableStringInternal, int i, int i2) {
        copySpansFromInternal(spannableStringInternal, i, i2, false);
    }
}
