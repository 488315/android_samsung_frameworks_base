package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;

public interface ICallRedirectionAdapter extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionAdapter";

    void cancelCall() throws RemoteException;

    void placeCallUnmodified() throws RemoteException;

    void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z)
            throws RemoteException;

    public static class Default implements ICallRedirectionAdapter {
        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void cancelCall() throws RemoteException {}

        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void placeCallUnmodified() throws RemoteException {}

        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void redirectCall(
                Uri handle, PhoneAccountHandle targetPhoneAccount, boolean confirmFirst)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICallRedirectionAdapter {
        static final int TRANSACTION_cancelCall = 1;
        static final int TRANSACTION_placeCallUnmodified = 2;
        static final int TRANSACTION_redirectCall = 3;

        public Stub() {
            attachInterface(this, ICallRedirectionAdapter.DESCRIPTOR);
        }

        public static ICallRedirectionAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICallRedirectionAdapter.DESCRIPTOR);
            if (iin != null && (iin instanceof ICallRedirectionAdapter)) {
                return (ICallRedirectionAdapter) iin;
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
                    return "cancelCall";
                case 2:
                    return "placeCallUnmodified";
                case 3:
                    return "redirectCall";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICallRedirectionAdapter.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICallRedirectionAdapter.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    cancelCall();
                    return true;
                case 2:
                    placeCallUnmodified();
                    return true;
                case 3:
                    Uri _arg0 = (Uri) data.readTypedObject(Uri.CREATOR);
                    PhoneAccountHandle _arg1 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    redirectCall(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICallRedirectionAdapter {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallRedirectionAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallRedirectionAdapter
            public void cancelCall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionAdapter
            public void placeCallUnmodified() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionAdapter
            public void redirectCall(
                    Uri handle, PhoneAccountHandle targetPhoneAccount, boolean confirmFirst)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    _data.writeTypedObject(handle, 0);
                    _data.writeTypedObject(targetPhoneAccount, 0);
                    _data.writeBoolean(confirmFirst);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
