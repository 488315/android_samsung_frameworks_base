package com.samsung.android.sepunion;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IDeviceInfoManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IDeviceInfoManager";

    public static class Default implements IDeviceInfoManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void clearPendingIntentAsUser(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public int getNumPendingIntentAsUser(int i, String str, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list, String str, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForIntentForAllUsers(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException {
        }
    }

    void clearPendingIntentAsUser(String str, int i) throws RemoteException;

    int getNumPendingIntentAsUser(int i, String str, int i2) throws RemoteException;

    void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list, String str, int i2) throws RemoteException;

    void registerPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException;

    void registerPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    void registerPendingIntentForIntentForAllUsers(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    void registerPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    void unregisterPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException;

    void unregisterPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    void unregisterPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceInfoManager {
        static final int TRANSACTION_clearPendingIntentAsUser = 10;
        static final int TRANSACTION_getNumPendingIntentAsUser = 11;
        static final int TRANSACTION_registerPendingIntent = 4;
        static final int TRANSACTION_registerPendingIntentForCustomEventAsUser = 6;
        static final int TRANSACTION_registerPendingIntentForIntentAsUser = 2;
        static final int TRANSACTION_registerPendingIntentForIntentForAllUsers = 3;
        static final int TRANSACTION_registerPendingIntentForUriAsUser = 1;
        static final int TRANSACTION_unregisterPendingIntent = 5;
        static final int TRANSACTION_unregisterPendingIntentForCustomEventAsUser = 9;
        static final int TRANSACTION_unregisterPendingIntentForIntentAsUser = 8;
        static final int TRANSACTION_unregisterPendingIntentForUriAsUser = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, IDeviceInfoManager.DESCRIPTOR);
        }

        public static IDeviceInfoManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceInfoManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceInfoManager)) {
                return (IDeviceInfoManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerPendingIntentForUriAsUser";
                case 2:
                    return "registerPendingIntentForIntentAsUser";
                case 3:
                    return "registerPendingIntentForIntentForAllUsers";
                case 4:
                    return "registerPendingIntent";
                case 5:
                    return "unregisterPendingIntent";
                case 6:
                    return "registerPendingIntentForCustomEventAsUser";
                case 7:
                    return "unregisterPendingIntentForUriAsUser";
                case 8:
                    return "unregisterPendingIntentForIntentAsUser";
                case 9:
                    return "unregisterPendingIntentForCustomEventAsUser";
                case 10:
                    return "clearPendingIntentAsUser";
                case 11:
                    return "getNumPendingIntentAsUser";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceInfoManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceInfoManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForUriAsUser(uri, pendingIntent, readString, readInt);
                    return true;
                case 2:
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForIntentAsUser(intentFilter, pendingIntent2, readString2, readInt2);
                    return true;
                case 3:
                    IntentFilter intentFilter2 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForIntentForAllUsers(intentFilter2, pendingIntent3, readString3, readInt3);
                    return true;
                case 4:
                    IntentFilter intentFilter3 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int readInt4 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    String readString4 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntent(intentFilter3, pendingIntent4, readInt4, createStringArrayList, readString4, readInt5);
                    return true;
                case 5:
                    IntentFilter intentFilter4 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntent(intentFilter4, pendingIntent5, readString5, readInt6);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForCustomEventAsUser(readString6, pendingIntent6, bundle, readString7, readInt7);
                    return true;
                case 7:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForUriAsUser(uri2, pendingIntent7, readString8, readInt8);
                    return true;
                case 8:
                    IntentFilter intentFilter5 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String readString9 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForIntentAsUser(intentFilter5, pendingIntent8, readString9, readInt9);
                    return true;
                case 9:
                    String readString10 = parcel.readString();
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString11 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForCustomEventAsUser(readString10, pendingIntent9, bundle2, readString11, readInt10);
                    return true;
                case 10:
                    String readString12 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPendingIntentAsUser(readString12, readInt11);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    String readString13 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int numPendingIntentAsUser = getNumPendingIntentAsUser(readInt12, readString13, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(numPendingIntentAsUser);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDeviceInfoManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInfoManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentForAllUsers(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeTypedObject(intentFilter, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void clearPendingIntentAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public int getNumPendingIntentAsUser(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
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
