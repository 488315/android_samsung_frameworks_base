package android.hardware.radio.ims;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SrvccCall implements Parcelable {
    public static final Parcelable.Creator<SrvccCall> CREATOR = new Parcelable.Creator<SrvccCall>() { // from class: android.hardware.radio.ims.SrvccCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SrvccCall createFromParcel(Parcel parcel) {
            SrvccCall srvccCall = new SrvccCall();
            srvccCall.readFromParcel(parcel);
            return srvccCall;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SrvccCall[] newArray(int i) {
            return new SrvccCall[i];
        }
    };
    public String name;
    public String number;
    public int index = 0;
    public int callType = 0;
    public int callState = 0;
    public int callSubstate = 0;
    public int ringbackToneType = 0;
    public boolean isMpty = false;
    public boolean isMT = false;
    public int numPresentation = 0;
    public int namePresentation = 0;

    public @interface CallSubState {
        public static final int NONE = 0;
        public static final int PREALERTING = 1;
    }

    public @interface CallType {
        public static final int EMERGENCY = 1;
        public static final int NORMAL = 0;
    }

    public @interface ToneType {
        public static final int LOCAL = 1;
        public static final int NETWORK = 2;
        public static final int NONE = 0;
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
        parcel.writeInt(this.index);
        parcel.writeInt(this.callType);
        parcel.writeInt(this.callState);
        parcel.writeInt(this.callSubstate);
        parcel.writeInt(this.ringbackToneType);
        parcel.writeBoolean(this.isMpty);
        parcel.writeBoolean(this.isMT);
        parcel.writeString(this.number);
        parcel.writeInt(this.numPresentation);
        parcel.writeString(this.name);
        parcel.writeInt(this.namePresentation);
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
                    this.callType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.callState = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.callSubstate = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.ringbackToneType = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.isMpty = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isMT = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.number = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.numPresentation = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.name = parcel.readString();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.namePresentation = parcel.readInt();
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
        stringJoiner.add("callType: " + this.callType);
        stringJoiner.add("callState: " + this.callState);
        stringJoiner.add("callSubstate: " + this.callSubstate);
        stringJoiner.add("ringbackToneType: " + this.ringbackToneType);
        stringJoiner.add("isMpty: " + this.isMpty);
        stringJoiner.add("isMT: " + this.isMT);
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("numPresentation: " + this.numPresentation);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("namePresentation: " + this.namePresentation);
        return "SrvccCall" + stringJoiner.toString();
    }
}
