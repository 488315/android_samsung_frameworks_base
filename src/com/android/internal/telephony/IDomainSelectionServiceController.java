package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.BarringInfo;
import android.telephony.DomainSelectionService;
import android.telephony.ServiceState;
import com.android.internal.telephony.ITransportSelectorCallback;

/* loaded from: classes4.dex */
public interface IDomainSelectionServiceController extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IDomainSelectionServiceController";

    public static class Default implements IDomainSelectionServiceController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void selectDomain(DomainSelectionService.SelectionAttributes selectionAttributes, ITransportSelectorCallback iTransportSelectorCallback) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateBarringInfo(int i, int i2, BarringInfo barringInfo) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IDomainSelectionServiceController
        public void updateServiceState(int i, int i2, ServiceState serviceState) throws RemoteException {
        }
    }

    void selectDomain(DomainSelectionService.SelectionAttributes selectionAttributes, ITransportSelectorCallback iTransportSelectorCallback) throws RemoteException;

    void updateBarringInfo(int i, int i2, BarringInfo barringInfo) throws RemoteException;

    void updateServiceState(int i, int i2, ServiceState serviceState) throws RemoteException;

    public static abstract class Stub extends Binder implements IDomainSelectionServiceController {
        static final int TRANSACTION_selectDomain = 1;
        static final int TRANSACTION_updateBarringInfo = 3;
        static final int TRANSACTION_updateServiceState = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IDomainSelectionServiceController.DESCRIPTOR);
        }

        public static IDomainSelectionServiceController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDomainSelectionServiceController.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDomainSelectionServiceController)) {
                return (IDomainSelectionServiceController) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "selectDomain";
            }
            if (i == 2) {
                return "updateServiceState";
            }
            if (i != 3) {
                return null;
            }
            return "updateBarringInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDomainSelectionServiceController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDomainSelectionServiceController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                DomainSelectionService.SelectionAttributes selectionAttributes = (DomainSelectionService.SelectionAttributes) parcel.readTypedObject(DomainSelectionService.SelectionAttributes.CREATOR);
                ITransportSelectorCallback iTransportSelectorCallbackAsInterface = ITransportSelectorCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                selectDomain(selectionAttributes, iTransportSelectorCallbackAsInterface);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                ServiceState serviceState = (ServiceState) parcel.readTypedObject(ServiceState.CREATOR);
                parcel.enforceNoDataAvail();
                updateServiceState(i3, i4, serviceState);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                BarringInfo barringInfo = (BarringInfo) parcel.readTypedObject(BarringInfo.CREATOR);
                parcel.enforceNoDataAvail();
                updateBarringInfo(i5, i6, barringInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDomainSelectionServiceController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDomainSelectionServiceController.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void selectDomain(DomainSelectionService.SelectionAttributes selectionAttributes, ITransportSelectorCallback iTransportSelectorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    parcelObtain.writeTypedObject(selectionAttributes, 0);
                    parcelObtain.writeStrongInterface(iTransportSelectorCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateServiceState(int i, int i2, ServiceState serviceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IDomainSelectionServiceController
            public void updateBarringInfo(int i, int i2, BarringInfo barringInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDomainSelectionServiceController.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
