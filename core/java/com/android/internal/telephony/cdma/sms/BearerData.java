package com.android.internal.telephony.cdma.sms;

import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsManager;
import android.telephony.cdma.CdmaSmsCbProgramData;
import android.telephony.cdma.CdmaSmsCbProgramResults;
import android.text.TextUtils;
import com.android.internal.R;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.util.BitwiseInputStream;
import com.android.internal.util.BitwiseOutputStream;
import com.android.telephony.Rlog;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;

public final class BearerData {
    public static final int ALERT_DEFAULT = 0;
    public static final int ALERT_HIGH_PRIO = 3;
    public static final int ALERT_LOW_PRIO = 1;
    public static final int ALERT_MEDIUM_PRIO = 2;
    public static final int DISPLAY_MODE_DEFAULT = 1;
    public static final int DISPLAY_MODE_IMMEDIATE = 0;
    public static final int DISPLAY_MODE_USER = 2;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_PERMANENT = 3;
    public static final int ERROR_TEMPORARY = 2;
    public static final int ERROR_UNDEFINED = 255;
    public static final int LANGUAGE_CHINESE = 6;
    public static final int LANGUAGE_ENGLISH = 1;
    public static final int LANGUAGE_FRENCH = 2;
    public static final int LANGUAGE_HEBREW = 7;
    public static final int LANGUAGE_JAPANESE = 4;
    public static final int LANGUAGE_KOREAN = 5;
    public static final int LANGUAGE_SPANISH = 3;
    public static final int LANGUAGE_UNKNOWN = 0;
    public static final int LANGUAGE_UNKNOWN_LGT = 64;
    private static final String LOG_TAG = "BearerData";
    public static final int MESSAGE_TYPE_CANCELLATION = 3;
    public static final int MESSAGE_TYPE_DELIVER = 1;
    public static final int MESSAGE_TYPE_DELIVERY_ACK = 4;
    public static final int MESSAGE_TYPE_DELIVER_REPORT = 7;
    public static final int MESSAGE_TYPE_READ_ACK = 6;
    public static final int MESSAGE_TYPE_SUBMIT = 2;
    public static final int MESSAGE_TYPE_SUBMIT_REPORT = 8;
    public static final int MESSAGE_TYPE_USER_ACK = 5;
    public static final int PRIORITY_EMERGENCY = 3;
    public static final int PRIORITY_INTERACTIVE = 1;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 2;
    public static final int PRIVACY_CONFIDENTIAL = 2;
    public static final int PRIVACY_NOT_RESTRICTED = 0;
    public static final int PRIVACY_RESTRICTED = 1;
    public static final int PRIVACY_SECRET = 3;
    public static final int RELATIVE_TIME_DAYS_LIMIT = 196;
    public static final int RELATIVE_TIME_HOURS_LIMIT = 167;
    public static final int RELATIVE_TIME_INDEFINITE = 245;
    public static final int RELATIVE_TIME_MINS_LIMIT = 143;
    public static final int RELATIVE_TIME_MOBILE_INACTIVE = 247;
    public static final int RELATIVE_TIME_NOW = 246;
    public static final int RELATIVE_TIME_RESERVED = 248;
    public static final int RELATIVE_TIME_WEEKS_LIMIT = 244;
    public static final int STATUS_ACCEPTED = 0;
    public static final int STATUS_BLOCKED_DESTINATION = 7;
    public static final int STATUS_CANCELLED = 3;
    public static final int STATUS_CANCEL_FAILED = 6;
    public static final int STATUS_DELIVERED = 2;
    public static final int STATUS_DEPOSITED_TO_INTERNET = 1;
    public static final int STATUS_DUPLICATE_MESSAGE = 9;
    public static final int STATUS_INVALID_DESTINATION = 10;
    public static final int STATUS_MESSAGE_EXPIRED = 13;
    public static final int STATUS_NETWORK_CONGESTION = 4;
    public static final int STATUS_NETWORK_ERROR = 5;
    public static final int STATUS_TEXT_TOO_LONG = 8;
    public static final int STATUS_UNDEFINED = 255;
    public static final int STATUS_UNKNOWN_ERROR = 31;
    private static final byte SUBPARAM_ALERT_ON_MESSAGE_DELIVERY = 12;
    private static final byte SUBPARAM_CALLBACK_NUMBER = 14;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_ABSOLUTE = 6;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_RELATIVE = 7;
    private static final byte SUBPARAM_ID_LAST_DEFINED = 23;
    private static final byte SUBPARAM_LANGUAGE_INDICATOR = 13;
    private static final byte SUBPARAM_MESSAGE_CENTER_TIME_STAMP = 3;
    private static final byte SUBPARAM_MESSAGE_DEPOSIT_INDEX = 17;
    private static final byte SUBPARAM_MESSAGE_DISPLAY_MODE = 15;
    private static final byte SUBPARAM_MESSAGE_IDENTIFIER = 0;
    private static final byte SUBPARAM_MESSAGE_STATUS = 20;
    private static final byte SUBPARAM_NUMBER_OF_MESSAGES = 11;
    private static final byte SUBPARAM_PRIORITY_INDICATOR = 8;
    private static final byte SUBPARAM_PRIVACY_INDICATOR = 9;
    private static final byte SUBPARAM_REPLY_OPTION = 10;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_DATA = 18;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_RESULTS = 19;
    private static final byte SUBPARAM_USER_DATA = 1;
    private static final byte SUBPARAM_USER_RESPONSE_CODE = 2;
    private static final byte SUBPARAM_VALIDITY_PERIOD_ABSOLUTE = 4;
    private static final byte SUBPARAM_VALIDITY_PERIOD_RELATIVE = 5;
    public static char compChar;
    public static int mBodyOffset;
    public static boolean mIsfourBytesUnicode;
    public static byte[] mlastByte;
    public static int userLength;
    public static byte[] userdata;
    public CdmaSmsAddress callbackNumber;
    public SmsCbCmasInfo cmasWarningInfo;
    public TimeStamp deferredDeliveryTimeAbsolute;
    public int deferredDeliveryTimeRelative;
    public boolean deferredDeliveryTimeRelativeSet;
    public boolean deliveryAckReq;
    public int depositIndex;
    public boolean hasUserDataHeader;
    public int messageId;
    public int messageType;
    public TimeStamp msgCenterTimeStamp;
    public String msgDeliveryTime;
    public int numberOfMessages;
    public boolean readAckReq;
    public boolean reportReq;
    public ArrayList<CdmaSmsCbProgramData> serviceCategoryProgramData;
    public ArrayList<CdmaSmsCbProgramResults> serviceCategoryProgramResults;
    public boolean userAckReq;
    public UserData userData;
    public int userResponseCode;
    public TimeStamp validityPeriodAbsolute;
    public int validityPeriodRelative;
    public boolean validityPeriodRelativeSet;
    public boolean priorityIndicatorSet = false;
    public int priority = 0;
    public boolean privacyIndicatorSet = false;
    public int privacy = 0;
    public boolean alertIndicatorSet = false;
    public int alert = 0;
    public boolean displayModeSet = false;
    public int displayMode = 1;
    public boolean languageIndicatorSet = false;
    public int language = 0;
    public boolean messageStatusSet = false;
    public int errorClass = 255;
    public int messageStatus = 255;
    public boolean userResponseCodeSet = false;

    public static class TimeStamp {
        public int hour;
        private ZoneId mZoneId = ZoneId.systemDefault();
        public int minute;
        public int monthDay;
        public int monthOrdinal;
        public int second;
        public int year;

        public static TimeStamp fromByteArray(byte[] data) {
            TimeStamp ts = new TimeStamp();
            int year = IccUtils.cdmaBcdByteToInt(data[0]);
            if (year > 99 || year < 0) {
                return null;
            }
            ts.year = year >= 96 ? year + 1900 : year + 2000;
            int month = IccUtils.cdmaBcdByteToInt(data[1]);
            if (month < 1 || month > 12) {
                return null;
            }
            ts.monthOrdinal = month;
            int day = IccUtils.cdmaBcdByteToInt(data[2]);
            if (day < 1 || day > 31) {
                return null;
            }
            ts.monthDay = day;
            int hour = IccUtils.cdmaBcdByteToInt(data[3]);
            if (hour < 0 || hour > 23) {
                return null;
            }
            ts.hour = hour;
            int minute = IccUtils.cdmaBcdByteToInt(data[4]);
            if (minute < 0 || minute > 59) {
                return null;
            }
            ts.minute = minute;
            int second = IccUtils.cdmaBcdByteToInt(data[5]);
            if (second < 0 || second > 59) {
                return null;
            }
            ts.second = second;
            return ts;
        }

        public static TimeStamp fromMillis(long timeInMillis) {
            TimeStamp ts = new TimeStamp();
            ?? localDateTime = Instant.ofEpochMilli(timeInMillis).atZone(ts.mZoneId).toLocalDateTime();
            int year = localDateTime.getYear();
            if (year < 1996 || year > 2095) {
                return null;
            }
            ts.year = year;
            ts.monthOrdinal = localDateTime.getMonthValue();
            ts.monthDay = localDateTime.getDayOfMonth();
            ts.hour = localDateTime.getHour();
            ts.minute = localDateTime.getMinute();
            ts.second = localDateTime.getSecond();
            return ts;
        }

        public byte[] toByteArray() {
            int year = this.year % 100;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream(6);
            outStream.write((((year / 10) & 15) << 4) | ((year % 10) & 15));
            outStream.write((((this.monthOrdinal / 10) << 4) & 240) | ((this.monthOrdinal % 10) & 15));
            outStream.write((((this.monthDay / 10) << 4) & 240) | ((this.monthDay % 10) & 15));
            outStream.write((((this.hour / 10) << 4) & 240) | ((this.hour % 10) & 15));
            outStream.write((((this.minute / 10) << 4) & 240) | ((this.minute % 10) & 15));
            outStream.write((((this.second / 10) << 4) & 240) | ((this.second % 10) & 15));
            return outStream.toByteArray();
        }

