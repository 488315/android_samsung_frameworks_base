package android.webkit;

import android.compat.Compatibility;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.net.ParseException;
import android.net.Uri;
import android.net.WebAddress;
import android.speech.RecognizerResultsIntent;
import android.telecom.Logging.Session;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class URLUtil {
    static final String ASSET_BASE = "file:///android_asset/";
    static final String CONTENT_BASE = "content:";
    private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*$", 2);
    private static final Pattern DISPOSITION_PATTERN = Pattern.compile("\\s*(\\S+?) # Group 1: parameter name\n\\s*=\\s* # Match equals sign\n(?: # non-capturing group of options\n   '( (?: [^'\\\\] | \\\\. )* )' # Group 2: single-quoted\n | \"( (?: [^\"\\\\] | \\\\. )*  )\" # Group 3: double-quoted\n | ( [^'\"][^;\\s]* ) # Group 4: un-quoted parameter\n)\\s*;? # Optional end semicolon", 4);
    static final String FILE_BASE = "file:";
    private static final String LOGTAG = "webkit";
    static final long PARSE_CONTENT_DISPOSITION_USING_RFC_6266 = 319400769;
    static final String PROXY_BASE = "file:///cookieless_proxy/";
    static final String RESOURCE_BASE = "file:///android_res/";
    private static final boolean TRACE = false;

    public static String guessUrl(String str) {
        if (str.length() == 0 || str.startsWith("about:") || str.startsWith("data:") || str.startsWith(FILE_BASE) || str.startsWith("javascript:")) {
            return str;
        }
        try {
            WebAddress webAddress = new WebAddress(str.endsWith(MediaMetrics.SEPARATOR) ? str.substring(0, str.length() - 1) : str);
            if (webAddress.getHost().indexOf(46) == -1) {
                webAddress.setHost("www." + webAddress.getHost() + ".com");
            }
            return webAddress.toString();
        } catch (ParseException unused) {
            return str;
        }
    }

    public static String composeSearchUrl(String str, String str2, String str3) {
        int iIndexOf = str2.indexOf(str3);
        if (iIndexOf < 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2.substring(0, iIndexOf));
        try {
            sb.append(URLEncoder.encode(str, "utf-8"));
            sb.append(str2.substring(iIndexOf + str3.length()));
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static byte[] decode(byte[] bArr) throws IllegalArgumentException {
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            byte hex = bArr[i];
            if (hex == 37) {
                if (bArr.length - i > 2) {
                    int hex2 = parseHex(bArr[i + 1]) * 16;
                    i += 2;
                    hex = (byte) (hex2 + parseHex(bArr[i]));
                } else {
                    throw new IllegalArgumentException("Invalid format");
                }
            }
            bArr2[i2] = hex;
            i++;
            i2++;
        }
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr2, 0, bArr3, 0, i2);
        return bArr3;
    }

    static boolean verifyURLEncoding(String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int iIndexOf = str.indexOf(37);
        while (iIndexOf >= 0 && iIndexOf < length) {
            if (iIndexOf < length - 2) {
                try {
                    parseHex((byte) str.charAt(iIndexOf + 1));
                    parseHex((byte) str.charAt(iIndexOf + 2));
                    iIndexOf = str.indexOf(37, iIndexOf + 3);
                } catch (IllegalArgumentException unused) {
                }
            }
            return false;
        }
        return true;
    }

    private static int parseHex(byte b) {
        if (b >= 48 && b <= 57) {
            return b - SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90;
        }
        if (b >= 65 && b <= 70) {
            return b - 55;
        }
        if (b >= 97 && b <= 102) {
            return b - 87;
        }
        throw new IllegalArgumentException("Invalid hex char '" + ((int) b) + "'");
    }

    public static boolean isAssetUrl(String str) {
        return str != null && str.startsWith(ASSET_BASE);
    }

    public static boolean isResourceUrl(String str) {
        return str != null && str.startsWith(RESOURCE_BASE);
    }

    @Deprecated
    public static boolean isCookielessProxyUrl(String str) {
        return str != null && str.startsWith(PROXY_BASE);
    }

    public static boolean isFileUrl(String str) {
        return (str == null || !str.startsWith(FILE_BASE) || str.startsWith(ASSET_BASE) || str.startsWith(PROXY_BASE)) ? false : true;
    }

    public static boolean isAboutUrl(String str) {
        return str != null && str.startsWith("about:");
    }

    public static boolean isDataUrl(String str) {
        return str != null && str.startsWith("data:");
    }

    public static boolean isJavaScriptUrl(String str) {
        return str != null && str.startsWith("javascript:");
    }

    public static boolean isHttpUrl(String str) {
        return str != null && str.length() > 6 && str.substring(0, 7).equalsIgnoreCase("http://");
    }

    public static boolean isHttpsUrl(String str) {
        return str != null && str.length() > 7 && str.substring(0, 8).equalsIgnoreCase("https://");
    }

    public static boolean isNetworkUrl(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return isHttpUrl(str) || isHttpsUrl(str);
    }

    public static boolean isContentUrl(String str) {
        return str != null && str.startsWith(CONTENT_BASE);
    }

    public static boolean isValidUrl(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return isAssetUrl(str) || isResourceUrl(str) || isFileUrl(str) || isAboutUrl(str) || isHttpUrl(str) || isHttpsUrl(str) || isJavaScriptUrl(str) || isContentUrl(str);
    }

    public static String stripAnchor(String str) {
        int iIndexOf = str.indexOf(35);
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : str;
    }

    public static String guessFileName(String str, String str2, String str3) {
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.androidOsBuildVanillaIceCream() && Compatibility.isChangeEnabled(PARSE_CONTENT_DISPOSITION_USING_RFC_6266)) {
            return guessFileNameRfc6266(str, str2, str3);
        }
        return guessFileNameRfc2616(str, str2, str3);
    }

    private static String guessFileNameRfc2616(String str, String str2, String str3) {
        String strSubstring;
        String strDecode;
        int iLastIndexOf;
        int iLastIndexOf2;
        String strSubstring2 = null;
        if (str2 != null) {
            strSubstring = parseContentDispositionRfc2616(str2);
            if (strSubstring != null && (iLastIndexOf2 = strSubstring.lastIndexOf(47) + 1) > 0) {
                strSubstring = strSubstring.substring(iLastIndexOf2);
            }
        } else {
            strSubstring = null;
        }
        if (strSubstring == null && (strDecode = Uri.decode(str)) != null) {
            int iIndexOf = strDecode.indexOf(63);
            if (iIndexOf > 0) {
                strDecode = strDecode.substring(0, iIndexOf);
            }
            if (!strDecode.endsWith("/") && (iLastIndexOf = strDecode.lastIndexOf(47) + 1) > 0) {
                strSubstring = strDecode.substring(iLastIndexOf);
            }
        }
        if (strSubstring == null) {
            strSubstring = "downloadfile";
        }
        int iIndexOf2 = strSubstring.indexOf(46);
        if (iIndexOf2 < 0) {
            if (str3 != null && (strSubstring2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(str3)) != null) {
                strSubstring2 = MediaMetrics.SEPARATOR + strSubstring2;
            }
            if (strSubstring2 == null) {
                if (str3 != null && str3.toLowerCase(Locale.ROOT).startsWith("text/")) {
                    if (str3.equalsIgnoreCase("text/html")) {
                        strSubstring2 = ".html";
                    } else {
                        strSubstring2 = ".txt";
                    }
                } else {
                    strSubstring2 = ".bin";
                }
            }
        } else {
            if (str3 != null) {
                String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(strSubstring.substring(strSubstring.lastIndexOf(46) + 1));
                if (mimeTypeFromExtension != null && !mimeTypeFromExtension.equalsIgnoreCase(str3) && (strSubstring2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(str3)) != null) {
                    strSubstring2 = MediaMetrics.SEPARATOR + strSubstring2;
                }
            }
            if (strSubstring2 == null) {
                strSubstring2 = strSubstring.substring(iIndexOf2);
            }
            strSubstring = strSubstring.substring(0, iIndexOf2);
        }
        return strSubstring + strSubstring2;
    }

    private static String guessFileNameRfc6266(String str, String str2, String str3) {
        String filenameSuggestion = getFilenameSuggestion(str, str2);
        String strSuggestExtensionFromMimeType = suggestExtensionFromMimeType(str3);
        if (filenameSuggestion.indexOf(46) < 0) {
            return filenameSuggestion + strSuggestExtensionFromMimeType;
        }
        if (str3 == null || !extensionDifferentFromMimeType(filenameSuggestion, str3)) {
            return filenameSuggestion;
        }
        return filenameSuggestion + strSuggestExtensionFromMimeType;
    }

    private static String getFilenameSuggestion(String str, String str2) {
        String lastPathSegment;
        String filenameFromContentDispositionRfc6266;
        if (str2 != null && (filenameFromContentDispositionRfc6266 = getFilenameFromContentDispositionRfc6266(str2)) != null) {
            return replacePathSeparators(filenameFromContentDispositionRfc6266);
        }
        if (str != null && (lastPathSegment = Uri.parse(str).getLastPathSegment()) != null) {
            return replacePathSeparators(lastPathSegment);
        }
        return "downloadfile";
    }

    private static String replacePathSeparators(String str) {
        return str.replaceAll("/", Session.SESSION_SEPARATION_CHAR_CHILD);
    }

    private static boolean extensionDifferentFromMimeType(String str, String str2) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(str.lastIndexOf(46) + 1));
        return (mimeTypeFromExtension == null || mimeTypeFromExtension.equalsIgnoreCase(str2)) ? false : true;
    }

    private static String suggestExtensionFromMimeType(String str) {
        if (str == null) {
            return ".bin";
        }
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType != null) {
            return MediaMetrics.SEPARATOR + extensionFromMimeType;
        }
        if (str.equalsIgnoreCase("text/html")) {
            return ".html";
        }
        if (!str.toLowerCase(Locale.ROOT).startsWith("text/")) {
            return ".bin";
        }
        return ".txt";
    }

    static String parseContentDisposition(String str) {
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.androidOsBuildVanillaIceCream() && Compatibility.isChangeEnabled(PARSE_CONTENT_DISPOSITION_USING_RFC_6266)) {
            return getFilenameFromContentDispositionRfc6266(str);
        }
        return parseContentDispositionRfc2616(str);
    }

    private static String parseContentDispositionRfc2616(String str) {
        try {
            Matcher matcher = CONTENT_DISPOSITION_PATTERN.matcher(str);
            if (matcher.find()) {
                return matcher.group(2);
            }
            return null;
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    private static String getFilenameFromContentDispositionRfc6266(String str) {
        String strGroup;
        String[] strArrSplit = str.trim().split(NavigationBarInflaterView.GRAVITY_SEPARATOR, 2);
        String extValueString = null;
        if (strArrSplit.length < 2 || RecognizerResultsIntent.URI_SCHEME_INLINE.equalsIgnoreCase(strArrSplit[0].trim())) {
            return null;
        }
        Matcher matcher = DISPOSITION_PATTERN.matcher(strArrSplit[1]);
        String str2 = null;
        while (matcher.find()) {
            String strGroup2 = matcher.group(1);
            if (matcher.group(2) != null) {
                strGroup = removeSlashEscapes(matcher.group(2));
            } else if (matcher.group(3) != null) {
                strGroup = removeSlashEscapes(matcher.group(3));
            } else {
                strGroup = matcher.group(4);
            }
            if (strGroup2 != null && strGroup != null) {
                if ("filename*".equalsIgnoreCase(strGroup2)) {
                    extValueString = parseExtValueString(strGroup);
                } else if ("filename".equalsIgnoreCase(strGroup2)) {
                    str2 = strGroup;
                }
            }
        }
        return extValueString != null ? extValueString : str2;
    }

    private static String removeSlashEscapes(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\\\(.)", "$1");
    }

    private static String parseExtValueString(String str) {
        String[] strArrSplit = str.split("'", 3);
        if (strArrSplit.length < 3) {
            return null;
        }
        String str2 = strArrSplit[0];
        String str3 = strArrSplit[2];
        try {
            Charset charsetForName = Charset.forName(str2);
            return URLDecoder.decode(encodePlusCharacters(str3, charsetForName), charsetForName);
        } catch (RuntimeException unused) {
            return null;
        }
    }

    private static String encodePlusCharacters(String str, Charset charset) {
        StringBuilder sb = new StringBuilder();
        for (byte b : charset.encode("+").array()) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return str.replaceAll("\\+", sb.toString());
    }
}
