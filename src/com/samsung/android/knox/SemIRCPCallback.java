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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(SemIRCPCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof SemIRCPCallback)) {
                return (SemIRCPCallback) iInterfaceQueryLocalInterface;
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
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onComplete(arrayListCreateStringArrayList, i3, i4);
                parcel2.writeNoException();
            } else if (i == 2) {
                String string = parcel.readString();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDone(string, i5);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string2 = parcel.readString();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFail(string2, i6, i7);
                parcel2.writeNoException();
            } else if (i == 4) {
                String string3 = parcel.readString();
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onProgress(string3, i8, i9);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onDone(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onFail(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.SemIRCPCallback
            public void onProgress(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemIRCPCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
