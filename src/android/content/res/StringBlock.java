package android.content.res;

import android.app.blob.XmlTags;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.TtmlUtils;
import android.provider.Telephony;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineHeightSpan;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import com.samsung.android.util.CustomizedTextParser;
import java.io.Closeable;

/* loaded from: classes.dex */
public final class StringBlock implements Closeable {
    private static final String TAG = "AssetManager";
    private static final boolean localLOGV = false;
    private long mNative;
    private SparseArray<CharSequence> mSparseStrings;
    private CharSequence[] mStrings;
    private final boolean mUseSparse;
    private boolean mOpen = true;
    StyleIDs mStyleIDs = null;
    private final boolean mOwnsNative = false;

    private static native long nativeCreate(byte[] bArr, int i, int i2);

    private static native void nativeDestroy(long j);

    private static native int nativeGetSize(long j);

    private static native String nativeGetString(long j, int i);

    private static native int[] nativeGetStyle(long j, int i);

    public StringBlock(byte[] bArr, boolean z) {
        this.mNative = nativeCreate(bArr, 0, bArr.length);
        this.mUseSparse = z;
    }

    public StringBlock(byte[] bArr, int i, int i2, boolean z) {
        this.mNative = nativeCreate(bArr, i, i2);
        this.mUseSparse = z;
    }

    @Deprecated
    public CharSequence get(int i) {
        CharSequence sequence = getSequence(i);
        return sequence == null ? "" : sequence;
    }

