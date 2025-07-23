package android.text.method;

import android.graphics.Rect;
import android.text.Editable;
import android.text.GetChars;
import android.text.Spannable;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;

/* loaded from: classes4.dex */
public abstract class ReplacementTransformationMethod implements TransformationMethod {
    protected abstract char[] getOriginal();

    protected abstract char[] getReplacement();

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
    }

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        char[] original = getOriginal();
        char[] replacement = getReplacement();
        if (!(charSequence instanceof Editable)) {
            for (char c : original) {
                if (TextUtils.indexOf(charSequence, c) >= 0) {
                    if (!(charSequence instanceof Spannable)) {
                        if (charSequence instanceof Spanned) {
                            return new SpannedString(new SpannedReplacementCharSequence((Spanned) charSequence, original, replacement));
                        }
                        return new ReplacementCharSequence(charSequence, original, replacement).toString();
                    }
                }
            }
            return charSequence;
        }
        if (charSequence instanceof Spanned) {
            return new SpannedReplacementCharSequence((Spanned) charSequence, original, replacement);
        }
        return new ReplacementCharSequence(charSequence, original, replacement);
    }

    private static class ReplacementCharSequence implements CharSequence, GetChars {
        private char[] mOriginal;
        private char[] mReplacement;
        private CharSequence mSource;

        public ReplacementCharSequence(CharSequence charSequence, char[] cArr, char[] cArr2) {
            this.mSource = charSequence;
            this.mOriginal = cArr;
            this.mReplacement = cArr2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mSource.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            char charAt = this.mSource.charAt(i);
            int length = this.mOriginal.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (charAt == this.mOriginal[i2]) {
                    charAt = this.mReplacement[i2];
                }
            }
            return charAt;
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new String(cArr);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            char[] cArr = new char[length()];
            getChars(0, length(), cArr, 0);
            return new String(cArr);
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            TextUtils.getChars(this.mSource, i, i2, cArr, i3);
            int i4 = (i2 - i) + i3;
            int length = this.mOriginal.length;
            while (i3 < i4) {
                char c = cArr[i3];
                for (int i5 = 0; i5 < length; i5++) {
                    if (c == this.mOriginal[i5]) {
                        cArr[i3] = this.mReplacement[i5];
                    }
                }
                i3++;
            }
        }
    }

    private static class SpannedReplacementCharSequence extends ReplacementCharSequence implements Spanned {
        private Spanned mSpanned;

        public SpannedReplacementCharSequence(Spanned spanned, char[] cArr, char[] cArr2) {
            super(spanned, cArr, cArr2);
            this.mSpanned = spanned;
        }

        @Override // android.text.method.ReplacementTransformationMethod.ReplacementCharSequence, java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            return new SpannedString(this).subSequence(i, i2);
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(int i, int i2, Class<T> cls) {
            return (T[]) this.mSpanned.getSpans(i, i2, cls);
        }

        @Override // android.text.Spanned
        public int getSpanStart(Object obj) {
            return this.mSpanned.getSpanStart(obj);
        }

        @Override // android.text.Spanned
        public int getSpanEnd(Object obj) {
            return this.mSpanned.getSpanEnd(obj);
        }

        @Override // android.text.Spanned
        public int getSpanFlags(Object obj) {
            return this.mSpanned.getSpanFlags(obj);
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, Class cls) {
            return this.mSpanned.nextSpanTransition(i, i2, cls);
        }
    }
}
