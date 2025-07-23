package android.view;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ISurfaceControlViewHostParent;
import android.window.ISurfaceSyncGroup;

/* loaded from: classes4.dex */
public interface ISurfaceControlViewHost extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISurfaceControlViewHost";

    public static class Default implements ISurfaceControlViewHost {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.ISurfaceControlViewHost
        public void attachParentInterface(ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public ISurfaceSyncGroup getSurfaceSyncGroup() throws RemoteException {
            return null;
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onConfigurationChanged(Configuration configuration) throws RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onDispatchDetachedFromWindow() throws RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onInsetsChanged(InsetsState insetsState, Rect rect) throws RemoteException {
        }
    }

    void attachParentInterface(ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws RemoteException;

    ISurfaceSyncGroup getSurfaceSyncGroup() throws RemoteException;

    void onConfigurationChanged(Configuration configuration) throws RemoteException;

    void onDispatchDetachedFromWindow() throws RemoteException;

    void onInsetsChanged(InsetsState insetsState, Rect rect) throws RemoteException;

    public static abstract class Stub extends Binder implements ISurfaceControlViewHost {
        static final int TRANSACTION_attachParentInterface = 5;
        static final int TRANSACTION_getSurfaceSyncGroup = 4;
        static final int TRANSACTION_onConfigurationChanged = 1;
        static final int TRANSACTION_onDispatchDetachedFromWindow = 2;
        static final int TRANSACTION_onInsetsChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISurfaceControlViewHost.DESCRIPTOR);
        }

        public static ISurfaceControlViewHost asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISurfaceControlViewHost.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISurfaceControlViewHost)) {
                return (ISurfaceControlViewHost) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConfigurationChanged";
            }
            if (i == 2) {
                return "onDispatchDetachedFromWindow";
            }
            if (i == 3) {
                return "onInsetsChanged";
            }
            if (i == 4) {
                return "getSurfaceSyncGroup";
            }
            if (i != 5) {
                return null;
            }
            return "attachParentInterface";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISurfaceControlViewHost.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISurfaceControlViewHost.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                parcel.enforceNoDataAvail();
                onConfigurationChanged(configuration);
            } else if (i == 2) {
                onDispatchDetachedFromWindow();
            } else if (i == 3) {
                InsetsState insetsState = (InsetsState) parcel.readTypedObject(InsetsState.CREATOR);
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                parcel.enforceNoDataAvail();
                onInsetsChanged(insetsState, rect);
            } else if (i == 4) {
                ISurfaceSyncGroup surfaceSyncGroup = getSurfaceSyncGroup();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(surfaceSyncGroup);
            } else if (i == 5) {
                ISurfaceControlViewHostParent asInterface = ISurfaceControlViewHostParent.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                attachParentInterface(asInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISurfaceControlViewHost {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfaceControlViewHost.DESCRIPTOR;
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onConfigurationChanged(Configuration configuration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onDispatchDetachedFromWindow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISurfaceControlViewHost.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void onInsetsChanged(InsetsState insetsState, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeTypedObject(insetsState, 0);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public ISurfaceSyncGroup getSurfaceSyncGroup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISurfaceControlViewHost.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISurfaceSyncGroup.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHost
            public void attachParentInterface(ISurfaceControlViewHostParent iSurfaceControlViewHostParent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISurfaceControlViewHost.DESCRIPTOR);
                    obtain.writeStrongInterface(iSurfaceControlViewHostParent);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
