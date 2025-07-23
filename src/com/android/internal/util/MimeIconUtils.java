package com.android.internal.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import com.android.internal.R;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.widget.MessagingMessage;
import com.google.android.mms.ContentType;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.Locale;
import java.util.Objects;
import libcore.content.type.MimeMap;

/* loaded from: classes4.dex */
public class MimeIconUtils {
    private static final ArrayMap<String, ContentResolver.MimeTypeInfo> sCache = new ArrayMap<>();

    private static ContentResolver.MimeTypeInfo buildTypeInfo(String str, int i, int i2, int i3) {
        String string;
        Resources system = Resources.getSystem();
        String guessExtensionFromMimeType = MimeMap.getDefault().guessExtensionFromMimeType(str);
        if (!TextUtils.isEmpty(guessExtensionFromMimeType) && i3 != -1) {
            string = system.getString(i3, guessExtensionFromMimeType.toUpperCase(Locale.US));
        } else {
            string = system.getString(i2);
        }
        return new ContentResolver.MimeTypeInfo(Icon.createWithResource(system, i), string, string);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static ContentResolver.MimeTypeInfo buildTypeInfo(String str) {
        char c;
        str.hashCode();
        switch (str.hashCode()) {
            case -2135180893:
                if (str.equals("application/vnd.stardivision.calc")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2135135086:
                if (str.equals("application/vnd.stardivision.draw")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -2035614749:
                if (str.equals("application/vnd.google-apps.spreadsheet")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1988437312:
                if (str.equals("application/x-x509-ca-cert")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1917350260:
                if (str.equals("text/x-literate-haskell")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1883861089:
                if (str.equals("application/rss+xml")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1808693885:
                if (str.equals("text/x-pascal")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1777056778:
                if (str.equals("application/vnd.oasis.opendocument.image")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1747277413:
                if (str.equals("application/vnd.sun.xml.writer.template")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1719571662:
                if (str.equals("application/vnd.oasis.opendocument.text")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1628346451:
                if (str.equals("application/vnd.sun.xml.writer")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1590813831:
                if (str.equals("application/vnd.sun.xml.calc.template")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1506009513:
                if (str.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.template")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1386165903:
                if (str.equals("application/x-kpresenter")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1348236371:
                if (str.equals("application/x-deb")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1348228591:
                if (str.equals("application/x-lha")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1348228026:
                if (str.equals("application/x-lzh")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1348228010:
                if (str.equals("application/x-lzx")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1348221103:
                if (str.equals("application/x-tar")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1326989846:
                if (str.equals("application/x-shockwave-flash")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1316922187:
                if (str.equals("application/vnd.oasis.opendocument.text-template")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1296467268:
                if (str.equals("application/atom+xml")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1294595255:
                if (str.equals("inode/directory")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -1248334925:
                if (str.equals("application/pdf")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -1248333084:
                if (str.equals("application/rar")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -1248326952:
                if (str.equals("application/xml")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -1248325150:
                if (str.equals("application/zip")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -1190438973:
                if (str.equals("application/x-pkcs7-signature")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1143717099:
                if (str.equals("application/x-pkcs7-certreqresp")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1082243251:
                if (str.equals("text/html")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1073633483:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1071817359:
                if (str.equals("application/vnd.ms-powerpoint")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1050893613:
                if (str.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -1033484950:
                if (str.equals("application/vnd.sun.xml.draw.template")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -1004747231:
                if (str.equals("text/css")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1004727243:
                if (str.equals("text/xml")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -958424608:
                if (str.equals("text/calendar")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case -951557661:
                if (str.equals("application/vnd.google-apps.presentation")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -779959281:
                if (str.equals("application/vnd.sun.xml.calc")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -779913474:
                if (str.equals("application/vnd.sun.xml.draw")) {
                    c = DateFormat.QUOTE;
                    break;
                }
                c = 65535;
                break;
            case -723118015:
                if (str.equals("application/x-javascript")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -676675015:
                if (str.equals("application/vnd.oasis.opendocument.text-web")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -479218428:
                if (str.equals("application/vnd.sun.xml.writer.global")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case -427343476:
                if (str.equals("application/x-webarchive-xml")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case -396757341:
                if (str.equals("application/vnd.sun.xml.impress.template")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case -366307023:
                if (str.equals("application/vnd.ms-excel")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case -261480694:
                if (str.equals("text/x-chdr")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case -261469704:
                if (str.equals("text/x-csrc")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case -261439913:
                if (str.equals("text/x-dsrc")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -261278343:
                if (str.equals("text/x-java")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case -228136375:
                if (str.equals("application/x-pkcs7-mime")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case -221944004:
                if (str.equals("application/x-font-ttf")) {
                    c = '3';
                    break;
                }
                c = 65535;
                break;
            case -109382304:
                if (str.equals("application/vnd.oasis.opendocument.spreadsheet-template")) {
                    c = '4';
                    break;
                }
                c = 65535;
                break;
            case -43923783:
                if (str.equals("application/gzip")) {
                    c = '5';
                    break;
                }
                c = 65535;
                break;
            case -43840953:
                if (str.equals("application/json")) {
                    c = '6';
                    break;
                }
                c = 65535;
                break;
            case 26919318:
                if (str.equals("application/x-iso9660-image")) {
                    c = '7';
                    break;
                }
                c = 65535;
                break;
            case 81142075:
                if (str.equals("application/vnd.android.package-archive")) {
                    c = '8';
                    break;
                }
                c = 65535;
                break;
            case 163679941:
                if (str.equals("application/pgp-signature")) {
                    c = '9';
                    break;
                }
                c = 65535;
                break;
            case 180207563:
                if (str.equals("application/x-stuffit")) {
                    c = ShortcutConstants.SERVICES_SEPARATOR;
                    break;
                }
                c = 65535;
                break;
            case 245790645:
                if (str.equals("application/vnd.google-apps.drawing")) {
                    c = ';';
                    break;
                }
                c = 65535;
                break;
            case 262346941:
                if (str.equals("text/x-vcalendar")) {
                    c = '<';
                    break;
                }
                c = 65535;
                break;
            case 302189274:
                if (str.equals(DocumentsContract.Document.MIME_TYPE_DIR)) {
                    c = '=';
                    break;
                }
                c = 65535;
                break;
            case 302663708:
                if (str.equals("application/ecmascript")) {
                    c = '>';
                    break;
                }
                c = 65535;
                break;
            case 363965503:
                if (str.equals("application/x-rar-compressed")) {
                    c = '?';
                    break;
                }
                c = 65535;
                break;
            case 394650567:
                if (str.equals("application/pgp-keys")) {
                    c = '@';
                    break;
                }
                c = 65535;
                break;
            case 428819984:
                if (str.equals("application/vnd.oasis.opendocument.graphics")) {
                    c = DateFormat.CAPITAL_AM_PM;
                    break;
                }
                c = 65535;
                break;
            case 501428239:
                if (str.equals(ContactsContract.Contacts.CONTENT_VCARD_TYPE)) {
                    c = 'B';
                    break;
                }
                c = 65535;
                break;
            case 571050671:
                if (str.equals("application/vnd.stardivision.writer-global")) {
                    c = 'C';
                    break;
                }
                c = 65535;
                break;
            case 603849904:
                if (str.equals(ContentType.APP_XHTML)) {
                    c = 'D';
                    break;
                }
                c = 65535;
                break;
            case 641141505:
                if (str.equals("application/x-texinfo")) {
                    c = DateFormat.DAY;
                    break;
                }
                c = 65535;
                break;
            case 669516689:
                if (str.equals("application/vnd.stardivision.impress")) {
                    c = 'F';
                    break;
                }
                c = 65535;
                break;
            case 694663701:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.template")) {
                    c = 'G';
                    break;
                }
                c = 65535;
                break;
            case 717553764:
                if (str.equals("application/vnd.google-apps.document")) {
                    c = 'H';
                    break;
                }
                c = 65535;
                break;
            case 822609188:
                if (str.equals("text/vcard")) {
                    c = 'I';
                    break;
                }
                c = 65535;
                break;
            case 822849473:
                if (str.equals("text/x-csh")) {
                    c = 'J';
                    break;
                }
                c = 65535;
                break;
            case 822865318:
                if (str.equals("text/x-tcl")) {
                    c = 'K';
                    break;
                }
                c = 65535;
                break;
            case 822865392:
                if (str.equals("text/x-tex")) {
                    c = DateFormat.STANDALONE_MONTH;
                    break;
                }
                c = 65535;
                break;
            case 859118878:
                if (str.equals("application/x-abiword")) {
                    c = DateFormat.MONTH;
                    break;
                }
                c = 65535;
                break;
            case 904647503:
                if (str.equals("application/msword")) {
                    c = PhoneNumberUtils.WILD;
                    break;
                }
                c = 65535;
                break;
            case 1043583697:
                if (str.equals("application/x-pkcs7-certificates")) {
                    c = 'O';
                    break;
                }
                c = 65535;
                break;
            case 1060806194:
                if (str.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.template")) {
                    c = 'P';
                    break;
                }
                c = 65535;
                break;
            case 1154415139:
                if (str.equals("application/x-font")) {
                    c = 'Q';
                    break;
                }
                c = 65535;
                break;
            case 1154449330:
                if (str.equals("application/x-gtar")) {
                    c = 'R';
                    break;
                }
                c = 65535;
                break;
            case 1239557416:
                if (str.equals("application/x-pkcs7-crl")) {
                    c = 'S';
                    break;
                }
                c = 65535;
                break;
            case 1255211837:
                if (str.equals("text/x-haskell")) {
                    c = 'T';
                    break;
                }
                c = 65535;
                break;
            case 1283455191:
                if (str.equals("application/x-apple-diskimage")) {
                    c = 'U';
                    break;
                }
                c = 65535;
                break;
            case 1305955842:
                if (str.equals("application/x-debian-package")) {
                    c = 'V';
                    break;
                }
                c = 65535;
                break;
            case 1377360791:
                if (str.equals("application/vnd.oasis.opendocument.graphics-template")) {
                    c = 'W';
                    break;
                }
                c = 65535;
                break;
            case 1383977381:
                if (str.equals("application/vnd.sun.xml.impress")) {
                    c = 'X';
                    break;
                }
                c = 65535;
                break;
            case 1431987873:
                if (str.equals("application/x-kword")) {
                    c = 'Y';
                    break;
                }
                c = 65535;
                break;
            case 1432260414:
                if (str.equals("application/x-latex")) {
                    c = 'Z';
                    break;
                }
                c = 65535;
                break;
            case 1436962847:
                if (str.equals("application/vnd.oasis.opendocument.presentation")) {
                    c = '[';
                    break;
                }
                c = 65535;
                break;
            case 1440428940:
                if (str.equals("application/javascript")) {
                    c = '\\';
                    break;
                }
                c = 65535;
                break;
            case 1454024983:
                if (str.equals("application/x-7z-compressed")) {
                    c = ']';
                    break;
                }
                c = 65535;
                break;
            case 1461751133:
                if (str.equals("application/vnd.oasis.opendocument.text-master")) {
                    c = '^';
                    break;
                }
                c = 65535;
                break;
            case 1502452311:
                if (str.equals("application/font-woff")) {
                    c = '_';
                    break;
                }
                c = 65535;
                break;
            case 1536912403:
                if (str.equals("application/x-object")) {
                    c = '`';
                    break;
                }
                c = 65535;
                break;
            case 1573656544:
                if (str.equals("application/x-pkcs12")) {
                    c = DateFormat.AM_PM;
                    break;
                }
                c = 65535;
                break;
            case 1577426419:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.slideshow")) {
                    c = 'b';
                    break;
                }
                c = 65535;
                break;
            case 1637222218:
                if (str.equals("application/x-kspread")) {
                    c = 'c';
                    break;
                }
                c = 65535;
                break;
            case 1643664935:
                if (str.equals("application/vnd.oasis.opendocument.spreadsheet")) {
                    c = DateFormat.DATE;
                    break;
                }
                c = 65535;
                break;
            case 1673742401:
                if (str.equals("application/vnd.stardivision.writer")) {
                    c = 'e';
                    break;
                }
                c = 65535;
                break;
            case 1709755138:
                if (str.equals("application/x-font-woff")) {
                    c = 'f';
                    break;
                }
                c = 65535;
                break;
            case 1851895234:
                if (str.equals("application/x-webarchive")) {
                    c = 'g';
                    break;
                }
                c = 65535;
                break;
            case 1868769095:
                if (str.equals("application/x-quicktimeplayer")) {
                    c = DateFormat.HOUR;
                    break;
                }
                c = 65535;
                break;
            case 1948418893:
                if (str.equals("application/mac-binhex40")) {
                    c = 'i';
                    break;
                }
                c = 65535;
                break;
            case 1969663169:
                if (str.equals("application/rdf+xml")) {
                    c = 'j';
                    break;
                }
                c = 65535;
                break;
            case 1993842850:
                if (str.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    c = DateFormat.HOUR_OF_DAY;
                    break;
                }
                c = 65535;
                break;
            case 2041423923:
                if (str.equals("application/x-x509-user-cert")) {
                    c = 'l';
                    break;
                }
                c = 65535;
                break;
            case 2062084266:
                if (str.equals("text/x-c++hdr")) {
                    c = DateFormat.MINUTE;
                    break;
                }
                c = 65535;
                break;
            case 2062095256:
                if (str.equals("text/x-c++src")) {
                    c = 'n';
                    break;
                }
                c = 65535;
                break;
            case 2132236175:
                if (str.equals("text/javascript")) {
                    c = 'o';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 2:
            case 11:
            case '&':
            case '4':
            case 'c':
            case 'd':
                return buildTypeInfo(str, R.drawable.ic_doc_spreadsheet, R.string.mime_type_spreadsheet, R.string.mime_type_spreadsheet_ext);
            case 1:
            case 7:
            case '!':
            case '\'':
            case ';':
            case 'A':
            case 'W':
                return buildTypeInfo(str, R.drawable.ic_doc_image, R.string.mime_type_image, R.string.mime_type_image_ext);
            case 3:
            case 27:
            case 28:
            case '2':
            case '9':
            case '@':
            case 'O':
            case 'S':
            case 'a':
            case 'l':
                return buildTypeInfo(str, R.drawable.ic_doc_certificate, R.string.mime_type_generic, R.string.mime_type_generic_ext);
            case 4:
            case 5:
            case 6:
            case 21:
            case 25:
            case 29:
            case '\"':
            case '#':
            case '(':
            case '.':
            case '/':
            case '0':
            case '1':
            case '6':
            case '>':
            case 'D':
            case 'E':
            case 'J':
            case 'K':
            case 'L':
            case 'T':
            case 'Z':
            case '\\':
            case '`':
            case 'j':
            case 'm':
            case 'n':
            case 'o':
                return buildTypeInfo(str, R.drawable.ic_doc_codes, R.string.mime_type_document, R.string.mime_type_document_ext);
            case '\b':
            case '\t':
            case '\n':
            case 20:
            case ')':
            case '*':
            case 'C':
            case 'H':
            case 'M':
            case 'Y':
            case '^':
            case 'e':
                return buildTypeInfo(str, R.drawable.ic_doc_document, R.string.mime_type_document, R.string.mime_type_document_ext);
            case '\f':
            case '-':
            case 'k':
                return buildTypeInfo(str, R.drawable.ic_doc_excel, R.string.mime_type_spreadsheet, R.string.mime_type_spreadsheet_ext);
            case '\r':
            case '%':
            case ',':
            case 'F':
            case 'X':
            case '[':
                return buildTypeInfo(str, R.drawable.ic_doc_presentation, R.string.mime_type_presentation, R.string.mime_type_presentation_ext);
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 24:
            case 26:
            case '+':
            case '5':
            case '7':
            case ':':
            case '?':
            case 'R':
            case 'U':
            case 'V':
            case ']':
            case 'g':
            case 'i':
                return buildTypeInfo(str, R.drawable.ic_doc_compressed, R.string.mime_type_compressed, R.string.mime_type_compressed_ext);
            case 19:
            case 'h':
                return buildTypeInfo(str, R.drawable.ic_doc_video, R.string.mime_type_video, R.string.mime_type_video_ext);
            case 22:
            case '=':
                return buildTypeInfo(str, R.drawable.ic_doc_folder, R.string.mime_type_folder, -1);
            case 23:
                return buildTypeInfo(str, R.drawable.ic_doc_pdf, R.string.mime_type_document, R.string.mime_type_document_ext);
            case 30:
            case 31:
            case 'G':
            case 'b':
                return buildTypeInfo(str, R.drawable.ic_doc_powerpoint, R.string.mime_type_presentation, R.string.mime_type_presentation_ext);
            case ' ':
            case 'N':
            case 'P':
                return buildTypeInfo(str, R.drawable.ic_doc_word, R.string.mime_type_document, R.string.mime_type_document_ext);
            case '$':
            case '<':
                return buildTypeInfo(str, R.drawable.ic_doc_event, R.string.mime_type_generic, R.string.mime_type_generic_ext);
            case '3':
            case 'Q':
            case '_':
            case 'f':
                return buildTypeInfo(str, R.drawable.ic_doc_font, R.string.mime_type_generic, R.string.mime_type_generic_ext);
            case '8':
                return buildTypeInfo(str, R.drawable.ic_doc_apk, R.string.mime_type_apk, -1);
            case 'B':
            case 'I':
                return buildTypeInfo(str, R.drawable.ic_doc_contact, R.string.mime_type_generic, R.string.mime_type_generic_ext);
            default:
                return buildGenericTypeInfo(str);
        }
    }

    private static ContentResolver.MimeTypeInfo buildGenericTypeInfo(String str) {
        if (str.startsWith("audio/")) {
            return buildTypeInfo(str, R.drawable.ic_doc_audio, R.string.mime_type_audio, R.string.mime_type_audio_ext);
        }
        if (str.startsWith(BnRConstants.VIDEO_DIR_PATH)) {
            return buildTypeInfo(str, R.drawable.ic_doc_video, R.string.mime_type_video, R.string.mime_type_video_ext);
        }
        if (str.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
            return buildTypeInfo(str, R.drawable.ic_doc_image, R.string.mime_type_image, R.string.mime_type_image_ext);
        }
        if (str.startsWith("text/")) {
            return buildTypeInfo(str, R.drawable.ic_doc_text, R.string.mime_type_document, R.string.mime_type_document_ext);
        }
        MimeMap mimeMap = MimeMap.getDefault();
        String guessMimeTypeFromExtension = mimeMap.guessMimeTypeFromExtension(mimeMap.guessExtensionFromMimeType(str));
        if (guessMimeTypeFromExtension != null && !Objects.equals(str, guessMimeTypeFromExtension)) {
            return buildTypeInfo(guessMimeTypeFromExtension);
        }
        return buildTypeInfo(str, R.drawable.ic_doc_generic, R.string.mime_type_generic, R.string.mime_type_generic_ext);
    }

    public static ContentResolver.MimeTypeInfo getTypeInfo(String str) {
        ContentResolver.MimeTypeInfo mimeTypeInfo;
        String lowerCase = str.toLowerCase(Locale.US);
        ArrayMap<String, ContentResolver.MimeTypeInfo> arrayMap = sCache;
        synchronized (arrayMap) {
            mimeTypeInfo = arrayMap.get(lowerCase);
            if (mimeTypeInfo == null) {
                mimeTypeInfo = buildTypeInfo(lowerCase);
                arrayMap.put(lowerCase, mimeTypeInfo);
            }
        }
        return mimeTypeInfo;
    }
}
