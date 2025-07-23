package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sepunion.IGoodCatchDispatcher;
import java.util.List;

/* loaded from: classes6.dex */
public interface IGoodCatchManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IGoodCatchManager";

    public static class Default implements IGoodCatchManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public List<String> getSelectedSettingKey() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public void registerListener(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IGoodCatchManager
        public void update(String[] strArr) throws RemoteException {
        }
    }

    List<String> getSelectedSettingKey() throws RemoteException;

    void registerListener(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) throws RemoteException;

    void update(String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IGoodCatchManager {
        static final int TRANSACTION_getSelectedSettingKey = 3;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_update = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGoodCatchManager.DESCRIPTOR);
        }

        public static IGoodCatchManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGoodCatchManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGoodCatchManager)) {
                return (IGoodCatchManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerListener";
            }
            if (i == 2) {
                return "update";
            }
            if (i != 3) {
                return null;
            }
            return "getSelectedSettingKey";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGoodCatchManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGoodCatchManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String[] createStringArray = parcel.createStringArray();
                IGoodCatchDispatcher asInterface = IGoodCatchDispatcher.Stub.asInterface(parcel.readStrongBinder());
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                registerListener(readString, createStringArray, asInterface, readStrongBinder);
                parcel2.writeNoException();
            } else if (i == 2) {
                String[] createStringArray2 = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                update(createStringArray2);
                parcel2.writeNoException();
            } else if (i == 3) {
                List<String> selectedSettingKey = getSelectedSettingKey();
                parcel2.writeNoException();
                parcel2.writeStringList(selectedSettingKey);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGoodCatchManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGoodCatchManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public void registerListener(String str, String[] strArr, IGoodCatchDispatcher iGoodCatchDispatcher, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iGoodCatchDispatcher);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public void update(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IGoodCatchManager
            public List<String> getSelectedSettingKey() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGoodCatchManager.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
