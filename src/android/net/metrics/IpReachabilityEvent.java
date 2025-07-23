package android.net.metrics;

import android.annotation.SystemApi;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.android.internal.util.MessageUtils;

@SystemApi
@Deprecated
/* loaded from: classes3.dex */
public final class IpReachabilityEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<IpReachabilityEvent> CREATOR = new Parcelable.Creator<IpReachabilityEvent>() { // from class: android.net.metrics.IpReachabilityEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpReachabilityEvent createFromParcel(Parcel parcel) {
            return new IpReachabilityEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IpReachabilityEvent[] newArray(int i) {
            return new IpReachabilityEvent[i];
        }
    };
    public static final int NUD_FAILED = 512;
    public static final int NUD_FAILED_ORGANIC = 1024;
    public static final int PROBE = 256;
    public static final int PROVISIONING_LOST = 768;
    public static final int PROVISIONING_LOST_ORGANIC = 1280;
    public final int eventType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IpReachabilityEvent(int i) {
        this.eventType = i;
    }

    private IpReachabilityEvent(Parcel parcel) {
        this.eventType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.eventType);
    }

    public String toString() {
        int i = this.eventType;
        return String.format("IpReachabilityEvent(%s:%02x)", Decoder.constants.get(65280 & i), Integer.valueOf(i & 255));
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(IpReachabilityEvent.class) && this.eventType == ((IpReachabilityEvent) obj).eventType;
    }

    static final class Decoder {
        static final SparseArray<String> constants = MessageUtils.findMessageNames(new Class[]{IpReachabilityEvent.class}, new String[]{"PROBE", "PROVISIONING_", "NUD_"});

        Decoder() {
        }
    }
}
