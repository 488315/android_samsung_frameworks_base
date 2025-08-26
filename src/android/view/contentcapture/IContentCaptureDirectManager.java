package android.view.contentcapture;

import android.content.ContentCaptureOptions;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IContentCaptureDirectManager extends IInterface {
    public static final String DESCRIPTOR = "android.view.contentcapture.IContentCaptureDirectManager";

    public static class Default implements IContentCaptureDirectManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.contentcapture.IContentCaptureDirectManager
        public void sendEvents(ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }
    }

    void sendEvents(ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentCaptureDirectManager {
        static final int TRANSACTION_sendEvents = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IContentCaptureDirectManager.DESCRIPTOR);
        }

        public static IContentCaptureDirectManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContentCaptureDirectManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContentCaptureDirectManager)) {
                return (IContentCaptureDirectManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "sendEvents";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentCaptureDirectManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentCaptureDirectManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                int i3 = parcel.readInt();
                ContentCaptureOptions contentCaptureOptions = (ContentCaptureOptions) parcel.readTypedObject(ContentCaptureOptions.CREATOR);
                parcel.enforceNoDataAvail();
                sendEvents(parceledListSlice, i3, contentCaptureOptions);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IContentCaptureDirectManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentCaptureDirectManager.DESCRIPTOR;
            }

            @Override // android.view.contentcapture.IContentCaptureDirectManager
            public void sendEvents(ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContentCaptureDirectManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(contentCaptureOptions, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
