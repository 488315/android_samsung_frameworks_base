package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.logging.InstanceId;

/* loaded from: classes4.dex */
public interface ISessionListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.statusbar.ISessionListener";

    public static class Default implements ISessionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.statusbar.ISessionListener
        public void onSessionEnded(int i, InstanceId instanceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.ISessionListener
        public void onSessionStarted(int i, InstanceId instanceId) throws RemoteException {
        }
    }

    void onSessionEnded(int i, InstanceId instanceId) throws RemoteException;

    void onSessionStarted(int i, InstanceId instanceId) throws RemoteException;

    public static abstract class Stub extends Binder implements ISessionListener {
        static final int TRANSACTION_onSessionEnded = 2;
        static final int TRANSACTION_onSessionStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISessionListener.DESCRIPTOR);
        }

        public static ISessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISessionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISessionListener)) {
                return (ISessionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSessionStarted";
            }
            if (i != 2) {
                return null;
            }
            return "onSessionEnded";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISessionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISessionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                onSessionStarted(readInt, instanceId);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                parcel.enforceNoDataAvail();
                onSessionEnded(readInt2, instanceId2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISessionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISessionListener.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.ISessionListener
            public void onSessionStarted(int i, InstanceId instanceId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.ISessionListener
            public void onSessionEnded(int i, InstanceId instanceId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
