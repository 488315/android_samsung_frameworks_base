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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKfbpFramework.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKfbpFramework)) {
                return (IKfbpFramework) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                analyzeUrl(string, string2, i3, intent, i4);
            } else if (i == 2) {
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                analyzeContent(string3, string4, i5, i6);
            } else if (i == 3) {
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                IMtdCallback iMtdCallbackAsInterface = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                String string5 = parcel.readString();
                parcel.enforceNoDataAvail();
                analyzeUrls(arrayListCreateStringArrayList, iMtdCallbackAsInterface, string5);
            } else if (i == 4) {
                ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                IMtdCallback iMtdCallbackAsInterface2 = IMtdCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                analyzeContents(arrayListCreateStringArrayList2, iMtdCallbackAsInterface2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContent(String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeUrls(List<String> list, IMtdCallback iMtdCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iMtdCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.mtd.IKfbpFramework
            public void analyzeContents(List<String> list, IMtdCallback iMtdCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKfbpFramework.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iMtdCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
