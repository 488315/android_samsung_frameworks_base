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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFoldStarManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFoldStarManager)) {
                return (IFoldStarManager) iInterfaceQueryLocalInterface;
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
                IFoldStarCallback iFoldStarCallbackAsInterface = IFoldStarCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerFoldStarCallback(iFoldStarCallbackAsInterface);
                parcel2.writeNoException();
            } else if (i == 9) {
                IFoldStarCallback iFoldStarCallbackAsInterface2 = IFoldStarCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterFoldStarCallback(iFoldStarCallbackAsInterface2);
                parcel2.writeNoException();
            } else if (i == 101) {
                int i3 = parcel.readInt();
                HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setDisplayCompatPackages(i3, hashMap, z);
                parcel2.writeNoException();
            } else if (i == 102) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                HashMap hashMap2 = parcel.readHashMap(getClass().getClassLoader());
                parcel.enforceNoDataAvail();
                Map displayCompatPackages = getDisplayCompatPackages(i4, i5, hashMap2);
                parcel2.writeNoException();
                parcel2.writeMap(displayCompatPackages);
            } else if (i == 201) {
                int i6 = parcel.readInt();
                HashMap hashMap3 = parcel.readHashMap(getClass().getClassLoader());
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setFixedAspectRatioPackages(i6, hashMap3, z2);
                parcel2.writeNoException();
            } else if (i == 202) {
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                HashMap hashMap4 = parcel.readHashMap(getClass().getClassLoader());
                parcel.enforceNoDataAvail();
                Map fixedAspectRatioPackages = getFixedAspectRatioPackages(i7, i8, hashMap4);
                parcel2.writeNoException();
                parcel2.writeMap(fixedAspectRatioPackages);
            } else {
                switch (i) {
                    case 301:
                        int i9 = parcel.readInt();
                        boolean z3 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setAllAppContinuityMode(i9, z3);
                        parcel2.writeNoException();
                        break;
                    case 302:
                        boolean z4 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setFrontScreenOnWhenAppContinuityMode(z4);
                        parcel2.writeNoException();
                        break;
                    case 303:
                        boolean z5 = parcel.readBoolean();
                        boolean z6 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        initAppContinuityValueWhenReset(z5, z6);
                        parcel2.writeNoException();
                        break;
                    case 304:
                        String string = parcel.readString();
                        int i10 = parcel.readInt();
                        boolean z7 = parcel.readBoolean();
                        parcel.enforceNoDataAvail();
                        setAppContinuityMode(string, i10, z7);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFoldStarCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void unregisterFoldStarCallback(IFoldStarCallback iFoldStarCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFoldStarCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setDisplayCompatPackages(int i, Map map, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getDisplayCompatPackages(int i, int i2, Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFixedAspectRatioPackages(int i, Map map, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public Map getFixedAspectRatioPackages(int i, int i2, Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAllAppContinuityMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(301, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setFrontScreenOnWhenAppContinuityMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(302, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void initAppContinuityValueWhenReset(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(303, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.core.IFoldStarManager
            public void setAppContinuityMode(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFoldStarManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(304, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
