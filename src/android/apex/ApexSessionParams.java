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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionId);
        parcel.writeIntArray(this.childSessionIds);
        parcel.writeInt(this.hasRollbackEnabled ? 1 : 0);
        parcel.writeInt(this.isRollback ? 1 : 0);
        parcel.writeInt(this.rollbackId);
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
                this.sessionId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.childSessionIds = parcel.createIntArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        boolean z = true;
                        this.hasRollbackEnabled = parcel.readInt() != 0;
                        if (parcel.dataPosition() - iDataPosition < i) {
                            if (parcel.readInt() == 0) {
                                z = false;
                            }
                            this.isRollback = z;
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.rollbackId = parcel.readInt();
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
