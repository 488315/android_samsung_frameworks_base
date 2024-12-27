package android.hardware.health;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int batteryCapacityLevel;
    public int batteryHealth;
    public BatteryHealthData batteryHealthData;
    public int batteryStatus;
    public String batteryTechnology;
    public int chargingPolicy;
    public int chargingState;
    public DiskStats[] diskStats;
    public StorageInfo[] storageInfos;
    public boolean chargerAcOnline = false;
    public boolean chargerUsbOnline = false;
    public boolean chargerWirelessOnline = false;
    public boolean chargerDockOnline = false;
    public int maxChargingCurrentMicroamps = 0;
    public int maxChargingVoltageMicrovolts = 0;
    public boolean batteryPresent = false;
    public int batteryLevel = 0;
    public int batteryVoltageMillivolts = 0;
    public int batteryTemperatureTenthsCelsius = 0;
    public int batteryCurrentMicroamps = 0;
    public int batteryCycleCount = 0;
    public int batteryFullChargeUah = 0;
    public int batteryChargeCounterUah = 0;
    public int batteryCurrentAverageMicroamps = 0;
    public long batteryChargeTimeToFullNowSeconds = 0;
    public int batteryFullChargeDesignCapacityUah = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.health.HealthInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            HealthInfo healthInfo = new HealthInfo();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    healthInfo.chargerAcOnline = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        healthInfo.chargerUsbOnline = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            healthInfo.chargerWirelessOnline = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                healthInfo.chargerDockOnline = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    healthInfo.maxChargingCurrentMicroamps = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        healthInfo.maxChargingVoltageMicrovolts = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            healthInfo.batteryStatus = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                healthInfo.batteryHealth = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    healthInfo.batteryPresent = parcel.readBoolean();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        healthInfo.batteryLevel = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            healthInfo.batteryVoltageMillivolts = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                healthInfo.batteryTemperatureTenthsCelsius = parcel.readInt();
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    healthInfo.batteryCurrentMicroamps = parcel.readInt();
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        healthInfo.batteryCycleCount = parcel.readInt();
                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                            healthInfo.batteryFullChargeUah = parcel.readInt();
                                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                healthInfo.batteryChargeCounterUah = parcel.readInt();
                                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                    healthInfo.batteryTechnology = parcel.readString();
                                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                        healthInfo.batteryCurrentAverageMicroamps = parcel.readInt();
                                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                            healthInfo.diskStats = (DiskStats[]) parcel.createTypedArray(DiskStats.CREATOR);
                                                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                healthInfo.storageInfos = (StorageInfo[]) parcel.createTypedArray(StorageInfo.CREATOR);
                                                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                    healthInfo.batteryCapacityLevel = parcel.readInt();
                                                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                        healthInfo.batteryChargeTimeToFullNowSeconds = parcel.readLong();
                                                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                            healthInfo.batteryFullChargeDesignCapacityUah = parcel.readInt();
                                                                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                                healthInfo.chargingState = parcel.readInt();
                                                                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                                    healthInfo.chargingPolicy = parcel.readInt();
                                                                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                                                        healthInfo.batteryHealthData = (BatteryHealthData) parcel.readTypedObject(BatteryHealthData.CREATOR);
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
                return healthInfo;
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
            return new HealthInfo[i];
        }
    }

    public static int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return describeContents(this.batteryHealthData) | describeContents(this.diskStats) | describeContents(this.storageInfos);
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.chargerAcOnline);
        parcel.writeBoolean(this.chargerUsbOnline);
        parcel.writeBoolean(this.chargerWirelessOnline);
        parcel.writeBoolean(this.chargerDockOnline);
        parcel.writeInt(this.maxChargingCurrentMicroamps);
        parcel.writeInt(this.maxChargingVoltageMicrovolts);
        parcel.writeInt(this.batteryStatus);
        parcel.writeInt(this.batteryHealth);
        parcel.writeBoolean(this.batteryPresent);
        parcel.writeInt(this.batteryLevel);
        parcel.writeInt(this.batteryVoltageMillivolts);
        parcel.writeInt(this.batteryTemperatureTenthsCelsius);
        parcel.writeInt(this.batteryCurrentMicroamps);
        parcel.writeInt(this.batteryCycleCount);
        parcel.writeInt(this.batteryFullChargeUah);
        parcel.writeInt(this.batteryChargeCounterUah);
        parcel.writeString(this.batteryTechnology);
        parcel.writeInt(this.batteryCurrentAverageMicroamps);
        parcel.writeTypedArray(this.diskStats, i);
        parcel.writeTypedArray(this.storageInfos, i);
        parcel.writeInt(this.batteryCapacityLevel);
        parcel.writeLong(this.batteryChargeTimeToFullNowSeconds);
        parcel.writeInt(this.batteryFullChargeDesignCapacityUah);
        parcel.writeInt(this.chargingState);
        parcel.writeInt(this.chargingPolicy);
        parcel.writeTypedObject(this.batteryHealthData, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
