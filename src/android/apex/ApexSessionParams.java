package android.apex;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ApexSessionParams implements Parcelable {
    public static final Parcelable.Creator<ApexSessionParams> CREATOR = new Parcelable.Creator<ApexSessionParams>() { // from class: android.apex.ApexSessionParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexSessionParams createFromParcel(Parcel parcel) {
            ApexSessionParams apexSessionParams = new ApexSessionParams();
            apexSessionParams.readFromParcel(parcel);
            return apexSessionParams;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApexSessionParams[] newArray(int i) {
            return new ApexSessionParams[i];
        }
    };
    public int sessionId = 0;
    public int[] childSessionIds = new int[0];
    public boolean hasRollbackEnabled = false;
    public boolean isRollback = false;
    public int rollbackId = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionId);
        parcel.writeIntArray(this.childSessionIds);
        parcel.writeInt(this.hasRollbackEnabled ? 1 : 0);
        parcel.writeInt(this.isRollback ? 1 : 0);
        parcel.writeInt(this.rollbackId);
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
                this.sessionId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.childSessionIds = parcel.createIntArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        boolean z = true;
                        this.hasRollbackEnabled = parcel.readInt() != 0;
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            if (parcel.readInt() == 0) {
                                z = false;
                            }
                            this.isRollback = z;
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.rollbackId = parcel.readInt();
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
