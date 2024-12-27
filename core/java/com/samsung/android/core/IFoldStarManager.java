package com.samsung.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.Map;

/* loaded from: classes6.dex */
public interface IFoldStarManager extends IInterface {
    public static final int ASPECT_RATIO_POLICY_16_TO_9 = 2;
    public static final int ASPECT_RATIO_POLICY_4_TO_3 = 3;
    public static final int ASPECT_RATIO_POLICY_FULL_SCREEN = 1;
    public static final int ASPECT_RATIO_POLICY_NOT_SUPPORTED = 0;
    public static final String DESCRIPTOR = "com.samsung.android.core.IFoldStarManager";
    public static final int OPTION_GET_ALL_PACKAGES = 0;
    public static final int OPTION_GET_REQUESTED_FIXED_ASPECT_RATIO_POLICY = 4;
    public static final int OPTION_GET_REQUESTED_PACKAGES = 3;
    public static final int OPTION_GET_SYSTEM_SETTINGS = 2;
    public static final int OPTION_GET_USER_SETTINGS = 1;

    Map getDisplayCompatPackages(int i, int i2, Map map) throws RemoteException;

    Map getFixedAspectRatioPackages(int i, int i2, Map map) throws RemoteException;

    void initAppContinuityValueWhenReset(boolean z, boolean z2) throws RemoteException;

    void registerFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException;

    void setAllAppContinuityMode(int i, boolean z) throws RemoteException;

    void setAppContinuityMode(String str, int i, boolean z) throws RemoteException;

    void setDisplayCompatPackages(int i, Map map, boolean z) throws RemoteException;

    void setFixedAspectRatioPackages(int i, Map map, boolean z) throws RemoteException;

    void setFrontScreenOnWhenAppContinuityMode(boolean z) throws RemoteException;

    void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException;

    public static class Default implements IFoldStarManager {
        @Override // com.samsung.android.core.IFoldStarManager
        public void registerFoldStarCallback(IFoldStarCallback callback) throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public void unregisterFoldStarCallback(IFoldStarCallback callback) throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public void setDisplayCompatPackages(int userId, Map requestedPackages, boolean replaceAll)
                throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public Map getDisplayCompatPackages(int userId, int option, Map requestedPackages)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setFixedAspectRatioPackages(
                int userId, Map requestedPackages, boolean replaceAll) throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public Map getFixedAspectRatioPackages(int userId, int option, Map requestedPackages)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setAllAppContinuityMode(int userId, boolean allAppContinuityModeOn)
                throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public void setFrontScreenOnWhenAppContinuityMode(boolean frontScreenOn)
                throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public void initAppContinuityValueWhenReset(
                boolean allAppContinuityModeOn, boolean frontScreenOn) throws RemoteException {}

