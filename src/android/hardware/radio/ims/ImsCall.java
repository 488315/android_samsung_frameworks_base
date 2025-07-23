package android.hardware.radio.ims;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ImsCall implements Parcelable {
    public static final Parcelable.Creator<ImsCall> CREATOR = new Parcelable.Creator<ImsCall>() { // from class: android.hardware.radio.ims.ImsCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCall createFromParcel(Parcel parcel) {
            ImsCall imsCall = new ImsCall();
            imsCall.readFromParcel(parcel);
            return imsCall;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCall[] newArray(int i) {
            return new ImsCall[i];
        }
    };
    public int index = 0;
    public int callType = 0;
    public int accessNetwork = 0;
    public int callState = 0;
    public int direction = 0;
    public boolean isHeldByRemote = false;

    public @interface CallState {
        public static final int ACTIVE = 0;
        public static final int ALERTING = 3;
        public static final int DIALING = 2;
        public static final int DISCONNECTED = 7;
        public static final int DISCONNECTING = 6;
        public static final int HOLDING = 1;
        public static final int INCOMING = 4;
        public static final int WAITING = 5;
    }

    public @interface CallType {
        public static final int EMERGENCY = 1;
        public static final int NORMAL = 0;
    }

    public @interface Direction {
        public static final int INCOMING = 0;
        public static final int OUTGOING = 1;
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
        parcel.writeInt(this.accessNetwork);
        parcel.writeInt(this.callState);
        parcel.writeInt(this.direction);
        parcel.writeBoolean(this.isHeldByRemote);
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
                        this.accessNetwork = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.callState = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.direction = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.isHeldByRemote = parcel.readBoolean();
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
        stringJoiner.add("accessNetwork: " + AccessNetwork$$.toString(this.accessNetwork));
        stringJoiner.add("callState: " + this.callState);
        stringJoiner.add("direction: " + this.direction);
        stringJoiner.add("isHeldByRemote: " + this.isHeldByRemote);
        return "ImsCall" + stringJoiner.toString();
    }
}
