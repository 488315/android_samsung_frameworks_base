package vendor.samsung.hardware.radio.sim;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SehCardStatus implements Parcelable {
    public static final Parcelable.Creator<SehCardStatus> CREATOR = new Parcelable.Creator<SehCardStatus>() { // from class: vendor.samsung.hardware.radio.sim.SehCardStatus.1
        @Override // android.os.Parcelable.Creator
        public SehCardStatus createFromParcel(Parcel parcel) {
            SehCardStatus sehCardStatus = new SehCardStatus();
            sehCardStatus.readFromParcel(parcel);
            return sehCardStatus;
        }

        @Override // android.os.Parcelable.Creator
        public SehCardStatus[] newArray(int i) {
            return new SehCardStatus[i];
        }
    };
    public SehAppStatus[] applications;
    public String atr;
    public String eid;
    public String iccid;
    public SehSlotPortMapping slotMap;
    public int universalPinState;
    public int cardState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
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
        parcel.writeInt(this.universalPinState);
        parcel.writeInt(this.gsmUmtsSubscriptionAppIndex);
        parcel.writeInt(this.cdmaSubscriptionAppIndex);
        parcel.writeInt(this.imsSubscriptionAppIndex);
        parcel.writeTypedArray(this.applications, i);
        parcel.writeString(this.atr);
        parcel.writeString(this.iccid);
        parcel.writeString(this.eid);
        parcel.writeTypedObject(this.slotMap, i);
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
                    this.universalPinState = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.gsmUmtsSubscriptionAppIndex = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.cdmaSubscriptionAppIndex = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.imsSubscriptionAppIndex = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.applications = (SehAppStatus[]) parcel.createTypedArray(SehAppStatus.CREATOR);
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.atr = parcel.readString();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.iccid = parcel.readString();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.eid = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.slotMap = (SehSlotPortMapping) parcel.readTypedObject(SehSlotPortMapping.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.slotMap) | describeContents(this.applications);
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
