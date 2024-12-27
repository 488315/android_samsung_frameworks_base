package android.net.networkstack.aidl;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.LinkProperties;
import android.net.NetworkAgentConfig;
import android.net.NetworkCapabilities;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class NetworkMonitorParameters implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public LinkProperties linkProperties;
    public NetworkAgentConfig networkAgentConfig;
    public NetworkCapabilities networkCapabilities;

    /* renamed from: android.net.networkstack.aidl.NetworkMonitorParameters$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            NetworkMonitorParameters networkMonitorParameters = new NetworkMonitorParameters();
            networkMonitorParameters.readFromParcel(parcel);
            return networkMonitorParameters;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NetworkMonitorParameters[i];
        }
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.linkProperties)
                | describeContents(this.networkAgentConfig)
                | describeContents(this.networkCapabilities);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NetworkMonitorParameters)) {
            return false;
        }
        NetworkMonitorParameters networkMonitorParameters = (NetworkMonitorParameters) obj;
        return Objects.deepEquals(
                        this.networkAgentConfig, networkMonitorParameters.networkAgentConfig)
                && Objects.deepEquals(
                        this.networkCapabilities, networkMonitorParameters.networkCapabilities)
                && Objects.deepEquals(this.linkProperties, networkMonitorParameters.linkProperties);
    }

    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                this.networkAgentConfig,
                                this.networkCapabilities,
                                this.linkProperties)
                        .toArray());
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.networkAgentConfig =
                        (NetworkAgentConfig) parcel.readTypedObject(NetworkAgentConfig.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.networkCapabilities =
                            (NetworkCapabilities)
                                    parcel.readTypedObject(NetworkCapabilities.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.linkProperties =
                                (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt);
                        return;
                    }
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("networkAgentConfig: " + Objects.toString(this.networkAgentConfig));
        stringJoiner.add("networkCapabilities: " + Objects.toString(this.networkCapabilities));
        stringJoiner.add("linkProperties: " + Objects.toString(this.linkProperties));
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("NetworkMonitorParameters"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.networkAgentConfig, i);
        parcel.writeTypedObject(this.networkCapabilities, i);
        parcel.writeTypedObject(this.linkProperties, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
