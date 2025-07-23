package com.sec.android.iaft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.android.iaft.callback.IIAFTCallback;

/* loaded from: classes6.dex */
public interface IIAFTManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.iaft.IIAFTManagerService";

    public static class Default implements IIAFTManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void registerCallback(IIAFTCallback iIAFTCallback) throws RemoteException {
        }

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void startAtrace() throws RemoteException {
        }

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void startAtraceAndAnalyze(int i, String str, int i2) throws RemoteException {
        }

        @Override // com.sec.android.iaft.IIAFTManagerService
        public void stopTrace() throws RemoteException {
        }

        @Override // com.sec.android.iaft.IIAFTManagerService
        public boolean traceLogSupported() throws RemoteException {
            return false;
        }
    }

    void registerCallback(IIAFTCallback iIAFTCallback) throws RemoteException;

    void startAtrace() throws RemoteException;

    void startAtraceAndAnalyze(int i, String str, int i2) throws RemoteException;

    void stopTrace() throws RemoteException;

    boolean traceLogSupported() throws RemoteException;

    public static abstract class Stub extends Binder implements IIAFTManagerService {
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_startAtrace = 2;
        static final int TRANSACTION_startAtraceAndAnalyze = 1;
        static final int TRANSACTION_stopTrace = 3;
        static final int TRANSACTION_traceLogSupported = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IIAFTManagerService.DESCRIPTOR);
        }

        public static IIAFTManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIAFTManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIAFTManagerService)) {
                return (IIAFTManagerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startAtraceAndAnalyze";
            }
            if (i == 2) {
                return "startAtrace";
            }
            if (i == 3) {
                return "stopTrace";
            }
            if (i == 4) {
                return "registerCallback";
            }
            if (i != 5) {
                return null;
            }
            return "traceLogSupported";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIAFTManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIAFTManagerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                startAtraceAndAnalyze(readInt, readString, readInt2);
                parcel2.writeNoException();
            } else if (i == 2) {
                startAtrace();
                parcel2.writeNoException();
            } else if (i == 3) {
                stopTrace();
                parcel2.writeNoException();
            } else if (i == 4) {
                IIAFTCallback asInterface = IIAFTCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerCallback(asInterface);
                parcel2.writeNoException();
            } else if (i == 5) {
                boolean traceLogSupported = traceLogSupported();
                parcel2.writeNoException();
                parcel2.writeBoolean(traceLogSupported);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIAFTManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIAFTManagerService.DESCRIPTOR;
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void startAtraceAndAnalyze(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void startAtrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void stopTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public void registerCallback(IIAFTCallback iIAFTCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iIAFTCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFTManagerService
            public boolean traceLogSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFTManagerService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
