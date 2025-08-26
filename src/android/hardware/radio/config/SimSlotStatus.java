package android.hardware.radio.config;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SimSlotStatus implements Parcelable {
    public static final Parcelable.Creator<SimSlotStatus> CREATOR = new Parcelable.Creator<SimSlotStatus>() { // from class: android.hardware.radio.config.SimSlotStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimSlotStatus createFromParcel(Parcel parcel) {
            SimSlotStatus simSlotStatus = new SimSlotStatus();
            simSlotStatus.readFromParcel(parcel);
            return simSlotStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimSlotStatus[] newArray(int i) {
            return new SimSlotStatus[i];
        }
    };
    public String atr;
    public String eid;
    public SimPortInfo[] portInfo;
    public int cardState = 0;
    public int supportedMepMode = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cardState);
        parcel.writeString(this.atr);
        parcel.writeString(this.eid);
        parcel.writeTypedArray(this.portInfo, i);
        parcel.writeInt(this.supportedMepMode);
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
                this.cardState = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.atr = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.eid = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.portInfo = (SimPortInfo[]) parcel.createTypedArray(SimPortInfo.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.supportedMepMode = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("cardState: " + this.cardState);
        stringJoiner.add("atr: " + Objects.toString(this.atr));
        stringJoiner.add("eid: " + Objects.toString(this.eid));
        stringJoiner.add("portInfo: " + Arrays.toString(this.portInfo));
        stringJoiner.add("supportedMepMode: " + MultipleEnabledProfilesMode$$.toString(this.supportedMepMode));
        return "SimSlotStatus" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.portInfo);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
