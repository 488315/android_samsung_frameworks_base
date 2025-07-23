package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class IccIo implements Parcelable {
    public static final Parcelable.Creator<IccIo> CREATOR = new Parcelable.Creator<IccIo>() { // from class: android.hardware.radio.sim.IccIo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccIo createFromParcel(Parcel parcel) {
            IccIo iccIo = new IccIo();
            iccIo.readFromParcel(parcel);
            return iccIo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IccIo[] newArray(int i) {
            return new IccIo[i];
        }
    };
    public String aid;
    public String data;
    public String path;
    public String pin2;
    public int command = 0;
    public int fileId = 0;
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;

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
        parcel.writeInt(this.command);
        parcel.writeInt(this.fileId);
        parcel.writeString(this.path);
        parcel.writeInt(this.p1);
        parcel.writeInt(this.p2);
        parcel.writeInt(this.p3);
        parcel.writeString(this.data);
        parcel.writeString(this.pin2);
        parcel.writeString(this.aid);
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
                this.command = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.fileId = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.path = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.p1 = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.p2 = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.p3 = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.data = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.pin2 = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.aid = parcel.readString();
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
        stringJoiner.add("command: " + this.command);
        stringJoiner.add("fileId: " + this.fileId);
        stringJoiner.add("path: " + Objects.toString(this.path));
        stringJoiner.add("p1: " + this.p1);
        stringJoiner.add("p2: " + this.p2);
        stringJoiner.add("p3: " + this.p3);
        stringJoiner.add("data: " + Objects.toString(this.data));
        stringJoiner.add("pin2: " + Objects.toString(this.pin2));
        stringJoiner.add("aid: " + Objects.toString(this.aid));
        return "IccIo" + stringJoiner.toString();
    }
}
