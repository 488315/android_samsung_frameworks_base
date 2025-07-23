package android.telephony;

import android.annotation.SystemApi;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.text.TextUtils;
import com.android.internal.R;
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

/* loaded from: classes4.dex */
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
    private static final int MAX_USER_DATA_SEPTETS_WITH_HEADER_NATIONAL_LOCKING_SHIFT_LANGUAGE = 147;
    private static final int PHONE_TYPE_CDMA = 2;
    private static final int PHONE_TYPE_GSM = 1;
    public static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE_FORMAT = 3;
    public static final int VALIDITY_PERIOD_FORMAT_ENHANCED_FORMAT = 1;
    public static final int VALIDITY_PERIOD_FORMAT_NOT_PRESENT = 0;
    public static final int VALIDITY_PERIOD_FORMAT_RELATIVE_FORMAT = 2;
    private static boolean mIsNoEmsSupportConfigListLoaded = false;
    private static NoEmsSupportConfig[] mNoEmsSupportConfigList;
    private int mSubId = 0;
    public SmsMessageBase mWrappedSmsMessage;

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

    public static boolean getCDMASmsReassembly() {
        return false;
    }

    public void setSubId(int i) {
        this.mSubId = i;
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

        protected SubmitPdu(SmsMessageBase.SubmitPduBase submitPduBase) {
            this.encodedMessage = submitPduBase.encodedMessage;
            this.encodedScAddress = submitPduBase.encodedScAddress;
        }
    }

    public SmsMessage(SmsMessageBase smsMessageBase) {
        this.mWrappedSmsMessage = smsMessageBase;
    }

    @Deprecated
    public static SmsMessage createFromPdu(byte[] bArr) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), bArr);
    }

    public static SmsMessage createFromPdu(byte[] bArr, String str) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), bArr, str, true);
    }

    private static SmsMessage createFromPdu(byte[] bArr, String str, boolean z) {
        return semCreateFromPdu(SubscriptionManager.getPhoneId(SmsManager.getDefaultSmsSubscriptionId()), bArr, str, z);
    }

    public static SmsMessage createFromEfRecord(int i, byte[] bArr) {
        return createFromEfRecord(i, bArr, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static SmsMessage createFromEfRecord(int i, byte[] bArr, int i2) {
        SmsMessageBase createFromEfRecord;
        SmsMessageBase createFromEfRecord2;
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, i2).getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK)) {
            createFromEfRecord2 = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
        } else if (isCdmaVoice(i2)) {
            createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(i, bArr);
            if (createFromEfRecord == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "createfromeEFrecord is failed >> retry to use gsm-decode ");
                createFromEfRecord2 = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
            }
            createFromEfRecord2 = createFromEfRecord;
        } else {
            createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
            if (createFromEfRecord == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "createfromeEFrecord is failed >> retry to use cdma-decode ");
                createFromEfRecord2 = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(i, bArr);
            }
            createFromEfRecord2 = createFromEfRecord;
        }
        if (createFromEfRecord2 != null) {
            return new SmsMessage(createFromEfRecord2);
        }
        return null;
    }

    @SystemApi
    public static SmsMessage createFromNativeSmsSubmitPdu(byte[] bArr, boolean z) {
        SmsMessageBase createFromEfRecord;
        if (z) {
            createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(0, bArr);
        } else {
            createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(0, bArr);
        }
        if (createFromEfRecord != null) {
            return new SmsMessage(createFromEfRecord);
        }
        return null;
    }

    public static int getTPLayerLengthForPDU(String str) {
        if (isCdmaVoice()) {
            return com.android.internal.telephony.cdma.SmsMessage.getTPLayerLengthForPDU(str);
        }
        return com.android.internal.telephony.gsm.SmsMessage.getTPLayerLengthForPDU(str);
    }

    public static int[] calculateLength(CharSequence charSequence, boolean z) {
        return calculateLength(charSequence, z, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static int[] calculateLength(CharSequence charSequence, boolean z, int i) {
        GsmAlphabet.TextEncodingDetails calculateLength;
        if (useCdmaFormatForMoSms(i)) {
            calculateLength = com.android.internal.telephony.cdma.SmsMessage.calculateLength(charSequence, z, true);
        } else {
            calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(charSequence, z);
        }
        return new int[]{calculateLength.msgCount, calculateLength.codeUnitCount, calculateLength.codeUnitsRemaining, calculateLength.codeUnitSize, calculateLength.languageTable, calculateLength.languageShiftTable};
    }

    public static ArrayList<String> fragmentText(String str) {
        return fragmentText(str, (SmsManager) null);
    }

    public static ArrayList<String> fragmentText(String str, int i) {
        GsmAlphabet.TextEncodingDetails calculateLength;
        int i2;
        String str2;
        int findNextUnicodePosition;
        int i3;
        boolean useCdmaFormatForMoSms = useCdmaFormatForMoSms(i);
        int i4 = 0;
        if (useCdmaFormatForMoSms) {
            calculateLength = com.android.internal.telephony.cdma.SmsMessage.calculateLength(str, false, true);
        } else {
            calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(str, false);
        }
        if (calculateLength.codeUnitSize == 1) {
            if (calculateLength.languageTable == 0 || calculateLength.languageShiftTable == 0) {
                i3 = (calculateLength.languageTable == 0 && calculateLength.languageShiftTable == 0) ? 0 : 4;
            } else {
                i3 = 7;
            }
            if (calculateLength.msgCount > 1) {
                i3 += 6;
            }
            if (i3 != 0) {
                i3++;
            }
            i2 = 160 - i3;
        } else if (calculateLength.msgCount > 1) {
            i2 = (hasEmsSupport() || calculateLength.msgCount >= 10) ? 134 : 132;
        } else {
            i2 = 140;
        }
        if (Resources.getSystem().getBoolean(R.bool.config_sms_force_7bit_encoding)) {
            str2 = Sms7BitEncodingTranslator.translate(str, useCdmaFormatForMoSms && calculateLength.msgCount == 1);
        } else {
            str2 = null;
        }
        if (!TextUtils.isEmpty(str2)) {
            str = str2;
        }
        int length = str.length();
        ArrayList<String> arrayList = new ArrayList<>(calculateLength.msgCount);
        while (i4 < length) {
            if (calculateLength.codeUnitSize == 1) {
                if (useCdmaFormatForMoSms && calculateLength.msgCount == 1) {
                    findNextUnicodePosition = Math.min(i2, length - i4) + i4;
                } else {
                    findNextUnicodePosition = GsmAlphabet.findGsmSeptetLimitIndex(str, i4, i2, calculateLength.languageTable, calculateLength.languageShiftTable);
                }
            } else {
                findNextUnicodePosition = SmsMessageBase.findNextUnicodePosition(i4, i2, str);
            }
            if (findNextUnicodePosition <= i4 || findNextUnicodePosition > length) {
                com.android.telephony.Rlog.e(LOG_TAG, "fragmentText failed (" + i4 + " >= " + findNextUnicodePosition + " or " + findNextUnicodePosition + " >= " + length + NavigationBarInflaterView.KEY_CODE_END);
                break;
            }
            arrayList.add(str.substring(i4, findNextUnicodePosition));
            i4 = findNextUnicodePosition;
        }
        return arrayList;
    }

    public static int[] calculateLength(String str, boolean z) {
        return calculateLength((CharSequence) str, z);
    }

    public static int[] calculateLength(String str, boolean z, int i) {
        return calculateLength((CharSequence) str, z, i);
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z) {
        return getSubmitPdu(str, str2, str3, z, SmsManager.getDefaultSmsSubscriptionId());
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, String str3, boolean z, int i) {
        SmsMessageBase.SubmitPduBase submitPdu;
        if (useCdmaFormatForMoSms(i)) {
            submitPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, str3, z, (SmsHeader) null);
        } else {
            submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, str3, z);
        }
        if (submitPdu != null) {
            return new SubmitPdu(submitPdu);
        }
        return null;
    }

    public static SubmitPdu getSubmitPdu(String str, String str2, short s, byte[] bArr, boolean z) {
        SmsMessageBase.SubmitPduBase submitPdu;
        if (useCdmaFormatForMoSms()) {
            submitPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, s, bArr, z);
        } else {
            submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, s, bArr, z);
        }
        if (submitPdu != null) {
            return new SubmitPdu(submitPdu);
        }
        return null;
    }

    @SystemApi
    public static SubmitPdu getSmsPdu(int i, int i2, String str, String str2, String str3, long j) {
        SmsMessageBase.SubmitPduBase deliverPdu;
        if (isCdmaVoice(i)) {
            if (i2 == 1 || i2 == 3) {
                deliverPdu = com.android.internal.telephony.cdma.SmsMessage.getDeliverPdu(str2, str3, j);
            } else {
                deliverPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, str3, false, (SmsHeader) null);
            }
        } else if (i2 == 1 || i2 == 3) {
            deliverPdu = com.android.internal.telephony.gsm.SmsMessage.getDeliverPdu(str, str2, str3, j);
        } else {
            deliverPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, str3, false, (byte[]) null);
        }
        if (deliverPdu != null) {
            return new SubmitPdu(deliverPdu);
        }
        return null;
    }

    @SystemApi
    public static byte[] getSubmitPduEncodedMessage(boolean z, String str, String str2, int i, int i2, int i3, int i4, int i5, int i6) {
        byte[] bArr;
        SmsHeader.ConcatRef concatRef = new SmsHeader.ConcatRef();
        concatRef.refNumber = i4;
        concatRef.seqNumber = i5;
        concatRef.msgCount = i6;
        concatRef.isEightBits = true;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.concatRef = concatRef;
        if (i == 1) {
            smsHeader.languageTable = i2;
            smsHeader.languageShiftTable = i3;
        }
        if (z) {
            bArr = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(null, str, str2, false, SmsHeader.toByteArray(smsHeader), i, i2, i3).encodedMessage;
        } else {
            UserData userData = new UserData();
            userData.payloadStr = str2;
            userData.userDataHeader = smsHeader;
            if (i == 1) {
                userData.msgEncoding = 9;
            } else {
                userData.msgEncoding = 4;
            }
            userData.msgEncodingSet = true;
            bArr = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, userData, false).encodedMessage;
        }
        return bArr == null ? new byte[0] : bArr;
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

    /* renamed from: android.telephony.SmsMessage$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass;

        static {
            int[] iArr = new int[SmsConstants.MessageClass.values().length];
            $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass = iArr;
            try {
                iArr[SmsConstants.MessageClass.CLASS_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass[SmsConstants.MessageClass.CLASS_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass[SmsConstants.MessageClass.CLASS_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass[SmsConstants.MessageClass.CLASS_3.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MessageClass getMessageClass() {
        int i = AnonymousClass1.$SwitchMap$com$android$internal$telephony$SmsConstants$MessageClass[this.mWrappedSmsMessage.getMessageClass().ordinal()];
        if (i == 1) {
            return MessageClass.CLASS_0;
        }
        if (i == 2) {
            return MessageClass.CLASS_1;
        }
        if (i == 3) {
            return MessageClass.CLASS_2;
        }
        if (i == 4) {
            return MessageClass.CLASS_3;
        }
        return MessageClass.UNKNOWN;
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

    private static boolean useCdmaFormatForMoSms(int i) {
        SmsManager smsManagerForSubscriptionId = SmsManager.getSmsManagerForSubscriptionId(i);
        if (!smsManagerForSubscriptionId.isImsSmsSupported()) {
            return isCdmaVoice(i);
        }
        return "3gpp2".equals(smsManagerForSubscriptionId.getImsSmsFormat());
    }

    private static boolean isCdmaVoice() {
        return isCdmaVoice(SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean isCdmaVoice(int i) {
        return !SmsManager.getSmsManagerForContextAndSubscriptionId(null, i).getSmsSetting(SmsConstants.SMS_3GPP2_LGT_NETWORK) && 2 == TelephonyManager.getDefault().getCurrentPhoneType(i);
    }

    public static boolean hasEmsSupport() {
        if (!isNoEmsSupportConfigListExisted()) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String simOperatorNumeric = TelephonyManager.getDefault().getSimOperatorNumeric();
            String groupIdLevel1 = TelephonyManager.getDefault().getGroupIdLevel1();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!TextUtils.isEmpty(simOperatorNumeric)) {
                for (NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
                    if (noEmsSupportConfig == null) {
                        com.android.telephony.Rlog.w(LOG_TAG, "hasEmsSupport currentConfig is null");
                    } else if (simOperatorNumeric.startsWith(noEmsSupportConfig.mOperatorNumber) && (TextUtils.isEmpty(noEmsSupportConfig.mGid1) || (!TextUtils.isEmpty(noEmsSupportConfig.mGid1) && noEmsSupportConfig.mGid1.equalsIgnoreCase(groupIdLevel1)))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean shouldAppendPageNumberAsPrefix() {
        if (!isNoEmsSupportConfigListExisted()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String simOperatorNumeric = TelephonyManager.getDefault().getSimOperatorNumeric();
            String groupIdLevel1 = TelephonyManager.getDefault().getGroupIdLevel1();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            for (NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
                if (simOperatorNumeric.startsWith(noEmsSupportConfig.mOperatorNumber) && (TextUtils.isEmpty(noEmsSupportConfig.mGid1) || (!TextUtils.isEmpty(noEmsSupportConfig.mGid1) && noEmsSupportConfig.mGid1.equalsIgnoreCase(groupIdLevel1)))) {
                    return noEmsSupportConfig.mIsPrefix;
                }
            }
            return false;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static class NoEmsSupportConfig {
        String mGid1;
        boolean mIsPrefix;
        String mOperatorNumber;

        public NoEmsSupportConfig(String[] strArr) {
            this.mOperatorNumber = strArr[0];
            this.mIsPrefix = "prefix".equals(strArr[1]);
            this.mGid1 = strArr.length > 2 ? strArr[2] : null;
        }

        public String toString() {
            return "NoEmsSupportConfig { mOperatorNumber = " + this.mOperatorNumber + ", mIsPrefix = " + this.mIsPrefix + ", mGid1 = " + this.mGid1 + " }";
        }
    }

    private static boolean isNoEmsSupportConfigListExisted() {
        Resources system;
        synchronized (SmsMessage.class) {
            if (!mIsNoEmsSupportConfigListLoaded && (system = Resources.getSystem()) != null) {
                String[] stringArray = system.getStringArray(R.array.no_ems_support_sim_operators);
                if (stringArray != null && stringArray.length > 0) {
                    mNoEmsSupportConfigList = new NoEmsSupportConfig[stringArray.length];
                    for (int i = 0; i < stringArray.length; i++) {
                        mNoEmsSupportConfigList[i] = new NoEmsSupportConfig(stringArray[i].split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
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

    public static int[] calculateLength(CharSequence charSequence, boolean z, int i, int i2, int i3) {
        GsmAlphabet.TextEncodingDetails calculateLengthWithEmail;
        if (i3 == 2) {
            calculateLengthWithEmail = com.android.internal.telephony.cdma.SmsMessage.calculateLengthWithEmail(charSequence, z, i2);
        } else {
            calculateLengthWithEmail = com.android.internal.telephony.gsm.SmsMessage.calculateLengthWithEmail(charSequence, z, i, i2);
        }
        return new int[]{calculateLengthWithEmail.msgCount, calculateLengthWithEmail.codeUnitCount, calculateLengthWithEmail.codeUnitsRemaining, calculateLengthWithEmail.codeUnitSize, calculateLengthWithEmail.languageTable, calculateLengthWithEmail.languageShiftTable};
    }

    public static int[] calculateLengthWithEncodingType(CharSequence charSequence, boolean z, int i) {
        GsmAlphabet.TextEncodingDetails calculateLengthWithEncodingType;
        if (useCdmaFormatForMoSms()) {
            calculateLengthWithEncodingType = com.android.internal.telephony.cdma.SmsMessage.calculateLength(charSequence, z, true);
        } else {
            calculateLengthWithEncodingType = com.android.internal.telephony.gsm.SmsMessage.calculateLengthWithEncodingType(charSequence, z, i);
        }
        return new int[]{calculateLengthWithEncodingType.msgCount, calculateLengthWithEncodingType.codeUnitCount, calculateLengthWithEncodingType.codeUnitsRemaining, calculateLengthWithEncodingType.codeUnitSize};
    }

    private static int getSubId(int i) {
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId == null || subId.length <= 0) {
            return -1;
        }
        return subId[0];
    }

    public static SmsMessage semCreateFromPdu(int i, byte[] bArr) {
        com.android.telephony.Rlog.i(LOG_TAG, "semCreateFromPdu() : phoneId = " + i);
        int subId = getSubId(i);
        int currentPhoneType = TelephonyManager.getDefault().getCurrentPhoneType();
        String str = "3gpp2";
        SmsMessage semCreateFromPdu = semCreateFromPdu(i, bArr, (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("KDDI") ? 2 == currentPhoneType : useCdmaFormatForMoSms()) ? "3gpp" : "3gpp2");
        if (semCreateFromPdu != null && semCreateFromPdu.mWrappedSmsMessage != null) {
            return semCreateFromPdu;
        }
        com.android.telephony.Rlog.e(LOG_TAG, "semCreateFromPdu(): decoding is failed because of wrong format");
        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, subId).getMnoName().toUpperCase().contains("KDDI") ? 2 == currentPhoneType : useCdmaFormatForMoSms()) {
            str = "3gpp";
        }
        return semCreateFromPdu(i, bArr, str);
    }

    public static SmsMessage semCreateFromPdu(int i, byte[] bArr, String str) {
        if (i != Integer.MAX_VALUE && (i < 0 || i >= TelephonyManager.getDefault().getPhoneCount())) {
            com.android.telephony.Rlog.e(LOG_TAG, "invalid phoneId = " + i);
            return null;
        }
        com.android.telephony.Rlog.i(LOG_TAG, "semCreateFromPdu phoneId = " + i);
        return semCreateFromPdu(i, bArr, str, true);
    }

    private static SmsMessage semCreateFromPdu(int i, byte[] bArr, String str, boolean z) {
        SmsMessageBase semCreateFromPdu;
        if (bArr == null || str == null) {
            com.android.telephony.Rlog.i(LOG_TAG, "semCreateFromPdu(): pdu or format are null");
            return null;
        }
        String str2 = "3gpp2".equals(str) ? "3gpp" : "3gpp2";
        if ("3gpp2".equals(str)) {
            semCreateFromPdu = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(i, bArr);
            if (semCreateFromPdu == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "semCreateFromPdu is failed >> retry to use gsm-decode ");
                semCreateFromPdu = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(i, bArr);
            }
        } else if ("3gpp".equals(str)) {
            semCreateFromPdu = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(i, bArr);
            if (semCreateFromPdu == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "semCreateFromPdu is failed >> retry to use CDMA-decode ");
                semCreateFromPdu = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(i, bArr);
            }
        } else {
            com.android.telephony.Rlog.e(LOG_TAG, "semCreateFromPdu(): unsupported message format " + str);
            return null;
        }
        if (semCreateFromPdu != null) {
            return new SmsMessage(semCreateFromPdu);
        }
        if (!z) {
            com.android.telephony.Rlog.e(LOG_TAG, "semCreateFromPdu(): wrappedMessage is null");
            return null;
        }
        return semCreateFromPdu(i, bArr, str2, false);
    }

    public static SmsMessage semCreateFromPdu(int i, byte[] bArr, int i2) {
        SmsMessageBase semCreateFromPdu;
        if (2 == i2) {
            semCreateFromPdu = com.android.internal.telephony.cdma.SmsMessage.semCreateFromPdu(i, bArr);
        } else {
            semCreateFromPdu = com.android.internal.telephony.gsm.SmsMessage.semCreateFromPdu(i, bArr);
        }
        if (semCreateFromPdu != null) {
            return new SmsMessage(semCreateFromPdu);
        }
        com.android.telephony.Rlog.e(LOG_TAG, "createFromPdu(): wrappedMessage is null");
        return null;
    }

    public static SmsMessage createFromEfRecord(int i, byte[] bArr, String str) {
        SmsMessageBase createFromEfRecord;
        if ("3gpp2".equals(str)) {
            createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(i, bArr);
            if (createFromEfRecord == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "createfromeEFrecord is failed >> retry to use gsm-decode ");
                createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
            }
        } else {
            createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
            if (createFromEfRecord == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "createfromeEFrecord is failed >> retry to use cdma-decode ");
                createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(i, bArr);
            }
        }
        if (createFromEfRecord != null) {
            return new SmsMessage(createFromEfRecord);
        }
        com.android.telephony.Rlog.e(LOG_TAG, "createFromEfRecord(): wrappedMessage is null");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00e7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f3 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList<java.lang.String> fragmentText(java.lang.String r12, int r13, android.telephony.SmsManager r14) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.SmsMessage.fragmentText(java.lang.String, int, android.telephony.SmsManager):java.util.ArrayList");
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

    public boolean getLinkWarningIndication() {
        return this.mWrappedSmsMessage.getLinkWarningIndication();
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

    public enum MessageTpPid {
        MSG_PID_DEFAULT(0),
        MSG_PID_SMS_HANDLED(64),
        MSG_PID_LBS_PORT(81),
        MSG_PID_APPLICATION_PORT(83);

        private int mValue;

        MessageTpPid(int i) {
            this.mValue = i;
        }

        public int value() {
            return this.mValue;
        }

        public static MessageTpPid fromInt(int i) {
            for (MessageTpPid messageTpPid : values()) {
                if (messageTpPid.mValue == i) {
                    return messageTpPid;
                }
            }
            return null;
        }
    }

    public static SubmitPdu getSubmitPdu(int i, String str, String str2, String str3, boolean z, byte[] bArr, String str4, int i2) {
        SmsMessageBase.SubmitPduBase submitPdu;
        if (useCdmaFormatForMoSms()) {
            submitPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(i, str, str2, str3, z, SmsHeader.fromByteArray(bArr), str4, i2);
        } else {
            submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, str3, z, bArr);
        }
        return new SubmitPdu(submitPdu);
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

    public static int[] calculateLengthForEms(CharSequence charSequence, boolean z, boolean z2) {
        GsmAlphabet.TextEncodingDetails calculateLength;
        if (useCdmaFormatForMoSms()) {
            calculateLength = com.android.internal.telephony.cdma.SmsMessage.calculateLengthForEms(charSequence, z, z2);
        } else {
            calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(charSequence, z);
        }
        return new int[]{calculateLength.msgCount, calculateLength.codeUnitCount, calculateLength.codeUnitsRemaining, calculateLength.codeUnitSize};
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00d3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00df A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList<java.lang.String> fragmentText(java.lang.String r13, android.telephony.SmsManager r14) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.SmsMessage.fragmentText(java.lang.String, android.telephony.SmsManager):java.util.ArrayList");
    }
}
