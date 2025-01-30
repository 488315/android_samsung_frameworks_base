package com.google.android.mms.pdu;

import android.provider.Telephony;
import android.util.Log;
import com.google.android.mms.ContentType;
import com.google.android.mms.InvalidHeaderValueException;
import com.samsung.android.feature.SemCscFeature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes5.dex */
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
  private final boolean mParseContentDisposition;
  private ByteArrayInputStream mPduDataStream;
  private static byte[] mTypeParam = null;
  private static byte[] mStartParam = null;
  private static boolean mEnableMmsServerTime = true;
  private PduHeaders mHeaders = null;
  private PduBody mBody = null;

  public PduParser(byte[] pduDataStream, boolean parseContentDisposition) {
    this.mPduDataStream = null;
    this.mPduDataStream = new ByteArrayInputStream(pduDataStream);
    this.mParseContentDisposition = parseContentDisposition;
    SemCscFeature cscFeature = SemCscFeature.getInstance();
    String displayMmsTimeAs = cscFeature.getString("CscFeature_Message_DisplayMmsTimeAs");
    mEnableMmsServerTime = isServerTime(displayMmsTimeAs, true);
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
    int messageType = parseHeaders.getOctet(140);
    byte[] contType = this.mHeaders.getTextString(132);
    if (!checkMandatoryHeader(this.mHeaders)) {
      log("check mandatory headers failed!");
      return null;
    }
    if (128 == messageType || 132 == messageType) {
      PduBody parseParts = parseParts(this.mPduDataStream, contType);
      this.mBody = parseParts;
      if (parseParts == null) {
        return null;
      }
      if (new String(contType).equals("text/plain")) {
        this.mHeaders.setTextString(ContentType.MULTIPART_MIXED.getBytes(), 132);
      }
    }
    switch (messageType) {
      case 128:
        SendReq sendReq = new SendReq(this.mHeaders, this.mBody);
        break;
      case 129:
        SendConf sendConf = new SendConf(this.mHeaders);
        break;
      case 130:
        NotificationInd notificationInd = new NotificationInd(this.mHeaders);
        break;
      case 131:
        NotifyRespInd notifyRespInd = new NotifyRespInd(this.mHeaders);
        break;
      case 132:
        RetrieveConf retrieveConf = new RetrieveConf(this.mHeaders, this.mBody);
        byte[] contentType = retrieveConf.getContentType();
        if (contentType != null) {
          String ctTypeStr = new String(contentType);
          if (!ctTypeStr.equals(ContentType.MULTIPART_MIXED)
              && !ctTypeStr.equals(ContentType.MULTIPART_RELATED)
              && !ctTypeStr.equals("text/plain")
              && !ctTypeStr.equals(ContentType.MULTIPART_ALTERNATIVE)) {
            if (ctTypeStr.equals(ContentType.MULTIPART_ALTERNATIVE)) {
              PduPart firstPart = this.mBody.getPart(0);
              this.mBody.removeAll();
              this.mBody.addPart(0, firstPart);
              break;
            }
          }
        }
        break;
      case 133:
        AcknowledgeInd acknowledgeInd = new AcknowledgeInd(this.mHeaders);
        break;
      case 134:
        DeliveryInd deliveryInd = new DeliveryInd(this.mHeaders);
        break;
      case 135:
        ReadRecInd readRecInd = new ReadRecInd(this.mHeaders);
        break;
      case 136:
        ReadOrigInd readOrigInd = new ReadOrigInd(this.mHeaders);
        break;
      default:
        log("Parser doesn't support this message type in this version!");
        break;
    }
    return null;
  }

  protected PduHeaders parseHeaders(ByteArrayInputStream pduDataStream) {
    String str;
    EncodedStringValue from;
    byte[] address;
    String str2;
    if (pduDataStream == null) {
      return null;
    }
    PduHeaders headers = new PduHeaders();
    boolean keepParsing = true;
    while (keepParsing && pduDataStream.available() > 0) {
      pduDataStream.mark(1);
      int headerField = extractByteValue(pduDataStream);
      if (headerField >= 32 && headerField <= 127) {
        pduDataStream.reset();
        parseWapString(pduDataStream, 0);
      } else {
        switch (headerField) {
          case 129:
          case 130:
          case 151:
            EncodedStringValue value = parseEncodedStringValue(pduDataStream);
            if (value == null) {
              continue;
            } else {
              byte[] address2 = value.getTextString();
              if (address2 != null) {
                String str3 = new String(address2);
                int endIndex = str3.indexOf("/");
                if (endIndex <= 0) {
                  str = str3;
                } else {
                  str = str3.substring(0, endIndex);
                }
                try {
                  value.setTextString(str.getBytes());
                } catch (NullPointerException e) {
                  log("null pointer error!");
                  return null;
                }
              }
              try {
                headers.appendEncodedStringValue(value, headerField);
                break;
              } catch (NullPointerException e2) {
                log("null pointer error!");
                break;
              } catch (RuntimeException e3) {
                log(headerField + "is not Encoded-String-Value header field!");
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
            byte[] value2 = parseWapString(pduDataStream, 0);
            if (value2 == null) {
              break;
            } else {
              try {
                headers.setTextString(value2, headerField);
                break;
              } catch (NullPointerException e4) {
                log("null pointer error!");
                break;
              } catch (RuntimeException e5) {
                log(headerField + "is not Text-String header field!");
                return null;
              }
            }
          case 132:
            HashMap<Integer, Object> map = new HashMap<>();
            byte[] contentType = parseContentType(pduDataStream, map);
            if (contentType != null) {
              try {
                headers.setTextString(contentType, 132);
              } catch (NullPointerException e6) {
                log("null pointer error!");
              } catch (RuntimeException e7) {
                log(headerField + "is not Text-String header field!");
                return null;
              }
            }
            mStartParam = (byte[]) map.get(153);
            mTypeParam = (byte[]) map.get(131);
            keepParsing = false;
            break;
          case 133:
            try {
              long value3 = parseLongInteger(pduDataStream);
              if (!mEnableMmsServerTime) {
                value3 = System.currentTimeMillis() / 1000;
                log("mEnableMmsServerTime = " + mEnableMmsServerTime + " Time value = " + value3);
              }
              headers.setLongInteger(value3, headerField);
              break;
            } catch (RuntimeException e8) {
              log(headerField + "is not Long-Integer header field!");
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
            int value4 = extractByteValue(pduDataStream);
            try {
              headers.setOctet(value4, headerField);
              break;
            } catch (InvalidHeaderValueException e9) {
              log("Set invalid Octet value: " + value4 + " into the header filed: " + headerField);
              return null;
            } catch (RuntimeException e10) {
              log(headerField + "is not Octet header field!");
              return null;
            }
          case 135:
          case 136:
          case 157:
            try {
              parseValueLength(pduDataStream);
              int token = extractByteValue(pduDataStream);
              try {
                long timeValue = parseLongInteger(pduDataStream);
                if (129 == token) {
                  timeValue += System.currentTimeMillis() / 1000;
                }
                try {
                  headers.setLongInteger(timeValue, headerField);
                  break;
                } catch (RuntimeException e11) {
                  log(headerField + "is not Long-Integer header field!");
                  return null;
                }
              } catch (RuntimeException e12) {
                log(headerField + "is not Long-Integer header field!");
                return null;
              }
            } catch (IllegalArgumentException e13) {
              log("parseValueLength Exception!");
              return null;
            }
          case 137:
            try {
              parseValueLength(pduDataStream);
              int fromToken = extractByteValue(pduDataStream);
              if (128 == fromToken) {
                from = parseEncodedStringValue(pduDataStream);
                if (from != null && (address = from.getTextString()) != null) {
                  String str4 = new String(address);
                  int endIndex2 = str4.indexOf("/");
                  if (endIndex2 <= 0) {
                    str2 = str4;
                  } else {
                    str2 = str4.substring(0, endIndex2);
                  }
                  try {
                    from.setTextString(str2.getBytes());
                  } catch (NullPointerException e14) {
                    log("null pointer error!");
                    return null;
                  }
                }
              } else {
                try {
                  from =
                      new EncodedStringValue(PduHeaders.FROM_INSERT_ADDRESS_TOKEN_STR.getBytes());
                } catch (NullPointerException e15) {
                  log(headerField + "is not Encoded-String-Value header field!");
                  return null;
                }
              }
              try {
                headers.setEncodedStringValue(from, 137);
                break;
              } catch (NullPointerException e16) {
                log("null pointer error!");
                break;
              } catch (RuntimeException e17) {
                log(headerField + "is not Encoded-String-Value header field!");
                return null;
              }
            } catch (IllegalArgumentException e18) {
              log("parseValueLength Exception!");
              return null;
            }
          case 138:
            pduDataStream.mark(1);
            int messageClass = extractByteValue(pduDataStream);
            if (messageClass >= 128) {
              if (128 == messageClass) {
                try {
                  headers.setTextString(PduHeaders.MESSAGE_CLASS_PERSONAL_STR.getBytes(), 138);
                  break;
                } catch (NullPointerException e19) {
                  log("null pointer error!");
                  break;
                } catch (RuntimeException e20) {
                  log(headerField + "is not Text-String header field!");
                  return null;
                }
              } else if (129 == messageClass) {
                headers.setTextString(PduHeaders.MESSAGE_CLASS_ADVERTISEMENT_STR.getBytes(), 138);
                break;
              } else if (130 == messageClass) {
                headers.setTextString(PduHeaders.MESSAGE_CLASS_INFORMATIONAL_STR.getBytes(), 138);
                break;
              } else if (131 != messageClass) {
                break;
              } else {
                headers.setTextString("auto".getBytes(), 138);
                break;
              }
            } else {
              pduDataStream.reset();
              byte[] messageClassString = parseWapString(pduDataStream, 0);
              if (messageClassString == null) {
                break;
              } else {
                try {
                  headers.setTextString(messageClassString, 138);
                  break;
                } catch (NullPointerException e21) {
                  log("null pointer error!");
                  break;
                } catch (RuntimeException e22) {
                  log(headerField + "is not Text-String header field!");
                  return null;
                }
              }
            }
          case 140:
            int messageType = extractByteValue(pduDataStream);
            switch (messageType) {
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
                  headers.setOctet(messageType, headerField);
                  break;
                } catch (InvalidHeaderValueException e23) {
                  log(
                      "Set invalid Octet value: "
                          + messageType
                          + " into the header filed: "
                          + headerField);
                  return null;
                } catch (RuntimeException e24) {
                  log(headerField + "is not Octet header field!");
                  return null;
                }
            }
          case 141:
            int version = parseShortInteger(pduDataStream);
            try {
              headers.setOctet(version, 141);
              break;
            } catch (InvalidHeaderValueException e25) {
              log("Set invalid Octet value: " + version + " into the header filed: " + headerField);
              return null;
            } catch (RuntimeException e26) {
              log(headerField + "is not Octet header field!");
              return null;
            }
          case 142:
          case 159:
            try {
              headers.setLongInteger(parseLongInteger(pduDataStream), headerField);
              break;
            } catch (RuntimeException e27) {
              log(headerField + "is not Long-Integer header field!");
              return null;
            }
          case 147:
          case 154:
          case 166:
          case 181:
          case 182:
            EncodedStringValue value5 = parseEncodedStringValue(pduDataStream);
            if (value5 == null) {
              break;
            } else {
              try {
                headers.setEncodedStringValue(value5, headerField);
                break;
              } catch (NullPointerException e28) {
                log("null pointer error!");
                break;
              } catch (RuntimeException e29) {
                log(headerField + "is not Encoded-String-Value header field!");
                return null;
              }
            }
          case 150:
            EncodedStringValue value6 = parseEncodedSubjectValue(pduDataStream);
            if (value6 != null) {
              try {
                headers.setEncodedStringValue(value6, headerField);
                break;
              } catch (NullPointerException e30) {
                log("null pointer error!");
                break;
              } catch (RuntimeException e31) {
                log(headerField + "is not Encoded-String-Value header field!");
                return null;
              }
            } else {
              log("Subject is null!");
              break;
            }
          case 160:
            try {
              parseValueLength(pduDataStream);
              try {
                parseIntegerValue(pduDataStream);
                EncodedStringValue previouslySentBy = parseEncodedStringValue(pduDataStream);
                if (previouslySentBy == null) {
                  break;
                } else {
                  try {
                    headers.setEncodedStringValue(previouslySentBy, 160);
                    break;
                  } catch (NullPointerException e32) {
                    log("null pointer error!");
                    break;
                  } catch (RuntimeException e33) {
                    log(headerField + "is not Encoded-String-Value header field!");
                    return null;
                  }
                }
              } catch (RuntimeException e34) {
                log(headerField + " is not Integer-Value");
                return null;
              }
            } catch (IllegalArgumentException e35) {
              log("parseValueLength Exception!");
              return null;
            }
          case 161:
            try {
              parseValueLength(pduDataStream);
              try {
                parseIntegerValue(pduDataStream);
                try {
                  long perviouslySentDate = parseLongInteger(pduDataStream);
                  headers.setLongInteger(perviouslySentDate, 161);
                  break;
                } catch (RuntimeException e36) {
                  log(headerField + "is not Long-Integer header field!");
                  return null;
                }
              } catch (RuntimeException e37) {
                log(headerField + " is not Integer-Value");
                return null;
              }
            } catch (IllegalArgumentException e38) {
              log("parseValueLength Exception!");
              return null;
            }
          case 164:
            try {
              parseValueLength(pduDataStream);
              extractByteValue(pduDataStream);
              parseEncodedStringValue(pduDataStream);
              break;
            } catch (IllegalArgumentException e39) {
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
              parseValueLength(pduDataStream);
              extractByteValue(pduDataStream);
              try {
                parseIntegerValue(pduDataStream);
                break;
              } catch (RuntimeException e40) {
                log(headerField + " is not Integer-Value");
                return null;
              }
            } catch (IllegalArgumentException e41) {
              log("parseValueLength Exception!");
              return null;
            }
          case 173:
          case 175:
          case 179:
            try {
              headers.setLongInteger(parseIntegerValue(pduDataStream), headerField);
              break;
            } catch (RuntimeException e42) {
              log(headerField + "is not Long-Integer header field!");
              return null;
            }
          case 178:
            parseContentType(pduDataStream, null);
            break;
        }
      }
    }
    return headers;
  }

  /* JADX WARN: Code restructure failed: missing block: B:42:0x0141, code lost:

     r2 = new byte[r10];
     r17 = r3;
     r20 = r5;
     r3 = new java.lang.String(r11.getContentType());
     r6 = r23.read(r2, 0, r10);
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x0156, code lost:

     if (r6 != (-1)) goto L57;
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:0x0160, code lost:

     if (r3.equalsIgnoreCase(com.google.android.mms.ContentType.MULTIPART_ALTERNATIVE) == false) goto L64;
  */
  /* JADX WARN: Code restructure failed: missing block: B:46:0x0162, code lost:

     r5 = r0.parseParts(new java.io.ByteArrayInputStream(r2), r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x016b, code lost:

     if (r5 != null) goto L62;
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x016d, code lost:

     log("childBody is null");
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x0178, code lost:

     r0 = null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:57:0x0173, code lost:

     r11 = r5.getPart(0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:58:0x017a, code lost:

     r0 = r11.getContentTransferEncoding();
  */
  /* JADX WARN: Code restructure failed: missing block: B:59:0x017e, code lost:

     if (r0 == null) goto L72;
  */
  /* JADX WARN: Code restructure failed: missing block: B:60:0x0180, code lost:

     r5 = new java.lang.String(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x018d, code lost:

     if (r5.equalsIgnoreCase(com.google.android.mms.pdu.PduPart.P_BASE64) == false) goto L69;
  */
  /* JADX WARN: Code restructure failed: missing block: B:62:0x018f, code lost:

     r2 = com.google.android.mms.pdu.Base64.decodeBase64(r2);
  */
  /* JADX WARN: Code restructure failed: missing block: B:63:0x01a4, code lost:

     if (r2 != null) goto L76;
  */
  /* JADX WARN: Code restructure failed: missing block: B:64:0x01ad, code lost:

     r0 = null;
     r11.setData(r2);
  */
  /* JADX WARN: Code restructure failed: missing block: B:66:0x01a6, code lost:

     log("Decode part data error!");
  */
  /* JADX WARN: Code restructure failed: missing block: B:67:0x01ac, code lost:

     return null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:69:0x019b, code lost:

     if (r5.equalsIgnoreCase(com.google.android.mms.pdu.PduPart.P_QUOTED_PRINTABLE) == false) goto L73;
  */
  /* JADX WARN: Code restructure failed: missing block: B:70:0x019d, code lost:

     r2 = com.google.android.mms.pdu.QuotedPrintable.decodeQuotedPrintable(r2);
  */
  /* JADX WARN: Code restructure failed: missing block: B:73:0x0158, code lost:

     return null;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  protected PduBody parseParts(ByteArrayInputStream pduDataStream, byte[] contType) {
    char c;
    PduParser pduParser = this;
    PduBody pduBody = null;
    if (pduDataStream == null) {
      return null;
    }
    int count = parseUnsignedInt(pduDataStream);
    PduBody body = new PduBody();
    String ctTypeStr = new String(contType);
    char c2 = 0;
    if (ctTypeStr.equals(ContentType.MULTIPART_MIXED)
        || ctTypeStr.equals(ContentType.MULTIPART_RELATED)) {
      int i = 0;
      while (i < count) {
        int headerLength = parseUnsignedInt(pduDataStream);
        int dataLength = parseUnsignedInt(pduDataStream);
        PduPart part = new PduPart();
        int startPos = pduDataStream.available();
        if (startPos <= 0) {
          return pduBody;
        }
        HashMap<Integer, Object> map = new HashMap<>();
        byte[] contentType = parseContentType(pduDataStream, map);
        if (contentType != null) {
          part.setContentType(contentType);
        } else {
          part.setContentType(PduContentTypes.contentTypes[c2].getBytes());
        }
        byte[] name = (byte[]) map.get(151);
        if (name != null) {
          part.setName(name);
        }
        Integer charset = (Integer) map.get(129);
        if (charset != null) {
          part.setCharset(charset.intValue());
        }
        int endPos = headerLength - (startPos - pduDataStream.available());
        if (endPos > 0) {
          if (!pduParser.parsePartHeaders(pduDataStream, part, endPos)) {
            return null;
          }
        } else if (endPos < 0) {
          return null;
        }
        if (part.getContentLocation() == null
            && part.getName() == null
            && part.getFilename() == null
            && part.getContentId() == null) {
          part.setContentLocation(Long.toOctalString(System.currentTimeMillis()).getBytes());
        }
        int count2 = count;
        String ctTypeStr2 = ctTypeStr;
        PduBody pduBody2 = null;
        if (checkPartPosition(part) == 0) {
          c = 0;
          body.addPart(0, part);
        } else {
          c = 0;
          body.addPart(part);
        }
        i++;
        c2 = c;
        count = count2;
        ctTypeStr = ctTypeStr2;
        pduBody = pduBody2;
        pduParser = this;
      }
    } else {
      if (!ctTypeStr.equals("text/plain")) {
        return null;
      }
      String smilDoc =
          new String(
              "<smil><head><layout><root-layout width=\"320px\" height=\"480px\"/><region"
                  + " id=\"Text\" left=\"0\" top=\"320\" width=\"320px\" height=\"160px\""
                  + " fit=\"meet\"/></layout></head><body><par><text src=\"attach.txt\""
                  + " region=\"Text\"/></par></body></smil>");
      PduPart smilPart = new PduPart();
      smilPart.setContentId("smil.txt".getBytes());
      smilPart.setContentLocation("smil.txt".getBytes());
      smilPart.setContentType(ContentType.APP_SMIL.getBytes());
      smilPart.setData(smilDoc.getBytes());
      body.addPart(0, smilPart);
      PduPart part2 = new PduPart();
      part2.setContentLocation("attach.txt".getBytes());
      part2.setContentId("attach.txt".getBytes());
      part2.setContentType("text/plain".getBytes());
      int dataLength2 = pduDataStream.available();
      byte[] partData = new byte[dataLength2];
      pduDataStream.read(partData, 0, dataLength2);
      part2.setData(partData);
      body.addPart(part2);
    }
    return body;
  }

  private static void log(String text) {}

  protected static int parseUnsignedInt(ByteArrayInputStream pduDataStream) {
    int result = 0;
    int temp = pduDataStream.read();
    if (temp == -1) {
      return temp;
    }
    while ((temp & 128) != 0) {
      result = (result << 7) | (temp & 127);
      temp = pduDataStream.read();
      if (temp == -1) {
        return temp;
      }
    }
    return (result << 7) | (temp & 127);
  }

  protected static int parseValueLength(ByteArrayInputStream pduDataStream) {
    int temp = pduDataStream.read();
    int first = temp & 255;
    if (first <= 30) {
      return first;
    }
    if (first == 31) {
      return parseUnsignedInt(pduDataStream);
    }
    throw new IllegalArgumentException("Value length > LENGTH_QUOTE!");
  }

  protected static EncodedStringValue parseEncodedStringValue(ByteArrayInputStream pduDataStream) {
    EncodedStringValue returnValue;
    pduDataStream.mark(1);
    int charset = 0;
    int temp = pduDataStream.read();
    if (temp == 0) {
      return null;
    }
    int first = temp & 255;
    if (first == 0) {
      return new EncodedStringValue("");
    }
    pduDataStream.reset();
    if (first < 32) {
      try {
        parseValueLength(pduDataStream);
        charset = parseShortInteger(pduDataStream);
      } catch (IllegalArgumentException e) {
        log("parseValueLength Exception!");
        return null;
      }
    }
    byte[] textString = parseWapString(pduDataStream, 0);
    try {
      if (charset != 0) {
        returnValue = new EncodedStringValue(charset, textString);
      } else {
        returnValue = new EncodedStringValue(textString);
      }
      return returnValue;
    } catch (Exception e2) {
      return null;
    }
  }

  protected static byte[] parseWapString(ByteArrayInputStream pduDataStream, int stringType) {
    pduDataStream.mark(1);
    int temp = pduDataStream.read();
    if (1 == stringType && 34 == temp) {
      pduDataStream.mark(1);
    } else if (stringType == 0 && 127 == temp) {
      pduDataStream.mark(1);
    } else {
      pduDataStream.reset();
    }
    return getWapString(pduDataStream, stringType);
  }

  protected static boolean isTokenCharacter(int ch) {
    if (ch < 33 || ch > 126) {
      return false;
    }
    switch (ch) {
      case 34:
      case 40:
      case 41:
      case 44:
      case 47:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 91:
      case 92:
      case 93:
      case 123:
      case 125:
        break;
    }
    return false;
  }

  protected static boolean isText(int ch) {
    if ((ch >= 32 && ch <= 126) || (ch >= 128 && ch <= 255)) {
      return true;
    }
    switch (ch) {
    }
    return true;
  }

  protected static byte[] getWapString(ByteArrayInputStream pduDataStream, int stringType) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int temp = pduDataStream.read();
    while (-1 != temp && temp != 0) {
      if (stringType == 2) {
        if (isTokenCharacter(temp)) {
          out.write(temp);
        }
      } else if (isText(temp)) {
        out.write(temp);
      }
      temp = pduDataStream.read();
    }
    if (out.size() > 0) {
      return out.toByteArray();
    }
    return null;
  }

  protected static int extractByteValue(ByteArrayInputStream pduDataStream) {
    int temp = pduDataStream.read();
    return temp & 255;
  }

  protected static int parseShortInteger(ByteArrayInputStream pduDataStream) {
    int temp = pduDataStream.read();
    return temp & 127;
  }

  protected static long parseLongInteger(ByteArrayInputStream pduDataStream) {
    int temp = pduDataStream.read();
    int count = temp & 255;
    if (count > 8) {
      throw new RuntimeException("Octet count greater than 8 and I can't represent that!");
    }
    long result = 0;
    for (int i = 0; i < count; i++) {
      int temp2 = pduDataStream.read();
      result = (result << 8) + (temp2 & 255);
    }
    return result;
  }

  protected static long parseIntegerValue(ByteArrayInputStream pduDataStream) {
    pduDataStream.mark(1);
    int temp = pduDataStream.read();
    pduDataStream.reset();
    if (temp > 127) {
      return parseShortInteger(pduDataStream);
    }
    return parseLongInteger(pduDataStream);
  }

  protected static int skipWapValue(ByteArrayInputStream pduDataStream, int length) {
    byte[] area = new byte[length];
    int readLen = pduDataStream.read(area, 0, length);
    if (readLen < length) {
      return -1;
    }
    return readLen;
  }

  protected static void parseContentTypeParams(
      ByteArrayInputStream pduDataStream, HashMap<Integer, Object> map, Integer length) {
    int startPos = pduDataStream.available();
    int lastLen = length.intValue();
    while (lastLen > 0) {
      int param = pduDataStream.read();
      lastLen--;
      switch (param) {
        case 129:
          pduDataStream.mark(1);
          int firstValue = extractByteValue(pduDataStream);
          pduDataStream.reset();
          if ((firstValue > 32 && firstValue < 127) || firstValue == 0) {
            byte[] charsetStr = parseWapString(pduDataStream, 0);
            try {
              int charsetInt = CharacterSets.getMibEnumValue(new String(charsetStr));
              map.put(129, Integer.valueOf(charsetInt));
            } catch (UnsupportedEncodingException e) {
              Log.m97e(LOG_TAG, Arrays.toString(charsetStr), e);
              map.put(129, 0);
            }
          } else {
            int charset = (int) parseIntegerValue(pduDataStream);
            if (map != null) {
              map.put(129, Integer.valueOf(charset));
            }
          }
          int tempPos = pduDataStream.available();
          int lastLen2 = length.intValue() - (startPos - tempPos);
          lastLen = lastLen2;
          break;
        case 131:
        case 137:
          pduDataStream.mark(1);
          int first = extractByteValue(pduDataStream);
          pduDataStream.reset();
          if (first > 127) {
            int index = parseShortInteger(pduDataStream);
            if (index < PduContentTypes.contentTypes.length) {
              map.put(131, PduContentTypes.contentTypes[index].getBytes());
            }
          } else {
            byte[] type = parseWapString(pduDataStream, 0);
            if (type != null && map != null) {
              map.put(131, type);
            }
          }
          int tempPos2 = pduDataStream.available();
          int lastLen3 = length.intValue() - (startPos - tempPos2);
          lastLen = lastLen3;
          break;
        case 133:
        case 151:
          byte[] name = parseWapString(pduDataStream, 0);
          if (name != null && map != null) {
            map.put(151, name);
          }
          int tempPos3 = pduDataStream.available();
          int lastLen4 = length.intValue() - (startPos - tempPos3);
          lastLen = lastLen4;
          break;
        case 138:
        case 153:
          byte[] start = parseWapString(pduDataStream, 0);
          if (start != null && map != null) {
            map.put(153, start);
          }
          int tempPos4 = pduDataStream.available();
          int lastLen5 = length.intValue() - (startPos - tempPos4);
          lastLen = lastLen5;
          break;
        default:
          if (-1 == skipWapValue(pduDataStream, lastLen)) {
            Log.m96e(LOG_TAG, "Corrupt Content-Type");
            break;
          } else {
            lastLen = 0;
            break;
          }
      }
    }
    if (lastLen != 0) {
      Log.m96e(LOG_TAG, "Corrupt Content-Type");
    }
  }

  protected static byte[] parseContentType(
      ByteArrayInputStream pduDataStream, HashMap<Integer, Object> map) {
    byte[] contentType;
    pduDataStream.mark(1);
    int temp = pduDataStream.read();
    pduDataStream.reset();
    int cur = temp & 255;
    if (cur < 32) {
      try {
        int length = parseValueLength(pduDataStream);
        int startPos = pduDataStream.available();
        if (length > startPos) {
          Log.m96e(
              LOG_TAG,
              "parseContentType: Invalid length "
                  + length
                  + " when available bytes are "
                  + startPos);
          return PduContentTypes.contentTypes[0].getBytes();
        }
        pduDataStream.mark(1);
        int temp2 = pduDataStream.read();
        pduDataStream.reset();
        int first = temp2 & 255;
        if (first >= 32 && first <= 127) {
          contentType = parseWapString(pduDataStream, 0);
        } else {
          if (first <= 127) {
            Log.m96e(LOG_TAG, "Corrupt content-type");
            return PduContentTypes.contentTypes[0].getBytes();
          }
          int index = parseShortInteger(pduDataStream);
          if (index < PduContentTypes.contentTypes.length) {
            contentType = PduContentTypes.contentTypes[index].getBytes();
          } else {
            pduDataStream.reset();
            contentType = parseWapString(pduDataStream, 0);
          }
        }
        int endPos = pduDataStream.available();
        int parameterLen = length - (startPos - endPos);
        if (parameterLen > 0) {
          parseContentTypeParams(pduDataStream, map, Integer.valueOf(parameterLen));
        }
        if (parameterLen < 0) {
          Log.m96e(LOG_TAG, "Corrupt MMS message");
          return PduContentTypes.contentTypes[0].getBytes();
        }
        return contentType;
      } catch (IllegalArgumentException e) {
        log("parseValueLength Exception!");
        return null;
      }
    }
    if (cur <= 127) {
      byte[] contentType2 = parseWapString(pduDataStream, 0);
      return contentType2;
    }
    byte[] contentType3 = PduContentTypes.contentTypes[parseShortInteger(pduDataStream)].getBytes();
    return contentType3;
  }

  protected boolean parsePartHeaders(ByteArrayInputStream pduDataStream, PduPart part, int length) {
    int startPos = pduDataStream.available();
    int lastLen = length;
    while (lastLen > 0) {
      int header = pduDataStream.read();
      lastLen--;
      if (header > 127) {
        switch (header) {
          case 142:
            byte[] contentLocation = parseWapString(pduDataStream, 0);
            if (contentLocation != null) {
              part.setContentLocation(contentLocation);
            }
            int tempPos = pduDataStream.available();
            lastLen = length - (startPos - tempPos);
            break;
          case 174:
          case 197:
            if (this.mParseContentDisposition) {
              try {
                int len = parseValueLength(pduDataStream);
                pduDataStream.mark(1);
                int thisStartPos = pduDataStream.available();
                int value = pduDataStream.read();
                if (value == 128) {
                  part.setContentDisposition(PduPart.DISPOSITION_FROM_DATA);
                } else if (value == 129) {
                  part.setContentDisposition(PduPart.DISPOSITION_ATTACHMENT);
                } else if (value == 130) {
                  part.setContentDisposition(PduPart.DISPOSITION_INLINE);
                } else {
                  pduDataStream.reset();
                  part.setContentDisposition(parseWapString(pduDataStream, 0));
                }
                if (thisStartPos - pduDataStream.available() < len) {
                  if (pduDataStream.read() == 152) {
                    part.setFilename(parseWapString(pduDataStream, 0));
                  }
                  int thisEndPos = pduDataStream.available();
                  if (thisStartPos - thisEndPos < len) {
                    int last = len - (thisStartPos - thisEndPos);
                    byte[] temp = new byte[last];
                    pduDataStream.read(temp, 0, last);
                  }
                }
                int tempPos2 = pduDataStream.available();
                lastLen = length - (startPos - tempPos2);
                break;
              } catch (IllegalArgumentException e) {
                log("parseValueLength Exception!");
                return false;
              }
            } else {
              continue;
            }
          case 192:
            byte[] contentId = parseWapString(pduDataStream, 1);
            if (contentId != null) {
              part.setContentId(contentId);
            }
            int tempPos3 = pduDataStream.available();
            lastLen = length - (startPos - tempPos3);
            break;
          default:
            if (-1 == skipWapValue(pduDataStream, lastLen)) {
              Log.m96e(LOG_TAG, "Corrupt Part headers");
              return false;
            }
            lastLen = 0;
            break;
        }
      } else if (header >= 32 && header <= 127) {
        byte[] tempHeader = parseWapString(pduDataStream, 0);
        byte[] tempValue = parseWapString(pduDataStream, 0);
        if (true == PduPart.CONTENT_TRANSFER_ENCODING.equalsIgnoreCase(new String(tempHeader))) {
          part.setContentTransferEncoding(tempValue);
        }
        int tempPos4 = pduDataStream.available();
        lastLen = length - (startPos - tempPos4);
      } else {
        if (-1 == skipWapValue(pduDataStream, lastLen)) {
          Log.m96e(LOG_TAG, "Corrupt Part headers");
          return false;
        }
        lastLen = 0;
      }
    }
    if (lastLen == 0) {
      return true;
    }
    Log.m96e(LOG_TAG, "Corrupt Part headers");
    return false;
  }

  private static int checkPartPosition(PduPart part) {
    byte[] contentType;
    byte[] contentId = mTypeParam;
    if (contentId == null && mStartParam == null) {
      return 1;
    }
    if (mStartParam == null) {
      return (contentId == null
              || (contentType = part.getContentType()) == null
              || true != Arrays.equals(mTypeParam, contentType))
          ? 1
          : 0;
    }
    byte[] contentId2 = part.getContentId();
    return (contentId2 == null || true != Arrays.equals(mStartParam, contentId2)) ? 1 : 0;
  }

  protected static boolean checkMandatoryHeader(PduHeaders headers) {
    if (headers == null) {
      return false;
    }
    int messageType = headers.getOctet(140);
    int mmsVersion = headers.getOctet(141);
    if (mmsVersion == 0) {
      return false;
    }
    switch (messageType) {
      case 128:
        byte[] srContentType = headers.getTextString(132);
        if (srContentType != null) {
          EncodedStringValue srFrom = headers.getEncodedStringValue(137);
          if (srFrom != null) {
            byte[] srTransactionId = headers.getTextString(152);
            if (srTransactionId == null) {}
          }
        }
        break;
      case 129:
        int scResponseStatus = headers.getOctet(146);
        if (scResponseStatus != 0) {
          byte[] scTransactionId = headers.getTextString(152);
          if (scTransactionId == null) {}
        }
        break;
      case 130:
        byte[] niContentLocation = headers.getTextString(131);
        if (niContentLocation != null) {
          long niExpiry = headers.getLongInteger(136);
          if (-1 != niExpiry) {
            byte[] niMessageClass = headers.getTextString(138);
            if (niMessageClass != null) {
              long niMessageSize = headers.getLongInteger(142);
              if (-1 != niMessageSize) {
                byte[] niTransactionId = headers.getTextString(152);
                if (niTransactionId == null) {}
              }
            }
          }
        }
        break;
      case 131:
        int nriStatus = headers.getOctet(149);
        if (nriStatus != 0) {
          byte[] nriTransactionId = headers.getTextString(152);
          if (nriTransactionId == null) {}
        }
        break;
      case 132:
        byte[] rcContentType = headers.getTextString(132);
        if (rcContentType != null) {
          long rcDate = headers.getLongInteger(133);
          if (-1 == rcDate) {}
        }
        break;
      case 133:
        byte[] aiTransactionId = headers.getTextString(152);
        if (aiTransactionId == null) {}
        break;
      case 134:
        long diDate = headers.getLongInteger(133);
        if (-1 != diDate) {
          byte[] diMessageId = headers.getTextString(139);
          if (diMessageId != null) {
            int diStatus = headers.getOctet(149);
            if (diStatus != 0) {
              EncodedStringValue[] diTo = headers.getEncodedStringValues(151);
              if (diTo == null) {}
            }
          }
        }
        break;
      case 135:
        EncodedStringValue rrFrom = headers.getEncodedStringValue(137);
        if (rrFrom != null) {
          byte[] rrMessageId = headers.getTextString(139);
          if (rrMessageId != null) {
            int rrReadStatus = headers.getOctet(155);
            if (rrReadStatus != 0) {
              EncodedStringValue[] rrTo = headers.getEncodedStringValues(151);
              if (rrTo == null) {}
            }
          }
        }
        break;
      case 136:
        long roDate = headers.getLongInteger(133);
        if (-1 != roDate) {
          EncodedStringValue roFrom = headers.getEncodedStringValue(137);
          if (roFrom != null) {
            byte[] roMessageId = headers.getTextString(139);
            if (roMessageId != null) {
              int roReadStatus = headers.getOctet(155);
              if (roReadStatus != 0) {
                EncodedStringValue[] roTo = headers.getEncodedStringValues(151);
                if (roTo == null) {}
              }
            }
          }
        }
        break;
    }
    return false;
  }

  public PduParser(byte[] pduDataStream) {
    this.mPduDataStream = null;
    this.mPduDataStream = new ByteArrayInputStream(pduDataStream);
    SemCscFeature cscFeature = SemCscFeature.getInstance();
    this.mParseContentDisposition = true;
    String displayMmsTimeAs = cscFeature.getString("CscFeature_Message_DisplayMmsTimeAs");
    mEnableMmsServerTime = isServerTime(displayMmsTimeAs, true);
  }

  private static boolean isServerTime(String displayTimeAs, boolean initValue) {
    if (displayTimeAs == null) {
      return initValue;
    }
    if ("phone".equals(displayTimeAs)) {
      return false;
    }
    if (Telephony.Carriers.SERVER.equals(displayTimeAs)) {
      return true;
    }
    return initValue;
  }

  protected static EncodedStringValue parseEncodedSubjectValue(ByteArrayInputStream pduDataStream) {
    pduDataStream.mark(1);
    EncodedStringValue returnValue = null;
    int charset = 0;
    int temp = pduDataStream.read();
    if (temp == 0) {
      return null;
    }
    int first = temp & 255;
    if (first > 0) {
      pduDataStream.reset();
      if (first < 32) {
        try {
          parseValueLength(pduDataStream);
          charset = parseShortInteger(pduDataStream);
        } catch (IllegalArgumentException e) {
          log("parseValueLength Exception!");
          return null;
        }
      }
      byte[] textString = parseWapString(pduDataStream, 0);
      try {
        if (charset != 0) {
          returnValue = new EncodedStringValue(charset, textString);
        } else {
          returnValue = new EncodedStringValue(textString);
        }
      } catch (Exception e2) {
        return null;
      }
    }
    return returnValue;
  }
}
