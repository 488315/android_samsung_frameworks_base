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
        int iDataPosition = parcel.dataPosition();
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
                this.cardState = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.universalPinState = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.gsmUmtsSubscriptionAppIndex = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cdmaSubscriptionAppIndex = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.imsSubscriptionAppIndex = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.applications = (SehAppStatus[]) parcel.createTypedArray(SehAppStatus.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.atr = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.iccid = parcel.readString();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.eid = parcel.readString();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.slotMap = (SehSlotPortMapping) parcel.readTypedObject(SehSlotPortMapping.CREATOR);
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.supportedMepMode = parcel.readInt();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
