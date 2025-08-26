package android.hardware.power;

import android.hardware.common.fmq.MQDescriptor;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ChannelConfig implements Parcelable {
    public static final Parcelable.Creator<ChannelConfig> CREATOR = new Parcelable.Creator<ChannelConfig>() { // from class: android.hardware.power.ChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelConfig createFromParcel(Parcel parcel) {
            ChannelConfig channelConfig = new ChannelConfig();
            channelConfig.readFromParcel(parcel);
            return channelConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChannelConfig[] newArray(int i) {
            return new ChannelConfig[i];
        }
    };
    public MQDescriptor<ChannelMessage, Byte> channelDescriptor;
    public MQDescriptor<Byte, Byte> eventFlagDescriptor;
    public int readFlagBitmask = 0;
    public int writeFlagBitmask = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.channelDescriptor, i);
        parcel.writeTypedObject(this.eventFlagDescriptor, i);
        parcel.writeInt(this.readFlagBitmask);
        parcel.writeInt(this.writeFlagBitmask);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.channelDescriptor = (MQDescriptor) parcel.readTypedObject(MQDescriptor.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.eventFlagDescriptor = (MQDescriptor) parcel.readTypedObject(MQDescriptor.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.readFlagBitmask = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.writeFlagBitmask = parcel.readInt();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.eventFlagDescriptor) | describeContents(this.channelDescriptor);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
