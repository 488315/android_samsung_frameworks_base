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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceInfoManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDeviceInfoManager)) {
                return (IDeviceInfoManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForUriAsUser(uri, pendingIntent, string, i3);
                    return true;
                case 2:
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForIntentAsUser(intentFilter, pendingIntent2, string2, i4);
                    return true;
                case 3:
                    IntentFilter intentFilter2 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent3 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string3 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForIntentForAllUsers(intentFilter2, pendingIntent3, string3, i5);
                    return true;
                case 4:
                    IntentFilter intentFilter3 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent4 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int i6 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string4 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntent(intentFilter3, pendingIntent4, i6, arrayListCreateStringArrayList, string4, i7);
                    return true;
                case 5:
                    IntentFilter intentFilter4 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent5 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string5 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntent(intentFilter4, pendingIntent5, string5, i8);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    PendingIntent pendingIntent6 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string7 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerPendingIntentForCustomEventAsUser(string6, pendingIntent6, bundle, string7, i9);
                    return true;
                case 7:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    PendingIntent pendingIntent7 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string8 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForUriAsUser(uri2, pendingIntent7, string8, i10);
                    return true;
                case 8:
                    IntentFilter intentFilter5 = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent pendingIntent8 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    String string9 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForIntentAsUser(intentFilter5, pendingIntent8, string9, i11);
                    return true;
                case 9:
                    String string10 = parcel.readString();
                    PendingIntent pendingIntent9 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string11 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterPendingIntentForCustomEventAsUser(string10, pendingIntent9, bundle2, string11, i12);
                    return true;
                case 10:
                    String string12 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPendingIntentAsUser(string12, i13);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    String string13 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int numPendingIntentAsUser = getNumPendingIntentAsUser(i14, string13, i15);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentForAllUsers(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, int i, List<String> list, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntent(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForIntentAsUser(IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForCustomEventAsUser(String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void clearPendingIntentAsUser(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public int getNumPendingIntentAsUser(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
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
