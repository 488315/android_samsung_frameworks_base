package android.view.inputmethod;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Editable;
import android.view.View;
import java.util.Locale;

/* loaded from: classes4.dex */
public class SemBaseInputConnectionUtil {
    public static CharSequence convertAllBrackets(CharSequence text, int newCursorPosition, Editable content, View targetView) {
        if (isAllBracketChars(text) && isRTLText(content.toString(), newCursorPosition, targetView)) {
            return convertAllBrackets(text);
        }
        return text;
    }

    /* JADX WARN: Incorrect condition in loop: B:28:0x005c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean isRTLText(String sCurStr, int newCursorPosition, View targetView) {
        int nLastEnterPos;
        byte b;
        byte b2;
        char ch;
        char ch2;
        char ch3;
        int nDirection = -1;
        int nLastEnterPos2 = -1;
        if (sCurStr != null && sCurStr.length() > 0) {
            char c = '\n';
            if (newCursorPosition != 0) {
                nLastEnterPos2 = sCurStr.lastIndexOf(10, newCursorPosition - 1);
            }
            if (nLastEnterPos2 < 0) {
                nLastEnterPos = 0;
            } else {
                nLastEnterPos = nLastEnterPos2 + 1;
            }
            int nIndex = nLastEnterPos;
            while (true) {
                b = 17;
                b2 = 16;
                if (nIndex >= sCurStr.length() || (ch3 = sCurStr.charAt(nIndex)) == c) {
                    break;
                }
                byte directionality = Character.getDirectionality(ch3);
                if (directionality == 0 || directionality == 14 || directionality == 15) {
                    break;
                }
                if (directionality == 1 || directionality == 2 || directionality == 16 || directionality == 17) {
                    break;
                }
                nIndex++;
                c = '\n';
            }
            nDirection = 0;
            int nIndex2 = newCursorPosition;
            while (nIndex2 < nIndex && (ch2 = sCurStr.charAt(nIndex2)) != '\n') {
                byte directionality2 = Character.getDirectionality(ch2);
                if (directionality2 == 0 || directionality2 == 14 || directionality2 == 15 || directionality2 == 1 || directionality2 == 2 || directionality2 == 16 || directionality2 == b || directionality2 == 3) {
                    nDirection = -1;
                    break;
                }
                nIndex2++;
                b = 17;
            }
            if (nDirection == -1) {
                int nIndex3 = newCursorPosition - 1;
                while (nIndex3 >= 0) {
                    char ch4 = sCurStr.charAt(nIndex3);
                    if (ch4 == '\n') {
                        break;
                    }
                    byte directionality3 = Character.getDirectionality(ch4);
                    if (directionality3 == 0 || directionality3 == 14 || directionality3 == 15) {
                        nDirection = 0;
                        break;
                    }
                    if (directionality3 == 1 || directionality3 == 2 || directionality3 == b2 || directionality3 == 17) {
                        nDirection = 1;
                        break;
                    }
                    nIndex3--;
                    b2 = 16;
                }
                if (nDirection == -1) {
                    for (int nIndex4 = newCursorPosition; nIndex4 < sCurStr.length() && (ch = sCurStr.charAt(nIndex4)) != '\n'; nIndex4++) {
                        byte directionality4 = Character.getDirectionality(ch);
                        if (directionality4 == 0 || directionality4 == 14 || directionality4 == 15) {
                            nDirection = 0;
                            break;
                        }
                        if (directionality4 == 1 || directionality4 == 2 || directionality4 == 16 || directionality4 == 17) {
                            nDirection = 1;
                            break;
                        }
                    }
                }
                if (nDirection == -1 && targetView != null && targetView.getLayoutDirection() == 1 && isRtlLanguage()) {
                    nDirection = 1;
                }
            }
        } else if (isRtlLanguage() && targetView != null && targetView.getLayoutDirection() == 1) {
            return true;
        }
        return nDirection == 1;
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
        return "ar".equals(curLanguage) || "fa".equals(curLanguage) || "ur".equals(curLanguage) || "iw".equals(curLanguage);
    }
}
