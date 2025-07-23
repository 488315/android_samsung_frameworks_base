package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class MicrophoneDynamicInfo implements Parcelable {
    public static final Parcelable.Creator<MicrophoneDynamicInfo> CREATOR = new Parcelable.Creator<MicrophoneDynamicInfo>() { // from class: android.media.audio.common.MicrophoneDynamicInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneDynamicInfo createFromParcel(Parcel parcel) {
            MicrophoneDynamicInfo microphoneDynamicInfo = new MicrophoneDynamicInfo();
            microphoneDynamicInfo.readFromParcel(parcel);
            return microphoneDynamicInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MicrophoneDynamicInfo[] newArray(int i) {
            return new MicrophoneDynamicInfo[i];
        }
    };
    public int[] channelMapping;
    public String id;

    public @interface ChannelMapping {
        public static final int DIRECT = 1;
        public static final int PROCESSED = 2;
        public static final int UNUSED = 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.id);
        parcel.writeIntArray(this.channelMapping);
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
                this.id = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.channelMapping = parcel.createIntArray();
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
        stringJoiner.add("id: " + Objects.toString(this.id));
        stringJoiner.add("channelMapping: " + Arrays.toString(this.channelMapping));
        return "MicrophoneDynamicInfo" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MicrophoneDynamicInfo)) {
            return false;
        }
        MicrophoneDynamicInfo microphoneDynamicInfo = (MicrophoneDynamicInfo) obj;
        return Objects.deepEquals(this.id, microphoneDynamicInfo.id) && Objects.deepEquals(this.channelMapping, microphoneDynamicInfo.channelMapping);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.id, this.channelMapping).toArray());
    }
}
