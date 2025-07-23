package com.android.framework.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.framework.protobuf.nano.ExtendableMessageNano;

/* loaded from: classes5.dex */
public abstract class ParcelableExtendableMessageNano<M extends ExtendableMessageNano<M>> extends ExtendableMessageNano<M> implements Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, parcel);
    }
}
