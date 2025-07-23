package com.samsung.android.media.codec;

import android.graphics.rendererpolicy.ScpmApiContract;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.media.codec.IVideoTranscodingServiceCallback;

/* loaded from: classes6.dex */
public interface IVideoTranscodingService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.codec.IVideoTranscodingService";

    public static class Default implements IVideoTranscodingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public void startTask(String str) throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingService
        public void stopTask(String str) throws RemoteException {
        }
    }

    String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) throws RemoteException;

    void startTask(String str) throws RemoteException;

    void stopTask(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoTranscodingService {
        static final int TRANSACTION_register = 1;
        static final int TRANSACTION_startTask = 2;
        static final int TRANSACTION_stopTask = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IVideoTranscodingService.DESCRIPTOR);
        }

        public static IVideoTranscodingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVideoTranscodingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVideoTranscodingService)) {
                return (IVideoTranscodingService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return ScpmApiContract.Method.REGISTER;
            }
            if (i == 2) {
                return "startTask";
            }
            if (i != 3) {
                return null;
            }
            return "stopTask";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVideoTranscodingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVideoTranscodingService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                IVideoTranscodingServiceCallback asInterface = IVideoTranscodingServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                String register = register(readInt, asInterface);
                parcel2.writeNoException();
                parcel2.writeString(register);
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                startTask(readString);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                stopTask(readString2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVideoTranscodingService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVideoTranscodingService.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public String register(int i, IVideoTranscodingServiceCallback iVideoTranscodingServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVideoTranscodingServiceCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public void startTask(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingService
            public void stopTask(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
