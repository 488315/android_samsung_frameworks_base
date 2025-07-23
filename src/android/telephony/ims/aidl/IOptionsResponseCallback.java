package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IOptionsResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IOptionsResponseCallback";

    public static class Default implements IOptionsResponseCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IOptionsResponseCallback
        public void onCommandError(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IOptionsResponseCallback
        public void onNetworkResponse(int i, String str, List<String> list) throws RemoteException {
        }
    }

    void onCommandError(int i) throws RemoteException;

    void onNetworkResponse(int i, String str, List<String> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IOptionsResponseCallback {
        static final int TRANSACTION_onCommandError = 1;
        static final int TRANSACTION_onNetworkResponse = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IOptionsResponseCallback.DESCRIPTOR);
        }

        public static IOptionsResponseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOptionsResponseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOptionsResponseCallback)) {
                return (IOptionsResponseCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCommandError";
            }
            if (i != 2) {
                return null;
            }
            return "onNetworkResponse";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOptionsResponseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOptionsResponseCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCommandError(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                String readString = parcel.readString();
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onNetworkResponse(readInt2, readString, createStringArrayList);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOptionsResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOptionsResponseCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IOptionsResponseCallback
            public void onCommandError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOptionsResponseCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IOptionsResponseCallback
            public void onNetworkResponse(int i, String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOptionsResponseCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
