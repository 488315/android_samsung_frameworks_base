package android.content.res;

import android.app.ActivityThread;
import android.app.blob.XmlTags;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.LineBreakConfig;
import android.media.TtmlUtils;
import android.provider.Telephony;
import android.text.Annotation;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBreakConfigSpan;
import android.text.style.LineHeightSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
                    int iNativeGetSize = nativeGetSize(this.mNative);
                    if (this.mUseSparse && iNativeGetSize > 250) {
                        this.mSparseStrings = new SparseArray<>();
                    } else {
                        this.mStrings = new CharSequence[iNativeGetSize];
                    }
                }
            }
            String strNativeGetString = nativeGetString(this.mNative, i);
            if (strNativeGetString == null) {
                return null;
            }
            int[] iArrNativeGetStyle = nativeGetStyle(this.mNative, i);
            CharSequence charSequenceApplyStyles = strNativeGetString;
            if (iArrNativeGetStyle != null) {
                if (this.mStyleIDs == null) {
                    this.mStyleIDs = new StyleIDs();
                }
                boolean z = false;
                for (int i2 = 0; i2 < iArrNativeGetStyle.length; i2 += 3) {
                    int i3 = iArrNativeGetStyle[i2];
                    if (i3 != this.mStyleIDs.boldId && i3 != this.mStyleIDs.italicId && i3 != this.mStyleIDs.underlineId && i3 != this.mStyleIDs.ttId && i3 != this.mStyleIDs.bigId && i3 != this.mStyleIDs.smallId && i3 != this.mStyleIDs.subId && i3 != this.mStyleIDs.supId && i3 != this.mStyleIDs.strikeId && i3 != this.mStyleIDs.listItemId && i3 != this.mStyleIDs.marqueeId) {
                        if (i3 != this.mStyleIDs.uniqueTextId) {
                            String strNativeGetString2 = nativeGetString(this.mNative, i3);
                            if (strNativeGetString2 == null) {
                                return null;
                            }
                            if (strNativeGetString2.equals(XmlTags.TAG_BLOB)) {
                                this.mStyleIDs.boldId = i3;
                            } else if (strNativeGetString2.equals("i")) {
                                this.mStyleIDs.italicId = i3;
                            } else if (strNativeGetString2.equals(XmlTags.ATTR_UID)) {
                                this.mStyleIDs.underlineId = i3;
                            } else if (strNativeGetString2.equals(TtmlUtils.TAG_TT)) {
                                this.mStyleIDs.ttId = i3;
                            } else if (strNativeGetString2.equals("big")) {
                                this.mStyleIDs.bigId = i3;
                            } else if (strNativeGetString2.equals("small")) {
                                this.mStyleIDs.smallId = i3;
                            } else if (strNativeGetString2.equals("sup")) {
                                this.mStyleIDs.supId = i3;
                            } else if (strNativeGetString2.equals(Telephony.BaseMmsColumns.SUBJECT)) {
                                this.mStyleIDs.subId = i3;
                            } else if (strNativeGetString2.equals("strike")) {
                                this.mStyleIDs.strikeId = i3;
                            } else if (strNativeGetString2.equals("li")) {
                                this.mStyleIDs.listItemId = i3;
                            } else if (strNativeGetString2.equals("marquee")) {
                                this.mStyleIDs.marqueeId = i3;
                            } else if (strNativeGetString2.equals("nobreak")) {
                                this.mStyleIDs.mNoBreakId = i3;
                            } else if (strNativeGetString2.equals("nohyphen")) {
                                this.mStyleIDs.mNoHyphenId = i3;
                            } else if (strNativeGetString2.equals(CustomizedTextParser.REPLACE_TAG)) {
                                this.mStyleIDs.uniqueTextId = i3;
                                z = true;
                            }
                        } else {
                            z = true;
                        }
                    }
                }
                charSequenceApplyStyles = applyStyles(strNativeGetString, iArrNativeGetStyle, this.mStyleIDs, z);
            }
            if (charSequenceApplyStyles != null) {
                CharSequence[] charSequenceArr2 = this.mStrings;
                if (charSequenceArr2 != null) {
                    charSequenceArr2[i] = charSequenceApplyStyles;
                } else {
                    this.mSparseStrings.put(i, charSequenceApplyStyles);
                }
            }
            return charSequenceApplyStyles;
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

    /* JADX WARN: Removed duplicated region for block: B:130:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0364  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private CharSequence applyStyles(String str, int[] iArr, StyleIDs styleIDs, boolean z) {
        String strSubtag;
        int i;
        if (iArr.length == 0) {
            return str;
        }
        if (z) {
            StringBuffer stringBuffer = new StringBuffer(str);
            for (int i2 = 0; i2 < iArr.length; i2 += 3) {
                if (iArr[i2] == styleIDs.uniqueTextId) {
                    int i3 = i2 + 1;
                    int i4 = i2 + 2;
                    String strSubstring = stringBuffer.substring(iArr[i3], iArr[i4] + 1);
                    String customizedString = AssetManager.getCustomizedString(strSubstring);
                    if (customizedString == null) {
                        customizedString = strSubstring;
                    }
                    stringBuffer.replace(iArr[i3], iArr[i4] + 1, customizedString);
                    int length = customizedString.length() - strSubstring.length();
                    for (int i5 = 0; i5 < iArr.length; i5 += 3) {
                        if (i2 != i5) {
                            int i6 = i5 + 1;
                            int i7 = iArr[i6];
                            if (i7 >= iArr[i4]) {
                                iArr[i6] = i7 + length;
                            }
                            int i8 = i5 + 2;
                            int i9 = iArr[i8];
                            if (i9 >= iArr[i4]) {
                                iArr[i8] = i9 + length;
                            }
                        }
                    }
                }
            }
            str = stringBuffer.toString();
        }
        SpannableString spannableString = new SpannableString(str);
        for (int i10 = 0; i10 < iArr.length; i10 += 3) {
            int i11 = iArr[i10];
            if (i11 == styleIDs.boldId) {
                spannableString.setSpan(new StyleSpan(1, ActivityThread.currentApplication().getResources().getConfiguration().fontWeightAdjustment), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
            } else {
                int i12 = 2;
                if (i11 == styleIDs.italicId) {
                    spannableString.setSpan(new StyleSpan(2), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.underlineId) {
                    spannableString.setSpan(new UnderlineSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.ttId) {
                    spannableString.setSpan(new TypefaceSpan("monospace"), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.bigId) {
                    spannableString.setSpan(new RelativeSizeSpan(1.25f), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.smallId) {
                    spannableString.setSpan(new RelativeSizeSpan(0.8f), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.subId) {
                    spannableString.setSpan(new SubscriptSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.supId) {
                    spannableString.setSpan(new SuperscriptSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.strikeId) {
                    spannableString.setSpan(new StrikethroughSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                } else if (i11 == styleIDs.listItemId) {
                    addParagraphSpan(spannableString, new BulletSpan(10), iArr[i10 + 1], iArr[i10 + 2] + 1);
                } else if (i11 == styleIDs.marqueeId) {
                    spannableString.setSpan(TextUtils.TruncateAt.MARQUEE, iArr[i10 + 1], iArr[i10 + 2] + 1, 18);
                } else if (i11 == styleIDs.mNoBreakId) {
                    spannableString.setSpan(LineBreakConfigSpan.createNoBreakSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 17);
                } else if (i11 == styleIDs.mNoHyphenId) {
                    spannableString.setSpan(LineBreakConfigSpan.createNoHyphenationSpan(), iArr[i10 + 1], iArr[i10 + 2] + 1, 17);
                } else {
                    String strNativeGetString = nativeGetString(this.mNative, i11);
                    if (strNativeGetString == null) {
                        return null;
                    }
                    if (strNativeGetString.startsWith("font;")) {
                        String strSubtag2 = subtag(strNativeGetString, ";height=");
                        if (strSubtag2 != null) {
                            addParagraphSpan(spannableString, new Height(Integer.parseInt(strSubtag2)), iArr[i10 + 1], iArr[i10 + 2] + 1);
                        }
                        String strSubtag3 = subtag(strNativeGetString, ";size=");
                        if (strSubtag3 != null) {
                            spannableString.setSpan(new AbsoluteSizeSpan(Integer.parseInt(strSubtag3), true), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                        String strSubtag4 = subtag(strNativeGetString, ";fgcolor=");
                        if (strSubtag4 != null) {
                            spannableString.setSpan(getColor(strSubtag4, true), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                        String strSubtag5 = subtag(strNativeGetString, ";color=");
                        if (strSubtag5 != null) {
                            spannableString.setSpan(getColor(strSubtag5, true), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                        String strSubtag6 = subtag(strNativeGetString, ";bgcolor=");
                        if (strSubtag6 != null) {
                            spannableString.setSpan(getColor(strSubtag6, false), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                        String strSubtag7 = subtag(strNativeGetString, ";face=");
                        if (strSubtag7 != null) {
                            spannableString.setSpan(new TypefaceSpan(strSubtag7), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                    } else if (strNativeGetString.startsWith("a;")) {
                        String strSubtag8 = subtag(strNativeGetString, ";href=");
                        if (strSubtag8 != null) {
                            spannableString.setSpan(new URLSpan(strSubtag8), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                        }
                    } else if (strNativeGetString.startsWith("annotation;")) {
                        int length2 = strNativeGetString.length();
                        int iIndexOf = strNativeGetString.indexOf(59);
                        while (iIndexOf < length2) {
                            int iIndexOf2 = strNativeGetString.indexOf(61, iIndexOf);
                            if (iIndexOf2 < 0) {
                                break;
                            }
                            int iIndexOf3 = strNativeGetString.indexOf(59, iIndexOf2);
                            if (iIndexOf3 < 0) {
                                iIndexOf3 = length2;
                            }
                            spannableString.setSpan(new Annotation(strNativeGetString.substring(iIndexOf + 1, iIndexOf2), strNativeGetString.substring(iIndexOf2 + 1, iIndexOf3)), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                            iIndexOf = iIndexOf3;
                        }
                    } else if (strNativeGetString.startsWith("lineBreakConfig;")) {
                        String strSubtag9 = subtag(strNativeGetString, ";style=");
                        if (strSubtag9 != null) {
                            if (strSubtag9.equals("none")) {
                                i12 = 0;
                            } else if (!strSubtag9.equals("normal")) {
                                if (strSubtag9.equals("loose")) {
                                    i12 = 1;
                                } else if (strSubtag9.equals("strict")) {
                                    i12 = 3;
                                } else {
                                    Log.w(TAG, "Unknown LineBreakConfig style: " + strSubtag9);
                                    i12 = -1;
                                }
                            }
                            strSubtag = subtag(strNativeGetString, ";wordStyle=");
                            if (strSubtag == null) {
                            }
                        } else {
                            i12 = -1;
                            strSubtag = subtag(strNativeGetString, ";wordStyle=");
                            if (strSubtag == null) {
                                i = -1;
                                if (i12 == -1 || i != -1) {
                                    spannableString.setSpan(new LineBreakConfigSpan(new LineBreakConfig(i12, i, -1)), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                                }
                            } else {
                                if (strSubtag.equals("none")) {
                                    i = 0;
                                } else if (strSubtag.equals("phrase")) {
                                    i = 1;
                                } else {
                                    Log.w(TAG, "Unknown LineBreakConfig word style: " + strSubtag);
                                    i = -1;
                                }
                                if (i12 == -1) {
                                    spannableString.setSpan(new LineBreakConfigSpan(new LineBreakConfig(i12, i, -1)), iArr[i10 + 1], iArr[i10 + 2] + 1, 33);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new SpannedString(spannableString);
    }

    private static CharacterStyle getColor(String str, boolean z) throws Resources.NotFoundException {
        int color = -16777216;
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("@")) {
                Resources system = Resources.getSystem();
                int identifier = system.getIdentifier(str.substring(1), "color", "android");
                if (identifier != 0) {
                    ColorStateList colorStateList = system.getColorStateList(identifier, null);
                    if (z) {
                        return new TextAppearanceSpan(null, 0, 0, colorStateList, null);
                    }
                    color = colorStateList.getDefaultColor();
                }
            } else {
                try {
                    color = Color.parseColor(str);
                } catch (IllegalArgumentException unused) {
                }
            }
        }
        if (z) {
            return new ForegroundColorSpan(color);
        }
        return new BackgroundColorSpan(color);
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
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf < 0) {
            return null;
        }
        int length = iIndexOf + str2.length();
        int iIndexOf2 = str.indexOf(59, length);
        if (iIndexOf2 < 0) {
            return str.substring(length);
        }
        return str.substring(length, iIndexOf2);
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
            int iCeil = (int) Math.ceil((-fontMetricsInt.top) * sProportion);
            if (i5 - fontMetricsInt.descent >= iCeil) {
                fontMetricsInt.top = fontMetricsInt.bottom - i5;
                fontMetricsInt.ascent = fontMetricsInt.descent - i5;
                return;
            }
            if (i5 >= iCeil) {
                int i6 = -iCeil;
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
