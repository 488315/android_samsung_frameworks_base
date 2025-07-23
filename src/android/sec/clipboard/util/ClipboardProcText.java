package android.sec.clipboard.util;

/* loaded from: classes3.dex */
public class ClipboardProcText {
    private static final String IMG_BEGIN = "<img";
    private static final String IMG_SRC = "src=\"";
    private static final String TAG = "ClipboardProcText";

    public static String getImgFileNameFromHtml(String str) {
        String lowerCase = str.toLowerCase();
        int indexOf = lowerCase.indexOf(IMG_BEGIN);
        String str2 = "";
        if (indexOf == -1) {
            if (lowerCase.contains("<iframe")) {
                Log.secD(TAG, "This is using a iframe tag.");
            }
            return "";
        }
        while (indexOf > -1) {
            String substring = lowerCase.substring(indexOf);
            String substring2 = str.substring(indexOf);
            int indexOf2 = substring.indexOf(IMG_SRC);
            if (indexOf2 <= 0) {
                break;
            }
            int i = indexOf2 + 5;
            String substring3 = substring.substring(i);
            String substring4 = substring2.substring(i);
            int indexOf3 = substring3.indexOf("\"");
            str2 = substring4.substring(0, indexOf3);
            lowerCase = substring3.substring(indexOf3);
            str = substring4.substring(indexOf3);
            int length = str2.length();
            if (length > 0 && length < 255) {
                return str2;
            }
            indexOf = lowerCase.indexOf(IMG_BEGIN);
        }
        return str2;
    }
}
