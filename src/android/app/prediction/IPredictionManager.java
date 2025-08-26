package android.app.prediction;

import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPredictionManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.prediction.IPredictionManager";

    public static class Default implements IPredictionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.prediction.IPredictionManager
        public void createPredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override // android.app.prediction.IPredictionManager
        public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }
    }

    void createPredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId, IBinder iBinder) throws RemoteException;

    void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException;

    void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException;

    void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException;

    void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IPredictionManager {
        static final int TRANSACTION_createPredictionSession = 1;
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onDestroyPredictionSession = 8;
        static final int TRANSACTION_registerPredictionUpdates = 5;
        static final int TRANSACTION_requestPredictionUpdate = 7;
        static final int TRANSACTION_requestServiceFeatures = 9;
        static final int TRANSACTION_sortAppTargets = 4;
        static final int TRANSACTION_unregisterPredictionUpdates = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IPredictionManager.DESCRIPTOR);
        }

        public static IPredictionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPredictionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPredictionManager)) {
                return (IPredictionManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createPredictionSession";
                case 2:
                    return "notifyAppTargetEvent";
                case 3:
                    return "notifyLaunchLocationShown";
                case 4:
                    return "sortAppTargets";
                case 5:
                    return "registerPredictionUpdates";
                case 6:
                    return "unregisterPredictionUpdates";
                case 7:
                    return "requestPredictionUpdate";
                case 8:
                    return "onDestroyPredictionSession";
                case 9:
                    return "requestServiceFeatures";
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
                parcel.enforceInterface(IPredictionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPredictionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AppPredictionContext appPredictionContext = (AppPredictionContext) parcel.readTypedObject(AppPredictionContext.CREATOR);
                    AppPredictionSessionId appPredictionSessionId = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createPredictionSession(appPredictionContext, appPredictionSessionId, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    AppPredictionSessionId appPredictionSessionId2 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    AppTargetEvent appTargetEvent = (AppTargetEvent) parcel.readTypedObject(AppTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAppTargetEvent(appPredictionSessionId2, appTargetEvent);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    AppPredictionSessionId appPredictionSessionId3 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    String string = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLaunchLocationShown(appPredictionSessionId3, string, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    AppPredictionSessionId appPredictionSessionId4 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sortAppTargets(appPredictionSessionId4, parceledListSlice2, iPredictionCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    AppPredictionSessionId appPredictionSessionId5 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface2 = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPredictionUpdates(appPredictionSessionId5, iPredictionCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    AppPredictionSessionId appPredictionSessionId6 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface3 = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPredictionUpdates(appPredictionSessionId6, iPredictionCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    AppPredictionSessionId appPredictionSessionId7 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPredictionUpdate(appPredictionSessionId7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    AppPredictionSessionId appPredictionSessionId8 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyPredictionSession(appPredictionSessionId8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    AppPredictionSessionId appPredictionSessionId9 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestServiceFeatures(appPredictionSessionId9, iRemoteCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPredictionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPredictionManager.DESCRIPTOR;
            }

            @Override // android.app.prediction.IPredictionManager
            public void createPredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionContext, 0);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeTypedObject(appTargetEvent, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.prediction.IPredictionManager
            public void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPredictionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
