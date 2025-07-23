package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telecom.IPhoneAccountSuggestionCallback;

/* loaded from: classes4.dex */
public interface IPhoneAccountSuggestionService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionService";

    public static class Default implements IPhoneAccountSuggestionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
        public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String str) throws RemoteException {
        }
    }

    void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IPhoneAccountSuggestionService {
        static final int TRANSACTION_onAccountSuggestionRequest = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPhoneAccountSuggestionService.DESCRIPTOR);
        }

        public static IPhoneAccountSuggestionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPhoneAccountSuggestionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPhoneAccountSuggestionService)) {
                return (IPhoneAccountSuggestionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAccountSuggestionRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPhoneAccountSuggestionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPhoneAccountSuggestionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IPhoneAccountSuggestionCallback asInterface = IPhoneAccountSuggestionCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onAccountSuggestionRequest(asInterface, readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPhoneAccountSuggestionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPhoneAccountSuggestionService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
            public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPhoneAccountSuggestionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iPhoneAccountSuggestionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
