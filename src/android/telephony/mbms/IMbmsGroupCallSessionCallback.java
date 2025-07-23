package android.telephony.mbms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsGroupCallSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.mbms.IMbmsGroupCallSessionCallback";

    public static class Default implements IMbmsGroupCallSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onAvailableSaisUpdated(List list, List list2) throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onMiddlewareReady() throws RemoteException {
        }

        @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
        public void onServiceInterfaceAvailable(String str, int i) throws RemoteException {
        }
    }

    void onAvailableSaisUpdated(List list, List list2) throws RemoteException;

    void onError(int i, String str) throws RemoteException;

    void onMiddlewareReady() throws RemoteException;

    void onServiceInterfaceAvailable(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMbmsGroupCallSessionCallback {
        static final int TRANSACTION_onAvailableSaisUpdated = 2;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onMiddlewareReady = 4;
        static final int TRANSACTION_onServiceInterfaceAvailable = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IMbmsGroupCallSessionCallback.DESCRIPTOR);
        }

        public static IMbmsGroupCallSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMbmsGroupCallSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMbmsGroupCallSessionCallback)) {
                return (IMbmsGroupCallSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onError";
            }
            if (i == 2) {
                return "onAvailableSaisUpdated";
            }
            if (i == 3) {
                return "onServiceInterfaceAvailable";
            }
            if (i != 4) {
                return null;
            }
            return "onMiddlewareReady";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMbmsGroupCallSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readInt, readString);
            } else if (i == 2) {
                ClassLoader classLoader = getClass().getClassLoader();
                ArrayList readArrayList = parcel.readArrayList(classLoader);
                ArrayList readArrayList2 = parcel.readArrayList(classLoader);
                parcel.enforceNoDataAvail();
                onAvailableSaisUpdated(readArrayList, readArrayList2);
            } else if (i == 3) {
                String readString2 = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onServiceInterfaceAvailable(readString2, readInt2);
            } else if (i == 4) {
                onMiddlewareReady();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMbmsGroupCallSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMbmsGroupCallSessionCallback.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onError(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onAvailableSaisUpdated(List list, List list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onServiceInterfaceAvailable(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
            public void onMiddlewareReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
