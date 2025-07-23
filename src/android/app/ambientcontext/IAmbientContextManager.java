package android.app.ambientcontext;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.app.ambientcontext.IAmbientContextObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAmbientContextManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.ambientcontext.IAmbientContextManager";

    public static class Default implements IAmbientContextManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserver(AmbientContextEventRequest ambientContextEventRequest, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void registerObserverWithCallback(AmbientContextEventRequest ambientContextEventRequest, String str, IAmbientContextObserver iAmbientContextObserver) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void startConsentActivity(int[] iArr, String str) throws RemoteException {
        }

        @Override // android.app.ambientcontext.IAmbientContextManager
        public void unregisterObserver(String str) throws RemoteException {
        }
    }

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException;

    void registerObserver(AmbientContextEventRequest ambientContextEventRequest, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException;

    void registerObserverWithCallback(AmbientContextEventRequest ambientContextEventRequest, String str, IAmbientContextObserver iAmbientContextObserver) throws RemoteException;

    void startConsentActivity(int[] iArr, String str) throws RemoteException;

    void unregisterObserver(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAmbientContextManager {
        static final int TRANSACTION_queryServiceStatus = 4;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_registerObserverWithCallback = 2;
        static final int TRANSACTION_startConsentActivity = 5;
        static final int TRANSACTION_unregisterObserver = 3;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IAmbientContextManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAmbientContextManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAmbientContextManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAmbientContextManager)) {
                return (IAmbientContextManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerObserver";
            }
            if (i == 2) {
                return "registerObserverWithCallback";
            }
            if (i == 3) {
                return "unregisterObserver";
            }
            if (i == 4) {
                return "queryServiceStatus";
            }
            if (i != 5) {
                return null;
            }
            return "startConsentActivity";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAmbientContextManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAmbientContextManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AmbientContextEventRequest ambientContextEventRequest = (AmbientContextEventRequest) parcel.readTypedObject(AmbientContextEventRequest.CREATOR);
                PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                registerObserver(ambientContextEventRequest, pendingIntent, remoteCallback);
                parcel2.writeNoException();
            } else if (i == 2) {
                AmbientContextEventRequest ambientContextEventRequest2 = (AmbientContextEventRequest) parcel.readTypedObject(AmbientContextEventRequest.CREATOR);
                String readString = parcel.readString();
                IAmbientContextObserver asInterface = IAmbientContextObserver.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerObserverWithCallback(ambientContextEventRequest2, readString, asInterface);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                unregisterObserver(readString2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int[] createIntArray = parcel.createIntArray();
                String readString3 = parcel.readString();
                RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                queryServiceStatus(createIntArray, readString3, remoteCallback2);
                parcel2.writeNoException();
            } else if (i == 5) {
                int[] createIntArray2 = parcel.createIntArray();
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                startConsentActivity(createIntArray2, readString4);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAmbientContextManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAmbientContextManager.DESCRIPTOR;
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserver(AmbientContextEventRequest ambientContextEventRequest, PendingIntent pendingIntent, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void registerObserverWithCallback(AmbientContextEventRequest ambientContextEventRequest, String str, IAmbientContextObserver iAmbientContextObserver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    obtain.writeTypedObject(ambientContextEventRequest, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iAmbientContextObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void unregisterObserver(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.ambientcontext.IAmbientContextManager
            public void startConsentActivity(int[] iArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAmbientContextManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void unregisterObserver_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_AMBIENT_CONTEXT_EVENT, getCallingPid(), getCallingUid());
        }
    }
}
