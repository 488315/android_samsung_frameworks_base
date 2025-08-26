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
        int iDataPosition = parcel.dataPosition();
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.activeCount = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.lastChange = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxTime = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.totalTime = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    boolean z = true;
                                    this.isActive = parcel.readInt() != 0;
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.activeTime = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            if (parcel.readInt() == 0) {
                                                z = false;
                                            }
                                            this.isKernelWakelock = z;
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.pid = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.eventCount = parcel.readLong();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.expireCount = parcel.readLong();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.preventSuspendTime = parcel.readLong();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.wakeupCount = parcel.readLong();
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
