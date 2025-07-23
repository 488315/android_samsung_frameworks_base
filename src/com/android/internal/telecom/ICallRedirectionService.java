package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;
import com.android.internal.telecom.ICallRedirectionAdapter;

/* loaded from: classes4.dex */
public interface ICallRedirectionService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionService";

    public static class Default implements ICallRedirectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void notifyTimeout() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallRedirectionService
        public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
        }
    }

    void notifyTimeout() throws RemoteException;

    void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallRedirectionService {
        static final int TRANSACTION_notifyTimeout = 2;
        static final int TRANSACTION_placeCall = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICallRedirectionService.DESCRIPTOR);
        }

        public static ICallRedirectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallRedirectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallRedirectionService)) {
                return (ICallRedirectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "placeCall";
            }
            if (i != 2) {
                return null;
            }
            return "notifyTimeout";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallRedirectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallRedirectionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ICallRedirectionAdapter asInterface = ICallRedirectionAdapter.Stub.asInterface(parcel.readStrongBinder());
                Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                placeCall(asInterface, uri, phoneAccountHandle, readBoolean);
            } else if (i == 2) {
                notifyTimeout();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallRedirectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallRedirectionService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallRedirectionService
            public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallRedirectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCallRedirectionAdapter);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallRedirectionService
            public void notifyTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallRedirectionService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
