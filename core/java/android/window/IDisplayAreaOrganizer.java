package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public interface IDisplayAreaOrganizer extends IInterface {
    public static final String DESCRIPTOR = "android.window.IDisplayAreaOrganizer";

    void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl)
            throws RemoteException;

    void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) throws RemoteException;

    void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) throws RemoteException;

    public static class Default implements IDisplayAreaOrganizer {
        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl leash)
                throws RemoteException {}

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) throws RemoteException {}

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDisplayAreaOrganizer {
        static final int TRANSACTION_onDisplayAreaAppeared = 1;
        static final int TRANSACTION_onDisplayAreaInfoChanged = 3;
        static final int TRANSACTION_onDisplayAreaVanished = 2;

        public Stub() {
            attachInterface(this, IDisplayAreaOrganizer.DESCRIPTOR);
        }

        public static IDisplayAreaOrganizer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDisplayAreaOrganizer.DESCRIPTOR);
            if (iin != null && (iin instanceof IDisplayAreaOrganizer)) {
                return (IDisplayAreaOrganizer) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onDisplayAreaAppeared";
                case 2:
                    return "onDisplayAreaVanished";
                case 3:
                    return "onDisplayAreaInfoChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDisplayAreaOrganizer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDisplayAreaOrganizer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    DisplayAreaInfo _arg0 =
                            (DisplayAreaInfo) data.readTypedObject(DisplayAreaInfo.CREATOR);
                    SurfaceControl _arg1 =
                            (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
                    data.enforceNoDataAvail();
                    onDisplayAreaAppeared(_arg0, _arg1);
                    return true;
                case 2:
                    DisplayAreaInfo _arg02 =
                            (DisplayAreaInfo) data.readTypedObject(DisplayAreaInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onDisplayAreaVanished(_arg02);
                    return true;
                case 3:
                    DisplayAreaInfo _arg03 =
                            (DisplayAreaInfo) data.readTypedObject(DisplayAreaInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onDisplayAreaInfoChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDisplayAreaOrganizer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDisplayAreaOrganizer.DESCRIPTOR;
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl leash)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    _data.writeTypedObject(displayAreaInfo, 0);
                    _data.writeTypedObject(leash, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    _data.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.window.IDisplayAreaOrganizer
            public void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDisplayAreaOrganizer.DESCRIPTOR);
                    _data.writeTypedObject(displayAreaInfo, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
