package android.text;

@Deprecated
/* loaded from: classes4.dex */
public abstract class LoginFilter implements InputFilter {
    private boolean mAppendInvalid;

    public abstract boolean isAllowed(char c);

    public void onInvalidCharacter(char c) {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    LoginFilter(boolean z) {
        this.mAppendInvalid = z;
    }

    LoginFilter() {
        this.mAppendInvalid = false;
    }

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        onStart();
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            char charAt = spanned.charAt(i6);
            if (!isAllowed(charAt)) {
                onInvalidCharacter(charAt);
            }
        }
        SpannableStringBuilder spannableStringBuilder = null;
        for (int i7 = i; i7 < i2; i7++) {
            char charAt2 = charSequence.charAt(i7);
            if (isAllowed(charAt2)) {
                i5++;
            } else {
                if (this.mAppendInvalid) {
                    i5++;
                } else {
                    if (spannableStringBuilder == null) {
                        spannableStringBuilder = new SpannableStringBuilder(charSequence, i, i2);
                        i5 = i7 - i;
                    }
                    spannableStringBuilder.delete(i5, i5 + 1);
                }
                onInvalidCharacter(charAt2);
            }
        }
        while (i4 < spanned.length()) {
            char charAt3 = spanned.charAt(i4);
            if (!isAllowed(charAt3)) {
                onInvalidCharacter(charAt3);
            }
            i4++;
        }
        onStop();
        return spannableStringBuilder;
    }

    @Deprecated
    public static class UsernameFilterGMail extends LoginFilter {
        @Override // android.text.LoginFilter
        public boolean isAllowed(char c) {
            if ('0' <= c && c <= '9') {
                return true;
            }
            if ('a' > c || c > 'z') {
                return ('A' <= c && c <= 'Z') || '.' == c;
            }
            return true;
        }

        public UsernameFilterGMail() {
            super(false);
        }

        public UsernameFilterGMail(boolean z) {
            super(z);
        }
    }

    public static class UsernameFilterGeneric extends LoginFilter {
        private static final String mAllowed = "@_-+.";

        public UsernameFilterGeneric() {
            super(false);
        }

        public UsernameFilterGeneric(boolean z) {
            super(z);
        }

        @Override // android.text.LoginFilter
        public boolean isAllowed(char c) {
            if ('0' <= c && c <= '9') {
                return true;
            }
            if ('a' > c || c > 'z') {
                return ('A' <= c && c <= 'Z') || mAllowed.indexOf(c) != -1;
            }
            return true;
        }
    }

    @Deprecated
    public static class PasswordFilterGMail extends LoginFilter {
        @Override // android.text.LoginFilter
        public boolean isAllowed(char c) {
            if (' ' > c || c > 127) {
                return 160 <= c && c <= 255;
            }
            return true;
        }

        public PasswordFilterGMail() {
            super(false);
        }

        public PasswordFilterGMail(boolean z) {
            super(z);
        }
    }
}
