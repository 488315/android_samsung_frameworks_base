package android.service.smartspace;

import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTargetEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISmartspaceService extends IInterface {
    public static final String DESCRIPTOR = "android.service.smartspace.ISmartspaceService";

    public static class Default implements ISmartspaceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) throws RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
        }

        @Override // android.service.smartspace.ISmartspaceService
        public void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
        }
    }

    void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException;

    void onCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) throws RemoteException;

    void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException;

    void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException;

    void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException;

    void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISmartspaceService {
        static final int TRANSACTION_notifySmartspaceEvent = 2;
        static final int TRANSACTION_onCreateSmartspaceSession = 1;
        static final int TRANSACTION_onDestroySmartspaceSession = 6;
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
            attachInterface(this, ISmartspaceService.DESCRIPTOR);
        }

        public static ISmartspaceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISmartspaceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISmartspaceService)) {
                return (ISmartspaceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreateSmartspaceSession";
                case 2:
                    return "notifySmartspaceEvent";
                case 3:
                    return "requestSmartspaceUpdate";
                case 4:
                    return "registerSmartspaceUpdates";
                case 5:
                    return "unregisterSmartspaceUpdates";
                case 6:
                    return "onDestroySmartspaceSession";
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
                parcel.enforceInterface(ISmartspaceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmartspaceService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SmartspaceConfig smartspaceConfig = (SmartspaceConfig) parcel.readTypedObject(SmartspaceConfig.CREATOR);
                    SmartspaceSessionId smartspaceSessionId = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
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
                    ISmartspaceCallback iSmartspaceCallbackAsInterface = ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSmartspaceUpdates(smartspaceSessionId4, iSmartspaceCallbackAsInterface);
                    return true;
                case 5:
                    SmartspaceSessionId smartspaceSessionId5 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    ISmartspaceCallback iSmartspaceCallbackAsInterface2 = ISmartspaceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSmartspaceUpdates(smartspaceSessionId5, iSmartspaceCallbackAsInterface2);
                    return true;
                case 6:
                    SmartspaceSessionId smartspaceSessionId6 = (SmartspaceSessionId) parcel.readTypedObject(SmartspaceSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroySmartspaceSession(smartspaceSessionId6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISmartspaceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartspaceService.DESCRIPTOR;
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void onCreateSmartspaceSession(SmartspaceConfig smartspaceConfig, SmartspaceSessionId smartspaceSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceConfig, 0);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void notifySmartspaceEvent(SmartspaceSessionId smartspaceSessionId, SmartspaceTargetEvent smartspaceTargetEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    parcelObtain.writeTypedObject(smartspaceTargetEvent, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void requestSmartspaceUpdate(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void registerSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    parcelObtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void unregisterSmartspaceUpdates(SmartspaceSessionId smartspaceSessionId, ISmartspaceCallback iSmartspaceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    parcelObtain.writeStrongInterface(iSmartspaceCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.smartspace.ISmartspaceService
            public void onDestroySmartspaceSession(SmartspaceSessionId smartspaceSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartspaceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(smartspaceSessionId, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
