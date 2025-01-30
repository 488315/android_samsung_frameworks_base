package com.samsung.android.desktopmode;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IDesktopModeLauncher extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.desktopmode.IDesktopModeLauncher";

    Bundle sendMessage(Bundle bundle) throws RemoteException;

    public static class Default implements IDesktopModeLauncher {
        @Override // com.samsung.android.desktopmode.IDesktopModeLauncher
        public Bundle sendMessage(Bundle message) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDesktopModeLauncher {
        static final int TRANSACTION_sendMessage = 1;

        public Stub() {
            attachInterface(this, IDesktopModeLauncher.DESCRIPTOR);
        }

        public static IDesktopModeLauncher asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDesktopModeLauncher.DESCRIPTOR);
            if (iin != null && (iin instanceof IDesktopModeLauncher)) {
                return (IDesktopModeLauncher) iin;
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
                    return "sendMessage";
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
                data.enforceInterface(IDesktopModeLauncher.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IDesktopModeLauncher.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            Bundle _result = sendMessage(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result, 1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IDesktopModeLauncher {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDesktopModeLauncher.DESCRIPTOR;
            }

            @Override // com.samsung.android.desktopmode.IDesktopModeLauncher
            public Bundle sendMessage(Bundle message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDesktopModeLauncher.DESCRIPTOR);
                    _data.writeTypedObject(message, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
