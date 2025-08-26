package android.view.contentcapture;

import android.content.ContentCaptureOptions;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IContentCaptureOptionsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureOptionsCallback";

    public static class Default implements IContentCaptureOptionsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.contentcapture.IContentCaptureOptionsCallback
        public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }
    }

    void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentCaptureOptionsCallback {
        static final int TRANSACTION_setContentCaptureOptions = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IContentCaptureOptionsCallback.DESCRIPTOR);
        }

        public static IContentCaptureOptionsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContentCaptureOptionsCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentCaptureOptionsCallback)) {
                return (IContentCaptureOptionsCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setContentCaptureOptions";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentCaptureOptionsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentCaptureOptionsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContentCaptureOptions contentCaptureOptions = (ContentCaptureOptions) parcel.readTypedObject(ContentCaptureOptions.CREATOR);
                parcel.enforceNoDataAvail();
                setContentCaptureOptions(contentCaptureOptions);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IContentCaptureOptionsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentCaptureOptionsCallback.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IContentCaptureOptionsCallback
            public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureOptionsCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contentCaptureOptions, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