        public long toMillis() {
            try {
                LocalDateTime localDateTime = LocalDateTime.of(this.year, this.monthOrdinal, this.monthDay, this.hour, this.minute, this.second);
                Instant instant = localDateTime.toInstant(this.mZoneId.getRules().getOffset(localDateTime));
                return instant.toEpochMilli();
            } catch (DateTimeException ex) {
                Rlog.e(BearerData.LOG_TAG, "Invalid timestamp", ex);
                return 0L;
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("TimeStamp ");
            builder.append("{ year=" + this.year);
            builder.append(", month=" + this.monthOrdinal);
            builder.append(", day=" + this.monthDay);
            builder.append(", hour=" + this.hour);
            builder.append(", minute=" + this.minute);
            builder.append(", second=" + this.second);
            builder.append(" }");
            return builder.toString();
        }
    }

    private static class CodingException extends Exception {
        public CodingException(String s) {
            super(s);
        }
    }

    public String getLanguage() {
        return getLanguageCodeForValue(this.language);
    }

    private static String getLanguageCodeForValue(int languageValue) {
        switch (languageValue) {
            case 1:
                return "en";
            case 2:
                return "fr";
            case 3:
                return "es";
            case 4:
                return "ja";
            case 5:
                return "ko";
            case 6:
                return "zh";
            case 7:
                return "he";
            default:
                return null;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BearerData ");
        builder.append("{ messageType=" + this.messageType);
        builder.append(", messageId=" + this.messageId);
        builder.append(", priority=" + (this.priorityIndicatorSet ? Integer.valueOf(this.priority) : "unset"));
        builder.append(", privacy=" + (this.privacyIndicatorSet ? Integer.valueOf(this.privacy) : "unset"));
        builder.append(", alert=" + (this.alertIndicatorSet ? Integer.valueOf(this.alert) : "unset"));
        builder.append(", displayMode=" + (this.displayModeSet ? Integer.valueOf(this.displayMode) : "unset"));
        builder.append(", language=" + (this.languageIndicatorSet ? Integer.valueOf(this.language) : "unset"));
        builder.append(", errorClass=" + (this.messageStatusSet ? Integer.valueOf(this.errorClass) : "unset"));
        builder.append(", msgStatus=" + (this.messageStatusSet ? Integer.valueOf(this.messageStatus) : "unset"));
        builder.append(", msgCenterTimeStamp=" + (this.msgCenterTimeStamp != null ? this.msgCenterTimeStamp : "unset"));
        builder.append(", validityPeriodAbsolute=" + (this.validityPeriodAbsolute != null ? this.validityPeriodAbsolute : "unset"));
        builder.append(", validityPeriodRelative=" + (this.validityPeriodRelativeSet ? Integer.valueOf(this.validityPeriodRelative) : "unset"));
        builder.append(", deferredDeliveryTimeAbsolute=" + (this.deferredDeliveryTimeAbsolute != null ? this.deferredDeliveryTimeAbsolute : "unset"));
        builder.append(", deferredDeliveryTimeRelative=" + (this.deferredDeliveryTimeRelativeSet ? Integer.valueOf(this.deferredDeliveryTimeRelative) : "unset"));
        builder.append(", userAckReq=" + this.userAckReq);
        builder.append(", deliveryAckReq=" + this.deliveryAckReq);
        builder.append(", readAckReq=" + this.readAckReq);
        builder.append(", reportReq=" + this.reportReq);
        builder.append(", numberOfMessages=" + this.numberOfMessages);
        builder.append(", callbackNumber=" + Rlog.pii(LOG_TAG, this.callbackNumber));
        builder.append(", depositIndex=" + this.depositIndex);
        builder.append(", hasUserDataHeader=" + this.hasUserDataHeader);
        builder.append(", userData=" + this.userData);
        builder.append(" }");
        return builder.toString();
    }

    private static void encodeMessageId(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 3);
        bitwiseOutputStream.write(4, bearerData.messageType);
        bitwiseOutputStream.write(8, bearerData.messageId >> 8);
        bitwiseOutputStream.write(8, bearerData.messageId);
        bitwiseOutputStream.write(1, bearerData.hasUserDataHeader ? 1 : 0);
        bitwiseOutputStream.skip(3);
    }

    private static int countAsciiSeptets(CharSequence msg, boolean force) {
        int msgLen = msg.length();
        if (force) {
            return msgLen;
        }
        for (int i = 0; i < msgLen; i++) {
            if (UserData.charToAscii.get(msg.charAt(i), -1) == -1) {
                return -1;
            }
        }
        return msgLen;
    }

    public static GsmAlphabet.TextEncodingDetails calcTextEncodingDetails(CharSequence msg, boolean force7BitEncoding, boolean isEntireMsg, boolean isEms) {
        CharSequence newMsg = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(R.bool.config_sms_force_7bit_encoding)) {
            newMsg = Sms7BitEncodingTranslator.translate(msg, true);
        }
        if (TextUtils.isEmpty(newMsg)) {
            newMsg = msg;
        }
        int septets = countAsciiSeptets(newMsg, force7BitEncoding);
        if (septets != -1 && septets <= 160) {
            GsmAlphabet.TextEncodingDetails ted = new GsmAlphabet.TextEncodingDetails();
            ted.msgCount = 1;
            ted.codeUnitCount = septets;
            ted.codeUnitsRemaining = 160 - septets;
            ted.codeUnitSize = 1;
            return ted;
        }
        GsmAlphabet.TextEncodingDetails ted2 = SmsMessage.calculateLengthForCdma(msg, force7BitEncoding);
        if (ted2.msgCount == 1 && ted2.codeUnitSize == 1 && isEntireMsg) {
            ted2.codeUnitCount = msg.length();
            int octets = ted2.codeUnitCount * 2;
            if (octets > 140) {
                int max_user_data_bytes_with_header = 134;
                if (!android.telephony.SmsMessage.hasEmsSupport() && octets <= (134 - 2) * 9) {
                    max_user_data_bytes_with_header = 134 - 2;
                }
                ted2.msgCount = ((max_user_data_bytes_with_header - 1) + octets) / max_user_data_bytes_with_header;
                ted2.codeUnitsRemaining = ((ted2.msgCount * max_user_data_bytes_with_header) - octets) / 2;
            } else {
                ted2.msgCount = 1;
                ted2.codeUnitsRemaining = (140 - octets) / 2;
            }
            if (!isEms) {
                ted2.codeUnitSize = 3;
                return ted2;
            }
            return ted2;
        }
        return ted2;
    }

    private static byte[] encode7bitAscii(String msg, boolean force) throws CodingException {
        try {
            BitwiseOutputStream outStream = new BitwiseOutputStream(msg.length());
            int msgLen = msg.length();
            for (int i = 0; i < msgLen; i++) {
                int charCode = UserData.charToAscii.get(msg.charAt(i), -1);
                if (charCode == -1) {
                    if (force) {
                        outStream.write(7, 32);
                    } else {
                        throw new CodingException("cannot ASCII encode (" + msg.charAt(i) + NavigationBarInflaterView.KEY_CODE_END);
                    }
                } else {
                    outStream.write(7, charCode);
                }
            }
            return outStream.toByteArray();
        } catch (BitwiseOutputStream.AccessException ex) {
            throw new CodingException("7bit ASCII encode failed: " + ex);
        }
    }

    private static byte[] encodeUtf16(String msg) throws CodingException {
        try {
            return msg.getBytes("utf-16be");
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException("UTF-16 encode failed: " + ex);
        }
    }

    private static class Gsm7bitCodingResult {
        byte[] data;
        int septets;

        private Gsm7bitCodingResult() {
        }
    }

    private static Gsm7bitCodingResult encode7bitGsm(String msg, int septetOffset, boolean force) throws CodingException {
        try {
            byte[] fullData = GsmAlphabet.stringToGsm7BitPacked(msg, septetOffset, !force, 0, 0);
            Gsm7bitCodingResult result = new Gsm7bitCodingResult();
            result.data = new byte[fullData.length - 1];
            System.arraycopy(fullData, 1, result.data, 0, fullData.length - 1);
            result.septets = fullData[0] & 255;
            return result;
        } catch (EncodeException ex) {
            throw new CodingException("7bit GSM encode failed: " + ex);
        }
    }

    private static void encode7bitEms(UserData uData, byte[] udhData, boolean force) throws CodingException {
        int udhBytes = udhData.length + 1;
        int udhSeptets = ((udhBytes * 8) + 6) / 7;
        Gsm7bitCodingResult gcr = encode7bitGsm(uData.payloadStr, udhSeptets, force);
        uData.msgEncoding = 9;
        uData.msgEncodingSet = true;
        uData.numFields = gcr.septets;
        uData.payload = gcr.data;
        uData.payload[0] = (byte) udhData.length;
        System.arraycopy(udhData, 0, uData.payload, 1, udhData.length);
    }

    private static void encode16bitEms(UserData uData, byte[] udhData) throws CodingException {
        byte[] payload = encodeUtf16(uData.payloadStr);
        int udhBytes = udhData.length + 1;
        int udhCodeUnits = (udhBytes + 1) / 2;
        int payloadCodeUnits = payload.length / 2;
        uData.msgEncoding = 4;
        uData.msgEncodingSet = true;
        uData.numFields = udhCodeUnits + payloadCodeUnits;
        uData.payload = new byte[uData.numFields * 2];
        uData.payload[0] = (byte) udhData.length;
        System.arraycopy(udhData, 0, uData.payload, 1, udhData.length);
        System.arraycopy(payload, 0, uData.payload, udhBytes, payload.length);
    }

