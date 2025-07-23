package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;

/* loaded from: classes4.dex */
public interface ICallRedirectionAdapter extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionAdapter";

    public static class Default implements ICallRedirectionAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void cancelCall() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void placeCallUnmodified() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallRedirectionAdapter
        public void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
        }
    }

    void cancelCall() throws RemoteException;

    void placeCallUnmodified() throws RemoteException;

    void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallRedirectionAdapter {
        static final int TRANSACTION_cancelCall = 1;
        static final int TRANSACTION_placeCallUnmodified = 2;
        static final int TRANSACTION_redirectCall = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICallRedirectionAdapter.DESCRIPTOR);
        }

        public static ICallRedirectionAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallRedirectionAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallRedirectionAdapter)) {
                return (ICallRedirectionAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "cancelCall";
            }
            if (i == 2) {
                return "placeCallUnmodified";
            }
            if (i != 3) {
                return null;
            }
            return "redirectCall";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallRedirectionAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallRedirectionAdapter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                cancelCall();
            } else if (i == 2) {
                placeCallUnmodified();
            } else if (i == 3) {
                Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                redirectCall(uri, phoneAccountHandle, readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallRedirectionAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionAdapter
            public void placeCallUnmodified() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionAdapter
            public void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallRedirectionAdapter.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
