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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamPinService");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamPinService)) {
                return (ICamPinService) iInterfaceQueryLocalInterface;
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
                ICamPinCapabilityListener iCamPinCapabilityListenerAsInterface = ICamPinCapabilityListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamPinCapabilityListener(iCamPinCapabilityListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamPinCapabilityListener iCamPinCapabilityListenerAsInterface2 = ICamPinCapabilityListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamPinCapabilityListener(iCamPinCapabilityListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                int[] iArrCreateIntArray = parcel.createIntArray();
                ICamPinStatusListener iCamPinStatusListenerAsInterface = ICamPinStatusListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iRequestCamPinValidation = requestCamPinValidation(i3, iArrCreateIntArray, iCamPinStatusListenerAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(iRequestCamPinValidation);
            } else if (i == 4) {
                int i4 = parcel.readInt();
                Bundle bundle = new Bundle();
                parcel.enforceNoDataAvail();
                int camPinCapability = getCamPinCapability(i4, bundle);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    parcelObtain.writeStrongInterface(iCamPinCapabilityListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public void removeCamPinCapabilityListener(ICamPinCapabilityListener iCamPinCapabilityListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    parcelObtain.writeStrongInterface(iCamPinCapabilityListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public int requestCamPinValidation(int i, int[] iArr, ICamPinStatusListener iCamPinStatusListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStrongInterface(iCamPinStatusListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamPinService
            public int getCamPinCapability(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
