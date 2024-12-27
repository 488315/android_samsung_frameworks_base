package android.telecom;

import android.annotation.SystemApi;
import android.telephony.CallQuality;
import android.telephony.ims.ImsReasonInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public abstract class CallDiagnostics {
    public static final int BATTERY_STATE_CHARGING = 3;
    public static final int BATTERY_STATE_GOOD = 2;
    public static final int BATTERY_STATE_LOW = 1;
    public static final int COVERAGE_GOOD = 2;
    public static final int COVERAGE_POOR = 1;
    public static final int MESSAGE_CALL_AUDIO_CODEC = 2;
    public static final int MESSAGE_CALL_NETWORK_TYPE = 1;
    public static final int MESSAGE_DEVICE_BATTERY_STATE = 3;
    public static final int MESSAGE_DEVICE_NETWORK_COVERAGE = 4;
    private String mCallId;
    private Listener mListener;

    public interface Listener {
        void onClearDiagnosticMessage(CallDiagnostics callDiagnostics, int i);

        void onDisplayDiagnosticMessage(
                CallDiagnostics callDiagnostics, int i, CharSequence charSequence);

        void onSendDeviceToDeviceMessage(CallDiagnostics callDiagnostics, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageType {}

    public abstract void onCallDetailsChanged(Call.Details details);

    public abstract CharSequence onCallDisconnected(int i, int i2);

    public abstract CharSequence onCallDisconnected(ImsReasonInfo imsReasonInfo);

    public abstract void onCallQualityReceived(CallQuality callQuality);

    public abstract void onReceiveDeviceToDeviceMessage(int i, int i2);

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setCallId(String callId) {
        this.mCallId = callId;
    }

    public String getCallId() {
        return this.mCallId;
    }

    public final void sendDeviceToDeviceMessage(int message, int value) {
        if (this.mListener != null) {
            this.mListener.onSendDeviceToDeviceMessage(this, message, value);
        }
    }

    public final void displayDiagnosticMessage(int messageId, CharSequence message) {
        if (this.mListener != null) {
            this.mListener.onDisplayDiagnosticMessage(this, messageId, message);
        }
    }

    public final void clearDiagnosticMessage(int messageId) {
        if (this.mListener != null) {
            this.mListener.onClearDiagnosticMessage(this, messageId);
        }
    }

    public void handleCallUpdated(Call.Details newDetails) {
        onCallDetailsChanged(newDetails);
    }
}
