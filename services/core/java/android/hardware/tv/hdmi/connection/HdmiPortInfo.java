package android.hardware.tv.hdmi.connection;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HdmiPortInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public boolean arcSupported;
    public boolean cecSupported;
    public boolean eArcSupported;
    public int physicalAddress;
    public int portId;
    public byte type;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.tv.hdmi.connection.HdmiPortInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            HdmiPortInfo hdmiPortInfo = new HdmiPortInfo();
            hdmiPortInfo.portId = 0;
            hdmiPortInfo.cecSupported = false;
            hdmiPortInfo.arcSupported = false;
            hdmiPortInfo.eArcSupported = false;
            hdmiPortInfo.physicalAddress = 0;
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    hdmiPortInfo.type = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        hdmiPortInfo.portId = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            hdmiPortInfo.cecSupported = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                hdmiPortInfo.arcSupported = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    hdmiPortInfo.eArcSupported = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        hdmiPortInfo.physicalAddress = parcel.readInt();
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
                return hdmiPortInfo;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new HdmiPortInfo[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.type);
        parcel.writeInt(this.portId);
        parcel.writeBoolean(this.cecSupported);
        parcel.writeBoolean(this.arcSupported);
        parcel.writeBoolean(this.eArcSupported);
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.physicalAddress, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
