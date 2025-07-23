package android.media;

import android.mtp.MtpConstants;
import android.text.format.DateFormat;
import com.android.internal.widget.MessagingMessage;
import com.google.android.mms.ContentType;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.HashMap;
import java.util.Locale;
import libcore.content.type.MimeMap;

/* loaded from: classes2.dex */
public class MediaFile {

    @Deprecated
    private static final int FIRST_AUDIO_FILE_TYPE = 1;

    @Deprecated
    private static final int LAST_AUDIO_FILE_TYPE = 10;

    @Deprecated
    private static final HashMap<String, MediaFileType> sFileTypeMap = new HashMap<>();

    @Deprecated
    private static final HashMap<String, Integer> sFileTypeToFormatMap = new HashMap<>();
    private static final HashMap<String, Integer> sMimeTypeToFormatMap = new HashMap<>();
    private static final HashMap<Integer, String> sFormatToMimeTypeMap = new HashMap<>();

    @Deprecated
    static void addFileType(String str, int i, String str2) {
    }

    @Deprecated
    public static MediaFileType getFileType(String str) {
        return null;
    }

    @Deprecated
    public static int getFileTypeForMimeType(String str) {
        return 0;
    }

    @Deprecated
    public static boolean isAudioFileType(int i) {
        return false;
    }

    @Deprecated
    public static boolean isDrmFileType(int i) {
        return false;
    }

    @Deprecated
    public static boolean isImageFileType(int i) {
        return false;
    }

    @Deprecated
    public static boolean isPlayListFileType(int i) {
        return false;
    }

    @Deprecated
    public static boolean isVideoFileType(int i) {
        return false;
    }

    @Deprecated
    public static class MediaFileType {
        public final int fileType;
        public final String mimeType;

        MediaFileType(int i, String str) {
            this.fileType = i;
            this.mimeType = str;
        }
    }

