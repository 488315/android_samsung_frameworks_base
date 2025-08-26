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
        int iDataPosition = parcel.dataPosition();
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
                this.suspendAttemptCount = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.failedSuspendCount = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.shortSuspendCount = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.suspendTimeMillis = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.shortSuspendTimeMillis = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.suspendOverheadTimeMillis = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.failedSuspendOverheadTimeMillis = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.newBackoffCount = parcel.readLong();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.backoffContinueCount = parcel.readLong();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.sleepTimeMillis = parcel.readLong();
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
