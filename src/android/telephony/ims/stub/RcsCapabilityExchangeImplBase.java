package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.net.Uri;
import android.telephony.ims.ImsException;
import android.telephony.ims.SipDetails;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public class RcsCapabilityExchangeImplBase {
    public static final int COMMAND_CODE_FETCH_ERROR = 3;
    public static final int COMMAND_CODE_GENERIC_FAILURE = 1;
    public static final int COMMAND_CODE_INSUFFICIENT_MEMORY = 5;
    public static final int COMMAND_CODE_INVALID_PARAM = 2;
    public static final int COMMAND_CODE_LOST_NETWORK_CONNECTION = 6;
    public static final int COMMAND_CODE_NOT_FOUND = 8;
    public static final int COMMAND_CODE_NOT_SUPPORTED = 7;
    public static final int COMMAND_CODE_NO_CHANGE = 10;
    public static final int COMMAND_CODE_REQUEST_TIMEOUT = 4;
    public static final int COMMAND_CODE_SERVICE_UNAVAILABLE = 9;
    public static final int COMMAND_CODE_SERVICE_UNKNOWN = 0;
    private static final String LOG_TAG = "RcsCapExchangeImplBase";

    @Retention(RetentionPolicy.SOURCE)
    public @interface CommandCode {
    }

    public interface OptionsResponseCallback {
        void onCommandError(int i) throws ImsException;

        void onNetworkResponse(int i, String str, List<String> list) throws ImsException;
    }

    public interface PublishResponseCallback {
        void onCommandError(int i) throws ImsException;

        @Deprecated
        void onNetworkResponse(int i, String str) throws ImsException;

        @Deprecated
        void onNetworkResponse(int i, String str, int i2, String str2) throws ImsException;

        default void onNetworkResponse(SipDetails sipDetails) throws ImsException {
            if (TextUtils.isEmpty(sipDetails.getReasonHeaderText())) {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase());
            } else {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase(), sipDetails.getReasonHeaderCause(), sipDetails.getReasonHeaderText());
            }
        }
    }

    public interface SubscribeResponseCallback {
        void onCommandError(int i) throws ImsException;

        @Deprecated
        void onNetworkResponse(int i, String str) throws ImsException;

        @Deprecated
        void onNetworkResponse(int i, String str, int i2, String str2) throws ImsException;

        void onNotifyCapabilitiesUpdate(List<String> list) throws ImsException;

        void onResourceTerminated(List<Pair<Uri, String>> list) throws ImsException;

        void onTerminated(String str, long j) throws ImsException;

        default void onNetworkResponse(SipDetails sipDetails) throws ImsException {
            if (TextUtils.isEmpty(sipDetails.getReasonHeaderText())) {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase());
            } else {
                onNetworkResponse(sipDetails.getResponseCode(), sipDetails.getResponsePhrase(), sipDetails.getReasonHeaderCause(), sipDetails.getReasonHeaderText());
            }
        }
    }

    public void subscribeForCapabilities(Collection<Uri> collection, SubscribeResponseCallback subscribeResponseCallback) {
        Log.w(LOG_TAG, "subscribeForCapabilities called with no implementation.");
        try {
            subscribeResponseCallback.onCommandError(7);
        } catch (ImsException unused) {
        }
    }

    public void publishCapabilities(String str, PublishResponseCallback publishResponseCallback) {
        Log.w(LOG_TAG, "publishCapabilities called with no implementation.");
        try {
            publishResponseCallback.onCommandError(7);
        } catch (ImsException unused) {
        }
    }

    public void sendOptionsCapabilityRequest(Uri uri, Set<String> set, OptionsResponseCallback optionsResponseCallback) {
        Log.w(LOG_TAG, "sendOptionsCapabilityRequest called with no implementation.");
        try {
            optionsResponseCallback.onCommandError(7);
        } catch (ImsException unused) {
        }
    }
}
