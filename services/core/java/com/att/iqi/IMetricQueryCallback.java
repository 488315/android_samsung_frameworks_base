package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.att.iqi.lib.Metric;

public interface IMetricQueryCallback extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IMetricQueryCallback";

    public class Default implements IMetricQueryCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.att.iqi.IMetricQueryCallback
        public void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IMetricQueryCallback {
        static final int TRANSACTION_onMetricQueried = 1;

        class Proxy implements IMetricQueryCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMetricQueryCallback.DESCRIPTOR;
            }

            @Override // com.att.iqi.IMetricQueryCallback
            public void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMetricQueryCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMetricQueryCallback.DESCRIPTOR);
        }

        public static IMetricQueryCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IMetricQueryCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IMetricQueryCallback))
                    ? new Proxy(iBinder)
                    : (IMetricQueryCallback) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMetricQueryCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMetricQueryCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onMetricQueried(
                    (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                    parcel.createByteArray());
            return true;
        }
    }

    public class _Parcel {
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException;
}
