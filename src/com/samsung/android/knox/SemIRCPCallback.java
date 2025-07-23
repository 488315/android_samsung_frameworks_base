package com.samsung.android.knox;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface SemIRCPCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.SemIRCPCallback";

    public static class Default implements SemIRCPCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onComplete(List<String> list, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onDone(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onFail(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onProgress(String str, int i, int i2) throws RemoteException {
        }
    }

    void onComplete(List<String> list, int i, int i2) throws RemoteException;

    void onDone(String str, int i) throws RemoteException;

    void onFail(String str, int i, int i2) throws RemoteException;

    void onProgress(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements SemIRCPCallback {
        static final int TRANSACTION_onComplete = 1;
        static final int TRANSACTION_onDone = 2;
        static final int TRANSACTION_onFail = 3;
        static final int TRANSACTION_onProgress = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, SemIRCPCallback.DESCRIPTOR);
        }

        public static SemIRCPCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemIRCPCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemIRCPCallback)) {
                return (SemIRCPCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onComplete";
            }
            if (i == 2) {
                return "onDone";
            }
            if (i == 3) {
                return "onFail";
            }
            if (i != 4) {
                return null;
            }
            return "onProgress";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemIRCPCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemIRCPCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onComplete(createStringArrayList, readInt, readInt2);
                parcel2.writeNoException();
            } else if (i == 2) {
                String readString = parcel.readString();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDone(readString, readInt3);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString2 = parcel.readString();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFail(readString2, readInt4, readInt5);
                parcel2.writeNoException();
            } else if (i == 4) {
                String readString3 = parcel.readString();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onProgress(readString3, readInt6, readInt7);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements SemIRCPCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemIRCPCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onComplete(List<String> list, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onDone(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onFail(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onProgress(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