    public CharSequence getSequence(int i) {
        synchronized (this) {
            CharSequence[] charSequenceArr = this.mStrings;
            if (charSequenceArr != null) {
                CharSequence charSequence = charSequenceArr[i];
                if (charSequence != null) {
                    return charSequence;
                }
            } else {
                SparseArray<CharSequence> sparseArray = this.mSparseStrings;
                if (sparseArray != null) {
                    CharSequence charSequence2 = sparseArray.get(i);
                    if (charSequence2 != null) {
                        return charSequence2;
                    }
                } else {
                    int nativeGetSize = nativeGetSize(this.mNative);
                    if (this.mUseSparse && nativeGetSize > 250) {
                        this.mSparseStrings = new SparseArray<>();
                    } else {
                        this.mStrings = new CharSequence[nativeGetSize];
                    }
                }
            }
            String nativeGetString = nativeGetString(this.mNative, i);
            if (nativeGetString == null) {
                return null;
            }
            int[] nativeGetStyle = nativeGetStyle(this.mNative, i);
            CharSequence charSequence3 = nativeGetString;
            if (nativeGetStyle != null) {
                if (this.mStyleIDs == null) {
                    this.mStyleIDs = new StyleIDs();
                }
                boolean z = false;
                for (int i2 = 0; i2 < nativeGetStyle.length; i2 += 3) {
                    int i3 = nativeGetStyle[i2];
                    if (i3 != this.mStyleIDs.boldId && i3 != this.mStyleIDs.italicId && i3 != this.mStyleIDs.underlineId && i3 != this.mStyleIDs.ttId && i3 != this.mStyleIDs.bigId && i3 != this.mStyleIDs.smallId && i3 != this.mStyleIDs.subId && i3 != this.mStyleIDs.supId && i3 != this.mStyleIDs.strikeId && i3 != this.mStyleIDs.listItemId && i3 != this.mStyleIDs.marqueeId) {
                        if (i3 != this.mStyleIDs.uniqueTextId) {
                            String nativeGetString2 = nativeGetString(this.mNative, i3);
                            if (nativeGetString2 == null) {
                                return null;
                            }
                            if (nativeGetString2.equals(XmlTags.TAG_BLOB)) {
                                this.mStyleIDs.boldId = i3;
                            } else if (nativeGetString2.equals("i")) {
                                this.mStyleIDs.italicId = i3;
                            } else if (nativeGetString2.equals(XmlTags.ATTR_UID)) {
                                this.mStyleIDs.underlineId = i3;
                            } else if (nativeGetString2.equals(TtmlUtils.TAG_TT)) {
                                this.mStyleIDs.ttId = i3;
                            } else if (nativeGetString2.equals("big")) {
                                this.mStyleIDs.bigId = i3;
                            } else if (nativeGetString2.equals("small")) {
                                this.mStyleIDs.smallId = i3;
                            } else if (nativeGetString2.equals("sup")) {
                                this.mStyleIDs.supId = i3;
                            } else if (nativeGetString2.equals(Telephony.BaseMmsColumns.SUBJECT)) {
                                this.mStyleIDs.subId = i3;
                            } else if (nativeGetString2.equals("strike")) {
                                this.mStyleIDs.strikeId = i3;
                            } else if (nativeGetString2.equals("li")) {
                                this.mStyleIDs.listItemId = i3;
                            } else if (nativeGetString2.equals("marquee")) {
                                this.mStyleIDs.marqueeId = i3;
                            } else if (nativeGetString2.equals("nobreak")) {
                                this.mStyleIDs.mNoBreakId = i3;
                            } else if (nativeGetString2.equals("nohyphen")) {
                                this.mStyleIDs.mNoHyphenId = i3;
                            } else if (nativeGetString2.equals(CustomizedTextParser.REPLACE_TAG)) {
                                this.mStyleIDs.uniqueTextId = i3;
                            }
                        }
                        z = true;
                    }
                }
                charSequence3 = applyStyles(nativeGetString, nativeGetStyle, this.mStyleIDs, z);
            }
            if (charSequence3 != null) {
                CharSequence[] charSequenceArr2 = this.mStrings;
                if (charSequenceArr2 != null) {
                    charSequenceArr2[i] = charSequence3;
                } else {
                    this.mSparseStrings.put(i, charSequence3);
                }
            }
            return charSequence3;
        }
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            close();
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                if (this.mOwnsNative) {
                    nativeDestroy(this.mNative);
                }
                this.mNative = 0L;
            }
        }
    }

    static final class StyleIDs {
        private int boldId = -1;
        private int italicId = -1;
        private int underlineId = -1;
        private int ttId = -1;
        private int bigId = -1;
        private int smallId = -1;
        private int subId = -1;
        private int supId = -1;
        private int strikeId = -1;
        private int listItemId = -1;
        private int marqueeId = -1;
        private int mNoBreakId = -1;
        private int mNoHyphenId = -1;
        private int uniqueTextId = -1;

        StyleIDs() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:144:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0362 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.CharSequence applyStyles(java.lang.String r11, int[] r12, android.content.res.StringBlock.StyleIDs r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 900
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.StringBlock.applyStyles(java.lang.String, int[], android.content.res.StringBlock$StyleIDs, boolean):java.lang.CharSequence");
    }

    private static CharacterStyle getColor(String str, boolean z) {
        int i = -16777216;
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("@")) {
                Resources system = Resources.getSystem();
                int identifier = system.getIdentifier(str.substring(1), "color", "android");
                if (identifier != 0) {
                    ColorStateList colorStateList = system.getColorStateList(identifier, null);
                    if (z) {
                        return new TextAppearanceSpan(null, 0, 0, colorStateList, null);
                    }
                    i = colorStateList.getDefaultColor();
                }
            } else {
                try {
                    i = Color.parseColor(str);
                } catch (IllegalArgumentException unused) {
                }
            }
        }
        if (z) {
            return new ForegroundColorSpan(i);
        }
        return new BackgroundColorSpan(i);
    }

    private static void addParagraphSpan(Spannable spannable, Object obj, int i, int i2) {
        int length = spannable.length();
        if (i != 0 && i != length && spannable.charAt(i - 1) != '\n') {
            do {
                i--;
                if (i <= 0) {
                    break;
                }
            } while (spannable.charAt(i - 1) != '\n');
        }
        if (i2 != 0 && i2 != length && spannable.charAt(i2 - 1) != '\n') {
            do {
                i2++;
                if (i2 >= length) {
                    break;
                }
            } while (spannable.charAt(i2 - 1) != '\n');
        }
        spannable.setSpan(obj, i, i2, 51);
    }

    private static String subtag(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return null;
        }
        int length = indexOf + str2.length();
        int indexOf2 = str.indexOf(59, length);
        if (indexOf2 < 0) {
            return str.substring(length);
        }
        return str.substring(length, indexOf2);
    }

    private static class Height implements LineHeightSpan.WithDensity {
        private static float sProportion;
        private int mSize;

        public Height(int i) {
            this.mSize = i;
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
            chooseHeight(charSequence, i, i2, i3, i4, fontMetricsInt, null);
        }

        @Override // android.text.style.LineHeightSpan.WithDensity
        public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt, TextPaint textPaint) {
            int i5 = this.mSize;
            if (textPaint != null) {
                i5 = (int) (i5 * textPaint.density);
            }
            if (fontMetricsInt.bottom - fontMetricsInt.top < i5) {
                fontMetricsInt.top = fontMetricsInt.bottom - i5;
                fontMetricsInt.ascent -= i5;
                return;
            }
            if (sProportion == 0.0f) {
                Paint paint = new Paint();
                paint.setTextSize(100.0f);
                paint.getTextBounds("ABCDEFG", 0, 7, new Rect());
                sProportion = r3.top / paint.ascent();
            }
            int ceil = (int) Math.ceil((-fontMetricsInt.top) * sProportion);
            if (i5 - fontMetricsInt.descent >= ceil) {
                fontMetricsInt.top = fontMetricsInt.bottom - i5;
                fontMetricsInt.ascent = fontMetricsInt.descent - i5;
                return;
            }
            if (i5 >= ceil) {
                int i6 = -ceil;
                fontMetricsInt.ascent = i6;
                fontMetricsInt.top = i6;
                int i7 = fontMetricsInt.top + i5;
                fontMetricsInt.descent = i7;
                fontMetricsInt.bottom = i7;
                return;
            }
            int i8 = -i5;
            fontMetricsInt.ascent = i8;
            fontMetricsInt.top = i8;
            fontMetricsInt.descent = 0;
            fontMetricsInt.bottom = 0;
        }
    }

    public StringBlock(long j, boolean z) {
        this.mNative = j;
        this.mUseSparse = z;
    }
}
