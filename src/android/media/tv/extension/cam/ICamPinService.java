package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamPinCapabilityListener;
import android.media.tv.extension.cam.ICamPinStatusListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamPinService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamPinService";

    public static class Default implements ICamPinService {
        @Override // android.media.tv.extension.cam.ICamPinService
        public void addCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamPinService
        public int getCamPinCapability(int i, Bundle bundle) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.cam.ICamPinService
        public void removeCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.ICamPinService
        public int requestCamPinValidation(int i, int[] iArr, ICamPinStatusListener iCamPinStatusListener) throws RemoteException {
            return 0;
        }
    }

    void addCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException;

    int getCamPinCapability(int i, Bundle bundle) throws RemoteException;

    void removeCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException;

    int requestCamPinValidation(int i, int[] iArr, ICamPinStatusListener iCamPinStatusListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamPinService {
        static final int TRANSACTION_addCamPinCapabilityListener = 1;
        static final int TRANSACTION_getCamPinCapability = 4;
        static final int TRANSACTION_removeCamPinCapabilityListener = 2;
        static final int TRANSACTION_requestCamPinValidation = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamPinService");
        }

        public static ICamPinService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamPinService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamPinService)) {
                return (ICamPinService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addCamPinCapabilityListener";
            }
            if (i == 2) {
                return "removeCamPinCapabilityListener";
            }
            if (i == 3) {
                return "requestCamPinValidation";
            }
            if (i != 4) {
                return null;
            }
            return "getCamPinCapability";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamPinService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamPinService");
                return true;
            }
            if (i == 1) {
                ICamPinCapabilityListener asInterface = ICamPinCapabilityListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamPinCapabilityListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamPinCapabilityListener asInterface2 = ICamPinCapabilityListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamPinCapabilityListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                int[] createIntArray = parcel.createIntArray();
                ICamPinStatusListener asInterface3 = ICamPinStatusListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int requestCamPinValidation = requestCamPinValidation(readInt, createIntArray, asInterface3);
                parcel2.writeNoException();
                parcel2.writeInt(requestCamPinValidation);
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                Bundle bundle = new Bundle();
                parcel.enforceNoDataAvail();
                int camPinCapability = getCamPinCapability(readInt2, bundle);
                parcel2.writeNoException();
                parcel2.writeInt(camPinCapability);
                parcel2.writeTypedObject(bundle, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamPinService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamPinService";
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public void addCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    obtain.writeStrongInterface(iCamPinCapabilityListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public void removeCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    obtain.writeStrongInterface(iCamPinCapabilityListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public int requestCamPinValidation(int i, int[] iArr, ICamPinStatusListener iCamPinStatusListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iCamPinStatusListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public int getCamPinCapability(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
