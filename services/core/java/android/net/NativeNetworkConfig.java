package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class NativeNetworkConfig implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final boolean excludeLocalRoutes;
    public final int netId;
    public final int networkType;
    public final int permission;
    public final boolean secure;
    public final int vpnType;

    /* renamed from: android.net.NativeNetworkConfig$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return NativeNetworkConfig.internalCreateFromParcel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NativeNetworkConfig[i];
        }
    }

    public final class Builder {
        private int netId = 0;
        private int networkType = 0;
        private int permission = 0;
        private boolean secure = false;
        private int vpnType = 2;
        private boolean excludeLocalRoutes = false;

        public NativeNetworkConfig build() {
            return new NativeNetworkConfig(
                    this.netId,
                    this.networkType,
                    this.permission,
                    this.secure,
                    this.vpnType,
                    this.excludeLocalRoutes);
        }

        public Builder setExcludeLocalRoutes(boolean z) {
            this.excludeLocalRoutes = z;
            return this;
        }

        public Builder setNetId(int i) {
            this.netId = i;
            return this;
        }

        public Builder setNetworkType(int i) {
            this.networkType = i;
            return this;
        }

        public Builder setPermission(int i) {
            this.permission = i;
            return this;
        }

        public Builder setSecure(boolean z) {
            this.secure = z;
            return this;
        }

        public Builder setVpnType(int i) {
            this.vpnType = i;
            return this;
        }
    }

    public NativeNetworkConfig(int i, int i2, int i3, boolean z, int i4, boolean z2) {
        this.netId = i;
        this.networkType = i2;
        this.permission = i3;
        this.secure = z;
        this.vpnType = i4;
        this.excludeLocalRoutes = z2;
    }

    public static NativeNetworkConfig internalCreateFromParcel(Parcel parcel) {
        Builder builder = new Builder();
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
        } finally {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                BadParcelableException badParcelableException =
                        new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            return builder.build();
        }
        if (readInt < 4) {
            throw new BadParcelableException("Parcelable too small");
        }
        builder.build();
        if (parcel.dataPosition() - dataPosition >= readInt) {
            builder.build();
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
        } else {
            builder.setNetId(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                int i = Integer.MAX_VALUE - readInt;
                if (dataPosition > i) {
                    throw new BadParcelableException(r4);
                }
            } else {
                builder.setNetworkType(parcel.readInt());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setPermission(parcel.readInt());
                    if (parcel.dataPosition() - dataPosition >= readInt) {
                        builder.build();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else {
                        builder.setSecure(parcel.readBoolean());
                        if (parcel.dataPosition() - dataPosition >= readInt) {
                            builder.build();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
                            }
                        } else {
                            builder.setVpnType(parcel.readInt());
                            if (parcel.dataPosition() - dataPosition >= readInt) {
                                builder.build();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else {
                                builder.setExcludeLocalRoutes(parcel.readBoolean());
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            }
                        }
                    }
                }
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NativeNetworkConfig)) {
            return false;
        }
        NativeNetworkConfig nativeNetworkConfig = (NativeNetworkConfig) obj;
        return Objects.deepEquals(
                        Integer.valueOf(this.netId), Integer.valueOf(nativeNetworkConfig.netId))
                && Objects.deepEquals(
                        Integer.valueOf(this.networkType),
                        Integer.valueOf(nativeNetworkConfig.networkType))
                && Objects.deepEquals(
                        Integer.valueOf(this.permission),
                        Integer.valueOf(nativeNetworkConfig.permission))
                && Objects.deepEquals(
                        Boolean.valueOf(this.secure), Boolean.valueOf(nativeNetworkConfig.secure))
                && Objects.deepEquals(
                        Integer.valueOf(this.vpnType), Integer.valueOf(nativeNetworkConfig.vpnType))
                && Objects.deepEquals(
                        Boolean.valueOf(this.excludeLocalRoutes),
                        Boolean.valueOf(nativeNetworkConfig.excludeLocalRoutes));
    }

    public int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(
                                Integer.valueOf(this.netId),
                                Integer.valueOf(this.networkType),
                                Integer.valueOf(this.permission),
                                Boolean.valueOf(this.secure),
                                Integer.valueOf(this.vpnType),
                                Boolean.valueOf(this.excludeLocalRoutes))
                        .toArray());
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        StringBuilder m =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                AmFmBandRange$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("netId: "),
                                        this.netId,
                                        stringJoiner,
                                        "networkType: "),
                                this.networkType,
                                stringJoiner,
                                "permission: "),
                        this.permission,
                        stringJoiner,
                        "secure: ");
        m.append(this.secure);
        stringJoiner.add(m.toString());
        StringBuilder m2 =
                AmFmBandRange$$ExternalSyntheticOutline0.m(
                        new StringBuilder("vpnType: "),
                        this.vpnType,
                        stringJoiner,
                        "excludeLocalRoutes: ");
        m2.append(this.excludeLocalRoutes);
        stringJoiner.add(m2.toString());
        return AmFmBandRange$$ExternalSyntheticOutline0.m(
                stringJoiner, new StringBuilder("NativeNetworkConfig"));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.netId);
        parcel.writeInt(this.networkType);
        parcel.writeInt(this.permission);
        parcel.writeBoolean(this.secure);
        parcel.writeInt(this.vpnType);
        parcel.writeBoolean(this.excludeLocalRoutes);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
