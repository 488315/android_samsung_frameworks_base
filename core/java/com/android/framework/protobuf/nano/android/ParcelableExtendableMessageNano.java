package com.android.framework.protobuf.nano.android;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import com.android.framework.protobuf.nano.ExtendableMessageNano;

/* loaded from: classes4.dex */
public abstract class ParcelableExtendableMessageNano<M extends ExtendableMessageNano<M>> extends ExtendableMessageNano<M> implements Parcelable {
    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ParcelableMessageNanoCreator.writeToParcel(getClass(), this, out);
    }
}
