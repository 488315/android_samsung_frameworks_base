package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICamPinStatusListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.ICamPinStatusListener";

    public static class Default implements ICamPinStatusListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.ICamPinStatusListener
        public void onCamPinValidationReply(int i, Bundle bundle) throws RemoteException {
        }
    }

    void onCamPinValidationReply(int i, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ICamPinStatusListener {
        static final int TRANSACTION_onCamPinValidationReply = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.ICamPinStatusListener");
        }

        public static ICamPinStatusListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.ICamPinStatusListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICamPinStatusListener)) {
                return (ICamPinStatusListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCamPinValidationReply";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.ICamPinStatusListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.ICamPinStatusListener");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onCamPinValidationReply(readInt, bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICamPinStatusListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.ICamPinStatusListener";
            }

            @Override // android.media.tv.extension.cam.ICamPinStatusListener
            public void onCamPinValidationReply(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.cam.ICamPinStatusListener");
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
