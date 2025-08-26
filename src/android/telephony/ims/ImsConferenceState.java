package android.telephony.ims;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.telephony.Rlog;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsConferenceState implements Parcelable {
    public static final Parcelable.Creator<ImsConferenceState> CREATOR = new Parcelable.Creator<ImsConferenceState>() { // from class: android.telephony.ims.ImsConferenceState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsConferenceState createFromParcel(Parcel parcel) {
            return new ImsConferenceState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsConferenceState[] newArray(int i) {
            return new ImsConferenceState[i];
        }
    };
    public static final String DISPLAY_TEXT = "display-text";
    public static final String ENDPOINT = "endpoint";
    public static final String SIP_STATUS_CODE = "sipstatuscode";
    public static final String STATUS = "status";
    public static final String STATUS_ALERTING = "alerting";
    public static final String STATUS_CONNECTED = "connected";
    public static final String STATUS_CONNECT_FAIL = "connect-fail";
    public static final String STATUS_DIALING_IN = "dialing-in";
    public static final String STATUS_DIALING_OUT = "dialing-out";
    public static final String STATUS_DISCONNECTED = "disconnected";
    public static final String STATUS_DISCONNECTING = "disconnecting";
    public static final String STATUS_MUTED_VIA_FOCUS = "muted-via-focus";
    public static final String STATUS_ON_HOLD = "on-hold";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_SEND_ONLY = "sendonly";
    public static final String STATUS_SEND_RECV = "sendrecv";
    private static final String TAG = "ImsConferenceState";
    public static final String USER = "user";
    public final HashMap<String, Bundle> mParticipants;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsConferenceState() {
        this.mParticipants = new HashMap<>();
    }

    private ImsConferenceState(Parcel parcel) {
        this.mParticipants = new HashMap<>();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Set<Map.Entry<String, Bundle>> setEntrySet;
        parcel.writeInt(this.mParticipants.size());
        if (this.mParticipants.size() <= 0 || (setEntrySet = this.mParticipants.entrySet()) == null) {
            return;
        }
        for (Map.Entry<String, Bundle> entry : setEntrySet) {
            parcel.writeString(entry.getKey());
            parcel.writeParcelable(entry.getValue(), 0);
        }
    }

    private void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mParticipants.put(parcel.readString(), (Bundle) parcel.readParcelable(null, Bundle.class));
        }
    }

    public static int getConnectionStateForStatus(String str) {
        if (str.equals(STATUS_PENDING)) {
            return 0;
        }
        if (str.equals(STATUS_DIALING_IN)) {
            return 2;
        }
        if (str.equals(STATUS_ALERTING) || str.equals(STATUS_DIALING_OUT)) {
            return 3;
        }
        if (str.equals(STATUS_ON_HOLD) || str.equals(STATUS_SEND_ONLY)) {
            return 5;
        }
        return (str.equals("connected") || str.equals(STATUS_MUTED_VIA_FOCUS) || str.equals(STATUS_DISCONNECTING) || str.equals(STATUS_SEND_RECV) || !str.equals("disconnected")) ? 4 : 6;
    }

    public String toString() {
        Set<Map.Entry<String, Bundle>> setEntrySet;
        StringBuilder sb = new StringBuilder("[ImsConferenceState ");
        if (this.mParticipants.size() > 0 && (setEntrySet = this.mParticipants.entrySet()) != null) {
            sb.append("<");
            for (Map.Entry<String, Bundle> entry : setEntrySet) {
                sb.append(Rlog.pii(TAG, entry.getKey()));
                sb.append(": ");
                Bundle value = entry.getValue();
                for (String str : value.keySet()) {
                    sb.append(str);
                    sb.append("=");
                    if ("status".equals(str)) {
                        sb.append(value.get(str));
                    } else {
                        sb.append(Rlog.pii(TAG, value.get(str)));
                    }
                    sb.append(", ");
                }
            }
            sb.append(">");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
