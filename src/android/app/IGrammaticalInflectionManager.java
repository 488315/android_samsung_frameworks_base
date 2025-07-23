package android.app;

import android.content.AttributionSource;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGrammaticalInflectionManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGrammaticalInflectionManager";

    public static class Default implements IGrammaticalInflectionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IGrammaticalInflectionManager
        public int getSystemGrammaticalGender(AttributionSource attributionSource, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IGrammaticalInflectionManager
        public int peekSystemGrammaticalGenderByUserId(AttributionSource attributionSource, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IGrammaticalInflectionManager
        public void setRequestedApplicationGrammaticalGender(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IGrammaticalInflectionManager
        public void setSystemWideGrammaticalGender(int i, int i2) throws RemoteException {
        }
    }

    int getSystemGrammaticalGender(AttributionSource attributionSource, int i) throws RemoteException;

    int peekSystemGrammaticalGenderByUserId(AttributionSource attributionSource, int i) throws RemoteException;

    void setRequestedApplicationGrammaticalGender(String str, int i, int i2) throws RemoteException;

    void setSystemWideGrammaticalGender(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGrammaticalInflectionManager {
        static final int TRANSACTION_getSystemGrammaticalGender = 3;
        static final int TRANSACTION_peekSystemGrammaticalGenderByUserId = 4;
        static final int TRANSACTION_setRequestedApplicationGrammaticalGender = 1;
        static final int TRANSACTION_setSystemWideGrammaticalGender = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IGrammaticalInflectionManager.DESCRIPTOR);
        }

        public static IGrammaticalInflectionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGrammaticalInflectionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGrammaticalInflectionManager)) {
                return (IGrammaticalInflectionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setRequestedApplicationGrammaticalGender";
            }
            if (i == 2) {
                return "setSystemWideGrammaticalGender";
            }
            if (i == 3) {
                return "getSystemGrammaticalGender";
            }
            if (i != 4) {
                return null;
            }
            return "peekSystemGrammaticalGenderByUserId";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGrammaticalInflectionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGrammaticalInflectionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setRequestedApplicationGrammaticalGender(readString, readInt, readInt2);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setSystemWideGrammaticalGender(readInt3, readInt4);
                parcel2.writeNoException();
            } else if (i == 3) {
                AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int systemGrammaticalGender = getSystemGrammaticalGender(attributionSource, readInt5);
                parcel2.writeNoException();
                parcel2.writeInt(systemGrammaticalGender);
            } else if (i == 4) {
                AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int peekSystemGrammaticalGenderByUserId = peekSystemGrammaticalGenderByUserId(attributionSource2, readInt6);
                parcel2.writeNoException();
                parcel2.writeInt(peekSystemGrammaticalGenderByUserId);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGrammaticalInflectionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGrammaticalInflectionManager.DESCRIPTOR;
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setRequestedApplicationGrammaticalGender(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setSystemWideGrammaticalGender(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int getSystemGrammaticalGender(AttributionSource attributionSource, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int peekSystemGrammaticalGenderByUserId(AttributionSource attributionSource, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
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
