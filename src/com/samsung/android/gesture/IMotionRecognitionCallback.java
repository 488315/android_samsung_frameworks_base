package com.samsung.android.gesture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IMotionRecognitionCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.gesture.IMotionRecognitionCallback";

    public static class Default implements IMotionRecognitionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public String getListenerPackageName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionCallback
        public void motionCallback(SemMotionRecognitionEvent semMotionRecognitionEvent) throws RemoteException {
        }
    }

    String getListenerInfo() throws RemoteException;

    String getListenerPackageName() throws RemoteException;

    void motionCallback(SemMotionRecognitionEvent semMotionRecognitionEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IMotionRecognitionCallback {
        static final int TRANSACTION_getListenerInfo = 2;
        static final int TRANSACTION_getListenerPackageName = 3;
        static final int TRANSACTION_motionCallback = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IMotionRecognitionCallback.DESCRIPTOR);
        }

        public static IMotionRecognitionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMotionRecognitionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMotionRecognitionCallback)) {
                return (IMotionRecognitionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "motionCallback";
            }
            if (i == 2) {
                return "getListenerInfo";
            }
            if (i != 3) {
                return null;
            }
            return "getListenerPackageName";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMotionRecognitionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMotionRecognitionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemMotionRecognitionEvent semMotionRecognitionEvent = (SemMotionRecognitionEvent) parcel.readTypedObject(SemMotionRecognitionEvent.CREATOR);
                parcel.enforceNoDataAvail();
                motionCallback(semMotionRecognitionEvent);
                parcel2.writeNoException();
            } else if (i == 2) {
                String listenerInfo = getListenerInfo();
                parcel2.writeNoException();
                parcel2.writeString(listenerInfo);
            } else if (i == 3) {
                String listenerPackageName = getListenerPackageName();
                parcel2.writeNoException();
                parcel2.writeString(listenerPackageName);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMotionRecognitionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMotionRecognitionCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public void motionCallback(SemMotionRecognitionEvent semMotionRecognitionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(semMotionRecognitionEvent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public String getListenerInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionCallback
            public String getListenerPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMotionRecognitionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
