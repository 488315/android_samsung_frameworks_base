package com.samsung.android.content.smartclip;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISpenGestureHoverListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.ISpenGestureHoverListener";

    void onBackPressed() throws RemoteException;

    void onHoverEnter() throws RemoteException;

    void onHoverExit() throws RemoteException;

    void onHoverExitTowardBack() throws RemoteException;

    void onHoverStay(int i, int i2) throws RemoteException;

    public static class Default implements ISpenGestureHoverListener {
        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverEnter() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverExit() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverExitTowardBack() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onBackPressed() throws RemoteException {
        }

        @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
        public void onHoverStay(int x, int y) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpenGestureHoverListener {
        static final int TRANSACTION_onBackPressed = 4;
        static final int TRANSACTION_onHoverEnter = 1;
        static final int TRANSACTION_onHoverExit = 2;
        static final int TRANSACTION_onHoverExitTowardBack = 3;
        static final int TRANSACTION_onHoverStay = 5;

        public Stub() {
            attachInterface(this, ISpenGestureHoverListener.DESCRIPTOR);
        }

        public static ISpenGestureHoverListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISpenGestureHoverListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISpenGestureHoverListener)) {
                return (ISpenGestureHoverListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onHoverEnter";
                case 2:
                    return "onHoverExit";
                case 3:
                    return "onHoverExitTowardBack";
                case 4:
                    return "onBackPressed";
                case 5:
                    return "onHoverStay";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISpenGestureHoverListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISpenGestureHoverListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onHoverEnter();
                            return true;
                        case 2:
                            onHoverExit();
                            return true;
                        case 3:
                            onHoverExitTowardBack();
                            return true;
                        case 4:
                            onBackPressed();
                            return true;
                        case 5:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            data.enforceNoDataAvail();
                            onHoverStay(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISpenGestureHoverListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpenGestureHoverListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverEnter() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverExit() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverExitTowardBack() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onBackPressed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.content.smartclip.ISpenGestureHoverListener
            public void onHoverStay(int x, int y) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISpenGestureHoverListener.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