    private static void encode7bitAsciiEms(UserData uData, byte[] udhData, boolean force) throws CodingException {
        int fill_bits;
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_QMI_CDMA_GSM)) {
            byte[] payload = encode7bitAscii(uData.payloadStr, force);
            int payloadSeptets = 7 * uData.payloadStr.length();
            int udhBitsOccupied = (udhData.length + 1) * 8;
            int fill_bits2 = udhBitsOccupied % 7;
            if (fill_bits2 == 0) {
                fill_bits = fill_bits2;
            } else {
                fill_bits = 7 - fill_bits2;
            }
            int totalBits = payloadSeptets + udhBitsOccupied + fill_bits;
            int totalBytes = (totalBits + 7) / 8;
            if (totalBytes > 140) {
                throw new CodingException("encoded user data too large (" + totalBytes + " > 140 bytes)");
            }
            uData.msgEncoding = 2;
            uData.msgEncodingSet = true;
            uData.numFields = totalBits / 7;
            uData.paddingBits = (totalBytes * 8) - totalBits;
            uData.payload = new byte[totalBytes];
            uData.payload[0] = (byte) udhData.length;
            try {
                BitwiseOutputStream outStream = new BitwiseOutputStream(totalBytes);
                outStream.writeByteArray(udhData.length * 8, udhData);
                if (fill_bits != 0) {
                    byte[] filbits = new byte[1];
                    outStream.writeByteArray(fill_bits, filbits);
                }
                outStream.writeByteArray(payloadSeptets, payload);
                System.arraycopy(outStream.toByteArray(), 0, uData.payload, 1, totalBytes - 1);
                return;
            } catch (BitwiseOutputStream.AccessException ex) {
                throw new CodingException("7bit ASCII encode failed: " + ex);
            }
        }
        try {
            Rlog.d(LOG_TAG, "encode7bitAsciiEms");
            int udhBytes = udhData.length + 1;
            int udhSeptets = ((udhBytes * 8) + 6) / 7;
            int paddingBits = (udhSeptets * 7) - (udhBytes * 8);
            String msg = uData.payloadStr;
            int msgLen = msg.length();
            BitwiseOutputStream outStream2 = new BitwiseOutputStream((paddingBits > 0 ? 1 : 0) + msgLen);
            outStream2.write(paddingBits, 0);
            for (int i = 0; i < msgLen; i++) {
                int charCode = UserData.charToAscii.get(msg.charAt(i), -1);
                if (charCode == -1) {
                    if (force) {
                        outStream2.write(7, 32);
                    } else {
                        throw new CodingException("cannot ASCII encode (" + msg.charAt(i) + NavigationBarInflaterView.KEY_CODE_END);
                    }
                } else {
                    outStream2.write(7, charCode);
                }
            }
            byte[] payload2 = outStream2.toByteArray();
            uData.msgEncoding = 2;
            uData.msgEncodingSet = true;
            uData.numFields = uData.payloadStr.length() + udhSeptets;
            uData.payload = new byte[payload2.length + udhBytes];
            uData.payload[0] = (byte) udhData.length;
            System.arraycopy(udhData, 0, uData.payload, 1, udhData.length);
            System.arraycopy(payload2, 0, uData.payload, udhBytes, payload2.length);
        } catch (BitwiseOutputStream.AccessException ex2) {
            throw new CodingException("7bit ASCII encode failed: " + ex2);
        }
    }

    private static void encodeEmsUserDataPayload(UserData uData) throws CodingException {
        byte[] headerData = SmsHeader.toByteArray(uData.userDataHeader);
        if (uData.msgEncodingSet) {
            if (uData.msgEncoding == 9) {
                encode7bitEms(uData, headerData, true);
                return;
            }
            if (uData.msgEncoding == 4) {
                encode16bitEms(uData, headerData);
                return;
            } else if (uData.msgEncoding == 2) {
                encode7bitAsciiEms(uData, headerData, true);
                return;
            } else {
                if (uData.msgEncoding == 0) {
                    encodeOctet(uData, headerData);
                    return;
                }
                throw new CodingException("unsupported EMS user data encoding (" + uData.msgEncoding + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        try {
            encode7bitEms(uData, headerData, false);
        } catch (CodingException e) {
            encode16bitEms(uData, headerData);
        }
    }

    private static byte[] encodeShiftJis(String msg) throws CodingException {
        try {
            return msg.getBytes("Shift_JIS");
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException("Shift-JIS encode failed: " + ex);
        }
    }

    private static void encodeUserDataPayload(UserData uData) throws CodingException {
        if (uData.payloadStr == null && uData.msgEncoding != 0) {
            Rlog.e(LOG_TAG, "user data with null payloadStr");
            uData.payloadStr = "";
        }
        if (!SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601)) {
            if (uData.userDataHeader != null) {
                encodeEmsUserDataPayload(uData);
                return;
            }
        } else if (uData.userDataHeader != null && uData.msgEncoding != 16) {
            encodeEmsUserDataPayload(uData);
            return;
        }
        if (uData.msgEncodingSet) {
            if (uData.msgEncoding == 0) {
                if (uData.payload == null) {
                    Rlog.e(LOG_TAG, "user data with octet encoding but null payload");
                    uData.payload = new byte[0];
                    uData.numFields = 0;
                    return;
                }
                uData.numFields = uData.payload.length;
                return;
            }
            if (uData.payloadStr == null) {
                Rlog.e(LOG_TAG, "non-octet user data with null payloadStr");
                uData.payloadStr = "";
            }
            if (uData.msgEncoding == 9) {
                Gsm7bitCodingResult gcr = encode7bitGsm(uData.payloadStr, 0, true);
                uData.payload = gcr.data;
                uData.numFields = gcr.septets;
                return;
            }
            if (uData.msgEncoding == 2) {
                uData.payload = encode7bitAscii(uData.payloadStr, true);
                uData.numFields = uData.payloadStr.length();
                return;
            }
            if (uData.msgEncoding == 4) {
                uData.payload = encodeUtf16(uData.payloadStr);
                uData.numFields = uData.payloadStr.length();
                return;
            } else if (uData.msgEncoding == 5) {
                uData.payload = encodeShiftJis(uData.payloadStr);
                uData.numFields = uData.payload.length;
                return;
            } else {
                if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601) && uData.msgEncoding == 16) {
                    uData.payload = encodeKSC5601(uData.payloadStr);
                    uData.numFields = uData.payload.length;
                    return;
                }
                throw new CodingException("unsupported user data encoding (" + uData.msgEncoding + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        if (uData.payloadStr != null) {
            try {
                if (uData.isAutoLogin) {
                    uData.payload = encode7bitAsciiForAutoLogin(uData.payloadStr, false);
                } else {
                    uData.payload = encode7bitAscii(uData.payloadStr, false);
                }
                uData.msgEncoding = 2;
            } catch (CodingException e) {
                uData.payload = encodeUtf16(uData.payloadStr);
                uData.msgEncoding = 4;
            }
            uData.numFields = uData.payloadStr.length();
        }
        uData.msgEncodingSet = true;
    }

    private static void encodeUserData(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException, CodingException {
        encodeUserDataPayload(bData.userData);
        bData.hasUserDataHeader = bData.userData.userDataHeader != null;
        if (bData.userData.payload.length > 140) {
            throw new CodingException("encoded user data too large (" + bData.userData.payload.length + " > 140 bytes)");
        }
        if (bData.userData.msgEncoding == 2) {
            bData.userData.paddingBits = (bData.userData.payload.length * 8) - (bData.userData.numFields * 7);
        } else {
            bData.userData.paddingBits = 0;
        }
        int dataBits = (bData.userData.payload.length * 8) - bData.userData.paddingBits;
        int paramBits = dataBits + 13;
        if (bData.userData.msgEncoding == 1 || bData.userData.msgEncoding == 10) {
            paramBits += 8;
        }
        int paramBytes = (paramBits / 8) + (paramBits % 8 > 0 ? 1 : 0);
        int paddingBits = (paramBytes * 8) - paramBits;
        outStream.write(8, paramBytes);
        outStream.write(5, bData.userData.msgEncoding);
        if (bData.userData.msgEncoding == 1 || bData.userData.msgEncoding == 10) {
            outStream.write(8, bData.userData.msgType);
        }
        outStream.write(8, bData.userData.numFields);
        outStream.writeByteArray(dataBits, bData.userData.payload);
        if (paddingBits > 0) {
            outStream.write(paddingBits, 0);
        }
    }

    private static void encodeReplyOption(BearerData bearerData, BitwiseOutputStream bitwiseOutputStream) throws BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(1, bearerData.userAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.deliveryAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.readAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.reportReq ? 1 : 0);
        bitwiseOutputStream.write(4, 0);
    }

    private static byte[] encodeDtmfSmsAddress(String address) {
        int val;
        int digits = address.length();
        int dataBits = digits * 4;
        int dataBytes = dataBits / 8;
        byte[] rawData = new byte[dataBytes + (dataBits % 8 > 0 ? 1 : 0)];
        for (int i = 0; i < digits; i++) {
            char c = address.charAt(i);
            if (c >= '1' && c <= '9') {
                val = c - '0';
            } else if (c == '0') {
                val = 10;
            } else if (c == '*') {
                val = 11;
            } else {
                if (c != '#') {
                    return null;
                }
                val = 12;
            }
            int i2 = i / 2;
            rawData[i2] = (byte) (rawData[i2] | (val << (4 - ((i % 2) * 4))));
        }
        return rawData;
    }

    private static void encodeCdmaSmsAddress(CdmaSmsAddress addr) throws CodingException {
        if (addr.digitMode == 1) {
            try {
                addr.origBytes = addr.address.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                throw new CodingException("invalid SMS address, cannot convert to ASCII");
            }
        } else {
            addr.origBytes = encodeDtmfSmsAddress(addr.address);
        }
    }

    private static void encodeCallbackNumber(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException, CodingException {
        int dataBits;
        CdmaSmsAddress addr = bData.callbackNumber;
        encodeCdmaSmsAddress(addr);
        int paramBits = 9;
        if (addr.digitMode == 1) {
            paramBits = 9 + 7;
            dataBits = addr.numberOfDigits * 8;
        } else {
            int dataBits2 = addr.numberOfDigits;
            dataBits = dataBits2 * 4;
        }
        int paramBits2 = paramBits + dataBits;
        int paramBytes = (paramBits2 / 8) + (paramBits2 % 8 > 0 ? 1 : 0);
        int paddingBits = (paramBytes * 8) - paramBits2;
        outStream.write(8, paramBytes);
        outStream.write(1, addr.digitMode);
        if (addr.digitMode == 1) {
            outStream.write(3, addr.ton);
            outStream.write(4, addr.numberPlan);
        }
        outStream.write(8, addr.numberOfDigits);
        outStream.writeByteArray(dataBits, addr.origBytes);
        if (paddingBits > 0) {
            outStream.write(paddingBits, 0);
        }
    }

    private static void encodeMsgStatus(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(2, bData.errorClass);
        outStream.write(6, bData.messageStatus);
    }

    private static void encodeMsgCount(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(8, bData.numberOfMessages);
    }

    private static void encodeValidityPeriodRel(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(8, bData.validityPeriodRelative);
    }

    private static void encodePrivacyIndicator(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(2, bData.privacy);
        outStream.skip(6);
    }

    private static void encodeLanguageIndicator(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(8, bData.language);
    }

    private static void encodeDisplayMode(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(2, bData.displayMode);
        outStream.skip(6);
    }

    private static void encodePriorityIndicator(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(2, bData.priority);
        outStream.skip(6);
    }

    private static void encodeMsgDeliveryAlert(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 1);
        outStream.write(2, bData.alert);
        outStream.skip(6);
    }

    private static void encodeScpResults(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        ArrayList<CdmaSmsCbProgramResults> results = bData.serviceCategoryProgramResults;
        outStream.write(8, results.size() * 4);
        Iterator<CdmaSmsCbProgramResults> it = results.iterator();
        while (it.hasNext()) {
            CdmaSmsCbProgramResults result = it.next();
            int category = result.getCategory();
            outStream.write(8, category >> 8);
            outStream.write(8, category);
            outStream.write(8, result.getLanguage());
            outStream.write(4, result.getCategoryResult());
            outStream.skip(4);
        }
    }

    private static void encodeMsgCenterTimeStamp(BearerData bData, BitwiseOutputStream outStream) throws BitwiseOutputStream.AccessException {
        outStream.write(8, 6);
        outStream.writeByteArray(48, bData.msgCenterTimeStamp.toByteArray());
    }

    public static byte[] encode(BearerData bData) {
        bData.hasUserDataHeader = (bData.userData == null || bData.userData.userDataHeader == null) ? false : true;
        try {
            BitwiseOutputStream outStream = new BitwiseOutputStream(200);
            outStream.write(8, 0);
            encodeMessageId(bData, outStream);
            if (bData.userData != null) {
                outStream.write(8, 1);
                encodeUserData(bData, outStream);
            }
            if (bData.callbackNumber != null) {
                outStream.write(8, 14);
                encodeCallbackNumber(bData, outStream);
            }
            if (bData.userAckReq || bData.deliveryAckReq || bData.readAckReq || bData.reportReq) {
                outStream.write(8, 10);
                encodeReplyOption(bData, outStream);
            }
            if (bData.numberOfMessages != 0) {
                outStream.write(8, 11);
                encodeMsgCount(bData, outStream);
            }
            if (bData.validityPeriodRelativeSet) {
                outStream.write(8, 5);
                encodeValidityPeriodRel(bData, outStream);
            }
            if (bData.privacyIndicatorSet) {
                outStream.write(8, 9);
                encodePrivacyIndicator(bData, outStream);
            }
            if (bData.languageIndicatorSet) {
                outStream.write(8, 13);
                encodeLanguageIndicator(bData, outStream);
            }
            if (bData.displayModeSet) {
                outStream.write(8, 15);
                encodeDisplayMode(bData, outStream);
            }
            if (bData.priorityIndicatorSet) {
                outStream.write(8, 8);
                encodePriorityIndicator(bData, outStream);
            }
            if (bData.alertIndicatorSet) {
                outStream.write(8, 12);
                encodeMsgDeliveryAlert(bData, outStream);
            }
            if (bData.messageStatusSet) {
                outStream.write(8, 20);
                encodeMsgStatus(bData, outStream);
            }
            if (bData.serviceCategoryProgramResults != null) {
                outStream.write(8, 19);
                encodeScpResults(bData, outStream);
            }
            if (bData.msgCenterTimeStamp != null) {
                outStream.write(8, 3);
                encodeMsgCenterTimeStamp(bData, outStream);
            }
            return outStream.toByteArray();
        } catch (CodingException ex) {
            Rlog.e(LOG_TAG, "BearerData encode failed: " + ex);
            return null;
        } catch (BitwiseOutputStream.AccessException ex2) {
            Rlog.e(LOG_TAG, "BearerData encode failed: " + ex2);
            return null;
        }
    }

    private static boolean decodeMessageId(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 24) {
            paramBits -= 24;
            decodeSuccess = true;
            bData.messageType = inStream.read(4);
            bData.messageId = inStream.read(8) << 8;
            bData.messageId = inStream.read(8) | bData.messageId;
            bData.hasUserDataHeader = inStream.read(1) == 1;
            inStream.skip(3);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "MESSAGE_IDENTIFIER decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeReserved(BearerData bData, BitwiseInputStream inStream, int subparamId) throws BitwiseInputStream.AccessException, CodingException {
        boolean decodeSuccess = false;
        int subparamLen = inStream.read(8);
        int paramBits = subparamLen * 8;
        if (paramBits <= inStream.available()) {
            decodeSuccess = true;
            inStream.skip(paramBits);
        }
        Rlog.d(LOG_TAG, "RESERVED bearer data subparameter " + subparamId + " decode " + (decodeSuccess ? "succeeded" : "failed") + " (param bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        if (!decodeSuccess) {
            throw new CodingException("RESERVED bearer data subparameter " + subparamId + " had invalid SUBPARAM_LEN " + subparamLen);
        }
        return decodeSuccess;
    }

    private static boolean decodeUserData(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        int paramBits = inStream.read(8) * 8;
        bData.userData = new UserData();
        bData.userData.msgEncoding = inStream.read(5);
        bData.userData.msgEncodingSet = true;
        bData.userData.msgType = 0;
        int consumedBits = 5;
        if (bData.userData.msgEncoding == 1 || bData.userData.msgEncoding == 10) {
            bData.userData.msgType = inStream.read(8);
            consumedBits = 5 + 8;
        }
        bData.userData.numFields = inStream.read(8);
        int dataBits = paramBits - (consumedBits + 8);
        bData.userData.payload = inStream.readByteArray(dataBits);
        return true;
    }

    private static String decodeUtf8(byte[] data, int offset, int numFields) throws CodingException {
        return decodeCharset(data, offset, numFields, 1, "UTF-8");
    }

    private static String decodeUtf16(byte[] data, int offset, int numFields) throws CodingException {
        int padding = offset % 2;
        int numFields2 = numFields - ((offset + padding) / 2);
        if (!android.telephony.SmsMessage.getCDMASmsReassembly()) {
            userLength = (numFields2 * 2) + offset;
            compChar = (char) ((data[userLength - 2] & 255) << 8);
            compChar = (char) (compChar | ((char) (data[userLength - 1] & 255)));
            if ((compChar == 55357 || compChar == 55356 || compChar == 55358) && userLength == 140) {
                Rlog.d(LOG_TAG, "emoji is broken in the end of segment");
                mlastByte = new byte[2];
                mlastByte[0] = data[userLength - 2];
                mlastByte[1] = data[userLength - 1];
                mBodyOffset = offset;
                mIsfourBytesUnicode = true;
            } else {
                mIsfourBytesUnicode = false;
            }
        }
        return decodeCharset(data, offset, numFields2, 2, "utf-16be");
    }

    private static String decodeCharset(byte[] data, int offset, int numFields, int width, String charset) throws CodingException {
        if (numFields < 0 || (numFields * width) + offset > data.length) {
            int padding = offset % width;
            int maxNumFields = ((data.length - offset) - padding) / width;
            if (maxNumFields < 0) {
                throw new CodingException(charset + " decode failed: offset out of range");
            }
            Rlog.e(LOG_TAG, charset + " decode error: offset = " + offset + " numFields = " + numFields + " data.length = " + data.length + " maxNumFields = " + maxNumFields);
            numFields = maxNumFields;
        }
        try {
            return new String(data, offset, numFields * width, charset);
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException(charset + " decode failed: " + ex);
        }
    }

    private static String decode7bitAscii(byte[] data, int offset, int numFields) throws CodingException {
        int offsetSeptets;
        int numFields2;
        int paddingBits;
        int numFields3;
        if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_QMI_CDMA_GSM)) {
            int offsetBits = offset * 8;
            try {
                int offsetSeptets2 = (offsetBits + 6) / 7;
                paddingBits = (offsetSeptets2 * 7) - offsetBits;
                numFields3 = numFields - offsetSeptets2;
            } catch (BitwiseInputStream.AccessException e) {
                ex = e;
            }
            try {
                StringBuffer strBuf = new StringBuffer(numFields3);
                BitwiseInputStream inStream = new BitwiseInputStream(data);
                int wantedBits = (numFields3 * 7) + offsetBits + paddingBits;
                if (inStream.available() < wantedBits) {
                    throw new CodingException("insufficient data (wanted " + wantedBits + " bits, but only have " + inStream.available() + NavigationBarInflaterView.KEY_CODE_END);
                }
                inStream.skip(offsetBits + paddingBits);
                for (int i = 0; i < numFields3; i++) {
                    int charCode = inStream.read(7);
                    if (charCode >= 32 && charCode <= UserData.ASCII_MAP_MAX_INDEX) {
                        strBuf.append(UserData.ASCII_MAP[charCode - 32]);
                    } else if (charCode == 10) {
                        strBuf.append('\n');
                    } else if (charCode == 13) {
                        strBuf.append('\r');
                    } else {
                        strBuf.append(' ');
                    }
                }
                return strBuf.toString();
            } catch (BitwiseInputStream.AccessException e2) {
                ex = e2;
                throw new CodingException("7bit ASCII decode failed: " + ex);
            }
        }
        try {
            offsetSeptets = ((offset * 8) + 6) / 7;
            numFields2 = numFields - offsetSeptets;
        } catch (BitwiseInputStream.AccessException e3) {
            ex = e3;
        }
        try {
            StringBuffer strBuf2 = new StringBuffer(numFields2);
            BitwiseInputStream inStream2 = new BitwiseInputStream(data);
            int wantedBits2 = (offsetSeptets * 7) + (numFields2 * 7);
            if (inStream2.available() < wantedBits2) {
                throw new CodingException("insufficient data (wanted " + wantedBits2 + " bits, but only have " + inStream2.available() + NavigationBarInflaterView.KEY_CODE_END);
            }
            inStream2.skip(offsetSeptets * 7);
            for (int i2 = 0; i2 < numFields2; i2++) {
                int charCode2 = inStream2.read(7);
                if (charCode2 >= 32 && charCode2 <= UserData.ASCII_MAP_MAX_INDEX) {
                    strBuf2.append(UserData.ASCII_MAP[charCode2 - 32]);
                } else if (charCode2 == 10) {
                    strBuf2.append('\n');
                } else if (charCode2 == 13) {
                    strBuf2.append('\r');
                } else {
                    strBuf2.append(' ');
                }
            }
            return strBuf2.toString();
        } catch (BitwiseInputStream.AccessException e4) {
            ex = e4;
            throw new CodingException("7bit ASCII decode failed: " + ex);
        }
    }

    private static String decode7bitGsm(byte[] data, int offset, int numFields) throws CodingException {
        int offsetBits = offset * 8;
        int offsetSeptets = (offsetBits + 6) / 7;
        int paddingBits = (offsetSeptets * 7) - offsetBits;
        String result = GsmAlphabet.gsm7BitPackedToString(data, offset, numFields - offsetSeptets, paddingBits, 0, 0);
        if (result == null) {
            throw new CodingException("7bit GSM decoding failed");
        }
        return result;
    }

    private static String decodeLatin(byte[] data, int offset, int numFields) throws CodingException {
        return decodeCharset(data, offset, numFields, 1, "ISO-8859-1");
    }

    private static String decodeShiftJis(byte[] data, int offset, int numFields) throws CodingException {
        return decodeCharset(data, offset, numFields, 1, "Shift_JIS");
    }

    private static String decodeGsmDcs(byte[] data, int offset, int numFields, int msgType) throws CodingException {
        if ((msgType & 192) != 0) {
            throw new CodingException("unsupported coding group (" + msgType + NavigationBarInflaterView.KEY_CODE_END);
        }
        switch ((msgType >> 2) & 3) {
            case 0:
                return decode7bitGsm(data, offset, numFields);
            case 1:
                return decodeUtf8(data, offset, numFields);
            case 2:
                return decodeUtf16(data, offset, numFields);
            default:
                throw new CodingException("unsupported user msgType encoding (" + msgType + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private static void decodeUserDataPayload(UserData userData, boolean hasUserDataHeader) throws CodingException {
        StringBuilder sb;
        int offset = 0;
        if (hasUserDataHeader) {
            int udhLen = userData.payload[0] & 255;
            offset = 0 + udhLen + 1;
            byte[] headerData = new byte[udhLen];
            System.arraycopy(userData.payload, 1, headerData, 0, udhLen);
            userData.userDataHeader = SmsHeader.fromByteArray(headerData);
        }
        switch (userData.msgEncoding) {
            case 0:
                boolean decodingtypeUTF8 = Resources.getSystem().getBoolean(R.bool.config_sms_utf8_support);
                byte[] payload = new byte[userData.numFields];
                int copyLen = userData.numFields < userData.payload.length ? userData.numFields : userData.payload.length;
                System.arraycopy(userData.payload, 0, payload, 0, copyLen);
                userData.payload = payload;
                if (!decodingtypeUTF8) {
                    userData.payloadStr = decodeLatin(userData.payload, offset, userData.numFields);
                    return;
                } else {
                    userData.payloadStr = decodeUtf8(userData.payload, offset, userData.numFields);
                    return;
                }
            case 1:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
                throw new CodingException("unsupported user data encoding (" + userData.msgEncoding + NavigationBarInflaterView.KEY_CODE_END);
            case 2:
            case 3:
                userData.payloadStr = decode7bitAscii(userData.payload, offset, userData.numFields);
                return;
            case 4:
                userData.payloadStr = decodeUtf16(userData.payload, offset, userData.numFields);
                return;
            case 5:
                userData.payloadStr = decodeShiftJis(userData.payload, offset, userData.numFields);
                return;
            case 8:
                userData.payloadStr = decodeLatin(userData.payload, offset, userData.numFields);
                return;
            case 9:
                userData.payloadStr = decode7bitGsm(userData.payload, offset, userData.numFields);
                return;
            case 10:
                if (SmsManager.getDefault().getMnoName().toUpperCase().contains("KDDI")) {
                    if (userData.userDataHeader != null && userData.userDataHeader.portAddrs != null && userData.userDataHeader.portAddrs.destPort == 2948) {
                        Rlog.d(LOG_TAG, "[pre_payload]: " + IccUtils.bytesToHexString(userData.payload));
                        ByteArrayInputStream bais = new ByteArrayInputStream(userData.payload);
                        DataInputStream dis = new DataInputStream(bais);
                        try {
                            try {
                                dis.skipBytes(offset);
                                int len = userData.numFields - offset;
                                userData.payload = new byte[len];
                                int numRead = dis.read(userData.payload, 0, len);
                                if (numRead < 0) {
                                    Rlog.e(LOG_TAG, "decodeUserDataPayload: the end of the stream has been reached.");
                                }
                                Rlog.d(LOG_TAG, "[post_payload]: " + IccUtils.bytesToHexString(userData.payload));
                                try {
                                    dis.close();
                                    return;
                                } catch (Exception e) {
                                    ex = e;
                                    sb = new StringBuilder();
                                    Rlog.e(LOG_TAG, sb.append("decodeUserDataPayload: conversion from byte array to object failed: ").append(ex).toString());
                                    return;
                                }
                            } catch (Exception ex) {
                                Rlog.e(LOG_TAG, "decodeUserDataPayload: conversion from byte array to object failed: " + ex);
                                try {
                                    dis.close();
                                    return;
                                } catch (Exception e2) {
                                    ex = e2;
                                    sb = new StringBuilder();
                                    Rlog.e(LOG_TAG, sb.append("decodeUserDataPayload: conversion from byte array to object failed: ").append(ex).toString());
                                    return;
                                }
                            }
                        } catch (Throwable ex2) {
                            try {
                                dis.close();
                            } catch (Exception ex3) {
                                Rlog.e(LOG_TAG, "decodeUserDataPayload: conversion from byte array to object failed: " + ex3);
                            }
                            throw ex2;
                        }
                    }
                    Rlog.w(LOG_TAG, "[[ENCODING_GSM_DCS]] userData.msgType = " + (userData.msgType & 255));
                    if ((userData.msgType & 128) == 0) {
                        switch ((userData.msgType >> 2) & 3) {
                            case 0:
                                userData.payloadStr = decode7bitGsm(userData.payload, offset, userData.numFields);
                                return;
                            case 1:
                                boolean decodingtypeUTF8_GsmDcs = Resources.getSystem().getBoolean(R.bool.config_sms_utf8_support);
                                byte[] payload_GsmDcs = new byte[userData.numFields];
                                int copyLen_GsmDcs = userData.numFields < userData.payload.length ? userData.numFields : userData.payload.length;
                                System.arraycopy(userData.payload, 0, payload_GsmDcs, 0, copyLen_GsmDcs);
                                userData.payload = payload_GsmDcs;
                                if (!decodingtypeUTF8_GsmDcs) {
                                    userData.payloadStr = decodeLatin(userData.payload, offset, userData.numFields);
                                    return;
                                } else {
                                    userData.payloadStr = decodeUtf8(userData.payload, offset, userData.numFields);
                                    return;
                                }
                            case 2:
                                userData.payloadStr = decodeUtf16_KDDI(userData.payload, offset, userData.numFields);
                                return;
                            default:
                                return;
                        }
                    }
                    return;
                }
                userData.payloadStr = decodeGsmDcs(userData.payload, offset, userData.numFields, userData.msgType);
                return;
            case 16:
                userData.payloadStr = decodeKSC5601(userData.payload, offset, userData.numFields);
                return;
        }
    }

    private static void decodeIs91VoicemailStatus(BearerData bData) throws BitwiseInputStream.AccessException, CodingException {
        BitwiseInputStream inStream = new BitwiseInputStream(bData.userData.payload);
        int dataLen = inStream.available() / 6;
        int numFields = bData.userData.numFields;
        if (dataLen > 14 || dataLen < 3 || dataLen < numFields) {
            throw new CodingException("IS-91 voicemail status decoding failed");
        }
        try {
            StringBuffer strbuf = new StringBuffer(dataLen);
            while (inStream.available() >= 6) {
                strbuf.append(UserData.ASCII_MAP[inStream.read(6)]);
            }
            String data = strbuf.toString();
            bData.numberOfMessages = Integer.parseInt(data.substring(0, 2));
            char prioCode = data.charAt(2);
            if (prioCode == ' ') {
                bData.priority = 0;
            } else if (prioCode == '!') {
                bData.priority = 2;
            } else {
                throw new CodingException("IS-91 voicemail status decoding failed: illegal priority setting (" + prioCode + NavigationBarInflaterView.KEY_CODE_END);
            }
            bData.priorityIndicatorSet = true;
            bData.userData.payloadStr = data.substring(3, numFields - 3);
        } catch (IndexOutOfBoundsException ex) {
            throw new CodingException("IS-91 voicemail status decoding failed: " + ex);
        } catch (NumberFormatException ex2) {
            throw new CodingException("IS-91 voicemail status decoding failed: " + ex2);
        }
    }

    private static void decodeIs91ShortMessage(BearerData bData) throws BitwiseInputStream.AccessException, CodingException {
        BitwiseInputStream inStream = new BitwiseInputStream(bData.userData.payload);
        int dataLen = inStream.available() / 6;
        int numFields = bData.userData.numFields;
        if (numFields > 14 || dataLen < numFields) {
            throw new CodingException("IS-91 short message decoding failed");
        }
        StringBuffer strbuf = new StringBuffer(dataLen);
        for (int i = 0; i < numFields; i++) {
            strbuf.append(UserData.ASCII_MAP[inStream.read(6)]);
        }
        bData.userData.payloadStr = strbuf.toString();
    }

    private static void decodeIs91Cli(BearerData bData) throws CodingException {
        BitwiseInputStream inStream = new BitwiseInputStream(bData.userData.payload);
        int dataLen = inStream.available() / 4;
        int numFields = bData.userData.numFields;
        if (dataLen > 14 || dataLen < 3 || dataLen < numFields) {
            throw new CodingException("IS-91 voicemail status decoding failed");
        }
        CdmaSmsAddress addr = new CdmaSmsAddress();
        addr.digitMode = 0;
        addr.origBytes = bData.userData.payload;
        addr.numberOfDigits = (byte) numFields;
        decodeSmsAddress(addr);
        bData.callbackNumber = addr;
    }

    private static void decodeIs91(BearerData bData) throws BitwiseInputStream.AccessException, CodingException {
        switch (bData.userData.msgType) {
            case 130:
                decodeIs91VoicemailStatus(bData);
                return;
            case 131:
            case 133:
                decodeIs91ShortMessage(bData);
                return;
            case 132:
                decodeIs91Cli(bData);
                return;
            default:
                throw new CodingException("unsupported IS-91 message type (" + bData.userData.msgType + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private static boolean decodeReplyOption(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.userAckReq = inStream.read(1) == 1;
            bData.deliveryAckReq = inStream.read(1) == 1;
            bData.readAckReq = inStream.read(1) == 1;
            bData.reportReq = inStream.read(1) == 1;
            inStream.skip(4);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "REPLY_OPTION decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeMsgCount(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            if (SmsManager.getDefault().getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
                bData.numberOfMessages = IccUtils.cdmaHexByteToInt((byte) inStream.read(8));
            } else {
                bData.numberOfMessages = IccUtils.cdmaBcdByteToInt((byte) inStream.read(8));
            }
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "NUMBER_OF_MESSAGES decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeDepositIndex(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 16) {
            paramBits -= 16;
            decodeSuccess = true;
            bData.depositIndex = inStream.read(8) | (inStream.read(8) << 8);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "MESSAGE_DEPOSIT_INDEX decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static String decodeDtmfSmsAddress(byte[] rawData, int numFields) throws CodingException {
        StringBuffer strBuf = new StringBuffer(numFields);
        for (int i = 0; i < numFields; i++) {
            int val = (rawData[i / 2] >>> (4 - ((i % 2) * 4))) & 15;
            if (val < 1 || val > 9) {
                if (val == 10) {
                    strBuf.append('0');
                } else if (val == 11) {
                    strBuf.append('*');
                } else if (val == 12) {
                    strBuf.append('#');
                } else if (val == 0) {
                    strBuf.append('0');
                } else {
                    throw new CodingException("invalid SMS address DTMF code (" + val + NavigationBarInflaterView.KEY_CODE_END);
                }
            } else {
                strBuf.append(Integer.toString(val, 10));
            }
        }
        return strBuf.toString();
    }

    private static void decodeSmsAddress(CdmaSmsAddress addr) throws CodingException {
        if (addr.digitMode == 1) {
            try {
                addr.address = new String(addr.origBytes, 0, addr.origBytes.length, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                throw new CodingException("invalid SMS address ASCII code");
            }
        } else {
            addr.address = decodeDtmfSmsAddress(addr.origBytes, addr.numberOfDigits);
        }
    }

    private static boolean decodeCallbackNumber(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException, CodingException {
        int paramBits = inStream.read(8) * 8;
        if (paramBits < 8) {
            inStream.skip(paramBits);
            return false;
        }
        CdmaSmsAddress addr = new CdmaSmsAddress();
        addr.digitMode = inStream.read(1);
        byte fieldBits = 4;
        byte consumedBits = 1;
        if (addr.digitMode == 1) {
            addr.ton = inStream.read(3);
            addr.numberPlan = inStream.read(4);
            fieldBits = 8;
            consumedBits = (byte) (1 + 7);
        }
        addr.numberOfDigits = inStream.read(8);
        int remainingBits = paramBits - ((byte) (consumedBits + 8));
        int dataBits = addr.numberOfDigits * fieldBits;
        int paddingBits = remainingBits - dataBits;
        if (remainingBits < dataBits) {
            throw new CodingException("CALLBACK_NUMBER subparam encoding size error (remainingBits + " + remainingBits + ", dataBits + " + dataBits + ", paddingBits + " + paddingBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        addr.origBytes = inStream.readByteArray(dataBits);
        inStream.skip(paddingBits);
        decodeSmsAddress(addr);
        bData.callbackNumber = addr;
        return true;
    }

    private static boolean decodeMsgStatus(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.errorClass = inStream.read(2);
            bData.messageStatus = inStream.read(6);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "MESSAGE_STATUS decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.messageStatusSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeMsgCenterTimeStamp(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 48) {
            paramBits -= 48;
            decodeSuccess = true;
            bData.msgCenterTimeStamp = TimeStamp.fromByteArray(inStream.readByteArray(48));
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "MESSAGE_CENTER_TIME_STAMP decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeValidityAbs(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 48) {
            paramBits -= 48;
            decodeSuccess = true;
            bData.validityPeriodAbsolute = TimeStamp.fromByteArray(inStream.readByteArray(48));
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "VALIDITY_PERIOD_ABSOLUTE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeDeferredDeliveryAbs(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 48) {
            paramBits -= 48;
            decodeSuccess = true;
            bData.deferredDeliveryTimeAbsolute = TimeStamp.fromByteArray(inStream.readByteArray(48));
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "DEFERRED_DELIVERY_TIME_ABSOLUTE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        return decodeSuccess;
    }

    private static boolean decodeValidityRel(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.deferredDeliveryTimeRelative = inStream.read(8);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "VALIDITY_PERIOD_RELATIVE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.deferredDeliveryTimeRelativeSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeDeferredDeliveryRel(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.validityPeriodRelative = inStream.read(8);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "DEFERRED_DELIVERY_TIME_RELATIVE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.validityPeriodRelativeSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodePrivacyIndicator(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.privacy = inStream.read(2);
            inStream.skip(6);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "PRIVACY_INDICATOR decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.privacyIndicatorSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeLanguageIndicator(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.language = inStream.read(8);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "LANGUAGE_INDICATOR decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.languageIndicatorSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeDisplayMode(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.displayMode = inStream.read(2);
            inStream.skip(6);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "DISPLAY_MODE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.displayModeSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodePriorityIndicator(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.priority = inStream.read(2);
            inStream.skip(6);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "PRIORITY_INDICATOR decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.priorityIndicatorSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeMsgDeliveryAlert(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.alert = inStream.read(2);
            inStream.skip(6);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "ALERT_ON_MESSAGE_DELIVERY decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.alertIndicatorSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeUserResponseCode(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException {
        boolean decodeSuccess = false;
        int paramBits = inStream.read(8) * 8;
        if (paramBits >= 8) {
            paramBits -= 8;
            decodeSuccess = true;
            bData.userResponseCode = inStream.read(8);
        }
        if (!decodeSuccess || paramBits > 0) {
            Rlog.d(LOG_TAG, "USER_RESPONSE_CODE decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits + NavigationBarInflaterView.KEY_CODE_END);
        }
        inStream.skip(paramBits);
        bData.userResponseCodeSet = decodeSuccess;
        return decodeSuccess;
    }

    private static boolean decodeServiceCategoryProgramData(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException, CodingException {
        if (inStream.available() < 13) {
            throw new CodingException("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only " + inStream.available() + " bits available");
        }
        int textBits = 8;
        int paramBits = inStream.read(8) * 8;
        int msgEncoding = inStream.read(5);
        int paramBits2 = paramBits - 5;
        if (inStream.available() < paramBits2) {
            throw new CodingException("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only " + inStream.available() + " bits available (" + paramBits2 + " bits expected)");
        }
        ArrayList<CdmaSmsCbProgramData> programDataList = new ArrayList<>();
        boolean decodeSuccess = false;
        while (paramBits2 >= 48) {
            int operation = inStream.read(4);
            int category = (inStream.read(textBits) << textBits) | inStream.read(textBits);
            int language = inStream.read(textBits);
            int maxMessages = inStream.read(textBits);
            int alertOption = inStream.read(4);
            int numFields = inStream.read(textBits);
            int paramBits3 = paramBits2 - 48;
            int textBits2 = getBitsForNumFields(msgEncoding, numFields);
            if (paramBits3 < textBits2) {
                throw new CodingException("category name is " + textBits2 + " bits in length, but there are only " + paramBits3 + " bits available");
            }
            UserData userData = new UserData();
            userData.msgEncoding = msgEncoding;
            userData.msgEncodingSet = true;
            userData.numFields = numFields;
            userData.payload = inStream.readByteArray(textBits2);
            paramBits2 = paramBits3 - textBits2;
            decodeUserDataPayload(userData, false);
            String categoryName = userData.payloadStr;
            CdmaSmsCbProgramData programData = new CdmaSmsCbProgramData(operation, category, language, maxMessages, alertOption, categoryName);
            programDataList.add(programData);
            decodeSuccess = true;
            textBits = 8;
        }
        if (!decodeSuccess || paramBits2 > 0) {
            Rlog.d(LOG_TAG, "SERVICE_CATEGORY_PROGRAM_DATA decode " + (decodeSuccess ? "succeeded" : "failed") + " (extra bits = " + paramBits2 + ')');
        }
        inStream.skip(paramBits2);
        bData.serviceCategoryProgramData = programDataList;
        return decodeSuccess;
    }

    private static int serviceCategoryToCmasMessageClass(int serviceCategory) {
        switch (serviceCategory) {
            case 4096:
                return 0;
            case 4097:
                return 1;
            case 4098:
                return 2;
            case 4099:
                return 3;
            case 4100:
                return 4;
            default:
                return -1;
        }
    }

    private static int getBitsForNumFields(int msgEncoding, int numFields) throws CodingException {
        switch (msgEncoding) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
                return numFields * 8;
            case 1:
            default:
                throw new CodingException("unsupported message encoding (" + msgEncoding + ')');
            case 2:
            case 3:
            case 9:
                return numFields * 7;
            case 4:
                return numFields * 16;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void decodeCmasUserData(com.android.internal.telephony.cdma.sms.BearerData r17, int r18) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.cdma.sms.BearerData.decodeCmasUserData(com.android.internal.telephony.cdma.sms.BearerData, int):void");
    }

    public static BearerData decode(byte[] smsData) {
        return decode(smsData, 0);
    }

    private static boolean isCmasAlertCategory(int category) {
        return category >= 4096 && category <= 4351;
    }

    public static BearerData decode(byte[] smsData, int serviceCategory) {
        boolean decodeSuccess;
        try {
            BitwiseInputStream inStream = new BitwiseInputStream(smsData);
            BearerData bData = new BearerData();
            int foundSubparamMask = 0;
            while (inStream.available() > 0) {
                int subparamId = inStream.read(8);
                int subparamIdBit = 1 << subparamId;
                if ((foundSubparamMask & subparamIdBit) != 0 && subparamId >= 0 && subparamId <= 23) {
                    throw new CodingException("illegal duplicate subparameter (" + subparamId + NavigationBarInflaterView.KEY_CODE_END);
                }
                switch (subparamId) {
                    case 0:
                        decodeSuccess = decodeMessageId(bData, inStream);
                        break;
                    case 1:
                        decodeSuccess = decodeUserData(bData, inStream);
                        break;
                    case 2:
                        decodeSuccess = decodeUserResponseCode(bData, inStream);
                        break;
                    case 3:
                        decodeSuccess = decodeMsgCenterTimeStamp(bData, inStream);
                        break;
                    case 4:
                        decodeSuccess = decodeValidityAbs(bData, inStream);
                        break;
                    case 5:
                        decodeSuccess = decodeValidityRel(bData, inStream);
                        break;
                    case 6:
                        decodeSuccess = decodeDeferredDeliveryAbs(bData, inStream);
                        break;
                    case 7:
                        decodeSuccess = decodeDeferredDeliveryRel(bData, inStream);
                        break;
                    case 8:
                        decodeSuccess = decodePriorityIndicator(bData, inStream);
                        break;
                    case 9:
                        decodeSuccess = decodePrivacyIndicator(bData, inStream);
                        break;
                    case 10:
                        decodeSuccess = decodeReplyOption(bData, inStream);
                        break;
                    case 11:
                        decodeSuccess = decodeMsgCount(bData, inStream);
                        break;
                    case 12:
                        decodeSuccess = decodeMsgDeliveryAlert(bData, inStream);
                        break;
                    case 13:
                        decodeSuccess = decodeLanguageIndicator(bData, inStream);
                        break;
                    case 14:
                        decodeSuccess = decodeCallbackNumber(bData, inStream);
                        break;
                    case 15:
                        decodeSuccess = decodeDisplayMode(bData, inStream);
                        break;
                    case 16:
                    case 19:
                    default:
                        decodeSuccess = decodeReserved(bData, inStream, subparamId);
                        break;
                    case 17:
                        decodeSuccess = decodeDepositIndex(bData, inStream);
                        break;
                    case 18:
                        decodeSuccess = decodeServiceCategoryProgramData(bData, inStream);
                        break;
                    case 20:
                        decodeSuccess = decodeMsgStatus(bData, inStream);
                        break;
                }
                if (decodeSuccess && subparamId >= 0 && subparamId <= 23) {
                    foundSubparamMask |= subparamIdBit;
                }
            }
            if ((foundSubparamMask & 1) == 0) {
                throw new CodingException("missing MESSAGE_IDENTIFIER subparam");
            }
            if (bData.userData != null) {
                if (isCmasAlertCategory(serviceCategory)) {
                    decodeCmasUserData(bData, serviceCategory);
                } else if (bData.userData.msgEncoding == 1) {
                    if (((foundSubparamMask ^ 1) ^ 2) != 0) {
                        Rlog.e(LOG_TAG, "IS-91 must occur without extra subparams (" + foundSubparamMask + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    decodeIs91(bData);
                } else {
                    decodeUserDataPayload(bData.userData, bData.hasUserDataHeader);
                    if (android.telephony.SmsMessage.getCDMASmsReassembly()) {
                        extractPagination(bData.userData.payloadStr, bData.userData);
                    }
                }
            }
            return bData;
        } catch (CodingException ex) {
            Rlog.e(LOG_TAG, "BearerData decode failed: " + ex);
            return null;
        } catch (BitwiseInputStream.AccessException ex2) {
            Rlog.e(LOG_TAG, "BearerData decode failed: " + ex2);
            return null;
        }
    }

    private static byte[] encode7bitAsciiForAutoLogin(String msg, boolean force) throws CodingException {
        try {
            int msgLen = msg.length();
            BitwiseOutputStream outStream = new BitwiseOutputStream(msgLen);
            for (int i = 0; i < msgLen; i++) {
                if (i < 4) {
                    outStream.write(7, msg.charAt(i));
                } else {
                    int charCode = UserData.charToAscii.get(msg.charAt(i), -1);
                    if (charCode == -1) {
                        if (force) {
                            outStream.write(7, 32);
                        } else {
                            throw new CodingException("cannot ASCII encode (" + msg.charAt(i) + NavigationBarInflaterView.KEY_CODE_END);
                        }
                    } else {
                        outStream.write(7, charCode);
                    }
                }
            }
            return outStream.toByteArray();
        } catch (BitwiseOutputStream.AccessException ex) {
            throw new CodingException("7bit ASCII encode failed: " + ex);
        }
    }

    private static byte[] encodeKSC5601(String msg) throws CodingException {
        try {
            return msg.getBytes("EUC_KR");
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException("EUC_KR encode failed: " + ex);
        }
    }

    private static void encodeOctet(UserData uData, byte[] udhData) throws CodingException {
        if (uData == null || uData.payloadStr == null) {
            throw new CodingException("encodeOctet failed with null data");
        }
        byte[] payload = uData.payloadStr.getBytes();
        int udhBytes = udhData.length + 1;
        uData.msgEncoding = 0;
        uData.msgEncodingSet = true;
        uData.numFields = uData.payloadStr.length() + udhBytes;
        uData.payload = new byte[uData.numFields];
        uData.payload[0] = (byte) udhData.length;
        System.arraycopy(udhData, 0, uData.payload, 1, udhData.length);
        System.arraycopy(payload, 0, uData.payload, udhBytes, payload.length);
    }

    private static String decodeUtf16_KDDI(byte[] data, int offset, int numFields) throws CodingException {
        try {
            return new String(data, offset, numFields - offset, "utf-16be");
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException("UTF-16 decode failed: " + ex);
        }
    }

    private static String decodeKSC5601(byte[] data, int offset, int numFields) throws CodingException {
        int padding = offset % 2;
        try {
            return new String(data, offset, numFields - ((offset + padding) / 2), "EUC_KR");
        } catch (UnsupportedEncodingException ex) {
            throw new CodingException("EUC-KR decode failed: " + ex);
        }
    }

    public static GsmAlphabet.TextEncodingDetails calcTextEncodingDetails(CharSequence msg, boolean force7BitEncoding, boolean isEntireMsg) {
        return calcTextEncodingDetails(msg, force7BitEncoding, isEntireMsg, false);
    }

    public static GsmAlphabet.TextEncodingDetails calcTextEncodingDetailsWithEmail(CharSequence msg, boolean force7BitEncoding, int maxEmailLen) {
        int maxLenPerSMS;
        int maxLenPerSMSWithHeader;
        int i;
        int i2;
        GsmAlphabet.TextEncodingDetails ted = new GsmAlphabet.TextEncodingDetails();
        int septets = countAsciiSeptets(msg, force7BitEncoding);
        if (maxEmailLen > 0) {
            maxLenPerSMS = (160 - maxEmailLen) - 1;
        } else {
            maxLenPerSMS = 160;
        }
        if (maxEmailLen > 0) {
            maxLenPerSMSWithHeader = (154 - maxEmailLen) - 1;
        } else {
            maxLenPerSMSWithHeader = 154;
        }
        if (septets == -1 || septets > maxLenPerSMS) {
            if (septets != -1) {
                ted.codeUnitCount = septets;
                if (septets > maxLenPerSMS) {
                    ted.msgCount = ((septets - 1) / maxLenPerSMSWithHeader) + 1;
                    if (septets % maxLenPerSMSWithHeader > 0) {
                        ted.codeUnitsRemaining = maxLenPerSMSWithHeader - (septets % maxLenPerSMSWithHeader);
                    } else {
                        ted.codeUnitsRemaining = 0;
                    }
                } else {
                    ted.msgCount = 1;
                    ted.codeUnitsRemaining = maxLenPerSMS - septets;
                }
                ted.codeUnitSize = 1;
            } else {
                int maxEmailLen2 = maxEmailLen * 2;
                if (maxEmailLen2 > 0) {
                    i = (140 - maxEmailLen2) - 1;
                } else {
                    i = 140;
                }
                int maxLenPerSMS2 = i;
                if (maxEmailLen2 > 0) {
                    i2 = (128 - maxEmailLen2) - 1;
                } else {
                    i2 = 128;
                }
                int maxLenPerSMSWithHeader2 = i2;
                int octets = msg.length() * 2;
                ted.codeUnitCount = msg.length();
                if (octets > maxLenPerSMS2) {
                    if (maxEmailLen2 > maxLenPerSMS2 - 2) {
                        ted.msgCount = 1000;
                        ted.codeUnitsRemaining = -1;
                    } else if (octets % maxLenPerSMSWithHeader2 != 0) {
                        ted.msgCount = (octets / maxLenPerSMSWithHeader2) + 1;
                        ted.codeUnitsRemaining = (maxLenPerSMSWithHeader2 - (octets % maxLenPerSMSWithHeader2)) / 2;
                    } else {
                        ted.msgCount = octets / maxLenPerSMSWithHeader2;
                        ted.codeUnitsRemaining = 0;
                    }
                } else if (maxEmailLen2 >= maxLenPerSMSWithHeader2 - 2) {
                    ted.msgCount = 1000;
                    ted.codeUnitsRemaining = -1;
                } else {
                    ted.msgCount = 1;
                    ted.codeUnitsRemaining = (maxLenPerSMS2 - octets) / 2;
                }
                ted.codeUnitSize = 3;
            }
        } else {
            ted.msgCount = 1;
            ted.codeUnitCount = septets;
            ted.codeUnitsRemaining = maxLenPerSMS - septets;
            ted.codeUnitSize = 1;
        }
        return ted;
    }

    private static void extractPagination(String payloadStr, UserData userData) {
        String payload;
        String payloadStr2 = payloadStr;
        int segNum = 0;
        int totNum = 0;
        boolean paginationSuccess = false;
        String payload2 = payloadStr;
        if (payloadStr2 == null) {
            Rlog.d(LOG_TAG, "there is no message body");
            return;
        }
        String pagination = null;
        try {
            if (payloadStr2.startsWith(NavigationBarInflaterView.KEY_CODE_START) && payloadStr2.contains(NavigationBarInflaterView.KEY_CODE_END)) {
                pagination = payloadStr2.substring(payloadStr2.indexOf(40) + 1, payloadStr2.indexOf(41));
                payloadStr2 = payloadStr2.substring(payloadStr2.indexOf(41) + 2);
            } else if (payloadStr2.startsWith(NavigationBarInflaterView.SIZE_MOD_START) && payloadStr2.contains(NavigationBarInflaterView.SIZE_MOD_END)) {
                pagination = payloadStr2.substring(payloadStr2.indexOf(91) + 1, payloadStr2.indexOf(93));
                payloadStr2 = payloadStr2.substring(payloadStr2.indexOf(93) + 2);
            } else if (!payloadStr2.startsWith("{") || !payloadStr2.contains("}")) {
                Rlog.d(LOG_TAG, "there is no pagination pattern maybe / or of ");
            } else {
                pagination = payloadStr2.substring(payloadStr2.indexOf(123) + 1, payloadStr2.indexOf(125));
                payloadStr2 = payloadStr2.substring(payloadStr2.indexOf(125) + 2);
            }
            if (pagination != null) {
                payload = payloadStr2;
                String[] page = pagination.split("/");
                if (page.length == 2) {
                    try {
                        segNum = Integer.parseInt(page[0].trim());
                        totNum = Integer.parseInt(page[1].trim());
                        paginationSuccess = true;
                    } catch (NumberFormatException e) {
                        Rlog.d(LOG_TAG, "there is no pagination yet");
                        paginationSuccess = false;
                    }
                }
            } else {
                if (payloadStr2.split(" of ").length >= 2) {
                    String[] pageCount = payloadStr2.split(" ");
                    if (pageCount.length >= 3) {
                        try {
                            segNum = Integer.parseInt(pageCount[0].trim());
                            totNum = Integer.parseInt(pageCount[2].trim());
                            int offset = pageCount[0].length() + pageCount[2].length() + 4;
                            payload2 = payloadStr2.substring(offset + 1);
                            paginationSuccess = true;
                        } catch (NumberFormatException e2) {
                            Rlog.d(LOG_TAG, "there is no pagination yet");
                        } catch (StringIndexOutOfBoundsException ex) {
                            Rlog.e(LOG_TAG, "extractPagination : " + ex);
                        }
                    }
                }
                if (!paginationSuccess) {
                    String[] tempPage = payloadStr2.split("/");
                    if (tempPage.length >= 2) {
                        totNum = 0;
                        char[] totalNumber = tempPage[1].toCharArray();
                        try {
                            segNum = Integer.parseInt(tempPage[0].trim());
                            int i = 0;
                            while (Character.isDigit(totalNumber[i])) {
                                if (i == 0) {
                                    totNum = Character.getNumericValue(totalNumber[i]);
                                } else if (i == 1) {
                                    totNum = (Character.getNumericValue(totalNumber[0]) * 10) + Character.getNumericValue(totalNumber[1]);
                                } else if (i == 2) {
                                    totNum = (Character.getNumericValue(totalNumber[0]) * 100) + (Character.getNumericValue(totalNumber[1]) * 10) + Character.getNumericValue(totalNumber[2]);
                                } else if (i == 3) {
                                    totNum = (Character.getNumericValue(totalNumber[0]) * 1000) + (Character.getNumericValue(totalNumber[1]) * 100) + (Character.getNumericValue(totalNumber[2]) * 10) + Character.getNumericValue(totalNumber[3]);
                                }
                                i++;
                            }
                            payload = payloadStr2.substring(tempPage[0].length() + i + 1);
                            paginationSuccess = true;
                        } catch (ArrayIndexOutOfBoundsException ex2) {
                            Rlog.e(LOG_TAG, "extractPagination : " + ex2);
                            return;
                        } catch (NumberFormatException e3) {
                            Rlog.d(LOG_TAG, "there is no pagination");
                            payload = payload2;
                        }
                    }
                }
                payload = payload2;
            }
            byte[] data = payload.getBytes();
            userLength = data.length;
            Rlog.d(LOG_TAG, "spr segment length : " + userLength);
            if (userData.msgEncoding == 3 && userLength > 1) {
                compChar = (char) ((data[userLength - 2] & 255) << 8);
                compChar = (char) (compChar | ((char) (data[userLength - 1] & 255)));
                if (compChar == 55357 || compChar == 55356 || compChar == 55358) {
                    Rlog.d(LOG_TAG, "spr emoji is broken in the end of segment");
                    mlastByte = new byte[2];
                    mlastByte[0] = data[userLength - 2];
                    mlastByte[1] = data[userLength - 1];
                    mIsfourBytesUnicode = true;
                    mBodyOffset = 0;
                } else {
                    mIsfourBytesUnicode = false;
                }
            }
            if (!paginationSuccess) {
                Rlog.d(LOG_TAG, "No pagination found");
                return;
            }
            Rlog.d(LOG_TAG, "segmented number: " + segNum);
            Rlog.d(LOG_TAG, "total number: " + totNum);
            if ((segNum < 0 && segNum > 9999) || (totNum < 0 && segNum > 9999)) {
                Rlog.d(LOG_TAG, "Its not segmented sms. ");
                return;
            }
            if (segNum == 0 || totNum == 0 || segNum > totNum || totNum > 9999) {
                Rlog.d(LOG_TAG, "It's not segmented sms.");
                return;
            }
            Rlog.d(LOG_TAG, "It's segmented sms");
            SmsHeader.ConcatRef concatRef = new SmsHeader.ConcatRef();
            concatRef.seqNumber = segNum;
            concatRef.msgCount = totNum;
            concatRef.refNumber = -1;
            userData.userDataHeader = new SmsHeader();
            userData.userDataHeader.concatRef = concatRef;
        } catch (StringIndexOutOfBoundsException ex3) {
            Rlog.e(LOG_TAG, "extractPagination : " + ex3);
        }
    }

    private static boolean decodeUnknownSubParam(BearerData bData, BitwiseInputStream inStream) throws BitwiseInputStream.AccessException, CodingException {
        int paramBits = inStream.read(8) * 8;
        inStream.skip(paramBits);
        return true;
    }
}
