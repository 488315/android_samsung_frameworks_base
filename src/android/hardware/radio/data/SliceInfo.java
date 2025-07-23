package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SliceInfo implements Parcelable {
    public static final Parcelable.Creator<SliceInfo> CREATOR = new Parcelable.Creator<SliceInfo>() { // from class: android.hardware.radio.data.SliceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceInfo createFromParcel(Parcel parcel) {
            SliceInfo sliceInfo = new SliceInfo();
            sliceInfo.readFromParcel(parcel);
            return sliceInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceInfo[] newArray(int i) {
            return new SliceInfo[i];
        }
    };
    public static final byte SERVICE_TYPE_EMBB = 1;
    public static final byte SERVICE_TYPE_MIOT = 3;
    public static final byte SERVICE_TYPE_NONE = 0;
    public static final byte SERVICE_TYPE_URLLC = 2;
    public static final byte STATUS_ALLOWED = 2;
    public static final byte STATUS_CONFIGURED = 1;
    public static final byte STATUS_DEFAULT_CONFIGURED = 5;
    public static final byte STATUS_REJECTED_NOT_AVAILABLE_IN_PLMN = 3;
    public static final byte STATUS_REJECTED_NOT_AVAILABLE_IN_REG_AREA = 4;
    public static final byte STATUS_UNKNOWN = 0;
    public byte sliceServiceType = 0;
    public int sliceDifferentiator = 0;
    public byte mappedHplmnSst = 0;
    public int mappedHplmnSd = 0;
    public byte status = 0;

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
        parcel.writeByte(this.sliceServiceType);
        parcel.writeInt(this.sliceDifferentiator);
        parcel.writeByte(this.mappedHplmnSst);
        parcel.writeInt(this.mappedHplmnSd);
        parcel.writeByte(this.status);
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
                this.sliceServiceType = parcel.readByte();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sliceDifferentiator = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mappedHplmnSst = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mappedHplmnSd = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.status = parcel.readByte();
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
        stringJoiner.add("sliceServiceType: " + ((int) this.sliceServiceType));
        stringJoiner.add("sliceDifferentiator: " + this.sliceDifferentiator);
        stringJoiner.add("mappedHplmnSst: " + ((int) this.mappedHplmnSst));
        stringJoiner.add("mappedHplmnSd: " + this.mappedHplmnSd);
        stringJoiner.add("status: " + ((int) this.status));
        return "SliceInfo" + stringJoiner.toString();
    }
}
