package android.hardware.health;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

public final class DiskStats implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public long reads = 0;
    public long readMerges = 0;
    public long readSectors = 0;
    public long readTicks = 0;
    public long writes = 0;
    public long writeMerges = 0;
    public long writeSectors = 0;
    public long writeTicks = 0;
    public long ioInFlight = 0;
    public long ioTicks = 0;
    public long ioInQueue = 0;

    /* renamed from: android.hardware.health.DiskStats$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DiskStats diskStats = new DiskStats();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    diskStats.reads = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        diskStats.readMerges = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            diskStats.readSectors = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                diskStats.readTicks = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    diskStats.writes = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        diskStats.writeMerges = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            diskStats.writeSectors = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                diskStats.writeTicks = parcel.readLong();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    diskStats.ioInFlight = parcel.readLong();
                                                    if (parcel.dataPosition() - dataPosition
                                                            < readInt) {
                                                        diskStats.ioTicks = parcel.readLong();
                                                        if (parcel.dataPosition() - dataPosition
                                                                < readInt) {
                                                            diskStats.ioInQueue = parcel.readLong();
                                                            if (dataPosition
                                                                    > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException(
                                                                        "Overflow in the size of"
                                                                            + " parcelable");
                                                            }
                                                        } else if (dataPosition
                                                                > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException(
                                                                    "Overflow in the size of"
                                                                        + " parcelable");
                                                        }
                                                    } else if (dataPosition
                                                            > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException(
                                                                "Overflow in the size of"
                                                                    + " parcelable");
                                                    }
                                                } else if (dataPosition
                                                        > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException(
                                                            "Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException(
                                                        "Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
                return diskStats;
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
            return new DiskStats[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.reads);
        parcel.writeLong(this.readMerges);
        parcel.writeLong(this.readSectors);
        parcel.writeLong(this.readTicks);
        parcel.writeLong(this.writes);
        parcel.writeLong(this.writeMerges);
        parcel.writeLong(this.writeSectors);
        parcel.writeLong(this.writeTicks);
        parcel.writeLong(this.ioInFlight);
        parcel.writeLong(this.ioTicks);
        parcel.writeLong(this.ioInQueue);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
