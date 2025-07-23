package android.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AppStatus implements Parcelable {
    public static final int APP_STATE_DETECTED = 1;
    public static final int APP_STATE_PIN = 2;
    public static final int APP_STATE_PUK = 3;
    public static final int APP_STATE_READY = 5;
    public static final int APP_STATE_SUBSCRIPTION_PERSO = 4;
    public static final int APP_STATE_UNKNOWN = 0;
    public static final int APP_TYPE_CSIM = 4;
    public static final int APP_TYPE_ISIM = 5;
    public static final int APP_TYPE_RUIM = 3;
    public static final int APP_TYPE_SIM = 1;
    public static final int APP_TYPE_UNKNOWN = 0;
    public static final int APP_TYPE_USIM = 2;
    public static final Parcelable.Creator<AppStatus> CREATOR = new Parcelable.Creator<AppStatus>() { // from class: android.hardware.radio.sim.AppStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppStatus createFromParcel(Parcel parcel) {
            AppStatus appStatus = new AppStatus();
            appStatus.readFromParcel(parcel);
            return appStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppStatus[] newArray(int i) {
            return new AppStatus[i];
        }
    };
    public String aidPtr;
    public String appLabelPtr;
    public int appType = 0;
    public int appState = 0;
    public int persoSubstate = 0;
    public boolean pin1Replaced = false;
    public int pin1 = 0;
    public int pin2 = 0;

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
        parcel.writeInt(this.appType);
        parcel.writeInt(this.appState);
        parcel.writeInt(this.persoSubstate);
        parcel.writeString(this.aidPtr);
        parcel.writeString(this.appLabelPtr);
        parcel.writeBoolean(this.pin1Replaced);
        parcel.writeInt(this.pin1);
        parcel.writeInt(this.pin2);
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
                this.appType = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.appState = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.persoSubstate = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.aidPtr = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.appLabelPtr = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.pin1Replaced = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.pin1 = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.pin2 = parcel.readInt();
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
        stringJoiner.add("appType: " + this.appType);
        stringJoiner.add("appState: " + this.appState);
        stringJoiner.add("persoSubstate: " + PersoSubstate$$.toString(this.persoSubstate));
        stringJoiner.add("aidPtr: " + Objects.toString(this.aidPtr));
        stringJoiner.add("appLabelPtr: " + Objects.toString(this.appLabelPtr));
        stringJoiner.add("pin1Replaced: " + this.pin1Replaced);
        stringJoiner.add("pin1: " + PinState$$.toString(this.pin1));
        stringJoiner.add("pin2: " + PinState$$.toString(this.pin2));
        return "AppStatus" + stringJoiner.toString();
    }
}
