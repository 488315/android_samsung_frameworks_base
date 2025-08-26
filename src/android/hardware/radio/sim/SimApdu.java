package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SimApdu implements Parcelable {
    public static final Parcelable.Creator<SimApdu> CREATOR = new Parcelable.Creator<SimApdu>() { // from class: android.hardware.radio.sim.SimApdu.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimApdu createFromParcel(Parcel parcel) {
            SimApdu simApdu = new SimApdu();
            simApdu.readFromParcel(parcel);
            return simApdu;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimApdu[] newArray(int i) {
            return new SimApdu[i];
        }
    };
    public String data;
    public int sessionId = 0;
    public int cla = 0;
    public int instruction = 0;
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public boolean isEs10 = false;

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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionId);
        parcel.writeInt(this.cla);
        parcel.writeInt(this.instruction);
        parcel.writeInt(this.p1);
        parcel.writeInt(this.p2);
        parcel.writeInt(this.p3);
        parcel.writeString(this.data);
        parcel.writeBoolean(this.isEs10);
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
                this.sessionId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.cla = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.instruction = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.p1 = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.p2 = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.p3 = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.data = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.isEs10 = parcel.readBoolean();
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
        stringJoiner.add("sessionId: " + this.sessionId);
        stringJoiner.add("cla: " + this.cla);
        stringJoiner.add("instruction: " + this.instruction);
        stringJoiner.add("p1: " + this.p1);
        stringJoiner.add("p2: " + this.p2);
        stringJoiner.add("p3: " + this.p3);
        stringJoiner.add("data: " + Objects.toString(this.data));
        stringJoiner.add("isEs10: " + this.isEs10);
        return "SimApdu" + stringJoiner.toString();
    }
}
