package com.samsung.android.mcfds.lib.common;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public interface IContinuitySimpleListener extends IInterface {

    public class Default implements IContinuitySimpleListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void onNotify(Bundle bundle);

    public abstract class Stub extends Binder implements IContinuitySimpleListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.mcfds.lib.common.IContinuitySimpleListener");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.mcfds.lib.common.IContinuitySimpleListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.mcfds.lib.common.IContinuitySimpleListener");
                return true;
            }
            if (i == 1) {
                onNotify((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements IContinuitySimpleListener {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }
    }

    public abstract class _Parcel {
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }
}
