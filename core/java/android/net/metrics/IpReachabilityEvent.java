package android.net.metrics;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import com.android.internal.util.MessageUtils;

@SystemApi
@Deprecated
public final class IpReachabilityEvent implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<IpReachabilityEvent> CREATOR =
            new Parcelable.Creator<
                    IpReachabilityEvent>() { // from class:
                                             // android.net.metrics.IpReachabilityEvent.1
                @Override // android.os.Parcelable.Creator
                public IpReachabilityEvent createFromParcel(Parcel in) {
                    return new IpReachabilityEvent(in);
                }

                @Override // android.os.Parcelable.Creator
                public IpReachabilityEvent[] newArray(int size) {
                    return new IpReachabilityEvent[size];
                }
            };
    public static final int NUD_FAILED = 512;
    public static final int NUD_FAILED_ORGANIC = 1024;
    public static final int PROBE = 256;
    public static final int PROVISIONING_LOST = 768;
    public static final int PROVISIONING_LOST_ORGANIC = 1280;
    public final int eventType;

    public IpReachabilityEvent(int eventType) {
        this.eventType = eventType;
    }

    private IpReachabilityEvent(Parcel in) {
        this.eventType = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.eventType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        int hi = this.eventType & 65280;
        int lo = this.eventType & 255;
        String eventName = Decoder.constants.get(hi);
        return String.format("IpReachabilityEvent(%s:%02x)", eventName, Integer.valueOf(lo));
    }

    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(IpReachabilityEvent.class)) {
            return false;
        }
        IpReachabilityEvent other = (IpReachabilityEvent) obj;
        return this.eventType == other.eventType;
    }

    static final class Decoder {
        static final SparseArray<String> constants =
                MessageUtils.findMessageNames(
                        new Class[] {IpReachabilityEvent.class},
                        new String[] {"PROBE", "PROVISIONING_", "NUD_"});

        Decoder() {}
    }
}
