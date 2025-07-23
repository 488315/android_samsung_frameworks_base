package com.samsung.android.knox.mtd;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.mtd.IMtdCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IKfbpFramework extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IKfbpFramework";

    public static class Default implements IKfbpFramework {
        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeContent(String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeUrl(String str, String str2, int i, Intent intent, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.knox.mtd.IKfbpFramework
        public void analyzeUrls(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void analyzeContent(String str, String str2, int i, int i2) throws RemoteException;

    void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException;

    void analyzeUrl(String str, String str2, int i, Intent intent, int i2) throws RemoteException;

    void analyzeUrls(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IKfbpFramework {
        static final int TRANSACTION_analyzeContent = 2;
        static final int TRANSACTION_analyzeContents = 4;
        static final int TRANSACTION_analyzeUrl = 1;
        static final int TRANSACTION_analyzeUrls = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IKfbpFramework.DESCRIPTOR);
        }

        public static IKfbpFramework asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKfbpFramework.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKfbpFramework)) {
                return (IKfbpFramework) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "analyzeUrl";
            }
            if (i == 2) {
                return "analyzeContent";
            }
            if (i == 3) {
                return "analyzeUrls";
            }
            if (i != 4) {
                return null;
            }
            return "analyzeContents";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKfbpFramework.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKfbpFramework.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                analyzeUrl(readString, readString2, readInt, intent, readInt2);
            } else if (i == 2) {
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                analyzeContent(readString3, readString4, readInt3, readInt4);
            } else if (i == 3) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                IMtdCallback asInterface = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                analyzeUrls(createStringArrayList, asInterface, readString5);
            } else if (i == 4) {
                ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                IMtdCallback asInterface2 = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                analyzeContents(createStringArrayList2, asInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IKfbpFramework {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKfbpFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeUrl(String str, String str2, int i, Intent intent, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContent(String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeUrls(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iMtdCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iMtdCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
