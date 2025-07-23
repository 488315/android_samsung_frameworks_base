package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IContextHubTransactionCallback extends IInterface {

    public static class Default implements IContextHubTransactionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IContextHubTransactionCallback
        public void onQueryResponse(int i, List<NanoAppState> list) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubTransactionCallback
        public void onTransactionComplete(int i) throws RemoteException {
        }
    }

    void onQueryResponse(int i, List<NanoAppState> list) throws RemoteException;

    void onTransactionComplete(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubTransactionCallback {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubTransactionCallback";
        static final int TRANSACTION_onQueryResponse = 1;
        static final int TRANSACTION_onTransactionComplete = 2;

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

        public static IContextHubTransactionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubTransactionCallback)) {
                return (IContextHubTransactionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onQueryResponse";
            }
            if (i != 2) {
                return null;
            }
            return "onTransactionComplete";
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
                int readInt = parcel.readInt();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(NanoAppState.CREATOR);
                parcel.enforceNoDataAvail();
                onQueryResponse(readInt, createTypedArrayList);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTransactionComplete(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContextHubTransactionCallback {
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

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onQueryResponse(int i, List<NanoAppState> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubTransactionCallback
            public void onTransactionComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
