package com.android.internal.telephony.gsm;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.content.res.Resources;
import android.drm.DrmManagerClient;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.text.format.Time;
import com.android.internal.C4337R;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.telephony.Rlog;
import com.google.android.mms.pdu.CharacterSets;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import org.apache.http.conn.ssl.SSLSocketFactory;

/* loaded from: classes5.dex */
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
                Rlog.m246w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            msg.mStatusOnIcc = data[0] & 7;
            int size = data.length - 1;
            byte[] pdu = new byte[size];
            System.arraycopy(data, 1, pdu, 0, size);
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.m241e(LOG_TAG, "SMS PDU parsing failed: ", ex);
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
                        Rlog.m246w(LOG_TAG, "Updating language table in SMS header: " + smsHeader.languageTable + " -> " + languageTable3 + ", " + smsHeader.languageShiftTable + " -> " + languageShiftTable3);
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
                    Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex);
                    return null;
                }
            }
        } catch (EncodeException e) {
            if (e.getError() == 1) {
                Rlog.m241e(LOG_TAG, "Exceed size limitation EncodeException", e);
                return null;
            }
            try {
                userData = encodeUCS2(message, header3);
                encoding2 = 3;
            } catch (EncodeException ex1) {
                Rlog.m241e(LOG_TAG, "Exceed size limitation EncodeException", ex1);
                return null;
            } catch (UnsupportedEncodingException uex2) {
                Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex2);
                return null;
            }
        }
        if (encoding2 == 1) {
            if ((userData[0] & 255) > 160) {
                Rlog.m240e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " septets)");
                return null;
            }
            bo.write(0);
        } else {
            if ((userData[0] & 255) > 140) {
                Rlog.m240e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
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
            Rlog.m240e(LOG_TAG, "SMS data message may only contain " + ((140 - smsHeaderData.length) - 1) + " bytes");
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
            Rlog.m240e(LOG_TAG, "daBytes is null");
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
                    Rlog.m238d(LOG_TAG, "Address is " + originatingAddress.charAt(i2));
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
                Rlog.m242i(LOG_TAG, "Address is Numeric.");
                byte[] oaBytes2 = PhoneNumberUtils.networkPortionToCalledPartyBCD(originatingAddress);
                if (oaBytes2 == null) {
                    return null;
                }
                bo.write(((oaBytes2.length - 1) * 2) - ((oaBytes2[oaBytes2.length - 1] & 240) == 240 ? 1 : 0));
                bo.write(oaBytes2, 0, oaBytes2.length);
                oaBytes = oaBytes2;
                i = 1;
            } else {
                Rlog.m242i(LOG_TAG, "Address is Alphabetic.");
                try {
                    byte[] oaBytes3 = GsmAlphabet.stringToGsm7BitPacked(originatingAddress);
                    i = 1;
                    bo.write((oaBytes3.length - 1) * 2);
                    bo.write(208);
                    bo.write(oaBytes3, 1, oaBytes3.length - 1);
                    oaBytes = oaBytes3;
                } catch (EncodeException ex) {
                    Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", ex);
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
                        Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex);
                        return null;
                    }
                }
            } catch (EncodeException e) {
                if (e.getError() == 1) {
                    Rlog.m241e(LOG_TAG, "Exceed size limitation EncodeException", e);
                    return null;
                }
                try {
                    userData = encodeUCS2(message, header);
                    encoding = 3;
                } catch (EncodeException ex1) {
                    Rlog.m241e(LOG_TAG, "Exceed size limitation EncodeException", ex1);
                    return null;
                } catch (UnsupportedEncodingException uex2) {
                    Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex2);
                    return null;
                }
            }
            if (encoding == 1) {
                if ((userData[0] & 255) > 160) {
                    Rlog.m240e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " septets)");
                    return null;
                }
                bo.write(0);
            } else {
                if ((userData[0] & 255) > 140) {
                    Rlog.m240e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
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
                    Rlog.m239d(SmsMessage.LOG_TAG, "invalid SC address: ", tr);
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
                Rlog.m238d(SmsMessage.LOG_TAG, "getAddress : Mno = " + SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getMnoName());
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
            time.year = year >= 90 ? year + SettingsEnums.ACCESSIBILITY_MENU : year + 2000;
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
            byte[] bArr = this.mPdu;
            int offset2 = offset + 1;
            int userDataLength = bArr[offset] & 255;
            int headerSeptets = 0;
            int userDataHeaderLength = 0;
            if (hasUserDataHeader) {
                int offset3 = offset2 + 1;
                userDataHeaderLength = bArr[offset2] & 255;
                byte[] udh = new byte[userDataHeaderLength];
                System.arraycopy(bArr, offset3, udh, 0, userDataHeaderLength);
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
                byte[] bArr2 = this.mPdu;
                byte[] bArr3 = this.mUserData;
                System.arraycopy(bArr2, offset2, bArr3, 0, bArr3.length);
            } else {
                Rlog.m240e(SmsMessage.LOG_TAG, "array copy skip! if dataCodingScheme is unsupporting,\n encodingType is Unknown and messageBody is null");
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
                Rlog.m241e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
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
                        Rlog.m238d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUserDataKSC5601 KSC5601");
                        ret = new String(this.mPdu, this.mCur, byteCount, "ISO8859_1");
                    } else {
                        ret = new String(this.mPdu, this.mCur, byteCount, "KSC5601");
                    }
                } else {
                    ret = new String(this.mPdu, this.mCur, byteCount, "KSC5601");
                }
            } catch (UnsupportedEncodingException ex) {
                Rlog.m241e(ret, "implausible UnsupportedEncodingException", ex);
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
            Rlog.m238d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUseDataNSRISms");
            try {
                if (Integer.toHexString(nsriUserdata[0] & 255).equals("f1") && Integer.toHexString(nsriUserdata[1] & 255).equals("a0")) {
                    ret = new String(this.mPdu, this.mCur, byteCount, "ISO8859_1");
                    Rlog.m238d(SmsMessage.LOG_TAG, "[NSRI_SMS] : getUserDataUCS2    ISO8859_1");
                } else {
                    ret = new String(this.mPdu, this.mCur, byteCount, CharacterSets.MIMENAME_UTF_16);
                }
                return ret;
            } catch (UnsupportedEncodingException ex) {
                Rlog.m241e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
                return "";
            }
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence msgBody, boolean use7bitOnly) {
        CharSequence newMsgBody = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(C4337R.bool.config_sms_force_7bit_encoding)) {
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
        int i = this.mProtocolIdentifier;
        return (i & 192) == 64 && (i & 63) > 0 && (i & 63) < 8;
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
                String str = SALES_CODE;
                if ("RWC".equals(str) || SSLSocketFactory.TLS.equals(str) || "MTA".equals(str)) {
                    Rlog.m238d(LOG_TAG, "CPHS MWI messages in Canada " + str + " don't store");
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
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0250, code lost:
    
        if ((r14 & 240) != 224) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0259, code lost:
    
        r19.mMwiDontStore = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0257, code lost:
    
        if ((r14 & 3) != 0) goto L107;
     */
    /* JADX WARN: Removed duplicated region for block: B:185:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseUserData(PduParser p, boolean hasUserDataHeader) {
        int encodingType;
        int encodingType2;
        int encodingType3;
        boolean userDataCompressed;
        Context context;
        int i;
        Iterator<SmsHeader.MiscElt> it;
        Iterator<SmsHeader.SpecialSmsMsg> it2;
        int encodingType4 = 0;
        Resources r = Resources.getSystem();
        int i2 = this.mDataCodingScheme;
        if ((i2 & 255) == 132) {
            encodingType2 = 4;
            encodingType3 = 0;
            userDataCompressed = false;
        } else if ((i2 & 128) == 0) {
            int i3 = (i2 & 32) != 0 ? 1 : 0;
            boolean hasMessageClass = (i2 & 16) != 0;
            if (i3 != 0) {
                Rlog.m246w(LOG_TAG, "4 - Unsupported SMS data coding scheme (compression) " + (this.mDataCodingScheme & 255));
                mUnsupportedDatacodingScheme = true;
                encodingType2 = 0;
                encodingType3 = i3;
                userDataCompressed = hasMessageClass;
            } else {
                switch ((i2 >> 2) & 3) {
                    case 0:
                        encodingType4 = 1;
                        mUnsupportedDatacodingScheme = false;
                        break;
                    case 1:
                        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601) && (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_GSM_8BIT_SMS) || r.getBoolean(C4337R.bool.config_sms_decode_gsm_8bit_data))) {
                            encodingType4 = 2;
                            break;
                        }
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601)) {
                            Rlog.m246w(LOG_TAG, "1 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                            encodingType4 = r.getInteger(C4337R.integer.default_reserved_data_coding_scheme);
                        } else {
                            encodingType4 = 4;
                        }
                        mUnsupportedDatacodingScheme = false;
                        break;
                    case 2:
                        encodingType4 = 3;
                        mUnsupportedDatacodingScheme = false;
                        break;
                    case 3:
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601)) {
                        }
                        mUnsupportedDatacodingScheme = false;
                        break;
                }
                encodingType2 = encodingType4;
                encodingType3 = i3;
                userDataCompressed = hasMessageClass;
            }
        } else if ((i2 & 240) == 240) {
            mUnsupportedDatacodingScheme = false;
            if ((i2 & 4) == 0) {
                encodingType2 = 1;
                encodingType3 = 0;
                userDataCompressed = true;
            } else {
                encodingType2 = 2;
                encodingType3 = 0;
                userDataCompressed = true;
            }
        } else if ((i2 & 240) == 192 || (i2 & 240) == 208 || (i2 & 240) == 224) {
            mUnsupportedDatacodingScheme = false;
            if ((i2 & 240) == 224) {
                encodingType = 3;
            } else {
                encodingType = 1;
            }
            boolean active = (i2 & 8) == 8;
            if ((i2 & 3) == 0) {
                this.mIsMwi = true;
                this.mMwiSense = active;
                this.mMwiDontStore = (this.mDataCodingScheme & 240) == 192;
                if (active) {
                    this.mVoiceMailCount = -1;
                } else {
                    this.mVoiceMailCount = 0;
                }
                Rlog.m246w(LOG_TAG, "MWI in DCS for Vmail. DCS = " + (this.mDataCodingScheme & 255) + " Dont store = " + this.mMwiDontStore + " vmail count = " + this.mVoiceMailCount);
            } else {
                this.mIsMwi = false;
                Rlog.m246w(LOG_TAG, "MWI in DCS for fax/email/other: " + (this.mDataCodingScheme & 255));
            }
            encodingType2 = encodingType;
            encodingType3 = 0;
            userDataCompressed = false;
        } else {
            if ((i2 & 192) != 128) {
                Rlog.m246w(LOG_TAG, "3 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                mUnsupportedDatacodingScheme = true;
            } else if (((i2 >> 2) & 3) == 1) {
                mUnsupportedDatacodingScheme = false;
                encodingType2 = 4;
                encodingType3 = 0;
                userDataCompressed = false;
            } else {
                mUnsupportedDatacodingScheme = true;
                Rlog.m246w(LOG_TAG, "5 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
            }
            encodingType2 = 0;
            encodingType3 = 0;
            userDataCompressed = false;
        }
        int count = p.constructUserData(hasUserDataHeader, encodingType2 == 1);
        this.mUserData = p.getUserData();
        this.mUserDataHeader = p.getUserDataHeader();
        this.mReceivedEncodingType = encodingType2;
        if (hasUserDataHeader && this.mUserDataHeader.specialSmsMsgList.size() != 0) {
            Iterator<SmsHeader.SpecialSmsMsg> it3 = this.mUserDataHeader.specialSmsMsgList.iterator();
            while (it3.hasNext()) {
                SmsHeader.SpecialSmsMsg msg = it3.next();
                int msgInd = msg.msgIndType & 255;
                if (msgInd == 0 || msgInd == 128) {
                    this.mIsMwi = true;
                    if (msgInd == 128) {
                        this.mMwiDontStore = false;
                        it2 = it3;
                    } else if (this.mMwiDontStore) {
                        it2 = it3;
                    } else {
                        int i4 = this.mDataCodingScheme;
                        it2 = it3;
                        if ((i4 & 240) == 208) {
                        }
                    }
                    int i5 = msg.msgCount & 255;
                    this.mVoiceMailCount = i5;
                    if (i5 > 0) {
                        this.mMwiSense = true;
                    } else {
                        this.mMwiSense = false;
                    }
                    Rlog.m246w(LOG_TAG, "MWI in TP-UDH for Vmail. Msg Ind = " + msgInd + " Dont store = " + this.mMwiDontStore + " Vmail count = " + this.mVoiceMailCount);
                } else {
                    Rlog.m246w(LOG_TAG, "TP_UDH fax/email/extended msg/multisubscriber profile. Msg Ind = " + msgInd);
                    it2 = it3;
                }
                it3 = it2;
            }
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            boolean hasReplyAddress = false;
            if (this.mUserDataHeader != null) {
                Iterator<SmsHeader.MiscElt> it4 = this.mUserDataHeader.miscEltList.iterator();
                while (it4.hasNext()) {
                    SmsHeader.MiscElt miscElt = it4.next();
                    if (miscElt.f1008id != 34 || this.mMti != 0) {
                        i = encodingType3;
                        it = it4;
                    } else {
                        if (miscElt.data.length > 2) {
                            try {
                                i = encodingType3;
                                try {
                                    it = it4;
                                } catch (Exception e) {
                                    it = it4;
                                }
                            } catch (Exception e2) {
                                i = encodingType3;
                                it = it4;
                            }
                            try {
                                this.replyAddress = new GsmSmsAddress(miscElt.data, 0, miscElt.data.length);
                            } catch (Exception e3) {
                                Rlog.m246w(LOG_TAG, "GsmSmsAddress FAIL!");
                                hasReplyAddress = true;
                                encodingType3 = i;
                                it4 = it;
                            }
                        } else {
                            i = encodingType3;
                            it = it4;
                            this.replyAddress = null;
                            Rlog.m246w(LOG_TAG, "SMS replyAddress: null");
                        }
                        hasReplyAddress = true;
                    }
                    encodingType3 = i;
                    it4 = it;
                }
            }
            if (!hasReplyAddress) {
                this.replyAddress = this.mOriginatingAddress;
                Rlog.m238d(LOG_TAG, "hasReplayAddress = false, SMS replayAddress = mOriginatingAddress");
            }
            if ((SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("SKT") || SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("LGU")) && this.replyAddress != null && this.replyAddress.address.length() > 3) {
                if (this.replyAddress.ton == 1 && "+82".equals(this.replyAddress.address.substring(0, 3))) {
                    this.replyAddress.address = this.replyAddress.address.replaceFirst("\\+82", "0");
                }
                if (this.replyAddress.ton == 1 && "82".equals(this.replyAddress.address.substring(0, 2))) {
                    this.replyAddress.address = this.replyAddress.address.replaceFirst("82", "0");
                }
            }
        }
        switch (encodingType2) {
            case 0:
                context = null;
                this.mMessageBody = null;
                break;
            case 1:
                if (this.mUserDataHeader != null) {
                    this.mMessageBody = p.getUserDataGSM7Bit(count, hasUserDataHeader ? this.mUserDataHeader.languageTable : 0, hasUserDataHeader ? this.mUserDataHeader.languageShiftTable : 0);
                    context = null;
                    break;
                } else {
                    this.mMessageBody = p.getUserDataGSM7Bit(count, 0, 0);
                    context = null;
                    break;
                }
            case 2:
                if (r.getBoolean(C4337R.bool.config_sms_decode_gsm_8bit_data) || SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_GSM_8BIT_SMS)) {
                    this.mMessageBody = p.getUserDataGSM8bit(count);
                    context = null;
                    break;
                } else {
                    this.mMessageBody = null;
                    context = null;
                    break;
                }
            case 3:
                byte[] userdata = p.getUserData();
                int userLength = userdata.length;
                if (userLength > 0) {
                    char compChar = (char) (((char) (userdata[userLength - 1] & 255)) | ((char) ((userdata[userLength - 2] & 255) << 8)));
                    if (compChar == 55357 || compChar == 55356 || compChar == 55358) {
                        Rlog.m238d(LOG_TAG, "found emoji");
                        this.mlastByte = new byte[2];
                        this.mlastByte[0] = userdata[userLength - 2];
                        this.mlastByte[1] = userdata[userLength - 1];
                        this.mIsfourBytesUnicode = true;
                    }
                }
                this.mMessageBody = p.getUserDataUCS2(count);
                context = null;
                break;
            case 4:
                this.mMessageBody = p.getUserDataKSC5601(count);
                context = null;
                break;
            default:
                context = null;
                break;
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(context, getSubId()).getMnoName().toUpperCase().contains("LGU") && this.mUserDataHeader != null && this.mUserDataHeader.portAddrs != null && this.mUserDataHeader.portAddrs.destPort == 49162) {
            parseSpecificTid(this.mUserDataHeader.portAddrs.destPort);
        }
        if (this.mMessageBody != null) {
            if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("DOCOMO")) {
                this.mMessageBody = this.mMessageBody.replace("\r\n", "\n").replace('\r', '\n');
            }
            parseMessageBody();
        }
        if (!userDataCompressed) {
            this.messageClass = SmsConstants.MessageClass.UNKNOWN;
            return;
        }
        switch (this.mDataCodingScheme & 3) {
            case 0:
                this.messageClass = SmsConstants.MessageClass.CLASS_0;
                break;
            case 1:
                this.messageClass = SmsConstants.MessageClass.CLASS_1;
                break;
            case 2:
                this.messageClass = SmsConstants.MessageClass.CLASS_2;
                break;
            case 3:
                this.messageClass = SmsConstants.MessageClass.CLASS_3;
                break;
        }
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    boolean isUsimDataDownload() {
        int i;
        return this.messageClass == SmsConstants.MessageClass.CLASS_2 && ((i = this.mProtocolIdentifier) == 127 || i == 124);
    }

    public int getNumOfVoicemails() {
        if (!this.mIsMwi && isCphsMwiMessage()) {
            if (this.mOriginatingAddress != null && ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet()) {
                this.mVoiceMailCount = 255;
            } else {
                this.mVoiceMailCount = 0;
            }
            Rlog.m244v(LOG_TAG, "CPHS voice mail message");
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
            Rlog.m241e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (RuntimeException ex) {
            Rlog.m241e(LOG_TAG, "SMS PDU parsing failed: ", ex);
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
            Rlog.m240e(LOG_TAG, "SMS data message may only contain " + ((140 - smsHeaderData.length) - 1) + " bytes");
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
            Rlog.m240e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
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

    /* JADX WARN: Removed duplicated region for block: B:48:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01d0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static SubmitPdu getSubmitPdu(int subId, String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, boolean replyPath, int expiry, int serviceType, int encodingType, int languageTable, int languageShiftTable) {
        byte mtiByte;
        int expiry2;
        byte[] userData;
        int expiry3;
        Rlog.m240e(LOG_TAG, "getSubmitPdu with Options");
        if (message != null && destinationAddress != null) {
            Rlog.m240e(LOG_TAG, "** getSubmitPdu_Options **");
            Rlog.m240e(LOG_TAG, "mno = " + SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName());
            Rlog.m240e(LOG_TAG, "subId = " + subId);
            Rlog.m240e(LOG_TAG, "replyPath = " + replyPath);
            Rlog.m240e(LOG_TAG, "encodingType = " + encodingType);
            Rlog.m240e(LOG_TAG, "**********************");
            SubmitPdu ret = new SubmitPdu();
            byte mtiByte2 = (byte) ((header != null ? 64 : 0) | 1);
            if (replyPath) {
                mtiByte2 = (byte) (mtiByte2 | 128);
                Rlog.m240e(LOG_TAG, "mtiByte = " + ((int) mtiByte2));
            }
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NOT_USED_VALIDITY_PERIOD_FORMAT)) {
                Rlog.m240e(LOG_TAG, "SMS not used TP-VPF  mtiByte = " + ((int) mtiByte2));
                mtiByte = mtiByte2;
            } else {
                byte mtiByte3 = (byte) (mtiByte2 | 16);
                Rlog.m240e(LOG_TAG, "mtiByte = " + ((int) mtiByte3));
                mtiByte = mtiByte3;
            }
            ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, mtiByte, statusReportRequested, ret);
            if (bo == null) {
                return ret;
            }
            try {
            } catch (EncodeException e) {
                e = e;
                expiry2 = expiry;
            }
            if (encodingType == 1) {
                throw new EncodeException("Input Method is Unicode");
            }
            byte[] userData2 = GsmAlphabet.stringToGsm7BitPackedWithHeader(message, header, languageTable, languageShiftTable);
            if ((userData2[0] & 255) > 160) {
                return null;
            }
            bo.write(0);
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NOT_USED_VALIDITY_PERIOD_FORMAT)) {
                expiry2 = expiry;
            } else {
                if (!"CPW".equals(SALES_CODE)) {
                    expiry2 = expiry;
                } else {
                    expiry2 = 167;
                }
                try {
                    bo.write(expiry2);
                    Rlog.m240e(LOG_TAG, "expirty = " + expiry2);
                } catch (EncodeException e2) {
                    e = e2;
                    int expiry4 = expiry2;
                    try {
                        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION)) {
                            try {
                                if (message.charAt(0) == 241 && message.charAt(1) == 160) {
                                    Rlog.m238d(LOG_TAG, "[NSRI_SMS_SEND] encoding 8859_1");
                                    message.getBytes("8859_1");
                                    byte[] textPart = message.getBytes("utf-16be");
                                    if (header != null) {
                                        userData = new byte[header.length + textPart.length + 1];
                                        userData[0] = (byte) header.length;
                                        System.arraycopy(header, 0, userData, 1, header.length);
                                        System.arraycopy(textPart, 0, userData, header.length + 1, textPart.length);
                                    } else {
                                        userData = textPart;
                                    }
                                    if (userData.length > 140) {
                                        return null;
                                    }
                                    bo.write(8);
                                    if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NOT_USED_VALIDITY_PERIOD_FORMAT)) {
                                        expiry3 = expiry4;
                                    } else {
                                        if (!"CPW".equals(SALES_CODE)) {
                                            expiry3 = expiry4;
                                        } else {
                                            expiry3 = 167;
                                        }
                                        bo.write(expiry3);
                                        Rlog.m240e(LOG_TAG, "expirty = " + expiry3);
                                    }
                                    bo.write(userData.length);
                                    bo.write(userData, 0, userData.length);
                                    ret.encodedMessage = bo.toByteArray();
                                    return ret;
                                }
                            } catch (UnsupportedEncodingException e3) {
                                uex = e3;
                                Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", uex);
                                return null;
                            }
                        }
                        message.getBytes("utf-16be");
                        byte[] textPart2 = message.getBytes("utf-16be");
                        if (header != null) {
                        }
                        if (userData.length > 140) {
                        }
                    } catch (UnsupportedEncodingException e4) {
                        uex = e4;
                    }
                }
            }
            try {
                bo.write(userData2, 0, userData2.length);
            } catch (EncodeException e5) {
                e = e5;
                int expiry42 = expiry2;
                if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION)) {
                }
                message.getBytes("utf-16be");
                byte[] textPart22 = message.getBytes("utf-16be");
                if (header != null) {
                }
                if (userData.length > 140) {
                }
            }
            ret.encodedMessage = bo.toByteArray();
            return ret;
        }
        return null;
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
            Rlog.m241e(LOG_TAG, "Implausible UnsupportedEncodingException ", ex);
            return null;
        }
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
            Rlog.m238d(LOG_TAG, "there is no message body");
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
                Rlog.m238d(LOG_TAG, "there is no pagination pattern maybe / or of ");
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
                        Rlog.m238d(LOG_TAG, "there is no pagination yet");
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
                            Rlog.m238d(LOG_TAG, "there is no pagination yet");
                        } catch (StringIndexOutOfBoundsException ex) {
                            Rlog.m240e(LOG_TAG, "extractPagination : " + ex);
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
                            Rlog.m240e(LOG_TAG, "extractPagination : " + ex2);
                            return;
                        } catch (NumberFormatException e3) {
                            Rlog.m238d(LOG_TAG, "there is no pagination");
                        }
                    }
                }
            }
            if (!paginationSuccess) {
                Rlog.m238d(LOG_TAG, "No pagination found");
                return;
            }
            Rlog.m238d(LOG_TAG, "segmented number: " + segNum);
            Rlog.m238d(LOG_TAG, "total number: " + totNum);
            if ((segNum < 0 && segNum > 9999) || (totNum < 0 && segNum > 9999)) {
                Rlog.m238d(LOG_TAG, "Its not segmented sms. ");
                return;
            }
            if (segNum == 0 || totNum == 0 || segNum > totNum || totNum > 9999) {
                Rlog.m238d(LOG_TAG, "It's not segmented sms.");
                return;
            }
            Rlog.m238d(LOG_TAG, "It's segmented sms");
            SmsHeader.ConcatRef concatRef = new SmsHeader.ConcatRef();
            concatRef.seqNumber = segNum;
            concatRef.msgCount = totNum;
            concatRef.refNumber = -1;
            this.mUserDataHeader = new SmsHeader();
            this.mUserDataHeader.concatRef = concatRef;
        } catch (StringIndexOutOfBoundsException ex3) {
            Rlog.m240e(LOG_TAG, "extractPagination : " + ex3);
        }
    }
}
