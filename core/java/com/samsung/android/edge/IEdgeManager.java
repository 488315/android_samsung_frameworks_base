package com.samsung.android.edge;

import android.content.ComponentName;
import android.hardware.display.SemWifiDisplayParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IEdgeManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.edge.IEdgeManager";

    void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName)
            throws RemoteException;

    void cancelNotification(String str, String str2, int i, int i2, String str3)
            throws RemoteException;

    void cancelNotificationByGroupKey(
            String str, String str2, int i, int i2, String str3, String str4)
            throws RemoteException;

    void disable(int i, String str, IBinder iBinder) throws RemoteException;

    void disableEdgeLightingNotification(String str, boolean z) throws RemoteException;

    int getEdgeLightingState() throws RemoteException;

    boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException;

    boolean isPackageEnabled(String str, int i) throws RemoteException;

    void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName)
            throws RemoteException;

    void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder)
            throws RemoteException;

    void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException;

    void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException;

    void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException;

    void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException;

    void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy)
            throws RemoteException;

    public static class Default implements IEdgeManager {
        @Override // com.samsung.android.edge.IEdgeManager
        public void bindEdgeLightingService(IBinder binder, int condition, ComponentName component)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void unbindEdgeLightingService(IBinder binder, String packageName)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void updateEdgeLightingPackageList(String callingPackage, List<String> list)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void updateEdgeLightingPolicy(String callingPackage, EdgeLightingPolicy policy)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void registerEdgeLightingListener(IBinder binder, ComponentName component)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void unregisterEdgeLightingListener(IBinder binder, String packageName)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void startEdgeLighting(String packageName, SemEdgeLightingInfo info, IBinder token)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void stopEdgeLighting(String packageName, IBinder token) throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public int getEdgeLightingState() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public boolean isEdgeLightingNotificationAllowed(String packageName)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void disable(int what, String callingPackage, IBinder binder)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void disableEdgeLightingNotification(String callingPackage, boolean disable)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public boolean isPackageEnabled(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.edge.IEdgeManager
        public void cancelNotification(String pkg, String tag, int id, int userId, String key)
                throws RemoteException {}

        @Override // com.samsung.android.edge.IEdgeManager
        public void cancelNotificationByGroupKey(
                String pkg, String tag, int id, int userId, String key, String groupKey)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IEdgeManager {
        static final int TRANSACTION_bindEdgeLightingService = 1;
        static final int TRANSACTION_cancelNotification = 14;
        static final int TRANSACTION_cancelNotificationByGroupKey = 15;
        static final int TRANSACTION_disable = 11;
        static final int TRANSACTION_disableEdgeLightingNotification = 12;
        static final int TRANSACTION_getEdgeLightingState = 9;
        static final int TRANSACTION_isEdgeLightingNotificationAllowed = 10;
        static final int TRANSACTION_isPackageEnabled = 13;
        static final int TRANSACTION_registerEdgeLightingListener = 5;
        static final int TRANSACTION_startEdgeLighting = 7;
        static final int TRANSACTION_stopEdgeLighting = 8;
        static final int TRANSACTION_unbindEdgeLightingService = 2;
        static final int TRANSACTION_unregisterEdgeLightingListener = 6;
        static final int TRANSACTION_updateEdgeLightingPackageList = 3;
        static final int TRANSACTION_updateEdgeLightingPolicy = 4;

        public Stub() {
            attachInterface(this, IEdgeManager.DESCRIPTOR);
        }

        public static IEdgeManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEdgeManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IEdgeManager)) {
                return (IEdgeManager) iin;
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
                    return "bindEdgeLightingService";
                case 2:
                    return "unbindEdgeLightingService";
                case 3:
                    return "updateEdgeLightingPackageList";
                case 4:
                    return "updateEdgeLightingPolicy";
                case 5:
                    return "registerEdgeLightingListener";
                case 6:
                    return "unregisterEdgeLightingListener";
                case 7:
                    return "startEdgeLighting";
                case 8:
                    return "stopEdgeLighting";
                case 9:
                    return "getEdgeLightingState";
                case 10:
                    return "isEdgeLightingNotificationAllowed";
                case 11:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 12:
                    return "disableEdgeLightingNotification";
                case 13:
                    return "isPackageEnabled";
                case 14:
                    return "cancelNotification";
                case 15:
                    return "cancelNotificationByGroupKey";
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
                data.enforceInterface(IEdgeManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IEdgeManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    ComponentName _arg2 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    bindEdgeLightingService(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    unbindEdgeLightingService(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    List<String> _arg13 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    updateEdgeLightingPackageList(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    EdgeLightingPolicy _arg14 =
                            (EdgeLightingPolicy) data.readTypedObject(EdgeLightingPolicy.CREATOR);
                    data.enforceNoDataAvail();
                    updateEdgeLightingPolicy(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg05 = data.readStrongBinder();
                    ComponentName _arg15 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    registerEdgeLightingListener(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg06 = data.readStrongBinder();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterEdgeLightingListener(_arg06, _arg16);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    SemEdgeLightingInfo _arg17 =
                            (SemEdgeLightingInfo) data.readTypedObject(SemEdgeLightingInfo.CREATOR);
                    IBinder _arg22 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    startEdgeLighting(_arg07, _arg17, _arg22);
                    reply.writeNoException();
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    IBinder _arg18 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    stopEdgeLighting(_arg08, _arg18);
                    reply.writeNoException();
                    return true;
                case 9:
                    int _result = getEdgeLightingState();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result2 = isEdgeLightingNotificationAllowed(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    String _arg19 = data.readString();
                    IBinder _arg23 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    disable(_arg010, _arg19, _arg23);
                    reply.writeNoException();
                    return true;
                case 12:
                    String _arg011 = data.readString();
                    boolean _arg110 = data.readBoolean();
                    data.enforceNoDataAvail();
                    disableEdgeLightingNotification(_arg011, _arg110);
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isPackageEnabled(_arg012, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 14:
                    String _arg013 = data.readString();
                    String _arg112 = data.readString();
                    int _arg24 = data.readInt();
                    int _arg3 = data.readInt();
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    cancelNotification(_arg013, _arg112, _arg24, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case 15:
                    String _arg014 = data.readString();
                    String _arg113 = data.readString();
                    int _arg25 = data.readInt();
                    int _arg32 = data.readInt();
                    String _arg42 = data.readString();
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    cancelNotificationByGroupKey(_arg014, _arg113, _arg25, _arg32, _arg42, _arg5);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IEdgeManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEdgeManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void bindEdgeLightingService(
                    IBinder binder, int condition, ComponentName component) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(condition);
                    _data.writeTypedObject(component, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unbindEdgeLightingService(IBinder binder, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(packageName);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPackageList(String callingPackage, List<String> list)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStringList(list);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void updateEdgeLightingPolicy(String callingPackage, EdgeLightingPolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void registerEdgeLightingListener(IBinder binder, ComponentName component)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeTypedObject(component, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void unregisterEdgeLightingListener(IBinder binder, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(packageName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void startEdgeLighting(
                    String packageName, SemEdgeLightingInfo info, IBinder token)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(info, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void stopEdgeLighting(String packageName, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public int getEdgeLightingState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isEdgeLightingNotificationAllowed(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disable(int what, String callingPackage, IBinder binder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void disableEdgeLightingNotification(String callingPackage, boolean disable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeBoolean(disable);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public boolean isPackageEnabled(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotification(String pkg, String tag, int id, int userId, String key)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(tag);
                    _data.writeInt(id);
                    _data.writeInt(userId);
                    _data.writeString(key);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.edge.IEdgeManager
            public void cancelNotificationByGroupKey(
                    String pkg, String tag, int id, int userId, String key, String groupKey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IEdgeManager.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(tag);
                    _data.writeInt(id);
                    _data.writeInt(userId);
                    _data.writeString(key);
                    _data.writeString(groupKey);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
