package android.media.tv.extension.cam;

import android.media.tv.extension.cam.ICamHostControlAskReleaseReplyCallback;
import android.media.tv.extension.cam.ICamHostControlInfoListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamHostControlService extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamHostControlService";

    public static class Default implements ICamHostControlService {
        @Override // android.media.tv.extension.cam.ICamHostControlService
        public void addCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlService
        public void removeCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.ICamHostControlService
        public int sendCamHostControlAskRelease(String str, ICamHostControlAskReleaseReplyCallback iCamHostControlAskReleaseReplyCallback) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.cam.ICamHostControlService
        public void setHostControlMode(String str, boolean z) throws RemoteException {
        }
    }

    void addCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException;

    void removeCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException;

    int sendCamHostControlAskRelease(String str, ICamHostControlAskReleaseReplyCallback iCamHostControlAskReleaseReplyCallback) throws RemoteException;

    void setHostControlMode(String str, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamHostControlService {
        static final int TRANSACTION_addCamHostcontrolInfoListener = 1;
        static final int TRANSACTION_removeCamHostcontrolInfoListener = 2;
        static final int TRANSACTION_sendCamHostControlAskRelease = 3;
        static final int TRANSACTION_setHostControlMode = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamHostControlService");
        }

        public static ICamHostControlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamHostControlService");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICamHostControlService)) {
                return (ICamHostControlService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addCamHostcontrolInfoListener";
            }
            if (i == 2) {
                return "removeCamHostcontrolInfoListener";
            }
            if (i == 3) {
                return "sendCamHostControlAskRelease";
            }
            if (i != 4) {
                return null;
            }
            return "setHostControlMode";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamHostControlService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamHostControlService");
                return true;
            }
            if (i == 1) {
                ICamHostControlInfoListener iCamHostControlInfoListenerAsInterface = ICamHostControlInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamHostcontrolInfoListener(iCamHostControlInfoListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamHostControlInfoListener iCamHostControlInfoListenerAsInterface2 = ICamHostControlInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamHostcontrolInfoListener(iCamHostControlInfoListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string = parcel.readString();
                ICamHostControlAskReleaseReplyCallback iCamHostControlAskReleaseReplyCallbackAsInterface = ICamHostControlAskReleaseReplyCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iSendCamHostControlAskRelease = sendCamHostControlAskRelease(string, iCamHostControlAskReleaseReplyCallbackAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(iSendCamHostControlAskRelease);
            } else if (i == 4) {
                String string2 = parcel.readString();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setHostControlMode(string2, z);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICamHostControlService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamHostControlService";
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public void addCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    parcelObtain.writeStrongInterface(iCamHostControlInfoListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public void removeCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    parcelObtain.writeStrongInterface(iCamHostControlInfoListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public int sendCamHostControlAskRelease(String str, ICamHostControlAskReleaseReplyCallback iCamHostControlAskReleaseReplyCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iCamHostControlAskReleaseReplyCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public void setHostControlMode(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
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
