package com.samsung.android.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.ims.util.SemImsUri;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface SemCapabilityServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.options.SemCapabilityServiceEventListener";

    public static class Default implements SemCapabilityServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onCapabilityAndAvailabilityPublished(int i) throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onMultipleCapabilitiesChanged(List<SemImsUri> list, List<SemCapabilities> list2) throws RemoteException {
        }

        @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
        public void onOwnCapabilitiesChanged() throws RemoteException {
        }
    }

    void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) throws RemoteException;

    void onCapabilityAndAvailabilityPublished(int i) throws RemoteException;

    void onMultipleCapabilitiesChanged(List<SemImsUri> list, List<SemCapabilities> list2) throws RemoteException;

    void onOwnCapabilitiesChanged() throws RemoteException;

    public static abstract class Stub extends Binder implements SemCapabilityServiceEventListener {
        static final int TRANSACTION_onCapabilitiesChanged = 2;
        static final int TRANSACTION_onCapabilityAndAvailabilityPublished = 4;
        static final int TRANSACTION_onMultipleCapabilitiesChanged = 3;
        static final int TRANSACTION_onOwnCapabilitiesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, SemCapabilityServiceEventListener.DESCRIPTOR);
        }

        public static SemCapabilityServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(SemCapabilityServiceEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof SemCapabilityServiceEventListener)) {
                return (SemCapabilityServiceEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onOwnCapabilitiesChanged";
            }
            if (i == 2) {
                return "onCapabilitiesChanged";
            }
            if (i == 3) {
                return "onMultipleCapabilitiesChanged";
            }
            if (i != 4) {
                return null;
            }
            return "onCapabilityAndAvailabilityPublished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemCapabilityServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemCapabilityServiceEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onOwnCapabilitiesChanged();
                parcel2.writeNoException();
            } else if (i == 2) {
                SemImsUri semImsUri = (SemImsUri) parcel.readTypedObject(SemImsUri.CREATOR);
                SemCapabilities semCapabilities = (SemCapabilities) parcel.readTypedObject(SemCapabilities.CREATOR);
                parcel.enforceNoDataAvail();
                onCapabilitiesChanged(semImsUri, semCapabilities);
                parcel2.writeNoException();
            } else if (i == 3) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemImsUri.CREATOR);
                ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(SemCapabilities.CREATOR);
                parcel.enforceNoDataAvail();
                onMultipleCapabilitiesChanged(arrayListCreateTypedArrayList, arrayListCreateTypedArrayList2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCapabilityAndAvailabilityPublished(i3);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements SemCapabilityServiceEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemCapabilityServiceEventListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onOwnCapabilitiesChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onCapabilitiesChanged(SemImsUri semImsUri, SemCapabilities semCapabilities) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(semImsUri, 0);
                    parcelObtain.writeTypedObject(semCapabilities, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onMultipleCapabilitiesChanged(List<SemImsUri> list, List<SemCapabilities> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemCapabilityServiceEventListener
            public void onCapabilityAndAvailabilityPublished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemCapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
