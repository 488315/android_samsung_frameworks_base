package com.android.internal.os;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes5.dex */
public class AppFuseMount implements Parcelable {
    public static final Parcelable.Creator<AppFuseMount> CREATOR = new Parcelable.Creator<AppFuseMount>() { // from class: com.android.internal.os.AppFuseMount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFuseMount createFromParcel(Parcel parcel) {
            return new AppFuseMount(parcel.readInt(), (ParcelFileDescriptor) parcel.readParcelable(null, ParcelFileDescriptor.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppFuseMount[] newArray(int i) {
            return new AppFuseMount[i];
        }
    };
    public final ParcelFileDescriptor fd;
    public final int mountPointId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppFuseMount(int i, ParcelFileDescriptor parcelFileDescriptor) {
        Preconditions.checkNotNull(parcelFileDescriptor);
        this.mountPointId = i;
        this.fd = parcelFileDescriptor;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mountPointId);
        parcel.writeParcelable(this.fd, i);
    }
}
