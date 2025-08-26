package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICameraServiceListener extends IInterface {
    public static final int STATUS_ENUMERATING = 2;
    public static final int STATUS_NOT_AVAILABLE = -2;
    public static final int STATUS_NOT_PRESENT = 0;
    public static final int STATUS_PRESENT = 1;
    public static final int STATUS_UNKNOWN = -1;
    public static final int TORCH_STATUS_AVAILABLE_OFF = 1;
    public static final int TORCH_STATUS_AVAILABLE_ON = 2;
    public static final int TORCH_STATUS_NOT_AVAILABLE = 0;
    public static final int TORCH_STATUS_UNKNOWN = -1;

    public static class Default implements ICameraServiceListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraAccessPrioritiesChanged() throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraClosed(String str, int i) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpened(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpenedInSharedMode(String str, String str2, int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onStatusChanged(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStatusChanged(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException {
        }
    }

    void onCameraAccessPrioritiesChanged() throws RemoteException;

    void onCameraClosed(String str, int i) throws RemoteException;

    void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5) throws RemoteException;

    void onCameraOpened(String str, String str2, int i) throws RemoteException;

    void onCameraOpenedInSharedMode(String str, String str2, int i, boolean z) throws RemoteException;

    void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2) throws RemoteException;

    void onStatusChanged(int i, String str, int i2) throws RemoteException;

    void onTorchStatusChanged(int i, String str, int i2) throws RemoteException;

    void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraServiceListener {
        public static final String DESCRIPTOR = "android.hardware.ICameraServiceListener";
        static final int TRANSACTION_onCameraAccessPrioritiesChanged = 5;
        static final int TRANSACTION_onCameraClosed = 8;
        static final int TRANSACTION_onCameraDeviceStateChanged = 9;
        static final int TRANSACTION_onCameraOpened = 6;
        static final int TRANSACTION_onCameraOpenedInSharedMode = 7;
        static final int TRANSACTION_onPhysicalCameraStatusChanged = 2;
        static final int TRANSACTION_onStatusChanged = 1;
        static final int TRANSACTION_onTorchStatusChanged = 3;
        static final int TRANSACTION_onTorchStrengthLevelChanged = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraServiceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICameraServiceListener)) {
                return (ICameraServiceListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStatusChanged";
                case 2:
                    return "onPhysicalCameraStatusChanged";
                case 3:
                    return "onTorchStatusChanged";
                case 4:
                    return "onTorchStrengthLevelChanged";
                case 5:
                    return "onCameraAccessPrioritiesChanged";
                case 6:
                    return "onCameraOpened";
                case 7:
                    return "onCameraOpenedInSharedMode";
                case 8:
                    return "onCameraClosed";
                case 9:
                    return "onCameraDeviceStateChanged";
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
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusChanged(i3, string, i4);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPhysicalCameraStatusChanged(i5, string2, string3, i6);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    String string4 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTorchStatusChanged(i7, string4, i8);
                    return true;
                case 4:
                    String string5 = parcel.readString();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTorchStrengthLevelChanged(string5, i9, i10);
                    return true;
                case 5:
                    onCameraAccessPrioritiesChanged();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraOpened(string6, string7, i11);
                    return true;
                case 7:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i12 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraOpenedInSharedMode(string8, string9, i12, z);
                    return true;
                case 8:
                    String string10 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraClosed(string10, i13);
                    return true;
                case 9:
                    String string11 = parcel.readString();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string12 = parcel.readString();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraDeviceStateChanged(string11, i14, i15, string12, i16, i17, i18);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraServiceListener {
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

            @Override // android.hardware.ICameraServiceListener
            public void onStatusChanged(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStatusChanged(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraAccessPrioritiesChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpened(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpenedInSharedMode(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraClosed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
