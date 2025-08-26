package android.security.intrusiondetection;

import android.annotation.SystemApi;
import android.app.admin.ConnectEvent;
import android.app.admin.DnsEvent;
import android.app.admin.SecurityLog;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class IntrusionDetectionEvent implements Parcelable {
    public static final Parcelable.Creator<IntrusionDetectionEvent> CREATOR = new Parcelable.Creator<IntrusionDetectionEvent>() { // from class: android.security.intrusiondetection.IntrusionDetectionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntrusionDetectionEvent createFromParcel(Parcel parcel) {
            return new IntrusionDetectionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntrusionDetectionEvent[] newArray(int i) {
            return new IntrusionDetectionEvent[i];
        }
    };
    public static final int NETWORK_EVENT_CONNECT = 2;
    public static final int NETWORK_EVENT_DNS = 1;
    public static final int SECURITY_EVENT = 0;
    private static final String TAG = "IntrusionDetectionEvent";
    private final ConnectEvent mNetworkEventConnect;
    private final DnsEvent mNetworkEventDns;
    private final SecurityLog.SecurityEvent mSecurityEvent;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private IntrusionDetectionEvent(SecurityLog.SecurityEvent securityEvent) {
        this.mType = 0;
        this.mSecurityEvent = securityEvent;
        this.mNetworkEventDns = null;
        this.mNetworkEventConnect = null;
    }

    private IntrusionDetectionEvent(DnsEvent dnsEvent) {
        this.mType = 1;
        this.mNetworkEventDns = dnsEvent;
        this.mSecurityEvent = null;
        this.mNetworkEventConnect = null;
    }

    private IntrusionDetectionEvent(ConnectEvent connectEvent) {
        this.mType = 2;
        this.mNetworkEventConnect = connectEvent;
        this.mSecurityEvent = null;
        this.mNetworkEventDns = null;
    }

    public static IntrusionDetectionEvent createForSecurityEvent(SecurityLog.SecurityEvent securityEvent) {
        return new IntrusionDetectionEvent(securityEvent);
    }

    public static IntrusionDetectionEvent createForDnsEvent(DnsEvent dnsEvent) {
        return new IntrusionDetectionEvent(dnsEvent);
    }

    public static IntrusionDetectionEvent createForConnectEvent(ConnectEvent connectEvent) {
        return new IntrusionDetectionEvent(connectEvent);
    }

    private IntrusionDetectionEvent(Parcel parcel) {
        int i = parcel.readInt();
        this.mType = i;
        if (i == 0) {
            this.mSecurityEvent = SecurityLog.SecurityEvent.CREATOR.createFromParcel(parcel);
            this.mNetworkEventDns = null;
            this.mNetworkEventConnect = null;
        } else if (i == 1) {
            this.mNetworkEventDns = DnsEvent.CREATOR.createFromParcel(parcel);
            this.mSecurityEvent = null;
            this.mNetworkEventConnect = null;
        } else if (i == 2) {
            this.mNetworkEventConnect = ConnectEvent.CREATOR.createFromParcel(parcel);
            this.mSecurityEvent = null;
            this.mNetworkEventDns = null;
        } else {
            throw new IllegalArgumentException("Invalid event type: " + i);
        }
    }

    public int getType() {
        return this.mType;
    }

    public SecurityLog.SecurityEvent getSecurityEvent() {
        if (this.mType == 0) {
            return this.mSecurityEvent;
        }
        throw new IllegalArgumentException("Event type is not security event: " + this.mType);
    }

    public DnsEvent getDnsEvent() {
        if (this.mType == 1) {
            return this.mNetworkEventDns;
        }
        throw new IllegalArgumentException("Event type is not network DNS event: " + this.mType);
    }

    public ConnectEvent getConnectEvent() {
        if (this.mType == 2) {
            return this.mNetworkEventConnect;
        }
        throw new IllegalArgumentException("Event type is not network connect event: " + this.mType);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        int i2 = this.mType;
        if (i2 == 0) {
            this.mSecurityEvent.writeToParcel(parcel, i);
            return;
        }
        if (i2 == 1) {
            this.mNetworkEventDns.writeToParcel(parcel, i);
        } else if (i2 == 2) {
            this.mNetworkEventConnect.writeToParcel(parcel, i);
        } else {
            throw new IllegalArgumentException("Invalid event type: " + this.mType);
        }
    }

    public String toString() {
        return "IntrusionDetectionEvent{mType=" + this.mType + '}';
    }
}
