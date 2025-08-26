package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class CreateAppDataResult implements Parcelable {
    public static final Parcelable.Creator<CreateAppDataResult> CREATOR = new Parcelable.Creator<CreateAppDataResult>() { // from class: android.os.CreateAppDataResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateAppDataResult createFromParcel(Parcel parcel) {
            CreateAppDataResult createAppDataResult = new CreateAppDataResult();
            createAppDataResult.readFromParcel(parcel);
            return createAppDataResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateAppDataResult[] newArray(int i) {
            return new CreateAppDataResult[i];
        }
    };
    public long ceDataInode = 0;
    public long deDataInode = 0;
    public int exceptionCode = 0;
    public String exceptionMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.ceDataInode);
        parcel.writeLong(this.deDataInode);
        parcel.writeInt(this.exceptionCode);
        parcel.writeString(this.exceptionMessage);
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
                this.ceDataInode = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.deDataInode = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.exceptionCode = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.exceptionMessage = parcel.readString();
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
