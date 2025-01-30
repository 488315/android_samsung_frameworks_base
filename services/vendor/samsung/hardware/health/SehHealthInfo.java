package vendor.samsung.hardware.health;

import android.hardware.health.HealthInfo;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SehHealthInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: vendor.samsung.hardware.health.SehHealthInfo.1
        @Override // android.os.Parcelable.Creator
        public SehHealthInfo createFromParcel(Parcel parcel) {
            SehHealthInfo sehHealthInfo = new SehHealthInfo();
            sehHealthInfo.readFromParcel(parcel);
            return sehHealthInfo;
        }

        @Override // android.os.Parcelable.Creator
        public SehHealthInfo[] newArray(int i) {
            return new SehHealthInfo[i];
        }
    };
    public HealthInfo aospHealthInfo;
    public int batteryCurrentNow = 0;
    public int batteryOnline = 0;
    public int batteryChargeType = 0;
    public boolean batteryPowerSharingOnline = false;
    public boolean chargerPogoOnline = false;
    public int batteryHighVoltageCharger = 0;
    public int batteryEvent = 0;
    public int batteryCurrentEvent = 0;
    public boolean chargerOtgOnline = false;
    public int wirelessPowerSharingTxEvent = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.aospHealthInfo, i);
        parcel.writeInt(this.batteryCurrentNow);
        parcel.writeInt(this.batteryOnline);
        parcel.writeInt(this.batteryChargeType);
        parcel.writeBoolean(this.batteryPowerSharingOnline);
        parcel.writeBoolean(this.chargerPogoOnline);
        parcel.writeInt(this.batteryHighVoltageCharger);
        parcel.writeInt(this.batteryEvent);
        parcel.writeInt(this.batteryCurrentEvent);
        parcel.writeBoolean(this.chargerOtgOnline);
        parcel.writeInt(this.wirelessPowerSharingTxEvent);
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
                this.aospHealthInfo = (HealthInfo) parcel.readTypedObject(HealthInfo.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.batteryCurrentNow = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.batteryOnline = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.batteryChargeType = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.batteryPowerSharingOnline = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.chargerPogoOnline = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.batteryHighVoltageCharger = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.batteryEvent = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.batteryCurrentEvent = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.chargerOtgOnline = parcel.readBoolean();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.wirelessPowerSharingTxEvent = parcel.readInt();
                                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                                        }
                                                        parcel.setDataPosition(dataPosition + readInt);
                                                        return;
                                                    }
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.aospHealthInfo) | 0;
    }

    public final int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
