package com.samsung.android.desktopmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDesktopModeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeListener";

    void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) throws RemoteException;

    public static class Default implements IDesktopModeListener {
        @Override // com.samsung.android.desktopmode.IDesktopModeListener
        public void onDesktopModeStateChanged(SemDesktopModeState state) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDesktopModeListener {
        static final int TRANSACTION_onDesktopModeStateChanged = 1;

        public Stub() {
            attachInterface(this, IDesktopModeListener.DESCRIPTOR);
        }

        public static IDesktopModeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDesktopModeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IDesktopModeListener)) {
                return (IDesktopModeListener) iin;
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
                    return "onDesktopModeStateChanged";
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
                data.enforceInterface(IDesktopModeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDesktopModeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SemDesktopModeState _arg0 = (SemDesktopModeState) data.readTypedObject(SemDesktopModeState.CREATOR);
                    data.enforceNoDataAvail();
                    onDesktopModeStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDesktopModeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeListener
            public void onDesktopModeStateChanged(SemDesktopModeState state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDesktopModeListener.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
