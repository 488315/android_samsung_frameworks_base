package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class DhcpClientEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<DhcpClientEvent> CREATOR = new Parcelable.Creator<DhcpClientEvent>() { // from class: android.net.metrics.DhcpClientEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DhcpClientEvent createFromParcel(Parcel parcel) {
            return new DhcpClientEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DhcpClientEvent[] newArray(int i) {
            return new DhcpClientEvent[i];
        }
    };
    public final int durationMs;
    public final String msg;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DhcpClientEvent(String str, int i) {
        this.msg = str;
        this.durationMs = i;
    }

    private DhcpClientEvent(Parcel parcel) {
        this.msg = parcel.readString();
        this.durationMs = parcel.readInt();
    }

    public static final class Builder {
        private int mDurationMs;
        private String mMsg;

        public Builder setMsg(String str) {
            this.mMsg = str;
            return this;
        }

        public Builder setDurationMs(int i) {
            this.mDurationMs = i;
            return this;
        }

        public DhcpClientEvent build() {
            return new DhcpClientEvent(this.mMsg, this.mDurationMs);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.msg);
        parcel.writeInt(this.durationMs);
    }

    public String toString() {
        return String.format("DhcpClientEvent(%s, %dms)", this.msg, Integer.valueOf(this.durationMs));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(DhcpClientEvent.class)) {
            DhcpClientEvent dhcpClientEvent = (DhcpClientEvent) obj;
            if (TextUtils.equals(this.msg, dhcpClientEvent.msg) && this.durationMs == dhcpClientEvent.durationMs) {
                return true;
            }
        }
        return false;
    }
}
