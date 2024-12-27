package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.DomainSelectionService;

/* loaded from: classes5.dex */
public interface IDomainSelector extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IDomainSelector";

    void finishSelection() throws RemoteException;

    void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes)
            throws RemoteException;

    public static class Default implements IDomainSelector {
        @Override // com.android.internal.telephony.IDomainSelector
        public void reselectDomain(DomainSelectionService.SelectionAttributes attr)
                throws RemoteException {}

        @Override // com.android.internal.telephony.IDomainSelector
        public void finishSelection() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDomainSelector {
        static final int TRANSACTION_finishSelection = 2;
        static final int TRANSACTION_reselectDomain = 1;

        public Stub() {
            attachInterface(this, IDomainSelector.DESCRIPTOR);
        }

        public static IDomainSelector asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDomainSelector.DESCRIPTOR);
            if (iin != null && (iin instanceof IDomainSelector)) {
                return (IDomainSelector) iin;
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
                    return "reselectDomain";
                case 2:
                    return "finishSelection";
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
                data.enforceInterface(IDomainSelector.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDomainSelector.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    DomainSelectionService.SelectionAttributes _arg0 =
                            (DomainSelectionService.SelectionAttributes)
                                    data.readTypedObject(
                                            DomainSelectionService.SelectionAttributes.CREATOR);
                    data.enforceNoDataAvail();
                    reselectDomain(_arg0);
                    return true;
                case 2:
                    finishSelection();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDomainSelector {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainSelector.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void reselectDomain(DomainSelectionService.SelectionAttributes attr)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDomainSelector.DESCRIPTOR);
                    _data.writeTypedObject(attr, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDomainSelector.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
