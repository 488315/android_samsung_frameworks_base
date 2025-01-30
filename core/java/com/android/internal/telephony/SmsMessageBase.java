package com.android.internal.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telephony.Rlog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
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
  public static final Pattern NAME_ADDR_EMAIL_PATTERN =
      Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");
  private static int mSubId = SubscriptionManager.getDefaultSmsSubscriptionId();
  private static final char[] voiceMailText = {
    49352, 47196, 50868, ' ', 51020, 49457, 47700, 51068, 51060, ' ', 46020, 52265, 54664, 49845,
    45768, 45796, '.', 53685, 54868, 53412, 47484, ' ', 45572, 47476, 47732, ' ', 51088, 46041,
    50672, 44208, 46121, 45768, 45796, '.'
  };
  private static final char[] pagingText = {'[', 54840, 52636, 47700, 49884, 51648, ']'};
  private static final char[] thirdPartyText = {
    '[', 50808, 48512, 49324, 50629, 51088, ' ', 50672, 44208, ']'
  };
  private static final char[] webText = {'[', 50937, 49436, 54609, ' ', 50672, 44208, ']'};
  private static final char[] dataText = {
    '[',
    DateFormat.STANDALONE_MONTH,
    'G',
    ' ',
    'U',
    '+',
    ' ',
    47924,
    49440,
    51064,
    53552,
    45367,
    ']'
  };
  private static final char[] lguText = {
    '[', DateFormat.STANDALONE_MONTH, 'G', ' ', 'U', '+', ' ', 50504, 45236, ']'
  };
  private static final char[] connectText = {
    50672, 44208, ' ', 54616, 49884, 44192, 49845, 45768, 44620, '?'
  };
  protected int mReceivedEncodingType = 0;
  protected int mStatusOnIcc = -1;
  protected int mIndexOnIcc = -1;
  protected String linkUrl = null;
  protected String mSharedAppID = null;
  protected String mSharedCmd = null;
  protected String mSharedPayLoad = null;
  protected byte[] bearerData = null;

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

  public abstract static class SubmitPduBase {
    public byte[] encodedMessage;
    public byte[] encodedScAddress;

    public String toString() {
      return "SubmitPdu: encodedScAddress = "
          + Arrays.toString(this.encodedScAddress)
          + ", encodedMessage = "
          + Arrays.toString(this.encodedMessage);
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
    if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId)
        .getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
      String simOperator =
          TelephonyManager.getTelephonyProperty(
              SubscriptionManager.getPhoneId(mSubId),
              TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC,
              "00000");
      if (simOperator == null || simOperator.startsWith("450") || simOperator.startsWith("001")) {
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
    if (smsAddress != null && smsAddress.couldBeEmailGateway()) {
      if (SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId)
          .getMnoName()
          .toUpperCase()
          .contains("ETISALAT_AE")) {
        Rlog.m77d(LOG_TAG, "Ignore e-mail gateway for Etisalat_AE");
        return;
      }
      SmsHeader smsHeader = this.mUserDataHeader;
      if (smsHeader != null
          && smsHeader.concatRef != null
          && this.mUserDataHeader.concatRef.seqNumber != 1) {
        Rlog.m77d(LOG_TAG, "Concatnated message and not the first page. no e-mail gateway");
      } else {
        extractEmailAddressFromMessageBody();
      }
    }
  }

  private static String extractAddrSpec(String messageHeader) {
    Matcher match = NAME_ADDR_EMAIL_PATTERN.matcher(messageHeader);
    if (match.matches()) {
      return match.group(2);
    }
    return messageHeader;
  }

  public static boolean isEmailAddress(String messageHeader) {
    if (TextUtils.isEmpty(messageHeader)) {
      return false;
    }
    String s = extractAddrSpec(messageHeader);
    Matcher match = Patterns.EMAIL_ADDRESS.matcher(s);
    return match.matches();
  }

  protected void extractEmailAddressFromMessageBody() {
    String[] parts = this.mMessageBody.split("( /)|( )", 2);
    if (parts.length < 2) {
      return;
    }
    this.mEmailFrom = parts[0];
    if (!SmsManager.getSmsManagerForContextAndSubscriptionId(null, mSubId)
        .getSmsSetting(SmsConstants.SMS_SUPPORT_REPLY_ADDRESS)) {
      int len = this.mEmailFrom.length();
      int firstAt = this.mEmailFrom.indexOf(64);
      int lastAt = this.mEmailFrom.lastIndexOf(64);
      int firstDot = this.mEmailFrom.indexOf(46, lastAt + 1);
      int lastDot = this.mEmailFrom.lastIndexOf(46);
      if (firstAt > 0
          && firstAt == lastAt
          && lastAt + 1 < firstDot
          && firstDot <= lastDot
          && lastDot < len - 1) {
        this.mEmailBody = parts[1];
        this.mIsEmail = true;
        return;
      }
      return;
    }
    this.mEmailBody = parts[1];
    this.mIsEmail = isEmailAddress(this.mEmailFrom);
  }

  /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:

     return r3;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static int findNextUnicodePosition(
      int currentPosition, int byteLimit, CharSequence msgBody) {
    int nextPos = Math.min((byteLimit / 2) + currentPosition, msgBody.length());
    Rlog.m77d(
        LOG_TAG,
        "currentPosition = "
            + currentPosition
            + " byteLimit= "
            + byteLimit
            + " msgBody.length()= "
            + msgBody.length());
    Rlog.m77d(LOG_TAG, "nextPos = " + nextPos);
    try {
      if (nextPos < msgBody.length()) {
        BreakIterator breakIterator = BreakIterator.getCharacterInstance();
        breakIterator.setText(msgBody.toString());
        if (!breakIterator.isBoundary(nextPos)) {
          int breakPos = breakIterator.preceding(nextPos);
          while (breakPos + 4 <= nextPos
              && isRegionalIndicatorSymbol(Character.codePointAt(msgBody, breakPos))
              && isRegionalIndicatorSymbol(Character.codePointAt(msgBody, breakPos + 2))) {
            breakPos += 4;
          }
          if (Character.isHighSurrogate(msgBody.charAt(nextPos - 1))) {
            return nextPos - 1;
          }
          return nextPos;
        }
        return nextPos;
      }
      return nextPos;
    } catch (IllegalArgumentException e) {
      Rlog.m79e(LOG_TAG, "IllegalArgumentException");
      return nextPos;
    }
  }

  private static boolean isRegionalIndicatorSymbol(int codePoint) {
    return 127462 <= codePoint && codePoint <= 127487;
  }

  public static GsmAlphabet.TextEncodingDetails calcUnicodeEncodingDetails(CharSequence msgBody) {
    GsmAlphabet.TextEncodingDetails ted = new GsmAlphabet.TextEncodingDetails();
    int octets = msgBody.length() * 2;
    ted.codeUnitSize = 3;
    ted.codeUnitCount = msgBody.length();
    if (octets > 140) {
      int maxUserDataBytesWithHeader = 134;
      if (!SmsMessage.hasEmsSupport() && octets <= (134 - 2) * 9) {
        maxUserDataBytesWithHeader = 134 - 2;
      }
      int pos = 0;
      int msgCount = 0;
      while (pos < msgBody.length()) {
        int nextPos = findNextUnicodePosition(pos, maxUserDataBytesWithHeader, msgBody);
        if (nextPos == msgBody.length()) {
          ted.codeUnitsRemaining = ((maxUserDataBytesWithHeader / 2) + pos) - msgBody.length();
        }
        if (nextPos <= pos || nextPos > msgBody.length()) {
          Log.m96e(
              LOG_TAG,
              "findNextUnicodePosition() isn`t working.("
                  + pos
                  + " >= "
                  + nextPos
                  + " or "
                  + nextPos
                  + " >= "
                  + msgBody.length()
                  + NavigationBarInflaterView.KEY_CODE_END);
          msgCount = ((maxUserDataBytesWithHeader - 1) + octets) / maxUserDataBytesWithHeader;
          ted.codeUnitsRemaining = ((msgCount * maxUserDataBytesWithHeader) - octets) / 2;
          break;
        }
        pos = nextPos;
        msgCount++;
      }
      ted.msgCount = msgCount;
    } else {
      ted.msgCount = 1;
      ted.codeUnitsRemaining = (140 - octets) / 2;
    }
    return ted;
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

  protected void setSubId(int subId) {
    mSubId = subId;
  }

  protected static int getSubId() {
    return mSubId;
  }

  public void replaceMessageBody(String messasgeBody) {
    this.mMessageBody = messasgeBody;
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
    if (smsHeader != null && smsHeader.portAddrs != null) {
      return this.mUserDataHeader.portAddrs.destPort;
    }
    return -1;
  }

  public int getReadConfirmId() {
    SmsHeader smsHeader = this.mUserDataHeader;
    if (smsHeader != null && smsHeader.ktReadConfirm != null) {
      return this.mUserDataHeader.ktReadConfirm.readConfirmID;
    }
    return -1;
  }

  public boolean getSafeMessageIndication() {
    SmsHeader smsHeader = this.mUserDataHeader;
    if (smsHeader != null) {
      return smsHeader.safeMessageIndication;
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

  public int getCDMAMessageType() {
    return 0;
  }

  protected void parseSpecificTid(int tid) {
    switch (tid) {
      case 4097:
        String str = this.mMessageBody;
        if (str == null || str.length() == 0) {
          this.mMessageBody = String.valueOf(pagingText);
          break;
        } else {
          this.mMessageBody = String.valueOf(pagingText) + "\n" + this.mMessageBody;
          break;
        }
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
        parseLGTWebNWapNoti(tid);
        break;
    }
  }

  private void parseLGTWebNWapNoti(int tid) {
    String destBody;
    int gs = this.mMessageBody.indexOf(29);
    if (gs != -1) {
      destBody = this.mMessageBody.substring(0, gs);
      int etx = this.mMessageBody.indexOf(3);
      if (etx == -1) {
        etx = this.mMessageBody.length();
      }
      if (etx == -1 || gs > etx) {
        Log.m96e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_ETX");
      } else {
        this.linkUrl = this.mMessageBody.substring(gs, etx).trim();
      }
    } else {
      destBody = this.mMessageBody;
      Log.m96e(LOG_TAG, "parseLGTWapUrlNoti parsing error...  DELIMITER_GS");
    }
    switch (tid) {
      case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49166 /* 49166 */:
      case SmsEnvelope.TELESERVICE_LGT_WEB_THIRD_49763 /* 49763 */:
        this.mMessageBody =
            String.valueOf(thirdPartyText) + "\n" + destBody + "\n" + String.valueOf(connectText);
        break;
      case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49167 /* 49167 */:
        this.mMessageBody = String.valueOf(dataText) + "\n" + destBody;
        break;
      case SmsEnvelope.TELESERVICE_LGT_WAP_URL_NOTI_49168 /* 49168 */:
        this.mMessageBody = String.valueOf(lguText) + "\n" + destBody;
        break;
      case SmsEnvelope.TELESERVICE_LGT_WEB_LGT_49765 /* 49765 */:
      case SmsEnvelope.TELESERVICE_LGT_WEB_CP_49767 /* 49767 */:
        this.mMessageBody =
            String.valueOf(webText) + "\n" + destBody + "\n" + String.valueOf(connectText);
        break;
    }
  }

  private void parseLGTSharingNoti() {
    String destBody = "";
    StringTokenizer tokenizer = new StringTokenizer(this.mMessageBody, String.valueOf((char) 29));
    int i = 0;
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken().trim();
      if (i == 0) {
        destBody = token;
      } else if (i == 1) {
        this.mSharedAppID = token;
      } else if (i == 2) {
        this.mSharedCmd = token;
      } else if (i == 3) {
        this.mSharedPayLoad = token;
        int index = token.lastIndexOf(String.valueOf((char) 3));
        if (index != -1) {
          this.mSharedPayLoad = this.mSharedPayLoad.substring(0, index);
        }
      }
      i++;
    }
    this.mMessageBody = destBody;
  }

  protected static int getSubId(int phoneId) {
    int[] subIds = SubscriptionManager.getSubId(phoneId);
    if (subIds != null && subIds.length > 0) {
      return subIds[0];
    }
    return -1;
  }
}
