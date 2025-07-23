package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IOnProjectionStateChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IOnProjectionStateChangedListener";

    public static class Default implements IOnProjectionStateChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IOnProjectionStateChangedListener
        public void onProjectionStateChanged(int i, List<String> list) throws RemoteException {
        }
    }

    void onProjectionStateChanged(int i, List<String> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IOnProjectionStateChangedListener {
        static final int TRANSACTION_onProjectionStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IOnProjectionStateChangedListener.DESCRIPTOR);
        }

        public static IOnProjectionStateChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOnProjectionStateChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOnProjectionStateChangedListener)) {
                return (IOnProjectionStateChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onProjectionStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOnProjectionStateChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOnProjectionStateChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onProjectionStateChanged(readInt, createStringArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOnProjectionStateChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnProjectionStateChangedListener.DESCRIPTOR;
            }

            @Override // android.app.IOnProjectionStateChangedListener
            public void onProjectionStateChanged(int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOnProjectionStateChangedListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
