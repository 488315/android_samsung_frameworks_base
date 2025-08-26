package com.android.internal.telephony.gsm;

import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Iterator;
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
        byte[] bArrEncodeUCS2;
        if (str3 == null || str2 == null) {
            return null;
        }
        byte[] byteArray = SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SEGMENTED_SMS) ? null : bArr;
        if (i == 0) {
            GsmAlphabet.TextEncodingDetails textEncodingDetailsCalculateLength = calculateLength(str3, false);
            i6 = textEncodingDetailsCalculateLength.codeUnitSize;
            i7 = textEncodingDetailsCalculateLength.languageTable;
            i8 = textEncodingDetailsCalculateLength.languageShiftTable;
            if (i6 == 1 && (i7 != 0 || i8 != 0)) {
                if (byteArray != null) {
                    SmsHeader smsHeaderFromByteArray = SmsHeader.fromByteArray(byteArray);
                    if (smsHeaderFromByteArray.languageTable != i7 || smsHeaderFromByteArray.languageShiftTable != i8) {
                        Rlog.w(LOG_TAG, "Updating language table in SMS header: " + smsHeaderFromByteArray.languageTable + " -> " + i7 + ", " + smsHeaderFromByteArray.languageShiftTable + " -> " + i8);
                        smsHeaderFromByteArray.languageTable = i7;
                        smsHeaderFromByteArray.languageShiftTable = i8;
                        byteArray = SmsHeader.toByteArray(smsHeaderFromByteArray);
                    }
                } else {
                    SmsHeader smsHeader = new SmsHeader();
                    smsHeader.languageTable = i7;
                    smsHeader.languageShiftTable = i8;
                    byteArray = SmsHeader.toByteArray(smsHeader);
                }
            }
        } else {
            i6 = i;
            i7 = i2;
            i8 = i3;
        }
        byte[] bArr2 = byteArray;
        SubmitPdu submitPdu = new SubmitPdu();
        int relativeValidityPeriod = getRelativeValidityPeriod(i4);
        byte b = bArr2 != null ? (byte) 65 : (byte) 1;
        if (relativeValidityPeriod != -1) {
            b = (byte) (b | 16);
        }
        ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, b, z, submitPdu, i5);
        if (submitPduHead == null) {
            return submitPdu;
        }
        try {
            if (i6 == 1) {
                bArrEncodeUCS2 = GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr2, i7, i8);
            } else {
                try {
                    bArrEncodeUCS2 = encodeUCS2(str3, bArr2);
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
                bArrEncodeUCS2 = encodeUCS2(str3, bArr2);
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
            if ((bArrEncodeUCS2[0] & 255) > 160) {
                Rlog.e(LOG_TAG, "Message too long (" + (bArrEncodeUCS2[0] & 255) + " septets)");
                return null;
            }
            submitPduHead.write(0);
        } else {
            if ((bArrEncodeUCS2[0] & 255) > 140) {
                Rlog.e(LOG_TAG, "Message too long (" + (bArrEncodeUCS2[0] & 255) + " bytes)");
                return null;
            }
            submitPduHead.write(8);
        }
        if (relativeValidityPeriod != -1) {
            submitPduHead.write(relativeValidityPeriod);
        }
        submitPduHead.write(bArrEncodeUCS2, 0, bArrEncodeUCS2.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    private static byte[] encodeUCS2(String str, byte[] bArr) throws EncodeException, UnsupportedEncodingException {
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
        byte[] bArrNetworkPortionToCalledPartyBCD;
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
            bArrNetworkPortionToCalledPartyBCD = PhoneNumberUtils.docomoNetworkPortionToCalledPartyBCD(str2);
        } else {
            bArrNetworkPortionToCalledPartyBCD = PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        }
        if (bArrNetworkPortionToCalledPartyBCD == null) {
            Rlog.e(LOG_TAG, "daBytes is null");
            return null;
        }
        byteArrayOutputStream.write(((bArrNetworkPortionToCalledPartyBCD.length - 1) * 2) - ((bArrNetworkPortionToCalledPartyBCD[bArrNetworkPortionToCalledPartyBCD.length - 1] & 240) != 240 ? 0 : 1));
        byteArrayOutputStream.write(bArrNetworkPortionToCalledPartyBCD, 0, bArrNetworkPortionToCalledPartyBCD.length);
        byteArrayOutputStream.write(0);
        return byteArrayOutputStream;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00bc, code lost:
    
        r10.write(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bf, code lost:
    
        if (r5 != 1) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c1, code lost:
    
        r0 = com.android.internal.telephony.GsmAlphabet.stringToGsm7BitPackedWithHeader(r21, r8, r6, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c6, code lost:
    
        r0 = encodeUCS2(r21, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00cd, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ce, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Implausible UnsupportedEncodingException ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d1, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00d8, code lost:
    
        if (r0.getError() == 1) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00da, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Exceed size limitation EncodeException", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00dd, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00de, code lost:
    
        r0 = encodeUCS2(r21, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e2, code lost:
    
        r5 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00e7, code lost:
    
        if (r5 != 1) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ef, code lost:
    
        if ((r0[0] & 255) <= 160) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00f1, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Message too long (" + (r0[0] & 255) + " septets)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0109, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x010a, code lost:
    
        r10.write(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0114, code lost:
    
        if ((r0[0] & 255) <= 140) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0116, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Message too long (" + (r0[0] & 255) + " bytes)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x012e, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x012f, code lost:
    
        r10.write(8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0132, code lost:
    
        r5 = new byte[7];
        r6 = java.time.Instant.ofEpochMilli(r22).atZone(java.time.ZoneId.systemDefault());
        r8 = r6.toLocalDateTime();
        r6 = (r6.getOffset().getTotalSeconds() / 60) / 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0151, code lost:
    
        if (r6 >= 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0153, code lost:
    
        r11 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0155, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0156, code lost:
    
        if (r11 == false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0158, code lost:
    
        r6 = -r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0159, code lost:
    
        r12 = r8.getYear();
        r13 = r8.getMonthValue();
        r15 = r8.getDayOfMonth();
        r16 = r8.getHour();
        r17 = r8.getMinute();
        r8 = r8.getSecond();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0175, code lost:
    
        if (r12 <= 2000) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0177, code lost:
    
        r12 = r12 - 2000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0179, code lost:
    
        r12 = r12 - 1900;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x017b, code lost:
    
        r5[0] = (byte) ((((r12 % 10) & 15) << 4) | ((r12 / 10) & 15));
        r5[1] = (byte) ((((r13 % 10) & 15) << 4) | ((r13 / 10) & 15));
        r5[2] = (byte) ((((r15 % 10) & 15) << 4) | ((r15 / 10) & 15));
        r5[3] = (byte) ((((r16 % 10) & 15) << 4) | ((r16 / 10) & 15));
        r5[4] = (byte) ((((r17 % 10) & 15) << 4) | ((r17 / 10) & 15));
        r5[5] = (byte) ((((r8 % 10) & 15) << 4) | ((r8 / 10) & 15));
        r2 = (byte) ((((r6 % 10) & 15) << 4) | ((r6 / 10) & 15));
        r5[6] = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01e1, code lost:
    
        if (r11 == false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01e3, code lost:
    
        r5[6] = (byte) (r2 | 8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01e7, code lost:
    
        r10.write(r5, 0, 7);
        r10.write(r0, 0, r0.length);
        r9.encodedMessage = r10.toByteArray();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01f4, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01f5, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01f6, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Implausible UnsupportedEncodingException ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01f9, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01fa, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01fb, code lost:
    
        com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Exceed size limitation EncodeException", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01fe, code lost:
    
        return r16;
     */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.time.LocalDateTime] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static SubmitPdu getDeliverPdu(String str, String str2, String str3, long j) {
        byte[] byteArray;
        SubmitPdu submitPdu;
        SubmitPdu submitPdu2 = null;
        if (str2 == null || str3 == null) {
            return null;
        }
        GsmAlphabet.TextEncodingDetails textEncodingDetailsCalculateLength = calculateLength(str3, false);
        int i = textEncodingDetailsCalculateLength.codeUnitSize;
        int i2 = textEncodingDetailsCalculateLength.languageTable;
        int i3 = textEncodingDetailsCalculateLength.languageShiftTable;
        if (i != 1 || (i2 == 0 && i3 == 0)) {
            byteArray = null;
        } else {
            SmsHeader smsHeader = new SmsHeader();
            smsHeader.languageTable = i2;
            smsHeader.languageShiftTable = i3;
            byteArray = SmsHeader.toByteArray(smsHeader);
        }
        SubmitPdu submitPdu3 = new SubmitPdu();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(180);
        if (str == null) {
            submitPdu3.encodedScAddress = null;
        } else {
            submitPdu3.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        }
        byteArrayOutputStream.write(0);
        int i4 = 0;
        while (true) {
            if (i4 < str2.length()) {
                submitPdu = submitPdu2;
                Rlog.d(LOG_TAG, "Address is " + str2.charAt(i4));
                if (!PhoneNumberUtils.isDialable(str2.charAt(i4))) {
                    Rlog.i(LOG_TAG, "Address is Alphabetic.");
                    try {
                        byte[] bArrStringToGsm7BitPacked = GsmAlphabet.stringToGsm7BitPacked(str2);
                        byteArrayOutputStream.write((bArrStringToGsm7BitPacked.length - 1) * 2);
                        byteArrayOutputStream.write(208);
                        byteArrayOutputStream.write(bArrStringToGsm7BitPacked, 1, bArrStringToGsm7BitPacked.length - 1);
                        break;
                    } catch (EncodeException e) {
                        Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
                        return submitPdu;
                    }
                }
                i4++;
                submitPdu2 = submitPdu;
            } else {
                submitPdu = submitPdu2;
                Rlog.i(LOG_TAG, "Address is Numeric.");
                byte[] bArrNetworkPortionToCalledPartyBCD = PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
                if (bArrNetworkPortionToCalledPartyBCD == null) {
                    return submitPdu;
                }
                byteArrayOutputStream.write(((bArrNetworkPortionToCalledPartyBCD.length - 1) * 2) - ((bArrNetworkPortionToCalledPartyBCD[bArrNetworkPortionToCalledPartyBCD.length - 1] & 240) == 240 ? 1 : 0));
                byteArrayOutputStream.write(bArrNetworkPortionToCalledPartyBCD, 0, bArrNetworkPortionToCalledPartyBCD.length);
            }
        }
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
            String strCalledPartyBCDToString = null;
            if (i != 0) {
                try {
                    strCalledPartyBCDToString = PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, i, 2);
                } catch (RuntimeException e) {
                    Rlog.d(SmsMessage.LOG_TAG, "invalid SC address: ", e);
                }
            }
            this.mCur += i;
            return strCalledPartyBCDToString;
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
            int iGsmBcdByteToInt = IccUtils.gsmBcdByteToInt(bArr[i]);
            byte[] bArr2 = this.mPdu;
            int i2 = this.mCur;
            this.mCur = i2 + 1;
            int iGsmBcdByteToInt2 = IccUtils.gsmBcdByteToInt(bArr2[i2]);
            byte[] bArr3 = this.mPdu;
            int i3 = this.mCur;
            this.mCur = i3 + 1;
            int iGsmBcdByteToInt3 = IccUtils.gsmBcdByteToInt(bArr3[i3]);
            byte[] bArr4 = this.mPdu;
            int i4 = this.mCur;
            this.mCur = i4 + 1;
            int iGsmBcdByteToInt4 = IccUtils.gsmBcdByteToInt(bArr4[i4]);
            byte[] bArr5 = this.mPdu;
            int i5 = this.mCur;
            this.mCur = i5 + 1;
            int iGsmBcdByteToInt5 = IccUtils.gsmBcdByteToInt(bArr5[i5]);
            byte[] bArr6 = this.mPdu;
            int i6 = this.mCur;
            this.mCur = i6 + 1;
            int iGsmBcdByteToInt6 = IccUtils.gsmBcdByteToInt(bArr6[i6]);
            byte[] bArr7 = this.mPdu;
            int i7 = this.mCur;
            this.mCur = i7 + 1;
            byte b = bArr7[i7];
            int iGsmBcdByteToInt7 = IccUtils.gsmBcdByteToInt((byte) (b & (-9)));
            if ((b & 8) != 0) {
                iGsmBcdByteToInt7 = -iGsmBcdByteToInt7;
            }
            Time time = new Time(Time.TIMEZONE_UTC);
            time.year = iGsmBcdByteToInt >= 90 ? iGsmBcdByteToInt + 1900 : iGsmBcdByteToInt + 2000;
            time.month = iGsmBcdByteToInt2 - 1;
            time.monthDay = iGsmBcdByteToInt3;
            time.hour = iGsmBcdByteToInt4;
            time.minute = iGsmBcdByteToInt5;
            time.second = iGsmBcdByteToInt6;
            return time.toMillis(true) - (iGsmBcdByteToInt7 * Build.VERSION_CODES_FULL.GINGERBREAD);
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0011  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int constructUserData(boolean z, boolean z2) {
            int i;
            int i2;
            int length;
            int i3 = this.mCur;
            int i4 = this.mValidityPeriodFormat;
            if (i4 == 1) {
                i3 = 7;
            } else if (i4 == 2) {
                i3++;
            } else if (i4 == 3) {
            }
            byte[] bArr = this.mPdu;
            int i5 = i3 + 1;
            int i6 = bArr[i3] & 255;
            if (z) {
                int i7 = i3 + 2;
                i2 = bArr[i5] & 255;
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i7, bArr2, 0, i2);
                this.mUserDataHeader = SmsHeader.semFromByteArray(this.mSubId, bArr2);
                i5 = i7 + i2;
                int i8 = (i2 + 1) * 8;
                i = (i8 / 7) + (i8 % 7 > 0 ? 1 : 0);
                this.mUserDataSeptetPadding = (i * 7) - i8;
            } else {
                i = 0;
                i2 = 0;
            }
            if (z2) {
                length = this.mPdu.length - i5;
            } else {
                length = i6 - (z ? i2 + 1 : 0);
                if (length < 0) {
                    length = 0;
                }
            }
            this.mUserData = new byte[length];
            if (!SmsMessage.mUnsupportedDatacodingScheme || z) {
                byte[] bArr3 = this.mPdu;
                byte[] bArr4 = this.mUserData;
                System.arraycopy(bArr3, i5, bArr4, 0, bArr4.length);
            } else {
                Rlog.e(SmsMessage.LOG_TAG, "array copy skip! if dataCodingScheme is unsupporting,\n encodingType is Unknown and messageBody is null");
            }
            this.mCur = i5;
            if (!z2) {
                return this.mUserData.length;
            }
            int i9 = i6 - i;
            if (i9 < 0) {
                return 0;
            }
            return i9;
        }

        byte[] getUserData() {
            return this.mUserData;
        }

        SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        String getUserDataGSM7Bit(int i, int i2, int i3) {
            String strGsm7BitPackedToString = GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, i, this.mUserDataSeptetPadding, i2, i3);
            this.mCur += (i * 7) / 8;
            return strGsm7BitPackedToString;
        }

        String getUserDataGSM8bit(int i) {
            String strGsm8BitUnpackedToString = GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, i);
            this.mCur += i;
            return strGsm8BitUnpackedToString;
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
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, this.mSubId).getSmsSetting(SmsConstants.SMS_NSRI_SECURITY_SOLUTION) && userData.length > 0 && Integer.toHexString(userData[0] & 255).equals("f1") && Integer.toHexString(userData[1] & 255).equals("a0")) {
                    Rlog.d(SmsMessage.LOG_TAG, "[NSRI_SMS] getUserDataKSC5601 KSC5601");
                    str = new String(this.mPdu, this.mCur, i, "ISO8859_1");
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
        String strTranslate = Resources.getSystem().getBoolean(R.bool.config_sms_force_7bit_encoding) ? Sms7BitEncodingTranslator.translate(charSequence, false) : null;
        if (!TextUtils.isEmpty(strTranslate)) {
            charSequence = strTranslate;
        }
        if (charSequence == null) {
            return null;
        }
        GsmAlphabet.TextEncodingDetails textEncodingDetailsCountGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, z, mIgnoreSpecialChar);
        mIgnoreSpecialChar = false;
        return textEncodingDetailsCountGsmSeptets == null ? SmsMessageBase.calcUnicodeEncodingDetails(charSequence) : textEncodingDetailsCountGsmSeptets;
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

    private void parsePdu(byte[] bArr) throws Resources.NotFoundException {
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

    private void parseSmsStatusReport(PduParser pduParser, int i) throws Resources.NotFoundException {
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

    private void parseSmsDeliver(PduParser pduParser, int i) throws Resources.NotFoundException {
        this.mReplyPathPresent = (i & 128) == 128;
        this.mOriginatingAddress = pduParser.getAddress();
        if (this.mOriginatingAddress != null && SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SPECIAL_ADDRESS_HANDLING_FOR) && this.mOriginatingAddress.address.startsWith("+00852")) {
            String strSubstring = this.mOriginatingAddress.address.substring(3);
            this.mOriginatingAddress.address = "+";
            StringBuilder sb = new StringBuilder();
            SmsAddress smsAddress = this.mOriginatingAddress;
            sb.append(smsAddress.address);
            sb.append(strSubstring);
            smsAddress.address = sb.toString();
        }
        SmsAddress smsAddress2 = this.mOriginatingAddress;
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        parseUserData(pduParser, (i & 64) == 64);
    }

    private void parseSmsSubmit(PduParser pduParser, int i) throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:112:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0422  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0459  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseUserData(PduParser pduParser, boolean z) throws Resources.NotFoundException {
        int i;
        int integer;
        boolean z2;
        Context context;
        boolean z3;
        int i2;
        Iterator<SmsHeader.SpecialSmsMsg> it;
        boolean z4;
        int i3;
        Resources system = Resources.getSystem();
        int i4 = this.mDataCodingScheme;
        boolean z5 = true;
        if ((i4 & 255) == 132) {
            z2 = false;
            i = 3;
        } else {
            if ((i4 & 128) == 0) {
                boolean z6 = (i4 & 32) != 0;
                z2 = (i4 & 16) != 0;
                if (z6) {
                    Rlog.w(LOG_TAG, "4 - Unsupported SMS data coding scheme (compression) " + (this.mDataCodingScheme & 255));
                    mUnsupportedDatacodingScheme = true;
                } else {
                    int i5 = (i4 >> 2) & 3;
                    if (i5 == 0) {
                        mUnsupportedDatacodingScheme = false;
                        integer = 1;
                    } else {
                        if (i5 != 1) {
                            if (i5 == 2) {
                                mUnsupportedDatacodingScheme = false;
                                integer = 3;
                            } else if (i5 == 3) {
                                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601)) {
                                    integer = 4;
                                } else {
                                    Rlog.w(LOG_TAG, "1 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                                    integer = system.getInteger(R.integer.default_reserved_data_coding_scheme);
                                }
                                mUnsupportedDatacodingScheme = false;
                            }
                        } else if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_KSC5601) && (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_GSM_8BIT_SMS) || system.getBoolean(R.bool.config_sms_decode_gsm_8bit_data))) {
                            integer = 2;
                        }
                        int iConstructUserData = pduParser.constructUserData(z, integer != 1);
                        this.mUserData = pduParser.getUserData();
                        this.mUserDataHeader = pduParser.getUserDataHeader();
                        this.mReceivedEncodingType = integer;
                        if (z && this.mUserDataHeader.specialSmsMsgList.size() != 0) {
                            it = this.mUserDataHeader.specialSmsMsgList.iterator();
                            while (it.hasNext()) {
                                SmsHeader.SpecialSmsMsg next = it.next();
                                int i6 = next.msgIndType & 255;
                                if (i6 == 0 || i6 == 128) {
                                    this.mIsMwi = z5;
                                    if (i6 == 128) {
                                        this.mMwiDontStore = false;
                                    } else {
                                        if (!this.mMwiDontStore) {
                                            int i7 = this.mDataCodingScheme;
                                            if ((i7 & 240) == 208 || (i7 & 240) == 224) {
                                                if ((i7 & 3) != 0) {
                                                    z4 = true;
                                                    this.mMwiDontStore = true;
                                                } else {
                                                    z4 = true;
                                                }
                                            }
                                        }
                                        i3 = next.msgCount & 255;
                                        this.mVoiceMailCount = i3;
                                        if (i3 <= 0) {
                                            this.mMwiSense = z4;
                                        } else {
                                            this.mMwiSense = false;
                                        }
                                        Rlog.w(LOG_TAG, "MWI in TP-UDH for Vmail. Msg Ind = " + i6 + " Dont store = " + this.mMwiDontStore + " Vmail count = " + this.mVoiceMailCount);
                                    }
                                    z4 = z5;
                                    i3 = next.msgCount & 255;
                                    this.mVoiceMailCount = i3;
                                    if (i3 <= 0) {
                                    }
                                    Rlog.w(LOG_TAG, "MWI in TP-UDH for Vmail. Msg Ind = " + i6 + " Dont store = " + this.mMwiDontStore + " Vmail count = " + this.mVoiceMailCount);
                                } else {
                                    Rlog.w(LOG_TAG, "TP_UDH fax/email/extended msg/multisubscriber profile. Msg Ind = " + i6);
                                }
                                z5 = true;
                            }
                        }
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
                            if (this.mUserDataHeader != null) {
                                Iterator<SmsHeader.MiscElt> it2 = this.mUserDataHeader.miscEltList.iterator();
                                z3 = false;
                                while (it2.hasNext()) {
                                    SmsHeader.MiscElt next2 = it2.next();
                                    if (next2.id == 34 && this.mMti == 0) {
                                        if (next2.data.length > 2) {
                                            try {
                                                this.replyAddress = new GsmSmsAddress(next2.data, 0, next2.data.length);
                                            } catch (Exception unused) {
                                                Rlog.w(LOG_TAG, "GsmSmsAddress FAIL!");
                                            }
                                        } else {
                                            this.replyAddress = null;
                                            Rlog.w(LOG_TAG, "SMS replyAddress: null");
                                        }
                                        z3 = true;
                                    }
                                }
                            } else {
                                z3 = false;
                            }
                            if (!z3) {
                                this.replyAddress = this.mOriginatingAddress;
                                Rlog.d(LOG_TAG, "hasReplayAddress = false, SMS replayAddress = mOriginatingAddress");
                            }
                            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("SKT") && this.replyAddress != null && this.replyAddress.address.length() > (i2 = i)) {
                                if (this.replyAddress.ton == 1 && "+82".equals(this.replyAddress.address.substring(0, i2))) {
                                    this.replyAddress.address = this.replyAddress.address.replaceFirst("\\+82", "0");
                                }
                                if (this.replyAddress.ton == 1 && "82".equals(this.replyAddress.address.substring(0, 2))) {
                                    this.replyAddress.address = this.replyAddress.address.replaceFirst("82", "0");
                                }
                            }
                        }
                        if (integer == 0) {
                            if (integer != 1) {
                                if (integer == 2) {
                                    if (!system.getBoolean(R.bool.config_sms_decode_gsm_8bit_data)) {
                                        context = null;
                                        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_GSM_8BIT_SMS)) {
                                            this.mMessageBody = null;
                                        }
                                    }
                                    this.mMessageBody = pduParser.getUserDataGSM8bit(iConstructUserData);
                                } else if (integer == 3) {
                                    byte[] userData = pduParser.getUserData();
                                    int length = userData.length;
                                    if (length > 0) {
                                        int i8 = length - 2;
                                        int i9 = length - 1;
                                        char c = (char) (((char) ((userData[i8] & 255) << 8)) | ((char) (userData[i9] & 255)));
                                        if (c == 55357 || c == 55356 || c == 55358) {
                                            Rlog.d(LOG_TAG, "found emoji");
                                            this.mlastByte = new byte[2];
                                            this.mlastByte[0] = userData[i8];
                                            this.mlastByte[1] = userData[i9];
                                            this.mIsfourBytesUnicode = true;
                                        }
                                    }
                                    this.mMessageBody = pduParser.getUserDataUCS2(iConstructUserData);
                                } else if (integer == 4) {
                                    this.mMessageBody = pduParser.getUserDataKSC5601(iConstructUserData);
                                }
                            } else if (this.mUserDataHeader != null) {
                                this.mMessageBody = pduParser.getUserDataGSM7Bit(iConstructUserData, z ? this.mUserDataHeader.languageTable : 0, z ? this.mUserDataHeader.languageShiftTable : 0);
                            } else {
                                this.mMessageBody = pduParser.getUserDataGSM7Bit(iConstructUserData, 0, 0);
                            }
                            context = null;
                        } else {
                            context = null;
                            this.mMessageBody = null;
                        }
                        if (SmsManager.getSmsManagerForContextAndSubscriptionId(context, getSubId()).getMnoName().toUpperCase().contains("LGU") && this.mUserDataHeader != null && this.mUserDataHeader.portAddrs != null && this.mUserDataHeader.portAddrs.destPort == 49162) {
                            parseSpecificTid(this.mUserDataHeader.portAddrs.destPort);
                        }
                        if (this.mMessageBody != null) {
                            if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getMnoName().toUpperCase().contains("DOCOMO")) {
                                this.mMessageBody = this.mMessageBody.replace("\r\n", ShaderAssembler.NEWLINE).replace('\r', '\n');
                            }
                            parseMessageBody();
                        }
                        if (z2) {
                            this.messageClass = SmsConstants.MessageClass.UNKNOWN;
                            return;
                        }
                        int i10 = this.mDataCodingScheme & 3;
                        if (i10 == 0) {
                            this.messageClass = SmsConstants.MessageClass.CLASS_0;
                            return;
                        }
                        if (i10 == 1) {
                            this.messageClass = SmsConstants.MessageClass.CLASS_1;
                            return;
                        } else if (i10 == 2) {
                            this.messageClass = SmsConstants.MessageClass.CLASS_2;
                            return;
                        } else {
                            if (i10 != 3) {
                                return;
                            }
                            this.messageClass = SmsConstants.MessageClass.CLASS_3;
                            return;
                        }
                    }
                }
                integer = 0;
            } else {
                if ((i4 & 240) == 240) {
                    mUnsupportedDatacodingScheme = false;
                    if ((i4 & 4) == 0) {
                        integer = 1;
                        z2 = true;
                    } else {
                        z2 = true;
                        i = 3;
                        integer = 2;
                    }
                } else {
                    if ((i4 & 240) != 192) {
                        i = 3;
                        if ((i4 & 240) != 208 && (i4 & 240) != 224) {
                            if ((i4 & 192) != 128) {
                                Rlog.w(LOG_TAG, "3 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                                mUnsupportedDatacodingScheme = true;
                            } else if (((i4 >> 2) & 3) == 1) {
                                mUnsupportedDatacodingScheme = false;
                                z2 = false;
                            } else {
                                mUnsupportedDatacodingScheme = true;
                                Rlog.w(LOG_TAG, "5 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                            }
                            integer = 0;
                            z2 = false;
                        }
                    } else {
                        i = 3;
                    }
                    mUnsupportedDatacodingScheme = false;
                    int i11 = (i4 & 240) == 224 ? i : 1;
                    boolean z7 = (i4 & 8) == 8;
                    if ((i4 & 3) == 0) {
                        this.mIsMwi = true;
                        this.mMwiSense = z7;
                        this.mMwiDontStore = (this.mDataCodingScheme & 240) == 192;
                        if (z7) {
                            this.mVoiceMailCount = -1;
                        } else {
                            this.mVoiceMailCount = 0;
                        }
                        Rlog.w(LOG_TAG, "MWI in DCS for Vmail. DCS = " + (this.mDataCodingScheme & 255) + " Dont store = " + this.mMwiDontStore + " vmail count = " + this.mVoiceMailCount);
                    } else {
                        this.mIsMwi = false;
                        Rlog.w(LOG_TAG, "MWI in DCS for fax/email/other: " + (this.mDataCodingScheme & 255));
                    }
                    integer = i11;
                    z2 = false;
                }
                int iConstructUserData2 = pduParser.constructUserData(z, integer != 1);
                this.mUserData = pduParser.getUserData();
                this.mUserDataHeader = pduParser.getUserDataHeader();
                this.mReceivedEncodingType = integer;
                if (z) {
                    it = this.mUserDataHeader.specialSmsMsgList.iterator();
                    while (it.hasNext()) {
                    }
                }
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
                }
                if (integer == 0) {
                }
                if (SmsManager.getSmsManagerForContextAndSubscriptionId(context, getSubId()).getMnoName().toUpperCase().contains("LGU")) {
                    parseSpecificTid(this.mUserDataHeader.portAddrs.destPort);
                }
                if (this.mMessageBody != null) {
                }
                if (z2) {
                }
            }
            i = 3;
            int iConstructUserData22 = pduParser.constructUserData(z, integer != 1);
            this.mUserData = pduParser.getUserData();
            this.mUserDataHeader = pduParser.getUserDataHeader();
            this.mReceivedEncodingType = integer;
            if (z) {
            }
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            }
            if (integer == 0) {
            }
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(context, getSubId()).getMnoName().toUpperCase().contains("LGU")) {
            }
            if (this.mMessageBody != null) {
            }
            if (z2) {
            }
        }
        integer = 4;
        int iConstructUserData222 = pduParser.constructUserData(z, integer != 1);
        this.mUserData = pduParser.getUserData();
        this.mUserDataHeader = pduParser.getUserDataHeader();
        this.mReceivedEncodingType = integer;
        if (z) {
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, getSubId()).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
        }
        if (integer == 0) {
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(context, getSubId()).getMnoName().toUpperCase().contains("LGU")) {
        }
        if (this.mMessageBody != null) {
        }
        if (z2) {
        }
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
        byte[] bArrStringToGsm8BitPackedForAutoLogin = GsmAlphabet.stringToGsm8BitPackedForAutoLogin(str3);
        if (bArrStringToGsm8BitPackedForAutoLogin == null) {
            return null;
        }
        if ((bArrStringToGsm8BitPackedForAutoLogin[0] & 255) > 140) {
            Rlog.e(LOG_TAG, "Message too long (" + (bArrStringToGsm8BitPackedForAutoLogin[0] & 255) + " bytes)");
            return null;
        }
        submitPduHead.write(4);
        if (i2 == 2) {
            submitPduHead.write(relativeValidityPeriod);
        }
        submitPduHead.write(bArrStringToGsm8BitPackedForAutoLogin, 0, bArrStringToGsm8BitPackedForAutoLogin.length);
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

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, byte[] bArr, boolean z2, int i2, int i3, int i4, int i5, int i6) throws EncodeException, UnsupportedEncodingException {
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
            byte[] bArrStringToGsm7BitPackedWithHeader = GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr, i5, i6);
            if ((bArrStringToGsm7BitPackedWithHeader[0] & 255) > 160) {
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
            submitPduHead.write(bArrStringToGsm7BitPackedWithHeader, 0, bArrStringToGsm7BitPackedWithHeader.length);
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
        byte[] bArrNetworkPortionToCalledPartyBCD = PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        if (bArrNetworkPortionToCalledPartyBCD == null) {
            byteArrayOutputStream.write(0);
        } else {
            byteArrayOutputStream.write(((bArrNetworkPortionToCalledPartyBCD.length - 1) * 2) - ((bArrNetworkPortionToCalledPartyBCD[bArrNetworkPortionToCalledPartyBCD.length - 1] & 240) != 240 ? 0 : 1));
            byteArrayOutputStream.write(bArrNetworkPortionToCalledPartyBCD, 0, bArrNetworkPortionToCalledPartyBCD.length);
            byteArrayOutputStream.write(127);
        }
        try {
            byte[] bArrStringToGsm7BitPacked = GsmAlphabet.stringToGsm7BitPacked(str3);
            if ((bArrStringToGsm7BitPacked[0] & 255) > 160) {
                return null;
            }
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(bArrStringToGsm7BitPacked, 0, bArrStringToGsm7BitPacked.length);
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
        GsmAlphabet.TextEncodingDetails textEncodingDetailsCountGsmSeptets;
        new GsmAlphabet.TextEncodingDetails();
        if (i == 1) {
            textEncodingDetailsCountGsmSeptets = null;
        } else if (i == 0) {
            textEncodingDetailsCountGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, true);
        } else {
            textEncodingDetailsCountGsmSeptets = GsmAlphabet.countGsmSeptets(charSequence, z);
        }
        return textEncodingDetailsCountGsmSeptets == null ? SmsMessageBase.calcUnicodeEncodingDetails(charSequence) : textEncodingDetailsCountGsmSeptets;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence charSequence, boolean z, int i) {
        GsmAlphabet.TextEncodingDetails textEncodingDetailsCountGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, z, i);
        if (textEncodingDetailsCountGsmSeptetsWithEmail == null) {
            textEncodingDetailsCountGsmSeptetsWithEmail = new GsmAlphabet.TextEncodingDetails();
            int i2 = i * 2;
            int i3 = i2 > 0 ? 139 - i2 : 140;
            int i4 = i2 > 0 ? 133 - i2 : 134;
            int length = charSequence.length() * 2;
            textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitCount = charSequence.length();
            if (length > i3) {
                if (i2 > i3 - 2) {
                    textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1000;
                    textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = -1;
                } else {
                    int i5 = length % i4;
                    if (i5 != 0) {
                        textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = (length / i4) + 1;
                        textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = (i4 - i5) / 2;
                    } else {
                        textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = length / i4;
                        textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = 0;
                    }
                }
            } else if (i2 >= i4 - 2) {
                textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1000;
                textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = -1;
            } else {
                textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1;
                textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = (i3 - length) / 2;
            }
            textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitSize = 3;
        }
        return textEncodingDetailsCountGsmSeptetsWithEmail;
    }

    public static GsmAlphabet.TextEncodingDetails calculateLengthWithEmail(CharSequence charSequence, boolean z, int i, int i2) {
        GsmAlphabet.TextEncodingDetails textEncodingDetailsCountGsmSeptetsWithEmail;
        new GsmAlphabet.TextEncodingDetails();
        if (i == 1) {
            textEncodingDetailsCountGsmSeptetsWithEmail = null;
        } else if (i == 0) {
            textEncodingDetailsCountGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, true, i2);
        } else {
            textEncodingDetailsCountGsmSeptetsWithEmail = GsmAlphabet.countGsmSeptetsWithEmail(charSequence, z, i2);
        }
        if (textEncodingDetailsCountGsmSeptetsWithEmail == null) {
            textEncodingDetailsCountGsmSeptetsWithEmail = new GsmAlphabet.TextEncodingDetails();
            int i3 = i2 * 2;
            int i4 = i3 > 0 ? 139 - i3 : 140;
            int i5 = i3 > 0 ? 133 - i3 : 134;
            int length = charSequence.length() * 2;
            textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitCount = charSequence.length();
            if (length > i4) {
                if (i3 > i4 - 2) {
                    textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1000;
                    textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = -1;
                } else {
                    int i6 = length % i5;
                    if (i6 != 0) {
                        textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = (length / i5) + 1;
                        textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = (i5 - i6) / 2;
                    } else {
                        textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = length / i5;
                        textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = 0;
                    }
                }
            } else if (i3 >= i5 - 2) {
                textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1000;
                textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = -1;
            } else {
                textEncodingDetailsCountGsmSeptetsWithEmail.msgCount = 1;
                textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitsRemaining = (i4 - length) / 2;
            }
            textEncodingDetailsCountGsmSeptetsWithEmail.codeUnitSize = 3;
        }
        return textEncodingDetailsCountGsmSeptetsWithEmail;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void extractPaginationForGsm() throws NumberFormatException {
        String strSubstring;
        boolean z;
        int i;
        int numericValue;
        int i2;
        int numericValue2;
        int numericValue3;
        int i3;
        String displayMessageBody = getDisplayMessageBody();
        if (displayMessageBody == null) {
            Rlog.d(LOG_TAG, "there is no message body");
            return;
        }
        try {
            boolean z2 = true;
            if (displayMessageBody.startsWith(NavigationBarInflaterView.KEY_CODE_START) && displayMessageBody.contains(NavigationBarInflaterView.KEY_CODE_END)) {
                strSubstring = displayMessageBody.substring(displayMessageBody.indexOf(40) + 1, displayMessageBody.indexOf(41));
                displayMessageBody = displayMessageBody.substring(displayMessageBody.indexOf(41) + 2);
            } else if (displayMessageBody.startsWith(NavigationBarInflaterView.SIZE_MOD_START) && displayMessageBody.contains(NavigationBarInflaterView.SIZE_MOD_END)) {
                strSubstring = displayMessageBody.substring(displayMessageBody.indexOf(91) + 1, displayMessageBody.indexOf(93));
                displayMessageBody = displayMessageBody.substring(displayMessageBody.indexOf(93) + 2);
            } else if (!displayMessageBody.startsWith("{") || !displayMessageBody.contains("}")) {
                Rlog.d(LOG_TAG, "there is no pagination pattern maybe / or of ");
                strSubstring = null;
            } else {
                strSubstring = displayMessageBody.substring(displayMessageBody.indexOf(123) + 1, displayMessageBody.indexOf(125));
                displayMessageBody = displayMessageBody.substring(displayMessageBody.indexOf(125) + 2);
            }
            int i4 = 0;
            if (strSubstring != null) {
                String[] strArrSplit = strSubstring.split("/");
                if (strArrSplit.length == 2) {
                    try {
                        i3 = Integer.parseInt(strArrSplit[0].trim());
                    } catch (NumberFormatException unused) {
                        i3 = 0;
                    }
                    try {
                        i2 = Integer.parseInt(strArrSplit[1].trim());
                    } catch (NumberFormatException unused2) {
                        Rlog.d(LOG_TAG, "there is no pagination yet");
                        i2 = 0;
                        z2 = false;
                        i4 = i3;
                        if (!z2) {
                        }
                    }
                    i4 = i3;
                } else {
                    i2 = 0;
                    z2 = false;
                }
            } else if (displayMessageBody.split(" of ").length >= 2) {
                String[] strArrSplit2 = displayMessageBody.split(" ");
                if (strArrSplit2.length >= 3) {
                    try {
                        i = Integer.parseInt(strArrSplit2[0].trim());
                        try {
                            numericValue = Integer.parseInt(strArrSplit2[2].trim());
                            try {
                                displayMessageBody.substring(strArrSplit2[0].length() + strArrSplit2[2].length() + 5);
                                z = true;
                            } catch (NumberFormatException unused3) {
                                Rlog.d(LOG_TAG, "there is no pagination yet");
                                z = false;
                                if (!z) {
                                }
                                if (!z2) {
                                }
                            } catch (StringIndexOutOfBoundsException e) {
                                e = e;
                                Rlog.e(LOG_TAG, "extractPagination : " + e);
                                z = false;
                                if (!z) {
                                }
                                if (!z2) {
                                }
                            }
                        } catch (NumberFormatException unused4) {
                            numericValue = 0;
                        } catch (StringIndexOutOfBoundsException e2) {
                            e = e2;
                            numericValue = 0;
                        }
                    } catch (NumberFormatException unused5) {
                        i = 0;
                        numericValue = 0;
                    } catch (StringIndexOutOfBoundsException e3) {
                        e = e3;
                        i = 0;
                        numericValue = 0;
                    }
                } else {
                    z = false;
                    i = 0;
                    numericValue = 0;
                }
                if (!z) {
                    String[] strArrSplit3 = displayMessageBody.split("/");
                    if (strArrSplit3.length >= 2) {
                        char[] charArray = strArrSplit3[1].toCharArray();
                        try {
                            try {
                                i = Integer.parseInt(strArrSplit3[0].trim());
                                numericValue = 0;
                                int i5 = 0;
                                while (Character.isDigit(charArray[i5])) {
                                    try {
                                        if (i5 == 0) {
                                            numericValue = Character.getNumericValue(charArray[i5]);
                                        } else {
                                            if (i5 == 1) {
                                                numericValue2 = Character.getNumericValue(charArray[0]) * 10;
                                                numericValue3 = Character.getNumericValue(charArray[1]);
                                            } else if (i5 == 2) {
                                                numericValue2 = (Character.getNumericValue(charArray[0]) * 100) + (Character.getNumericValue(charArray[1]) * 10);
                                                numericValue3 = Character.getNumericValue(charArray[2]);
                                            } else if (i5 == 3) {
                                                numericValue2 = (Character.getNumericValue(charArray[0]) * 1000) + (Character.getNumericValue(charArray[1]) * 100) + (Character.getNumericValue(charArray[2]) * 10);
                                                numericValue3 = Character.getNumericValue(charArray[3]);
                                            }
                                            numericValue = numericValue3 + numericValue2;
                                        }
                                        i5++;
                                    } catch (NumberFormatException unused6) {
                                        i4 = i;
                                        Rlog.d(LOG_TAG, "there is no pagination");
                                        z2 = z;
                                        i2 = numericValue;
                                        if (!z2) {
                                        }
                                    }
                                }
                                displayMessageBody.substring(strArrSplit3[0].length() + i5 + 1);
                            } catch (ArrayIndexOutOfBoundsException e4) {
                                Rlog.e(LOG_TAG, "extractPagination : " + e4);
                                return;
                            }
                        } catch (NumberFormatException unused7) {
                            numericValue = 0;
                        }
                    } else {
                        z2 = z;
                    }
                    i4 = i;
                    i2 = numericValue;
                }
            }
            if (!z2) {
                Rlog.d(LOG_TAG, "No pagination found");
                return;
            }
            Rlog.d(LOG_TAG, "segmented number: " + i4);
            Rlog.d(LOG_TAG, "total number: " + i2);
            if ((i4 < 0 && i4 > 9999) || (i2 < 0 && i4 > 9999)) {
                Rlog.d(LOG_TAG, "Its not segmented sms. ");
                return;
            }
            if (i4 == 0 || i2 == 0 || i4 > i2 || i2 > 9999) {
                Rlog.d(LOG_TAG, "It's not segmented sms.");
                return;
            }
            Rlog.d(LOG_TAG, "It's segmented sms");
            SmsHeader.ConcatRef concatRef = new SmsHeader.ConcatRef();
            concatRef.seqNumber = i4;
            concatRef.msgCount = i2;
            concatRef.refNumber = -1;
            this.mUserDataHeader = new SmsHeader();
            this.mUserDataHeader.concatRef = concatRef;
        } catch (StringIndexOutOfBoundsException e5) {
            Rlog.e(LOG_TAG, "extractPagination : " + e5);
        }
    }
}
