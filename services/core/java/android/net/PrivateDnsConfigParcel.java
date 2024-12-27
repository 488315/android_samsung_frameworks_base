package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmRegionConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class PrivateDnsConfigParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String hostname;
    public String[] ips;
    public int privateDnsMode = -1;
    public String dohName = "";
    public String[] dohIps = new String[0];
    public String dohPath = "";
    public int dohPort = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.PrivateDnsConfigParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            PrivateDnsConfigParcel privateDnsConfigParcel = new PrivateDnsConfigParcel();
            privateDnsConfigParcel.readFromParcel(parcel);
            return privateDnsConfigParcel;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PrivateDnsConfigParcel[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PrivateDnsConfigParcel)) {
            return false;
        }
        PrivateDnsConfigParcel privateDnsConfigParcel = (PrivateDnsConfigParcel) obj;
        return Objects.deepEquals(this.hostname, privateDnsConfigParcel.hostname) && Objects.deepEquals(this.ips, privateDnsConfigParcel.ips) && Objects.deepEquals(Integer.valueOf(this.privateDnsMode), Integer.valueOf(privateDnsConfigParcel.privateDnsMode)) && Objects.deepEquals(this.dohName, privateDnsConfigParcel.dohName) && Objects.deepEquals(this.dohIps, privateDnsConfigParcel.dohIps) && Objects.deepEquals(this.dohPath, privateDnsConfigParcel.dohPath) && Objects.deepEquals(Integer.valueOf(this.dohPort), Integer.valueOf(privateDnsConfigParcel.dohPort));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.hostname, this.ips, Integer.valueOf(this.privateDnsMode), this.dohName, this.dohIps, this.dohPath, Integer.valueOf(this.dohPort)).toArray());
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.hostname = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.ips = parcel.createStringArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.privateDnsMode = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.dohName = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.dohIps = parcel.createStringArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.dohPath = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.dohPort = parcel.readInt();
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
        return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, AmFmBandRange$$ExternalSyntheticOutline0.m(DabTableEntry$$ExternalSyntheticOutline0.m(this.dohPath, "dohPort: ", AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.dohIps), "dohPath: ", DabTableEntry$$ExternalSyntheticOutline0.m(this.dohName, "dohIps: ", AmFmBandRange$$ExternalSyntheticOutline0.m(AmFmRegionConfig$$ExternalSyntheticOutline0.m(Arrays.toString(this.ips), "privateDnsMode: ", DabTableEntry$$ExternalSyntheticOutline0.m(this.hostname, "ips: ", new StringBuilder("hostname: "), stringJoiner), stringJoiner), this.privateDnsMode, stringJoiner, "dohName: "), stringJoiner), stringJoiner), stringJoiner), this.dohPort, stringJoiner, "PrivateDnsConfigParcel"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.hostname);
        parcel.writeStringArray(this.ips);
        parcel.writeInt(this.privateDnsMode);
        parcel.writeString(this.dohName);
        parcel.writeStringArray(this.dohIps);
        parcel.writeString(this.dohPath);
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.dohPort, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
