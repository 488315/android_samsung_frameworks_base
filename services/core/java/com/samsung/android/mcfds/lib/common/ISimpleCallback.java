package com.samsung.android.mcfds.lib.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public interface ISimpleCallback extends IInterface {

    public class Default implements ISimpleCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void onCallback(Message message);

    public abstract class Stub extends Binder implements ISimpleCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.mcfds.lib.common.ISimpleCallback");
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.mcfds.lib.common.ISimpleCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.mcfds.lib.common.ISimpleCallback");
                return true;
            }
            if (i == 1) {
                onCallback((Message) _Parcel.readTypedObject(parcel, Message.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        public class Proxy implements ISimpleCallback {
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
