package android.text;

@Deprecated
/* loaded from: classes4.dex */
public class AlteredCharSequence implements CharSequence, GetChars {
    private char[] mChars;
    private int mEnd;
    private CharSequence mSource;
    private int mStart;

    public static AlteredCharSequence make(CharSequence charSequence, char[] cArr, int i, int i2) {
        if (charSequence instanceof Spanned) {
            return new AlteredSpanned(charSequence, cArr, i, i2);
        }
        return new AlteredCharSequence(charSequence, cArr, i, i2);
    }

    private AlteredCharSequence(CharSequence charSequence, char[] cArr, int i, int i2) {
        this.mSource = charSequence;
        this.mChars = cArr;
        this.mStart = i;
        this.mEnd = i2;
    }

    void update(char[] cArr, int i, int i2) {
        this.mChars = cArr;
        this.mStart = i;
        this.mEnd = i2;
    }

    private static class AlteredSpanned extends AlteredCharSequence implements Spanned {
        private Spanned mSpanned;

        private AlteredSpanned(CharSequence charSequence, char[] cArr, int i, int i2) {
            super(charSequence, cArr, i, i2);
            this.mSpanned = (Spanned) charSequence;
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

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        int i2 = this.mStart;
        if (i >= i2 && i < this.mEnd) {
            return this.mChars[i - i2];
        }
        return this.mSource.charAt(i);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mSource.length();
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return make(this.mSource.subSequence(i, i2), this.mChars, this.mStart - i, this.mEnd - i);
    }

    @Override // android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        TextUtils.getChars(this.mSource, i, i2, cArr, i3);
        int max = Math.max(this.mStart, i);
        int min = Math.min(this.mEnd, i2);
        if (max > min) {
            System.arraycopy(this.mChars, max - this.mStart, cArr, i3, min - max);
        }
    }

    @Override // java.lang.CharSequence
    public String toString() {
        int length = length();
        char[] cArr = new char[length];
        getChars(0, length, cArr, 0);
        return String.valueOf(cArr);
    }
}
