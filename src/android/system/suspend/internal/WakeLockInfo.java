package android.system.suspend.internal;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class WakeLockInfo implements Parcelable {
    public static final Parcelable.Creator<WakeLockInfo> CREATOR = new Parcelable.Creator<WakeLockInfo>() { // from class: android.system.suspend.internal.WakeLockInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WakeLockInfo createFromParcel(Parcel parcel) {
            WakeLockInfo wakeLockInfo = new WakeLockInfo();
            wakeLockInfo.readFromParcel(parcel);
            return wakeLockInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WakeLockInfo[] newArray(int i) {
            return new WakeLockInfo[i];
        }
    };
    public String name;
    public long activeCount = 0;
    public long lastChange = 0;
    public long maxTime = 0;
    public long totalTime = 0;
    public boolean isActive = false;
    public long activeTime = 0;
    public boolean isKernelWakelock = false;
    public int pid = 0;
    public long eventCount = 0;
    public long expireCount = 0;
    public long preventSuspendTime = 0;
    public long wakeupCount = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.name);
        parcel.writeLong(this.activeCount);
        parcel.writeLong(this.lastChange);
        parcel.writeLong(this.maxTime);
        parcel.writeLong(this.totalTime);
        parcel.writeInt(this.isActive ? 1 : 0);
        parcel.writeLong(this.activeTime);
        parcel.writeInt(this.isKernelWakelock ? 1 : 0);
        parcel.writeInt(this.pid);
        parcel.writeLong(this.eventCount);
        parcel.writeLong(this.expireCount);
        parcel.writeLong(this.preventSuspendTime);
        parcel.writeLong(this.wakeupCount);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.activeCount = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.lastChange = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.maxTime = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.totalTime = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    boolean z = true;
                                    this.isActive = parcel.readInt() != 0;
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.activeTime = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            if (parcel.readInt() == 0) {
                                                z = false;
                                            }
                                            this.isKernelWakelock = z;
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.pid = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.eventCount = parcel.readLong();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.expireCount = parcel.readLong();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.preventSuspendTime = parcel.readLong();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.wakeupCount = parcel.readLong();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
