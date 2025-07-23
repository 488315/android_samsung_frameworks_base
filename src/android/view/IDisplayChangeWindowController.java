package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.IDisplayChangeWindowCallback;
import android.window.DisplayAreaInfo;

/* loaded from: classes4.dex */
public interface IDisplayChangeWindowController extends IInterface {
    public static final String DESCRIPTOR = "android.view.IDisplayChangeWindowController";

    public static class Default implements IDisplayChangeWindowController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDisplayChangeWindowController
        public void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws RemoteException {
        }
    }

    void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDisplayChangeWindowController {
        static final int TRANSACTION_onDisplayChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDisplayChangeWindowController.DESCRIPTOR);
        }

        public static IDisplayChangeWindowController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDisplayChangeWindowController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDisplayChangeWindowController)) {
                return (IDisplayChangeWindowController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDisplayChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDisplayChangeWindowController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDisplayChangeWindowController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                DisplayAreaInfo displayAreaInfo = (DisplayAreaInfo) parcel.readTypedObject(DisplayAreaInfo.CREATOR);
                IDisplayChangeWindowCallback asInterface = IDisplayChangeWindowCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onDisplayChange(readInt, readInt2, readInt3, displayAreaInfo, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDisplayChangeWindowController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayChangeWindowController.DESCRIPTOR;
            }

            @Override // android.view.IDisplayChangeWindowController
            public void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, IDisplayChangeWindowCallback iDisplayChangeWindowCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDisplayChangeWindowController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(displayAreaInfo, 0);
                    obtain.writeStrongInterface(iDisplayChangeWindowCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
