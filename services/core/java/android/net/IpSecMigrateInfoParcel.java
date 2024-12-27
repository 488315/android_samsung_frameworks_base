package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class IpSecMigrateInfoParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final int direction;
    public final int interfaceId;
    public final String newDestinationAddress;
    public final String newSourceAddress;
    public final String oldDestinationAddress;
    public final String oldSourceAddress;
    public final int requestId;
    public final int selAddrFamily;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.IpSecMigrateInfoParcel$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return IpSecMigrateInfoParcel.internalCreateFromParcel(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new IpSecMigrateInfoParcel[i];
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        private String newDestinationAddress;
        private String newSourceAddress;
        private String oldDestinationAddress;
        private String oldSourceAddress;
        private int requestId = 0;
        private int selAddrFamily = 0;
        private int direction = 0;
        private int interfaceId = 0;

        public IpSecMigrateInfoParcel build() {
            return new IpSecMigrateInfoParcel(
                    this.requestId,
                    this.selAddrFamily,
                    this.direction,
                    this.oldSourceAddress,
                    this.oldDestinationAddress,
                    this.newSourceAddress,
                    this.newDestinationAddress,
                    this.interfaceId);
        }

        public Builder setDirection(int i) {
            this.direction = i;
            return this;
        }

        public Builder setInterfaceId(int i) {
            this.interfaceId = i;
            return this;
        }

        public Builder setNewDestinationAddress(String str) {
            this.newDestinationAddress = str;
            return this;
        }

        public Builder setNewSourceAddress(String str) {
            this.newSourceAddress = str;
            return this;
        }

        public Builder setOldDestinationAddress(String str) {
            this.oldDestinationAddress = str;
            return this;
        }

        public Builder setOldSourceAddress(String str) {
            this.oldSourceAddress = str;
            return this;
        }

        public Builder setRequestId(int i) {
            this.requestId = i;
            return this;
        }

        public Builder setSelAddrFamily(int i) {
            this.selAddrFamily = i;
            return this;
        }
    }

    public IpSecMigrateInfoParcel(
            int i, int i2, int i3, String str, String str2, String str3, String str4, int i4) {
        this.requestId = i;
        this.selAddrFamily = i2;
        this.direction = i3;
        this.oldSourceAddress = str;
        this.oldDestinationAddress = str2;
        this.newSourceAddress = str3;
        this.newDestinationAddress = str4;
        this.interfaceId = i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IpSecMigrateInfoParcel internalCreateFromParcel(Parcel parcel) {
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
            builder.setRequestId(parcel.readInt());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                builder.build();
                int i = Integer.MAX_VALUE - readInt;
                if (dataPosition > i) {
                    throw new BadParcelableException(r4);
                }
            } else {
                builder.setSelAddrFamily(parcel.readInt());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    builder.build();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else {
                    builder.setDirection(parcel.readInt());
                    if (parcel.dataPosition() - dataPosition >= readInt) {
                        builder.build();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else {
                        builder.setOldSourceAddress(parcel.readString());
                        if (parcel.dataPosition() - dataPosition >= readInt) {
                            builder.build();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
                            }
                        } else {
                            builder.setOldDestinationAddress(parcel.readString());
                            if (parcel.dataPosition() - dataPosition >= readInt) {
                                builder.build();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else {
                                builder.setNewSourceAddress(parcel.readString());
                                if (parcel.dataPosition() - dataPosition >= readInt) {
                                    builder.build();
                                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else {
                                    builder.setNewDestinationAddress(parcel.readString());
                                    if (parcel.dataPosition() - dataPosition >= readInt) {
                                        builder.build();
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else {
                                        builder.setInterfaceId(parcel.readInt());
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
            }
        }
        parcel.setDataPosition(dataPosition + readInt);
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.requestId);
        parcel.writeInt(this.selAddrFamily);
        parcel.writeInt(this.direction);
        parcel.writeString(this.oldSourceAddress);
        parcel.writeString(this.oldDestinationAddress);
        parcel.writeString(this.newSourceAddress);
        parcel.writeString(this.newDestinationAddress);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.interfaceId, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
