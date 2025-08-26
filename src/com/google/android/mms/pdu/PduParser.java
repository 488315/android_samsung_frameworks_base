package com.google.android.mms.pdu;

import android.util.Log;
import com.google.android.mms.ContentType;
import com.google.android.mms.InvalidHeaderValueException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class PduParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final int END_STRING_FLAG = 0;
    private static final int LENGTH_QUOTE = 31;
    private static final boolean LOCAL_LOGV = false;
    private static final String LOG_TAG = "PduParser";
    private static final int LONG_INTEGER_LENGTH_MAX = 8;
    private static final int QUOTE = 127;
    private static final int QUOTED_STRING_FLAG = 34;
    private static final int SHORT_INTEGER_MAX = 127;
    private static final int SHORT_LENGTH_MAX = 30;
    private static final int TEXT_MAX = 127;
    private static final int TEXT_MIN = 32;
    private static final int THE_FIRST_PART = 0;
    private static final int THE_LAST_PART = 1;
    private static final int TYPE_QUOTED_STRING = 1;
    private static final int TYPE_TEXT_STRING = 0;
    private static final int TYPE_TOKEN_STRING = 2;
    private static byte[] mStartParam;
    private static byte[] mTypeParam;
    private PduBody mBody;
    private PduHeaders mHeaders;
    private final boolean mParseContentDisposition;
    private ByteArrayInputStream mPduDataStream;

    protected static boolean isText(int i) {
        return (i >= 32 && i <= 126) || (i >= 128 && i <= 255) || i == 9 || i == 10 || i == 13;
    }

    protected static boolean isTokenCharacter(int i) {
        if (i >= 33 && i <= 126 && i != 34 && i != 44 && i != 47 && i != 123 && i != 125 && i != 40 && i != 41) {
            switch (i) {
                default:
                    switch (i) {
                        case 91:
                        case 92:
                        case 93:
                            break;
                        default:
                            return true;
                    }
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                    return false;
            }
        }
        return false;
    }

    private static void log(String str) {
    }

    public PduParser(byte[] bArr, boolean z) {
        this.mPduDataStream = null;
        this.mHeaders = null;
        this.mBody = null;
        this.mPduDataStream = new ByteArrayInputStream(bArr);
        this.mParseContentDisposition = z;
    }

    public GenericPdu parse() {
        ByteArrayInputStream byteArrayInputStream = this.mPduDataStream;
        if (byteArrayInputStream == null) {
            return null;
        }
        PduHeaders headers = parseHeaders(byteArrayInputStream);
        this.mHeaders = headers;
        if (headers == null) {
            return null;
        }
        int octet = headers.getOctet(140);
        byte[] textString = this.mHeaders.getTextString(132);
        if (!checkMandatoryHeader(this.mHeaders)) {
            log("check mandatory headers failed!");
            return null;
        }
        if (128 == octet || 132 == octet) {
            PduBody parts = parseParts(this.mPduDataStream, textString);
            this.mBody = parts;
            if (parts == null) {
                return null;
            }
            if (new String(textString).equals("text/plain")) {
                this.mHeaders.setTextString(ContentType.MULTIPART_MIXED.getBytes(), 132);
            }
        }
        switch (octet) {
            case 128:
                break;
            case 129:
                break;
            case 130:
                break;
            case 131:
                break;
            case 132:
                RetrieveConf retrieveConf = new RetrieveConf(this.mHeaders, this.mBody);
                byte[] contentType = retrieveConf.getContentType();
                if (contentType != null) {
                    String str = new String(contentType);
                    if (!str.equals(ContentType.MULTIPART_MIXED) && !str.equals(ContentType.MULTIPART_RELATED) && !str.equals("text/plain") && !str.equals(ContentType.MULTIPART_ALTERNATIVE)) {
                        if (str.equals(ContentType.MULTIPART_ALTERNATIVE)) {
                            PduPart part = this.mBody.getPart(0);
                            this.mBody.removeAll();
                            this.mBody.addPart(0, part);
                            break;
                        }
                    }
                }
                break;
            case 133:
                break;
            case 134:
                break;
            case 135:
                break;
            case 136:
                break;
            default:
                log("Parser doesn't support this message type in this version!");
                break;
        }
        return null;
    }

    protected PduHeaders parseHeaders(ByteArrayInputStream byteArrayInputStream) {
        EncodedStringValue encodedStringValue;
        byte[] textString;
        if (byteArrayInputStream == null) {
            return null;
        }
        PduHeaders pduHeaders = new PduHeaders();
        boolean z = true;
        while (z && byteArrayInputStream.available() > 0) {
            byteArrayInputStream.mark(1);
            int iExtractByteValue = extractByteValue(byteArrayInputStream);
            if (iExtractByteValue >= 32 && iExtractByteValue <= 127) {
                byteArrayInputStream.reset();
                parseWapString(byteArrayInputStream, 0);
            } else {
                switch (iExtractByteValue) {
                    case 129:
                    case 130:
                    case 151:
                        EncodedStringValue encodedStringValue2 = parseEncodedStringValue(byteArrayInputStream);
                        if (encodedStringValue2 == null) {
                            continue;
                        } else {
                            byte[] textString2 = encodedStringValue2.getTextString();
                            if (textString2 != null) {
                                String str = new String(textString2);
                                int iIndexOf = str.indexOf("/");
                                if (iIndexOf > 0) {
                                    str = str.substring(0, iIndexOf);
                                }
                                try {
                                    encodedStringValue2.setTextString(str.getBytes());
                                } catch (NullPointerException unused) {
                                    log("null pointer error!");
                                    return null;
                                }
                            }
                            try {
                                pduHeaders.appendEncodedStringValue(encodedStringValue2, iExtractByteValue);
                                break;
                            } catch (NullPointerException unused2) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused3) {
                                log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                    case 131:
                    case 139:
                    case 152:
                    case 158:
                    case 183:
                    case 184:
                    case 185:
                    case 189:
                    case 190:
                        byte[] wapString = parseWapString(byteArrayInputStream, 0);
                        if (wapString == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setTextString(wapString, iExtractByteValue);
                                break;
                            } catch (NullPointerException unused4) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused5) {
                                log(iExtractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                    case 132:
                        HashMap map = new HashMap();
                        byte[] contentType = parseContentType(byteArrayInputStream, map);
                        if (contentType != null) {
                            try {
                                pduHeaders.setTextString(contentType, 132);
                            } catch (NullPointerException unused6) {
                                log("null pointer error!");
                            } catch (RuntimeException unused7) {
                                log(iExtractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                        mStartParam = (byte[]) map.get(153);
                        mTypeParam = (byte[]) map.get(131);
                        z = false;
                        break;
                    case 133:
                    case 142:
                    case 159:
                        try {
                            pduHeaders.setLongInteger(parseLongInteger(byteArrayInputStream), iExtractByteValue);
                            break;
                        } catch (RuntimeException unused8) {
                            log(iExtractByteValue + "is not Long-Integer header field!");
                            return null;
                        }
                    case 134:
                    case 143:
                    case 144:
                    case 145:
                    case 146:
                    case 148:
                    case 149:
                    case 153:
                    case 155:
                    case 156:
                    case 162:
                    case 163:
                    case 165:
                    case 167:
                    case 169:
                    case 171:
                    case 177:
                    case 180:
                    case 186:
                    case 187:
                    case 188:
                    case 191:
                        int iExtractByteValue2 = extractByteValue(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(iExtractByteValue2, iExtractByteValue);
                            break;
                        } catch (InvalidHeaderValueException unused9) {
                            log("Set invalid Octet value: " + iExtractByteValue2 + " into the header filed: " + iExtractByteValue);
                            return null;
                        } catch (RuntimeException unused10) {
                            log(iExtractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 135:
                    case 136:
                    case 157:
                        try {
                            parseValueLength(byteArrayInputStream);
                            int iExtractByteValue3 = extractByteValue(byteArrayInputStream);
                            try {
                                long longInteger = parseLongInteger(byteArrayInputStream);
                                if (129 == iExtractByteValue3) {
                                    longInteger += System.currentTimeMillis() / 1000;
                                }
                                try {
                                    pduHeaders.setLongInteger(longInteger, iExtractByteValue);
                                    break;
                                } catch (RuntimeException unused11) {
                                    log(iExtractByteValue + "is not Long-Integer header field!");
                                    return null;
                                }
                            } catch (RuntimeException unused12) {
                                log(iExtractByteValue + "is not Long-Integer header field!");
                                return null;
                            }
                        } catch (IllegalArgumentException unused13) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 137:
                        try {
                            parseValueLength(byteArrayInputStream);
                            if (128 == extractByteValue(byteArrayInputStream)) {
                                encodedStringValue = parseEncodedStringValue(byteArrayInputStream);
                                if (encodedStringValue != null && (textString = encodedStringValue.getTextString()) != null) {
                                    String str2 = new String(textString);
                                    int iIndexOf2 = str2.indexOf("/");
                                    if (iIndexOf2 > 0) {
                                        str2 = str2.substring(0, iIndexOf2);
                                    }
                                    try {
                                        encodedStringValue.setTextString(str2.getBytes());
                                    } catch (NullPointerException unused14) {
                                        log("null pointer error!");
                                        return null;
                                    }
                                }
                            } else {
                                try {
                                    encodedStringValue = new EncodedStringValue(PduHeaders.FROM_INSERT_ADDRESS_TOKEN_STR.getBytes());
                                } catch (NullPointerException unused15) {
                                    log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                    return null;
                                }
                            }
                            try {
                                pduHeaders.setEncodedStringValue(encodedStringValue, 137);
                                break;
                            } catch (NullPointerException unused16) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused17) {
                                log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        } catch (IllegalArgumentException unused18) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 138:
                        byteArrayInputStream.mark(1);
                        int iExtractByteValue4 = extractByteValue(byteArrayInputStream);
                        if (iExtractByteValue4 < 128) {
                            byteArrayInputStream.reset();
                            byte[] wapString2 = parseWapString(byteArrayInputStream, 0);
                            if (wapString2 == null) {
                                break;
                            } else {
                                try {
                                    pduHeaders.setTextString(wapString2, 138);
                                    break;
                                } catch (NullPointerException unused19) {
                                    log("null pointer error!");
                                    break;
                                } catch (RuntimeException unused20) {
                                    log(iExtractByteValue + "is not Text-String header field!");
                                    return null;
                                }
                            }
                        } else if (128 != iExtractByteValue4) {
                            if (129 != iExtractByteValue4) {
                                if (130 != iExtractByteValue4) {
                                    if (131 != iExtractByteValue4) {
                                        break;
                                    } else {
                                        pduHeaders.setTextString("auto".getBytes(), 138);
                                        break;
                                    }
                                } else {
                                    pduHeaders.setTextString(PduHeaders.MESSAGE_CLASS_INFORMATIONAL_STR.getBytes(), 138);
                                    break;
                                }
                            } else {
                                pduHeaders.setTextString(PduHeaders.MESSAGE_CLASS_ADVERTISEMENT_STR.getBytes(), 138);
                                break;
                            }
                        } else {
                            try {
                                pduHeaders.setTextString(PduHeaders.MESSAGE_CLASS_PERSONAL_STR.getBytes(), 138);
                                break;
                            } catch (NullPointerException unused21) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused22) {
                                log(iExtractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                    case 140:
                        int iExtractByteValue5 = extractByteValue(byteArrayInputStream);
                        switch (iExtractByteValue5) {
                            case 137:
                            case 138:
                            case 139:
                            case 140:
                            case 141:
                            case 142:
                            case 143:
                            case 144:
                            case 145:
                            case 146:
                            case 147:
                            case 148:
                            case 149:
                            case 150:
                            case 151:
                                return null;
                            default:
                                try {
                                    pduHeaders.setOctet(iExtractByteValue5, iExtractByteValue);
                                    break;
                                } catch (InvalidHeaderValueException unused23) {
                                    log("Set invalid Octet value: " + iExtractByteValue5 + " into the header filed: " + iExtractByteValue);
                                    return null;
                                } catch (RuntimeException unused24) {
                                    log(iExtractByteValue + "is not Octet header field!");
                                    return null;
                                }
                        }
                    case 141:
                        int shortInteger = parseShortInteger(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(shortInteger, 141);
                            break;
                        } catch (InvalidHeaderValueException unused25) {
                            log("Set invalid Octet value: " + shortInteger + " into the header filed: " + iExtractByteValue);
                            return null;
                        } catch (RuntimeException unused26) {
                            log(iExtractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 147:
                    case 154:
                    case 166:
                    case 181:
                    case 182:
                        EncodedStringValue encodedStringValue3 = parseEncodedStringValue(byteArrayInputStream);
                        if (encodedStringValue3 == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setEncodedStringValue(encodedStringValue3, iExtractByteValue);
                                break;
                            } catch (NullPointerException unused27) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused28) {
                                log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                    case 150:
                        EncodedStringValue encodedSubjectValue = parseEncodedSubjectValue(byteArrayInputStream);
                        if (encodedSubjectValue != null) {
                            try {
                                pduHeaders.setEncodedStringValue(encodedSubjectValue, iExtractByteValue);
                                break;
                            } catch (NullPointerException unused29) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused30) {
                                log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        } else {
                            log("Subject is null!");
                            break;
                        }
                    case 160:
                        try {
                            parseValueLength(byteArrayInputStream);
                            try {
                                parseIntegerValue(byteArrayInputStream);
                                EncodedStringValue encodedStringValue4 = parseEncodedStringValue(byteArrayInputStream);
                                if (encodedStringValue4 == null) {
                                    break;
                                } else {
                                    try {
                                        pduHeaders.setEncodedStringValue(encodedStringValue4, 160);
                                        break;
                                    } catch (NullPointerException unused31) {
                                        log("null pointer error!");
                                        break;
                                    } catch (RuntimeException unused32) {
                                        log(iExtractByteValue + "is not Encoded-String-Value header field!");
                                        return null;
                                    }
                                }
                            } catch (RuntimeException unused33) {
                                log(iExtractByteValue + " is not Integer-Value");
                                return null;
                            }
                        } catch (IllegalArgumentException unused34) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 161:
                        try {
                            parseValueLength(byteArrayInputStream);
                            try {
                                parseIntegerValue(byteArrayInputStream);
                                try {
                                    pduHeaders.setLongInteger(parseLongInteger(byteArrayInputStream), 161);
                                    break;
                                } catch (RuntimeException unused35) {
                                    log(iExtractByteValue + "is not Long-Integer header field!");
                                    return null;
                                }
                            } catch (RuntimeException unused36) {
                                log(iExtractByteValue + " is not Integer-Value");
                                return null;
                            }
                        } catch (IllegalArgumentException unused37) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 164:
                        try {
                            parseValueLength(byteArrayInputStream);
                            extractByteValue(byteArrayInputStream);
                            parseEncodedStringValue(byteArrayInputStream);
                            break;
                        } catch (IllegalArgumentException unused38) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 168:
                    case 174:
                    case 176:
                    default:
                        log("Unknown header");
                        break;
                    case 170:
                    case 172:
                        try {
                            parseValueLength(byteArrayInputStream);
                            extractByteValue(byteArrayInputStream);
                            try {
                                parseIntegerValue(byteArrayInputStream);
                                break;
                            } catch (RuntimeException unused39) {
                                log(iExtractByteValue + " is not Integer-Value");
                                return null;
                            }
                        } catch (IllegalArgumentException unused40) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 173:
                    case 175:
                    case 179:
                        try {
                            pduHeaders.setLongInteger(parseIntegerValue(byteArrayInputStream), iExtractByteValue);
                            break;
                        } catch (RuntimeException unused41) {
                            log(iExtractByteValue + "is not Long-Integer header field!");
                            return null;
                        }
                    case 178:
                        parseContentType(byteArrayInputStream, null);
                        break;
                }
            }
        }
        return pduHeaders;
    }

    protected PduBody parseParts(ByteArrayInputStream byteArrayInputStream, byte[] bArr) {
        if (byteArrayInputStream == null) {
            return null;
        }
        int unsignedInt = parseUnsignedInt(byteArrayInputStream);
        PduBody pduBody = new PduBody();
        String str = new String(bArr);
        if (!str.equals(ContentType.MULTIPART_MIXED) && !str.equals(ContentType.MULTIPART_RELATED)) {
            if (!str.equals("text/plain")) {
                return null;
            }
            String str2 = new String("<smil><head><layout><root-layout width=\"320px\" height=\"480px\"/><region id=\"Text\" left=\"0\" top=\"320\" width=\"320px\" height=\"160px\" fit=\"meet\"/></layout></head><body><par><text src=\"attach.txt\" region=\"Text\"/></par></body></smil>");
            PduPart pduPart = new PduPart();
            pduPart.setContentId("smil.txt".getBytes());
            pduPart.setContentLocation("smil.txt".getBytes());
            pduPart.setContentType(ContentType.APP_SMIL.getBytes());
            pduPart.setData(str2.getBytes());
            pduBody.addPart(0, pduPart);
            PduPart pduPart2 = new PduPart();
            pduPart2.setContentLocation("attach.txt".getBytes());
            pduPart2.setContentId("attach.txt".getBytes());
            pduPart2.setContentType("text/plain".getBytes());
            int iAvailable = byteArrayInputStream.available();
            byte[] bArr2 = new byte[iAvailable];
            byteArrayInputStream.read(bArr2, 0, iAvailable);
            pduPart2.setData(bArr2);
            pduBody.addPart(pduPart2);
            return pduBody;
        }
        for (int i = 0; i < unsignedInt; i++) {
            int unsignedInt2 = parseUnsignedInt(byteArrayInputStream);
            int unsignedInt3 = parseUnsignedInt(byteArrayInputStream);
            PduPart pduPart3 = new PduPart();
            int iAvailable2 = byteArrayInputStream.available();
            if (iAvailable2 <= 0) {
                return null;
            }
            HashMap map = new HashMap();
            byte[] contentType = parseContentType(byteArrayInputStream, map);
            if (contentType != null) {
                pduPart3.setContentType(contentType);
            } else {
                pduPart3.setContentType(PduContentTypes.contentTypes[0].getBytes());
            }
            byte[] bArr3 = (byte[]) map.get(151);
            if (bArr3 != null) {
                pduPart3.setName(bArr3);
            }
            Integer num = (Integer) map.get(129);
            if (num != null) {
                pduPart3.setCharset(num.intValue());
            }
            int iAvailable3 = unsignedInt2 - (iAvailable2 - byteArrayInputStream.available());
            if (iAvailable3 > 0) {
                if (!parsePartHeaders(byteArrayInputStream, pduPart3, iAvailable3)) {
                    return null;
                }
            } else if (iAvailable3 < 0) {
                return null;
            }
            if (pduPart3.getContentLocation() == null && pduPart3.getName() == null && pduPart3.getFilename() == null && pduPart3.getContentId() == null) {
                pduPart3.setContentLocation(Long.toOctalString(System.currentTimeMillis()).getBytes());
            }
            if (unsignedInt3 > 0) {
                byte[] bArrDecodeQuotedPrintable = new byte[unsignedInt3];
                String str3 = new String(pduPart3.getContentType());
                if (byteArrayInputStream.read(bArrDecodeQuotedPrintable, 0, unsignedInt3) == -1) {
                    return null;
                }
                if (str3.equalsIgnoreCase(ContentType.MULTIPART_ALTERNATIVE)) {
                    PduBody parts = parseParts(new ByteArrayInputStream(bArrDecodeQuotedPrintable), contentType);
                    if (parts == null) {
                        log("childBody is null");
                    } else {
                        pduPart3 = parts.getPart(0);
                    }
                } else {
                    byte[] contentTransferEncoding = pduPart3.getContentTransferEncoding();
                    if (contentTransferEncoding != null) {
                        String str4 = new String(contentTransferEncoding);
                        if (str4.equalsIgnoreCase(PduPart.P_BASE64)) {
                            bArrDecodeQuotedPrintable = Base64.decodeBase64(bArrDecodeQuotedPrintable);
                        } else if (str4.equalsIgnoreCase(PduPart.P_QUOTED_PRINTABLE)) {
                            bArrDecodeQuotedPrintable = QuotedPrintable.decodeQuotedPrintable(bArrDecodeQuotedPrintable);
                        }
                    }
                    if (bArrDecodeQuotedPrintable == null) {
                        log("Decode part data error!");
                        return null;
                    }
                    pduPart3.setData(bArrDecodeQuotedPrintable);
                }
            }
            if (checkPartPosition(pduPart3) == 0) {
                pduBody.addPart(0, pduPart3);
            } else {
                pduBody.addPart(pduPart3);
            }
        }
        return pduBody;
    }

    protected static int parseUnsignedInt(ByteArrayInputStream byteArrayInputStream) {
        int i = byteArrayInputStream.read();
        if (i == -1) {
            return i;
        }
        int i2 = 0;
        while ((i & 128) != 0) {
            i2 = (i2 << 7) | (i & 127);
            i = byteArrayInputStream.read();
            if (i == -1) {
                return i;
            }
        }
        return (i2 << 7) | (i & 127);
    }

    protected static int parseValueLength(ByteArrayInputStream byteArrayInputStream) {
        int i = byteArrayInputStream.read() & 255;
        if (i <= 30) {
            return i;
        }
        if (i == 31) {
            return parseUnsignedInt(byteArrayInputStream);
        }
        throw new IllegalArgumentException("Value length > LENGTH_QUOTE!");
    }

    protected static EncodedStringValue parseEncodedStringValue(ByteArrayInputStream byteArrayInputStream) {
        int shortInteger;
        byteArrayInputStream.mark(1);
        int i = byteArrayInputStream.read();
        if (i == 0) {
            return null;
        }
        int i2 = i & 255;
        if (i2 == 0) {
            return new EncodedStringValue("");
        }
        byteArrayInputStream.reset();
        if (i2 < 32) {
            try {
                parseValueLength(byteArrayInputStream);
                shortInteger = parseShortInteger(byteArrayInputStream);
            } catch (IllegalArgumentException unused) {
                log("parseValueLength Exception!");
                return null;
            }
        } else {
            shortInteger = 0;
        }
        byte[] wapString = parseWapString(byteArrayInputStream, 0);
        try {
            if (shortInteger != 0) {
                return new EncodedStringValue(shortInteger, wapString);
            }
            return new EncodedStringValue(wapString);
        } catch (Exception unused2) {
            return null;
        }
    }

    protected static byte[] parseWapString(ByteArrayInputStream byteArrayInputStream, int i) {
        byteArrayInputStream.mark(1);
        int i2 = byteArrayInputStream.read();
        if (1 == i && 34 == i2) {
            byteArrayInputStream.mark(1);
        } else if (i == 0 && 127 == i2) {
            byteArrayInputStream.mark(1);
        } else {
            byteArrayInputStream.reset();
        }
        return getWapString(byteArrayInputStream, i);
    }

    protected static byte[] getWapString(ByteArrayInputStream byteArrayInputStream, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = byteArrayInputStream.read();
        while (-1 != i2 && i2 != 0) {
            if (i == 2) {
                if (isTokenCharacter(i2)) {
                    byteArrayOutputStream.write(i2);
                }
            } else if (isText(i2)) {
                byteArrayOutputStream.write(i2);
            }
            i2 = byteArrayInputStream.read();
        }
        if (byteArrayOutputStream.size() > 0) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    protected static int extractByteValue(ByteArrayInputStream byteArrayInputStream) {
        return byteArrayInputStream.read() & 255;
    }

    protected static int parseShortInteger(ByteArrayInputStream byteArrayInputStream) {
        return byteArrayInputStream.read() & 127;
    }

    protected static long parseLongInteger(ByteArrayInputStream byteArrayInputStream) {
        int i = byteArrayInputStream.read() & 255;
        if (i > 8) {
            throw new RuntimeException("Octet count greater than 8 and I can't represent that!");
        }
        long j = 0;
        for (int i2 = 0; i2 < i; i2++) {
            j = (j << 8) + (byteArrayInputStream.read() & 255);
        }
        return j;
    }

    protected static long parseIntegerValue(ByteArrayInputStream byteArrayInputStream) {
        byteArrayInputStream.mark(1);
        int i = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        if (i > 127) {
            return parseShortInteger(byteArrayInputStream);
        }
        return parseLongInteger(byteArrayInputStream);
    }

    protected static int skipWapValue(ByteArrayInputStream byteArrayInputStream, int i) {
        int i2 = byteArrayInputStream.read(new byte[i], 0, i);
        if (i2 < i) {
            return -1;
        }
        return i2;
    }

    protected static void parseContentTypeParams(ByteArrayInputStream byteArrayInputStream, HashMap<Integer, Object> map, Integer num) {
        int iAvailable;
        int iIntValue;
        int iAvailable2 = byteArrayInputStream.available();
        int iIntValue2 = num.intValue();
        while (iIntValue2 > 0) {
            int i = byteArrayInputStream.read();
            iIntValue2--;
            if (i != 129) {
                if (i != 131) {
                    if (i != 133 && i != 151) {
                        if (i != 153) {
                            if (i != 137) {
                                if (i != 138) {
                                    if (-1 == skipWapValue(byteArrayInputStream, iIntValue2)) {
                                        Log.e(LOG_TAG, "Corrupt Content-Type");
                                    } else {
                                        iIntValue2 = 0;
                                    }
                                }
                            }
                        }
                        byte[] wapString = parseWapString(byteArrayInputStream, 0);
                        if (wapString != null && map != null) {
                            map.put(153, wapString);
                        }
                        iAvailable = byteArrayInputStream.available();
                        iIntValue = num.intValue();
                    } else {
                        byte[] wapString2 = parseWapString(byteArrayInputStream, 0);
                        if (wapString2 != null && map != null) {
                            map.put(151, wapString2);
                        }
                        iAvailable = byteArrayInputStream.available();
                        iIntValue = num.intValue();
                    }
                }
                byteArrayInputStream.mark(1);
                int iExtractByteValue = extractByteValue(byteArrayInputStream);
                byteArrayInputStream.reset();
                if (iExtractByteValue > 127) {
                    int shortInteger = parseShortInteger(byteArrayInputStream);
                    if (shortInteger < PduContentTypes.contentTypes.length) {
                        map.put(131, PduContentTypes.contentTypes[shortInteger].getBytes());
                    }
                } else {
                    byte[] wapString3 = parseWapString(byteArrayInputStream, 0);
                    if (wapString3 != null && map != null) {
                        map.put(131, wapString3);
                    }
                }
                iAvailable = byteArrayInputStream.available();
                iIntValue = num.intValue();
            } else {
                byteArrayInputStream.mark(1);
                int iExtractByteValue2 = extractByteValue(byteArrayInputStream);
                byteArrayInputStream.reset();
                if ((iExtractByteValue2 > 32 && iExtractByteValue2 < 127) || iExtractByteValue2 == 0) {
                    byte[] wapString4 = parseWapString(byteArrayInputStream, 0);
                    try {
                        map.put(129, Integer.valueOf(CharacterSets.getMibEnumValue(new String(wapString4))));
                    } catch (UnsupportedEncodingException e) {
                        Log.e(LOG_TAG, Arrays.toString(wapString4), e);
                        map.put(129, 0);
                    }
                } else {
                    int integerValue = (int) parseIntegerValue(byteArrayInputStream);
                    if (map != null) {
                        map.put(129, Integer.valueOf(integerValue));
                    }
                }
                iAvailable = byteArrayInputStream.available();
                iIntValue = num.intValue();
            }
            iIntValue2 = iIntValue - (iAvailable2 - iAvailable);
        }
        if (iIntValue2 != 0) {
            Log.e(LOG_TAG, "Corrupt Content-Type");
        }
    }

    protected static byte[] parseContentType(ByteArrayInputStream byteArrayInputStream, HashMap<Integer, Object> map) {
        byte[] wapString;
        byteArrayInputStream.mark(1);
        int i = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        int i2 = i & 255;
        if (i2 >= 32) {
            if (i2 <= 127) {
                return parseWapString(byteArrayInputStream, 0);
            }
            return PduContentTypes.contentTypes[parseShortInteger(byteArrayInputStream)].getBytes();
        }
        try {
            int valueLength = parseValueLength(byteArrayInputStream);
            int iAvailable = byteArrayInputStream.available();
            if (valueLength > iAvailable) {
                Log.e(LOG_TAG, "parseContentType: Invalid length " + valueLength + " when available bytes are " + iAvailable);
                return PduContentTypes.contentTypes[0].getBytes();
            }
            byteArrayInputStream.mark(1);
            int i3 = byteArrayInputStream.read();
            byteArrayInputStream.reset();
            int i4 = i3 & 255;
            if (i4 >= 32 && i4 <= 127) {
                wapString = parseWapString(byteArrayInputStream, 0);
            } else {
                if (i4 <= 127) {
                    Log.e(LOG_TAG, "Corrupt content-type");
                    return PduContentTypes.contentTypes[0].getBytes();
                }
                int shortInteger = parseShortInteger(byteArrayInputStream);
                if (shortInteger < PduContentTypes.contentTypes.length) {
                    wapString = PduContentTypes.contentTypes[shortInteger].getBytes();
                } else {
                    byteArrayInputStream.reset();
                    wapString = parseWapString(byteArrayInputStream, 0);
                }
            }
            int iAvailable2 = valueLength - (iAvailable - byteArrayInputStream.available());
            if (iAvailable2 > 0) {
                parseContentTypeParams(byteArrayInputStream, map, Integer.valueOf(iAvailable2));
            }
            if (iAvailable2 >= 0) {
                return wapString;
            }
            Log.e(LOG_TAG, "Corrupt MMS message");
            return PduContentTypes.contentTypes[0].getBytes();
        } catch (IllegalArgumentException unused) {
            log("parseValueLength Exception!");
            return null;
        }
    }

    protected boolean parsePartHeaders(ByteArrayInputStream byteArrayInputStream, PduPart pduPart, int i) {
        int iAvailable;
        int iAvailable2 = byteArrayInputStream.available();
        int i2 = i;
        while (i2 > 0) {
            int i3 = byteArrayInputStream.read();
            i2--;
            if (i3 > 127) {
                if (i3 == 142) {
                    byte[] wapString = parseWapString(byteArrayInputStream, 0);
                    if (wapString != null) {
                        pduPart.setContentLocation(wapString);
                    }
                    iAvailable = byteArrayInputStream.available();
                } else {
                    if (i3 != 174) {
                        if (i3 == 192) {
                            byte[] wapString2 = parseWapString(byteArrayInputStream, 1);
                            if (wapString2 != null) {
                                pduPart.setContentId(wapString2);
                            }
                            iAvailable = byteArrayInputStream.available();
                        } else if (i3 != 197) {
                            if (-1 == skipWapValue(byteArrayInputStream, i2)) {
                                Log.e(LOG_TAG, "Corrupt Part headers");
                                return false;
                            }
                            i2 = 0;
                        }
                    }
                    if (this.mParseContentDisposition) {
                        try {
                            int valueLength = parseValueLength(byteArrayInputStream);
                            byteArrayInputStream.mark(1);
                            int iAvailable3 = byteArrayInputStream.available();
                            int i4 = byteArrayInputStream.read();
                            if (i4 == 128) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_FROM_DATA);
                            } else if (i4 == 129) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_ATTACHMENT);
                            } else if (i4 == 130) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_INLINE);
                            } else {
                                byteArrayInputStream.reset();
                                pduPart.setContentDisposition(parseWapString(byteArrayInputStream, 0));
                            }
                            if (iAvailable3 - byteArrayInputStream.available() < valueLength) {
                                if (byteArrayInputStream.read() == 152) {
                                    pduPart.setFilename(parseWapString(byteArrayInputStream, 0));
                                }
                                int iAvailable4 = iAvailable3 - byteArrayInputStream.available();
                                if (iAvailable4 < valueLength) {
                                    int i5 = valueLength - iAvailable4;
                                    byteArrayInputStream.read(new byte[i5], 0, i5);
                                }
                            }
                            iAvailable = byteArrayInputStream.available();
                        } catch (IllegalArgumentException unused) {
                            log("parseValueLength Exception!");
                            return false;
                        }
                    } else {
                        continue;
                    }
                }
                i2 = i - (iAvailable2 - iAvailable);
            } else if (i3 >= 32 && i3 <= 127) {
                byte[] wapString3 = parseWapString(byteArrayInputStream, 0);
                byte[] wapString4 = parseWapString(byteArrayInputStream, 0);
                if (true == PduPart.CONTENT_TRANSFER_ENCODING.equalsIgnoreCase(new String(wapString3))) {
                    pduPart.setContentTransferEncoding(wapString4);
                }
                iAvailable = byteArrayInputStream.available();
                i2 = i - (iAvailable2 - iAvailable);
            } else {
                if (-1 == skipWapValue(byteArrayInputStream, i2)) {
                    Log.e(LOG_TAG, "Corrupt Part headers");
                    return false;
                }
                i2 = 0;
            }
        }
        if (i2 == 0) {
            return true;
        }
        Log.e(LOG_TAG, "Corrupt Part headers");
        return false;
    }

    private static int checkPartPosition(PduPart pduPart) {
        byte[] contentType;
        byte[] bArr = mTypeParam;
        if (bArr == null && mStartParam == null) {
            return 1;
        }
        if (mStartParam == null) {
            return (bArr == null || (contentType = pduPart.getContentType()) == null || true != Arrays.equals(mTypeParam, contentType)) ? 1 : 0;
        }
        byte[] contentId = pduPart.getContentId();
        return (contentId == null || true != Arrays.equals(mStartParam, contentId)) ? 1 : 0;
    }

    protected static boolean checkMandatoryHeader(PduHeaders pduHeaders) {
        if (pduHeaders == null) {
            return false;
        }
        int octet = pduHeaders.getOctet(140);
        if (pduHeaders.getOctet(141) == 0) {
            return false;
        }
        switch (octet) {
            case 128:
                if (pduHeaders.getTextString(132) != null && pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 129:
                if (pduHeaders.getOctet(146) != 0 && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 130:
                if (pduHeaders.getTextString(131) != null && -1 != pduHeaders.getLongInteger(136) && pduHeaders.getTextString(138) != null && -1 != pduHeaders.getLongInteger(142) && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 131:
                if (pduHeaders.getOctet(149) != 0 && pduHeaders.getTextString(152) != null) {
                }
                break;
            case 132:
                if (pduHeaders.getTextString(132) != null && -1 != pduHeaders.getLongInteger(133)) {
                }
                break;
            case 133:
                if (pduHeaders.getTextString(152) == null) {
                }
                break;
            case 134:
                if (-1 != pduHeaders.getLongInteger(133) && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(149) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
            case 135:
                if (pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(155) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
            case 136:
                if (-1 != pduHeaders.getLongInteger(133) && pduHeaders.getEncodedStringValue(137) != null && pduHeaders.getTextString(139) != null && pduHeaders.getOctet(155) != 0 && pduHeaders.getEncodedStringValues(151) != null) {
                }
                break;
        }
        return false;
    }

    public PduParser(byte[] bArr) {
        this.mPduDataStream = null;
        this.mHeaders = null;
        this.mBody = null;
        this.mPduDataStream = new ByteArrayInputStream(bArr);
        this.mParseContentDisposition = true;
    }

    protected static EncodedStringValue parseEncodedSubjectValue(ByteArrayInputStream byteArrayInputStream) {
        int i;
        int shortInteger;
        byteArrayInputStream.mark(1);
        int i2 = byteArrayInputStream.read();
        if (i2 != 0 && (i = i2 & 255) > 0) {
            byteArrayInputStream.reset();
            if (i < 32) {
                try {
                    parseValueLength(byteArrayInputStream);
                    shortInteger = parseShortInteger(byteArrayInputStream);
                } catch (IllegalArgumentException unused) {
                    log("parseValueLength Exception!");
                    return null;
                }
            } else {
                shortInteger = 0;
            }
            byte[] wapString = parseWapString(byteArrayInputStream, 0);
            try {
                if (shortInteger != 0) {
                    return new EncodedStringValue(shortInteger, wapString);
                }
                return new EncodedStringValue(wapString);
            } catch (Exception unused2) {
            }
        }
        return null;
    }
}