        @Override // com.samsung.android.core.IFoldStarManager
        public void setAppContinuityMode(
                String packageName, int userId, boolean applyAppContinuityMode)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IFoldStarManager {
        static final int TRANSACTION_getDisplayCompatPackages = 102;
        static final int TRANSACTION_getFixedAspectRatioPackages = 202;
        static final int TRANSACTION_initAppContinuityValueWhenReset = 303;
        static final int TRANSACTION_registerFoldStarCallback = 8;
        static final int TRANSACTION_setAllAppContinuityMode = 301;
        static final int TRANSACTION_setAppContinuityMode = 304;
        static final int TRANSACTION_setDisplayCompatPackages = 101;
        static final int TRANSACTION_setFixedAspectRatioPackages = 201;
        static final int TRANSACTION_setFrontScreenOnWhenAppContinuityMode = 302;
        static final int TRANSACTION_unregisterFoldStarCallback = 9;

        public Stub() {
            attachInterface(this, IFoldStarManager.DESCRIPTOR);
        }

        public static IFoldStarManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IFoldStarManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IFoldStarManager)) {
                return (IFoldStarManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 8:
                    return "registerFoldStarCallback";
                case 9:
                    return "unregisterFoldStarCallback";
                case 101:
                    return "setDisplayCompatPackages";
                case 102:
                    return "getDisplayCompatPackages";
                case 201:
                    return "setFixedAspectRatioPackages";
                case 202:
                    return "getFixedAspectRatioPackages";
                case 301:
                    return "setAllAppContinuityMode";
                case 302:
                    return "setFrontScreenOnWhenAppContinuityMode";
                case 303:
                    return "initAppContinuityValueWhenReset";
                case 304:
                    return "setAppContinuityMode";
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
                data.enforceInterface(IFoldStarManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFoldStarManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 8:
                    IFoldStarCallback _arg0 =
                            IFoldStarCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerFoldStarCallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 9:
                    IFoldStarCallback _arg02 =
                            IFoldStarCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterFoldStarCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 101:
                    int _arg03 = data.readInt();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg1 = data.readHashMap(cl);
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDisplayCompatPackages(_arg03, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 102:
                    int _arg04 = data.readInt();
                    int _arg12 = data.readInt();
                    ClassLoader cl2 = getClass().getClassLoader();
                    Map _arg22 = data.readHashMap(cl2);
                    data.enforceNoDataAvail();
                    Map _result = getDisplayCompatPackages(_arg04, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeMap(_result);
                    return true;
                case 201:
                    int _arg05 = data.readInt();
                    ClassLoader cl3 = getClass().getClassLoader();
                    Map _arg13 = data.readHashMap(cl3);
                    boolean _arg23 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFixedAspectRatioPackages(_arg05, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 202:
                    int _arg06 = data.readInt();
                    int _arg14 = data.readInt();
                    ClassLoader cl4 = getClass().getClassLoader();
                    Map _arg24 = data.readHashMap(cl4);
                    data.enforceNoDataAvail();
                    Map _result2 = getFixedAspectRatioPackages(_arg06, _arg14, _arg24);
                    reply.writeNoException();
                    reply.writeMap(_result2);
                    return true;
                case 301:
                    int _arg07 = data.readInt();
                    boolean _arg15 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAllAppContinuityMode(_arg07, _arg15);
                    reply.writeNoException();
                    return true;
                case 302:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFrontScreenOnWhenAppContinuityMode(_arg08);
                    reply.writeNoException();
                    return true;
                case 303:
                    boolean _arg09 = data.readBoolean();
                    boolean _arg16 = data.readBoolean();
                    data.enforceNoDataAvail();
                    initAppContinuityValueWhenReset(_arg09, _arg16);
                    reply.writeNoException();
                    return true;
                case 304:
                    String _arg010 = data.readString();
                    int _arg17 = data.readInt();
                    boolean _arg25 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAppContinuityMode(_arg010, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFoldStarManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFoldStarManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void registerFoldStarCallback(IFoldStarCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void unregisterFoldStarCallback(IFoldStarCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setDisplayCompatPackages(
                    int userId, Map requestedPackages, boolean replaceAll) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeMap(requestedPackages);
                    _data.writeBoolean(replaceAll);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getDisplayCompatPackages(int userId, int option, Map requestedPackages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(option);
                    _data.writeMap(requestedPackages);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFixedAspectRatioPackages(
                    int userId, Map requestedPackages, boolean replaceAll) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeMap(requestedPackages);
                    _data.writeBoolean(replaceAll);
                    this.mRemote.transact(201, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getFixedAspectRatioPackages(int userId, int option, Map requestedPackages)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(option);
                    _data.writeMap(requestedPackages);
                    this.mRemote.transact(202, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAllAppContinuityMode(int userId, boolean allAppContinuityModeOn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(allAppContinuityModeOn);
                    this.mRemote.transact(301, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFrontScreenOnWhenAppContinuityMode(boolean frontScreenOn)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeBoolean(frontScreenOn);
                    this.mRemote.transact(302, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void initAppContinuityValueWhenReset(
                    boolean allAppContinuityModeOn, boolean frontScreenOn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeBoolean(allAppContinuityModeOn);
                    _data.writeBoolean(frontScreenOn);
                    this.mRemote.transact(303, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAppContinuityMode(
                    String packageName, int userId, boolean applyAppContinuityMode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeBoolean(applyAppContinuityMode);
                    this.mRemote.transact(304, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 303;
        }
    }
}
