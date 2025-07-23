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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraServiceListener)) {
                return (ICameraServiceListener) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusChanged(readInt, readString, readInt2);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPhysicalCameraStatusChanged(readInt3, readString2, readString3, readInt4);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTorchStatusChanged(readInt5, readString4, readInt6);
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTorchStrengthLevelChanged(readString5, readInt7, readInt8);
                    return true;
                case 5:
                    onCameraAccessPrioritiesChanged();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraOpened(readString6, readString7, readInt9);
                    return true;
                case 7:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCameraOpenedInSharedMode(readString8, readString9, readInt10, readBoolean);
                    return true;
                case 8:
                    String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraClosed(readString10, readInt11);
                    return true;
                case 9:
                    String readString11 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString12 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraDeviceStateChanged(readString11, readInt12, readInt13, readString12, readInt14, readInt15, readInt16);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStatusChanged(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraAccessPrioritiesChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpened(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpenedInSharedMode(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraClosed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
