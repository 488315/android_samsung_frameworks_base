package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class IpManagerEvent implements IpConnectivityLog.Event {
    public static final int COMPLETE_LIFECYCLE = 3;
    public static final Parcelable.Creator<IpManagerEvent> CREATOR = new Parcelable.Creator<IpManagerEvent>() { // from class: android.net.metrics.IpManagerEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpManagerEvent createFromParcel(Parcel parcel) {
            return new IpManagerEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpManagerEvent[] newArray(int i) {
            return new IpManagerEvent[i];
        }
    };
    public static final int ERROR_INTERFACE_NOT_FOUND = 8;
    public static final int ERROR_INVALID_PROVISIONING = 7;
    public static final int ERROR_STARTING_IPREACHABILITYMONITOR = 6;
    public static final int ERROR_STARTING_IPV4 = 4;
    public static final int ERROR_STARTING_IPV6 = 5;
    public static final int PROVISIONING_FAIL = 2;
    public static final int PROVISIONING_OK = 1;
    public final long durationMs;
    public final int eventType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IpManagerEvent(int i, long j) {
        this.eventType = i;
        this.durationMs = j;
    }

    private IpManagerEvent(Parcel parcel) {
        this.eventType = parcel.readInt();
        this.durationMs = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.eventType);
        parcel.writeLong(this.durationMs);
    }

    public String toString() {
        return String.format("IpManagerEvent(%s, %dms)", Decoder.constants.get(this.eventType), Long.valueOf(this.durationMs));
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(IpManagerEvent.class)) {
            IpManagerEvent ipManagerEvent = (IpManagerEvent) obj;
            if (this.eventType == ipManagerEvent.eventType && this.durationMs == ipManagerEvent.durationMs) {
                return true;
            }
        }
        return false;
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{IpManagerEvent.class}, new String[]{"PROVISIONING_", "COMPLETE_", "ERROR_"});

        Decoder() {
        }
    }
}
