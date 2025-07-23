package android.system.suspend.internal;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class SuspendInfo implements Parcelable {
    public static final Parcelable.Creator<SuspendInfo> CREATOR = new Parcelable.Creator<SuspendInfo>() { // from class: android.system.suspend.internal.SuspendInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuspendInfo createFromParcel(Parcel parcel) {
            SuspendInfo suspendInfo = new SuspendInfo();
            suspendInfo.readFromParcel(parcel);
            return suspendInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SuspendInfo[] newArray(int i) {
            return new SuspendInfo[i];
        }
    };
    public long suspendAttemptCount = 0;
    public long failedSuspendCount = 0;
    public long shortSuspendCount = 0;
    public long suspendTimeMillis = 0;
    public long shortSuspendTimeMillis = 0;
    public long suspendOverheadTimeMillis = 0;
    public long failedSuspendOverheadTimeMillis = 0;
    public long newBackoffCount = 0;
    public long backoffContinueCount = 0;
    public long sleepTimeMillis = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.suspendAttemptCount);
        parcel.writeLong(this.failedSuspendCount);
        parcel.writeLong(this.shortSuspendCount);
        parcel.writeLong(this.suspendTimeMillis);
        parcel.writeLong(this.shortSuspendTimeMillis);
        parcel.writeLong(this.suspendOverheadTimeMillis);
        parcel.writeLong(this.failedSuspendOverheadTimeMillis);
        parcel.writeLong(this.newBackoffCount);
        parcel.writeLong(this.backoffContinueCount);
        parcel.writeLong(this.sleepTimeMillis);
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
                this.suspendAttemptCount = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.failedSuspendCount = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.shortSuspendCount = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.suspendTimeMillis = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.shortSuspendTimeMillis = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.suspendOverheadTimeMillis = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.failedSuspendOverheadTimeMillis = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.newBackoffCount = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.backoffContinueCount = parcel.readLong();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.sleepTimeMillis = parcel.readLong();
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
}
