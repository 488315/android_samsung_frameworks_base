package android.telephony;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.compat.Compatibility;
import android.content.Context;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.IPhoneSubInfo;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.SmsRawData;
import com.android.internal.telephony.TelephonyProperties;
import com.android.internal.telephony.uicc.IccUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class SmsManager {
    public static final int CDMA_SMS_RECORD_LENGTH = 255;
    public static final String EXTRA_MMS_DATA = "android.telephony.extra.MMS_DATA";
    public static final String EXTRA_MMS_HTTP_STATUS = "android.telephony.extra.MMS_HTTP_STATUS";
    public static final String EXTRA_SIM_SUBSCRIPTION_ID = "android.telephony.extra.SIM_SUBSCRIPTION_ID";
    public static final String EXTRA_SMS_MESSAGE = "android.telephony.extra.SMS_MESSAGE";
    public static final String EXTRA_STATUS = "android.telephony.extra.STATUS";
    private static final long GET_TARGET_SDK_VERSION_CODE_CHANGE = 145147528;
    public static final int ICC_TYPE_AUTO = -1;
    public static final int ICC_TYPE_CSIM = 4;
    public static final int ICC_TYPE_CSIM_DEACTIVE = 10;
    public static final int ICC_TYPE_ISIM = 5;
    public static final int ICC_TYPE_RUIM = 3;
    public static final int ICC_TYPE_SIM = 1;
    public static final int ICC_TYPE_UNKNOW = 0;
    public static final int ICC_TYPE_USIM = 2;
    private static final String ISIS_PACKAGE_NAME = "com.isis.mclient.verizon.activity";
    public static final String MMS_CONFIG_ALIAS_ENABLED = "aliasEnabled";
    public static final String MMS_CONFIG_ALIAS_MAX_CHARS = "aliasMaxChars";
    public static final String MMS_CONFIG_ALIAS_MIN_CHARS = "aliasMinChars";
    public static final String MMS_CONFIG_ALLOW_ATTACH_AUDIO = "allowAttachAudio";
    public static final String MMS_CONFIG_APPEND_TRANSACTION_ID = "enabledTransID";
    public static final String MMS_CONFIG_CLOSE_CONNECTION = "mmsCloseConnection";
    public static final String MMS_CONFIG_EMAIL_GATEWAY_NUMBER = "emailGatewayNumber";
    public static final String MMS_CONFIG_GROUP_MMS_ENABLED = "enableGroupMms";
    public static final String MMS_CONFIG_HTTP_PARAMS = "httpParams";
    public static final String MMS_CONFIG_HTTP_SOCKET_TIMEOUT = "httpSocketTimeout";
    public static final String MMS_CONFIG_MAX_IMAGE_HEIGHT = "maxImageHeight";
    public static final String MMS_CONFIG_MAX_IMAGE_WIDTH = "maxImageWidth";
    public static final String MMS_CONFIG_MAX_MESSAGE_SIZE = "maxMessageSize";
    public static final String MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE = "maxMessageTextSize";
    public static final String MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED = "enableMMSDeliveryReports";
    public static final String MMS_CONFIG_MMS_ENABLED = "enabledMMS";
    public static final String MMS_CONFIG_MMS_READ_REPORT_ENABLED = "enableMMSReadReports";
    public static final String MMS_CONFIG_MULTIPART_SMS_ENABLED = "enableMultipartSMS";
    public static final String MMS_CONFIG_NAI_SUFFIX = "naiSuffix";
    public static final String MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED = "enabledNotifyWapMMSC";
    public static final String MMS_CONFIG_RECIPIENT_LIMIT = "recipientLimit";
    public static final String MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES = "sendMultipartSmsAsSeparateMessages";
    public static final String MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS = "config_cellBroadcastAppLinks";
    public static final String MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED = "enableSMSDeliveryReports";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD = "smsToMmsTextLengthThreshold";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD = "smsToMmsTextThreshold";
    public static final String MMS_CONFIG_SUBJECT_MAX_LENGTH = "maxSubjectLength";
    public static final String MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER = "supportHttpCharsetHeader";
    public static final String MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION = "supportMmsContentDisposition";
    public static final String MMS_CONFIG_UA_PROF_TAG_NAME = "uaProfTagName";
    public static final String MMS_CONFIG_UA_PROF_URL = "uaProfUrl";
    public static final String MMS_CONFIG_USER_AGENT = "userAgent";
    public static final int MMS_ERROR_CONFIGURATION_ERROR = 7;
    public static final int MMS_ERROR_DATA_DISABLED = 11;
    public static final int MMS_ERROR_HTTP_FAILURE = 4;
    public static final int MMS_ERROR_INACTIVE_SUBSCRIPTION = 10;
    public static final int MMS_ERROR_INVALID_APN = 2;
    public static final int MMS_ERROR_INVALID_SUBSCRIPTION_ID = 9;
    public static final int MMS_ERROR_IO_ERROR = 5;
    public static final int MMS_ERROR_MMS_DISABLED_BY_CARRIER = 12;
    public static final int MMS_ERROR_NO_DATA_NETWORK = 8;
    public static final int MMS_ERROR_RETRY = 6;
    public static final int MMS_ERROR_TOO_LARGE_FOR_TRANSPORT = 13;
    public static final int MMS_ERROR_UNABLE_CONNECT_MMS = 3;
    public static final int MMS_ERROR_UNSPECIFIED = 1;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_ALWAYS_ALLOW = 3;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_ASK_USER = 1;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_NEVER_ALLOW = 2;

    @SystemApi
    public static final int PREMIUM_SMS_CONSENT_UNKNOWN = 0;
    public static final String REGEX_PREFIX_DELIMITER = ",";
    public static final int RESULT_BLUETOOTH_DISCONNECTED = 27;
    public static final int RESULT_CANCELLED = 23;
    public static final int RESULT_ENCODING_ERROR = 18;
    public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    public static final int RESULT_ERROR_LIMIT_EXCEEDED = 5;
    public static final int RESULT_ERROR_NONE = 0;
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    public static final int RESULT_ERROR_NULL_PDU = 3;
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    public static final int RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    public static final int RESULT_INTERNAL_ERROR = 21;
    public static final int RESULT_INVALID_ARGUMENTS = 11;
    public static final int RESULT_INVALID_BLUETOOTH_ADDRESS = 26;
    public static final int RESULT_INVALID_SMSC_ADDRESS = 19;
    public static final int RESULT_INVALID_SMS_FORMAT = 14;
    public static final int RESULT_INVALID_STATE = 12;
    public static final int RESULT_MODEM_ERROR = 16;
    public static final int RESULT_NETWORK_ERROR = 17;
    public static final int RESULT_NETWORK_REJECT = 10;
    public static final int RESULT_NO_BLUETOOTH_SERVICE = 25;
    public static final int RESULT_NO_DEFAULT_SMS_APP = 32;
    public static final int RESULT_NO_MEMORY = 13;
    public static final int RESULT_NO_RESOURCES = 22;
    public static final int RESULT_OPERATION_NOT_ALLOWED = 20;
    public static final int RESULT_RADIO_NOT_AVAILABLE = 9;
    public static final int RESULT_RECEIVE_DISPATCH_FAILURE = 500;
    public static final int RESULT_RECEIVE_INJECTED_NULL_PDU = 501;
    public static final int RESULT_RECEIVE_NULL_MESSAGE_FROM_RIL = 503;
    public static final int RESULT_RECEIVE_RUNTIME_EXCEPTION = 502;
    public static final int RESULT_RECEIVE_SQL_EXCEPTION = 505;
    public static final int RESULT_RECEIVE_URI_EXCEPTION = 506;
    public static final int RESULT_RECEIVE_WHILE_ENCRYPTED = 504;
    public static final int RESULT_REMOTE_EXCEPTION = 31;
    public static final int RESULT_REQUEST_NOT_SUPPORTED = 24;
    public static final int RESULT_RIL_ABORTED = 137;
    public static final int RESULT_RIL_ACCESS_BARRED = 122;
    public static final int RESULT_RIL_BLOCKED_DUE_TO_CALL = 123;
    public static final int RESULT_RIL_CANCELLED = 119;
    public static final int RESULT_RIL_DEVICE_IN_USE = 136;
    public static final int RESULT_RIL_ENCODING_ERR = 109;
    public static final int RESULT_RIL_GENERIC_ERROR = 124;
    public static final int RESULT_RIL_INTERNAL_ERR = 113;
    public static final int RESULT_RIL_INVALID_ARGUMENTS = 104;
    public static final int RESULT_RIL_INVALID_MODEM_STATE = 115;
    public static final int RESULT_RIL_INVALID_RESPONSE = 125;
    public static final int RESULT_RIL_INVALID_SIM_STATE = 130;
    public static final int RESULT_RIL_INVALID_SMSC_ADDRESS = 110;
    public static final int RESULT_RIL_INVALID_SMS_FORMAT = 107;
    public static final int RESULT_RIL_INVALID_STATE = 103;
    public static final int RESULT_RIL_MODEM_ERR = 111;
    public static final int RESULT_RIL_NETWORK_ERR = 112;
    public static final int RESULT_RIL_NETWORK_NOT_READY = 116;
    public static final int RESULT_RIL_NETWORK_REJECT = 102;
    public static final int RESULT_RIL_NO_MEMORY = 105;
    public static final int RESULT_RIL_NO_NETWORK_FOUND = 135;
    public static final int RESULT_RIL_NO_RESOURCES = 118;
    public static final int RESULT_RIL_NO_SMS_TO_ACK = 131;
    public static final int RESULT_RIL_NO_SUBSCRIPTION = 134;
    public static final int RESULT_RIL_OPERATION_NOT_ALLOWED = 117;
    public static final int RESULT_RIL_RADIO_NOT_AVAILABLE = 100;
    public static final int RESULT_RIL_REQUEST_NOT_SUPPORTED = 114;
    public static final int RESULT_RIL_REQUEST_RATE_LIMITED = 106;
    public static final int RESULT_RIL_SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED = 121;
    public static final int RESULT_RIL_SIM_ABSENT = 120;
    public static final int RESULT_RIL_SIM_BUSY = 132;
    public static final int RESULT_RIL_SIM_ERROR = 129;
    public static final int RESULT_RIL_SIM_FULL = 133;
    public static final int RESULT_RIL_SIM_PIN2 = 126;
    public static final int RESULT_RIL_SIM_PUK2 = 127;
    public static final int RESULT_RIL_SMS_SEND_FAIL_RETRY = 101;
    public static final int RESULT_RIL_SUBSCRIPTION_NOT_AVAILABLE = 128;
    public static final int RESULT_RIL_SYSTEM_ERR = 108;
    public static final int RESULT_SMS_BLOCKED_DURING_EMERGENCY = 29;
    public static final int RESULT_SMS_SEND_RETRY_FAILED = 30;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_TIMEOUT = 1;
    public static final int RESULT_SYSTEM_ERROR = 15;
    public static final int RESULT_UNEXPECTED_EVENT_STOP_SENDING = 28;
    public static final int RESULT_USER_NOT_ALLOWED = 33;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    public static final int SMS_RECORD_LENGTH = 176;
    public static final int SMS_RP_CAUSE_CALL_BARRING = 10;
    public static final int SMS_RP_CAUSE_CONGESTION = 42;
    public static final int SMS_RP_CAUSE_DESTINATION_OUT_OF_ORDER = 27;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int SMS_RP_CAUSE_FACILITY_REJECTED = 29;
    public static final int SMS_RP_CAUSE_INFORMATION_ELEMENT_NON_EXISTENT = 99;
    public static final int SMS_RP_CAUSE_INTERWORKING_UNSPECIFIED = 127;
    public static final int SMS_RP_CAUSE_INVALID_MANDATORY_INFORMATION = 96;
    public static final int SMS_RP_CAUSE_INVALID_MESSAGE_REFERENCE_VALUE = 81;
    public static final int SMS_RP_CAUSE_MESSAGE_INCOMPATIBLE_WITH_PROTOCOL_STATE = 98;
    public static final int SMS_RP_CAUSE_MESSAGE_TYPE_NON_EXISTENT = 97;
    public static final int SMS_RP_CAUSE_NETWORK_OUT_OF_ORDER = 38;
    public static final int SMS_RP_CAUSE_OPERATOR_DETERMINED_BARRING = 8;
    public static final int SMS_RP_CAUSE_PROTOCOL_ERROR = 111;
    public static final int SMS_RP_CAUSE_RESERVED = 11;
    public static final int SMS_RP_CAUSE_RESOURCES_UNAVAILABLE = 47;
    public static final int SMS_RP_CAUSE_SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SMS_RP_CAUSE_SHORT_MESSAGE_TRANSFER_REJECTED = 21;
    public static final int SMS_RP_CAUSE_TEMPORARY_FAILURE = 41;
    public static final int SMS_RP_CAUSE_UNALLOCATED_NUMBER = 1;
    public static final int SMS_RP_CAUSE_UNIDENTIFIED_SUBSCRIBER = 28;
    public static final int SMS_RP_CAUSE_UNKNOWN_SUBSCRIBER = 30;
    public static final int STATUS_ON_ICC_FREE = 0;
    public static final int STATUS_ON_ICC_READ = 1;
    public static final int STATUS_ON_ICC_SENT = 5;
    public static final int STATUS_ON_ICC_UNREAD = 3;
    public static final int STATUS_ON_ICC_UNSENT = 7;
    private static final String TAG = "SmsManager";
    public static final int VALUE_INPUT_MODE_AUTO = 2;
    public static final int VALUE_INPUT_MODE_GSM7BIT = 0;
    public static final int VALUE_INPUT_MODE_UCS2 = 1;
    private final Context mContext;
    private int mSubId;
    private static final Object sLockObject = new Object();
    private static final Map<Pair<Context, Integer>, SmsManager> sSubInstances = new ArrayMap();
    private static final SmsManager DEFAULT_INSTANCE = getSmsManagerForContextAndSubscriptionId(null, Integer.MAX_VALUE);
    static int mMsgEncodingType = 0;

    public static abstract class FinancialSmsCallback {
        public abstract void onFinancialSmsMessages(CursorWindow cursorWindow);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PremiumSmsConsent {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SMS_RP_CAUSE {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SmsShortCodeCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusOnIcc {
    }

    private interface SubscriptionResolverResult {
        void onFailure();

        void onSuccess(int i);
    }

    private int getMessageStatusForIcc(int i, boolean z) {
        if (i == 2) {
            return 5;
        }
        if (i == 4) {
            return 7;
        }
        return z ? 1 : 3;
    }

    public void getSmsMessagesForFinancialApp(Bundle bundle, Executor executor, FinancialSmsCallback financialSmsCallback) {
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        return context.getOpPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAttributionTag() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        return context.getAttributionTag();
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2, long j) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, getOpPackageName(), getAttributionTag(), j);
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i, boolean z, int i2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, i, z, i2);
    }

    private void sendTextMessageInternal(final String str, final String str2, final String str3, final PendingIntent pendingIntent, final PendingIntent pendingIntent2, final boolean z, final String str4, final String str5, final long j) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (z) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult(this) { // from class: android.telephony.SmsManager.1
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendTextForSubscriber(i, str4, str5, str, str2, str3, pendingIntent, pendingIntent2, z, j);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(j));
                        SmsManager.notifySmsError(pendingIntent, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
            return;
        }
        try {
            getISmsServiceOrThrow().sendTextForSubscriber(getSubscriptionId(), str4, str5, str, str2, str3, pendingIntent, pendingIntent2, z, j);
        } catch (RemoteException e) {
            Log.e(TAG, "sendTextMessageInternal (no persist): Couldn't send SMS, exception - " + e.getMessage() + " " + formatCrossStackMessageId(j));
            notifySmsError(pendingIntent, 31);
        }
    }

    public void sendTextMessageWithoutPersisting(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    private void sendTextMessageInternal(final String str, final String str2, final String str3, final PendingIntent pendingIntent, final PendingIntent pendingIntent2, final boolean z, int i, final boolean z2, int i2) {
        final int i3;
        final int i4;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (i < 0 || i > 3) {
            Log.e(TAG, "Invalid Priority " + i);
            i3 = -1;
        } else {
            i3 = i;
        }
        if (i2 < 5 || i2 > 635040) {
            Log.e(TAG, "Invalid Validity Period " + i2);
            i4 = -1;
        } else {
            i4 = i2;
        }
        if (z) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.2
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i5) {
                    try {
                        ISms iSmsServiceOrThrow = SmsManager.getISmsServiceOrThrow();
                        if (iSmsServiceOrThrow != null) {
                            iSmsServiceOrThrow.sendTextForSubscriberWithOptions(i5, null, SmsManager.this.getAttributionTag(), str, str2, str3, pendingIntent, pendingIntent2, z, i3, z2, i4);
                        }
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                        SmsManager.notifySmsError(pendingIntent, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
            return;
        }
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.sendTextForSubscriberWithOptions(getSubscriptionId(), null, getAttributionTag(), str, str2, str3, pendingIntent, pendingIntent2, z, i3, z2, i4);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "sendTextMessageInternal(no persist): Couldn't send SMS, exception - " + e.getMessage());
            notifySmsError(pendingIntent, 31);
        }
    }

    public void injectSmsPdu(byte[] bArr, String str, PendingIntent pendingIntent) {
        if (!str.equals("3gpp") && !str.equals("3gpp2")) {
            throw new IllegalArgumentException("Invalid pdu format. format must be either 3gpp or 3gpp2");
        }
        try {
            ISms smsService = TelephonyManager.getSmsService();
            if (smsService != null) {
                smsService.injectSmsPduForSubscriber(getSubscriptionId(), bArr, str, pendingIntent);
            }
        } catch (RemoteException unused) {
            if (pendingIntent != null) {
                try {
                    pendingIntent.send(31);
                } catch (PendingIntent.CanceledException unused2) {
                }
            }
        }
    }

    public ArrayList<String> divideMessage(String str) {
        if (str == null) {
            throw new IllegalArgumentException("text is null");
        }
        return SmsMessage.fragmentText(str, this);
    }

    public void sendMultipartTextMessage(String str, String str2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3) {
        sendMultipartTextMessageInternal(str, str2, (List<String>) arrayList, (List<PendingIntent>) arrayList2, (List<PendingIntent>) arrayList3, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(String str, String str2, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, long j) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, true, getOpPackageName(), getAttributionTag(), j);
    }

    public void sendMultipartTextMessage(String str, String str2, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str3, String str4) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, true, str3, str4, 0L);
    }

    private void sendMultipartTextMessageInternal(final String str, final String str2, final List<String> list, final List<PendingIntent> list2, final List<PendingIntent> list3, final boolean z, final String str3, final String str4, final long j) {
        List<PendingIntent> list4;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if ((list == null || list.size() < 1) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (list.size() <= 1) {
            PendingIntent pendingIntent = null;
            PendingIntent pendingIntent2 = (list2 == null || list2.size() <= 0) ? null : list2.get(0);
            if (list3 != null && list3.size() > 0) {
                pendingIntent = list3.get(0);
            }
            sendTextMessageInternal(str, str2, list.get(0), pendingIntent2, pendingIntent, true, str3, str4, j);
            return;
        }
        if (z) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult(this) { // from class: android.telephony.SmsManager.3
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendMultipartTextForSubscriber(i, str3, str4, str, str2, list, list2, list3, z, j);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + SmsManager.formatCrossStackMessageId(j));
                        SmsManager.notifySmsError((List<PendingIntent>) list2, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError((List<PendingIntent>) list2, 32);
                }
            });
            return;
        }
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                list4 = list2;
                try {
                    iSmsServiceOrThrow.sendMultipartTextForSubscriber(getSubscriptionId(), str3, str4, str, str2, list, list4, list3, z, j);
                } catch (RemoteException e) {
                    e = e;
                    Log.e(TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + formatCrossStackMessageId(j));
                    notifySmsError(list4, 31);
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            list4 = list2;
        }
    }

    @SystemApi
    public void sendMultipartTextMessageWithoutPersisting(String str, String str2, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(String str, String str2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, int i, boolean z, int i2) {
        sendMultipartTextMessageInternal(str, str2, (List<String>) arrayList, (List<PendingIntent>) arrayList2, (List<PendingIntent>) arrayList3, true, i, z, i2);
    }

    private void sendMultipartTextMessageInternal(final String str, final String str2, final List<String> list, final List<PendingIntent> list2, final List<PendingIntent> list3, final boolean z, int i, final boolean z2, int i2) {
        final int i3;
        int i4;
        List<PendingIntent> list4;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (list == null || list.size() < 1) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (i < 0 || i > 3) {
            Log.e(TAG, "Invalid Priority " + i);
            i3 = -1;
        } else {
            i3 = i;
        }
        if (i2 < 5 || i2 > 635040) {
            Log.e(TAG, "Invalid Validity Period " + i2);
            i4 = -1;
        } else {
            i4 = i2;
        }
        if (list.size() <= 1) {
            int i5 = i3;
            PendingIntent pendingIntent = null;
            PendingIntent pendingIntent2 = (list2 == null || list2.size() <= 0) ? null : list2.get(0);
            if (list3 != null && list3.size() > 0) {
                pendingIntent = list3.get(0);
            }
            sendTextMessageInternal(str, str2, list.get(0), pendingIntent2, pendingIntent, z, i5, z2, i4);
            return;
        }
        if (z) {
            final int i6 = i4;
            resolveSubscriptionForOperation(new SubscriptionResolverResult(this) { // from class: android.telephony.SmsManager.4
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i7) {
                    try {
                        ISms iSmsServiceOrThrow = SmsManager.getISmsServiceOrThrow();
                        if (iSmsServiceOrThrow != null) {
                            iSmsServiceOrThrow.sendMultipartTextForSubscriberWithOptions(i7, null, null, str, str2, list, list2, list3, z, i3, z2, i6);
                        }
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                        SmsManager.notifySmsError((List<PendingIntent>) list2, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError((List<PendingIntent>) list2, 32);
                }
            });
            return;
        }
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                list4 = list2;
                try {
                    iSmsServiceOrThrow.sendMultipartTextForSubscriberWithOptions(getSubscriptionId(), null, null, str, str2, list, list4, list3, z, i3, z2, i4);
                } catch (RemoteException e) {
                    e = e;
                    Log.e(TAG, "sendMultipartTextMessageInternal (no persist): Couldn't send SMS - " + e.getMessage());
                    notifySmsError(list4, 31);
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            list4 = list2;
        }
    }

    public void sendDataMessage(final String str, final String str2, final short s, final byte[] bArr, final PendingIntent pendingIntent, final PendingIntent pendingIntent2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        }
        resolveSubscriptionForOperation(new SubscriptionResolverResult() { // from class: android.telephony.SmsManager.5
            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onSuccess(int i) {
                try {
                    SmsManager.getISmsServiceOrThrow().sendDataForSubscriber(i, null, SmsManager.this.getAttributionTag(), str, str2, s & 65535, bArr, pendingIntent, pendingIntent2);
                } catch (RemoteException e) {
                    Log.e(SmsManager.TAG, "sendDataMessage: Couldn't send SMS - Exception: " + e.getMessage());
                    SmsManager.notifySmsError(pendingIntent, 31);
                }
            }

            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onFailure() {
                SmsManager.notifySmsError(pendingIntent, 32);
            }
        });
    }

    @Deprecated
    public static SmsManager getDefault() {
        return DEFAULT_INSTANCE;
    }

    public static SmsManager getSmsManagerForContextAndSubscriptionId(Context context, int i) {
        SmsManager smsManager;
        synchronized (sLockObject) {
            Pair<Context, Integer> pair = new Pair<>(context, Integer.valueOf(i));
            Map<Pair<Context, Integer>, SmsManager> map = sSubInstances;
            smsManager = map.get(pair);
            if (smsManager == null) {
                smsManager = new SmsManager(context, i);
                map.put(pair, smsManager);
            }
        }
        return smsManager;
    }

    @Deprecated
    public static SmsManager getSmsManagerForSubscriptionId(int i) {
        return getSmsManagerForContextAndSubscriptionId(null, i);
    }

    public SmsManager createForSubscriptionId(int i) {
        return getSmsManagerForContextAndSubscriptionId(this.mContext, i);
    }

    private SmsManager(Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    public int getSubscriptionId() {
        try {
            int i = this.mSubId;
            return i == Integer.MAX_VALUE ? getISmsServiceOrThrow().getPreferredSmsSubscription() : i;
        } catch (RemoteException unused) {
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void resolveSubscriptionForOperation(final android.telephony.SmsManager.SubscriptionResolverResult r6) {
        /*
            r5 = this;
            java.lang.String r0 = "SmsManager"
            int r1 = r5.getSubscriptionId()
            r2 = 0
            com.android.internal.telephony.ISms r3 = getISmsService()     // Catch: android.os.RemoteException -> L12
            if (r3 == 0) goto L19
            boolean r3 = r3.isSmsSimPickActivityNeeded(r1)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r3 = move-exception
            java.lang.String r4 = "resolveSubscriptionForOperation"
            android.util.Log.e(r0, r4, r3)
        L19:
            r3 = r2
        L1a:
            if (r3 != 0) goto L20
            r5.sendResolverResult(r6, r1, r2)
            return
        L20:
            java.lang.String r2 = "resolveSubscriptionForOperation isSmsSimPickActivityNeeded is true for calling package. "
            android.util.Log.d(r0, r2)
            com.android.internal.telephony.ITelephony r2 = getITelephony()     // Catch: android.os.RemoteException -> L34
            android.telephony.SmsManager$6 r3 = new android.telephony.SmsManager$6     // Catch: android.os.RemoteException -> L34
            r3.<init>()     // Catch: android.os.RemoteException -> L34
            r4 = 0
            r2.enqueueSmsPickResult(r4, r4, r3)     // Catch: android.os.RemoteException -> L34
            goto L3e
        L34:
            r2 = move-exception
            java.lang.String r3 = "Unable to launch activity"
            android.util.Log.e(r0, r3, r2)
            r0 = 1
            r5.sendResolverResult(r6, r1, r0)
        L3e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.SmsManager.resolveSubscriptionForOperation(android.telephony.SmsManager$SubscriptionResolverResult):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResolverResult(SubscriptionResolverResult subscriptionResolverResult, int i, boolean z) {
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            subscriptionResolverResult.onSuccess(i);
        } else if (!Compatibility.isChangeEnabled(GET_TARGET_SDK_VERSION_CODE_CHANGE) && !z) {
            subscriptionResolverResult.onSuccess(i);
        } else {
            subscriptionResolverResult.onFailure();
        }
    }

    private static ITelephony getITelephony() {
        ITelephony asInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        if (asInterface != null) {
            return asInterface;
        }
        throw new RuntimeException("Could not find Telephony Service.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifySmsError(PendingIntent pendingIntent, int i) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(i);
            } catch (PendingIntent.CanceledException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifySmsError(List<PendingIntent> list, int i) {
        if (list != null) {
            Iterator<PendingIntent> it = list.iterator();
            while (it.hasNext()) {
                notifySmsError(it.next(), i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ISms getISmsServiceOrThrow() {
        ISms smsService = TelephonyManager.getSmsService();
        if (smsService != null) {
            return smsService;
        }
        throw new UnsupportedOperationException("Sms is not supported");
    }

    private static ISms getISmsService() {
        return TelephonyManager.getSmsService();
    }

    public boolean copyMessageToIcc(byte[] bArr, byte[] bArr2, int i) {
        if (bArr2 == null) {
            throw new IllegalArgumentException("pdu is null");
        }
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.copyMessageToIccEfForSubscriber(getSubscriptionId(), null, i, bArr2, bArr);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean deleteMessageFromIcc(int i) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, i, 0, null);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean updateMessageOnIcc(int i, int i2, byte[] bArr) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, i, i2, bArr);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public List<SmsMessage> getMessagesFromIcc() {
        return getAllMessagesFromIcc();
    }

    public ArrayList<SmsMessage> getAllMessagesFromIcc() {
        List<SmsRawData> list = null;
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                list = iSmsService.getAllMessagesFromIccEfForSubscriber(getSubscriptionId(), null);
            }
        } catch (RemoteException unused) {
        }
        return createMessageListFromRawRecords(list);
    }

    @SystemApi
    @Deprecated
    public boolean enableCellBroadcastRange(int i, int i2, int i3) {
        boolean z = false;
        if (i3 == 2 && Flags.cleanupCdma()) {
            com.android.telephony.Rlog.d(TAG, "CDMA cellbroadcast is not supported.");
            return false;
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                int subscriptionId = getSubscriptionId();
                z = iSmsService.enableCellBroadcastRangeForSubscriber(subscriptionId, i, i2, i3);
                StringBuilder sb = new StringBuilder("enableCellBroadcastRange: ");
                sb.append(z ? "succeeded" : "failed");
                sb.append(" at calling enableCellBroadcastRangeForSubscriber. subId = ");
                sb.append(subscriptionId);
                com.android.telephony.Rlog.d(TAG, sb.toString());
            }
            return z;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "enableCellBroadcastRange: ", e);
            return z;
        }
    }

    @SystemApi
    @Deprecated
    public boolean disableCellBroadcastRange(int i, int i2, int i3) {
        boolean z = false;
        if (i3 == 2 && Flags.cleanupCdma()) {
            com.android.telephony.Rlog.d(TAG, "CDMA cellbroadcast is not supported.");
            return false;
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                int subscriptionId = getSubscriptionId();
                z = iSmsService.disableCellBroadcastRangeForSubscriber(subscriptionId, i, i2, i3);
                StringBuilder sb = new StringBuilder("disableCellBroadcastRange: ");
                sb.append(z ? "succeeded" : "failed");
                sb.append(" at calling disableCellBroadcastRangeForSubscriber. subId = ");
                sb.append(subscriptionId);
                com.android.telephony.Rlog.d(TAG, sb.toString());
            }
            return z;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "disableCellBroadcastRange: ", e);
            return z;
        }
    }

    private ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> list) {
        SmsMessage createFromEfRecord;
        ArrayList<SmsMessage> arrayList = new ArrayList<>();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                SmsRawData smsRawData = list.get(i);
                if (smsRawData != null && (createFromEfRecord = SmsMessage.createFromEfRecord(i + 1, smsRawData.getBytes(), getSubscriptionId())) != null) {
                    arrayList.add(createFromEfRecord);
                }
            }
        }
        return arrayList;
    }

    public boolean isImsSmsSupported() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.isImsSmsSupportedForSubscriber(getSubscriptionId());
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public String getImsSmsFormat() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getImsSmsFormatForSubscriber(getSubscriptionId());
            }
            return "unknown";
        } catch (RemoteException unused) {
            return "unknown";
        }
    }

    public static int getDefaultSmsSubscriptionId() {
        try {
            return getISmsService().getPreferredSmsSubscription();
        } catch (RemoteException | NullPointerException unused) {
            return -1;
        }
    }

    public boolean isSMSPromptEnabled() {
        try {
            return TelephonyManager.getSmsService().isSMSPromptEnabled();
        } catch (RemoteException | NullPointerException unused) {
            return false;
        }
    }

    public int getSmsCapacityOnIcc() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getSmsCapacityOnIccForSubscriber(getSubscriptionId());
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "getSmsCapacityOnIcc() RemoteException", e);
            return 0;
        }
    }

    public void sendMultimediaMessage(Context context, Uri uri, String str, Bundle bundle, PendingIntent pendingIntent) {
        sendMultimediaMessage(context, uri, str, bundle, pendingIntent, 0L);
    }

    public void sendMultimediaMessage(Context context, final Uri uri, final String str, final Bundle bundle, final PendingIntent pendingIntent, final long j) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri contentUri null");
        }
        if (isMsgBlockedForOneNumberService()) {
            com.android.telephony.Rlog.i(TAG, "3rd party MMS Sending Blocked due to One Number Service");
            notifySmsError(pendingIntent, 1);
        } else {
            final MmsManager mmsManager = (MmsManager) context.getSystemService("mms");
            if (mmsManager != null) {
                resolveSubscriptionForOperation(new SubscriptionResolverResult(this) { // from class: android.telephony.SmsManager.7
                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onSuccess(int i) {
                        mmsManager.sendMultimediaMessage(i, uri, str, bundle, pendingIntent, j);
                    }

                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onFailure() {
                        SmsManager.notifySmsError(pendingIntent, 32);
                    }
                });
            }
        }
    }

    public void downloadMultimediaMessage(Context context, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) {
        downloadMultimediaMessage(context, str, uri, bundle, pendingIntent, 0L);
    }

    public void downloadMultimediaMessage(Context context, final String str, final Uri uri, final Bundle bundle, final PendingIntent pendingIntent, final long j) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty MMS location URL");
        }
        if (uri == null) {
            throw new IllegalArgumentException("Uri contentUri null");
        }
        final MmsManager mmsManager = (MmsManager) context.getSystemService("mms");
        if (mmsManager != null) {
            resolveSubscriptionForOperation(new SubscriptionResolverResult(this) { // from class: android.telephony.SmsManager.8
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    mmsManager.downloadMultimediaMessage(i, str, uri, bundle, pendingIntent, j);
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
        }
    }

    public Bundle getCarrierConfigValues() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getCarrierConfigValuesForSubscriber(getSubscriptionId());
            }
        } catch (RemoteException unused) {
        }
        return new Bundle();
    }

    public String createAppSpecificSmsToken(PendingIntent pendingIntent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsToken(getSubscriptionId(), null, pendingIntent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public String createAppSpecificSmsTokenWithPackageInfo(String str, PendingIntent pendingIntent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsTokenWithPackageInfo(getSubscriptionId(), null, str, pendingIntent);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public void setStorageMonitorMemoryStatusOverride(boolean z) {
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.setStorageMonitorMemoryStatusOverride(getSubscriptionId(), z);
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void clearStorageMonitorMemoryStatusOverride() {
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.clearStorageMonitorMemoryStatusOverride(getSubscriptionId());
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public int checkSmsShortCodeDestination(String str, String str2) {
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                return iSmsServiceOrThrow.checkSmsShortCodeDestination(getSubscriptionId(), null, null, str, str2);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "checkSmsShortCodeDestination() RemoteException", e);
            return 0;
        }
    }

    public String getSmscAddress() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getSmscAddressFromIccEfForSubscriber(getSubscriptionId(), null);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setSmscAddress(String str) {
        byte[] stringToGsm7BitPacked;
        boolean isDialable = PhoneNumberUtils.isDialable(str.charAt(0));
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (str.indexOf(43) != -1) {
            length--;
        }
        if (length > 20) {
            return false;
        }
        if (isDialable) {
            com.android.telephony.Rlog.d(TAG, "Smsc is Numeric.");
            stringToGsm7BitPacked = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        } else {
            com.android.telephony.Rlog.i(TAG, "Smsc is Alphabetic.");
            try {
                stringToGsm7BitPacked = GsmAlphabet.stringToGsm7BitPacked(str);
            } catch (EncodeException e) {
                Log.e(TAG, "Implausible UnsupportedEncodingException ", e);
                return false;
            }
        }
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.setSmscAddressOnIccEfForSubscriber(IccUtils.bytesToHexString(stringToGsm7BitPacked), getSubscriptionId(), null);
            }
            return false;
        } catch (RemoteException e2) {
            throw new RuntimeException(e2);
        }
    }

    @SystemApi
    public int getPremiumSmsConsent(String str) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getPremiumSmsPermission(str);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "getPremiumSmsPermission() RemoteException", e);
            return 0;
        }
    }

    @SystemApi
    public void setPremiumSmsConsent(String str, int i) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                if (ISIS_PACKAGE_NAME.equals(str) && getMnoName().toUpperCase().contains("VZW")) {
                    Log.i(TAG, "setPremiumSmsPermission() for ISIS_PACKAGE");
                    i = 3;
                }
                iSmsService.setPremiumSmsPermission(str, i);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "setPremiumSmsPermission() RemoteException", e);
        }
    }

    @SystemApi
    @Deprecated
    public void resetAllCellBroadcastRanges() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                iSmsService.resetAllCellBroadcastRanges(getSubscriptionId());
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatCrossStackMessageId(long j) {
        return "{x-message-id:" + j + "}";
    }

    @SystemApi
    public Uri getSmscIdentity() {
        Uri uri = Uri.EMPTY;
        try {
            IPhoneSubInfo subscriberInfoService = TelephonyManager.getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): IPhoneSubInfo instance is NULL");
                throw new IllegalStateException("Telephony service is not available");
            }
            Uri smscIdentity = subscriberInfoService.getSmscIdentity(getSubscriptionId(), 5);
            return Uri.EMPTY.equals(smscIdentity) ? subscriberInfoService.getSmscIdentity(getSubscriptionId(), 2) : smscIdentity;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): Exception : " + e);
            e.rethrowAsRuntimeException();
            return uri;
        }
    }

    public long getWapMessageSize(String str) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getWapMessageSize(str);
            }
            throw new RuntimeException("Could not acquire ISms service.");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.telephony.gsm.SemCbConfig semGetCbSettings() {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.SmsManager.semGetCbSettings():com.samsung.android.telephony.gsm.SemCbConfig");
    }

    public void semSendMultipartTextMessage(String str, String str2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, boolean z, int i, int i2, int i3) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with encodiing type");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (arrayList == null) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (arrayList.size() < 1 && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (arrayList.size() > 1) {
            try {
                getISmsServiceOrThrow().sendMultipartTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, arrayList, arrayList2, arrayList3, z, i, i2, i3);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        PendingIntent pendingIntent = null;
        PendingIntent pendingIntent2 = (arrayList2 == null || arrayList2.size() <= 0) ? null : arrayList2.get(0);
        if (arrayList3 != null && arrayList3.size() > 0) {
            pendingIntent = arrayList3.get(0);
        }
        sendTextMessage(str, str2, arrayList.get(0), pendingIntent2, pendingIntent, z, i, i2, i3);
    }

    public void semSendMultipartTextMessage(String str, String str2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, String str3, int i) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with priority");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (arrayList == null) {
            throw new IllegalArgumentException("Invalid parts");
        }
        if (arrayList.size() < 1 && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (arrayList.size() > 1) {
            try {
                ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
                if (iSmsServiceOrThrow != null) {
                    iSmsServiceOrThrow.sendMultipartTextwithCBPForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, arrayList, arrayList2, arrayList3, str3, i);
                    return;
                }
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        PendingIntent pendingIntent = null;
        PendingIntent pendingIntent2 = (arrayList2 == null || arrayList2.size() <= 0) ? null : arrayList2.get(0);
        if (arrayList3 != null && arrayList3.size() > 0) {
            pendingIntent = arrayList3.get(0);
        }
        sendTextMessage(str, str2, arrayList.get(0), pendingIntent2, pendingIntent, str3, i);
    }

    public boolean semGetSMSPAvailable() {
        com.android.telephony.Rlog.d(TAG, "getSMSPAvailable in SmsManager");
        if (TelephonyManager.getDefault().getSimState() != 5) {
            return false;
        }
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getSMSPAvailableForSubscriber(getSubscriptionId());
            }
            return false;
        } catch (RemoteException unused) {
            com.android.telephony.Rlog.d(TAG, "Exception In getSMSPAvailable() of SmsManager.java");
            return false;
        }
    }

    public ArrayList<String> semDivideMessage(String str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("text is null");
        }
        return SmsMessage.fragmentText(str, i, this);
    }

    public void semSendMultipartTextMessage(String str, String str2, ArrayList<String> arrayList, ArrayList<PendingIntent> arrayList2, ArrayList<PendingIntent> arrayList3, boolean z, int i, int i2, int i3, int i4) {
        com.android.telephony.Rlog.i(TAG, "semSendMultipartTextMessage with confirmId");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (arrayList == null) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (arrayList.size() < 1 && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        if (arrayList.size() > 1) {
            try {
                getISmsServiceOrThrow().sendMultipartTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, arrayList, arrayList2, arrayList3, z, i, i2, i3);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        PendingIntent pendingIntent = null;
        PendingIntent pendingIntent2 = (arrayList2 == null || arrayList2.size() <= 0) ? null : arrayList2.get(0);
        if (arrayList3 != null && arrayList3.size() > 0) {
            pendingIntent = arrayList3.get(0);
        }
        sendTextMessage(str, str2, arrayList.get(0), pendingIntent2, pendingIntent, z, i, i2, i3, i4);
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i, int i2, int i3) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with encoding Type: mno - " + getMnoName());
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            getISmsServiceOrThrow().sendTextwithOptionsForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, str3, pendingIntent, pendingIntent2, z, i, i2, i3);
        } catch (RemoteException unused) {
        }
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str4, int i) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with callbacknmber and priority");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        try {
            ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.sendTextwithCBPForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, str3, pendingIntent, pendingIntent2, str4, i);
            }
        } catch (RemoteException unused) {
        }
    }

    public void sendTextMessage(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i, int i2, int i3, int i4) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessage with confirmId");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            getISmsServiceOrThrow().sendTextwithOptionsReadconfirmForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, str3, pendingIntent, pendingIntent2, z, i, i2, i3, i4);
        } catch (RemoteException unused) {
        }
    }

    public boolean semEnableCellBroadcastRange(int i, int i2) {
        return enableCellBroadcastRange(i, i2, 1);
    }

    public boolean semDisableCellBroadcastRange(int i, int i2) {
        return disableCellBroadcastRange(i, i2, 1);
    }

    public String getMnoName() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getMnoNameForSubscriber(getSubscriptionId());
            }
            return "default";
        } catch (RemoteException unused) {
            return "default";
        }
    }

    public boolean getSmsSetting(String str) {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getSmsSettingForSubscriber(getSubscriptionId(), str);
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b7, code lost:
    
        r2 = "3gpp2";
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a9  */
    /* JADX WARN: Type inference failed for: r17v1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<android.telephony.SmsMessage> getAllMessagesFromIccSimType(int r17) {
        /*
            r16 = this;
            r0 = r17
            java.lang.String r1 = "getAllMessagesFromIccSimType - exception - iccType:"
            java.lang.String r2 = r16.getCurrentFormat()
            int r3 = r16.getSubscriptionId()
            int r4 = android.telephony.SubscriptionManager.getPhoneId(r3)
            r5 = -1
            if (r0 == r5) goto L15
            if (r0 != 0) goto L19
        L15:
            int r0 = com.android.internal.telephony.uicc.IccUtils.getIccType(r4)
        L19:
            java.lang.String r6 = "SmsManager"
            r7 = 0
            java.lang.String r8 = "3gpp"
            if (r0 != 0) goto L2a
            java.lang.String r0 = "IccType is Unknown"
            com.android.telephony.Rlog.d(r6, r0)
            java.util.ArrayList r0 = createMessageListFromRawRecords(r7, r8)
            return r0
        L2a:
            r9 = 10
            r10 = 4
            com.android.internal.telephony.ISms r12 = getISmsService()     // Catch: java.lang.Throwable -> L90 android.os.RemoteException -> L94
            if (r12 == 0) goto L8b
            android.telephony.TelephonyManager.getDefault()     // Catch: java.lang.Throwable -> L90 android.os.RemoteException -> L94
            java.lang.String r13 = "gsm.operator.isroaming"
            java.lang.String r14 = "false"
            java.lang.String r13 = android.telephony.TelephonyManager.getTelephonyProperty(r4, r13, r14)     // Catch: java.lang.Throwable -> L90 android.os.RemoteException -> L94
            boolean r13 = java.lang.Boolean.parseBoolean(r13)     // Catch: java.lang.Throwable -> L90 android.os.RemoteException -> L94
            java.lang.String r14 = "CHN"
            java.lang.String r15 = "HKG"
            r17 = 0
            java.lang.String r11 = "TPE"
            java.lang.String[] r11 = new java.lang.String[]{r14, r15, r11}     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            boolean r11 = com.android.internal.telephony.TelephonyFeatures.isCountrySpecific(r4, r11)     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            if (r4 != 0) goto L58
            java.lang.String r4 = "voicecall_type"
            goto L5b
        L58:
            java.lang.String r4 = "voicecall_type2"
        L5b:
            android.app.Application r14 = android.app.ActivityThread.currentApplication()     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            android.content.Context r14 = r14.getApplicationContext()     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            android.content.ContentResolver r14 = r14.getContentResolver()     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            int r4 = android.provider.Settings.System.getInt(r14, r4, r5)     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            r5 = 1
            if (r4 != 0) goto L70
            r4 = r5
            goto L72
        L70:
            r4 = r17
        L72:
            if (r0 != r9) goto L78
            if (r13 != 0) goto L78
            if (r4 == 0) goto L80
        L78:
            if (r0 != r10) goto L82
            if (r13 != 0) goto L7e
            if (r4 == 0) goto L82
        L7e:
            if (r11 == 0) goto L82
        L80:
            android.telephony.TelephonyManager.isSelecttelecomDF = r5     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
        L82:
            int r4 = r16.getSubscriptionId()     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            java.util.List r7 = r12.getAllMessagesFromIccEfForSubscriber(r4, r7)     // Catch: android.os.RemoteException -> L96 java.lang.Throwable -> Le0
            goto L8d
        L8b:
            r17 = 0
        L8d:
            android.telephony.TelephonyManager.isSelecttelecomDF = r17
            goto La6
        L90:
            r0 = move-exception
            r17 = 0
            goto Le1
        L94:
            r17 = 0
        L96:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Le0
            r4.<init>(r1)     // Catch: java.lang.Throwable -> Le0
            r4.append(r0)     // Catch: java.lang.Throwable -> Le0
            java.lang.String r1 = r4.toString()     // Catch: java.lang.Throwable -> Le0
            com.android.telephony.Rlog.d(r6, r1)     // Catch: java.lang.Throwable -> Le0
            goto L8d
        La6:
            if (r0 != r10) goto La9
            goto Lba
        La9:
            java.lang.String r1 = "3gpp2"
            if (r0 != r9) goto Lb4
            boolean r2 = r8.equals(r2)
            if (r2 == 0) goto Lb9
            goto Lb7
        Lb4:
            r2 = 3
            if (r0 != r2) goto Lb9
        Lb7:
            r2 = r1
            goto Lba
        Lb9:
            r2 = r8
        Lba:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "getAllMessagesFromIccSimType, subId = "
            r1.<init>(r4)
            r1.append(r3)
            java.lang.String r3 = " format = "
            r1.append(r3)
            r1.append(r2)
            java.lang.String r3 = " iccType = "
            r1.append(r3)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.android.telephony.Rlog.d(r6, r0)
            java.util.ArrayList r0 = createMessageListFromRawRecords(r7, r2)
            return r0
        Le0:
            r0 = move-exception
        Le1:
            android.telephony.TelephonyManager.isSelecttelecomDF = r17
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.SmsManager.getAllMessagesFromIccSimType(int):java.util.ArrayList");
    }

    private static ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> list, String str) {
        ArrayList<SmsMessage> arrayList = new ArrayList<>();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                SmsRawData smsRawData = list.get(i);
                if (smsRawData != null) {
                    SmsMessage createFromEfRecord = SmsMessage.createFromEfRecord(i + 1, smsRawData.getBytes(), str);
                    arrayList.add(createFromEfRecord);
                    if (createFromEfRecord == null) {
                        Log.d(TAG, "createFromEfRecord NULL:" + str + "index:" + i);
                    }
                } else {
                    arrayList.add(null);
                }
            }
        }
        return arrayList;
    }

    public void sendOTADomestic(String str, String str2, String str3) {
        com.android.telephony.Rlog.i(TAG, "sendOTADomestic");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            getISmsServiceOrThrow().sendOTADomesticForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, str3);
        } catch (RemoteException unused) {
        }
    }

    public void sendDataMessage(String str, String str2, short s, short s2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        com.android.telephony.Rlog.i(TAG, "sendDataMessage");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        }
        try {
            getISmsServiceOrThrow().sendDatawithOrigPortForSubscriber(getSubscriptionId(), null, str, str2, s & 65535, s2 & 65535, bArr, pendingIntent, pendingIntent2);
        } catch (RemoteException unused) {
        }
    }

    public void sendTextMessageNSRI(String str, String str2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str3, int i, int i2) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessageNSRI");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        Log.d(TAG, "[NSRI_SMS] sendTextMessageNSRI Addr = " + str + " Smsc = " + str2 + " textLen = " + bArr.length + " from = " + str3 + " msgCount = " + i + " msgTotal = " + i2);
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                iSmsService.sendTextNSRIForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, bArr, pendingIntent, pendingIntent2, i, i2);
            }
        } catch (RemoteException unused) {
        }
    }

    public void sendTextMessageAutoLogin(String str, String str2, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        com.android.telephony.Rlog.i(TAG, "sendTextMessageAutoLogin");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (getMnoName().toUpperCase().contains("DOCOMO") && str.length() >= 256) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        }
        if (TextUtils.isEmpty(str3) && !isSupportSendingEmptySms()) {
            throw new IllegalArgumentException("Invalid message body");
        }
        try {
            getISmsServiceOrThrow().sendTextAutoLoginForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), str, str2, str3, pendingIntent, pendingIntent2, false);
        } catch (RemoteException unused) {
        }
    }

    public String getCurrentFormat() {
        int subscriptionId = getSubscriptionId();
        int phoneId = SubscriptionManager.getPhoneId(subscriptionId);
        TelephonyManager.getDefault();
        String telephonyProperty = TelephonyManager.getTelephonyProperty(phoneId, TelephonyProperties.CURRENT_ACTIVE_PHONE, String.valueOf(1));
        com.android.telephony.Rlog.d(TAG, "getCurrentFormat, subId = " + subscriptionId + " mode = " + telephonyProperty);
        if (Integer.parseInt(telephonyProperty) == 2) {
            return "3gpp2";
        }
        return "3gpp";
    }

    private boolean isSupportSendingEmptySms() {
        final String str = SystemProperties.get("mdc.matched_code", SystemProperties.get("ro.csc.sales_code", ""));
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Arrays.stream(new String[]{"BST", "TEL", "TLP"}).anyMatch(new Predicate() { // from class: android.telephony.SmsManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((String) obj).equals(str);
                return equals;
            }
        });
    }

    public boolean isMsgBlockedForOneNumberService() {
        try {
            ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.isMsgBlockedForOneNumberServiceForSubscriber(getSubscriptionId());
            }
            return false;
        } catch (RemoteException unused) {
            return false;
        }
    }
}
