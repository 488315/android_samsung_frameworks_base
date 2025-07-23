package android.text;

/* loaded from: classes4.dex */
public class SpannableString extends SpannableStringInternal implements CharSequence, GetChars, Spannable {
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

    public SpannableString(CharSequence charSequence, boolean z) {
        super(charSequence, 0, charSequence.length(), z);
    }

    public SpannableString(CharSequence charSequence) {
        this(charSequence, false);
    }

    private SpannableString(CharSequence charSequence, int i, int i2) {
        super(charSequence, i, i2, false);
    }

    public static SpannableString valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannableString) {
            return (SpannableString) charSequence;
        }
        return new SpannableString(charSequence);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public void setSpan(Object obj, int i, int i2, int i3) {
        super.setSpan(obj, i, i2, i3);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public void removeSpan(Object obj) {
        super.removeSpan(obj);
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return new SpannableString(this, i, i2);
    }
}
