package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehAppStatus implements Parcelable {
    public static final Parcelable.Creator<SehAppStatus> CREATOR = new Parcelable.Creator<SehAppStatus>() { // from class: vendor.samsung.hardware.radio.sim.SehAppStatus.1
        @Override // android.os.Parcelable.Creator
        public SehAppStatus createFromParcel(Parcel parcel) {
            SehAppStatus sehAppStatus = new SehAppStatus();
            sehAppStatus.readFromParcel(parcel);
            return sehAppStatus;
        }

        @Override // android.os.Parcelable.Creator
        public SehAppStatus[] newArray(int i) {
            return new SehAppStatus[i];
        }
    };
    public String aidPtr;
    public String appLabelPtr;
    public int persoSubstate;
    public int pin1;
    public int pin2;
    public int appType = 0;
    public int appState = 0;
    public int pin1Replaced = 0;
    public int pin1NumRetries = 0;
    public int puk1NumRetries = 0;
    public int pin2NumRetries = 0;
    public int puk2NumRetries = 0;
    public int persoUnblockRetries = 0;

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
        parcel.writeInt(this.appType);
        parcel.writeInt(this.appState);
        parcel.writeInt(this.persoSubstate);
        parcel.writeString(this.aidPtr);
        parcel.writeString(this.appLabelPtr);
        parcel.writeInt(this.pin1Replaced);
        parcel.writeInt(this.pin1);
        parcel.writeInt(this.pin2);
        parcel.writeInt(this.pin1NumRetries);
        parcel.writeInt(this.puk1NumRetries);
        parcel.writeInt(this.pin2NumRetries);
        parcel.writeInt(this.puk2NumRetries);
        parcel.writeInt(this.persoUnblockRetries);
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
                this.appType = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.appState = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.persoSubstate = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.aidPtr = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.appLabelPtr = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.pin1Replaced = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.pin1 = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.pin2 = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.pin1NumRetries = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.puk1NumRetries = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.pin2NumRetries = parcel.readInt();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.puk2NumRetries = parcel.readInt();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.persoUnblockRetries = parcel.readInt();
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
}
