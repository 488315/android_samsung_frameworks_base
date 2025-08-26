package android.service.appprediction;

import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.IPredictionCallback;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPredictionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.appprediction.IPredictionService";

    public static class Default implements IPredictionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
        }

        @Override // android.service.appprediction.IPredictionService
        public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
        }
    }

    void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException;

    void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException;

    void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException;

    void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException;

    void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IPredictionService {
        static final int TRANSACTION_notifyAppTargetEvent = 2;
        static final int TRANSACTION_notifyLaunchLocationShown = 3;
        static final int TRANSACTION_onCreatePredictionSession = 1;
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
            attachInterface(this, IPredictionService.DESCRIPTOR);
        }

        public static IPredictionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPredictionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPredictionService)) {
                return (IPredictionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCreatePredictionSession";
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
                parcel.enforceInterface(IPredictionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPredictionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    AppPredictionContext appPredictionContext = (AppPredictionContext) parcel.readTypedObject(AppPredictionContext.CREATOR);
                    AppPredictionSessionId appPredictionSessionId = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreatePredictionSession(appPredictionContext, appPredictionSessionId);
                    return true;
                case 2:
                    AppPredictionSessionId appPredictionSessionId2 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    AppTargetEvent appTargetEvent = (AppTargetEvent) parcel.readTypedObject(AppTargetEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAppTargetEvent(appPredictionSessionId2, appTargetEvent);
                    return true;
                case 3:
                    AppPredictionSessionId appPredictionSessionId3 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    String string = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLaunchLocationShown(appPredictionSessionId3, string, parceledListSlice);
                    return true;
                case 4:
                    AppPredictionSessionId appPredictionSessionId4 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sortAppTargets(appPredictionSessionId4, parceledListSlice2, iPredictionCallbackAsInterface);
                    return true;
                case 5:
                    AppPredictionSessionId appPredictionSessionId5 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface2 = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPredictionUpdates(appPredictionSessionId5, iPredictionCallbackAsInterface2);
                    return true;
                case 6:
                    AppPredictionSessionId appPredictionSessionId6 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IPredictionCallback iPredictionCallbackAsInterface3 = IPredictionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPredictionUpdates(appPredictionSessionId6, iPredictionCallbackAsInterface3);
                    return true;
                case 7:
                    AppPredictionSessionId appPredictionSessionId7 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestPredictionUpdate(appPredictionSessionId7);
                    return true;
                case 8:
                    AppPredictionSessionId appPredictionSessionId8 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyPredictionSession(appPredictionSessionId8);
                    return true;
                case 9:
                    AppPredictionSessionId appPredictionSessionId9 = (AppPredictionSessionId) parcel.readTypedObject(AppPredictionSessionId.CREATOR);
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestServiceFeatures(appPredictionSessionId9, iRemoteCallbackAsInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPredictionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPredictionService.DESCRIPTOR;
            }

            @Override // android.service.appprediction.IPredictionService
            public void onCreatePredictionSession(AppPredictionContext appPredictionContext, AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionContext, 0);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyAppTargetEvent(AppPredictionSessionId appPredictionSessionId, AppTargetEvent appTargetEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeTypedObject(appTargetEvent, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void notifyLaunchLocationShown(AppPredictionSessionId appPredictionSessionId, String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void sortAppTargets(AppPredictionSessionId appPredictionSessionId, ParceledListSlice parceledListSlice, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void registerPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void unregisterPredictionUpdates(AppPredictionSessionId appPredictionSessionId, IPredictionCallback iPredictionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iPredictionCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestPredictionUpdate(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void onDestroyPredictionSession(AppPredictionSessionId appPredictionSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.appprediction.IPredictionService
            public void requestServiceFeatures(AppPredictionSessionId appPredictionSessionId, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPredictionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appPredictionSessionId, 0);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
