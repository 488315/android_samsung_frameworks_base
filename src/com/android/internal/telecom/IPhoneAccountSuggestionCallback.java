package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccountSuggestion;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPhoneAccountSuggestionCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionCallback";

    public static class Default implements IPhoneAccountSuggestionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IPhoneAccountSuggestionCallback
        public void suggestPhoneAccounts(String str, List<PhoneAccountSuggestion> list) throws RemoteException {
        }
    }

    void suggestPhoneAccounts(String str, List<PhoneAccountSuggestion> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IPhoneAccountSuggestionCallback {
        static final int TRANSACTION_suggestPhoneAccounts = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPhoneAccountSuggestionCallback.DESCRIPTOR);
        }

        public static IPhoneAccountSuggestionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPhoneAccountSuggestionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPhoneAccountSuggestionCallback)) {
                return (IPhoneAccountSuggestionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "suggestPhoneAccounts";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPhoneAccountSuggestionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPhoneAccountSuggestionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(PhoneAccountSuggestion.CREATOR);
                parcel.enforceNoDataAvail();
                suggestPhoneAccounts(string, arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPhoneAccountSuggestionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPhoneAccountSuggestionCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IPhoneAccountSuggestionCallback
            public void suggestPhoneAccounts(String str, List<PhoneAccountSuggestion> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPhoneAccountSuggestionCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