    static {
        addFileType(12297, "audio/mpeg");
        addFileType(12296, ContentType.AUDIO_X_WAV);
        addFileType(MtpConstants.FORMAT_WMA, ContentType.AUDIO_WMA);
        addFileType(MtpConstants.FORMAT_OGG, ContentType.AUDIO_OGG2);
        addFileType(MtpConstants.FORMAT_AAC, ContentType.AUDIO_AAC);
        addFileType(MtpConstants.FORMAT_FLAC, "audio/flac");
        addFileType(12295, "audio/x-aiff");
        addFileType(MtpConstants.FORMAT_MP2, "audio/mpeg");
        addFileType(12299, "video/mpeg");
        addFileType(MtpConstants.FORMAT_MP4_CONTAINER, "video/mp4");
        addFileType(MtpConstants.FORMAT_3GP_CONTAINER, "video/3gpp");
        addFileType(MtpConstants.FORMAT_3GP_CONTAINER, "video/3gpp2");
        addFileType(12298, "video/avi");
        addFileType(MtpConstants.FORMAT_WMV, "video/x-ms-wmv");
        addFileType(12300, "video/x-ms-asf");
        addFileType(MtpConstants.FORMAT_EXIF_JPEG, ContentType.IMAGE_JPEG);
        addFileType(MtpConstants.FORMAT_GIF, ContentType.IMAGE_GIF);
        addFileType(MtpConstants.FORMAT_PNG, ContentType.IMAGE_PNG);
        addFileType(MtpConstants.FORMAT_BMP, ContentType.IMAGE_X_MS_BMP);
        addFileType(MtpConstants.FORMAT_HEIF, "image/heif");
        addFileType(MtpConstants.FORMAT_DNG, "image/x-adobe-dng");
        addFileType(MtpConstants.FORMAT_TIFF, "image/tiff");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-canon-cr2");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-nikon-nrw");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-sony-arw");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-panasonic-rw2");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-olympus-orf");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-pentax-pef");
        addFileType(MtpConstants.FORMAT_TIFF, "image/x-samsung-srw");
        addFileType(MtpConstants.FORMAT_TIFF_EP, "image/tiff");
        addFileType(MtpConstants.FORMAT_TIFF_EP, "image/x-nikon-nef");
        addFileType(MtpConstants.FORMAT_JP2, "image/jp2");
        addFileType(MtpConstants.FORMAT_JPX, "image/jpx");
        addFileType(MtpConstants.FORMAT_M3U_PLAYLIST, "audio/x-mpegurl");
        addFileType(MtpConstants.FORMAT_PLS_PLAYLIST, "audio/x-scpls");
        addFileType(MtpConstants.FORMAT_WPL_PLAYLIST, "application/vnd.ms-wpl");
        addFileType(MtpConstants.FORMAT_ASX_PLAYLIST, "video/x-ms-asf");
        addFileType(12292, "text/plain");
        addFileType(12293, "text/html");
        addFileType(MtpConstants.FORMAT_XML_DOCUMENT, "text/xml");
        addFileType(MtpConstants.FORMAT_MS_WORD_DOCUMENT, "application/msword");
        addFileType(MtpConstants.FORMAT_MS_WORD_DOCUMENT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        addFileType(MtpConstants.FORMAT_MS_EXCEL_SPREADSHEET, "application/vnd.ms-excel");
        addFileType(MtpConstants.FORMAT_MS_EXCEL_SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        addFileType(MtpConstants.FORMAT_MS_POWERPOINT_PRESENTATION, "application/vnd.ms-powerpoint");
        addFileType(MtpConstants.FORMAT_MS_POWERPOINT_PRESENTATION, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }

    private static void addFileType(int i, String str) {
        HashMap<String, Integer> hashMap = sMimeTypeToFormatMap;
        if (!hashMap.containsKey(str)) {
            hashMap.put(str, Integer.valueOf(i));
        }
        HashMap<Integer, String> hashMap2 = sFormatToMimeTypeMap;
        if (hashMap2.containsKey(Integer.valueOf(i))) {
            return;
        }
        hashMap2.put(Integer.valueOf(i), str);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static boolean isDocumentMimeType(String str) {
        if (str == null) {
            return false;
        }
        String normalizeMimeType = normalizeMimeType(str);
        if (normalizeMimeType.startsWith("text/")) {
            return true;
        }
        String lowerCase = normalizeMimeType.toLowerCase(Locale.ROOT);
        lowerCase.hashCode();
        char c = 65535;
        switch (lowerCase.hashCode()) {
            case -2135180893:
                if (lowerCase.equals("application/vnd.stardivision.calc")) {
                    c = 0;
                    break;
                }
                break;
            case -2135135086:
                if (lowerCase.equals("application/vnd.stardivision.draw")) {
                    c = 1;
                    break;
                }
                break;
            case -2134883067:
                if (lowerCase.equals("application/vnd.stardivision.mail")) {
                    c = 2;
                    break;
                }
                break;
            case -2134882730:
                if (lowerCase.equals("application/vnd.stardivision.math")) {
                    c = 3;
                    break;
                }
                break;
            case -2008589971:
                if (lowerCase.equals("application/epub+zip")) {
                    c = 4;
                    break;
                }
                break;
            case -1782746503:
                if (lowerCase.equals("application/vnd.oasis.opendocument.chart")) {
                    c = 5;
                    break;
                }
                break;
            case -1765899696:
                if (lowerCase.equals("application/vnd.stardivision.chart")) {
                    c = 6;
                    break;
                }
                break;
            case -1747277413:
                if (lowerCase.equals("application/vnd.sun.xml.writer.template")) {
                    c = 7;
                    break;
                }
                break;
            case -1719571662:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text")) {
                    c = '\b';
                    break;
                }
                break;
            case -1653115602:
                if (lowerCase.equals("application/vnd.ms-excel.sheet.macroenabled.12")) {
                    c = '\t';
                    break;
                }
                break;
            case -1628346451:
                if (lowerCase.equals("application/vnd.sun.xml.writer")) {
                    c = '\n';
                    break;
                }
                break;
            case -1590813831:
                if (lowerCase.equals("application/vnd.sun.xml.calc.template")) {
                    c = 11;
                    break;
                }
                break;
            case -1506009513:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.template")) {
                    c = '\f';
                    break;
                }
                break;
            case -1316922187:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-template")) {
                    c = '\r';
                    break;
                }
                break;
            case -1293459259:
                if (lowerCase.equals("application/vnd.ms-word.document.macroenabled.12")) {
                    c = 14;
                    break;
                }
                break;
            case -1248334925:
                if (lowerCase.equals("application/pdf")) {
                    c = 15;
                    break;
                }
                break;
            case -1248332507:
                if (lowerCase.equals("application/rtf")) {
                    c = 16;
                    break;
                }
                break;
            case -1095881539:
                if (lowerCase.equals("application/vnd.ms-powerpoint.slideshow.macroenabled.12")) {
                    c = 17;
                    break;
                }
                break;
            case -1073633483:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                    c = 18;
                    break;
                }
                break;
            case -1071817359:
                if (lowerCase.equals("application/vnd.ms-powerpoint")) {
                    c = 19;
                    break;
                }
                break;
            case -1050893613:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    c = 20;
                    break;
                }
                break;
            case -1033484950:
                if (lowerCase.equals("application/vnd.sun.xml.draw.template")) {
                    c = 21;
                    break;
                }
                break;
            case -943935167:
                if (lowerCase.equals("application/vnd.oasis.opendocument.formula")) {
                    c = 22;
                    break;
                }
                break;
            case -878977693:
                if (lowerCase.equals("application/vnd.ms-excel.template.macroenabled.12")) {
                    c = 23;
                    break;
                }
                break;
            case -779959281:
                if (lowerCase.equals("application/vnd.sun.xml.calc")) {
                    c = 24;
                    break;
                }
                break;
            case -779913474:
                if (lowerCase.equals("application/vnd.sun.xml.draw")) {
                    c = 25;
                    break;
                }
                break;
            case -779661118:
                if (lowerCase.equals("application/vnd.sun.xml.math")) {
                    c = 26;
                    break;
                }
                break;
            case -676675015:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-web")) {
                    c = 27;
                    break;
                }
                break;
            case -481744190:
                if (lowerCase.equals("application/x-mspublisher")) {
                    c = 28;
                    break;
                }
                break;
            case -479218428:
                if (lowerCase.equals("application/vnd.sun.xml.writer.global")) {
                    c = 29;
                    break;
                }
                break;
            case -396757341:
                if (lowerCase.equals("application/vnd.sun.xml.impress.template")) {
                    c = 30;
                    break;
                }
                break;
            case -366307023:
                if (lowerCase.equals("application/vnd.ms-excel")) {
                    c = 31;
                    break;
                }
                break;
            case -300783143:
                if (lowerCase.equals("application/vnd.ms-excel.sheet.binary.macroenabled.12")) {
                    c = ' ';
                    break;
                }
                break;
            case -291851672:
                if (lowerCase.equals("application/vnd.oasis.opendocument.presentation-template")) {
                    c = '!';
                    break;
                }
                break;
            case -151068779:
                if (lowerCase.equals("application/vnd.ms-powerpoint.addin.macroenabled.12")) {
                    c = '\"';
                    break;
                }
                break;
            case -109382304:
                if (lowerCase.equals("application/vnd.oasis.opendocument.spreadsheet-template")) {
                    c = '#';
                    break;
                }
                break;
            case 65167651:
                if (lowerCase.equals("application/vnd.ms-powerpoint.template.macroenabled.12")) {
                    c = '$';
                    break;
                }
                break;
            case 428819984:
                if (lowerCase.equals("application/vnd.oasis.opendocument.graphics")) {
                    c = '%';
                    break;
                }
                break;
            case 571050671:
                if (lowerCase.equals("application/vnd.stardivision.writer-global")) {
                    c = '&';
                    break;
                }
                break;
            case 669516689:
                if (lowerCase.equals("application/vnd.stardivision.impress")) {
                    c = DateFormat.QUOTE;
                    break;
                }
                break;
            case 694663701:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.template")) {
                    c = '(';
                    break;
                }
                break;
            case 776322612:
                if (lowerCase.equals("application/vnd.stardivision.impress-packed")) {
                    c = ')';
                    break;
                }
                break;
            case 904647503:
                if (lowerCase.equals("application/msword")) {
                    c = '*';
                    break;
                }
                break;
            case 1060806194:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.template")) {
                    c = '+';
                    break;
                }
                break;
            case 1377360791:
                if (lowerCase.equals("application/vnd.oasis.opendocument.graphics-template")) {
                    c = ',';
                    break;
                }
                break;
            case 1383644708:
                if (lowerCase.equals("application/vnd.ms-word.template.macroenabled.12")) {
                    c = '-';
                    break;
                }
                break;
            case 1383977381:
                if (lowerCase.equals("application/vnd.sun.xml.impress")) {
                    c = '.';
                    break;
                }
                break;
            case 1436962847:
                if (lowerCase.equals("application/vnd.oasis.opendocument.presentation")) {
                    c = '/';
                    break;
                }
                break;
            case 1461751133:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-master")) {
                    c = '0';
                    break;
                }
                break;
            case 1577426419:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.slideshow")) {
                    c = '1';
                    break;
                }
                break;
            case 1643664935:
                if (lowerCase.equals("application/vnd.oasis.opendocument.spreadsheet")) {
                    c = '2';
                    break;
                }
                break;
            case 1673742401:
                if (lowerCase.equals("application/vnd.stardivision.writer")) {
                    c = '3';
                    break;
                }
                break;
            case 1750539587:
                if (lowerCase.equals("application/vnd.ms-powerpoint.presentation.macroenabled.12")) {
                    c = '4';
                    break;
                }
                break;
            case 1993842850:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    c = '5';
                    break;
                }
                break;
            case 2094058325:
                if (lowerCase.equals("application/vnd.ms-excel.addin.macroenabled.12")) {
                    c = '6';
                    break;
                }
                break;
            case 2117577984:
                if (lowerCase.equals("application/vnd.oasis.opendocument.database")) {
                    c = '7';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
                return true;
            default:
                return false;
        }
    }

    public static boolean isExifMimeType(String str) {
        return isImageMimeType(str);
    }

    public static boolean isAudioMimeType(String str) {
        return normalizeMimeType(str).startsWith("audio/");
    }

    public static boolean isVideoMimeType(String str) {
        return normalizeMimeType(str).startsWith(BnRConstants.VIDEO_DIR_PATH);
    }

    public static boolean isImageMimeType(String str) {
        return normalizeMimeType(str).startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    public static boolean isPlayListMimeType(String str) {
        String normalizeMimeType = normalizeMimeType(str);
        normalizeMimeType.hashCode();
        switch (normalizeMimeType) {
            case "audio/x-scpls":
            case "application/x-mpegurl":
            case "application/vnd.apple.mpegurl":
            case "audio/mpegurl":
            case "audio/x-mpegurl":
            case "application/vnd.ms-wpl":
                return true;
            default:
                return false;
        }
    }

    public static boolean isDrmMimeType(String str) {
        return normalizeMimeType(str).equals("application/x-android-drm-fl");
    }

    public static String getFileTitle(String str) {
        int i;
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf >= 0 && (i = lastIndexOf + 1) < str.length()) {
            str = str.substring(i);
        }
        int lastIndexOf2 = str.lastIndexOf(46);
        return lastIndexOf2 > 0 ? str.substring(0, lastIndexOf2) : str;
    }

    public static String getFileExtension(String str) {
        int lastIndexOf;
        if (str != null && (lastIndexOf = str.lastIndexOf(46)) >= 0) {
            return str.substring(lastIndexOf + 1);
        }
        return null;
    }

    public static String getMimeType(String str, int i) {
        String mimeTypeForFile = getMimeTypeForFile(str);
        return !"application/octet-stream".equals(mimeTypeForFile) ? mimeTypeForFile : getMimeTypeForFormatCode(i);
    }

    public static String getMimeTypeForFile(String str) {
        String guessMimeTypeFromExtension = MimeMap.getDefault().guessMimeTypeFromExtension(getFileExtension(str));
        return guessMimeTypeFromExtension != null ? guessMimeTypeFromExtension : "application/octet-stream";
    }

    public static String getMimeTypeForFormatCode(int i) {
        String str = sFormatToMimeTypeMap.get(Integer.valueOf(i));
        return str != null ? str : "application/octet-stream";
    }

    public static int getFormatCode(String str, String str2) {
        int formatCodeForMimeType = getFormatCodeForMimeType(str2);
        return formatCodeForMimeType != 12288 ? formatCodeForMimeType : getFormatCodeForFile(str);
    }

    public static int getFormatCodeForFile(String str) {
        return getFormatCodeForMimeType(getMimeTypeForFile(str));
    }

    public static int getFormatCodeForMimeType(String str) {
        if (str == null) {
            return 12288;
        }
        HashMap<String, Integer> hashMap = sMimeTypeToFormatMap;
        Integer num = hashMap.get(str);
        if (num != null) {
            return num.intValue();
        }
        String normalizeMimeType = normalizeMimeType(str);
        Integer num2 = hashMap.get(normalizeMimeType);
        if (num2 != null) {
            return num2.intValue();
        }
        if (normalizeMimeType.startsWith("audio/")) {
            return 47360;
        }
        return normalizeMimeType.startsWith(BnRConstants.VIDEO_DIR_PATH) ? MtpConstants.FORMAT_UNDEFINED_VIDEO : normalizeMimeType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX) ? 14336 : 12288;
    }

    private static String normalizeMimeType(String str) {
        String guessMimeTypeFromExtension;
        MimeMap mimeMap = MimeMap.getDefault();
        String guessExtensionFromMimeType = mimeMap.guessExtensionFromMimeType(str);
        return (guessExtensionFromMimeType == null || (guessMimeTypeFromExtension = mimeMap.guessMimeTypeFromExtension(guessExtensionFromMimeType)) == null) ? str != null ? str : "application/octet-stream" : guessMimeTypeFromExtension;
    }
}
