package com.samsung.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.core.IFoldStarCallback;
import java.util.HashMap;
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

    public static class Default implements IFoldStarManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public Map getDisplayCompatPackages(int i, int i2, Map map) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public Map getFixedAspectRatioPackages(int i, int i2, Map map) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void initAppContinuityValueWhenReset(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void registerFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setAllAppContinuityMode(int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setAppContinuityMode(String str, int i, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setDisplayCompatPackages(int i, Map map, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setFixedAspectRatioPackages(int i, Map map, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void setFrontScreenOnWhenAppContinuityMode(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.core.IFoldStarManager
        public void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException {
        }
    }

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

    public static abstract class Stub extends Binder implements IFoldStarManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 303;
        }

        public Stub() {
            attachInterface(this, IFoldStarManager.DESCRIPTOR);
        }

        public static IFoldStarManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFoldStarManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFoldStarManager)) {
                return (IFoldStarManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 8) {
                return "registerFoldStarCallback";
            }
            if (i == 9) {
                return "unregisterFoldStarCallback";
            }
            if (i == 101) {
                return "setDisplayCompatPackages";
            }
            if (i == 102) {
                return "getDisplayCompatPackages";
            }
            if (i == 201) {
                return "setFixedAspectRatioPackages";
            }
            if (i == 202) {
                return "getFixedAspectRatioPackages";
            }
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFoldStarManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFoldStarManager.DESCRIPTOR);
                return true;
            }
            if (i == 8) {
                IFoldStarCallback asInterface = IFoldStarCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerFoldStarCallback(asInterface);
                parcel2.writeNoException();
            } else if (i == 9) {
                IFoldStarCallback asInterface2 = IFoldStarCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterFoldStarCallback(asInterface2);
                parcel2.writeNoException();
            } else if (i == 101) {
                int readInt = parcel.readInt();
                HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setDisplayCompatPackages(readInt, readHashMap, readBoolean);
                parcel2.writeNoException();
            } else if (i == 102) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                HashMap readHashMap2 = parcel.readHashMap(getClass().getClassLoader());
                parcel.enforceNoDataAvail();
                Map displayCompatPackages = getDisplayCompatPackages(readInt2, readInt3, readHashMap2);
                parcel2.writeNoException();
                parcel2.writeMap(displayCompatPackages);
            } else if (i == 201) {
                int readInt4 = parcel.readInt();
                HashMap readHashMap3 = parcel.readHashMap(getClass().getClassLoader());
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setFixedAspectRatioPackages(readInt4, readHashMap3, readBoolean2);
                parcel2.writeNoException();
            } else if (i == 202) {
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                HashMap readHashMap4 = parcel.readHashMap(getClass().getClassLoader());
                parcel.enforceNoDataAvail();
                Map fixedAspectRatioPackages = getFixedAspectRatioPackages(readInt5, readInt6, readHashMap4);
                parcel2.writeNoException();
                parcel2.writeMap(fixedAspectRatioPackages);
            } else {
                switch (i) {
                    case 301:
                        int readInt7 = parcel.readInt();
                        boolean readBoolean3 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setAllAppContinuityMode(readInt7, readBoolean3);
                        parcel2.writeNoException();
                        break;
                    case 302:
                        boolean readBoolean4 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setFrontScreenOnWhenAppContinuityMode(readBoolean4);
                        parcel2.writeNoException();
                        break;
                    case 303:
                        boolean readBoolean5 = parcel.readBoolean();
                        boolean readBoolean6 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        initAppContinuityValueWhenReset(readBoolean5, readBoolean6);
                        parcel2.writeNoException();
                        break;
                    case 304:
                        String readString = parcel.readString();
                        int readInt8 = parcel.readInt();
                        boolean readBoolean7 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setAppContinuityMode(readString, readInt8, readBoolean7);
                        parcel2.writeNoException();
                        break;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            }
            return true;
        }

        private static class Proxy implements IFoldStarManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFoldStarManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void registerFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iFoldStarCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iFoldStarCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setDisplayCompatPackages(int i, Map map, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeMap(map);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getDisplayCompatPackages(int i, int i2, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeMap(map);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFixedAspectRatioPackages(int i, Map map, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeMap(map);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getFixedAspectRatioPackages(int i, int i2, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeMap(map);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAllAppContinuityMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(301, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFrontScreenOnWhenAppContinuityMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(302, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void initAppContinuityValueWhenReset(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(303, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAppContinuityMode(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(304, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
