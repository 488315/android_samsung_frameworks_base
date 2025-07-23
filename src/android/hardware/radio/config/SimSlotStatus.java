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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cardState);
        parcel.writeString(this.atr);
        parcel.writeString(this.eid);
        parcel.writeTypedArray(this.portInfo, i);
        parcel.writeInt(this.supportedMepMode);
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
                this.cardState = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.atr = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.eid = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.portInfo = (SimPortInfo[]) parcel.createTypedArray(SimPortInfo.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.supportedMepMode = parcel.readInt();
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
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
