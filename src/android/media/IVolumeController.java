package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IVolumeController extends IInterface {

    public static class Default implements IVolumeController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IVolumeController
        public void dismiss() throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void displayCsdWarning(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void displaySafeVolumeWarning(int i) throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void displayVolumeLimiterToast() throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void masterMuteChanged(int i) throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void setA11yMode(int i) throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void setLayoutDirection(int i) throws RemoteException {
        }

        @Override // android.media.IVolumeController
        public void volumeChanged(int i, int i2) throws RemoteException {
        }
    }

    void dismiss() throws RemoteException;

    void displayCsdWarning(int i, int i2) throws RemoteException;

    void displaySafeVolumeWarning(int i) throws RemoteException;

    void displayVolumeLimiterToast() throws RemoteException;

    void masterMuteChanged(int i) throws RemoteException;

    void setA11yMode(int i) throws RemoteException;

    void setLayoutDirection(int i) throws RemoteException;

    void volumeChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IVolumeController {
        public static final String DESCRIPTOR = "android.media.IVolumeController";
        static final int TRANSACTION_dismiss = 5;
        static final int TRANSACTION_displayCsdWarning = 7;
        static final int TRANSACTION_displaySafeVolumeWarning = 1;
        static final int TRANSACTION_displayVolumeLimiterToast = 8;
        static final int TRANSACTION_masterMuteChanged = 3;
        static final int TRANSACTION_setA11yMode = 6;
        static final int TRANSACTION_setLayoutDirection = 4;
        static final int TRANSACTION_volumeChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVolumeController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVolumeController)) {
                return (IVolumeController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "displaySafeVolumeWarning";
                case 2:
                    return "volumeChanged";
                case 3:
                    return "masterMuteChanged";
                case 4:
                    return "setLayoutDirection";
                case 5:
                    return "dismiss";
                case 6:
                    return "setA11yMode";
                case 7:
                    return "displayCsdWarning";
                case 8:
                    return "displayVolumeLimiterToast";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    displaySafeVolumeWarning(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    volumeChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    masterMuteChanged(readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLayoutDirection(readInt5);
                    return true;
                case 5:
                    dismiss();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setA11yMode(readInt6);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    displayCsdWarning(readInt7, readInt8);
                    return true;
                case 8:
                    displayVolumeLimiterToast();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVolumeController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IVolumeController
            public void displaySafeVolumeWarning(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void volumeChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void masterMuteChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void setLayoutDirection(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void dismiss() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void setA11yMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void displayCsdWarning(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IVolumeController
            public void displayVolumeLimiterToast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
