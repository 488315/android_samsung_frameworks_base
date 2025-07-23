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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.channelDescriptor, i);
        parcel.writeTypedObject(this.eventFlagDescriptor, i);
        parcel.writeInt(this.readFlagBitmask);
        parcel.writeInt(this.writeFlagBitmask);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.channelDescriptor = (MQDescriptor) parcel.readTypedObject(MQDescriptor.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.eventFlagDescriptor = (MQDescriptor) parcel.readTypedObject(MQDescriptor.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.readFlagBitmask = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.writeFlagBitmask = parcel.readInt();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
