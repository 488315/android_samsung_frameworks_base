package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class DhcpResultsParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public StaticIpConfiguration baseConfiguration;
    public String captivePortalApiUrl;
    public int leaseDuration = 0;
    public int mtu = 0;
    public String serverAddress;
    public String serverHostName;
    public String vendorInfo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.DhcpResultsParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DhcpResultsParcelable dhcpResultsParcelable = new DhcpResultsParcelable();
            dhcpResultsParcelable.readFromParcel(parcel);
            return dhcpResultsParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DhcpResultsParcelable[i];
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
        return describeContents(this.baseConfiguration);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.baseConfiguration =
                        (StaticIpConfiguration)
                                parcel.readTypedObject(StaticIpConfiguration.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.leaseDuration = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mtu = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.serverAddress = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.vendorInfo = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.serverHostName = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.captivePortalApiUrl = parcel.readString();
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                        parcel.setDataPosition(dataPosition + readInt);
                                        return;
                                    }
                                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
        stringJoiner.add("baseConfiguration: " + Objects.toString(this.baseConfiguration));
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner,
                DabTableEntry$$ExternalSyntheticOutline0.m(
                        this.captivePortalApiUrl,
                        "DhcpResultsParcelable",
                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                this.serverHostName,
                                "captivePortalApiUrl: ",
                                DabTableEntry$$ExternalSyntheticOutline0.m(
                                        this.vendorInfo,
                                        "serverHostName: ",
                                        DabTableEntry$$ExternalSyntheticOutline0.m(
                                                this.serverAddress,
                                                "vendorInfo: ",
                                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                                                new StringBuilder(
                                                                        "leaseDuration: "),
                                                                this.leaseDuration,
                                                                stringJoiner,
                                                                "mtu: "),
                                                        this.mtu,
                                                        stringJoiner,
                                                        "serverAddress: "),
                                                stringJoiner),
                                        stringJoiner),
                                stringJoiner),
                        stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.baseConfiguration, i);
        parcel.writeInt(this.leaseDuration);
        parcel.writeInt(this.mtu);
        parcel.writeString(this.serverAddress);
        parcel.writeString(this.vendorInfo);
        parcel.writeString(this.serverHostName);
        parcel.writeString(this.captivePortalApiUrl);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
