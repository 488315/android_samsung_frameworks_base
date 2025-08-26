package android.sec.clipboard.util;

/* loaded from: classes3.dex */
public class ClipboardProcText {
    private static final String IMG_BEGIN = "<img";
    private static final String IMG_SRC = "src=\"";
    private static final String TAG = "ClipboardProcText";

    public static String getImgFileNameFromHtml(String str) {
        String lowerCase = str.toLowerCase();
        int iIndexOf = lowerCase.indexOf(IMG_BEGIN);
        String strSubstring = "";
        if (iIndexOf == -1) {
            if (lowerCase.contains("<iframe")) {
                Log.secD(TAG, "This is using a iframe tag.");
            }
            return "";
        }
        while (iIndexOf > -1) {
            String strSubstring2 = lowerCase.substring(iIndexOf);
            String strSubstring3 = str.substring(iIndexOf);
            int iIndexOf2 = strSubstring2.indexOf(IMG_SRC);
            if (iIndexOf2 <= 0) {
                break;
            }
            int i = iIndexOf2 + 5;
            String strSubstring4 = strSubstring2.substring(i);
            String strSubstring5 = strSubstring3.substring(i);
            int iIndexOf3 = strSubstring4.indexOf("\"");
            strSubstring = strSubstring5.substring(0, iIndexOf3);
            lowerCase = strSubstring4.substring(iIndexOf3);
            str = strSubstring5.substring(iIndexOf3);
            int length = strSubstring.length();
            if (length > 0 && length < 255) {
                return strSubstring;
            }
            iIndexOf = lowerCase.indexOf(IMG_BEGIN);
        }
        return strSubstring;
    }
}
