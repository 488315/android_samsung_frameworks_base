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

import java.util.List;

public interface IDeviceInfoManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IDeviceInfoManager";

    void clearPendingIntentAsUser(String str, int i) throws RemoteException;

    int getNumPendingIntentAsUser(int i, String str, int i2) throws RemoteException;

    void registerPendingIntent(
            IntentFilter intentFilter,
            PendingIntent pendingIntent,
            int i,
            List<String> list,
            String str,
            int i2)
            throws RemoteException;

    void registerPendingIntentForCustomEventAsUser(
            String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i)
            throws RemoteException;

    void registerPendingIntentForIntentAsUser(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i)
            throws RemoteException;

    void registerPendingIntentForIntentForAllUsers(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i)
            throws RemoteException;

    void registerPendingIntentForUriAsUser(Uri uri, PendingIntent pendingIntent, String str, int i)
            throws RemoteException;

    void unregisterPendingIntent(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i)
            throws RemoteException;

    void unregisterPendingIntentForCustomEventAsUser(
            String str, PendingIntent pendingIntent, Bundle bundle, String str2, int i)
            throws RemoteException;

    void unregisterPendingIntentForIntentAsUser(
            IntentFilter intentFilter, PendingIntent pendingIntent, String str, int i)
            throws RemoteException;

    void unregisterPendingIntentForUriAsUser(
            Uri uri, PendingIntent pendingIntent, String str, int i) throws RemoteException;

    public static class Default implements IDeviceInfoManager {
        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForUriAsUser(
                Uri uri, PendingIntent intent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForIntentAsUser(
                IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForIntentForAllUsers(
                IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntent(
                IntentFilter filter,
                PendingIntent pendingIntent,
                int flag,
                List<String> conditions,
                String callingPackage,
                int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntent(
                IntentFilter filter, PendingIntent pendingIntent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void registerPendingIntentForCustomEventAsUser(
                String event,
                PendingIntent intent,
                Bundle bundle,
                String callingPackage,
                int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForUriAsUser(
                Uri uri, PendingIntent intent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForIntentAsUser(
                IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void unregisterPendingIntentForCustomEventAsUser(
                String event,
                PendingIntent intent,
                Bundle bundle,
                String callingPackage,
                int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public void clearPendingIntentAsUser(String callingPackage, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.sepunion.IDeviceInfoManager
        public int getNumPendingIntentAsUser(int type, String callingPackage, int userId)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDeviceInfoManager {
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

        public Stub() {
            attachInterface(this, IDeviceInfoManager.DESCRIPTOR);
        }

        public static IDeviceInfoManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDeviceInfoManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IDeviceInfoManager)) {
                return (IDeviceInfoManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDeviceInfoManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDeviceInfoManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Uri _arg0 = (Uri) data.readTypedObject(Uri.CREATOR);
                    PendingIntent _arg1 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPendingIntentForUriAsUser(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    IntentFilter _arg02 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent _arg12 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg22 = data.readString();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPendingIntentForIntentAsUser(_arg02, _arg12, _arg22, _arg32);
                    return true;
                case 3:
                    IntentFilter _arg03 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent _arg13 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg23 = data.readString();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPendingIntentForIntentForAllUsers(_arg03, _arg13, _arg23, _arg33);
                    return true;
                case 4:
                    IntentFilter _arg04 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent _arg14 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    int _arg24 = data.readInt();
                    List<String> _arg34 = data.createStringArrayList();
                    String _arg4 = data.readString();
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPendingIntent(_arg04, _arg14, _arg24, _arg34, _arg4, _arg5);
                    return true;
                case 5:
                    IntentFilter _arg05 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent _arg15 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg25 = data.readString();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterPendingIntent(_arg05, _arg15, _arg25, _arg35);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    PendingIntent _arg16 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    Bundle _arg26 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg36 = data.readString();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    registerPendingIntentForCustomEventAsUser(
                            _arg06, _arg16, _arg26, _arg36, _arg42);
                    return true;
                case 7:
                    Uri _arg07 = (Uri) data.readTypedObject(Uri.CREATOR);
                    PendingIntent _arg17 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg27 = data.readString();
                    int _arg37 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterPendingIntentForUriAsUser(_arg07, _arg17, _arg27, _arg37);
                    return true;
                case 8:
                    IntentFilter _arg08 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    PendingIntent _arg18 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    String _arg28 = data.readString();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterPendingIntentForIntentAsUser(_arg08, _arg18, _arg28, _arg38);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    PendingIntent _arg19 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    Bundle _arg29 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg39 = data.readString();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    unregisterPendingIntentForCustomEventAsUser(
                            _arg09, _arg19, _arg29, _arg39, _arg43);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    clearPendingIntentAsUser(_arg010, _arg110);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    String _arg111 = data.readString();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getNumPendingIntentAsUser(_arg011, _arg111, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDeviceInfoManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceInfoManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForUriAsUser(
                    Uri uri, PendingIntent intent, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentAsUser(
                    IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForIntentForAllUsers(
                    IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntent(
                    IntentFilter filter,
                    PendingIntent pendingIntent,
                    int flag,
                    List<String> conditions,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(pendingIntent, 0);
                    _data.writeInt(flag);
                    _data.writeStringList(conditions);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntent(
                    IntentFilter filter,
                    PendingIntent pendingIntent,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(pendingIntent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void registerPendingIntentForCustomEventAsUser(
                    String event,
                    PendingIntent intent,
                    Bundle bundle,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeString(event);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(bundle, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForUriAsUser(
                    Uri uri, PendingIntent intent, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForIntentAsUser(
                    IntentFilter filter, PendingIntent intent, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeTypedObject(filter, 0);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void unregisterPendingIntentForCustomEventAsUser(
                    String event,
                    PendingIntent intent,
                    Bundle bundle,
                    String callingPackage,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeString(event);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(bundle, 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public void clearPendingIntentAsUser(String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IDeviceInfoManager
            public int getNumPendingIntentAsUser(int type, String callingPackage, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDeviceInfoManager.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
