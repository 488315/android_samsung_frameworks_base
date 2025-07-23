package android.text;

/* loaded from: classes4.dex */
public final class SpannedString extends SpannableStringInternal implements CharSequence, GetChars, Spanned {
    @Override // android.text.SpannableStringInternal
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanEnd(Object obj) {
        return super.getSpanEnd(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanFlags(Object obj) {
        return super.getSpanFlags(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanStart(Object obj) {
        return super.getSpanStart(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ Object[] getSpans(int i, int i2, Class cls) {
        return super.getSpans(i, i2, cls);
    }

    @Override // android.text.SpannableStringInternal
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int nextSpanTransition(int i, int i2, Class cls) {
        return super.nextSpanTransition(i, i2, cls);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public /* bridge */ /* synthetic */ void removeSpan(Object obj, int i) {
        super.removeSpan(obj, i);
    }

    public SpannedString(CharSequence charSequence, boolean z) {
        super(charSequence, 0, charSequence.length(), z);
    }

    public SpannedString(CharSequence charSequence) {
        this(charSequence, false);
    }

    private SpannedString(CharSequence charSequence, int i, int i2) {
        super(charSequence, i, i2, false);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return new SpannedString(this, i, i2);
    }

    public static SpannedString valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannedString) {
            return (SpannedString) charSequence;
        }
        return new SpannedString(charSequence);
    }
}
