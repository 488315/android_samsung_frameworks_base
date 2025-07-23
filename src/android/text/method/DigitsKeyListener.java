package android.text.method;

import android.icu.lang.UCharacter;
import android.icu.text.DecimalFormatSymbols;
import com.android.internal.util.ArrayUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

/* loaded from: classes4.dex */
public class DigitsKeyListener extends NumberKeyListener {
    private static final int DECIMAL = 2;
    private static final String DEFAULT_DECIMAL_POINT_CHARS = ".";
    private static final String DEFAULT_SIGN_CHARS = "-+";
    private static final char EN_DASH = 8211;
    private static final char MINUS_SIGN = 8722;
    private static final int SIGN = 1;
    private char[] mAccepted;
    private final boolean mDecimal;
    private String mDecimalPointChars;
    private final Locale mLocale;
    private boolean mNeedsAdvancedInput;
    private final boolean mSign;
    private String mSignChars;
    private final boolean mStringMode;
    private static final char HYPHEN_MINUS = '-';
    private static final char[][] COMPATIBILITY_CHARACTERS = {new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', HYPHEN_MINUS, '+'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', HYPHEN_MINUS, '+', '.'}};
    private static final Object sLocaleCacheLock = new Object();
    private static final HashMap<Locale, DigitsKeyListener[]> sLocaleInstanceCache = new HashMap<>();
    private static final Object sStringCacheLock = new Object();
    private static final HashMap<String, DigitsKeyListener> sStringInstanceCache = new HashMap<>();

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return this.mAccepted;
    }

    private boolean isSignChar(char c) {
        return this.mSignChars.indexOf(c) != -1;
    }

    private boolean isDecimalPointChar(char c) {
        return this.mDecimalPointChars.indexOf(c) != -1;
    }

    @Deprecated
    public DigitsKeyListener() {
        this(null, false, false);
    }

    @Deprecated
    public DigitsKeyListener(boolean z, boolean z2) {
        this(null, z, z2);
    }

    public DigitsKeyListener(Locale locale) {
        this(locale, false, false);
    }

    private void setToCompat() {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mAccepted = COMPATIBILITY_CHARACTERS[(this.mSign ? 1 : 0) | (this.mDecimal ? 2 : 0)];
        this.mNeedsAdvancedInput = false;
    }

    private void calculateNeedForAdvancedInput() {
        this.mNeedsAdvancedInput = !ArrayUtils.containsAll(COMPATIBILITY_CHARACTERS[(this.mSign ? 1 : 0) | (this.mDecimal ? 2 : 0)], this.mAccepted);
    }

    private static String stripBidiControls(String str) {
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!UCharacter.hasBinaryProperty(charAt, 2)) {
                str2 = str2.isEmpty() ? String.valueOf(charAt) : str2 + charAt;
            }
        }
        return str2;
    }

    public DigitsKeyListener(Locale locale, boolean z, boolean z2) {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mSign = z;
        this.mDecimal = z2;
        this.mStringMode = false;
        this.mLocale = locale;
        if (locale == null) {
            setToCompat();
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (!NumberKeyListener.addDigits(linkedHashSet, locale)) {
            setToCompat();
            return;
        }
        if (z || z2) {
            DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
            if (z) {
                String stripBidiControls = stripBidiControls(decimalFormatSymbols.getMinusSignString());
                String stripBidiControls2 = stripBidiControls(decimalFormatSymbols.getPlusSignString());
                if (stripBidiControls.length() > 1 || stripBidiControls2.length() > 1) {
                    setToCompat();
                    return;
                }
                char charAt = stripBidiControls.charAt(0);
                char charAt2 = stripBidiControls2.charAt(0);
                linkedHashSet.add(Character.valueOf(charAt));
                linkedHashSet.add(Character.valueOf(charAt2));
                this.mSignChars = "" + charAt + charAt2;
                if (charAt == 8722 || charAt == 8211) {
                    linkedHashSet.add(Character.valueOf(HYPHEN_MINUS));
                    this.mSignChars += HYPHEN_MINUS;
                }
            }
            if (z2) {
                String decimalSeparatorString = decimalFormatSymbols.getDecimalSeparatorString();
                if (decimalSeparatorString.length() > 1) {
                    setToCompat();
                    return;
                } else {
                    Character valueOf = Character.valueOf(decimalSeparatorString.charAt(0));
                    linkedHashSet.add(valueOf);
                    this.mDecimalPointChars = valueOf.toString();
                }
            }
        }
        this.mAccepted = NumberKeyListener.collectionToArray(linkedHashSet);
        calculateNeedForAdvancedInput();
    }

    private DigitsKeyListener(String str) {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mSign = false;
        this.mDecimal = false;
        this.mStringMode = true;
        this.mLocale = null;
        this.mAccepted = new char[str.length()];
        str.getChars(0, str.length(), this.mAccepted, 0);
        this.mNeedsAdvancedInput = false;
    }

    @Deprecated
    public static DigitsKeyListener getInstance() {
        return getInstance(false, false);
    }

    @Deprecated
    public static DigitsKeyListener getInstance(boolean z, boolean z2) {
        return getInstance(null, z, z2);
    }

    public static DigitsKeyListener getInstance(Locale locale) {
        return getInstance(locale, false, false);
    }

    public static DigitsKeyListener getInstance(Locale locale, boolean z, boolean z2) {
        DigitsKeyListener digitsKeyListener;
        int i = (z2 ? 2 : 0) | (z ? 1 : 0);
        synchronized (sLocaleCacheLock) {
            HashMap<Locale, DigitsKeyListener[]> hashMap = sLocaleInstanceCache;
            DigitsKeyListener[] digitsKeyListenerArr = hashMap.get(locale);
            if (digitsKeyListenerArr != null && (digitsKeyListener = digitsKeyListenerArr[i]) != null) {
                return digitsKeyListener;
            }
            if (digitsKeyListenerArr == null) {
                digitsKeyListenerArr = new DigitsKeyListener[4];
                hashMap.put(locale, digitsKeyListenerArr);
            }
            DigitsKeyListener digitsKeyListener2 = new DigitsKeyListener(locale, z, z2);
            digitsKeyListenerArr[i] = digitsKeyListener2;
            return digitsKeyListener2;
        }
    }

    public static DigitsKeyListener getInstance(String str) {
        DigitsKeyListener digitsKeyListener;
        synchronized (sStringCacheLock) {
            HashMap<String, DigitsKeyListener> hashMap = sStringInstanceCache;
            digitsKeyListener = hashMap.get(str);
            if (digitsKeyListener == null) {
                digitsKeyListener = new DigitsKeyListener(str);
                hashMap.put(str, digitsKeyListener);
            }
        }
        return digitsKeyListener;
    }

    public static DigitsKeyListener getInstance(Locale locale, DigitsKeyListener digitsKeyListener) {
        return digitsKeyListener.mStringMode ? digitsKeyListener : getInstance(locale, digitsKeyListener.mSign, digitsKeyListener.mDecimal);
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        int i = this.mSign ? 4098 : 2;
        return this.mDecimal ? i | 8192 : i;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x008b A[SYNTHETIC] */
    @Override // android.text.method.NumberKeyListener, android.text.InputFilter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.CharSequence filter(java.lang.CharSequence r10, int r11, int r12, android.text.Spanned r13, int r14, int r15) {
        /*
            r9 = this;
            java.lang.CharSequence r0 = super.filter(r10, r11, r12, r13, r14, r15)
            boolean r1 = r9.mSign
            if (r1 != 0) goto Le
            boolean r1 = r9.mDecimal
            if (r1 != 0) goto Le
            goto L93
        Le:
            r1 = 0
            if (r0 == 0) goto L17
            int r12 = r0.length()
            r10 = r0
            r11 = r1
        L17:
            int r2 = r13.length()
            r3 = -1
            r5 = r1
            r4 = r3
        L1e:
            if (r5 >= r14) goto L36
            char r6 = r13.charAt(r5)
            boolean r7 = r9.isSignChar(r6)
            if (r7 == 0) goto L2c
            r4 = r5
            goto L33
        L2c:
            boolean r6 = r9.isDecimalPointChar(r6)
            if (r6 == 0) goto L33
            r3 = r5
        L33:
            int r5 = r5 + 1
            goto L1e
        L36:
            java.lang.String r5 = ""
            if (r15 >= r2) goto L4f
            char r6 = r13.charAt(r15)
            boolean r7 = r9.isSignChar(r6)
            if (r7 == 0) goto L45
            return r5
        L45:
            boolean r5 = r9.isDecimalPointChar(r6)
            if (r5 == 0) goto L4c
            r3 = r15
        L4c:
            int r15 = r15 + 1
            goto L36
        L4f:
            int r13 = r12 + (-1)
            r15 = 0
            r2 = r15
        L53:
            if (r13 < r11) goto L8e
            char r6 = r10.charAt(r13)
            boolean r7 = r9.isSignChar(r6)
            r8 = 1
            if (r7 == 0) goto L6a
            if (r13 != r11) goto L75
            if (r14 == 0) goto L65
            goto L75
        L65:
            if (r4 < 0) goto L68
            goto L75
        L68:
            r4 = r13
            goto L74
        L6a:
            boolean r6 = r9.isDecimalPointChar(r6)
            if (r6 == 0) goto L74
            if (r3 < 0) goto L73
            goto L75
        L73:
            r3 = r13
        L74:
            r8 = r1
        L75:
            if (r8 == 0) goto L8b
            int r6 = r11 + 1
            if (r12 != r6) goto L7c
            return r5
        L7c:
            if (r2 != 0) goto L83
            android.text.SpannableStringBuilder r2 = new android.text.SpannableStringBuilder
            r2.<init>(r10, r11, r12)
        L83:
            int r6 = r13 - r11
            int r7 = r13 + 1
            int r7 = r7 - r11
            r2.delete(r6, r7)
        L8b:
            int r13 = r13 + (-1)
            goto L53
        L8e:
            if (r2 == 0) goto L91
            return r2
        L91:
            if (r0 == 0) goto L94
        L93:
            return r0
        L94:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.method.DigitsKeyListener.filter(java.lang.CharSequence, int, int, android.text.Spanned, int, int):java.lang.CharSequence");
    }
}
