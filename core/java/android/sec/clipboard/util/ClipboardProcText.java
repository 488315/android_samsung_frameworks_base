package android.sec.clipboard.util;

public class ClipboardProcText {
    private static final String IMG_BEGIN = "<img";
    private static final String IMG_SRC = "src=\"";
    private static final String TAG = "ClipboardProcText";

    public static String getImgFileNameFromHtml(String sSource) {
        String sSourceOriginal = sSource;
        String sSourceLower = sSourceOriginal.toLowerCase();
        String sResult = "";
        int iIndex = sSourceLower.indexOf(IMG_BEGIN);
        if (iIndex != -1) {
            while (iIndex > -1) {
                String sSourceLower2 = sSourceLower.substring(iIndex);
                String sSourceOriginal2 = sSourceOriginal.substring(iIndex);
                int iSubIndex = sSourceLower2.indexOf(IMG_SRC);
                if (iSubIndex <= 0) {
                    break;
                }
                int iSubIndex2 = iSubIndex + IMG_SRC.length();
                String sSourceLower3 = sSourceLower2.substring(iSubIndex2);
                String sSourceOriginal3 = sSourceOriginal2.substring(iSubIndex2);
                int i1 = sSourceLower3.indexOf("\"");
                sResult = sSourceOriginal3.substring(0, i1);
                sSourceLower = sSourceLower3.substring(i1);
                sSourceOriginal = sSourceOriginal3.substring(i1);
                int resultLength = sResult.length();
                if (resultLength > 0 && resultLength < 255) {
                    break;
                }
                iIndex = sSourceLower.indexOf(IMG_BEGIN);
            }
        } else if (sSourceLower.contains("<iframe")) {
            Log.secD(TAG, "This is using a iframe tag.");
        }
        return sResult;
    }
}
