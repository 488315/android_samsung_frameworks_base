package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CallForwardInfo implements Parcelable {
    public static final Parcelable.Creator<CallForwardInfo> CREATOR = new Parcelable.Creator<CallForwardInfo>() { // from class: android.hardware.radio.voice.CallForwardInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallForwardInfo createFromParcel(Parcel parcel) {
            CallForwardInfo callForwardInfo = new CallForwardInfo();
            callForwardInfo.readFromParcel(parcel);
            return callForwardInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallForwardInfo[] newArray(int i) {
            return new CallForwardInfo[i];
        }
    };
    public static final int STATUS_DISABLE = 0;
    public static final int STATUS_ENABLE = 1;
    public static final int STATUS_ERASURE = 4;
    public static final int STATUS_INTERROGATE = 2;
    public static final int STATUS_REGISTRATION = 3;
    public String number;
    public int status = 0;
    public int reason = 0;
    public int serviceClass = 0;
    public int toa = 0;
    public int timeSeconds = 0;

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
        parcel.writeInt(this.status);
        parcel.writeInt(this.reason);
        parcel.writeInt(this.serviceClass);
        parcel.writeInt(this.toa);
        parcel.writeString(this.number);
        parcel.writeInt(this.timeSeconds);
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
                this.status = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.reason = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.serviceClass = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.toa = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.number = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.timeSeconds = parcel.readInt();
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
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("reason: " + this.reason);
        stringJoiner.add("serviceClass: " + this.serviceClass);
        stringJoiner.add("toa: " + this.toa);
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("timeSeconds: " + this.timeSeconds);
        return "CallForwardInfo" + stringJoiner.toString();
    }
}
