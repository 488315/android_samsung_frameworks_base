package android.media.tv.extension.tune;

import android.media.tv.extension.tune.IChannelTunedListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IChannelTunedInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.tune.IChannelTunedInterface";

    public static class Default implements IChannelTunedInterface {
        @Override // android.media.tv.extension.tune.IChannelTunedInterface
        public void addChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.tune.IChannelTunedInterface
        public void removeChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException {
        }
    }

    void addChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException;

    void removeChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IChannelTunedInterface {
        static final int TRANSACTION_addChannelTunedListener = 1;
        static final int TRANSACTION_removeChannelTunedListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.tune.IChannelTunedInterface");
        }

        public static IChannelTunedInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.tune.IChannelTunedInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IChannelTunedInterface)) {
                return (IChannelTunedInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addChannelTunedListener";
            }
            if (i != 2) {
                return null;
            }
            return "removeChannelTunedListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.tune.IChannelTunedInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.tune.IChannelTunedInterface");
                return true;
            }
            if (i == 1) {
                IChannelTunedListener asInterface = IChannelTunedListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addChannelTunedListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IChannelTunedListener asInterface2 = IChannelTunedListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeChannelTunedListener(asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IChannelTunedInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.tune.IChannelTunedInterface";
            }

            @Override // android.media.tv.extension.tune.IChannelTunedInterface
            public void addChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IChannelTunedInterface");
                    obtain.writeStrongInterface(iChannelTunedListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.tune.IChannelTunedInterface
            public void removeChannelTunedListener(IChannelTunedListener iChannelTunedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.tune.IChannelTunedInterface");
                    obtain.writeStrongInterface(iChannelTunedListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
