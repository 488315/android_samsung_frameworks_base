package android.view.inputmethod;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Editable;
import android.view.View;

import java.util.Locale;

/* loaded from: classes4.dex */
public class SemBaseInputConnectionUtil {
    public static CharSequence convertAllBrackets(
            CharSequence text, int newCursorPosition, Editable content, View targetView) {
        if (isAllBracketChars(text)
                && isRTLText(content.toString(), newCursorPosition, targetView)) {
            return convertAllBrackets(text);
        }
        return text;
    }

    /* JADX WARN: Incorrect condition in loop: B:28:0x005c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean isRTLText(java.lang.String r16, int r17, android.view.View r18) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.inputmethod.SemBaseInputConnectionUtil.isRTLText(java.lang.String,"
                    + " int, android.view.View):boolean");
    }

    private static boolean isAllBracketChars(CharSequence text) {
        String sText = text.toString();
        for (int nIndex = 0; nIndex < sText.length(); nIndex++) {
            CharSequence ch = Character.valueOf(sText.charAt(nIndex)).toString();
            if (!"<>{}[]()«»《》『』「」〔〕【】".contains(ch)) {
                return false;
            }
        }
        return true;
    }

    private static String convertAllBrackets(CharSequence text) {
        String sText = text.toString();
        for (int nIndex = 0; nIndex < sText.length(); nIndex++) {
            CharSequence ch = Character.valueOf(sText.charAt(nIndex)).toString();
            sText = sText.substring(0, nIndex) + convertBracket(ch) + sText.substring(nIndex + 1);
        }
        return sText;
    }

    private static String convertBracket(CharSequence text) {
        String sText = text.toString();
        if ("{".equals(sText)) {
            return "}";
        }
        if ("}".equals(sText)) {
            return "{";
        }
        if (NavigationBarInflaterView.SIZE_MOD_START.equals(sText)) {
            return NavigationBarInflaterView.SIZE_MOD_END;
        }
        if (NavigationBarInflaterView.SIZE_MOD_END.equals(sText)) {
            return NavigationBarInflaterView.SIZE_MOD_START;
        }
        if ("<".equals(sText)) {
            return ">";
        }
        if (">".equals(sText)) {
            return "<";
        }
        if (NavigationBarInflaterView.KEY_CODE_START.equals(sText)) {
            return NavigationBarInflaterView.KEY_CODE_END;
        }
        if (NavigationBarInflaterView.KEY_CODE_END.equals(sText)) {
            return NavigationBarInflaterView.KEY_CODE_START;
        }
        if (String.valueOf((char) 171).equals(sText)) {
            return String.valueOf((char) 187);
        }
        if (String.valueOf((char) 187).equals(sText)) {
            return String.valueOf((char) 171);
        }
        if (String.valueOf((char) 12298).equals(sText)) {
            return String.valueOf((char) 12299);
        }
        if (String.valueOf((char) 12299).equals(sText)) {
            return String.valueOf((char) 12298);
        }
        if ("『".equals(sText)) {
            return "』";
        }
        if ("』".equals(sText)) {
            return "『";
        }
        if ("「".equals(sText)) {
            return "」";
        }
        if ("」".equals(sText)) {
            return "「";
        }
        if ("〔".equals(sText)) {
            return "〕";
        }
        if ("〕".equals(sText)) {
            return "〔";
        }
        if ("【".equals(sText)) {
            return "】";
        }
        if ("】".equals(sText)) {
            return "【";
        }
        return sText;
    }

    private static boolean isRtlLanguage() {
        Locale locale = Locale.getDefault();
        String curLanguage = locale.getLanguage();
        return "ar".equals(curLanguage)
                || "fa".equals(curLanguage)
                || "ur".equals(curLanguage)
                || "iw".equals(curLanguage);
    }
}
