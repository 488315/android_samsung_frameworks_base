package com.android.internal.telephony;

import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes4.dex */
public class IccLogicalChannelRequest implements Parcelable {
    public static final Parcelable.Creator<IccLogicalChannelRequest> CREATOR = new Parcelable.Creator<IccLogicalChannelRequest>() { // from class: com.android.internal.telephony.IccLogicalChannelRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccLogicalChannelRequest createFromParcel(Parcel parcel) {
            IccLogicalChannelRequest iccLogicalChannelRequest = new IccLogicalChannelRequest();
            iccLogicalChannelRequest.readFromParcel(parcel);
            return iccLogicalChannelRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccLogicalChannelRequest[] newArray(int i) {
            return new IccLogicalChannelRequest[i];
        }
    };
    public String aid;
    public IBinder binder;
    public String callingPackage;
    public int subId = -1;
    public int slotIndex = -1;
    public int portIndex = 0;
    public int p2 = 0;
    public int channel = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subId);
        parcel.writeInt(this.slotIndex);
        parcel.writeInt(this.portIndex);
        parcel.writeString(this.callingPackage);
        parcel.writeString(this.aid);
        parcel.writeInt(this.p2);
        parcel.writeInt(this.channel);
        parcel.writeStrongBinder(this.binder);
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
                this.subId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.slotIndex = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.portIndex = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.callingPackage = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.aid = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.p2 = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.channel = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.binder = parcel.readStrongBinder();
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
        stringJoiner.add("subId: " + this.subId);
        stringJoiner.add("slotIndex: " + this.slotIndex);
        stringJoiner.add("portIndex: " + this.portIndex);
        stringJoiner.add("callingPackage: " + Objects.toString(this.callingPackage));
        stringJoiner.add("aid: " + Objects.toString(this.aid));
        stringJoiner.add("p2: " + this.p2);
        stringJoiner.add("channel: " + this.channel);
        stringJoiner.add("binder: " + Objects.toString(this.binder));
        return "IccLogicalChannelRequest" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IccLogicalChannelRequest)) {
            return false;
        }
        IccLogicalChannelRequest iccLogicalChannelRequest = (IccLogicalChannelRequest) obj;
        return Objects.deepEquals(Integer.valueOf(this.subId), Integer.valueOf(iccLogicalChannelRequest.subId)) && Objects.deepEquals(Integer.valueOf(this.slotIndex), Integer.valueOf(iccLogicalChannelRequest.slotIndex)) && Objects.deepEquals(Integer.valueOf(this.portIndex), Integer.valueOf(iccLogicalChannelRequest.portIndex)) && Objects.deepEquals(this.callingPackage, iccLogicalChannelRequest.callingPackage) && Objects.deepEquals(this.aid, iccLogicalChannelRequest.aid) && Objects.deepEquals(Integer.valueOf(this.p2), Integer.valueOf(iccLogicalChannelRequest.p2)) && Objects.deepEquals(Integer.valueOf(this.channel), Integer.valueOf(iccLogicalChannelRequest.channel)) && Objects.deepEquals(this.binder, iccLogicalChannelRequest.binder);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.subId), Integer.valueOf(this.slotIndex), Integer.valueOf(this.portIndex), this.callingPackage, this.aid, Integer.valueOf(this.p2), Integer.valueOf(this.channel), this.binder).toArray());
    }
}
