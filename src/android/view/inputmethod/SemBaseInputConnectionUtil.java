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

    private static boolean isRTLText(String str, int i, View view) {
        char c;
        char charAt;
        char charAt2;
        char charAt3;
        char c2 = 65535;
        if (str != null && str.length() > 0) {
            int lastIndexOf = i != 0 ? str.lastIndexOf(10, i - 1) : -1;
            for (int i2 = lastIndexOf < 0 ? 0 : lastIndexOf + 1; i2 < str.length() && (charAt3 = str.charAt(i2)) != '\n'; i2++) {
                byte directionality = Character.getDirectionality(charAt3);
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
            for (int i3 = i; i3 < str.length() && (charAt2 = str.charAt(i3)) != '\n'; i3++) {
                byte directionality2 = Character.getDirectionality(charAt2);
                if (directionality2 == 0 || directionality2 == 14 || directionality2 == 15 || directionality2 == 1 || directionality2 == 2 || directionality2 == 16 || directionality2 == 17 || directionality2 == 3) {
                    c = 65535;
                    break;
                }
            }
            if (c == 65535) {
                for (int i4 = i - 1; i4 >= 0; i4--) {
                    char charAt4 = str.charAt(i4);
                    if (charAt4 == '\n') {
                        break;
                    }
                    byte directionality3 = Character.getDirectionality(charAt4);
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
                    while (i < str.length() && (charAt = str.charAt(i)) != '\n') {
                        byte directionality4 = Character.getDirectionality(charAt);
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
                if (c == 65535 && view != null && view.getLayoutDirection() == 1 && isRtlLanguage()) {
                    c2 = 1;
                }
            }
            c2 = c;
        } else if (isRtlLanguage() && view != null && view.getLayoutDirection() == 1) {
            return true;
        }
        return c2 == 1;
    }

    private static boolean isAllBracketChars(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        for (int i = 0; i < charSequence2.length(); i++) {
            if (!"<>{}[]()«»《》『』「」〔〕【】".contains(Character.valueOf(charSequence2.charAt(i)).toString())) {
                return false;
            }
        }
        return true;
    }

    private static String convertAllBrackets(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        int i = 0;
        while (i < charSequence2.length()) {
            String ch = Character.valueOf(charSequence2.charAt(i)).toString();
            StringBuilder sb = new StringBuilder();
            sb.append(charSequence2.substring(0, i));
            sb.append(convertBracket(ch));
            i++;
            sb.append(charSequence2.substring(i));
            charSequence2 = sb.toString();
        }
        return charSequence2;
    }

    private static String convertBracket(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        if ("{".equals(charSequence2)) {
            return "}";
        }
        if ("}".equals(charSequence2)) {
            return "{";
        }
        if (NavigationBarInflaterView.SIZE_MOD_START.equals(charSequence2)) {
            return NavigationBarInflaterView.SIZE_MOD_END;
        }
        if (NavigationBarInflaterView.SIZE_MOD_END.equals(charSequence2)) {
            return NavigationBarInflaterView.SIZE_MOD_START;
        }
        if ("<".equals(charSequence2)) {
            return ">";
        }
        if (">".equals(charSequence2)) {
            return "<";
        }
        if (NavigationBarInflaterView.KEY_CODE_START.equals(charSequence2)) {
            return NavigationBarInflaterView.KEY_CODE_END;
        }
        if (NavigationBarInflaterView.KEY_CODE_END.equals(charSequence2)) {
            return NavigationBarInflaterView.KEY_CODE_START;
        }
        if (String.valueOf((char) 171).equals(charSequence2)) {
            return String.valueOf((char) 187);
        }
        if (String.valueOf((char) 187).equals(charSequence2)) {
            return String.valueOf((char) 171);
        }
        if (String.valueOf((char) 12298).equals(charSequence2)) {
            return String.valueOf((char) 12299);
        }
        if (String.valueOf((char) 12299).equals(charSequence2)) {
            return String.valueOf((char) 12298);
        }
        if ("『".equals(charSequence2)) {
            return "』";
        }
        if ("』".equals(charSequence2)) {
            return "『";
        }
        if ("「".equals(charSequence2)) {
            return "」";
        }
        if ("」".equals(charSequence2)) {
            return "「";
        }
        if ("〔".equals(charSequence2)) {
            return "〕";
        }
        if ("〕".equals(charSequence2)) {
            return "〔";
        }
        return "【".equals(charSequence2) ? "】" : "】".equals(charSequence2) ? "【" : charSequence2;
    }

    private static boolean isRtlLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "ar".equals(language) || "fa".equals(language) || "ur".equals(language) || "iw".equals(language);
    }
}
