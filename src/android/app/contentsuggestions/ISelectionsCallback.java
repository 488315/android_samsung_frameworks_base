package android.app.contentsuggestions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface ISelectionsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.contentsuggestions.ISelectionsCallback";

    public static class Default implements ISelectionsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.contentsuggestions.ISelectionsCallback
        public void onContentSelectionsAvailable(int i, List<ContentSelection> list) throws RemoteException {
        }
    }

    void onContentSelectionsAvailable(int i, List<ContentSelection> list) throws RemoteException;

    public static abstract class Stub extends Binder implements ISelectionsCallback {
        static final int TRANSACTION_onContentSelectionsAvailable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISelectionsCallback.DESCRIPTOR);
        }

        public static ISelectionsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISelectionsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISelectionsCallback)) {
                return (ISelectionsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onContentSelectionsAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISelectionsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISelectionsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ContentSelection.CREATOR);
                parcel.enforceNoDataAvail();
                onContentSelectionsAvailable(readInt, createTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISelectionsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISelectionsCallback.DESCRIPTOR;
            }

            @Override // android.app.contentsuggestions.ISelectionsCallback
            public void onContentSelectionsAvailable(int i, List<ContentSelection> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISelectionsCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
