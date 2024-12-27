package com.android.internal.telephony.gsm;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.drm.DrmManagerClient;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.SystemProperties;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.text.format.Time;
import com.android.internal.R;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.TelephonyFeatures;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.telephony.Rlog;
import com.google.android.mms.pdu.CharacterSets;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class SmsMessage extends SmsMessageBase {
    private static final int INVALID_VALIDITY_PERIOD = -1;
    static final String LOG_TAG = "SmsMessage";
    private static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE = 3;
    private static final int VALIDITY_PERIOD_FORMAT_ENHANCED = 1;
    private static final int VALIDITY_PERIOD_FORMAT_NONE = 0;
    private static final int VALIDITY_PERIOD_FORMAT_RELATIVE = 2;
    private static final int VALIDITY_PERIOD_MAX = 635040;
    private static final int VALIDITY_PERIOD_MIN = 5;
    private static final boolean VDBG = false;
    private int mDataCodingScheme;
    private int mProtocolIdentifier;
    private int mStatus;
    private SmsConstants.MessageClass messageClass;
    private static final String SALES_CODE = SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE);
    private static boolean mUnsupportedDatacodingScheme = false;
    private static boolean mIgnoreSpecialChar = false;
    private boolean mReplyPathPresent = false;
    private boolean mIsStatusReportMessage = false;
    private int mVoiceMailCount = 0;

    public static class SubmitPdu extends SmsMessageBase.SubmitPduBase {
    }

    public static SmsMessage createFromPdu(byte[] pdu) {
        int phoneId = SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId());
        return semCreateFromPdu(phoneId, pdu);
    }

    public boolean isTypeZero() {
        return this.mProtocolIdentifier == 64;
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.mIndexOnIcc = index;
            if ((data[0] & 1) == 0) {
                Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            msg.mStatusOnIcc = data[0] & 7;
            int size = data.length - 1;
            byte[] pdu = new byte[size];
            System.arraycopy(data, 1, pdu, 0, size);
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(String pdu) {
        int len = pdu.length() / 2;
        int smscLen = Integer.parseInt(pdu.substring(0, 2), 16);
        return (len - smscLen) - 1;
    }

    public static int getRelativeValidityPeriod(int validityPeriod) {
        if (validityPeriod < 5) {
            return -1;
        }
        if (validityPeriod <= 720) {
            int relValidityPeriod = (validityPeriod / 5) - 1;
            return relValidityPeriod;
        }
        if (validityPeriod <= 1440) {
            int relValidityPeriod2 = ((validityPeriod - 720) / 30) + 143;
            return relValidityPeriod2;
        }
        if (validityPeriod <= 43200) {
            int relValidityPeriod3 = (validityPeriod / 1440) + 166;
            return relValidityPeriod3;
        }
        if (validityPeriod > VALIDITY_PERIOD_MAX) {
            return -1;
        }
        int relValidityPeriod4 = (validityPeriod / 10080) + 192;
        return relValidityPeriod4;
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header, 0, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int encoding, int languageTable, int languageShiftTable) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header, encoding, languageTable, languageShiftTable, -1, 0);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int encoding, int languageTable, int languageShiftTable, int validityPeriod) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header, encoding, languageTable, languageShiftTable, validityPeriod, 0);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int encoding, int languageTable, int languageShiftTable, int validityPeriod, int messageRef) {
        byte[] header2;
        int encoding2;
        int languageTable2;
        int languageShiftTable2;
        byte[] header3;
        byte mtiByte;
        byte[] userData;
        if (message == null || destinationAddress == null) {
            return null;
        }
        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SEGMENTED_SMS)) {
            header2 = header;
        } else {
            header2 = null;
        }
        if (encoding != 0) {
            encoding2 = encoding;
            languageTable2 = languageTable;
            languageShiftTable2 = languageShiftTable;
            header3 = header2;
        } else {
            GsmAlphabet.TextEncodingDetails ted = calculateLength(message, false);
            int encoding3 = ted.codeUnitSize;
            int languageTable3 = ted.languageTable;
            int languageShiftTable3 = ted.languageShiftTable;
            if (encoding3 == 1 && (languageTable3 != 0 || languageShiftTable3 != 0)) {
                if (header2 != null) {
                    SmsHeader smsHeader = SmsHeader.fromByteArray(header2);
                    if (smsHeader.languageTable != languageTable3 || smsHeader.languageShiftTable != languageShiftTable3) {
                        Rlog.w(LOG_TAG, "Updating language table in SMS header: " + smsHeader.languageTable + " -> " + languageTable3 + ", " + smsHeader.languageShiftTable + " -> " + languageShiftTable3);
                        smsHeader.languageTable = languageTable3;
                        smsHeader.languageShiftTable = languageShiftTable3;
                        header2 = SmsHeader.toByteArray(smsHeader);
                    }
                    header3 = header2;
                    encoding2 = encoding3;
                    languageTable2 = languageTable3;
                    languageShiftTable2 = languageShiftTable3;
                } else {
                    SmsHeader smsHeader2 = new SmsHeader();
                    smsHeader2.languageTable = languageTable3;
                    smsHeader2.languageShiftTable = languageShiftTable3;
                    byte[] header4 = SmsHeader.toByteArray(smsHeader2);
                    header3 = header4;
                    encoding2 = encoding3;
                    languageTable2 = languageTable3;
                    languageShiftTable2 = languageShiftTable3;
                }
            } else {
                header3 = header2;
                encoding2 = encoding3;
                languageTable2 = languageTable3;
                languageShiftTable2 = languageShiftTable3;
            }
        }
        SubmitPdu ret = new SubmitPdu();
        int relativeValidityPeriod = getRelativeValidityPeriod(validityPeriod);
        byte mtiByte2 = 1;
        if (header3 != null) {
            mtiByte2 = (byte) (1 | 64);
        }
        if (relativeValidityPeriod == -1) {
            mtiByte = mtiByte2;
        } else {
            mtiByte = (byte) (mtiByte2 | 16);
        }
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, mtiByte, statusReportRequested, ret, messageRef);
        if (bo == null) {
            return ret;
        }
        try {
            if (encoding2 == 1) {
                userData = GsmAlphabet.stringToGsm7BitPackedWithHeader(message, header3, languageTable2, languageShiftTable2);
            } else {
                try {
                    userData = encodeUCS2(message, header3);
                } catch (UnsupportedEncodingException uex) {
                    Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex);
                    return null;
                }
            }
        } catch (EncodeException e) {
            if (e.getError() == 1) {
                Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e);
                return null;
            }
            try {
                userData = encodeUCS2(message, header3);
                encoding2 = 3;
            } catch (EncodeException ex1) {
                Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", ex1);
                return null;
            } catch (UnsupportedEncodingException uex2) {
                Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex2);
                return null;
            }
        }
        if (encoding2 == 1) {
            if ((userData[0] & 255) > 160) {
                Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " septets)");
                return null;
            }
            bo.write(0);
        } else {
            if ((userData[0] & 255) > 140) {
                Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
                return null;
            }
            bo.write(8);
        }
        if (relativeValidityPeriod != -1) {
            bo.write(relativeValidityPeriod);
        }
        bo.write(userData, 0, userData.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    private static byte[] encodeUCS2(String message, byte[] header) throws UnsupportedEncodingException, EncodeException {
        byte[] userData;
        byte[] textPart = message.getBytes("utf-16be");
        if (header != null) {
            userData = new byte[header.length + textPart.length + 1];
            userData[0] = (byte) header.length;
            System.arraycopy(header, 0, userData, 1, header.length);
            System.arraycopy(textPart, 0, userData, header.length + 1, textPart.length);
        } else {
            userData = textPart;
        }
        if (userData.length > 255) {
            throw new EncodeException("Payload cannot exceed 255 bytes", 1);
        }
        byte[] ret = new byte[userData.length + 1];
        ret[0] = (byte) (255 & userData.length);
        System.arraycopy(userData, 0, ret, 1, userData.length);
        return ret;
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, (byte[]) null);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, int validityPeriod) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, (byte[]) null, 0, 0, 0, validityPeriod, 0);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, int destinationPort, byte[] data, boolean statusReportRequested, int messageRef) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = destinationPort;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] smsHeaderData = SmsHeader.toByteArray(smsHeader);
        if (data.length + smsHeaderData.length + 1 > 140) {
            Rlog.e(LOG_TAG, "SMS data message may only contain " + ((140 - smsHeaderData.length) - 1) + " bytes");
            return null;
        }
        SubmitPdu ret = new SubmitPdu();
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, (byte) 65, statusReportRequested, ret, messageRef);
        if (bo == null) {
            return ret;
        }
        bo.write(4);
        bo.write(data.length + smsHeaderData.length + 1);
        bo.write(smsHeaderData.length);
        bo.write(smsHeaderData, 0, smsHeaderData.length);
        bo.write(data, 0, data.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, int destinationPort, byte[] data, boolean statusReportRequested) {
        return getSubmitPdu(scAddress, destinationAddress, destinationPort, data, statusReportRequested, 0);
    }

    private static ByteArrayOutputStream getSubmitPduHead(String scAddress, String destinationAddress, byte mtiByte, boolean statusReportRequested, SubmitPdu ret) {
        return getSubmitPduHead(scAddress, destinationAddress, mtiByte, statusReportRequested, ret, 0);
    }

    private static ByteArrayOutputStream getSubmitPduHead(String scAddress, String destinationAddress, byte mtiByte, boolean statusReportRequested, SubmitPdu ret, int messageRef) {
        byte[] daBytes;
        ByteArrayOutputStream bo = new ByteArrayOutputStream(180);
        if (scAddress == null) {
            ret.encodedScAddress = null;
        } else {
            ret.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(scAddress);
        }
        if (statusReportRequested) {
            mtiByte = (byte) (mtiByte | 32);
        }
        bo.write(mtiByte);
        bo.write(messageRef);
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("DOCOMO")) {
            daBytes = PhoneNumberUtils.docomoNetworkPortionToCalledPartyBCD(destinationAddress);
        } else {
            daBytes = PhoneNumberUtils.networkPortionToCalledPartyBCD(destinationAddress);
        }
        if (daBytes == null) {
            Rlog.e(LOG_TAG, "daBytes is null");
            return null;
        }
        bo.write(((daBytes.length - 1) * 2) - ((daBytes[daBytes.length - 1] & 240) != 240 ? 0 : 1));
        bo.write(daBytes, 0, daBytes.length);
        bo.write(0);
        return bo;
    }

    /* JADX WARN: Type inference failed for: r13v6, types: [java.time.LocalDateTime] */
    public static SubmitPdu getDeliverPdu(String scAddress, String originatingAddress, String message, long date) {
        byte[] header;
        boolean isNumeric;
        int i;
        byte[] oaBytes;
        byte[] userData;
        if (originatingAddress != null && message != null) {
            GsmAlphabet.TextEncodingDetails ted = calculateLength(message, false);
            int encoding = ted.codeUnitSize;
            int languageTable = ted.languageTable;
            int languageShiftTable = ted.languageShiftTable;
            if (encoding == 1 && (languageTable != 0 || languageShiftTable != 0)) {
                SmsHeader smsHeader = new SmsHeader();
                smsHeader.languageTable = languageTable;
                smsHeader.languageShiftTable = languageShiftTable;
                byte[] header2 = SmsHeader.toByteArray(smsHeader);
                header = header2;
            } else {
                header = null;
            }
            SubmitPdu ret = new SubmitPdu();
            ByteArrayOutputStream bo = new ByteArrayOutputStream(180);
            if (scAddress == null) {
                ret.encodedScAddress = null;
            } else {
                ret.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(scAddress);
            }
            bo.write(0);
            int i2 = 0;
            while (true) {
                if (i2 < originatingAddress.length()) {
                    Rlog.d(LOG_TAG, "Address is " + originatingAddress.charAt(i2));
                    if (PhoneNumberUtils.isDialable(originatingAddress.charAt(i2))) {
                        i2++;
                    } else {
                        isNumeric = false;
                        break;
                    }
                } else {
                    isNumeric = true;
                    break;
                }
            }
            if (isNumeric) {
                Rlog.i(LOG_TAG, "Address is Numeric.");
                byte[] oaBytes2 = PhoneNumberUtils.networkPortionToCalledPartyBCD(originatingAddress);
                if (oaBytes2 == null) {
                    return null;
                }
                bo.write(((oaBytes2.length - 1) * 2) - ((oaBytes2[oaBytes2.length - 1] & 240) == 240 ? 1 : 0));
                bo.write(oaBytes2, 0, oaBytes2.length);
                oaBytes = oaBytes2;
                i = 1;
            } else {
                Rlog.i(LOG_TAG, "Address is Alphabetic.");
                try {
                    byte[] oaBytes3 = GsmAlphabet.stringToGsm7BitPacked(originatingAddress);
                    i = 1;
                    bo.write((oaBytes3.length - 1) * 2);
                    bo.write(208);
                    bo.write(oaBytes3, 1, oaBytes3.length - 1);
                    oaBytes = oaBytes3;
                } catch (EncodeException ex) {
                    Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", ex);
                    return null;
                }
            }
            bo.write(0);
            try {
                if (encoding == i) {
                    userData = GsmAlphabet.stringToGsm7BitPackedWithHeader(message, header, languageTable, languageShiftTable);
                } else {
                    try {
                        userData = encodeUCS2(message, header);
                    } catch (UnsupportedEncodingException uex) {
                        Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex);
                        return null;
                    }
                }
            } catch (EncodeException e) {
                if (e.getError() == 1) {
                    Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e);
                    return null;
                }
                try {
                    userData = encodeUCS2(message, header);
                    encoding = 3;
                } catch (EncodeException ex1) {
                    Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", ex1);
                    return null;
                } catch (UnsupportedEncodingException uex2) {
                    Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex2);
                    return null;
                }
            }
            if (encoding == 1) {
                if ((userData[0] & 255) > 160) {
                    Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " septets)");
                    return null;
                }
                bo.write(0);
            } else {
                if ((userData[0] & 255) > 140) {
                    Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
                    return null;
                }
                bo.write(8);
            }
            byte[] scts = new byte[7];
            ZonedDateTime zoneDateTime = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault());
            ?? localDateTime = zoneDateTime.toLocalDateTime();
            int timezoneOffset = (zoneDateTime.getOffset().getTotalSeconds() / 60) / 15;
            boolean negativeOffset = timezoneOffset < 0;
            if (negativeOffset) {
                timezoneOffset = -timezoneOffset;
            }
            int year = localDateTime.getYear();
            int month = localDateTime.getMonthValue();
            int day = localDateTime.getDayOfMonth();
            int hour = localDateTime.getHour();
            int minute = localDateTime.getMinute();
            int second = localDateTime.getSecond();
            int year2 = year > 2000 ? year + DrmManagerClient.ERROR_UNKNOWN : year - 1900;
            scts[0] = (byte) ((((year2 % 10) & 15) << 4) | ((year2 / 10) & 15));
            scts[1] = (byte) ((((month % 10) & 15) << 4) | ((month / 10) & 15));
            scts[2] = (byte) ((((day % 10) & 15) << 4) | ((day / 10) & 15));
            scts[3] = (byte) ((((hour % 10) & 15) << 4) | ((hour / 10) & 15));
            scts[4] = (byte) ((((minute % 10) & 15) << 4) | ((minute / 10) & 15));
            scts[5] = (byte) ((((second % 10) & 15) << 4) | ((second / 10) & 15));
            scts[6] = (byte) ((((timezoneOffset % 10) & 15) << 4) | ((timezoneOffset / 10) & 15));
            if (negativeOffset) {
                scts[6] = (byte) (scts[6] | 8);
            }
            bo.write(scts, 0, scts.length);
            bo.write(userData, 0, userData.length);
            ret.encodedMessage = bo.toByteArray();
            return ret;
        }
        return null;
    }

    private static class PduParser {
        byte[] mPdu;
        byte[] mUserData;
        SmsHeader mUserDataHeader;
        int mValidityPeriodFormat = 0;
        int mSubId = SubscriptionManager.getDefaultSmsSubscriptionId();
        int mCur = 0;
        int mUserDataSeptetPadding = 0;

        PduParser(byte[] pdu) {
            this.mPdu = pdu;
        }

        void setSubIdforParser(int subId) {
            this.mSubId = subId;
        }

        String getSCAddress() {
            String ret;
            int len = getByte();
            if (len == 0) {
                ret = null;
            } else {
                try {
                    ret = PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, len, 2);
                } catch (RuntimeException tr) {
                    Rlog.d(SmsMessage.LOG_TAG, "invalid SC address: ", tr);
                    ret = null;
                }
            }
            this.mCur += len;
            return ret;
        }

        int getByte() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            return bArr[i] & 255;
        }

        GsmSmsAddress getAddress() {
            int addressLength = this.mPdu[this.mCur] & 255;
            int lengthBytes = ((addressLength + 1) / 2) + 2;
            try {
                Rlog.d(SmsMessage.LOG_TAG, "getAddress : Mno = " + SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getMnoName());
                GsmSmsAddress ret = new GsmSmsAddress(this.mSubId, this.mPdu, this.mCur, lengthBytes);
                this.mCur += lengthBytes;
                return ret;
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        long getSCTimestampMillis() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            int year = IccUtils.gsmBcdByteToInt(bArr[i]);
            byte[] bArr2 = this.mPdu;
            int i2 = this.mCur;
            this.mCur = i2 + 1;
            int month = IccUtils.gsmBcdByteToInt(bArr2[i2]);
            byte[] bArr3 = this.mPdu;
            int i3 = this.mCur;
            this.mCur = i3 + 1;
            int day = IccUtils.gsmBcdByteToInt(bArr3[i3]);
            byte[] bArr4 = this.mPdu;
            int i4 = this.mCur;
            this.mCur = i4 + 1;
            int hour = IccUtils.gsmBcdByteToInt(bArr4[i4]);
            byte[] bArr5 = this.mPdu;
            int i5 = this.mCur;
            this.mCur = i5 + 1;
            int minute = IccUtils.gsmBcdByteToInt(bArr5[i5]);
            byte[] bArr6 = this.mPdu;
            int i6 = this.mCur;
            this.mCur = i6 + 1;
            int second = IccUtils.gsmBcdByteToInt(bArr6[i6]);
            byte[] bArr7 = this.mPdu;
            int i7 = this.mCur;
            this.mCur = i7 + 1;
            byte tzByte = bArr7[i7];
            int timezoneOffset = IccUtils.gsmBcdByteToInt((byte) (tzByte & (-9)));
            int timezoneOffset2 = (tzByte & 8) == 0 ? timezoneOffset : -timezoneOffset;
            Time time = new Time(Time.TIMEZONE_UTC);
            time.year = year >= 90 ? year + 1900 : year + 2000;
            time.month = month - 1;
            time.monthDay = day;
            time.hour = hour;
            time.minute = minute;
            time.second = second;
            return time.toMillis(true) - (((timezoneOffset2 * 15) * 60) * 1000);
        }

        int constructUserData(boolean hasUserDataHeader, boolean dataInSeptets) {
            int bufferLen;
            int offset = this.mCur;
            switch (this.mValidityPeriodFormat) {
                case 1:
                case 3:
                    offset = 7;
                    break;
                case 2:
                    offset++;
                    break;
            }
            int offset2 = offset + 1;
            int userDataLength = this.mPdu[offset] & 255;
            int headerSeptets = 0;
            int userDataHeaderLength = 0;
            if (hasUserDataHeader) {
                int offset3 = offset2 + 1;
                userDataHeaderLength = this.mPdu[offset2] & 255;
                byte[] udh = new byte[userDataHeaderLength];
                System.arraycopy(this.mPdu, offset3, udh, 0, userDataHeaderLength);
                this.mUserDataHeader = SmsHeader.semFromByteArray(this.mSubId, udh);
                int offset4 = offset3 + userDataHeaderLength;
                int headerBits = (userDataHeaderLength + 1) * 8;
                int headerSeptets2 = headerBits / 7;
                headerSeptets = headerSeptets2 + (headerBits % 7 > 0 ? 1 : 0);
                this.mUserDataSeptetPadding = (headerSeptets * 7) - headerBits;
                offset2 = offset4;
            }
            if (dataInSeptets) {
                bufferLen = this.mPdu.length - offset2;
            } else {
                bufferLen = userDataLength - (hasUserDataHeader ? userDataHeaderLength + 1 : 0);
                if (bufferLen < 0) {
                    bufferLen = 0;
                }
            }
            this.mUserData = new byte[bufferLen];
            if (!SmsMessage.mUnsupportedDatacodingScheme || hasUserDataHeader) {
                System.arraycopy(this.mPdu, offset2, this.mUserData, 0, this.mUserData.length);
            } else {
                Rlog.e(SmsMessage.LOG_TAG, "array copy skip! if dataCodingScheme is unsupporting,\n encodingType is Unknown and messageBody is null");
            }
            this.mCur = offset2;
            if (dataInSeptets) {
                int count = userDataLength - headerSeptets;
                if (count < 0) {
                    return 0;
                }
                return count;
            }
            return this.mUserData.length;
        }

        byte[] getUserData() {
            return this.mUserData;
        }

        SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        String getUserDataGSM7Bit(int septetCount, int languageTable, int languageShiftTable) {
            String ret = GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, septetCount, this.mUserDataSeptetPadding, languageTable, languageShiftTable);
            this.mCur += (septetCount * 7) / 8;
            return ret;
        }

        String getUserDataGSM8bit(int byteCount) {
            String ret = GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, byteCount);
            this.mCur += byteCount;
            return ret;
        }

        String getUserDataUCS2(int byteCount) {
            String ret;
            try {
                byte[] nsriUserdata = getUserData();
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && nsriUserdata.length > 0) {
                    ret = getUseDataNSRISms(byteCount);
                } else {
                    ret = new String(this.mPdu, this.mCur, byteCount, CharacterSets.MIMENAME_UTF_16);
                }
            } catch (UnsupportedEncodingException ex) {
                ret = "";
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
            }
            this.mCur += byteCount;
            return ret;
        }

        String getUserDataKSC5601(int byteCount) {
            String ret = SmsMessage.LOG_TAG;
            try {
                byte[] nsriUserdata = getUserData();
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && nsriUserdata.length > 0) {
                    if (Integer.toHexString(nsriUserdata[0] & 255).equals("f1") && Integer.toHexString(nsriUserdata[1] & 255).equals("a0")) {
                        Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUserDataKSC5601 KSC5601");
                        ret = new String(this.mPdu, this.mCur, byteCount, "ISO8859_1");
                    } else {
                        ret = new String(this.mPdu, this.mCur, byteCount, "KSC5601");
                    }
                } else {
                    ret = new String(this.mPdu, this.mCur, byteCount, "KSC5601");
                }
            } catch (UnsupportedEncodingException ex) {
                Rlog.e(ret, "implausible UnsupportedEncodingException", ex);
                ret = "";
            }
            this.mCur += byteCount;
            return ret;
        }

        boolean moreDataPresent() {
            return this.mPdu.length > this.mCur;
        }

        String getUseDataNSRISms(int byteCount) {
            String ret;
            byte[] nsriUserdata = getUserData();
            Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUseDataNSRISms");
            try {
                if (Integer.toHexString(nsriUserdata[0] & 255).equals("f1") && Integer.toHexString(nsriUserdata[1] & 255).equals("a0")) {
                    ret = new String(this.mPdu, this.mCur, byteCount, "ISO8859_1");
                    Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] : getUserDataUCS2    ISO8859_1");
                } else {
                    ret = new String(this.mPdu, this.mCur, byteCount, CharacterSets.MIMENAME_UTF_16);
                }
                return ret;
            } catch (UnsupportedEncodingException ex) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
                return "";
            }
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence msgBody, boolean use7bitOnly) {
        CharSequence newMsgBody = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(R.bool.config_sms_force_7bit_encoding)) {
            newMsgBody = Sms7BitEncodingTranslator.translate(msgBody, false);
        }
        if (TextUtils.isEmpty(newMsgBody)) {
            newMsgBody = msgBody;
        }
        GsmAlphabet.TextEncodingDetails ted = null;
        if (newMsgBody != null) {
            ted = GsmAlphabet.countGsmSeptets(newMsgBody, use7bitOnly, mIgnoreSpecialChar);
            mIgnoreSpecialChar = false;
            if (ted == null) {
                return SmsMessageBase.calcUnicodeEncodingDetails(newMsgBody);
            }
        }
        return ted;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getProtocolIdentifier() {
        return this.mProtocolIdentifier;
    }

    int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplace() {
        return (this.mProtocolIdentifier & 192) == 64 && (this.mProtocolIdentifier & 63) > 0 && (this.mProtocolIdentifier & 63) < 8;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isCphsMwiMessage() {
        return ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear() || ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet();
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWIClearMessage() {
        if (!this.mIsMwi || this.mMwiSense) {
            return this.mOriginatingAddress != null && ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear();
        }
        return true;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWISetMessage() {
        if (this.mIsMwi && this.mMwiSense) {
            return true;
        }
        return this.mOriginatingAddress != null && ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet();
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMwiDontStore() {
        if (this.mIsMwi && this.mMwiDontStore) {
            return true;
        }
        if (isCphsMwiMessage()) {
            if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_NOT_COUNT_VOICEMAIL)) {
                if (" ".equals(getMessageBody())) {
                    return true;
                }
                if ("RWC".equals(SALES_CODE) || SSLSocketFactory.TLS.equals(SALES_CODE) || "MTA".equals(SALES_CODE)) {
                    Rlog.d(LOG_TAG, "CPHS MWI messages in Canada " + SALES_CODE + " don't store");
                    return true;
                }
            } else {
                " ".equals(getMessageBody());
                return true;
            }
        }
        if (getMessageBody() != null) {
            getMessageBody().length();
            return false;
        }
        return false;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getStatus() {
        return this.mStatus;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isStatusReportMessage() {
        return this.mIsStatusReportMessage;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplyPathPresent() {
        return this.mReplyPathPresent;
    }

    private void parsePdu(byte[] pdu) {
        this.mPdu = pdu;
        PduParser p = new PduParser(pdu);
        p.setSubIdforParser(getSubId());
        this.mScAddress = p.getSCAddress();
        String str = this.mScAddress;
        int firstByte = p.getByte();
        this.mMti = firstByte & 3;
        switch (this.mMti) {
            case 0:
            case 3:
                parseSmsDeliver(p, firstByte);
                return;
            case 1:
                parseSmsSubmit(p, firstByte);
                return;
            case 2:
                parseSmsStatusReport(p, firstByte);
                return;
            default:
                throw new RuntimeException("Unsupported message type");
        }
    }

    private void parseSmsStatusReport(PduParser p, int firstByte) {
        this.mIsStatusReportMessage = true;
        this.mMessageRef = p.getByte();
        this.mRecipientAddress = p.getAddress();
        this.mScTimeMillis = p.getSCTimestampMillis();
        p.getSCTimestampMillis();
        this.mStatus = p.getByte();
        if (p.moreDataPresent()) {
            int extraParams = p.getByte();
            int moreExtraParams = extraParams;
            while ((moreExtraParams & 128) != 0) {
                moreExtraParams = p.getByte();
            }
            if ((extraParams & 120) == 0) {
                if ((extraParams & 1) != 0) {
                    this.mProtocolIdentifier = p.getByte();
                }
                if ((extraParams & 2) != 0) {
                    this.mDataCodingScheme = p.getByte();
                }
                if ((extraParams & 4) != 0) {
                    boolean hasUserDataHeader = (firstByte & 64) == 64;
                    parseUserData(p, hasUserDataHeader);
                }
            }
        }
    }

    private void parseSmsDeliver(PduParser p, int firstByte) {
        this.mReplyPathPresent = (firstByte & 128) == 128;
        this.mOriginatingAddress = p.getAddress();
        if (this.mOriginatingAddress != null && SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SPECIAL_ADDRESS_HANDLING_FOR) && this.mOriginatingAddress.address.startsWith("+00852")) {
            String origAddress = this.mOriginatingAddress.address;
            String origAddress2 = origAddress.substring(3);
            this.mOriginatingAddress.address = "+";
            StringBuilder sb = new StringBuilder();
            SmsAddress smsAddress = this.mOriginatingAddress;
            smsAddress.address = sb.append(smsAddress.address).append(origAddress2).toString();
        }
        SmsAddress smsAddress2 = this.mOriginatingAddress;
        this.mProtocolIdentifier = p.getByte();
        this.mDataCodingScheme = p.getByte();
        this.mScTimeMillis = p.getSCTimestampMillis();
        boolean hasUserDataHeader = (firstByte & 64) == 64;
        parseUserData(p, hasUserDataHeader);
    }

    private void parseSmsSubmit(PduParser p, int firstByte) {
        int validityPeriodLength;
        this.mReplyPathPresent = (firstByte & 128) == 128;
        this.mMessageRef = p.getByte();
        this.mRecipientAddress = p.getAddress();
        SmsAddress smsAddress = this.mRecipientAddress;
        this.mProtocolIdentifier = p.getByte();
        this.mDataCodingScheme = p.getByte();
        int validityPeriodFormat = (firstByte >> 3) & 3;
        if (validityPeriodFormat == 0) {
            validityPeriodLength = 0;
        } else if (validityPeriodFormat == 2) {
            validityPeriodLength = 1;
        } else {
            validityPeriodLength = 7;
        }
        while (true) {
            int validityPeriodLength2 = validityPeriodLength - 1;
            if (validityPeriodLength <= 0) {
                break;
            }
            p.getByte();
            validityPeriodLength = validityPeriodLength2;
        }
        boolean hasUserDataHeader = (firstByte & 64) == 64;
        parseUserData(p, hasUserDataHeader);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0269, code lost:
    
        if ((r18.mDataCodingScheme & 240) != 224) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0276, code lost:
    
        r18.mMwiDontStore = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0274, code lost:
    
        if ((r18.mDataCodingScheme & 3) != 0) goto L107;
     */
    /* JADX WARN: Removed duplicated region for block: B:185:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseUserData(com.android.internal.telephony.gsm.SmsMessage.PduParser r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 1334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.parseUserData(com.android.internal.telephony.gsm.SmsMessage$PduParser, boolean):void");
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    boolean isUsimDataDownload() {
        return this.messageClass == SmsConstants.MessageClass.CLASS_2 && (this.mProtocolIdentifier == 127 || this.mProtocolIdentifier == 124);
    }

    public int getNumOfVoicemails() {
        if (!this.mIsMwi && isCphsMwiMessage()) {
            if (this.mOriginatingAddress != null && ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet()) {
                this.mVoiceMailCount = 255;
            } else {
                this.mVoiceMailCount = 0;
            }
            Rlog.v(LOG_TAG, "CPHS voice mail message");
        }
        return this.mVoiceMailCount;
    }

    public static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.setSubId(getSubId(phoneId));
            msg.parsePdu(pdu);
            return msg;
        } catch (OutOfMemoryError e) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthForCdma(CharSequence msgBody, boolean use7bitOnly) {
        mIgnoreSpecialChar = true;
        return calculateLength(msgBody, use7bitOnly);
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageIdentifier() {
        return this.mMessageRef;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessagePriority() {
        return 0;
    }

    private static int decToBcd(int digit) {
        int left = digit / 10;
        int right = digit % 10;
        int bcd_value = (right * 10) + left;
        return bcd_value;
    }

    public static SubmitPdu getSubmitPdu(int subId, String scAddress, String destinationAddress, int destinationPort, int originationPort, byte[] data, boolean statusReportRequested) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = destinationPort;
        portAddrs.origPort = originationPort;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] smsHeaderData = SmsHeader.toByteArray(smsHeader);
        if (data.length + smsHeaderData.length + 1 > 140) {
            Rlog.e(LOG_TAG, "SMS data message may only contain " + ((140 - smsHeaderData.length) - 1) + " bytes");
            return null;
        }
        SubmitPdu ret = new SubmitPdu();
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, (byte) 65, statusReportRequested, ret);
        if (bo == null) {
            return ret;
        }
        bo.write(4);
        bo.write(data.length + smsHeaderData.length + 1);
        bo.write(smsHeaderData.length);
        bo.write(smsHeaderData, 0, smsHeaderData.length);
        bo.write(data, 0, data.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    public static SubmitPdu getSubmitPduForAutoLogin(String scAddress, String destinationAddress, String message, boolean statusReportRequested, int validityPeriod) {
        return getSubmitPduForAutoLogin(scAddress, destinationAddress, message, statusReportRequested, null, validityPeriod);
    }

    public static SubmitPdu getSubmitPduForAutoLogin(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int validityPeriod) {
        if (message == null || destinationAddress == null) {
            return null;
        }
        SubmitPdu ret = new SubmitPdu();
        int validityPeriodFormat = 0;
        int relativeValidityPeriod = getRelativeValidityPeriod(validityPeriod);
        if (relativeValidityPeriod >= 0) {
            validityPeriodFormat = 2;
        }
        byte mtiByte = (byte) ((validityPeriodFormat << 3) | 1 | (header != null ? 64 : 0));
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, mtiByte, statusReportRequested, ret);
        if (bo == null) {
            return ret;
        }
        byte[] userData = GsmAlphabet.stringToGsm8BitPackedForAutoLogin(message);
        if (userData == null) {
            return null;
        }
        if ((userData[0] & 255) > 140) {
            Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
            return null;
        }
        bo.write(4);
        if (validityPeriodFormat == 2) {
            bo.write(relativeValidityPeriod);
        }
        bo.write(userData, 0, userData.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    public static SubmitPdu getSubmitPdu(int subId, String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, boolean replyPath, int expiry, int serviceType, int encodingType) {
        return getSubmitPdu(subId, scAddress, destinationAddress, message, statusReportRequested, header, replyPath, expiry, serviceType, encodingType, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(int subId, String scAddress, String destinationAddress, String message, boolean statusReportRequested, boolean replyPath, int expiry, int serviceType, int encodingType, int a, int b) {
        if (a > 0 || b > 0) {
            SmsHeader Header = new SmsHeader();
            Header.languageTable = a;
            Header.languageShiftTable = b;
            return getSubmitPdu(subId, scAddress, destinationAddress, message, statusReportRequested, SmsHeader.toByteArray(Header), replyPath, expiry, serviceType, encodingType, a, b);
        }
        return getSubmitPdu(subId, scAddress, destinationAddress, message, statusReportRequested, null, replyPath, expiry, serviceType, encodingType, a, b);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01cb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(int r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, boolean r21, byte[] r22, boolean r23, int r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 545
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(int, java.lang.String, java.lang.String, java.lang.String, boolean, byte[], boolean, int, int, int, int, int):com.android.internal.telephony.gsm.SmsMessage$SubmitPdu");
    }

    public static SubmitPdu getSubmitPduForKTOTA(String scAddress, String destinationAddress, String message) {
        SubmitPdu ret = new SubmitPdu();
        ByteArrayOutputStream bo = new ByteArrayOutputStream(180);
        if (scAddress == null) {
            ret.encodedScAddress = null;
        } else {
            ret.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(scAddress);
        }
        bo.write(1);
        bo.write(0);
        byte[] daBytes = PhoneNumberUtils.networkPortionToCalledPartyBCD(destinationAddress);
        if (daBytes == null) {
            bo.write(0);
        } else {
            bo.write(((daBytes.length - 1) * 2) - ((daBytes[daBytes.length - 1] & 240) != 240 ? 0 : 1));
            bo.write(daBytes, 0, daBytes.length);
            bo.write(127);
        }
        try {
            byte[] userData = GsmAlphabet.stringToGsm7BitPacked(message);
            if ((userData[0] & 255) > 160) {
                return null;
            }
            bo.write(0);
            bo.write(userData, 0, userData.length);
            ret.encodedMessage = bo.toByteArray();
            return ret;
        } catch (EncodeException ex) {
            Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", ex);
            return null;
        }
    }

    private static boolean useValidityPeriod(int subId) {
        Context context;
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NOT_USED_VALIDITY_PERIOD_FORMAT)) {
            return false;
        }
        if (TelephonyFeatures.isSupportTiantong() && (context = ActivityThread.currentApplication().getApplicationContext()) != null) {
            try {
                if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.SATELLITE_MODE_ENABLED, 0) != 0) {
                    Rlog.d(LOG_TAG, "Do not use TP-VP for Tiantong");
                    return false;
                }
            } catch (SecurityException e) {
                Rlog.e(LOG_TAG, "SecurityException during get setting DB" + e);
                return true;
            }
        }
        return true;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEncodingType(CharSequence msgBody, boolean use7bitOnly, int encodingType) {
        GsmAlphabet.TextEncodingDetails ted;
        new GsmAlphabet.TextEncodingDetails();
        if (encodingType == 1) {
            ted = null;
        } else if (encodingType == 0) {
            ted = GsmAlphabet.countGsmSeptets(msgBody, true);
        } else {
            ted = GsmAlphabet.countGsmSeptets(msgBody, use7bitOnly);
        }
        if (ted == null) {
            return SmsMessageBase.calcUnicodeEncodingDetails(msgBody);
        }
        return ted;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence msgBody, boolean use7bitOnly, int maxEmailLen) {
        int maxLenPerSMS;
        int maxLenPerSMSWithHeader;
        GsmAlphabet.TextEncodingDetails ted = GsmAlphabet.countGsmSeptetsWithEmail(msgBody, use7bitOnly, maxEmailLen);
        if (ted == null) {
            ted = new GsmAlphabet.TextEncodingDetails();
            int maxEmailLen2 = maxEmailLen * 2;
            if (maxEmailLen2 > 0) {
                maxLenPerSMS = (140 - maxEmailLen2) - 1;
            } else {
                maxLenPerSMS = 140;
            }
            if (maxEmailLen2 > 0) {
                maxLenPerSMSWithHeader = (134 - maxEmailLen2) - 1;
            } else {
                maxLenPerSMSWithHeader = 134;
            }
            int octets = msgBody.length() * 2;
            ted.codeUnitCount = msgBody.length();
            if (octets > maxLenPerSMS) {
                if (maxEmailLen2 > maxLenPerSMS - 2) {
                    ted.msgCount = 1000;
                    ted.codeUnitsRemaining = -1;
                } else if (octets % maxLenPerSMSWithHeader != 0) {
                    ted.msgCount = (octets / maxLenPerSMSWithHeader) + 1;
                    ted.codeUnitsRemaining = (maxLenPerSMSWithHeader - (octets % maxLenPerSMSWithHeader)) / 2;
                } else {
                    ted.msgCount = octets / maxLenPerSMSWithHeader;
                    ted.codeUnitsRemaining = 0;
                }
            } else if (maxEmailLen2 >= maxLenPerSMSWithHeader - 2) {
                ted.msgCount = 1000;
                ted.codeUnitsRemaining = -1;
            } else {
                ted.msgCount = 1;
                ted.codeUnitsRemaining = (maxLenPerSMS - octets) / 2;
            }
            ted.codeUnitSize = 3;
        }
        return ted;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence msgBody, boolean use7bitOnly, int encodingType, int maxEmailLen) {
        GsmAlphabet.TextEncodingDetails ted;
        int maxLenPerSMS;
        int maxLenPerSMSWithHeader;
        new GsmAlphabet.TextEncodingDetails();
        if (encodingType == 1) {
            ted = null;
        } else if (encodingType == 0) {
            ted = GsmAlphabet.countGsmSeptetsWithEmail(msgBody, true, maxEmailLen);
        } else {
            ted = GsmAlphabet.countGsmSeptetsWithEmail(msgBody, use7bitOnly, maxEmailLen);
        }
        if (ted == null) {
            ted = new GsmAlphabet.TextEncodingDetails();
            int maxEmailLen2 = maxEmailLen * 2;
            if (maxEmailLen2 > 0) {
                maxLenPerSMS = (140 - maxEmailLen2) - 1;
            } else {
                maxLenPerSMS = 140;
            }
            if (maxEmailLen2 > 0) {
                maxLenPerSMSWithHeader = (134 - maxEmailLen2) - 1;
            } else {
                maxLenPerSMSWithHeader = 134;
            }
            int octets = msgBody.length() * 2;
            ted.codeUnitCount = msgBody.length();
            if (octets > maxLenPerSMS) {
                if (maxEmailLen2 > maxLenPerSMS - 2) {
                    ted.msgCount = 1000;
                    ted.codeUnitsRemaining = -1;
                } else if (octets % maxLenPerSMSWithHeader != 0) {
                    ted.msgCount = (octets / maxLenPerSMSWithHeader) + 1;
                    ted.codeUnitsRemaining = (maxLenPerSMSWithHeader - (octets % maxLenPerSMSWithHeader)) / 2;
                } else {
                    ted.msgCount = octets / maxLenPerSMSWithHeader;
                    ted.codeUnitsRemaining = 0;
                }
            } else if (maxEmailLen2 >= maxLenPerSMSWithHeader - 2) {
                ted.msgCount = 1000;
                ted.codeUnitsRemaining = -1;
            } else {
                ted.msgCount = 1;
                ted.codeUnitsRemaining = (maxLenPerSMS - octets) / 2;
            }
            ted.codeUnitSize = 3;
        }
        return ted;
    }

    protected void extractPaginationForGsm() {
        int segNum = 0;
        int totNum = 0;
        String payloadStr = getDisplayMessageBody();
        boolean paginationSuccess = false;
        String payload = payloadStr;
        if (payloadStr == null) {
            Rlog.d(LOG_TAG, "there is no message body");
            return;
        }
        String pagination = null;
        try {
            if (payloadStr.startsWith(NavigationBarInflaterView.KEY_CODE_START) && payloadStr.contains(NavigationBarInflaterView.KEY_CODE_END)) {
                pagination = payloadStr.substring(payloadStr.indexOf(40) + 1, payloadStr.indexOf(41));
                payloadStr = payloadStr.substring(payloadStr.indexOf(41) + 2);
            } else if (payloadStr.startsWith(NavigationBarInflaterView.SIZE_MOD_START) && payloadStr.contains(NavigationBarInflaterView.SIZE_MOD_END)) {
                pagination = payloadStr.substring(payloadStr.indexOf(91) + 1, payloadStr.indexOf(93));
                payloadStr = payloadStr.substring(payloadStr.indexOf(93) + 2);
            } else if (!payloadStr.startsWith("{") || !payloadStr.contains("}")) {
                Rlog.d(LOG_TAG, "there is no pagination pattern maybe / or of ");
            } else {
                pagination = payloadStr.substring(payloadStr.indexOf(123) + 1, payloadStr.indexOf(125));
                payloadStr = payloadStr.substring(payloadStr.indexOf(125) + 2);
            }
            if (pagination != null) {
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
                if (payloadStr.split(" of ").length >= 2) {
                    String[] pageCount = payloadStr.split(" ");
                    if (pageCount.length >= 3) {
                        try {
                            segNum = Integer.parseInt(pageCount[0].trim());
                            totNum = Integer.parseInt(pageCount[2].trim());
                            int offset = pageCount[0].length() + pageCount[2].length() + 4;
                            payload = payloadStr.substring(offset + 1);
                            paginationSuccess = true;
                        } catch (NumberFormatException e2) {
                            Rlog.d(LOG_TAG, "there is no pagination yet");
                        } catch (StringIndexOutOfBoundsException ex) {
                            Rlog.e(LOG_TAG, "extractPagination : " + ex);
                        }
                    }
                }
                if (!paginationSuccess) {
                    String[] tempPage = payloadStr.split("/");
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
                            payloadStr.substring(tempPage[0].length() + i + 1);
                            paginationSuccess = true;
                        } catch (ArrayIndexOutOfBoundsException ex2) {
                            Rlog.e(LOG_TAG, "extractPagination : " + ex2);
                            return;
                        } catch (NumberFormatException e3) {
                            Rlog.d(LOG_TAG, "there is no pagination");
                        }
                    }
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
            this.mUserDataHeader = new SmsHeader();
            this.mUserDataHeader.concatRef = concatRef;
        } catch (StringIndexOutOfBoundsException ex3) {
            Rlog.e(LOG_TAG, "extractPagination : " + ex3);
        }
    }
}
