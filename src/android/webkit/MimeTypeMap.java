package android.webkit;

import android.text.TextUtils;
import com.google.android.mms.ContentType;
import java.util.regex.Pattern;
import libcore.content.type.MimeMap;

/* loaded from: classes4.dex */
public class MimeTypeMap {
    private static final MimeTypeMap sMimeTypeMap = new MimeTypeMap();

    private MimeTypeMap() {
    }

    public static String getFileExtensionFromUrl(String str) {
        int iLastIndexOf;
        if (!TextUtils.isEmpty(str)) {
            int iLastIndexOf2 = str.lastIndexOf(35);
            if (iLastIndexOf2 > 0) {
                str = str.substring(0, iLastIndexOf2);
            }
            int iLastIndexOf3 = str.lastIndexOf(63);
            if (iLastIndexOf3 > 0) {
                str = str.substring(0, iLastIndexOf3);
            }
            int iLastIndexOf4 = str.lastIndexOf(47);
            if (iLastIndexOf4 >= 0) {
                str = str.substring(iLastIndexOf4 + 1);
            }
            if (!str.isEmpty() && Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", str) && (iLastIndexOf = str.lastIndexOf(46)) >= 0) {
                return str.substring(iLastIndexOf + 1);
            }
            return "";
        }
        return "";
    }

    public boolean hasMimeType(String str) {
        return MimeMap.getDefault().hasMimeType(str);
    }

    public String getMimeTypeFromExtension(String str) {
        return MimeMap.getDefault().guessMimeTypeFromExtension(str);
    }

    private static String mimeTypeFromExtension(String str) {
        return MimeMap.getDefault().guessMimeTypeFromExtension(str);
    }

    public boolean hasExtension(String str) {
        return MimeMap.getDefault().hasExtension(str);
    }

    public String getExtensionFromMimeType(String str) {
        return MimeMap.getDefault().guessExtensionFromMimeType(str);
    }

    String remapGenericMimeType(String str, String str2, String str3) {
        if ("text/plain".equals(str) || "application/octet-stream".equals(str)) {
            String contentDisposition = str3 != null ? URLUtil.parseContentDisposition(str3) : null;
            if (contentDisposition != null) {
                str2 = contentDisposition;
            }
            String mimeTypeFromExtension = getMimeTypeFromExtension(getFileExtensionFromUrl(str2));
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        } else {
            if ("text/vnd.wap.wml".equals(str)) {
                return "text/plain";
            }
            if (ContentType.APP_WAP_XHTML.equals(str)) {
                return ContentType.APP_XHTML;
            }
        }
        return str;
    }

    public static MimeTypeMap getSingleton() {
        return sMimeTypeMap;
    }
}
