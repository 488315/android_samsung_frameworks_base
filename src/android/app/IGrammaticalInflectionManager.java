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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGrammaticalInflectionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGrammaticalInflectionManager)) {
                return (IGrammaticalInflectionManager) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setRequestedApplicationGrammaticalGender(string, i3, i4);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setSystemWideGrammaticalGender(i5, i6);
                parcel2.writeNoException();
            } else if (i == 3) {
                AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int systemGrammaticalGender = getSystemGrammaticalGender(attributionSource, i7);
                parcel2.writeNoException();
                parcel2.writeInt(systemGrammaticalGender);
            } else if (i == 4) {
                AttributionSource attributionSource2 = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                int i8 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iPeekSystemGrammaticalGenderByUserId = peekSystemGrammaticalGenderByUserId(attributionSource2, i8);
                parcel2.writeNoException();
                parcel2.writeInt(iPeekSystemGrammaticalGenderByUserId);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public void setSystemWideGrammaticalGender(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int getSystemGrammaticalGender(AttributionSource attributionSource, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGrammaticalInflectionManager
            public int peekSystemGrammaticalGenderByUserId(AttributionSource attributionSource, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGrammaticalInflectionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
