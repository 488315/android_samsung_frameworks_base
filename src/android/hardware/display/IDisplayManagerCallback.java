package android.hardware.display;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface IDisplayManagerCallback extends IInterface {

    public static class Default implements IDisplayManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDeviceEvent(Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayEvent(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayVolumeEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onDisplayVolumeKeyEvent(int i) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onTopologyChanged(DisplayTopology displayTopology) throws RemoteException {
        }

        @Override // android.hardware.display.IDisplayManagerCallback
        public void onWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) throws RemoteException {
        }
    }

    void onDeviceEvent(Bundle bundle, int i) throws RemoteException;

    void onDisplayEvent(int i, int i2) throws RemoteException;

    void onDisplayVolumeEvent(int i, Bundle bundle) throws RemoteException;

    void onDisplayVolumeKeyEvent(int i) throws RemoteException;

    void onTopologyChanged(DisplayTopology displayTopology) throws RemoteException;

    void onWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayManagerCallback {
        public static final String DESCRIPTOR = "android.hardware.display.IDisplayManagerCallback";
        static final int TRANSACTION_onDeviceEvent = 6;
        static final int TRANSACTION_onDisplayEvent = 1;
        static final int TRANSACTION_onDisplayVolumeEvent = 3;
        static final int TRANSACTION_onDisplayVolumeKeyEvent = 4;
        static final int TRANSACTION_onTopologyChanged = 2;
        static final int TRANSACTION_onWifiDisplayParameterEvent = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDisplayManagerCallback)) {
                return (IDisplayManagerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDisplayEvent";
                case 2:
                    return "onTopologyChanged";
                case 3:
                    return "onDisplayVolumeEvent";
                case 4:
                    return "onDisplayVolumeKeyEvent";
                case 5:
                    return "onWifiDisplayParameterEvent";
                case 6:
                    return "onDeviceEvent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayEvent(i3, i4);
                    return true;
                case 2:
                    DisplayTopology displayTopology = (DisplayTopology) parcel.readTypedObject(DisplayTopology.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTopologyChanged(displayTopology);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayVolumeEvent(i5, bundle);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayVolumeKeyEvent(i6);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemWifiDisplayParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    onWifiDisplayParameterEvent(i7, arrayListCreateTypedArrayList);
                    return true;
                case 6:
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeviceEvent(bundle2, i8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDisplayManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onDisplayEvent(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onTopologyChanged(DisplayTopology displayTopology) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(displayTopology, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onDisplayVolumeEvent(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onDisplayVolumeKeyEvent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onWifiDisplayParameterEvent(int i, List<SemWifiDisplayParameter> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.display.IDisplayManagerCallback
            public void onDeviceEvent(Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
