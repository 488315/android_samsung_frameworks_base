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
import java.text.BreakIterator;
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
        String[] strArrSplit = this.mMessageBody.split("( /)|( )", 2);
        if (strArrSplit.length < 2) {
            return;
        }
        this.mEmailFrom = strArrSplit[0];
        if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId).getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
            int length = this.mEmailFrom.length();
            int iIndexOf = this.mEmailFrom.indexOf(64);
            int iLastIndexOf = this.mEmailFrom.lastIndexOf(64);
            int i = iLastIndexOf + 1;
            int iIndexOf2 = this.mEmailFrom.indexOf(46, i);
            int iLastIndexOf2 = this.mEmailFrom.lastIndexOf(46);
            if (iIndexOf <= 0 || iIndexOf != iLastIndexOf || i >= iIndexOf2 || iIndexOf2 > iLastIndexOf2 || iLastIndexOf2 >= length - 1) {
                return;
            }
            this.mEmailBody = strArrSplit[1];
            this.mIsEmail = true;
            return;
        }
        this.mEmailBody = strArrSplit[1];
        this.mIsEmail = isEmailAddress(this.mEmailFrom);
    }

    public static int findNextUnicodePosition(int i, int i2, CharSequence charSequence) {
        int iMin = Math.min((i2 / 2) + i, charSequence.length());
        Rlog.d(LOG_TAG, "currentPosition = " + i + " byteLimit= " + i2 + " msgBody.length()= " + charSequence.length());
        StringBuilder sb = new StringBuilder("nextPos = ");
        sb.append(iMin);
        Rlog.d(LOG_TAG, sb.toString());
        try {
            if (iMin < charSequence.length()) {
                BreakIterator characterInstance = BreakIterator.getCharacterInstance();
                characterInstance.setText(charSequence.toString());
                if (!characterInstance.isBoundary(iMin)) {
                    int iPreceding = characterInstance.preceding(iMin);
                    while (true) {
                        int i3 = iPreceding + 4;
                        if (i3 > iMin || !isRegionalIndicatorSymbol(Character.codePointAt(charSequence, iPreceding)) || !isRegionalIndicatorSymbol(Character.codePointAt(charSequence, iPreceding + 2))) {
                            break;
                        }
                        iPreceding = i3;
                    }
                    if (iPreceding > i) {
                        return iPreceding;
                    }
                    if (Character.isHighSurrogate(charSequence.charAt(iMin - 1))) {
                        return iMin - 1;
                    }
                }
            }
        } catch (IllegalArgumentException unused) {
            Rlog.e(LOG_TAG, "IllegalArgumentException");
        }
        return iMin;
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
                int iFindNextUnicodePosition = findNextUnicodePosition(i2, i, charSequence);
                if (iFindNextUnicodePosition == charSequence.length()) {
                    textEncodingDetails.codeUnitsRemaining = ((i / 2) + i2) - charSequence.length();
                }
                if (iFindNextUnicodePosition <= i2 || iFindNextUnicodePosition > charSequence.length()) {
                    Log.e(LOG_TAG, "findNextUnicodePosition() isn`t working.(" + i2 + " >= " + iFindNextUnicodePosition + " or " + iFindNextUnicodePosition + " >= " + charSequence.length() + NavigationBarInflaterView.KEY_CODE_END);
                    i3 = ((i + (-1)) + length) / i;
                    textEncodingDetails.codeUnitsRemaining = ((i * i3) - length) / 2;
                    break;
                }
                i3++;
                i2 = iFindNextUnicodePosition;
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
        String strSubstring;
        int iIndexOf = this.mMessageBody.indexOf(29);
        if (iIndexOf != -1) {
            strSubstring = this.mMessageBody.substring(0, iIndexOf);
            int iIndexOf2 = this.mMessageBody.indexOf(3);
            if (iIndexOf2 == -1) {
                iIndexOf2 = this.mMessageBody.length();
            }
            if (iIndexOf2 == -1 || iIndexOf > iIndexOf2) {
                Log.e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_ETX");
            } else {
                this.linkUrl = this.mMessageBody.substring(iIndexOf, iIndexOf2).trim();
            }
        } else {
            strSubstring = this.mMessageBody;
            Log.e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_GS");
        }
        switch (i) {
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49166 /* 49166 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_THIRD_49763 /* 49763 */:
                this.mMessageBody = String.valueOf(thirdPartyText) + ShaderAssembler.NEWLINE + strSubstring + ShaderAssembler.NEWLINE + String.valueOf(connectText);
                break;
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49167 /* 49167 */:
                this.mMessageBody = String.valueOf(dataText) + ShaderAssembler.NEWLINE + strSubstring;
                break;
            case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49168 /* 49168 */:
                this.mMessageBody = String.valueOf(lguText) + ShaderAssembler.NEWLINE + strSubstring;
                break;
            case SmsEnvelope.TELESERVICE_LGT_WEB_LGT_49765 /* 49765 */:
            case SmsEnvelope.TELESERVICE_LGT_WEB_CP_49767 /* 49767 */:
                this.mMessageBody = String.valueOf(webText) + ShaderAssembler.NEWLINE + strSubstring + ShaderAssembler.NEWLINE + String.valueOf(connectText);
                break;
        }
    }

    private void parseLGTSharingNoti() {
        StringTokenizer stringTokenizer = new StringTokenizer(this.mMessageBody, String.valueOf((char) 29));
        String str = "";
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strTrim = stringTokenizer.nextToken().trim();
            if (i == 0) {
                str = strTrim;
            } else if (i == 1) {
                this.mSharedAppID = strTrim;
            } else if (i == 2) {
                this.mSharedCmd = strTrim;
            } else if (i == 3) {
                this.mSharedPayLoad = strTrim;
                int iLastIndexOf = strTrim.lastIndexOf(String.valueOf((char) 3));
                if (iLastIndexOf != -1) {
                    this.mSharedPayLoad = this.mSharedPayLoad.substring(0, iLastIndexOf);
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
