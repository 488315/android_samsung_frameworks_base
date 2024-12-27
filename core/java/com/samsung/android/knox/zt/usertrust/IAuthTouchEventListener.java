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

    void onPointerEvent(MotionEvent motionEvent) throws RemoteException;

    public static class Default implements IAuthTouchEventListener {
        @Override // com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener
        public void onPointerEvent(MotionEvent event) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAuthTouchEventListener {
        static final int TRANSACTION_onPointerEvent = 1;

        public Stub() {
            attachInterface(this, IAuthTouchEventListener.DESCRIPTOR);
        }

        public static IAuthTouchEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAuthTouchEventListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IAuthTouchEventListener)) {
                return (IAuthTouchEventListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onPointerEvent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IAuthTouchEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAuthTouchEventListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    MotionEvent _arg0 = (MotionEvent) data.readTypedObject(MotionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onPointerEvent(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAuthTouchEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAuthTouchEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener
            public void onPointerEvent(MotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAuthTouchEventListener.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
