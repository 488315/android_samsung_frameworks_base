package android.text;

/* loaded from: classes4.dex */
public interface Spannable extends Spanned {
    void removeSpan(Object obj);

    void setSpan(Object obj, int i, int i2, int i3);

    default void removeSpan(Object obj, int i) {
        removeSpan(obj);
    }

    public static class Factory {
        private static Factory sInstance = new Factory();

        public static Factory getInstance() {
            return sInstance;
        }

        public Spannable newSpannable(CharSequence charSequence) {
            return new SpannableString(charSequence);
        }
    }
}
