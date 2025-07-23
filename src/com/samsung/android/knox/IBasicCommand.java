package com.samsung.android.knox;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IBasicCommand extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IBasicCommand";

    public static class Default implements IBasicCommand {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.IBasicCommand
        public Bundle sendCmd(Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.IBasicCommand
        public void setCaller(IBasicCommand iBasicCommand) throws RemoteException {
        }
    }

    Bundle sendCmd(Bundle bundle) throws RemoteException;

    void setCaller(IBasicCommand iBasicCommand) throws RemoteException;

    public static abstract class Stub extends Binder implements IBasicCommand {
        static final int TRANSACTION_sendCmd = 1;
        static final int TRANSACTION_setCaller = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IBasicCommand.DESCRIPTOR);
        }

        public static IBasicCommand asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBasicCommand.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBasicCommand)) {
                return (IBasicCommand) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "sendCmd";
            }
            if (i != 2) {
                return null;
            }
            return "setCaller";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBasicCommand.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBasicCommand.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle sendCmd = sendCmd(bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(sendCmd, 1);
            } else if (i == 2) {
                IBasicCommand asInterface = asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setCaller(asInterface);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBasicCommand {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBasicCommand.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IBasicCommand
            public Bundle sendCmd(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBasicCommand.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.IBasicCommand
            public void setCaller(IBasicCommand iBasicCommand) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBasicCommand.DESCRIPTOR);
                    obtain.writeStrongInterface(iBasicCommand);
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
