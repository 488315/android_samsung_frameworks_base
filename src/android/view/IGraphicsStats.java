package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.IGraphicsStatsCallback;

/* loaded from: classes4.dex */
public interface IGraphicsStats extends IInterface {

    public static class Default implements IGraphicsStats {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IGraphicsStats
        public ParcelFileDescriptor requestBufferForProcess(String str, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException {
            return null;
        }

        @Override // android.view.IGraphicsStats
        public int requestRenderEngineFor(String str) throws RemoteException {
            return 0;
        }
    }

    ParcelFileDescriptor requestBufferForProcess(String str, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException;

    int requestRenderEngineFor(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGraphicsStats {
        public static final String DESCRIPTOR = "android.view.IGraphicsStats";
        static final int TRANSACTION_requestBufferForProcess = 1;
        static final int TRANSACTION_requestRenderEngineFor = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGraphicsStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGraphicsStats)) {
                return (IGraphicsStats) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "requestBufferForProcess";
            }
            if (i != 2) {
                return null;
            }
            return "requestRenderEngineFor";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                IGraphicsStatsCallback asInterface = IGraphicsStatsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ParcelFileDescriptor requestBufferForProcess = requestBufferForProcess(readString, asInterface);
                parcel2.writeNoException();
                parcel2.writeTypedObject(requestBufferForProcess, 1);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int requestRenderEngineFor = requestRenderEngineFor(readString2);
                parcel2.writeNoException();
                parcel2.writeInt(requestRenderEngineFor);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGraphicsStats {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.IGraphicsStats
            public ParcelFileDescriptor requestBufferForProcess(String str, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iGraphicsStatsCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelFileDescriptor) obtain2.readTypedObject(ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IGraphicsStats
            public int requestRenderEngineFor(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
