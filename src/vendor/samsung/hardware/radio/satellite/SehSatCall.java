package vendor.samsung.hardware.radio.satellite;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class SehSatCall implements Parcelable {
    public static final Parcelable.Creator<SehSatCall> CREATOR = new Parcelable.Creator<SehSatCall>() { // from class: vendor.samsung.hardware.radio.satellite.SehSatCall.1
        @Override // android.os.Parcelable.Creator
        public SehSatCall createFromParcel(Parcel parcel) {
            SehSatCall sehSatCall = new SehSatCall();
            sehSatCall.readFromParcel(parcel);
            return sehSatCall;
        }

        @Override // android.os.Parcelable.Creator
        public SehSatCall[] newArray(int i) {
            return new SehSatCall[i];
        }
    };
    public String alpha;
    public String number;
    public int index = 0;
    public int dir = 0;
    public int status = 0;
    public int mode = 0;
    public boolean mpty = false;
    public int type = 0;
    public int priority = 0;
    public int cli = 0;

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
        parcel.writeInt(this.index);
        parcel.writeInt(this.dir);
        parcel.writeInt(this.status);
        parcel.writeInt(this.mode);
        parcel.writeBoolean(this.mpty);
        parcel.writeString(this.number);
        parcel.writeInt(this.type);
        parcel.writeString(this.alpha);
        parcel.writeInt(this.priority);
        parcel.writeInt(this.cli);
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
                this.index = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.dir = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.status = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mode = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.mpty = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.number = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.type = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.alpha = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.priority = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.cli = parcel.readInt();
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
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("dir: " + this.dir);
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("mode: " + this.mode);
        stringJoiner.add("mpty: " + this.mpty);
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("alpha: " + Objects.toString(this.alpha));
        stringJoiner.add("priority: " + this.priority);
        stringJoiner.add("cli: " + this.cli);
        return "SehSatCall" + stringJoiner.toString();
    }
}
