package com.android.internal.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import com.android.telephony.Rlog;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public abstract class SmsMessageBase {
    private static final int DELIMITER_ETX = 3;
    private static final int DELIMITER_GS = 29;
    private static final String LOG_TAG = "SmsMessageBase";
    protected String callbackNumber;
    protected int mBodyOffset;
    protected String mEmailBody;
    protected String mEmailFrom;
    protected boolean mIsEmail;
    protected boolean mIsMwi;
    protected boolean mIsfourBytesUnicode;
    protected String mMessageBody;
    public int mMessageRef;
    protected int mMti;
    protected boolean mMwiDontStore;
    protected boolean mMwiSense;
    protected SmsAddress mOriginatingAddress;
    protected byte[] mPdu;
    protected String mPseudoSubject;
    protected SmsAddress mRecipientAddress;
    protected String mScAddress;
    protected long mScTimeMillis;
    protected int mTeleserviceId;
    protected byte[] mUserData;
    protected SmsHeader mUserDataHeader;
    protected byte[] mlastByte;
    protected SmsAddress replyAddress;
    public static final Pattern NAME_ADDR_EMAIL_PATTERN = Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");
    private static int mSubId = SubscriptionManager.getDefaultSmsSubscriptionId();
    private static final char[] voiceMailText = {49352, 47196, 50868, ' ', 51020, 49457, 47700, 51068, 51060, ' ', 46020, 52265, 54664, 49845, 45768, 45796, '.', 53685, 54868, 53412, 47484, ' ', 45572, 47476, 47732, ' ', 51088, 46041, 50672, 44208, 46121, 45768, 45796, '.'};
    private static final char[] pagingText = {'[', 54840, 52636, 47700, 49884, 51648, ']'};
    private static final char[] thirdPartyText = {'[', 50808, 48512, 49324, 50629, 51088, ' ', 50672, 44208, ']'};
    private static final char[] webText = {'[', 50937, 49436, 54609, ' ', 50672, 44208, ']'};
    private static final char[] dataText = {'[', DateFormat.STANDALONE_MONTH, 'G', ' ', 'U', '+', ' ', 47924, 49440, 51064, 53552, 45367, ']'};
    private static final char[] lguText = {'[', DateFormat.STANDALONE_MONTH, 'G', ' ', 'U', '+', ' ', 50504, 45236, ']'};
    private static final char[] connectText = {50672, 44208, ' ', 54616, 49884, 44192, 49845, 45768, 44620, '?'};
    protected int mReceivedEncodingType = 0;
    protected int mStatusOnIcc = -1;
    protected int mIndexOnIcc = -1;
    protected String linkUrl = null;
    protected String mSharedAppID = null;
    protected String mSharedCmd = null;
    protected String mSharedPayLoad = null;
    protected byte[] bearerData = null;

    private static boolean isRegionalIndicatorSymbol(int i) {
        return 127462 <= i && i <= 127487;
    }

    public int getCDMAMessageType() {
        return 0;
    }

    public abstract SmsConstants.MessageClass getMessageClass();

    public abstract int getMessageIdentifier();

    public abstract int getMessagePriority();

    public abstract int getProtocolIdentifier();

    public abstract int getStatus();

    public abstract boolean isCphsMwiMessage();

    public abstract boolean isMWIClearMessage();

    public abstract boolean isMWISetMessage();

    public abstract boolean isMwiDontStore();

    public abstract boolean isReplace();

    public abstract boolean isReplyPathPresent();

    public abstract boolean isStatusReportMessage();

    public static abstract class SubmitPduBase {
        public byte[] encodedMessage;
        public byte[] encodedScAddress;

        public String toString() {
            return "SubmitPdu: encodedScAddress = " + Arrays.toString(this.encodedScAddress) + ", encodedMessage = " + Arrays.toString(this.encodedMessage);
        }
    }

    public String getServiceCenterAddress() {
        return this.mScAddress;
    }

    public String getOriginatingAddress() {
        SmsAddress smsAddress = this.mOriginatingAddress;
        if (smsAddress == null) {
            return null;
        }
        return smsAddress.getAddressString();
    }

    public String getDisplayOriginatingAddress() {
        if (this.mIsEmail) {
            return this.mEmailFrom;
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            String telephonyProperty = TelephonyManager.getTelephonyProperty(SubscriptionManager.getPhoneId(mSubId), TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC, "00000");
            if (telephonyProperty == null || telephonyProperty.startsWith("450") || telephonyProperty.startsWith("001")) {
                return getReplyAddress();
            }
            return getOriginatingAddress();
        }
        return getOriginatingAddress();
    }

    public String getMessageBody() {
        return this.mMessageBody;
    }

    public String getDisplayMessageBody() {
        if (this.mIsEmail) {
            return this.mEmailBody;
        }
        return getMessageBody();
    }

    public String getPseudoSubject() {
        String str = this.mPseudoSubject;
        return str == null ? "" : str;
    }

    public long getTimestampMillis() {
        return this.mScTimeMillis;
    }

    public boolean isEmail() {
        return this.mIsEmail;
    }

    public String getEmailBody() {
        return this.mEmailBody;
    }

    public String getEmailFrom() {
        return this.mEmailFrom;
    }

    public byte[] getUserData() {
        return this.mUserData;
    }

    public SmsHeader getUserDataHeader() {
        return this.mUserDataHeader;
    }

    public byte[] getPdu() {
        return this.mPdu;
    }

    public int getStatusOnIcc() {
        return this.mStatusOnIcc;
    }

    public int getIndexOnIcc() {
        return this.mIndexOnIcc;
    }

    protected void parseMessageBody() {
        SmsAddress smsAddress = this.mOriginatingAddress;
        if (smsAddress == null || !smsAddress.couldBeEmailGateway()) {
            return;
        }
        if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId).getMnoName().toUpperCase().contains("ETISALAT_AE")) {
            Rlog.d(LOG_TAG, "Ignore e-mail gateway for Etisalat_AE");
            return;
        }
        SmsHeader smsHeader = this.mUserDataHeader;
        if (smsHeader != null && smsHeader.concatRef != null && this.mUserDataHeader.concatRef.seqNumber != 1) {
            Rlog.d(LOG_TAG, "Concatnated message and not the first page. no e-mail gateway");
        } else {
            extractEmailAddressFromMessageBody();
        }
    }

    private static String extractAddrSpec(String str) {
        Matcher matcher = NAME_ADDR_EMAIL_PATTERN.matcher(str);
        return matcher.matches() ? matcher.group(2) : str;
    }

    public static boolean isEmailAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(extractAddrSpec(str)).matches();
    }

    protected void extractEmailAddressFromMessageBody() {
        String[] split = this.mMessageBody.split("( /)|( )", 2);
        if (split.length < 2) {
            return;
        }
        this.mEmailFrom = split[0];
        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            int length = this.mEmailFrom.length();
            int indexOf = this.mEmailFrom.indexOf(64);
            int lastIndexOf = this.mEmailFrom.lastIndexOf(64);
            int i = lastIndexOf + 1;
            int indexOf2 = this.mEmailFrom.indexOf(46, i);
            int lastIndexOf2 = this.mEmailFrom.lastIndexOf(46);
            if (indexOf <= 0 || indexOf != lastIndexOf || i >= indexOf2 || indexOf2 > lastIndexOf2 || lastIndexOf2 >= length - 1) {
                return;
            }
            this.mEmailBody = split[1];
            this.mIsEmail = true;
            return;
        }
        this.mEmailBody = split[1];
        this.mIsEmail = isEmailAddress(this.mEmailFrom);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x007d, code lost:
    
        return r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int findNextUnicodePosition(int r4, int r5, java.lang.CharSequence r6) {
        /*
            int r0 = r5 / 2
            int r0 = r0 + r4
            int r1 = r6.length()
            int r0 = java.lang.Math.min(r0, r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "currentPosition = "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = " byteLimit= "
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = " msgBody.length()= "
            r1.append(r5)
            int r5 = r6.length()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            java.lang.String r1 = "SmsMessageBase"
            com.android.telephony.Rlog.d(r1, r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r2 = "nextPos = "
            r5.<init>(r2)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.android.telephony.Rlog.d(r1, r5)
            int r5 = r6.length()     // Catch: java.lang.IllegalArgumentException -> L8d
            if (r0 >= r5) goto L92
            java.text.BreakIterator r5 = java.text.BreakIterator.getCharacterInstance()     // Catch: java.lang.IllegalArgumentException -> L8d
            java.lang.String r2 = r6.toString()     // Catch: java.lang.IllegalArgumentException -> L8d
            r5.setText(r2)     // Catch: java.lang.IllegalArgumentException -> L8d
            boolean r2 = r5.isBoundary(r0)     // Catch: java.lang.IllegalArgumentException -> L8d
            if (r2 != 0) goto L92
            int r5 = r5.preceding(r0)     // Catch: java.lang.IllegalArgumentException -> L8d
        L5f:
            int r2 = r5 + 4
            if (r2 > r0) goto L7b
            int r3 = java.lang.Character.codePointAt(r6, r5)     // Catch: java.lang.IllegalArgumentException -> L8d
            boolean r3 = isRegionalIndicatorSymbol(r3)     // Catch: java.lang.IllegalArgumentException -> L8d
            if (r3 == 0) goto L7b
            int r3 = r5 + 2
            int r3 = java.lang.Character.codePointAt(r6, r3)     // Catch: java.lang.IllegalArgumentException -> L8d
            boolean r3 = isRegionalIndicatorSymbol(r3)     // Catch: java.lang.IllegalArgumentException -> L8d
            if (r3 == 0) goto L7b
            r5 = r2
            goto L5f
        L7b:
            if (r5 <= r4) goto L7e
            return r5
        L7e:
            int r4 = r0 + (-1)
            char r4 = r6.charAt(r4)     // Catch: java.lang.IllegalArgumentException -> L8d
            boolean r4 = java.lang.Character.isHighSurrogate(r4)     // Catch: java.lang.IllegalArgumentException -> L8d
            if (r4 == 0) goto L92
            int r0 = r0 + (-1)
            return r0
        L8d:
            java.lang.String r4 = "IllegalArgumentException"
            com.android.telephony.Rlog.e(r1, r4)
        L92:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsMessageBase.findNextUnicodePosition(int, int, java.lang.CharSequence):int");
    }

    public static GsmAlphabet.TextEncodingDetails calcUnicodeEncodingDetails(CharSequence charSequence) {
        GsmAlphabet.TextEncodingDetails textEncodingDetails = new GsmAlphabet.TextEncodingDetails();
        int length = charSequence.length() * 2;
        textEncodingDetails.codeUnitSize = 3;
        textEncodingDetails.codeUnitCount = charSequence.length();
        if (length > 140) {
            int i = (SmsMessage.hasEmsSupport() || length > 1188) ? 134 : 132;
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequence.length()) {
                int findNextUnicodePosition = findNextUnicodePosition(i2, i, charSequence);
                if (findNextUnicodePosition == charSequence.length()) {
                    textEncodingDetails.codeUnitsRemaining = ((i / 2) + i2) - charSequence.length();
                }
                if (findNextUnicodePosition <= i2 || findNextUnicodePosition > charSequence.length()) {
                    Log.e(LOG_TAG, "findNextUnicodePosition() isn`t working.(" + i2 + " >= " + findNextUnicodePosition + " or " + findNextUnicodePosition + " >= " + charSequence.length() + NavigationBarInflaterView.KEY_CODE_END);
                    i3 = ((i + (-1)) + length) / i;
                    textEncodingDetails.codeUnitsRemaining = ((i * i3) - length) / 2;
                    break;
                }
                i3++;
                i2 = findNextUnicodePosition;
            }
            textEncodingDetails.msgCount = i3;
            return textEncodingDetails;
        }
        textEncodingDetails.msgCount = 1;
        textEncodingDetails.codeUnitsRemaining = (140 - length) / 2;
        return textEncodingDetails;
    }

    public String getRecipientAddress() {
        SmsAddress smsAddress = this.mRecipientAddress;
        if (smsAddress == null) {
            return null;
        }
        return smsAddress.getAddressString();
    }

    public int getReceivedEncodingType() {
        return this.mReceivedEncodingType;
    }

    protected void setSubId(int i) {
        mSubId = i;
    }

    protected static int getSubId() {
        return mSubId;
    }

    public void replaceMessageBody(String str) {
        this.mMessageBody = str;
    }

    public boolean getIsFourBytesUnicode() {
        return this.mIsfourBytesUnicode;
    }

    public int getBodyOffset() {
        return this.mBodyOffset;
    }

    public byte[] getLastByte() {
        return this.mlastByte;
    }

    public int getDestPortAddr() {
        SmsHeader smsHeader = this.mUserDataHeader;
        if (smsHeader == null || smsHeader.portAddrs == null) {
            return -1;
        }
        return this.mUserDataHeader.portAddrs.destPort;
    }

    public int getReadConfirmId() {
        SmsHeader smsHeader = this.mUserDataHeader;
        if (smsHeader == null || smsHeader.ktReadConfirm == null) {
            return -1;
        }
        return this.mUserDataHeader.ktReadConfirm.readConfirmID;
    }

    public boolean getSafeMessageIndication() {
        SmsHeader smsHeader = this.mUserDataHeader;
        if (smsHeader != null) {
            return smsHeader.safeMessageIndication;
        }
        return false;
    }

    public boolean getLinkWarningIndication() {
        SmsHeader smsHeader = this.mUserDataHeader;
        if (smsHeader != null) {
            return smsHeader.linkWarningIndication;
        }
        return false;
    }

    public String getReplyAddress() {
        SmsAddress smsAddress = this.replyAddress;
        if (smsAddress == null) {
            return null;
        }
        return smsAddress.getAddressString();
    }

    public String getOriginalOriginatingAddress() {
        if (this.mIsEmail) {
            return this.mEmailFrom;
        }
        return getOriginatingAddress();
    }

    public String getlinkUrl() {
        return this.linkUrl;
    }

    public String getSharedAppId() {
        return this.mSharedAppID;
    }

    public String getSharedCmd() {
        return this.mSharedCmd;
    }

    public String getSharedPayLoad() {
        return this.mSharedPayLoad;
    }

    public int getTeleserviceId() {
        return this.mTeleserviceId;
    }

    public String getCallbackNumber() {
        return this.callbackNumber;
    }

    public byte[] getBearerData() {
        return this.bearerData;
    }

    public int getMessageType() {
        return this.mMti;
    }

    protected void parseSpecificTid(int i) {
        switch (i) {
            case 4097:
                String str = this.mMessageBody;
                if (str == null || str.length() == 0) {
                    this.mMessageBody = String.valueOf(pagingText);
                    break;
                } else {
                    this.mMessageBody = String.valueOf(pagingText) + ShaderAssembler.NEWLINE + this.mMessageBody;
                    break;
                }
                break;
            case 4099:
            case 262144:
                this.mMessageBody = String.valueOf(voiceMailText);
                break;
            case 49162:
                parseLGTSharingNoti();
                break;
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49166 /* 49166 */:
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49167 /* 49167 */:
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49168 /* 49168 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_THIRD_49763 /* 49763 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_LGT_49765 /* 49765 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_CP_49767 /* 49767 */:
                parseLGTWebNWapNoti(i);
                break;
        }
    }

    private void parseLGTWebNWapNoti(int i) {
        String str;
        int indexOf = this.mMessageBody.indexOf(29);
        if (indexOf != -1) {
            str = this.mMessageBody.substring(0, indexOf);
            int indexOf2 = this.mMessageBody.indexOf(3);
            if (indexOf2 == -1) {
                indexOf2 = this.mMessageBody.length();
            }
            if (indexOf2 == -1 || indexOf > indexOf2) {
                Log.e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_ETX");
            } else {
                this.linkUrl = this.mMessageBody.substring(indexOf, indexOf2).trim();
            }
        } else {
            str = this.mMessageBody;
            Log.e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_GS");
        }
        switch (i) {
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49166 /* 49166 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_THIRD_49763 /* 49763 */:
                this.mMessageBody = String.valueOf(thirdPartyText) + ShaderAssembler.NEWLINE + str + ShaderAssembler.NEWLINE + String.valueOf(connectText);
                break;
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49167 /* 49167 */:
                this.mMessageBody = String.valueOf(dataText) + ShaderAssembler.NEWLINE + str;
                break;
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49168 /* 49168 */:
                this.mMessageBody = String.valueOf(lguText) + ShaderAssembler.NEWLINE + str;
                break;
            case SmsEnvelope.TELESERVICE_LGT_WEB_LGT_49765 /* 49765 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_CP_49767 /* 49767 */:
                this.mMessageBody = String.valueOf(webText) + ShaderAssembler.NEWLINE + str + ShaderAssembler.NEWLINE + String.valueOf(connectText);
                break;
        }
    }

    private void parseLGTSharingNoti() {
        StringTokenizer stringTokenizer = new StringTokenizer(this.mMessageBody, String.valueOf((char) 29));
        String str = "";
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String trim = stringTokenizer.nextToken().trim();
            if (i == 0) {
                str = trim;
            } else if (i == 1) {
                this.mSharedAppID = trim;
            } else if (i == 2) {
                this.mSharedCmd = trim;
            } else if (i == 3) {
                this.mSharedPayLoad = trim;
                int lastIndexOf = trim.lastIndexOf(String.valueOf((char) 3));
                if (lastIndexOf != -1) {
                    this.mSharedPayLoad = this.mSharedPayLoad.substring(0, lastIndexOf);
                }
            }
            i++;
        }
        this.mMessageBody = str;
    }

    protected static int getSubId(int i) {
        int[] subId = SubscriptionManager.getSubId(i);
        if (subId == null || subId.length <= 0) {
            return -1;
        }
        return subId[0];
    }
}
