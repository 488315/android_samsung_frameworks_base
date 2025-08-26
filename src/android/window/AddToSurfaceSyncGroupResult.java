package android.window;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.ISurfaceSyncGroup;
import android.window.ITransactionReadyCallback;

/* loaded from: classes5.dex */
public class AddToSurfaceSyncGroupResult implements Parcelable {
    public static final Parcelable.Creator<AddToSurfaceSyncGroupResult> CREATOR = new Parcelable.Creator<AddToSurfaceSyncGroupResult>() { // from class: android.window.AddToSurfaceSyncGroupResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AddToSurfaceSyncGroupResult createFromParcel(Parcel parcel) {
            AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new AddToSurfaceSyncGroupResult();
            addToSurfaceSyncGroupResult.readFromParcel(parcel);
            return addToSurfaceSyncGroupResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AddToSurfaceSyncGroupResult[] newArray(int i) {
            return new AddToSurfaceSyncGroupResult[i];
        }
    };
    public ISurfaceSyncGroup mParentSyncGroup;
    public ITransactionReadyCallback mTransactionReadyCallback;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.mParentSyncGroup);
        parcel.writeStrongInterface(this.mTransactionReadyCallback);
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
                this.mParentSyncGroup = ISurfaceSyncGroup.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mTransactionReadyCallback = ITransactionReadyCallback.Stub.asInterface(parcel.readStrongBinder());
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
