package android.service.vr;

import android.app.Vr2dDisplayProperties;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.vr.IPersistentVrStateCallbacks;
import android.service.vr.IVrStateCallbacks;

/* loaded from: classes3.dex */
public interface IVrManager extends IInterface {

    public static class Default implements IVrManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.vr.IVrManager
        public boolean getPersistentVrModeEnabled() throws RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public int getVr2dDisplayId() throws RemoteException {
            return 0;
        }

        @Override // android.service.vr.IVrManager
        public boolean getVrModeState() throws RemoteException {
            return false;
        }

        @Override // android.service.vr.IVrManager
        public void registerListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void registerPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setAndBindCompositor(String str) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setPersistentVrModeEnabled(boolean z) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setStandbyEnabled(boolean z) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void unregisterListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException {
        }

        @Override // android.service.vr.IVrManager
        public void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException {
        }
    }

    boolean getPersistentVrModeEnabled() throws RemoteException;

    int getVr2dDisplayId() throws RemoteException;

    boolean getVrModeState() throws RemoteException;

    void registerListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException;

    void registerPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException;

    void setAndBindCompositor(String str) throws RemoteException;

    void setPersistentVrModeEnabled(boolean z) throws RemoteException;

    void setStandbyEnabled(boolean z) throws RemoteException;

    void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) throws RemoteException;

    void unregisterListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException;

    void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException;

    public static abstract class Stub extends Binder implements IVrManager {
        public static final String DESCRIPTOR = "android.service.vr.IVrManager";
        static final int TRANSACTION_getPersistentVrModeEnabled = 6;
        static final int TRANSACTION_getVr2dDisplayId = 9;
        static final int TRANSACTION_getVrModeState = 5;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_registerPersistentVrStateListener = 3;
        static final int TRANSACTION_setAndBindCompositor = 10;
        static final int TRANSACTION_setPersistentVrModeEnabled = 7;
        static final int TRANSACTION_setStandbyEnabled = 11;
        static final int TRANSACTION_setVr2dDisplayProperties = 8;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_unregisterPersistentVrStateListener = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVrManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVrManager)) {
                return (IVrManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerListener";
                case 2:
                    return "unregisterListener";
                case 3:
                    return "registerPersistentVrStateListener";
                case 4:
                    return "unregisterPersistentVrStateListener";
                case 5:
                    return "getVrModeState";
                case 6:
                    return "getPersistentVrModeEnabled";
                case 7:
                    return "setPersistentVrModeEnabled";
                case 8:
                    return "setVr2dDisplayProperties";
                case 9:
                    return "getVr2dDisplayId";
                case 10:
                    return "setAndBindCompositor";
                case 11:
                    return "setStandbyEnabled";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IVrStateCallbacks iVrStateCallbacksAsInterface = IVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(iVrStateCallbacksAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IVrStateCallbacks iVrStateCallbacksAsInterface2 = IVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(iVrStateCallbacksAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IPersistentVrStateCallbacks iPersistentVrStateCallbacksAsInterface = IPersistentVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPersistentVrStateListener(iPersistentVrStateCallbacksAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IPersistentVrStateCallbacks iPersistentVrStateCallbacksAsInterface2 = IPersistentVrStateCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPersistentVrStateListener(iPersistentVrStateCallbacksAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean vrModeState = getVrModeState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(vrModeState);
                    return true;
                case 6:
                    boolean persistentVrModeEnabled = getPersistentVrModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(persistentVrModeEnabled);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPersistentVrModeEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    Vr2dDisplayProperties vr2dDisplayProperties = (Vr2dDisplayProperties) parcel.readTypedObject(Vr2dDisplayProperties.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVr2dDisplayProperties(vr2dDisplayProperties);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int vr2dDisplayId = getVr2dDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(vr2dDisplayId);
                    return true;
                case 10:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAndBindCompositor(string);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setStandbyEnabled(z2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVrManager {
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

            @Override // android.service.vr.IVrManager
            public void registerListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVrStateCallbacks);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterListener(IVrStateCallbacks iVrStateCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVrStateCallbacks);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void registerPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPersistentVrStateCallbacks);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPersistentVrStateCallbacks);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getVrModeState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public boolean getPersistentVrModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setPersistentVrModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(vr2dDisplayProperties, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public int getVr2dDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setAndBindCompositor(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.vr.IVrManager
            public void setStandbyEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
