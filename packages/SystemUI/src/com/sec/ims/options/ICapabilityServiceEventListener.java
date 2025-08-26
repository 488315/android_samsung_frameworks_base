package com.sec.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.util.ImsUri;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICapabilityServiceEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.options.ICapabilityServiceEventListener";

    void onCapabilitiesChanged(List<ImsUri> list, Capabilities capabilities) throws RemoteException;

    void onCapabilityAndAvailabilityPublished(int i) throws RemoteException;

    void onMultipleCapabilitiesChanged(List<ImsUri> list, List<Capabilities> list2) throws RemoteException;

    void onOwnCapabilitiesChanged() throws RemoteException;

    public class Default implements ICapabilityServiceEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityServiceEventListener
        public void onOwnCapabilitiesChanged() throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityServiceEventListener
        public void onCapabilityAndAvailabilityPublished(int i) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityServiceEventListener
        public void onCapabilitiesChanged(List<ImsUri> list, Capabilities capabilities) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityServiceEventListener
        public void onMultipleCapabilitiesChanged(List<ImsUri> list, List<Capabilities> list2) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements ICapabilityServiceEventListener {
        static final int TRANSACTION_onCapabilitiesChanged = 2;
        static final int TRANSACTION_onCapabilityAndAvailabilityPublished = 4;
        static final int TRANSACTION_onMultipleCapabilitiesChanged = 3;
        static final int TRANSACTION_onOwnCapabilitiesChanged = 1;

        class Proxy implements ICapabilityServiceEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICapabilityServiceEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.options.ICapabilityServiceEventListener
            public void onCapabilitiesChanged(List<ImsUri> list, Capabilities capabilities) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(capabilities, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityServiceEventListener
            public void onCapabilityAndAvailabilityPublished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityServiceEventListener
            public void onMultipleCapabilitiesChanged(List<ImsUri> list, List<Capabilities> list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityServiceEventListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityServiceEventListener
            public void onOwnCapabilitiesChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityServiceEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICapabilityServiceEventListener.DESCRIPTOR);
        }

        public static ICapabilityServiceEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICapabilityServiceEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICapabilityServiceEventListener)) ? new Proxy(iBinder) : (ICapabilityServiceEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICapabilityServiceEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICapabilityServiceEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onOwnCapabilitiesChanged();
                parcel2.writeNoException();
            } else if (i == 2) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ImsUri.CREATOR);
                Capabilities capabilities = (Capabilities) parcel.readTypedObject(Capabilities.CREATOR);
                parcel.enforceNoDataAvail();
                onCapabilitiesChanged(arrayListCreateTypedArrayList, capabilities);
                parcel2.writeNoException();
            } else if (i == 3) {
                ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(ImsUri.CREATOR);
                ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(Capabilities.CREATOR);
                parcel.enforceNoDataAvail();
                onMultipleCapabilitiesChanged(arrayListCreateTypedArrayList2, arrayListCreateTypedArrayList3);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCapabilityAndAvailabilityPublished(i3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
