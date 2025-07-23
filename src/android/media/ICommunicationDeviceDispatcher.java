package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICommunicationDeviceDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.ICommunicationDeviceDispatcher";

    public static class Default implements ICommunicationDeviceDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ICommunicationDeviceDispatcher
        public void dispatchCommunicationDeviceChanged(int i) throws RemoteException {
        }
    }

    void dispatchCommunicationDeviceChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICommunicationDeviceDispatcher {
        static final int TRANSACTION_dispatchCommunicationDeviceChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICommunicationDeviceDispatcher.DESCRIPTOR);
        }

        public static ICommunicationDeviceDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICommunicationDeviceDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICommunicationDeviceDispatcher)) {
                return (ICommunicationDeviceDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchCommunicationDeviceChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICommunicationDeviceDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICommunicationDeviceDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispatchCommunicationDeviceChanged(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICommunicationDeviceDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICommunicationDeviceDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ICommunicationDeviceDispatcher
            public void dispatchCommunicationDeviceChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICommunicationDeviceDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
