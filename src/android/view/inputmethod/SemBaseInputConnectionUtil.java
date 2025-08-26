package android.view.inputmethod;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Editable;
import android.view.View;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SemBaseInputConnectionUtil {
    public static CharSequence convertAllBrackets(CharSequence charSequence, int i, Editable editable, View view) {
        return (isAllBracketChars(charSequence) && isRTLText(editable.toString(), i, view)) ? convertAllBrackets(charSequence) : charSequence;
    }

    /* JADX WARN: Removed duplicated region for block: B:96:0x00d8 A[PHI: r4
      0x00d8: PHI (r4v8 char) = (r4v7 char), (r4v10 char), (r4v10 char), (r4v10 char), (r4v10 char) binds: [B:51:0x0076, B:89:0x00c6, B:90:0x00c8, B:92:0x00ce, B:94:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean isRTLText(String str, int i, View view) {
        char c;
        char cCharAt;
        char cCharAt2;
        char cCharAt3;
        char c2 = 65535;
        if (str != null && str.length() > 0) {
            int iLastIndexOf = i != 0 ? str.lastIndexOf(10, i - 1) : -1;
            for (int i2 = iLastIndexOf < 0 ? 0 : iLastIndexOf + 1; i2 < str.length() && (cCharAt3 = str.charAt(i2)) != '\n'; i2++) {
                byte directionality = Character.getDirectionality(cCharAt3);
                if (directionality == 0 || directionality == 14 || directionality == 15) {
                    c = 0;
                    break;
                }
                if (directionality == 1 || directionality == 2 || directionality == 16 || directionality == 17) {
                    c = 1;
                    break;
                }
            }
            c = 65535;
            for (int i3 = i; i3 < str.length() && (cCharAt2 = str.charAt(i3)) != '\n'; i3++) {
                byte directionality2 = Character.getDirectionality(cCharAt2);
                if (directionality2 == 0 || directionality2 == 14 || directionality2 == 15 || directionality2 == 1 || directionality2 == 2 || directionality2 == 16 || directionality2 == 17 || directionality2 == 3) {
                    c = 65535;
                    break;
                }
            }
            if (c == 65535) {
                for (int i4 = i - 1; i4 >= 0; i4--) {
                    char cCharAt4 = str.charAt(i4);
                    if (cCharAt4 == '\n') {
                        break;
                    }
                    byte directionality3 = Character.getDirectionality(cCharAt4);
                    if (directionality3 == 0 || directionality3 == 14 || directionality3 == 15) {
                        c = 0;
                        break;
                    }
                    if (directionality3 == 1 || directionality3 == 2 || directionality3 == 16 || directionality3 == 17) {
                        c = 1;
                        break;
                    }
                }
                if (c == 65535) {
                    while (i < str.length() && (cCharAt = str.charAt(i)) != '\n') {
                        byte directionality4 = Character.getDirectionality(cCharAt);
                        if (directionality4 == 0 || directionality4 == 14 || directionality4 == 15) {
                            c = 0;
                            break;
                        }
                        if (directionality4 == 1 || directionality4 == 2 || directionality4 == 16 || directionality4 == 17) {
                            c = 1;
                            break;
                        }
                        i++;
                    }
                }
                c2 = (c == 65535 && view != null && view.getLayoutDirection() == 1 && isRtlLanguage()) ? (char) 1 : c;
            }
        } else if (isRtlLanguage() && view != null && view.getLayoutDirection() == 1) {
            return true;
        }
        return c2 == 1;
    }

    private static boolean isAllBracketChars(CharSequence charSequence) {
        String string = charSequence.toString();
        for (int i = 0; i < string.length(); i++) {
            if (!"<>{}[]()«»《》『』「」〔〕【】".contains(Character.valueOf(string.charAt(i)).toString())) {
                return false;
            }
        }
        return true;
    }

    private static String convertAllBrackets(CharSequence charSequence) {
        String string = charSequence.toString();
        int i = 0;
        while (i < string.length()) {
            String string2 = Character.valueOf(string.charAt(i)).toString();
            StringBuilder sb = new StringBuilder();
            sb.append(string.substring(0, i));
            sb.append(convertBracket(string2));
            i++;
            sb.append(string.substring(i));
            string = sb.toString();
        }
        return string;
    }

    private static String convertBracket(CharSequence charSequence) {
        String string = charSequence.toString();
        if ("{".equals(string)) {
            return "}";
        }
        if ("}".equals(string)) {
            return "{";
        }
        if (NavigationBarInflaterView.SIZE_MOD_START.equals(string)) {
            return NavigationBarInflaterView.SIZE_MOD_END;
        }
        if (NavigationBarInflaterView.SIZE_MOD_END.equals(string)) {
            return NavigationBarInflaterView.SIZE_MOD_START;
        }
        if ("<".equals(string)) {
            return ">";
        }
        if (">".equals(string)) {
            return "<";
        }
        if (NavigationBarInflaterView.KEY_CODE_START.equals(string)) {
            return NavigationBarInflaterView.KEY_CODE_END;
        }
        if (NavigationBarInflaterView.KEY_CODE_END.equals(string)) {
            return NavigationBarInflaterView.KEY_CODE_START;
        }
        if (String.valueOf((char) 171).equals(string)) {
            return String.valueOf((char) 187);
        }
        if (String.valueOf((char) 187).equals(string)) {
            return String.valueOf((char) 171);
        }
        if (String.valueOf((char) 12298).equals(string)) {
            return String.valueOf((char) 12299);
        }
        if (String.valueOf((char) 12299).equals(string)) {
            return String.valueOf((char) 12298);
        }
        if ("『".equals(string)) {
            return "』";
        }
        if ("』".equals(string)) {
            return "『";
        }
        if ("「".equals(string)) {
            return "」";
        }
        if ("」".equals(string)) {
            return "「";
        }
        if ("〔".equals(string)) {
            return "〕";
        }
        if ("〕".equals(string)) {
            return "〔";
        }
        return "【".equals(string) ? "】" : "】".equals(string) ? "【" : string;
    }

    private static boolean isRtlLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "ar".equals(language) || "fa".equals(language) || "ur".equals(language) || "iw".equals(language);
    }
}
