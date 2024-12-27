package android.internal.framework.protobuf.nano.android;

import android.internal.framework.protobuf.nano.MessageNano;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class ParcelableMessageNano extends MessageNano implements Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, out);
    }
}
