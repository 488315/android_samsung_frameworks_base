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
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamHostControlService");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamHostControlService)) {
                return (ICamHostControlService) queryLocalInterface;
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
                ICamHostControlInfoListener asInterface = ICamHostControlInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addCamHostcontrolInfoListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                ICamHostControlInfoListener asInterface2 = ICamHostControlInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeCamHostcontrolInfoListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString = parcel.readString();
                ICamHostControlAskReleaseReplyCallback asInterface3 = ICamHostControlAskReleaseReplyCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int sendCamHostControlAskRelease = sendCamHostControlAskRelease(readString, asInterface3);
                parcel2.writeNoException();
                parcel2.writeInt(sendCamHostControlAskRelease);
            } else if (i == 4) {
                String readString2 = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setHostControlMode(readString2, readBoolean);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    obtain.writeStrongInterface(iCamHostControlInfoListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public void removeCamHostcontrolInfoListener(ICamHostControlInfoListener iCamHostControlInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    obtain.writeStrongInterface(iCamHostControlInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public int sendCamHostControlAskRelease(String str, ICamHostControlAskReleaseReplyCallback iCamHostControlAskReleaseReplyCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iCamHostControlAskReleaseReplyCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.ICamHostControlService
            public void setHostControlMode(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamHostControlService");
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
