package com.samsung.android.knox.zt.usertrust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MotionEvent;

/* loaded from: classes6.dex */
public interface IAuthTouchEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener";

    public static class Default implements IAuthTouchEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener
        public void onPointerEvent(MotionEvent motionEvent) throws RemoteException {
        }
    }

    void onPointerEvent(MotionEvent motionEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IAuthTouchEventListener {
        static final int TRANSACTION_onPointerEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAuthTouchEventListener.DESCRIPTOR);
        }

        public static IAuthTouchEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAuthTouchEventListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAuthTouchEventListener)) {
                return (IAuthTouchEventListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPointerEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAuthTouchEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAuthTouchEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onPointerEvent(motionEvent);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAuthTouchEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthTouchEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener
            public void onPointerEvent(MotionEvent motionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAuthTouchEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
