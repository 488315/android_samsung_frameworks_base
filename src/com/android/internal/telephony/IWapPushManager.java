package com.android.internal.telephony;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IWapPushManager extends IInterface {

    public static class Default implements IWapPushManager {
        @Override // com.android.internal.telephony.IWapPushManager
        public boolean addPackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IWapPushManager
        public boolean deletePackage(String str, String str2, String str3, String str4) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.IWapPushManager
        public int processMessage(String str, String str2, Intent intent) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.IWapPushManager
        public boolean updatePackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException {
            return false;
        }
    }

    boolean addPackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException;

    boolean deletePackage(String str, String str2, String str3, String str4) throws RemoteException;

    int processMessage(String str, String str2, Intent intent) throws RemoteException;

    boolean updatePackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IWapPushManager {
        public static final String DESCRIPTOR = "com.android.internal.telephony.IWapPushManager";
        static final int TRANSACTION_addPackage = 2;
        static final int TRANSACTION_deletePackage = 4;
        static final int TRANSACTION_processMessage = 1;
        static final int TRANSACTION_updatePackage = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWapPushManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWapPushManager)) {
                return (IWapPushManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "processMessage";
            }
            if (i == 2) {
                return "addPackage";
            }
            if (i == 3) {
                return "updatePackage";
            }
            if (i != 4) {
                return null;
            }
            return "deletePackage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                parcel.enforceNoDataAvail();
                int processMessage = processMessage(readString, readString2, intent);
                parcel2.writeNoException();
                parcel2.writeInt(processMessage);
            } else if (i == 2) {
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean addPackage = addPackage(readString3, readString4, readString5, readString6, readInt, readBoolean, readBoolean2);
                parcel2.writeNoException();
                parcel2.writeBoolean(addPackage);
            } else if (i == 3) {
                String readString7 = parcel.readString();
                String readString8 = parcel.readString();
                String readString9 = parcel.readString();
                String readString10 = parcel.readString();
                int readInt2 = parcel.readInt();
                boolean readBoolean3 = parcel.readBoolean();
                boolean readBoolean4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean updatePackage = updatePackage(readString7, readString8, readString9, readString10, readInt2, readBoolean3, readBoolean4);
                parcel2.writeNoException();
                parcel2.writeBoolean(updatePackage);
            } else if (i == 4) {
                String readString11 = parcel.readString();
                String readString12 = parcel.readString();
                String readString13 = parcel.readString();
                String readString14 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean deletePackage = deletePackage(readString11, readString12, readString13, readString14);
                parcel2.writeNoException();
                parcel2.writeBoolean(deletePackage);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IWapPushManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IWapPushManager
            public int processMessage(String str, String str2, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWapPushManager
            public boolean addPackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWapPushManager
            public boolean updatePackage(String str, String str2, String str3, String str4, int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IWapPushManager
            public boolean deletePackage(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
