package android.service.displayhash;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.view.displayhash.DisplayHash;

/* loaded from: classes3.dex */
public interface IDisplayHashingService extends IInterface {
    public static final String DESCRIPTOR = "android.service.displayhash.IDisplayHashingService";

    public static class Default implements IDisplayHashingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void generateDisplayHash(byte[] bArr, HardwareBuffer hardwareBuffer, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getDisplayHashAlgorithms(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void getIntervalBetweenRequestsMillis(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.displayhash.IDisplayHashingService
        public void verifyDisplayHash(byte[] bArr, DisplayHash displayHash, RemoteCallback remoteCallback) throws RemoteException {
        }
    }

    void generateDisplayHash(byte[] bArr, HardwareBuffer hardwareBuffer, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException;

    void getDisplayHashAlgorithms(RemoteCallback remoteCallback) throws RemoteException;

    void getIntervalBetweenRequestsMillis(RemoteCallback remoteCallback) throws RemoteException;

    void verifyDisplayHash(byte[] bArr, DisplayHash displayHash, RemoteCallback remoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayHashingService {
        static final int TRANSACTION_generateDisplayHash = 1;
        static final int TRANSACTION_getDisplayHashAlgorithms = 3;
        static final int TRANSACTION_getIntervalBetweenRequestsMillis = 4;
        static final int TRANSACTION_verifyDisplayHash = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IDisplayHashingService.DESCRIPTOR);
        }

        public static IDisplayHashingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplayHashingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayHashingService)) {
                return (IDisplayHashingService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "generateDisplayHash";
            }
            if (i == 2) {
                return "verifyDisplayHash";
            }
            if (i == 3) {
                return "getDisplayHashAlgorithms";
            }
            if (i != 4) {
                return null;
            }
            return "getIntervalBetweenRequestsMillis";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayHashingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayHashingService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] createByteArray = parcel.createByteArray();
                HardwareBuffer hardwareBuffer = (HardwareBuffer) parcel.readTypedObject(HardwareBuffer.CREATOR);
                Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                String readString = parcel.readString();
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                generateDisplayHash(createByteArray, hardwareBuffer, rect, readString, remoteCallback);
            } else if (i == 2) {
                byte[] createByteArray2 = parcel.createByteArray();
                DisplayHash displayHash = (DisplayHash) parcel.readTypedObject(DisplayHash.CREATOR);
                RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                verifyDisplayHash(createByteArray2, displayHash, remoteCallback2);
            } else if (i == 3) {
                RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                getDisplayHashAlgorithms(remoteCallback3);
            } else if (i == 4) {
                RemoteCallback remoteCallback4 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                getIntervalBetweenRequestsMillis(remoteCallback4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDisplayHashingService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayHashingService.DESCRIPTOR;
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void generateDisplayHash(byte[] bArr, HardwareBuffer hardwareBuffer, Rect rect, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayHashingService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(hardwareBuffer, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void verifyDisplayHash(byte[] bArr, DisplayHash displayHash, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayHashingService.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(displayHash, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void getDisplayHashAlgorithms(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayHashingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.displayhash.IDisplayHashingService
            public void getIntervalBetweenRequestsMillis(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayHashingService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
