package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class InitialConfigurationParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public IpPrefix[] directlyConnectedRoutes;
    public String[] dnsServers;
    public String gateway;
    public LinkAddress[] ipAddresses;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.InitialConfigurationParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            InitialConfigurationParcelable initialConfigurationParcelable = new InitialConfigurationParcelable();
            initialConfigurationParcelable.readFromParcel(parcel);
            return initialConfigurationParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new InitialConfigurationParcelable[i];
        }
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.directlyConnectedRoutes) | describeContents(this.ipAddresses);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.ipAddresses = (LinkAddress[]) parcel.createTypedArray(LinkAddress.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.directlyConnectedRoutes = (IpPrefix[]) parcel.createTypedArray(IpPrefix.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.dnsServers = parcel.createStringArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.gateway = parcel.readString();
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
        return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, DabTableEntry$$ExternalSyntheticOutline0.m(this.gateway, "InitialConfigurationParcelable", AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.dnsServers), "gateway: ", AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.directlyConnectedRoutes), "dnsServers: ", AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.ipAddresses), "directlyConnectedRoutes: ", new StringBuilder("ipAddresses: "), stringJoiner), stringJoiner), stringJoiner), stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.ipAddresses, i);
        parcel.writeTypedArray(this.directlyConnectedRoutes, i);
        parcel.writeStringArray(this.dnsServers);
        parcel.writeString(this.gateway);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
