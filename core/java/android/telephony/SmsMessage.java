package android.telephony;

import android.annotation.SystemApi;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Binder;
import android.text.TextUtils;
import com.android.internal.C4337R;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.sms.UserData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class SmsMessage {
    public static final int ENCODING_16BIT = 3;
    public static final int ENCODING_7BIT = 1;
    public static final int ENCODING_8BIT = 2;
    public static final int ENCODING_EUC_KR = 4;
    public static final int ENCODING_KSC5601 = 4;
    public static final int ENCODING_UNKNOWN = 0;
    public static final String FORMAT_3GPP = "3gpp";
    public static final String FORMAT_3GPP2 = "3gpp2";
    private static final String LOG_TAG = "SmsMessage";
    public static final int MAX_DATA_LEN_WITH_SEGMENT_SEPERATOR = 154;
    public static final int MAX_USER_DATA_BYTES = 140;
    public static final int MAX_USER_DATA_BYTES_WITH_HEADER = 134;
    private static final int MAX_USER_DATA_BYTES_WITH_HEADER_SINGLE_LOCKING_SHIFT = 128;
    private static final int MAX_USER_DATA_BYTES_WITH_HEADER_SINGLE_SHIFT = 131;
    public static final int MAX_USER_DATA_BYTES_WITH_SEGMENT_SEPERATOR = 128;
    public static final int MAX_USER_DATA_SEPTETS = 160;
    public static final int MAX_USER_DATA_SEPTETS_WITH_HEADER = 153;
    private static final int MAX_USER_DATA_SEPTETS_WITH_HEADER_NATIONAL_LANGUAGE = 149;

    /* renamed from: MAX_USER_DATA_SEPTETS_WITH_HEADER_NATIONAL_LOCKING_SHIFT_LANGUAGE */
    private static final int f517x34bbd584 = 147;
    private static final int PHONE_TYPE_CDMA = 2;
    private static final int PHONE_TYPE_GSM = 1;
    public static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE_FORMAT = 3;
    public static final int VALIDITY_PERIOD_FORMAT_ENHANCED_FORMAT = 1;
    public static final int VALIDITY_PERIOD_FORMAT_NOT_PRESENT = 0;
    public static final int VALIDITY_PERIOD_FORMAT_RELATIVE_FORMAT = 2;
    private int mSubId = 0;
    public SmsMessageBase mWrappedSmsMessage;
    private static NoEmsSupportConfig[] mNoEmsSupportConfigList = null;
    private static boolean mIsNoEmsSupportConfigListLoaded = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncodingSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Format {
    }

    public enum MessageClass {
        UNKNOWN,
        CLASS_0,
        CLASS_1,
        CLASS_2,
        CLASS_3
    }

    public void setSubId(int subId) {
        this.mSubId = subId;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public static class SubmitPdu {
        public byte[] encodedMessage;
        public byte[] encodedScAddress;

        public String toString() {
            return "SubmitPdu: encodedScAddress = " + Arrays.toString(this.encodedScAddress) + ", encodedMessage = " + Arrays.toString(this.encodedMessage);
        }

        protected SubmitPdu(SmsMessageBase.SubmitPduBase spb) {
            this.encodedMessage = spb.encodedMessage;
            this.encodedScAddress = spb.encodedScAddress;
        }
    }

    public SmsMessage(SmsMessageBase smb) {
        this.mWrappedSmsMessage = smb;
    }

    @Deprecated
    public static SmsMessage createFromPdu(byte[] pdu) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), pdu);
    }

    public static SmsMessage createFromPdu(byte[] pdu, String format) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), pdu, format, true);
    }

    private static SmsMessage createFromPdu(byte[] pdu, String format, boolean fallbackToOtherFormat) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), pdu, format, fallbackToOtherFormat);
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data) {
        return createFromEfRecord(index, data, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data, int subId) {
        SmsMessageBase wrappedMessage;
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(index, data);
        } else if (isCdmaVoice(subId)) {
            wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(index, data);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "createfromeEFrecord is failed >> retry to use gsm-decode ");
                wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(index, data);
            }
        } else {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(index, data);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "createfromeEFrecord is failed >> retry to use cdma-decode ");
                wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(index, data);
            }
        }
        if (wrappedMessage != null) {
            return new SmsMessage(wrappedMessage);
        }
        return null;
    }

    @SystemApi
    public static SmsMessage createFromNativeSmsSubmitPdu(byte[] data, boolean isCdma) {
        SmsMessageBase wrappedMessage;
        if (isCdma) {
            wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(0, data);
        } else {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(0, data);
        }
        if (wrappedMessage != null) {
            return new SmsMessage(wrappedMessage);
        }
        return null;
    }

    public static int getTPLayerLengthForPDU(String pdu) {
        if (isCdmaVoice()) {
            return com.android.internal.telephony.cdma.SmsMessage.getTPLayerLengthForPDU(pdu);
        }
        return com.android.internal.telephony.gsm.SmsMessage.getTPLayerLengthForPDU(pdu);
    }

    public static int[] calculateLength(CharSequence msgBody, boolean use7bitOnly) {
        return calculateLength(msgBody, use7bitOnly, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static int[] calculateLength(CharSequence msgBody, boolean use7bitOnly, int subId) {
        GsmAlphabet.TextEncodingDetails ted;
        if (useCdmaFormatForMoSms(subId)) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLength(msgBody, use7bitOnly, true);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLength(msgBody, use7bitOnly);
        }
        int[] ret = {ted.msgCount, ted.codeUnitCount, ted.codeUnitsRemaining, ted.codeUnitSize, ted.languageTable, ted.languageShiftTable};
        return ret;
    }

    public static ArrayList<String> fragmentText(String text) {
        return fragmentText(text, (SmsManager) null);
    }

    public static ArrayList<String> fragmentText(String text, int subId) {
        GsmAlphabet.TextEncodingDetails ted;
        int udhLength;
        int nextPos;
        int udhLength2;
        boolean isCdma = useCdmaFormatForMoSms(subId);
        boolean z = false;
        if (isCdma) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLength(text, false, true);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLength(text, false);
        }
        if (ted.codeUnitSize == 1) {
            if (ted.languageTable != 0 && ted.languageShiftTable != 0) {
                udhLength2 = 7;
            } else {
                int udhLength3 = ted.languageTable;
                if (udhLength3 != 0 || ted.languageShiftTable != 0) {
                    udhLength2 = 4;
                } else {
                    udhLength2 = 0;
                }
            }
            if (ted.msgCount > 1) {
                udhLength2 += 6;
            }
            if (udhLength2 != 0) {
                udhLength2++;
            }
            udhLength = 160 - udhLength2;
        } else {
            int limit = ted.msgCount;
            if (limit > 1) {
                udhLength = 134;
                if (!hasEmsSupport() && ted.msgCount < 10) {
                    udhLength = 134 - 2;
                }
            } else {
                udhLength = 140;
            }
        }
        String newMsgBody = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(C4337R.bool.config_sms_force_7bit_encoding)) {
            if (isCdma && ted.msgCount == 1) {
                z = true;
            }
            newMsgBody = Sms7BitEncodingTranslator.translate(text, z);
        }
        if (TextUtils.isEmpty(newMsgBody)) {
            newMsgBody = text;
        }
        int pos = 0;
        int textLen = newMsgBody.length();
        ArrayList<String> result = new ArrayList<>(ted.msgCount);
        while (pos < textLen) {
            if (ted.codeUnitSize == 1) {
                if (isCdma && ted.msgCount == 1) {
                    nextPos = Math.min(udhLength, textLen - pos) + pos;
                } else {
                    int nextPos2 = ted.languageTable;
                    nextPos = GsmAlphabet.findGsmSeptetLimitIndex(newMsgBody, pos, udhLength, nextPos2, ted.languageShiftTable);
                }
            } else {
                nextPos = SmsMessageBase.findNextUnicodePosition(pos, udhLength, newMsgBody);
            }
            if (nextPos <= pos || nextPos > textLen) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "fragmentText failed (" + pos + " >= " + nextPos + " or " + nextPos + " >= " + textLen + NavigationBarInflaterView.KEY_CODE_END);
                break;
            }
            result.add(newMsgBody.substring(pos, nextPos));
            pos = nextPos;
        }
        return result;
    }

    public static int[] calculateLength(String messageBody, boolean use7bitOnly) {
        return calculateLength((CharSequence) messageBody, use7bitOnly);
    }

    public static int[] calculateLength(String messageBody, boolean use7bitOnly, int subId) {
        return calculateLength((CharSequence) messageBody, use7bitOnly, subId);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, int subId) {
        SmsMessageBase.SubmitPduBase spb;
        if (useCdmaFormatForMoSms(subId)) {
            spb = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, (SmsHeader) null);
        } else {
            spb = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested);
        }
        if (spb != null) {
            return new SubmitPdu(spb);
        }
        return null;
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, short destinationPort, byte[] data, boolean statusReportRequested) {
        SmsMessageBase.SubmitPduBase spb;
        if (useCdmaFormatForMoSms()) {
            spb = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(scAddress, destinationAddress, destinationPort, data, statusReportRequested);
        } else {
            spb = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(scAddress, destinationAddress, destinationPort, data, statusReportRequested);
        }
        if (spb != null) {
            return new SubmitPdu(spb);
        }
        return null;
    }

    @SystemApi
    public static SubmitPdu getSmsPdu(int subId, int status, String scAddress, String address, String message, long date) {
        SmsMessageBase.SubmitPduBase spb;
        if (isCdmaVoice(subId)) {
            if (status == 1 || status == 3) {
                spb = com.android.internal.telephony.cdma.SmsMessage.getDeliverPdu(address, message, date);
            } else {
                spb = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(scAddress, address, message, false, (SmsHeader) null);
            }
        } else if (status == 1 || status == 3) {
            spb = com.android.internal.telephony.gsm.SmsMessage.getDeliverPdu(scAddress, address, message, date);
        } else {
            spb = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(scAddress, address, message, false, (byte[]) null);
        }
        if (spb != null) {
            return new SubmitPdu(spb);
        }
        return null;
    }

    @SystemApi
    public static byte[] getSubmitPduEncodedMessage(boolean isTypeGsm, String destinationAddress, String message, int encoding, int languageTable, int languageShiftTable, int refNumber, int seqNumber, int msgCount) {
        int i;
        byte[] data;
        SmsHeader.ConcatRef concatRef = new SmsHeader.ConcatRef();
        concatRef.refNumber = refNumber;
        concatRef.seqNumber = seqNumber;
        concatRef.msgCount = msgCount;
        concatRef.isEightBits = true;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.concatRef = concatRef;
        if (encoding == 1) {
            smsHeader.languageTable = languageTable;
            smsHeader.languageShiftTable = languageShiftTable;
        }
        if (isTypeGsm) {
            i = 0;
            data = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(null, destinationAddress, message, false, SmsHeader.toByteArray(smsHeader), encoding, languageTable, languageShiftTable).encodedMessage;
        } else {
            i = 0;
            UserData uData = new UserData();
            uData.payloadStr = message;
            uData.userDataHeader = smsHeader;
            if (encoding == 1) {
                uData.msgEncoding = 9;
            } else {
                uData.msgEncoding = 4;
            }
            uData.msgEncodingSet = true;
            data = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(destinationAddress, uData, false).encodedMessage;
        }
        if (data == null) {
            return new byte[i];
        }
        return data;
    }

    public String getServiceCenterAddress() {
        return this.mWrappedSmsMessage.getServiceCenterAddress();
    }

    public String getOriginatingAddress() {
        return this.mWrappedSmsMessage.getOriginatingAddress();
    }

    public String getDisplayOriginatingAddress() {
        return this.mWrappedSmsMessage.getDisplayOriginatingAddress();
    }

    public String getMessageBody() {
        return this.mWrappedSmsMessage.getMessageBody();
    }

    /* renamed from: android.telephony.SmsMessage$1 */
    static /* synthetic */ class C31621 {

        /* renamed from: $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass */
        static final /* synthetic */ int[] f518xf858d1e4;

        static {
            int[] iArr = new int[SmsConstants.MessageClass.values().length];
            f518xf858d1e4 = iArr;
            try {
                iArr[SmsConstants.MessageClass.CLASS_0.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f518xf858d1e4[SmsConstants.MessageClass.CLASS_1.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f518xf858d1e4[SmsConstants.MessageClass.CLASS_2.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f518xf858d1e4[SmsConstants.MessageClass.CLASS_3.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public MessageClass getMessageClass() {
        switch (C31621.f518xf858d1e4[this.mWrappedSmsMessage.getMessageClass().ordinal()]) {
            case 1:
                return MessageClass.CLASS_0;
            case 2:
                return MessageClass.CLASS_1;
            case 3:
                return MessageClass.CLASS_2;
            case 4:
                return MessageClass.CLASS_3;
            default:
                return MessageClass.UNKNOWN;
        }
    }

    public String getDisplayMessageBody() {
        return this.mWrappedSmsMessage.getDisplayMessageBody();
    }

    public String getPseudoSubject() {
        return this.mWrappedSmsMessage.getPseudoSubject();
    }

    public long getTimestampMillis() {
        return this.mWrappedSmsMessage.getTimestampMillis();
    }

    public boolean isEmail() {
        return this.mWrappedSmsMessage.isEmail();
    }

    public String getEmailBody() {
        return this.mWrappedSmsMessage.getEmailBody();
    }

    public String getEmailFrom() {
        return this.mWrappedSmsMessage.getEmailFrom();
    }

    public int getProtocolIdentifier() {
        return this.mWrappedSmsMessage.getProtocolIdentifier();
    }

    public boolean isReplace() {
        return this.mWrappedSmsMessage.isReplace();
    }

    public boolean isCphsMwiMessage() {
        return this.mWrappedSmsMessage.isCphsMwiMessage();
    }

    public boolean isMWIClearMessage() {
        return this.mWrappedSmsMessage.isMWIClearMessage();
    }

    public boolean isMWISetMessage() {
        return this.mWrappedSmsMessage.isMWISetMessage();
    }

    public boolean isMwiDontStore() {
        return this.mWrappedSmsMessage.isMwiDontStore();
    }

    public byte[] getUserData() {
        return this.mWrappedSmsMessage.getUserData();
    }

    public byte[] getPdu() {
        return this.mWrappedSmsMessage.getPdu();
    }

    @Deprecated
    public int getStatusOnSim() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    public int getStatusOnIcc() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @Deprecated
    public int getIndexOnSim() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    public int getIndexOnIcc() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    public int getStatus() {
        return this.mWrappedSmsMessage.getStatus();
    }

    public boolean isStatusReportMessage() {
        return this.mWrappedSmsMessage.isStatusReportMessage();
    }

    public boolean isReplyPathPresent() {
        return this.mWrappedSmsMessage.isReplyPathPresent();
    }

    public int getReceivedEncodingType() {
        return this.mWrappedSmsMessage.getReceivedEncodingType();
    }

    public boolean is3gpp() {
        return this.mWrappedSmsMessage instanceof com.android.internal.telephony.gsm.SmsMessage;
    }

    private static boolean useCdmaFormatForMoSms() {
        return useCdmaFormatForMoSms(SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean useCdmaFormatForMoSms(int subId) {
        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(subId);
        if (!smsManager.isImsSmsSupported()) {
            return isCdmaVoice(subId);
        }
        return "3gpp2".equals(smsManager.getImsSmsFormat());
    }

    private static boolean isCdmaVoice() {
        return isCdmaVoice(SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean isCdmaVoice(int subId) {
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
            return false;
        }
        int activePhone = TelephonyManager.getDefault().getCurrentPhoneType(subId);
        return 2 == activePhone;
    }

    public static boolean hasEmsSupport() {
        if (!isNoEmsSupportConfigListExisted()) {
            return true;
        }
        long identity = Binder.clearCallingIdentity();
        try {
            String simOperator = TelephonyManager.getDefault().getSimOperatorNumeric();
            String gid = TelephonyManager.getDefault().getGroupIdLevel1();
            Binder.restoreCallingIdentity(identity);
            if (!TextUtils.isEmpty(simOperator)) {
                for (NoEmsSupportConfig currentConfig : mNoEmsSupportConfigList) {
                    if (currentConfig == null) {
                        com.android.telephony.Rlog.m246w(LOG_TAG, "hasEmsSupport currentConfig is null");
                    } else if (simOperator.startsWith(currentConfig.mOperatorNumber) && (TextUtils.isEmpty(currentConfig.mGid1) || (!TextUtils.isEmpty(currentConfig.mGid1) && currentConfig.mGid1.equalsIgnoreCase(gid)))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(identity);
            throw th;
        }
    }

    public static boolean shouldAppendPageNumberAsPrefix() {
        if (!isNoEmsSupportConfigListExisted()) {
            return false;
        }
        long identity = Binder.clearCallingIdentity();
        try {
            String simOperator = TelephonyManager.getDefault().getSimOperatorNumeric();
            String gid = TelephonyManager.getDefault().getGroupIdLevel1();
            Binder.restoreCallingIdentity(identity);
            for (NoEmsSupportConfig currentConfig : mNoEmsSupportConfigList) {
                if (simOperator.startsWith(currentConfig.mOperatorNumber) && (TextUtils.isEmpty(currentConfig.mGid1) || (!TextUtils.isEmpty(currentConfig.mGid1) && currentConfig.mGid1.equalsIgnoreCase(gid)))) {
                    return currentConfig.mIsPrefix;
                }
            }
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(identity);
            throw th;
        }
    }

    private static class NoEmsSupportConfig {
        String mGid1;
        boolean mIsPrefix;
        String mOperatorNumber;

        public NoEmsSupportConfig(String[] config) {
            this.mOperatorNumber = config[0];
            this.mIsPrefix = "prefix".equals(config[1]);
            this.mGid1 = config.length > 2 ? config[2] : null;
        }

        public String toString() {
            return "NoEmsSupportConfig { mOperatorNumber = " + this.mOperatorNumber + ", mIsPrefix = " + this.mIsPrefix + ", mGid1 = " + this.mGid1 + " }";
        }
    }

    private static boolean isNoEmsSupportConfigListExisted() {
        Resources r;
        synchronized (SmsMessage.class) {
            if (!mIsNoEmsSupportConfigListLoaded && (r = Resources.getSystem()) != null) {
                String[] listArray = r.getStringArray(C4337R.array.no_ems_support_sim_operators);
                if (listArray != null && listArray.length > 0) {
                    mNoEmsSupportConfigList = new NoEmsSupportConfig[listArray.length];
                    for (int i = 0; i < listArray.length; i++) {
                        mNoEmsSupportConfigList[i] = new NoEmsSupportConfig(listArray[i].split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
                    }
                }
                mIsNoEmsSupportConfigListLoaded = true;
            }
        }
        NoEmsSupportConfig[] noEmsSupportConfigArr = mNoEmsSupportConfigList;
        return (noEmsSupportConfigArr == null || noEmsSupportConfigArr.length == 0) ? false : true;
    }

    public String getRecipientAddress() {
        return this.mWrappedSmsMessage.getRecipientAddress();
    }

    public static int[] calculateLength(CharSequence msgBody, boolean use7bitOnly, int encodingType, int maxEmailLen, int phoneType) {
        GsmAlphabet.TextEncodingDetails ted;
        if (phoneType == 2) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLengthWithEmail(msgBody, use7bitOnly, maxEmailLen);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLengthWithEmail(msgBody, use7bitOnly, encodingType, maxEmailLen);
        }
        int[] ret = {ted.msgCount, ted.codeUnitCount, ted.codeUnitsRemaining, ted.codeUnitSize, ted.languageTable, ted.languageShiftTable};
        return ret;
    }

    public static int[] calculateLengthWithEncodingType(CharSequence msgBody, boolean use7bitOnly, int encodingType) {
        GsmAlphabet.TextEncodingDetails ted;
        if (useCdmaFormatForMoSms()) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLength(msgBody, use7bitOnly, true);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLengthWithEncodingType(msgBody, use7bitOnly, encodingType);
        }
        int[] ret = {ted.msgCount, ted.codeUnitCount, ted.codeUnitsRemaining, ted.codeUnitSize};
        return ret;
    }

    private static int getSubId(int phoneId) {
        int[] subIds = SubscriptionManager.getSubId(phoneId);
        if (subIds != null && subIds.length > 0) {
            return subIds[0];
        }
        return -1;
    }

    public static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu) {
        String format;
        String format2;
        com.android.telephony.Rlog.m242i(LOG_TAG, "semCreateFromPdu() : phoneId = " + phoneId);
        int subId = getSubId(phoneId);
        int activePhone = TelephonyManager.getDefault().getCurrentPhoneType();
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("KDDI")) {
            format = useCdmaFormatForMoSms() ? "3gpp2" : "3gpp";
        } else {
            format = 2 == activePhone ? "3gpp2" : "3gpp";
        }
        SmsMessage message = semCreateFromPdu(phoneId, pdu, format);
        if (message == null || message.mWrappedSmsMessage == null) {
            com.android.telephony.Rlog.m240e(LOG_TAG, "semCreateFromPdu(): decoding is failed because of wrong format");
            if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("KDDI")) {
                format2 = useCdmaFormatForMoSms() ? "3gpp" : "3gpp2";
            } else {
                format2 = 2 == activePhone ? "3gpp" : "3gpp2";
            }
            return semCreateFromPdu(phoneId, pdu, format2);
        }
        return message;
    }

    public static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu, String format) {
        if (phoneId != Integer.MAX_VALUE && (phoneId < 0 || phoneId >= TelephonyManager.getDefault().getPhoneCount())) {
            com.android.telephony.Rlog.m240e(LOG_TAG, "invalid phoneId = " + phoneId);
            return null;
        }
        com.android.telephony.Rlog.m242i(LOG_TAG, "semCreateFromPdu phoneId = " + phoneId);
        return semCreateFromPdu(phoneId, pdu, format, true);
    }

    private static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu, String format, boolean fallbackToOtherFormat) {
        SmsMessageBase wrappedMessage;
        if (pdu == null || format == null) {
            com.android.telephony.Rlog.m242i(LOG_TAG, "semCreateFromPdu(): pdu or format are null");
            return null;
        }
        String otherFormat = "3gpp2".equals(format) ? "3gpp" : "3gpp2";
        if ("3gpp2".equals(format)) {
            wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(phoneId, pdu);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "semCreateFromPdu is failed >> retry to use gsm-decode ");
                wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(phoneId, pdu);
            }
        } else if ("3gpp".equals(format)) {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(phoneId, pdu);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "semCreateFromPdu is failed >> retry to use CDMA-decode ");
                wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(phoneId, pdu);
            }
        } else {
            com.android.telephony.Rlog.m240e(LOG_TAG, "semCreateFromPdu(): unsupported message format " + format);
            return null;
        }
        if (wrappedMessage != null) {
            return new SmsMessage(wrappedMessage);
        }
        if (fallbackToOtherFormat) {
            return semCreateFromPdu(phoneId, pdu, otherFormat, false);
        }
        com.android.telephony.Rlog.m240e(LOG_TAG, "semCreateFromPdu(): wrappedMessage is null");
        return null;
    }

    public static SmsMessage semCreateFromPdu(int phoneId, byte[] pdu, int encoding) {
        SmsMessageBase wrappedMessage;
        if (2 == encoding) {
            wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(phoneId, pdu);
        } else {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(phoneId, pdu);
        }
        if (wrappedMessage != null) {
            return new SmsMessage(wrappedMessage);
        }
        com.android.telephony.Rlog.m240e(LOG_TAG, "createFromPdu(): wrappedMessage is null");
        return null;
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data, String format) {
        SmsMessageBase wrappedMessage;
        if ("3gpp2".equals(format)) {
            wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(index, data);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "createfromeEFrecord is failed >> retry to use gsm-decode ");
                wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(index, data);
            }
        } else {
            wrappedMessage = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(index, data);
            if (wrappedMessage == null) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "createfromeEFrecord is failed >> retry to use cdma-decode ");
                wrappedMessage = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(index, data);
            }
        }
        if (wrappedMessage != null) {
            return new SmsMessage(wrappedMessage);
        }
        com.android.telephony.Rlog.m240e(LOG_TAG, "createFromEfRecord(): wrappedMessage is null");
        return null;
    }

    public static ArrayList<String> fragmentText(String text, int encodingType, SmsManager smsManager) {
        GsmAlphabet.TextEncodingDetails ted;
        int limit;
        int nextPos;
        boolean isCdma = useCdmaFormatForMoSms(smsManager);
        int i = 1;
        if (isCdma) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLength(text, false, true);
        } else if (encodingType == 1) {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLengthWithEncodingType(text, false, encodingType);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLength(text, false);
        }
        if (ted.msgCount > 1) {
            if (GsmAlphabet.getEnabledSingleShiftTables().length >= 1 && GsmAlphabet.getEnabledLockingShiftTables().length >= 1) {
                limit = ted.codeUnitSize == 1 ? 147 : 128;
            } else if (GsmAlphabet.getEnabledSingleShiftTables().length >= 1 || GsmAlphabet.getEnabledLockingShiftTables().length >= 1) {
                int limit2 = ted.codeUnitSize;
                limit = limit2 == 1 ? 149 : 131;
            } else {
                limit = ted.codeUnitSize == 1 ? 153 : 134;
            }
        } else {
            int limit3 = ted.codeUnitSize;
            limit = limit3 == 1 ? 160 : 140;
        }
        String newMsgBody = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(C4337R.bool.config_sms_force_7bit_encoding)) {
            newMsgBody = Sms7BitEncodingTranslator.translate(text, isCdma);
        }
        if (TextUtils.isEmpty(newMsgBody)) {
            newMsgBody = text;
        }
        int pos = 0;
        int textLen = newMsgBody.length();
        ArrayList<String> result = new ArrayList<>(ted.msgCount);
        while (pos < textLen) {
            if (ted.codeUnitSize == i) {
                if (useCdmaFormatForMoSms(smsManager) && ted.msgCount == i) {
                    nextPos = Math.min(limit, textLen - pos) + pos;
                } else {
                    nextPos = GsmAlphabet.findGsmSeptetLimitIndex(newMsgBody, pos, limit, ted.languageTable, ted.languageShiftTable);
                }
            } else {
                nextPos = SmsMessageBase.findNextUnicodePosition(pos, limit, newMsgBody);
                if (nextPos <= pos || nextPos > textLen) {
                    com.android.telephony.Rlog.m240e(LOG_TAG, "findNextUnicodePosition() isn`t working.(" + pos + " >= " + nextPos + " or " + nextPos + " >= " + textLen + NavigationBarInflaterView.KEY_CODE_END);
                    nextPos = pos + Math.min(limit / 2, textLen - pos);
                }
            }
            if (nextPos <= pos || nextPos > textLen) {
                com.android.telephony.Rlog.m238d(LOG_TAG, "fragmentText failed (" + pos + " >= " + nextPos + " or " + nextPos + " >= " + textLen + NavigationBarInflaterView.KEY_CODE_END);
                break;
            }
            result.add(newMsgBody.substring(pos, nextPos));
            pos = nextPos;
            i = 1;
        }
        return result;
    }

    public int getMessageIdentifier() {
        return this.mWrappedSmsMessage.getMessageIdentifier();
    }

    public String getSharedAppId() {
        return this.mWrappedSmsMessage.getSharedAppId();
    }

    public String getSharedCmd() {
        return this.mWrappedSmsMessage.getSharedCmd();
    }

    public int getTeleserviceId() {
        return this.mWrappedSmsMessage.getTeleserviceId();
    }

    public String getSharedPayLoad() {
        return this.mWrappedSmsMessage.getSharedPayLoad();
    }

    public int getMessagePriority() {
        return this.mWrappedSmsMessage.getMessagePriority();
    }

    public String getCallbackNumber() {
        return this.mWrappedSmsMessage.getCallbackNumber();
    }

    public String getlinkUrl() {
        return this.mWrappedSmsMessage.getlinkUrl();
    }

    public SmsHeader getUserDataHeader() {
        return this.mWrappedSmsMessage.getUserDataHeader();
    }

    public int getDestPortAddr() {
        return this.mWrappedSmsMessage.getDestPortAddr();
    }

    public int getReadConfirmId() {
        return this.mWrappedSmsMessage.getReadConfirmId();
    }

    public boolean getSafeMessageIndication() {
        return this.mWrappedSmsMessage.getSafeMessageIndication();
    }

    public int getMessageType() {
        return this.mWrappedSmsMessage.getMessageType();
    }

    public byte[] getBearerData() {
        return this.mWrappedSmsMessage.getBearerData();
    }

    public String getDisplayDestinationAddress() {
        return this.mWrappedSmsMessage.getRecipientAddress();
    }

    public static boolean getCDMASmsReassembly() {
        return false;
    }

    public enum MessageTpPid {
        MSG_PID_DEFAULT(0),
        MSG_PID_SMS_HANDLED(64),
        MSG_PID_LBS_PORT(81),
        MSG_PID_APPLICATION_PORT(83);

        private int mValue;

        MessageTpPid(int value) {
            this.mValue = value;
        }

        public int value() {
            return this.mValue;
        }

        public static MessageTpPid fromInt(int value) {
            for (MessageTpPid e : values()) {
                if (e.mValue == value) {
                    return e;
                }
            }
            return null;
        }
    }

    public static SubmitPdu getSubmitPdu(int subId, String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, String callbackNumber, int priority) {
        SmsMessageBase.SubmitPduBase spb;
        if (useCdmaFormatForMoSms()) {
            spb = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(subId, scAddress, destinationAddress, message, statusReportRequested, SmsHeader.fromByteArray(header), callbackNumber, priority);
        } else {
            spb = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header);
        }
        return new SubmitPdu(spb);
    }

    private static boolean useCdmaFormatForMoSms(SmsManager smsManager) {
        if (smsManager == null) {
            smsManager = SmsManager.getDefault();
        }
        if (!smsManager.isImsSmsSupported()) {
            return "3gpp2".equals(smsManager.getCurrentFormat());
        }
        return "3gpp2".equals(smsManager.getImsSmsFormat());
    }

    public static int[] calculateLengthForEms(CharSequence msgBody, boolean use7bitOnly, boolean isEms) {
        GsmAlphabet.TextEncodingDetails ted;
        if (useCdmaFormatForMoSms()) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLengthForEms(msgBody, use7bitOnly, isEms);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLength(msgBody, use7bitOnly);
        }
        int[] ret = {ted.msgCount, ted.codeUnitCount, ted.codeUnitsRemaining, ted.codeUnitSize};
        return ret;
    }

    public static ArrayList<String> fragmentText(String text, SmsManager smsManager) {
        GsmAlphabet.TextEncodingDetails ted;
        int udhLength;
        int nextPos;
        int udhLength2;
        boolean isCdma = useCdmaFormatForMoSms(smsManager);
        int i = 1;
        if (isCdma) {
            ted = com.android.internal.telephony.cdma.SmsMessage.calculateLength(text, false, true);
        } else {
            ted = com.android.internal.telephony.gsm.SmsMessage.calculateLength(text, false);
        }
        if (ted.codeUnitSize == 1) {
            if (ted.languageTable != 0 && ted.languageShiftTable != 0) {
                udhLength2 = 7;
            } else {
                int udhLength3 = ted.languageTable;
                if (udhLength3 != 0 || ted.languageShiftTable != 0) {
                    udhLength2 = 4;
                } else {
                    udhLength2 = 0;
                }
            }
            if (ted.msgCount > 1) {
                udhLength2 += 6;
            }
            if (udhLength2 != 0) {
                udhLength2++;
            }
            udhLength = 160 - udhLength2;
        } else {
            int limit = ted.msgCount;
            if (limit > 1) {
                udhLength = 134;
                if (!hasEmsSupport() && ted.msgCount < 10) {
                    udhLength = 134 - 2;
                }
            } else {
                udhLength = 140;
            }
        }
        String newMsgBody = null;
        Resources r = Resources.getSystem();
        if (r.getBoolean(C4337R.bool.config_sms_force_7bit_encoding)) {
            newMsgBody = Sms7BitEncodingTranslator.translate(text, isCdma);
        }
        if (TextUtils.isEmpty(newMsgBody)) {
            newMsgBody = text;
        }
        int pos = 0;
        int textLen = newMsgBody.length();
        ArrayList<String> result = new ArrayList<>(ted.msgCount);
        while (pos < textLen) {
            if (ted.codeUnitSize == i) {
                if (isCdma && ted.msgCount == i) {
                    nextPos = Math.min(udhLength, textLen - pos) + pos;
                } else {
                    nextPos = GsmAlphabet.findGsmSeptetLimitIndex(newMsgBody, pos, udhLength, ted.languageTable, ted.languageShiftTable);
                }
            } else {
                nextPos = SmsMessageBase.findNextUnicodePosition(pos, udhLength, newMsgBody);
                if (nextPos <= pos || nextPos > textLen) {
                    com.android.telephony.Rlog.m240e(LOG_TAG, "findNextUnicodePosition() isn't working.(" + pos + " >= " + nextPos + " or " + nextPos + " >= " + textLen + NavigationBarInflaterView.KEY_CODE_END);
                    nextPos = pos + Math.min(udhLength / 2, textLen - pos);
                }
            }
            if (nextPos <= pos || nextPos > textLen) {
                com.android.telephony.Rlog.m240e(LOG_TAG, "fragmentText failed (" + pos + " >= " + nextPos + " or " + nextPos + " >= " + textLen + NavigationBarInflaterView.KEY_CODE_END);
                break;
            }
            result.add(newMsgBody.substring(pos, nextPos));
            pos = nextPos;
            i = 1;
        }
        return result;
    }
}
