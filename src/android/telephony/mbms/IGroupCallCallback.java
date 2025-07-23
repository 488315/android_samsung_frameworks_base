package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IGroupCallCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.mbms.IGroupCallCallback";

    public static class Default implements IGroupCallCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.IGroupCallCallback
        public void onBroadcastSignalStrengthUpdated(int i) throws RemoteException {
        }

        @Override // android.telephony.mbms.IGroupCallCallback
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.mbms.IGroupCallCallback
        public void onGroupCallStateChanged(int i, int i2) throws RemoteException {
        }
    }

    void onBroadcastSignalStrengthUpdated(int i) throws RemoteException;

    void onError(int i, String str) throws RemoteException;

    void onGroupCallStateChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGroupCallCallback {
        static final int TRANSACTION_onBroadcastSignalStrengthUpdated = 3;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onGroupCallStateChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGroupCallCallback.DESCRIPTOR);
        }

        public static IGroupCallCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGroupCallCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGroupCallCallback)) {
                return (IGroupCallCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onError";
            }
            if (i == 2) {
                return "onGroupCallStateChanged";
            }
            if (i != 3) {
                return null;
            }
            return "onBroadcastSignalStrengthUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGroupCallCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGroupCallCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readInt, readString);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGroupCallStateChanged(readInt2, readInt3);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onBroadcastSignalStrengthUpdated(readInt4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGroupCallCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGroupCallCallback.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IGroupCallCallback
            public void onError(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGroupCallCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IGroupCallCallback
            public void onGroupCallStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGroupCallCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IGroupCallCallback
            public void onBroadcastSignalStrengthUpdated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGroupCallCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
