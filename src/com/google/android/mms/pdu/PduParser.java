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
        PduHeaders parseHeaders = parseHeaders(byteArrayInputStream);
        this.mHeaders = parseHeaders;
        if (parseHeaders == null) {
            return null;
        }
        int octet = parseHeaders.getOctet(140);
        byte[] textString = this.mHeaders.getTextString(132);
        if (!checkMandatoryHeader(this.mHeaders)) {
            log("check mandatory headers failed!");
            return null;
        }
        if (128 == octet || 132 == octet) {
            PduBody parseParts = parseParts(this.mPduDataStream, textString);
            this.mBody = parseParts;
            if (parseParts == null) {
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
            int extractByteValue = extractByteValue(byteArrayInputStream);
            if (extractByteValue >= 32 && extractByteValue <= 127) {
                byteArrayInputStream.reset();
                parseWapString(byteArrayInputStream, 0);
            } else {
                switch (extractByteValue) {
                    case 129:
                    case 130:
                    case 151:
                        EncodedStringValue parseEncodedStringValue = parseEncodedStringValue(byteArrayInputStream);
                        if (parseEncodedStringValue == null) {
                            continue;
                        } else {
                            byte[] textString2 = parseEncodedStringValue.getTextString();
                            if (textString2 != null) {
                                String str = new String(textString2);
                                int indexOf = str.indexOf("/");
                                if (indexOf > 0) {
                                    str = str.substring(0, indexOf);
                                }
                                try {
                                    parseEncodedStringValue.setTextString(str.getBytes());
                                } catch (NullPointerException unused) {
                                    log("null pointer error!");
                                    return null;
                                }
                            }
                            try {
                                pduHeaders.appendEncodedStringValue(parseEncodedStringValue, extractByteValue);
                                break;
                            } catch (NullPointerException unused2) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused3) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
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
                        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setTextString(parseWapString, extractByteValue);
                                break;
                            } catch (NullPointerException unused4) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused5) {
                                log(extractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                    case 132:
                        HashMap hashMap = new HashMap();
                        byte[] parseContentType = parseContentType(byteArrayInputStream, hashMap);
                        if (parseContentType != null) {
                            try {
                                pduHeaders.setTextString(parseContentType, 132);
                            } catch (NullPointerException unused6) {
                                log("null pointer error!");
                            } catch (RuntimeException unused7) {
                                log(extractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                        mStartParam = (byte[]) hashMap.get(153);
                        mTypeParam = (byte[]) hashMap.get(131);
                        z = false;
                        break;
                    case 133:
                    case 142:
                    case 159:
                        try {
                            pduHeaders.setLongInteger(parseLongInteger(byteArrayInputStream), extractByteValue);
                            break;
                        } catch (RuntimeException unused8) {
                            log(extractByteValue + "is not Long-Integer header field!");
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
                        int extractByteValue2 = extractByteValue(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(extractByteValue2, extractByteValue);
                            break;
                        } catch (InvalidHeaderValueException unused9) {
                            log("Set invalid Octet value: " + extractByteValue2 + " into the header filed: " + extractByteValue);
                            return null;
                        } catch (RuntimeException unused10) {
                            log(extractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 135:
                    case 136:
                    case 157:
                        try {
                            parseValueLength(byteArrayInputStream);
                            int extractByteValue3 = extractByteValue(byteArrayInputStream);
                            try {
                                long parseLongInteger = parseLongInteger(byteArrayInputStream);
                                if (129 == extractByteValue3) {
                                    parseLongInteger += System.currentTimeMillis() / 1000;
                                }
                                try {
                                    pduHeaders.setLongInteger(parseLongInteger, extractByteValue);
                                    break;
                                } catch (RuntimeException unused11) {
                                    log(extractByteValue + "is not Long-Integer header field!");
                                    return null;
                                }
                            } catch (RuntimeException unused12) {
                                log(extractByteValue + "is not Long-Integer header field!");
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
                                    int indexOf2 = str2.indexOf("/");
                                    if (indexOf2 > 0) {
                                        str2 = str2.substring(0, indexOf2);
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
                                    log(extractByteValue + "is not Encoded-String-Value header field!");
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
                                log(extractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        } catch (IllegalArgumentException unused18) {
                            log("parseValueLength Exception!");
                            return null;
                        }
                    case 138:
                        byteArrayInputStream.mark(1);
                        int extractByteValue4 = extractByteValue(byteArrayInputStream);
                        if (extractByteValue4 < 128) {
                            byteArrayInputStream.reset();
                            byte[] parseWapString2 = parseWapString(byteArrayInputStream, 0);
                            if (parseWapString2 == null) {
                                break;
                            } else {
                                try {
                                    pduHeaders.setTextString(parseWapString2, 138);
                                    break;
                                } catch (NullPointerException unused19) {
                                    log("null pointer error!");
                                    break;
                                } catch (RuntimeException unused20) {
                                    log(extractByteValue + "is not Text-String header field!");
                                    return null;
                                }
                            }
                        } else if (128 != extractByteValue4) {
                            if (129 != extractByteValue4) {
                                if (130 != extractByteValue4) {
                                    if (131 != extractByteValue4) {
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
                                log(extractByteValue + "is not Text-String header field!");
                                return null;
                            }
                        }
                    case 140:
                        int extractByteValue5 = extractByteValue(byteArrayInputStream);
                        switch (extractByteValue5) {
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
                                    pduHeaders.setOctet(extractByteValue5, extractByteValue);
                                    break;
                                } catch (InvalidHeaderValueException unused23) {
                                    log("Set invalid Octet value: " + extractByteValue5 + " into the header filed: " + extractByteValue);
                                    return null;
                                } catch (RuntimeException unused24) {
                                    log(extractByteValue + "is not Octet header field!");
                                    return null;
                                }
                        }
                    case 141:
                        int parseShortInteger = parseShortInteger(byteArrayInputStream);
                        try {
                            pduHeaders.setOctet(parseShortInteger, 141);
                            break;
                        } catch (InvalidHeaderValueException unused25) {
                            log("Set invalid Octet value: " + parseShortInteger + " into the header filed: " + extractByteValue);
                            return null;
                        } catch (RuntimeException unused26) {
                            log(extractByteValue + "is not Octet header field!");
                            return null;
                        }
                    case 147:
                    case 154:
                    case 166:
                    case 181:
                    case 182:
                        EncodedStringValue parseEncodedStringValue2 = parseEncodedStringValue(byteArrayInputStream);
                        if (parseEncodedStringValue2 == null) {
                            break;
                        } else {
                            try {
                                pduHeaders.setEncodedStringValue(parseEncodedStringValue2, extractByteValue);
                                break;
                            } catch (NullPointerException unused27) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused28) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
                                return null;
                            }
                        }
                    case 150:
                        EncodedStringValue parseEncodedSubjectValue = parseEncodedSubjectValue(byteArrayInputStream);
                        if (parseEncodedSubjectValue != null) {
                            try {
                                pduHeaders.setEncodedStringValue(parseEncodedSubjectValue, extractByteValue);
                                break;
                            } catch (NullPointerException unused29) {
                                log("null pointer error!");
                                break;
                            } catch (RuntimeException unused30) {
                                log(extractByteValue + "is not Encoded-String-Value header field!");
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
                                EncodedStringValue parseEncodedStringValue3 = parseEncodedStringValue(byteArrayInputStream);
                                if (parseEncodedStringValue3 == null) {
                                    break;
                                } else {
                                    try {
                                        pduHeaders.setEncodedStringValue(parseEncodedStringValue3, 160);
                                        break;
                                    } catch (NullPointerException unused31) {
                                        log("null pointer error!");
                                        break;
                                    } catch (RuntimeException unused32) {
                                        log(extractByteValue + "is not Encoded-String-Value header field!");
                                        return null;
                                    }
                                }
                            } catch (RuntimeException unused33) {
                                log(extractByteValue + " is not Integer-Value");
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
                                    log(extractByteValue + "is not Long-Integer header field!");
                                    return null;
                                }
                            } catch (RuntimeException unused36) {
                                log(extractByteValue + " is not Integer-Value");
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
                                log(extractByteValue + " is not Integer-Value");
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
                            pduHeaders.setLongInteger(parseIntegerValue(byteArrayInputStream), extractByteValue);
                            break;
                        } catch (RuntimeException unused41) {
                            log(extractByteValue + "is not Long-Integer header field!");
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
        int parseUnsignedInt = parseUnsignedInt(byteArrayInputStream);
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
            int available = byteArrayInputStream.available();
            byte[] bArr2 = new byte[available];
            byteArrayInputStream.read(bArr2, 0, available);
            pduPart2.setData(bArr2);
            pduBody.addPart(pduPart2);
            return pduBody;
        }
        for (int i = 0; i < parseUnsignedInt; i++) {
            int parseUnsignedInt2 = parseUnsignedInt(byteArrayInputStream);
            int parseUnsignedInt3 = parseUnsignedInt(byteArrayInputStream);
            PduPart pduPart3 = new PduPart();
            int available2 = byteArrayInputStream.available();
            if (available2 <= 0) {
                return null;
            }
            HashMap hashMap = new HashMap();
            byte[] parseContentType = parseContentType(byteArrayInputStream, hashMap);
            if (parseContentType != null) {
                pduPart3.setContentType(parseContentType);
            } else {
                pduPart3.setContentType(PduContentTypes.contentTypes[0].getBytes());
            }
            byte[] bArr3 = (byte[]) hashMap.get(151);
            if (bArr3 != null) {
                pduPart3.setName(bArr3);
            }
            Integer num = (Integer) hashMap.get(129);
            if (num != null) {
                pduPart3.setCharset(num.intValue());
            }
            int available3 = parseUnsignedInt2 - (available2 - byteArrayInputStream.available());
            if (available3 > 0) {
                if (!parsePartHeaders(byteArrayInputStream, pduPart3, available3)) {
                    return null;
                }
            } else if (available3 < 0) {
                return null;
            }
            if (pduPart3.getContentLocation() == null && pduPart3.getName() == null && pduPart3.getFilename() == null && pduPart3.getContentId() == null) {
                pduPart3.setContentLocation(Long.toOctalString(System.currentTimeMillis()).getBytes());
            }
            if (parseUnsignedInt3 > 0) {
                byte[] bArr4 = new byte[parseUnsignedInt3];
                String str3 = new String(pduPart3.getContentType());
                if (byteArrayInputStream.read(bArr4, 0, parseUnsignedInt3) == -1) {
                    return null;
                }
                if (str3.equalsIgnoreCase(ContentType.MULTIPART_ALTERNATIVE)) {
                    PduBody parseParts = parseParts(new ByteArrayInputStream(bArr4), parseContentType);
                    if (parseParts == null) {
                        log("childBody is null");
                    } else {
                        pduPart3 = parseParts.getPart(0);
                    }
                } else {
                    byte[] contentTransferEncoding = pduPart3.getContentTransferEncoding();
                    if (contentTransferEncoding != null) {
                        String str4 = new String(contentTransferEncoding);
                        if (str4.equalsIgnoreCase(PduPart.P_BASE64)) {
                            bArr4 = Base64.decodeBase64(bArr4);
                        } else if (str4.equalsIgnoreCase(PduPart.P_QUOTED_PRINTABLE)) {
                            bArr4 = QuotedPrintable.decodeQuotedPrintable(bArr4);
                        }
                    }
                    if (bArr4 == null) {
                        log("Decode part data error!");
                        return null;
                    }
                    pduPart3.setData(bArr4);
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
        int read = byteArrayInputStream.read();
        if (read == -1) {
            return read;
        }
        int i = 0;
        while ((read & 128) != 0) {
            i = (i << 7) | (read & 127);
            read = byteArrayInputStream.read();
            if (read == -1) {
                return read;
            }
        }
        return (i << 7) | (read & 127);
    }

    protected static int parseValueLength(ByteArrayInputStream byteArrayInputStream) {
        int read = byteArrayInputStream.read() & 255;
        if (read <= 30) {
            return read;
        }
        if (read == 31) {
            return parseUnsignedInt(byteArrayInputStream);
        }
        throw new IllegalArgumentException("Value length > LENGTH_QUOTE!");
    }

    protected static EncodedStringValue parseEncodedStringValue(ByteArrayInputStream byteArrayInputStream) {
        int parseShortInteger;
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        if (read == 0) {
            return null;
        }
        int i = read & 255;
        if (i == 0) {
            return new EncodedStringValue("");
        }
        byteArrayInputStream.reset();
        if (i < 32) {
            try {
                parseValueLength(byteArrayInputStream);
                parseShortInteger = parseShortInteger(byteArrayInputStream);
            } catch (IllegalArgumentException unused) {
                log("parseValueLength Exception!");
                return null;
            }
        } else {
            parseShortInteger = 0;
        }
        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
        try {
            if (parseShortInteger != 0) {
                return new EncodedStringValue(parseShortInteger, parseWapString);
            }
            return new EncodedStringValue(parseWapString);
        } catch (Exception unused2) {
            return null;
        }
    }

    protected static byte[] parseWapString(ByteArrayInputStream byteArrayInputStream, int i) {
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        if (1 == i && 34 == read) {
            byteArrayInputStream.mark(1);
        } else if (i == 0 && 127 == read) {
            byteArrayInputStream.mark(1);
        } else {
            byteArrayInputStream.reset();
        }
        return getWapString(byteArrayInputStream, i);
    }

    protected static byte[] getWapString(ByteArrayInputStream byteArrayInputStream, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = byteArrayInputStream.read();
        while (-1 != read && read != 0) {
            if (i == 2) {
                if (isTokenCharacter(read)) {
                    byteArrayOutputStream.write(read);
                }
            } else if (isText(read)) {
                byteArrayOutputStream.write(read);
            }
            read = byteArrayInputStream.read();
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
        int read = byteArrayInputStream.read() & 255;
        if (read > 8) {
            throw new RuntimeException("Octet count greater than 8 and I can't represent that!");
        }
        long j = 0;
        for (int i = 0; i < read; i++) {
            j = (j << 8) + (byteArrayInputStream.read() & 255);
        }
        return j;
    }

    protected static long parseIntegerValue(ByteArrayInputStream byteArrayInputStream) {
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        if (read > 127) {
            return parseShortInteger(byteArrayInputStream);
        }
        return parseLongInteger(byteArrayInputStream);
    }

    protected static int skipWapValue(ByteArrayInputStream byteArrayInputStream, int i) {
        int read = byteArrayInputStream.read(new byte[i], 0, i);
        if (read < i) {
            return -1;
        }
        return read;
    }

    protected static void parseContentTypeParams(ByteArrayInputStream byteArrayInputStream, HashMap<Integer, Object> hashMap, Integer num) {
        int available;
        int intValue;
        int available2 = byteArrayInputStream.available();
        int intValue2 = num.intValue();
        while (intValue2 > 0) {
            int read = byteArrayInputStream.read();
            intValue2--;
            if (read != 129) {
                if (read != 131) {
                    if (read != 133 && read != 151) {
                        if (read != 153) {
                            if (read != 137) {
                                if (read != 138) {
                                    if (-1 == skipWapValue(byteArrayInputStream, intValue2)) {
                                        Log.e(LOG_TAG, "Corrupt Content-Type");
                                    } else {
                                        intValue2 = 0;
                                    }
                                }
                            }
                        }
                        byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString != null && hashMap != null) {
                            hashMap.put(153, parseWapString);
                        }
                        available = byteArrayInputStream.available();
                        intValue = num.intValue();
                    } else {
                        byte[] parseWapString2 = parseWapString(byteArrayInputStream, 0);
                        if (parseWapString2 != null && hashMap != null) {
                            hashMap.put(151, parseWapString2);
                        }
                        available = byteArrayInputStream.available();
                        intValue = num.intValue();
                    }
                }
                byteArrayInputStream.mark(1);
                int extractByteValue = extractByteValue(byteArrayInputStream);
                byteArrayInputStream.reset();
                if (extractByteValue > 127) {
                    int parseShortInteger = parseShortInteger(byteArrayInputStream);
                    if (parseShortInteger < PduContentTypes.contentTypes.length) {
                        hashMap.put(131, PduContentTypes.contentTypes[parseShortInteger].getBytes());
                    }
                } else {
                    byte[] parseWapString3 = parseWapString(byteArrayInputStream, 0);
                    if (parseWapString3 != null && hashMap != null) {
                        hashMap.put(131, parseWapString3);
                    }
                }
                available = byteArrayInputStream.available();
                intValue = num.intValue();
            } else {
                byteArrayInputStream.mark(1);
                int extractByteValue2 = extractByteValue(byteArrayInputStream);
                byteArrayInputStream.reset();
                if ((extractByteValue2 > 32 && extractByteValue2 < 127) || extractByteValue2 == 0) {
                    byte[] parseWapString4 = parseWapString(byteArrayInputStream, 0);
                    try {
                        hashMap.put(129, Integer.valueOf(CharacterSets.getMibEnumValue(new String(parseWapString4))));
                    } catch (UnsupportedEncodingException e) {
                        Log.e(LOG_TAG, Arrays.toString(parseWapString4), e);
                        hashMap.put(129, 0);
                    }
                } else {
                    int parseIntegerValue = (int) parseIntegerValue(byteArrayInputStream);
                    if (hashMap != null) {
                        hashMap.put(129, Integer.valueOf(parseIntegerValue));
                    }
                }
                available = byteArrayInputStream.available();
                intValue = num.intValue();
            }
            intValue2 = intValue - (available2 - available);
        }
        if (intValue2 != 0) {
            Log.e(LOG_TAG, "Corrupt Content-Type");
        }
    }

    protected static byte[] parseContentType(ByteArrayInputStream byteArrayInputStream, HashMap<Integer, Object> hashMap) {
        byte[] parseWapString;
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        byteArrayInputStream.reset();
        int i = read & 255;
        if (i >= 32) {
            if (i <= 127) {
                return parseWapString(byteArrayInputStream, 0);
            }
            return PduContentTypes.contentTypes[parseShortInteger(byteArrayInputStream)].getBytes();
        }
        try {
            int parseValueLength = parseValueLength(byteArrayInputStream);
            int available = byteArrayInputStream.available();
            if (parseValueLength > available) {
                Log.e(LOG_TAG, "parseContentType: Invalid length " + parseValueLength + " when available bytes are " + available);
                return PduContentTypes.contentTypes[0].getBytes();
            }
            byteArrayInputStream.mark(1);
            int read2 = byteArrayInputStream.read();
            byteArrayInputStream.reset();
            int i2 = read2 & 255;
            if (i2 >= 32 && i2 <= 127) {
                parseWapString = parseWapString(byteArrayInputStream, 0);
            } else {
                if (i2 <= 127) {
                    Log.e(LOG_TAG, "Corrupt content-type");
                    return PduContentTypes.contentTypes[0].getBytes();
                }
                int parseShortInteger = parseShortInteger(byteArrayInputStream);
                if (parseShortInteger < PduContentTypes.contentTypes.length) {
                    parseWapString = PduContentTypes.contentTypes[parseShortInteger].getBytes();
                } else {
                    byteArrayInputStream.reset();
                    parseWapString = parseWapString(byteArrayInputStream, 0);
                }
            }
            int available2 = parseValueLength - (available - byteArrayInputStream.available());
            if (available2 > 0) {
                parseContentTypeParams(byteArrayInputStream, hashMap, Integer.valueOf(available2));
            }
            if (available2 >= 0) {
                return parseWapString;
            }
            Log.e(LOG_TAG, "Corrupt MMS message");
            return PduContentTypes.contentTypes[0].getBytes();
        } catch (IllegalArgumentException unused) {
            log("parseValueLength Exception!");
            return null;
        }
    }

    protected boolean parsePartHeaders(ByteArrayInputStream byteArrayInputStream, PduPart pduPart, int i) {
        int available;
        int available2 = byteArrayInputStream.available();
        int i2 = i;
        while (i2 > 0) {
            int read = byteArrayInputStream.read();
            i2--;
            if (read > 127) {
                if (read == 142) {
                    byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
                    if (parseWapString != null) {
                        pduPart.setContentLocation(parseWapString);
                    }
                    available = byteArrayInputStream.available();
                } else {
                    if (read != 174) {
                        if (read == 192) {
                            byte[] parseWapString2 = parseWapString(byteArrayInputStream, 1);
                            if (parseWapString2 != null) {
                                pduPart.setContentId(parseWapString2);
                            }
                            available = byteArrayInputStream.available();
                        } else if (read != 197) {
                            if (-1 == skipWapValue(byteArrayInputStream, i2)) {
                                Log.e(LOG_TAG, "Corrupt Part headers");
                                return false;
                            }
                            i2 = 0;
                        }
                    }
                    if (this.mParseContentDisposition) {
                        try {
                            int parseValueLength = parseValueLength(byteArrayInputStream);
                            byteArrayInputStream.mark(1);
                            int available3 = byteArrayInputStream.available();
                            int read2 = byteArrayInputStream.read();
                            if (read2 == 128) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_FROM_DATA);
                            } else if (read2 == 129) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_ATTACHMENT);
                            } else if (read2 == 130) {
                                pduPart.setContentDisposition(PduPart.DISPOSITION_INLINE);
                            } else {
                                byteArrayInputStream.reset();
                                pduPart.setContentDisposition(parseWapString(byteArrayInputStream, 0));
                            }
                            if (available3 - byteArrayInputStream.available() < parseValueLength) {
                                if (byteArrayInputStream.read() == 152) {
                                    pduPart.setFilename(parseWapString(byteArrayInputStream, 0));
                                }
                                int available4 = available3 - byteArrayInputStream.available();
                                if (available4 < parseValueLength) {
                                    int i3 = parseValueLength - available4;
                                    byteArrayInputStream.read(new byte[i3], 0, i3);
                                }
                            }
                            available = byteArrayInputStream.available();
                        } catch (IllegalArgumentException unused) {
                            log("parseValueLength Exception!");
                            return false;
                        }
                    } else {
                        continue;
                    }
                }
                i2 = i - (available2 - available);
            } else if (read >= 32 && read <= 127) {
                byte[] parseWapString3 = parseWapString(byteArrayInputStream, 0);
                byte[] parseWapString4 = parseWapString(byteArrayInputStream, 0);
                if (true == PduPart.CONTENT_TRANSFER_ENCODING.equalsIgnoreCase(new String(parseWapString3))) {
                    pduPart.setContentTransferEncoding(parseWapString4);
                }
                available = byteArrayInputStream.available();
                i2 = i - (available2 - available);
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
        int parseShortInteger;
        byteArrayInputStream.mark(1);
        int read = byteArrayInputStream.read();
        if (read != 0 && (i = read & 255) > 0) {
            byteArrayInputStream.reset();
            if (i < 32) {
                try {
                    parseValueLength(byteArrayInputStream);
                    parseShortInteger = parseShortInteger(byteArrayInputStream);
                } catch (IllegalArgumentException unused) {
                    log("parseValueLength Exception!");
                    return null;
                }
            } else {
                parseShortInteger = 0;
            }
            byte[] parseWapString = parseWapString(byteArrayInputStream, 0);
            try {
                if (parseShortInteger != 0) {
                    return new EncodedStringValue(parseShortInteger, parseWapString);
                }
                return new EncodedStringValue(parseWapString);
            } catch (Exception unused2) {
            }
        }
        return null;
    }
}
