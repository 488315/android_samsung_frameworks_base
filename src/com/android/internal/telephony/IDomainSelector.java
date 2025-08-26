package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.DomainSelectionService;

/* loaded from: classes4.dex */
public interface IDomainSelector extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IDomainSelector";

    public static class Default implements IDomainSelector {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IDomainSelector
        public void finishSelection() throws RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelector
        public void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes) throws RemoteException {
        }
    }

    void finishSelection() throws RemoteException;

    void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes) throws RemoteException;

    public static abstract class Stub extends Binder implements IDomainSelector {
        static final int TRANSACTION_finishSelection = 2;
        static final int TRANSACTION_reselectDomain = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDomainSelector.DESCRIPTOR);
        }

        public static IDomainSelector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDomainSelector.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDomainSelector)) {
                return (IDomainSelector) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "reselectDomain";
            }
            if (i != 2) {
                return null;
            }
            return "finishSelection";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDomainSelector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDomainSelector.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DomainSelectionService.SelectionAttributes selectionAttributes = (DomainSelectionService.SelectionAttributes) parcel.readTypedObject(DomainSelectionService.SelectionAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                reselectDomain(selectionAttributes);
            } else if (i == 2) {
                finishSelection();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDomainSelector {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainSelector.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void reselectDomain(DomainSelectionService.SelectionAttributes selectionAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDomainSelector.DESCRIPTOR);
                    parcelObtain.writeTypedObject(selectionAttributes, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelector
            public void finishSelection() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDomainSelector.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
