package android.text.method;

import android.icu.text.DecimalFormatSymbols;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes4.dex */
public abstract class NumberKeyListener extends BaseKeyListener implements InputFilter {
    private static final String DATE_TIME_FORMAT_SYMBOLS = "GyYuUrQqMLlwWdDFgEecabBhHKkjJCmsSAzZOvVXx";
    private static final char SINGLE_QUOTE = '\'';

    protected abstract char[] getAcceptedChars();

    protected int lookup(KeyEvent keyEvent, Spannable spannable) {
        return keyEvent.getMatch(getAcceptedChars(), getMetaState(spannable, keyEvent));
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        char[] acceptedChars = getAcceptedChars();
        int i5 = i;
        while (i5 < i2 && ok(acceptedChars, charSequence.charAt(i5))) {
            i5++;
        }
        if (i5 == i2) {
            return null;
        }
        int i6 = i2 - i;
        if (i6 == 1) {
            return "";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence, i, i2);
        int i7 = i5 - i;
        for (int i8 = i6 - 1; i8 >= i7; i8--) {
            if (!ok(acceptedChars, charSequence.charAt(i8))) {
                spannableStringBuilder.delete(i8, i8 + 1);
            }
        }
        return spannableStringBuilder;
    }

    protected static boolean ok(char[] cArr, char c) {
        for (int length = cArr.length - 1; length >= 0; length--) {
            if (cArr[length] == c) {
                return true;
            }
        }
        return false;
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        int selectionStart = Selection.getSelectionStart(editable);
        int selectionEnd = Selection.getSelectionEnd(editable);
        int iMin = Math.min(selectionStart, selectionEnd);
        int iMax = Math.max(selectionStart, selectionEnd);
        if (iMin < 0 || iMax < 0) {
            Selection.setSelection(editable, 0);
            iMax = 0;
            iMin = 0;
        }
        int iLookup = keyEvent != null ? lookup(keyEvent, editable) : 0;
        int repeatCount = keyEvent != null ? keyEvent.getRepeatCount() : 0;
        if (repeatCount == 0) {
            if (iLookup != 0) {
                if (iMin != iMax) {
                    Selection.setSelection(editable, iMax);
                }
                editable.replace(iMin, iMax, String.valueOf((char) iLookup));
                adjustMetaAfterKeypress(editable);
                return true;
            }
        } else if (iLookup == 48 && repeatCount == 1 && iMin == iMax && iMax > 0) {
            int i2 = iMin - 1;
            if (editable.charAt(i2) == '0') {
                editable.replace(i2, iMax, String.valueOf('+'));
                adjustMetaAfterKeypress(editable);
                return true;
            }
        }
        adjustMetaAfterKeypress(editable);
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    static boolean addDigits(Collection<Character> collection, Locale locale) {
        if (locale == null) {
            return false;
        }
        String[] digitStrings = DecimalFormatSymbols.getInstance(locale).getDigitStrings();
        for (int i = 0; i < 10; i++) {
            if (digitStrings[i].length() > 1) {
                return false;
            }
            collection.add(Character.valueOf(digitStrings[i].charAt(0)));
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002d A[PHI: r2
      0x002d: PHI (r2v2 boolean) = (r2v1 boolean), (r2v4 boolean) binds: [B:12:0x001e, B:16:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static boolean addFormatCharsFromSkeleton(Collection<Character> collection, Locale locale, String str, String str2) {
        if (locale == null) {
            return false;
        }
        String bestDateTimePattern = DateFormat.getBestDateTimePattern(locale, str);
        boolean z = true;
        for (int i = 0; i < bestDateTimePattern.length(); i++) {
            char cCharAt = bestDateTimePattern.charAt(i);
            if (Character.isSurrogate(cCharAt)) {
                return false;
            }
            if (cCharAt == '\'') {
                z = !z;
                if (i != 0 && bestDateTimePattern.charAt(i - 1) == '\'') {
                    if (z) {
                        if (str2.indexOf(cCharAt) == -1) {
                            if (DATE_TIME_FORMAT_SYMBOLS.indexOf(cCharAt) != -1) {
                                return false;
                            }
                            collection.add(Character.valueOf(cCharAt));
                        } else {
                            continue;
                        }
                    } else {
                        collection.add(Character.valueOf(cCharAt));
                    }
                }
            }
        }
        return true;
    }

    static boolean addFormatCharsFromSkeletons(Collection<Character> collection, Locale locale, String[] strArr, String str) {
        for (String str2 : strArr) {
            if (!addFormatCharsFromSkeleton(collection, locale, str2, str)) {
                return false;
            }
        }
        return true;
    }

    static boolean addAmPmChars(Collection<Character> collection, Locale locale) {
        if (locale == null) {
            return false;
        }
        String[] amPmStrings = DateFormat.getIcuDateFormatSymbols(locale).getAmPmStrings();
        for (int i = 0; i < amPmStrings.length; i++) {
            for (int i2 = 0; i2 < amPmStrings[i].length(); i2++) {
                char cCharAt = amPmStrings[i].charAt(i2);
                if (!Character.isBmpCodePoint(cCharAt)) {
                    return false;
                }
                collection.add(Character.valueOf(cCharAt));
            }
        }
        return true;
    }

    static char[] collectionToArray(Collection<Character> collection) {
        char[] cArr = new char[collection.size()];
        Iterator<Character> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            cArr[i] = it.next().charValue();
            i++;
        }
        return cArr;
    }
}
