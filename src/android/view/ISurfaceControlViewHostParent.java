package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.WindowManager;

/* loaded from: classes4.dex */
public interface ISurfaceControlViewHostParent extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISurfaceControlViewHostParent";

    public static class Default implements ISurfaceControlViewHostParent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // android.view.ISurfaceControlViewHostParent
        public void updateParams(WindowManager.LayoutParams[] layoutParamsArr) throws RemoteException {
        }
    }

    void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException;

    void updateParams(WindowManager.LayoutParams[] layoutParamsArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISurfaceControlViewHostParent {
        static final int TRANSACTION_forwardBackKeyToParent = 2;
        static final int TRANSACTION_updateParams = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISurfaceControlViewHostParent.DESCRIPTOR);
        }

        public static ISurfaceControlViewHostParent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISurfaceControlViewHostParent.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISurfaceControlViewHostParent)) {
                return (ISurfaceControlViewHostParent) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "updateParams";
            }
            if (i != 2) {
                return null;
            }
            return "forwardBackKeyToParent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISurfaceControlViewHostParent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISurfaceControlViewHostParent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                WindowManager.LayoutParams[] layoutParamsArr = (WindowManager.LayoutParams[]) parcel.createTypedArray(WindowManager.LayoutParams.CREATOR);
                parcel.enforceNoDataAvail();
                updateParams(layoutParamsArr);
            } else if (i == 2) {
                KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                parcel.enforceNoDataAvail();
                forwardBackKeyToParent(keyEvent);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISurfaceControlViewHostParent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfaceControlViewHostParent.DESCRIPTOR;
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void updateParams(WindowManager.LayoutParams[] layoutParamsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISurfaceControlViewHostParent.DESCRIPTOR);
                    parcelObtain.writeTypedArray(layoutParamsArr, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.ISurfaceControlViewHostParent
            public void forwardBackKeyToParent(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISurfaceControlViewHostParent.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
