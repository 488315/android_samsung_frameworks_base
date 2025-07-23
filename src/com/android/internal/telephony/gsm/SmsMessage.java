package com.android.internal.telephony.gsm;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
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
import org.apache.http.conn.ssl.SSLSocketFactory;

/* loaded from: classes4.dex */
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

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessagePriority() {
        return 0;
    }

    public static SmsMessage createFromPdu(byte[] bArr) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), bArr);
    }

    public boolean isTypeZero() {
        return this.mProtocolIdentifier == 64;
    }

    public static SmsMessage createFromEfRecord(int i, byte[] bArr) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.mIndexOnIcc = i;
            byte b = bArr[0];
            if ((b & 1) == 0) {
                Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            smsMessage.mStatusOnIcc = b & 7;
            int length = bArr.length - 1;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 1, bArr2, 0, length);
            smsMessage.parsePdu(bArr2);
            return smsMessage;
        } catch (RuntimeException e) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(String str) {
        return ((str.length() / 2) - Integer.parseInt(str.substring(0, 2), 16)) - 1;
    }

    public static int getRelativeValidityPeriod(int i) {
        if (i < 5) {
            return -1;
        }
        if (i <= 720) {
            return (i / 5) - 1;
        }
        if (i <= 1440) {
            return ((i - 720) / 30) + 143;
        }
        if (i <= 43200) {
            return (i / 1440) + 166;
        }
        if (i <= VALIDITY_PERIOD_MAX) {
            return (i / 10080) + 192;
        }
        return -1;
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, byte[] bArr) {
        return getSubmitPdu(str, str2, str3, z, bArr, 0, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, byte[] bArr, int i, int i2, int i3) {
        return getSubmitPdu(str, str2, str3, z, bArr, i, i2, i3, -1, 0);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, byte[] bArr, int i, int i2, int i3, int i4) {
        return getSubmitPdu(str, str2, str3, z, bArr, i, i2, i3, i4, 0);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        byte[] encodeUCS2;
        if (str3 == null || str2 == null) {
            return null;
        }
        byte[] bArr2 = SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SEGMENTED_SMS) ? null : bArr;
        if (i == 0) {
            GsmAlphabet.TextEncodingDetails calculateLength = calculateLength(str3, false);
            i6 = calculateLength.codeUnitSize;
            i7 = calculateLength.languageTable;
            i8 = calculateLength.languageShiftTable;
            if (i6 == 1 && (i7 != 0 || i8 != 0)) {
                if (bArr2 != null) {
                    SmsHeader fromByteArray = SmsHeader.fromByteArray(bArr2);
                    if (fromByteArray.languageTable != i7 || fromByteArray.languageShiftTable != i8) {
                        Rlog.w(LOG_TAG, "Updating language table in SMS header: " + fromByteArray.languageTable + " -> " + i7 + ", " + fromByteArray.languageShiftTable + " -> " + i8);
                        fromByteArray.languageTable = i7;
                        fromByteArray.languageShiftTable = i8;
                        bArr2 = SmsHeader.toByteArray(fromByteArray);
                    }
                } else {
                    SmsHeader smsHeader = new SmsHeader();
                    smsHeader.languageTable = i7;
                    smsHeader.languageShiftTable = i8;
                    bArr2 = SmsHeader.toByteArray(smsHeader);
                }
            }
        } else {
            i6 = i;
            i7 = i2;
            i8 = i3;
        }
        byte[] bArr3 = bArr2;
        SubmitPdu submitPdu = new SubmitPdu();
        int relativeValidityPeriod = getRelativeValidityPeriod(i4);
        byte b = bArr3 != null ? (byte) 65 : (byte) 1;
        if (relativeValidityPeriod != -1) {
            b = (byte) (b | 16);
        }
        ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, b, z, submitPdu, i5);
        if (submitPduHead == null) {
            return submitPdu;
        }
        try {
            if (i6 == 1) {
                encodeUCS2 = GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr3, i7, i8);
            } else {
                try {
                    encodeUCS2 = encodeUCS2(str3, bArr3);
                } catch (UnsupportedEncodingException e) {
                    Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
                    return null;
                }
            }
        } catch (EncodeException e2) {
            if (e2.getError() == 1) {
                Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e2);
                return null;
            }
            try {
                encodeUCS2 = encodeUCS2(str3, bArr3);
                i6 = 3;
            } catch (EncodeException e3) {
                Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e3);
                return null;
            } catch (UnsupportedEncodingException e4) {
                Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e4);
                return null;
            }
        }
        if (i6 == 1) {
            if ((encodeUCS2[0] & 255) > 160) {
                Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " septets)");
                return null;
            }
            submitPduHead.write(0);
        } else {
            if ((encodeUCS2[0] & 255) > 140) {
                Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " bytes)");
                return null;
            }
            submitPduHead.write(8);
        }
        if (relativeValidityPeriod != -1) {
            submitPduHead.write(relativeValidityPeriod);
        }
        submitPduHead.write(encodeUCS2, 0, encodeUCS2.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    private static byte[] encodeUCS2(String str, byte[] bArr) throws UnsupportedEncodingException, EncodeException {
        byte[] bytes = str.getBytes("utf-16be");
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length + bytes.length + 1];
            bArr2[0] = (byte) bArr.length;
            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
            System.arraycopy(bytes, 0, bArr2, bArr.length + 1, bytes.length);
            bytes = bArr2;
        }
        if (bytes.length > 255) {
            throw new EncodeException("Payload cannot exceed 255 bytes", 1);
        }
        byte[] bArr3 = new byte[bytes.length + 1];
        bArr3[0] = (byte) (255 & bytes.length);
        System.arraycopy(bytes, 0, bArr3, 1, bytes.length);
        return bArr3;
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z) {
        return getSubmitPdu(str, str2, str3, z, (byte[]) null);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, int i) {
        return getSubmitPdu(str, str2, str3, z, (byte[]) null, 0, 0, 0, i, 0);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, int i, byte[] bArr, boolean z, int i2) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = i;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] byteArray = SmsHeader.toByteArray(smsHeader);
        if (bArr.length + byteArray.length + 1 > 140) {
            Rlog.e(LOG_TAG, "SMS data message may only contain " + (139 - byteArray.length) + " bytes");
            return null;
        }
        SubmitPdu submitPdu = new SubmitPdu();
        ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, (byte) 65, z, submitPdu, i2);
        if (submitPduHead == null) {
            return submitPdu;
        }
        submitPduHead.write(4);
        submitPduHead.write(bArr.length + byteArray.length + 1);
        submitPduHead.write(byteArray.length);
        submitPduHead.write(byteArray, 0, byteArray.length);
        submitPduHead.write(bArr, 0, bArr.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, int i, byte[] bArr, boolean z) {
        return getSubmitPdu(str, str2, i, bArr, z, 0);
    }

    private static ByteArrayOutputStream getSubmitPduHead(String str, String str2, byte b, boolean z, SubmitPdu submitPdu) {
        return getSubmitPduHead(str, str2, b, z, submitPdu, 0);
    }

    private static ByteArrayOutputStream getSubmitPduHead(String str, String str2, byte b, boolean z, SubmitPdu submitPdu, int i) {
        byte[] networkPortionToCalledPartyBCD;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(180);
        if (str == null) {
            submitPdu.encodedScAddress = null;
        } else {
            submitPdu.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        }
        if (z) {
            b = (byte) (b | 32);
        }
        byteArrayOutputStream.write(b);
        byteArrayOutputStream.write(i);
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("DOCOMO")) {
            networkPortionToCalledPartyBCD = PhoneNumberUtils.docomoNetworkPortionToCalledPartyBCD(str2);
        } else {
            networkPortionToCalledPartyBCD = PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        }
        if (networkPortionToCalledPartyBCD == null) {
            Rlog.e(LOG_TAG, "daBytes is null");
            return null;
        }
        byteArrayOutputStream.write(((networkPortionToCalledPartyBCD.length - 1) * 2) - ((networkPortionToCalledPartyBCD[networkPortionToCalledPartyBCD.length - 1] & 240) != 240 ? 0 : 1));
        byteArrayOutputStream.write(networkPortionToCalledPartyBCD, 0, networkPortionToCalledPartyBCD.length);
        byteArrayOutputStream.write(0);
        return byteArrayOutputStream;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00bc, code lost:
    
        r10.write(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bf, code lost:
    
        if (r5 != 1) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c1, code lost:
    
        r0 = com.android.internal.telephony.GsmAlphabet.stringToGsm7BitPackedWithHeader(r21, r8, r6, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00e7, code lost:
    
        if (r5 != 1) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ef, code lost:
    
        if ((r0[0] & 255) <= 160) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00f1, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Message too long (" + (r0[0] & 255) + " septets)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0109, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x010a, code lost:
    
        r10.write(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0132, code lost:
    
        r5 = new byte[7];
        r6 = java.time.Instant.ofEpochMilli(r22).atZone(java.time.ZoneId.systemDefault());
        r8 = r6.toLocalDateTime();
        r6 = (r6.getOffset().getTotalSeconds() / 60) / 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0151, code lost:
    
        if (r6 >= 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0153, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0156, code lost:
    
        if (r11 == false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0158, code lost:
    
        r6 = -r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0159, code lost:
    
        r12 = r8.getYear();
        r13 = r8.getMonthValue();
        r15 = r8.getDayOfMonth();
        r16 = r8.getHour();
        r17 = r8.getMinute();
        r8 = r8.getSecond();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0175, code lost:
    
        if (r12 <= 2000) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0177, code lost:
    
        r12 = r12 - 2000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x017b, code lost:
    
        r5[0] = (byte) ((((r12 % 10) & 15) << 4) | ((r12 / 10) & 15));
        r5[1] = (byte) ((((r13 % 10) & 15) << 4) | ((r13 / 10) & 15));
        r5[2] = (byte) ((((r15 % 10) & 15) << 4) | ((r15 / 10) & 15));
        r5[3] = (byte) ((((r16 % 10) & 15) << 4) | ((r16 / 10) & 15));
        r5[4] = (byte) ((((r17 % 10) & 15) << 4) | ((r17 / 10) & 15));
        r5[5] = (byte) ((((r8 % 10) & 15) << 4) | ((r8 / 10) & 15));
        r2 = (byte) ((((r6 % 10) & 15) << 4) | ((r6 / 10) & 15));
        r5[6] = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01e1, code lost:
    
        if (r11 == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01e3, code lost:
    
        r5[6] = (byte) (r2 | 8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01e7, code lost:
    
        r10.write(r5, 0, 7);
        r10.write(r0, 0, r0.length);
        r9.encodedMessage = r10.toByteArray();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01f4, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0179, code lost:
    
        r12 = r12 - 1900;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0155, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0114, code lost:
    
        if ((r0[0] & 255) <= 140) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0116, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Message too long (" + (r0[0] & 255) + " bytes)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x012e, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x012f, code lost:
    
        r10.write(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c6, code lost:
    
        r0 = encodeUCS2(r21, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00cd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ce, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Implausible UnsupportedEncodingException ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00d1, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d8, code lost:
    
        if (r0.getError() == 1) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00da, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Exceed size limitation EncodeException", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00dd, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00de, code lost:
    
        r0 = encodeUCS2(r21, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00e2, code lost:
    
        r5 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01fa, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01fb, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Exceed size limitation EncodeException", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01fe, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01f5, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01f6, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Implausible UnsupportedEncodingException ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01f9, code lost:
    
        return r16;
     */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.time.LocalDateTime] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getDeliverPdu(java.lang.String r19, java.lang.String r20, java.lang.String r21, long r22) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.getDeliverPdu(java.lang.String, java.lang.String, java.lang.String, long):com.android.internal.telephony.gsm.SmsMessage$SubmitPdu");
    }

    private static class PduParser {
        byte[] mPdu;
        byte[] mUserData;
        SmsHeader mUserDataHeader;
        int mValidityPeriodFormat = 0;
        int mSubId = SubscriptionManager.getDefaultSmsSubscriptionId();
        int mCur = 0;
        int mUserDataSeptetPadding = 0;

        PduParser(byte[] bArr) {
            this.mPdu = bArr;
        }

        void setSubIdforParser(int i) {
            this.mSubId = i;
        }

        String getSCAddress() {
            int i = getByte();
            String str = null;
            if (i != 0) {
                try {
                    str = PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, i, 2);
                } catch (RuntimeException e) {
                    Rlog.d(SmsMessage.LOG_TAG, "invalid SC address: ", e);
                }
            }
            this.mCur += i;
            return str;
        }

        int getByte() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            return bArr[i] & 255;
        }

        GsmSmsAddress getAddress() {
            int i = (((this.mPdu[this.mCur] & 255) + 1) / 2) + 2;
            try {
                Rlog.d(SmsMessage.LOG_TAG, "getAddress : Mno = " + SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getMnoName());
                GsmSmsAddress gsmSmsAddress = new GsmSmsAddress(this.mSubId, this.mPdu, this.mCur, i);
                this.mCur += i;
                return gsmSmsAddress;
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        long getSCTimestampMillis() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            int gsmBcdByteToInt = IccUtils.gsmBcdByteToInt(bArr[i]);
            byte[] bArr2 = this.mPdu;
            int i2 = this.mCur;
            this.mCur = i2 + 1;
            int gsmBcdByteToInt2 = IccUtils.gsmBcdByteToInt(bArr2[i2]);
            byte[] bArr3 = this.mPdu;
            int i3 = this.mCur;
            this.mCur = i3 + 1;
            int gsmBcdByteToInt3 = IccUtils.gsmBcdByteToInt(bArr3[i3]);
            byte[] bArr4 = this.mPdu;
            int i4 = this.mCur;
            this.mCur = i4 + 1;
            int gsmBcdByteToInt4 = IccUtils.gsmBcdByteToInt(bArr4[i4]);
            byte[] bArr5 = this.mPdu;
            int i5 = this.mCur;
            this.mCur = i5 + 1;
            int gsmBcdByteToInt5 = IccUtils.gsmBcdByteToInt(bArr5[i5]);
            byte[] bArr6 = this.mPdu;
            int i6 = this.mCur;
            this.mCur = i6 + 1;
            int gsmBcdByteToInt6 = IccUtils.gsmBcdByteToInt(bArr6[i6]);
            byte[] bArr7 = this.mPdu;
            int i7 = this.mCur;
            this.mCur = i7 + 1;
            byte b = bArr7[i7];
            int gsmBcdByteToInt7 = IccUtils.gsmBcdByteToInt((byte) (b & (-9)));
            if ((b & 8) != 0) {
                gsmBcdByteToInt7 = -gsmBcdByteToInt7;
            }
            Time time = new Time(Time.TIMEZONE_UTC);
            time.year = gsmBcdByteToInt >= 90 ? gsmBcdByteToInt + 1900 : gsmBcdByteToInt + 2000;
            time.month = gsmBcdByteToInt2 - 1;
            time.monthDay = gsmBcdByteToInt3;
            time.hour = gsmBcdByteToInt4;
            time.minute = gsmBcdByteToInt5;
            time.second = gsmBcdByteToInt6;
            return time.toMillis(true) - (gsmBcdByteToInt7 * Build.VERSION_CODES_FULL.GINGERBREAD);
        }

        /* JADX WARN: Code restructure failed: missing block: B:5:0x000b, code lost:
        
            if (r1 != 3) goto L10;
         */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0079  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x004e  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int constructUserData(boolean r9, boolean r10) {
            /*
                r8 = this;
                int r0 = r8.mCur
                int r1 = r8.mValidityPeriodFormat
                r2 = 2
                r3 = 1
                if (r1 == r3) goto L11
                if (r1 == r2) goto Le
                r4 = 3
                if (r1 == r4) goto L11
                goto L12
            Le:
                int r0 = r0 + 1
                goto L12
            L11:
                r0 = 7
            L12:
                byte[] r1 = r8.mPdu
                int r4 = r0 + 1
                r5 = r1[r0]
                r5 = r5 & 255(0xff, float:3.57E-43)
                r6 = 0
                if (r9 == 0) goto L45
                int r0 = r0 + r2
                r2 = r1[r4]
                r2 = r2 & 255(0xff, float:3.57E-43)
                byte[] r4 = new byte[r2]
                java.lang.System.arraycopy(r1, r0, r4, r6, r2)
                int r1 = r8.mSubId
                com.android.internal.telephony.SmsHeader r1 = com.android.internal.telephony.SmsHeader.semFromByteArray(r1, r4)
                r8.mUserDataHeader = r1
                int r4 = r0 + r2
                int r0 = r2 + 1
                int r0 = r0 * 8
                int r1 = r0 / 7
                int r7 = r0 % 7
                if (r7 <= 0) goto L3d
                r7 = r3
                goto L3e
            L3d:
                r7 = r6
            L3e:
                int r1 = r1 + r7
                int r7 = r1 * 7
                int r7 = r7 - r0
                r8.mUserDataSeptetPadding = r7
                goto L47
            L45:
                r1 = r6
                r2 = r1
            L47:
                if (r10 == 0) goto L4e
                byte[] r0 = r8.mPdu
                int r0 = r0.length
                int r0 = r0 - r4
                goto L58
            L4e:
                if (r9 == 0) goto L52
                int r2 = r2 + r3
                goto L53
            L52:
                r2 = r6
            L53:
                int r0 = r5 - r2
                if (r0 >= 0) goto L58
                r0 = r6
            L58:
                byte[] r0 = new byte[r0]
                r8.mUserData = r0
                boolean r0 = com.android.internal.telephony.gsm.SmsMessage.m8313$$Nest$sfgetmUnsupportedDatacodingScheme()
                if (r0 == 0) goto L6d
                if (r9 == 0) goto L65
                goto L6d
            L65:
                java.lang.String r9 = "SmsMessage"
                java.lang.String r0 = "array copy skip! if dataCodingScheme is unsupporting,\n encodingType is Unknown and messageBody is null"
                com.android.telephony.Rlog.e(r9, r0)
                goto L75
            L6d:
                byte[] r9 = r8.mPdu
                byte[] r0 = r8.mUserData
                int r2 = r0.length
                java.lang.System.arraycopy(r9, r4, r0, r6, r2)
            L75:
                r8.mCur = r4
                if (r10 == 0) goto L7e
                int r5 = r5 - r1
                if (r5 >= 0) goto L7d
                return r6
            L7d:
                return r5
            L7e:
                byte[] r8 = r8.mUserData
                int r8 = r8.length
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.PduParser.constructUserData(boolean, boolean):int");
        }

        byte[] getUserData() {
            return this.mUserData;
        }

        SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        String getUserDataGSM7Bit(int i, int i2, int i3) {
            String gsm7BitPackedToString = GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, i, this.mUserDataSeptetPadding, i2, i3);
            this.mCur += (i * 7) / 8;
            return gsm7BitPackedToString;
        }

        String getUserDataGSM8bit(int i) {
            String gsm8BitUnpackedToString = GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, i);
            this.mCur += i;
            return gsm8BitUnpackedToString;
        }

        String getUserDataUCS2(int i) {
            String str;
            try {
                byte[] userData = getUserData();
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && userData.length > 0) {
                    str = getUseDataNSRISms(i);
                } else {
                    str = new String(this.mPdu, this.mCur, i, CharacterSets.MIMENAME_UTF_16);
                }
            } catch (UnsupportedEncodingException e) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = "";
            }
            this.mCur += i;
            return str;
        }

        String getUserDataKSC5601(int i) {
            String str;
            try {
                byte[] userData = getUserData();
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && userData.length > 0) {
                    if (Integer.toHexString(userData[0] & 255).equals("f1") && Integer.toHexString(userData[1] & 255).equals("a0")) {
                        Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUserDataKSC5601 KSC5601");
                        str = new String(this.mPdu, this.mCur, i, "ISO8859_1");
                    } else {
                        str = new String(this.mPdu, this.mCur, i, "KSC5601");
                    }
                } else {
                    str = new String(this.mPdu, this.mCur, i, "KSC5601");
                }
            } catch (UnsupportedEncodingException e) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = "";
            }
            this.mCur += i;
            return str;
        }

        boolean moreDataPresent() {
            return this.mPdu.length > this.mCur;
        }

        String getUseDataNSRISms(int i) {
            byte[] userData = getUserData();
            Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUseDataNSRISms");
            try {
                if (Integer.toHexString(userData[0] & 255).equals("f1") && Integer.toHexString(userData[1] & 255).equals("a0")) {
                    String str = new String(this.mPdu, this.mCur, i, "ISO8859_1");
                    Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] : getUserDataUCS2    ISO8859_1");
                    return str;
                }
                return new String(this.mPdu, this.mCur, i, CharacterSets.MIMENAME_UTF_16);
            } catch (UnsupportedEncodingException e) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", e);
                return "";
            }
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean z) {
        String translate = Resources.getSystem().getBoolean(R.bool.config_sms_force_7bit_encoding) ? Sms7BitEncodingTranslator.translate(charSequence, false) : null;
        if (!TextUtils.isEmpty(translate)) {
            charSequence = translate;
        }
        if (charSequence == null) {
            return null;
        }
        GsmAlphabet.TextEncodingDetails countGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, z, mIgnoreSpecialChar);
        mIgnoreSpecialChar = false;
        return countGsmSeptets == null ? SmsMessageBase.calcUnicodeEncodingDetails(charSequence) : countGsmSeptets;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getProtocolIdentifier() {
        return this.mProtocolIdentifier;
    }

    public int getDataCodingScheme() {
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
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_NOT_COUNT_VOICEMAIL) || " ".equals(getMessageBody())) {
                return true;
            }
            String str = SALES_CODE;
            if ("RWC".equals(str) || SSLSocketFactory.TLS.equals(str) || "MTA".equals(str)) {
                Rlog.d(LOG_TAG, "CPHS MWI messages in Canada " + str + " don't store");
                return true;
            }
        }
        if (getMessageBody() == null) {
            return false;
        }
        getMessageBody().length();
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

    private void parsePdu(byte[] bArr) {
        this.mPdu = bArr;
        PduParser pduParser = new PduParser(bArr);
        pduParser.setSubIdforParser(getSubId());
        this.mScAddress = pduParser.getSCAddress();
        String str = this.mScAddress;
        int i = pduParser.getByte();
        this.mMti = i & 3;
        int i2 = this.mMti;
        if (i2 != 0) {
            if (i2 == 1) {
                parseSmsSubmit(pduParser, i);
                return;
            } else if (i2 == 2) {
                parseSmsStatusReport(pduParser, i);
                return;
            } else if (i2 != 3) {
                throw new RuntimeException("Unsupported message type");
            }
        }
        parseSmsDeliver(pduParser, i);
    }

    private void parseSmsStatusReport(PduParser pduParser, int i) {
        this.mIsStatusReportMessage = true;
        this.mMessageRef = pduParser.getByte();
        this.mRecipientAddress = pduParser.getAddress();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        pduParser.getSCTimestampMillis();
        this.mStatus = pduParser.getByte();
        if (pduParser.moreDataPresent()) {
            int i2 = pduParser.getByte();
            int i3 = i2;
            while ((i3 & 128) != 0) {
                i3 = pduParser.getByte();
            }
            if ((i2 & 120) == 0) {
                if ((i2 & 1) != 0) {
                    this.mProtocolIdentifier = pduParser.getByte();
                }
                if ((i2 & 2) != 0) {
                    this.mDataCodingScheme = pduParser.getByte();
                }
                if ((i2 & 4) != 0) {
                    parseUserData(pduParser, (i & 64) == 64);
                }
            }
        }
    }

    private void parseSmsDeliver(PduParser pduParser, int i) {
        this.mReplyPathPresent = (i & 128) == 128;
        this.mOriginatingAddress = pduParser.getAddress();
        if (this.mOriginatingAddress != null && SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SPECIAL_ADDRESS_HANDLING_FOR) && this.mOriginatingAddress.address.startsWith("+00852")) {
            String substring = this.mOriginatingAddress.address.substring(3);
            this.mOriginatingAddress.address = "+";
            StringBuilder sb = new StringBuilder();
            SmsAddress smsAddress = this.mOriginatingAddress;
            sb.append(smsAddress.address);
            sb.append(substring);
            smsAddress.address = sb.toString();
        }
        SmsAddress smsAddress2 = this.mOriginatingAddress;
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        parseUserData(pduParser, (i & 64) == 64);
    }

    private void parseSmsSubmit(PduParser pduParser, int i) {
        this.mReplyPathPresent = (i & 128) == 128;
        this.mMessageRef = pduParser.getByte();
        this.mRecipientAddress = pduParser.getAddress();
        SmsAddress smsAddress = this.mRecipientAddress;
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        int i2 = (i >> 3) & 3;
        int i3 = i2 == 0 ? 0 : i2 == 2 ? 1 : 7;
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            pduParser.getByte();
            i3 = i4;
        }
        parseUserData(pduParser, (i & 64) == 64);
    }

    /* JADX WARN: Code restructure failed: missing block: B:180:0x0063, code lost:
    
        if (r4 != 3) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0213, code lost:
    
        if ((r10 & 240) != 224) goto L112;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseUserData(com.android.internal.telephony.gsm.SmsMessage.PduParser r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 1170
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.parseUserData(com.android.internal.telephony.gsm.SmsMessage$PduParser, boolean):void");
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    boolean isUsimDataDownload() {
        if (this.messageClass != SmsConstants.MessageClass.CLASS_2) {
            return false;
        }
        int i = this.mProtocolIdentifier;
        return i == 127 || i == 124;
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

    public static SmsMessage semCreateFromPdu(int i, byte[] bArr) {
        try {
            SmsMessage smsMessage = new SmsMessage();
            smsMessage.setSubId(getSubId(i));
            smsMessage.parsePdu(bArr);
            return smsMessage;
        } catch (OutOfMemoryError e) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (RuntimeException e2) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e2);
            return null;
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthForCdma(CharSequence charSequence, boolean z) {
        mIgnoreSpecialChar = true;
        return calculateLength(charSequence, z);
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getMessageIdentifier() {
        return this.mMessageRef;
    }

    private static int decToBcd(int i) {
        return ((i % 10) * 10) + (i / 10);
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, int i2, int i3, byte[] bArr, boolean z) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = i2;
        portAddrs.origPort = i3;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] byteArray = SmsHeader.toByteArray(smsHeader);
        if (bArr.length + byteArray.length + 1 > 140) {
            Rlog.e(LOG_TAG, "SMS data message may only contain " + (139 - byteArray.length) + " bytes");
            return null;
        }
        SubmitPdu submitPdu = new SubmitPdu();
        ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, (byte) 65, z, submitPdu);
        if (submitPduHead == null) {
            return submitPdu;
        }
        submitPduHead.write(4);
        submitPduHead.write(bArr.length + byteArray.length + 1);
        submitPduHead.write(byteArray.length);
        submitPduHead.write(byteArray, 0, byteArray.length);
        submitPduHead.write(bArr, 0, bArr.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    public static SubmitPdu getSubmitPduForAutoLogin(String str, String str2, String str3, boolean z, int i) {
        return getSubmitPduForAutoLogin(str, str2, str3, z, null, i);
    }

    public static SubmitPdu getSubmitPduForAutoLogin(String str, String str2, String str3, boolean z, byte[] bArr, int i) {
        if (str3 == null || str2 == null) {
            return null;
        }
        SubmitPdu submitPdu = new SubmitPdu();
        int relativeValidityPeriod = getRelativeValidityPeriod(i);
        int i2 = relativeValidityPeriod >= 0 ? 2 : 0;
        ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, (byte) ((bArr != null ? 64 : 0) | (i2 << 3) | 1), z, submitPdu);
        if (submitPduHead == null) {
            return submitPdu;
        }
        byte[] stringToGsm8BitPackedForAutoLogin = GsmAlphabet.stringToGsm8BitPackedForAutoLogin(str3);
        if (stringToGsm8BitPackedForAutoLogin == null) {
            return null;
        }
        if ((stringToGsm8BitPackedForAutoLogin[0] & 255) > 140) {
            Rlog.e(LOG_TAG, "Message too long (" + (stringToGsm8BitPackedForAutoLogin[0] & 255) + " bytes)");
            return null;
        }
        submitPduHead.write(4);
        if (i2 == 2) {
            submitPduHead.write(relativeValidityPeriod);
        }
        submitPduHead.write(stringToGsm8BitPackedForAutoLogin, 0, stringToGsm8BitPackedForAutoLogin.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, byte[] bArr, boolean z2, int i2, int i3, int i4) {
        return getSubmitPdu(i, str, str2, str3, z, bArr, z2, i2, i3, i4, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, boolean z2, int i2, int i3, int i4, int i5, int i6) {
        if (i5 > 0 || i6 > 0) {
            SmsHeader smsHeader = new SmsHeader();
            smsHeader.languageTable = i5;
            smsHeader.languageShiftTable = i6;
            return getSubmitPdu(i, str, str2, str3, z, SmsHeader.toByteArray(smsHeader), z2, i2, i3, i4, i5, i6);
        }
        return getSubmitPdu(i, str, str2, str3, z, null, z2, i2, i3, i4, i5, i6);
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, byte[] bArr, boolean z2, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        Rlog.e(LOG_TAG, "getSubmitPdu with Options");
        if (str3 != null && str2 != null) {
            Rlog.e(LOG_TAG, "** getSubmitPdu_Options **");
            Rlog.e(LOG_TAG, "mno = " + SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getMnoName());
            Rlog.e(LOG_TAG, "subId = " + i);
            Rlog.e(LOG_TAG, "replyPath = " + z2);
            Rlog.e(LOG_TAG, "encodingType = " + i4);
            Rlog.e(LOG_TAG, "**********************");
            SubmitPdu submitPdu = new SubmitPdu();
            byte b = (byte) ((bArr != null ? 64 : 0) | 1);
            if (z2) {
                b = (byte) (b | 128);
                Rlog.e(LOG_TAG, "mtiByte = " + ((int) b));
            }
            if (!useValidityPeriod(i)) {
                Rlog.e(LOG_TAG, "SMS not used TP-VPF  mtiByte = " + ((int) b));
            } else {
                b = (byte) (b | 16);
                Rlog.e(LOG_TAG, "mtiByte = " + ((int) b));
            }
            ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, b, z, submitPdu);
            if (submitPduHead == null) {
                return submitPdu;
            }
            try {
            } catch (EncodeException unused) {
                i7 = i2;
            }
            if (i4 == 1) {
                throw new EncodeException("Input Method is Unicode");
            }
            byte[] stringToGsm7BitPackedWithHeader = GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr, i5, i6);
            if ((stringToGsm7BitPackedWithHeader[0] & 255) > 160) {
                return null;
            }
            submitPduHead.write(0);
            if (useValidityPeriod(i)) {
                i7 = "CPW".equals(SALES_CODE) ? 167 : i2;
                try {
                    submitPduHead.write(i7);
                    Rlog.e(LOG_TAG, "expirty = " + i7);
                } catch (EncodeException unused2) {
                    try {
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && str3.charAt(0) == 241 && str3.charAt(1) == 160) {
                            Rlog.d(LOG_TAG, "[NSRI_SMS_SEND] encoding 8859_1");
                            str3.getBytes("8859_1");
                        } else {
                            str3.getBytes("utf-16be");
                        }
                        byte[] bytes = str3.getBytes("utf-16be");
                        if (bArr != null) {
                            byte[] bArr2 = new byte[bArr.length + bytes.length + 1];
                            bArr2[0] = (byte) bArr.length;
                            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
                            System.arraycopy(bytes, 0, bArr2, bArr.length + 1, bytes.length);
                            bytes = bArr2;
                        }
                        if (bytes.length > 140) {
                            return null;
                        }
                        submitPduHead.write(8);
                        if (useValidityPeriod(i)) {
                            int i8 = "CPW".equals(SALES_CODE) ? 167 : i7;
                            submitPduHead.write(i8);
                            Rlog.e(LOG_TAG, "expirty = " + i8);
                        }
                        submitPduHead.write(bytes.length);
                        submitPduHead.write(bytes, 0, bytes.length);
                        submitPdu.encodedMessage = submitPduHead.toByteArray();
                        return submitPdu;
                    } catch (UnsupportedEncodingException e) {
                        Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
                    }
                }
            } else {
                i7 = i2;
            }
            submitPduHead.write(stringToGsm7BitPackedWithHeader, 0, stringToGsm7BitPackedWithHeader.length);
            submitPdu.encodedMessage = submitPduHead.toByteArray();
            return submitPdu;
        }
        return null;
    }

    public static SubmitPdu getSubmitPduForKTOTA(String str, String str2, String str3) {
        SubmitPdu submitPdu = new SubmitPdu();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(180);
        if (str == null) {
            submitPdu.encodedScAddress = null;
        } else {
            submitPdu.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        }
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(0);
        byte[] networkPortionToCalledPartyBCD = PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        if (networkPortionToCalledPartyBCD == null) {
            byteArrayOutputStream.write(0);
        } else {
            byteArrayOutputStream.write(((networkPortionToCalledPartyBCD.length - 1) * 2) - ((networkPortionToCalledPartyBCD[networkPortionToCalledPartyBCD.length - 1] & 240) != 240 ? 0 : 1));
            byteArrayOutputStream.write(networkPortionToCalledPartyBCD, 0, networkPortionToCalledPartyBCD.length);
            byteArrayOutputStream.write(127);
        }
        try {
            byte[] stringToGsm7BitPacked = GsmAlphabet.stringToGsm7BitPacked(str3);
            if ((stringToGsm7BitPacked[0] & 255) > 160) {
                return null;
            }
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(stringToGsm7BitPacked, 0, stringToGsm7BitPacked.length);
            submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
            return submitPdu;
        } catch (EncodeException e) {
            Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
            return null;
        }
    }

    private static boolean useValidityPeriod(int i) {
        Context applicationContext;
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_NOT_USED_VALIDITY_PERIOD_FORMAT)) {
            return false;
        }
        if (TelephonyFeatures.isSupportTiantong() && (applicationContext = ActivityThread.currentApplication().getApplicationContext()) != null) {
            try {
                if (Settings.Global.getInt(applicationContext.getContentResolver(), Settings.Global.SATELLITE_MODE_ENABLED, 0) != 0) {
                    Rlog.d(LOG_TAG, "Do not use TP-VP for Tiantong");
                    return false;
                }
            } catch (SecurityException e) {
                Rlog.e(LOG_TAG, "SecurityException during get setting DB" + e);
            }
        }
        return true;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEncodingType(CharSequence charSequence, boolean z, int i) {
        GsmAlphabet.TextEncodingDetails countGsmSeptets;
        new GsmAlphabet.TextEncodingDetails();
        if (i == 1) {
            countGsmSeptets = null;
        } else if (i == 0) {
            countGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, true);
        } else {
            countGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, z);
        }
        return countGsmSeptets == null ? SmsMessageBase.calcUnicodeEncodingDetails(charSequence) : countGsmSeptets;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence charSequence, boolean z, int i) {
        GsmAlphabet.TextEncodingDetails countGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, z, i);
        if (countGsmSeptetsWithEmail == null) {
            countGsmSeptetsWithEmail = new GsmAlphabet.TextEncodingDetails();
            int i2 = i * 2;
            int i3 = i2 > 0 ? 139 - i2 : 140;
            int i4 = i2 > 0 ? 133 - i2 : 134;
            int length = charSequence.length() * 2;
            countGsmSeptetsWithEmail.codeUnitCount = charSequence.length();
            if (length > i3) {
                if (i2 > i3 - 2) {
                    countGsmSeptetsWithEmail.msgCount = 1000;
                    countGsmSeptetsWithEmail.codeUnitsRemaining = -1;
                } else {
                    int i5 = length % i4;
                    if (i5 != 0) {
                        countGsmSeptetsWithEmail.msgCount = (length / i4) + 1;
                        countGsmSeptetsWithEmail.codeUnitsRemaining = (i4 - i5) / 2;
                    } else {
                        countGsmSeptetsWithEmail.msgCount = length / i4;
                        countGsmSeptetsWithEmail.codeUnitsRemaining = 0;
                    }
                }
            } else if (i2 >= i4 - 2) {
                countGsmSeptetsWithEmail.msgCount = 1000;
                countGsmSeptetsWithEmail.codeUnitsRemaining = -1;
            } else {
                countGsmSeptetsWithEmail.msgCount = 1;
                countGsmSeptetsWithEmail.codeUnitsRemaining = (i3 - length) / 2;
            }
            countGsmSeptetsWithEmail.codeUnitSize = 3;
        }
        return countGsmSeptetsWithEmail;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence charSequence, boolean z, int i, int i2) {
        GsmAlphabet.TextEncodingDetails countGsmSeptetsWithEmail;
        new GsmAlphabet.TextEncodingDetails();
        if (i == 1) {
            countGsmSeptetsWithEmail = null;
        } else if (i == 0) {
            countGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, true, i2);
        } else {
            countGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, z, i2);
        }
        if (countGsmSeptetsWithEmail == null) {
            countGsmSeptetsWithEmail = new GsmAlphabet.TextEncodingDetails();
            int i3 = i2 * 2;
            int i4 = i3 > 0 ? 139 - i3 : 140;
            int i5 = i3 > 0 ? 133 - i3 : 134;
            int length = charSequence.length() * 2;
            countGsmSeptetsWithEmail.codeUnitCount = charSequence.length();
            if (length > i4) {
                if (i3 > i4 - 2) {
                    countGsmSeptetsWithEmail.msgCount = 1000;
                    countGsmSeptetsWithEmail.codeUnitsRemaining = -1;
                } else {
                    int i6 = length % i5;
                    if (i6 != 0) {
                        countGsmSeptetsWithEmail.msgCount = (length / i5) + 1;
                        countGsmSeptetsWithEmail.codeUnitsRemaining = (i5 - i6) / 2;
                    } else {
                        countGsmSeptetsWithEmail.msgCount = length / i5;
                        countGsmSeptetsWithEmail.codeUnitsRemaining = 0;
                    }
                }
            } else if (i3 >= i5 - 2) {
                countGsmSeptetsWithEmail.msgCount = 1000;
                countGsmSeptetsWithEmail.codeUnitsRemaining = -1;
            } else {
                countGsmSeptetsWithEmail.msgCount = 1;
                countGsmSeptetsWithEmail.codeUnitsRemaining = (i4 - length) / 2;
            }
            countGsmSeptetsWithEmail.codeUnitSize = 3;
        }
        return countGsmSeptetsWithEmail;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void extractPaginationForGsm() {
        /*
            Method dump skipped, instructions count: 590
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.extractPaginationForGsm():void");
    }
}
