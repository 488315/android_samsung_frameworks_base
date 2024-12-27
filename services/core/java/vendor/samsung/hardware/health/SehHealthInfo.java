package vendor.samsung.hardware.health;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.health.HealthInfo;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SehHealthInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: vendor.samsung.hardware.health.SehHealthInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            SehHealthInfo sehHealthInfo = new SehHealthInfo();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    sehHealthInfo.aospHealthInfo = (HealthInfo) parcel.readTypedObject(HealthInfo.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        sehHealthInfo.batteryCurrentNow = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            sehHealthInfo.batteryOnline = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                sehHealthInfo.batteryChargeType = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    sehHealthInfo.batteryPowerSharingOnline = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        sehHealthInfo.chargerPogoOnline = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            sehHealthInfo.batteryHighVoltageCharger = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                sehHealthInfo.batteryEvent = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    sehHealthInfo.batteryCurrentEvent = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        sehHealthInfo.chargerOtgOnline = parcel.readBoolean();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            sehHealthInfo.wirelessPowerSharingTxEvent = parcel.readInt();
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
                return sehHealthInfo;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SehHealthInfo[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        HealthInfo healthInfo = this.aospHealthInfo;
        if (healthInfo == null) {
            return 0;
        }
        return healthInfo.describeContents();
    }

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
        int m = SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(parcel, this.wirelessPowerSharingTxEvent, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
