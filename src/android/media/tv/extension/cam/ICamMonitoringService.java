package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamMonitoringService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamMonitoringService";

    public static class Default implements ICamMonitoringService {
        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public void addCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public Bundle getCamInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public int[] getSlotIds() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public Bundle getSlotInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public boolean isCamSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.tv.extension.cam.ICamMonitoringService
        public void removeCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException {
        }
    }

    void addCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException;

    Bundle getCamInfo(int i) throws RemoteException;

    int[] getSlotIds() throws RemoteException;

    Bundle getSlotInfo(int i) throws RemoteException;

    boolean isCamSupported() throws RemoteException;

    void removeCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamMonitoringService {
        static final int TRANSACTION_addCamInfoListener = 1;
        static final int TRANSACTION_getCamInfo = 3;
        static final int TRANSACTION_getSlotIds = 5;
        static final int TRANSACTION_getSlotInfo = 4;
        static final int TRANSACTION_isCamSupported = 6;
        static final int TRANSACTION_removeCamInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamMonitoringService");
        }

        public static ICamMonitoringService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamMonitoringService");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamMonitoringService)) {
                return (ICamMonitoringService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addCamInfoListener";
                case 2:
                    return "removeCamInfoListener";
                case 3:
                    return "getCamInfo";
                case 4:
                    return "getSlotInfo";
                case 5:
                    return "getSlotIds";
                case 6:
                    return "isCamSupported";
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
                parcel.enforceInterface("android.media.tv.extension.cam.ICamMonitoringService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamMonitoringService");
                return true;
            }
            switch (i) {
                case 1:
                    ICamInfoListener iCamInfoListenerAsInterface = ICamInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCamInfoListener(iCamInfoListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ICamInfoListener iCamInfoListenerAsInterface2 = ICamInfoListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCamInfoListener(iCamInfoListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle camInfo = getCamInfo(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(camInfo, 1);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle slotInfo = getSlotInfo(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(slotInfo, 1);
                    return true;
                case 5:
                    int[] slotIds = getSlotIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(slotIds);
                    return true;
                case 6:
                    boolean zIsCamSupported = isCamSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCamSupported);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICamMonitoringService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamMonitoringService";
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public void addCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    parcelObtain.writeStrongInterface(iCamInfoListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public void removeCamInfoListener(ICamInfoListener iCamInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    parcelObtain.writeStrongInterface(iCamInfoListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public Bundle getCamInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public Bundle getSlotInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public int[] getSlotIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamMonitoringService
            public boolean isCamSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamMonitoringService");
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
