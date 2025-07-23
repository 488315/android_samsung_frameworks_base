package android.app.smartspace;

import android.app.smartspace.ISmartspaceCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISmartspaceManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.smartspace.ISmartspaceManager";

    public static class Default implements ISmartspaceManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void createSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void destroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException {
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
        }

        @Override // android.app.smartspace.ISmartspaceManager
        public void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
        }
    }

    void createSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId, IBinder iBinder) throws RemoteException;

    void destroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException;

    void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException;

    void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException;

    void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException;

    void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISmartspaceManager {
        static final int TRANSACTION_createSmartspaceSession = 1;
        static final int TRANSACTION_destroySmartspaceSession = 6;
        static final int TRANSACTION_notifySmartspaceEvent = 2;
        static final int TRANSACTION_registerSmartspaceUpdates = 4;
        static final int TRANSACTION_requestSmartspaceUpdate = 3;
        static final int TRANSACTION_unregisterSmartspaceUpdates = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISmartspaceManager.DESCRIPTOR);
        }

        public static ISmartspaceManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISmartspaceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISmartspaceManager)) {
                return (ISmartspaceManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSmartspaceSession";
                case 2:
                    return "notifySmartspaceEvent";
                case 3:
                    return "requestSmartspaceUpdate";
                case 4:
                    return "registerSmartspaceUpdates";
                case 5:
                    return "unregisterSmartspaceUpdates";
                case 6:
                    return "destroySmartspaceSession";
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
                parcel.enforceInterface(ISmartspaceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmartspaceManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SmartspaceConfig smartspaceConfig = (SmartspaceConfig) parcel.readTypedObject(SmartspaceConfig.CREATOR);
                    SmartspaceSessionId smartspaceSessionId = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createSmartspaceSession(smartspaceConfig, smartspaceSessionId, readStrongBinder);
                    return true;
                case 2:
                    SmartspaceSessionId smartspaceSessionId2 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    SmartspaceTargetEvent smartspaceTargetEvent = (SmartspaceTargetEvent) parcel.readTypedObject(SmartspaceTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySmartspaceEvent(smartspaceSessionId2, smartspaceTargetEvent);
                    return true;
                case 3:
                    SmartspaceSessionId smartspaceSessionId3 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestSmartspaceUpdate(smartspaceSessionId3);
                    return true;
                case 4:
                    SmartspaceSessionId smartspaceSessionId4 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    ISmartspaceCallback asInterface = ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSmartspaceUpdates(smartspaceSessionId4, asInterface);
                    return true;
                case 5:
                    SmartspaceSessionId smartspaceSessionId5 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    ISmartspaceCallback asInterface2 = ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSmartspaceUpdates(smartspaceSessionId5, asInterface2);
                    return true;
                case 6:
                    SmartspaceSessionId smartspaceSessionId6 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    destroySmartspaceSession(smartspaceSessionId6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISmartspaceManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartspaceManager.DESCRIPTOR;
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void createSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceConfig, 0);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeTypedObject(smartspaceTargetEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    obtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.smartspace.ISmartspaceManager
            public void destroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartspaceManager.DESCRIPTOR);
                    obtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
